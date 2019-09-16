package com.zhengpp.padis.controller;

import com.zhengpp.padis.base.BaseController;
import com.zhengpp.padis.entity.PageData;
import com.zhengpp.padis.entity.User;
import com.zhengpp.padis.service.ApplyService;
import com.zhengpp.padis.utils.Const;
import com.zhengpp.padis.utils.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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
        PageData pd = this.getPageInfo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        pd.put("user_role",user.getUserRole());
        if("1".equals(user.getUserRole())){
            //客户端列表，只查看自己发布的
            pd.put("insert_user",user.getUserId());
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
    public ModelAndView toAddApply(ModelAndView mv){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        mv.addObject("user",user);
        mv.setViewName("/apply/applyAdd");
        return mv;
    }

    /**
     * 跳转到提取详情页面
     * @param applyId
     * @return
     */
    @RequestMapping("/toApplyInfo")
    public ModelAndView toApplyInfo(@RequestParam(value = "apply_id") String applyId) throws Exception {
        ModelAndView mv = new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        mv.addObject("user",user);
        PageData pd = applyService.findById(applyId);
        mv.addObject("pd",pd);
        mv.setViewName("/apply/applyInfo");
        return mv;
    }

    /**
     * 查看
     * @param applyId
     * @return
     * @throws Exception
     */
    @RequestMapping("toDescInfo")
    public ModelAndView toDescInfo(@RequestParam(value = "apply_id") String applyId) throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = applyService.findById(applyId);
        mv.addObject("apply_desc",pd.getString("apply_desc"));
        mv.setViewName("/apply/applyDescInfo");
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
     * 综合部审核退回
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkBack")
    public String checkBack() throws Exception {
        PageData pd = this.getPageData();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        pd.put("apply_status","4");
        pd.put("update_user",user.getUserId());
        applyService.updateStatus(pd);
        return "success";
    }

    /**
     * 综合部审核通过
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/checkPass")
    public String checkPass() throws Exception {
        PageData pd = this.getPageData();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        pd.put("apply_status","3");
        pd.put("update_user",user.getUserId());
        applyService.updateStatus(pd);
        return "success";
    }

    /**
     * 删除提取
     * @return
     * @throws Exception
     */
    @RequestMapping("/delApply")
    @ResponseBody
    public String delApply() throws Exception {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        PageData pd = this.getPageData();
        pd.put("update_user",user.getUserId());
        applyService.delApply(pd);
        return "success";
    }

    @RequestMapping("/applySend")
    @ResponseBody
    public String applySend(@RequestParam(value = "apply_id") String applyId) throws Exception {
        PageData pd = applyService.findById(applyId);
        if(pd==null){
            return "未查询到该数据";
        }
        if(StringUtils.isBlank(pd.getString("certify_file_name")) || StringUtils.isBlank(pd.getString("certify_file_path"))){
            return "未上传电子公函";
        }
        if(StringUtils.isBlank(pd.getString("apply_desc")) && StringUtils.isBlank(pd.getString("apply_file_path"))){
            return "未填写描述信息或上传描述文件";
        }

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        pd.put("update_user",user.getUserId());
        pd.put("apply_status","2");
        applyService.updateApply(pd);
        return "success";
    }

    /**
     * 修改页面
     * @param applyId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toEditApply")
    public ModelAndView toEditApply(@RequestParam(value = "apply_id") String applyId) throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = applyService.findById(applyId);
        mv.addObject("pd",pd);
        mv.setViewName("/apply/applyEdit");
        return mv;
    }

    /**
     * 保存提取
     * @param mv
     * @return
     */
    @RequestMapping(value = "/editApply")
    public ModelAndView editApply(ModelAndView mv) throws Exception {
        PageData pd = this.getPageData();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        pd.put("update_user",user.getUserId());
        applyService.updateApply(pd);
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
    /**
     * 上传警员证
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/uploadUserNumberFile")
    @ResponseBody
    public ResponseData uploadUserNumberFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            if (!Const.JPG.equals(fileExt) && !Const.JPEG.equals(fileExt) && !Const.PNG.equals(fileExt) && !Const.GIF.equals(fileExt)
                    && !Const.BMP.equals(fileExt)) {
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
                String rootPath = request.getServletContext().getRealPath( "WEB-INF/classes"+Const.APPLY_USER_NUMBER);
                imgPath = Const.APPLY_USER_NUMBER + fileName;
                File dic = new File(rootPath );
                if(!dic.exists()){
                    dic.mkdirs();
                }
                //上传至服务器
                String realPath = rootPath + fileName;
                mf.transferTo(new File(realPath));

                data.getMap().put("user_number_file_name",orgFileName);
                data.getMap().put("user_number_file_path",imgPath);
                return data;
            }
        }
        return data;
    }

    /**
     * 下载处理文件
     * @param applyId
     * @return
     */
    @RequestMapping(value = "/downloadDealFile")
    public void downloadDealFile(@RequestParam(value = "apply_id") String applyId, HttpServletRequest request,HttpServletResponse response) throws Exception {
        PageData pd = applyService.findById(applyId);
        String fileName = pd.getString("apply_deal_file_name");
        String rootPath = request.getServletContext().getRealPath("/");
        String url = pd.getString("apply_deal_file_path");
        String filePath = null;
        if(StringUtils.isNotBlank(url)){
            filePath = rootPath + url.replace("/","\\");
        }
        File file = new File(filePath);
        if(file.exists()) {
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    /**
     * 下载描述文件
     * @param applyId
     * @return
     */
    @RequestMapping(value = "/downloadApplyDesc")
    public void downloadApplyDesc(@RequestParam(value = "apply_id") String applyId, HttpServletRequest request,HttpServletResponse response) throws Exception {
        PageData pd = applyService.findById(applyId);
        String fileName = pd.getString("apply_file_name");
        String rootPath = request.getServletContext().getRealPath("/");
        String url = pd.getString("apply_file_path");
        String filePath = null;
        if(StringUtils.isNotBlank(url)){
            filePath = rootPath + url.replace("/","\\");
        }
        File file = new File(filePath);
        if(file.exists()) {
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    /**
     * 下载电子凭证
     * @param applyId
     * @return
     */
    @RequestMapping(value = "/downloadCertify")
    public void downloadCertify(@RequestParam(value = "apply_id") String applyId, HttpServletRequest request,HttpServletResponse response) throws Exception {
        PageData pd = applyService.findById(applyId);
        String fileName = pd.getString("certify_file_name");
        String rootPath = request.getServletContext().getRealPath("/");
        String url = pd.getString("certify_file_path");
        String filePath = null;
        if(StringUtils.isNotBlank(url)){
            filePath = rootPath + url.replace("/","\\");
        }
        File file = new File(filePath);
        if(file.exists()) {
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
