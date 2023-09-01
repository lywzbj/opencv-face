package org.example.face;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfo;


import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

public class FaceService {


    private static final String appKey = "ErYrgARyWrUYDB3UZmKTdFM8tCdnEQzozr3HEeVKWKND";

    private static final String appSecret = "8eNAFzRcEPJG1zXh4BEanoqGtwQgYYdkkeHwGRqqMJFp";


    private static final FaceEngine faceEngine;


    static {
        faceEngine = new FaceEngine("C:\\develop\\lib\\WIN64");
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

}







