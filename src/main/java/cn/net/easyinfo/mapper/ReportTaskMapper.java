package cn.net.easyinfo.mapper;

import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.report.entity.Department;
import cn.net.easyinfo.report.entity.Report;
import cn.net.easyinfo.entity.ReportUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ReportTaskMapper extends Mapper<ReportUser> {

    void addReportTask(Map params);
    int updateNum(Map params);
    void updateReportRead(Map params);
    List<Report> reportAudit(Query query);
    Report byReportBysubId(Map params);
    Integer bySumScoreByuser(Map params);
    Integer updateAudit(Map params);
    Integer getLastReportId();

    List<Department> getDeptByFid(Query query);
    Report byDeptReport(Query query);
    List<Report> taskReportScoreByuser(Query query);
    List<Report> taskReportDetailByUser(Query query);


}
