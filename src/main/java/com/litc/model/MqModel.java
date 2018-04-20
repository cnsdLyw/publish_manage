package com.litc.model;

/**
 * 这里自定义一个Model来接收队列的信息
 * @author LY
 * @date 2016-08-10 11:11
 */
public class MqModel {
	private String name;//队列的名称
	private Long queueSize;//队列中剩余的消息数
	private Long consumerCount;//消费者数
	private Long enqueueCount;//进入队列的总数量
	private Long dequeueCount;//出队列的数量

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(Long queueSize) {
		this.queueSize = queueSize;
	}

	public Long getConsumerCount() {
		return consumerCount;
	}

	public void setConsumerCount(Long consumerCount) {
		this.consumerCount = consumerCount;
	}

	public Long getDequeueCount() {
		return dequeueCount;
	}

	public void setDequeueCount(Long dequeueCount) {
		this.dequeueCount = dequeueCount;
	}

	public Long getEnqueueCount() {
		return enqueueCount;
	}

	public void setEnqueueCount(Long enqueueCount) {
		this.enqueueCount = enqueueCount;
	}
	

	
	

}
