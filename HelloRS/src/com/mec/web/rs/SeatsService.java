package com.mec.web.rs;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mec.ejb.dao.SeatDAO;
import com.mec.pojo.entity.Seat;

@Path("/seats")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class SeatsService {

	@Path("/query")
	@GET
	public List<Seat> getSeats(){
		return seats;
	}

	@Path("/query/{id}")
	@GET
	public Seat getSeat(@PathParam("id") int id){
		return seatDao.find(id);
	}
	
	@Inject
	private List<Seat> seats;
	
	
	@Inject
	private SeatDAO seatDao;
}