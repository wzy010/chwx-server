package cn.net.easyinfo.controller;

import cn.net.easyinfo.common.msg.TableResultResponse;
import cn.net.easyinfo.common.util.DateUtil;
import cn.net.easyinfo.entity.History;
import cn.net.easyinfo.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public TableResultResponse<History> list(@RequestParam Map<String,Object> params){
        try {
            String sname = params.get("name").toString();
            //用户ID,暂时写死
            params.put("userid", 1);
            if(params.get("starttime")!=null)
                params.put("starttime", DateUtil.jointStartTime(params.get("starttime").toString()));
            if(params.get("endtime")!=null)
                params.put("endtime", DateUtil.jointEndTime(params.get("endtime").toString()));
            if (sname != null && !sname.equals("")) {
                sname = new String(sname.getBytes("ISO-8859-1"),"UTF-8");
            }
            params.put("sname", sname);
            //设置当前用户部门ID
            params.put("deptid",1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return historyService.select(params);
    }
}
