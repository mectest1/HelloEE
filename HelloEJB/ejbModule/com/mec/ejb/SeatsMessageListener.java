package com.mec.ejb;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.CompletionListener;
import javax.jms.JMSException;
import javax.jms.Message;

import com.mec.ejb.inter.Logger;

@ApplicationScoped
@Default
@Named("SeatsMessageListener")
public class SeatsMessageListener implements CompletionListener {

	@Inject
	private Logger logger;

	@Override
	public void onCompletion(Message message) {
		try {
			final String txt = message.getBody(String.class);
			logger.info(String.format("Message sent successfully: %s", txt));
		} catch (JMSException e) {
			logger.error(String.format("Problem with message format. %s", e.getMessage()));
		}
	}

	@Override
	public void onException(Message message, Exception exception) {
		try{
			final String txt = message.getBody(String.class);
			logger.info(String.format("Message sent failed: %s", txt));
		}catch(JMSException e){
			logger.error(String.format("Problem with messag format. %s", e.getMessage()));
		}
	}

}
