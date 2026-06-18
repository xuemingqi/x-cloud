package com.x.auth.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.x.auth.constants.ValidMsgConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author : xuemingqi
 * @since : 2024/8/12 上午11:13
 */
@Data
@Schema(name = "VerificationImgDto", description = "验证滑动验证码请求参数")
public class VerificationImgDto {

    @Schema(name = "codeId", description = "验证码ID")
    @NotNull(message = ValidMsgConstant.CODE_ID_NULL_ERROR)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long codeId;

    @Schema(name = "position", description = "x坐标点")
    @NotNull(message = ValidMsgConstant.IMG_X_POSITION_NULL_ERROR)
    private Integer position;
}
