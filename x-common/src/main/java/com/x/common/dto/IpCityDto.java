package com.x.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : xuemingqi
 * @since : 2023/2/10 15:37
 */
@Data
public class IpCityDto implements Serializable {

    /**
     * ip地址
     */
    private String ip;

    /**
     * 省名称
     */
    private String pro;

    /**
     * 省code码
     */
    private String proCode;

    /**
     * 市名称
     */
    private String city;

    /**
     * 市code码
     */
    private String cityCode;

    /**
     * 区名称
     */
    private String region;

    /**
     * 区code码
     */
    private String regionCode;

    private String regionNames;

    /**
     * 运营商名称
     */
    private String addr;

    /**
     * 错误
     */
    private String err;
}
