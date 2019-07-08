package com.zhengpp.padis.utils;

import com.zhengpp.padis.entity.PageData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  layui表格返回值封装
 * Created by zpp360 on 2019/7/8.
 */
public class ResponseData {
    public ResponseData() {
        super();
    }

    public ResponseData(Long count, List<PageData> list) {
        this.count = count;
        this.data = list;
    }

    private String code = "0";

    private String msg = "success";

    private Long count;

    private List<PageData> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<PageData> getData() {
        return data;
    }

    public void setData(List<PageData> data) {
        this.data = data;
    }
}
