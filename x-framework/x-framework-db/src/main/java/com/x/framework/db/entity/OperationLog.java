package com.x.framework.db.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author : xuemingqi
 * @since : 2025/01/17 10:18
 */

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("operation_log")
public class OperationLog {

    /**
     * 日志id
     */
    @TableId("log_id")
    private Long logId;

    /**
     * 操作状态
     */
    @TableField("code")
    private Integer code;

    /**
     * 操作
     */
    @TableField("operation")
    private String operation;

    /**
     * 操作ip
     */
    @TableField("ip")
    private String ip;

    /**
     * 操作人
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
}
