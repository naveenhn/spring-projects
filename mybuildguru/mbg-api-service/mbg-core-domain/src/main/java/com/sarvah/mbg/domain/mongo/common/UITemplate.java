/**
 * 
 */
package com.sarvah.mbg.domain.mongo.common;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author naveen
 *
 */
@Document
public class UITemplate extends AbstractDocument {

	private String name;
	private Description desc;

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

}
