package com.sarvah.mbg.rest.store.response;

import java.util.List;

import com.sarvah.mbg.domain.mongo.store.Store;
import com.sarvah.mbg.rest.model.AbstractCollectionResponse;

public class StoreResponse extends AbstractCollectionResponse{
	private List<Store> stores;

	public List<Store> getStores() {
		return stores;
	}

	public void setStores(List<Store> stores) {
		this.stores = stores;
	}

}
