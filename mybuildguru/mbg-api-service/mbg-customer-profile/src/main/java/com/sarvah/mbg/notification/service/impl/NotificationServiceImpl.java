/**
 * 
 */
package com.sarvah.mbg.notification.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.notification.Notification;
import com.sarvah.mbg.domain.mongo.userprofile.notification.NotificationChangeEvent;
import com.sarvah.mbg.domain.mongo.userprofile.notification.NotificationChangeEventType;
import com.sarvah.mbg.domain.mongo.userprofile.notification.NotificationType;
import com.sarvah.mbg.notification.dao.mongo.NotificationChangeEventDAO;
import com.sarvah.mbg.notification.dao.mongo.NotificationDAO;
import com.sarvah.mbg.notification.service.NotificationService;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;

/**
 * @author sumanth
 *
 */
@Service
public class NotificationServiceImpl implements NotificationService {
	private static final Logger logger = LoggerFactory
			.getLogger(NotificationServiceImpl.class);

	@Autowired
	private NotificationDAO notificationDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	NotificationChangeEventDAO notificationChangeEventDAO;

	public void createNotification(String userId, String desc, String text,
			String subject) {
		// Creating order created notification
		Notification notification = new Notification();
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findById(userId);
			// User info
			notification.setUser(user);
			// description
			Description descr = new Description();
			descr.setLang("Eng");
			descr.setVal(desc);
			notification.setDesc(descr);
			// text
			notification.setText(text);
			// notificationType
			notification.setType(NotificationType.ALERTS);
			// subject
			notification.setNotificationSubject(subject);
			notificationDAO.save(notification);
		}
	}

	@Override
	public List<Notification> search(String userId, int page, int size,
			Sort sort) throws Exception {
		List<Notification> notificationList = null;
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findOne(userId);
			if (user != null) {
				notificationList = notificationDAO.findByUserId(userId,
						new PageRequest(page, size, sort)).getContent();
			} else {
				logger.info("User doesn't exists");
				throw new Exception("User doesn't exists");
			}
		} else {
			logger.info("User Id null");
			throw new Exception("User Id null");
		}
		return notificationList;
	}

	@Override
	public int count(String userId) throws Exception {
		int count = 0;
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findOne(userId);
			if (user != null) {
				count = notificationDAO.countByUserId(userId);
			} else {
				logger.info("User doesn't exists");
				throw new Exception("User doesn't exists");
			}
		} else {
			logger.info("User Id null");
			throw new Exception("User Id Null");
		}
		return count;
	}

	/**
	 * Method to add new Notification
	 * 
	 * @param userId
	 * @param notificationCreateRequestParam
	 * @return
	 * @throws MBGAppException
	 */

	@Override
	public Notification addNotification(String userId, String type,
			String text, String desc, String archieved, String acknowledged)
			throws Exception {

		Notification notification = new Notification();

		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findOne(userId);
			if (user != null) {
				if (StringUtils.isNotBlank(type)) {
					notification.setType(NotificationType.valueOf(type));
				}
				if (StringUtils.isNotBlank(text)) {
					notification.setText(text);
				}
				if (StringUtils.isNotBlank(desc)) {
					Description descr = new Description();
					descr.setLang("Eng");
					descr.setVal(desc);
					notification.setDesc(descr);
				}
				if (StringUtils.isNotBlank(archieved)) {
					notification.setArchived(Boolean.valueOf(archieved));
				}
				if (StringUtils.isNotBlank(acknowledged)) {
					notification.setAcknowledged(Boolean.valueOf(acknowledged));
				}
			} else {
				logger.info("User doesn't exists");
				throw new Exception("User doesn't exist");
			}
			notification.setUser(user);

			notificationDAO.insert(notification);

		}
		return notification;
	}

	/**
	 * Method to update Notification
	 * 
	 * @param userId
	 * @param notificationId
	 * @param notificationUpdateRequestParam
	 * @return
	 * @throws MBGAppException
	 * @throws Exception
	 */
	@Override
	public Notification editNotification(String userId, String notificationId,
			String type, String text, String desc, String archieved,
			String acknowledged) throws Exception {

		Notification notification = null;

		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findOne(userId);
			if (user != null) {
				if (StringUtils.isNotBlank(notificationId)) {
					notification = notificationDAO.findOne(notificationId);
					if (notification != null) {
						if (StringUtils.isNotBlank(type)) {
							notification
									.setType(NotificationType.valueOf(type));
						}
						if (StringUtils.isNotBlank(text)) {
							notification.setText(text);
						}
						if (StringUtils.isNotBlank(desc)) {
							Description descr = new Description();
							descr.setLang("Eng");
							descr.setVal(desc);
							notification.setDesc(descr);
						}
						if (StringUtils.isNotBlank(archieved)) {
							notification
									.setArchived(Boolean.valueOf(archieved));
						}
						if (StringUtils.isNotBlank(acknowledged)) {
							notification.setAcknowledged(Boolean
									.valueOf(acknowledged));
						}
					} else {
						logger.info("Notification doesn't exists");
						throw new Exception("Notification doesn't exist");
					}
				}
			} else {
				throw new Exception("User doesn't exist");
			}
			notification.setUser(user);

			notificationDAO.save(notification);

		}
		return notification;
	}

	/**
	 * Method to delete a notification
	 * 
	 * @param userId
	 * @param notificationId
	 * @return
	 * @throws MBGAppException
	 * @throws Exception
	 */
	@Override
	public String deleteNotification(String userId, String notificationId)
			throws Exception {

		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findOne(userId);
			if (user != null) {
				if (StringUtils.isNotBlank(notificationId)) {
					Notification notification = notificationDAO
							.findOne(notificationId);
					if (notification != null) {
						notificationDAO.delete(notification);
					} else {
						logger.info("Notification doesn't exists");
						throw new Exception("Notification doesn't exist");
					}
				}
			} else {
				logger.info("user doesn't exists");
				throw new Exception("User doesn't exist");
			}
		}

		return notificationId;
	}

	public void createNotificationChangeEvent(String userId, String subject,
			String subId, String changeEvent) {

		NotificationChangeEvent notificationChangeEvent = new NotificationChangeEvent();
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findById(userId);
			if (user != null) {
				notificationChangeEvent.setUserId(userId);
			}

			notificationChangeEvent.setSubject(subject);
			notificationChangeEvent.setSubjectId(subId);
			notificationChangeEvent.setChangeType(NotificationChangeEventType
					.valueOf(changeEvent));

			notificationChangeEventDAO.save(notificationChangeEvent);
		}
	}

}
