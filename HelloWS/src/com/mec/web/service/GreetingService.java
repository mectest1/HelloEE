package com.mec.web.service;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.mec.ejb.inter.Greetings;

@WebService(targetNamespace=WSConstants.TARGET_NAMESPACE, serviceName= GreetingService.SERVICE_NAME)
@SOAPBinding(style=Style.RPC)
@Singleton
@Startup
public class GreetingService implements Greetings{

	public static final String SERVICE_NAME = "greeting";
//	@WebMethod(operationName="greetingWithName")	//Without a different name, "An operation with name already exists in this service" will be thrown
													//Note that the operation name should be specified in the WebService interface.
	@WebResult(name="greeting")
	@Override
	public String greeting(@WebParam(name="name") String name){
		return String.format("Hello %s", name);
	}
	
	@WebMethod(operationName="greeting")
	@WebResult(name="greeting")
	@Override
	public String greeting() {
		return "Hello, Web Service";
	}
	
	
}
