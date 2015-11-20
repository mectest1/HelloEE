package com.mec.ejb;

import javax.ejb.Stateless;

import com.mec.pojo.entity.Seat;

//@ApplicationScoped
//@Transactional
@Stateless
public class SeatDAO extends AbstractDAO<Seat>{
	private static final long serialVersionUID = 1L;
	
	public SeatDAO(){
		super(Seat.class);
	}
}
