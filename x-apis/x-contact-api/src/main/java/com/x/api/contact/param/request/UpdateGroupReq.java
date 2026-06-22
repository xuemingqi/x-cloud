package com.x.api.contact.param.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : xuemingqi
 * @since : 2025/04/07 10:04
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "UpdateGroupReq", description = "修改群组请求参数")
public class UpdateGroupReq extends CreateGroupReq {

}
