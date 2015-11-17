package com.mec.ejb;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.mec.ejb.inter.Greetings;

/**
 * Session Bean implementation class TestEJB
 */
//@Stateless
//@LocalBean
//@Default
//@Model
@RequestScoped
@Named
public class TestEJB implements Greetings{

    public TestEJB() {
       
    }

    @Override
    public String greeting(){
    	return "Hello, EJB";
    }
}
