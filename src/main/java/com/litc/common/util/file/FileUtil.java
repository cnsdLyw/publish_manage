package com.litc.common.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 文件帮助类
 * 
 * @author lu.j
 * 
 */
public class FileUtil {
	private static int counter = 100;// 为了文件名不重复，故加了计数器

	// 计数器的大小在100~999的三位数字

	// 在生成新的文件名时才使用此方法

	private static int getCounter() {
		if (counter > 999) {
			counter = 100;
		}
		return counter++;
	}

	/**
	 * 把文件内容转换为字节
	 * 
	 * @param fileName
	 * @return
	 */
	public static byte[] convertFile2Byte(String fileName) {
		InputStream in = null;
		try {
			File file = new File(fileName);

			if (!file.exists()) {
				return null;
			}
			in = new FileInputStream(file);
			byte[] bytes = IOUtils.toByteArray(in);
			return bytes;
		} catch (IOException e) {
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {

				}
			}

		}
	}

	/**
	 * 把流转换为字节
	 * 
	 * @param in
	 * @return
	 */
	public static byte[] convertFile2Byte(InputStream in) {

		if (in == null) {
			return null;
		}
		try {
			byte[] bytes = IOUtils.toByteArray(in);
			return bytes;
		} catch (IOException e) {
			return null;
		}

	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean existFile(String fileName) {
		File file = new File(fileName);
		return (file.exists() && file.isFile());
	}

	/**
	 * 创建路径
	 * 
	 * @param filePath
	 */
	public static void creatPath(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			if (file.isFile()) {
				file.getParentFile().mkdirs();
			} else {
				file.mkdirs();
			}
		}

	}

	public static void exactCreatePath(String filePath) {
		if (filePath != null) {
			filePath = filePath.replace("/", "\\").trim();
			int index = filePath.lastIndexOf(".");
			if ((index != -1) && ((index + 1) != filePath.length())) {
				index = filePath.lastIndexOf("\\");
				if ((index != -1) && ((index + 1) != filePath.length())) {
					creatPath(filePath.substring(0, index));
				}
			} else {
				creatPath(filePath);
			}
		}
	}

	/**
	 * 根据当前时间生成路径，格式为 fileType/yyyyMM/dd/HH/
	 * 
	 * @return
	 */
	public static String getFilePath(String fileType) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM/dd/HH/");
		String filePath = dateFormat.format(java.util.Calendar.getInstance()
				.getTime());
		return "/" + fileType + "/" + filePath;
	}

	/**
	 * 获取文件的路径
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getPath(String filePath) {
		if (filePath == null)
			return null;
		filePath = filePath.replace("/", "\\");
		int endIndex = filePath.lastIndexOf("\\");
		int startIndex = filePath.indexOf(",");
		if (startIndex > endIndex) {
			startIndex = -1;
		}
		if (endIndex == -1) {
			return null;
		}
		if (endIndex + 1 == filePath.length()) {
			return null;
		}

		return filePath.substring(startIndex + 1, endIndex + 1);
	}

	/**
	 * 获取文件的路径,不考虑逗号
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getPathNoComma(String filePath) {
		if (filePath == null)
			return null;
		filePath = filePath.replace("/", "\\");
		int endIndex = filePath.lastIndexOf("\\");
		if (endIndex == -1) {
			return null;
		}
		if (endIndex + 1 == filePath.length()) {
			return null;
		}

		return filePath.substring(0, endIndex + 1);
	}

	/**
	 * 取文件的名字（包括扩展名）
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		if (filePath == null)
			return null;
		filePath = filePath.replace("/", "\\");
		int index = filePath.lastIndexOf("\\");
		if (index == -1) {
			return filePath;
		}
		if (index + 1 == filePath.length()) {
			return null;
		}

		return filePath.substring(index + 1);
	}

	/**
	 * 取文件的名字（不包括路径但包括后缀）
	 */
	public static String getName(String filePath) {
		if (filePath == null)
			return null;
		filePath = filePath.trim();
		filePath = filePath.replace("/", "\\");
		int index = filePath.lastIndexOf("\\");
		if (index == -1) {
			return null;
		}
		return filePath.substring(index + 1);
	}

	/**
	 * 取文件的名字（不包括扩展名）
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileNameUnExt(String fileName) {
		if (fileName == null) {
			return null;
		}
		fileName = fileName.trim();
		int index = fileName.lastIndexOf(".");
		if (index == -1) {
			return null;
		}
		if (index + 1 == fileName.length()) {
			return null;
		}

		return fileName.substring(0, index);
	}

	/**
	 * 取文件的名字（不包括扩展名）
	 * 
	 * @param filePath
	 *            路径名
	 * @return
	 */
	public static String getFileNameUnExt2(String filePath) {
		if (filePath == null) {
			return null;
		}
		filePath = filePath.trim();
		String fileName = FileUtil.getFileName(filePath);
		int index = fileName.lastIndexOf(".");
		if (index == -1) {
			return fileName;
		}
		if (index + 1 == fileName.length()) {
			return fileName;
		}

		return fileName.substring(0, index);
	}

	/**
	 * 仅取文件名字（不包括路径 和 后缀）
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getOnlyFileName(String filePath) {

		if (filePath == null) {
			return null;
		}
		filePath = filePath.trim();
		filePath = filePath.replace("/", "\\");
		int startIndex = filePath.lastIndexOf("\\");
		if (startIndex == -1) {
			return null;
		}
		int endIndex = filePath.lastIndexOf(".");
		if (endIndex == -1) {
			return null;
		}
		if (endIndex + 1 == filePath.length()) {
			return null;
		}
		return filePath.substring(startIndex + 1, endIndex);
	}

	/**
	 * 取图片的扩展名
	 * 
	 * @param fileName
	 *            文件名
	 * 
	 * @return
	 */
	public static String getFileExt(String fileName) {
		if (fileName == null) {
			return null;
		}
		fileName = fileName.trim();
		int index = fileName.lastIndexOf(".");
		if (index == -1) {
			return null;
		}
		if (index + 1 == fileName.length()) {
			return null;
		}

		return fileName.substring(index + 1);
	}

	/**
	 * 生成一个新的文件名
	 * 
	 * @return
	 */
	public static String getFileName() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("mmssSSS");
		String subfix = dateFormat.format(java.util.Calendar.getInstance()
				.getTime());
		String prefix = getRandomCharaters(10);
		String counter = Integer.toString(getCounter());

		return counter + prefix + subfix;
	}

	/**
	 * 取length个随机字符
	 * 
	 * @param length
	 *            要取的字符长度
	 * 
	 * @return 随机字符串
	 */
	public static String getRandomCharaters(int length) {
		if (length < 1) {
			return null;
		}
		String[] strChars = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7",
				"8", "9", };
		StringBuffer str = new StringBuffer();
		int nRand = (int) java.lang.Math.round(java.lang.Math.random() * 100);
		for (int i = 0; i < length; i++) {
			nRand = (int) java.lang.Math.round(java.lang.Math.random() * 100);
			str.append(strChars[nRand % (strChars.length - 1)]);
		}
		return str.toString();
	}

	/**
	 * 
	 * 
	 * @param tempFolderPath
	 * @return
	 */
	public static File getXmlFile(String tempFolderPath) {
		File folder = new File(tempFolderPath);

		File[] files = folder.listFiles();
		for (int k = 0; k < files.length; k++) {
			String fileName = files[k].getName();
			String ext = getFileExt(fileName);

			if (ext != null && ext.equalsIgnoreCase("xml")) {
				return files[k];
			}
		}
		return null;
	}

	/**
	 * 取tempFolderPath目录下的XML文件
	 * 
	 * @param tempFolderPath
	 * @return
	 */
	public static File getConfigFile(String tempFolderPath, String searchedName) {
		File folder = new File(tempFolderPath);

		File[] files = folder.listFiles();
		for (int k = 0; k < files.length; k++) {
			File tempFile = null;
			if (files[k].isFile()) {
				String fileName = files[k].getName();
				if (fileName.equals(searchedName))
					tempFile = files[k];
			} else
				tempFile = getConfigFile(files[k].getPath(), searchedName);
			if (tempFile != null)
				return tempFile;
		}
		return null;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folder
	 */
	public static void deleteFolder(File folder) {
		if (folder.exists()) {
			if (folder.isFile()) {
				folder.delete();
			} else {
				File files[] = folder.listFiles();

				if (files != null && files.length > 0) {
					for (int k = 0; k < files.length; k++) {
						deleteFolder(files[k]);
					}
				}
				folder.delete();
			}
		}

	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 */
	public static void deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		file = null;
	}

	/**
	 * 复制文件
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, File targetFile)
			throws IOException {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();

		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
	}
	
	/**
	 * 剪切文件
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static void cutFile(File sourceFile, File targetFile)
			throws IOException {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();

		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
		
		if(targetFile.exists()){
			sourceFile.deleteOnExit();
		}
	}

	/**
	 * 复制文件夹
	 * 
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 */
	public static void copyDirectiory(String sourceDir, String targetDir)
			throws IOException {
		// 新建目标目录
		(new File(targetDir)).mkdirs();
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(
						new File(targetDir).getAbsolutePath() + File.separator
								+ file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + "/" + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}

	public static String validFilePath(String fileName) {
		// <>/\|:"*?
		if (fileName == null || fileName.equals("")) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		int length = fileName.length();

		for (int k = 0; k < length; k++) {
			char c = fileName.charAt(k);
			switch (c) {
			case '<':
				break;
			case '>':
				break;
			case '/':
				break;
			case '\\':
				break;
			case '|':
				break;
			case ':':
				break;
			case '\"':
				break;
			case '*':
				break;
			case '?':
				break;
			default:
				sb.append(c);
			}
		}

		String f = sb.toString();
		if (f.equals("")) {
			f = "fileName";
		}
		return f;
	}

	/**
	 * 根据字符串获取文件大小，返回以K为单位
	 * 
	 * @param strSize
	 * @return
	 */
	public static long transFileSize(String strSize) {
		long fileSize = 0;
		try {
			fileSize = Long.parseLong(strSize);
		} catch (Exception e) {

		}
		fileSize = fileSize < 1024 ? 1 : (fileSize / 1024);
		return fileSize;
	}

	/**
	 * 根据字符串获取文件大小，返回以K为单位
	 * 
	 * @param strSize
	 * @return
	 */
	public static long transFileSize(long size) {
		return size < 1024 ? 1 : (size / 1024);
	}

	/**
	 * 对文件名以UTF-8进行编码
	 * 
	 * @param s
	 * @return
	 */
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
					System.out.println(ex);
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

	/**
	 * 将文件大小标准化
	 * 
	 * @param totalSize
	 * @return
	 */
	public static String getStandardSize(long totalSize) {
		String[] unitArr = { " B", " KB", " MB", " GB", " TB" };
		float size = totalSize;
		int i = 0;
		while (size / 1024 > 1 && i < unitArr.length) {
			size = size / 1024;
			i++;
		}
		String sizeStr = new DecimalFormat("0.00").format(size);
		return sizeStr + unitArr[i];
	}

	/**
	 * 将文件大小转为B，getStandardSize的逆操作
	 * 
	 * @param totalSize
	 * @return
	 */
	public static long getLongSize(String standardSize) {
		String[] unitArr = { " B", " KB", " MB", " GB", " TB" };
		String sizeStr = standardSize.split(" ")[0];
		long size = Long.valueOf(sizeStr);
		int i = 0;
		if (standardSize.endsWith(" B") || standardSize.endsWith(" KB")
				|| standardSize.endsWith(" MB") || standardSize.endsWith(" GB")
				|| standardSize.endsWith(" TB")) {
			while (!standardSize.endsWith(unitArr[i])) {
				size = size * 1024;
				i++;
			}
		}
		return size;
	}

	/**
	 * 生成文件
	 * 
	 * @param path
	 *            路径
	 * @param content
	 *            内容
	 * @throws Exception
	 */
	public static void creatFile(String path, String content) throws Exception {
		File file = new File(path);

		file.createNewFile();

		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), "UTF-8"));

		output.write(content);

		output.close();
	}

	/**
	 * 生成文件夹
	 * 
	 * 
	 * @param path
	 *            路径
	 */
	public static void creatFolder(String path) {
		File dir = new File(path);
		dir.mkdirs();
	}

	/**
	 * 复制文件夹里的全部内容
	 * 
	 * @param oldPath
	 *            原路径
	 * 
	 * @param newPath
	 *            新路径
	 */
	public static void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹

			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; file != null && i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹

					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 删除临时文件
	 * 
	 * @param folder
	 */
	public static void deleteExportFiles(File folder) {
		if (folder.isFile()) {
			folder.delete();
		} else {
			File files[] = folder.listFiles();
			for (int k = 0; k < files.length; k++) {
				deleteExportFiles(files[k]);
			}
			folder.delete();
		}
	}
	/**
	 * 删除临时文件,不包括文件夹
	 * 
	 * @param folder
	 */
	public static void deleteFiles(File folder) {
		if (folder.isFile()) {
			folder.delete();
		} else {
			File files[] = folder.listFiles();
			for (int k = 0; k < files.length; k++) {
				deleteExportFiles(files[k]);
			}
		}
	}

	public static String ensureSeparatorChar(String path) {
		char lastChar = path.charAt(path.length() - 1);
		if (lastChar != '\\' && lastChar != '/') {
			path = path + "/";
		}
		return path;
	}

	public static String read(InputStream is, String encoding) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, encoding), 16 * 1024); // 强制缓存大小为16KB，一般Java类默认为8KB

			String line = null;

			while ((line = reader.readLine()) != null) { // 处理换行符

				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}
	
	public static String renameFile(File file){
		String alias = StringUtils.join(new String[] {
				UUID.randomUUID().toString() + "", ".",
				FilenameUtils.getExtension(file.getName()) });
		return alias;
	}
	
	public static void main(String[] args) {
		String size = "22 MB";
		System.out.println(getLongSize(size));
	}

}
