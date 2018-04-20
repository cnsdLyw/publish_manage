package com.litc.common.util.thread;

import java.io.Serializable;
import java.util.List;

import com.litc.common.util.fts.SolrIndexOperTask;

/**
 * @author sungao
 * @since 2016-1-5
 *
 */
public class WorkerExecutor implements Runnable, Serializable {

	private static final long serialVersionUID = -6511844887263567204L;

	private String operType;

	private List<String> resIds;

	public WorkerExecutor(String operType, List<String> resIds) {
		this.operType = operType;
		this.resIds = resIds;
	}

	@Override
	public void run() {
		synchronized (resIds) {

			WorkTask operTask = new SolrIndexOperTask(operType, resIds);
			WorkTaskManager.addTask(operTask);
		}
	}

}