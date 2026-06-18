# x-cloud

<p>
    <img src="https://custom-icon-badges.herokuapp.com/github/license/xuemingqi/x-cloud?logo=law&color=orange" alt="License"/>
    <img src="https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fxuemingqi%2Fx-cloud&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false" alt="hits"/>
    <img src="https://custom-icon-badges.herokuapp.com/github/v/release/xuemingqi/x-cloud?logo=rocket" alt="version">
    <img src="https://custom-icon-badges.herokuapp.com/github/issues-pr-closed/xuemingqi/x-cloud?color=purple&logo=git-pull-request&logoColor=white" alt="issues"/>
    <img src="https://custom-icon-badges.herokuapp.com/github/last-commit/xuemingqi/x-cloud?logo=history&logoColor=white" alt="last-commit"/>
</p>


#### 介绍
x-cloud 是基于 Spring Boot 4、Spring Cloud Alibaba 的微服务工程模板。

当前工程已按 API、服务、公共能力、框架能力分层组织，并新增 AgentScope Java 智能体服务与静态工作台。


#### 软件架构
```
├── x-parent                 -- 父依赖与版本管理
├── x-apis                   -- Feign API 聚合模块
│   ├── x-auth-api           -- 权限认证 Feign API
│   ├── x-contact-api        -- 用户模块 Feign API
│   ├── x-websocket-api      -- WebSocket Feign API
│   ├── x-work-api           -- 主要业务 Feign API
│   └── x-ai-api             -- AgentScope 智能体 Feign API
├── x-services               -- 业务服务聚合模块
│   ├── x-gateway            -- 网关服务
│   ├── x-auth               -- 权限认证服务
│   ├── x-contact            -- 用户服务
│   ├── x-websocket          -- WebSocket 服务
│   ├── x-work               -- 主要业务服务
│   ├── x-chat               -- 在线聊天示例服务
│   ├── x-ai                 -- AgentScope Java 智能体服务
│   └── x-ai-web             -- AgentScope 静态工作台
├── x-common                 -- 通用工具模块
├── x-framework              -- 通用框架模块
│   ├── x-framework-core     -- 框架核心能力
│   ├── x-framework-web      -- Web、拦截器、认证等能力
│   ├── x-framework-db       -- 数据库能力
│   ├── x-framework-redis    -- Redis 能力
│   └── x-framework-mq       -- MQ 能力
└── sentinel-dashboard       -- Sentinel 控制台
```


### 技术栈
基础框架：

1. Java 21
2. Spring Boot 4.0.6
3. Spring Cloud 2025.1.1
4. Spring Cloud Alibaba 2025.1.0.0
5. Spring WebMVC / WebFlux / WebSocket
6. Spring Cloud OpenFeign
7. Spring Cloud LoadBalancer
8. Spring Validation
9. Lombok

微服务治理：

1. Nacos Config / Discovery
2. Sentinel 1.8.6
3. Sentinel Dashboard
4. Sentinel Nacos 规则数据源
5. Seata
6. Gateway

数据与中间件：

1. MySQL Connector 8.0.33
2. MyBatis-Plus 3.5.16
3. MyBatis-Plus Generator
4. MyBatis-Plus JSqlParser
5. Redis / Redisson 4.5.0
6. RocketMQ 2.3.0
7. Kafka 3.2.3
8. Quartz

安全、文档与观测：

1. JWT
2. Knife4j 4.5.0
3. JavaMelody
4. Logback

AI 与业务扩展：

1. AgentScope Java 2.0.0-RC3
2. DeepSeek OpenAI-Compatible API
3. 微信公众号 SDK `weixin-java-mp` 3.7.0
4. Tess4J OCR 4.5.4
5. FFmpeg Java Wrapper 0.8.0
6. Apache Commons Lang 3.17.0
7. Velocity 2.3

构建与发布：

1. Maven
2. Spring Boot Maven Plugin
3. GraalVM Native Maven Plugin
4. Jib Maven Plugin


#### 安装教程

1. 启动 Nacos。
2. 启动基础服务：MySQL、Redis、RocketMQ 等。
3. 配置各服务所需的 Nacos 配置。
4. 启动需要调试的服务。


#### 常用命令

编译全部模块：

```bash
mvn -f x-parent/pom.xml clean install
```

编译 AI 服务与 API：

```bash
mvn -f x-parent/pom.xml -pl ../x-apis/x-ai-api,../x-services/x-ai -am test -DskipTests=false
```

编译 AI 静态工作台：

```bash
mvn -f x-parent/pom.xml -pl ../x-services/x-ai-web -am test -DskipTests=false
```


#### 使用说明

1. Swagger 通过 gateway 集成，访问地址：`https://gateway-host/doc.html`。
2. 后端服务默认从 Nacos 加载配置，具体端口以 Nacos 配置为准。


#### AI 静态工作台

`x-services/x-ai-web` 提供 AgentScope 静态工作台，用于调试能力列表、同步运行、流式事件、计划模式、记忆、文件系统和权限模式。

推荐使用本地代理模式，避免浏览器跨域问题：

```bash
python3 x-services/x-ai-web/scripts/dev_proxy.py --port 19084 --target http://127.0.0.1:18086
```

访问：

```text
http://127.0.0.1:19084
```

页面顶部 API 地址填写：

```text
http://127.0.0.1:19084/ai/agent
```

如果不需要代理，也可以直接启动静态服务：

```bash
python3 -m http.server 19082 --directory x-services/x-ai-web/src/main/resources/static/agent-workbench
```


#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  个人博客 [blog.yonyeyy.com](https://blog.yonyeyy.com)
3.  个人github仓库 [https://github.com/xuemingqi](https://github.com/xuemingqi)


#### 星数
[![Stargazers over time](https://starchart.cc/xuemingqi/x-cloud.svg)](https://starchart.cc/xuemingqi/x-cloud)
