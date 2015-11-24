package com.mec.ejb.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.mec.pojo.entity.SeatType;

//@Stateless
//@LocalBean
//@Singleton
@Transactional
@ApplicationScoped
//@Stateless
public class SeatTypeDAO extends AbstractDAO<SeatType>{

	private static final long serialVersionUID = 1L;
	
//	public List<SeatType> findAll(){
//		return null;
//	}

	public SeatTypeDAO(){
		super(SeatType.class);
	}
	@Inject
	private EntityManager em;

}
