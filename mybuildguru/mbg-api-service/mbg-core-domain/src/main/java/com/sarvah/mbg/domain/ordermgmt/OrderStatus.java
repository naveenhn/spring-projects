/**
 * 
 */
package com.sarvah.mbg.domain.ordermgmt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author naveen
 *
 */
@Entity
@Table(name = "order_status", schema = "mbgdb")
public class OrderStatus {

	@Id
	@Column(name = "order_status_id")
	private int orderStatusId;

	@Column(nullable = false)
	private String description;


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the orderStatusId
	 */
	public int getOrderStatusId() {
		return orderStatusId;
	}

	/**
	 * @param orderStatusId
	 *            the orderStatusId to set
	 */
	public void setOrderStatusId(int orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

}
