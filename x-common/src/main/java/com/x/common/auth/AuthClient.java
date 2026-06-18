package com.x.common.auth;

import com.x.common.dto.UserInfo;

/**
 * Authentication adapter contract.
 */
public interface AuthClient {

    UserInfo check();
}
