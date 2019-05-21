package cn.net.easyinfo.report.service;

import cn.net.easyinfo.common.biz.BaseService;
import cn.net.easyinfo.common.msg.TableResultResponse;
import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.entity.Departments;
import cn.net.easyinfo.common.vo.SysResult;
import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.entity.Departments;
import cn.net.easyinfo.entity.ReportType;
import cn.net.easyinfo.mapper.NDeptMapper;
import cn.net.easyinfo.mapper.ReportTypeMapper;
import cn.net.easyinfo.report.entity.Report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import cn.net.easyinfo.entity.ReportTypeBig;
import cn.net.easyinfo.mapper.ReportTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import cn.net.easyinfo.entity.ReportTypeBig;

@Service
public class ReportTypeService extends BaseService<ReportTypeMapper, ReportType> {
	@Autowired
	private ReportTypeMapper reportTypeMapper;
	@Autowired
	private NDeptMapper nDeptMapper;
	
	public TableResultResponse<Report> getmissiontype(Query query) {
//		query.put("deptids", getdeptsonids(query.get("deptid").toString(), 1));
		Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
		List<Report> list = reportTypeMapper.getMyReprotType(query);
		return new TableResultResponse<Report>(result.getTotal(), list);
	}

	public TableResultResponse<Report> getmytask(Query query) {
		query.put("deptids", getdeptsonids(query.get("deptid").toString(), 1));
		Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
		List<Report> list = reportTypeMapper.myReport(query);
		return new TableResultResponse<Report>(result.getTotal(), list);
	}

	private String getdeptsonids(String fid, int haveMyDept) {
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
	}

	public boolean delmyreport(String ids) {
		// TODO Auto-generated method stub
		 String[] split = ids.split(",");
	     return reportTypeMapper.delmyreport(split) == split.length;
	}



    public List<ReportTypeBig> getBigType(Map<String,Object> param){
        return reportTypeMapper.getBigType(param);
    }

    public SysResult getReportTypeName(){
        List<Report> list = reportTypeMapper.getReportTypeName();

        return  new SysResult(list);
    }

    public TableResultResponse<Report> getType(Map<String,Object> params){

      List<Report> list = reportTypeMapper.getType(params);
      return new TableResultResponse<Report>(0,list);
    }

}
