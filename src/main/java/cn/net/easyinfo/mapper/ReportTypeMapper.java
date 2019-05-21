package cn.net.easyinfo.mapper;

import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.entity.ReportType;
import cn.net.easyinfo.report.entity.Report;
import cn.net.easyinfo.entity.ReportTypeBig;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ReportTypeMapper extends Mapper<ReportType> {
	
	List<Report> getMyReprotType(Query query);

	List<Report> myReport(Query query);

	int delmyreport(@Param("ids") String[] split);

    List<ReportTypeBig> getBigType(Map<String,Object> param);
    List<Report> getReportTypeName();
    List<Report> getType(Map<String,Object> params);
}
