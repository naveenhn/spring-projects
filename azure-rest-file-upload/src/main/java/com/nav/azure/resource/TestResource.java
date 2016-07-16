/**
 * 
 */
package com.nav.azure.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

/**
 * @author naveen
 *
 */

@Service
@Path("/test")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

	@GET
	public Response getTest() {
		
		return Response.ok().build();
	}

	@DELETE
	@Path("/{testid}")
	public Response deleteTest(@PathParam("testid") String testid) {
		System.out.println(testid);
		return Response.ok().build();
	}


}
