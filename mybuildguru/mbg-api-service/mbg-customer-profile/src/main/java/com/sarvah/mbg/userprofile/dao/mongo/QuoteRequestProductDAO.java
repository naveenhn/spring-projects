/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.QuoteRequestProduct;
import com.sarvah.mbg.domain.mongo.userprofile.SellerQuoteProductPricing;

/**
 * @author Shivu
 *
 */
public interface QuoteRequestProductDAO extends
		MongoRepository<QuoteRequestProduct, String> {

	Set<QuoteRequestProduct> findByQuoteProductPricingsIn(
			Set<SellerQuoteProductPricing> sellerQuoteProductPricings);

}
