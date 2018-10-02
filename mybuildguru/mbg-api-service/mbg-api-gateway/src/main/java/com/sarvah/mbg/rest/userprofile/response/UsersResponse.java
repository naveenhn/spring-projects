/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.response;

import java.util.List;

import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.rest.model.AbstractCollectionResponse;

/**
 * @author naveen
 *
 */
public class UsersResponse extends AbstractCollectionResponse {


	private List<User> users;
	


	/**
	 * @return the users
	 */
	public List<User> getUsers() {

		return users;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(List<User> users) {

		this.users = users;
	}
}
