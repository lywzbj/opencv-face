package org.example.face;


import org.opencv.core.Core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // 加载opencv
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 启动springboot
        SpringApplication.run(Application.class,args);
    }




}
