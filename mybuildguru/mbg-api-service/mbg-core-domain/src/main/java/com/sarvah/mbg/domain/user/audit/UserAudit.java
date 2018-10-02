/**
 * 
 */
package com.sarvah.mbg.domain.user.audit;

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
 * @author Shiva
 *
 */
@Entity
@Table(name = "user_audit", schema = "mbgdb")
public class UserAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_audit_id")
	private int userAuditId;

	@Column(name = "user_id")
	private int userId;

	@ManyToOne
	@JoinColumn(name = "user_audit_type_id")
	private UserAuditType userAuditType;

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
	 * @return the userAuditId
	 */
	public int getUserAuditId() {
		return userAuditId;
	}

	/**
	 * @param userAuditId
	 *            the userAuditId to set
	 */
	public void setUserAuditId(int userAuditId) {
		this.userAuditId = userAuditId;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the userAuditType
	 */
	public UserAuditType getUserAuditType() {
		return userAuditType;
	}

	/**
	 * @param userAuditType
	 *            the userAuditType to set
	 */
	public void setUserAuditType(UserAuditType userAuditType) {
		this.userAuditType = userAuditType;
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
