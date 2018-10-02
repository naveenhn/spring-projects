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
@Table(name = "item_status", schema = "mbgdb")
public class ItemStatus {

	@Id
	@Column(name = "item_status_id")
	private int itemStatusId;

	@Column(nullable = false)
	private String description;

	/**
	 * @return the itemStatusId
	 */
	public int getItemStatusId() {
		return itemStatusId;
	}

	/**
	 * @param itemStatusId
	 *            the itemStatusId to set
	 */
	public void setItemStatusId(int itemStatusId) {
		this.itemStatusId = itemStatusId;
	}

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

}
