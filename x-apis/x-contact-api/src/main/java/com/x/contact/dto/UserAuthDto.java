package com.x.contact.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author : xuemingqi
 * @since : 2023/1/16 9:19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UserAuthDto", description = "验证用户")
public class UserAuthDto {

    @Schema(name = "mobile", description = "用户手机号")
    @NotEmpty(message = "用户手机号不能为空")
    private String mobile;

    @Schema(name = "password", description = "密码")
    @NotNull(message = "密码不能为空")
    private String password;

}
