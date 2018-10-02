/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

import java.util.Date;

import com.sarvah.mbg.domain.ordermgmt.audit.OrderAuditType;

/**
 * @author Shiva
 *
 */
public class OrderAuditResponse {

	private int orderId;

	private String itemId;

	private OrderAuditType orderAuditType;

	private String note;

	private Date createdTime;

	private Date lastModifiedTime;

	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the orderAuditType
	 */
	public OrderAuditType getOrderAuditType() {
		return orderAuditType;
	}

	/**
	 * @param orderAuditType
	 *            the orderAuditType to set
	 */
	public void setOrderAuditType(OrderAuditType orderAuditType) {
		this.orderAuditType = orderAuditType;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the createdTime
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	/**
	 * @param createdTime
	 *            the createdTime to set
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * @return the lastModifiedTime
	 */
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	/**
	 * @param lastModifiedTime
	 *            the lastModifiedTime to set
	 */
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

}
