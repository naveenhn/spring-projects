/**
 * 
 */
package com.sarvah.mbg.domain.mongo.catalog;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sarvah.mbg.domain.mongo.common.AbstractDocument;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author naveen
 *
 */
@Document
public class ProductBrand extends AbstractDocument {

	@TextIndexed
	@NotNull(message = "Brand name cannot be null")
	private String name;

	//@NotNull(message = "Brand desc cannot be null")
	private Description desc;

	@JsonIgnore
	@DBRef
	@NotNull(message = "Brand provider cannot be null")
	private User provider;
	
	private Set<String> subCategoryIds;
	

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
	 * @return the provider
	 */
	public User getProvider() {
		return provider;
	}

	/**
	 * @param provider
	 *            the provider to set
	 */
	public void setProvider(User provider) {
		this.provider = provider;
	}

	/**
	 * @return the subCategoryIds
	 */
	public Set<String> getSubCategoryIds() {
		return subCategoryIds;
	}

	/**
	 * @param subCategoryIds the subCategoryIds to set
	 */
	public void setSubCategoryIds(Set<String> subCategoryIds) {
		this.subCategoryIds = subCategoryIds;
	}

}
