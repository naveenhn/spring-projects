/**
 * 
 */
package com.sarvah.mbg.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.rest.authorization.AuthorizationRequestFilter;
import com.sarvah.mbg.rest.resource.CORSResponseFilter;

/**
 * @author naveen
 *
 */
@Component
@ApplicationPath("/v1")
public class JerseyConfig extends ResourceConfig {
	
	
	
	public JerseyConfig() {
		register(MultiPartFeature.class);
		
		/**
		 * Register all the REST Endpoints 
		 */
		packages("com.sarvah.mbg.rest");
		
		/*register(Endpoint.class);
		register(ProductResource.class);
		register(CategoryResource.class);
		register(UserResource.class);*/
		
		register(AuthorizationRequestFilter.class);
		register(CORSResponseFilter.class);
		register(LoggingFilter.class);  // use it when you really need to debug requests hitting api and you dont have a clue
		register(RolesAllowedDynamicFeature.class);
		
	}
	

}
