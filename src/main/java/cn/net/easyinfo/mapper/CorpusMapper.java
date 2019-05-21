package cn.net.easyinfo.mapper;

import cn.net.easyinfo.corpus.entity.Corpus;
import cn.net.easyinfo.corpus.entity.CorpusData;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;
import java.util.Map;

public interface CorpusMapper extends Mapper<Corpus> {

    List<Corpus> getCorpusByFid(Map<String,Object> params);

    int insertCorpus(Corpus corpus);

    int delCorpus(@Param("id") int id);
    int getCorpusDataByCidCount(Map<String,Object> params);

    List<CorpusData> getCorpusDataByCid(Map<String,Object> params);

    int delCorpusDataByIds(@Param("ids") String ids);

    int addCorpusData(List<Map<String,Object>> params);
    int updateCorpusData(CorpusData params);
}
