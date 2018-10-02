/**
 * 
 */
package com.sarvah.mbg.reviews.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.Product;

/**
 * @author shivu
 *
 */
public interface CommentProductDAO extends MongoRepository<Product,String>{

}
