# x-cloud

#### 介绍
The template for the Spring Cloud Alibaba project

#### 软件架构
```
├── x-parent             -- 父依赖模块
│   ├── x-auth-api       -- 权限认证feign api
│   ├── x-contact-api    -- 用户模块feign api
│   ├── x-websocket-api  -- websocket feign api
│   ├── x-work-api       -- 主要业务服务feign api
│   ├── x-gateway        -- 网关服务
│   ├── x-config         -- 通用配置模块
│   ├── x-common         -- 通用工具模块
│   ├── x-module         -- 业务模块公共依赖
│   │   ├── x-auth       -- 权限认证服务
│   │   ├── x-contact    -- 用户服务
│   │   ├── x-websocket  -- websocket服务
│   │   ├── x-work       -- 主要业务服务
```

### 技术栈
1. Spring Boot 2.6.13  
2. Spring Cloud 2021.0.5  
3. Spring Cloud Alibaba 2021.0.5.0  
4. Nacos 2.2.0
5. JWT  
6. Redisson  
7. Mybatis-plus  
8. RocketMq  
9. Websocket


#### 安装教程

1.  启动nacos
2.  启动基础服务，mysql、redis、rocketmq
3.  启动任意服务

#### 使用说明

1.  swagger通过gateway集成,url->https://gateway-host/doc.html

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  个人博客 [blog.yonyeyy.com](https://blog.yonyeyy.com)
3.  个人github仓库 [https://github.com/xuemingqi](https://github.com/xuemingqi)
