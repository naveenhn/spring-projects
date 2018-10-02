/**
 * 
 */
package com.sarvah.mbg.domain.mongo.userprofile;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sarvah.mbg.domain.common.asset.Image;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.domain.mongo.common.AbstractDocument;
import com.sarvah.mbg.domain.mongo.common.Description;

/**
 * @author naveen This class is used for showcasing/mainting user's profile
 *         (esp. Architects and Interior designer) and their projects, this can
 *         be extended further as required
 *
 */
@Document
public class UserProjectProfile extends AbstractDocument {

	@NotNull
	private String profileName;

	private List<Image> profileImages;
	
	private ProjectType projectTypesHandled;

	private Description desc;
	
	@NotNull
	@DBRef
	@JsonIgnore
	private User user; // for now this is only architects/interior designers

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
	 * @return the profileImages
	 */
	public List<Image> getProfileImages() {
		return profileImages;
	}

	/**
	 * @param profileImages
	 *            the profileImages to set
	 */
	public void setProfileImages(List<Image> profileImages) {
		this.profileImages = profileImages;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the desc
	 */
	public Description getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(Description desc) {
		this.desc = desc;
	}

	/**
	 * @return the projectTypesHandled
	 */
	public ProjectType getProjectTypesHandled() {
		return projectTypesHandled;
	}

	/**
	 * @param projectTypesHandled the projectTypesHandled to set
	 */
	public void setProjectTypesHandled(ProjectType projectTypesHandled) {
		this.projectTypesHandled = projectTypesHandled;
	}

	

	

}
