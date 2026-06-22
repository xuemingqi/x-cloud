package com.x.framework.redis.aspect;

import com.x.framework.common.enums.OperationEnum;
import com.x.framework.common.enums.ResponseCodeEnum;
import com.x.framework.common.response.ResultUtil;
import com.x.framework.common.utils.ServletUtil;
import com.x.framework.redis.annotation.AccessLimit;
import com.x.framework.redis.util.RedisUtil;
import jakarta.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@ConditionalOnClass(RedissonClient.class)
public class AccessLimitAspect {

    @Resource
    private RedisUtil redisUtil;

    @Pointcut("@annotation(com.x.framework.redis.annotation.AccessLimit)")
    private void accessLimitPointcut() {
    }

    @Around(value = "accessLimitPointcut() && @annotation(accessLimit)")
    public Object round(ProceedingJoinPoint joinPoint, AccessLimit accessLimit) throws Throwable {
        //限流规则
        int counts = accessLimit.counts();
        OperationEnum operationEnum = accessLimit.types();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String declaringName = method.getDeclaringClass().getName();
        String methodName = declaringName.substring(declaringName.lastIndexOf(".") + 1) + "." + method.getName();

        //ip
        String ip = ServletUtil.getRemoteIP();

        //key
        String key = DigestUtils.md5Hex(ip + operationEnum + methodName);

        //获取令牌
        boolean limit = redisUtil.tryAcquire(key, counts, 1);
        if (!limit) {
            return ResultUtil.buildResultError(ResponseCodeEnum.TOO_MANY_REQUESTS);
        }
        return joinPoint.proceed();
    }
}
