package com.mec.ejb;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class FacesContextProducer {

	@Produces
	@RequestScoped
	public FacesContext produceFacesContext(){
		return FacesContext.getCurrentInstance();
	}
}
