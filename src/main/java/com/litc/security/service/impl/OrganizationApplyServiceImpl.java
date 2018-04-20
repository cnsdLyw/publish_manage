package com.litc.security.service.impl;

import java.util.ArrayList;
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
import com.litc.security.model.OrganizationApply;
import com.litc.security.repository.OrganizationApplyRepository;
import com.litc.security.service.OrganizationApplyService;

@Service("organizationApplyService")
public class OrganizationApplyServiceImpl implements OrganizationApplyService {

	@Autowired
	private OrganizationApplyRepository organizationApplyRepository;
	
	@Override
	@Transactional
	public void addOrganizationApply(OrganizationApply organizationApply) {
		organizationApplyRepository.save(organizationApply);

	}

	@Override
	public OrganizationApply getOrganizationApply(Long id) {
		return organizationApplyRepository.findOne(id);
	}
	
	@Override
	@Transactional
	public OrganizationApply updateOrganizationApply(
			OrganizationApply organizationApply) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void deleteOrganizationApply(Long id) {
		organizationApplyRepository.delete(id);
	}

	@Override
	@Transactional
	public int deleteOrganizationApplys(Long[] ids) {
		return organizationApplyRepository.deleteOrganizationApplyIn(ids);
	}

	@Override
	public List<OrganizationApply> getOrganizationApplysByOrgType(Long[] types) {
		return organizationApplyRepository.findOrganizationApplysByOrgType(types);
	}

	@Override
	public List<OrganizationApply> findByOrgType(Long typeId) {
		return organizationApplyRepository.findByOrgType(typeId);
	}

	@Override
	public List<OrganizationApply> getOrganizationApplys() {
		return organizationApplyRepository.findAll();
	}
//
//	Predicate p1 = cb.and(cb.equal(root.get("orgType").as(Long.class), "%"+orgType+"%"),cb.equal(root.get("orgStatus").as(Integer.class),  "%"+orgStatus+"%"));
//	Predicate p2 = cb.or(cb.like(root.get("orgName").as(String.class), "%"+keyWord+"%"), cb.like(root.get("orgCode").as(String.class),  "%"+keyWord+"%")); 
//	Predicate p = cb.and(p1,p2);
//	predList.add(p);
	@Override
	public Page<OrganizationApply> getOrganizationApplysByPages(int pageNo,
			int pageSize, Direction driection, String orderType,
			final Long orgTypes,final Integer orgStatu,final String keyWord,final String secondOrgName) {
		Page<OrganizationApply> userPage = organizationApplyRepository.findAll(new Specification<OrganizationApply>() {
			@Override
			public Predicate toPredicate(Root<OrganizationApply> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predList = new ArrayList<Predicate>();
				Long orgType = orgTypes;
				Integer orgStatus = orgStatu;
					if(StringUtils.isNotBlank(secondOrgName)){
						Predicate p = cb.equal(root.get("secondOrgName").as(String.class), secondOrgName);
						predList.add(p);
					}
					if(orgType!=null || orgStatus!=null || StringUtils.isNotBlank(keyWord)){
						if(orgType!=null && orgStatus!=null && StringUtils.isBlank(keyWord)){
							Predicate p = cb.and(cb.equal(root.get("orgType").as(Long.class), orgType),cb.equal(root.get("orgStatus").as(Integer.class),  orgStatus));
							predList.add(p);
						}else if(orgType!=null && orgStatus!=null && StringUtils.isNotBlank(keyWord)){
							Predicate p = cb.and(cb.and(cb.equal(root.get("orgType").as(Long.class), orgType),cb.equal(root.get("orgStatus").as(Integer.class),  orgStatus)), cb.or(cb.like(root.get("orgName").as(String.class), "%"+keyWord+"%"), cb.like(root.get("orgCode").as(String.class),  "%"+keyWord+"%")));
							predList.add(p);
						}else if((orgType!=null || orgStatus!=null)&& StringUtils.isNotBlank(keyWord)){
							Predicate p = cb.and(cb.or(cb.equal(root.get("orgType").as(Long.class), orgType),cb.equal(root.get("orgStatus").as(Integer.class),  orgStatus)), cb.or(cb.like(root.get("orgName").as(String.class), "%"+keyWord+"%"), cb.like(root.get("orgCode").as(String.class),  "%"+keyWord+"%")));
							predList.add(p);
						}else if((orgType!=null || orgStatus!=null)&& StringUtils.isBlank(keyWord)){
							Predicate p = cb.or(cb.equal(root.get("orgType").as(Long.class), orgType),cb.equal(root.get("orgStatus").as(Integer.class),  orgStatus));
							predList.add(p);
						}else if((orgType==null || orgStatus==null)&& StringUtils.isNotBlank(keyWord)){
							Predicate p = cb.or(cb.like(root.get("orgName").as(String.class), "%"+keyWord+"%"), cb.like(root.get("orgCode").as(String.class),  "%"+keyWord+"%"));
							predList.add(p);
						}else if((orgType==null || orgStatus==null)&& StringUtils.isNotBlank(keyWord)){
							Predicate p = cb.or(cb.like(root.get("orgName").as(String.class), "%"+keyWord+"%"), cb.like(root.get("orgCode").as(String.class),  "%"+keyWord+"%"));
							predList.add(p);
						}
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
		int i = organizationApplyRepository.isLoginNameExist(loginName);
		return i>0?true:false;
	}

	@Override
	public boolean isEmailExist(String email) {
		int i = organizationApplyRepository.isEmailExist(email);
		return i>0?true:false;
	}

	@Override
	public boolean isOrgCodeExist(String orgCode) {
		int i = organizationApplyRepository.isOrgCodeExist(orgCode);
		return i>0?true:false;
	}

}
