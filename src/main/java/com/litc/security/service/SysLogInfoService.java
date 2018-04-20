package com.litc.security.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.litc.security.common.page.PageParam;
import com.litc.security.model.LogInfo;

public interface SysLogInfoService {
	
	/**
	 * 分页带条件查询机构
	 * @param pageNo
	 * @param pageSize
	 * @param driection
	 * @param orderType
	 * @param args
	 * @return
	 */
	public Page<LogInfo> getOrganizationsByPages(int pageNo,int pageSize,Direction driection,String orderType,PageParam...args);
	
	
	/**
	 * 登录日志
	 * @param organization
	 */
	public void addLogInfo(LogInfo logInfo);


	public void deleteLogInfo(Long[] id);


	public LogInfo getLogInfo(Long id);
}
