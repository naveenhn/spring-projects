/**
 * 
 */
package com.sarvah.mbg.domain.mail.audit;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author naveen
 *
 */
@Entity
@Table(name = "email_audit_type", schema = "mbgdb")
public class EmailAuditType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "email_audit_type_id")
	private int emailAuditTypeId;

	@Column(nullable = false)
	private String value;

	private String description;



	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

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
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the emailAuditTypeId
	 */
	public int getEmailAuditTypeId() {
		return emailAuditTypeId;
	}

	/**
	 * @param emailAuditTypeId
	 *            the emailAuditTypeId to set
	 */
	public void setEmailAuditTypeId(int emailAuditTypeId) {
		this.emailAuditTypeId = emailAuditTypeId;
	}

}
