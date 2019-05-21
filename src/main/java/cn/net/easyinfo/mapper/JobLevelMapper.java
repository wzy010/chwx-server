package cn.net.easyinfo.mapper;

import org.apache.ibatis.annotations.Param;
import cn.net.easyinfo.bean.JobLevel;

import java.util.List;


public interface JobLevelMapper {
    JobLevel getJobLevelByName(String name);

    int addJobLevel(@Param("jobLevel") JobLevel jobLevel);

    List<JobLevel> getAllJobLevels();

    int deleteJobLevelById(@Param("ids") String[] ids);

    int updateJobLevel(@Param("jobLevel") JobLevel jobLevel);
}
