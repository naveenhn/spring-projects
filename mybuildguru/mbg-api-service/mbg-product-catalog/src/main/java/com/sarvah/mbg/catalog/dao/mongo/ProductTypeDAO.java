/**
 * 
 */
package com.sarvah.mbg.catalog.dao.mongo;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;

/**
 * @author naveen
 *
 */
public interface ProductTypeDAO extends MongoRepository<ProductType, String> {
	List<ProductType> findByName(String name);

	Set<ProductType> findBySubCategory(SubCategory subCategory);
}
