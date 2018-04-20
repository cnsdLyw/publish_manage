package com.litc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.litc.common.util.Constant;
import com.litc.security.model.Supplier;


public interface SupplierRepository extends JpaRepository<Supplier, Long>{
	
	@Query(value="select count(*) from "+Constant.SUPPLIER_TABLE_NAME+" where business_contacts_email=?1",nativeQuery=true)
	int isEmailExist(String email);
	
	@Query(value="select count(*) from "+Constant.SUPPLIER_TABLE_NAME+" where id<>?1 and business_contacts_email=?2",nativeQuery=true)
	int isEmailExist(Long id,String email);
	
	@Modifying
	@Query("delete from com.litc.security.model.Supplier where id in :ids")
	int deleteUserIn(@Param("ids")Long[] ids);
	
	Page<Supplier> findAll(Specification<Supplier> spec, Pageable pageable );
}