package com.litc.common.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

/**
 *  Function:hibernate DAO基类
 *  提供hql分页查询，不带泛型，与具体实体类无关  
 *  @author  zhongying(281264212@qq.com)
 *  @date    2015-12-4 上午10:30:59    
 *  @version 1.0
 */
public abstract class JpaSimpleDao {
	/**
	 * 日志，可用于子类
	 */
	 Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * HIBERNATE 的 order 属性
	 */
	 static final String ORDER_ENTRIES = "orderEntries";

	/**
	 * 通过HQL查询对象列表
	 * 
	 * @param hql
	 *            hql语句
	 * @param values
	 *            数量可变的参数
	 */
	@SuppressWarnings("unchecked")
	public static List find(String hql, Object... values) {
		return createQuery(hql, values).getResultList();
	}

	/**
	 * 通过HQL查询唯一对象
	 */
	public static Object findUnique(String hql, Object... values) {
		return createQuery(hql, values).getSingleResult();
	}

	/**
	 * 通过Finder获得分页数据
	 * 
	 * @param finder
	 *            Finder对象
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Page find(EntityManager em,Finder finder, int pageNo, int pageSize) {
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
	}
	
	
	public static Page findByGroup(Finder finder,String selectSql, int pageNo, int pageSize) {
		return findByTotalCount(finder, pageNo, pageSize,  countQueryResultByGroup(finder,selectSql));
	}

	/**
	 * 通过Finder获得列表数据
	 * 
	 * @param finder
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List find(EntityManager em,Finder finder) {
		Query query = finder.createQuery(em);
		List list = query.getResultList();
		return list;
	}
	
	/**
	 * 通过Finder获得列表数据
	 * 
	 * @param finder
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List findBySql(EntityManager em,Finder finder) {
		Query query = finder.createNativeQuery(em);
		List list = query.getResultList();
		return list;
	}
	/**
	 * 根据查询函数与参数列表创建Query对象,后续可进行更多处理,辅助函数.
	 */
	public static Query  createQuery(String queryString, Object... values) {
		Assert.hasText(queryString);
		Query queryObject = em.createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject;
	}


	/**
	 * 获得Finder的记录总数
	 * 
	 * @param finder
	 * @return
	 */
	public static  int countQueryResult(EntityManager em,Finder finder) {
		Query query = em.createQuery(finder.getRowCountHql());
		finder.setParamsToQuery(query);
//		if (finder.isCacheable()) {
//			query.setCacheable(true);
//		}
		return ((Number) query.getSingleResult()).intValue();
	}
	
	
	public static  int countQueryResultBySql(EntityManager em,Finder finder) {
//		Query query = em.createQuery(finder.getRowCountHql());
		String sql=finder.getRowCountHql();
		sql=sql.replace(".*", ".id");
		Query query  =  em.createNativeQuery(sql);   
		finder.setParamsToQuery(query);
//		if (finder.isCacheable()) {
//			query.setCacheable(true);
//		}
		return ((Number) query.getSingleResult()).intValue();
	}
	
	public static int countQueryResultByGroup(Finder finder,String selectSql) {
		Query query = em.createQuery(finder.getRowCountTotalHql(selectSql));
		setParamsToQuery(finder, query);
		return ((Number) query.getSingleResult()).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public static Page findByTotalCount(Finder finder, int pageNo, int pageSize,int totalCount) {
		Page p =null;
		if (totalCount < 1) {
			 p=new PageImpl(new ArrayList(),new PageRequest(pageNo,pageSize), totalCount);
			return p;
		}
		Query query = em.createQuery(finder.getOrigHql());
		finder.setParamsToQuery(query);
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
//		if (finder.isCacheable()) {
//			query.setCacheable(true);
//		}
		List list = query.getResultList();
		 p=new PageImpl(list,new PageRequest(pageNo,pageSize), totalCount);
		return p;
	}
	
	 public static Query  setParamsToQuery(Finder finder,Query query){
		finder.setParamsToQuery(query);
//		if (finder.isCacheable()) {
//			query.setCacheable(true);
//		}
		return query;
	}

	/**
	 *  Function:获取hibernate的session
	 *  @author  zhongying(281264212@qq.com)
	 *  @date    2015-10-17 下午7:17:37   
	 *  @return
	 */
	 public static Session getSessionHibernate(){
		return (Session) getEm().getDelegate();
	}
	
	@PersistenceContext  
	public static EntityManager em;

	public static EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}   
	
	
}
