package cn.net.easyinfo.mapper;

import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.entity.Notice;
import cn.net.easyinfo.report.entity.Report;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ReportMapper extends Mapper<Report> {

    Integer addReport(Map map);

    List<Report> queryOrderByPage(Query query);

    List<Report> getPublish(Query query);

    List<Report> getReceiveTask(Query query);

    void updateReportRead(Query query);

    List<Report> getReportsByPage(Map map);

    List<Report> findUsers(Map map);

    Integer insertSelectiveReturnId(Report report);

    int addReports(@Param("reports") List<Report> reports);

	void addNotice(Notice notice);

	void addReportUsers(List<Map> listu);

	List<Report> getMyReprotType(Query query);

    List<Report> getReport(Map map);

    List<Report> getUserByReport(Map map);

    Report reportDetail(Map map);

    List<Report> reportByDept(Map map);

    List<Report> reportByJF(Map map);

    List<Report> reportByLV(Map map);
}