package com.mec.ejb.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.mec.pojo.entity.SeatType;

//@SessionScoped
//@Singleton
@RequestScoped
public class SeatTypeProducer implements Serializable{
	private static final long serialVersinoUID = 1L;
	@Inject
//	@EJB
	private SeatTypeDAO seatTypeDAO;
	
	private List<SeatType> seatTypes;
	
	@PostConstruct
	public void retrieveAlLSeatTypes(){
		seatTypes = seatTypeDAO.findAll();
	}
	
	@Produces
//	@Named
	@RequestScoped
	public List<SeatType> getSeatTypes(){
		return seatTypes;
	}
	
	public void onListChanged(@Observes(notifyObserver=Reception.IF_EXISTS) final SeatType member){
		retrieveAlLSeatTypes();
	}

}
