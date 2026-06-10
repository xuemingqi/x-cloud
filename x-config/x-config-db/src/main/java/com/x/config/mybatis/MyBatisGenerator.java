package com.x.config.mybatis;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2023/6/20 10:16
 */
public class MyBatisGenerator {

    /**
     * 模块名称
     */
    public static final String MODULE_NAME = "x-work";

    /**
     * 包路径
     */
    public static final String PACKAGE_PATH = "com.x.work.db";

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
     * 资源文件路径
     */
    public static final String RESOURCES_PATH = MODULE_BASE_PATH + File.separator +
            "src" + File.separator + "main" + File.separator + "resources" + File.separator;

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
        //数据库配置
        FastAutoGenerator.create(new DataSourceConfig.Builder(DB_URL, DB_USERNAME, DB_PASSWORD)
                        .dbQuery(new MySqlQuery()))
                .globalConfig(builder -> builder.author("yonyeyy") //作者
                        .fileOverride()                            //覆盖已生成文件
                        .outputDir(JAVA_PATH)                      //文件目录
                        .build())

                //包配置
                .packageConfig(builder -> {
                    builder.parent(PACKAGE_PATH) //父包名
                            .mapper("mapper")
                            .entity("entity")
                            //.controller("controller")
                            .service("service")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, RESOURCES_PATH + "mapper"))
                            .build(); //mapper xml路径;
                })

                //实体层配置
                .strategyConfig((scanner, builder) -> {
                    builder.entityBuilder()
                            .naming(NamingStrategy.underline_to_camel) //数据库表映射到实体的命名策略.默认下划线转驼峰命名:NamingStrategy.underline_to_camel
                            .enableTableFieldAnnotation()              //生成字段注解
                            .enableRemoveIsPrefix()                    //Boolean类型字段移除 is 前缀
                            .enableLombok();                           //lombok模型

                    //controller层操作
                    builder.controllerBuilder()
                            .enableRestStyle();      //开启生成@RestController 控制器

                    //mapper层操作
                    builder.mapperBuilder()
                            .enableBaseResultMap()   //启用BaseResultMap生成
                            .enableBaseColumnList(); //启用BaseColumnList
                    //builder.addTablePrefix("x_");  //增加过滤表前缀

                    //要操作的表
                    builder.addInclude(getTables(scanner.apply("请输入要逆向工程的表名，多个表名之间英文逗号分隔，所有输入：all")))
                            .build(); //设置需要生成的表名
                })
                .templateEngine(new VelocityTemplateEngine()) // 使用Velocity引擎模板，Freemarker同样也可以
                .execute();
    }

    /**
     * 表名处理
     */
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
