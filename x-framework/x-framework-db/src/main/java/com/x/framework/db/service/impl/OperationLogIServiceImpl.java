package com.x.framework.db.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.x.framework.db.entity.OperationLog;
import com.x.framework.db.mapper.OperationLogMapper;
import com.x.framework.db.service.OperationLogIService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;

/**
 * @author : xuemingqi
 * @since : 2025/01/17 10:20
 */
@ConditionalOnClass(BaseMapper.class)
@Service
public class OperationLogIServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogIService {
}
