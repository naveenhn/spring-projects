package com.sarvah.mbg.promotion.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.store.Store;
import com.sarvah.mbg.domain.mongo.userprofile.User;

public interface DashboardStoreDAO extends MongoRepository<Store, String>{

	List<Store> findByUser(User user);

}
