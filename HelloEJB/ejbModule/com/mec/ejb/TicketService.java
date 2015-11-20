package com.mec.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.mec.ejb.inter.Logger;
import com.mec.pojo.entity.Seat;
import com.mec.pojo.entity.SeatType;

@Stateless
public class TicketService {
	@Inject
	private Logger logger;
	
	@Inject
	private Event<SeatType> seatTypeEventSrc;
	
	@Inject
	private Event<Seat> seatEventSrc;
	
	@Inject
	private SeatDAO seatDao;
	
	@Inject
	private SeatTypeDAO seatTypeDao;
	
	public void createSeatType(SeatType seatType) throws Exception{
		logger.info("Registering " + seatType.getDescription());
		seatTypeDao.persist(seatType);
		seatTypeEventSrc.fire(seatType);
	}

	public void createTheatre(List<SeatType> seatTypes){
		for(SeatType type: seatTypes){
			for(int j = 0; j < type.getQuantity(); ++j){
				final Seat seat = new Seat();
				seat.setBooked(false);
				seat.setSeatType(type);
				seatDao.persist(seat);
			}
		}
	}
	
	public void bookSeat(long seatId){
		final Seat seat = seatDao.find(seatId);
		seat.setBooked(true);
		seatDao.persist(seat);
		seatEventSrc.fire(seat);
	}
	
	public void doClearnUp(){
		seatDao.deleteAll();
		seatTypeDao.deleteAll();
	}
}
