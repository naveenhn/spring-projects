/**
 * 
 */
package com.sarvah.mbg.config.populator;

import com.sarvah.mbg.catalog.dao.mongo.ProductDAO;
import com.sarvah.mbg.store.dao.mongo.StoreDAO;
import com.sarvah.mbg.userprofile.dao.mongo.BannerUITemplateDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;

/**
 * @author shivu s
 *
 */
public class StorePopulatorBean {
	StoreDAO productStoreDAO;
	UserDAO userDAO;
	BannerUITemplateDAO uiTemplateDAO;
	ProductDAO productDAO;

	StorePopulatorBean(StoreDAO productStoreDAO, UserDAO userDAO,
			BannerUITemplateDAO uiTemplateDAO, ProductDAO productDAO) {
		this.productStoreDAO = productStoreDAO;
		this.userDAO = userDAO;
		this.uiTemplateDAO = uiTemplateDAO;
		this.productDAO = productDAO;
	}

	public void initStore() {
		productStoreDAO.deleteAll();

		// store1
//		Store store1 = new Store();
//		store1.setStorename("Praveen Store");
//
//		Description desc1 = new Description();
//		desc1.setLang("eng");
//		desc1.setVal("Praveen Store information");
//		store1.setDesc(desc1);
//
//		List<User> user1 = userDAO.findByUsername("admin@jaquar.com",
//				new PageRequest(0, 10));
//		store1.setUser(user1.get(0));
//
//		UITemplate uiTemplate1 = uiTemplateDAO
//				.findByName("Ganesh Chaturthi Offer");
//		store1.setTemplate(uiTemplate1);
//
//		List<Product> productList1 = new ArrayList<>();
//
//		List<Product> product1 = productDAO
//				.findByNameAllIgnoreCase("Jaguar - XYZ");
//		productList1.addAll(product1);
//		List<Product> product11 = productDAO
//				.findByNameAllIgnoreCase("Cera - XYZ");
//		productList1.addAll(product11);
//		List<Product> product111 = productDAO
//				.findByNameAllIgnoreCase("Toto - MNO");
//		productList1.addAll(product111);
//		store1.setProducts(productList1);
//		productStoreDAO.insert(store1);

		// store2
//		Store store2 = new Store();
//		store2.setStorename("Kiran Store");
//
//		Description desc2 = new Description();
//		desc2.setLang("eng");
//		desc2.setVal("Kiran Store information");
//		store2.setDesc(desc2);
//		List<User> user2 = userDAO.findByUsername("admin@cera.com",
//				new PageRequest(0, 10));
//
//		store2.setUser(user2.get(0));
//
//		UITemplate uiTemplate2 = uiTemplateDAO
//				.findByName("Dasara Chaturthi Offer");
//
//		store2.setTemplate(uiTemplate2);
//
//		List<Product> productList2 = new ArrayList<>();
//
//		List<Product> product2 = productDAO
//				.findByNameAllIgnoreCase("Cera - ABC");
//		productList2.addAll(product2);
//		List<Product> product22 = productDAO
//				.findByNameAllIgnoreCase("Hindware - XYZ");
//		productList2.addAll(product22);
//		List<Product> product222 = productDAO
//				.findByNameAllIgnoreCase("Hindware - PQR");
//		productList2.addAll(product222);
//		store2.setProducts(productList2);
//		productStoreDAO.insert(store2);

		// store3
//		Store store3 = new Store();
//		store3.setStorename("Naveen Store");
//
//		Description desc3 = new Description();
//		desc3.setLang("eng");
//		desc3.setVal("Naveen Store information");
//		store3.setDesc(desc3);
//		List<User> user3 = userDAO.findByUsername("admin@hindware.com",
//				new PageRequest(0, 10));
//
//		store3.setUser(user3.get(0));
//
//		UITemplate uiTemplate3 = uiTemplateDAO
//				.findByName("new year Offer");
//
//		store3.setTemplate(uiTemplate3);
//
//		List<Product> productList3 = new ArrayList<>();
//
//		List<Product> product3 = productDAO
//				.findByNameAllIgnoreCase("Jaguar - XYZ");
//		productList3.addAll(product3);
//		List<Product> product33 = productDAO
//				.findByNameAllIgnoreCase("Hindware - XYZ");
//		productList3.addAll(product33);
//		List<Product> product333 = productDAO
//				.findByNameAllIgnoreCase("Toto - MNO");
//		productList3.addAll(product333);
//		store3.setProducts(productList3);
//		productStoreDAO.insert(store3);

		// store4
//		Store store4 = new Store();
//		store4.setStorename("Sangeetha Store");
//
//		Description desc4 = new Description();
//		desc4.setLang("eng");
//		desc4.setVal("Naveen Store information");
//		store4.setDesc(desc4);
//		List<User> user4 = userDAO.findByUsername("admin@sangeetha.com",
//				new PageRequest(0, 10));
//
//		store4.setUser(user4.get(0));
//
//		UITemplate uiTemplate4 = uiTemplateDAO
//				.findByName("Dasara Chaturthi Offer");
//
//		store4.setTemplate(uiTemplate4);
//
//		List<Product> productList4 = new ArrayList<>();
//
//		List<Product> product4 = productDAO
//				.findByNameAllIgnoreCase("Cera - ABC");
//		productList4.addAll(product4);
//		List<Product> product44 = productDAO
//				.findByNameAllIgnoreCase("Cera - XYZ");
//		productList4.addAll(product44);
//		List<Product> product444 = productDAO
//				.findByNameAllIgnoreCase("Hindware - PQR");
//		productList4.addAll(product444);
//		store4.setProducts(productList4);
//		productStoreDAO.insert(store4);
	}
}
