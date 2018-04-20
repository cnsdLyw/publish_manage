package com.litc.common.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.litc.taglib.PageList;

/**
 *  Function:模版 DAO基类
 *  提供Sql分页查询，不带泛型，与具体实体类无关  
 *  @author  zhongying(281264212@qq.com)
 *  @date    2015-12-4 上午10:30:59    
 *  @version 1.0
 */
public abstract class JpaArticleDao {
	/**
	 * 日志，可用于子类
	 */
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings("unchecked")
	public static PageList findBySql(EntityManager em,Finder finder, String[] fieldsAsNameArg, int pageNo, int pageSize) {
		long l1 = System.currentTimeMillis();
		int totalCount = countQueryResultBySql(em,finder);
		long l2 = System.currentTimeMillis();
		System.out.println("查询总数量，用时  "+(l2-l1)+" ms");
		//int totalCount=5;
		PageList pageList = new PageList();
		pageList.setCurrentPage(pageNo);
		pageList.setPageSize(pageSize);
		pageList.setTotalCount(totalCount);
		if (totalCount < 1) {
			return pageList;
		}
//		Query query = em.createQuery(finder.getOrigHql());
		//Query query  =  em.createNativeQuery(finder.getOrigHql(),Message.class);   
		Query query  =  em.createNativeQuery(finder.getOrigHql());   
		finder.setParamsToQuery(query);
		Integer firstResult=(pageNo)*pageSize;
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
//		if (finder.isCacheable()) {
//			query.setCacheable(true);
//		}
		
		
		List objectList = query.getResultList();
		
	
		List<Map<String, String>> dataList=new ArrayList<Map<String, String>>();
		Map<String, String> dataMap=null;
		for(Object obj : objectList)
		{
			dataMap=new LinkedHashMap<String,String>();
			Object[] obj1 = (Object[]) obj; 
			for(int j =0;j<obj1.length;j++)
            {
				dataMap.put(fieldsAsNameArg[j], obj1[j]==null? null:obj1[j].toString());
            }
			dataList.add(dataMap);
		}
		pageList.setDataList(dataList);
		em.close();
		return pageList;
		
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> findMapBySql(EntityManager em,Finder finder, String[] fieldsAsNameArg) {
		Map<String, Object>  dataMap=new HashMap<String, Object>();
		Query query  =  em.createNativeQuery(finder.getOrigHql());   
		finder.setParamsToQuery(query);
		List objectList = query.getResultList();
		if(objectList.size()>0){
			Object object = objectList.get(0);
			Object[] obj1 = (Object[]) object; 
			int length = obj1.length;
			for(int i =0;i<length;i++){
				dataMap.put(fieldsAsNameArg[i], obj1[i]==null? null:obj1[i].toString());
			}
		}
		return dataMap;
	}
	

	protected static int countQueryResultBySql(EntityManager em,Finder finder) {
//		Query query = em.createQuery(finder.getRowCountHql());
		String sql=finder.getRowCountSql();
		sql=sql.replace(".*", ".id");
		Query query  =  em.createNativeQuery(sql);   
		finder.setParamsToQuery(query);
//		if (finder.isCacheable()) {
//			query.setCacheable(true);
//		}
		int countValue=((Number) query.getSingleResult()).intValue();
		return countValue;
	}
	
}
