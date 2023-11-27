package com.x.work.dto;

import com.x.work.constants.ValidMsgConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * @author : xuemingqi
 * @since : 2023/2/13 10:03
 */
@Data
@Schema(name = "ServerMessageDto", description = "日志请求参数")
public class ServerMessageDto {
    @Schema(name = "文件地址", description = "文件地址")
    @NotBlank(message = ValidMsgConstant.PATH_NULL_ERROR)
    private String path;

    @Schema(name = "行数", description = "行数")
    @NotNull(message = ValidMsgConstant.SIZE_NULL_ERROR)
    private Integer size;
}
