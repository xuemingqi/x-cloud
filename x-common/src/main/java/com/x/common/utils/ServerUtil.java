package com.x.common.utils;

import com.x.common.constants.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.util.ObjectUtils;

/**
 * @author : xuemingqi
 * @since : 2023/1/10 16:50
 */
public class ServerUtil {

    /**
     * 获取traceId,获取不到设置为async可能是内部线程调用
     */
    public static String getTraceId() {
        String traceId = MDC.get(CommonConstant.TRACE_ID);
        return !StringUtils.isBlank(traceId) ? traceId : "async";
    }

    /**
     * 获取token
     */
    public static String getTokenByHeaderStr() {
        String jwtStr = ServletUtil.getHeader(CommonConstant.TOKEN);
        int jwtTokenPreLength = CommonConstant.TOKEN_PRE.length();
        if (ObjectUtils.isEmpty(jwtStr) || !jwtStr.startsWith(CommonConstant.TOKEN_PRE) || jwtStr.length() == jwtTokenPreLength) {
            return null;
        }
        return jwtStr.substring(jwtTokenPreLength);
    }
}
