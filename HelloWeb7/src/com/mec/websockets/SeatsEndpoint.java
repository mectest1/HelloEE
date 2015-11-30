package com.mec.websockets;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.mec.ejb.SessionRegistry;
import com.mec.ejb.inter.Logger;
import com.mec.pojo.entity.Seat;

@ServerEndpoint("/websocket/seats")
public class SeatsEndpoint {

	@Inject
//	@Singleton
//	@Named("sessionRegistry")
	private SessionRegistry sessionRegistry;
	
	
	@Inject
	private Logger logger;
	
	@PostConstruct
	public void init(){
		logger.info("SeatsEndpoint initialized.");
	}
	@OnOpen
	public void open(Session session, EndpointConfig conf){
//		logger.info("Welcome to WebSocket");
		sessionRegistry.add(session);
	}
	
	@OnClose
	public void close(Session session, CloseReason reason){
		sessionRegistry.remove(session);
	}
	
	public void send(@Observes Seat seat){
		sessionRegistry.getAll().forEach(session -> session.getAsyncRemote().sendText(toJson(seat)));
	}
	
	
	private String toJson(Seat seat){
		final JsonObject jsonObject = Json.createObjectBuilder()
				.add("id", seat.getId())
				.add("booked", seat.isBooked())
				.build();
		return jsonObject.toString();
	}
}
