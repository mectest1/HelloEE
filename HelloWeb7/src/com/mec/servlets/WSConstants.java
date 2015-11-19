package com.mec.servlets;

public class WSConstants {
	public static final String SUCCESS = "success";
	public static final String ATTR_SERVLET_LIST = "servlets";
	public static final String ATTR_TYPE = "type";
	public static final String SERVLET_LIST = "/WEB-INF/config/servlet-list.xml";
	public static final String EE_CONFIG = "/WEB-INF/config/ee-config.xml";
	
	public static final class EEConfig{
		public static final String EE_PARA_DIR = "ee.para.dir";
		public static final String paraFileEntry = "entry";
	}
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
