package com.x.framework.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : xuemingqi
 * @since : 2025/01/17 13:59
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {

    /**
     * 锁的key前缀
     */
    String prefix() default "redis_lock";

    /**
     * 锁的key后缀
     */
    String[] suffix() default {};

    /**
     * 加锁等待时间
     */
    long waitTime() default 5;

    /**
     * 锁的释放时间
     */
    long releaseTime() default 5;
}
