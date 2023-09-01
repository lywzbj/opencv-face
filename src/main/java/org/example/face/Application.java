package org.example.face;


import com.arcsoft.face.FaceInfo;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        VideoCapture capture = new VideoCapture(0);
        if (!capture.isOpened()) {
            System.out.println("Camera not found.");
            return;
        }
        // Create a Mat object to store each frame
        Mat frame = new Mat();
        int frameCount = 0;
        while (true) {
            // Capture a frame from the camera
            capture.read(frame);
            // Check if the frame is valid
            if (!frame.empty()) {
                // Save the frame as an image every 5 seconds
                if (frameCount % 150 == 0) {  // 30 frames per second, so 150 frames ≈ 5 seconds
                    String filename =  "D:\\tmp\\frame_" + frameCount + ".png";
                    BufferedImage image = matToBufferedImage(frame);
                    byte[] bytes = bufferedImageToByteArray(image);
                    FaceInfo face = FaceService.isFace(bytes);
                    if(face != null) {
                        Imgcodecs.imwrite(filename, frame);
                        System.out.println("检测到人脸信息,保存文件: " + filename);
                        byte[] faceBytes = FaceService.getFace(bytes, face);
                        System.out.println("提取到人脸特征值: " + faceBytes);

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




    }


    private static BufferedImage matToBufferedImage(Mat mat) {
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

    private static byte[] bufferedImageToByteArray(BufferedImage image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }


}
