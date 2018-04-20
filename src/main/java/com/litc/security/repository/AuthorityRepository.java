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

import com.litc.security.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority,Long> ,IComponent{
		
	@Modifying
	@Transactional
	@Query("delete from com.litc.security.model.Authority where id in :ids")
	int deleteAuthorityIn(@Param("ids")Long[] ids);

	@Query(value="select count(*) from "+SYS_ROLE_AUTHORITY+" where authorityId in ?1",nativeQuery=true)
	int isAuthorityUsed(Long[] ids);
	
	@Query(value="select count(*) from "+SYS_ROLE_AUTHORITY+" where authorityId=?1",nativeQuery=true)
	int isAuthorityUsed(Long id);
	
	@Query("from com.litc.security.model.Authority au where au.status=?1") 
	List<Authority> findAuthorityByStatus(int status);
	
	@Query("from com.litc.security.model.Authority au where au.type=?1 and au.status=1") 
	List<Authority> findAuthorityByType(int type);
	
	@Query("select id from com.litc.security.model.Authority au where au.authorityKey=?1") 
	Long getAuthorityByKey(String authorityKey);

	Page<Authority> findByAuthorityKeyLike(String authorityKey,Pageable pageable);
	
	Page<Authority> findByAuthorityNameLikeAndAuthorityKeyLike(String authorityName,String address,Pageable pageable);
	
	Page<Authority> findAll(Specification<Authority> spec, Pageable pageable );  
	
	@Query(value="select count(*) from "+SYS_AUTHORITY+" where authorityKey=?1",nativeQuery=true)
	int isAuthorityKeyExist(String authorityKey);
	
	@Query(value="select count(*) from "+SYS_AUTHORITY+" where id<>?1 and authorityKey=?2",nativeQuery=true)
	int isAuthorityKeyExist(Long id,String authorityKey);
	
	@Query(value="select count(*) from "+SYS_AUTHORITY+" where authorityName=?1",nativeQuery=true)
	int isAuthorityNameExist(String authorityName);

	@Query(value="select count(*) from "+SYS_AUTHORITY+" where id<>?1 and authorityName=?2",nativeQuery=true)
	int isAuthorityNameExist(Long id, String authorityName);

}
