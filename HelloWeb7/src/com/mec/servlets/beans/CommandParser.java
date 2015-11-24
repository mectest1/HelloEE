package com.mec.servlets.beans;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import com.mec.servlets.WSConstants;

@RequestScoped
public class CommandParser {

	
	public List<String> parseCommandsFromUrl(String urlPath, String servletPrefix){
		int index = urlPath.indexOf(servletPrefix);
		if(-1 < index){
			urlPath = urlPath.substring(index + servletPrefix.length());
		}
		String[] commands = urlPath.split(WSConstants.SLASH);
		
		return Arrays.asList(commands);
	}
	
}
