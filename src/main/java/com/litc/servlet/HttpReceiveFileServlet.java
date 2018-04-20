package com.litc.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Function:接收HTTP发送过来的文件，并保存处理
 * 
 * @author zhongying(281264212@qq.com)
 * @date 2016-2-24 上午11:09:25
 * @version 1.0
 */
@WebServlet("/HttpReceiveFileServlet")
public class HttpReceiveFileServlet extends HttpServlet {
	// 无论客户端是哪种上传方式，服务端的处理都是一样的。在通过HttpServletRequest获得参数之后，把得到的Item进行分类，分为普通的表单和File表单。
	// 通过ServletFileUpload 可以设置上传文件的大小及编码格式等。
	// 总之，服务端的处理是把得到的参数当做HTML表单进行处理的。

	// 服务端在处理之后，可以在Header中设置返回给客户端的简单信息。如果返回客户端是一个流的话，流的大小必须提前设置！
	// response.setContentLength((int) file.length());

	private static final long serialVersionUID = -7714076810450707673L;

	public HttpReceiveFileServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		// String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		// String uploadPath = Constant.HTTP_RESOURCE_SAVEPATH
		// +"\\attachement\\book\\"+date+"\\";
		// String uploadPath = "C:\\reso\\attachement\\books\\20160225\\";
		System.out.println("【中心服务器】HttpReceiveFileServletHTTP-doPost-开始接收【客户端】发的附件，并解析start");	

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		System.out.println("【中心服务器】HttpReceiveFileServletHTTP-doPost-接收【客户端】发的附件end");		
	}

	public File createFile(String path,String fileName) {

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
