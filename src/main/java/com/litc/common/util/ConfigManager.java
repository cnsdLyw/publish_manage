package com.litc.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.litc.common.bean.SpringBeanUtil;
import com.litc.system.model.SysConfigure;
import com.litc.system.service.SysConfigureService;

public class ConfigManager {

	private static Logger log = LoggerFactory.getLogger(ConfigManager.class);
	
	private static XMLProperties properties = null;
	
	private static String enpHome = null;

	/**
	 * 返回EnpHome目录(包含：系统配置文件、日志文件等)
	 * 
	 * @return EnpHome目录
	 */
	public static String getHome() {
		if (enpHome == null) {
			// enpHome = System.getProperty(Constant.HOME_DIRNAME);
			return "/NOT_CONFIG_ENPHOME";
		}
		return enpHome;
	}

	public static void setHome(String homeRoot) {
		getProperty();
		enpHome = homeRoot;
	}

	/**
	 * 获取初始化参数ENPROOT这个物理路径
	 * 
	 * @return
	 */
	public static String getEnpRoot() {
		return getHome();
	}
	
	/**
	 * 提取配置文件
	 */
	public synchronized static void loadProperties() {
		properties = new XMLProperties();//getConfigFilePath());
		properties.setPropertyCache(getProperty());
	}
	
	/**
	 * 初始系统配置信息
	 * @return
	 */
	public static Map<String,String> getProperty() {
		log.info("初始化配置信息");
		/*SysConfigureService cnonixTransService = (SysConfigureService) SpringBeanUtil.getBean("sysConfigureService");
		List<SysConfigure> list = cnonixTransService.getSysConfigGroup();
		for(SysConfigure config:list){
			System.out.println(config.getConfigureCode()+"    "+config.getConfigureName()+"   "+config.getConfigureValue());
		}*/
		//System.out.println(1/0);
		return null;
	}

}
