# x-cloud

#### Introduction
The template for the Spring Cloud Alibaba project

#### Software Architecture
```
├── x-parent             -- Parent dependency module
│   ├── x-auth-api       -- Authentication and authorization feign API
│   ├── x-contact-api    -- User module feign API
│   ├── x-websocket-api  -- Websocket feign API
│   ├── x-work-api       -- Main business service feign API
│   ├── x-gateway        -- Gateway service
│   ├── x-config         -- Common configuration module
│   ├── x-common         -- Common utility module
│   ├── x-module         -- Business module common dependencies
│   │   ├── x-auth       -- Authentication and authorization service
│   │   ├── x-contact    -- User service
│   │   ├── x-websocket  -- Websocket service
│   │   ├── x-work       -- Main business service
```

### Technology Stack
Spring Boot 2.6.13  
Spring Cloud 2021.0.5  
Spring Cloud Alibaba 2021.0.5.0  
Nacos 2.2.0
JWT  
Redisson  
Mybatis-plus  
RocketMq  
Websocket


#### Installation Tutorial

1.  Start Nacos
2.  Start basic services such as MySQL, Redis, RocketMQ
3.  Start any service

#### Usage Instructions

1.  Swagger is integrated through the gateway, URL->https://gateway-host/doc.html

#### Contribution

1.  Fork this repository
2.  Create a new Feat_xxx branch
3.  Submit your code
4.  Create a new Pull Request


#### Special Skills

1.  Use Readme_XXX.md to support different languages, such as Readme_en.md, Readme_zh.md
2.  Personal blog [blog.yonyeyy.com](https://blog.yonyeyy.com)
3.  Personal GitHub repository [https://github.com/xuemingqi](https://github.com/xuemingqi)
