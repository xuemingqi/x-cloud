package com.x.common.utils;

import cn.hutool.core.io.IoUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2023/2/5 16:43
 */
@Slf4j
public class FileUtil extends cn.hutool.core.io.FileUtil {

    /**
     * 获取远程服务器的文件
     *
     * @param ip       服务ip
     * @param port     服务端口
     * @param username 服务器用户名
     * @param password 服务器密码
     * @param path     文件路径
     * @param size     读取内容大小
     * @return 文件
     */
    @SneakyThrows
    public static File getServerFile(String ip, int port, String username, String password, String path, int size) {
        File file = new File(UUIDUtil.generatorId());
        Session session = null;
        try (SSHClient ssh = new SSHClient()) {
            ssh.addHostKeyVerifier(new PromiscuousVerifier());
            ssh.connect(ip, port);
            ssh.authPassword(username, password);
            session = ssh.startSession();
            String execute = "cat " + path + " | tail -n " + size;
            final Session.Command cmd = session.exec(execute);
            InputStream inputStream = cmd.getInputStream();
            IoUtil.copy(inputStream, Files.newOutputStream(file.toPath()));
        } finally {
            if (null != session) {
                session.close();
            }
        }
        return file;
    }

    /**
     * 获取本地文件内容
     *
     * @param path 文件路径
     * @param size 读取大小
     * @return 文件内容
     */
    @SneakyThrows
    public static String getServerLog(String path, int size) {
        StringBuilder sb = new StringBuilder();
        List<String> cmd = Arrays.asList("tail", "-n", Integer.toString(size), path);
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(cmd);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            //循环等待进程输出，判断进程存活则循环获取输出流数据
            while (process.isAlive()) {
                while (bufferedReader.ready()) {
                    sb.append(bufferedReader.readLine());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取本地文件内容
     *
     * @param path 文件路径
     * @param size 读取大小
     * @return 文件内容
     */
    @SneakyThrows
    public static List<String> getServerLogLines(String path, int size) {
        List<String> lines = new ArrayList<>();
        List<String> cmd = Arrays.asList("tail", "-n", Integer.toString(size), path);
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(cmd);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            //循环等待进程输出，判断进程存活则循环获取输出流数据
            while (process.isAlive()) {
                while (bufferedReader.ready()) {
                    lines.add(bufferedReader.readLine());
                }
            }
        }
        return lines;
    }

    /**
     * 获取本地文件内容
     *
     * @param cmd 操作命令
     * @return 文件内容
     */
    @SneakyThrows
    public static String getServerFileStr(List<String> cmd) {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(cmd);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            //循环等待进程输出，判断进程存活则循环获取输出流数据
            while (process.isAlive()) {
                while (bufferedReader.ready()) {
                    sb.append(bufferedReader.readLine());
                }
            }
        }
        return sb.toString();
    }
}
