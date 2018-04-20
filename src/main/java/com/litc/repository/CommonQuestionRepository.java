package com.litc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.litc.model.CommonQuestion;


/**
 *  Function: 常见问题(数据访问层)
 *  @author  cuiyc
 *  @date    2017-7-9 上午11:15:18    
 *  @version 1.0
 */
public interface CommonQuestionRepository extends JpaRepository<CommonQuestion, Long>{
	
	@Modifying
	@Transactional
	@Query("delete from com.litc.model.CommonQuestion where id in :ids")
	int deleteCommonQuestions(@Param("ids")Long[] ids);

}
