package cn.net.easyinfo.common.controller;


import cn.net.easyinfo.bean.Hr;
import cn.net.easyinfo.common.biz.BaseService;
import cn.net.easyinfo.common.context.BaseContextHandler;
import cn.net.easyinfo.common.msg.ObjectRestResponse;
import cn.net.easyinfo.common.msg.TableResultResponse;
import cn.net.easyinfo.common.util.Query;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Slf4j
public class BaseController<Biz extends BaseService, Entity> {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private Biz baseService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Entity> add(@RequestBody Entity entity) {
        log.debug("==================entity:"+entity.toString());
        baseService.insertSelective(entity);
        return new ObjectRestResponse<Entity>();
    }

    @RequestMapping(value = "/a{id}", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Entity> get(@PathVariable int id) {
        log.debug("============================this is test for get reuquest id="+id);
        ObjectRestResponse<Entity> entityObjectRestResponse = new ObjectRestResponse<>();
        Object o = baseService.selectById(id);
        entityObjectRestResponse.data((Entity) o);
        return entityObjectRestResponse;
    }

    @RequestMapping(value = "/a{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<Entity> update(@RequestBody Entity entity) {
        log.debug("============================this is update entity="+entity);
        baseService.updateSelectiveById(entity);
        return new ObjectRestResponse<Entity>();
    }

    @RequestMapping(value = "/a{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ObjectRestResponse<Entity> remove(@PathVariable int id) {
        baseService.deleteById(id);
        return new ObjectRestResponse<Entity>();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Entity> all() {
        return baseService.selectListAll();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<Entity> list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return baseService.selectByQuery(query);
    }

    public String getCurrentUserName() {
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        return securityContextImpl.getAuthentication().getName();
    }
    public long getUserID(){
        return getUser().getId();
    }
    public Hr getUser(){
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        return (Hr) securityContextImpl.getAuthentication().getPrincipal();
    }
    /**
     * 得到所有传递的参数以Map的形式保存
     * GET方法有效,主要是封装 start/limit
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getParameterMap() {
        Map<String, String[]> map = (Map<String, String[]>) getRequest().getParameterMap();
        Map<String, Object> result = new HashMap<String, Object>();
        Set<Map.Entry<String, String[]>> set = map.entrySet();
        StringBuffer params = new StringBuffer("params: ");
        for (Map.Entry<String, String[]> entry : set) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            String value = StringUtils.join(values, "-");
            result.put(key, value);
            params.append(key + "=" + value + " ");
        }
        int page = 1;
        try {
            page = Integer.parseInt((String)result.get("page"));
        } catch (NumberFormatException e) {}
        int limit = 10;
        try {
            limit = Integer.parseInt((String)result.get("limit"));
        } catch (NumberFormatException e) {}
        result.put("start", (page-1)*limit);
        result.put("limit", limit);
        return result;
    }
    public HttpServletRequest getRequest(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }
}
