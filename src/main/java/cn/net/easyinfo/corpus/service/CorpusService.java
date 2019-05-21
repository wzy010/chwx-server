package cn.net.easyinfo.corpus.service;

import cn.net.easyinfo.common.biz.BaseService;
import cn.net.easyinfo.common.msg.TableResultResponse;
import cn.net.easyinfo.corpus.entity.Corpus;
import cn.net.easyinfo.corpus.entity.CorpusData;
import cn.net.easyinfo.mapper.CorpusMapper;
import cn.net.easyinfo.report.entity.Report;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("corpusService")
@Slf4j
public class CorpusService extends BaseService<CorpusMapper,Corpus> {
    @Autowired
    CorpusMapper CorpusMapper;

    public List<Corpus> getCorpusByFid(Map<String,Object> params){
        List<Corpus> list = CorpusMapper.getCorpusByFid(params);
        return list;
    }
    public int insertCorpus(Corpus corpus){
        return CorpusMapper.insertCorpus(corpus);
    }
    public int delCorpus(int id){
        return CorpusMapper.delCorpus(id);
    }
    public int getCorpusDataByCidCount(Map<String,Object> params){
        return CorpusMapper.getCorpusDataByCidCount(params);
    }
    public List<CorpusData> getCorpusDataByCid(Map<String,Object> params){
        List<CorpusData> list = CorpusMapper.getCorpusDataByCid(params);
        for(int i=0;i<list.size();i++){
            list.get(i).setIndex(i+1);
        }
        return list;
    }
    public int delCorpusDataByIds(String ids){
        return CorpusMapper.delCorpusDataByIds(ids);
    }
    public int addCorpusData(Map<String,Object> params){
        List<Map<String,Object>> list = new ArrayList<>();
        String[] data = params.get("data").toString().split("\n");
        for(String str:data){
            System.out.println(str);
            if(str.trim().equals(""))continue;
            Map<String,Object> d = new HashMap<>();
            d.put("cid",params.get("cid"));
            d.put("content",str);
            list.add(d);
        }
        return CorpusMapper.addCorpusData(list);
    }

    public int updateCorpusData(CorpusData params){
        return CorpusMapper.updateCorpusData(params);
    }

}
