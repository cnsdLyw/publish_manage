package com.litc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.litc.security.model.FinanceRecord;


public interface FinanceRecordRepository extends JpaRepository<FinanceRecord, Long>{
	
	@Modifying
	@Query("delete from com.litc.security.model.FinanceRecord where id in :ids")
	int deleteUserIn(@Param("ids")Long[] ids);
	
	Page<FinanceRecord> findAll(Specification<FinanceRecord> spec, Pageable pageable );
}