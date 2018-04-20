package com.litc.common.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.util.Streams;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * Function:打包压缩文件，并下载
 * 
 * @author zhongying(281264212@qq.com)
 * @date 2015-12-5 下午5:23:59
 * @version 1.0
 */
public class ZipFileDownloadUtil {

	/**
	 * 压缩文件列表中的文件
	 * 
	 * @param files
	 * @param outputStream
	 * @throws IOException
	 */
	public static void zipFile(List files, ZipOutputStream outputStream)
			throws IOException, ServletException {
		try {
			int size = files.size();
			// 压缩列表中的文件
			for (int i = 0; i < size; i++) {
				File file = (File) files.get(i);
				zipFile(file, outputStream);
			}
		} catch (IOException e) {
			throw e;
		}
	}
	
	/**
	 * 压缩文件列表中的文件
	 * 
	 * @param files
	 * @param outputStream
	 * @throws IOException
	 */
	public static void zipFile(int folderLength, File[] files, ZipOutputStream outputStream)
			throws IOException, ServletException {
		try {
			int size = files.length;
			// 压缩列表中的文件
			for (int i = 0; i < size; i++) {
				File file = (File) files[i];
				if(file.isDirectory()){//如果是文件夹，递归压缩
					zipFolder(folderLength,file,outputStream);
				}else{
					zipFile(folderLength,file, outputStream);
				}
			
			}
		} catch (IOException e) {
			throw e;
		}
	}

	private static void zipFolder(int folderLength,File file, ZipOutputStream outputStream) throws IOException, ServletException{
		File[] fileList = file.listFiles();
		for(File temp:fileList){
			if(temp.isDirectory()){
				zipFolder(folderLength,temp, outputStream);
			}else{
				zipFile(folderLength,temp, outputStream);
			}
		}
	}
	
	/**
	 * 解压文件，包含文件夹路径
	 * 
	 * @param zipFile
	 *            要解压的文件路径，解压目的文件夹
	 * @param descDir
	 * @throws IOException
	 */
	public static void unZipFiles(File zipFile, String descDir)
			{
		File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		try {
			ZipFile zip = new ZipFile(zipFile);
			for (Enumeration entries = zip.getEntries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String zipEntryName = entry.getName();
				InputStream in = zip.getInputStream(entry);
				String outPath = (descDir + zipEntryName).replaceAll("\\\\", "/");
				// 判断路径是否存在,不存在则创建文件路径
				File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
				if (!file.exists()) {
					file.mkdirs();
				}
				// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
				if (new File(outPath).isDirectory()) {
					continue;
				}
				// 输出文件路径信息
				// System.out.println(outPath);

				OutputStream out = new FileOutputStream(outPath);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				in.close();
				out.close();

			}
			//此处要关闭文件，否则无法删除。
			zip.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("******************解压完毕********************");
	}
	/**
	 * 解压文件，包含文件夹路径
	 * 
	 * @param zipFile
	 *            要解压的文件路径，解压目的文件夹
	 * @param descDir
	 * @throws IOException
	 */
	public static Map<String, Object> unZipFilesRtn(File zipFile, String descDir)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		String fileOutPath = "";
		File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		try {
			ZipFile zip = new ZipFile(zipFile);
			for (Enumeration entries = zip.getEntries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String zipEntryName = entry.getName();
				InputStream in = zip.getInputStream(entry);
				String outPath = (descDir + zipEntryName).replaceAll("\\\\", "/");
				// 判断路径是否存在,不存在则创建文件路径
				File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
				
				if (!file.exists()) {
					file.mkdirs();
				}
				// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
				if (new File(outPath).isDirectory()) {
					continue;
				}
				// 输出文件路径信息
				fileOutPath = outPath.substring(0, outPath.lastIndexOf("/"));
				//System.out.println("////////////"+fileOutPath);
				OutputStream out = new FileOutputStream(outPath);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				in.close();
				out.close();
				
			}
			//此处要关闭文件，否则无法删除。
			zip.close();
			System.out.println("******************解压完毕********************");
			map.put("url", fileOutPath);
			map.put("code", 0);
			return map;
		} catch (IOException e) {
			e.printStackTrace();
			map.put("code", -1);
			return map;
		}
	}
	 
