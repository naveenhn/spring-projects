/**
 * 
 */
package com.sarvah.mbg.config.populator;

import java.text.ParseException;

import com.sarvah.mbg.store.dao.mongo.StoreDAO;
import com.sarvah.mbg.userprofile.dao.mongo.PromotionBannerDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;

/**
 * @author shivu s
 *
 */
public class BannerPopulatorBean {
	PromotionBannerDAO promotionBannerDAO;
	UserDAO userDAO;
	StoreDAO productStoreDAO;

	public BannerPopulatorBean(PromotionBannerDAO promotionBannerDAO,
			UserDAO userDAO, StoreDAO productStoreDAO) {
		this.promotionBannerDAO = promotionBannerDAO;
		this.userDAO = userDAO;
		this.productStoreDAO = productStoreDAO;

	}

	public void initBanner() throws ParseException {
		promotionBannerDAO.deleteAll();
		// Banner banner1 = new Banner();
		//
		// Description desc1 = new Description();
		// desc1.setLang("eng");
		// desc1.setVal("Banner information");
		// banner1.setDesc(desc1);
		//
		// List<User> user = userDAO.findByUsername("admin@cera.com",
		// new PageRequest(0, 10));
		// banner1.setUser(user.get(0));
		//
		// banner1.setLocation("Top Right");
		//
		// Image image1 = new Image("img1",
		// "https://mbgtest.blob.core.windows.net/images/catalog/product/A/AN/img1.jpg");
		//
		// banner1.setBannerImage(image1);
		//
		// List<String> productIds = new ArrayList<>();
		//
		// String pid1 = "1";
		// String pid2 = "2";
		//
		// productIds.add(pid1);
		// productIds.add(pid2);
		//
		// banner1.setProductIds(productIds);
		//
		// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// // DateFormat formatter = new
		// SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
		//
		// String startDate = "2015-12-17";
		// Date sDate = formatter.parse(startDate);
		// banner1.setStartDate(sDate);
		//
		// String endDate = "2019-12-19";
		// Date eDate = formatter.parse(endDate);
		// banner1.setEndDate(eDate);
		// promotionBannerDAO.insert(banner1);

	}
}
