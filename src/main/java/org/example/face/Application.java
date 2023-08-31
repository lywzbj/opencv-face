package org.example.face;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

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
                    String filename = "frame_" + frameCount + ".png";
                    Imgcodecs.imwrite(filename, frame);
                    System.out.println("Saved: " + filename);
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



}
