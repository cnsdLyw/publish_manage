package com.litc.fileSystem.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.jetsen.fileManagerment.FileManage;
import com.jetsen.fileManagerment.interfaces.IFileOperation;
import com.litc.common.util.Constant;
import com.litc.common.util.UUID;
import com.litc.common.util.file.FilePathUtil;
import com.litc.fileSystem.model.JetsenFiles;
import com.litc.fileSystem.repository.JetsenFilesRepository;
import com.litc.fileSystem.service.JetsenFilesService;

@Service("jetsenFilesFastDFService")
public class JetsenFilesFastDFSServiceImpl implements JetsenFilesService {
	
	@Autowired
	private JetsenFilesRepository jetsenFilesRepository;

	@Override
	public JetsenFiles uploadFile(File file, String destPath) {
		IFileOperation fo=FileManage.createtDefaultDevice();
		JetsenFiles files = new JetsenFiles();
		String file_ext_name=null,fileUrl=null;
		try {
			byte[] buffer = ((MultipartFile) file).getBytes();
			String url = fo.upload_file(buffer, file_ext_name);
			if(url!=null)
			fileUrl="/"+url.replace(String.valueOf(File.separator), "/");
			String uuid = UUID.getUUID();
			files.setUuid(uuid);
			files.setName(file.getName());
			files.setStoragePath(fileUrl);
			files.setFileType(file_ext_name);
			jetsenFilesRepository.save(files);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return files;
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
		JetsenFiles files = jetsenFilesRepository.findByFilePath(filePath);
		if(files.getStoragePath()!=null){
			try {
				String fileUrl = files.getStoragePath();
				String path = localPath + fileUrl;
				String newPath = path.substring(0, path.length()-34);
		    	String newName =fileUrl.substring(fileUrl.length()-34, fileUrl.length());
				download(Constant.SERVER_IMAGE_PATH+fileUrl, newName,newPath);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return null;
	}

	@Override
	public String downloadFile(String uuid, String localPath) {
		JetsenFiles files = jetsenFilesRepository.findByUuid(uuid);
		if(files.getStoragePath()!=null){
			try {
				String fileUrl = files.getStoragePath();
				String path = localPath + fileUrl;
				String newPath = path.substring(0, path.length()-34);
		    	String newName =fileUrl.substring(fileUrl.length()-34, fileUrl.length());
				download(Constant.SERVER_IMAGE_PATH+fileUrl, newName,newPath);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return null;
	}

	@Override
	public String downloadFileByPath(String filePath, String localPath) {
		JetsenFiles files = jetsenFilesRepository.findByFilePath(filePath);
		if(files.getStoragePath()!=null){
			try {
				String fileUrl = files.getStoragePath();
				String path = localPath + localPath;
				String newPath = path.substring(0, path.length()-34);
		    	String newName =fileUrl.substring(fileUrl.length()-34, fileUrl.length());
				download(Constant.SERVER_IMAGE_PATH+fileUrl, newName,newPath);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
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
		IFileOperation fo=FileManage.createtDefaultDevice();
		JetsenFiles files = jetsenFilesRepository.findByUuid(uuid);
		if(files!=null){
			fo.delete_file(files.getStoragePath(), "");
			jetsenFilesRepository.delete(files.getUuid());
			return 1;
		}
		return 0;
	}

	@Override
	public int deteteFileByPath(String filePath) {
		IFileOperation fo=FileManage.createtDefaultDevice();
		JetsenFiles files = jetsenFilesRepository.findByFilePath(filePath);
		if(files!=null){
			String path =files.getStoragePath();
			if (path.startsWith("/")) {
				path = path.substring(path.indexOf("/")+1);
			}
			fo.delete_file(path, "");
			return 1;
		}
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
	
	public static void download(String urlString, String filename,String savePath) throws Exception {  
        // 构造URL  
        URL url = new URL(urlString);  
        // 打开连接  
        URLConnection con = url.openConnection();  
        //设置请求超时为5s  
        con.setConnectTimeout(5*1000);  
        // 输入流  
        InputStream is = con.getInputStream();  
      
        // 1K的数据缓冲  
        byte[] bs = new byte[1024];  
        // 读取到的数据长度  
        int len;  
        // 输出的文件流  
       File sf=new File(savePath);  
       if(!sf.exists()){  
           sf.mkdirs();  
       }  
       OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);  
        // 开始读取  
        while ((len = is.read(bs)) != -1) {  
          os.write(bs, 0, len);  
        }  
        // 完毕，关闭所有链接  
        os.close();  
        is.close();  
    }

	@Override
	public String uploadFile(MultipartFile file,String orgCode) {
		IFileOperation fo=FileManage.createtDefaultDevice();
		JetsenFiles files = new JetsenFiles();
		String fileExt=null,fileName=null;
		fileName=file.getOriginalFilename();
		fileExt=FilePathUtil.getSuffix(fileName);
		try {
			String fileUrl = fo.upload_file(file.getBytes(), fileExt);
				if(fileUrl!=null){
					fileUrl="/"+fileUrl.replace(String.valueOf(File.separator), "/");
					String uuid = java.util.UUID.randomUUID().toString();
					files.setUuid(uuid);
					files.setName(fileName);
					files.setFileSize(file.getSize());
					files.setTime(new Date());
					files.setStoragePath(fileUrl);
					files.setFileType("fast");
					files.setFileType(fileExt);
					jetsenFilesRepository.save(files);
					return fileUrl;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public JetsenFiles uploadFile(MultipartFile file,String orgCode, String destPath,String type) {
		IFileOperation fo=FileManage.createtDefaultDevice();
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String home = request.getServletContext().getRealPath("/");
		JetsenFiles files = new JetsenFiles();
		String file_ext_name=null,fileName=null;
		fileName=file.getOriginalFilename();
		file_ext_name=FilePathUtil.getSuffix(fileName);
		try {
			String fileUrl = fo.upload_file(file.getBytes(), file_ext_name);
				if(fileUrl!=null){
				fileUrl="/"+fileUrl.replace(String.valueOf(File.separator), "/");
				String uuid = java.util.UUID.randomUUID().toString();
				files.setUuid(uuid);
				files.setName(fileName);
				files.setFileSize(file.getSize());
				files.setTime(new Date());
				files.setStoragePath(fileUrl);
				files.setFileType("fast");
				files.setType(type);
				files.setFileType(file_ext_name);
				files.setOrgCode(orgCode);
				jetsenFilesRepository.save(files);
				/*String path = home + fileUrl;
				String newPath = path.substring(0, path.length()-34);
		    	String newName = fileUrl.substring(fileUrl.length()-34, fileUrl.length());
				download(Constant.SERVER_FILE_HOME_URL+fileUrl, newName,newPath);
				*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return files;
	}
	
	@Override
	public JetsenFiles uploadFile(File file,String orgCode, String destPath,String type) {
		IFileOperation fo=FileManage.createtDefaultDevice();
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String home = request.getServletContext().getRealPath("/");
		JetsenFiles files = new JetsenFiles();
		String file_ext_name=null,fileName=null;
		fileName=file.getName();
		file_ext_name=FilePathUtil.getSuffix(fileName);
		try {
			String fileUrl = fo.upload_file(fileToByteArray(file), file_ext_name);
				if(fileUrl!=null){
				fileUrl="/"+fileUrl.replace(String.valueOf(File.separator), "/");
				String uuid = java.util.UUID.randomUUID().toString();
				files.setUuid(uuid);
				files.setName(fileName);
				files.setFileSize(file.length());
				files.setTime(new Date());
				files.setStoragePath(fileUrl);
				files.setFileType("fast");
				files.setType(type);
				files.setFileType(file_ext_name);
				if(StringUtils.isNotBlank(orgCode)){
					files.setOrgCode(orgCode);
				}
				jetsenFilesRepository.save(files);
				/*String path = home + fileUrl;
				String newPath = path.substring(0, path.length()-34);
		    	String newName = fileUrl.substring(fileUrl.length()-34, fileUrl.length());
				download(Constant.SERVER_FILE_HOME_URL+fileUrl, newName,newPath);
				*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return files;
	}

	@Override
	public JetsenFiles findByUuid(String uuid) {
		return jetsenFilesRepository.findByUuid(uuid);
	}
	
	@Override
	public JetsenFiles findByStoragePath(String storagePath) {
		List<JetsenFiles> list = jetsenFilesRepository.findByStoragePath(storagePath);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Page<JetsenFiles> getJetsenFilesByPages(int pageNo,
			int pageSize, Direction driection, String orderType,final String loginOrgCode) {
		Page<JetsenFiles> page = jetsenFilesRepository.findAll(new Specification<JetsenFiles>() {
			@Override
			public Predicate toPredicate(Root<JetsenFiles> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predList = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(loginOrgCode)){
					Predicate p = cb.equal(root.get("orgCode").as(String.class), loginOrgCode);  
					predList.add(p);
				}
				
				
				Predicate[] sCondition=(Predicate[])predList.toArray(new Predicate[predList.size()]);
				query.where(sCondition);
				return null;
			}
		},new PageRequest(pageNo, pageSize,driection, orderType));
		return page;
	}
	
	private byte[] fileToByteArray(File file) {
		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
}
