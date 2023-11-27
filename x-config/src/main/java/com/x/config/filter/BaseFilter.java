package com.x.config.filter;

import com.x.common.constants.CommonConstant;
import com.x.common.utils.ServletUtil;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
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
                    MDC.put(CommonConstant.TRACE_ID, traceId);
                    MDC.put(CommonConstant.IP, ServletUtil.getRemoteIP());
                    filterChain.doFilter(servletRequest, servletResponse);
                } finally {
                    MDC.clear();
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
