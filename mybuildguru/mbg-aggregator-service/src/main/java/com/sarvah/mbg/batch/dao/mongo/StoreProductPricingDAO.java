/**
 * 
 */
package com.sarvah.mbg.batch.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.store.StoreProductPricing;

/**
 * @author shivu
 *
 */
public interface StoreProductPricingDAO extends
		MongoRepository<StoreProductPricing, String> {

	StoreProductPricing findByProductIdAndStoreId(String id, String id2);
}
