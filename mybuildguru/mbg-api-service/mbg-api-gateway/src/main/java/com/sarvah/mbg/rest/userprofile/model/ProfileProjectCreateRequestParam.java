/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.model;

import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;

/**
 * @author Shiva
 *
 */
public class ProfileProjectCreateRequestParam {

	private String profileName;
	private String desc;
	private ProjectType projectTypes;

	/**
	 * @return the projectTypes
	 */
	public ProjectType getProjectTypes() {
		return projectTypes;
	}

	/**
	 * @param projectTypes
	 *            the projectTypes to set
	 */
	public void setProjectTypes(ProjectType projectTypes) {
		this.projectTypes = projectTypes;
	}

	/**
	 * @return the profileName
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * @param profileName
	 *            the profileName to set
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
