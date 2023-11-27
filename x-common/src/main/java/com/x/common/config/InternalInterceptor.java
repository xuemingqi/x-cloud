package com.x.common.config;

import com.x.common.constants.CommonConstant;
import com.x.common.utils.DateUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author : xuemingqi
 * @since : 2023/1/14 15:49
 */
public class InternalInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        //时间戳
        String timestamp = DateUtil.getTimestamp().toString();
        //url
        String url = template.path();
        //md5 key
        String token = DigestUtils.md5Hex(CommonConstant.KEY + url + timestamp);

        //set header
        template.header(CommonConstant.INTERNAL_TOKEN, token);
        template.header(CommonConstant.INTERNAL_TIMESTAMP, timestamp);
    }
}
