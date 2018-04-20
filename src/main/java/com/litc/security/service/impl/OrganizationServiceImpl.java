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
import com.litc.security.model.Organization;
import com.litc.security.repository.OrganizationRepository;
import com.litc.security.service.OrganizationService;

@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {

	//@PersistenceContext
	//private EntityManager em;
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Override
	@Transactional
	public void addOrganization(Organization organization) {
		organizationRepository.save(organization);

	}

	@Override
	public String getOrgName(String upperCode) {
		return organizationRepository.getOrgName(upperCode);
	}
	
	@Override
	public Organization getOrganization(String orgCode) {
		return organizationRepository.findOne(orgCode);
	}

	@Override
	public Organization updateOrganization(Organization organization) {
		return null;
	}

	@Override
	public int isOrganizationUsed(String orgCode) {
		return organizationRepository.isOrganizationUsed(orgCode);
	}
	
	@Override
	public int isOrganizationUsed(String[] orgCodes) {
		return organizationRepository.isOrganizationUsed(orgCodes);
	}
	
	@Override
	@Transactional
	public void deleteOrganization(String orgCode) {
		organizationRepository.delete(orgCode);
	}

	@Override
	@Transactional
	public int deleteOrganizations(String[] orgCodes) {
		return organizationRepository.deleteOrganizationIn(orgCodes);
	}
	
	@Override
	public List<Organization> getOrganizations() {
		return organizationRepository.findAll();
	}
	
	@Override
	public List<Organization> getOrganizationsByOrgType(String[] types) {
		return organizationRepository.findOrganizationsByOrgType(types);
	}
	
	
	@Override
	public List<Organization> findByOrgType(String typeId) {
		return organizationRepository.findByOrgType(typeId);
	}
	
	@Override
	public Page<Organization> getOrganizationsByPages(int pageNo,int pageSize,Direction driection,String orderType,
			final Long orgType,final String secondOrgName,final String keyWord) {
		Page<Organization> userPage = organizationRepository.findAll(new Specification<Organization>() {
			@Override
			public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predList = new ArrayList<Predicate>();
				
				if(StringUtils.isNotBlank(secondOrgName)){
					Predicate p = cb.equal(root.get("secondOrgName").as(String.class), secondOrgName);
					predList.add(p);
				}
				if(StringUtils.isNotBlank(keyWord)){
					Predicate p = cb.or(cb.like(root.get("orgName").as(String.class), "%"+keyWord+"%"), cb.like(root.get("orgCode").as(String.class),  "%"+keyWord+"%"));
					predList.add(p);
				}
				if(orgType!=null){
					Predicate p = cb.equal(root.get("orgType").as(Long.class), orgType);
					predList.add(p);
				}
				Predicate[] sCondition=(Predicate[])predList.toArray(new Predicate[predList.size()]);
				query.where(sCondition);
				return null;
			}
		},new PageRequest(pageNo, pageSize,driection, orderType));
		return userPage;
	}

	public List<String> findOrganizationNames(String[] orgCodes){
		return organizationRepository.findOrganizationNames(orgCodes);
	}
	
	public Organization getOrganizationByCode(String orgCode){
		return organizationRepository.getOrganization(orgCode);
	}

	@Override
	public boolean isOrgCodeExist(String orgCode) {
		int i = organizationRepository.isOrgCodeExist(orgCode);
		return i>0?true:false;
	}

	@Override
	public Organization getOrgan(String orgName) {
		return organizationRepository.getOrganCode(orgName);
	}

	@Override
	public Organization getOrganization(Organization org) {
		return organizationRepository.getOrganization(org);
	}

	@Override
	public List<Organization> getFirstOrganization() {
		return organizationRepository.getFirstOrganization();
	}

	@Override
	public List<Organization> getOrganizationByFirstName(String firstName) {
		return organizationRepository.getOrganizationByFirstName(firstName);
	}

	@Override
	public List<Organization> getFirstOrganization(String firstName) {
		return organizationRepository.getFirstOrganization(firstName);
	}

	@Override
	public List<Organization> getOrganizationBySecondName(String secondName) {
		return organizationRepository.getSecondOrganization(secondName);
	}

	@Override
	public Organization updateOrgStatus(String orgID,String status) {
		//根据orgId获取 org，获取服务状态
		Organization organization  = organizationRepository.getOrg(orgID);
		if(organization==null){
			return null;
		}else{
			organization.setOrgServiceStatus(status);
			organizationRepository.save(organization);
			return  organization;
		}
	}

}
