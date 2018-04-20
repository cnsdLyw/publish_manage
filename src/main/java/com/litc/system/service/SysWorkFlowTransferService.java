package com.litc.system.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.litc.security.common.page.PageParam;
import com.litc.system.model.SysWorkFlowTransfer;

public interface SysWorkFlowTransferService {

	
	/**
	 * 添加工作流迁移节点
	 * @param processWorkFlowTransfer
	 */
	public void addProcessWorkFlowTransfer(SysWorkFlowTransfer processWorkFlowTransfer);

	/**
	 * 获取工作流迁移节点
	 * @param id
	 * @return
	 */
	public SysWorkFlowTransfer getProcessWorkFlowTransfer(Long id);

	/**
	 *  修改工作流迁移节点
	 * @param user
	 * @return
	 */
	public SysWorkFlowTransfer updateProcessWorkFlowTransfer(SysWorkFlowTransfer processWorkFlowTransfer);
	
	/**
	 * 工作流迁移节点是否使用
	 * @param id
	 * @return
	 */
	public int isProcessWorkFlowTransferUsed(Long id);
	
	
	/**
	 * 删除工作流迁移节点,根据编号删除
	 * @param id
	 */
	public void deleteProcessWorkFlowTransfer(Long id);

	/**
	 * 批量删除工作流迁移节点
	 * @param ids
	 * @return
	 */
	public int deleteProcessWorkFlowTransfers(Long[] ids);

	/**
	 * 查询所有工作流迁移节点
	 * @return
	 */
	public List<SysWorkFlowTransfer> getProcessWorkFlowTransfers();
	
	/**
	 * 分页带条件查询
	 * @param pageNo
	 * @param pageSize
	 * @param driection
	 * @param orderType
	 * @param args
	 * @return
	 */
	public Page<SysWorkFlowTransfer> getProcessWorkFlowTransfersByPages(int pageNo,int pageSize,Direction driection,String orderType,PageParam...args);
	

}
