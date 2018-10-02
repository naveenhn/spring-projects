/**
 * 
 */
package com.sarvah.mbg.userprofile.auth.model;

/**
 * @author naveen
 *
 */
public class AuthorizationRequest {
	
	
	   

	    /**
	     * An Iso8061 formatted date timestamp
	     */
	    private final String requestDate;

	    /**
	     * Client generated unique nonce value
	     */
	    private final String nonceToken;
	    
	    /**
	     * The AuthorizationToken which should be in a format that the appropriate AuthorizationService can understand
	     */
	    private final String authorizationToken;
	    
	    
	    public AuthorizationRequest( String authorizationToken, String nonceToken, String requestDateString){
	    	this.authorizationToken = authorizationToken;
	    	this.nonceToken = nonceToken;
	    	this.requestDate = requestDateString;
	    }


		/**
		 * @return the requestDate
		 */
		public String getRequestDate() {
			return requestDate;
		}


		/**
		 * @return the nonceToken
		 */
		public String getNonceToken() {
			return nonceToken;
		}


		/**
		 * @return the authorizationToken
		 */
		public String getAuthorizationToken() {
			return authorizationToken;
		}

		

		

	    
	    

}
