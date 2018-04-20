package com.litc.security.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.litc.security.common.page.PageParam;
import com.litc.security.model.Organization;
import com.litc.security.model.Role;

public interface RoleService {

	/**
	 * 添加角色
	 * @param role
	 */
	public void addRole(Role role);

	/**
	 * 获取角色
	 * @param id
	 * @return
	 */
	public Role getRole(Long id);

	/**
	 *  修改角色
	 * @param user
	 * @return
	 */
	public Role updateRole(Role role);
	
	/**
	 * 角色是否被用户使用
	 * @param id
	 * @return
	 */
	public int isRoleUsedByUser(Long id);
	
	/**
	 * 角色是否被用户使用
	 * @param id
	 * @return
	 */
	public int isRoleUsedByUser(Long[] id);
	
	/**
	 * 角色是否使用
	 * @param id
	 * @return
	 */
	public int isRoleUsed(Long id);
	
	/**
	 * 角色是否使用
	 * @param id
	 * @return
	 */
	public int isRoleUsed(Long[] id);
	

	/**
	 * 删除角色,根据编号删除
	 * @param id
	 */
	public void deleteRole(Long id);

	/**
	 * 批量删除角色
	 * @param ids
	 * @return
	 */
	public int deleteRoles(Long[] ids);
	/**
	 * 
	 * @param loginName
	 * @return
	 */
	public Role getRole(String idStr);

	/**
	 * 查询所有角色
	 * @return
	 */
	public List<Role> getRoles();
	
	/**
	 * 根据平台查询角色
	 * @return
	 */
	public List<Role> getRoles(String organizationFlag);
	
	/**
	 * 分页带条件查询角色
	 * @param pageNo
	 * @param pageSize
	 * @param driection
	 * @param orderType
	 * @param args
	 * @return
	 */
	public Page<Role> getRolesByPages(int pageNo,int pageSize,Direction driection,String orderType,String keyWord);
	
	/**
	 * 是否存在角色 ，根据名称判断， 返回true表示已存在
	 * @param authorityKey
	 * @return
	 */
	public boolean isRoleExist(String roleName);
	
	/**
	 * 是否存在权限值 ，根据名称判断， 返回true表示已存在
	 * @param id
	 * @param authorityKey
	 * @return
	 */
	public boolean isRoleExist(Long id,String roleName);
	
}
