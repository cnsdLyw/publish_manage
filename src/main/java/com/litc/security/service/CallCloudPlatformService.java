package com.litc.security.service;

import com.alibaba.fastjson.JSONObject;

public interface CallCloudPlatformService {

	/**
	 * 上报心跳状态接口
	 * @author cuiyc
	 * @return true 处理成功 flas 失败
	 */
	public  boolean reportHeartBeatStatus();
	
	/**
	 * 用户身份验证接口
	 * @author cuiyc
	 * @return 验证结果 //0：有效，1：无效，2：超时3：参数错误 -1，saas 服务已停止
	 */
	public  int getUserAuthenticationInfo() ;
	
	/**
	 * 上报用户计量信息接口
	 * @author cuiyc true成功 false：失败
	 */
	public  boolean reportChargingInfo(String orgId) ;
	
	/**
	 * 订单初始化完成通知接口
	 * @author cuiyc 
	 */
	public  boolean NotifyOrderProcessImpl(JSONObject obj) ;
	
	
}
