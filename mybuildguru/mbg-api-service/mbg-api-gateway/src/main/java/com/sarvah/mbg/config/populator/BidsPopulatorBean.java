package com.sarvah.mbg.config.populator;

import java.util.ArrayList;
import java.util.List;

import com.sarvah.mbg.domain.mongo.aceproject.Bid;
import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.userprofile.dao.mongo.UserBidDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserProjectDAO;

public class BidsPopulatorBean {
	UserProjectDAO projectDAO;
	UserDAO userDAO;
	UserBidDAO bidDAO;

	public BidsPopulatorBean(UserBidDAO bidDAO, UserDAO userDAO,
			UserProjectDAO projectDAO) {
		this.bidDAO = bidDAO;
		this.userDAO = userDAO;
		this.projectDAO = projectDAO;
	}

	public void initBid() {
		bidDAO.deleteAll();
//		User user = userDAO.findByUsername("admin@godrejs.com");
//		if (user != null) {
//			List<Project> projects = projectDAO.findByNameAndUser("Apartment",
//					user);
//			Project project = !projects.isEmpty() ? projects.get(0) : null;
//			if (project != null) {
//				// Bid object1
//				Bid bid = new Bid();
//				Description desc = new Description("eng",
//						"I am interested in this Apartment project");
//				bid.setDesc(desc);
//				Integer quoteint = new Integer(100000);
//				bid.setQuoteAmount(quoteint);
//				List<String> ids = new ArrayList<>();
//				ids.add("1");
//				ids.add("2");
//				bid.setAssetIds(ids);
//				bid.setUser(user);
//				bid.setProject(project);
//				bidDAO.insert(bid);
//			}
//		}
//
//		User user1 = userDAO.findByUsername("admin@godrejes.com");
//		if (user1 != null) {
//			List<Project> projects1 = projectDAO.findByNameAndUser("Villa",
//					user1);
//			Project project1 = !projects1.isEmpty() ? projects1.get(0) : null;
//			if (project1 != null) {
//				// Bid object2
//				Bid bid1 = new Bid();
//				Description desc1 = new Description("eng",
//						"I am interseted in this Villa project");
//				bid1.setDesc(desc1);
//				Integer quoteint1 = new Integer(200000);
//				bid1.setQuoteAmount(quoteint1);
//				List<String> ids1 = new ArrayList<>();
//				ids1.add("6");
//				ids1.add("7");
//				bid1.setAssetIds(ids1);
//				bid1.setUser(user1);
//				bid1.setProject(project1);
//				bidDAO.insert(bid1);
//			}
//		}
//
//		User user2 = userDAO.findByUsername("admin@godrejs.com");
//		if (user2 != null) {
//			List<Project> projects2 = projectDAO.findByNameAndUser("Independent house",
//					user2);
//			Project project2 = !projects2.isEmpty() ? projects2.get(0) : null;
//			if (project2 != null) {
//				// Bid object2
//				Bid bid2 = new Bid();
//				Description desc2 = new Description("eng",
//						"I am interseted in this Villa project");
//				bid2.setDesc(desc2);
//				Integer quoteint1 = new Integer(200000);
//				bid2.setQuoteAmount(quoteint1);
//				List<String> ids2 = new ArrayList<>();
//				ids2.add("6");
//				ids2.add("7");
//				bid2.setAssetIds(ids2);
//				bid2.setUser(user2);
//				bid2.setProject(project2);
//				bidDAO.insert(bid2);
//			}
//		}
	}
}
