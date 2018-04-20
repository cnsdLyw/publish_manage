package com.litc.security.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.litc.security.model.Role;
import com.litc.security.model.User;

public interface RoleRepository extends JpaRepository<Role,Long>,IComponent {
	
	@Modifying
	@Query("delete from com.litc.security.model.Role where id in :ids")
	int deleteRoleIn(@Param("ids")Long[] ids);
	
	@Query(value="select count(*) from "+SYS_USER_ROLE+" where roleId=?1",nativeQuery=true)
	int isRoleUsedByUser(Long id);
	
	@Query(value="select count(*) from "+SYS_USER_ROLE+" where roleId in :ids",nativeQuery=true)
	int isRoleUsedByUser(@Param("ids")Long[] ids);
	
	@Query(value="select count(*) from "+SYS_ROLE_AUTHORITY+" where roleId=?1",nativeQuery=true)
	int isRoleUsed(Long id);
	
	@Query(value="select count(*) from "+SYS_ROLE_AUTHORITY+" where roleId in :ids",nativeQuery=true)
	int isRoleUsed(@Param("ids")Long[] ids);
	
	Page<Role> findAll(Specification<Role> spec, Pageable pageable );
	
	@Query(value="select count(*) from "+SYS_ROLE+" where roleName=?1",nativeQuery=true)
	int isRoleExist(String roleName);
	
	@Query(value="select count(*) from "+SYS_ROLE+" where id<>?1 and roleName=?2",nativeQuery=true)
	int isRoleExist(Long id,String roleName);
	
	//@Query("from com.litc.security.model.Role r where r.organizationFlag=?1") 
	List<Role> findByOrganizationFlag(String organizationFlag);
	
	String getRoleIdSql = "select r.id "+
		"from security_sys_role r "+
		"join security_sys_role_authority ra on r.ID=ra.roleId "+
		"join security_sys_authority a on ra.authorityId=a.ID "+
		"where a.authorityKey=?1 ";
	@Query(value=getRoleIdSql,nativeQuery=true)
	List<Long> getRoleId(String authorityKey);
	
	String getUsersSql = "select u.loginname,u.`name` "+
			"from security_sys_user u  "+
			"join security_sys_user_role ur on u.id=ur.userId  "+
			"join security_sys_role r on ur.roleId=r.ID "+
			"where u.orgCode=?1 and r.ID=?2 ";
	@Query(value=getUsersSql,nativeQuery=true)
	List<Object[]> getUsersByRole(String loginOrgCode,Long roleId);
	
	String getRolesSql = "select r.* "+
			"from security_sys_user u  "+
			"join security_sys_user_role ur on u.id=ur.userId  "+
			"join security_sys_role r on ur.roleId=r.ID "+
			"where u.ID=?1 ";
	@Query(value=getRolesSql,nativeQuery=true)
	List<Role> getRolesByUser(Long userId);
	
}
