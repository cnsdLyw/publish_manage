package com.litc.service.impl;

import java.awt.print.Book;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.litc.common.bean.SpringBeanUtil;
import com.litc.common.util.ConfigurationUtil;
import com.litc.common.util.Constant;
import com.litc.common.util.file.FilePathUtil;
import com.litc.fileSystem.model.JetsenFiles;
import com.litc.fileSystem.service.JetsenFilesService;
import com.litc.service.HttpService;

/**
 * Function:以HTTP上传和下载操作实现类
 * @author zhongying(281264212@qq.com)
 * @date 2016-2-24 下午5:05:58
 * @version 1.0
 */
public class HttpServiceImpl implements HttpService {

	private final static Logger logger = LoggerFactory.getLogger(HttpServiceImpl.class);
	
	@Override
	public void uploadFileByHttpPost(String serverUrl, List<JetsenFiles> attachmentList, Map map) {
		logger.info("【客户端】HttpServiceImpl-uploadFileByHttpPost-发送附件给【中心服务器】start！");
		logger.info("发送地址为=="+serverUrl);		
		// 上传两种方式：PostMethod是使用FileBody将文件包装流包装起来，HttpPost是使用FilePart将文件流包装起来。
		// 这种方式，与上面类似，只不过变成了FileBody。上面的Part数组在这里对应HttpEntity。
		// 此处的HttpClient是org.apache.http.client.methods下的。
		// 已经过时的方法DefaultHttpClient
		// 已经过时的方法StringBody
		// 已经过时的方法MultipartEntity

		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.createDefault();

			// 把一个普通参数和文件上传给下面这个地址 是一个servlet
			HttpPost httpPost = new HttpPost(serverUrl);

			// File file =new File(localFilePath);
			// File file2 =new File("D:\\jetsen\\复合出版\\21包\\meta_class.sql");
			// FileBody bin = new FileBody(file);
			// FileBody bin2 = new FileBody(file2);

			StringBody userName = new StringBody("zhongying", ContentType.create("text/plain", Consts.UTF_8));
			StringBody password = new StringBody("123456", ContentType.create("text/plain", Consts.UTF_8));

			// .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)以浏览器兼容模式运行，防止文件名乱码。
			MultipartEntityBuilder mb = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);// RFC6532//BROWSER_COMPATIBLE
			// 相当于<input type="file" name="file"/>
			// 把文件转换成流对象FileBody
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			String home = request.getServletContext().getRealPath("/");
			for (int i = 0; i < attachmentList.size(); i++) {
				JetsenFiles attachment = attachmentList.get(i);
				File file = new File(ConfigurationUtil.fileUpload_Directory + attachment.getStoragePath());
				String path = home + attachment.getStoragePath();
				int length=FilePathUtil.getSuffix(file.getPath()).length();
				String newPath = "";
				if(length==3){
					newPath = path.substring(0, path.length()-34);
				}else if(length==4){
					newPath = path.substring(0, path.length()-35);
				}
		    	String newName = attachment.getStoragePath().substring(attachment.getStoragePath().length()-34, attachment.getStoragePath().length());
				download(Constant.SERVER_IMAGE_PATH+attachment.getStoragePath(), newName,newPath); 
				FileBody bin = new FileBody(file);
				mb.addPart("file", bin);
				StringBody attachment_name = new StringBody(attachment.getName(), ContentType.create("text/plain",
						Consts.UTF_8));
				mb.addPart("attachment_name", attachment_name);
				StringBody attachment_path = new StringBody(attachment.getStoragePath(), ContentType.create("text/plain",
						Consts.UTF_8));
				logger.info("【客户端】HttpServiceImpl-uploadFileByHttpPost-发送附件给【中心服务器】path=="+attachment.getStoragePath());
				mb.addPart("attachment_path", attachment_path);
				StringBody attachment_size = new StringBody(attachment.getFileSize() + "", ContentType.create("text/plain",
						Consts.UTF_8));
				mb.addPart("attachment_size", attachment_size);
				StringBody attachment_type = new StringBody(attachment.getType() + "", ContentType.create("text/plain",
						Consts.UTF_8));
				mb.addPart("attachment_type", attachment_type);
				StringBody attachment_uuid = new StringBody(attachment.getUuid() + "", ContentType.create("text/plain",
						Consts.UTF_8));
				mb.addPart("attachment_uuid", attachment_uuid);
			}

