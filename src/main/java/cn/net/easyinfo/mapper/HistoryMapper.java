package cn.net.easyinfo.mapper;

import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.entity.History;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface HistoryMapper extends Mapper<History> {
    List<History> selectHistory(Query query);
}
