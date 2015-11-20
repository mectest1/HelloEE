package com.mec.ejb;

import java.io.Serializable;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.ejb.AsyncResult;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.mec.ejb.TheatreBox.NoSuchSeatException;
import com.mec.ejb.TheatreBox.NotEnoughMoneyException;
import com.mec.ejb.TheatreBox.SeatBookedException;
import com.mec.ejb.inter.Logger;
import com.mec.pojo.entity.Seat;

//@Stateful
//@Remote(TheatreBookerRemote.class)
//@AccessTimeout(value =5, unit = TimeUnit.MINUTES)
@SessionScoped
@Named
//@Named("theatreBooker")
public class TheatreBooker implements Serializable {	//<- bean must be serializable if it's SessionScoped;
//public class TheatreBooker{

	private static final long serialVersionUID = 1L;
//	Logger logger = Logger.getLogger(TheatreBooker.class);
//	@EJB
//	private TheatreBox theatreBox;
	private int money;
	
	@PostConstruct
	public void createCustomer(){
		this.money = 100;
	}
	
	public int getAccountBalance() {
		return money;
	}
	
	public void reclaimMoney(){
		theatreBox.reclaimTickets();
		createCustomer();
		FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Money reclaimed!", "Monye Reclaimed successfully");
		facesContext.addMessage(null, m);
//		seatEvent.fire(event);
		
	}

	public void bookSeat(int seatId) throws SeatBookedException, NotEnoughMoneyException, NoSuchSeatException {
		final int seatPrice = theatreBox.getSeatPrice(seatId);
		if(seatPrice > money){
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No enough money!", "Registration unsuccessful");
			facesContext.addMessage(null, m);
//			throw new NotEnoughMoneyException(String.format("You don't have enough money to buy this %s seat!", seatId));
			return;
		}
		
		theatreBox.buyTicket(seatId);
//		lastedSeatId = seatId;
		
		FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Booked!", "Booking successful");
		facesContext.addMessage(null, m);
		
		logger.info("Seat booked");
		
		money = money - seatPrice;
	}
	
//	@Asynchronous
	public void bookSeatAsync(int seatId)throws NotEnoughMoneyException, NoSuchSeatException, SeatBookedException{
		bookSeat(seatId);
	}
	
	public int getMoney(){
		return money;
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
	
	
	@Inject
//	private transient Logger logger;	//transient field: no need to be serialized;
	private Logger logger;	//transient field: no need to be serialized;
	@Inject
	private TheatreBox theatreBox;
	@Inject
	private FacesContext facesContext;
	@Inject
	private Event<Seat> seatEvent;
	
//	private int lastedSeatId = Integer.MIN_VALUE;
	
}
