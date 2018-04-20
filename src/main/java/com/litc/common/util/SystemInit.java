package com.litc.common.util;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




public class SystemInit extends HttpServlet
{
    private static final long serialVersionUID = 1;
    private static Log log = LogFactory.getLog(SystemInit.class);

    /**
     * 系统启动时 执行一些初始任务
     * 	<servlet>
     * <servlet-name>/servlet/systemInitServlet</servlet-name>
     * <servlet-class>com.founder.publish.web.SystemInit</servlet-class>
     * <load-on-startup>1</load-on-startup>
     */
    public void init(ServletConfig config) throws ServletException
    {
    	log.info("初始化SystemInit");
        String root = config.getServletContext().getRealPath("/");
        ConfigManager.setHome(root);
        long l1 = System.currentTimeMillis();
        UserCacheManager.loadUserAuthorityCache();
        long l2 = System.currentTimeMillis();
        log.info("初始化用户权限，用时"+(l2-l1)+"ms。");
        
        log.info("初始化常用的系统代码表");
    }


}
