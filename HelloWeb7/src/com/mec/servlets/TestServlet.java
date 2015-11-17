package com.mec.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mec.ejb.inter.Greetings;
import static com.mec.servlets.WSConstants.AttrInjectType;

//import com.mec.ejb.TestEJB;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/test")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html;charset=UTF-8";
	private static final String MESSAGE = "<!DOCTYPE html><html>"
			+ "<head><title>Hello!</title></head>"
			+ "<body><p>Hello World WildFly</p>\n</body>"
			+ "%s"
			+ "</html>";

	public TestServlet() {
		super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		Greetings g = gCdi;
		String injectType = request.getParameter(WSConstants.ATTR_TYPE);
		if(AttrInjectType.EJB.getValue().equalsIgnoreCase(injectType)){
			g = gEjb;
		}
		try(PrintWriter out = response.getWriter()){
			out.println(String.format(MESSAGE, g.greeting()));
		}
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
//	private TestEJB testEjb;	//failed;
	
//	@Inject
//	private HelloCDI helloCdi;
}
