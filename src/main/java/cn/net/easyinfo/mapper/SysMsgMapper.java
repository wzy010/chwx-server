package cn.net.easyinfo.mapper;

import cn.net.easyinfo.bean.Hr;
import cn.net.easyinfo.bean.MsgContent;
import cn.net.easyinfo.bean.SysMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysMsgMapper {

    int sendMsg(MsgContent msg);

    int addMsg2AllHr(@Param("hrs") List<Hr> hrs, @Param("mid") Long mid);

    List<SysMsg> getSysMsg(@Param("start") int start, @Param("size") Integer size, @Param("hrid") Long hrid);

    int markRead(@Param("flag") Long flag, @Param("hrid") Long hrid);
}
