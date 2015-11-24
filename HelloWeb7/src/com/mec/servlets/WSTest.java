package com.mec.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mec.ejb.inter.Greetings;
import com.mec.ejb.inter.Logger;
import com.mec.pojo.entity.Seat;
import com.mec.servlets.WSConstants.AttrInjectType;
import com.mec.servlets.beans.CommandParser;

//import com.mec.ejb.TestEJB;

/**
 * Servlet implementation class TestServlet
 */
//@WebServlet("/test")
@WebServlet(urlPatterns={
		"/test",
		"/test/*"
})
public class WSTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html;charset=UTF-8";
	private static final String MESSAGE = "<!DOCTYPE html><html>"
			+ "<head><title>Hello!</title></head>"
			+ "<body><p>Hello World WildFly</p>\n</body>"
			+ "%s"
			+ "</html>";

	public WSTest() {
		super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		Greetings g = gCdi;
		String type = request.getParameter(WSConstants.ATTR_TYPE);
		
		String forwardPage = commandMap.get(type);
		if(null != forwardPage){
			request.setAttribute("seats", seats);
			request.getRequestDispatcher(forwardPage).forward(request, response);
		}
		
		if(AttrInjectType.EJB.getValue().equalsIgnoreCase(type)){
			g = gEjb;
		}
		try(PrintWriter out = response.getWriter()){
			out.println(String.format(MESSAGE, g.greeting()));
		}
		logger.info("Hello");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Inject
//	@Any
	@Named("helloCDI")
	private Greetings gCdi;
	
	@Inject
//	@Default
	@Named("testEJB")
	private Greetings gEjb;
	
//	@Inject
//	private TestEJB testEjb;	//failed: may only able to inject interfaces;
	
//	@Inject
//	private HelloCDI helloCdi;
	@Inject
//	@Named("JBossLogger")
//	@FromJBoss
	private Logger logger;
	
//	@Inject
//	private FacesContext ctx;
	
//	@Inject
//	private EntityManager em;
	
	@Inject
	private CommandParser parser;
	
	@Inject
	private List<Seat> seats;
	
	
	private static Map<String, String> commandMap = new HashMap<>();
	static{
		commandMap.put("seats", "/WEB-INF/jsp/seats.jsp");
//		commandMap.put("query", "");
//		commandMap.put("delete", "");
//		commandMap.put("", "");
//		commandMap.put("", "");
//		commandMap.put("", "");
//		commandMap.put("", "");
//		commandMap.put("", "");
//		commandMap.put("", "");
//		commandMap.put("", "");
//		commandMap.put("", "");
	}
}
