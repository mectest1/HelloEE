package com.mec.ejb;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.mec.ejb.config.MessageQueueDefinition;
import com.mec.ejb.inter.Logger;

//("Usage of JMS1.1 API")
@Deprecated
public class BookingQueueProducerV11 {
	@Inject
	private Logger logger;
	
	@Resource(mappedName="java:/ConnectionFactory")
	private ConnectionFactory cf;
	
	@Resource(mappedName=MessageQueueDefinition.BOOKING_QUEUE)
	private Queue queueExample;
	
	public void sendMessage(String txt){
		try(final Connection connection = cf.createConnection();){
			
			Session session =  connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			final MessageProducer publisher = session.createProducer(queueExample);
			
			connection.start();
			
			final TextMessage message = session.createTextMessage(txt);
			publisher.send(message);
		}catch(Exception e){
			logger.error("Error !" + e.getMessage());
		}
	}

}
