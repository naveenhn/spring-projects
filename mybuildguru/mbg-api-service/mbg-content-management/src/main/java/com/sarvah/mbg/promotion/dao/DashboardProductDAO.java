package com.sarvah.mbg.promotion.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.ProductStatus;

/**
 * 
 * @author Raju
 *
 */
public interface DashboardProductDAO extends MongoRepository<Product, String> {

	long countByStatus(String status);

	long findByBrand_Provider_IdAndStatus(String uid, String status);

	long countByBrand(ProductBrand brand);

	long countBySkuIdAndStatus(String skuId, String productStatus);

	List<Product> findByBrand(ProductBrand brand);

	Product findByIdAndBrand(String id, ProductBrand brand);

	List<Product> findByCreatedBy(String uid);

	Page<Product> findByStatus(ProductStatus inMbg, Pageable pagable);

	List<Product> findByBrandAndStatus(ProductBrand brand, String string);

	long countByBrandAndStatus(ProductBrand brand, String string);
}
