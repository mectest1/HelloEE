package com.mec.ejb;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.mec.pojo.entity.SeatType;
import com.mec.pojo.entity.SeatType.SeatPosition;

@Model
public class TheaterSetupService {
	private static final String VIEW_BOOK = "book";
	private static final String VIEW_START = "/index";
	
	@Inject
	private FacesContext facesContext;
	

	@Inject
	private TicketService ticketService;
	
	@Inject
	private List<SeatType> seatTypes;
	
	@Produces
	@Named
	private SeatType newSeatType;
	
	@PostConstruct
	public void initNewSeatType(){
		newSeatType = new SeatType();
	}
	
	public String createTheatre(){
		ticketService.createTheatre(seatTypes);
		return VIEW_BOOK;
	}
	
	public String restart(){
		ticketService.doClearnUp();
		return VIEW_START;
	}
	
	public void addNewSeats() throws Exception{
		try{
			ticketService.createSeatType(newSeatType);
			
			final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Done", "Seats added");
			facesContext.addMessage(null, m);
			initNewSeatType();
		}catch(Exception e){
			final String errorMsg = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMsg, "Error while saving data");
			facesContext.addMessage(null, m);
		}
	}
	
	private String getRootErrorMessage(Exception e){
		//Default to general error message that registration failed.
		String errorMessage = "Registration failed. See server log for more information";
		if(null == e){
			//this shouldn't happen, but return the default error message.
			return errorMessage;
		}
		
		Throwable t = e;
		while(null != t){
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		//This is the root cause message;
		return errorMessage;
	}
	public List<SeatPosition> getPositions(){
		return Arrays.asList(SeatPosition.values());
	}
}
