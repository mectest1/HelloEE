package com.mec.ejb;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import com.mec.ejb.TheatreBox.NoSuchSeatException;
import com.mec.ejb.TheatreBox.Seat;

//@Stateless
public class AutomaticSellerService {

//	@EJB
	private TheatreBox theatreBox;
	
//	@Resource
	private TimerService timerService;
	
//	@Schedule(hour = "*", minute="*/1", persistent = false)
	public void automaticCustomer() throws NoSuchSeatException{
		final Optional<Seat> seatOptional = findFreeSeat();
		if(!seatOptional.isPresent()){
			System.out.println(String.format("[%s] No seats anymore, cancel timers.", getClass().getName()));
			cancelTimers();
			return;
		}
	}
	
	private Optional<Seat> findFreeSeat(){
		final Collection<Seat>list = theatreBox.getSeats();
		return list.stream().filter(seat -> !seat.isBooked()).findFirst();
	}
	
	private void cancelTimers(){
		for(Timer timer : timerService.getTimers()){
			timer.cancel();
		}
	}
	
}
