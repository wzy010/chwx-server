package cn.net.easyinfo.dept.service;

import cn.net.easyinfo.common.biz.BaseService;
import cn.net.easyinfo.common.util.ConfigInfo;
import cn.net.easyinfo.entity.Departments;
import cn.net.easyinfo.mapper.DeptRightMapper;
import cn.net.easyinfo.mapper.NDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NDeptService extends BaseService<NDeptMapper,Departments> {

    @Autowired
    private NDeptMapper nDeptMapper;

    @Autowired
    private DeptRightMapper deptRightMapper;

    public List<Departments> selectDeptById(Integer id){
        return nDeptMapper.selectDeptById(id);
    }

    public boolean addDep(Map<String,Object> params){
        int n = 0;
        try {
             n = nDeptMapper.addDept(params);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "1".equals(n);
    }

    /**
     * 添加部门
     * @param params
     * @return
     */
    public Integer addDept(Map<String,Object> params){
        int sc = 0;
        try{
            params.put("roleid", getDeep(params)+"");
            Departments dept = this.deptParamsToObject(params);
            sc = nDeptMapper.addDepart(dept);
            Map<String, String> temp = new HashMap<String, String>();
            //若添加的部门是顶级部门,则在添加部门的同时在人数限制表增加一条记录
            if (sc > 0 && params.get("fid") != null &&
                    !params.get("fid").equals("") && params.get("fid").equals("1")) {
                temp.put("deptid", dept.getId() + "");
                temp.put("currentNum", "0");
                deptRightMapper.saveDeptRight(temp);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return sc;
    }

    /**
     * 修改部门
     * @param params
     * @return
     */
    public Integer updateDeptById(Map<String,Object> params){
        return nDeptMapper.updateDept(params);
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    public Integer deleteDepart(Integer id){
        int sc = 0;
        try {
            //查询该部门的所有子部门
            String ids = nDeptMapper.queryChildrenDepartInfo(id);
            //删除部门及关联表包括dept_right
            Map<String,Object> map = new HashMap<>();
            map.put("ids",ids);
            sc = nDeptMapper.deleteDepart(map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return sc;
    }

    public List<Departments> selectDeptsById(Integer id){
        List<Departments> list = null;
        try{
            list = nDeptMapper.selectDeptsById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<Departments> selectByDeptName(Map<String,Object> params){
        return nDeptMapper.selectByDeptName(params);
    }

    public Integer selMaxSortByFid(Map<String,Object> params){
        return nDeptMapper.selMaxSortByFid(params);
    }

    public Departments getRootdept(Map<String,Object> params){
        return  nDeptMapper.getRootdept(params);
    }

    public Integer getDeep(Map<String,Object> params){
        int right=ConfigInfo.right_3;
        if(params.get("fdeptid").equals("1")){
            right = ConfigInfo.right_1;
        }else{
            try{
                Departments dept = nDeptMapper.getDeep(params);
                if(dept != null){
                    if(dept.getFid() == 1){
                        right = ConfigInfo.right_3;
                    }else {
                        right = ConfigInfo.right_3;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return  right;
    }

    public Departments deptParamsToObject(Map<String, Object> params) {
        Departments dept = new Departments();
        dept.setFid(Integer.parseInt((String)params.get("fid")));
        dept.setName((String)params.get("name"));
        dept.setRoleid(Integer.parseInt((String)params.get("roleid")));
        dept.setSort(Integer.parseInt((String)params.get("sort")));
        if(params.get("setAdmin") != null && !params.get("setAdmin").equals("")) {
            dept.setSetAdmin(Integer.parseInt((String)params.get("setAdmin")));
        }
        return dept;
    }
}
