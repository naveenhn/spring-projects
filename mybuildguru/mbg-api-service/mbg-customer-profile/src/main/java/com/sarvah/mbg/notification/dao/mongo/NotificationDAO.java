package com.sarvah.mbg.notification.dao.mongo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.notification.Notification;

public interface NotificationDAO extends MongoRepository<Notification, String>{
/*
/v1/users/{uid}/notifications			GET
/v1/users/{uid}/notifications/count		GET
/v1/users/{uid}/notifications			POST
/v1/users/{uid}/notifications/{notid}	PUT
/v1/users/{uid}/notifications/{notid}	DELETE
 */
	List<Notification> findByUserId(String userId);
	Page<Notification> findByUserId(String userId, Pageable pageable);
	int countByUserId(String userId);		
}
