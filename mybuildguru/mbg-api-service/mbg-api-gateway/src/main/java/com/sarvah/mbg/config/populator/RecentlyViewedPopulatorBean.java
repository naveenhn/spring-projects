package com.sarvah.mbg.config.populator;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.PageRequest;

import com.sarvah.mbg.catalog.dao.mongo.ProductDAO;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.userprofile.RecentlyViewed;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.recentlyviewed.dao.mongo.RecentlyViewedDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;

public class RecentlyViewedPopulatorBean {
	RecentlyViewedDAO recentlyViewedDAO;
	UserDAO userDAO;
	ProductDAO productDAO;
	public RecentlyViewedPopulatorBean(RecentlyViewedDAO recentlyViewedDAO,UserDAO userDAO,ProductDAO productDAO)
	{
		this.recentlyViewedDAO=recentlyViewedDAO;
		this.userDAO=userDAO;
		this.productDAO=productDAO;
	}
	public void initRecentlyViewed() {
		recentlyViewedDAO.deleteAll();

//		List<User> user = userDAO.findByUsername("admin@hindware.com",new PageRequest(0,10));
//		RecentlyViewed rv = new RecentlyViewed();
//
//		if (user != null && user.size() != 0) {
//			rv.setUser(user.get(0));
//			List<Product> products = productDAO.findByNameAllIgnoreCase("Jaguar - XYZ");
//			Set<String> setId = new LinkedHashSet<>();
//			for (Product product : products) {
//				setId.add(product.getId());
//			}
//			rv.setProductIds(setId);
//			recentlyViewedDAO.insert(rv);
//
//		}

	}

}
