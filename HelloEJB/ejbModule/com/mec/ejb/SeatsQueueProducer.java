package com.mec.ejb;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.CompletionListener;
import javax.jms.Queue;

import com.mec.ejb.config.MessageQueueDefinition;
import com.mec.ejb.inter.Logger;

@ApplicationScoped
@Default
@Named("SeatsQueueProducer")
public class SeatsQueueProducer extends MessageQueueProducer{
	
	@Inject
	private Logger logger;
	
	@Inject
	private CompletionListener asyncListener;
	
//	@Inject
//	private JMSContext context;
	@Resource(mappedName=MessageQueueDefinition.SEATS_QUEUE)
	private Queue syncQueue;
	
	
	
	@Override
	public void sendMessage(String txt) {
//		super.sendMessage(txt);
		context.createProducer().setProperty(MSG_PROP_PRIORITY, Priority.LOW.toString()).send(getQueue(), txt);
	}

	@Override
	protected Queue getQueue() {
		return syncQueue;
	}

	@Override
	protected CompletionListener getCompletionListener() {
		return asyncListener;
	}

	
	
	
}
