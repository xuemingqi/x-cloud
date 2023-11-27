package com.x.websocket;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootTest(classes = WebSocketApplication.class)
class WebSocketApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        int port = 80;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("正在监听端口 " + port + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("收到来自 " + clientSocket.getInetAddress() + " 的连接请求");

                // 处理请求消息
                processRequest(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processRequest(Socket clientSocket) {
        try (InputStream inputStream = clientSocket.getInputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            // 读取请求消息
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                String request = new String(buffer, 0, bytesRead);
                System.out.println("收到请求消息:\n" + request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
