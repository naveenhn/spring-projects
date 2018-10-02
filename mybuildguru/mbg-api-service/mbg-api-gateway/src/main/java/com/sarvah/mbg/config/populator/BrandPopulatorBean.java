/**
 * 
 */
package com.sarvah.mbg.config.populator;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.userprofile.dao.mongo.BrandDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;

/**
 * @author shivu
 *
 */
public class BrandPopulatorBean {
	BrandDAO brandDAO;
	UserDAO userDAO;

	public BrandPopulatorBean(BrandDAO brandDAO, UserDAO userDAO) {
		this.brandDAO = brandDAO;
		this.userDAO = userDAO;
	}

	public void initBrands() {
		brandDAO.deleteAll();

		// Jaquar
		ProductBrand productBrand1 = new ProductBrand();
		List<User> users1 = userDAO.findByUsername("admin@jaquar.com",
				new PageRequest(0, 10));
		User provider1 = users1.get(0);
		productBrand1.setName("Jaquar");
		Description desc1 = new Description();
		desc1.setLang("eng");
		desc1.setVal("Jaquar brand is good");
		productBrand1.setDesc(desc1);
		productBrand1.setProvider(provider1);
		brandDAO.insert(productBrand1);

		// Cera
		ProductBrand productBrand2 = new ProductBrand();
		List<User> users2 = userDAO.findByUsername("admin@cera.com",
				new PageRequest(0, 10));
		User provider2 = users2.get(0);
		productBrand2.setName("Cera");
		Description desc2 = new Description();
		desc2.setLang("eng");
		desc2.setVal("Cera brand is good");
		productBrand2.setDesc(desc2);
		productBrand2.setProvider(provider2);
		brandDAO.insert(productBrand2);

		// Hindware
		ProductBrand productBrand3 = new ProductBrand();
		List<User> users3 = userDAO.findByUsername("admin@hindware.com",
				new PageRequest(0, 10));
		User provider3 = users3.get(0);
		productBrand3.setName("Hindware");
		Description desc3 = new Description();
		desc3.setLang("eng");
		desc3.setVal("Hindware brand is good");
		productBrand3.setDesc(desc3);
		productBrand3.setProvider(provider3);
		brandDAO.insert(productBrand3);
		
		//Toto
		ProductBrand productBrand4 = new ProductBrand(); 
		List<User> users4 = userDAO.findByUsername("admin@jaquar.com",
				new PageRequest(0, 10));
		User provider4 = users4.get(0);
		productBrand4.setName("Toto");
		Description desc4 = new Description();
		desc4.setLang("eng");
		desc4.setVal("Toto brand is good");
		productBrand4.setDesc(desc4);
		productBrand4.setProvider(provider4);
		brandDAO.insert(productBrand4);
		
	}
}
