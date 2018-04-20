package com.litc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.litc.common.jpa.Finder;
import com.litc.model.MqModel;
import com.litc.model.cms.Manuscript;
import com.litc.service.JmxContentService;
@Service("jmxContentSercie")
public class JmxContentServiceImpl implements JmxContentService{

	/*public static Page find(EntityManager em,String count, int pageNo, int pageSize) {
		int totalCount = countQueryResult(em,finder);
		Page p =null;
		if (totalCount < 1) {
			 p=new PageImpl(new ArrayList(),new PageRequest(pageNo,pageSize), totalCount);
			return p;
		}
		Query query = em.createQuery(finder.getOrigHql());
		finder.setParamsToQuery(query);
		
		if(pageSize>0){
			query.setFirstResult(pageNo * pageSize);
			query.setMaxResults(pageSize);
		}
		
//		if (finder.isCacheable()) {
//			query.setCacheable(true);
//		}
		List list = query.getResultList();
		if(pageSize>0){
			p=new PageImpl(list,new PageRequest(pageNo,pageSize), totalCount);
		}else{
			p=new PageImpl(list);
		}
		
		
		return p;
	}*/

}
