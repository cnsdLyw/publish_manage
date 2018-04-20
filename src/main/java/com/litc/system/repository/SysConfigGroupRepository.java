package com.litc.system.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.litc.security.repository.IComponent;
import com.litc.system.model.SysConfigGroup;

public interface SysConfigGroupRepository extends JpaRepository<SysConfigGroup, Long>,IComponent{

	Page<SysConfigGroup> findAll(Specification<SysConfigGroup> spec, Pageable pageable );
	
	@Query("from com.litc.system.model.SysConfigGroup where parentId=?1")
	List<SysConfigGroup> getSysConfigGroup(Long parentId);
	
	@Query("from com.litc.system.model.SysConfigGroup")
	List<SysConfigGroup> getSysConfigGroup();

	@Query("select count(*) from com.litc.system.model.SysConfigGroup where parentId=?1")
	int getCounts(Long typeid);
	@Query(value="select count(*) from "+SYS_CONFIGURE_GROUP+" where groupName=?1",nativeQuery=true)
	int isSysConfigureNameExist(String groupName);

	@Query(value="select count(*) from "+SYS_CONFIGURE_GROUP+" where id<>?1 and groupName=?2",nativeQuery=true)
	int isSysConfigureNameExist(Long id, String groupName);
	
}
