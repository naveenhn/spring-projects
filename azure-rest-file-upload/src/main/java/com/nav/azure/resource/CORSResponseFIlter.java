/**
 * 
 */
package com.nav.azure.resource;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

/**
 * @author naveen
 *
 */
@Provider
public class CORSResponseFIlter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {

		MultivaluedMap<String, Object> headers = responseContext.getHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		// headers.add("Access-Control-Allow-Origin",
		// "http://podcastpedia.org"); //allows CORS requests only coming from
		// podcastpedia.org
		headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
		headers.add("Access-Control-Allow-Headers",
				"access-control-allow-origin, Content-Type, X-Requested-With, X-PINGOTHER, X-File-Name, Cache-Control");
		
		
		/*
		 * Access-Control-Allow-Credentials:true
		 * Access-Control-Allow-Headers:Content-Type, X-Requested-With,
		 * X-PINGOTHER, X-File-Name, Cache-Control
		 * Access-Control-Allow-Methods:PUT, POST, GET, OPTIONS
		 * Access-Control-Allow-Origin:http://www.dropzonejs.com
		 */
	}

}
