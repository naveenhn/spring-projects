package com.sarvah.mbg.rest.notification.response;

import java.util.List;

import com.sarvah.mbg.domain.mongo.userprofile.notification.Notification;
import com.sarvah.mbg.rest.model.AbstractCollectionResponse;

public class NotificationResponse extends AbstractCollectionResponse{
	private List<Notification> notifications;

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
}
