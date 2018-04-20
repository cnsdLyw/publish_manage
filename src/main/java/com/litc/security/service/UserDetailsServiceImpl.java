package com.litc.security.service;



import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.litc.common.util.UserCacheManager;
import com.litc.security.model.Authority;
import com.litc.security.model.Role;


/**
 * 实现UserDetailsService接口,回调用户信息
 * 
 */
@Transactional(value="transactionManager",readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 获取用户Details信息的回调函数.
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
		com.litc.security.model.User user = userService.loadUser(userName);
		if (user == null)
			throw new UsernameNotFoundException("用户" + userName + " 不存在");
		Collection<GrantedAuthority> grantedAuths = obtainGrantedAuthorities(user);

		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		User userdetail = new User(
				user.getLoginName(), user.getPassWord(), enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, grantedAuths);

		return userdetail;
	}

	/**
	 * 获得用户所有角色的权限集合.
	 */
	private Set<GrantedAuthority> obtainGrantedAuthorities(com.litc.security.model.User user) {

		try { 
			/*Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
			for (Role role : user.getRoleList()) {
				for (Authority authority : role.getAuthorityList()) {
					authSet.add(new SimpleGrantedAuthority(authority.getAuthorityKey()));
				}
			}
			return authSet;*/
			//long l1 = System.currentTimeMillis();
		    Set<GrantedAuthority> set = UserCacheManager.getUserAuthority(user.getLoginName());
		    
		    if (set==null||set.size()==0) {
		    	set = new HashSet<GrantedAuthority>();
				for (Role role : user.getRoleList()) {
					for (Authority authority : role.getAuthorityList()) {
						System.out.println(" ----------   "+authority.getAuthorityKey());
						set.add(new SimpleGrantedAuthority(authority.getAuthorityKey()));
					}
				}
			}
			//long l2 = System.currentTimeMillis();
	        //System.out.println("初始化用户权限，用时"+(l2-l1)+"ms。");
	        return set;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
