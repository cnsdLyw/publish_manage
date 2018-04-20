package com.litc.common.util.thread;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.litc.model.WorkStatus;

/**
 * @author sungao
 * @since 2016-1-5
 *
 */
public class WorkTaskMonitorThread extends Thread {

	private final static Logger log = LoggerFactory
			.getLogger(WorkTaskMonitorThread.class);

	public WorkTaskMonitorThread() {
		log.info("创建任务监测线程...");
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (WorkTaskManager.getTask() != null) {
					WorkStatus status = (WorkStatus) WorkTaskManager.getTask();
					WorkTask task = WorkObjOperate.deserializeObject(status.getObj());
					WorkerCallable work = WorkerCallable.getInstance();
					work.setWorkTask(task);
					work.setIsRunning(true);
					
					WorkObjOperate.updateStatus(WorkObjOperate.RUNNING,task.getTaskFlag());
					
					ThreadPoolFactory.executor(work);
					
				} else {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
