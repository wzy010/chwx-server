package cn.net.easyinfo.service;

import cn.net.easyinfo.common.vo.PicUploadResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public PicUploadResult upload(MultipartFile uploadFile);
}
