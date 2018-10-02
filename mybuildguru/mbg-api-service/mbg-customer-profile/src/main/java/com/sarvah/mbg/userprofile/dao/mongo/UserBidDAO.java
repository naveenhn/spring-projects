package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.aceproject.Bid;
import com.sarvah.mbg.domain.mongo.userprofile.User;

public interface UserBidDAO extends MongoRepository<Bid, String> {
	List<Bid> findByUser(User user);

	Long countByUser(User user);
}
