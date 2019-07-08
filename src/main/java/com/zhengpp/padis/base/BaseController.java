package com.zhengpp.padis.base;

import com.zhengpp.padis.controller.ApplyController;
import com.zhengpp.padis.entity.PageData;
import com.zhengpp.padis.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zpp360 on 2019/7/8.
 */
public class BaseController {
    protected  Logger log = Logger.getLogger(this.getClass());
    /**
     * 得到PageData
     *
     * @return
     */
    public PageData getPageData() {
        return new PageData(this.getRequest());
    }

    /**
     * 获取分页信息
     * @param pd
     * @return
     */
    public PageData getPageInfo(PageData pd){
        String pageNumber = pd.getString("page_number");
        String pageSize = pd.getString("page_size");
        if(StringUtils.isBlank(pageSize)){
            pageSize = "15";
        }
        int start = 0;
        if (StringUtils.isNotBlank(pageNumber)) {
            int pageNum = Integer.parseInt(pageNumber);
            if(pageNum>0){
                start = (pageNum-1)* Integer.parseInt(pageSize);
            }
        }
        pd.put("start", start);
        pd.put("page_size", Integer.parseInt(pageSize));
        return pd;
    }

    /**
     * 得到32位的uuid
     *
     * @return
     */
    public String get32UUID() {
        return UUIDUtil.uuid();
    }

    /**
     * 得到request对象
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        return request;
    }

}
