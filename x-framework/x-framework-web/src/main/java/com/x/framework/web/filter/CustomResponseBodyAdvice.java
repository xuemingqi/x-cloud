package com.x.framework.web.filter;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author : xuemingqi
 * @since : 2024-09-13 14:50
 */
@ControllerAdvice
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {

//        if (body instanceof BaseResult<?> baseResult) {
//            //根据 BaseResult 的状态来设置 HttpServletResponse 的状态码
//            HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
//            if (baseResult.getStatus() != null) {
//                servletResponse.setStatus(baseResult.getStatus().value());
//            }
//        }
        return body;
    }
}
