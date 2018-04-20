package com.litc.common.util;

import java.io.IOException;
import java.util.Properties;

/**
 *  Function:下面读取资源文件 配置，以后会用数据库管理 
 *  @author  zhongying(281264212@qq.com)
 *  @date    2016-3-12 下午2:19:54    
 *  @version 1.0
 */
public class ConfigurationUtil {

	private static Properties properties = new Properties();

	static {
		try {
			// 加载上传文件设置参数：配置文件
			properties.load(ConfigurationUtil.class.getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//如：c\:\\reso\\
	//【文件上传保存目录】如果为空，则默认tomcat根目录下 1
	public static final String fileUpload_Directory=properties.getProperty("fileUpload.Directory").equals("")?com.litc.common.util.ConfigManager.getEnpRoot():properties.getProperty("fileUpload.Directory"); //以后修改为读取数据库
		
	/**
	 * zhongying_add
	 */
	//如：c\:\\reso\\ 1
	//【ueditor文件上传保存目录】如果为空，则默认tomcat根目录下
	public static final String ueditor_FileUploadDirectory = properties.getProperty("ueditor.fileUploadDirectory"); //以后修改为读取数据库 1
	
	
	
	//如：http://198.9.1.209:8010/frontmanager/
	//【中心服务器】接收附件下载所在tomcat访问地址，本配置在出版，图书馆 ，发行 都是一样的。
	public static final String center_TomcatAccessAddress = properties.getProperty("center.TomcatAccessAddress"); //以后修改为读取数据库 1
	
	//机构本地浏览文件前缀地址  如http://111.207.13.82:8090/cbs/
	public static final String resource_AccessAddress = properties.getProperty("resource.AccessAddress"); //以后修改为读取数据库
		
	//Tomcat启动时
	
	
	
}
