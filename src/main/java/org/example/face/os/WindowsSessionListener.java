package org.example.face.os;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 用于监听Windows的锁屏状态
 */
public class WindowsSessionListener implements OsSessionListener{


    /**
     * windows的session状态
     */
    private AtomicInteger sessionStatus;


    public static void main(String[] args) {
        try {
            String cmd = "E:\\python\\demo\\lockStatus.py";
            String pythonBin  = "D:\\python3.10\\python.exe";
            String mvnCmd = "D:\\Development_tools\\Maven\\apache-maven-3.6.1\\bin\\mvn.cmd -V";
            ProcessBuilder processBuilder = new ProcessBuilder();
            List<String> commands = new ArrayList<>();
            commands.add("cmd");
            commands.add("python");
            commands.add(cmd);
            processBuilder.command(commands);
            Process start = processBuilder.start();
            String result = new BufferedReader(new InputStreamReader(start.getInputStream(), StandardCharsets.UTF_8)).lines().collect(Collectors.joining());
            System.out.println(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    @Override
    public boolean isLockScreen() {
        return false;
    }

    @Override
    public boolean isSleep() {
        return false;
    }
}
