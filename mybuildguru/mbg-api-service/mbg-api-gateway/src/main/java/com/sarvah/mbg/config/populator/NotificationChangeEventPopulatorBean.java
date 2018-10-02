/**
 * 
 */
package com.sarvah.mbg.config.populator;

import com.sarvah.mbg.domain.mongo.userprofile.notification.NotificationChangeEvent;
import com.sarvah.mbg.domain.mongo.userprofile.notification.NotificationChangeEventType;
import com.sarvah.mbg.notification.dao.mongo.NotificationChangeEventDAO;

/**
 * @author Shiva
 *
 */
public class NotificationChangeEventPopulatorBean {

	NotificationChangeEventDAO notificationChangeEventDAO;

	public NotificationChangeEventPopulatorBean(
			NotificationChangeEventDAO notificationChangeEventDAO) {
		this.notificationChangeEventDAO = notificationChangeEventDAO;
	}

	public void initNotificationChangeEvent() {
		notificationChangeEventDAO.deleteAll();

//		NotificationChangeEvent notificationChangeEvent1 = new NotificationChangeEvent();
//		notificationChangeEvent1.setUserId("3");
//		notificationChangeEvent1.setSubject("Product");
//		notificationChangeEvent1.setSubjectId("1");
//		notificationChangeEvent1
//				.setChangeType(NotificationChangeEventType.UPDATE);
//
//		notificationChangeEventDAO.insert(notificationChangeEvent1);
//
//		NotificationChangeEvent notificationChangeEvent2 = new NotificationChangeEvent();
//		notificationChangeEvent2.setUserId("3");
//		notificationChangeEvent2.setSubject("Product");
//		notificationChangeEvent2.setSubjectId("1");
//		notificationChangeEvent2
//				.setChangeType(NotificationChangeEventType.UPDATE);
//
//		notificationChangeEventDAO.insert(notificationChangeEvent2);
//
//		NotificationChangeEvent notificationChangeEvent3 = new NotificationChangeEvent();
//		notificationChangeEvent3.setUserId("3");
//		notificationChangeEvent3.setSubject("Product");
//		notificationChangeEvent3.setSubjectId("1");
//		notificationChangeEvent3
//				.setChangeType(NotificationChangeEventType.UPDATE);
//
//		notificationChangeEventDAO.insert(notificationChangeEvent3);

	}
}
