package com.litc.common.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Function:字符串工具类
 * 
 * @author zhongying(281264212@qq.com)
 * @date 2015-12-4 上午10:24:53
 * @version 1.0
 */
public class StringUtil {
	
	/**
	 * String转map
	 * 
	 * @param mapString
	 *            待转字符串
	 * @param strSplit
	 *            字符串分割
	 * @param keySplit
	 *            key,value分割
	 * @return
	 */
	public static Map<String, Object> transStringToMap(String mapString,
			String strSplit, String keySplit) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		StringTokenizer items;
		for (StringTokenizer entrys = new StringTokenizer(mapString, strSplit); entrys
				.hasMoreTokens(); map.put(items.nextToken(),
				items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
			items = new StringTokenizer(entrys.nextToken(), keySplit);
		return map;
	}

	/**
	 * url请求参数转map
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		Map<String, String[]> propertyMaptemp = request.getParameterMap();
		Map<String, String> parameterMap = new HashMap<String, String>();

		for (Map.Entry<String, String[]> itemProperty : propertyMaptemp
				.entrySet()) {
			if (itemProperty.getValue()[0] != null
					&& !"".equals(itemProperty.getValue()[0])) {
				parameterMap.put(itemProperty.getKey(),
						itemProperty.getValue()[0]);
			}
		}
		return parameterMap;
	}

	/**
	 * http url组装
	 * 
	 * @param method
	 * @param requestParam
	 * @return
	 */
	public static String assemblyUrl(String method, String requestParam) {

		StringBuffer url = new StringBuffer(Constant.URL);
		url.append("/" + method);

		Map<String, Object> map = transStringToMap(requestParam, ";", ":");
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			url.append("/" + entry.getValue());
		}
		return url.toString();
	}

	public static <T> String getParamStr(Map<String, ?> paramMap) {

		StringBuffer paramStr = new StringBuffer("?");
		for (Map.Entry<String, ?> map : paramMap.entrySet()) {
			paramStr.append(map.getKey()).append("=").append(map.getValue())
					.append("&");
		}
		if (paramStr.length() < 1) {
			return "";
		}
		return paramStr.substring(0, paramStr.length() - 1);
	}
	
	
	/**
	 * 获取系统登录名称
	 * @param session
	 * @return
	 */
	public static String getUserName(HttpSession session){
		String userName="";
		if(session!=null)
			userName=(String)session.getAttribute("loginname");
		else
			return userName;
		
		return (userName!=null?userName:"");
	}
	
	/**
	 * 获取系统登录名称
	 * @param request
	 * @return
	 */
	public static String getUserName(HttpServletRequest request){
		
		String userName=(String)request.getSession().getAttribute("loginname");
		
		return (userName!=null?userName:"");
	}
	
	
	public static String getString(Object value) {
		return getString(value,"");

	}
	
	/**
	 * 获取对象对应的字符串类型
	 *  @param value
	 *  @param defaultValue
	 *  @return
	 */
	public static String getString(Object value,String defaultValue) {
		String result = defaultValue;
		if(value==null){
			return defaultValue;
		}
		try
		{
			result = value+"";
		}
		catch (Exception ex)
		{
		}
		return result;

	}
	
	public static int getInt(Object value) {
		return getInt(value,0);

	}
	
	/**
	 * 获取字符串中的int值,
	 * 如果在转换过程中出现任何错误则返回缺省值
	 *
	 * @param value 输入的字符串
	 * @param defaultValue 缺省值
	 * @return 对应的int值
	 */
	public static int getInt(Object value, int defaultValue)
	{
		int result = defaultValue;
		if(value==null){
			return defaultValue;
		}
		try
		{
			result = Integer.parseInt(value+"");
		}
		catch (Exception ex)
		{
		}
		return result;
	}
	
