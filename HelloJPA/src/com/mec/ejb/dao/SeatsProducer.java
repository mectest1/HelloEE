package com.mec.ejb.dao;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.mec.pojo.entity.Seat;

//@SessionScoped
@ApplicationScoped
//@Singleton
//@Stateless
public class SeatsProducer implements Serializable{
	
	private static final long serialVersionUID = 6846741836452186914L;

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
	@Default
	public List<Seat> getSeats(){
		return Collections.unmodifiableList(seats);
	}
	
	public void onMemeberListChanged(@Observes(notifyObserver=Reception.IF_EXISTS) final Seat member){
		retrieveAllSeats();
	}
	
}
