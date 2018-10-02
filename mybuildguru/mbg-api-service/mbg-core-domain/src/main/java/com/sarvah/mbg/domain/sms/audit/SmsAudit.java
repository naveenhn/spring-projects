/**
 * 
 */
package com.sarvah.mbg.domain.sms.audit;

import java.io.Serializable;
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
@Table(name = "sms_audit", schema = "mbgdb")
public class SmsAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "sms_audit_id")
	private int smsAuditId;

	@ManyToOne
	@JoinColumn(name = "sms_audit_type_id")
	private SmsAuditType smsAuditType;

	@Column(name = "sms_to")
	private String smsTo;

	@Column(name = "sms_message_id")
	private long smsMessageId;

	@Column(name = "sms_createdby")
	private String smsCreatedBy;

	@Column(name = "sms_modifiedby")
	private String smsModifiedBy;

	@Column(name = "sms_createdtime_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date smsCreatedTime;

	@Column(name = "sms_lastmodifiedtime_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date smsLastModifiedTime;

	/**
	 * @return the smsAuditId
	 */
	public int getSmsAuditId() {
		return smsAuditId;
	}

	/**
	 * @param smsAuditId
	 *            the smsAuditId to set
	 */
	public void setSmsAuditId(int smsAuditId) {
		this.smsAuditId = smsAuditId;
	}

	/**
	 * @return the smsAuditType
	 */
	public SmsAuditType getSmsAuditType() {
		return smsAuditType;
	}

	/**
	 * @param smsAuditType
	 *            the smsAuditType to set
	 */
	public void setSmsAuditType(SmsAuditType smsAuditType) {
		this.smsAuditType = smsAuditType;
	}

	/**
	 * @return the smsTo
	 */
	public String getSmsTo() {
		return smsTo;
	}

	/**
	 * @param smsTo
	 *            the smsTo to set
	 */
	public void setSmsTo(String smsTo) {
		this.smsTo = smsTo;
	}

	/**
	 * @return the smsCreatedBy
	 */
	public String getSmsCreatedBy() {
		return smsCreatedBy;
	}

	/**
	 * @param smsCreatedBy
	 *            the smsCreatedBy to set
	 */
	public void setSmsCreatedBy(String smsCreatedBy) {
		this.smsCreatedBy = smsCreatedBy;
	}

	/**
	 * @return the smsModifiedBy
	 */
	public String getSmsModifiedBy() {
		return smsModifiedBy;
	}

	/**
	 * @param smsModifiedBy
	 *            the smsModifiedBy to set
	 */
	public void setSmsModifiedBy(String smsModifiedBy) {
		this.smsModifiedBy = smsModifiedBy;
	}

	/**
	 * @return the smsCreatedTime
	 */
	public Date getSmsCreatedTime() {
		return smsCreatedTime;
	}

	/**
	 * @param smsCreatedTime
	 *            the smsCreatedTime to set
	 */
	public void setSmsCreatedTime(Date smsCreatedTime) {
		this.smsCreatedTime = smsCreatedTime;
	}

	/**
	 * @return the smsMessageId
	 */
	public long getSmsMessageId() {
		return smsMessageId;
	}

	/**
	 * @param smsMessageId
	 *            the smsMessageId to set
	 */
	public void setSmsMessageId(long smsMessageId) {
		this.smsMessageId = smsMessageId;
	}

	/**
	 * @return the smsLastModifiedTime
	 */
	public Date getSmsLastModifiedTime() {
		return smsLastModifiedTime;
	}

	/**
	 * @param smsLastModifiedTime
	 *            the smsLastModifiedTime to set
	 */
	public void setSmsLastModifiedTime(Date smsLastModifiedTime) {
		this.smsLastModifiedTime = smsLastModifiedTime;
	}

}
