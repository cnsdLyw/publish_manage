package com.litc.common.util.file;

import com.litc.system.model.StorageDevice;

public class StorageDeviceHelper {

	public StorageDeviceHelper() {
		super();
	}
	/**
	 * 从FTP设备路径和文件名（带相对路径）中，获得完整的路径
	 * @param device 存储设备，其路径可能为ftp://172.16.9.235:21/PhotoDev/2007-11-10/
	 * @param rltPath 文件的相对路径和文件名
	 * @return
	 */
	public static String getWholeFtpPath(StorageDevice device, String rltPath) {
		return getWholeFtpPath(device.getFtpUrl(), rltPath);
	}

	private static String getWholeFtpPath(String deviceURL, String rltPath) {
		int index = deviceURL.lastIndexOf("://");
		index++;
		if (index > 0)
			index += 3;// 去掉前缀ftp://

		int orgIndex = index;
		index = deviceURL.indexOf(":", orgIndex);// 找端口
		if (index == -1) {// 没写端口，则找到下一个/
			index = deviceURL.indexOf("/", orgIndex);
		} else { // 写了端口
			index = deviceURL.indexOf("/", index);
		}

		String re = "";
		if (index >= 0) {
			re = deviceURL.substring(index + 1);
		}

		rltPath = rltPath.replaceAll("\\\\", "/");
		String rltPath1 = null;
		int lastpos = rltPath.lastIndexOf("/");
		if (lastpos > 0) {
			if (rltPath.startsWith("/"))
				rltPath1 = rltPath.substring(1, lastpos);
			else
				rltPath1 = rltPath.substring(0, lastpos);
		}
		if (rltPath1 != null) {
			if (re.length() > 0 && re.charAt(re.length() - 1) != '/')
				re += "/" + rltPath1;
			else
				re += rltPath1;
		}
		return re;
	}

	public static int getFtpPort(StorageDevice device) {
		int nPort = 21;
		int index = device.getFtpUrl().lastIndexOf(":");
		if (index == -1) {
			return nPort;
		}
		String re = device.getFtpUrl().substring(index + 1);
		String port = re.substring(0, re.indexOf("/"));
		try {
			nPort = Integer.parseInt(port);
		} catch (Exception ex) {

		}
		return nPort;
	}

	static public String getFileName(String rltPath) {
		rltPath = rltPath.replaceAll("\\\\", "/");

		String fileName;
		if (rltPath.lastIndexOf("/") >= 0)
			fileName = rltPath.substring(rltPath.lastIndexOf("/") + 1);
		else
			fileName = rltPath;

		return fileName;
	}

	public static String getHostName(StorageDevice device) {
		String dUrl = device.getFtpUrl();
		int begin = dUrl.lastIndexOf("//");
		if (begin == -1) {
			return dUrl;
		}
		begin += 2;
		int end = dUrl.indexOf(":", begin);
		if (end == -1) {
			end = dUrl.indexOf("/", begin);
		}
		if (end != -1) {
			return dUrl.substring(begin, end);
		} else {
			return dUrl.substring(begin);
		}
	}

	public static void main(String[] args) {
		String deviceURL = "ftp://172.16.9.235:21/PhotoDev/Daily/";
		String rltPath = "\\2007-11\\10\\Ad323.jpg";

		String path = getWholeFtpPath(deviceURL, rltPath);
		System.out.println(path);
	}
}
