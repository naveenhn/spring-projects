/**
 * 
 */
package com.nav.azure.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * @author naveen
 *
 */
@Component
@ApplicationPath("/v1")
public class RestConfig extends ResourceConfig {

	public RestConfig() {
		register(MultiPartFeature.class);
		packages("com.nav.azure");

	}

}
