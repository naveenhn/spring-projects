package com.sarvah.mbg.rest.dashboard.response;

import java.util.Map;

import com.sarvah.mbg.rest.model.AbstractCollectionResponse;

public class DashboardMapResponse<K,V> extends AbstractCollectionResponse{
	private Map<K,V> resultMap;

	public Map<K,V> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<K,V> resultMap) {
		this.resultMap = resultMap;
	}	
}
