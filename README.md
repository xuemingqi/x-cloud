# x-cloud

<p>
    <img src="https://custom-icon-badges.herokuapp.com/github/license/xuemingqi/x-cloud?logo=law&color=orange" alt="License"/>
    <img src="https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fxuemingqi%2Fx-cloud&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false" alt="hits"/>
    <img src="https://custom-icon-badges.herokuapp.com/github/v/release/xuemingqi/x-cloud?logo=rocket" alt="version">
    <img src="https://custom-icon-badges.herokuapp.com/github/issues-pr-closed/xuemingqi/x-cloud?color=purple&logo=git-pull-request&logoColor=white" alt="issues"/>
    <img src="https://custom-icon-badges.herokuapp.com/github/last-commit/xuemingqi/x-cloud?logo=history&logoColor=white" alt="last-commit"/>
</p>


#### 介绍
基于 Spring Cloud Alibaba 框架的项目通用模板


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


#### 星数
[![Stargazers over time](https://starchart.cc/xuemingqi/x-cloud.svg)](https://starchart.cc/xuemingqi/x-cloud)
