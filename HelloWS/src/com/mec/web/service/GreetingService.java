package com.mec.web.service;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService(targetNamespace="http://www.mec.com/", serviceName= GreetingService.SERVICE_NAME)
@SOAPBinding(style=Style.RPC)
@Singleton
@Startup
public class GreetingService {

	public static final String SERVICE_NAME = "greeting";
	@WebMethod
	@WebResult(name="greeting")
	public String greeting(@WebParam(name="name") String name){
		return String.format("Hello %s", name);
	}
}
