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
public class ProductQuantityType extends AbstractDocument {
	
	@NotNull(message = "Quantity Type name cannot be null")
	@Indexed(unique=true)
	private String name;

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
