package cn.net.easyinfo.mapper;

import org.apache.ibatis.annotations.Param;
import cn.net.easyinfo.bean.Role;

import java.util.List;


public interface RoleMapper {
    List<Role> roles();

    int addNewRole(@Param("role") String role, @Param("roleZh") String roleZh);

    int deleteRoleById(Long rid);
}
