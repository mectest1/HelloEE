package com.mec.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mec.ejb.inter.Greetings;

/**
 * Session Bean implementation class TestEJB
 */
//@Stateless
//@LocalBean
public class TestEJB implements Greetings{

    public TestEJB() {
       
    }

    @Override
    public String greeting(){
    	return "Hello, EJB";
    }
}
