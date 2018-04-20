package com.litc.common.util.thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.litc.model.WorkStatus;

/**
 * @author sungao 注：sql方式容易连接断开-正在排除
 * @since 2016-1-5
 * 
 */

public class WorkTaskManager {
	// public static BlockingQueue<WorkTask> workqueue = new
	// LinkedBlockingQueue<WorkTask>(
	// Constant.QUEUECAPACITY);// 缓冲队列

	public static final String TASKSQL = "select * from work_status where curStatus = 1 order by priority desc,updateTime asc limit 5";

	public synchronized static void addTask(WorkTask opertask) {
		if (opertask != null) {
			/*
			 * try { workqueue.put(opertask); } catch (InterruptedException e) {
			 * e.printStackTrace(); }
			 */
			WorkStatus ws = new WorkStatus();
			try {
				ws.setWorkId(opertask.getTaskFlag());
				ws.setObj(WorkObjOperate.serializeObject(opertask));
				ws.setPriority(WorkObjOperate.MIDDLE);
				ws.setCurStatus(WorkObjOperate.NOTSTART);
				ws.setType(opertask.getTaskType());
				ws.setDescription(opertask.getTaskDesc());
				WorkObjOperate.persist(ws);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public synchronized static WorkStatus getTask() throws InterruptedException {
		// while (!workqueue.isEmpty()) {
		// return (WorkTask) workqueue.poll();
		/*Query query = WorkObjOperate.getEntityManager().createNativeQuery(TASKSQL);
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		List<WorkStatus> resultList = new ArrayList<WorkStatus>();
		for (Object[] objects : list) {
			WorkStatus ws = new WorkStatus();
			ws.setWorkId(objects[0].toString());
			ws.setObj((byte[]) objects[1]);
			ws.setPriority(Integer.parseInt(objects[2].toString()));
			// ws.setUpdateTime(objects[3]);
			resultList.add(ws);
		}

		if (resultList.size() > 0) {
			return resultList.get(0);
		}*/

		// }
		return null;
	}
}