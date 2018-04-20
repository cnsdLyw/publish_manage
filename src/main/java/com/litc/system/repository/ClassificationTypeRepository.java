package com.litc.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.litc.system.model.ClassificationType;

/**
 *  分类类别表dao
 * @author liyw
 *
 */
public interface ClassificationTypeRepository extends JpaRepository<ClassificationType,Long>{

}

