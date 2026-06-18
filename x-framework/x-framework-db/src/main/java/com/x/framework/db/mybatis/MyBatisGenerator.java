package com.x.framework.db.mybatis;

import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.query.SQLQuery;
import com.x.common.utils.DateUtil;

import java.io.File;

/**
 * @author : xuemingqi
 * @since : 2023/6/20 10:16
 */
public class MyBatisGenerator {

    /**
     * 模块名称
     */
    public static final String MODULE_NAME = "x-auth";

    /**
     * 包路径
     */
    public static final String PACKAGE_PATH = "com.x.auth.db";

    /**
     * 模块根路径
     */
    public static final String MODULE_BASE_PATH = "." + File.separator + MODULE_NAME;

    /**
     * 代码路径
     */
    public static final String JAVA_PATH = MODULE_BASE_PATH + File.separator +
            "src" + File.separator + "main" + File.separator + "java" + File.separator;

    /**
     * 数据库连接地址
     */
    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/xdb?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull";

    /**
     * 数据库用户名
     */
    public static final String DB_USERNAME = "root";

    /**
     * 数据库密码
     */
    public static final String DB_PASSWORD = "root";


    public static void main(String[] args) {
        FastAutoGenerator.create(DB_URL, DB_USERNAME, DB_PASSWORD)
                .dataSourceConfig(builder -> builder
                                .databaseQueryClass(SQLQuery.class)
                        //TODO 自定义类型转换tinyint转换为enum
//                        .typeConvert(new MySqlTypeConvert())
                )
                .globalConfig(builder -> builder
                        .author("xuemingqi")
                        .enableSpringdoc()
                        .disableOpenDir()
                        .outputDir(JAVA_PATH)
                        .commentDate(DateUtil.now())
                )
                .packageConfig(builder -> builder
                        .parent(PACKAGE_PATH)
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> builder
                        .notLikeTable(new LikeTable("QRTZ_%' AND NAME NOT LIKE '%_table", SqlLike.DEFAULT))
                        .addExclude("distributed_lock", "undo_log")
                        .entityBuilder()
                        .enableFileOverride()
                        .enableTableFieldAnnotation()
                        .enableLombok()
                        .mapperBuilder()
                        .enableFileOverride()
                        .serviceBuilder()
                        .enableFileOverride()
                        .formatServiceImplFileName("%sIServiceImp")
                        .formatServiceFileName("%sIService")
                        .entityBuilder()
                        .enableFileOverride()
                        .controllerBuilder()
                        .disable()
                        .build()
                )
                .execute();
    }
}