	/**
	 * 将文件写入到zip文件中,包含文件夹路径
	 * @param folderLength
	 * @param inputFile
	 * @param outputstream
	 * @throws Exception
	 */
	public static void zipFile(int folderLength,File inputFile, ZipOutputStream outputstream)
			throws IOException, ServletException {
		try {
			if (inputFile.exists()) {
				if (inputFile.isFile()) {
					FileInputStream inStream = new FileInputStream(inputFile);
					BufferedInputStream bInStream = new BufferedInputStream(inStream);
					String filePath = inputFile.getPath().substring(folderLength+1, inputFile.getPath().length());
					ZipEntry entry = new ZipEntry(filePath);
					outputstream.putNextEntry(entry);
					final int MAX_BYTE = 10 * 1024 * 1024; // 最大的流为10M
					long streamTotal = 0; // 接受流的容量
					int streamNum = 0; // 流需要分开的数量
					int leaveByte = 0; // 文件剩下的字符数
					byte[] inOutbyte; // byte数组接受文件的数据

					streamTotal = bInStream.available(); // 通过available方法取得流的最大字符数
					streamNum = (int) Math.floor(streamTotal / MAX_BYTE); // 取得流文件需要分开的数量
					leaveByte = (int) streamTotal % MAX_BYTE; // 分开文件之后,剩余的数量

					if (streamNum > 0) {
						for (int j = 0; j < streamNum; ++j) {
							inOutbyte = new byte[MAX_BYTE];
							// 读入流,保存在byte数组
							bInStream.read(inOutbyte, 0, MAX_BYTE);
							outputstream.write(inOutbyte, 0, MAX_BYTE); // 写出流
						}
					}
					// 写出剩下的流数据
					inOutbyte = new byte[leaveByte];
					bInStream.read(inOutbyte, 0, leaveByte);
					outputstream.write(inOutbyte);
					outputstream.closeEntry(); // Closes the current ZIP entry
												// and positions the stream for
												// writing the next entry
					bInStream.close(); // 关闭
					inStream.close();
				}
			} else {
				throw new ServletException("文件不存在！");
			}
		} catch (IOException e) {
			throw e;
		}
	}
	
