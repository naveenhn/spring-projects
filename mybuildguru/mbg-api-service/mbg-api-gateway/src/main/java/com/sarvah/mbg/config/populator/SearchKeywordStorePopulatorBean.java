package com.sarvah.mbg.config.populator;

import com.sarvah.mbg.catalog.dao.mongo.SearchKeywordStoreDAO;
import com.sarvah.mbg.domain.mongo.dashboard.SearchKeywordStore;

public class SearchKeywordStorePopulatorBean {
	SearchKeywordStoreDAO searchKeywordStoreDAO;

	public SearchKeywordStorePopulatorBean(SearchKeywordStoreDAO searchKeywordStoreDAO) {
		this.searchKeywordStoreDAO = searchKeywordStoreDAO;
	}

	public void initSearchValues() {
		searchKeywordStoreDAO.deleteAll();
		
//		SearchKeywordStore searchValues = new SearchKeywordStore();
//		searchValues.setKeyword("Jaquer");
//		searchKeywordStoreDAO.insert(searchValues);
//		
//		SearchKeywordStore searchValues1 = new SearchKeywordStore();
//		searchValues1.setKeyword("Jaquer");
//		searchKeywordStoreDAO.insert(searchValues1);
//		
//		SearchKeywordStore searchValues2 = new SearchKeywordStore();
//		searchValues2.setKeyword("Jaquer");
//		searchKeywordStoreDAO.insert(searchValues2);
//		
//
//		SearchKeywordStore searchValues3 = new SearchKeywordStore();
//		searchValues3.setKeyword("Cera");
//		searchKeywordStoreDAO.insert(searchValues3);
//		
//
//		SearchKeywordStore searchValues4 = new SearchKeywordStore();
//		searchValues4.setKeyword("Hindware");
//		searchKeywordStoreDAO.insert(searchValues4);
//		
//		
//		SearchKeywordStore searchValues5 = new SearchKeywordStore();
//		searchValues5.setKeyword("Hindware");
//		searchKeywordStoreDAO.insert(searchValues5);
//		
//		SearchKeywordStore searchValues6 = new SearchKeywordStore();
//		searchValues6.setKeyword("Hindware");
//		searchKeywordStoreDAO.insert(searchValues6);
//		
//		
//
//		SearchKeywordStore searchValues7 = new SearchKeywordStore();
//		searchValues7.setKeyword("Toto");
//		searchKeywordStoreDAO.insert(searchValues7);
//		
//		SearchKeywordStore searchValues8 = new SearchKeywordStore();
//		searchValues8.setKeyword("Jaquer");
//		searchKeywordStoreDAO.insert(searchValues8);
//		

	}
}
