/**
 * 
 */
package com.sarvah.mbg.domain.mail.audit;

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
@Table(name = "email_audit", schema = "mbgdb")
public class EmailAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "email_audit_id")
	private Integer emailAuditId;

	@Column(name = "email_from")
	private String emailFrom;

	@Column(name = "email_to")
	private String emailTo;

	@Column(name = "email_subject")
	private String emailSubject;
	
	@Column (name="email_message_id")
	private long emailMessageId;

	@Column(name = "email_createdby")
	private String createdBy;

	@Column(name = "email_modifiedby")
	private String modifiedBy;

	@Column(name = "email_createdtime_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	@Column(name = "email_lastmodifiedtime_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastmodifiedTime;

	@Column(name = "email_category")
	private String emailCategory;

	@ManyToOne
	@JoinColumn(name = "email_audit_type_id")
	private EmailAuditType auditType;

	/**
	 * @return the emailFrom
	 */
	public String getEmailFrom() {
		return emailFrom;
	}

	/**
	 * @param emailFrom
	 *            the emailFrom to set
	 */
	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	/**
	 * @return the emailTo
	 */
	public String getEmailTo() {
		return emailTo;
	}

	/**
	 * @param emailTo
	 *            the emailTo to set
	 */
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	/**
	 * @return the emailSubject
	 */
	public String getEmailSubject() {
		return emailSubject;
	}

	/**
	 * @param emailSubject
	 *            the emailSubject to set
	 */
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
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
	 * @return the lastmodifiedTime
	 */
	public Date getLastmodifiedTime() {
		return lastmodifiedTime;
	}

	/**
	 * @param lastmodifiedTime
	 *            the lastmodifiedTime to set
	 */
	public void setLastmodifiedTime(Date lastmodifiedTime) {
		this.lastmodifiedTime = lastmodifiedTime;
	}

	/**
	 * @return the emailCategory
	 */
	public String getEmailCategory() {
		return emailCategory;
	}

	/**
	 * @param emailCategory
	 *            the emailCategory to set
	 */
	public void setEmailCategory(String emailCategory) {
		this.emailCategory = emailCategory;
	}

	/**
	 * @return the auditType
	 */
	public EmailAuditType getAuditType() {
		return auditType;
	}

	/**
	 * @param auditType
	 *            the auditType to set
	 */
	public void setAuditType(EmailAuditType auditType) {
		this.auditType = auditType;
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
	 * @return the emailAuditId
	 */
	public Integer getEmailAuditId() {
		return emailAuditId;
	}

	/**
	 * @param emailAuditId the emailAuditId to set
	 */
	public void setEmailAuditId(Integer emailAuditId) {
		this.emailAuditId = emailAuditId;
	}

	/**
	 * @return the emailMessageId
	 */
	public long getEmailMessageId() {
		return emailMessageId;
	}

	/**
	 * @param emailMessageId the emailMessageId to set
	 */
	public void setEmailMessageId(long emailMessageId) {
		this.emailMessageId = emailMessageId;
	}

}
