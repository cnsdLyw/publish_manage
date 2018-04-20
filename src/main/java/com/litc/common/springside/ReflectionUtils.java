package com.litc.common.springside;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *  Function:反射工具类
 *  @author  zhongying(281264212@qq.com)
 *  @date    2015-12-3 下午12:30:01    
 *  @version 1.0
 */
public class ReflectionUtils {
	private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

	public static String convertElementPropertyToString(Collection collection, String propertyName, String separator) {
		List list = convertElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	public static List convertElementPropertyToList(Collection collection, String propertyName)
	  {
	    List list = new ArrayList();
	    Iterator i$;
	    try {
	      for (i$ = collection.iterator(); i$.hasNext(); ) { Object obj = i$.next();
	        list.add(PropertyUtils.getProperty(obj, propertyName)); }
	    }
	    catch (Exception e) {
	      throw convertReflectionExceptionToUnchecked(e);
	    }

	    return list;
	  }
	
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if ((e instanceof IllegalAccessException) || (e instanceof IllegalArgumentException) || (e instanceof NoSuchMethodException)) {
			return new IllegalArgumentException("Reflection Exception.", e);
		}
		if (e instanceof InvocationTargetException)
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}

	
}