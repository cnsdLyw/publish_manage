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
 * @description  Topic消息监听器
 * 
 */
@Component
public class TopicReceiver implements MessageListener{


	/*@Autowired
	MessageTXTService  msgTXTService;
	
	@Autowired
	MessageService  messageService;*/
	
	@Override
	public void onMessage(Message message) {
		try {
			
			//消息处理
			handleMessage(((TextMessage)message).getText());
			//System.out.println("TopicReceiver接收到消息:"+((TextMessage)message).getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void handleMessage(String message)
	{
		//messageService.analysisMsg(message);
		//msgTXTService.parseMessageTxt(message);
		// 更新数目信息
	}
	
}

