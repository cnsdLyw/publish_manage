package com.litc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.litc.security.model.Supplier;

public interface SupplierService {
	
	/**
	 * 添加供应商
	 * @param supplier
	 */
	public void addSupplier(Supplier supplier);

	/**
	 * 获取供应商
	 * @param id
	 * @return
	 */
	public Supplier getSupplier(Long id);
	
	/**
	 * 修改供应商
	 * @param supplier
	 * @return
	 */
	public Supplier updateSupplier(Supplier supplier);
	
	
	/**
	 * 删除供应商,根据编号删除
	 * @param id
	 */
	public void deleteSupplier(Long id);

	/**
	 * 批量删除供应商
	 * @param ids
	 * @return
	 */
	public int deleteSuppliers(Long[] ids);

	/**
	 * 查询所有供应商
	 * @return
	 */
	public List<Supplier> getSuppliers();
	

	/**
	 * 分页带条件查询供应商
	 * @param pageNo
	 * @param pageSize
	 * @param driection
	 * @param orderType
	 * @param args
	 * @return
	 */
	public Page<Supplier> getSuppliersByPages(int pageNo,int pageSize,Direction driection,String orderType,String keyWord);
	
	
	/**
	 * 是否存在邮箱 返回true表示已存在
	 * @param email
	 * @return
	 */
	public boolean isEmailExist(String email);
	
	/**
	 * 是否存在邮箱 返回true表示已存在
	 * @param id
	 * @param email
	 * @return
	 */
	public boolean isEmailExist(Long id,String email);
}
