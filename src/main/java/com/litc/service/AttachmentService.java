package com.litc.service;

/**
 *  Function:附件操作接口
 *  @author  zhongying(281264212@qq.com)
 *  @date    2015-11-15 下午1:28:17    
 *  @version 1.0
 */
public interface AttachmentService {
	
	/**
	 * 批量删除附件信息
	 *  @param attach_ids 附件ID集合
	 */
	public void deleteAttachments(Long[] attach_ids,Integer operate_type);
}