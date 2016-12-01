//package com.mec.servlets.beans;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.enterprise.context.SessionScoped;
//import javax.inject.Inject;
//import javax.inject.Named;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.mec.ejb.inter.Greetings;
//import com.mec.ejb.inter.Logger;
//
//
//@SessionScoped
//@Named("CDIForwarder")
//public class CDIForwarder implements PageForwarder {
//
//	private static final long serialVersionUID = 7906259237463392065L;
//
//	private CDIForwarder(){
//		
//	}
//	
//	
//	@Override
//	public void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		try(PrintWriter out = response.getWriter()){
//			out.println(gCdi.greeting());
//		}
//	}
//	
////	@Produces
////	@SessionScoped
////	public static PageForwarder getPageForwarder(){
////		return instance;
////	}
//
////	private static final CDIForwarder instance = new CDIForwarder();
//	
//	@Inject
//	@Named("helloCDI")
//	private Greetings gCdi;
//	
//	@Inject
//	private static Logger logger;
//}
