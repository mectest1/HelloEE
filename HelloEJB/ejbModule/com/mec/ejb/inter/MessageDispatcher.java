package com.mec.ejb.inter;

public interface MessageDispatcher {

	void sendMessage(String message);
	void sendAsyncMessage(String message);
}