			// .addPart("file", bin)
			// reqEntity.addPart("file", "");
			// reqEntity.addPart("file", bin2)

			// 相当于<input type="text" name="userName" value=userName>
			mb.addPart("userName", userName).addPart("pass", password);
			HttpEntity reqEntity = mb.build();
			httpPost.setEntity(reqEntity);

			// 发起请求 并返回请求的响应
			response = httpClient.execute(httpPost);

//			System.out.println("HTTP服务器端接收附件后，反馈给本地的状态为:" + response.getFirstHeader("token"));

			// 获取响应对象
			HttpEntity resEntity = response.getEntity();
			// if (resEntity != null) {
			// // 打印响应长度
			// System.out.println("响应长度: " + resEntity.getContentLength());
			// // 打印响应内容
			// System.out.println("响应内容："+EntityUtils.toString(resEntity,
			// Charset.forName("UTF-8")));
			// }

			// 销毁
			EntityUtils.consume(resEntity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("【客户端】HttpServiceImpl-uploadFileByHttpPost-发送附件给【中心服务器】end！");
	}

	
	@Override
	public void uploadPicByHttpPost(String serverUrl, List<Book> bookList, Map map) {
		
		logger.info("【客户端】HttpServiceImpl-uploadPicByHttpPost-发送图书图片给【中心服务器】start！");
		logger.info("发送地址为=="+serverUrl);		

		logger.info("【客户端】HttpServiceImpl-uploadFileByHttpPost-发送图书图片给【中心服务器】end！");
	}
	
	
	@Override
	public void downloadFileByHttpGet(String serverUrl, String serverFilePath, String localFilePath) {
		logger.info("【客户端】HttpServiceImpl-downloadFileByHttpGet-开始去【中心服务器】下载附件start");	
		DefaultHttpClient httpClient = new DefaultHttpClient();
		OutputStream out = null;
		InputStream in = null;
		try {
			logger.info("请求【中心服务器】地址为=="+serverUrl+"    "+serverFilePath);		
			HttpGet httpGet = new HttpGet(serverUrl);
			httpGet.addHeader("userName", "userName");
			httpGet.addHeader("passwd", "passwd");
			httpGet.addHeader("fileName", "remoteFileName");
			httpGet.addHeader("filePath", serverFilePath);
			
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			in = entity.getContent();

			long length = entity.getContentLength();
			if (length <= 0) {
				logger.info("真遗憾，您从【中心服务器】下载的文件不存在！");
				return;
			}else{
				logger.info("恭喜您，下载成功从【中心服务器】！");
			}

//			System.out.println("下载附件，服务器端返回的结果:" + httpResponse.getFirstHeader("token"));

			String saveFileName = localFilePath.substring(localFilePath.lastIndexOf("/") + 1);
			String saveFileDirectory = localFilePath.substring(0,localFilePath.lastIndexOf("/")+1);
			
			logger.info("保存附件到【本地服务器】saveFileName=="+saveFileName);	
			logger.info("保存附件到【本地服务器】saveFileDirectory=="+saveFileDirectory);	
			File file = createFile(saveFileDirectory, saveFileName);

			out = new FileOutputStream(file);
			byte[] buffer = new byte[4096];
			int readLength = 0;
			while ((readLength = in.read(buffer)) > 0) {
				byte[] bytes = new byte[readLength];
				System.arraycopy(buffer, 0, bytes, 0, readLength);
				out.write(bytes);
			}
			
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("【客户端】HttpServiceImpl-downloadFileByHttpGet-开始去【中心服务器】下载附件end");	
	}

	/**
	 *  @param path 目录路径
	 *  @param fileName文件名称
	 *  @return
	 */
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
	public static void download(String urlString, String filename,String savePath) throws Exception {  
        // 构造URL  
        URL url = new URL(urlString);  
        // 打开连接  
        URLConnection con = url.openConnection();  
        //设置请求超时为5s  
        con.setConnectTimeout(5*1000);  
        // 输入流  
        InputStream is = con.getInputStream();  
      
        // 1K的数据缓冲  
        byte[] bs = new byte[1024];  
        // 读取到的数据长度  
        int len;  
        // 输出的文件流  
       File sf=new File(savePath);  
       if(!sf.exists()){  
           sf.mkdirs();  
       }  
       OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);  
        // 开始读取  
        while ((len = is.read(bs)) != -1) {  
          os.write(bs, 0, len);  
        }  
        // 完毕，关闭所有链接  
        os.close();  
        is.close();  
    }   
	@Override
	// 直接下载
	@Deprecated
	public void downloadFileByHttpGet2(String serverUrl, String serverFilePath, String localFilePath) {
		// 下载中心服务器的资源到本地资源服务器
		/**
		HttpClient client = new DefaultHttpClient();
		HttpGet httpget = null;
		try {
			String remoteFileUrl = serverUrl + serverFilePath;
			String localRootPath = Constant.sdadfssfdsfsfsfd;

			File newFile = new File(localRootPath + localFilePath);
			// 判断目标文件所在的目录是否存在
			if (!newFile.getParentFile().exists()) {
				// 如果目标文件所在的目录不存在，则创建父目录
				System.out.println("目标文件所在目录不存在，准备创建它！");
				if (!newFile.getParentFile().mkdirs()) {
					System.out.println("创建目标文件所在目录失败！");
				}
			}

			httpget = new HttpGet(new URI(remoteFileUrl));
			HttpResponse response = client.execute(httpget);
			InputStream is = null;
			OutputStream os = null;
			HttpEntity entity = null;
			entity = response.getEntity();
			is = entity.getContent();
			// outFileName=UploadUtils.generateFilename(uploadPath,
			// FileNameUtils.getFileSufix(imgUrl));
			os = new FileOutputStream(localRootPath + localFilePath);
			IOUtils.copy(is, os);
			is.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpget.releaseConnection();
		}
		*/
		
	}

