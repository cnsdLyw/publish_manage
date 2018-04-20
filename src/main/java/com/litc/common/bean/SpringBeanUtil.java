package com.litc.common.bean;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 *  Function:获取SpringBean工具类
 *  @author  zhongying(281264212@qq.com)
 *  @date    2015-12-4 上午10:24:06    
 *  @version 1.0
 */
public class SpringBeanUtil {

	/**
	 * 获取SpringBean
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		Object obj = null;
		try {
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			obj = wac.getBean(beanName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	
	
}
