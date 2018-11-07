package com.pictures.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.pictures.cache.RedisCache.CACHETIME;

/**
 * 缓存添加
 *
 * @author wangning
 * @date 2018/11/5 15:53
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {
    /**
     * 一类缓存的通用前缀
     */
    String key();

    /**
     * 当前object或list的唯一标识，如id
     */
    String fieldKey();

    /**
     * 被缓存的内容是否是List标识
     */
    boolean isList() default false;

    /**
     * 如果被缓存的内容是List，那么需要提供List所存储的类的完整名称
     *
     * @return List所存储的类的完整名称
     */
    String contentClass() default "";

    /**
     * cache的到期时间，单位为秒
     */
    int expireTime() default CACHETIME;
}
