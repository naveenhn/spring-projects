/**
 * 
 */
package com.sarvah.mbg.domain.mongo.userprofile.role;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sarvah.mbg.domain.mongo.common.AbstractDocument;
import com.sarvah.mbg.domain.mongo.common.Description;

/**
 * @author naveen
 *
 */

@Document
public class Privilege extends AbstractDocument {

	@NotNull
	private String name;

	@NotNull
	private Description desc;

	@NotNull
	private Resource resource;

	@DBRef
	private Action action;

	private Constraint constraint;

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
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 * @return the resource
	 */
	public Resource getResource() {
		return resource;
	}

	/**
	 * @param resource
	 *            the resource to set
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	/**
	 * @return the constraint
	 */
	public Constraint getConstraint() {
		return constraint;
	}

	/**
	 * @param constraint
	 *            the constraint to set
	 */
	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}

}
