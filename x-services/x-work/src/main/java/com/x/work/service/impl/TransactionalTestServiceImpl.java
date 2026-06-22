package com.x.work.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.x.api.contact.api.UserApi;
import com.x.api.contact.dto.CreateUserDto;
import com.x.api.contact.enums.Sex;
import com.x.work.db.entity.Menu;
import com.x.work.db.service.MenuIService;
import com.x.work.service.TransactionalTestService;
import org.apache.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : xuemingqi
 * @since : 2024-10-17 13:11
 */
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class TransactionalTestServiceImpl implements TransactionalTestService {

    private final UserApi userApi;

    private final MenuIService menuIService;

    private final Snowflake snowflake;


    @Override
    @GlobalTransactional
    public void transactional() {
        menuIService.save(new Menu()
                .setMenuId(snowflake.nextId())
                .setMenuName("test")
                .setSort(11));
        userApi.createUser(new CreateUserDto()
                .setMobile("18640468142")
                .setName("test")
                .setPassword("123456")
                .setSex(Sex.MALE));
        throw new RuntimeException("test");
    }
}
