/**
 * 
 */
package com.sarvah.mbg.notification.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.notification.NotificationChangeEvent;

/**
 * @author Shiva
 *
 */
public interface NotificationChangeEventDAO extends
		MongoRepository<NotificationChangeEvent, String> {

}
