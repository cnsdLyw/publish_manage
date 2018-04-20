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

import com.litc.security.model.Role;
import com.litc.security.repository.RoleRepository;
import com.litc.security.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	@Transactional
	public void addRole(Role role) {
		role.setLastModifyTime(new Date());
		if(StringUtils.isBlank(role.getOrganizationFlag())){
			role.setOrganizationFlag("0");
		}
		roleRepository.save(role);

	}

	@Override
	public Role getRole(Long id) {
		return roleRepository.findOne(id);
	}

	@Override
	public Role updateRole(Role role) {
		return null;
	}

	@Override
	public int isRoleUsedByUser(Long id) {
		return roleRepository.isRoleUsedByUser(id);
	}
	
	@Override
	public int isRoleUsedByUser(Long[] id) {
		return roleRepository.isRoleUsedByUser(id);
	}
	
	@Override
	public int isRoleUsed(Long id) {
		return roleRepository.isRoleUsed(id);
	}
	
	@Override
	public int isRoleUsed(Long[] id) {
		return roleRepository.isRoleUsed(id);
	}
	
	@Override
	@Transactional
	public void deleteRole(Long id) {
		roleRepository.delete(id);
	}

	@Override
	@Transactional
	public int deleteRoles(Long[] ids) {
		return roleRepository.deleteRoleIn(ids);
	}
	
	@Override
	public Role getRole(String idStr) {
		return null;
	}

	@Override
	public List<Role> getRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Page<Role> getRolesByPages(int pageNo,int pageSize,Direction driection,String orderType,final String keyWord) {
		Page<Role> userPage = roleRepository.findAll(new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predList = new ArrayList<Predicate>();
				/*if(StringUtils.isNotBlank(keyWord)){
					Predicate p = cb.and(cb.equal(root.get("orgCode").as(String.class), orgCode), cb.or(cb.like(root.get("roleName").as(String.class), "%"+keyWord+"%")));
					predList.add(p);
				}else{
					Predicate p = cb.and(cb.equal(root.get("orgCode").as(String.class), orgCode));
					predList.add(p);
				}*/
				if(StringUtils.isNotBlank(keyWord)){
					Predicate p =  cb.or(cb.like(root.get("roleName").as(String.class), "%"+keyWord+"%"));
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
	public List<Role> getRoles(String organizationFlag) {
		return roleRepository.findByOrganizationFlag(organizationFlag);
	}


	@Override
	public boolean isRoleExist(String roleName) {
		int i = roleRepository.isRoleExist(roleName);
		return i>0?true:false;
	}

	@Override
	public boolean isRoleExist(Long id, String roleName) {
		int i = 0;
		if(id!=null){
			i = roleRepository.isRoleExist(id, roleName);
		}else{
			i = roleRepository.isRoleExist(roleName);
		}
		return i>0?true:false;
	}

}
