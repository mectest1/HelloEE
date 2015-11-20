package com.mec.ejb;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import com.mec.ejb.inter.FromJBoss;
import com.mec.ejb.inter.Logger;


/**
 * <code>
 * <h4>Reference:</h4>
 * <ul>
 * <li>[1] https://developer.jboss.org/thread/223578</li>
 * </ul>
 * </code>
 * <pre>
 * I'm wondering if it's because the LoggerProducer would not be discovered by CDI context if it's not because the @ApplicationScoped annotation.
 * </pre>
 * @author MEC
 *
 */
@ApplicationScoped	//Seriously? If this LoggerProducer is not a managed bean, then all those @Procues method would not be discovered.
public class LoggerProducer {
	
	@Produces
//	@ApplicationScoped
//	@Named("JBossLogger")
	@FromJBoss
	@Default
	public Logger produceLogger(InjectionPoint injectionPoint){
		JBossLogger logger = new JBossLogger();
		logger.setLogger(org.jboss.logging.Logger.getLogger(injectionPoint.getMember().getDeclaringClass()));
		return logger;
	}
}
