# x-ai-web

AgentScope 智能体静态工作台。

## 本地预览

```bash
python3 -m http.server 19082 --directory x-ai-web/src/main/resources/static/agent-workbench
```

访问 `http://127.0.0.1:19082`，在顶部设置实际的 `x-ai` 接口地址。

## 本地代理预览

如果浏览器跨域拦截，可以使用代理模式。它会同源托管静态页面，并把 `/ai/agent/**` 转发到 `x-ai` 服务。

```bash
python3 x-ai-web/scripts/dev_proxy.py --port 19084 --target http://127.0.0.1:18086
```

访问 `http://127.0.0.1:19084`，页面顶部 API 设置为 `http://127.0.0.1:19084/ai/agent`。
