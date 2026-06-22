package com.x.framework.db.aspect;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.x.framework.common.constants.CommonConstant;
import com.x.framework.common.enums.ResponseCodeEnum;
import com.x.framework.common.utils.ServletUtil;
import com.x.framework.common.utils.SpelUtil;
import com.x.framework.common.utils.StringUtil;
import com.x.framework.db.annotation.XLog;
import com.x.framework.db.entity.OperationLog;
import com.x.framework.db.service.OperationLogIService;
import com.x.framework.common.exception.XBaseException;
import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@ConditionalOnClass(BaseMapper.class)
public class XLogAspect {

    @Resource
    private Snowflake snowflake;

    @Resource
    private OperationLogIService operationLogIService;

    @Pointcut("@annotation(com.x.framework.db.annotation.XLog)")
    private void xLogPointcut() {
    }

    @Around(value = "xLogPointcut() && @annotation(xLog)")
    public Object round(ProceedingJoinPoint joinPoint, XLog xLog) throws Throwable {
        // 保存操作日志
        String userId = MDC.get(CommonConstant.USER_ID);
        OperationLog log = createOperationLog(userId);
        ResponseCodeEnum responseCodeEnum = ResponseCodeEnum.SUCCESS;
        String logContent = responseCodeEnum.getMsg();

        try {
            return joinPoint.proceed();
        } catch (XBaseException e) {
            responseCodeEnum = e.getResponseCodeEnum();
            logContent = responseCodeEnum.getMsg();
            throw e;
        } catch (Exception e) {
            responseCodeEnum = ResponseCodeEnum.INTERNAL_SERVER_ERROR;
            logContent = responseCodeEnum.getMsg();
            throw e;
        } finally {
            finalizeLog(joinPoint, xLog, log, responseCodeEnum, logContent);
        }
    }

    private OperationLog createOperationLog(String userId) {
        return new OperationLog()
                .setLogId(snowflake.nextId())
                .setIp(ServletUtil.getRemoteIP())
                .setUserId(Long.parseLong(userId));
    }

    private void finalizeLog(ProceedingJoinPoint joinPoint, XLog xLog, OperationLog log, ResponseCodeEnum responseCodeEnum, String logContent) {
        if (responseCodeEnum.equals(ResponseCodeEnum.SUCCESS)) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Object[] args = joinPoint.getArgs();
            logContent = getOperation(xLog.operation(), xLog.parameters(), method, args);
        }
        log.setCode(responseCodeEnum.getCode());
        log.setOperation(logContent);
        operationLogIService.save(log);
    }

    /**
     * 获取操作内容
     *
     * @param operation  操作内容
     * @param parameters 参数
     * @param method     方法
     * @param arguments  参数值
     * @return 操作内容
     */
    private String getOperation(String operation, String[] parameters, Method method, Object[] arguments) {
        String[] arr = SpelUtil.getParameters(parameters, method, arguments);
        operation = StringUtil.format(operation, (Object[]) Arrays.copyOf(arr, arr.length));
        return operation;
    }
}
