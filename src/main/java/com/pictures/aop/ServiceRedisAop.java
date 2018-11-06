package com.pictures.aop;

import com.pictures.cache.RedisCache;
import com.pictures.cache.annotation.CacheEvict;
import com.pictures.cache.annotation.Cacheable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 运用AOP+注解的方式实现redis缓存（序列化采用protostuff）
 * 在需要被缓存的方法上添加cache相关的自定义注解，由AOP去处理，对注解的参数实现了SPEL支持。
 *
 * @author wangning
 * @date 2018/10/18 11:34
 */

@Aspect
@Component
public class ServiceRedisAop {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceRedisAop.class);
    @Autowired
    private RedisCache cache;

    /**
     * 添加缓存
     */
    @Around("@annotation(com.pictures.cache.annotation.Cacheable)")
    public Object addCache(ProceedingJoinPoint joinPoint) throws Throwable {
        // 得到被代理的方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 得到被代理的方法上的注解
        Cacheable cacheable = method.getAnnotation(com.pictures.cache.annotation.Cacheable.class);
        // 解析得到 fieldKey="#id" 的内容
        String cacheKey = cacheable.key() + "-" + String.valueOf(parseKey(cacheable.fieldKey(), method, joinPoint.getArgs()));
        //获取方法的返回类型,让缓存可以返回正确的类型
        Class returnType = ((MethodSignature) joinPoint.getSignature()).getReturnType();

        if (cacheable.isList()) {
            List result = cache.getListCache(cacheKey, Class.forName(cacheable.contentClass()));
            if (result != null) {
                LOG.info("list缓存命中：" + cacheKey);
            }
            if (result == null) {
                LOG.info("list缓存未命中：" + cacheKey);
                result = (List) joinPoint.proceed();
                //后置：将数据库查到的数据保存到Redis
                try {
                    cache.putListCache(cacheKey, result);
                } catch (RuntimeException e) {
                    LOG.info("数据有误，添加cache失败！");
                }
            }
            return result;
        } else {
            Object result = cache.getCache(cacheKey, returnType);
            if (result != null) {
                LOG.info("缓存命中：" + cacheKey);
            }
            if (result == null) {
                LOG.info("缓存未命中：" + cacheKey);
                result = joinPoint.proceed();
                //后置：将数据库查到的数据保存到Redis
                try {
                    cache.putCache(cacheKey, result);
                } catch (RuntimeException e) {
                    LOG.info("数据有误，添加cache失败！");
                }
            }
            return result;
        }
    }

    /**
     * 清除缓存
     */
    @Around("@annotation(com.pictures.cache.annotation.CacheEvict)")
    public Object evictCache(ProceedingJoinPoint joinPoint) throws Throwable {
        // 得到被代理的方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 得到被代理的方法上的注解
        CacheEvict cacheEvict = method.getAnnotation(com.pictures.cache.annotation.CacheEvict.class);
        // 解析得到 fieldKey="#id" 的内容
        String cacheKey = cacheEvict.key() + "-" + String.valueOf(parseKey(cacheEvict.fieldKey(), method, joinPoint.getArgs()));
        cache.deleteCache(cacheKey);
        // 单个picture元素修改时，其列表的cache也需要修改，因此也删除列表缓存
        cache.deleteCacheWithPattern("getPictures*");
        return joinPoint.proceed();
    }

    /**
     * 获取缓存的key
     * key定义在注解上，支持SPEL表达式
     *
     * @param key    cacheable.fieldKey(),如"#id"
     * @param method 被注解的方法对象
     * @param args   被注解的方法参数
     * @return
     */
    private long parseKey(String key, Method method, Object[] args) {
        // 获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);
        // 使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        // SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中，(参数名，obj)键值对
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, long.class);
    }
}
