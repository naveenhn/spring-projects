/**
 * 
 */
package com.sarvah.mbg.catalog.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.QuoteRequestProduct;

/**
 * @author Shivu
 *
 */
public interface ProductSellerRequestQuoteDAO extends
		MongoRepository<QuoteRequestProduct, String> {

}
