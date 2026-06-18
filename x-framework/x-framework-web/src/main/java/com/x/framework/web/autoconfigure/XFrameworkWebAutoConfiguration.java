package com.x.framework.web.autoconfigure;

import com.x.framework.web.auth.MissingAuthClient;
import com.x.framework.web.exception.handler.CommonExceptionHandler;
import com.x.framework.web.feign.conf.FeignConfiguration;
import com.x.framework.web.filter.BaseFilter;
import com.x.framework.web.filter.CustomResponseBodyAdvice;
import com.x.framework.web.interceptor.InterceptorConfig;
import com.x.framework.web.logback.FeignLogger;
import com.x.framework.web.logback.LogBackAop;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({
        BaseFilter.class,
        CommonExceptionHandler.class,
        CustomResponseBodyAdvice.class,
        FeignConfiguration.class,
        FeignLogger.class,
        InterceptorConfig.class,
        LogBackAop.class,
        MissingAuthClient.class
})
public class XFrameworkWebAutoConfiguration {
}
