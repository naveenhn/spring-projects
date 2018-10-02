package com.sarvah.mbg.promotion.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.marketing.Banner;
import com.sarvah.mbg.domain.mongo.userprofile.User;

public interface DashboardBannerDAO extends MongoRepository<Banner, String>{
	List<Banner> findByUser(User user);

}
