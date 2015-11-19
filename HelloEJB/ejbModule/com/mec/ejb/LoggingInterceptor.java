package com.mec.ejb;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.mec.ejb.inter.Logged;
import com.mec.ejb.inter.Logger;

@Interceptor
@Logged
public class LoggingInterceptor implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@AroundInvoke
	public Object log(InvocationContext context) throws Exception{
		final Logger logger = JBossLogger.getLogger(context.getTarget().getClass());
		logger.infov("Excuting method {0}", context.getMethod().toString());
		return context.proceed();
	}
	
	
//	@Inject
//	private Logger logger;
}
