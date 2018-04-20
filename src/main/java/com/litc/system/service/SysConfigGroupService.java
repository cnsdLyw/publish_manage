package com.litc.system.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.litc.system.model.SysConfigGroup;

public interface SysConfigGroupService {

	List<SysConfigGroup> getSysConfigGroup(Long parentId);
	List<SysConfigGroup> getSysConfigGroup();
	int getCounts(Long typeid);
	Page<SysConfigGroup> getSysConfigGroupsByPage(int pageNo,int pageSize,Direction driection,String orderType,Long typeid,String keyWord);
	SysConfigGroup getSysConfigGroupBy(Long id);
	void addSysConfigGroup(SysConfigGroup sysConfigGroup);
	void delete(Long id);
	
	boolean isSysConfigureNameExist(String configureName);

	boolean isSysConfigureNameExist(Long id, String configureName);


	
}
