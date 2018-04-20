package com.litc.system.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.litc.security.common.page.PageParam;
import com.litc.system.model.StorageDevice;

public interface StorageDeviceService {
	
	/**
	 * 添加存储设备
	 * @param storageDevice
	 */
	public void addStorageDevice(StorageDevice storageDevice);

	/**
	 * 获取存储设备
	 * @param id
	 * @return
	 */
	public StorageDevice getStorageDevice(Long id);

	/**
	 *  修改存储设备
	 * @param user
	 * @return
	 */
	public StorageDevice updateStorageDevice(StorageDevice storageDevice);
	
	/**
	 * 存储设备是否使用
	 * @param id
	 * @return
	 */
	public int isStorageDeviceUsed(Long id);
	
	/**
	 * 存储设备是否使用
	 * @param id
	 * @return
	 */
	public int isStorageDeviceUsed(Long[] id);
	
	/**
	 * 删除存储设备,根据编号删除
	 * @param id
	 */
	public void deleteStorageDevice(Long id);

	/**
	 * 批量删除存储设备
	 * @param ids
	 * @return
	 */
	public int deleteStorageDevices(Long[] ids);
	/**
	 * 根据ftp表示获取ftp设备
	 * @param ftpCode
	 * @return
	 */
	public StorageDevice getStorageDevice(String ftpCode);

	/**
	 * 查询所有存储设备
	 * @return
	 */
	public List<StorageDevice> getStorageDevices();
	
	/**
	 * 分页带条件查询存储设备
	 * @param pageNo
	 * @param pageSize
	 * @param driection
	 * @param orderType
	 * @param args
	 * @return
	 */
	public Page<StorageDevice> getStorageDevicesByPages(int pageNo,int pageSize,Direction driection,String orderType,PageParam...args);
	
}
