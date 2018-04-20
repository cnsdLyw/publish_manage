package com.litc.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Function:接收HTTP发送过来的图书图片，并保存处理
 * 
 * @author zhongying(281264212@qq.com)
 * @date 2016-2-24 上午11:09:25
 * @version 1.0
 */
@WebServlet("/HttpReceivePicServlet")
public class HttpReceivePicServlet extends HttpServlet {
	
	private static final long serialVersionUID = -7714076810450707673L;
	
	private final static Logger logger = LoggerFactory.getLogger(HttpReceivePicServlet.class);
	
	public HttpReceivePicServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		logger.info("【中心服务器】HttpReceiveFileServletHTTP-doPost-开始接收【客户端】发的图片，并解析start");

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		logger.info("【中心服务器】HttpReceiveFileServletHTTP-doPost-接收【客户端】发的文件end");
	}

	public File createFile(String path, String fileName) {

		// path表示你所创建文件的路径
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		// fileName表示你创建的文件名；为txt类型；
		File file = new File(f, fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

}
