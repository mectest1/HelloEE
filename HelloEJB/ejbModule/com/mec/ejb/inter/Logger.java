package com.mec.ejb.inter;

public interface Logger {

	void info(Object obj);
	void warn(Object obj);
	void error(Object error);
	
	
	void info(Object obj, Throwable exp);
	void warn(Object obj, Throwable exp);
	void error(Object obj, Throwable exp);
	
	void infov(String format, Object ... objects);
}
