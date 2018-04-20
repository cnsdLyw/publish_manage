package com.litc.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.litc.common.jpa.Finder;
import com.litc.common.jpa.JpaSimpleDao;
import com.litc.model.CommonQuestion;
import com.litc.repository.CommonQuestionRepository;
import com.litc.service.CommonQuestionService;

@Service("commonQuestionService")
public class CommonQuestionImpl implements CommonQuestionService {
	
	@Autowired
	private CommonQuestionRepository commonQuestionRepository;
	

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public CommonQuestion saveCommonQuestion(CommonQuestion question) {
		CommonQuestion pubComment = commonQuestionRepository.save(question);
		return pubComment;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<CommonQuestion> getCommentByPages(int pageNo,int pageSize,String keyWord) {
		Finder f = Finder.create("select  bean from CommonQuestion bean where 1= 1");
		if (keyWord!=null&&!"".equals(keyWord)) {
			f.append(" and bean.question like '%").append(keyWord).append("%'");
			
		}
		f.append("order by time desc");
		return JpaSimpleDao.find(em, f, pageNo, pageSize);
	}

	@Override
	public void deleteQuestion(Long id) {
		commonQuestionRepository.delete(id);
	}

	@Override
	public void deleteQuestions(Long[] id) {
		commonQuestionRepository.deleteCommonQuestions(id);
	}

	@Override
	public CommonQuestion getQuestion(Long id) {
		return commonQuestionRepository.findOne(id);
	}

}
