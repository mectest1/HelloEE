package com.mec.ejb;

import java.util.Optional;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.mec.pojo.entity.Seat;

@Model
public class TheatrePoller {

	@Inject
	private TheatreBox theatreBox;
	
	public boolean isPollingActive(){
		return areFreeSeatsAvailable();
	}
	
	private boolean areFreeSeatsAvailable(){
		final Optional<Seat> firstSeat = theatreBox.getSeats().stream().filter(s -> !s.isBooked()).findFirst();
		return firstSeat.isPresent();
	}
}
