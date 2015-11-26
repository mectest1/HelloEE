package com.mec.web.service;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 * 
 * <h4>output:</h4>
 * <p>
 * when the serviceName is set to "ws"
 * </p>
 * <code>
 *  address=http://localhost:8080/HelloWS/ws/CalculatePowerWebService
	 implementor=com.mec.web.service.CalculatePowerWebService
	 serviceName={http://www.mec.com/}ws
	 portName={http://www.mec.com/}CalculatePowerWebServicePort
	 annotationWsdlLocation=null
	 wsdlLocationOverride=null
	 mtomEnabled=false
	 </code>
 * @author MEC
 * 
 *
 */
@WebService(targetNamespace=WSConstants.TARGET_NAMESPACE, serviceName=CalculateWebService.SERVICE_NAME)
@SOAPBinding(style=Style.RPC)
@Singleton
@Startup
public class CalculateWebService {

	public static final String SERVICE_NAME = "calculate";
	@WebMethod
	@WebResult(name="result")
	public double calculatePower(@WebParam(name="base") double base, @WebParam(name="exponent") double exponent){
		return Math.pow(base, exponent);
	}
}
