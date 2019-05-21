package cn.net.easyinfo.report.controller;

import cn.net.easyinfo.bean.RespBean;
import cn.net.easyinfo.common.controller.BaseController;
import cn.net.easyinfo.common.msg.TableResultResponse;
import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.entity.ReportType;
import cn.net.easyinfo.report.entity.Report;
import cn.net.easyinfo.entity.ReportTypeBig;
import cn.net.easyinfo.report.service.ReportTypeService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/reportType")
public class ReportTypeController extends BaseController<ReportTypeService,ReportType> {
    @Autowired
    ReportTypeService reportTypeService;
    
    @RequestMapping(value = "/mytask", method = RequestMethod.GET)
    public TableResultResponse<Report>  mytaskbypage(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return reportTypeService.getmytask(query);
    }
    
    @RequestMapping(value = "/getmytaskmissiontype", method = RequestMethod.GET)
    public TableResultResponse<Report>  getmytaskmissiontype(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return reportTypeService.getmissiontype(query);
    }
    
    @RequestMapping(value = "/delmyreport/{ids}", method = RequestMethod.DELETE)
    public RespBean deleteEmpById(@PathVariable String ids) {
        if (reportTypeService.delmyreport(ids)) {
            return new RespBean("success", "删除成功!");
        }
        return new RespBean("error", "删除失败!");
    }
    
    @RequestMapping(value = "/getBigType",method = RequestMethod.GET)
    public Map<String,Object> getBigType(@RequestParam Map<String,Object> param){
        Map<String,Object> map = new HashMap<String,Object>();
        List<ReportTypeBig> list = reportTypeService.getBigType(param);
        map.put("data",list);
        map.put("status",200);
        return map;
    }

    @RequestMapping(value = "/getType" ,method = RequestMethod.GET)
    public TableResultResponse<Report> getType(@RequestParam Map<String,Object> params){
        //当前用户ID
        params.put("userid", 1);
        return reportTypeService.getType(params);
    }

}
