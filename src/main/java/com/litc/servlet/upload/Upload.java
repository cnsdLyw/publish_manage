package com.litc.servlet.upload;

import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 *  Function:  资源上传接口
 *  @author  zhongying(281264212@qq.com)
 *  @date    2015-11-17 下午4:19:02    
 *  @version 1.0
 */
public interface Upload {

	/**
	 * 获取文件根路径
	 * 
	 * @return
	 */
	String getRoot();

	/**
	 * 获取保存路径
	 * 
	 * @return 保存路径
	 */
	String getSaveDirectory();

	/**
	 * 获取最大大小
	 * 
	 * @return 最大大小
	 */
	Integer getMaxPostSize();

	/**
	 * 获取编码
	 * 
	 * @return 编码
	 */
	String getEncoding();

	/**
	 * 获取重命名规则
	 * 
	 * @return 重命名规则
	 */
	FileRenamePolicy getPolicy();

}
