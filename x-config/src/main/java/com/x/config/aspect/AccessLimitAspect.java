package com.x.config.aspect;

import com.x.common.enums.OperationEnum;
import com.x.common.enums.ResponseCodeEnum;
import com.x.common.response.ResultUtil;
import com.x.common.utils.ServletUtil;
import com.x.config.annotation.AccessLimit;
import com.x.config.redis.util.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RateIntervalUnit;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Aspect
@Component
@ConditionalOnBean(RedisUtil.class)
public class AccessLimitAspect {

    @Resource
    private RedisUtil redisUtil;

    @Pointcut("@annotation(com.x.config.annotation.AccessLimit)")
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
        String key = ip + operationEnum + methodName;

        //获取令牌
        boolean limit = redisUtil.tryAcquire(key, counts, RateIntervalUnit.SECONDS);
        if (!limit) {
            return ResultUtil.buildResultError(ResponseCodeEnum.TOO_MANY_REQUESTS);
        }
        return joinPoint.proceed();
    }
}
