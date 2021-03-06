package com.mec.ejb.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import com.mec.pojo.entity.Seat;

@ApplicationScoped
@Transactional
//@Stateless
public class SeatDAO extends AbstractDAO<Seat>{
	
	private static final long serialVersionUID = -6336885612287150807L;

	public SeatDAO(){
		super(Seat.class);
	}
}
