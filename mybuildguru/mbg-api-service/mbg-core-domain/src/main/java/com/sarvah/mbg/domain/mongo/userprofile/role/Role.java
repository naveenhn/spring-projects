/**
 * 
 */
package com.sarvah.mbg.domain.mongo.userprofile.role;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sarvah.mbg.domain.mongo.common.AbstractDocument;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.UserType;

/**
 * @author naveen
 *
 */
@Document
public class Role extends AbstractDocument {

	@NotNull(message = "Role name cannot be null")
	// @Indexed(unique = true)
	private String name;

	@NotNull
	@DBRef
	private UserPackage userPackage;

	@NotNull(message = "Role desc cannot be null")
	private Description desc;

	@NotNull(message = "Role type cannot be null")
	private UserType type;

	/**
	 * @return the desc
	 */
	public Description getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(Description desc) {
		this.desc = desc;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the userPackage
	 */
	public UserPackage getUserPackage() {
		return userPackage;
	}

	/**
	 * @param userPackage
	 *            the userPackage to set
	 */
	public void setUserPackage(UserPackage userPackage) {
		this.userPackage = userPackage;
	}

	/**
	 * @return the type
	 */
	public UserType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(UserType type) {
		this.type = type;
	}

}
