package com.litc.system.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.litc.security.common.page.PageParam;
import com.litc.system.model.SysConfigure;

public interface SysConfigureService {

	/**
	 * 分页带条件查询
	 * @param pageNo
	 * @param pageSize
	 * @param driection
	 * @param orderType
	 * @param args
	 * @return
	 */
	Page<SysConfigure> getSysConfiguresByPage(int pageNo,int pageSize,Direction driection,String orderType,Long typeid,String keyWord);

	SysConfigure getSysConfigure(Long id);

	void addSysConfigure(SysConfigure sysConfigure);

	boolean isSysConfigureNameExist(String groupName);

	boolean isSysConfigureNameExist(Long id, String groupName);

	void delete(Long id);

	List<SysConfigure> getSysConfigGroup();
	List<SysConfigure> getSysConfigGroup(Long typeid);

	Long getParentId(Long typeid);


}
