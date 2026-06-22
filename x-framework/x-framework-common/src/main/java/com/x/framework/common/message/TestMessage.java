package com.x.framework.common.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author : xuemingqi
 * @since : 2024-09-14 15:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
public class TestMessage extends BaseMessage {

    private String msg;

}
