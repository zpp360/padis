package com.zhengpp.padis.controller;

import com.zhengpp.padis.base.BaseController;
import com.zhengpp.padis.entity.PageData;
import com.zhengpp.padis.entity.User;
import com.zhengpp.padis.service.ApplyService;
import com.zhengpp.padis.utils.ResponseData;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by zpp360 on 2019/7/7.
 */
@Controller
@RequestMapping("/apply")
public class ApplyController extends BaseController{

    @Autowired
    private ApplyService applyService;

    @RequestMapping(value = "/applyList")
    public ModelAndView applyList(ModelAndView mv){
        mv.setViewName("/apply/applyList");
        return mv;
    }

    /**
     * 表格数据
     * @return
     */
    @RequestMapping("/applyListData")
    @ResponseBody
    public ResponseData applyListData() throws Exception {
        PageData pd = this.getPageData();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if("1".equals(user.getUserRoele())){
            //公安列表，只查看自己发布的
            pd.put("insert_user",user.getUserId());
        }
        if("2".equals(user.getUserRoele())){
            //移动，查看所有数据

        }
        List<PageData> list = applyService.listPage(pd);
        Long count = applyService.countListPage(pd);
        ResponseData data = new ResponseData(count,list);
        return data;
    }
}
