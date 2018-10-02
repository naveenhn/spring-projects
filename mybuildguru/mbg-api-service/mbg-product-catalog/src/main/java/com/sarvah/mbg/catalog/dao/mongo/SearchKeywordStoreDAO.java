package com.sarvah.mbg.catalog.dao.mongo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.dashboard.SearchKeywordStore;

public interface SearchKeywordStoreDAO extends
		MongoRepository<SearchKeywordStore, String> {

	List<SearchKeywordStore> findByKeyword(String keywordStore);

	Page<SearchKeywordStore> findByKeyword(String keywordStore,
			Pageable pageable);

}
