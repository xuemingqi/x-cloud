package com.x.framework.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : xuemingqi
 * @since : 2024/8/12 上午10:07
 */
@Data
@Accessors(chain = true)
public class VerifyImage {
    /**
     * 原图
     */
    String srcImage;
    /**
     * 滑块图
     */
    String cutImage;

    /**
     * X坐标
     */
    Integer XPosition;

    /**
     * Y坐标
     */
    Integer YPosition;
}
