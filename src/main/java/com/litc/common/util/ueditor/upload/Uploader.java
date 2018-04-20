package com.litc.common.util.ueditor.upload;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.litc.common.util.ueditor.define.State;
/**
 *  Function:上传文件控制类  
 *  @author  zhongying(281264212@qq.com)
 *  @date    2015-1-21 上午10:06:16    
 *  @version 1.0
 */
public class Uploader {
	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;

	public Uploader(HttpServletRequest request, Map<String, Object> conf) {
		this.request = request;
		this.conf = conf;
	}

	public final State doExec() {
		String filedName = (String) this.conf.get("fieldName");
		State state = null;

		if ("true".equals(this.conf.get("isBase64"))) {
			state = Base64Uploader.save(this.request.getParameter(filedName),
					this.conf);
		} else {
			state = BinaryUploader.save(this.request, this.conf);
		}

		return state;
	}
}
