package cn.net.easyinfo.report.service;

import cn.net.easyinfo.common.biz.BaseService;
import cn.net.easyinfo.common.msg.TableResultResponse;
import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.entity.*;
import cn.net.easyinfo.mapper.NDeptMapper;
import cn.net.easyinfo.mapper.ReportMapper;

import cn.net.easyinfo.report.entity.Report;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportService extends BaseService<ReportMapper, Report> {
	@Autowired
	private ReportMapper reportMapper;
	@Autowired
	private NDeptMapper nDeptMapper;

	@Value("${virtualPath}")
	private String virtualPath;

	@Value("${filePath}")
	private String FilePath;

	public TableResultResponse<Report> getPublish(Map<String, Object> params) {
		String deptid = params.get("deptid").toString();
		String[] deptids = deptid.split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < deptids.length; i++) {
			params.put("deptid", deptids[i]);
			// 根据部门ID获取员工
			sb.append(getdeptsonids(deptids[i], 1));
			if (i < deptids.length - 1)
				sb.append(",");
		}
		params.put("deptids", sb.toString());
		Query query = new Query(params);
		Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
		List<Report> list = reportMapper.getPublish(query);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setIndex(i + 1);
		}
		return new TableResultResponse<Report>(result.getTotal(), list);
	}

	/**
	 * 下发任务
	 * 
	 * @param query
	 * @returni
	 */
	public TableResultResponse<Report> queryOrderByPage(Query query) {
		Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
		List<Report> list = reportMapper.queryOrderByPage(query);

		return new TableResultResponse<Report>(result.getTotal(), list);
	}

	/**
	 * 任务接收
	 * 
	 * @param query
	 * @return
	 */
	public TableResultResponse<Report> getReceiveTask(Map params) {

		try {
			Query query = new Query(params);
			Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
			List<Report> list = reportMapper.getReceiveTask(query);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setIndex(i + 1);
			}
			reportMapper.updateReportRead(query);
			return new TableResultResponse<Report>(result.getTotal(), list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new TableResultResponse<Report>();
	}

	/**
	 * 发布任务
	 * 
	 * @param params
	 */
	public void publishReport(Map params) {
		params.put("id", 0);

		// 1.获取所有的用户
		// 去掉左右括号
		String deptid = params.get("deptids").toString();
		deptid = deptid.substring(1, deptid.length() - 1);
		String[] deptids = deptid.split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < deptids.length; i++) {
			params.put("deptid", deptids[i]);
			// 根据部门ID获取员工
			sb.append(getdeptsonids(deptids[i], 1));
			if (i < deptids.length - 1)
				sb.append(",");
		}
		params.put("deptids", sb.toString());
		reportMapper.addReport(params);
		/*
		 * Date ptime = new Date(); params.put("ptime",new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ptime)); //将map转换为对象 Report
		 * report =
		 * JSONObject.parseObject(JSONObject.toJSONString(params),Report.class);
		 * report.setUserid(1); reportMapper.insertSelectiveReturnId(report);
		 */

		// 4.添加任务以及添加用户和任务的关系数据
		List<Report> listUser = reportMapper.findUsers(params);
		List<Map> listu = new ArrayList<Map>();
		List<String> noticeList = new ArrayList<String>();
		for (int i = 0; i < listUser.size(); i++) {
			Map<String, Object> mapsub = new HashMap<String, Object>();
			mapsub.put("reportid", params.get("id"));
			mapsub.put("userid", listUser.get(i).getId());
			if (params.get("noticeType") != null && "1".equals(params.get("noticeType"))) {
				noticeList.add(listUser.get(i).getTel());
			}
			Notice notice = new Notice();
			notice.setNoticeContent("新任务,任务名称[" + params.get("name") + "]");
			notice.setUserId("" + listUser.get(i).getId());
			reportMapper.addNotice(notice);
			listu.add(mapsub);
		}
		reportMapper.addReportUsers(listu);
		// 任务通知
//		try {
//			SendSms.send(noticeList.toString().replace("[", "").replace("]", ""), "尊敬的用户,您有新任务待查看,任务名称["+params.get("name")+"],请注意查看");
//		} catch (Exception e) {
//			logger.info("任务通知异常------------------->>>>");
//		}
	}

	public TableResultResponse<Report> getReports(Map params) {
		String deptid = params.get("deptid").toString();
		String[] deptids = deptid.split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < deptids.length; i++) {
			params.put("deptid", deptids[i]);
			// 根据部门ID获取员工
			sb.append(getdeptsonids(deptids[i], 1));
			if (i < deptids.length - 1)
				sb.append(",");
		}
		params.put("deptids", sb.toString());
		Query query = new Query(params);
		Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());

		List<Report> list = reportMapper.getReport(query);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setIndex(i + 1);
			list.get(i).setNumber(list.get(i).getNumber() * list.get(i).getNumber3());
		}
		return new TableResultResponse<Report>(result.getTotal(), list);
	}

	public TableResultResponse<Report> getReportUser(Map params) {
		Query query = new Query(params);
		Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());

		List<Report> list = reportMapper.getUserByReport(query);

		for (int i = 0; i < list.size(); i++)
			list.get(i).setIndex(i + 1);
		return new TableResultResponse<Report>(result.getTotal(), list);
	}

	private List<Report> getallusers(String deptids) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("deptids", deptids);
		List<Report> result = reportMapper.findUsers(map);
		return result;
	}

	private String getdeptsonids(String fid,int haveMyDept) {
		// TODO Auto-generated method stub
    	StringBuffer sb = new StringBuffer();
    	Map<String,String> map =new HashMap<String,String>();
    	map.put("fid", fid);
    	List<Departments> deptlist=nDeptMapper.getSonDeptidByDeptId(map);
    	if(haveMyDept==1)
    	{
		sb.append(fid);
    	}
    	if(deptlist!=null&&deptlist.size()>0)
    	{
    		if(haveMyDept==1)
    		{
    		sb.append(",");
    		}
    	for (int i=0 ; i<deptlist.size() ; i++) {
            //根据部门ID获取员工
            sb.append(getdeptsonids(""+deptlist.get(i).getId(),1));
            if(i<deptlist.size()-1) sb.append(",");
        	}
    	} 	
	return sb.toString();

	}

	public List<Tree> get(Map map){
        return nDeptMapper.getSonDeptByDeptId(map);
    }

	public List<Report> getAllReports(Map params){
        return reportMapper. getReportsByPage(params);

    }

	public int addReports(List<Report> reports){
        return reportMapper.addReports(reports);
    }

	public TableResultResponse<Report> getPictureAddress(Query query) {
		// TODO Auto-generated method stub
		List<Report> list = new ArrayList<Report>();
		String id = query.get("id").toString();
		File[] filelist = new File(FilePath + virtualPath + "/r_" + id + "/").listFiles();
		if (filelist != null) {
			for (int i = 0; i < filelist.length; i++) {
				Report a = new Report();
				a.setAddress("./" + virtualPath + "/r_" + id + "/" + filelist[i].getName());
				list.add(a);
			}
		}
		// 根据id拼写文件目录，获取图片数据写入report中,将report组成list
		return new TableResultResponse<Report>(list.size(), list);
	}

