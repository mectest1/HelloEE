package com.mec.servlets.beans;

import java.io.IOException;
import java.io.PrintWriter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mec.ejb.inter.Greetings;
import com.mec.ejb.inter.Logger;

@SessionScoped
@Named("EjbForwarder")
public class EjbForwarder implements PageForwarder {

	private static final long serialVersionUID = -2357635830243543641L;

	private EjbForwarder(){
		
	}
	@Override
	public void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out = response.getWriter()){
			out.println(gEjb.greeting());
		}
	}

//	@Override
//	public static PageForwarder getPageForwarder() {
//		return instance;
//	}

	
//	private final static EjbForwarder instance = new EjbForwarder();
	
	@Inject
	@Named("testEJB")
	private Greetings gEjb;
	
	@Inject
	private static Logger logger;
	
}
