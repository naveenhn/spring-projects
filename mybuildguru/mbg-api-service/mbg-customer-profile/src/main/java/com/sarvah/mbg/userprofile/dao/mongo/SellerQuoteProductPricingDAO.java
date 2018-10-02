/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.SellerQuoteProductPricing;

/**
 * @author Shivu
 *
 */
public interface SellerQuoteProductPricingDAO extends
		MongoRepository<SellerQuoteProductPricing, String> {

	Set<SellerQuoteProductPricing> findBySeller_Id(String dealerId);

}
