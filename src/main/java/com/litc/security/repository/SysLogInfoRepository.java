package com.litc.security.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.litc.security.model.LogInfo;


public interface SysLogInfoRepository extends JpaRepository<LogInfo,Long>,IComponent {
	
	@Modifying
	@Query("delete from com.litc.security.model.LogInfo where id in :id")
	int deleteIn(@Param("id")Long[] id);
	
	Page<LogInfo> findAll(Specification<LogInfo> spec, Pageable pageable );  
}
