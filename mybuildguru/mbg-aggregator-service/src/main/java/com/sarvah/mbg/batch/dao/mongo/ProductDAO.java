/**
 * 
 */
package com.sarvah.mbg.batch.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductStatus;

import java.lang.String;
import java.util.List;

/**
 * @author Shiva
 *
 */
public interface ProductDAO extends MongoRepository<Product, String>{
	
	List<Product> findByCreatedBy(String createdby);

	List<Product> findByStatus(ProductStatus inBuildonn);

}
