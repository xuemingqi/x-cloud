package com.x.work.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.x.work.db.entity.Menu;
import com.x.work.db.mapper.MenuMapper;
import com.x.work.db.service.MenuIService;
import org.springframework.stereotype.Service;

/**
 * @author : xuemingqi
 * @since : 2023/2/23 17:20
 */
@Service
public class MenuIServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuIService {
}
