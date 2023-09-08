package org.example.face.controller;

import org.example.face.fileserver.service.ImageServerService;
import org.example.face.fileserver.service.ImageServerServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/file")
public class FileController {


    private ImageServerService imageServerService =  new ImageServerServiceImpl();



    @PostMapping(value = "/updateImage")
    public String updateImage(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) {
            return "请指定要上传的图片";
        }
        try {
            byte[] bytes = file.getBytes();
            return imageServerService.save(bytes);
        } catch (IOException e) {
           return e.getMessage();
        }
    }






}
