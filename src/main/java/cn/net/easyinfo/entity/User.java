package cn.net.easyinfo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "user")
@Data
public class User {
    @Id
    private Integer id;
    private Integer fid;
    private String name;
    @Column(name = "realName")
    private String realName;
    private String deptid;
    private String email;
    private String tel;
    private Integer roleid;

}
