package cn.net.easyinfo.entity;

import cn.net.easyinfo.bean.MenuMeta;
import cn.net.easyinfo.bean.Role;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Table(name = "menu")
@Data
public class Menu {
    @Id
    private Long id;
    private String url;
    private String path;
    private Object component;
    private String name;
    @Column(name = "iconCls")
    private String iconCls;
    @Column(name = "parentId")
    private Long parentId;
    @Column(name = "enabled")
    private boolean enabled;
    private List<Role> roles;
    private List<Menu> children;
    private MenuMeta meta;
}
