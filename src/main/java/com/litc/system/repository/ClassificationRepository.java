package com.litc.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.litc.system.model.Classification;

/**
 *  分类表dao
 * @author liyw
 *
 */
public interface ClassificationRepository extends JpaRepository<Classification,String>{

	@Query("from com.litc.system.model.Classification where classKey=?1") 
	List<Classification> getClassByKey(String classKey);
	
	@Query("from com.litc.system.model.Classification where classKey=?1 and classCode=?2") 
	List<Classification> getClassByKeyAndClassCode(String classKey,String classCode);
	
	@Query("from com.litc.system.model.Classification where classKey=?1 and classLevel=?2") 
	List<Classification> getClassByKeyAndLevel(String classKey,Short classLevel);
	
	@Query("from com.litc.system.model.Classification where classKey=?1 and parentCode=?2") 
	List<Classification> getClassByKeyAndParentcode(String classKey,String parentCode);
	
	@Query(value="select count(*) from sys_classification where class_code=?1",nativeQuery=true)
	int isClassCodeExist(String classCode);
}

