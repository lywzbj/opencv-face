package org.example.face.os;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

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
            Process exec = Runtime.getRuntime().exec("cmd mvn -V");
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
