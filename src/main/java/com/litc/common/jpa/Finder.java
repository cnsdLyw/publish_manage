package com.litc.common.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.repository.query.parser.Part.Type;

/**
 *  Function:HQL语句分页查询  
 *  @author  zhongying(281264212@qq.com)
 *  @date    2015-12-4 上午10:29:27    
 *  @version 1.0
 */
public class Finder {
	protected Finder() {
		hqlBuilder = new StringBuilder();
	}

	protected Finder(String hql) {
		hqlBuilder = new StringBuilder(hql);
	}

	public static Finder create() {
		return new Finder();
	}

	public static Finder create(String hql) {
		return new Finder(hql);
	}

	public Finder append(String hql) {
		hqlBuilder.append(hql);
		return this;
	}

	/**
	 * 获得原始hql语句
	 * 
	 * @return
	 */
	public String getOrigHql() {
		return hqlBuilder.toString();
	}

	/**
	 * 获得查询数据库记录数的hql语句。
	 * @return
	 */
	public String getRowCountHql() {
		return getRowCountBaseHql(ORDER_BY);
	}
	
	

	/**
	 * 获得查询数据库记录数的SQL语句。
	 * @return
	 */
	public String getRowCountSql() {
		return getRowCountBaseSql(ORDER_BY);
	}
	
	public String getRowCountTotalHql(String selectSql) {
		return getRowCountTotalBaseHql(ORDER_BY,selectSql);
	}
	
	public String getRowCountHqlByGroup() {
		return getRowCountBaseHql(GROUP_BY);
	}

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	/**
	 * 是否使用查询缓存
	 * 
	 * @return
	 */
	public boolean isCacheable() {
		return cacheable;
	}

	/**
	 * 设置是否使用查询缓存
	 * 
	 * @param cacheable
	 * @see Query#setCacheable(boolean)
	 */
	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	/**
	 * 设置参数
	 * 
	 * @param param
	 * @param value
	 * @return
	 * @see Query#setParameter(String, Object)
	 */
	public Finder setParam(String param, Object value) {
		return setParam(param, value, null);
	}

	/**
	 * 设置参数。与hibernate的Query接口一致。
	 * 
	 * @param param
	 * @param value
	 * @param type
	 * @return
	 * @see Query#setParameter(String, Object, Type)
	 */
	public Finder setParam(String param, Object value, Type type) {
		getParams().add(param);
		getValues().add(value);
		getTypes().add(type);
		return this;
	}

