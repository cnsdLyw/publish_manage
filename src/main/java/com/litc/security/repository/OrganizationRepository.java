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

import com.litc.security.model.Organization;

public interface OrganizationRepository extends JpaRepository<Organization,String>,IComponent {
		
	@Modifying
	@Transactional
	@Query("delete from com.litc.security.model.Organization where orgCode in :orgCodes")
	int deleteOrganizationIn(@Param("orgCodes")String[] orgCodes);

	
	@Query(value="select count(*) from "+SYS_USER+" where orgCode in :orgCodes",nativeQuery=true)
	int isOrganizationUsed(@Param("orgCodes")String[] orgCodes);
	
	@Query(value="select count(*) from "+SYS_USER+" where orgCode=?1",nativeQuery=true)
	int isOrganizationUsed(String orgCode);

	@Query("from com.litc.security.model.Organization org where org.orgType in :types") 
	List<Organization> findOrganizationsByOrgType(@Param("types")String[] types);
	
	@Query("from com.litc.security.model.Organization org where org.orgType = ?1") 
	List<Organization> findByOrgType(String typeId);
	
	Page<Organization> findAll(Specification<Organization> spec, Pageable pageable );

	
	@Query("from com.litc.security.model.Organization org where org.orgCode =?1") 
	Organization getOrganization(String orgCode);  
	
	@Query("from com.litc.security.model.Organization org where org.orgCode =?1") 
	Organization getOrganization(Organization orgCode);  
	
	@Query("from com.litc.security.model.Organization org where org.orgID =?1") 
	Organization getOrg(String orgId);  
	
	@Query("from com.litc.security.model.Organization org where org.orgName =?1") 
	Organization getOrganCode(String orgName);  
	
	@Query("from com.litc.security.model.Organization org where org.loginName =?1") 
	Organization getPassword(String loginName);  

	@Query("select org.orgName from com.litc.security.model.Organization org where org.orgCode in :orgCodes") 
	List<String> findOrganizationNames(@Param("orgCodes")String[] orgCodes);
	
	@Query(value="select count(*) from "+SYS_ORGANIZATION+" where orgCode=?1",nativeQuery=true)
	int isOrgCodeExist(String orgCode);

	@Query(value="select orgName from "+SYS_ORGANIZATION+" where orgCode=?1",nativeQuery=true)
	String getOrgName(String upperCode);

	@Query("from com.litc.security.model.Organization org where org.firstOrgName !=null and org.firstOrgName!='' group by org.firstOrgName") 
	List<Organization> getFirstOrganization();
	
	@Query("from com.litc.security.model.Organization org where org.firstOrgName !=null and org.firstOrgName!='' and org.firstOrgName =?1") 
	List<Organization> getFirstOrganization(String firstName);

	@Query("from com.litc.security.model.Organization org where org.secondOrgName =?1") 
	List<Organization> getSecondOrganization(String secondName);

	@Query(value="select count(*) from "+SYS_ORGANIZATION+" where upperCode=?1",nativeQuery=true)
	int getCounts(String orgCode);

	@Query("from com.litc.security.model.Organization org where org.firstOrgName =?1") 
	List<Organization> getOrganizationByFirstName(String firstName);

	@Query("from com.litc.security.model.Organization org where org.firstOrgName =?1 group by org.secondOrgName")
	List<Organization> getOrganizationBySecondNames(String secondOrgName);

}
