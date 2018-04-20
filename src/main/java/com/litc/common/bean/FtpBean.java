package com.litc.common.bean;

import com.litc.common.util.file.StorageDeviceHelper;
import com.litc.system.model.StorageDevice;

public class FtpBean 
{
	private String host;
	private int port = 21;
	private String user;
	private String pass;
	private String relativePath;
	private String deviceName;
		
	public void convert(StorageDevice device,String fileName)
	{
		user = device.getFtpUser();
		pass = device.getFtpPassword();
		host = StorageDeviceHelper.getHostName(device);
		port = StorageDeviceHelper.getFtpPort(device);
		deviceName = device.getFtpCode();
		relativePath = "/"+StorageDeviceHelper.getWholeFtpPath(device, fileName);
		relativePath = relativePath.replaceAll("//", "/");
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getRelativePath() {
		return relativePath;
	}
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
}
