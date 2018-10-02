/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.SubCategory;

/**
 * @author Shivu
 *
 */
public interface StoreSubcategoryDAO extends
		MongoRepository<SubCategory, String> {

	Set<SubCategory> findByName(String subCategoryName);

	Set<SubCategory> findByNameAllIgnoreCase(String searchValue);

}
