package com.litc.servlet.download.impl;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.litc.common.util.ConfigurationUtil;
import com.litc.servlet.download.Download;

/**
 *盘符下附件下载
 * 
 * @author zhongying
 * 
 */
public class FilePanDownloadImpl implements Download {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private String id;
	private String path;
	private String name;
	private String root;
	
	public FilePanDownloadImpl(HttpServletRequest req,
			HttpServletResponse resp) {
		root=ConfigurationUtil.fileUpload_Directory;
		path=req.getParameter("path");
		id=req.getParameter("id");
	}

	public Boolean isVaild() {
		File file = new File(root + path);
		if (!file.exists()) {
			logger.info("附件不存在");
			return false;
		}

		return true;
	}

	public String getFileName() {
//		String fileName = "";
//		int flag = path.lastIndexOf("/");
//		if( flag!=-1 )
//		{
//			fileName = path.substring(flag+1);
//		}
//		else
//		{
//			//name = path.substring(path.lastIndexOf("\\")+1);//xp
//			fileName =path;
//		}
		
		
		String filename=path.substring(path.lastIndexOf("/") + 1);
		return filename;
	}

	public String getPath() {
		return root + path;
	}

}
