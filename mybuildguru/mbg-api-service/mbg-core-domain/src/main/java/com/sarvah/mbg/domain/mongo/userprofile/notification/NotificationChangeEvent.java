/**
 * 
 */
package com.sarvah.mbg.domain.mongo.userprofile.notification;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sarvah.mbg.domain.mongo.common.AbstractDocument;

/**
 * @author Shiva
 *
 */
@Document
public class NotificationChangeEvent extends AbstractDocument {

	private String userId;

	private String subject;

	private String subjectId;

	private NotificationChangeEventType changeType;

	

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the changeType
	 */
	public NotificationChangeEventType getChangeType() {
		return changeType;
	}

	/**
	 * @param changeType the changeType to set
	 */
	public void setChangeType(NotificationChangeEventType changeType) {
		this.changeType = changeType;
	}

	/**
	 * @return the subjectId
	 */
	public String getSubjectId() {
		return subjectId;
	}

	/**
	 * @param subjectId
	 *            the subjectId to set
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

}
