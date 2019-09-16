package com.zhengpp.padis.service;

import com.zhengpp.padis.dao.DaoSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zpp360 on 2019/9/8.
 */
@Service("sysConfigService")
public class SysConfigService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;
}
