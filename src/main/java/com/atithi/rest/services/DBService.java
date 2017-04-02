package com.atithi.rest.services;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.atithi.rest.dto.Customer;
import com.atithi.rest.dto.UserDetails;

@Path("/dbService/")
public interface DBService {
	//http://localhost:8081/ImageManager/rest/dbService/store	
	@POST
	@Path("/store")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean postUser(UserDetails userDetails);
	
	@GET
	@Path("/get/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	public UserDetails getUser(UserDetails userDetails);
	
	@PUT
	@Path("/update/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean updateUser(UserDetails userDetails);
	
	
	//http://localhost:8081/ImageManager/rest/dbService/storeCustomer
	@POST
	@Path("/storeCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postCustomer(Customer custDetails);
	
	
	@GET
	@Path("/get/{userName}")
	public void getCustomer();
	
	//http://localhost:8081/ImageManager/rest/dbService/updateCustomer
	//http://localhost:8081/ImageManager/rest/dbService/store
	@PUT
	@Path("/updateCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCustomer(Customer custDetails);

	

}
