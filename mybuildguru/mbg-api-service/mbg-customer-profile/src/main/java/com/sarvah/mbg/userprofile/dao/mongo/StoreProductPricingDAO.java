/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.store.StoreProductPricing;

/**
 * @author shivu
 *
 */
public interface StoreProductPricingDAO extends
		MongoRepository<StoreProductPricing, String> {

	StoreProductPricing findByStoreIdLike(String storeId);

	StoreProductPricing findByProductIdAndStoreId(String pid, String storeId);

	StoreProductPricing findByProductIdAndStoreIdAndShipping_LocalDeliveryIsTrue(
			String pid, String storeId);

	List<StoreProductPricing> findByProductIdAndShipping_ZonalDeliveryIsTrue(
			String pid);

	List<StoreProductPricing> findByProductIdAndShipping_NationalDeliveryIsTrue(
			String pid);

	StoreProductPricing findByStoreId(String storeId);

	List<StoreProductPricing> findByProductId(String productId);

	StoreProductPricing findByStoreIdAndProductId(String id, String id2);
}
