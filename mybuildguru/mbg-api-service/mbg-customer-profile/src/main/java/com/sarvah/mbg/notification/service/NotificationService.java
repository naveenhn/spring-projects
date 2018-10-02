/**
 * 
 */
package com.sarvah.mbg.notification.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.sarvah.mbg.domain.mongo.userprofile.notification.Notification;

/**
 * @author sumanth
 *
 */
public interface NotificationService {
	/**
	 * 
	 * @param userId
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	List<Notification> search(String userId, int page, int size, Sort sort)
			throws Exception;

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	int count(String userId) throws Exception;

	/**
	 * 
	 * @param userId
	 * @param notificationId
	 * @return
	 * @throws Exception
	 */
	String deleteNotification(String userId, String notificationId)
			throws Exception;

	/**
	 * 
	 * @param userId
	 * @param notificationId
	 * @param type
	 * @param text
	 * @param desc
	 * @param archieved
	 * @param acknowledged
	 * @return
	 * @throws Exception
	 */
	Notification editNotification(String userId, String notificationId,
			String type, String text, String desc, String archieved,
			String acknowledged) throws Exception;

	/**
	 * 
	 * @param userId
	 * @param type
	 * @param text
	 * @param desc
	 * @param archieved
	 * @param acknowledged
	 * @return
	 * @throws Exception
	 */
	Notification addNotification(String userId, String type, String text,
			String desc, String archieved, String acknowledged)
			throws Exception;

}
