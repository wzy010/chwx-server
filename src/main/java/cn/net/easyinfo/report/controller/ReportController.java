package cn.net.easyinfo.report.controller;

import cn.net.easyinfo.bean.RespBean;
import cn.net.easyinfo.common.controller.BaseController;
import cn.net.easyinfo.common.msg.TableResultResponse;
import cn.net.easyinfo.common.poi.ReportPoiUtils;
import cn.net.easyinfo.common.util.CommonUtil;
import cn.net.easyinfo.common.util.DateUtil;
import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.common.util.StringUtil;
import cn.net.easyinfo.common.vo.SysResult;
import cn.net.easyinfo.entity.ReportType;
import cn.net.easyinfo.entity.Tree;
import cn.net.easyinfo.report.entity.Report;
import cn.net.easyinfo.report.service.ReportService;
import cn.net.easyinfo.report.service.ReportTypeService;
import cn.net.easyinfo.report.service.UserService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/report")
@Slf4j
public class ReportController extends  BaseController<ReportService,Report> {

    @Autowired
    private ReportService reportService;
    @Autowired
    private ReportTypeService reportTypeService;
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/queryorder", method = RequestMethod.GET)
    public TableResultResponse<Report>  queryOrderByPage(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return reportService. queryOrderByPage(query);
    }

    @RequestMapping(value="/getPublish",method = RequestMethod.GET)
    public TableResultResponse<Report> getPublish(@RequestParam Map<String,Object> params){
        //设置当前用户ID
        params.put("userid",1);
        //设置当前用户的部门ID
        params.put("deptid",1);
        params.put("ptime", DateUtil.jointStartTime(String.valueOf(params.get("ptime"))));
        params.put("endtime", DateUtil.jointEndTime(String.valueOf(params.get("endtime"))));
        return reportService.getPublish(params);
    }

    @RequestMapping(value = "/getReceive",method = RequestMethod.GET)
    public TableResultResponse<Report> queryReceiveByPage(@RequestParam Map<String,Object> params){
        //暂时写死用户ID
        params.put("userid", 1);
        params.put("ptime", DateUtil.jointStartTime(String.valueOf(params.get("ptime"))));
        params.put("endtime", DateUtil.jointEndTime(String.valueOf(params.get("endtime"))));

        return reportService.getReceiveTask(params);
    }


    @RequestMapping(value ="/publishReport",method = RequestMethod.POST)
    public SysResult add(@RequestBody  Map<String,Object> params){

        try {
        String address = StringUtil.decode((String) params.get("address"));
        params.put("address", address);
        //获取当前用户ID
        params.put("userid",1);
            //1.判断typeAfter
            String typeAfter = (String) params.get("typeAfter");
            if("直发".equals(typeAfter)){
                params.put("typeAfter",1);
            }else if("转发".equals(typeAfter)){
                params.put("typeAfter",2);
            }else if("点赞".equals(typeAfter)){
                params.put("typeAfter",3);
            }else if("评论".equals(typeAfter)){
                params.put("typeAfter",4);
            }
            //2.设置typeName
            Integer type = (Integer) params.get("type");
            ReportType reportType = reportTypeService.selectById(type);
            log.debug("*************"+reportType.getValue());
            String typeName = reportType.getValue() + typeAfter;
            params.put("typeName",typeName);
            reportService.publishReport(params);
            return  SysResult.oK(200,"任务发布成功!");
        }catch (Exception e){
            e.printStackTrace();
        }
        return SysResult.build(201,"任务发布失败!");
    }

