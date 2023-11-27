package com.x.config.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : xuemingqi
 * @since : 2023/11/24 11:12
 */
@Data
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperty {

    private Docket docket = new Docket();

    @Data
    public static class Docket {
        /**
         * 是否开启swagger
         */
        private boolean enable = true;

        /**
         * 组名
         */
        private String groupName = "default";

        /**
         * 主机名
         */
        private String host = "localhost";

        /**
         * api文档基本信息
         */
        private ApiInfo apiInfo = new ApiInfo();

        @Data
        public static class ApiInfo {
            /**
             * 标题
             */
            private String title;

            /**
             * 描述
             */
            private String description;

            /**
             * 服务条款URL
             */
            private String termsOfServiceUrl = "https://www.yonyeyy.com/api";

            /**
             * 版本
             */
            private String version;

            /**
             * 证书
             */
            private String license =  "Apache License 2.0";

            /**
             * 证书地址
             */
            private String licenseUrl = "https://www.apache.org/licenses/LICENSE-2.0";

            /**
             * 作者信息
             */
            private Contact contact = new Contact();

            @Data
            public static class Contact {
                /**
                 * 姓名
                 */
                private String name = "yonyeyy";

                /**
                 * 主页地址
                 */
                private String url = "https://www.yonyeyy.com";

                /**
                 * 邮箱地址
                 */
                private String email = "xuemingqi@yonyeyy.com";
            }
        }
    }
}
