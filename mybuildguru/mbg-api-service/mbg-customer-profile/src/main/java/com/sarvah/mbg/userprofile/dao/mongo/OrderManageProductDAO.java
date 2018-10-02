package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;

public interface OrderManageProductDAO extends MongoRepository<Product, String> {

	long countByBrand(ProductBrand brand);

	List<Product> findByBrand(ProductBrand brand);

	long countByCreatedBy(String id);

	Product findByIdAndCreatedBy(String id, String userId);

	List<Product> findByIdIn(Set<String> productIds);

	Page<Product> findByIdIn(Set<String> productIds, Pageable pageable);

	List<Product> findByNameLikeIgnoreCase(String itemName);
}
