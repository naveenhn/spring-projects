package com.sarvah.mbg.promotion.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;

public interface DashboardBrandDAO extends MongoRepository<ProductBrand, String> {

	
	List<ProductBrand> findByProvider(String uid);
	
	List<ProductBrand> findByProvider_Id(String uid);
	ProductBrand findByName(String name);
}
