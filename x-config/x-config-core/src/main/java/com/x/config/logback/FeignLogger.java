package com.x.config.logback;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author xuemingqi
 */
@Component
@Slf4j
public class FeignLogger extends Logger {
    @Override
    protected void log(String s, String s1, Object... objects) {

    }

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        String bodyText = request.charset() != null ? new String(request.body(), request.charset()) : null;
        log.info("url:[{}],method:[{}],headers:[{}],request:[{}]", request.url(), request.httpMethod(), request.headers(), bodyText);
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        if (response.body() == null) {
            return response;
        }
        String result = "";
        byte[] bodyData = Util.toByteArray(response.body().asInputStream());
        int bodyLength = bodyData.length;
        if (bodyLength > 0) {
            result = Util.decodeOrDefault(bodyData, Util.UTF_8, "Binary data");
        }
        Response build = response.toBuilder().body(bodyData).build();
        Request request = build.request();
        log.info("url:[{}],method:[{}],headers:[{}],response:[{}]", request.url(), request.httpMethod(), request.headers(), result);
        return build;
    }
}
