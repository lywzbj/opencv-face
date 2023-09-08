package org.example.face;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfo;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

public class FaceService {


    private static final String appKey = "ErYrgARyWrUYDB3UZmKTdFM8tCdnEQzozr3HEeVKWKND";

    private static final String appSecret = "8eNAFzRcEPJG1zXh4BEanoqGtwQgYYdkkeHwGRqqMJFp";


    private static final FaceEngine faceEngine;


    static {
        faceEngine = new FaceEngine("E:\\tmp\\ArcSoft_ArcFace_Java_Windows_x64_V3.0\\libs\\WIN64");
        //激活引擎
        int errorCode = faceEngine.activeOnline(appKey, appSecret);

        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("引擎激活失败");
        }

        ActiveFileInfo activeFileInfo = new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("获取激活文件信息失败");
        }

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);


        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.out.println("初始化引擎失败");
        }
    }


    public static FaceInfo isFace(byte[] bytes) {
        ImageInfo imageInfo = getRGBData(bytes);
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        int detected = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        if (ErrorInfo.MOK.getValue() != detected) {
            System.out.println("人脸检测出错");
            return null;
        }
        if(faceInfoList.size() == 0) {
            return null;
        }
        return faceInfoList.get(0);
    }


    public static byte[] getFace(byte[] bytes, FaceInfo faceInfo) {
        ImageInfo imageInfo = getRGBData(bytes);
        FaceFeature faceFeature = new FaceFeature();
        int code = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfo, faceFeature);
        if(ErrorInfo.MOK.getValue() != code) {
            System.out.println("提取特征值失败");
            return null;
        }
        return faceFeature.getFeatureData();
    }

    public static void main(String[] args) {
        ImageInfo imageInfo = getRGBData(new File("E:\\tmp\\frame_1200.png"));
        ImageInfo imageInfo1 = getRGBData(new File("E:\\tmp\\WIN_20211130_22_18_42_Pro.jpg"));


        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        List<FaceInfo> faceInfoList1 = new ArrayList<FaceInfo>();
         faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        faceEngine.detectFaces(imageInfo1.getImageData(), imageInfo1.getWidth(), imageInfo1.getHeight(), imageInfo1.getImageFormat(), faceInfoList1);


        FaceFeature faceFeature = new FaceFeature();
         faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        System.out.println("特征值大小：" + faceFeature.getFeatureData().length);



        FaceFeature faceFeature1 = new FaceFeature();
        faceEngine.extractFaceFeature(imageInfo1.getImageData(), imageInfo1.getWidth(), imageInfo1.getHeight(), imageInfo1.getImageFormat(), faceInfoList1.get(0), faceFeature1);
        System.out.println("特征值大小：" + faceFeature.getFeatureData().length);

        System.out.println(isSeem(faceFeature.getFeatureData(),faceFeature1.getFeatureData()));

    }



    public static boolean isSeem(byte[] b1,byte[] b2) {
        FaceFeature targetFaceFeature = new FaceFeature(b1);
        FaceFeature faceFeature = new FaceFeature(b2);
        FaceSimilar similar = new FaceSimilar();

        int i = faceEngine.compareFaceFeature(targetFaceFeature, faceFeature, similar);
        if(ErrorInfo.MOK.getValue() != i) {
            System.out.println("特征比对失败");
            return false;
        }
        float score = similar.getScore();
        return score >= 0.7;
    }








}