//    public TableResultResponse<Report>  getmytask(Query query){
//        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
//        List<Report> list =  reportMapper. getMyReprotType(query);
//        return new TableResultResponse<Report>(result.getTotal(), list);
//    }
	public Report reportDetail(Map<String, Object> params) {
		return reportMapper.reportDetail(params);
	}

	public List<Report> reportByDept(Map<String, Object> params) {
		List<Report> list = null;
		list = reportMapper.reportByDept(params);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setIndex(i + 1);
		}
		return list;
	}

	public List<Report> getUserByReport(Map<String, Object> params) {
		List<Report> list = null;
		list = reportMapper.getUserByReport(params);
		for (int i = 0; i < list.size(); i++)
			list.get(i).setIndex(i + 1);
		return list;
	}

	public List<Report> reportByJF(Map<String, Object> params) {
		List<Report> list = null;
		list = reportMapper.reportByJF(params);
		for (int i = 0; i < list.size(); i++)
			list.get(i).setIndex(i + 1);
		return list;
	}

	public List<Report> reportByLV(Map<String, Object> params) {
		List<Report> list = null;
		list = reportMapper.reportByLV(params);
		for (int i = 0; i < list.size(); i++)
			list.get(i).setIndex(i + 1);
		return list;
	}

//    public TableResultResponse<Report>  getmytask(Query query){
//        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
//        List<Report> list =  reportMapper. getMyReprotType(query);
//        return new TableResultResponse<Report>(result.getTotal(), list);
//    }

}
