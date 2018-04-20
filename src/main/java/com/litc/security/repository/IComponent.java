package com.litc.security.repository;

/**
 * 组件，静态常量
 * @author liyw
 *
 */
public interface IComponent {
	
	public static final String SERCHTYPE_LIKE = "like";
	
	public static final String SERCHTYPE_SAME = "=";
	

	/**
	 * 系统权限表
	 */
	public final static String SYS_AUTHORITY = "security_sys_authority";

	/**
	 * 系统角色表
	 */
	public final static String SYS_ROLE = "security_sys_role";
	
	/**
	 * 系统用户表
	 */
	public final static String SYS_USER = "security_sys_user";
	
	/**
	 * 系统机构表
	 */
	public final static String SYS_ORGANIZATION = "security_sys_organization";

	/**
	 * 系统角色和权限关联表名
	 */
	public final static String SYS_ROLE_AUTHORITY = "security_sys_role_authority";

	/**
	 * 系统用户和角色关联表名
	 */
	public final static String SYS_USER_ROLE = "security_sys_user_role";
	
	/**
	 * 存储设备表
	 */
	public final static String STORAGE_DEVICE = "sys_storage_device";
	
	/**
	 * 机构申请表
	 */
	public final static String ORGANIZATION_APPLY = "security_sys_organizationapply";
	
	/**
	 * 新闻链接广告
	 */
	public final static String MANUSCRIPT = "cms_manuscript";
	
	/**
	 * 日志
	 */
	public final static String SYS_LOG_INFO = "sys_loginfo";
	
	/**
	 * 符合性测试任务和测试用例关联表名
	 */
	public final static String COMPLIANCE_TASK_CASE = "jc_compliance_task_case";
	
	/**
	 * 系统配置分类组表
	 */
	public final static String SYS_CONFIGURE_GROUP = "sys_configure_group";
	/**
	 * 系统配置表
	 */
	public final static String SYS_CONFIGURE = "sys_configure";

	/**
	 * 机构积分记录表
	 */
	public final static String CHARGING = "jc_charging";
	
	
	/**
	 * 计费权限表
	 */
	public final static String CHARGING_AUTHORITY = "jc_charging_authority";
	
	/**
	 * 计费策略表
	 */
	public final static String CHARGING_STRAGERY = "jc_charging_strategy";
	
	/**
	 * 计费策略规则表
	 */
	public final static String CHARGING_STRAGERY_RULE = "jc_charging_strategy_rule";
	
	/**
	 * 计费权限规则表
	 */
	public final static String CHARGING_AUTHORITY_RULE = "jc_charging_authority_rule";
}