    @RequestMapping(value = "/exportReports", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportEmp(@RequestParam Map<String,Object> params) {

        return ReportPoiUtils.exportrep2Excel(reportService.getAllReports(params));
    }

    @RequestMapping(value = "/importReports", method = RequestMethod.POST)
    public RespBean importEmp(MultipartFile file) {
        List<Report> reports = ReportPoiUtils.importrep2List(file,userService.getAllUsers());
        if (reportService.addReports(reports) == reports.size()) {
            return new RespBean("success", "导入成功!");
        }
        return new RespBean("error", "导入失败!");
    }

    @RequestMapping(value = "/getdep/{id}")
    public List<Tree> get(@PathVariable Integer id){
        Map<String,Object> map = new HashMap<>();
        map.put("deptid",id);
        return  reportService.get(map);
    }
    
    @RequestMapping(value = "/getPictureAddress", method = RequestMethod.GET)
    public TableResultResponse<Report> getPictureAddress(@RequestParam Map<String,Object> params) {
    	Query query = new Query(params);
        return reportService.getPictureAddress(query);
    }

//    @RequestMapping(value = "/mytask", method = RequestMethod.GET)
//    public TableResultResponse<Report>  mytaskbypage(@RequestParam Map<String, Object> params) {
//        //查询列表数据
//        Query query = new Query(params);
//        return reportService.getmytask(query);
//    }


    @RequestMapping(value = "/getReport",method = RequestMethod.GET)
    public TableResultResponse<Report> getReport(@RequestParam Map<String,Object> params){
        String name = CommonUtil.getRequest().getParameter("name");
        params.put("ptime", DateUtil.jointStartTime(String.valueOf(params.get("ptime"))));
        params.put("endtime", DateUtil.jointEndTime(String.valueOf(params.get("endtime"))));
        //params.put("name", StringUtil.formatStr(name));
        if(params.get("deptid")==null || params.get("deptid").equals("-1")
                || params.get("deptid").equals("") || params.get("deptid").equals("undefined")){
            //获取当前用户部门ID
            //params.put("deptid", CommonUtil.getUser().getDeptid());
            params.put("deptid",1);
        }
        return reportService.getReports(params);
    }


    @RequestMapping(value = "/getReportUser",method = RequestMethod.GET)
    public TableResultResponse<Report> getReportUser(@RequestParam Map<String,Object> params){
        return reportService.getReportUser(params);
    }

    @RequestMapping(value = "/getReportTypeName",method = RequestMethod.GET)
    public SysResult getReportTypeName(){
        return reportTypeService.getReportTypeName();
    }

    @RequestMapping(value = "/reportDetail",method = RequestMethod.GET)
    public Map<String,Object> reportDetail(@RequestParam Map<String,Object> params){
        Map<String,Object> map = new HashMap<String,Object>();
        params.put("start", 0);
        params.put("limit", 10);
        Report report = reportService.reportDetail(params);
        //地区占比
        {
            List<Report> list = reportService.reportByDept(params);
            for(Report obj : list){
                obj.setName(obj.getName()+"("+obj.getNumber()+")");
                String deptPie = jsonData(list);
                map.put("report",report);
                map.put("deptlist",list);
                map.put("deptPie",deptPie);
            }
        }
        //个人排名
        {
            List<Report> userlist = reportService.getUserByReport(params);
            if (userlist.size()>=10) {
                userlist = userlist.subList(0, 10);
            }
            map.put("userlist",userlist);
            String barAxis[] = new String[userlist.size()];
            int barAYis[] = new int[userlist.size()];
            for(int i=0;i<userlist.size();i++){
                barAxis[i] = userlist.get(i).getName();
                barAYis[i] = userlist.get(i).getNumber();
            }
            map.put("barAxis",barAxis);
            map.put("barAYis", barAYis);
            /*map.put("barAxis",Arrays.toString(barAxis));
            map.put("barAYis", Arrays.toString(barAYis));*/
        }
        //积分排名
        {
            List<Report> jflist = reportService.reportByJF(params);
            if(jflist.size()>=10) {
                jflist = jflist.subList(0, 10);
            }
            map.put("jflist",jflist);
            String jfbarAxis[] = new String[jflist.size()];
            int jfbarAYis[] = new int[jflist.size()];
            for(int i=0;i<jflist.size();i++){
                jfbarAxis[i] = jflist.get(i).getName();
                jfbarAYis[i] = jflist.get(i).getScore();
            }
            map.put("jfbarAxis", jfbarAxis);
            map.put("jfbarAYis", jfbarAYis);
           /* map.put("jfbarAxis", Arrays.toString(jfbarAxis));
            map.put("jfbarAYis", Arrays.toString(jfbarAYis));*/
        }
        //响应速度排名
        {
            List<Report> lvlist = reportService.reportByLV(params);
            if(lvlist.size()>=10) {
                lvlist.subList(0, 10);
            }
            map.put("lvlist", lvlist);
            String lvbarAxis[] = new String[lvlist.size()];
            int lvdata2[] = new int[lvlist.size()];
            for(int i=0;i<lvlist.size();i++){
                lvbarAxis[i] = lvlist.get(i).getName();
                lvdata2[i] = lvlist.get(i).getNumber();
            }
            map.put("lvbarAxis",lvbarAxis);
            map.put("lvdata", lvdata2);
    /*        map.put("lvbarAxis", Arrays.toString(lvbarAxis));
            map.put("lvdata", Arrays.toString(lvdata2));*/
        }
        return map;
    }

    public String jsonData(List<Report> list){
        JSONArray ja = new JSONArray();
        for(int i=0;i<list.size();i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", list.get(i).getName());
            jsonObject.put("value", list.get(i).getNumber());
            //jsonObject.put("itemStyle", colorArr[i]);
            ja.add(jsonObject);
        }
        System.out.println("*****************"+ja.toString());
        return ja.toString();
    }

    public static Object colorArr[] = new Object[10];
    static{
        JSONObject jcolor0 = new JSONObject(); jcolor0.put("color","#2ec7c9");
        JSONObject jcolor1 = new JSONObject(); jcolor1.put("color","#b6a2de");
        JSONObject jcolor2 = new JSONObject(); jcolor2.put("color","#5ab1ef");
        JSONObject jcolor3 = new JSONObject(); jcolor3.put("color","#ffb980");
        JSONObject jcolor4 = new JSONObject(); jcolor4.put("color","#d87a80");
        JSONObject jcolor5 = new JSONObject(); jcolor5.put("color","#8d98b3");
        JSONObject jcolor6 = new JSONObject(); jcolor6.put("color","#e5cf0d");
        JSONObject jcolor7 = new JSONObject(); jcolor7.put("color","#97b552");
        JSONObject jcolor8 = new JSONObject(); jcolor8.put("color","#95706d");
        JSONObject jcolor9 = new JSONObject(); jcolor9.put("color","#dc69aa");
        colorArr[0] = jcolor0; colorArr[1] = jcolor1;
        colorArr[2] = jcolor2; colorArr[3] = jcolor3;
        colorArr[4] = jcolor4; colorArr[5] = jcolor5;
        colorArr[6] = jcolor6; colorArr[7] = jcolor7;
        colorArr[8] = jcolor8; colorArr[9] = jcolor9;
    }
}
