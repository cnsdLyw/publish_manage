package com.test.demo;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.web.RemoteJMXBrokerFacade;
import org.apache.activemq.web.config.SystemPropertiesConfiguration;

public class JmxTest {

	public static void main(String[] args) throws Exception {
		/*1  .RemoteJMXBrokerFacade createConnector = new RemoteJMXBrokerFacade(); 
		//填写链接属性  
		System.setProperty("webconsole.jmx.url","service:jmx:rmi:///jndi/rmi://198.9.4.167:11099/jmxrmi");  
		System.setProperty("webconsole.jmx.user", "admin");  
		System.setProperty("webconsole.jmx.password", "activemq");  
		//创建配置  
		SystemPropertiesConfiguration configuration = new SystemPropertiesConfiguration();  
		//创建链接  
		createConnector.setConfiguration(configuration);  
		BrokerViewMBean brokerAdmin = createConnector.getBrokerAdmin();
		System.out.println(brokerAdmin);*/
		
		
		 ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://198.9.4.167:61616");
		    Connection connection = factory.createConnection();
		    connection.start();
		 
		    final Session session = connection.createSession(true/*支持事务*/, Session.AUTO_ACKNOWLEDGE);
		    Destination queue = session.createQueue("test_345");
		    MessageConsumer consumer = session.createConsumer(queue);
		    //consumer.setMessageListener(new MessageListener() {
		 
		      /* @Override
		       public void onMessage(Message message) {
		           try {
		              if (message instanceof TextMessage) {
		                  String text = ((TextMessage) message).getText();
		                  session.commit();//如果开启事务，这儿就需要提交，才会消费掉这条消息
		                  System.out.println(text);
		              }
		           }
		           catch (Exception e) {
		              e.printStackTrace();
		           }
		       }*/
		    //});
	}

}
