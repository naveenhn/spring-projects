/**
 * 
 */
package com.sarvah.mbg.rest.authorization;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.sarvah.mbg.rest.exception.MBGAppException;
import com.sarvah.mbg.userprofile.auth.AuthUserService;
import com.sarvah.mbg.userprofile.auth.exception.AuthorizationException;
import com.sarvah.mbg.userprofile.auth.model.ApiUser;
import com.sarvah.mbg.userprofile.auth.model.AuthorizationRequest;

/**
 * @author naveen
 *
 */

@Priority(Priorities.AUTHORIZATION)
@PreMatching
public class AuthorizationRequestFilter  implements ContainerRequestFilter{
	
	@Value("${api.security.enabledstorage.connectionstr}")
	private boolean enableSecurity;
	
	@Autowired
	private AuthUserService authUserService;
	 
	protected static final String HEADER_AUTHORIZATION = "X-Auth-Token";
    protected static final String HEADER_DATE = "X-Auth-Date";
    protected static final String HEADER_NONCE = "nonce";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		String authTokenHeader = requestContext.getHeaderString(HEADER_AUTHORIZATION);
		String requestDateStr = requestContext.getHeaderString(HEADER_DATE);
		String nonce = requestContext.getHeaderString(HEADER_NONCE);
		
		AuthorizationRequest authorizationRequest = new AuthorizationRequest(authTokenHeader, nonce, requestDateStr);
		
		try {
			ApiUser user = authUserService.authorize(authorizationRequest);	
			if(user!= null) {
			MBGSecurityContext context = new MBGSecurityContext(user);
			//set security context
			requestContext.setSecurityContext(context);
			}
			
		} catch (AuthorizationException e) {
			throw new MBGAppException("User is not authorized", e, Status.UNAUTHORIZED.getStatusCode(), 30002);
		}
		
		
		
	
		
		
	}

}
