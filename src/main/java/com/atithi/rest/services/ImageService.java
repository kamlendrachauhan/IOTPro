package com.atithi.rest.services;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/imageService/")
public interface ImageService {
	
	@POST
	@Path("/store/{name}/{userid}")
	public Response storeImage(final InputStream istream, @PathParam("name") final String fileName, @PathParam("userid")final String userID) throws IOException;
	
}
