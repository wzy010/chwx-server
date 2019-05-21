package cn.net.easyinfo.mapper;

import cn.net.easyinfo.bean.Menu;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface MenuMapper  extends Mapper<Menu> {
    List<Menu> getAllMenu();

    List<Menu> getMenusByHrId(Long hrId);

    List<Menu> menuTree();
    List<Menu> getMenuTreeByPid(Long pid);
    List<Long> getMenusByRid(Long rid);
}
