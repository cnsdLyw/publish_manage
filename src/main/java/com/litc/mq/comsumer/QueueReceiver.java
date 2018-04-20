package com.litc.mq.comsumer;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author yangyong
 * @description  队列消息监听器
 * 
 */
@Component
public class QueueReceiver implements MessageListener {
	
	/*@Autowired
	MessageTXTService  msgTXTService;

	@Autowired
	MessageService  messageService;
	*/
	@Override
	public void onMessage(Message message) {
		try {
			//消息处理
			handleMessage(((TextMessage)message).getText());
			//System.out.println("QueueReceiver接收到消息:"+((TextMessage)message).getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 接收消息队列消息ActiveMQ.xml的<jms:listener-container>
	 *  @param message
	 */
	public void handleMessage(String message)
	{
		
		//解析
		//messageService.analysisMsg(message);
		
	}
}

