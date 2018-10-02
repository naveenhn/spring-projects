/**
 * 
 */
package com.sarvah.mbg.store.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.Product;

/**
 * @author Shiva
 *
 */
public interface StoreProductDAO extends MongoRepository<Product, String>{
	

}
