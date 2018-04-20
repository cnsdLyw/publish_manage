package com.litc.security.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.litc.security.model.User;

public interface UserService {
	
	/**
	 * 添加用户
	 * @param user
	 */
	public void addUser(User user);

	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public User getUser(Long id);
	
	/**
	 * 获取用户
	 * 
	 * @param loginName
	 * @return
	 */
	public User loadUser(String loginName);
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public User updateUser(User user);
	
	
	/**
	 * 用户是否有角色
	 * @param id
	 * @return
	 */
	public int isUserHasRole(Long id);
	
	/**
	 * 用户是否有角色
	 * @param id
	 * @return
	 */
	public int isUserHasRole(Long[] id);
	

	/**
	 * 删除用户,根据编号删除
	 * @param id
	 */
	public void deleteUser(Long id);

	/**
	 * 批量删除用户
	 * @param ids
	 * @return
	 */
	public int deleteUsers(Long[] ids);
	/**
	 * 
	 * @param loginName
	 * @return
	 */
	public User getUser(String idStr);

	/**
	 * 查询所有用户
	 * @return
	 */
	public List<User> getUsers();
	
	/**
	 * 根据机构码查询用户
	 * @return
	 */
	public List<User> getUsers(String orgCode);

	/**
	 * 分页带条件查询用户
	 * @param pageNo
	 * @param pageSize
	 * @param driection
	 * @param orderType
	 * @param args
	 * @return
	 */
	public Page<User> getUsersByPages(int pageNo,int pageSize,Direction driection,String orderType,String orgCode,String keyWord);
	
	/**
	 * 是否存在用户名 是否存在用户名 返回true表示已存在
	 * @param loginName
	 * @return
	 */
	public boolean isLoginNameExist(String loginName);
	
	/**
	 * 是否存在邮箱 返回true表示已存在
	 * @param email
	 * @return
	 */
	public boolean isEmailExist(String email);
	
	/**
	 * 是否存在邮箱 返回true表示已存在
	 * @param id
	 * @param email
	 * @return
	 */
	public boolean isEmailExist(Long id,String email);
	
	
	List<Map<String,String>> getUsersByRole(String loginOrgCode,String authorityKey);

	public void deletUsers(String orgCode);
	
	public List<String> getUserAuthoritys(String loginName);
	/**
	 * 更新用户服务状态
	 * @param userID
	 * @return
	 */
	public User updateUserStatus(String userID,String status);
}
