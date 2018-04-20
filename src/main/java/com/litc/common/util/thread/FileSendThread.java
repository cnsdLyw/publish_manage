package com.litc.common.util.thread;

import java.util.List;

import com.litc.common.util.ConfigurationUtil;
import com.litc.fileSystem.model.JetsenFiles;
import com.litc.service.HttpService;
import com.litc.service.impl.HttpServiceImpl;

public class FileSendThread implements Runnable {

	private List<JetsenFiles> attachementCreate;
	
	public FileSendThread(List<JetsenFiles> attachementCreate){
		this.attachementCreate=attachementCreate;
	}

	@Override
	public void run() {

		// 发送完消息，然后将本地的附件，发布到【中心资源服务器】
		// 1，采用FTP方式
		// 2，或者采用翔宇soket方式方式
		// 3，HTTP
		if (attachementCreate != null && attachementCreate.size() > 0) {
			HttpService httpService = new HttpServiceImpl();
			// String localFilePath="D:\\jetsen\\复合出版\\21包\\中图法分类.xml";
			String serverUrl =ConfigurationUtil.center_TomcatAccessAddress + "HttpReceiveFileServlet";
			httpService.uploadFileByHttpPost(serverUrl, attachementCreate, null);
		}
	}

}
