package com.litc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.litc.security.model.FinanceRecord;

public interface FinanceRecordService {
	
	/**
	 * 添加供应商
	 * @param financeRecord
	 */
	public void addFinanceRecord(FinanceRecord financeRecord);

	/**
	 * 获取供应商
	 * @param id
	 * @return
	 */
	public FinanceRecord getFinanceRecord(Long id);
	
	/**
	 * 修改供应商
	 * @param financeRecord
	 * @return
	 */
	public FinanceRecord updateFinanceRecord(FinanceRecord financeRecord);
	
	
	/**
	 * 删除供应商,根据编号删除
	 * @param id
	 */
	public void deleteFinanceRecord(Long id);

	/**
	 * 批量删除供应商
	 * @param ids
	 * @return
	 */
	public int deleteFinanceRecords(Long[] ids);

	/**
	 * 查询所有供应商
	 * @return
	 */
	public List<FinanceRecord> getFinanceRecords();
	

	/**
	 * 分页带条件查询供应商
	 * @param pageNo
	 * @param pageSize
	 * @param driection
	 * @param orderType
	 * @param args
	 * @return
	 */
	public Page<FinanceRecord> getFinanceRecordsByPages(int pageNo,int pageSize,Direction driection,String orderType,String keyWord);
}
