package com.mec.ejb;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import com.mec.ejb.TheatreBox.NoSuchSeatException;
import com.mec.ejb.TheatreBox.NotEnoughMoneyException;
import com.mec.ejb.TheatreBox.SeatBookedException;
import com.mec.ejb.inter.TheatreBookerRemote;

//@Stateful
//@Remote(TheatreBookerRemote.class)
//@AccessTimeout(value =5, unit = TimeUnit.MINUTES)
public class TheatreBooker implements TheatreBookerRemote {

//	Logger logger = Logger.getLogger(TheatreBooker.class);
//	@EJB
	private TheatreBox theatreBox;
	private int money;
	
//	@PostConstruct
	public void createCustomer(){
		this.money = 100;
	}
	
	
	@Override
	public int getAccountBalance() {
		return money;
	}

	@Override
	public String bookSeat(int seatId) throws SeatBookedException, NotEnoughMoneyException, NoSuchSeatException {
		final int seatPrice = theatreBox.getSeatPrice(seatId);
		if(seatPrice > money){
			throw new NotEnoughMoneyException(String.format("You don't have enough money to buy this %s seat!", seatId));
		}
		
		theatreBox.buyTicket(seatId);
		money = money - seatPrice;
		return null;
	}
	
//	@Asynchronous
	public void bookSeatAsync(int seatId)throws NotEnoughMoneyException, NoSuchSeatException, SeatBookedException{
		bookSeat(seatId);
	}

//	@Asynchronous
	public Future<String> bookSeatAyncFuture(int seatId) throws NotEnoughMoneyException, NoSuchSeatException, SeatBookedException{
		try{
			Thread.sleep(1000);
			bookSeat(seatId);
			return new AsyncResult<>(String.format("Booked seat: %s, Money left: %s", seatId, money));
		}catch(NoSuchSeatException | SeatBookedException | NotEnoughMoneyException | InterruptedException e){
			return new AsyncResult<>(e.getMessage());
		}
	}
}
