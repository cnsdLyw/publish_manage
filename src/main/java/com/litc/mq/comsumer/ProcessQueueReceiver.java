package com.litc.mq.comsumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 加工任务队列消息监听器 接收数据
 * @author liyw
 *
 */
@Component("processQueueReceiver")
public class ProcessQueueReceiver implements MessageListener {

	/*@Autowired
	MessageTXTService msgTXTService;

	@Autowired
	MessageService messageService;*/

	@Override
	public void onMessage(Message message) {
		try {
			// 消息处理
			handleMessage(((TextMessage) message).getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 接收消息队列消息ActiveMQ.xml的<jms:listener-container>
	 * 
	 * @param message
	 */
	public void handleMessage(String message) {
		// 解析
	    //messageService.analysisProcessMsg(message);
		//1 接收的数据先进行图书入库
		//2 入库的图书
	}
}
