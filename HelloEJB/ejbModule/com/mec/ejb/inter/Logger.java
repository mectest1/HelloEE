package com.mec.ejb.inter;

import java.io.Serializable;

public interface Logger extends Serializable{

	void info(Object obj);
	void warn(Object obj);
	void error(Object error);
	
	
	void info(Object obj, Throwable exp);
	void warn(Object obj, Throwable exp);
	void error(Object obj, Throwable exp);
	
	void infov(String format, Object ... objects);
}
