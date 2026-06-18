package com.x.contact.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.x.contact.db.entity.GroupMember;
import com.x.contact.db.mapper.GroupMemberMapper;
import com.x.contact.db.service.GroupMemberIService;
import org.springframework.stereotype.Service;

/**
 * @author : xuemingqi
 * @since : 2025/04/07 11:27
 */
@Service
public class GroupMemberServiceImpl extends ServiceImpl<GroupMemberMapper, GroupMember> implements GroupMemberIService {
}
