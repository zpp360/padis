package com.zhengpp.padis.service;

import com.zhengpp.padis.dao.DaoSupport;
import com.zhengpp.padis.entity.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zpp360 on 2019/7/7.
 */
@Service("infoService")
public class ApplyService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 列表分页
     * @param pd
     * @return
     */
    public List<PageData> listPage(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ApplyMapper.listPage",pd);
    }

    /**
     * 分页列表数量
     * @param pd
     * @return
     */
    public Long countListPage(PageData pd) throws Exception {
        return (Long) dao.findForObject("ApplyMapper.countListPage",pd);
    }

    /**
     * 保存提取
     * @param pd
     */
    public void saveApply(PageData pd) throws Exception {
        dao.save("ApplyMapper.saveApply",pd);
    }

    /**
     *  任务处理
     * @param pd
     */
    public void dealApply(PageData pd) throws Exception {
        dao.update("ApplyMapper.dealApply",pd);
    }

    /**
     * 删除提取
     * @param pd
     */
    public void delApply(PageData pd) throws Exception {
        dao.update("ApplyMapper.delApply",pd);
    }
}
