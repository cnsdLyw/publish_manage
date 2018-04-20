package com.litc.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
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

import com.litc.security.common.page.PageParam;
import com.litc.security.model.Organization;
import com.litc.system.model.SysConfigGroup;
import com.litc.system.model.SysConfigure;
import com.litc.system.repository.SysConfigureRepository;
import com.litc.system.service.SysConfigureService;
@Service("sysConfigureService")
public class SysConfigureServiceImpl implements SysConfigureService {

	@Autowired
	private SysConfigureRepository sysConfigureRepository;
	
	@Override
	public List<SysConfigure> getSysConfigGroup(Long parentId){
		return sysConfigureRepository.getSysConfigure(parentId);
	}
	
	@Override
	public List<SysConfigure> getSysConfigGroup(){
		return sysConfigureRepository.getSysConfigure();
	}
	
	@Override
	public Long getParentId(Long typeid){
		return sysConfigureRepository.getParentId(typeid);
	}
	
	@Override
	public Page<SysConfigure> getSysConfiguresByPage(int pageNo, int pageSize, Direction driection, String orderType,final Long typeid,final String keyWord) {
		Page<SysConfigure> userPage = sysConfigureRepository.findAll(new Specification<SysConfigure>() {
		@Override
		public Predicate toPredicate(Root<SysConfigure> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
			List<Predicate> predList = new ArrayList<Predicate>();
			Predicate p = null;
			Predicate p1 = cb.equal(root.get("groupId").as(Long.class), typeid);
			p = p1;
			if(StringUtils.isNotBlank(keyWord)){
				Predicate p2 = cb.like(root.get("configureName").as(String.class), "%"+keyWord+"%");  
				Predicate p3 = cb.like(root.get("configureCode").as(String.class),  "%"+keyWord+"%");  
				p = cb.and(p1,cb.or(p2,p3));
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
	@Transactional
	public void addSysConfigure(SysConfigure sysConfigure){
		sysConfigureRepository.save(sysConfigure);
	}
	
	@Override
	@Transactional
	public void delete(Long id){
		sysConfigureRepository.delete(id);
	}
	
	@Override
	public SysConfigure getSysConfigure(Long id) {
		return sysConfigureRepository.findOne(id);
	}
	
	@Override
	public boolean isSysConfigureNameExist(String configureName) {
		int i = sysConfigureRepository.isSysConfigureNameExist(configureName);
		return i>0?true:false;
	}
	
	@Override
	public boolean isSysConfigureNameExist(Long id,String configureName) {
		int i = 0;
		if(id!=null){
			i = sysConfigureRepository.isSysConfigureNameExist(id,configureName);
		}else{
			i = sysConfigureRepository.isSysConfigureNameExist(configureName);
		}
		return i>0?true:false;
	}

	
}
