package cn.net.easyinfo.controller;

import cn.net.easyinfo.common.controller.BaseController;
import cn.net.easyinfo.common.msg.BaseResponse;
import cn.net.easyinfo.common.msg.ObjectRestResponse;
import cn.net.easyinfo.common.msg.TableResultResponse;
import cn.net.easyinfo.common.util.ConfigInfo;
import cn.net.easyinfo.common.vo.SysResult;
import cn.net.easyinfo.entity.*;
import cn.net.easyinfo.report.service.UserService;
import cn.net.easyinfo.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: bobqiu
 * @create: 2018-08-23
 **/
@RestController
@RequestMapping("api/user")
@Slf4j
public class UserController extends BaseController<SysUserService,SysUser> {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUnitsubAllUserNumber",method = RequestMethod.GET)
    public SysResult getUnitsubAllUserNumber(@RequestParam Map<String,Object> params){
        return userService.getUnitsubAllUserNumber(params);
    }

    @RequestMapping(value = "/getUnitUser",method = RequestMethod.GET)
    public TableResultResponse<LoginUser> getUnitUser(@RequestParam Map<String,Object> params){
        return userService.getUnitUser(params);
    }

    @RequestMapping(value = "/modifyUserSubPwd",method = RequestMethod.PUT)
    public SysResult modifyUserSubPwd(@RequestBody Map<String,Object> params){
        params.put("111",11);
        try{
            int num = userService.modifyUserSubPwd(params);
            if(num >0 ){
                return SysResult.oK(200,"密码修改成功!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return SysResult.build(201,"密码修改失败!");
    }
    /**
     * 获得民族列表
     */
    @RequestMapping(value = "/getNation",method = RequestMethod.GET)
    public SysResult getNation(){
        return userService.getNation();
    }
    /**
     * 获得中共党员等政治面貌信息列表
     */
    @RequestMapping(value = "/getPolitical",method = RequestMethod.GET)
    public SysResult getPolitical(){
        List<Political> list = null;
        try {
            list = userService.getPolitical();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new SysResult(list);
    }

    /**
     * 获得网评员属性 (绿色账号/红色大V....)
     */
    @RequestMapping(value = "/getUserType",method = RequestMethod.GET)
    public SysResult getUserType(){
        List<UserType> list = null;
        try{
            list = userService.getUserType();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new SysResult(list);
    }

    /**
     * 获得学历列表
     */
    @RequestMapping(value = "/getDegree",method = RequestMethod.GET)
    public SysResult getDegree(){
        List<Degree> list = null;
        try {
            list = userService.getDegree();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new SysResult(list);
    }
    /**
     * 根据用户id获得用户详情
     */
    @RequestMapping(value = "/getDetail",method = RequestMethod.GET)
    public ObjectRestResponse<LoginUser> getDetail(@RequestParam Map<String,Object> map){
        LoginUser loginUser = null;
        try {
        int id = 0;
        String idStr = map.get("id").toString();
        if (idStr != null && !idStr.equals("")) {
            id = Integer.parseInt(idStr);
        }
        loginUser = userService.getDetail(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        //return new SysResult(list);
        return new ObjectRestResponse<LoginUser>().data(loginUser);

    }
    /**
     * 更新用户信息
     */
    @RequestMapping(value = "/updateUser",method = RequestMethod.PUT)
    public BaseResponse updateUser(@RequestBody Map<String,Object> params){
        try {
        String id = params.get("id").toString();
        if (id != null && !id.equals("")){
            List<LoginUser> list = userService.vaildateUpdateUser(params);
            int n =0;
            if(list.size() > 0){
                return new BaseResponse(201,"该用户名已存在");
            }else {
                n = userService.updateUserById(params);
                if(n>0){
                    return new BaseResponse(200,"修改成功");
                }
            }
        }
        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponse(201,"修改失败!");
        }
        return new BaseResponse(200,"修改成功");
    }
    /**
     * 添加网评员
     */
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public BaseResponse addUser(@RequestBody Map<String,Object> params){
        int n = 0;
        String name = (String)params.get("realName");
        try{
            List<LoginUser> list = userService.selLikeRealByName(params);
            if (list.size() > 0) {
                int index = list.size() + 1;
                name = (String)params.get("realName") + index;
            }
            params.put("name", name);
            params.put("password", ConfigInfo.DEFAULT_LOGIN_PASSWORD);
            n = userService.addUser(params);
            if(n>0){
                return new BaseResponse(200,"添加成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new BaseResponse(200,"添加失败");
        }

        return new BaseResponse(200,"添加成功");
    }
    /**
     * 删除网评员
     */
    @RequestMapping(value = "/deleteUser/{ids}",method = RequestMethod.DELETE)
    public BaseResponse doDeleteUser(@PathVariable String ids){
        if(userService.deleteUserById(ids)){
            return new BaseResponse(200,"删除成功!");
        }
        return new BaseResponse(201,"删除失败!");
    }
    /**
     * 修改网评员部门
     */
    @RequestMapping(value = "/updateDept",method = RequestMethod.PUT)
    public BaseResponse updateDept(@RequestBody Map<String,Object> params){
        if(userService.updateDept(params)){
            return new BaseResponse(200,"修改成功!");
        }
        return new BaseResponse(201,"修改失败!");
    }
}
