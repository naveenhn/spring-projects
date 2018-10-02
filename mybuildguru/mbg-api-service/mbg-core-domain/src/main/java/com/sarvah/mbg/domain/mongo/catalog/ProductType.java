/**
 * 
 */
package com.sarvah.mbg.domain.mongo.catalog;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sarvah.mbg.domain.mongo.common.AbstractDocument;
import com.sarvah.mbg.domain.mongo.common.Description;

/**
 * @author naveen
 *
 */
@Document
public class ProductType extends AbstractDocument{
	
	
	@NotNull (message = "ProductType name cannot be null")
	@TextIndexed
	private String name;
	@NotNull (message = "ProductType desc cannot be null")
	private Description desc;
	
	@DBRef
	@NotNull (message = "ProductType - ref subcategory cannot be null")
	private SubCategory subCategory;
	
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
	/**
	 * @return the subCategory
	 */
	public SubCategory getSubCategory() {
		return subCategory;
	}
	/**
	 * @param subCategory the subCategory to set
	 */
	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}
	
	
	
	
	
	
	

}
