package com.mec.servlets.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PageForwarder extends Serializable{

	void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

//	PageForwarder getPageForwarder();
}