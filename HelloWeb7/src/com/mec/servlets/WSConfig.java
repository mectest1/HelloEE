package com.mec.servlets;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import com.mec.servlets.WSConstants.EEConfig;
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
			URL config = sc.getResource(WSConstants.EE_CONFIG);
			eeConfig.loadFromXML(sc.getResourceAsStream(WSConstants.EE_CONFIG));
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

	public static String getEEParaDirectory(){
		return eeConfig.getProperty(EEConfig.EE_PARA_DIR);
	}

	private static Properties servletList = new Properties();
	private static Properties eeConfig = new Properties();
}
