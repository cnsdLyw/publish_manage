package com.litc.security.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.litc.security.model.OrganizationApply;

public interface OrganizationApplyRepository extends JpaRepository<OrganizationApply,Long>,IComponent {
		
	@Modifying
	@Transactional
	@Query("delete from com.litc.security.model.OrganizationApply where id in :ids")
	int deleteOrganizationApplyIn(@Param("ids")Long[] ids);
	
	@Query("from com.litc.security.model.OrganizationApply org where org.orgType in :types") 
	List<OrganizationApply> findOrganizationApplysByOrgType(@Param("types")Long[] types);
	
	@Query("from com.litc.security.model.OrganizationApply org where org.orgType = ?1") 
	List<OrganizationApply> findByOrgType(Long typeId);
	
	Page<OrganizationApply> findAll(Specification<OrganizationApply> spec, Pageable pageable );

	
	@Query("from com.litc.security.model.OrganizationApply org where org.id =?1") 
	OrganizationApply getOrganization(Long id);  
	
	@Query(value="select count(*) from "+ORGANIZATION_APPLY+" where orgCode=?1",nativeQuery=true)
	int isOrgCodeExist(String orgCode);
	
	@Query(value="select count(*) from "+ORGANIZATION_APPLY+" where loginName=?1",nativeQuery=true)
	int isLoginNameExist(String loginName);
	
	@Query(value="select count(*) from "+ORGANIZATION_APPLY+" where orgContactEmail=?1",nativeQuery=true)
	int isEmailExist(String email);

	@Query(value="select orgName from "+ORGANIZATION_APPLY+" where orgCode=?1",nativeQuery=true)
	String getOrgName(String upperCode);
	
}
