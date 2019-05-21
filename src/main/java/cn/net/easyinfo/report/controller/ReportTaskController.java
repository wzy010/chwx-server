package cn.net.easyinfo.report.controller;

import cn.net.easyinfo.common.controller.BaseController;
import cn.net.easyinfo.common.msg.TableResultResponse;
import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.common.util.CommonUtil;
import cn.net.easyinfo.common.util.DateUtil;
import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.common.vo.SysResult;
import cn.net.easyinfo.report.entity.Report;
import cn.net.easyinfo.entity.ReportUser;
import cn.net.easyinfo.report.service.ReportTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/reportTask")
public class ReportTaskController extends BaseController<ReportTaskService,ReportUser> {
    @Autowired
    private ReportTaskService reportTaskService;

    @RequestMapping(value = "/getLastReportId",method = RequestMethod.GET)
    public Map<String,Object> getLastReportId(){
        Map<String,Object> map = new HashMap<>();
            int id = reportTaskService.getLastReportId();
            map.put("id",id);
            map.put("status",200);
            return map;
    }

    @RequestMapping(value ="/add" ,method = RequestMethod.POST)
    public SysResult reportTask(@RequestBody Map params){
        //log.debug("**********"+params);
        try {
            reportTaskService.reportTask(params);
            if((Integer)params.get("number")>0){
                params.put("userid",1);
                reportTaskService.updateNum(params);
            }

            return SysResult.oK(200,"任务上报成功!");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  SysResult.build(201,"任务上报失败!");
    }

    @RequestMapping(value = "/reportAudit",method = RequestMethod.GET)
    public TableResultResponse<Report> reportAudit(@RequestParam Map<String,Object> params){
        params.put("ptime", DateUtil.jointStartTime(String.valueOf(params.get("ptime"))));
        params.put("endtime", DateUtil.jointEndTime(String.valueOf(params.get("endtime"))));
        String deptid = CommonUtil.getRequest().getParameter("deptid");
        if (deptid == null || deptid.equals("-1") || deptid.equals("")){
            //获取当前用户部门ID
            //dept = ?
            params.put("deptid",1);
        }
        return  reportTaskService.reportAudit(params);
    }

    @RequestMapping(value = "/updateAudit",method = RequestMethod.POST)
    public SysResult updateAudit(@RequestBody Map params){

        try {
            //设置用户ID
            params.put("userid",1);
            Integer suc = reportTaskService.updateAudit(params);//发的
            return SysResult.oK(200,"审核成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return SysResult.build(201,"审核失败");
    }


    @RequestMapping(value = "/reportCountByUser",method = RequestMethod.GET)
    public TableResultResponse<Report> reportCountByUser(@RequestParam Map<String,Object> params) {
        log.debug(params.toString());
        params.put("ptime", DateUtil.jointStartTime(String.valueOf(params.get("ptime"))));
        params.put("endtime", DateUtil.jointEndTime(String.valueOf(params.get("endtime"))));
        String isAu = CommonUtil.getRequest().getParameter("isAu");
        if (isAu == null || isAu.equals("0") || isAu == "") params.put("isAu", 0);
        else params.put("isAu", 1);
        if (params.get("deptid") == null || params.get("deptid").equals("-1")
                || params.get("deptid").equals("")) {
            //获取当前用户部门ID
            //?????????
            params.put("deptid", 1);
        }
        return reportTaskService.reportCountByUser(params);
    }
    /*
     * 汇总统计 组员筛选 数据接口
     */
    @RequestMapping(value = "/taskReportScoreByuser", method = RequestMethod.GET)
    public TableResultResponse<Report>  taskReportScoreByuser(@RequestParam Map<String, Object> params) {
        //查询列表数据
    	if(params.get("deptid")==null||params.get("deptid").equals("null")||params.get("deptid").equals(""))
    	{
    		params.put("deptid", 1);
    	}
        Query query = new Query(params);
        return reportTaskService.taskReportScoreByuser(query);
    }

    @RequestMapping(value = "/taskReportDetailByUser",method = RequestMethod.GET)
    public TableResultResponse<Report> taskReportDetailByUser(@RequestParam Map<String,Object> params){
        params.put("ptime", DateUtil.jointStartTime(String.valueOf(params.get("ptime"))));
        params.put("endtime", DateUtil.jointEndTime(String.valueOf(params.get("endtime"))));
        return reportTaskService.taskReportDetailByUser(params);
    }
    @RequestMapping(value="/byDeptReport",method = { RequestMethod.POST,RequestMethod.GET })
    public TableResultResponse<cn.net.easyinfo.report.entity.Report>  byDeptReport(@RequestParam Map<String, Object> params){
        if(params.get("deptid")==null||params.get("deptid").equals("null")||params.get("deptid").equals(""))
        {
            params.put("deptid", 1);
        }
        params.put("fid", params.get("deptid"));
        Query query = new Query(params);
        return reportTaskService.byDeptReport(query);
    }


}
