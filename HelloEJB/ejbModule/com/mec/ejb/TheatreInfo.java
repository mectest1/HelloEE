package com.mec.ejb;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.mec.ejb.inter.TheatreInfoRemote;
import com.mec.pojo.entity.Seat;

//@Stateless
//@Remote(TheatreInfoRemote.class)
@Model
public class TheatreInfo implements TheatreInfoRemote {

//	@Override
	public String printSeatList() {
		final Collection<Seat> seats = box.getSeats();
		final StringBuilder sb = new StringBuilder();
		for(Seat seat: seats){
			sb.append(seat.toString());
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
	
	@PostConstruct
	public void retrieveAllSeatsOrderedByName(){
		seats = box.getSeats();
	}
	
	@Produces
	@Named
	public Collection<Seat> getSeats(){
		return seats;
	}
	
//	
//	@Produces
//	@Named
//	public TheatreBooker getTheatreBooker(){
//		return theatreBooker;
//	}
	
	
	public void onMemberListChagned(@Observes(notifyObserver = Reception.IF_EXISTS) final Seat member){
		retrieveAllSeatsOrderedByName();
	}

//	@EJB
	@Inject
	private TheatreBox box;
	
	private Collection<Seat> seats;
	
//	@Inject
//	private TheatreBooker theatreBooker;
}
