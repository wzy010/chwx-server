package cn.net.easyinfo.mapper;

import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.common.vo.UserVo;
import cn.net.easyinfo.entity.*;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface UserMapper extends Mapper<UserVo> {

    List<UserVo> getAllUsers();

    Integer getUnitsubAllUserNumber(Map params);

    int isManager(int roleid);

    List<LoginUser> getUnitUser(Query query);

    String getDepartNames(Map map);

    int modifyUserSubPwd(Map map);

    List<Nation> getNation();

    List<Political> getPolitical();

    List<UserType> getUserType();

    List<Degree> getDegree();

    LoginUser selectDetailById(int id);

    List<LoginUser> vaildateUpdateUser(Map map);

    int updateUserById(Map map);

    List<LoginUser> selLikeRealByName(Map map);

    int addUser(Map map);

    int deleteUserById(@Param("ids")String... ids);

    int updateDepteById(@Param("deptid") String deptid, @Param("ids") String... ids);
}
