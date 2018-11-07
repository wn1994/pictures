package com.pictures.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存清除
 *
 * @author wangning
 * @date 2018/11/5 20:24
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheEvict {
    /**
     * 一类缓存的通用前缀
     */
    String key();

    /**
     * 当前object或list的唯一标识，如id
     */
    String fieldKey();

    int expireTime() default 3600;
}