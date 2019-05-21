package cn.net.easyinfo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.net.easyinfo.bean.Role;
import cn.net.easyinfo.bean.RoleRight;
import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.entity.SysUser;
import cn.net.easyinfo.entity.User;
import tk.mybatis.mapper.common.Mapper;


public interface SysRoleMapper {

	List<Role> getrole(Map<String, Object> params);

	List<User> getUsers(Query query);

	List<RoleRight> getRoleRight(Map<String, Object> params);

	int changerolebyid(@Param("ids")String[] split, @Param("roleid")String checkedroleid);

	void deleteroleright(@Param("roleid")String checkedroleid);

	int insertroleright(@Param("ids")String[] ids, @Param("roleid")String checkedroleid);

	
}
