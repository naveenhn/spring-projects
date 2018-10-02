/**
 * 
 */
package com.sarvah.mbg.domain.mongo.review;

import javax.validation.constraints.NotNull;

import com.sarvah.mbg.domain.common.asset.Image;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author naveen
 *
 */
public class Author {
	
	@NotNull (message = "Author id cannot be null")
	private String userId;
	@NotNull (message = "Author name cannot be null")
	private String name;
	private Image image;
	private boolean expert = false;

	public Author() {
	}

	public Author(String userId, String name, Image image, boolean expert) {
		if (userId != null) {

			this.userId = userId;
			this.name = name;
			this.image = image;
			this.expert = expert;
		}
	}

	public Author(User user) {
		// TODO to be implemented
		if (user != null) {

		}
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}
	/**
	 * @return the expert
	 */
	public boolean isExpert() {
		return expert;
	}
	/**
	 * @param expert the expert to set
	 */
	public void setExpert(boolean expert) {
		this.expert = expert;
	}
	

}
