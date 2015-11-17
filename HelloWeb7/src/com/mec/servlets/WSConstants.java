package com.mec.servlets;

public class WSConstants {
	public static final String SUCCESS = "success";
	public static final String ATTR_SERVLET_LIST = "servlets";
	public static final String ATTR_TYPE = "type";
	public static final String SERVLET_LIST = "/WEB-INF/config/servlet-list.xml";
	
	public static enum AttrInjectType{
		EJB("ejb")
		,CDI("cdi")
		;
		private AttrInjectType(String val){
			this.value = val;
		}
		public String getValue(){
			return value;
		}
		private String value;
	}
	
}
