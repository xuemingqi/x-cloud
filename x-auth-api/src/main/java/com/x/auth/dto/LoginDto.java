package com.x.auth.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.x.auth.constants.ValidMsgConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * @author ：xuemingqi
 * @since ：created in 2023/1/10 15:17
 */
@Data
@Schema(name = "LoginDto", description = "登录请求参数")
public class LoginDto {

    @Schema(name = "mobile", description = "手机号")
    @NotBlank(message = ValidMsgConstant.MOBILE_NULL_ERROR)
    private String mobile;

    @Schema(name = "password", description = "密码")
    @NotBlank(message = ValidMsgConstant.PASSWORD_NULL_ERROR)
    private String password;

    @Schema(name = "codeId", description = "验证码ID")
    @NotNull(message = ValidMsgConstant.CODE_ID_NULL_ERROR)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long codeId;

    @Schema(name = "codeValue", description = "验证码")
    @NotBlank(message = ValidMsgConstant.CODE_VALUE_NULL_ERROR)
    private String codeValue;
}
