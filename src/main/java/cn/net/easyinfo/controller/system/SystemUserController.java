package cn.net.easyinfo.controller.system;

import cn.net.easyinfo.bean.Menu;
import cn.net.easyinfo.bean.RespBean;
import cn.net.easyinfo.bean.Role;
import cn.net.easyinfo.bean.RoleRight;
import cn.net.easyinfo.common.msg.ObjectRestResponse;
import cn.net.easyinfo.common.msg.TableResultResponse;
import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.entity.Departments;
import cn.net.easyinfo.entity.User;
import cn.net.easyinfo.service.system.SysRoleService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 系统权限相关代码
 * author：李云龙
 * 2018/10/24
 */

@RestController
@RequestMapping("api/system/user")
@Slf4j
public class SystemUserController {
    
	@Autowired
	SysRoleService sysRoleService;
	
	/*
	 * 获取当前组织用户信息 无组织则获取所有用户信息
	 */
	 @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	    public TableResultResponse<User>  taskReportScoreByuser(@RequestParam Map<String, Object> params) {
	        //查询列表数据
	        Query query = new Query(params);
	        return sysRoleService.getUsers(query);
	    }
	
	/*
	 * 获取角色列表
	 */
	@RequestMapping(value = "/getrolelist",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse getrolelist(@RequestParam Map<String,Object> params){
        List<Role> list = sysRoleService.getrole(params);
        return new ObjectRestResponse<>().data(list);
    }	
	
	/*
	 * 获取菜单列表 
	 */
	//使用api/menu/menuTree接口已实现，直接调用 具体位置 cn.net.easyinfo.menu.controller.MenuController。java下
	
	/*
	 * 获取两者中间表
	 */
	@RequestMapping(value = "/getrolemenu",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse getrolemenu(@RequestParam Map<String,Object> params){
        List<RoleRight> list = sysRoleService.getroleright(params);
        return new ObjectRestResponse<>().data(list);
    }
	
	/*
	 * 通过用户id修改角色id即roleid
	 */
	@RequestMapping(value = "/changerolebyid", method = RequestMethod.PUT)
    public RespBean changerolebyid(@RequestParam Map<String, Object> params) {
        if (sysRoleService.changerolebyid(params)) {
            return new RespBean("success", "修改成功!");
        }
        return new RespBean("error", "修改失败!");
    }
	
	/*
	 * 通过用户id修改角色id即roleid
	 */
	@RequestMapping(value = "/updaterolemenu", method = RequestMethod.PUT)
    public RespBean updaterolemenu(@RequestParam Map<String, Object> params) {
        if (sysRoleService.updaterolemenu(params)) {
            return new RespBean("success", "修改成功!");
        }
        return new RespBean("error", "修改失败!");
    }
}
