package com.sarvah.mbg.promotion.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.dashboard.ProductNotOnBoardedNameStore;

public interface DashBoardProductNotOnBoardedNameStoreDAO extends
		MongoRepository<ProductNotOnBoardedNameStore, String> {

	Page<ProductNotOnBoardedNameStore> findByOrderCountGreaterThan(long i,
			Pageable pageable);

}
