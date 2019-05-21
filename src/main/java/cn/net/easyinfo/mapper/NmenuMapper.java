package cn.net.easyinfo.mapper;

/**
 * @description:
 * @author: bobqiu
 * @create: 2018-09-13
 **/
import cn.net.easyinfo.entity.Menu;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface NmenuMapper  extends Mapper<Menu> {
    List<Menu> getAllMenu();

    List<Menu> getMenusByHrId(Long hrId);

    List<Menu> menuTree();
    List<Menu> getMenuTreeByPid(Long pid);
    List<Long> getMenusByRid(Long rid);
}
