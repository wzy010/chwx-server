package cn.net.easyinfo.common.poi;

import cn.net.easyinfo.common.vo.UserVo;
import cn.net.easyinfo.report.entity.Report;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sang on 2018/1/16.
 */
public class ReportPoiUtils {

    public static ResponseEntity<byte[]> exportrep2Excel(List<Report> reports) {
        HttpHeaders headers = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.创建Excel文档
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建文档摘要
            workbook.createInformationProperties();
            //3.获取文档信息，并配置
            DocumentSummaryInformation dsi = workbook.getDocumentSummaryInformation();
            //3.1文档类别
            dsi.setCategory("员工信息");
            //3.2设置文档管理员
            dsi.setManager("江南一点雨");
            //3.3设置组织机构
            dsi.setCompany("XXX集团");
            //4.获取摘要信息并配置
            SummaryInformation si = workbook.getSummaryInformation();
            //4.1设置文档主题
            si.setSubject("员工信息表");
            //4.2.设置文档标题
            si.setTitle("员工信息");
            //4.3 设置文档作者
            si.setAuthor("XXX集团");
            //4.4设置文档备注
            si.setComments("备注信息暂无");
            //创建Excel表单
            HSSFSheet sheet = workbook.createSheet("召唤师峡谷集团员工信息表");
            //创建日期显示格式
            HSSFCellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
            //创建标题的显示样式
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREEN.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //定义列的宽度
            sheet.setColumnWidth(0, 5 * 256);
            sheet.setColumnWidth(1, 20 * 256);
            sheet.setColumnWidth(2, 45 * 256);
            sheet.setColumnWidth(3, 10 * 256);
            sheet.setColumnWidth(4, 20 * 256);//第四列(出生日期那一列)的宽度为10个字符的宽度
            sheet.setColumnWidth(5, 20 * 256);
            sheet.setColumnWidth(6, 20 * 256);
            sheet.setColumnWidth(7, 10 * 256);
            sheet.setColumnWidth(8, 30 * 256);

            //5.设置表头
            HSSFRow headerRow = sheet.createRow(0);
            HSSFCell cell0 = headerRow.createCell(0);
            cell0.setCellValue("编号");
            cell0.setCellStyle(headerStyle);
            HSSFCell cell2 = headerRow.createCell(1);
            cell2.setCellValue("任务名称");
            cell2.setCellStyle(headerStyle);
            HSSFCell cell3 = headerRow.createCell(2);
            cell3.setCellValue("任务链接");
            cell3.setCellStyle(headerStyle);
            HSSFCell cell4 = headerRow.createCell(3);
            cell4.setCellValue("发布人");
            cell4.setCellStyle(headerStyle);
            HSSFCell cell5 = headerRow.createCell(4);
            cell5.setCellValue("任务类型");
            cell5.setCellStyle(headerStyle);
            HSSFCell cell6 = headerRow.createCell(5);
            cell6.setCellValue("发布时间");
            cell6.setCellStyle(headerStyle);
            HSSFCell cell7 = headerRow.createCell(6);
            cell7.setCellValue("截止时间");
            cell7.setCellStyle(headerStyle);
            HSSFCell cell8 = headerRow.createCell(7);
            cell8.setCellValue("任务数量");
            cell8.setCellStyle(headerStyle);
            HSSFCell cell9 = headerRow.createCell(8);
            cell9.setCellValue("任务描述");
            cell9.setCellStyle(headerStyle);

            //6.装数据
            for (int i = 0; i < reports.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1);
                Report rep = reports.get(i);
                row.createCell(0).setCellValue(rep.getId());
                row.createCell(1).setCellValue(rep.getName());
                row.createCell(2).setCellValue(rep.getAddress());
                row.createCell(3).setCellValue(rep.getUser().getName());
                row.createCell(4).setCellValue(rep.getTypeName());
/*                HSSFCell birthdayCell = row.createCell(4);
                birthdayCell.setCellValue(rep.getBirthday());
                birthdayCell.setCellStyle(dateCellStyle);*/
                row.createCell(5).setCellValue(rep.getPtime());
                row.createCell(6).setCellValue(rep.getEndtime());
                row.createCell(7).setCellValue(rep.getNumber());
                row.createCell(8).setCellValue(rep.getMark());

            }
            headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", new String("任务表.xls".getBytes("UTF-8"), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            baos = new ByteArrayOutputStream();
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }

    public static List<Report> importrep2List(MultipartFile file, List<UserVo> allUsers) {
        List<Report> reports = new ArrayList<>();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(file.getInputStream()));
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                HSSFSheet sheet = workbook.getSheetAt(i);
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                Report report;
                for (int j = 0; j < physicalNumberOfRows; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }
                    HSSFRow row = sheet.getRow(j);
                    if (row == null) {
                        continue;//没数据
                    }
                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                    report = new Report();
                    for (int k = 0; k < physicalNumberOfCells; k++) {
                        HSSFCell cell = row.getCell(k);
                        System.out.println(""+cell.getCellTypeEnum());
                        switch (cell.getCellTypeEnum()) {
                            case NUMERIC:{
                                Double cellValue = cell.getNumericCellValue();
                                if (cellValue == null) {
                                    cellValue = 0.0;
                                }
                                switch (k){

                                    case 7:
                                        //此处有问题!!!无法设置值
                                        report.setNumber(cellValue.intValue());
                                        System.out.print("aaaaaaaaaaaaaaaaaaaaaaaa     "+k);
                                        break;
                                }
                                break;
                            }
                            case STRING: {
                                String cellValue = cell.getStringCellValue();
                                if (cellValue == null) {
                                    cellValue = "";
                                }
                                switch (k) {

                                    case 1:
                                        report.setName(cellValue);
                                        break;
                                    case 2:
                                        report.setAddress(cellValue);
                                        break;
                                    case 3:
                                        int uIndex = allUsers.indexOf(new UserVo(cellValue));
                                        report.setUserid(allUsers.get(uIndex).getId());
                                        break;
                                    case 4:
                                        report.setTypeName(cellValue);
                                        break;
                                    case 5:
                                        report.setPtime(cellValue);
                                        break;
                                    case 6:
                                        report.setEndtime(cellValue);
                                        break;
                                    case 7:
                                        //此处有问题!!!无法设置值
                                        report.setNumber(Integer.parseInt(cellValue));
                                        break;
                                    case 8:
                                        report.setMark(cellValue);
                                        break;
                                }
                           }

                            break;

                       }
                    }
                    reports.add(report);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reports;
    }
}
