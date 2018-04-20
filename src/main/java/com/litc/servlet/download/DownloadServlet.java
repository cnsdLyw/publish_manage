package com.litc.servlet.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.litc.servlet.download.impl.FileDataDownloadImpl;
import com.litc.servlet.download.impl.FilePanDownloadImpl;

/**
 * 下载
 * 
 * @author zhongying
 * 
 */
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 3532755719014409025L;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/* 下载接口 */
	private Download download = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		logger.info("***********************下载开始***********************");

		// 获取实现类
		download = factory(req, resp);
		if (download == null) {
			logger.info("未获取到下载实现类");
			return;
		}

		// 验证是否允许下载
		if (!download.isVaild()) {
			logger.info("不符合下载条件");
			return;
		}

		// 获取文件全路径
		String path = download.getPath();
		if (StringUtils.isEmpty(path)) {
			logger.info("未获取到文件全路径");
			return;
		}

		File file = new File(path);

		// 文件不存在
		if (!file.exists()) {
			logger.info("需要下载的文件不存在");
			return;
		}

		// 文件名
		String fileName = download.getFileName();
		if (StringUtils.isEmpty(fileName)) {
			logger.info("未获取到文件名");
			return;
		}

		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Content-Disposition", "attachment;filename="
				+ URLEncoder.encode(fileName, "UTF-8"));

		// 下载
		ServletOutputStream os = null;
		FileInputStream is = null;
		try {
			os = resp.getOutputStream();
			is = new FileInputStream(file);

			byte[] buff = new byte[1024];
			int leng = is.read(buff);
			while (leng > 0) {
				os.write(buff, 0, leng);
				leng = is.read(buff);
			}
		} catch (Exception e) {
			logger.error("下载文件出错:", e.getMessage());
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		logger.info("***********************下载结束***********************");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	/**
	 * 工厂方法
	 * 
	 * @param req
	 * @param resp
	 * @return Download接口实现类
	 */
	private Download factory(HttpServletRequest req, HttpServletResponse resp) {
		// 类别
		String type = req.getParameter("type");
		if (StringUtils.isEmpty(type)) {
			logger.info("未获取到类别");
			return null;
		}
		if ("panDownload".equals(type)) {
			return new FilePanDownloadImpl(req, resp);
		}
		if ("dataDownload".equals(type)) {
			return new FileDataDownloadImpl(req, resp);
		}
		
		return null;
	}

}
