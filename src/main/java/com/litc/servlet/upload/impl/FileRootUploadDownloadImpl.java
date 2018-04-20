package com.litc.servlet.upload.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.litc.common.util.ConfigurationUtil;
import com.litc.servlet.upload.Upload;
import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * 文件上传
 * @author zhongying
 *
 */
public class FileRootUploadDownloadImpl implements Upload {
	private String root = "";

	private String siteid;
	private String limitSize;
	private String type;
	private String download;
	private String id;
	public FileRootUploadDownloadImpl() 
	{}
	
	public FileRootUploadDownloadImpl(HttpServletRequest req, HttpServletResponse resp) 
	{
		
		limitSize=req.getParameter("limitSize");
		type=req.getParameter("uplaodType");
		id=req.getParameter("id");
		download=req.getParameter("download");
		//root = req.getSession().getServletContext().getRealPath("/");
		if("pan".equals(download)){
			root=ConfigurationUtil.fileUpload_Directory;
		}else{
			//root =  com.litc.publish.util.ConfigManager.getEnpRoot();
		}
		
	}

	public String getRoot() {
		return root;
	}

	public String getSaveDirectory() {
		String src="";
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		if("1".equals(type)){//书目中心附件
		    src= "/attachement/book/"+date;
		}else if("2".equals(type)){
		   
		}
		
		return root + src;
	}

	public Integer getMaxPostSize() {
		return Integer.parseInt(limitSize) * 1024 * 1024;
	}

	public String getEncoding() {
		return null;
	}
	
	/**
	 * 实现投稿文件重命名 作者姓名-文章标题
	 */
	class FileRenamePolicyImpl implements FileRenamePolicy 
	{
		public File rename(File file) 
		{
			// 别名 作者姓名-文章标题
//			String alias = StringUtils.join(new String[] {
//					"123" +"-"+ "55" +"-"+ System.currentTimeMillis() + "", ".",
//					FilenameUtils.getExtension(file.getName()) });
			if("50".equals(type)){
				String alias = id+".jpg";
				return new File(file.getParentFile(), alias);
			}
			return file;
		}

	}

	@Override
	public FileRenamePolicy getPolicy() {
		if("50".equals(type)){
			FileRenamePolicyImpl fp=new FileRenamePolicyImpl();
			return fp;
		}
		return null;
	}
	

}
