package cn.net.easyinfo.service;

import cn.net.easyinfo.mapper.SystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class SystemService {
    @Autowired
    SystemMapper systemMapper;

}
