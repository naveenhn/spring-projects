/**
 * 
 */
package com.sarvah.mbg.domain.mongo.dashboard;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sarvah.mbg.domain.mongo.common.AbstractDocument;

/**
 * @author Shivu
 *
 */
@Document
public class ProductNotOnBoardedNameStore extends AbstractDocument {
	@NotNull(message = "name cannot be null")
	private String name;

	private long orderCount;

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
	 * @return the orderCount
	 */
	public long getOrderCount() {
		return orderCount;
	}

	/**
	 * @param orderCount
	 *            the orderCount to set
	 */
	public void setOrderCount(long orderCount) {
		this.orderCount = orderCount;
	}
}
