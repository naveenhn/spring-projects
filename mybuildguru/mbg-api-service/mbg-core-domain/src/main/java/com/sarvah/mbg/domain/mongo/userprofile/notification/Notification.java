/**
 * 
 */
package com.sarvah.mbg.domain.mongo.userprofile.notification;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sarvah.mbg.domain.mongo.common.AbstractDocument;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author naveen
 *
 */
@Document
public class Notification extends AbstractDocument {

	private NotificationType type;

	@JsonIgnore
	@DBRef
	@NotNull(message = "Notification user cannot be null")
	private User user;

	@NotNull(message = "Notification desc cannot be null")
	private Description desc;

	@NotNull(message = "Notification text cannot be null")
	private String text;

	private String notificationSubject;

	/**
	 * @return the notificationSubject
	 */
	public String getNotificationSubject() {
		return notificationSubject;
	}

	/**
	 * @param notificationSubject
	 *            the notificationSubject to set
	 */
	public void setNotificationSubject(String notificationSubject) {
		this.notificationSubject = notificationSubject;
	}

	// has cleared the notification
	private boolean archived;

	// seen the notification
	private boolean acknowledged;

	/**
	 * @return the type
	 */
	public NotificationType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(NotificationType type) {
		this.type = type;
	}

	/**
	 * @return the desc
	 */
	public Description getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(Description desc) {
		this.desc = desc;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the archived
	 */
	public boolean isArchived() {
		return archived;
	}

	/**
	 * @param archived
	 *            the archived to set
	 */
	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	/**
	 * @return the acknowledged
	 */
	public boolean isAcknowledged() {
		return acknowledged;
	}

	/**
	 * @param acknowledged
	 *            the acknowledged to set
	 */
	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
