package com.mec.ejb;

import javax.inject.Inject;
import javax.jms.CompletionListener;
import javax.jms.JMSContext;
import javax.jms.Queue;

import com.mec.ejb.inter.MessageDispatcher;

public abstract class MessageQueueProducer implements MessageDispatcher {

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
	private JMSContext context;
	
	protected abstract Queue getQueue();
	protected abstract CompletionListener getCompletionListener();
}
