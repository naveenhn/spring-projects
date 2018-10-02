/**
 * 
 */
package com.sarvah.mbg.catalog.dao.mongo;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.SubCategory;

/**
 * @author naveen
 *
 */
public interface SubCategoryDAO extends MongoRepository<SubCategory, String> {

	List<SubCategory> findByName(String name);

	Set<SubCategory> findByCategory(String category);

	SubCategory findById(String subcatid);
}
