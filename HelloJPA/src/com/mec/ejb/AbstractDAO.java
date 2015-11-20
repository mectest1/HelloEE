package com.mec.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AbstractDAO <T extends Serializable> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	protected AbstractDAO(Class<T> clazz){
		this.clazz = clazz;
	}
	
	public T find (Object id){
		return em.find(clazz, id);
	}
	
	public void persist(final T entity){
		em.persist(entity);
	}
	
	public List<T> findAll(){
		final CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);
		criteriaQuery.select(criteriaQuery.from(clazz));
		return em.createQuery(criteriaQuery).getResultList();
	}
	
	public void deleteAll(){
		final CriteriaDelete<T> criteriaDelete = em.getCriteriaBuilder().createCriteriaDelete(clazz);
		criteriaDelete.from(clazz);
		em.createQuery(criteriaDelete).executeUpdate();
	}

	@Inject
	private EntityManager em;
	private final Class<T> clazz;

}
