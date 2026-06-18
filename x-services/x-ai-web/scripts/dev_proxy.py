#!/usr/bin/env python3
import argparse
import http.client
from http.server import SimpleHTTPRequestHandler, ThreadingHTTPServer
from pathlib import Path
from urllib.parse import urlparse

STATIC_ROOT = Path(__file__).resolve().parents[1] / "src/main/resources/static/agent-workbench"
AGENT_PATH_PREFIX = "/ai/agent"
DEFAULT_PORT = 19084
DEFAULT_TARGET = "http://127.0.0.1:18086"
HOP_BY_HOP_HEADERS = {
    "connection",
    "keep-alive",
    "proxy-authenticate",
    "proxy-authorization",
    "te",
    "trailer",
    "transfer-encoding",
    "upgrade",
    "host",
}
PROXY_BUFFER_SIZE = 8192


class DevProxyHandler(SimpleHTTPRequestHandler):
    target = urlparse(DEFAULT_TARGET)

    def __init__(self, *args, **kwargs):
        super().__init__(*args, directory=str(STATIC_ROOT), **kwargs)

    def do_HEAD(self):
        if self.path.startswith(AGENT_PATH_PREFIX):
            self.proxy(write_body=False)
            return
        super().do_HEAD()

    def do_GET(self):
        if self.path.startswith(AGENT_PATH_PREFIX):
            self.proxy()
            return
        super().do_GET()

    def do_POST(self):
        if self.path.startswith(AGENT_PATH_PREFIX):
            self.proxy()
            return
        self.send_error(404)

    def do_OPTIONS(self):
        if self.path.startswith(AGENT_PATH_PREFIX):
            self.proxy()
            return
        self.send_response(204)
        self.end_headers()

    def proxy(self, write_body=True):
        body = self.rfile.read(int(self.headers.get("Content-Length", 0)))
        connection = http.client.HTTPConnection(self.target.hostname, self.target.port, timeout=300)
        path = self.path
        headers = {
            key: value
            for key, value in self.headers.items()
            if key.lower() not in HOP_BY_HOP_HEADERS
        }
        headers["Host"] = self.target.netloc

        try:
            connection.request(self.command, path, body=body, headers=headers)
            response = connection.getresponse()
            self.send_response(response.status, response.reason)
            for key, value in response.getheaders():
                if key.lower() not in HOP_BY_HOP_HEADERS:
                    self.send_header(key, value)
            self.end_headers()
            if write_body:
                while True:
                    data = response.read(PROXY_BUFFER_SIZE)
                    if not data:
                        break
                    try:
                        self.wfile.write(data)
                        self.wfile.flush()
                    except BrokenPipeError:
                        break
        finally:
            connection.close()


def parse_args():
    parser = argparse.ArgumentParser(description="Serve x-ai-web and proxy x-ai agent APIs.")
    parser.add_argument("--port", type=int, default=DEFAULT_PORT)
    parser.add_argument("--target", default=DEFAULT_TARGET)
    return parser.parse_args()


def main():
    args = parse_args()
    DevProxyHandler.target = urlparse(args.target)
    server = ThreadingHTTPServer(("127.0.0.1", args.port), DevProxyHandler)
    print(f"x-ai-web proxy: http://127.0.0.1:{args.port} -> {args.target}", flush=True)
    server.serve_forever()


if __name__ == "__main__":
    main()
