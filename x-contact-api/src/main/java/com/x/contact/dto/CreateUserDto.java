package com.x.contact.dto;

import com.x.contact.enums.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author : xuemingqi
 * @since : 2023/1/14 18:09
 */
@Data
@Schema(name = "CreateUserDto", description = "添加用户")
public class CreateUserDto {

    @Schema(name = "name", description = "姓名")
    @NotEmpty(message = "姓名不能为空")
    private String name;

    @Schema(name = "sex", description = "性别")
    @NotNull(message = "性别不能为空")
    private Sex sex;

    @Schema(name = "mobile", description = "手机号")
    @NotEmpty(message = "手机号不能为空")
    private String mobile;

    @Schema(name = "password", description = "密码")
    @NotEmpty(message = "密码不能为空")
    private String password;

}
