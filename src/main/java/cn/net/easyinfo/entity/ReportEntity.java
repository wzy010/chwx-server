package cn.net.easyinfo.entity;

import lombok.Data;

@Data
public class ReportEntity {
    private  Integer id;
    private String status;
    private String mark;
    private Integer score;
    private Integer type;

}
