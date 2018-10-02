/**
 * 
 */
package com.sarvah.mbg.dummy.service;

import java.util.ArrayList;
import java.util.List;

import com.sarvah.mbg.domain.mongo.aceproject.Bid;
import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author naveen
 *
 */

public class DummyBidService {

	public List<Bid> getBidsForProject(String projectId) {
		List<Bid> bids = new ArrayList<>();

		Bid bid = new Bid();
		bid.setId("1");
		bid.setDesc(new Description("en", "bid1"));
		bid.setQuoteAmount(50000);
		
		Project project1=new Project();
		project1.setId("1");
		project1.setName("MyBuildGuru");
		bid.setProject(project1);
		
		User user=new User();
		user.setId("u1");
		user.setUsername("Ramesh");
		bid.setUser(user);

		bids.add(bid);
		return bids;

	}

}
