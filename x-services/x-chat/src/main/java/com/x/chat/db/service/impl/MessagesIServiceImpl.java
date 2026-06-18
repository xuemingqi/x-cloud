package com.x.chat.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.x.chat.db.entity.Messages;
import com.x.chat.db.mapper.MessagesMapper;
import com.x.chat.db.service.MessagesIService;
import org.springframework.stereotype.Service;

/**
 * @author : xuemingqi
 * @since : 2025/04/03 16:07
 */
@Service
public class MessagesIServiceImpl extends ServiceImpl<MessagesMapper, Messages> implements MessagesIService {
}
