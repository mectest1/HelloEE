package com.mec.web.rs;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.mec.ejb.inter.Greetings;

@Path("/greetings")
//@Produces(MediaType.APPLICATION_JSON)
public class GreetingService {

	@GET		//without it, will get this error: Subresource for target class has no jax-rs annotations.: java.lang.String
	@Path("/")
	public String greeting(){
		return greetings.greeting();
	}
	
	@GET
	@Path("/{name}")
	public String greeting(@PathParam("name") String name){
		return String.format("%s %s", greeting(), name);
	}
	
	
	
	@Inject
//	@Named("helloCDI")	//WELD-001475: The following beans match by type, but none have matching qualifiers:
	  					//- Managed Bean [class com.mec.ejb.TestEJB] with qualifiers [@Default @Any @Named]
//	@Named("testEJB")
	private Greetings greetings;
}
