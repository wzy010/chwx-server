package cn.net.easyinfo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="department")
public class Department {
    @Id
    private Long id;
    private String name;
    @Column(name = "parentId")
    private Long parentId;
    @Column(name = "depPath")
    private String depPath;
    @Column(name = "enabled")
    private boolean enabled;
    @Column(name = "isParent")
    private boolean isParent;


}
