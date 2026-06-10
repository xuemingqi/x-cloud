package com.x.config.feign.conf;

import com.x.common.constants.CommonConstant;
import com.x.common.utils.ServletUtil;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：xuemingqi
 * @since ：Created in 2023/1/10 17:16
 */
@Configuration
public class FeignConfiguration implements RequestInterceptor {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Override
    public void apply(RequestTemplate res) {
        String traceId = ServletUtil.getHeader(CommonConstant.TRACE_ID);
        String token = ServletUtil.getHeader(CommonConstant.TOKEN);

        if (null != token) {
            res.header(CommonConstant.TOKEN, token);
        }
        if (null != traceId) {
            res.header(CommonConstant.TRACE_ID, traceId);
        }
    }
}