	/**
	 * 获取字符串中的int值,
	 * 如果在转换过程中出现任何错误则返回缺省值
	 *
	 * @param value 输入的字符串
	 * @param defaultValue 缺省值
	 * @return 对应的int值
	 */
	public static long getLong(String value, long defaultValue)
	{
		long result = defaultValue;
		try
		{
			result = Long.parseLong(value);
		}
		catch (Exception ex)
		{
		}
		return result;
	}
	
	public static double getDouble(String value, double defaultValue)
	{
		double result = defaultValue;
		try
		{
			result = Double.parseDouble(value);
		}
		catch (Exception ex)
		{
		}
		return result;
	}
	
	
	/**
	 * 将String类型字符串(逗号分割)，转换成Long类型数组
	 * 
	 * @param stringArr
	 *            字符串数组
	 * @return
	 */
	public static Long[] strToLongArray(String str) {
		if (str == null || "".equals(str)) {
			return null;
		}
		String[] stringArr = str.split(",");
		return strArrayToLongArray(stringArr);
	}

	/**
	 * 将String类型数组，转换成Long类型数组
	 * 
	 * @param stringArr
	 *            字符串数组
	 * @return
	 */
	public static Long[] strArrayToLongArray(String[] stringArr) {
		if (stringArr == null || stringArr.length == 0) {
			return null;
		}
		Long[] userIDArray = new Long[stringArr.length];
		for (int i = 0; i < userIDArray.length; i++) {
			if (stringArr[i] == null || stringArr[i].length() == 0) {
				continue;
			}
			userIDArray[i] = Long.valueOf(stringArr[i]);
		}
		return userIDArray;
	}

