package com.mec.ejb;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 * <p>
 * Note that the @ApplicationScoped is only used when the discovery-mode in beans.xml is set to "annotated" (instead of "all"),
 * thus this EntityManagerProducer can be discovered by the CDI manager.
 * </p>
 * @author MEC
 *
 */
@ApplicationScoped
public class EntityManagerProducer {

	@Produces
	@PersistenceContext
	private EntityManager em;
}
