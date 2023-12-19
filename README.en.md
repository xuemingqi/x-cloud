# x-cloud

<p>
    <img src="https://custom-icon-badges.herokuapp.com/github/license/xuemingqi/x-cloud?logo=law&color=orange" alt="License"/>
    <img src="https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fxuemingqi%2Fx-cloud&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false" alt="hits"/>
    <img src="https://custom-icon-badges.herokuapp.com/github/v/release/xuemingqi/x-cloud?logo=rocket" alt="version">
    <img src="https://custom-icon-badges.herokuapp.com/github/issues-pr-closed/xuemingqi/x-cloud?color=purple&logo=git-pull-request&logoColor=white" alt="issues"/>
    <img src="https://custom-icon-badges.herokuapp.com/github/last-commit/xuemingqi/x-cloud?logo=history&logoColor=white" alt="last-commit"/>
</p>


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


#### Stargazers over time
[![Stargazers over time](https://starchart.cc/xuemingqi/x-cloud.svg)](https://starchart.cc/xuemingqi/x-cloud)
