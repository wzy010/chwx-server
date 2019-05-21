package cn.net.easyinfo.common.util;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static int getDirFiles(String path){
        File file = new File(path);
        if(!file.exists()){
            System.out.println("路径不存在");
            return 0;
        }else{
            File[] files = file.listFiles();
            return files.length;

        }
    }

}
