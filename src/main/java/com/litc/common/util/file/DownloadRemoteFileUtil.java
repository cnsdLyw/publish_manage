package com.litc.common.util.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.log.Log;

/**
 * 
 * 负责文件下载的工具类类
 */

public class DownloadRemoteFileUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadRemoteFileUtil.class);

	/**
	 * 
	 * 获取远程文件尺寸
	 */

	public static long getRemoteFileSize(String remoteFileUrl) throws IOException {

		long fileSize = 0;

		HttpURLConnection httpConnection = (HttpURLConnection) new URL(

		remoteFileUrl).openConnection();

		httpConnection.setRequestMethod("HEAD");

		int responseCode = httpConnection.getResponseCode();

		if (responseCode >= 400) {

			LOGGER.debug("Web服务器响应错误!");

			return 0;

		}

		String sHeader;

		for (int i = 1;; i++) {

			sHeader = httpConnection.getHeaderFieldKey(i);

			if (sHeader != null && sHeader.equals("Content-Length")) {

				System.out.println("文件大小ContentLength:" + httpConnection.getContentLength());

				fileSize = Long.parseLong(httpConnection.getHeaderField(sHeader));

				break;

			}

		}

		return fileSize;

	}

	/**
	 * 
	 * 创建指定大小的文件
	 */

	public static void createFile(String path, long fileSize) throws IOException {

		File newFile = new File(path);
		// 判断目标文件所在的目录是否存在
		if (!newFile.getParentFile().exists()) {
			// 如果目标文件所在的目录不存在，则创建父目录
			System.out.println("目标文件所在目录不存在，准备创建它！");
			if (!newFile.getParentFile().mkdirs()) {
				System.out.println("创建目标文件所在目录失败！");
			}
		}
		// 创建目标文件
		try {
			if (newFile.createNewFile()) {
				System.out.println("创建单个文件" + path + "成功！");
			} else {
				System.out.println("创建单个文件" + path + "失败！");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("创建单个文件" + path + "失败！" + e.getMessage());
		}

			    
		RandomAccessFile raf = new RandomAccessFile(newFile, "rw");

		raf.setLength(fileSize);

		raf.close();

	}

}
