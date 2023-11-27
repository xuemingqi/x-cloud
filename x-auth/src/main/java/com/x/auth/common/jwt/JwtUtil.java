package com.x.auth.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.x.common.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * @author ：xuemingqi
 * @since ：created in 2023/1/10 15:56
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class JwtUtil {

    /**
     * 创建token
     *
     * @param user 用户信息
     * @return token
     */
    public String createToken(UserInfoClaims user) {
        //过期时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, JwtConfig.expiresTime);
        Date expiresDate = nowTime.getTime();
        //创建token
        return JWT.create().withAudience(user.getUserId()).withIssuedAt(new Date())
                .withExpiresAt(expiresDate).withClaim(user.getUserId(), JsonUtil.toJsonStr(user))
                .withIssuer(JwtConfig.iss)
                .sign(Algorithm.HMAC256(user.getUserId() + JwtConfig.sign));
    }

    /**
     * 获取签发对象
     *
     * @param token token
     * @return 签发对象
     */
    public String getAudience(String token) {
        String audience = null;
        try {
            audience = JWT.decode(token).getAudience().get(0);
        } catch (Exception ignored) {

        }
        return audience;
    }

    /**
     * 解析token信息
     *
     * @param token    token
     * @param audience 签发对象
     * @return 用户信息
     */
    private UserInfoClaims getClaimByAudience(String token, String audience) {
        String str = JWT.decode(token).getClaim(audience).asString();
        return JsonUtil.jsonToBean(str, UserInfoClaims.class);
    }

    /**
     * 解析token用户信息
     *
     * @param token token
     * @return 用户信息
     */
    public UserInfoClaims getUser(String token) {
        String userId = getAudience(token);
        return getClaimByAudience(token, userId);
    }

    /**
     * 验证token有效性
     *
     * @param token    token
     * @param audience 签发机构
     * @return true/false
     */
    public boolean verifyToken(String token, String audience) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(audience + JwtConfig.sign)).build();
            verifier.verify(token);
        } catch (TokenExpiredException | SignatureVerificationException e) {
            return false;
        }
        return true;
    }
}
