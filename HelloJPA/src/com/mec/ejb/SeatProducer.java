package com.mec.ejb;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.mec.pojo.entity.Seat;

@RequestScoped
public class SeatProducer {
	
	@Inject
//	@EJB
	private SeatDAO seatDao;
	
	private List<Seat> seats;
	
	
	@PostConstruct
	public void retrieveAllSeats(){
//		seats = seatDao.findAllSeats();
		seats = seatDao.findAll();
	}
	
	@Produces
	@RequestScoped
//	@Named
	public List<Seat> getSeats(){
		return seats;
	}
	
	public void onMemeberListChanged(@Observes(notifyObserver=Reception.IF_EXISTS) final Seat member){
		retrieveAllSeats();
	}
	
}
