package com.litc.common.util;

import java.io.IOException;
import java.util.Properties;


/**
 *  Function:动态发布系统常量类  
 *  常量类型定义为：操作模块名_属性名_常量说明
 *  @author  zhongying(281264212@qq.com)
 *  @date    2015-12-9 下午2:20:27    
 *  @version 1.0
 */
public class Constant {

	private static Properties properties = new Properties();

	static {
		try {
			// 加载上传文件设置参数：配置文件
			properties.load(Constant.class.getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 点对点发送消息队列
	 */
	public static final String CNONIX_PTP_TRANS = "CNONIX_PTP.trans_test"; 
	
	/**
	 * 广播发送消息队列
	 */
	public static final String CNONIX_ALL_TRANS = "CNONIX_ALL.trans";
	
	/**
	 * 加工任务发送消息队列
	 */
	public static final String PROCESS_TRANS = "CNONIX_PROCESS.trans";
	
	
	/**
	 * 消息类型 发送
	 */
	public static final Integer MESSAGE_TYPE_SEND = 1;
	
	/**
	 * 消息类型 接收
	 */
	public static final Integer MESSAGE_TYPE_RECEIVE = 2;
	
	public static final String URL = "目前没有用到";
	
	//全局变量，不准修改
	public static final String TOMCAT_ROOT_PATH = com.litc.common.util.ConfigManager.getEnpRoot();
	
	public static final String SERVER_LOCAL_PATH = properties.getProperty("server.local.path");//服务器本地临时文件夹路径 1
	
	public static final String SERVER_IMAGE_PATH = properties.getProperty("http.bookImagePath");//服务器本地临时首页大图路径 
	
	public static final String SERVER_FILE_HOME_URL = properties.getProperty("http.fileHomeUrl");//文件服务器浏览地址
	
	public static final String EMAIL_NAME = properties.getProperty("email.sendName");//获取邮箱名称 1
	 
	public static final String EMAIL_PWD = properties.getProperty("email.sendPwd");//获取邮箱密码 1
	
	public static final String MQ_URL = properties.getProperty("mq.url");//获取mq远程链接地址 1
	
	public static final String MQ_USERNAME = properties.getProperty("mq.userName");//mq用户名 1
	
	public static final String MQ_USERPWD = properties.getProperty("mq.userPwd");//mq密码 1
	
	public static final Long PROCESS_TASKFLOW_ID = 1L;//加工任务流程ID
	
	public static final Long COMPLIANCETEST_TASKFLOW_ID = 2L;//符合性测试流程ID
	
	/**
	 * 集成接口访问地址
	 */
	public static final String CLOUD_PlAT_Url =  properties.getProperty("CloudPlatformUrl");
	/**
	 * 集成接口服务ID
	 */
	public static final String SERVICE_ID = properties.getProperty("serviceID");

	/**
	 * 集成接口实例ID
	 */
	public static final String INSTANCE_ID = properties.getProperty("instanceID");
	
	
	/**
	 * 索引
	 */
	public static final String TYPE_INDEX = "index";
	
	/**
	 * 
	 */
	public static final String DESC_INDEX = "索引操作";
	
	/**
	 * 中心平台机构码
	 */
	public static final String CEMTRAL_PLATFORM_CODE = "central_platform_code";
	
	/**
	 * ONIX符合性测试服务码
	 */
	public static final String TASK_ONIX_CHECK = "TASK_ONIX_CHECK";
	
	/**
	 * 排序 正序排列
	 */
	public static final String SORT_TYPE_ASC = "asc";
	
	/**
	 * 排序 倒序排列
	 */
	public static final String SORT_TYPE_DESC = "desc";
	
	
	//for comet4j
    public static long EXPIRE_AFTER_ONE_HOUR = 30; //cache过期时间
    
    /**
     * 图书符合性测试监听频道
     */
    public static String CHANNEL_BOOK_ONIX_CHECK = "BOOK_ONIX_CHECK";
    
    /**
     * 发布平台首页数据更新监听频道
     */
    public static String CHANNEL_PUBLISH_DATA_GATHER = "PUBLISH_DATA_GATHER";
    
   
	/**
	 * 图书封面
	 */
	public static final String BOOK_FILE_COVER = "BOOK_FILE_COVER";

	/**
	 * 图书封底
	 */
	public static final String BOOK_FILE_BACK_COVER = "BOOK_FILE_BACK_COVER";



	/**
	 * 消息附件
	 */
	public static final String MESSAGE_FILE = "MESSAGE_FILE";
	
	/**
	 * 符合性测试用例文件
	 */
	public static final String COMPLIANCE_CASE_FILE = "COMPLIANCE_CASE_FILE";

	/**
	 * 首页大图图片
	 */
	public static final String PUBLISH_BIG_PICTURE = "PUBLISH_BIG_PICTURE";
	
	/**
	 * 文件发布附件
	 */
	public static final String PUBLISH_FILE_RELEASE = "PUBLISH_FILE_RELEASE";
	
	/**
	 * 机构封面图图片
	 */
	public static final String ORGANIZATION_COVER_FILE = "ORGANIZATION_COVER_FILE";
	
	/**
	 * 默认封面（图书确少封面时使用）
	 */
	public static final String LACK_IMG_PATH = "/resources/images/lack_img.jpg";
	
	/**
	 * 分类类型 行业分类
	 */
	public static final String SYS_CLASS_INDUSTRY = "INDUSTRY_CLASS";

	/**
	 * 附件在Tomcat下的存储位置
	 */
	public static final String FILE_SAVE_PATH = "attachement";
	
	/**
	 * 供应商表名
	 */
	public static final String SUPPLIER_TABLE_NAME = "sly_supplier";
	
	/**
	 * 财务流水表名
	 */
	//public static final String SUPPLIER_TABLE_NAME = "sly_supplier";
}
