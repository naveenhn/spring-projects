package com.sarvah.mbg.userprofile.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.WishList;

public interface WishListDAO extends MongoRepository<WishList, String> {

	WishList findByUser_Id(String uid);

	String deleteByProductIds();

	WishList findByUser(User user);

}