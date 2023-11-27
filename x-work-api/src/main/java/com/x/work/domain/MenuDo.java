package com.x.work.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : xuemingqi
 * @since : 2023/2/23 17:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "MenuDto", description = "菜单信息")
public class MenuDo implements Serializable {

    @Schema(name = "menuId", description = "菜单id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long menuId;

    @Schema(name = "menuName", description = "菜单名称")
    private String menuName;

    @Schema(name = "sort", description = "排序值")
    private int sort;
}
