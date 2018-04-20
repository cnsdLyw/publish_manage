package com.litc.security.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.litc.security.model.Classification;

public class BaseController<T> {
	
	protected static final Direction SORT_TYPE_DESC = Sort.Direction.DESC;
	
	protected static final Direction SORT_TYPE_ASC = Sort.Direction.ASC;
	
	protected List<Classification> getOrgTypes(){
		List<Classification> list = new ArrayList<Classification>();
		Classification class1 = new Classification("1", "图书出版机构");
		Classification class2 = new Classification("2", "图书馆");
		Classification class3 = new Classification("3", "图书发行机构");
		Classification class4 = new Classification("4", "数据加工机构");
		Classification class5 = new Classification("5", "零售机构");
		Classification class6 = new Classification("6", "其他机构");
		list.add(class1);
		list.add(class2);
		list.add(class3);
		list.add(class4);
		list.add(class5);
		list.add(class6);
		return list;
		
	}
	protected List<Classification> getScoreLimit(){
		List<Classification> list = new ArrayList<Classification>();
		Classification class1 = new Classification("1", "0-500");
		Classification class2 = new Classification("2", "500-2000");
		Classification class3 = new Classification("3", "2000-5000");
		Classification class4 = new Classification("4", "5000-10000");
		Classification class5 = new Classification("5", "10000-50000");
		Classification class6 = new Classification("6", "50000-100000");
		Classification class7 = new Classification("7", "100000-1000000");
		list.add(class1);
		list.add(class2);
		list.add(class3);
		list.add(class4);
		list.add(class5);
		list.add(class6);
		list.add(class7);
		return list;
		
	}
	
	/**
	 * 当前页码,从0开始
	 */
	protected int pageNo;
	
	protected Page<T> page;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}

	/**
	 * @param request
	 * @param paramName 参数名称
	 * @return 从request获取参数对应字符串值
	 */
	public static String getString(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		return value != null ? value : "";
	}

	/**
	 * @param request
	 * @param paramName 参数名称
	 * @return 从request获取参数对应整型值
	 */
	protected int getInt( String paramName) {
		if (paramName == null || paramName.length() == 0)
			return 0;
		else {
			try {
				return Integer.parseInt(paramName);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	}
	
	/**
	 * @param request
	 * @param paramName 参数名称
	 * @return 从request获取参数对应整型值
	 */
	protected int getInt(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		if (value == null || value.length() == 0)
			return 0;
		else {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	}
	
	/**
	 * 将ID数组由String类型转换成Long型
	 * @param ids
	 * @return
	 */
	protected Long[] getIdArray(String ids){
		String[] str1 = ids.split(",");
		Long[] str2 = new Long[str1.length];
		for (int i = 0; i < str1.length; i++) {
			str2[i] = Long.valueOf(str1[i]);
		}
		return str2;
	}
	
	/**
	 * 将ID数组由String类型转换成Long型
	 * @param ids
	 * @return
	 */
	protected String[] getOrgCodeArray(String ids){
		String[] str1 = ids.split(",");
		String[] str2 = new String[str1.length];
		for (int i = 0; i < str1.length; i++) {
			str2[i] = str1[i];
		}
		return str2;
	}
	
	public boolean isSaveSuccess(Long id){
		if(id!=null&&!id.equals("0")){
			return true ;
		}
		return false ;
	}
	
	public boolean isSaveSuccess(String orgCode){
		if(StringUtils.isNotBlank(orgCode)){
			return true ;
		}
		return false ;
	}
	
	/**
	 * @param request
	 * @param paramName
	 *            参数名称
	 * @return 从request获取参数对应整型值
	 */
	protected long getLong(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		if (value == null || value.length() == 0)
			return 0;
		else {
			try {
				return Long.parseLong(value);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	}
	
	public List<Map<String,String>> getStatus(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		Map<String,String> map1 = new HashMap<String,String>();
		map1.put("statusId", "0");
		map1.put("statusName", "待审核");
		list.add(map1);
		
		Map<String,String> map2 = new HashMap<String,String>();
		map2.put("statusId", "1");
		map2.put("statusName", "审核通过");
		list.add(map2);
		
		Map<String,String> map3 = new HashMap<String,String>();
		map3.put("statusId", "2");
		map3.put("statusName", "审核不通过");
		list.add(map3);
		
		return list;
	}
}
