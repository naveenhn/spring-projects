package com.sarvah.mbg.batch.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.notification.Notification;

public interface NotificationDAO extends MongoRepository<Notification, String> {

	Long countByText(String text);

	Long countByDesc_Val(String text);

	Long countByUserAndDesc_Val(User user, String desc);

	List<Notification> findByUserAndDesc_Val(User user, String desc);

	List<Notification> findByDesc_Val(String text27);
}
