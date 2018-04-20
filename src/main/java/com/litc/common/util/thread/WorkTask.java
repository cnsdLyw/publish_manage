package com.litc.common.util.thread;

import java.io.Serializable;

/**
 *  Function:实现该接口来，调用就行  
 *  @author  zhongying(281264212@qq.com)
 *  @date    2016-3-15 上午10:04:18    
 *  @version 1.0
 */
public interface WorkTask extends Serializable{

	public void runTask();// 执行工作任务

	/**
	 * 目前必须实现此方法，目的是获取当前任务标识，统一此参数,
	 * 例如可以在构造方法内生成任务标识然后通过此方法调用，可参照SolrIndexOperTask,
	 * 后期可以优化
	 * @return
	 */
	public String getTaskFlag();
	
	/**
	 * 获取任务类型
	 * @return
	 */
	public String getTaskType();
	
	
	/**
	 * 获取任务描述
	 * @return
	 */
	public String getTaskDesc();
	
	public void cancelTask();

	public int getProgress();
}
