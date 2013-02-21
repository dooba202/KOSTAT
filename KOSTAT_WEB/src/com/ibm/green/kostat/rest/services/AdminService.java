package com.ibm.green.kostat.rest.services;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ibm.green.kostat.rest.AbstractRESTResource;


/**
 * JAX-RS resource class for administration API
 */
@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
public class AdminService extends AbstractRESTResource {
	
	public AdminService() {
		
	}
	
	
}
