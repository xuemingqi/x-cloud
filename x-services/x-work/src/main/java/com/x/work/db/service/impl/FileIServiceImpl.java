package com.x.work.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.x.work.db.entity.File;
import com.x.work.db.mapper.FileMapper;
import com.x.work.db.service.FileIService;
import org.springframework.stereotype.Service;

/**
 * @author : xuemingqi
 * @since : 2023/2/23 17:38
 */
@Service
public class FileIServiceImpl extends ServiceImpl<FileMapper, File> implements FileIService {
}
