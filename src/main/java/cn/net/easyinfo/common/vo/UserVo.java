package cn.net.easyinfo.common.vo;

import cn.net.easyinfo.entity.User;
import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name="user")
public class UserVo {

    private Integer id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserVo nation = (UserVo) o;

        return name != null ? name.equals(nation.name) : nation.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public UserVo(){

    }
    public UserVo(Integer id,String name){
        this.id=id;
        this.name=name;
    }

    public UserVo(String name){
        this.name= name;
    }
}
