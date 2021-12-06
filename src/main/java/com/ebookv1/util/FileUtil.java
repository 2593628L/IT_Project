package com.ebookv1.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.google.common.io.Resources.getResource;

public class FileUtil {
    static String pathSaveFile=getResource("").getPath();
    public static String uploadFile(MultipartFile file,Long id) throws IOException {
        if(file.isEmpty()) return null;
        String fileName = id+".pdf";
        String  path = pathSaveFile.substring(1);
        String newfileName="/static/download/"+fileName;
        String newFilePath = path+newfileName;
        File newFile = new File(newFilePath);
        if (!newFile.getParentFile().exists()) {
                newFile.getParentFile().mkdirs();
            }
            file.transferTo(newFile);
        return newfileName;
    }
   // D:\study\ebookV1\target
    // D:\study\ebookV1\src\main\resources\static
}
