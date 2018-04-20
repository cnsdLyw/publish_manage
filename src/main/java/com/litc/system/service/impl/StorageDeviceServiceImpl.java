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
import com.litc.security.repository.IComponent;
import com.litc.system.model.StorageDevice;
import com.litc.system.repository.StorageDeviceRepository;
import com.litc.system.service.StorageDeviceService;

@Service("storageDeviceService")
public class StorageDeviceServiceImpl implements StorageDeviceService {

	@Autowired
	private StorageDeviceRepository storageDeviceRepository;
	
	@Override
	@Transactional
	public void addStorageDevice(StorageDevice storageDevice) {
		storageDeviceRepository.save(storageDevice);

	}

	@Override
	public StorageDevice getStorageDevice(Long id) {
		return storageDeviceRepository.getOne(id);
	}

	@Override
	public StorageDevice updateStorageDevice(StorageDevice storageDevice) {
		return null;
	}
	
	@Override
	public int isStorageDeviceUsed(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int isStorageDeviceUsed(Long[] id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional
	public void deleteStorageDevice(Long id) {
		storageDeviceRepository.delete(id);
	}

	@Override
	@Transactional
	public int deleteStorageDevices(Long[] ids) {
		return storageDeviceRepository.deleteStorageDeviceIn(ids);
	}
	
	@Override
	public StorageDevice getStorageDevice(String ftpCode) {
		return storageDeviceRepository.findByFtpCode(ftpCode);
	}

	@Override
	public List<StorageDevice> getStorageDevices() {
		return storageDeviceRepository.findAll();
	}
	
	@Override
	public  Page<StorageDevice> getStorageDevicesByPages(int pageNo,int pageSize,Direction driection,String orderType,final PageParam...args) {
		Page<StorageDevice> userPage = storageDeviceRepository.findAll(new Specification<StorageDevice>() {
			@Override
			public Predicate toPredicate(Root<StorageDevice> root, CriteriaQuery<?> query,
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

}
