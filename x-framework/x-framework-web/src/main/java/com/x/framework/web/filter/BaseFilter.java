package com.x.framework.web.filter;

import com.x.common.constants.CommonConstant;
import com.x.common.utils.IdUtil;
import com.x.common.utils.ServletUtil;
import com.x.common.utils.UserThreadLocalUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author xuemingqi
 */
@Configuration
public class BaseFilter {

    @Bean
    public Filter filter() {
        return new Filter() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                try {
                    HttpServletRequest req = (HttpServletRequest) servletRequest;
                    String traceId = req.getHeader(CommonConstant.TRACE_ID);
                    if (StringUtils.isBlank(traceId)) {
                        traceId = IdUtil.randomAlphanumeric(10);
                    }
                    MDC.put(CommonConstant.TRACE_ID, traceId);
                    MDC.put(CommonConstant.IP, ServletUtil.getRemoteIP());
                    filterChain.doFilter(servletRequest, servletResponse);
                } finally {
                    MDC.clear();
                    UserThreadLocalUtil.clear();
                }
            }

            @Override
            public void destroy() {

            }

            @Override
            public void init(FilterConfig arg0) {
            }
        };
    }
}
