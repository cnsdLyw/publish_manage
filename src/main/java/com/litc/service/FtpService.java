package com.litc.service;

import java.io.File;
/**
 *  FTP相关接口 
 */
@Deprecated
public interface FtpService {

	/**
	 * 上传文件到FTP
	 * @param deviceName设备名
	 * @param ftpFolderPath上传到设备上的文件夹路径
	 * @param ftpNewFileName上传到设备上的文件名
	 * @param localFile 被上传的本地文件路径
	 * @return
	 */
	public  void uploadFile(String deviceName, String ftpFolderPath,String ftpNewFileName, File localFile);
	
	/**
	 * 上传文件夹到ftp
	 * @param deviceName设备名
	 * @param ftpFolderPath上传到ftp的文件夹路径
	 * @param localFolder 本地文件夹路径
	 */
	public  void uploadFolder(String deviceName, String ftpFolderPath,File localFolder);
	
	
	/**
	 * 从存储设备上，下载文件到本地文件夹下，以本地文件名重命名
	 * @param deviceName 设备名
	 * @param ftpFilePath 设备相对文件路径
	 * @param localFilePath 本地文件路径 ,不需要存在，下载过程中会新建该文件
	 */
	public  void downloadFile(String deviceName, String ftpFilePath,String localFolderPath, String newName);
	
	/**
	 * 从存储设备上，下载文件夹到本地文件夹下，以本地文件夹名重命名
	 * @param deviceName 设备名
	 * @param ftpFolderPath 设备相对文件夹路径
	 * @param localFolderPath 本地文件夹路径 ,不需要存在，下载过程中会新建该文件夹
	 */
	public  void downloadFolder(String deviceName, String ftpFolderPath,String localFolderPath);
	
	
}
