package com.mec.ejb;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mec.ejb.inter.Logger;

//@Named
//@ViewScoped
public class BookerService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Logger logger;
	
	@Inject
	private TicketService ticketService;
	
	@Inject
	private FacesContext facesContext;
	
	private int money;

	@PostConstruct
	public void createCoustomer(){
		this.money = 100;
	}
	
	public void bookSeat(long seatId, int price){
		logger.info("Booking seat " + seatId);
		
		if(money < price){
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not enough money!", "Registration failed.");
			facesContext.addMessage(null, m);
			return;
		}
		
		ticketService.bookSeat(seatId);
		
		final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successfully");
		logger.info("Seat booked");
		
		money -= price;
	}
	
	public int getMoney(){
		return money;
	}
}
