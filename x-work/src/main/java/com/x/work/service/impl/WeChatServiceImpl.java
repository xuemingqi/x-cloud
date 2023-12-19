package com.x.work.service.impl;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.x.common.enums.ResponseCodeEnum;
import com.x.common.response.BaseResult;
import com.x.common.response.ResultUtil;
import com.x.common.utils.JsonUtil;
import com.x.common.utils.ServletUtil;
import com.x.work.common.property.WeChatProperty;
import com.x.work.service.WeChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

/**
 * @author : xuemingqi
 * @since : 2023/12/5 15:58
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class WeChatServiceImpl implements WeChatService {

    private final WxMpService wxMpService;

    private final WeChatProperty weChatProperty;

    @Override
    public void getQr(String state) {
        try {
            ImgUtil.write(getQrImage(state), ImgUtil.IMAGE_TYPE_PNG, ServletUtil.getResponse().getOutputStream());
        } catch (Exception e) {
            log.info(ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public BaseResult<?> getQrData(String state) {
        return ResultUtil.buildResultSuccess(ImgUtil.toBase64DataUri(getQrImage(state), ImgUtil.IMAGE_TYPE_PNG));
    }

    @Override
    public BaseResult<WxMpUser> userInfo(String code, String state) {
        try {
            //获取token
            WxMpOAuth2AccessToken token = getToken(code);
            log.info(JsonUtil.toJsonStr(token));
            //获取用户信息
            WxMpUser userInfo = wxMpService.oauth2getUserInfo(token, null);
            log.info(JsonUtil.toJsonStr(userInfo));
            return ResultUtil.buildResultSuccess(userInfo);
        } catch (WxErrorException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return ResultUtil.buildResultError(ResponseCodeEnum.WX_GET_USER_INFO_ERROR);
    }

    /**
     * 获取token信息
     *
     * @param code code
     * @return token信息
     */
    private WxMpOAuth2AccessToken getToken(String code) {
        try {
            return wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    /**
     * 获取鉴权地址
     *
     * @param scope 鉴权类型
     * @param state state
     * @return 扫码鉴权地址
     */
    private String getRedirectUrl(String scope, String state) {
        return wxMpService.oauth2buildAuthorizationUrl(getCallbackUrl(), scope, state);
    }

    /**
     * 获取回调地址
     *
     * @return 回调地址
     */
    private String getCallbackUrl() {
        return weChatProperty.getDomain() + weChatProperty.getRedirectUrl();
    }

    /**
     * 获取验证二维码图片
     *
     * @param state state
     * @return 图片
     */
    private BufferedImage getQrImage(String state) {
        return QrCodeUtil.generate(getRedirectUrl(WxConsts.OAuth2Scope.SNSAPI_USERINFO, state),
                QrConfig.create()
                        .setWidth(300)
                        .setHeight(300));
    }
}
