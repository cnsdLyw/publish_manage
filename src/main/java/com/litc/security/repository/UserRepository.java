package com.litc.security.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.litc.security.model.User;
public interface UserRepository extends JpaRepository<User,Long>,IComponent {//JpaRepository<User,Long>,
		

	@Modifying
	@Query("delete from com.litc.security.model.User where id in :ids")
	int deleteUserIn(@Param("ids")Long[] ids);
	
	@Query("from com.litc.security.model.User u where u.loginName=?1") 
	List<User> findByLoginName(String loginName);
	
	@Query("from com.litc.security.model.User  where orgCode=?1") 
	List<User> findByOrgCode(String orgCode);
	  
	@Query("from com.litc.security.model.User u where u.id=?1") 
	User findById(Long id);
	
	@Query("from com.litc.security.model.User") 
	List<User> findUsers();
	  
	
	@Query(value="select count(*) from "+SYS_USER_ROLE+" where userId=?1",nativeQuery=true)
	int isUserHasRole(Long id);
	
	@Query(value="select count(*) from "+SYS_USER_ROLE+" where userId in :ids",nativeQuery=true)
	int isUserHasRole(@Param("ids")Long[] ids);
	
	Page<User> findAll(Specification<User> spec, Pageable pageable );
	
	@Query(value="select count(*) from "+SYS_USER+" where loginname=?1",nativeQuery=true)
	int isLoginNameExist(String loginName);
	
	@Query(value="select count(*) from "+SYS_USER+" where email=?1",nativeQuery=true)
	int isEmailExist(String email);
	
	@Query(value="select count(*) from "+SYS_USER+" where id<>?1 and email=?2",nativeQuery=true)
	int isEmailExist(Long id,String email);
	
	@Query(value="select u.name,org.orgName from security_sys_user u join security_sys_organization org on u.organizationId = org.id",nativeQuery=true)
	List<Object[]> searchByNativeQuery();

	@Modifying
	@Query("delete from com.litc.security.model.User where orgCode=?1")
	void deletUsers(String orgCode);
	
	public final static String USER_AUTHORITY_SQL = "select a.authorityKey from "+SYS_USER+" u join "+SYS_USER_ROLE+" ur on u.id=ur.userId "+
			"join "+SYS_ROLE+" r on ur.roleId=r.id "+
			"join "+SYS_ROLE_AUTHORITY+" ra on r.id=ra.roleId "+
			"join "+SYS_AUTHORITY+" a on ra.authorityId=a.id "+
			"where u.loginname=?1 "; 
	@Query(value=USER_AUTHORITY_SQL,nativeQuery=true)
	List<String> getUserAuthoritys(String loginName);
 	
}
