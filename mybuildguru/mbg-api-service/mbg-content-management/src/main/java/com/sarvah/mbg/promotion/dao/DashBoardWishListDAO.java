package com.sarvah.mbg.promotion.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.WishList;

public interface DashBoardWishListDAO extends MongoRepository<WishList, String> {
	
	long countByProductIds(String id);

}
