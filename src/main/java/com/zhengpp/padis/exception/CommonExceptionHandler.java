package com.zhengpp.padis.exception;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class CommonExceptionHandler {
    private static Logger log = Logger.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ModelAndView exceptionHandler(Exception e){
        //输错错误到日志文件
        log.error(e,e);
        //返回202系统异常
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/error");
        return mv;
    }

}
