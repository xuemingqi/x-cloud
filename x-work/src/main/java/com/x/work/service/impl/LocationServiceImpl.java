package com.x.work.service.impl;

import com.x.common.dto.IpCityDto;
import com.x.common.utils.IpUtil;
import com.x.config.redis.constants.RedisCommonConstant;
import com.x.work.service.LocationService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author : xuemingqi
 * @since : 2023/7/4 16:36
 */
@Service
public class LocationServiceImpl implements LocationService {

    /**
     * 缓存过期时间: 10天
     */
    private static final int IP_EXPIRE_TIME = 60 * 60 * 24 * 10;

    @Override
    @Cacheable(cacheNames = "IP_LOCATION=" + IP_EXPIRE_TIME, key = "#ip", cacheManager = RedisCommonConstant.CACHE_MANAGER_NAME)
    public IpCityDto getLocationByIp(String ip) {
        return IpUtil.getIpMessage(ip);
    }
}
