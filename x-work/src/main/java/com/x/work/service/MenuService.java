package com.x.work.service;

import com.x.common.response.BaseResult;
import com.x.work.domain.MenuDo;

import java.util.List;

/**
 * @author: xuemingqi
 * @since: 2023/2/23 17:51
 */
public interface MenuService {

    /**
     * 获取菜单列表
     */
    BaseResult<List<MenuDo>> getMenuList();
}
