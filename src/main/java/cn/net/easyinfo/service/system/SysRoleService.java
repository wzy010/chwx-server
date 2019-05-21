package cn.net.easyinfo.service.system;

import cn.net.easyinfo.bean.Role;
import cn.net.easyinfo.bean.RoleRight;
import cn.net.easyinfo.common.msg.TableResultResponse;
import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.entity.User;
import cn.net.easyinfo.mapper.SysRoleMapper;
import cn.net.easyinfo.report.entity.Report;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * liyunlong
 * 2018/10/24
 */
@Service
public class SysRoleService {

	@Autowired
    private SysRoleMapper sysrolemaper;
	
	/*
	 * 获取角色列表
	 * author：李云龙
	 * 2018/10/24
	 */
	public List<Role> getrole(Map<String, Object> params) {
		// TODO Auto-generated method stub
		List<Role> list;
		list=sysrolemaper.getrole(params);
		return list;
	}

	public TableResultResponse<User> getUsers(Query query) {
		// TODO Auto-generated method stub
		Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
		List<User> list = sysrolemaper.getUsers(query);
		return new TableResultResponse<User>(result.getTotal(), list);
	}

	public List<RoleRight> getroleright(Map<String, Object> params) {
		// TODO Auto-generated method stub
		List<RoleRight> list;
		list=sysrolemaper.getRoleRight(params);
		return list;
	}

	public boolean changerolebyid(Map<String, Object> params) {
		// TODO Auto-generated method stub
		 String ids=params.get("ids").toString();
		 String checkedroleid=params.get("checkedroleid").toString();
		 String[] split = ids.split(",");
	     return sysrolemaper.changerolebyid(split,checkedroleid) == split.length;
	}

	public boolean updaterolemenu(Map<String, Object> params) {
		// TODO Auto-generated method stub
		String ids=params.get("ids").toString();
		 String checkedroleid=params.get("checkedroleid").toString();
		 String[] split = ids.split(",");
		 sysrolemaper.deleteroleright(checkedroleid);
	     return sysrolemaper.insertroleright(split,checkedroleid) == split.length;
	}
}
