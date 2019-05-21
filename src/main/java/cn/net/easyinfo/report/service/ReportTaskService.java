package cn.net.easyinfo.report.service;

import cn.net.easyinfo.common.biz.BaseService;
import cn.net.easyinfo.common.msg.TableResultResponse;
import cn.net.easyinfo.common.util.DateUtil;
import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.entity.Departments;
import cn.net.easyinfo.report.entity.Department;
import cn.net.easyinfo.report.entity.Report;
import cn.net.easyinfo.entity.ReportUser;
import cn.net.easyinfo.mapper.NDeptMapper;
import cn.net.easyinfo.mapper.ReportTaskMapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReportTaskService extends BaseService<ReportTaskMapper,ReportUser> {

    @Autowired
    private ReportTaskMapper reportTaskMapper;

    @Autowired
    private NDeptMapper nDeptMapper;

    public int getLastReportId(){
        return reportTaskMapper.getLastReportId();
    }

    public void reportTask(Map params){
        params.put("id",0);
        try {
            //此处暂时写死用户ID
            params.put("userid",1);
            Integer radio = (Integer) params.get("radio2");
            if (radio == 3){
                params.put("type",2);
            }else if (radio == 6){
                params.put("type",10);
            }else if (radio == 9){
                params.put("type",27);
            }
                reportTaskMapper.addReportTask(params);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public int updateNum(Map params){
        int suc  = 0;
        try {
            suc = reportTaskMapper.updateNum(params);
            //修改已读
            reportTaskMapper.updateReportRead(params);
        }catch (Exception e) {
           e.printStackTrace();
        }
        return suc;
    }

    public TableResultResponse<Report> reportAudit(Map params){

        String deptid =  params.get("deptid").toString();
        String[] deptids = deptid.split(",");
        StringBuffer sb = new StringBuffer();
        for (int i=0 ; i<deptids.length ; i++) {
            params.put("deptid",deptids[i]);
            //根据部门ID获取员工
            sb.append(getdeptsonids(deptids[i],1));
            if(i<deptids.length-1) sb.append(",");
        }
        //返回的不应该包括自身的deptid
        String str = sb.toString().substring(7);
        params.put("deptids",str);
        //params.put("deptids",sb.toString());

        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<Report> list =  reportTaskMapper. reportAudit(query);
        for(int i=0;i<list.size();i++){
            list.get(i).setIndex(i+1);
        }

        return new TableResultResponse<Report>(result.getTotal(),list);
    }

    private String getdeptsonids(String fid,int havaMyDept) {
        StringBuffer sb = new StringBuffer();
        Map<String,String> map =new HashMap<String,String>();
        map.put("fid", fid);
        List<Departments> deptlist=nDeptMapper.getSonDeptidByDeptId(map);
        if(havaMyDept == 1){
            sb.append(fid);
        }
        if(deptlist!=null&&deptlist.size()>0)
        {
            if(havaMyDept == 1){
                sb.append(",");
            }
            for (int i=0 ; i<deptlist.size() ; i++) {
                //根据部门ID获取员工
                sb.append(getdeptsonids(""+deptlist.get(i).getId(),1));
                if(i<deptlist.size()-1) sb.append(","); }
        }
        return sb.toString();
    }

    public Integer updateAudit(Map<String,Object> params){
        int suc = 0;
        try {
            Report r = reportTaskMapper.byReportBysubId(params);
            if(r.getType()==1){
                params.put("uduserid", r.getUserid());
                String time = r.getPtime().substring(0, 10);
                params.put("udstarttime", DateUtil.jointStartTime(time));
                params.put("udendtime", DateUtil.jointEndTime(time));
                int score = reportTaskMapper.bySumScoreByuser(params);

                int syscore = r.getMaxscore()-score;//剩余积分  未达到最大分值
                int scoreup = Integer.parseInt(params.get("score").toString());//要更新的分值
                if(syscore<=0) params.put("score", 0);
                else if(syscore-scoreup>0) params.put("score", scoreup);//剩余积分比要更新的积分大,则更新积分
                else params.put("score", syscore);//剩余积分比要更新的积分小,则剩余积分
            }
            suc = reportTaskMapper.updateAudit(params);
        }catch (Exception e){
            e.printStackTrace();
        }
        return suc;
    }
    
    public TableResultResponse<cn.net.easyinfo.report.entity.Report> taskReportScoreByuser(Query query) {
		// TODO Auto-generated method stub
		String deptid =  query.get("deptid").toString();
        String[] deptids = deptid.split(",");
        StringBuffer sb = new StringBuffer();
        for (int i=0 ; i<deptids.length ; i++) {
        	query.put("deptid",deptids[i]);
            //根据部门ID获取员工
            sb.append(getdeptsonids(deptids[i],1));
            if(i<deptids.length-1) sb.append(",");
        }
        query.put("deptids",sb.toString());
		Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
		List<cn.net.easyinfo.report.entity.Report> list = reportTaskMapper.taskReportScoreByuser(query);
		return new TableResultResponse<cn.net.easyinfo.report.entity.Report>(result.getTotal(), list);
	}
	

	public TableResultResponse<cn.net.easyinfo.report.entity.Report> byDeptReport(Query query) {
		// TODO Auto-generated method stub
		List<Department> listd = reportTaskMapper.getDeptByFid(query);
		List<cn.net.easyinfo.report.entity.Report> listr = new ArrayList<cn.net.easyinfo.report.entity.Report>();
		for(int i=0;i<listd.size();i++){
			cn.net.easyinfo.report.entity.Report r = new cn.net.easyinfo.report.entity.Report();
			r.setId(listd.get(i).getId());
			r.setName(listd.get(i).getName());
			listr.add(r);
		}
		for(int i=0;i<listr.size();i++){
			query.put("deptid", listr.get(i).getId());
			String deptids =getdeptsonids(""+listr.get(i).getId(),1);
			query.put("deptids", deptids);
			Report r = reportTaskMapper.byDeptReport(query);
			if(r!=null){
				listr.get(i).setNumber(r.getNumber());
				listr.get(i).setScore(r.getScore());
			}
		}
		Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
		return new TableResultResponse<cn.net.easyinfo.report.entity.Report>(result.getTotal(), listr);
	}

	public TableResultResponse<cn.net.easyinfo.report.entity.Report> reportCountByUser(Map<String,Object> params){
	    String deptid =  params.get("deptid").toString();
	    String[] deptids = deptid.split(",");
	    StringBuffer sb = new StringBuffer();
	    for (int i=0 ; i<deptids.length ; i++) {
        params.put("deptid",deptids[i]);
        sb.append(getdeptsonids(deptids[i],1));
        if(i<deptids.length-1) sb.append(",");
	    }
        //返回的不应该包括自身的deptid
        params.put("deptids",sb.toString());
        params.put("node", sb.toString());
        //params.put("deptids",sb.toString());

        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<cn.net.easyinfo.report.entity.Report> list = reportTaskMapper.taskReportScoreByuser(query);

        for(int i=0;i<list.size();i++){
            list.get(i).setIndex(i+1);
        }

        return new TableResultResponse<cn.net.easyinfo.report.entity.Report>(result.getTotal(),list);
    }

    public TableResultResponse<Report> taskReportDetailByUser(Map<String,Object> params){
        Query query = new Query(params);
        Page<Object> result = PageHelper.startPage(query.getPage(),query.getLimit());
        List<Report> list = reportTaskMapper.taskReportDetailByUser(query);
        for(int i=0;i<list.size();i++){
            list.get(i).setIndex(i+1);
        }
        return new TableResultResponse<>(result.getTotal(),list);
    }
//	public TableResultResponse<Report> taskReportScoreByuser(Query query) {
//		// TODO Auto-generated method stub
//		query.put("deptids", getdeptsonids(query.get("deptid").toString(), 1));
//		Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
//		List<Report> list = reportTaskMapper.taskReportScoreByuser(query);
//		return new TableResultResponse<Report>(result.getTotal(), list);
//	}

//    public TableResultResponse<cn.net.easyinfo.report.entity.Report> byDeptReport(Query query) {
//        // TODO Auto-generated method stub
//        List<Department> listd = reportTaskMapper.getDeptByFid(query);
//        List<Report> listr = new ArrayList<Report>();
//        for(int i=0;i<listd.size();i++){
//            Report r = new Report();
//            r.setId(listd.get(i).getId());
//            r.setName(listd.get(i).getName());
//            listr.add(r);
//        }
//        for(int i=0;i<listr.size();i++){
//            query.put("deptid", listr.get(i).getId());
//            String deptids =getdeptsonids(""+listr.get(i).getId(),1);
//            query.put("deptids", deptids);
//            Report r = reportTaskMapper.byDeptReport(query);
//            if(r!=null){
//                listr.get(i).setNumber(r.getNumber());
//                listr.get(i).setScore(r.getScore());
//            }
//        }
//        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
//        return new TableResultResponse<Report>(result.getTotal(), listr);
//    }
	
/*	private String getdeptsonids(String fid, int haveMyDept) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>();
		map.put("fid", fid);
		List<Departments> deptlist = nDeptMapper.getSonDeptidByDeptId(map);
		if (haveMyDept == 1) {
			sb.append(fid);
		}
		if (deptlist != null && deptlist.size() > 0) {
			if (haveMyDept == 1) {
				sb.append(",");
			}
			for (int i = 0; i < deptlist.size(); i++) {
				// 根据部门ID获取员工
				sb.append(getdeptsonids("" + deptlist.get(i).getId(), 1));
				if (i < deptlist.size() - 1)
					sb.append(",");
			}
		}
		return sb.toString();
	}*/


}
