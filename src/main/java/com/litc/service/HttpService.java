package com.litc.service;

import java.awt.print.Book;
import java.util.List;
import java.util.Map;

import com.litc.fileSystem.model.JetsenFiles;

/**
 *  HTTP上传和下载相关接口 
 */

public interface HttpService {
	
	/**
	 *  HttpPost方式上传文件到服务器
	 *  @param serverUrl服务器接收地址
	 *  @param localFilePath本地文件路径
	 */
	public void uploadFileByHttpPost(String serverUrl,List<JetsenFiles> attachmentList,Map map);
	
	/**
	 *  HttpPost方式上传图片到服务器
	 *  @param serverUrl服务器接收地址
	 *  @param localFilePath本地文件路径
	 */
	public void uploadPicByHttpPost(String serverUrl,List<Book> bookList,Map map);
	 
	 
	/**
	 * HttpGet方式下载文件到本地，并且需要“服务器端”验证相关信息，方可下载
	 * @param apacheAddres是apache的访问地址
	 * @param FilePath下载的资源路径
	 * @param localFilePath下载到本地的资源路径
	 */
	public  void downloadFileByHttpGet(String apacheAddres,String FilePath, String localFilePath);
	
	/**
	 * 下载文件(HttpGet处理方式，直接下载到本地相关目录)
	 * @param apacheAddres是apache的访问地址
	 * @param FilePath下载的资源路径
	 * @param localFilePath下载到本地的资源路径
	 */
	public  void downloadFileByHttpGet2(String apacheAddres,String FilePath, String localFilePath);
}
