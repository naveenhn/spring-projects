/**
 * 
 */
package com.sarvah.mbg.domain.user.audit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Shiva
 *
 */
@Entity
@Table(name = "user_audit_type", schema = "mbgdb")
public class UserAuditType {

	@Id
	@Column(name = "user_audit_type_id")
	private int userAuditTypeId;
	@Column(nullable = false)
	private String value;
	private String description;

	/**
	 * @return the userAuditTypeId
	 */
	public int getUserAuditTypeId() {
		return userAuditTypeId;
	}

	/**
	 * @param userAuditTypeId
	 *            the userAuditTypeId to set
	 */
	public void setUserAuditTypeId(int userAuditTypeId) {
		this.userAuditTypeId = userAuditTypeId;
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
