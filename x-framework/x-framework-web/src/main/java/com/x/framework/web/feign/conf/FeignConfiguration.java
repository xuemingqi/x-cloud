package com.x.framework.web.feign.conf;

import com.x.framework.common.constants.CommonConstant;
import com.x.framework.common.utils.IdUtil;
import com.x.framework.common.utils.StringUtil;
import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author ：xuemingqi
 * @since ：Created in 2023/1/10 17:16
 */
@Configuration
public class FeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return res -> {
            String traceId = getCurrentRequestHeader(CommonConstant.TRACE_ID);
            String token = getCurrentRequestHeader(CommonConstant.TOKEN);

            if (null != token) {
                res.header(CommonConstant.TOKEN, token);
            }
            if (StringUtil.isBlank(traceId)) {
                traceId = MDC.get(CommonConstant.TRACE_ID);
            }
            if (StringUtil.isBlank(traceId)) {
                traceId = IdUtil.randomAlphanumeric(10);
            }
            res.header(CommonConstant.TRACE_ID, traceId);
        };
    }

    private String getCurrentRequestHeader(String name) {
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes) {
            HttpServletRequest request = attributes.getRequest();
            return request.getHeader(name);
        }
        return null;
    }
}
