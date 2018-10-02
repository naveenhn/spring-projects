/**
 * 
 */
package com.sarvah.mbg.product.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.Product;

/**
 * @author Shiva
 *
 */
public interface ProviderProductDAO extends MongoRepository<Product, String> {

	long countByBrand_Provider_Id(String uid);
}
