package com.x.config.logback;

import com.x.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author xuemingqi
 */
@Slf4j
@Aspect
@Component
public class LogBackAop {

    @Around("execution(* com.x.*.controller.*.*(..)) || execution(* com.x.*.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        String declaringName = method.getDeclaringClass().getName();
        String methodName = declaringName.substring(declaringName.lastIndexOf(".") + 1) + "." + method.getName();
        //打印请求日志
        log.info("method:[{}],request:[{}]", methodName, paramToJson(args));
        //打印返回日志
        Object result = pjp.proceed();
        log.info("method:[{}],response:[{}]", methodName, JsonUtil.toJsonStrNonNull(result));
        return result;
    }

    private String paramToJson(Object[] a) {
        if (a == null) {
            return "null";
        }
        int iMax = a.length - 1;
        if (iMax == -1) {
            return "[]";
        }
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(JsonUtil.toJsonStrIfNotAlready(a[i]));
            if (i == iMax) {
                return b.append(']').toString();
            }
            b.append(", ");
        }
    }
}
