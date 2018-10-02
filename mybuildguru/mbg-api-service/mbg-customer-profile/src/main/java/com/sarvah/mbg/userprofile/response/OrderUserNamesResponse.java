/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

import java.util.Set;

/**
 * @author Shiva
 *
 */
public class OrderUserNamesResponse {

	Set<String> userNames;

	/**
	 * @return the userNames
	 */
	public Set<String> getUserNames() {
		return userNames;
	}

	/**
	 * @param userNames
	 *            the userNames to set
	 */
	public void setUserNames(Set<String> userNames) {
		this.userNames = userNames;
	}
}
