/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author shivu
 *
 */
public interface BrandDAO extends MongoRepository<ProductBrand, String> {
	List<ProductBrand> findAll();

	ProductBrand findByProvider(User user);

	ProductBrand findByName(String name);

	List<ProductBrand> findByProvider_Id(String uid);

	ProductBrand findByNameAllIgnoreCase(String brand);

	Set<ProductBrand> findBySubCategoryIdsIn(Set<String> subCategoryIds);

	ProductBrand findByNameIgnoreCase(String keyword);
}
