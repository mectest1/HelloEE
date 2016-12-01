/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mec.ejb.inter;

import javax.inject.Inject;

/**
 *
 * @author mtang071
 */
//@Inject
public interface Logger {
	void info(Object obj);
	void warn(Object obj);
	void error(Object error);
	
	
	void info(Object obj, Throwable exp);
	void warn(Object obj, Throwable exp);
	void error(Object obj, Throwable exp);
	
	void infov(String format, Object ... objects);
}
