/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.response;

import java.util.List;

import com.sarvah.mbg.rest.model.AbstractCollectionResponse;
import com.sarvah.mbg.userprofile.response.UserSummaryView;

/**
 * @author shivu
 *
 */
public class ManageUserResponse extends AbstractCollectionResponse {
	private List<UserSummaryView> users;

	/**
	 * @return the users
	 */
	public List<UserSummaryView> getUsers() {
		return users;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(List<UserSummaryView> users) {
		this.users = users;
	}
}