	/**
	 * @Override
	 * @Deprecated public void uploadFileByPostMethod(String serverUrl, String
	 *             localFilePath,Map map) {
	 *             //Commons的HttpClient项目现在是生命的尽头，不再被开发。它已取代由Apache
	 *             HttpComponents项目HttpClient和的HttpCore模组，提供更好的性能和更大的灵活性。
	 *             //将文件封装到FilePart中，放入Part数组，同时，其他参数可以放入StringPart中，这里没有写，
	 *             只是单纯的将参数以setParameter的方式进行设置。
	 *             //此处的HttpClient是org.apache.commons.httpclient.HttpClient。
	 *             File file = new File(localFilePath); PostMethod filePost =
	 *             new PostMethod(serverUrl); HttpClient client = new
	 *             HttpClient();
	 * 
	 *             try { // 通过以下方法可以模拟页面参数提交 filePost.setParameter("userName",
	 *             userName); filePost.setParameter("passwd", passwd);
	 * 
	 *             Part[] parts = { new FilePart(file.getName(), file) };
	 *             filePost.setRequestEntity(new MultipartRequestEntity(parts,
	 *             filePost.getParams()));
	 * 
	 *             client.getHttpConnectionManager().getParams().
	 *             setConnectionTimeout(5000);
	 * 
	 *             int status = client.executeMethod(filePost); if (status ==
	 *             HttpStatus.SC_OK) { System.out.println("上传成功"); } else {
	 *             System.out.println("上传失败"); } } catch (Exception ex) {
	 *             ex.printStackTrace(); } finally {
	 *             filePost.releaseConnection(); } }
	 */

}
