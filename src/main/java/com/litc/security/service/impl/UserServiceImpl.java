package com.litc.security.service.impl;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.litc.security.model.Organization;
import com.litc.security.model.User;
import com.litc.security.repository.OrganizationRepository;
import com.litc.security.repository.RoleRepository;
import com.litc.security.repository.UserRepository;
import com.litc.security.service.UserService;


@Service("userService")
//@Transactional(value="transactionManager")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Override
	public void addUser(User user) {
		user.setLastModifyTime(new Date());
		userRepository.save(user);

	}

	@Override
	public User getUser(Long id) {
	    //return userRepository.findById(id);
		return userRepository.findOne(id);
	}
	
	@Override
	public User loadUser(String loginName) {
		List<User> list = userRepository.findByLoginName(loginName);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public User updateUser(User user) {
		return null;
	}

	@Override
	public int isUserHasRole(Long id) {
		return userRepository.isUserHasRole(id);
	}
	
	@Override
	public int isUserHasRole(Long[] id) {
		return userRepository.isUserHasRole(id);
	}
	
	@Override
	public void deleteUser(Long id) {
		userRepository.delete(id);
	}

	@Override
	@Transactional
	public int deleteUsers(Long[] ids) {
		return userRepository.deleteUserIn(ids);
	}
	
	@Override
	@Transactional
	public void deletUsers(String orgCode) {
		userRepository.deletUsers(orgCode);
	}
	
	@Override
	public User getUser(String idStr) {
		return null;
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findUsers();
		//return userRepository.findUsers();
	}
	
	@Override
	public List<User> getUsers(String orgCode) {
		return userRepository.findByOrgCode(orgCode);
	}
	
	
	
	@Override
	public Page<User> getUsersByPages(int pageNo,int pageSize,
			Direction driection,String orderType,final String orgCode,final String keyWord) {
		/*List<Object[]> list = userRepository.searchByNativeQuery();
		for(Object[] object: list){
			String name =(String)object[0];
			String orgName =(String)object[1];
			System.out.println( name+"-- "+orgName);
		}*/
		
		Page<User> userPage = userRepository.findAll(new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predList = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(keyWord)){
					Organization organization = organizationRepository.getOne(orgCode);
					Predicate pOrgCode = cb.equal(root.get("organization").as(Organization.class), organization);  
					Predicate p1 = cb.like(root.get("loginName").as(String.class), "%"+keyWord+"%");  
					Predicate p2 = cb.like(root.get("name").as(String.class),  "%"+keyWord+"%");  
					Predicate p = cb.and(pOrgCode,cb.or(p1,p2));
					predList.add(p);
				}else{
					Organization organization = organizationRepository.getOne(orgCode);
					Predicate pOrgCode = cb.equal(root.get("organization").as(Organization.class), organization);
					predList.add(pOrgCode);
				}
				
				Predicate[] sCondition=(Predicate[])predList.toArray(new Predicate[predList.size()]);
				query.where(sCondition);
				return null;
			}
		},new PageRequest(pageNo, pageSize,driection, orderType));
		return userPage;
	}

	@Override
	public boolean isLoginNameExist(String loginName) {
		int i = userRepository.isLoginNameExist(loginName);
		return i>0?true:false;
	}

	@Override
	public boolean isEmailExist(String email) {
		int i = userRepository.isEmailExist(email);
		return i>0?true:false;
	}
	
	@Override
	public boolean isEmailExist(Long id,String email) {
		int i = 0;
		if(id!=null){
			i = userRepository.isEmailExist(id,email);
		}else{
			i = userRepository.isEmailExist(email);
		}
		return i>0?true:false;
	}

	@Override
	public List<Map<String, String>> getUsersByRole(String loginOrgCode,String authorityKey) {
		List<Long> roleIds = roleRepository.getRoleId(authorityKey);
		List<Map<String,String>> userLists = new ArrayList<Map<String,String>>();
		if(roleIds!=null&&roleIds.size()>0){
			for(int i=0;i<roleIds.size();i++){
				BigInteger bigInteger = new BigInteger(String.valueOf(roleIds.get(i)));
				List<Object[]> list = roleRepository.getUsersByRole(loginOrgCode,bigInteger.longValue());
				if(list!=null&&list.size()>0){
					for(Object[] object: list){
						Map<String,String> map = new HashMap<String,String>();
						map.put("loginName", (String)object[0]);
						map.put("name", (String)object[1]);
						if(userLists!=null&&userLists.contains(map)){
							continue;
						}
						userLists.add(map);
					}
				}
			}
			return userLists;
		}
		return null;
	}
	

	@Override
	public List<String> getUserAuthoritys(String loginName) {
		return userRepository.getUserAuthoritys(loginName);
	}

	@Override
	public User updateUserStatus(String userID, String status) {
		/*User user= userRepository.findByUserId(userID);
		//user.setUserServiceStatus(status);   
		userRepository.save(user);
		return user;*/
		return null;
		
	}

}
