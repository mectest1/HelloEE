package com.mec.ejb;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mec.ejb.inter.Logger;


/**
 * <code>
 * <h4>Reference:</h4>
 * <ul>
 * <li>[1] https://developer.jboss.org/thread/223578</li>
 * </ul>
 * </code>
 * @author MEC
 *
 */
@ApplicationScoped	//Seriously? If this LoggerProducer is not a managed bean, then all those @Procues method would not be discovered.
public class LoggerProducer {
	
	@Produces
//	@ApplicationScoped
//	@Named("JBossLogger")
	public Logger produceLogger(InjectionPoint injectionPoint){
		JBossLogger logger = new JBossLogger();
		logger.setLogger(org.jboss.logging.Logger.getLogger(injectionPoint.getMember().getDeclaringClass()));
		return logger;
	}
}
