/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.marketing.Promotion;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author shivu
 *
 */
public interface ProductPromotionDAO extends MongoRepository<Promotion, String> {
	List<Promotion> findByDealer(User user);
	long countByDealer(User user);
}
