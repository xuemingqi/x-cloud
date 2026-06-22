package com.x.api.auth.client;

import com.x.api.auth.api.LoginApi;
import com.x.framework.common.auth.AuthClient;
import com.x.framework.common.dto.UserInfo;
import com.x.framework.common.enums.ResponseCodeEnum;
import com.x.framework.common.response.BaseResult;
import com.x.framework.common.exception.XException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(AuthClient.class)
public class FeignAuthClient implements AuthClient {

    private final ObjectProvider<LoginApi> loginApiProvider;

    public FeignAuthClient(ObjectProvider<LoginApi> loginApiProvider) {
        this.loginApiProvider = loginApiProvider;
    }

    @Override
    public UserInfo check() {
        LoginApi loginApi = loginApiProvider.getObject();
        BaseResult<UserInfo> result = loginApi.check();
        if (result != null && ResponseCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
            return result.getData();
        }
        throw new XException(ResponseCodeEnum.PERMISSION_ERROR);
    }
}
