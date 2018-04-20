package com.litc.common.util.thread;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * @author sungao
 * @since 2016-1-5
 *
 */
public class WorkerCallable implements Callable<String>, Serializable {

	private static final long serialVersionUID = -6089524891635732222L;

	public boolean isrunning = false;
	private WorkTask nowTask; // 当前任务
	private Object threadTag;// 线程标识

	public static WorkerCallable getInstance() {
		return new WorkerCallable();
	}

	// 获取线程标识key
	public Object getThreadTag() {
		return threadTag;
	}

	public synchronized void setWorkTask(WorkTask task) {
		this.nowTask = task;
	}

	public synchronized void setIsRunning(boolean flag) {
		this.isrunning = flag;
		// if (flag) {
		// this.notify();
		// }
	}

	public boolean getIsrunning() {
		return isrunning;
	}

	@Override
	public String call() throws Exception {

		/*
		 * while (true) { if (!isrunning) { try { this.wait(); } catch
		 * (InterruptedException e) { e.printStackTrace(); } } else {
		 * nowTask.runTask(); setIsRunning(false); } }
		 */

		while (isrunning) {

			nowTask.runTask();
			setIsRunning(false);

		}
		return nowTask.getTaskFlag();
	}

}
