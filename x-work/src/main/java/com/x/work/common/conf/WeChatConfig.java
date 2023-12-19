package com.x.work.common.conf;

import com.x.work.common.property.WeChatProperty;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author : xuemingqi
 * @since : 2023/12/5 15:50
 */
@Configuration
public class WeChatConfig {

    @Resource
    private WeChatProperty weChatProperty;

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpDefaultConfigImpl wxMpConfigStorage = new WxMpDefaultConfigImpl();
        wxMpConfigStorage.setAppId(weChatProperty.getAppId());
        wxMpConfigStorage.setSecret(weChatProperty.getSecret());
        return wxMpConfigStorage;
    }
}