	/**
	 * 将文件写入到zip文件中
	 * @param inputFile
	 * @param outputstream
	 * @throws Exception
	 */
	public static void zipFile(File inputFile, ZipOutputStream outputstream)
			throws IOException, ServletException {
		try {
			if (inputFile.exists()) {
				if (inputFile.isFile()) {
					FileInputStream inStream = new FileInputStream(inputFile);
					BufferedInputStream bInStream = new BufferedInputStream(
							inStream);
					ZipEntry entry = new ZipEntry(inputFile.getName());
					outputstream.putNextEntry(entry);

					final int MAX_BYTE = 10 * 1024 * 1024; // 最大的流为10M
					long streamTotal = 0; // 接受流的容量
					int streamNum = 0; // 流需要分开的数量
					int leaveByte = 0; // 文件剩下的字符数
					byte[] inOutbyte; // byte数组接受文件的数据

					streamTotal = bInStream.available(); // 通过available方法取得流的最大字符数
					streamNum = (int) Math.floor(streamTotal / MAX_BYTE); // 取得流文件需要分开的数量
					leaveByte = (int) streamTotal % MAX_BYTE; // 分开文件之后,剩余的数量

					if (streamNum > 0) {
						for (int j = 0; j < streamNum; ++j) {
							inOutbyte = new byte[MAX_BYTE];
							// 读入流,保存在byte数组
							bInStream.read(inOutbyte, 0, MAX_BYTE);
							outputstream.write(inOutbyte, 0, MAX_BYTE); // 写出流
						}
					}
					// 写出剩下的流数据
					inOutbyte = new byte[leaveByte];
					bInStream.read(inOutbyte, 0, leaveByte);
					outputstream.write(inOutbyte);
					outputstream.closeEntry(); // Closes the current ZIP entry
												// and positions the stream for
												// writing the next entry
					bInStream.close(); // 关闭
					inStream.close();
				}
			} else {
				throw new ServletException("文件不存在！");
			}
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 下载打包的文件
	 * 
	 * @param file
	 * @param response
	 */
	public static void downloadZip(File file, HttpServletResponse response,
			String zipName) {
		try {
			// 以流的形式下载文件。
			BufferedInputStream fis = new BufferedInputStream(
					new FileInputStream(file.getPath()));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();

			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/octet-stream");
			// response.setHeader("Content-Disposition", "attachment;filename="
			// + file.getName());
			// response.setHeader解决文件名只能输入17个汉字，和乱码处理和空格处理
			// String str=toUtf8String("下载使得说三  道四反反复复反反的方芳芳淡淡的方芳芳");
			// DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 hh点mm分ss秒");
			// String cdate= df.format(new Date());
			String strUtf8 = toUtf8String(zipName);
			response.setHeader("Content-Disposition", "attachment;filename="
					+ strUtf8 + ".zip");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
			file.delete(); // 将生成的服务器端文件删除
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 下载文件
	 * @param file
	 * @param response
	 */
	public static void downloadfile(HttpServletResponse response,String tempFilePath ,String fileName) throws IOException{
		   BufferedInputStream bis=null;
           BufferedOutputStream bos=null;
	   try {
	       URL url = new URL(tempFilePath);
	       HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
	       connection.setRequestMethod("GET");
	       connection.connect();
	       InputStream is = connection.getInputStream();
	       bis = new BufferedInputStream(is);
	       OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		   response.setContentType("application/octet-stream");
		   String strUtf8 = toUtf8String(fileName);
		   response.setHeader("Content-Disposition", "attachment;filename="+ strUtf8 );
		   int b = 0;
		   byte[] byArr = new byte[1024*4];
		     while((b=bis.read(byArr))!=-1){
		           toClient.write(byArr, 0, b);
		     }
			toClient.flush();
			toClient.close();
			
	   } catch (Exception e) {
	       e.printStackTrace();
	   }finally{
	       try {
	           if(bis!=null){
	               bis.close();
	           }
	           if(bos!=null){
	               bos.close();
	           }
	       } catch (IOException e) {
	           e.printStackTrace();
	       }
	   }
	 }
	/**
	 * 下载文件到本地临时目录
	 * @param file
	 * @param response
	 */
	public static File downloadfileTemp(String downPath,String filePath) throws IOException{
		File file =null;
		try {
			URL url = new URL(filePath);
			InputStream in = url.openStream();
			DownloadRemoteFileUtil.createFile(downPath, 20);
			file = new File(downPath);
			FileOutputStream out = new FileOutputStream(file);
			Streams.copy(in, out, true);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
		return file;
	}
	/**
	 * 下载文件
	 * @param file
	 * @param response
	 */
	public static void downloadFile(File file, HttpServletResponse response,
			String fileName) {
		String fileType = file.getName().substring(file.getName().lastIndexOf("."));
		try {
			// 以流的形式下载文件。
			BufferedInputStream fis = new BufferedInputStream(
					new FileInputStream(file.getPath()));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			String strUtf8 = toUtf8String(fileName);
			response.setHeader("Content-Disposition", "attachment;filename="
					+ strUtf8 + fileType.toLowerCase());
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 下载日志的文件
	 * 
	 * @param file
	 * @param response
	 */
	public static void downloadTxt(File file, HttpServletResponse response,
			String zipName) {
		try {
			// 以流的形式下载文件。
			BufferedInputStream fis = new BufferedInputStream(
					new FileInputStream(file.getPath()));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/txt");
			String strUtf8 = toUtf8String(zipName);
			response.setHeader("Content-Disposition", "attachment;filename="
					+ strUtf8 + ".txt");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
			file.delete(); 
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
	public static void  deleteTempFile(String path) {
	       File file = new File(path);  
	       if (!file.exists()) {  
	         return ;  
	       }  
	       if (!file.isDirectory()) {  
	         return ;  
	       }  
	       String[] tempList = file.list();  
	       File temp = null;  
	       for (int i = 0; i < tempList.length; i++) {  
	          if (path.endsWith(File.separator)) {  
	             temp = new File(path + tempList[i]);  
	          } else {  
	              temp = new File(path + File.separator + tempList[i]);  
	          }  
	          if (temp.isFile()) {  
	             temp.delete();  
	          }  
	          if (temp.isDirectory()) {  
	        	  deleteTempFile(path + "/" + tempList[i]);//先删除文件夹里面的文件  
	              delFolder(path + "/" + tempList[i]);//再删除空文件夹  
	              
	          }  
	       }  
	    
	     }  
		//删除文件夹  
		public static void delFolder(String folderPath) {  
		   try {  
			  deleteTempFile(folderPath); //删除完里面所有内容  
		      String filePath = folderPath;  
		      filePath = filePath.toString();  
		      java.io.File myFilePath = new java.io.File(filePath);  
		      myFilePath.delete(); //删除空文件夹  
		   } catch (Exception e) {  
		     e.printStackTrace();   
		   }  
		}
}
