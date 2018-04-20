package com.litc.security.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.litc.security.model.Authority;
import com.litc.security.repository.AuthorityRepository;
import com.litc.security.service.AuthorityService;

@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {

	/*@PersistenceContext(unitName = "userPU")    
	private EntityManager entityManager;  
	*/
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Override
	@Transactional
	public void addAuthority(Authority authority) {
		authority.setLastModifyTime(new Date());
		authorityRepository.save(authority);

	}

	@Override
	public Authority getAuthority(Long id) {
		return authorityRepository.findOne(id);
	}

	@Override
	@Transactional
	public Authority updateAuthority(Authority authority) {
		return null;
	}

	@Override
	public int isAuthorityUsed(Long id) {
		return authorityRepository.isAuthorityUsed(id);
	}
	
	@Override
	public int isAuthorityUsed(Long[] id) {
		return authorityRepository.isAuthorityUsed(id);
	}
	
	@Override
	@Transactional
	public void deleteAuthority(Long id) {
		authorityRepository.delete(id);
	}

	@Override
	public int deleteAuthoritys(Long[] ids) {
		return authorityRepository.deleteAuthorityIn(ids);
	}
	
	@Override
	public Authority getAuthority(String idStr) {
		return null;
	}

	@Override
	public List<Authority> getAuthoritys() {
		return authorityRepository.findAll();
	}
	
	@Override
	public List<Authority> getAuthoritys(int status) {
		return authorityRepository.findAll();
	}
	
	/*@Override
	@Transactional
	public Page<Authority> getAuthoritiesByPages(final String authName,final String authKey,int pageNo,int pageSize) {*/
		
		/*String sql = "SELECT name FROM sys_user";
		// 创建原生SQL查询QUERY实例
		Query query = entityManager.createNativeQuery(sql);
		List<String> list = (List<String>) query.getResultList();
		System.out.println("LIST  " + list.size());
		for (String str : list) {
			System.out.println("loginName  " + str);
		}
		*/
		//Page<Authority> userPage =authorityRepository.findAll(new PageRequest(pageNo, pageSize,Sort.Direction.DESC, "id"));
	
	/*
		Page<Authority> authorityPage = authorityRepository.findAll(new Specification<Authority>() {
			@Override
			public Predicate toPredicate(Root<Authority> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Path<String> nameExp = root.get("authorityName");//权限名称
				Path<String> keyExp = root.get("authorityKey");//权限值
				List<Predicate> predList = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(authName)){
					predList.add(cb.like(nameExp, "%"+authName+"%"));
				}
				if(StringUtils.isNotBlank(authKey)){
					predList.add(cb.like(keyExp, "%"+authKey+"%"));
				}
				Predicate[] sCondition=(Predicate[])predList.toArray(new Predicate[predList.size()]);
				//Predicate[] ss={cb.like(nameExp, "%文章%"), cb.like(key, "%ROLER_%")};
				query.where(sCondition);
				return null;
			}
		},new PageRequest(pageNo, pageSize,Sort.Direction.DESC, "id"));
		//Page<Authority> userPage =authorityRepository.findByAuthorityNameLikeAndAuthorityKeyLike("%文章%","%ROLER_A%", new PageRequest(pageNo, pageSize,Sort.Direction.DESC, "id"));
		return authorityPage;
	}*/

	@Override
	public Page<Authority> getAuthoritiesByPages(int pageNo,int pageSize,Direction driection,String orderType,final String keyWord) {
		/*String sql = "select u.name,org.orgName from security_sys_user u join security_sys_organization org on u.organizationId = org.id";
		// 创建原生SQL查询QUERY实例
		Query  query = entityManager.createNativeQuery(sql);
		List<Object[]>  list =  query.getResultList();
		for(Object[] object: list){
			String name =(String)object[0];
			String password =(String)object[1];
			System.out.print(name+": "+password);
		}*/
		
		Page<Authority> userPage = authorityRepository.findAll(new Specification<Authority>() {
			@Override
			public Predicate toPredicate(Root<Authority> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predList = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(keyWord)){
					Predicate p1 = cb.like(root.get("authorityName").as(String.class), "%"+keyWord+"%");  
					Predicate p2 = cb.like(root.get("authorityKey").as(String.class),  "%"+keyWord+"%");  
					Predicate p = cb.or(p1,p2);
					predList.add(p);
				}
				Predicate[] sCondition=(Predicate[])predList.toArray(new Predicate[predList.size()]);
				query.where(sCondition);
				return null;
			}
		},new PageRequest(pageNo, pageSize,driection, orderType));
		return userPage;
	}
	
	@Override
	public boolean isAuthorityKeyExist(String authorityKey) {
		int i = authorityRepository.isAuthorityKeyExist(authorityKey);
		return i>0?true:false;
	}
	
	@Override
	public boolean isAuthorityKeyExist(Long id,String authorityKey) {
		int i = 0;
		if(id!=null){
			i = authorityRepository.isAuthorityKeyExist(id,authorityKey);
		}else{
			i = authorityRepository.isAuthorityKeyExist(authorityKey);
		}
		return i>0?true:false;
	}
	@Override
	public boolean isAuthorityNameExist(String authorityName) {
		int i = authorityRepository.isAuthorityNameExist(authorityName);
		return i>0?true:false;
	}
	
	@Override
	public boolean isAuthorityNameExist(Long id,String authorityName) {
		int i = 0;
		if(id!=null){
			i = authorityRepository.isAuthorityNameExist(id,authorityName);
		}else{
			i = authorityRepository.isAuthorityNameExist(authorityName);
		}
		return i>0?true:false;
	}

	@Override
	public List<Authority> getAuthoritysByType(int type) {
		return authorityRepository.findAuthorityByType(type);
	}
}
