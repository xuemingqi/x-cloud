package com.x.framework.web.auth;

import com.x.framework.common.auth.AuthClient;
import com.x.framework.common.dto.UserInfo;
import com.x.framework.common.enums.ResponseCodeEnum;
import com.x.framework.common.exception.XException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(AuthClient.class)
public class MissingAuthClient implements AuthClient {

    @Override
    public UserInfo check() {
        throw new XException(ResponseCodeEnum.PERMISSION_ERROR);
    }
}
