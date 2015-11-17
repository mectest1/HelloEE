package com.mec.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class WSContextListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		WSConfig.destroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		WSConfig.init(sce.getServletContext());
	}


}
