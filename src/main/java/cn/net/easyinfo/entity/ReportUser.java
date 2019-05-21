package cn.net.easyinfo.entity;

import lombok.Data;

import javax.persistence.Table;

@Table(name = "report_user")
@Data
public class ReportUser {

    private Integer id;
    private Integer tyep;
    private Integer reportid;
    private Integer userid;
    private Integer number;
}
