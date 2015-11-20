package com.mec.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.mec.pojo.entity.SeatType;

//@Stateless
//@LocalBean
//@Singleton
//@Transactional
//@ApplicationScoped
@Stateless
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
