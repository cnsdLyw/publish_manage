package com.litc.service.impl;

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

import com.litc.repository.FinanceRecordRepository;
import com.litc.security.model.FinanceRecord;
import com.litc.service.FinanceRecordService;

@Service("financeRecordService")
public class FinanceRecordServiceImpl implements FinanceRecordService {

	@Autowired
	private FinanceRecordRepository financeRecordRepository;
	
	@Override
	@Transactional
	public void addFinanceRecord(FinanceRecord financeRecord) {
		financeRecord.setLastModifyTime(new Date());
		financeRecordRepository.save(financeRecord);

	}

	@Override
	public FinanceRecord getFinanceRecord(Long id) {
		return financeRecordRepository.findOne(id);
	}

	@Override
	@Transactional
	public FinanceRecord updateFinanceRecord(FinanceRecord financeRecord) {
		financeRecord.setLastModifyTime(new Date());
		financeRecordRepository.save(financeRecord);
		return financeRecord;
	}

	@Override
	@Transactional
	public void deleteFinanceRecord(Long id) {
		financeRecordRepository.delete(id);
	}

	@Override
	@Transactional
	public int deleteFinanceRecords(Long[] ids) {
		return financeRecordRepository.deleteUserIn(ids);
	}

	@Override
	public List<FinanceRecord> getFinanceRecords() {
		// TODO Auto-generated method stub
		return financeRecordRepository.findAll();
	}

	@Override
	public Page<FinanceRecord> getFinanceRecordsByPages(int pageNo, int pageSize,
			Direction driection, String orderType, final String keyWord) {
		Page<FinanceRecord> userPage = financeRecordRepository.findAll(new Specification<FinanceRecord>() {
			@Override
			public Predicate toPredicate(Root<FinanceRecord> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predList = new ArrayList<Predicate>();
				
				if(StringUtils.isNotBlank(keyWord)){
					Predicate p = cb.or(cb.like(root.get("companyName").as(String.class), "%"+keyWord+"%"), cb.like(root.get("businessContacts").as(String.class),  "%"+keyWord+"%"));
					predList.add(p);
				}
				Predicate[] sCondition=(Predicate[])predList.toArray(new Predicate[predList.size()]);
				query.where(sCondition);
				return null;
			}
		},new PageRequest(pageNo, pageSize,driection, orderType));
		return userPage;
	}

}
