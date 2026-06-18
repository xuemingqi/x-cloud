package com.x.contact.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.x.contact.db.entity.Group;
import com.x.contact.db.mapper.GroupMapper;
import com.x.contact.db.service.GroupIService;
import org.springframework.stereotype.Service;

/**
 * @author : xuemingqi
 * @since : 2025/04/03 17:36
 */
@Service
public class GroupIServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupIService {
}
