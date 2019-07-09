package com.zhengpp.padis.controller;

import com.alibaba.fastjson.JSON;
import com.zhengpp.padis.base.BaseController;
import com.zhengpp.padis.entity.PageData;
import com.zhengpp.padis.entity.User;
import com.zhengpp.padis.service.ApplyService;
import com.zhengpp.padis.utils.Const;
import com.zhengpp.padis.utils.ResponseData;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        mv.addObject("user",user);
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
        if("1".equals(user.getUserRole())){
            //公安列表，只查看自己发布的
            pd.put("user_role","1");
            pd.put("insert_user",user.getUserId());
        }
        if("2".equals(user.getUserRole())){
            //移动，查看所有数据,不看apply_status为1的数据
            pd.put("user_role","2");
        }
        List<PageData> list = applyService.listPage(pd);
        Long count = applyService.countListPage(pd);
        ResponseData data = new ResponseData(count,list);
        return data;
    }

    /**
     * 新增提取页面
     * @param mv
     * @return
     */
    @RequestMapping("/toAddApply")
    @ResponseBody
    public ModelAndView toAddApply(ModelAndView mv){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        mv.addObject("user",user);
        mv.setViewName("/apply/applyAdd");
        return mv;
    }

    /**
     * 保存提取
     * @param mv
     * @return
     */
    @RequestMapping(value = "/saveApply")
    public ModelAndView saveApply(ModelAndView mv) throws Exception {
        PageData pd = this.getPageData();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        pd.put("apply_id",this.get32UUID());
        pd.put("insert_user",user.getUserId());
        pd.put("update_user",user.getUserId());
        applyService.saveApply(pd);
        mv.setViewName("/saveResult");
        return mv;
    }

    /**
     * 任务处理页面
     * @param mv
     * @return
     */
    @RequestMapping(value = "/toDealApply")
    public ModelAndView toDealApply(ModelAndView mv){
        PageData pd = this.getPageData();
        mv.addObject("pd",pd);
        mv.setViewName("/apply/applyDeal");
        return mv;
    }

    /**
     * 更新任务处理
     * @param mv
     * @return
     */
    @RequestMapping(value = "/dealApply")
    public ModelAndView dealApply(ModelAndView mv) throws Exception {
        PageData pd = this.getPageData();
        applyService.dealApply(pd);
        mv.setViewName("/saveResult");
        return mv;
    }

    /**
     * 上传电子公函
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/uploadCertify")
    @ResponseBody
    public ResponseData uploadCertify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseData data = new ResponseData();
        // 图片路径
        String imgPath = "";
        // 图片名称
        String orgFileName = null;
        // 图片后缀
        String fileExt = null;
        // 文件格式错误信息
        String fileExtError = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 上传文件
            MultipartFile mf = entity.getValue();
            // 获取图片大小
            int picSize = Integer.parseInt(String.valueOf(mf.getSize()));
            // 获取原文件名
            orgFileName = mf.getOriginalFilename();
            String fileName =  mf.getOriginalFilename();
            // 获取图片后缀
            fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if (!Const.XLSX.equals(fileExt) && !Const.XLS.equals(fileExt) && !Const.DOCX.equals(fileExt)
                    && !Const.DOC.equals(fileExt) && !Const.JPG.equals(fileExt) && !Const.PNG.equals(fileExt)
                    && !Const.JPEG.equals(fileExt) && !Const.GIF.equals(fileExt)) {
                fileExtError = "nonsupport_type";
                data.setCode("1");
                data.setMsg(fileExtError);
                return data;
            } else if (picSize > Const.ONE_HUNDRED_MB) {
                fileExtError = "out_size";
                data.setCode("1");
                data.setMsg(fileExtError);
                return data;
            } else {
                // 对原文件名进行重命名
                fileName = this.get32UUID() + "." + fileExt;
                // 返回图片路径
                String rootPath = request.getServletContext().getRealPath("/");
                imgPath = Const.APPLY_CERTIFY_PATH + fileName;
                File dic = new File(rootPath + Const.APPLY_CERTIFY_PATH);
                if(!dic.exists()){
                    dic.mkdirs();
                }
                //上传至服务器
                String realPath = rootPath + imgPath;
                mf.transferTo(new File(realPath));
                data.getMap().put("certify_file_name",orgFileName);
                data.getMap().put("certify_file_path",imgPath);
                return data;
            }
        }
        return data;
    }
    /**
     * 上传描述文件
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/uploadDesc")
    @ResponseBody
    public ResponseData uploadDesc(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseData data = new ResponseData();
        // 图片路径
        String imgPath = "";
        // 图片名称
        String orgFileName = null;
        // 图片后缀
        String fileExt = null;
        // 文件格式错误信息
        String fileExtError = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 上传文件
            MultipartFile mf = entity.getValue();
            // 获取图片大小
            int picSize = Integer.parseInt(String.valueOf(mf.getSize()));
            // 获取原文件名
            orgFileName = mf.getOriginalFilename();
            String fileName =  mf.getOriginalFilename();
            // 获取图片后缀
            fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if (!Const.XLSX.equals(fileExt) && !Const.XLS.equals(fileExt)) {
                fileExtError = "nonsupport_type";
                data.setCode("1");
                data.setMsg(fileExtError);
                return data;
            } else if (picSize > Const.ONE_HUNDRED_MB) {
                fileExtError = "out_size";
                data.setCode("1");
                data.setMsg(fileExtError);
                return data;
            } else {
                // 对原文件名进行重命名
                fileName = this.get32UUID() + "." + fileExt;
                // 返回图片路径
                String rootPath = request.getServletContext().getRealPath("/");
                imgPath = Const.APPLY_DESC_PATH + fileName;
                File dic = new File(rootPath + Const.APPLY_DESC_PATH);
                if(!dic.exists()){
                    dic.mkdirs();
                }
                //上传至服务器
                String realPath = rootPath + imgPath;
                mf.transferTo(new File(realPath));

                data.getMap().put("apply_file_name",orgFileName);
                data.getMap().put("apply_file_path",imgPath);
                return data;
            }
        }
        return data;
    }


    /**
     * 上传描述文件
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/uploadDeal")
    @ResponseBody
    public ResponseData uploadDeal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseData data = new ResponseData();
        // 图片路径
        String imgPath = "";
        // 图片名称
        String orgFileName = null;
        // 图片后缀
        String fileExt = null;
        // 文件格式错误信息
        String fileExtError = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 上传文件
            MultipartFile mf = entity.getValue();
            // 获取图片大小
            int picSize = Integer.parseInt(String.valueOf(mf.getSize()));
            // 获取原文件名
            orgFileName = mf.getOriginalFilename();
            String fileName =  mf.getOriginalFilename();
            // 获取图片后缀
            fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if (!Const.DOCX.equals(fileExt) && !Const.DOC.equals(fileExt) && !Const.XLSX.equals(fileExt) && !Const.XLS.equals(fileExt)) {
                fileExtError = "nonsupport_type";
                data.setCode("1");
                data.setMsg(fileExtError);
                return data;
            } else if (picSize > Const.ONE_HUNDRED_MB) {
                fileExtError = "out_size";
                data.setCode("1");
                data.setMsg(fileExtError);
                return data;
            } else {
                // 对原文件名进行重命名
                fileName = this.get32UUID() + "." + fileExt;
                // 返回图片路径
                String rootPath = request.getServletContext().getRealPath("/");
                imgPath = Const.APPLY_DEAL_PATH + fileName;
                File dic = new File(rootPath + Const.APPLY_DEAL_PATH);
                if(!dic.exists()){
                    dic.mkdirs();
                }
                //上传至服务器
                String realPath = rootPath + imgPath;
                mf.transferTo(new File(realPath));

                data.getMap().put("apply_deal_file_name",orgFileName);
                data.getMap().put("apply_deal_file_path",imgPath);
                return data;
            }
        }
        return data;
    }
}
