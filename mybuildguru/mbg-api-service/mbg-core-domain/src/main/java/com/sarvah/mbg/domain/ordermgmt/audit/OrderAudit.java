/**
 * 
 */
package com.sarvah.mbg.domain.ordermgmt.audit;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author naveen
 *
 */

@Entity
@Table(name = "order_audit", schema = "mbgdb")
public class OrderAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_audit_id")
	private int orderAuditId;

	@Column(name = "order_id")
	private int orderId;

	@Column(name = "item_id")
	private String itemId;

	@ManyToOne
	@JoinColumn(name = "order_audit_type_id")
	private OrderAuditType orderAuditType;

	@Column(name = "note")
	private String note;

	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String modifiedBy;

	@Column(name = "createdtime_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	@Column(name = "lastmodifiedtime_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedTime;

	/**
	 * @return the orderAuditId
	 */
	public int getOrderAuditId() {
		return orderAuditId;
	}

	/**
	 * @param orderAuditId
	 *            the orderAuditId to set
	 */
	public void setOrderAuditId(int orderAuditId) {
		this.orderAuditId = orderAuditId;
	}

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
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy
	 *            the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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
