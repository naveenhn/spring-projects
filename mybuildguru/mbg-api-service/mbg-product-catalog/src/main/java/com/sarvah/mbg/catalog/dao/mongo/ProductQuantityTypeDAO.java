package com.sarvah.mbg.catalog.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.ProductQuantityType;

public interface ProductQuantityTypeDAO extends
		MongoRepository<ProductQuantityType, String> {

	ProductQuantityType findByNameIgnoreCase(String name);

}
