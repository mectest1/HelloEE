package com.mec.ejb.inter;

import com.mec.ejb.TheatreBox.NoSuchSeatException;
import com.mec.ejb.TheatreBox.NotEnoughMoneyException;
import com.mec.ejb.TheatreBox.SeatBookedException;

public interface TheatreBookerRemote {

	int getAccountBalance();
	String bookSeat(int seatId) throws SeatBookedException, NotEnoughMoneyException, NoSuchSeatException;
}
