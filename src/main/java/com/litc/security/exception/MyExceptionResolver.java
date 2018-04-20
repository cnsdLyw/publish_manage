package com.litc.security.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyExceptionResolver implements HandlerExceptionResolver {

    //private ExceptionLogDao exceptionLogDao;

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {

        // 异常处理，例如将异常信息存储到数据库
    	System.out.println("异常操作 "+handler.toString());
        System.out.println("异常 " +ex.getClass().getName() );
        System.out.println("异常堆栈信息 "+ex.getMessage());
        ex.printStackTrace();
        // exceptionLogDao.save(ex);
        //此处可以将异常信息保存到数据库中

        // 视图显示专门的错误页
        ModelAndView modelAndView = new ModelAndView("/pages/error/500");
        modelAndView.addObject("ex", ex);
        //如果此处返回null,则跳转到web.xml中配置的500错误页面
        return modelAndView;
    }
}