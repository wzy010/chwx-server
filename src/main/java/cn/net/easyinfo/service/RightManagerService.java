package cn.net.easyinfo.service;

import cn.net.easyinfo.common.biz.BaseService;
import cn.net.easyinfo.common.util.ConfigInfo;
import cn.net.easyinfo.entity.RightManager;
import cn.net.easyinfo.mapper.RigthManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RightManagerService extends BaseService <RigthManagerMapper,RightManager>{
    @Autowired
    private RigthManagerMapper rigthManagerMapper;

    /**
     * 获得角色
     * 若是admin用户则显示权限的value字段(即可以看到一级、二级管理员)
     * 否则只能看到自定义的和管理员网评员角色
     */
    public List<RightManager> getRole(Map<String,Object> params){
        List<RightManager> list = null;
        try {
            if(params.get("userid").equals(ConfigInfo.ADMINID)){//admin
                list = rigthManagerMapper.getAllRole();
            }else {
                list = rigthManagerMapper.getRole(params);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
