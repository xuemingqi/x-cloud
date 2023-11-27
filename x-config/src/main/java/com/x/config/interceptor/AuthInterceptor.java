package com.x.config.interceptor;

import com.x.auth.api.LoginApi;
import com.x.common.constants.CommonConstant;
import com.x.common.enums.ResponseCodeEnum;
import com.x.common.response.BaseResult;
import com.x.common.response.ResultUtil;
import com.x.common.utils.DateUtil;
import com.x.common.utils.JsonUtil;
import com.x.common.utils.ServerUtil;
import com.x.config.annotation.Internal;
import com.x.config.annotation.PassToken;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * @author ：xuemingqi
 * @since ：created in 2023/1/10 17:07
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private LoginApi loginApi;

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws IOException {
        //获取token
        String token = ServerUtil.getTokenByHeaderStr();

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Class<?> clazz = handlerMethod.getBeanType();

        //错误返回
        if (BasicErrorController.class.equals(clazz)) {
            return true;
        }

        //内部接口
        if (method.isAnnotationPresent(Internal.class) || method.getDeclaringClass().isAnnotationPresent(Internal.class)) {
            boolean verify = verifyInternalAccess(request);
            if (!verify) {
                result(response, ResponseCodeEnum.INTERNAL_ERROR);
                return false;
            }
            return true;
        }

        //有PassToken注释则放行
        if (method.getDeclaringClass().isAnnotationPresent(PassToken.class)) {
            PassToken classPassToken = method.getDeclaringClass().getAnnotation(PassToken.class);
            if ((classPassToken != null && classPassToken.required())) {
                return true;
            }
        }
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken methodPassToken = method.getAnnotation(PassToken.class);
            if ((methodPassToken != null && methodPassToken.required())) {
                return true;
            }
        }

        // token为空
        if (token == null) {
            result(response, ResponseCodeEnum.PERMISSION_ERROR);
            return false;
        }

        //token有效
        BaseResult<?> checkResult = loginApi.check();
        if (null != checkResult && checkResult.getCode() == (ResponseCodeEnum.SUCCESS.getCode())) {
            return true;
        }

        //无权限
        result(response, ResponseCodeEnum.PERMISSION_ERROR);
        return false;
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

    private void result(HttpServletResponse response, ResponseCodeEnum codeEnum) throws IOException {
        //返回字符集支持UTF-8
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        //返回
        response.getWriter().write(JsonUtil.toJsonStr(ResultUtil.buildResultError(codeEnum)));
    }
}
