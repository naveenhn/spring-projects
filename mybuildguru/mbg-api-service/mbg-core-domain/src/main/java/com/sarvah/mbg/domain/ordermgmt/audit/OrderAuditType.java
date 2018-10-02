/**
 * 
 */
package com.sarvah.mbg.domain.ordermgmt.audit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author naveen
 *
 */
@Entity
@Table(name = "order_audit_type", schema = "mbgdb")
public class OrderAuditType {

	@Id
	@Column(name = "order_audit_type_id")
	private int orderAuditTypeId;
	@Column(nullable = false)
	private String value;
	private String description;


	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
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

	/**
	 * @return the orderAuditTypeId
	 */
	public int getOrderAuditTypeId() {
		return orderAuditTypeId;
	}

	/**
	 * @param orderAuditTypeId
	 *            the orderAuditTypeId to set
	 */
	public void setOrderAuditTypeId(int orderAuditTypeId) {
		this.orderAuditTypeId = orderAuditTypeId;
	}

}
