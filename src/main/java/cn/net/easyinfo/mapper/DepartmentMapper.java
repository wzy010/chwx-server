package cn.net.easyinfo.mapper;

import cn.net.easyinfo.bean.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DepartmentMapper {
    void addDep(@Param("dep") Department department);

    void deleteDep(@Param("dep") Department department);

    List<Department> getDepByPid(Long pid);

    List<Department> getAllDeps();
}
