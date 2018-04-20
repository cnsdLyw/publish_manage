package com.litc.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.litc.security.repository.IComponent;
import com.litc.system.model.StorageDevice;

public interface StorageDeviceRepository extends JpaRepository<StorageDevice,Long>,IComponent {
	
	@Query("from com.litc.system.model.StorageDevice de where de.ftpCode=?1") 
	StorageDevice findByFtpCode(String ftpCode);
	
	@Modifying
	@Query("delete from com.litc.system.model.StorageDevice where id in :ids")
	int deleteStorageDeviceIn(@Param("ids")Long[] ids);
	
	Page<StorageDevice> findAll(Specification<StorageDevice> spec, Pageable pageable );  

}
