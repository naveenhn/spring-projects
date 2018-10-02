/**
 * 
 */
package com.sarvah.mbg.rest.authorization;

import java.security.Principal;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import com.sarvah.mbg.domain.user.AuthRoles;
import com.sarvah.mbg.rest.exception.MBGAppException;
import com.sarvah.mbg.userprofile.auth.model.ApiUser;
// TODO: Auto-generated Javadoc

/**
 * The Class MBGSecurityContext.
 *
 * @author naveen
 */
public class MBGSecurityContext implements SecurityContext{
	
	/** The user. */
	private ApiUser user;
	
	/**
	 * Instantiates a new MBG security context.
	 *
	 * @param user the user
	 */
	public MBGSecurityContext(ApiUser user) {
		this.user = user;
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.core.SecurityContext#getUserPrincipal()
	 */
	@Override
	public Principal getUserPrincipal() {
		
		return user;
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.core.SecurityContext#isUserInRole(java.lang.String)
	 */
	@Override
	public boolean isUserInRole(String role) {
		
		if(user == null)
		{
			throw new MBGAppException("Invalid authorization", Status.UNAUTHORIZED.getStatusCode(), 4003 );
		}
		
		if(role != null && role.equalsIgnoreCase(AuthRoles.ANONYMOUS.name())) {
			return true;
		}
		
		return user.getRole().equalsIgnoreCase(role);
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.core.SecurityContext#isSecure()
	 */
	@Override
	public boolean isSecure() {
		
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.core.SecurityContext#getAuthenticationScheme()
	 */
	@Override
	public String getAuthenticationScheme() {
		
		return SecurityContext.BASIC_AUTH;
	}
	
	
	/**
	 * Gets the api user from security context.
	 *
	 * @param securityContext the security context
	 * @return the api user from security context
	 */
	public final static ApiUser getApiUserFromSecurityContext(
			SecurityContext securityContext) {
		if (securityContext != null) {
			ApiUser apiUser = (ApiUser) securityContext.getUserPrincipal();
			return apiUser;
		}
		return null;
	}
	
	

}
