package cn.net.easyinfo.dept.controller;

import cn.net.easyinfo.common.controller.BaseController;
import cn.net.easyinfo.common.msg.BaseResponse;
import cn.net.easyinfo.common.msg.ObjectRestResponse;
import cn.net.easyinfo.dept.service.NDeptService;
import cn.net.easyinfo.entity.Departments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/ndept")
public class NDeptController extends BaseController<NDeptService,Departments> {

    @Autowired
    private NDeptService nDeptService;

    /** 
     *    获取所有的部门
     * @param id
     * @return
     */
    @RequestMapping(value = "/deptTree/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Departments> queryDepts(@PathVariable Integer id){
        List<Departments> list = nDeptService.selectDeptById(id);
        return new ObjectRestResponse<>().data(list);
    }

    /**
     * 添加部门
     */
    @RequestMapping(value = "/addDept",method = RequestMethod.POST)
    public BaseResponse addDep(@RequestBody Map<String,Object> params){
        try {

        String fdeptid = params.get("deptid").toString();
        params.put("fdeptid", fdeptid);
        params.put("deptid", fdeptid);
        params.put("fid", fdeptid);
        int suc = 0;
        if(fdeptid != null && !fdeptid.equals("")){
            List<Departments> list = nDeptService.selectByDeptName(params);
            if(list.size()>0){
                return new BaseResponse(200,"该部门已存在!");
            }else {
                Integer sort = nDeptService.selMaxSortByFid(params);
               if(sort != null){
                   sort = sort +1;
              }else {
                   sort = 1;
               }
                params.put("sort", sort + "");
                Departments rootDept = nDeptService.getRootdept(params);
                if (rootDept != null) {
                    params.put("setAdmin", rootDept.getSetAdmin() + "");
                }
                suc = nDeptService.addDept(params);
            }
        }
        if(suc >0 ){
            return new BaseResponse(200,"部门添加成功!");
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new BaseResponse(201,"部门添加失败!");
    }

    /**
     * 修改部门
     */
    @RequestMapping(value = "/updateDept",method = RequestMethod.PUT)
    public BaseResponse updateDept(@RequestBody Map<String,Object> params){
        Integer n = null;
        try {
            List<Departments> list = nDeptService.selectByDeptName(params);
            if (list.size() > 0) {
                return new BaseResponse(200, "该部门已存在!");
            }
            n = nDeptService.updateDeptById(params);
            if(n >0 ) {
                return new BaseResponse(200, "修改成功!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new BaseResponse(201,"修改失败!");
    }

    /**
     * 删除部门
     */
    @RequestMapping(value = "/deleteDept/{id}",method = RequestMethod.DELETE)
    public BaseResponse deleteDept(@PathVariable Integer id){
        Integer n = null;
        try {
            //当前用户部门ID
            //String userdeptid = CommonUtil.getUser().getDeptid()+"";//不能删除自己所在的部门
            String userdeptid = "1";
            if (!userdeptid.equals(id)) {
                n = nDeptService.deleteDepart(id);
            }
            if (n >0)return  new BaseResponse(200,"删除成功!");
        }catch (Exception e){
            e.printStackTrace();
        }
        return new BaseResponse(201,"删除失败!");
    }

    /**
     * 获取该部门下的所有部门
     */
    @RequestMapping(value = "/showDept",method = RequestMethod.GET)
    public ObjectRestResponse<Departments> showDept(@RequestParam Integer id){
        List<Departments> list = nDeptService.selectDeptById(id);
        return  new ObjectRestResponse<>().data(list);
    }

}
