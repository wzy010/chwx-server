package cn.net.easyinfo.corpus.controller;

import cn.net.easyinfo.common.controller.BaseController;
import cn.net.easyinfo.common.msg.ObjectRestResponse;
import cn.net.easyinfo.common.msg.TableResultResponse;
import cn.net.easyinfo.common.util.CommonUtil;
import cn.net.easyinfo.corpus.entity.Corpus;
import cn.net.easyinfo.corpus.entity.CorpusData;
import cn.net.easyinfo.corpus.service.CorpusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/corpus")
@Slf4j
public class CorpusController extends BaseController<CorpusService,Corpus> {
    @Resource(name = "corpusService")
    CorpusService corpusService;

    /**
     * 根据父语料集id获得子语料集
     * @param params
     * @return
     */
    @RequestMapping(value="/getCorpusByFid",method = RequestMethod.GET)
    public List<Corpus> getCorpusByFid(@RequestParam Map<String, Object> params) {
        System.out.println("-----------------");
        params.put("uid",this.getUserID());
        System.out.println(params.toString());
        return corpusService.getCorpusByFid(params);
    }
    /**
     * 获得所有语料集
     */
    @RequestMapping(value="/getAllByUid",method = RequestMethod.GET)
    public List<Corpus> getAllByUid(){
        Corpus params = new Corpus();
        params.setUid(getUserID());
        return corpusService.selectList(params);
    }

    @RequestMapping(value="/insertCorpus",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Corpus> insertCorpus(@RequestBody Corpus corpus){
        corpus.setUid(getUserID());
        corpusService.insertCorpus(corpus);
        ObjectRestResponse<Corpus> entityObjectRestResponse = new ObjectRestResponse<>();
        entityObjectRestResponse.setData(corpus);
        return entityObjectRestResponse;
    }
    @RequestMapping(value="/delCorpus/a{id}",method = RequestMethod.DELETE)
    public ObjectRestResponse<Corpus> delCorpus(@PathVariable int id){
        corpusService.delCorpus(id);
        ObjectRestResponse<Corpus> entityObjectRestResponse = new ObjectRestResponse<>();
        return entityObjectRestResponse;
    }

    /**
     * 根据语料集id获得语料
     * @return
     */
    @RequestMapping(value="/getCorpusDataByCid",method = RequestMethod.GET)
    public TableResultResponse<CorpusData> getCorpusDataByCid(){
        int total = corpusService.getCorpusDataByCidCount(getParameterMap());
        List<CorpusData> list = corpusService.getCorpusDataByCid(getParameterMap());
        return new TableResultResponse(total,list);
    }

    @RequestMapping(value="/delCorpusData/a{ids}",method = RequestMethod.DELETE)
    public ObjectRestResponse<Corpus> delCorpusDataByIds(@PathVariable String ids){
        corpusService.delCorpusDataByIds(ids);
        ObjectRestResponse<Corpus> entityObjectRestResponse = new ObjectRestResponse<>();
        return entityObjectRestResponse;
    }

    /**
     * 添加语料
     * @return
     */
    @RequestMapping(value="/addCorpusData",method = RequestMethod.POST)
    public ObjectRestResponse<Corpus> addCorpusData(@RequestBody Map<String, Object> params){
        corpusService.addCorpusData(params);
        return new ObjectRestResponse<Corpus>();
    }
    /**
     * 修改语料
     * @return
     */
    @RequestMapping(value="/updateCorpusData/a{id}",method = RequestMethod.PUT)
    public ObjectRestResponse<CorpusData> updateCorpusData(@PathVariable int id,@RequestBody CorpusData params){
        params.setId(id);
        corpusService.updateCorpusData(params);
        return new ObjectRestResponse<CorpusData>();
    }
}
