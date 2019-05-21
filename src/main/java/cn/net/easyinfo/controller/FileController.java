package cn.net.easyinfo.controller;

import cn.net.easyinfo.common.util.FileUtil;
import cn.net.easyinfo.common.vo.PicUploadResult;
import cn.net.easyinfo.common.vo.SysResult;
import cn.net.easyinfo.report.service.ReportTaskService;
import cn.net.easyinfo.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


/**
 * 文件上传
 */
@Slf4j
@RestController
@RequestMapping("api/file")
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private ReportTaskService reportTaskService;

    @Value("${virtualPath}")
	private String virtualPath;

	@Value("${filePath}")
	private String FilePath;
    //处理文件上传
    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public SysResult uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        //文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //上传文件名
        String fileName = UUID.randomUUID()+suffix;
        //获取最后一个ID +1作为下个任务的图片存储路径
        int id = reportTaskService.getLastReportId();
        log.debug("***********  "+id);
        String filePath = FilePath+virtualPath+"/r_"+id+"/";
        System.out.println("ssssss+"+filePath);
        try {
                FileUtil.uploadFile(file.getBytes(), filePath, fileName);
                return SysResult.oK(200,"图片上传成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201,"图片上传失败");
    }

    @RequestMapping(value = "/uploadimg",method = RequestMethod.POST)
    public PicUploadResult upload(@RequestParam(value = "fileImg") MultipartFile fileImg){
        return fileService.upload(fileImg);
    }


}
