package com.litc.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.litc.model.cms.Manuscript;
import com.litc.security.repository.IComponent;

/**
 * 内容管理
 * @author liyw
 * 
 */
public interface ManuscriptRepository extends JpaRepository<Manuscript, Long>,IComponent {
	/**
	 * 批量发布
	 */
	String sql_publishState="update cms_manuscript set publishstate= :publishstate,pubTime= :pubTime where id in :ids";
	@Modifying@Transactional  
	@Query(value=sql_publishState,nativeQuery=true)
	void publishState(@Param("publishstate")Integer publishstate,@Param("pubTime")Date pubTime,@Param("ids")Long[] ids);
	
	/**
	 * 单条发布
	 */
	String sql_single_publishState="update cms_manuscript set publishstate= :publishSingleState,pubTime= :pubTime where id= :ids";
	@Modifying@Transactional  
	@Query(value=sql_single_publishState,nativeQuery=true)
	void publishSingleState(@Param("publishSingleState")Integer publishSingleState,@Param("pubTime")Date pubTime,@Param("ids")Long ids);
	
	@Query("from com.litc.model.cms.Manuscript where task=?1") 
	public Manuscript getManuScriptByTask(Long taskId);
	/**
	 * 批量发布
	 */
	String sql_unpublishState="update cms_manuscript set publishstate= :publishstate where id in :ids";
	@Modifying@Transactional  
	@Query(value=sql_unpublishState,nativeQuery=true)
	void unpublishState(@Param("publishstate")Integer publishstate,@Param("ids")Long[] ids);
	
	/**
	 * 根据新闻ID__获取新闻列表
	 *  @param ids
	 *  @return
	 */
	@Query("from com.litc.model.cms.Manuscript where id in :ids") 
	public List<Manuscript> getManuscriptList(@Param("ids")Long[] ids);
	@Query("from com.litc.model.cms.Manuscript where id = :id") 
	public List<Manuscript> getManuscriptListById(@Param("id")Long id);
	@Query(value="select count(*) from "+MANUSCRIPT+" where title=?1 and nodeID=?2",nativeQuery=true)
	int isContentNameExist(String authorityName,Long nodeId);
	@Query(value="select count(*) from "+MANUSCRIPT+" where id<>?1 and title=?2 and nodeID=?3",nativeQuery=true)
	int isContentNameExist(Long id, String authorityName,Long nodeId);
	
	
}