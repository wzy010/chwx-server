package cn.net.easyinfo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RightManager implements Serializable {
    private int id;
    private int roleid;
    private String value;
    private int createUserid;
}
