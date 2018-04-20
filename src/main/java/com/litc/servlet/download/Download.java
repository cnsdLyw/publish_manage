package com.litc.servlet.download;
/**
 *  Function:  下载附件接口
 *  @author  zhongying(281264212@qq.com)
 *  @date    2015-11-14 下午6:16:17    
 *  @version 1.0
 */
public interface Download {

	/**
	 * 是否有效
	 * 
	 * @return true,允许下载;false,不允许下载;
	 */
	Boolean isVaild();

	/**
	 * 获取文件路径
	 * 
	 * @return 文件全路径
	 */
	String getPath();

	/**
	 * 获取文件名
	 * 
	 * @return 文件名
	 */
	String getFileName();

}
