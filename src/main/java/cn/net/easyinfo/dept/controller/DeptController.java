package cn.net.easyinfo.dept.controller;

import cn.net.easyinfo.common.controller.BaseController;
import cn.net.easyinfo.entity.Department;

import cn.net.easyinfo.dept.service.DeptService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: bobqiu
 * @create: 2018-09-11DeptService
 **/
@RestController
@RequestMapping("api/dept")
public class DeptController extends BaseController<DeptService,Department> {

}
