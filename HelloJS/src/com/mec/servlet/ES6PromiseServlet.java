package com.mec.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MEC
 */
@WebServlet(name = "ES6PromiseServlet", urlPatterns = {"/ES6Promise"})
//@WebInitParam(name = "url", value = "ES6Promise")
public class ES6PromiseServlet extends HttpServlet {

	private static final String INIT_PARA_URL = "url";
	private static final String REQUEST_TYPE = "type";
	private static final String TYPE_VALUE = "url";
	private static final String SERVLET_URL = "ES6Promise";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("text/html;charset=UTF-8");
	PrintWriter out = response.getWriter();
	try {
		String requestType = request.getParameter(REQUEST_TYPE);
		if(TYPE_VALUE.equalsIgnoreCase(requestType)){
			//out.print(getInitParameter(INIT_PARA_URL));
			out.print(SERVLET_URL);
		}else{
			out.print(rand.nextInt());
		}
	} finally {
	    out.close();
	}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "ES6 Promise";
    }// </editor-fold>

    private static final Random rand = new Random(System.currentTimeMillis());
}
