package cn.net.easyinfo.service;

import cn.net.easyinfo.common.vo.PicUploadResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    private String localPath = "E:/pic/";
    private String urlPath = "http://image.com";
    @Override
    public PicUploadResult upload(MultipartFile uploadFile) {
        PicUploadResult result = new PicUploadResult();

        //1.获取图片的名称   abc.jpg
        String fileName = uploadFile.getOriginalFilename();
        fileName = fileName.toLowerCase();

        //2.判断是否为图片的类型
        if(!fileName.matches("^.*(jpg|png|gif)$")){

            result.setError(1); //表示不是图片
        }

        //3.判断是否为恶意程序
        try {
            BufferedImage bufferedImage =
                    ImageIO.read(uploadFile.getInputStream());

            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();
            if(height == 0 || width == 0){

                result.setError(1);
                return result;
            }

            //4.将图片分文件存储 yyyy/MM-dd
            String DatePath =
                    new SimpleDateFormat("yyyy/MM/dd").format(new Date());

            //判断是否有该文件夹  E:/jt-upload/2018/11/11
            String picDir = localPath + DatePath;
            File picFile = new File(picDir);

            if(!picFile.exists()){

                picFile.mkdirs();
            }

            //防止文件重名
            String uuid = UUID.randomUUID().toString().replace("-", "");

            int randomNum = new Random().nextInt(1000);
            //.jpg
            String fileType = fileName.substring(fileName.lastIndexOf("."));

            //拼接文件的名称
            String fileNowName = uuid + randomNum + fileType;

            //实现文件上传            e:jt-upload/yyyy/MM/dd/1231231231231231231.jpg
            String realFilePath = picDir + "/" +fileNowName;
            uploadFile.transferTo(new File(realFilePath));


            //将真实数据回显
            result.setHeight(height+"");
            result.setWidth(width+"");

            /**
             * 实现虚拟路径的拼接
             * E:/jt-upload/2018/07/23/e4d5c2667a174477b2ab59158670bbbe816.jpg
             * image.jt.com
             */
            String realUrl = urlPath + DatePath + "/" + fileNowName;
            result.setUrl(realUrl);

        } catch (Exception e) {
            e.printStackTrace();
            result.setError(1); //文件长传有误
        }
        return result;
    }
}
