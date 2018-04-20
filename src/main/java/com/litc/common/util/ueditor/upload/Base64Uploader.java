package com.litc.common.util.ueditor.upload;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.litc.common.util.ueditor.PathFormat;
import com.litc.common.util.ueditor.define.AppInfo;
import com.litc.common.util.ueditor.define.BaseState;
import com.litc.common.util.ueditor.define.FileType;
import com.litc.common.util.ueditor.define.State;

public final class Base64Uploader {

	public static State save(String content, Map<String, Object> conf) {
		
		byte[] data = decode(content);

		long maxSize = ((Long) conf.get("maxSize")).longValue();

		if (!validSize(data, maxSize)) {
			return new BaseState(false, AppInfo.MAX_SIZE);
		}

		String suffix = FileType.getSuffix("JPG");

		String savePath = PathFormat.parse((String) conf.get("savePath"),
				(String) conf.get("filename"));
		
		savePath = savePath + suffix;
		String physicalPath = (String) conf.get("rootPath") + savePath;
		System.out.println("文件保存地址Base64Uploader--physicalPath=="+physicalPath);
		State storageState = StorageManager.saveBinaryFile(data, physicalPath);

		if (storageState.isSuccess()) {
			storageState.putInfo("url", PathFormat.format(savePath));
			storageState.putInfo("type", suffix);
			storageState.putInfo("original", "");
		}

		return storageState;
	}

	private static byte[] decode(String content) {
		return Base64.decodeBase64(content);
	}

	private static boolean validSize(byte[] data, long length) {
		return data.length <= length;
	}
	
}