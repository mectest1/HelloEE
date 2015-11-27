package com.mec.websockets;

import java.io.IOException;

import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/hello")
public class HelloEndpoint {

	@OnOpen
	public void open(Session session, EndpointConfig config) throws IOException{
		session.getBasicRemote().sendText("Hi!");
	}
}
