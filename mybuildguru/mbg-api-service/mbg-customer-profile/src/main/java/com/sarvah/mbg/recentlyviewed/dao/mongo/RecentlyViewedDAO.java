package com.sarvah.mbg.recentlyviewed.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.RecentlyViewed;

public interface RecentlyViewedDAO extends
		MongoRepository<RecentlyViewed, String> {

	RecentlyViewed findByUser_Id(String userId);

	String deleteByProductIds(String id);
}