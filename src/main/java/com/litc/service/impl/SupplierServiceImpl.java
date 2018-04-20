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

import com.litc.repository.SupplierRepository;
import com.litc.security.model.Supplier;
import com.litc.security.model.Supplier;
import com.litc.service.SupplierService;

@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;
	
	@Override
	@Transactional
	public void addSupplier(Supplier supplier) {
		supplier.setLastModifyTime(new Date());
		supplierRepository.save(supplier);

	}

	@Override
	public Supplier getSupplier(Long id) {
		return supplierRepository.findOne(id);
	}

	@Override
	@Transactional
	public Supplier updateSupplier(Supplier supplier) {
		supplier.setLastModifyTime(new Date());
		supplierRepository.save(supplier);
		return supplier;
	}

	@Override
	@Transactional
	public void deleteSupplier(Long id) {
		supplierRepository.delete(id);
	}

	@Override
	@Transactional
	public int deleteSuppliers(Long[] ids) {
		return supplierRepository.deleteUserIn(ids);
	}

	@Override
	public List<Supplier> getSuppliers() {
		// TODO Auto-generated method stub
		return supplierRepository.findAll();
	}

	@Override
	public Page<Supplier> getSuppliersByPages(int pageNo, int pageSize,
			Direction driection, String orderType, final String keyWord) {
		Page<Supplier> userPage = supplierRepository.findAll(new Specification<Supplier>() {
			@Override
			public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> query,
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

	@Override
	public boolean isEmailExist(String email) {
		int i = supplierRepository.isEmailExist(email);
		return i>0?true:false;
	}

	@Override
	public boolean isEmailExist(Long id, String email) {
		int i = 0;
		if(id!=null){
			i = supplierRepository.isEmailExist(id,email);
		}else{
			i = supplierRepository.isEmailExist(email);
		}
		return i>0?true:false;
	}

}
