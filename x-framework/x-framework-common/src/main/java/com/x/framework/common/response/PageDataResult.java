package com.x.framework.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "PageDataResult", description = "分页信息")
public class PageDataResult {

    @Schema(name = "page", description = "当前页", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer page;

    @Schema(name = "pageTotal", description = "总页数", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer pageTotal;

    @Schema(name = "pageSize", description = "分页条数", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer pageSize;

    @Schema(name = "hasNextPages", description = "是否有下一页", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean hasNextPages;

}
