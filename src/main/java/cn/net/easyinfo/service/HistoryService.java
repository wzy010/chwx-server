package cn.net.easyinfo.service;

import cn.net.easyinfo.common.biz.BaseService;
import cn.net.easyinfo.common.msg.TableResultResponse;
import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.entity.Departments;
import cn.net.easyinfo.entity.History;
import cn.net.easyinfo.mapper.HistoryMapper;
import cn.net.easyinfo.mapper.NDeptMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HistoryService extends BaseService<HistoryMapper,History> {

    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private NDeptMapper nDeptMapper;


    public TableResultResponse<History> select(Map<String,Object> params){
        String deptid =  params.get("deptid").toString();
        String[] deptids = deptid.split(",");
        StringBuffer sb = new StringBuffer();
        for (int i=0 ; i<deptids.length ; i++) {
            params.put("deptid",deptids[i]);
            //根据部门ID获取员工
            sb.append(getdeptsonids(deptids[i],1));
            if(i<deptids.length-1) sb.append(",");
        }
        params.put("deptids",sb.toString());
        Query query = new Query(params);
        Page result = PageHelper.startPage(query.getPage(),query.getLimit());
        List<History> list = historyMapper.selectHistory(query);
        for(int i=0;i<list.size();i++) {
            History his = list.get(i);
            his.setIndex(i + 1);
            if (his.getResult() == 1) {
                his.setResultStr("成功");
            }
            if (his.getResult() == 0) {
                his.setResultStr("失败");
            }
        }
        return new TableResultResponse<History>(result.getTotal(),list);
    }

    private String getdeptsonids(String fid,int haveMyDept) {
        StringBuffer sb = new StringBuffer();
        Map<String,String> map =new HashMap<String,String>();
        map.put("fid", fid);
        List<Departments> deptlist=nDeptMapper.getSonDeptidByDeptId(map);
        if (haveMyDept == 1){
            sb.append(fid);
        }
        if(deptlist!=null&&deptlist.size()>0)
        {
            if(haveMyDept == 1){
                sb.append(",");
            }
            for (int i=0 ; i<deptlist.size() ; i++) {
                //根据部门ID获取员工
                sb.append(getdeptsonids(""+deptlist.get(i).getId(),haveMyDept));
                if(i<deptlist.size()-1) sb.append(","); }
        }
        return sb.toString();
    }
}
