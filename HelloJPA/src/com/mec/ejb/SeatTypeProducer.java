package com.mec.ejb;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.mec.pojo.entity.SeatType;

@RequestScoped
public class SeatTypeProducer {
	
//	@Inject
	@EJB
	private SeatTypeDAO seatTypeDAO;
	
	private List<SeatType> seatTypes;
	
	@PostConstruct
	public void retrieveAlLSeatTypes(){
		seatTypes = seatTypeDAO.findAll();
	}
	
	@Produces
//	@Named
	public List<SeatType> getSeatTypes(){
		return seatTypes;
	}
	
	public void onListChanged(@Observes(notifyObserver=Reception.IF_EXISTS) final SeatType member){
		retrieveAlLSeatTypes();
	}

}
