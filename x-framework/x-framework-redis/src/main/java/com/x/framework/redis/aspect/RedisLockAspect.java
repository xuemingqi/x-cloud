package com.x.framework.redis.aspect;

import com.x.framework.common.enums.ResponseCodeEnum;
import com.x.framework.common.utils.SpelUtil;
import com.x.framework.redis.annotation.RedisLock;
import com.x.framework.common.exception.XException;
import com.x.framework.redis.util.RedisUtil;
import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author : xuemingqi
 * @since : 2025/01/17 14:06
 */
@Aspect
@Component
@ConditionalOnClass(RedissonClient.class)
public class RedisLockAspect {

    @Resource
    private RedisUtil redisUtil;


    @Pointcut("@annotation(com.x.framework.redis.annotation.RedisLock)")
    private void redisLockPointcut() {
    }

    @Around(value = "redisLockPointcut() && @annotation(redisLock)")
    public Object round(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        String key = redisLock.prefix();
        if (redisLock.suffix().length != 0) {
            String[] suffix = SpelUtil.getParameters(redisLock.suffix(), method, args);
            key += ":" + String.join(":", suffix);
        }
        boolean lock = redisUtil.lock(key, redisLock.releaseTime(), redisLock.waitTime());
        try {
            if (lock) {
                return joinPoint.proceed();
            }
            throw new XException(ResponseCodeEnum.TOO_MANY_REQUESTS);
        } finally {
            if (lock) {
                redisUtil.unLock(key);
            }
        }
    }
}
