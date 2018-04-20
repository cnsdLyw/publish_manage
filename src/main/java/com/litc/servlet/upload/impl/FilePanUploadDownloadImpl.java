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
 *  Function:  上传附件到服务器的盘符下
 *  @author  zhongying(281264212@qq.com)
 *  @date    2015-11-14 下午4:41:42    
 *  @version 1.0
 */
public class FilePanUploadDownloadImpl implements Upload {
	private String root = "";

	private String limitSize;
	private String type;
	private String id;
	public FilePanUploadDownloadImpl() {
		
	}
	
	public FilePanUploadDownloadImpl(HttpServletRequest req, HttpServletResponse resp) 
	{
		
		limitSize=req.getParameter("limitSize");
		type=req.getParameter("uplaodType");
		id=req.getParameter("id");
		//root = req.getSession().getServletContext().getRealPath("/");
		root=ConfigurationUtil.fileUpload_Directory;
		
	}

	public String getRoot() {
		return root;
	}

	// type<50 是标准图书附件
	public String getSaveDirectory() {
		String src="";
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		if("1".equals(type)){//书目中心附件-cover封面
		    src= "/attachement/book/cover/"+date;
		}else if("2".equals(type)){//书目中心附件-back_cover封底
			src= "/attachement/book/backcover/"+date;
		}else if("3".equals(type)){//书目中心附件-copyright版权
			src= "/attachement/book/copyright/"+date;
		}else if("4".equals(type)){//书目中心附件-copyright目录
			src= "/attachement/book/catalog/"+date;
		}else if("5".equals(type)){//书目中心附件-other其它
			src= "/attachement/book/other/"+date;
		}else if("7".equals(type)){//产品图片/美术作品
			src= "/attachement/book/picture/"+date;
		}else if("11".equals(type)){//书皮/包装
			src= "/attachement/book/jacket/"+date;
		}else if("12".equals(type)){//样张
			src= "/attachement/book/sample/"+date;
		}else if("13".equals(type)){//电子书
			src= "/attachement/book/ebook/"+date;
		}else if("61".equals(type)){//出版社交换附件
			src= "/attachement/book/companychange/"+date;
		}else if("62".equals(type)){//图书馆交换附件
			src= "/attachement/book/librarychange/"+date;
		}else if("63".equals(type)){//发行交换附件
			src= "/attachement/book/issuechange/"+date;
		}else if("101".equals(type)){//CMS新闻附件-海报图
			src= "/attachement/cms/posterpic/"+date;
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
//			if("1".equals(type)){
//				String alias = id+".jpg";
//				return new File(file.getParentFile(), alias);
//			}
			return file;
		}

	}

	@Override
	public FileRenamePolicy getPolicy() {
//		if("1".equals(type)){
//			FileRenamePolicyImpl fp=new FileRenamePolicyImpl();
//			return fp;
//		}
		return null;
	}
	

}
