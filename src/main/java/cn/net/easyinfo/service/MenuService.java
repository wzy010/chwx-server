package cn.net.easyinfo.service;

import cn.net.easyinfo.common.HrUtils;
import cn.net.easyinfo.bean.Menu;
import cn.net.easyinfo.common.biz.BaseService;
import cn.net.easyinfo.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class MenuService extends BaseService<MenuMapper,Menu> {
    @Autowired
    MenuMapper menuMapper;

    public List<Menu> getAllMenu(){
        return menuMapper.getAllMenu();
    }

    public List<Menu> getMenusByHrId() {
        return menuMapper.getMenusByHrId(HrUtils.getCurrentHr().getId());
    }

    public List<Menu> menuTree() {
        return menuMapper.menuTree();
    }

    public List<Menu> getMenuTreeByPid(Long id) {
        return menuMapper.getMenuTreeByPid(id);
    }

    public List<Long> getMenusByRid(Long rid) {
        return menuMapper.getMenusByRid(rid);
    }
}
