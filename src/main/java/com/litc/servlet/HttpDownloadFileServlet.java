package com.litc.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.litc.common.util.ConfigurationUtil;

/**
 * Function:解析客户端发的下载文件请求接口，并且验证相关信息。
 * param1：文件路径
 * param2：用户名
 * param3：密码
 * @author zhongying(281264212@qq.com)
 * @date 2016-2-24 下午2:52:29
 * @version 1.0
 */
@WebServlet("/HttpDownloadFileServlet")
public class HttpDownloadFileServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5722554815698902949L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		System.out.println();
		System.out.println();
		System.out.println("【中心服务器】HttpDownloadFileServlet-doPost开始解析【客户端】发来的下载请求start");
		
		//【文件上传保存目录】如果为空，则默认tomcat根目录下
		String downloadApacheRoot=ConfigurationUtil.fileUpload_Directory;
		
		int BUFFER_SIZE = 4096;
		InputStream in = null;
		OutputStream out = null;


		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/octet-stream");

			String userName = request.getHeader("userName");
			String passwd = request.getHeader("passwd");
			String filePath = request.getHeader("filePath");

//			System.out.println("userName:" + userName);
//			System.out.println("passwd:" + passwd);

			// 可以根据传递来的userName和passwd做进一步处理，比如验证请求是否合法等
			String parseFilePath=downloadApacheRoot + "\\" + filePath;
			System.out.println("【中心服务器】-解析下载地址为=="+parseFilePath);
			
			File file = new File(parseFilePath);
			if(!file.exists()){
				System.out.println("【中心服务器】-解析不到该附件！！！");
			}
			response.setContentLength((int) file.length());
			response.setHeader("Accept-Ranges", "bytes");

			int readLength = 0;

			in = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);
			out = new BufferedOutputStream(response.getOutputStream());

			byte[] buffer = new byte[BUFFER_SIZE];
			while ((readLength = in.read(buffer)) > 0) {
				byte[] bytes = new byte[readLength];
				System.arraycopy(buffer, 0, bytes, 0, readLength);
				out.write(bytes);
			}

			out.flush();

			response.addHeader("token", "hello 1");

		} catch (Exception e) {
			e.printStackTrace();
			response.addHeader("token", "hello 2");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		System.out.println("【中心服务器】HttpDownloadFileServlet-doPost解析【客户端】下载请求end");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
}
