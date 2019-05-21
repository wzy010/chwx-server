package cn.net.easyinfo.controller;

import cn.net.easyinfo.common.controller.BaseController;
import cn.net.easyinfo.common.vo.SysResult;
import cn.net.easyinfo.entity.RightManager;
import cn.net.easyinfo.service.RightManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/rightManager")
public class RightManagerController extends BaseController<RightManagerService,RightManager> {
    @Autowired
    private RightManagerService rightManagerService;

    @RequestMapping(value = "/getRole",method = RequestMethod.GET)
    public SysResult getRole(){
        List<RightManager> list = null;
        try {
        Map<String,Object> params = new HashMap<String,Object>();
        //获取当前用户ID
        params.put("userid",1);
        list = rightManagerService.getRole(params);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new SysResult(list);
    }

}
