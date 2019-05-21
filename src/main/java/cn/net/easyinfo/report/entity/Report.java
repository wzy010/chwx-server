package cn.net.easyinfo.report.entity;

import cn.net.easyinfo.entity.ReportType;
import cn.net.easyinfo.entity.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务实体类
 */
@Table(name = "report")
@Data
public class Report {

    @Id
    private Integer id;
    private Integer userid;
    private Integer fid;
    private String name;
    private Integer number;
    private String address;
    private Integer score;
    private String ptime;
    private String endtime;
    @Column(name = "typeName")
    private String typeName;
    private Integer type;
    @Column(name = "typeAfter")
    private Integer typeAfter;
    private String mark;
    private String deptids;
    private String username;

    private String value;
    private String ptimeAll;
    private Integer publish=0;//0发布 1接收
    private Integer status;//1结束
    private List<String> list;
    private Integer score2;
    private Integer score3;
    private String markto;
    private Integer index;
    private Integer number3;
    private Integer number2;
    private int maxscore;
    private int readNumber;//阅读人数
    private int zhixPersonNumber;//执行人数
    private int zhixNumber;//执行数量
    private int pnumber;//下发人数
    private String numberlv;//完成率
    private String readNumberlv;//查看率
    private String zhixlv;//执行率
    private int fabunumber;//下发数量
    private String realName;//用户真实姓名
    private String tel;//手机号

    private User user;
    public List<ReportType> depts = new ArrayList<>();
}