package com.litc.system.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.litc.security.common.page.PageParam;
import com.litc.system.model.SysWorkFlow;

public interface SysWorkFlowService {
	
	/**
	 * 添加工作流
	 * @param processWorkFlow
	 */
	public void addSysWorkFlow(SysWorkFlow processWorkFlow);

	/**
	 * 获取工作流
	 * @param id
	 * @return
	 */
	public SysWorkFlow getSysWorkFlow(Long id);

	/**
	 *  修改工作流
	 * @param user
	 * @return
	 */
	public SysWorkFlow updateSysWorkFlow(SysWorkFlow processWorkFlow);
	
	/**
	 * 工作流是否使用
	 * @param id
	 * @return
	 */
	public int isSysWorkFlowUsed(Long id);
	
	
	/**
	 * 删除工作流,根据编号删除
	 * @param id
	 */
	public void deleteSysWorkFlow(Long id);

	/**
	 * 批量删除工作流
	 * @param ids
	 * @return
	 */
	public int deleteSysWorkFlows(Long[] ids);

	/**
	 * 查询所有工作流
	 * @return
	 */
	public List<SysWorkFlow> getSysWorkFlows();
	
	/**
	 * 分页带条件查询
	 * @param pageNo
	 * @param pageSize
	 * @param driection
	 * @param orderType
	 * @param args
	 * @return
	 */
	public Page<SysWorkFlow> getSysWorkFlowsByPages(int pageNo,int pageSize,Direction driection,String orderType,PageParam...args);
	
}
