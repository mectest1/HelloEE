package com.mec.ejb;

import javax.inject.Named;

import org.jboss.logging.Logger;

public class JBossLogger implements com.mec.ejb.inter.Logger {

	private static final long serialVersionUID = 1L;
	
	public JBossLogger(){
		
	}
//	public JBossLogger(String name){
//		logger = Logger.getLogger(name);
//	}
//	
//	public JBossLogger(Class clazz){
//		logger = Logger.getLogger(clazz);
//	}
//	
//	protected JBossLogger(Logger logger){
//		this.logger = logger;
//	}
	
//	public static Logger getLogger(String name){
//		return getLogger(name);
//	}
//	
	public static JBossLogger getLogger(Class<?> clazz){
		Logger logger = Logger.getLogger(clazz);
		
		return new JBossLogger().setLogger(logger);
	}

	
	@Override
	public void info(Object obj) {
		logger.info(obj);
	}

	@Override
	public void warn(Object obj) {
		logger.warn(obj);
	}

	@Override
	public void error(Object error) {
		logger.error(error);
	}

	@Override
	public void info(Object obj, Throwable exp) {
		logger.info(obj, exp);
	}

	@Override
	public void warn(Object obj, Throwable exp) {
		logger.warn(obj, exp);
	}

	@Override
	public void error(Object obj, Throwable exp) {
		logger.error(obj, exp);
	}

	@Override
	public void infov(String format, Object... objects) {
		logger.infov(format, objects);
	}

	public JBossLogger setLogger(Logger logger) {
		this.logger = logger;
		return this;
	}



	private Logger logger;
}
