package com.mec.ejb;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.AccessTimeout;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

//@Singleton
//@Startup
//@AccessTimeout(value = 5, unit = TimeUnit.MINUTES)
////@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class TheatreBox {
	private Map<Integer, Seat> seats;
	
//	@Resource
	TimerService timerService;
	
	private static final long DURATION = TimeUnit.SECONDS.toMillis(6);
	
	public void createTimer(){
		timerService.createSingleActionTimer(DURATION, new TimerConfig());
	}
	
//	@Timeout
	public void timeout(Timer timer){
		System.out.println(String.format("[%s] Timer activated", getClass().getName()));
		setupThreatre();
	}
	
//	@PostConstruct
	public void setupThreatre(){
		seats = new HashMap<>();
		int id = 0;
		for(int i = 0; i < 5; ++i){
			addSeat(new Seat(++id, "Stalls", 40));
			addSeat(new Seat(++id, "Circle", 20));
			addSeat(new Seat(++id, "Balcony", 10));
		}
	}
	
	private void addSeat(Seat seat){
		seats.put(seat.getId(), seat);
	}
	
//	@Lock(LockType.READ)
	public Collection<Seat> getSeats(){
		return Collections.unmodifiableCollection(seats.values());
	}
	
//	@Lock(LockType.WRITE)
	public int getSeatPrice(int seatId) throws NoSuchSeatException{
		return getSeat(seatId).getPrice();
	}
	
//	@Lock(LockType.WRITE)
	public void buyTicket(int seatId) throws SeatBookedException, NoSuchSeatException{
		final Seat seat = getSeat(seatId);
		if(seat.isBooked()){
			throw new SeatBookedException(String.format("Seat %s already booked!", seatId));
		}
		addSeat(seat.getBookedSeat());
	}
	
//	@Lock(LockType.READ)
	private Seat getSeat(int seatId) throws NoSuchSeatException{
		final Seat seat = seats.get(seatId);
		if(null == seat){
			throw new NoSuchSeatException(String.format("Seat %s does not exist!", seatId));
		}
		return seat;
	}
	
	public static class Seat{
		private final int id;
		private final String description;
		private final int price;
		private boolean booked = false;
		private Seat bookedSeat;
		
		public Seat(int id, String type, int price) {
			super();
			this.id = id;
			this.description = type;
			this.price = price;
		}

		public int getId() {
			return id;
		}
		public String getDescription() {
			return description;
		}
		public int getPrice() {
			return price;
		}

		public boolean isBooked() {
			return booked;
		}

		public Seat getBookedSeat() {
			return bookedSeat;
		}

		@Override
		public String toString() {
			return "Seat [id=" + id + ", description=" + description + ", price=" + price + "]";
		}
	}
	
	public static class NoSuchSeatException extends Exception{
		public NoSuchSeatException(String msg){
			super(msg);
		}
	}
	
	public static class SeatBookedException extends Exception{
		public SeatBookedException(String msg){
			super(msg);
		}
	}

	public static class NotEnoughMoneyException extends Exception{
		public NotEnoughMoneyException(String msg){
			super(msg);
		}
	}
}
