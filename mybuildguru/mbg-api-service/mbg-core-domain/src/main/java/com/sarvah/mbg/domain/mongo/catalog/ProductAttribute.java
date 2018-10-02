/**
 * 
 */
package com.sarvah.mbg.domain.mongo.catalog;

import java.util.Set;

import javax.validation.constraints.NotNull;

/**
 * @author naveen
 *
 */
public class ProductAttribute {
	@NotNull (message="ProductAttr key cannot be null")
	private String key;

	private boolean isMultiValued;
	@NotNull(message = "ProductAttr values cannot be null")
	private Set<String> values;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the isMultiValued
	 */
	public boolean isMultiValued() {
		return isMultiValued;
	}
	/**
	 * @param isMultiValued the isMultiValued to set
	 */
	public void setMultiValued(boolean isMultiValued) {
		this.isMultiValued = isMultiValued;
	}

	/**
	 * @return the values
	 */
	public Set<String> getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(Set<String> values) {
		this.values = values;
	}
	
	

}
