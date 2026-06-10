//package com.x.config.knife4j;
//
//import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
///**
// * @author ：xuemingqi
// * @since ：created in 2023/1/10 14:05
// */
//@Configuration
//@EnableKnife4j
//public class Knife4jConfig {
//
//    @Bean
//    public Docket dockerBean() {
//        //指定使用Swagger2规范
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(new ApiInfoBuilder()
//                        //描述字段支持Markdown语法
//                        .description("# Knife4j RESTful APIs")
//                        .termsOfServiceUrl("https://doc.xiaominfo.com/")
//                        .version("1.0")
//                        .build())
//                //分组名称
//                .groupName("用户服务")
//                .select()
//                //这里指定Controller扫描包路径
//                .apis(RequestHandlerSelectors.basePackage("com.x"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//}
