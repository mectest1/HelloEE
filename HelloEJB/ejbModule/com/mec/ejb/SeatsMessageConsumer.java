package com.mec.ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.mec.ejb.config.MessageQueueDefinition;
import com.mec.ejb.dao.SeatsProducer;
import com.mec.ejb.inter.FromJBoss;
import com.mec.ejb.inter.Logger;

/**
 * <p>
 * Message-Driven Bean implementation class for: SeatMessageConsumer.
 * </p>
 * <div>
 * <p>
 * Standard properties: acknowledgeMode, messageSelector, destinationType, destinationLookup, 
 * connectionFactoryLookup, sbuscriptinoDurability, subscriptionName, clientId.
 * </p>
 * </div>
 * <p>
 * References:
 * </p>
 * <ul>
 * <li>[1] <a href="http://docs.oracle.com/javaee/7/api/javax/ejb/ActivationConfigProperty.html">ActivationConfigProperty API Doc</a></li>
 * </ul>
 */
@MessageDriven(
		activationConfig = { 
				 @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue")
				,@ActivationConfigProperty(propertyName="destinationLookup", propertyValue=MessageQueueDefinition.SEATS_QUEUE)
//				,@ActivationConfigProperty(propertyName="destinationName", propertyValue=MessageQueueDefinition.SEATS_QUEUE_NAME)
				,@ActivationConfigProperty(propertyName="messageSelector", propertyValue="priority='LOW'")
		})
public class SeatsMessageConsumer implements MessageListener {

	@Inject
	private Logger logger;
	
    public SeatsMessageConsumer() {
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	try{
    		final String txt = message.getBody(String.class);
    		logger.info(String.format("Message received, seat id: %s", txt));
    		seatsProducer.onMemeberListChanged(null);
    	}catch(JMSException e){
    		logger.error(String.format("Message receive failed: %s", e.getMessage()));
    	}
    }

    @Inject
    private SeatsProducer seatsProducer;
}
