package com.sarvah.mbg.promotion.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.marketing.Promotion;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * 
 * @author Raju
 *
 */
public interface DashboardPromotionDAO extends
		MongoRepository<Promotion, String> {

	Promotion findById(String trandata);

	List<Promotion> findByDealer(User user);
	

}
