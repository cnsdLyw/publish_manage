package com.litc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.litc.model.cms.Manuscript;

public interface ManuscriptService {
	/**
	 * 获取书目列表
	 */
	public Page<Manuscript> getManuscripts(Map map, String queryOrderBy, String queryOrdertype, Integer pageNo,
			Integer pageSize);

	/**
	 * 更新书目
	 */
	public Manuscript updateManuscript(Manuscript manuscript);

	/**
	 * 获取一本书目
	 */
	public Manuscript getManuscript(Long id);

	/**
	 * 删除一本书目
	 */
	public void deleteManuscript(Long id);

	/**
	 * 批量发布新闻
	 * 
	 * @param publishstate
	 * @param ids
	 */
	public void publishState(Integer publishstate, Date pubTime, Long[] ids);
	
	public void publishSingleState(Integer publishstate,Date pubTime,Long ids);
	/**
	 * 根据新闻ID__获取新闻列表
	 * 
	 * @param ids
	 * @return
	 */
	public List<Manuscript> getManuscriptList(Long[] ids);
	
	
	public List<Manuscript> getManuscriptList(Long id);
	public boolean isContentNameExist(String authorityName,Long nodeId);

	public boolean isContentNameExist(Long id, String authorityName,Long nodeId);
	public String getUploadTime();
	public Manuscript getManuScriptByTask(Long taskId);
}
