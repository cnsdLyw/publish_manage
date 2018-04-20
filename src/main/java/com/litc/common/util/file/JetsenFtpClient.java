package com.litc.common.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.litc.common.bean.FtpBean;

public class JetsenFtpClient {
	private String host;
	private int port;
	private String user;
	private String pass;
	private FTPClient client;
	private int connectNum = 5;
	// 记录创建的路径
	private List<Path> createdPathList = new ArrayList<Path>();

	public JetsenFtpClient(String host, int port, String user, String pass)
			throws IOException {
		super();
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
		
		try {
			client = new FTPClient();
			client.setControlEncoding("UTF-8");
			client.setDefaultPort(this.port);
			client.setConnectTimeout(60 * 1000);
			client.connect(this.host);
			client.setSoTimeout(60 * 1000);
			client.login(this.user, this.pass);
			if (!client.setFileType(2)){//设置二进制文件方式上传
				throw new IOException("设置二进制文件方式上传失败");
			}	
			if (!client.setFileStructure(7)){
				throw new IOException("设置文件结构失败");
			}
			//client.enterLocalPassiveMode();//设置本地被动模式
			//client.enterRemotePassiveMode();//设置远程被动模式
			int reply = client.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				client.disconnect();
				throw new IOException("login failed: user=" + this.user
						+ " password=" + this.pass);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IOException("connect failed" + ex.getMessage());
		}
	}

	/**
	 * 关闭ftp连接
	 * 
	 * @throws IOException
	 */
	public void closeFtp() throws IOException {
		if (client.isConnected())
			client.disconnect();
	}

