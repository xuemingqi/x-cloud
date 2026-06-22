package com.x.api.work.domain;

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
 * @since : 2023/2/24 10:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "FileSimpleInfo", description = "文件索引信息")
public class FileSimpleDo implements Serializable {

    @Schema(name = "fileId", description = "文件id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fileId;

    @Schema(name = "fileName", description = "文件名称")
    private String fileName;

}
