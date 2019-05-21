package cn.net.easyinfo.mapper;

import cn.net.easyinfo.entity.Departments;
import cn.net.easyinfo.entity.Tree;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 部门
 */
public interface NDeptMapper extends Mapper<Departments> {

    List<Departments> selectDeptById(@Param("id") Integer id);

    List<Tree> getSonDeptByDeptId(Map map);

	List<Departments> getSonDeptidByDeptId(Map map);

    Departments getDeptFidById(Map map);

    String queryFatherDepartInfo(Map map);

    Integer addDept(Map map);

    List<Departments> selectDeptsById(Integer id);

    List<Departments> selectByDeptName(Map map);

    Integer selMaxSortByFid(Map map);

    Departments getRootdept(Map map);

    Departments getDeep(Map map);

    Integer addDepart(Departments department);

    Integer updateDept(Map map);

    Integer deleteDepart(Map map);

    String queryChildrenDepartInfo(@Param("id") Integer id);

}
