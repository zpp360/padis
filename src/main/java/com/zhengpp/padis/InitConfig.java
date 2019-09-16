package com.zhengpp.padis;

import com.zhengpp.padis.service.SysConfigService;
import com.zhengpp.padis.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 初始化所有配置放进内存，随spring启动加载
 */
@Component
public class InitConfig {
    private static Logger log = Logger.getLogger(InitConfig.class);

    @Autowired
    private SysConfigService sysConfigService;

    @PostConstruct
    public void init(){
        log.info("开始初始化变量");

        Constants.temperature_gap = 30 * 60;

        log.info("初始化变量完成");
    }

    @PreDestroy
    public void destroy(){
        log.info("销毁所有初始化的变量");
    }
}
