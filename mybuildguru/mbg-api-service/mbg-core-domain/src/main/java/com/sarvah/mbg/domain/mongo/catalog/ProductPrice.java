/**
 * 
 */
package com.sarvah.mbg.domain.mongo.catalog;

import javax.validation.constraints.NotNull;

/**
 * @author Shivu
 *
 */
public class ProductPrice {
	@NotNull(message = "Product quantity cannot be null")
	private String key;

	@NotNull(message = "Product price cannot be null")
	private String value;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

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
