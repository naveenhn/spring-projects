/**
 * 
 */
package com.sarvah.mbg.domain.mongo.catalog;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sarvah.mbg.domain.mongo.common.AbstractDocument;
import com.sarvah.mbg.domain.mongo.common.Description;

/**
 * @author naveen
 *
 */
@Document
public class SubCategory extends AbstractDocument {

	@NotNull(message = "SubCategory name cannot be null")
	@TextIndexed
	private String name;

	@NotNull(message = "SubCategory desc cannot be null")
	private Description desc;

	@TextIndexed
	@NotNull(message = "SubCategory - ref category cannot be null")
	private String category;

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(Description desc) {
		this.desc = desc;
	}

	/**
	 * @return the desc
	 */
	public Description getDesc() {
		return desc;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
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

}
