package com.x.framework.db.autoconfigure;

import com.x.framework.db.aspect.XLogAspect;
import com.x.framework.db.logback.MybatisLogInterceptor;
import com.x.framework.db.mybatis.MybatisPlusConfig;
import com.x.framework.db.service.impl.OperationLogIServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@MapperScan("com.x.framework.db.mapper")
@Import({
        MybatisLogInterceptor.class,
        MybatisPlusConfig.class,
        OperationLogIServiceImpl.class,
        XLogAspect.class
})
public class XFrameworkDbAutoConfiguration {
}
