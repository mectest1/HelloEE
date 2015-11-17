package com.mec.servlets;

import static com.mec.servlets.WSConstants.SUCCESS;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WSIndex
 */
@WebServlet(urlPatterns={"/index"},
	initParams = {@WebInitParam(name=SUCCESS, value="/hello.jsp")}
)
public class WSIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public WSIndex() {
        super();
        
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	succUrl = getInitParameter(SUCCESS);
		request.setAttribute(WSConstants.ATTR_SERVLET_LIST, WSConfig.getServletList());
//		ServletContext sc = getServletContext();
//		String ctxPath = sc.getContextPath();
		
		request.getRequestDispatcher(succUrl).forward(request, response);
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

    private String succUrl;
}
