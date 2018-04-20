package com.litc.fileSystem.service;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.multipart.MultipartFile;

import com.litc.fileSystem.model.JetsenFiles;

public interface JetsenFilesService {

	/**
	 * 上传文件
	 * @param File 要上传的文件
	 * @param destPath 目的文件夹
	 * @return
	 */
	public JetsenFiles uploadFile(File File, String destPath);
	
	/**
	 * 上传文件
	 * @param File 要上传的文件
	 * @param destPath 目的文件夹
	 * @return
	 */
	public String uploadFile(MultipartFile File,String orgCode);
	
	/**
	 * 上传文件
	 * @param File 要上传的文件
	 * @param destPath 目的文件夹
	 * @return
	 */
	public JetsenFiles uploadFile(MultipartFile File,String orgCode, String destPath,String type);
	
	/**
	 * 上传文件
	 * @param File 要上传的文件
	 * @param destPath 目的文件夹
	 * @return
	 */
	public JetsenFiles uploadFile(File File,String orgCode, String destPath,String type);
	
	/**
	 * 上传文件
	 * @param File 要上传的文件
	 * @param destPath 目的文件夹
	 * @param fileName 上传之后的文件名称
	 * @return
	 */
	public JetsenFiles uploadFile(File File, String destPath, String fileName);

	/**
	 * 上传文件夹
	 * @param File 要上传的文件夹
	 * @param destPath 上传目的文件夹
	 * @return
	 */
	public int uploadFolder(File File, String destPath);

	/**
	 * 下载文件夹
	 * @param filePath  保存的文件夹路
	 * @param localPath 下载目的文件夹
	 * @return
	 */
	public String downloadFolder(String filePath, String localPath);

	/**
	 * 下载文件
	 * @param uuid  文件UUID 
	 * @param localPath  下载目的文件夹
	 * @return
	 */
	public String downloadFile(String uuid, String localPath);
	
	/**
	 * 下载文件
	 * @param uuid  文件UUID 
	 * @param localPath  下载目的文件夹
	 * @return
	 */
	public String downloadFileByPath(String filePath, String localPath);
	
	/**
	 * 下载文件
	 * @param uuid   文件UUID 
	 * @return
	 */
	public FileOutputStream downloadFile(String uuid);
	
	/**
	 * 下载文件  
	 * @param uuid 文件UUID
	 * @return
	 */
	public FileOutputStream downloadFileByPath(String filePath);
	
	
	/**
	 * 删除文件
	 * @param uuid 文件UUID
	 * @return
	 */
	public int deteteFile(String uuid);

	/**
	 * 删除文件
	 * @param filePath 保存的文件夹路径
	 * @return
	 */
	public int deteteFileByPath(String filePath);

	/**
	 *  删除文件
	 * @param jetsenFile 文件对象
	 * @return
	 */
	public int deteteFile(JetsenFiles jetsenFile);
	
	/**
	 * 删除文件夹
	 * @param filePath 文件夹路径
	 * @return
	 */
	public int deteteFolder(String filePath);
	/**
	 * 通过uuid来查询附件信息
	 * @param uuid
	 * @return
	 */
	public JetsenFiles findByUuid(String uuid);
	
	/**
	 * 通过路径来查询附件信息
	 * @param uuid
	 * @return
	 */
	public JetsenFiles findByStoragePath(String storagePath);
	
	/**
	 * 查询文件
	 * @param pageNo
	 * @param pageSize
	 * @param driection
	 * @param orderType
	 * @param keyWord
	 * @return
	 */
	public Page<JetsenFiles> getJetsenFilesByPages(int pageNo,int pageSize,Direction driection,String orderType,String loginOrgCode);

}
