package com.sarvah.mbg.privilege.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.marketing.Promotion;

/**
 * @author Shiva
 *
 */
public interface PrivilegePromotionDAO extends
		MongoRepository<Promotion, String> {

	Long countByDealer(String uid);

}
