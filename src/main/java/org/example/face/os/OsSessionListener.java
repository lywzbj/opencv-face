package org.example.face.os;

public interface OsSessionListener {


    /**
     * 是否锁屏
     * @return
     */
    boolean isLockScreen();


    /**
     * 是否休眠
     * @return
     */
    boolean isSleep();
}
