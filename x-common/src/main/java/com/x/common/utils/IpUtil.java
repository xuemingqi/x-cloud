package com.x.common.utils;

import cn.hutool.http.HttpUtil;
import com.x.common.dto.IpCityDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/**
 * @author : xuemingqi
 * @since : 2023/2/10 15:32
 */
@Slf4j
public class IpUtil {

    /**
     * 获取本机ip
     */
    public static String getLocalIp() {
        return getLocalHost().getHostAddress();
    }

    /**
     * 本机地址信息
     */
    @SneakyThrows
    public static InetAddress getLocalHost() {
        return InetAddress.getLocalHost();
    }


    /**
     * 获取ip归属地信息
     *
     * @param ip ip
     */
    public static IpCityDto getIpMessage(String ip) {
        final String IP_URL = "https://whois.pconline.com.cn/ipJson.jsp?ip=" + ip;
        String rspStr = HttpUtil.get(IP_URL);
        String messageStr = rspStr.substring(rspStr.indexOf("({") + 1, rspStr.lastIndexOf(")"));
        return JsonUtil.jsonToBean(messageStr, IpCityDto.class);
    }
}
