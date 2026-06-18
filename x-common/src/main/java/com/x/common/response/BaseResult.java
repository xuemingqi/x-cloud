package com.x.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 通用返回结构
 *
 * @author xuemingqi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "BaseResult<T>", description = "通用返回格式")
public class BaseResult<T> implements Serializable {

    @Schema(name = "status", description = "响应状态")
    @JsonIgnore
    private HttpStatus status;

    @Schema(name = "code", description = "响应码", requiredMode = Schema.RequiredMode.REQUIRED)
    private int code;

    @Schema(name = "msg", description = "提示信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private String msg;

    @Schema(name = "data", description = "请求响应数据")
    private T data;
}
