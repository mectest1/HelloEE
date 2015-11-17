package com.mec.client;

import java.lang.reflect.Method;

import javax.naming.InitialContext;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class MainFrame extends JFrame{
	public MainFrame(){
		super();
		setSize(400, 300);
		setLocationRelativeTo(null);
		setTitle("Main");
		init();
		setVisible(true);
	}
	
	
	private void init(){
		btnInvoke = new JButton("Get the message");
		txtEjbUrl = new JTextArea();
		txtMsgReturned = new JTextArea();
		
		getContentPane().add(txtMsgReturned);
		getContentPane().add(btnInvoke);
		btnInvoke.addActionListener(e -> {
			try {
				String ejbUrl = txtEjbUrl.getText();
				InitialContext context = new InitialContext();
				Object hello = context.lookup(ejbUrl);
				Method greeting = hello.getClass().getMethod("greeting");
				String result = (String) greeting.invoke(hello);
				txtMsgReturned.setText(result);
			} catch (Exception e2) {
//				e2.printStackTrace(System.out);
				txtMsgReturned.setText(e2.getMessage());
//				throw e2;
			}
			
		});
		getContentPane().add(txtMsgReturned);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
	
	
	
	private JButton btnInvoke;
	private JTextArea txtEjbUrl;
	private JTextArea txtMsgReturned;
}
