package cn.net.easyinfo.mapper;

import cn.net.easyinfo.bean.Position;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface PositionMapper {

    int addPos(@Param("pos") Position pos);

    Position getPosByName(String name);

    List<Position> getAllPos();

    int deletePosById(@Param("pids") String[] pids);

    int updatePosById(@Param("pos") Position position);
}
