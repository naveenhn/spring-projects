/**
 * 
 */
package com.sarvah.mbg.catalog.dao.mongo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductPricing;

/**
 * @author Shivu
 *
 */
public interface ProductPricingDAO extends
		MongoRepository<ProductPricing, String> {

	ProductPricing findById(String productPricingId);

	List<ProductPricing> findByProductIn(List<Product> products);

	ProductPricing findByProduct_Id(String id);

	Page<ProductPricing> findByProductIn(List<Product> products,
			Pageable pageable);

}
