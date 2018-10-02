package com.sarvah.mbg.rest.dashboard.response;

import java.util.List;

import com.sarvah.mbg.rest.model.AbstractCollectionResponse;

public class DashboardListResponse<T> extends AbstractCollectionResponse{
	private List<T> resultList;
	
	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
}
