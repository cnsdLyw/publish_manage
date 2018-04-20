package com.litc.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import com.litc.security.repository.IComponent;
import com.litc.system.model.SysWorkFlow;

public interface SysWorkFlowRepository extends JpaRepository<SysWorkFlow,Long>,IComponent {
	
	Page<SysWorkFlow> findAll(Specification<SysWorkFlow> spec, Pageable pageable );  
	
	
	

}
