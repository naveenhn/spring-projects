/**
 * 
 */
package com.sarvah.mbg.rest.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.rest.exception.MBGAppException;

/**
 * @author naveen
 *
 */
@Component
@Path("/hello")
public class Endpoint {
	
	@Value("${message:World}")
	private String msg;

	@GET
	public Response message(@QueryParam("val") String str) throws MBGAppException {
		
		String responseStr = "Hello " + msg ;
		try {
			if(str.equals("")) { //nullpointer candidate will test if exception handling is working 
				responseStr += msg;
			}
			
		} catch (Exception e) {
			throw new MBGAppException("Test Error msg", e, Status.BAD_REQUEST.getStatusCode(), 007);
		}
		
		
		return Response.ok(responseStr).build();
	}
	 

}
