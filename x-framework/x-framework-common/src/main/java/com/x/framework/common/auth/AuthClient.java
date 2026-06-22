package com.x.framework.common.auth;

import com.x.framework.common.dto.UserInfo;

/**
 * Authentication adapter contract.
 */
public interface AuthClient {

    UserInfo check();
}
