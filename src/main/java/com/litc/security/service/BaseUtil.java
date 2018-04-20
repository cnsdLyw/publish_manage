package com.litc.security.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import com.litc.security.model.LogInfo;



@Transactional
public class BaseUtil {
	
	/**
	 * 
	 * @param resId {0:'111';1:'222';2:'333'}
	 * @return '111','222','333'
	 */
	public static String ListToString (List<String> resId){
		if(null == resId){
			return "";
		}
		if(resId.size() < 1){
			return "";
		}
		String tem = "";
		for(String temp:resId){
			tem = tem + "'" + temp + "',";
		}
		if(tem.length()>0){
			tem = tem.substring(0, tem.length()-1);
		}
		return tem;
	}
	
	/**
	 * 
	 * ListToStringForHQL
	 */
	public static String ListToStringForHQL (List<String> resId){
		if(null == resId){
			return "";
		}
		if(resId.size() < 1){
			return "";
		}
		String tem = "";
		for(String temp:resId){
			tem = tem + temp + ",";
		}
		if(tem.length()>0){
			tem = tem.substring(0, tem.length()-1);
		}
		return tem;
	}
	
	/**
	 * 根据SQL语句获取单个值
	 * @param em
	 * @param sql
	 * @return
	 */
	public static String getSingleValue(EntityManager em,String sql)
	{
		try
		{
			Query query=em.createNativeQuery(sql);
			List list=query.getResultList();
			if(list!=null && list.size()>0)
			{
				String s = (list.get(0).toString()); 
				return s;
			}
			else 
				return null;
		}
		finally
		{
			
		}
	}
	
	
	/*
	 * return 2015-12-04 14:31:25
	 */
	public static String getNowTime(){
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		return time;
	}
	/*
	 * 
	 */
	public static  LogInfo addOperateLog(HttpServletRequest request,String operateName){
		//SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		//if(null != SecurityContextHolder.getContext().getAuthentication().getPrincipal()){
		   UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	       String ip = request.getHeader("x-forwarded-for"); 
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	           ip = request.getHeader("Proxy-Client-IP"); 
	       } 
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	           ip = request.getHeader("WL-Proxy-Client-IP"); 
	       } 
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	           ip = request.getRemoteAddr(); 
	       }
	       
//	       if(operateName=="shanchu"){
//	    	   operateName = "图书删除";
//	       }
//	       if(operateName=="caiji"){
//	    	   operateName = "图书采集";
//	       }
			LogInfo logInfo = new LogInfo();	
			logInfo.setOperateIp(ip);
			logInfo.setOperateName(operateName);
			logInfo.setOperateTime(new Date());
			logInfo.setOperateType("operate");
			logInfo.setOrgCode((String)request.getSession().getAttribute("loginOrgCode"));
			logInfo.setOperateUser(userDetails.getUsername());
			return logInfo;
	}
	/**
	 * 将字符串text按照splitValue拆分成List<String>
	 * @param text
	 * @param splitValue
	 * @return
	 */
	public static List<String> StringToList(String text,String splitValue)
	{
		List<String> returnlist=new ArrayList<String>();
		if(text==null)
			return returnlist;
		String[] strings=text.split(splitValue);
		for(String temp:strings){
			returnlist.add(temp);
		}
		return returnlist;
	}
	
	/**
	 * 将字符串text按照splitValue拆分成List<String>
	 * @param text
	 * @param splitValue
	 * @return
	 */
	public static List<Integer> IntegerToList(String text,String splitValue)
	{
		List<Integer> returnlist=new ArrayList<Integer>();
		if(text==null)
			return returnlist;
		String[] strings=text.split(splitValue);
		for(String temp:strings){
			returnlist.add(Integer.parseInt(temp));
		}
		return returnlist;
	}
	
	/**
	 *获取总页数
	 * @author lu.j
	 * @param totalCount
	 * @param pageSize
	 * @return
	 */
	public static int getTotalPage(int totalCount,int pageSize){
		
		int totalPage=totalCount%pageSize==0? totalCount/pageSize:(totalCount/pageSize+1);
		
		return totalPage>0?totalPage:1;
	}
	
	

}