	/**
	 * 设置参数。与hibernate的Query接口一致。
	 * 
	 * @param paramMap
	 * @return
	 * @see Query#setProperties(Map)
	 */
	public Finder setParams(Map<String, Object> paramMap) {
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			setParam(entry.getKey(), entry.getValue());
		}
		return this;
	}

	/**
	 * 设置参数。与hibernate的Query接口一致。
	 * 
	 * @param name
	 * @param vals
	 * @param type
	 * @return
	 * @see Query#setParameterList(String, Collection, Type))
	 */
	public Finder setParamList(String name, Collection<Object> vals, Type type) {
		getParamsList().add(name);
		getValuesList().add(vals);
		getTypesList().add(type);
		return this;
	}

	/**
	 * 设置参数。与hibernate的Query接口一致。
	 * 
	 * @param name
	 * @param vals
	 * @return
	 * @see Query#setParameterList(String, Collection)
	 */
	public Finder setParamList(String name, Collection<Object> vals) {
		return setParamList(name, vals, null);
	}

	/**
	 * 设置参数。与hibernate的Query接口一致。
	 * 
	 * @param name
	 * @param vals
	 * @param type
	 * @return
	 * @see Query#setParameterList(String, Object[], Type)
	 */
	public Finder setParamList(String name, Object[] vals, Type type) {
		getParamsArray().add(name);
		getValuesArray().add(vals);
		getTypesArray().add(type);
		return this;
	}

	/**
	 * 设置参数。与hibernate的Query接口一致。
	 * 
	 * @param name
	 * @param vals
	 * @return
	 * @see Query#setParameterList(String, Object[])
	 */
	public Finder setParamList(String name, Object[] vals) {
		return setParamList(name, vals, null);
	}

	/**
	 * 将finder中的参数设置到query中。
	 * 
	 * @param query
	 */
	public Query setParamsToQuery(Query query) {
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				if (types.get(i) == null) {
					query.setParameter(params.get(i), values.get(i));
				} else {
					//query.setParameter(params.get(i), values.get(i), types.get(i));
				}
			}
		}
		if (paramsList != null) {
			for (int i = 0; i < paramsList.size(); i++) {
				if (typesList.get(i) == null) {
					query
							.setParameter(paramsList.get(i), valuesList
									.get(i));
				} else {
					query.setParameter(paramsList.get(i),valuesList.get(i));
					
//					query.setParameterList(paramsList.get(i),
//							valuesList.get(i), typesList.get(i));
				}
			}
		}
		if (paramsArray != null) {
			for (int i = 0; i < paramsArray.size(); i++) {
				if (typesArray.get(i) == null) {
					query.setParameter(paramsArray.get(i), valuesArray
							.get(i));
				} else {
					//query.setParameter(paramsArray.get(i), valuesArray.get(i), typesArray.get(i));
				}
			}
		}
		return query;
	}

	public Query createQuery(EntityManager em) {
		Query query = setParamsToQuery(em.createQuery(getOrigHql()));
		if (getFirstResult() > 0) {
			query.setFirstResult(getFirstResult());
		}
		if (getMaxResults() > 0) {
			query.setMaxResults(getMaxResults());
		}
//		if (isCacheable()) {
//			query.setCacheable(true);
//		}
		return query;
	}
	public Query createNativeQuery(EntityManager em) {
		Query query = setParamsToQuery(em.createNativeQuery(getOrigHql()));
		if (getFirstResult() > 0) {
			query.setFirstResult(getFirstResult());
		}
		if (getMaxResults() > 0) {
			query.setMaxResults(getMaxResults());
		}
//		if (isCacheable()) {
//			query.setCacheable(true);
//		}
		return query;
	}
	private String getRowCountBaseHql(String indexKey) {
		String hql = hqlBuilder.toString();

		int fromIndex = hql.toLowerCase().indexOf(FROM);
		String projectionHql = hql.substring(0, fromIndex);

		hql = hql.substring(fromIndex);
		String rowCountHql = hql.replace(HQL_FETCH, "");

		int index = rowCountHql.indexOf(indexKey);
		if (index > 0) {
			rowCountHql = rowCountHql.substring(0, index);
		}
		String countHQL=wrapProjection(projectionHql) + rowCountHql;
		return countHQL;
	}
	
	private String getRowCountBaseSql(String indexKey) {
		String hql = hqlBuilder.toString();

		int fromIndex = hql.toLowerCase().indexOf(FROM);
		String projectionHql = hql.substring(0, fromIndex);

		hql = hql.substring(fromIndex);
		String rowCountHql = hql.replace(HQL_FETCH, "");

		int index = rowCountHql.indexOf(indexKey);
		if (index > 0) {
			rowCountHql = rowCountHql.substring(0, index);
		}
		String countHQL="select count(1) "+ rowCountHql;
		return countHQL;
	}
	private String getRowCountTotalBaseHql(String indexKey,String selectSql) {
		String hql = hqlBuilder.toString();

		int fromIndex = hql.toLowerCase().indexOf(FROM);
		String projectionHql = hql.substring(0, fromIndex);

		hql = hql.substring(fromIndex);
		String rowCountHql = hql.replace(HQL_FETCH, "");

		int index = rowCountHql.indexOf(indexKey);
		if (index > 0) {
			rowCountHql = rowCountHql.substring(0, index);
		}
		//return selectSql+"( "+wrapProjectionBeanId(projectionHql)+rowCountHql+")";
		return "select count(bean) from "+"( "+projectionHql+rowCountHql+") as a";
	}
	
	private String wrapProjection(String projection) {
		if (projection.indexOf("select") == -1) {
			return ROW_COUNT;
		} else {
			return projection.replace("select", "select count(") + ") ";
		}
	}
	
	private String wrapProjectionCount1(String projection) {
		if (projection.indexOf("select") == -1) {
			return ROW_COUNT;
		} else {
			//将select和from代码替换成count(1)
			int selectFrom=projection.indexOf("from");
			String endprojection="select count(1) "+ projection.substring(selectFrom);
			return endprojection;
		}
	}
	
	@SuppressWarnings("unused")
	private String wrapProjectionBeanId(String projection) {
		if (projection.indexOf("select") == -1) {
			return "select bean.id ";
		} else {
			return projection.replace("select bean", "select bean.id") + " ";
		}
	}

	
	private List<String> getParams() {
		if (params == null) {
			params = new ArrayList<String>();
		}
		return params;
	}

	private List<Object> getValues() {
		if (values == null) {
			values = new ArrayList<Object>();
		}
		return values;
	}

	private List<Type> getTypes() {
		if (types == null) {
			types = new ArrayList<Type>();
		}
		return types;
	}

	private List<String> getParamsList() {
		if (paramsList == null) {
			paramsList = new ArrayList<String>();
		}
		return paramsList;
	}

	private List<Collection<Object>> getValuesList() {
		if (valuesList == null) {
			valuesList = new ArrayList<Collection<Object>>();
		}
		return valuesList;
	}

	private List<Type> getTypesList() {
		if (typesList == null) {
			typesList = new ArrayList<Type>();
		}
		return typesList;
	}

	private List<String> getParamsArray() {
		if (paramsArray == null) {
			paramsArray = new ArrayList<String>();
		}
		return paramsArray;
	}

	private List<Object[]> getValuesArray() {
		if (valuesArray == null) {
			valuesArray = new ArrayList<Object[]>();
		}
		return valuesArray;
	}

	private List<Type> getTypesArray() {
		if (typesArray == null) {
			typesArray = new ArrayList<Type>();
		}
		return typesArray;
	}

	private StringBuilder hqlBuilder;

	private List<String> params;
	private List<Object> values;
	private List<Type> types;

	private List<String> paramsList;
	private List<Collection<Object>> valuesList;
	private List<Type> typesList;

	private List<String> paramsArray;
	private List<Object[]> valuesArray;
	private List<Type> typesArray;

	private int firstResult = 0;

	private int maxResults = 0;

	private boolean cacheable = false;

	public static final String ROW_COUNT = "select count(*) ";
	public static final String FROM = "from";
	public static final String DISTINCT = "distinct";
	public static final String HQL_FETCH = "fetch";
	public static final String ORDER_BY = "order";
	public static final String GROUP_BY = "group";

	public static void main(String[] args) {
		Finder find = Finder
				.create("select distinct p FROM BookType join fetch p");
		System.out.println(find.getRowCountHql());
		System.out.println(find.getOrigHql());
	}

}