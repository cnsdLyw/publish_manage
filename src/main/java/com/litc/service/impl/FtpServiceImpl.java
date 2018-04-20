package com.litc.service.impl;

import java.io.File;

import com.litc.common.util.file.StorageDeviceUtil;
import com.litc.service.FtpService;
/**
 *  FTP相关实现类
 */
@Deprecated
public class FtpServiceImpl implements FtpService {
	
	@Override
	public void uploadFile(String deviceName, String ftpFolderPath, String ftpNewFileName, File localFile) {
		try {
			StorageDeviceUtil.uploadFile( deviceName,  ftpFolderPath,  ftpNewFileName,  localFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void uploadFolder(String deviceName, String ftpFolderPath, File localFolder) {
		try {
			StorageDeviceUtil.uploadFolder( deviceName,  ftpFolderPath,  localFolder);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void downloadFile(String deviceName, String ftpFilePath,String localFolderPath, String newName) {
		try {
			StorageDeviceUtil.downloadFile( deviceName,  ftpFilePath,  localFolderPath,newName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void downloadFolder(String deviceName, String ftpFolderPath, String localFolderPath) {
		try {
			StorageDeviceUtil.downloadFolder( deviceName,  ftpFolderPath,  localFolderPath);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
