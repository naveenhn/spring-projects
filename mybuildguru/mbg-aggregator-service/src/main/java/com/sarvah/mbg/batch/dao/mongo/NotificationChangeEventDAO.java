package com.sarvah.mbg.batch.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;




import com.sarvah.mbg.domain.mongo.userprofile.notification.NotificationChangeEvent;

/**
 * 
 * @author Raju
 *
 */
public interface NotificationChangeEventDAO extends
		MongoRepository<NotificationChangeEvent, String> {
	
	List<NotificationChangeEvent> findByUserId(String userId);

}
