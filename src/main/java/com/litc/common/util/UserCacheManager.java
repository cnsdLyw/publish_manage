package com.litc.common.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import com.litc.common.bean.SpringBeanUtil;
import com.litc.security.model.User;
import com.litc.security.service.UserService;

@Transactional(value="transactionManager",readOnly = true)
public class UserCacheManager {
	private static Logger log = LoggerFactory.getLogger(UserCacheManager.class);

	private final static Map<String,Set<GrantedAuthority>> authorityCache = new HashMap<String,Set<GrantedAuthority>>();

	private final static Map<String,String> loginUserMap = new HashMap<String,String>();
	
	/**
	 * 初始化指定机构的用户权限信息
	 */
	public synchronized static void loadUserAuthorityCache(String orgCode) {
		UserService userService = (UserService) SpringBeanUtil.getBean("userService");
		// 查询所有用户
		List<User> list = userService.getUsers(orgCode);
		// 查询用户的角色和权限信息，生成缓存资源
		for (User user : list) {
			Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
			List<String> authorityList = userService.getUserAuthoritys(user.getLoginName());
			for (String authorityKey : authorityList) {
				authSet.add(new SimpleGrantedAuthority(authorityKey));
			}
			authorityCache.put(user.getLoginName(), authSet);
		}
	}
	
	/**
	 * 初始化用户权限信息
	 */
	public synchronized static void loadUserAuthorityCache() {
		UserService userService = (UserService) SpringBeanUtil.getBean("userService");
		// 查询所有用户
		List<User> list = userService.getUsers();
		// 查询用户的角色和权限信息，生成缓存资源
		for (User user : list) {
			Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
			List<String> authorityList = userService.getUserAuthoritys(user.getLoginName());
			for (String authorityKey : authorityList) {
				authSet.add(new SimpleGrantedAuthority(authorityKey));
			}
			/*for (Role role : user.getRoleList()) {
				for (Authority authority : role.getAuthorityList()) {
					authSet.add(new SimpleGrantedAuthority(authority.getAuthorityKey()));
				}
			}*/
			authorityCache.put(user.getLoginName(), authSet);
		}
	}
	
	/**
	 * 获取登录用户权限
	 */
	public static Set<GrantedAuthority> getUserAuthority(String loginName) {
		return authorityCache.get(loginName);
	}
	
	public static void addUser(String loginName,String sessionId){
		loginUserMap.put(loginName, sessionId);
	}
	
	public static String getUserSessionId(String loginName){
		return loginUserMap.get(loginName);
	}
	
	public static void removeUser(String loginName){
		loginUserMap.remove(loginName);
	}
	
	public static boolean isUserLogin(String loginName){
		return loginUserMap.containsKey(loginName)?true:false;
	}
}
