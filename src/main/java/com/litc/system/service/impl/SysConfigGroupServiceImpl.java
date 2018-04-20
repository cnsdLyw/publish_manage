package com.litc.system.service.impl;

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

import com.litc.system.model.SysConfigGroup;
import com.litc.system.model.SysConfigure;
import com.litc.system.repository.SysConfigGroupRepository;
import com.litc.system.service.SysConfigGroupService;

@Service("sysConfigGroupService")
public class SysConfigGroupServiceImpl implements SysConfigGroupService{

	@Autowired
	private SysConfigGroupRepository sysConfigGroupRepository;
	
	@Override
	public List<SysConfigGroup> getSysConfigGroup(Long parentId){
		return sysConfigGroupRepository.getSysConfigGroup(parentId);
	}
	
	@Override
	public SysConfigGroup getSysConfigGroupBy(Long id){
		return sysConfigGroupRepository.findOne(id);
	}
	
	@Override
	public int getCounts(Long typeid){
		return sysConfigGroupRepository.getCounts(typeid);
	}
	
	@Override
	public List<SysConfigGroup> getSysConfigGroup(){
		return sysConfigGroupRepository.getSysConfigGroup();
	}
	
	@Override
	@Transactional
	public void addSysConfigGroup(SysConfigGroup sysConfigGroup){
		sysConfigGroupRepository.save(sysConfigGroup);
	}
	
	@Override
	@Transactional
	public void delete(Long id){
		sysConfigGroupRepository.delete(id);
	}
	
	@Override
	public Page<SysConfigGroup> getSysConfigGroupsByPage(int pageNo, int pageSize, Direction driection, String orderType,final Long typeid,final String keyWord) {
		Page<SysConfigGroup> userPage = sysConfigGroupRepository.findAll(new Specification<SysConfigGroup>() {
		@Override
		public Predicate toPredicate(Root<SysConfigGroup> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
			List<Predicate> predList = new ArrayList<Predicate>();
			Predicate p = null;
			Predicate p1 = cb.equal(root.get("parentId").as(Long.class), typeid);
			p = p1;
			if(StringUtils.isNotBlank(keyWord)){
				Predicate p2 = cb.like(root.get("groupName").as(String.class), "%"+keyWord+"%");  
				p = cb.and(p1,p2);
			}
			predList.add(p);
			Predicate[] sCondition=(Predicate[])predList.toArray(new Predicate[predList.size()]);
			query.where(sCondition);
			return null;
		}
	},new PageRequest(pageNo, pageSize,driection, orderType));
	return userPage;
	}
	
	@Override
	public boolean isSysConfigureNameExist(String configureName) {
		int i = sysConfigGroupRepository.isSysConfigureNameExist(configureName);
		return i>0?true:false;
	}
	
	@Override
	public boolean isSysConfigureNameExist(Long id,String configureName) {
		int i = 0;
		if(id!=null){
			i = sysConfigGroupRepository.isSysConfigureNameExist(id,configureName);
		}else{
			i = sysConfigGroupRepository.isSysConfigureNameExist(configureName);
		}
		return i>0?true:false;
	}
	
}
