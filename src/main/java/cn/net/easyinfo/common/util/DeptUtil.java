package cn.net.easyinfo.common.util;

import cn.net.easyinfo.entity.Department;
import cn.net.easyinfo.entity.Departments;
import cn.net.easyinfo.mapper.NDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

public class DeptUtil {


    public static String getChildDeptByDeptId(Map<String,Object> param,int haveMyDept){
        String ids = param.get("deptid")+"";
        if (haveMyDept == 0) ids = "-1";

        return ids;
    }
}
