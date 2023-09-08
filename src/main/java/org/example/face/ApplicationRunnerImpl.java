package org.example.face;


import org.example.face.device.IDevice;
import org.example.face.device.impl.USBCamera;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 加载摄像头
        IDevice device = new USBCamera();
        device.start();
    }
}
