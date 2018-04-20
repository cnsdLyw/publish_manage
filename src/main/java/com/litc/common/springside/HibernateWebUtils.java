package com.litc.common.springside;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.Assert;

/**
 * springside3-core-3.2.2的工具类
 * 
 * @author zhongying
 * 
 */
public class HibernateWebUtils {
	public static <T, ID> void mergeByCheckedIds(Collection<T> srcObjects, Collection<ID> checkedIds, Class<T> clazz) {
		mergeByCheckedIds(srcObjects, checkedIds, clazz, "id");
	}

	public static <T, ID> void mergeByCheckedIds(Collection<T> srcObjects, Collection<ID> checkedIds, Class<T> clazz, String idName) {
		Assert.notNull(srcObjects, "scrObjects不能为空");
		Assert.hasText(idName, "idName不能为空");
		Assert.notNull(clazz, "clazz不能为空");

		if (checkedIds == null) {
			srcObjects.clear();
			return;
		}

		Iterator<T> srcIterator = srcObjects.iterator();
		try {
			while (srcIterator.hasNext()) {
				T element = srcIterator.next();
				Object id = PropertyUtils.getProperty(element, idName);
				if (!checkedIds.contains(id)) {
					srcIterator.remove();
				} else {
					checkedIds.remove(id);
				}

			}

			for (ID id : checkedIds) {
				T obj = clazz.newInstance();
				PropertyUtils.setProperty(obj, idName, id);
				srcObjects.add(obj);
			}
		} catch (Exception e) {
			// throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
	}
	
	// public static List<PropertyFilter>
	// buildPropertyFilters(HttpServletRequest request)
	// {
	// return buildPropertyFilters(request, "filter_");
	// }
	//
	// public static List<PropertyFilter>
	// buildPropertyFilters(HttpServletRequest request, String filterPrefix)
	// {
	// List filterList = new ArrayList();
	//
	// Map filterParamMap = WebUtils.getParametersStartingWith(request,
	// filterPrefix);
	//
	// for (Map.Entry entry : filterParamMap.entrySet()) {
	// String filterName = (String)entry.getKey();
	// String value = (String)entry.getValue();
	//
	// if ((!StringUtils.isNotBlank(value)) || (value.equals("-1")))
	// continue;
	// PropertyFilter filter = new PropertyFilter(filterName, value);
	// filterList.add(filter);
	// }
	//
	// return filterList;
	// }

}