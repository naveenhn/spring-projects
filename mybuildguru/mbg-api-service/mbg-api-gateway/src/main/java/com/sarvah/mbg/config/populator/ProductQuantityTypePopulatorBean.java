package com.sarvah.mbg.config.populator;

import com.sarvah.mbg.catalog.dao.mongo.ProductQuantityTypeDAO;
import com.sarvah.mbg.domain.mongo.catalog.ProductQuantityType;
import com.sarvah.mbg.domain.mongo.common.Description;

public class ProductQuantityTypePopulatorBean {
	ProductQuantityTypeDAO productQuantityTypeDAO;

	public ProductQuantityTypePopulatorBean(
			ProductQuantityTypeDAO productQuantityTypeDAO) {
		this.productQuantityTypeDAO = productQuantityTypeDAO;
	}

	public void initProductQuantityType() {
		productQuantityTypeDAO.deleteAll();
		// productQuantity type1
		ProductQuantityType productQuantityType = new ProductQuantityType();
		productQuantityType.setName("sqft");
		Description desc = new Description("en", "sqft info");
		productQuantityType.setDesc(desc);
		// productQuantity type2
		ProductQuantityType productQuantityType1 = new ProductQuantityType();
		productQuantityType1.setName("unit");
		Description desc1 = new Description("en", "unit info");
		productQuantityType1.setDesc(desc1);

		// productQuantity type3
		ProductQuantityType productQuantityType2 = new ProductQuantityType();
		productQuantityType2.setName("inch");
		Description desc2 = new Description("en", "inch info");
		productQuantityType2.setDesc(desc2);

		// productQuantity type4
		ProductQuantityType productQuantityType3 = new ProductQuantityType();
		productQuantityType3.setName("box");
		Description desc3 = new Description("en", "box info");
		productQuantityType3.setDesc(desc3);

		// productQuantity type5
		ProductQuantityType productQuantityType4 = new ProductQuantityType();
		productQuantityType4.setName("kg");
		Description desc4 = new Description("en", "kg info");
		productQuantityType4.setDesc(desc4);

		// productQuantity type6
		ProductQuantityType productQuantityType5 = new ProductQuantityType();
		productQuantityType5.setName("cms");
		Description desc5 = new Description("en", "cms info");
		productQuantityType5.setDesc(desc5);

		productQuantityTypeDAO.insert(productQuantityType);
		productQuantityTypeDAO.insert(productQuantityType1);
		productQuantityTypeDAO.insert(productQuantityType2);
		productQuantityTypeDAO.insert(productQuantityType3);
		productQuantityTypeDAO.insert(productQuantityType4);
		productQuantityTypeDAO.insert(productQuantityType5);
	}
}
