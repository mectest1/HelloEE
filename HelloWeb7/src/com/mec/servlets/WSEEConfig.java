package com.mec.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WSEEConfig
 */
@WebServlet(urlPatterns={"/ee-config"},
	initParams = {@WebInitParam(name= WSConstants.SUCCESS, value = "/WEB-INF/jsp/eeConfig.jsp")}
		)
public class WSEEConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public WSEEConfig() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String succUrl = getInitParameter(WSConstants.SUCCESS);
		String eeParaDir = WSConfig.getEEParaDirectory();
		
		String sysParaFile = String.format("%s/EE_SYS/SYST/sys_para.xml", eeParaDir);
		Map<String, String> configFile = new HashMap<>();
		request.setAttribute("configFile", configFile);
		configFile.put("name", sysParaFile);
		
		BufferedReader r = new BufferedReader(new FileReader(new File(sysParaFile)));
		StringBuilder content = new StringBuilder();
		r.lines().forEach(l -> {
			l = l.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll(" ", "&nbsp;").replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
			content.append(l).append("<br/>\n");
			});
		
//		StringBuilder content = new StringBuilder();
//		
		configFile.put("content", content.toString());
		
		request.getRequestDispatcher(succUrl).forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
