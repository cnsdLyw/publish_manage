package com.litc.common.util.thread;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author sungao
 * @since 2015-12-28
 */
public class ThreadPoolInitBean implements
		ApplicationListener<ContextRefreshedEvent> {

	private int corePoolSize;

	private int maxPoolSize;

	private int queueCapacity;

	private int keepAliveSeconds;

	private WorkTaskMonitorThread monitorThread;// 任务监测线程

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public void setQueueCapacity(int queueCapacity) {
		this.queueCapacity = queueCapacity;
	}

	public void setKeepAliveSeconds(int keepAliveSeconds) {
		this.keepAliveSeconds = keepAliveSeconds;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (event.getApplicationContext().getParent() == null) {// 防止两次初始化

			ThreadPoolFactory.init(corePoolSize, maxPoolSize, queueCapacity,
					keepAliveSeconds);
			
			monitorThread = new WorkTaskMonitorThread();  
			monitorThread.start();

			//SolrClientHandler.creatSolrClient(ResourceBundle.getBundle("solr"));//应该写在其它地方
		}

		
		
	}
}
