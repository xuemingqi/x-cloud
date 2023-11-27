package com.x.work.service.impl;

import com.x.common.response.BaseResult;
import com.x.common.response.ResultUtil;
import com.x.common.utils.ListUtil;
import com.x.config.redis.constants.RedisCommonConstant;
import com.x.work.db.entity.Menu;
import com.x.work.db.service.MenuIService;
import com.x.work.domain.MenuDo;
import com.x.work.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2023/2/23 17:51
 */
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MenuServiceImpl implements MenuService {

    private final MenuIService menuIService;

    @Override
    @Cacheable(cacheNames = "MENU_LIST=" + 60, cacheManager = RedisCommonConstant.CACHE_MANAGER_NAME)
    public BaseResult<List<MenuDo>> getMenuList() {
        List<Menu> menus = menuIService.lambdaQuery()
                .orderBy(true, true, Menu::getSort)
                .list();

        List<MenuDo> result = ListUtil.copyList(menus, MenuDo.class, BeanUtils::copyProperties);
        return ResultUtil.buildResultSuccess(result);
    }
}
