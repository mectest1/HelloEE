//package com.mec.servlets.beans;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.annotation.PostConstruct;
//import javax.enterprise.context.RequestScoped;
//import javax.inject.Inject;
//import javax.inject.Named;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.mec.ejb.inter.Logger;
//import com.mec.pojo.entity.Seat;
//
//@RequestScoped
//@Named("SeatsForwarder")
//public class SeatsForwarder implements PageForwarder {
//
//	private static final long serialVersionUID = 746252536929332811L;
//
//	private SeatsForwarder(){
//		
//	}
//	
//	
//	@PostConstruct
//	private void init(){
//		logger.info("Seats forwarder created.");
//	}
//	@Override
//	public void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//		request.setAttribute("seats", seats);
//		request.getRequestDispatcher(forwardPage).forward(request, response);
//	}
////	@Override
////	public static PageForwarder getPageForwarder() {
////		return instance;
////	}
//	
////	private static final SeatsForwarder instance = new SeatsForwarder();
//	
//	
//	@Inject
//	private List<Seat> seats;
//	private static final String forwardPage = "/WEB-INF/jsp/seats.jsp";
//	
//	@Inject	//seems can not inject static field.
//	private Logger logger;
//}
