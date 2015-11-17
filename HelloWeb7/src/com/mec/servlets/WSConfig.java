package com.mec.servlets;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
//@ApplicationScoped
//@Singleton
//@Startup
//@WebListener
public class WSConfig{

//	@PostConstruct
	protected static void init(ServletContext sc){
		try {
			String servletListConfig = WSConstants.SERVLET_LIST;
//			URL config = sc.getResource(servletListConfig);
			servletList.loadFromXML(sc.getResourceAsStream(servletListConfig));
		} catch(IOException e){
//			e.printStackTrace();
		}finally {
		}
	}
	
	
//	@PreDestroy
	protected static void destroy(){
		
	}
	

	public static Set<Object> getServletList(){
		return servletList.keySet();
	}


	private static Properties servletList = new Properties();
}
