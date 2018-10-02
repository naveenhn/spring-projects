/**
 * 
 */
package com.sarvah.mbg.domain.mongo.userprofile.role;

import javax.validation.constraints.NotNull;

/**
 * @author naveen
 *
 */
public class Constraint {

	// standard set of constraints will come into it
	@NotNull
	private String value;

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}

