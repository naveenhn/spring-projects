/**
 * 
 */
package com.sarvah.mbg.domain.mongo.catalog;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sarvah.mbg.domain.mongo.common.AbstractDocument;
import com.sarvah.mbg.domain.mongo.common.Description;

/**
 * @author naveen
 *
 */
@Document
public class Category extends AbstractDocument {
	

	@NotNull (message = "Category name cannot be null")
	@Indexed(unique = true)
	private String name;
	@NotNull (message = "Category desc cannot be null")
	private Description desc;
	
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

	

	
	

}
