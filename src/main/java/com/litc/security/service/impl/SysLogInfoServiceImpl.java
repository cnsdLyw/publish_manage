package com.litc.security.service.impl;

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

import com.litc.security.service.SysLogInfoService;
import com.litc.security.common.page.PageParam;
import com.litc.security.model.LogInfo;
import com.litc.security.repository.IComponent;
import com.litc.security.repository.SysLogInfoRepository;


@Service("sysLogInfoService")
public class SysLogInfoServiceImpl implements SysLogInfoService {

	@Autowired
	private SysLogInfoRepository sysLogInfoRepository;
	
	@Override
	public LogInfo getLogInfo(Long id){
		return sysLogInfoRepository.findOne(id);
	}
	
	@Override
	@Transactional
	public void deleteLogInfo(Long[] id) {
		// TODO Auto-generated method stub
		sysLogInfoRepository.deleteIn(id);
	}
	
	@Override
	public  Page<LogInfo> getOrganizationsByPages(int pageNo,int pageSize,Direction driection,String orderType,final PageParam...args) {
		Page<LogInfo> userPage = sysLogInfoRepository.findAll(new Specification<LogInfo>() {
			@Override
			public Predicate toPredicate(Root<LogInfo> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predList = new ArrayList<Predicate>();
				for(PageParam param:args){
					Path<String> loginNameExp = root.get(param.getSearchColumn());
					if(StringUtils.isNotBlank(param.getSearchValue())){
						if(param.getSearchType().equals(IComponent.SERCHTYPE_SAME)){
							predList.add(cb.equal(loginNameExp, param.getSearchValue()));
						}else if(param.getSearchType().equals(IComponent.SERCHTYPE_LIKE)){
							predList.add(cb.like(loginNameExp, "%"+param.getSearchValue()+"%"));
						}
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
	@Transactional
	public void addLogInfo(LogInfo logInfo) {
		sysLogInfoRepository.save(logInfo);
	}

}
