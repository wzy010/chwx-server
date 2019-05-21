package cn.net.easyinfo.mapper;

import cn.net.easyinfo.entity.RightManager;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface RigthManagerMapper extends Mapper<RightManager> {
    List<RightManager> getRole(Map map);
    List<RightManager> getAllRole();
}