	/**
	 * 将List类型，转换成Long类型数组
	 * 
	 * @param <T>
	 * @param stringArr
	 *            字符串数组
	 * @return
	 */
	public static Long[] strArrayToLongArray(List<?> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		Long[] userIDArray = new Long[list.size()];
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				continue;
			}
			userIDArray[i] = Long.valueOf(list.get(i) + "");
		}
		return userIDArray;
	}
	
	/**
	 * 将List类型，转换成Long类型数组
	 * 
	 * @param <T>
	 * @param stringArr
	 *            字符串数组
	 * @return
	 */
	public static String[] lisToArray(List<String> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		String[] idDArray = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				continue;
			}
			idDArray[i] = list.get(i);
		}
		return idDArray;
	}


	/**
	 * 将集合转换成以逗号分割的字符串
	 * 
	 * @param coll
	 * @return
	 */
	public static String collectionToStr(Collection<String> coll) {
		if (coll != null && coll.size() > 0) {
			StringBuffer sb = new StringBuffer();
			int i = 0;
			for (String str : coll) {
				if (i > 0)
					sb.append(",");
				i++;
				sb.append(str);
			}
			return sb.toString();
		}
		return "";
	}

	/**
	 * 将String集合转换成数组
	 * @param coll
	 * @return
	 */
	public static String[] collectionToArray(Collection<String> coll) {
		if (coll != null && coll.size() > 0) {
			String[] toBeStored = new String[coll.size()];    
			coll.toArray(toBeStored);    
			return toBeStored;
		}
		return null;
	}
	
	
	/**
	 * 将数组转换成以逗号分割的字符串,用于in查询
	 * 如 'id1','id2','id3'
	 * @param coll
	 * @return
	 */
	public static String ArrayToIn(String[] ids) {
		if (ids != null && ids.length > 0) {
			StringBuffer sb = new StringBuffer();
			int i = 0;
			for (String str : ids) {
				if (i > 0)
					sb.append(",");
				i++;
				sb.append("'"+str+"'");
			}
			return sb.toString();
		}
		return "";
	}
	
	/**
	 * 将数组转换成以逗号分割的字符串
	 * 如id1,id2,id3
	 * @param coll
	 * @return
	 */
	public static String ArrayToStr(String[] ids) {
		if (ids != null && ids.length > 0) {
			StringBuffer sb = new StringBuffer();
			int i = 0;
			for (String str : ids) {
				if (i > 0)
					sb.append(",");
				i++;
				sb.append(str);
			}
			return sb.toString();
		}
		return "";
	}

	/**
	 * 按照指定的标志分割字符串到一个数组中
	 * 
	 * @param string
	 *            要操作的源字符串
	 * @param flag
	 *            分割标志字符串
	 * 
	 * @return 分割操作完成后的字符数组
	 */
	public static final String[] split(String string, String flag) {
		if (string == null) {
			return null;
		}
		if (string.equals("")) {
			// 如果传入的为空字符串则返回一个长度为0的数组
			return new String[0];
		}
		string = rtrim(string, flag);

		int size = 1;
		int i = 0;
		int len = string.length();
		while (i < len && i != -1) {
			i = string.indexOf(flag, i);
			if (i != -1) {
				size++;
				i++;
			}
		}

		String[] resultArray = new String[size];
		StringTokenizer tokenizer = new StringTokenizer(string, flag);
		i = 0;
		while (tokenizer.hasMoreTokens()) {
			resultArray[i] = tokenizer.nextToken();
			i++;
		}
		return resultArray;
	}

	/**
	 * 从字符串的右边删除指定的字符串
	 * 
	 * <p>
	 * <li>如果 flag 的长度大于 src 的长度返回空字符串("")
	 * <li>如果 src 的右侧有多个 flag 出现，则全部删除
	 * <li>如果 src为 null，则返回 null
	 * <li>如果 flag为 null或空，则不作处理直接返回src
	 * <li>如果 src 为空字符串("")，则返回空字符串("")
	 * </p>
	 * 
	 * @param string 源字符串
	 * @param flag 要删除的字符串
	 * 
	 * @return 已经删除了指定字符的字符串
	 */
	public static final String rtrim(String string, String flag)
	{
		int lenSrc = 0;
		int lenFlag = 0;
		String tmpStr = null;
		boolean hasNext = true;
		if (string == null)
		{
			return null;
		}
		if (flag == null || flag.equals(""))
		{
			return string;
		}
		if (string.equals(""))
		{
			return "";
		}
		lenSrc = string.length();
		lenFlag = flag.length();
		if (lenFlag > lenSrc)
		{
			return "";
		}
		while (lenSrc >= lenFlag && hasNext)
		{
			tmpStr = string.substring(lenSrc - lenFlag);
			if (tmpStr.equals(flag))
			{
				hasNext = true;
				string = string.substring(0, lenSrc - lenFlag);
				lenSrc = string.length();
			}
			else
			{
				hasNext = false;
			}
		}
		return string;
	}

	/**
	 * 从字符串的左边删除指定的字符串
	 * 
	 * <p>
	 * <li>如果 flag 的长度大于 src 的长度返回空字符串("")
	 * <li>如果 src 的左侧有多个 flag 出现，则全部删除
	 * <li>如果 src 为 null，则返回 null
	 * <li>如果 flag 为 null或空，则返回原字符串src
	 * <li>如果 src 为空字符串("")，则返回空字符串("")
	 * </p>
	 * 
	 * @param src 源字符串
	 * @param flag 要删除的字符串
	 * 
	 * @return 已经删除了指定字符的字符串
	 */
	public static final String ltrim(String src, String flag)
	{
		int lenSrc = 0;
		int lenFlag = 0;
		String tmpStr = null;
		boolean hasNext = true;
		if (src == null)
		{
			return null;
		}
		if (flag == null || flag.equals(""))
		{
			return src;
		}
		if (src.equals(""))
		{
			return "";
		}
		lenSrc = src.length();
		lenFlag = flag.length();
		if (lenFlag > lenSrc)
		{
			return "";
		}
		while (lenSrc >= lenFlag && hasNext)
		{
			tmpStr = src.substring(0, lenFlag);
			if (tmpStr.equals(flag))
			{
				hasNext = true;
				src = src.substring(lenFlag);
				lenSrc = src.length();
			}
			else
			{
				hasNext = false;
			}
		}
		return src;
	}
	/**
	 * 去除空格
	 * 
	 */
	public static String replaceBlank(String str){
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;		
	}
}
