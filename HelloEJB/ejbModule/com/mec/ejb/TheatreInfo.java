package com.mec.ejb;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.mec.ejb.TheatreBox.Seat;
import com.mec.ejb.inter.TheatreInfoRemote;

//@Stateless
//@Remote(TheatreInfoRemote.class)
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

//	@EJB
	private TheatreBox box;
}
