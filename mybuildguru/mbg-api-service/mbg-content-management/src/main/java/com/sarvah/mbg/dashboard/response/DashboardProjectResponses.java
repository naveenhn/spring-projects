package com.sarvah.mbg.dashboard.response;

import java.util.List;

import com.sarvah.mbg.domain.mongo.aceproject.Bid;

public class DashboardProjectResponses {

	private List<Bid> bids;

	// private List<Project> projects;
	// private Project project;

	public List<Bid> getBids() {
		return bids;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}

}
