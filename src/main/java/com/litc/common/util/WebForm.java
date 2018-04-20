package com.litc.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Function:替代Struts1的ActionForm
 * 
 * @author zhongying(281264212@qq.com)
 * @date 2010-3-12 上午11         :46:37
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class WebForm {
	
	/**
	 * 将jsp里面的值保存到持久化的entity中
	 *  后期修改为BeanUtils.populate(entity, map)即可，参看14包服务器端MediaCutLayerService
	 *  @param clzPersistence
	 *  @param clzJsp
	 *  @return
	 */
	public static Object form2Obj(Object persistenceObj, Object jspObj) {
		try {
			Map<String, Object> mapJsp=parseObj2Map(jspObj,true);//将jsp对应的entity转换成Map
			BeanWrapper bw = new BeanWrapperImpl(persistenceObj);
			PropertyDescriptor[] propertys = bw.getPropertyDescriptors();
			for (PropertyDescriptor property : propertys) {
				String propertyName = property.getName();
				if (!bw.isWritableProperty(propertyName) || "id".equals(propertyName)) {//id不能修改
					continue;
				}
				
				if (!mapJsp.containsKey(propertyName)) {
					continue;
				}
				Object propertyValue=mapJsp.get(propertyName);
				System.out.println("保存到表里面的字段："+propertyName+"=="+propertyValue);
				
				Object value = propertyValue;
				
				// 赋值值进行类型转换
				propertyValue = ConvertUtils.convert(propertyValue, property.getPropertyType());
				ConvertUtils.register(new DateConverter(null), java.util.Date.class);
				
				if(value==null && (property.getPropertyType()==Double.class || property.getPropertyType()==Integer.class)){
					propertyValue = null;
				}
				
				bw.setPropertyValue(propertyName,propertyValue);
			}
			return persistenceObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 将jsp对应的entity转换成Map
	 *  @param obj
	 *  @param ignoreBlankString
	 *  @return
	 */
	public static Map parseObj2Map(Object obj, boolean ignoreBlankString) {
//		Class clazz = obj.getClass();//获取对象的类型  
		Map<String, Object> parseMap = new HashMap<String, Object>();
		BeanWrapper bw = new BeanWrapperImpl(obj);
		PropertyDescriptor[] propertys = bw.getPropertyDescriptors();
		for (PropertyDescriptor property : propertys) {
			String propertyName = property.getName();
			Object propertyType = property.getPropertyType();
//			Object propertyValue= property.getValue(propertyName);
			
//			  Method getMethod = property.getReadMethod();//从属性描述器中获取 get 方法  
//		       Object propertyValue =null ;  
//		       try {  
//		    	   propertyValue = getMethod.invoke(clazz, new Object[]{});//调用方法获取方法的返回值  
//		       } catch (Exception e) {  
//		           e.printStackTrace();  
//		       }  
		       
			Object propertyValue= bw.getPropertyValue(propertyName);
			System.out.println(propertyName+"=="+propertyValue+",         propertyType=="+propertyType); 
			if (!bw.isWritableProperty(propertyName) || "id".equals(propertyName)|| "[]".equals(propertyValue+"")) {
				continue;
			}
			//System.out.println("页面上不为空的字段："+propertyName+"=="+propertyValue);
			// 将属性值注入该对应的属性当中，相当于FormBean中设值
			parseMap.put(propertyName, propertyValue);
			
		}
		System.out.println();
		return parseMap;
	}
}
