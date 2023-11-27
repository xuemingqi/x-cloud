package com.x.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回结构
 *
 * @author xuemingqi
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "BaseResult<T>", description = "通用返回格式")
public class BaseResult<T> implements Serializable {

    @Schema(name = "code", description = "响应码", required = true)
    private int code;

    @Schema(name = "msg", description = "提示信息", required = true)
    private String msg;

    @Schema(name = "data", description = "请求响应数据")
    private T data;
}
