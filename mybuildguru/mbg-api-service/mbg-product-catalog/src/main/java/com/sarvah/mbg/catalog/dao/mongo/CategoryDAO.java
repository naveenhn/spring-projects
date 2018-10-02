/**
 * 
 */
package com.sarvah.mbg.catalog.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.Category;

/**
 * @author naveen
 *
 */
public interface CategoryDAO extends MongoRepository<Category, String> {

	List<Category> findByNameAllIgnoreCase(String name);

	List<Category> findAll();

	Category findById(String catid);

	long deleteById(String catid);

	long countByName(String name);
}
