package org.example.face.fileserver.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ImageServerServiceImpl implements ImageServerService{


    private String ROOT_DIR = "E:/tmp/";


    @Override
    public String save(byte[] data) {

        String imageName = UUID.randomUUID().toString();
        int modValue =  getMod(imageName);

        String savePath = ROOT_DIR + modValue + File.separator + imageName + ".png";

        File dir = new File(ROOT_DIR + modValue);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        // 创建文件输出流并保存图片
        File imageFile = new File(savePath);
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            fos.write(data);
            fos.flush();
            return imageName + ".png";
        }catch (IOException e) {
            throw new RuntimeException("图片保存失败: " + e.getMessage());
        }
    }

    @Override
    public String save(String base64) {
        return null;
    }

    @Override
    public String getPath(String imgName) {

        int indexOf = imgName.indexOf(".");
        String substring = imgName.substring(0, indexOf - 1);
        int mod = getMod(substring);
        String path = ROOT_DIR + mod + File.separator + imgName;
        File file = new File(path);
        if(!file.exists()) {
            throw new RuntimeException("图片不存在: " + imgName);
        }
        return path;
    }



    private int getMod(String name) {
        int hashCode = name.hashCode();
        return Math.abs(hashCode) % 16;
    }











}
