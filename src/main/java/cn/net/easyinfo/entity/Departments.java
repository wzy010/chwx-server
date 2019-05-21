package cn.net.easyinfo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;



@Table(name = "departments")
@Data
public class Departments {
    @Id
    private Integer id;
    private Integer fid;
    private String name;
    private Integer roleid;
    private Integer sort;
    private Integer flag;
    @Column(name = "noticeType")
    private Integer noticeType;

    private int index;
    private String mark;
    private int categoryid;
    private int setAdmin;
    private int isSend;
    private int modifyScoreRight;
    private int loginMsg; //登录是否用短信
    private int noticeMsg; //通知是否用短信
    private int isJietu; //默认0,0不调用截图脚本 1调用截图脚本
    private int appJietuShow;
    private int appTaskShow;



    private List<Departments> children = new ArrayList<>();
}
