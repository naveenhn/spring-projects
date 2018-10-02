/**
 * 
 */
package com.sarvah.mbg.config.populator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sarvah.mbg.catalog.dao.mongo.ProductDAO;
import com.sarvah.mbg.catalog.dao.mongo.SubCategoryDAO;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.marketing.Promotion;
import com.sarvah.mbg.domain.mongo.marketing.PromotionType;
import com.sarvah.mbg.userprofile.dao.mongo.ProductPromotionDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;

/**
 * @author shivu
 *
 */
public class PromotionPopulatorBean {
	UserDAO userDAO;
	ProductDAO productDAO;
	ProductPromotionDAO promotionDAO;
	SubCategoryDAO subCategoryDAO;

	PromotionPopulatorBean(UserDAO userDAO, ProductDAO productDAO,
			ProductPromotionDAO promotionDAO, SubCategoryDAO subCategoryDAO) {
		this.userDAO = userDAO;
		this.productDAO = productDAO;
		this.promotionDAO = promotionDAO;
		this.subCategoryDAO = subCategoryDAO;
	}

	public void initPromotion() throws ParseException {

		promotionDAO.deleteAll();
//		Promotion promoCode1 = new Promotion();
//		promoCode1.setName("Sankranthi_Promotion");
//
//		Description desc1 = new Description();
//		desc1.setLang("eng");
//		desc1.setVal("Sankranthi promotion information");
//		promoCode1.setDesc(desc1);
//
//		promoCode1.setType(PromotionType.PROMOCODE);
//
//		List<SubCategory> subCategoryList1 = subCategoryDAO.findByName("Tubs");
//		Set<SubCategory> subCategories1 = new HashSet<SubCategory>();
//		for (SubCategory subcategory : subCategoryList1) {
//			subCategories1.add(subcategory);
//		}
//		promoCode1.setSubCategory(subCategories1);
//		promoCode1.setDiscount(25.5);
//
//		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//
//		String startDate1 = "04/09/2015 11:15:15";
//		Date sDate1 = formatter1.parse(startDate1);
//		promoCode1.setStartDate(sDate1);
//
//		String endDate1 = "10/09/2017 11:15:15";
//		Date eDate1 = formatter1.parse(endDate1);
//		promoCode1.setEndDate(eDate1);
//		promoCode1.setPromoCode("MBG5OFF");
//		promoCode1.setActive(true);
//
//		promotionDAO.insert(promoCode1);
//
//		// Promo code2 (Product level)
//		Promotion promoCode2 = new Promotion();
//		promoCode2.setName("Republic day_Promotion");
//
//		Description desc2 = new Description();
//		desc2.setLang("eng");
//		desc2.setVal("Republic day_Promotion information");
//		promoCode2.setDesc(desc2);
//
//		promoCode2.setType(PromotionType.PROMOCODE);
//
//		List<Product> productList = productDAO
//				.findByNameAllIgnoreCase("Jaguar - XYZ");
//		Set<String> productIds = new HashSet<String>();
//		for (Product product : productList) {
//			productIds.add(product.getId());
//		}
//
//		promoCode2.setProductIds(productIds);
//		promoCode2.setDiscount(30.5);
//
//		SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//
//		String startDate2 = "04/09/2015 11:15:15";
//		Date sDate2 = formatter2.parse(startDate2);
//		promoCode2.setStartDate(sDate2);
//
//		String endDate2 = "10/09/2017 11:15:15";
//		Date eDate2 = formatter2.parse(endDate2);
//		promoCode2.setEndDate(eDate2);
//		promoCode2.setPromoCode("MBG10OFF");
//		promoCode2.setActive(true);
//
//		promotionDAO.insert(promoCode2);

	}

}