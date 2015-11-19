package com.mec.ejb;

import javax.enterprise.event.Observes;

import com.mec.ejb.inter.NamedView;
import com.mec.pojo.entity.Seat;

//@Named
//@ViewScoped
@NamedView
public class BookingRecord {
	private int bookedCount = 0;
	
	public int getBookedCount(){
		return bookedCount;
	}
	
	public void bookEvent(@Observes Seat bookedSeat){
		++bookedCount;
	}
}
