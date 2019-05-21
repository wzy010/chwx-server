package cn.net.easyinfo.menu.controller;

import cn.net.easyinfo.common.controller.BaseController;
import cn.net.easyinfo.entity.Menu;
import cn.net.easyinfo.menu.service.NmenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: bobqiu
 * @create: 2018-09-13
 **/
@RestController
@RequestMapping("api/menu")
public class MenuController extends BaseController<NmenuService,Menu> {

    @Autowired
    NmenuService nmenuService;
    @RequestMapping(value = "/menuTree/{rid}", method = RequestMethod.GET)
    public List<Menu> menuTree(@PathVariable Long rid) {
       /* Map<String, Object> map = new HashMap<>();
        List<Menu> menus = menuService.menuTree();
        map.put("menus", menus);
        List<Long> selMids = menuService.getMenusByRid(rid);
        map.put("mids", selMids);
        return map;*/
        List<Menu> menuTreeByPid = nmenuService.getMenuTreeByPid(rid);
        return menuTreeByPid;
    }
}
