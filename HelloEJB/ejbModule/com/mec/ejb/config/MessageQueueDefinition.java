package com.mec.ejb.config;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;

import com.mec.ejb.inter.Logger;

@JMSDestinationDefinitions({
	@JMSDestinationDefinition(name=MessageQueueDefinition.BOOKING_QUEUE, interfaceName="javax.jms.Queue", destinationName=MessageQueueDefinition.BOOKING_QUEUE_NAME),
	@JMSDestinationDefinition(name=MessageQueueDefinition.SEATS_QUEUE, interfaceName="javax.jms.Queue", destinationName=MessageQueueDefinition.SEATS_QUEUE_NAME)
	
})
@Singleton
@Startup
public class MessageQueueDefinition {
	private static final String QUEUE_PREFIX = "java:global/jms/";
	/**
	 * Note: 
	 * 1, if the Queue_prefix is "global/jms/", aka without scheme, then the messaging object will be bound to java:com/env/global/jms/;
	 * 2, The same rule applies for mapping name that does not start with "java:", e.g.:
	 * 		derp:global/jms -> java:comp/env/derp:global/jms/seatsQueue
	 * 		ldap:global/jms/ -> java:comp/env/ldap:global/jms/seatsQueue
	 * 3, Only mapping name that starts with "java:" will be bound as it is specified;
	 * 		java:global/jms/ -> java:global/jms
	 * 		java://derp	-> empty entry name is not allowed for java
	 * 		java:/>/./global/jms/ -> java:/>/./global/jms/
	 */
	public static final String BOOKING_QUEUE_NAME = "bookingQueue";
	public static final String BOOKING_QUEUE = QUEUE_PREFIX + BOOKING_QUEUE_NAME;
	public static final String SEATS_QUEUE_NAME = "seatsQueue";
	public static final String SEATS_QUEUE = QUEUE_PREFIX + SEATS_QUEUE_NAME;
	
	@PostConstruct
	public void init(){
		logger.info("Message Queue Definition initialized.");
	}
	
	@Inject
	private Logger logger;
}
