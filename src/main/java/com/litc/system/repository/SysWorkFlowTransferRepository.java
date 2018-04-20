package com.litc.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.litc.security.repository.IComponent;
import com.litc.system.model.SysWorkFlowTransfer;

public interface SysWorkFlowTransferRepository extends JpaRepository<SysWorkFlowTransfer,Long>,IComponent {
	
	Page<SysWorkFlowTransfer> findAll(Specification<SysWorkFlowTransfer> spec, Pageable pageable );  

}
