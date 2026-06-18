package com.x.contact.db.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author : xuemingqi
 * @since : 2025/04/03 17:23
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("chat_group")
public class Group {

    /**
     * 群组id
     */
    @TableId("group_id")
    private Long groupId;

    /**
     * 群组名称
     */
    @TableField("group_name")
    private String groupName;

    /**
     * 群组描述
     */
    @TableField("group_description")
    private String groupDescription;

    /**
     * 群公告
     */
    @TableField("group_announcement")
    private String groupAnnouncement;

    /**
     * 群组更新时间
     */
    @TableField("update_time")
    private String updateTime;

    /**
     * 群组创建时间
     */
    @TableField("create_time")
    private String createTime;
}
