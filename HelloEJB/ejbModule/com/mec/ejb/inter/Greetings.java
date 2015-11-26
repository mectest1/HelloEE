package com.mec.ejb.inter;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService	//List this annotation here, or the web service client will throw an exception concerning this annotation is required.
public interface Greetings {
	@WebMethod(operationName="greeting")
	String greeting();
	
	@WebMethod(operationName="greetingWithName")	//Without a different name, "An operation with name already exists in this service" will be thrown
	default String greeting(String name){
		return greeting();
	}
}