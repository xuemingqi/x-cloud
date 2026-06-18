package com.x.auth.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：xuemingqi
 * @since ：created in 2023/1/10 15:31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "VerificationCodeDo", description = "验证码返回信息")
public class VerificationCodeDo {

    @Schema(name = "id", description = "验证码ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(name = "imgBase64", description = "验证码Base64编码")
    private String imgBase64;

}
