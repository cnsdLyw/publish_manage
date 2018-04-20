package com.litc.fileSystem.service.impl;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.litc.fileSystem.model.JetsenFiles;
import com.litc.fileSystem.service.JetsenFilesService;

@Service("jetsenFilesFtpService")
public class JetsenFilesFtpServiceImpl implements JetsenFilesService {

	@Override
	public JetsenFiles uploadFile(File File, String destPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JetsenFiles uploadFile(File File, String destPath, String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int uploadFolder(File File, String destPath) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String downloadFolder(String filePath, String localPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String downloadFile(String uuid, String localPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String downloadFileByPath(String filePath, String localPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileOutputStream downloadFile(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileOutputStream downloadFileByPath(String filePath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deteteFile(String uuid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deteteFileByPath(String filePath) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deteteFile(JetsenFiles jetsenFile) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deteteFolder(String filePath) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public JetsenFiles findByUuid(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String uploadFile(MultipartFile File, String orgCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JetsenFiles uploadFile(MultipartFile File, String orgCode, String destPath, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<JetsenFiles> getJetsenFilesByPages(int pageNo,
			int pageSize, Direction driection, String orderType,
			String loginOrgCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JetsenFiles uploadFile(File File, String orgCode, String destPath,
			String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JetsenFiles findByStoragePath(String storagePath) {
		// TODO Auto-generated method stub
		return null;
	}

}