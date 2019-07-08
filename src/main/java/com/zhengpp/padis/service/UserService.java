package com.zhengpp.padis.service;

import com.zhengpp.padis.dao.DaoSupport;
import com.zhengpp.padis.entity.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;


    public PageData findByUserName(String userName) throws Exception {
        return (PageData) dao.findForObject("UserMapper.findByUserName",userName);
    }

    /**
     * 根据Id查询
     * @param userId
     * @return
     */
    public PageData findById(String userId) throws Exception {
        return (PageData) dao.findForObject("UserMapper.findById",userId);
    }

    /**
     * 更新密码
     * @param pd
     */
    public void updatePassword(PageData pd) throws Exception {
        dao.update("UserMapper.updatePassword",pd);
    }
}
