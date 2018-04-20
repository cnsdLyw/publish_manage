package com.litc.controller.fileupload;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.litc.common.util.Constant;
import com.litc.common.util.file.FilePathUtil;
import com.litc.common.util.file.FileUtil;
import com.litc.controller.BaseController;

/**
 *  Function:文件上传  
 *  @author  zhongying(281264212@qq.com)
 *  @date    2015-12-23 上午9:56:01    
 *  @version 1.0
 */
@Controller
@RequestMapping(value="/file")
public class FileUploadController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    private List<Map<String,String>> FileList;
    private static Map<String,String> fileMap;
    private static String BaseUrl;
    
    @ModelAttribute
	public void beforeInvokingHandlerMethod(HttpSession session) {
		if(BaseUrl==null || BaseUrl.isEmpty())
			BaseUrl=session.getServletContext().getRealPath("/")+"filetemp"+File.separator;
	}
	
	@RequestMapping("/uploadMagFile")
	@ResponseBody
	public String uploadMagFile(HttpServletRequest request,String tempPath,String fileclass, MultipartFile file) throws Exception {
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
		tempPath=multipartRequest.getParameter("tempPath");
		fileclass=multipartRequest.getParameter("fileclass");
		
		//String tempFilePath = BaseUrl+ File.separator +tempPath+ File.separator +fileclass;
		String tempFilePath = Constant.SERVER_LOCAL_PATH + File.separator +tempPath+ File.separator +fileclass;
		File folder = new File(tempFilePath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File file1 = new File(tempFilePath, file.getOriginalFilename());
		file.transferTo(file1);// 保存文件到临时路径
		logger.info("--文件存放目录  "+tempFilePath);;
		return null;
	}
	
	@RequestMapping("/deleteTempFile")
	@ResponseBody
	public String deleteTempFile(String tempPath ,String fileName,String fileclass ) throws Exception {
		//String tempFilePath = BaseUrl + File.separator + tempPath + File.separator +fileclass +File.separator;
		String tempFilePath = Constant.SERVER_LOCAL_PATH + File.separator + tempPath + File.separator +fileclass +File.separator;
		File file = new File(tempFilePath + fileName);
		File folder = new File(tempFilePath);
		if (file.exists()){
			file.delete();
		}else{
			return "0";
		}
		if(file.exists()){
			System.gc();
			file.delete();
		}
		if (folder.listFiles().length == 0) {
			folder.delete();
		}
		return "1";
	}
	
	/**
	 * 
	 * @param tempPath 
	 * @param libid
	 * @param resid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/savetoserice") 
	@ResponseBody
	public String saveFileToService(String tempPath,String libid,String resid,String fileclass) throws Exception {
		//获取文件存放临时目录
		String tempFilePath = BaseUrl+ File.separator +tempPath+ File.separator +fileclass +File.separator;
		File folder = new File(tempFilePath);
		
		//zhongying
		 File[] fileArray=folder.listFiles();
		List<File> fileList=new ArrayList<File>();
		for(File file:fileArray){
			fileList.add(file);
		}
		
		//上传文件加下的所有文件(lujian)
//		if(folder!=null && folder.listFiles().length > 0){
//			FileList=new ArrayList<Map<String,String>>(); 
//			getFileNameList(FileList,folder);  
//			String result=RestTemplateFactory.postForObject("resAdmin/addResFile?userId="+loginname+"&resId="+resid, FileList);
//			return result;
//		}
		return "0";
	}
	
	public static void getFileNameList(List<Map<String,String>> fileNameList, File folder){
		if(folder!=null && folder.isFile()){
			fileMap=new HashMap<String,String>();
			String fileName=folder.getName();
			fileMap.put("FileName", FilePathUtil.getSuffix(fileName));
			fileMap.put("FileURL", folder.getPath().replace(BaseUrl, "/filetemp/"));
			fileMap.put("FileClass", folder.getParentFile().getName());
			fileMap.put("FileType", FilePathUtil.getSuffix(fileName));
			fileMap.put("FileSize", FileUtil.getStandardSize(folder.length()));
			fileNameList.add(fileMap);
		}else{
			if(folder.listFiles().length > 0){
				for(File fi : folder.listFiles()){
					getFileNameList(fileNameList , fi);
				}
			}
			
		}
		
	}
	
}