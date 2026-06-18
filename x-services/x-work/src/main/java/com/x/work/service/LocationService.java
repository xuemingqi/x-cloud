package com.x.work.service;

import com.x.common.dto.IpCityDto;

/**
 * @author : xuemingqi
 * @since : 2023/7/4 16:35
 */
public interface LocationService {

    /**
     * 根据ip获取地区信息
     *
     * @param ip ip
     * @return 地区信息
     */
    IpCityDto getLocationByIp(String ip);
}
