package com.sarvah.mbg.rest.userprofile.model;

import java.util.Set;

import com.sarvah.mbg.domain.mongo.userprofile.UserType;

public class UserRelatedDocumentsCreateRequestParam {

	private Set<UserType> userTypes;
	private String status;

	/**
	 * @return the userTypes
	 */
	public Set<UserType> getUserTypes() {
		return userTypes;
	}

	/**
	 * @param userTypes
	 *            the userTypes to set
	 */
	public void setUserTypes(Set<UserType> userTypes) {
		this.userTypes = userTypes;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
