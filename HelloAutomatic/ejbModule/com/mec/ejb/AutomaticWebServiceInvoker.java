package com.mec.ejb;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.mec.ejb.inter.Greetings;
import com.mec.ejb.inter.Logger;

@Singleton
@Startup
public class AutomaticWebServiceInvoker {
	
	@Resource
	private HelloTimerService timer;
	
	@Inject
	private Logger logger;
	
	
	private static final String targetNamespace = "http://www.mec.com/";
	private static final String serviceName = "greeting";
	private static final String portName = "GreetingService";
	private static final String wsdlUrl = String.format("http://localhost:8080/HelloWS/%s/%s?wsdl", serviceName, portName);
	private Greetings greeting;
	
	@PostConstruct
	public void init(){
		try {
			QName qname = new QName(targetNamespace, serviceName);
			URL serviceUrl = new URL(wsdlUrl);
			Service greetingService = Service.create(serviceUrl, qname);
			greeting = greetingService.getPort(Greetings.class);
		} catch (MalformedURLException e) {
			logger.error("URL error", e);
		}
	}

	@Schedule(dayOfMonth="*", hour="*", minute="*", second="*/10", persistent=false)
	public void greetingsWS(){
		logger.info(greeting.greeting());
		logger.info(greeting.greeting("Current time is " + Calendar.getInstance().getTime()));
	}
}
