package com.x.contact.param.request;

import com.x.contact.constants.ValidMsgConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author : xuemingqi
 * @since : 2025/04/07 14:30
 */
@Data
@Schema(name = "AddContactReq", description = "添加联系人请求参数")
public class AddContactReq {

    @NotNull(message = ValidMsgConstant.USER_ID_NULL_ERROR)
    @Schema(name = "userId", description = "用户id")
    private Long userId;
}
