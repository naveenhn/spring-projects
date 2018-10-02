package com.sarvah.mbg.config.populator;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.PageRequest;

import com.sarvah.mbg.catalog.dao.mongo.ProductDAO;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.WishList;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;
import com.sarvah.mbg.userprofile.dao.mongo.WishListDAO;

public class WishListPopulatorBean {
	WishListDAO wishListDAO;
	UserDAO userDAO;
	ProductDAO productDAO;

	public WishListPopulatorBean(WishListDAO wishListDAO, UserDAO userDAO,
			ProductDAO productDAO) {
		this.wishListDAO = wishListDAO;
		this.userDAO = userDAO;
		this.productDAO = productDAO;
	}

	public void initWistList() {
		wishListDAO.deleteAll();
//		List<User> user = userDAO.findByUsername("admin@hindware.com",new PageRequest(0,10));
//		//wishList1
//		WishList wishList = new WishList();
//		if (user != null && user.size() != 0) {
//			wishList.setUser(user.get(0));
//			List<Product> products = productDAO.findByNameAllIgnoreCase("Jaguar - XYZ");
//			Set<String> setId = new LinkedHashSet<>();
//			for (Product product : products) {
//				setId.add(product.getId());
//			}
//			wishList.setProductIds(setId);
//			wishListDAO.insert(wishList);
//			
//			  //WishList2
//	        List<User> user1 = userDAO.findByUsername("admin@jaquar.com",new PageRequest(0,10));
//	        WishList wishList1 = new WishList();
//	        if (user1 != null && user1.size() != 0) {
//	            wishList1.setUser(user1.get(0));
//	            List<Product> products1 = productDAO.findByNameAllIgnoreCase("Jaguar - XYZ");
//	            Set<String> setId1 = new LinkedHashSet<>();
//	            for (Product product : products1) {
//	                setId1.add(product.getId());
//	            }
//	            wishList1.setProductIds(setId1);
//	            wishListDAO.insert(wishList1);
//
//	        }
//	        
//	        //WishList3
//	                List<User> user2= userDAO.findByUsername("admin@cera.com",new PageRequest(0,10));
//	                WishList wishList2 = new WishList();
//	                if (user2 != null && user2.size() != 0) {
//	                    wishList2.setUser(user2.get(0));
//	                    List<Product> products2 = productDAO.findByNameAllIgnoreCase("Jaguar - XYZ");
//	                    Set<String> setId2 = new LinkedHashSet<>();
//	                    for (Product product : products2) {
//	                        setId2.add(product.getId());
//	                    }
//	                    wishList2.setProductIds(setId2);
//	                    wishListDAO.insert(wishList2);
//
//	                }
//	                
//	                //WishList4
//	                List<User> user3= userDAO.findByUsername("admin@havells.com",new PageRequest(0,10));
//	                WishList wishList3 = new WishList();
//	                if (user3 != null && user3.size() != 0) {
//	                    wishList3.setUser(user3.get(0));
//	                    List<Product> products3 = productDAO.findByNameAllIgnoreCase("Jaguar - XYZ");
//	                    Set<String> setId3 = new LinkedHashSet<>();
//	                    for (Product product :products3 ) {
//	                        setId3.add(product.getId());
//	                    }
//	                    wishList3.setProductIds(setId3);
//	                    wishListDAO.insert(wishList3);
//
//	                }
//	                
//	                //WishList5
//	                List<User> user4= userDAO.findByUsername("admin@godrej.com",new PageRequest(0,10));
//	                WishList wishList4 = new WishList();
//	                if (user4 != null && user4.size() != 0) {
//	                    wishList4.setUser(user4.get(0));
//	                    List<Product> products4 = productDAO.findByNameAllIgnoreCase("Jaguar - XYZ");
//	                    Set<String> setId4 = new LinkedHashSet<>();
//	                    for (Product product :products4 ) {
//	                        setId4.add(product.getId());
//	                    }
//	                    wishList4.setProductIds(setId4);
//	                    wishListDAO.insert(wishList4);
//
//	                }

//		}

	}
}
