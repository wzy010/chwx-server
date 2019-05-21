package cn.net.easyinfo.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 任务类型
 */
@Table(name = "report_type")
@Data
public class ReportType {

    @Id
    private Integer id;
    private String value;
    private Integer userid;
    private Integer deptid;
}

