package com.x.api.auth.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : xuemingqi
 * @since : 2024/8/12 上午10:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "VerificationImgDo", description = "图片滑动验证返回信息")
public class VerificationImgDo {

    @Schema(name = "id", description = "验证码ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(name = "srcImage", description = "原图Base64编码")
    private String srcImage;

    @Schema(name = "cutImage", description = "裁剪图Base64编码")
    private String cutImage;
}
