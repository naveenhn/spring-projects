/**
 * 
 */
package com.sarvah.mbg.batch.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.store.Store;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author Shiva
 *
 */
public interface StoreDAO extends MongoRepository<Store, String> {

	Store findByUserId(String dealerId);

	Store findByUser(User dealer);

	List<Store> findByProducts(Product product);

}
