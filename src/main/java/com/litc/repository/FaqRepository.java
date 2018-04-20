package com.litc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.litc.model.cms.Faq;

/**
 *  Function:  留言板(数据访问层)
 *  @author  zhongying(281264212@qq.com)
 *  @date    2015-11-12 上午11:15:18    
 *  @version 1.0
 */
public interface FaqRepository extends JpaRepository<Faq, Long>{


}