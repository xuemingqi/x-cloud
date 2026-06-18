package com.x.common.message;

import cn.hutool.core.util.IdUtil;
import com.x.common.utils.IpUtil;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : xuemingqi
 * @since : 2024-09-14 13:36
 */
@Data
public abstract class BaseMessage {

    /**
     * 消息id
     */
    private final String msgId;

    /**
     * 主机
     */
    private final String host;

    /**
     * 时间
     */
    private final LocalDateTime time;


    public BaseMessage() {
        msgId = IdUtil.fastSimpleUUID();
        host = IpUtil.getLocalIp();
        time = LocalDateTime.now();
    }

    public String getTopic() {
        return this.getClass().getSimpleName();
    }
}
