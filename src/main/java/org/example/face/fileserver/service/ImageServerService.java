package org.example.face.fileserver.service;

public interface ImageServerService {



    String save(byte[] data);

    String save(String base64);


    String getPath(String imgName);




}
