package com.x.contact.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.x.contact.enums.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : xuemingqi
 * @since : 2023/1/13 17:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UserDo", description = "用户基本信息")
public class UserDo {

    @Schema(name = "id", description = "用户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(name = "name", description = "姓名")
    private String name;

    @Schema(name = "sex", description = "性别")
    private Sex sex;

    @Schema(name = "mobile", description = "手机号")
    private String mobile;

    @Schema(name = "updateTime", description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(name = "createTime", description = "创建时间")
    private LocalDateTime createTime;
}
