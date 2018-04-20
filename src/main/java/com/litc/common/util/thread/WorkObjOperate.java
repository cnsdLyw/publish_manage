package com.litc.common.util.thread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.litc.model.WorkStatus;

/**
 * 对象操作类
 * 
 * 序列化发序列化需要对象实现Serializable接口
 * 
 * @author sungao
 * @since 2016-02-26
 *
 */
public class WorkObjOperate {

	/**
	 * 低(优先级)
	 */
	public static final int LOW = 1;

	/**
	 * 中(优先级)
	 */
	public static final int MIDDLE = 2;

	/**
	 * 高(优先级)
	 */
	public static final int HIGH = 3;

	/**
	 * 未开始
	 */
	public static final String NOTSTART = "1";

	/**
	 * 执行中
	 */
	public static final String RUNNING = "2";

	/**
	 * 完成
	 */
	public static final String FINISH = "3";
	
	/**
	 * 异常
	 */
	public static final String EXCEPTION = "4";

	public static EntityManager em;

	/**
	 * 对象序列化
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static byte[] serializeObject(Object object) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		ObjectOutputStream oos = new ObjectOutputStream(baos);

		oos.writeObject(object);

		oos.flush();

		return baos.toByteArray();

	}

	/**
	 * 对象反序列化
	 * 
	 * @param buf
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static WorkTask deserializeObject(byte[] buf) throws IOException,
			ClassNotFoundException {

		WorkTask object = null;

		ByteArrayInputStream bais = new ByteArrayInputStream(buf);

		ObjectInputStream ois = new ObjectInputStream(bais);

		object = (WorkTask) ois.readObject();

		return object;

	}
	
	//修改成jpa方式，
	public static EntityManager getEntityManager() {
//		if (em == null) {
//			EntityManagerFactory emf = Persistence.createEntityManagerFactory("userPU");
//			em = emf.createEntityManager();
//		}
//		return em;
		return null;
	}
	
	public static void persist(WorkStatus ws) {
//		getEntityManager().getTransaction().begin();
//		getEntityManager().persist(ws);
//		getEntityManager().flush();
//		getEntityManager().getTransaction().commit();
	}
	
	public static void updateStatus(String status,String workId) {
//		String sql = String.format("update work_status set curStatus ='%s' where workId = '%s'",status,workId);
//		getEntityManager().getTransaction().begin();
//		Query query = getEntityManager().createNativeQuery(sql);
//		query.executeUpdate();
//		getEntityManager().getTransaction().commit();
	}
}
