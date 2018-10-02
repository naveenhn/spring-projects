package com.sarvah.mbg.userprofile.response;

import java.util.Set;

import com.sarvah.mbg.domain.common.asset.File;
import com.sarvah.mbg.domain.mongo.userprofile.UserType;

/**
 * 
 * @author RAJu
 *
 */

public class AdminManageDocs {
	private String documentId;
	private File file;
	private String status;
	private Set<UserType> UserTypes;

	/**
	 * @return the documentId
	 */
	public String getDocumentId() {
		return documentId;
	}

	/**
	 * @param documentId the documentId to set
	 */
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(File file) {
		this.file = file;
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

	/**
	 * @return the userTypes
	 */
	public Set<UserType> getUserTypes() {
		return UserTypes;
	}

	/**
	 * @param userTypes
	 *            the userTypes to set
	 */
	public void setUserTypes(Set<UserType> userTypes) {
		UserTypes = userTypes;
	}

}
