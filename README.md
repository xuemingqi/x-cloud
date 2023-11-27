# x-cloud

该项目是一个基于 Spring Cloud Alibaba 技术栈搭建的微服务架构，用于演示如何构建一个分布式系统。

## 项目结构

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
# 技术栈
Spring Boot 2.6.13  
Spring Cloud 2021.0.5  
Spring Cloud Alibaba 2021.0.5.0  
Nacos 2.2.0

JWT  
Redisson  
Mybatis-plus  
RocketMq  
Websocket
