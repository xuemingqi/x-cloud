package com.x.framework.web.interceptor;

import com.x.common.constants.CommonConstant;
import com.x.common.auth.AuthClient;
import com.x.common.dto.UserInfo;
import com.x.common.enums.ResponseCodeEnum;
import com.x.common.utils.DateUtil;
import com.x.common.utils.ServerUtil;
import com.x.framework.core.exception.XException;
import com.x.framework.web.annotation.Internal;
import com.x.framework.web.annotation.PassToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

/**
 * @author ：xuemingqi
 * @since ：created in 2023/1/10 17:07
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthClient authClient;

    public AuthInterceptor(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //获取token
        String token = ServerUtil.getTokenByHeaderStr();

        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            log.info("get request: {},handler is not HandlerMethod", request.getRequestURI());
            return true;
        }

        Method method = handlerMethod.getMethod();

        //错误返回
        if ("/error".equals(request.getRequestURI())) {
            return true;
        }

        //内部接口
        if (method.isAnnotationPresent(Internal.class) || method.getDeclaringClass().isAnnotationPresent(Internal.class)) {
            boolean verify = verifyInternalAccess(request);
            if (!verify) {
                log.error("get request: {},internal access verification failed", request.getRequestURI());
                throw new XException(ResponseCodeEnum.INTERNAL_ERROR);
            }
            return true;
        }

        //有PassToken注释则放行
        if (method.getDeclaringClass().isAnnotationPresent(PassToken.class)) {
            PassToken classPassToken = method.getDeclaringClass().getAnnotation(PassToken.class);
            if (classPassToken != null && classPassToken.required()) {
                return true;
            }
        }
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken methodPassToken = method.getAnnotation(PassToken.class);
            if (methodPassToken != null && methodPassToken.required()) {
                return true;
            }
        }

        //token为空
        if (token == null) {
            log.error("get request: {},token is null", request.getRequestURI());
            throw new XException(ResponseCodeEnum.PERMISSION_ERROR);
        }

        //token有效
        UserInfo userInfo = authClient.check();
        MDC.put(CommonConstant.USER_ID, userInfo.getUserId());
        ServerUtil.setAuthenticatedUser(userInfo);
        return true;

    }

    /**
     * 验证内部接口
     */
    private boolean verifyInternalAccess(HttpServletRequest request) {
        //验证header完整
        String token = request.getHeader(CommonConstant.INTERNAL_TOKEN);
        String timestamp = request.getHeader(CommonConstant.INTERNAL_TIMESTAMP);
        if (StringUtils.isBlank(token) || StringUtils.isBlank(timestamp)) {
            return false;
        }

        //验证时间戳
        long now = DateUtil.getTimestamp();
        if (Math.abs(now - Long.parseLong(timestamp)) > (CommonConstant.EXPIRE_TIME * 1000L)) {
            return false;
        }

        //验证token是否正确
        String trueToken = DigestUtils.md5Hex(CommonConstant.KEY + request.getRequestURI() + timestamp);
        return token.equals(trueToken);
    }
}
