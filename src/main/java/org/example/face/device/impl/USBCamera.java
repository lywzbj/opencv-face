package org.example.face.device.impl;

import com.arcsoft.face.FaceInfo;
import org.example.face.FaceService;
import org.example.face.device.ICamera;
import org.example.face.fileserver.service.ImageServerService;
import org.example.face.fileserver.service.ImageServerServiceImpl;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class USBCamera implements ICamera {


    // 获取摄像头
    private VideoCapture capture = new VideoCapture(0);


    //  0 - 停止   1 - 运行中
    private AtomicInteger status =  new AtomicInteger(0);


    private ImageServerService serverService = new ImageServerServiceImpl();




    @Override
    public void start() {
        if(status.get() == 1) {
            return;
        }
        status.compareAndSet(0,1);
        new Thread(() -> {
            Mat frame = new Mat();
            int frameCount = 0;
            while (status.get() == 1) {
                // Capture a frame from the camera
                capture.read(frame);
                // Check if the frame is valid
                if (!frame.empty()) {
                    // Save the frame as an image every 5 seconds
                    if (frameCount % 150 == 0) {  // 30 frames per second, so 150 frames ≈ 5 seconds
                        String filename =  "E:\\tmp\\frame_" + frameCount + ".png";
                        BufferedImage image = matToBufferedImage(frame);
                        byte[] bytes = bufferedImageToByteArray(image);
                        FaceInfo face = FaceService.isFace(bytes);
                        if(face != null) {
                            try {
                                String save = serverService.save(bytes);
                                System.out.println("检测到人脸信息,保存文件: " + save);
                            }catch (Exception e) {
                                System.out.println("人脸信息保存失败: " + e.getMessage());
                            }
                        }

                    }
                    frameCount++;
                }

                try {
                    Thread.sleep(33);  // Approximately 30 frames per second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    @Override
    public boolean stop() {
        if(status.get() == 0) {
            return true;
        }
        return this.status.compareAndSet(1, 0);
    }


    private  BufferedImage matToBufferedImage(Mat mat) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, matOfByte);
        byte[] byteArray = matOfByte.toArray();

        try {
            return ImageIO.read(new ByteArrayInputStream(byteArray));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private  byte[] bufferedImageToByteArray(BufferedImage image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

}