	/**
	 * 上传文件，以localFile的名字命名
	 * @param localFile 本地文件路径
	 * @param ftpPath 上传的ftp的文件路径
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFile(String localFile, String ftpPath)
			throws IOException {
		createPath(ftpPath, true);
		String ftpFile = ftpPath + "/" + (new File(localFile)).getName();
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(localFile));
		boolean flag;
		try {
			flag = client.storeFile(ftpFile, in);
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return flag;
	}

	/**
	 * 上传文件到指定文件夹的ftp
	 * @param stream  文件输入流
	 * @param ftpFile 上传到ftp上的文件路径
	 * @throws IOException
	 */
	public void uploadFile(InputStream stream, String ftpFile)
			throws IOException {
		File file = new File(ftpFile);
		String ftpPath = file.getParent();
		String filename = file.getName();
		createPath(ftpPath, true);
		try {
			boolean result = client.storeFile(filename, stream);
			if (!result) {
				throw new IOException("上传文件失败");
			}
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	/**
	 * 上传文件
	 * @param in
	 * @param ftpPath
	 * @param newName
	 * @throws IOException
	 */
	public void uploadToDirectory(InputStream in, String ftpPath, String newName)
			throws IOException {
		if (ftpPath == null){
			throw new NullPointerException("ftpPath");
		}
		createPath(ftpPath, true);
		String remoteName = newName;
		// 只尝试一次写入
		if (connectNum <= 0) {
			client.storeFile(remoteName, in);
		} else {
			int retry = 0;
			while (retry <= connectNum) {
				try {
					client.storeFile(remoteName, in);
					break;
				} catch (Exception ex) {
					String msg = ex.getMessage();
					retry++;
					if (retry > connectNum) {
						msg = "上传文件失败："+ " ftpPath=" + ftpPath + " newName=" + newName;
						throw new IOException(msg);
					}
				}
			}
		}
	}
	
	/**
	 * 上传本地文件localFile到ftpPath下，以newName命名
	 * @param localFile
	 * @param ftpPath
	 * @param newName
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	public void uploadToDirectory(File localFile, String ftpPath, String newName)
			throws IOException {
		if (ftpPath == null)
			throw new NullPointerException("ftpPath");
		createPath(ftpPath, true);
		String remoteName = newName != null ? newName : localFile.getName();
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				localFile));

		// 只尝试一次写入
		if (connectNum <= 0) {
			client.storeFile(remoteName, in);
		} else {
			int retry = 0;
			while (retry <= connectNum) {
				try {
					client.storeFile(remoteName, in);
					break;
				} catch (Exception ex) {
					String msg = ex.getMessage();
					retry++;
					if (retry > connectNum) {
						msg = "上传文件失败："+ " ftpPath=" + ftpPath + " newName=" + newName;
						if (in != null) {
							in.close();
						}
						throw new IOException(msg);
					}
					try {
						System.out.println("重试第" + retry + "连接...");
						Thread.currentThread().sleep(5000);
					} catch (InterruptedException e1) {
					}
				}
			}
		}
		if (in != null) {
			in.close();
		}
		return;
	}

	/**
	 * 上传本地文件localFile到ftpPath下，以localFile的名字命名
	 * 
	 * @param localFile
	 * @param ftpPath
	 * @throws IOException
	 */
	public void uploadToDirectory(File localFile, String ftpPath) throws IOException {
		uploadToDirectory(localFile, ftpPath, null);
	}

	public void uploadFolderToDir(File localDir, String ftpDirPath)
			throws IOException {
		if (localDir.exists()) {
			if (!ftpDirPath.endsWith("/")
					&& !ftpDirPath.endsWith(File.separator)) {
				ftpDirPath = ftpDirPath + File.separator;
			}
			File[] files = localDir.listFiles();
			if (files != null) {
				for (int k = 0; k < files.length; k++) {
					File file = files[k];
					if (file.isFile()) {
						uploadToDirectory(file, ftpDirPath);
					} else {
						String folderName = file.getName();
						String ftpPath = ftpDirPath + folderName
								+ File.separator;
						uploadFolderToDir(file, ftpPath);
					}
				}
			}
		}
	}

	/**
	 * 下载文件到本地
	 * @param ftpFile
	 * @param localDir
	 * @return
	 * @throws IOException
	 */
	public File downloadToDirectory(String ftpFile, File localDir) throws IOException {
		File localFile;
		boolean result;
		if (!localDir.exists()){
			localDir.mkdirs();
		}
		
		client.changeWorkingDirectory("/");
		//ftpFile="/mysddsd.txt";
		localFile = new File(localDir, (new File(ftpFile)).getName());
		result = false;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(localFile));
		if (connectNum <= 0) {
			result = client.retrieveFile(ftpFile, bos);
		} else {
			int retry = 0;
			while (retry <= connectNum) {
				try {
					System.out.println("ftpFile "+ftpFile);
					result = client.retrieveFile(ftpFile, bos);
					break;
				} catch (Exception ex) {
					retry++;
					if (retry > connectNum) {
						if (bos != null) {
							bos.close();
						}
						throw new IOException("下载文件出错：ftpFile=" + ftpFile
								+ ",localFile=" + localFile);
					}
					try {
						Thread.currentThread().sleep(5000);
					} catch (InterruptedException e1) {
					}
				}
			}
		}
		if (bos != null) {
			bos.close();
		}

		if (!result){
			throw new IOException("下载文件出错：ftpFile="+ ftpFile + ",localFile=" + localFile);
		}else{
			return localFile;
		}
			
	}
	
	/**
	   * 把此文件夹下的文件下载到本地
	   * 
	   * @param remotePath
	   *          FTP相对路径
	   * @param localPath
	   *          本地文件路径
	   * @throws IOException
	   */
	  public void downloadAllToDir(String remotePath, String localPath)
	      throws IOException
	  {
	    if (!remotePath.endsWith("/"))
	    {
	      remotePath = remotePath + "/";
	    }
	    localPath = localPath.replaceAll("\\\\", "/").replaceAll("////", "/");
	    if (!localPath.endsWith("/"))
	    {
	      localPath = localPath + "/";
	    }

	    File localDir = new File(localPath);
	    createPath(remotePath, false);
	    if (!localDir.exists())
	      localDir.mkdirs();

	    FTPFile[] ftpFile = this.client.listFiles();
	    if (ftpFile != null)
	    {
	      for (int k = 0; k < ftpFile.length; k++)
	      {
	        String fileName = ftpFile[k].getName();
	        if (fileName.equals(".") || fileName.equals(".."))
	        {
	          continue;
	        }

	        if (ftpFile[k].isDirectory())
	        {
	          String path = remotePath + fileName + "/";
	          String path2 = localPath + fileName + "/";
	          this.downloadAllToDir(path, path2);
	        }
	        else if (ftpFile[k].isFile())
	        {
	          this.downloadToDirectory(remotePath + fileName, localDir);
	        }

	      }
	    }

	  }

	/**
	 * 删除ftp文件
	 * 
	 * @param pathName
	 * @return
	 * @throws IOException
	 */
	public boolean deleteFile(String pathName) throws IOException {
		if (pathName == null) {
			return false;
		}
		pathName = pathName.trim();
		if (pathName.equals("")) {
			return false;
		}
		boolean b = client.deleteFile(pathName);
		return b;
	}

	/**
	 * 创建目录
	 * @param destPath
	 * @param createWhenNotExist
	 * @throws IOException
	 */
	public void createPath(String destPath, boolean isCreat) throws IOException {
		if (destPath == null || "".equals(destPath))
			throw new IllegalArgumentException();
		if (!isCreat && !client.changeWorkingDirectory(destPath)) {
			throw new IOException("切换目录失败：" + destPath);
		} else {
			String path = destPath.replace('\\', '/');
			if (path.startsWith("/")) {
				if (!client.changeWorkingDirectory("/"))
					throw new IOException("切换到根路径失败");
				path = path.substring(1);
			}
			// 当前目录
			String oldPath = client.printWorkingDirectory();
			if (!oldPath.endsWith("/")) {
				oldPath = oldPath + "/";
			}
			String createdPath = "";
			String paths[] = path.split("/");
			for (int i = 0; i < paths.length; i++) {
				String dirName = paths[i];
				if (dirName.equals("")) {
					continue;
				}
				if (!client.changeWorkingDirectory(dirName)) {
					createdPath = createdPath + dirName + "/";
					client.mkd(dirName);
					if (!client.changeWorkingDirectory(dirName)) {
						String wd = client.printWorkingDirectory();
						String msg = "在目录[" + wd + "]下创建目录[" + dirName + "]失败！";
						throw new IOException(msg);
					}
				} else {
					oldPath = oldPath + dirName + "/";
				}
			}

			if (!createdPath.equals("")) {// 创建了新的目录
				Path pathBean = new Path();
				pathBean.setCreatedPath(createdPath);
				pathBean.setWorkingDirectory(oldPath);
				this.createdPathList.add(pathBean);
			}
		}
	}

	/**
	 * 删除afterDirectory目录下的deletingPath
	 * @param afterDirectory 相对于根的目录
	 * @param deletingPath afterDirectory下的目录
	 * @throws IOException
	 */
	public void deleteDirectory(String afterDirectory, String deletingPath)
			throws IOException {
		StringBuffer directory = new StringBuffer();
		directory.append(afterDirectory);
		if (!afterDirectory.endsWith("/")) {
			directory.append("/");
		}
		directory.append(deletingPath);

		int newSize = deletingPath.split("/").length;
		client.setSoTimeout(60000);
		this.client.changeWorkingDirectory(directory.toString());
		for (int k = 0; k < newSize; k++) {
			int count = this.client.listFiles().length;
			if (count == 2 || count == 0) {
				this.client.removeDirectory(this.client.printWorkingDirectory());
				this.client.changeToParentDirectory();
			} else {
				return;
			}
		}

	}
	
	/**
	 * 把流保存到ftpPath下，以newName命名
	 * @param in
	 * @param ftpPath
	 * @param newName
	 * @throws IOException
	 */
	public void uploadToDir(InputStream in, String ftpPath, String newName)
			throws IOException {
		if (ftpPath == null)
			throw new NullPointerException("ftpPath");
		createPath(ftpPath, true);
		String remoteName = newName;
		if (connectNum <= 0) {
			client.storeFile(remoteName, in);
		} else {
			int retry = 0;
			while (retry <= connectNum) {
				try {
					client.storeFile(remoteName, in);
					break;
				} catch (Exception ex) {
					String msg = ex.getMessage();
					retry++;
					if (retry > connectNum) {
						msg = "ftp 上传文件失败：" + " ftpPath=" + ftpPath + " newName=" + newName;
						throw new IOException(msg);
					}
				}
			}
		}
		return;
	}

	/**
	 * 复制文件
	 * @param srcFile
	 * @param destFile
	 */
	public void copyFile(String srcFile, String destFile) {
		JetsenFtpClient ftpClient = null;
		try {
			// 设置一次读取的字节数
			this.client.setBufferSize(1 << 30);
			InputStream stream = this.client.retrieveFileStream(srcFile);
			if (stream == null)
				return;
			// 直接调用this.client.store(destFile, stream) 方法不能存储
			ftpClient = new JetsenFtpClient(this.host, this.port, this.user,
					this.pass);
			String destPath = destFile.substring(0,
					destFile.lastIndexOf("/") + 1);
			String fileName = destFile.substring(destFile.lastIndexOf("/") + 1);

			ftpClient.uploadToDir(stream, destPath, fileName);
			stream.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ftpClient.closeFtp();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void copyFile(String srcRelPath,FtpBean ftpBean, String fileName) {
		JetsenFtpClient ftpClient = null;
		try {
			// 设置一次读取的字节数
			this.client.setBufferSize(1 << 30);
			InputStream stream = this.client.retrieveFileStream(srcRelPath);

			ftpClient = new JetsenFtpClient(ftpBean.getHost(), ftpBean.getPort(),
					ftpBean.getUser(), ftpBean.getPass());
			ftpClient.uploadToDir(stream, ftpBean.getRelativePath(), fileName);

			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ftpClient.closeFtp();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	  
	public static String getRelPath(String ftpDeviceURL) {
		String s;
		int i;
		if ((i = (s = ftpDeviceURL).lastIndexOf("//")) == -1)
			return s;
		i += 2;
		int j = s.indexOf(":", i);
		j = s.indexOf("/", j);
		if (j == -1)
			return "";
		else
			return s.substring(j);
	}

	public static String getHost(String ftpDeviceURL) {
		String s;
		int i;
		if ((i = (s = ftpDeviceURL).lastIndexOf("//")) == -1)
			return s;
		i += 2;
		int j;
		if ((j = s.indexOf(":", i)) == -1)
			j = s.indexOf("/", i);
		if (j != -1)
			return s.substring(i, j);
		else
			return s.substring(i);
	}

	public static int getFtpPort(String ftpDeviceURL) {
		int i = 21;
		int j;
		if ((j = ftpDeviceURL.lastIndexOf(":")) == -1)
			return 21;
		String s;
		String s1 = (s = ftpDeviceURL.substring(j + 1)).substring(0,
				s.indexOf("/"));
		try {
			i = Integer.parseInt(s1);
		} catch (Exception _ex) {
		}
		return i;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	private class Path {
		private String workingDirectory;// 已存在的工作目录
		private String createdPath;// 在已存在的工作目录下创建的路径

		public String getCreatedPath() {
			return createdPath;
		}

		public void setCreatedPath(String createdPath) {
			this.createdPath = createdPath;
		}

		public String getWorkingDirectory() {
			return workingDirectory;
		}

		public void setWorkingDirectory(String workingDirectory) {
			this.workingDirectory = workingDirectory;
		}

		public String toString() {
			return this.workingDirectory + ":" + this.createdPath;
		}
	}
}
