package com.litc.system.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.litc.security.repository.IComponent;
import com.litc.system.model.SysConfigure;


public interface SysConfigureRepository extends JpaRepository<SysConfigure,Long>,IComponent{

	Page<SysConfigure> findAll(Specification<SysConfigure> spec, Pageable pageable );

	@Query(value="select count(*) from "+SYS_CONFIGURE+" where configureName=?1",nativeQuery=true)
	int isSysConfigureNameExist(String configureName);

	@Query(value="select count(*) from "+SYS_CONFIGURE+" where id<>?1 and configureName=?2",nativeQuery=true)
	int isSysConfigureNameExist(Long id, String configureName);
	@Query("from com.litc.system.model.SysConfigGroup where groupId=?1")
	List<SysConfigure> getSysConfigure(Long parentId);
	@Query("from com.litc.system.model.SysConfigure")
	List<SysConfigure> getSysConfigure();
	@Query(value="select parentId from "+SYS_CONFIGURE_GROUP+" where id=?1 ",nativeQuery=true)
	Long getParentId(Long typeid);
}
