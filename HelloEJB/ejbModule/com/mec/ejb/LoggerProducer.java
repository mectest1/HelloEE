package com.mec.ejb;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import com.mec.ejb.inter.Logger;

public class LoggerProducer {
	
	@Produces
//	@Named("JBossLogger")
	public Logger produceLogger(InjectionPoint injectionPoint){
		return (Logger)JBossLogger.getLogger(injectionPoint.getMember().getDeclaringClass());
	}

}
