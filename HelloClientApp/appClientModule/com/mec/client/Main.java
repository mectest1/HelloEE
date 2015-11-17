package com.mec.client;

import java.lang.reflect.Method;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.swing.JOptionPane;

import com.mec.ejb.TestEJB;

public class Main {
	/* (non-Java-doc)
	 * @see java.lang.Object#Object()
	 */
	public Main() {
		super();
	}
	
	
	public static void main(String[] args){
//		JFrame main = new MainFrame();
		Main main = new Main();
//		JOptionPane.showConfirmDialog(null, main.getMsg());
//		JOptionPane.showMessageDialog(null, main.getMsg());
		JOptionPane.showMessageDialog(null, main.getMsgFromInjectedEJB());
	}
	
	@Deprecated
	private String getMsg(){
		try{
			String url= "java:app/HelloEJB/TheatreBox";
			InitialContext context = new InitialContext();
			Object obj = context.lookup(url);
			Method greetings = obj.getClass().getMethod("greetings");
			String msg = (String) greetings.invoke(obj);
			return msg;
		}catch(Exception e){
			return e.getMessage();
		}
	}
	
	private String getMsgFromInjectedEJB(){
		return testEjb.greeting();
	}
	
	@EJB
	private TestEJB testEjb;

}