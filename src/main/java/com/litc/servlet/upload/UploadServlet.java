package com.litc.servlet.upload;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.litc.servlet.upload.impl.FilePanUploadDownloadImpl;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 *  Function:  文件上传
 *  @author  zhongying(281264212@qq.com)
 *  @date    2015-11-15 下午4:24:38    
 *  @version 1.0
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 2568794015024269732L;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/* 上传接口 */
	private Upload upload = null;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		logger.info("***********************上传开始***********************");

		// 获取实现类
		upload = factory(req, resp);
		if (upload == null) {
			logger.info("未获取到上传实现类");
			return;
		}

		String saveDirectory = upload.getSaveDirectory();
		if (StringUtils.isEmpty(saveDirectory)) {
			logger.info("未获取到文件保存路径");
			return;
		}
		// 目录
		File dir = new File(saveDirectory);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		Integer maxPostSize = upload.getMaxPostSize();
		if (maxPostSize == null) {
			logger.info("未获取到文件最大大小");
			return;
		}

		String encoding = upload.getEncoding() == null ? "UTF-8" : upload
				.getEncoding();

		FileRenamePolicy policy = upload.getPolicy() == null ? new FileRenamePolicyImpl()
				: upload.getPolicy();

		// 上传
		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest(req, saveDirectory, maxPostSize,
					encoding, policy);
		} catch (IOException e) {
			addErrorMsg(resp, e.getMessage());
			return;
		}

		Enumeration<String> fileNames = multi.getFileNames();
		while (fileNames.hasMoreElements()) {
			String fileName = fileNames.nextElement();
			// 附件
			File file = multi.getFile(fileName);
			if (file == null) {
				addErrorMsg(resp, "上传失败");
				return;
			}
			// 附件名称
			String attachName = multi.getOriginalFileName(fileName);
			// 附件别名
			String attachAlias = multi.getFilesystemName(fileName);
			// 扩展名
			String attachExt = FilenameUtils.getExtension(attachName);
			// 附件大小
			Long attachSize = file.length();
			// 附件路径
			String absolutePath=file.getAbsolutePath();
			
			//去除逗号,
			attachName=attachName.replace(",", "，");
			attachAlias=attachAlias.replace(",", "，");
			absolutePath=absolutePath.replace(",", "，");
			attachExt=attachExt.replace(",", "，");
			
			attachName=attachName.replace("'", "`");
			attachAlias=attachAlias.replace("'", "`");
			absolutePath=absolutePath.replace("'", "`");
			attachExt=attachExt.replace("'", "`");
			
			String rootPath=upload.getRoot();
			String attachPath = StringUtils.substringAfter(absolutePath,rootPath );

			Map<String, String> map = new HashMap<String, String>();
			map.put("success", "true");
			map.put("attach_id", " ");
			map.put("name", attachName);
			map.put("alias", attachAlias);
			map.put("ext", attachExt);
			map.put("size", attachSize.toString());
			map.put("path", StringUtils.replace(attachPath, String
					.valueOf(File.separatorChar), "/"));

			resp.getWriter().write(buildJSON(map));
			logger.info("返回的JSON:" + buildJSON(map));
		}

		logger.info("***********************上传结束***********************");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	/**
	 * 文件重命名实现
	 * 
	 * @author zhongying
	 * 
	 */
	class FileRenamePolicyImpl implements FileRenamePolicy {

		public File rename(File file) {
			// 别名
			String alias = StringUtils.join(new String[] {
					System.currentTimeMillis() + "", ".",
					FilenameUtils.getExtension(file.getName()) });
			return new File(file.getParentFile(), alias);
		}

	}

	private void addErrorMsg(HttpServletResponse response, String msg)
			throws IOException {
		logger.info("错误信息:" + msg);

		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "false");
		map.put("message", msg);
		response.getWriter().write(buildJSON(map));
	}

	private String buildJSON(Map<String, String> map) {
		StringBuffer json = new StringBuffer();
		json.append("{");
		Iterator<Map.Entry<String, String>> iterator = map.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			json.append("'");
			json.append(entry.getKey());
			json.append("'");
			json.append(":");
			json.append("'");
			json.append(entry.getValue());
			json.append("'");
			if (iterator.hasNext()) {
				json.append(",");
			}
		}
		json.append("}");
		return json.toString();
	}

	/**
	 * 工厂方法
	 * 
	 * @param req
	 * @param resp
	 * @return Upload接口实现类
	 */
	private Upload factory(HttpServletRequest req, HttpServletResponse resp) {
		// 类别
		String type = req.getParameter("type");
		if (StringUtils.isEmpty(type)) {
			logger.info("未获取到类别");
			return null;
		}

		if ("fileUpload".equals(type)){
			return new FilePanUploadDownloadImpl(req, resp);
		}
		
		return null;
	}

}
