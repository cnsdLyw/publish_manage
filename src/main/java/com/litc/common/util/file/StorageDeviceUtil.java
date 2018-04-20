package com.litc.common.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.litc.common.bean.FtpBean;
import com.litc.common.bean.SpringContextHolder;
import com.litc.system.model.StorageDevice;
import com.litc.system.service.StorageDeviceService;

/**
 * ftp文件上传及下载工具类
 * 
 * @author liyw
 * 
 */
public class StorageDeviceUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(StorageDeviceUtil.class);
	
	/**
	 * 根据ftp名称获取ftp详细信息
	 * @param deviceName
	 * @return
	 */
	private static StorageDevice getStorageDevice(String deviceName){
		StorageDeviceService storageDeviceService = (StorageDeviceService) SpringContextHolder.getBean("storageDeviceService");
		StorageDevice device = storageDeviceService.getStorageDevice(deviceName);
		return device;
	}
	
	/**
	 * 上传文件到FTP
	 * @param deviceName ftp 设备名
	 * @param ftpFolderPath  上传到ftp的文件夹路径
	 * @param localFile 被上传的本地文件路径，包含路径+文件名称
	 */
	public static void uploadFile(String deviceName, String ftpFolderPath,
			File localFile) throws Exception {
		uploadFile(deviceName, ftpFolderPath, localFile.getName(), localFile);
	}

	/**
	 * 上传文件到FTP
	 * @param deviceName设备名
	 * @param ftpFolderPath上传到设备上的文件夹路径
	 * @param ftpNewFileName上传到设备上的文件名
	 * @param localFile 被上传的本地文件路径
	 * @return
	 */
	public static void uploadFile(String deviceName, String ftpFolderPath,
			String ftpNewFileName, File localFile) throws Exception {
		StorageDevice device = getStorageDevice(deviceName);
		String localPath = device.getLocalFolderPath();
		String ftpUrl = device.getFtpUrl();
		if (!ftpFolderPath.endsWith("/")
				&& !ftpFolderPath.endsWith(File.separator)) {
			ftpFolderPath += File.separator;
		}
		if (StringUtils.isNotBlank(localPath) && new File(localPath).exists()&&isLocalHostIPOrHostName(ftpUrl)) {
			try {
				File tempFile = new File(localPath + File.separator+ ftpFolderPath);
				if (!tempFile.exists()) {
					tempFile.mkdirs();
				}
				FileUtil.copyFile(localFile, new File(localPath
						+ File.separator + ftpFolderPath + ftpNewFileName));
				logger.info("采用本地拷贝的形式上传文件  "+localPath+ File.separator + ftpFolderPath + ftpNewFileName);
			} catch (IOException e) {
				throw new Exception("IOException:" + e.getMessage());
			}
		} else {
			InputStream in = null;
			try {
				in = new FileInputStream(localFile);
				uploadFile(deviceName, ftpFolderPath, ftpNewFileName, in);
				logger.info("采用ftp的形式上传文件  "+ftpFolderPath +","+ ftpNewFileName);
			} catch (Exception e) {
				throw new Exception(e);
			} finally {
				if ( in == null )
					return;
				try {
					in.close();
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
		}
	}
/*
	public static void uploadFile(String deviceName, String ftpFolderPath,
			String ftpNewFileName, File localFile) throws Exception {
		StorageDevice device = getStorageDevice(deviceName);
		String localPath = device.getLocalFolderPath();
		String ftpUrl = device.getFtpUrl();
		if (!ftpFolderPath.endsWith("/")
				&& !ftpFolderPath.endsWith(File.separator)) {
			ftpFolderPath += File.separator;
		}
		if (StringUtils.isNotBlank(localPath) && new File(localPath).exists()&&isLocalHostIPOrHostName(ftpUrl)) {
			try {
				File tempFile = new File(localPath + File.separator+ ftpFolderPath);
				if (!tempFile.exists()) {
					tempFile.mkdirs();
				}
				FileUtil.copyFile(localFile, new File(localPath
						+ File.separator + ftpFolderPath + ftpNewFileName));
				logger.info("采用本地拷贝的形式上传文件  "+localPath+ File.separator + ftpFolderPath + ftpNewFileName);
			} catch (IOException e) {
				throw new Exception("IOException:" + e.getMessage());
			}
		} else {
			//
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(localFile));
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			byte[] bytes = new byte[1024];
			// 将文件内容写入位数组流
			int len = 0;
			while ((len = bufferedInputStream.read(bytes)) != -1) {
				arrayOutputStream.write(bytes, 0, len);
			}
			ByteArrayInputStream in = null;
			try {
				in =  new ByteArrayInputStream(arrayOutputStream.toByteArray());
				uploadFile(deviceName, ftpFolderPath, ftpNewFileName, in);
				logger.info("采用ftp的形式上传文件  "+ftpFolderPath +","+ ftpNewFileName);
			} catch (Exception e) {
				throw new Exception(e);
			} finally {
				if ( in == null )
					return;
				try {
					in.close();
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
		}
	}*/
	
	/**
	 * 上传文件到服务器
	 * @param deviceName设备名
	 * @param ftpFolderPath上传到设备上的文件夹路径
	 * @param ftpNewFileName上传到设备上的文件名
	 * @param in 上传的输入流
	 * @throws Exception
	 */
	public static void uploadFile(String deviceName, String ftpFolderPath,
			String ftpNewFileName, InputStream in) throws Exception {
		JetsenFtpClient ftpUtil = null;
		try {
			StorageDevice device = getStorageDevice(deviceName);
			FtpBean ftpBean = new FtpBean();
			if (!ftpFolderPath.endsWith("/")
					&& !ftpFolderPath.endsWith(File.separator)) {
				ftpFolderPath += File.separator;
			}
			ftpBean.convert(device, ftpFolderPath + ftpNewFileName);
			ftpUtil = new JetsenFtpClient(ftpBean.getHost(), ftpBean.getPort(),
					ftpBean.getUser(), ftpBean.getPass());

			ftpUtil.uploadToDirectory(in, ftpBean.getRelativePath(), ftpNewFileName);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			ftpUtil.closeFtp();
		}
	}
	
	

	/**
	 * 上传文件夹到ftp
	 * @param deviceName设备名
	 * @param ftpFolderPath上传到ftp的文件夹路径
	 * @param localFolder 本地文件夹路径
	 * @throws Exception
	 */
	public static void uploadFolder(String deviceName, String ftpFolderPath,
			File localFolder) throws Exception {
		StorageDevice device = getStorageDevice(deviceName);
		String localPath = device.getLocalFolderPath();
		String ftpUrl = device.getFtpUrl();
		if (!ftpFolderPath.endsWith("/")
				&& !ftpFolderPath.endsWith(File.separator)) {
			ftpFolderPath += File.separator;
		}
		if (localPath != null && localPath.length() > 0&& new File(localPath).exists()&&isLocalHostIPOrHostName(ftpUrl)) {
			File tempFile = new File(localPath + File.separator + ftpFolderPath);
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			FileUtil.copyFolder(localFolder.getAbsolutePath(),tempFile.getAbsolutePath());
			logger.info("采用本地拷贝的形式上传文件夹  源文件夹"+localFolder +",目的文件夹 "+ftpFolderPath);
		} else {
			FtpBean ftpBean = new FtpBean();
			ftpBean.convert(device, ftpFolderPath);
			try {
				JetsenFtpClient ftpClient = new JetsenFtpClient(ftpBean.getHost(),
						ftpBean.getPort(), ftpBean.getUser(), ftpBean.getPass());
				ftpClient.uploadFolderToDir(localFolder,ftpBean.getRelativePath());
				logger.info("采用ftp的形式上传文件夹  "+ftpFolderPath +","+ localFolder);
			} catch (IOException e) {
				throw new Exception(e);
			}
		}
	}
	
	//下载文件
	/**
	 * 从存储设备上下载文件到本地文件夹下，以本地文件名重命名
	 * @param deviceName 设备名
	 * @param ftpFilePath 设备相对文件路径
	 * @param localFilePath 本地文件路径 ,不需要存在，下载过程中会新建该文件
	 * @return
	 */
	public static void downloadFile(String ftpFilePath, String localFilePath)
			throws Exception {
		File localFile = new File(localFilePath);
		String[] arr = getPathArr(ftpFilePath);
		if (arr.length != 2)
			throw new Exception("FTP文件路径错误");
		downloadFile(arr[0], arr[1], localFile.getParent(), localFile.getName());
	}
	
	/**
	 * @param deviceName
	 * @param ftpFilePath
	 * @param localFolderPath
	 * @param newName
	 * @throws Exception
	 */
	public static void downloadFile(String deviceName, String ftpFilePath,
			String localFolderPath, String newName) throws Exception {
		StorageDevice device = getStorageDevice(deviceName);
		String localPath = device.getLocalFolderPath();
		String ftpUrl = device.getFtpUrl();

		// 如果是在本机，则采用本地路径拷贝方式
		if ((localPath != null && localPath.length() > 0&& new File(localPath).exists() && isLocalHostIPOrHostName(ftpUrl))) {
			File srcFile = new File(localPath + File.separator + ftpFilePath);
			File newFile = new File(localFolderPath + File.separator + newName);
			try {
				File tempFile = new File(localFolderPath);
				if (!tempFile.exists()) {
					tempFile.mkdirs();
				}
				logger.info("使用拷贝方式下载，srcFile:" + srcFile.getAbsolutePath()
						+ ",newFile:" + newFile.getAbsolutePath());
				FileUtil.copyFile(srcFile, newFile);
			} catch (IOException e) {
				throw new Exception("IOException:" + e.getMessage());
			}
		} else {
			logger.info("使用FTP方式下载，device:" + device.getFtpCode() + ",ftpFilePath:"
					+ ftpFilePath);
			downloadFileByFtp(device, ftpFilePath, localFolderPath, newName);
		}
	}
	

	/**
	 * 从存储设备上下载文件到本地文件夹下，并重命名
	 * @param deviceName设备名
	 * 
	 * @param ftpFilePath设备相对文件路径
	 * @param localFolderPath本地文件夹路径
	 * 
	 * @param newName
	 *            下到本地后的新文件名
	 * @return
	 */
	public static void downloadFileByFtp(StorageDevice device,
			String ftpFilePath, String localFolderPath, String newName)
			throws Exception {
		JetsenFtpClient ftpClient = null;
		try {
			FtpBean ftpBean = new FtpBean();
			ftpBean.convert(device, ftpFilePath);
			ftpClient = new JetsenFtpClient(ftpBean.getHost(), ftpBean.getPort(),ftpBean.getUser(), ftpBean.getPass());
			String srcName = FileUtil.getFileName(ftpFilePath);
			String relPath = ftpBean.getRelativePath() + "/" + srcName;
			File localFolder = new File(localFolderPath);
			ftpClient.downloadToDirectory(relPath, localFolder);
			if (!FileUtil.getFileName(ftpFilePath).equals(newName)) {
				File localFile = new File(localFolderPath + "/" + srcName);
				File newFile = new File(localFolderPath + "/" + newName);
				if (newFile.exists()) {
					newFile.delete();
				}
				localFile.renameTo(newFile);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			ftpClient.closeFtp();
		}
	}
	//下载文件夹
	public static void downloadFolder(String deviceName, String ftpFolderPath,
			String localFolderPath) throws Exception {
		StorageDevice device = getStorageDevice(deviceName);
		String localPath = device.getLocalFolderPath();
		String ftpUrl = device.getFtpUrl();

		if (localPath != null && localPath.length() > 0 && new File(localPath).exists() && isLocalHostIPOrHostName(ftpUrl)) {
			try {
				FileUtil.copyDirectiory(localPath + File.separator
						+ ftpFolderPath, localFolderPath);
			} catch (IOException e) {
				throw new Exception("IOException:" + e.getMessage());
			}
		} else {
			JetsenFtpClient ftpClient = null;
			try {
				FtpBean ftpBean = new FtpBean();
				ftpBean.convert(device, ftpFolderPath);
				ftpClient = new JetsenFtpClient(ftpBean.getHost(), ftpBean.getPort(),
						ftpBean.getUser(), ftpBean.getPass());
				String relPath = ftpFolderPath;
				if (!relPath.endsWith("/") && !relPath.endsWith(File.separator)) {
					relPath = relPath + File.separator;
				}
				ftpClient.downloadAllToDir(relPath, localFolderPath);
			} catch (Exception e) {
				throw new Exception(e);
			} finally {
				ftpClient.closeFtp();
			}
		}
	}
	
	/**
	 * FTP之间拷贝文件
	 * @param srcFile 原始文件，格式为ftp_*,/*
	 * @param destFile  目标文件全名，格式为ftp_*,/*或者直接是相对目录
	 * @throws Exception
	 */
	public static void copyFtpFile(String srcFile, String destFile)
			throws Exception {
		JetsenFtpClient ftpClient = null;
		try {
			String[] arr = getPathArr(srcFile);
			String deviceName = arr[0];
			String srcRelPath = arr[1];
			StorageDevice device = getStorageDevice(deviceName);
			FtpBean ftpBean = new FtpBean();
			ftpBean.convert(device, srcRelPath);
			ftpClient = new JetsenFtpClient(ftpBean.getHost(), ftpBean.getPort(),
					ftpBean.getUser(), ftpBean.getPass());
			String relPath = ftpBean.getRelativePath();
			if (!relPath.endsWith("/") && !relPath.endsWith(File.separator)) {
				relPath = relPath + File.separator;
			}
			String[] dests = getPathArr(destFile);
			if (dests.length == 1
					|| (dests.length == 2 && dests[0].equals(deviceName))) {
				if (dests.length == 2) {
					destFile = dests[1];
				}
				destFile.replace("\\", "/");
				ftpClient.copyFile(srcRelPath, destFile);
			} else if (dests.length == 2) {
				device = getStorageDevice(dests[0]);
				ftpBean.convert(device, dests[1]);
				ftpClient.copyFile(srcRelPath, ftpBean,
						FileUtil.getFileName(dests[1]));
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			ftpClient.closeFtp();
		}
	}

	
	/**
	 * 删除存储设备上的文件，包括文件夹或文件夹
	 * @param deviceName 设备名
	 * @param relativePath 设备相对文件路径
	 * @throws Exception
	 */
	public static void deleteFtpPath(String ftpPath) throws Exception {
		if (ftpPath != null && !ftpPath.equals("")) {
			String[] paths = getPathArr(ftpPath);
			if (paths.length < 2) {
				throw new Exception("删除文件路径不正确：" + ftpPath);
			} else if (FileUtil.getFileExt(ftpPath) == null) {
				deletePath(paths[0], paths[1]);
			} else {
				deleteFile(paths[0], paths[1]);
			}
		}
	}
	
	//删除文件
	/**
	 * 删除存储设备上的文件
	 * @param devicePath 设备名+文件地址
	 * @throws Exception
	 */
	public static void deleteFile(String devicePath) throws Exception {
		if (devicePath != null && !devicePath.equals("")) {
			String[] paths = getPathArr(devicePath);
			if (paths.length < 2) {
				throw new Exception("删除文件路径不正确：" + devicePath);
			} else {
				deleteFile(paths[0], paths[1]);
			}
		}
	}

	/**
	 * 删除ftp上的文件
	 * @param deviceName   设备名
	 * @param relativePath 设备相对文件路径
	 * @throws Exception
	 */
	public static void deleteFile(String deviceName, String relativePath)
			throws Exception {
		StorageDevice device = getStorageDevice(deviceName);
		JetsenFtpClient client = null;
		try {
			if (device != null) {
				FtpBean ftpBean = new FtpBean();
				ftpBean.convert(device, relativePath);
				client = new JetsenFtpClient(ftpBean.getHost(), ftpBean.getPort(),
						ftpBean.getUser(), ftpBean.getPass());
				if (!relativePath.startsWith("/")&& !relativePath.startsWith(File.separator)) {
					relativePath = File.separator + relativePath;
				}
				String parentPath = "/"+ StorageDeviceHelper.getWholeFtpPath(device,FileUtil.getFileName(relativePath));
				parentPath = parentPath.replaceAll("//", "/");
				client.deleteFile(parentPath + relativePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("删除ftp文件失败：" + e.getMessage());
		} finally {
			client.closeFtp();
		}
	}
	//删除文件夹
	/**
	 * 删除ftp上的文件夹
	 * @param deviceName设备名
	 * @param relativePath 设备路径
	 * @throws Exception
	 */
	public static void deletePath(String deviceName, String relativePath)
			throws Exception {
		StorageDevice device = getStorageDevice(deviceName);
		JetsenFtpClient client = null;
		try {
			FtpBean ftpBean = new FtpBean();
			ftpBean.convert(device, relativePath);
			client = new JetsenFtpClient(ftpBean.getHost(), ftpBean.getPort(),ftpBean.getUser(), ftpBean.getPass());
			if (!relativePath.startsWith("/")&& !relativePath.startsWith(File.separator)) {
				relativePath = File.separator + relativePath;
			}
			String parentPath = "/"+ StorageDeviceHelper.getWholeFtpPath(device,FileUtil.getFileName(relativePath));
			parentPath = parentPath.replaceAll("//", "/");
			client.deleteDirectory(parentPath, relativePath);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("删除ftp文件夹失败：" + e.getMessage());
		} finally {
			client.closeFtp();
		}
	}
	
	/**
	 * 获取ftp存储设备和存储路径数组
	 * @param ftpPath
	 * @return
	 */
	public static String[] getPathArr(String ftpPath) {
		String[] arr = new String[2];
		if (ftpPath != null && ftpPath.indexOf(",") > 0) {
			int index = ftpPath.indexOf(",");
			arr[0] = ftpPath.substring(0, index);
			arr[1] = ftpPath.substring(index + 1);
		}
		return arr;
	}
	/**
	 * 判断当前要上传的ftp地址是否是本机地址。
	 * @param localPath
	 * @param ftpUrl
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean isLocalHostIPOrHostName(String ftpUrl)
			throws Exception {
		String tempUrl = ftpUrl.substring(6);
		int index = tempUrl.indexOf(":");
		if (index == -1)
			index = tempUrl.indexOf("/");
		String url = tempUrl.substring(0, index);
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			throw new Exception("找不到主机:" + e.getMessage());
		}

		String localHostName = address.getHostName();
		String localHostIP = address.getHostAddress();
		if (url.equals(localHostIP) || url.equals(localHostName))
			return true;

		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface ni = interfaces.nextElement();
				Enumeration<?> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					String localIP = ((InetAddress) ips.nextElement())
							.getHostAddress();
					if (!localIP.equals("127.0.0.1")&& !localIP.equals("0:0:0:0:0:0:0:1")&& url.equals(localIP)){
						return true;
					}
				}
			}
		} catch (SocketException e) {
			throw new Exception("SocketException:" + e.getMessage());
		}

		return false;
	}

	public static void getFile() {
		StorageDevice device = getStorageDevice("FTP_D");
		System.out.println(" " + device.getFtpName());
	}
}
