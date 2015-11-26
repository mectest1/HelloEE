package com.mec.ejb;

import javax.inject.Inject;
import javax.jms.CompletionListener;
import javax.jms.JMSContext;
import javax.jms.Queue;

import com.mec.ejb.inter.MessageDispatcher;

public abstract class MessageQueueProducer implements MessageDispatcher {

	public static final String MSG_PROP_PRIORITY = "priority";
	public static enum Priority{
		 LOW
		,HIGH
		;
	}
	
	@Override
	public void sendMessage(String txt){
		Queue syncQueue = getQueue();
		context.createProducer().send(syncQueue, txt);
	}
	
	@Override
	public void sendAsyncMessage(String message) {
		Queue asyncQueue = getQueue();
		context.createProducer().setAsync(getCompletionListener()).send(asyncQueue, message);
	}


	@Inject
//	@Resource	
	//error message:Error injecting resource into CDI managed bean. Can't find a resource named java:comp/env/com.mec.ejb.MessageQueueProducer/context
	protected JMSContext context;
	
	protected abstract Queue getQueue();
	protected abstract CompletionListener getCompletionListener();
}
