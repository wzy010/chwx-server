package cn.net.easyinfo.report.service;

import cn.net.easyinfo.common.biz.BaseService;
import cn.net.easyinfo.common.msg.TableResultResponse;
import cn.net.easyinfo.common.util.MD5;
import cn.net.easyinfo.common.util.Query;
import cn.net.easyinfo.common.vo.SysResult;
import cn.net.easyinfo.common.vo.UserVo;
import cn.net.easyinfo.entity.*;
import cn.net.easyinfo.mapper.NDeptMapper;
import cn.net.easyinfo.mapper.UserMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends BaseService<UserMapper,UserVo> {
    @Autowired
    private  UserMapper userMapper;
    @Autowired
    private NDeptMapper nDeptMapper;

    public List<UserVo> getAllUsers(){
        return userMapper.getAllUsers();
    }

    public SysResult getUnitsubAllUserNumber(Map<String,Object> params){
        try {
            String deptid = params.get("deptid").toString();
            String[] deptids = deptid.split(",");
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < deptids.length; i++) {
                params.put("deptid", deptids[i]);
                //根据部门ID获取员工
                sb.append(getdeptsonids(deptids[i], 1));
                if (i < deptids.length - 1) sb.append(",");
            }
            params.put("deptids", sb.toString());
            int number = userMapper.getUnitsubAllUserNumber(params);
            return new SysResult(200,"OK",number);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  SysResult.oK(201,"ERROR");
    }

    public int modifyUserSubPwd(Map<String,Object> params){
        params.put("pwd", MD5.getMD5(String.valueOf(params.get("pass"))));
        int n = userMapper.modifyUserSubPwd(params);
        return n;
    }

    public SysResult getNation(){
        List<Nation> list = userMapper.getNation();
        return new SysResult(list);
    }

    public List<Political> getPolitical(){
       return userMapper.getPolitical();
    }

    public List<UserType> getUserType(){
        return userMapper.getUserType();
    }

    public List<Degree> getDegree(){
        return userMapper.getDegree();
    }

    public List<LoginUser> vaildateUpdateUser(Map<String,Object> params){
        return userMapper.vaildateUpdateUser(params);
    }

    public List<LoginUser> selLikeRealByName(Map<String,Object> params){
       return userMapper.selLikeRealByName(params);
    }

    public int addUser(Map<String,Object> params){
        return userMapper.addUser(params);
    }

    public int updateUserById(Map<String,Object> params){
        return userMapper.updateUserById(params);
    }

    public LoginUser getDetail(Integer id){
        LoginUser loginUser = null;
        loginUser = userMapper.selectDetailById(id);
            int sex = loginUser.getSex();
            if (sex == Sex.MALE.getId()) {
                loginUser.setSexValue(Sex.MALE.getValue());
            } else {
                loginUser.setSexValue(Sex.FEMALE.getValue());
            }
        return loginUser;
    }

    public boolean deleteUserById(String ids){
        String[] split = ids.split(",");
        return userMapper.deleteUserById(split) == split.length;
    }

    public boolean updateDept(Map<String,Object> params){
        String ids = params.get("ids").toString();
        String[] split = ids.split(",");
        //params.put("ids",split);
        String deptid = params.get("deptid").toString();
        return userMapper.updateDepteById(deptid,split) == split.length;
    }

    public TableResultResponse<LoginUser> getUnitUser(Map<String,Object> params){
        try{
        String node = params.get("node").toString();
        String username = params.get("username").toString();
        String realName = params.get("realName").toString();
        String tel = params.get("tel").toString();

        int isS = 0;
        if (username != null && !username.trim().equals("")) isS=1;
        if (realName != null && !realName.trim().equals("")) isS=1;
        if (tel != null && !tel.trim().equals("")) isS=1;
        //获取当前用户
        //LoginUser user = CommonUtil.getUser();
        params.put("isS",isS);
        String isSearch = params.get("search").toString();
        if(isSearch!=null && isSearch.equals("1")){
            Map<String,Object> params2 = new HashMap<String,Object>();
            String[] deptids = node.split(",");
            StringBuffer sb = new StringBuffer();
            for (int i=0;i<deptids.length;i++){
                params2.put("deptid", deptids[i]);
                //根据部门ID获取员工
                sb.append(getdeptsonids(deptids[i], 1));
                if (i < deptids.length - 1) sb.append(",");
            }
            node = sb.toString();
        }else{
            if(node != null){
                if(node.equals("-1"))
                    //node = user.getDeptid()+"";
                    node = "305";
            }
        }
        params.put("node", node);
        params.put("deptid", node);
        params.put("deptids", node);
        params.put("username", username);
        params.put("realName", realName);
        params.put("tel", tel);
        //获取当前用户的角色ID
        //int roleid = user.getRoleid();
        int roleid = 1;
        List<LoginUser> list = null;
        Page<Object> result = null;
        if(isManage(roleid)) {
            Query query = new Query(params);
            result = PageHelper.startPage(query.getPage(), query.getLimit());
            list = userMapper.getUnitUser(query);
        }
        Integer isS1 = (Integer) params.get("isS");

        StringBuffer sb = new StringBuffer();
        for(int i=0;i<list.size();i++){
            list.get(i).setIndex(i+1);
           if(isS==1 && list.get(i).getDeptid()!=1) {//查询递归部门
                params.put("deptid", list.get(i).getDeptid());
               String ids = nDeptMapper.queryFatherDepartInfo(params);
               params.put("ids", ids);
               String names = userMapper.getDepartNames(params);
               list.get(i).setDeptName(names);
           }
        }
        return new TableResultResponse<LoginUser>(result.getTotal(),list);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    private boolean isManage(int roleid){
        boolean flag =false ;
        try {
            int n = userMapper.isManager(roleid);
            if(n == 1){
                flag = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    private String getDeptFids(String id){
        StringBuffer sb = new StringBuffer();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",id);
        Departments dept = nDeptMapper.getDeptFidById(params);
        sb.append(id).append(",").append(dept.getFid());
        while(id.equals(0)){

        }
        return  sb.toString();
    }

    private String getdeptsonids(String fid,int haveMyDept) {

        StringBuffer sb = new StringBuffer();
        Map<String,String> map =new HashMap<String,String>();
        map.put("fid", fid);
        List<Departments> deptlist=nDeptMapper.getSonDeptidByDeptId(map);

        if(haveMyDept==1)
        {
            sb.append(fid);
        }
        if(deptlist!=null&&deptlist.size()>0)
        {
            if(haveMyDept==1)
            {
                sb.append(",");
            }
            for (int i=0 ; i<deptlist.size() ; i++) {
                //根据部门ID获取员工
                sb.append(getdeptsonids(""+deptlist.get(i).getId(),1));
                if(i<deptlist.size()-1) sb.append(",");
            }
        }

        return sb.toString();
    }
}
