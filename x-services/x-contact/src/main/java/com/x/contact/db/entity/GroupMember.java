package com.x.contact.db.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.x.contact.db.enums.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author : xuemingqi
 * @since : 2025/04/07 11:09
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("group_member")
public class GroupMember {

    /**
     * 主键id
     */
    @TableId("id")
    private Long id;

    /**
     * 群组id
     */
    @TableField("group_id")
    private Long groupId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 群组角色
     */
    @TableField("member_type")
    private MemberType type;

    /**
     * 加入时间
     */
    @TableField("join_time")
    private LocalDateTime JoinTime;
}
