package com.sarvah.mbg.config.populator;

import java.util.List;

import com.sarvah.mbg.catalog.dao.mongo.ProductTypeDAO;
import com.sarvah.mbg.catalog.dao.mongo.SubCategoryDAO;
import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.common.Description;

public class ProductTypePopulatorBean {

	ProductTypeDAO productTypeDAO;

	SubCategoryDAO subCategoryDAO;

	public ProductTypePopulatorBean(ProductTypeDAO productTypeDAO,
			SubCategoryDAO subCategoryDAO) {
		this.productTypeDAO = productTypeDAO;
		this.subCategoryDAO = subCategoryDAO;
	}

	public void initProdTypes() {

		productTypeDAO.deleteAll();

		// ProductType 1
		List<SubCategory> subCategories = subCategoryDAO.findByName("Tubs");

		if (subCategories != null && !subCategories.isEmpty()) {

			SubCategory subCat = subCategories.get(0);

			ProductType productType1 = new ProductType();
			productType1.setName("Ivory");
			productType1.setSubCategory(subCat);
			Description desc1 = new Description("en", "Ivory Finish");
			productType1.setDesc(desc1);

			productTypeDAO.insert(productType1);

			ProductType productType2 = new ProductType();
			productType2.setName("Magenta");
			productType2.setSubCategory(subCat);
			Description desc2 = new Description("en", "Magenta Finish");
			productType2.setDesc(desc2);

			productTypeDAO.insert(productType2);

		}

		// ProductType 2
		List<SubCategory> subCategories1 = subCategoryDAO.findByName("Fans");

		if (subCategories1 != null && !subCategories1.isEmpty()) {

			SubCategory subCat1 = subCategories1.get(0);

			ProductType productType1 = new ProductType();
			productType1.setName("Ivory");
			productType1.setSubCategory(subCat1);
			Description desc1 = new Description("en", "Ivory Finish");
			productType1.setDesc(desc1);

			productTypeDAO.insert(productType1);

			ProductType productType2 = new ProductType();
			productType2.setName("Magenta");
			productType2.setSubCategory(subCat1);
			Description desc2 = new Description("en", "Magenta Finish");
			productType2.setDesc(desc2);

			productTypeDAO.insert(productType2);

		}

		// ProductType 3
		List<SubCategory> subCategories2 = subCategoryDAO.findByName("Enamels");

		if (subCategories2 != null && !subCategories2.isEmpty()) {

			SubCategory subCat2 = subCategories2.get(0);

			ProductType productType1 = new ProductType();
			productType1.setName("Ivory");
			productType1.setSubCategory(subCat2);
			Description desc1 = new Description("en", "Ivory Finish");
			productType1.setDesc(desc1);

			productTypeDAO.insert(productType1);

			ProductType productType2 = new ProductType();
			productType2.setName("Magenta");
			productType2.setSubCategory(subCat2);
			Description desc2 = new Description("en", "Magenta Finish");
			productType2.setDesc(desc2);

			productTypeDAO.insert(productType2);

		}

		// ProductType 4
		List<SubCategory> subCategories3 = subCategoryDAO.findByName("Doors");

		if (subCategories3 != null && !subCategories3.isEmpty()) {

			SubCategory subCat3 = subCategories3.get(0);

			ProductType productType1 = new ProductType();
			productType1.setName("Ivory");
			productType1.setSubCategory(subCat3);
			Description desc1 = new Description("en", "Ivory Finish");
			productType1.setDesc(desc1);

			productTypeDAO.insert(productType1);

			ProductType productType2 = new ProductType();
			productType2.setName("Magenta");
			productType2.setSubCategory(subCat3);
			Description desc2 = new Description("en", "Magenta Finish");
			productType2.setDesc(desc2);

			productTypeDAO.insert(productType2);

		}

		// ProductType 5
		List<SubCategory> subCategories4 = subCategoryDAO
				.findByName("Interior Flooring");

		if (subCategories4 != null && !subCategories4.isEmpty()) {

			SubCategory subCat4 = subCategories4.get(0);

			ProductType productType1 = new ProductType();
			productType1.setName("Ivory");
			productType1.setSubCategory(subCat4);
			Description desc1 = new Description("en", "Ivory Finish");
			productType1.setDesc(desc1);

			productTypeDAO.insert(productType1);

			ProductType productType2 = new ProductType();
			productType2.setName("Magenta");
			productType2.setSubCategory(subCat4);
			Description desc2 = new Description("en", "Magenta Finish");
			productType2.setDesc(desc2);

			productTypeDAO.insert(productType2);

		}

		// ProductType 6
		List<SubCategory> subCategories5 = subCategoryDAO
				.findByName("Office Furnitures");

		if (subCategories5 != null && !subCategories5.isEmpty()) {

			SubCategory subCat5 = subCategories5.get(0);

			ProductType productType1 = new ProductType();
			productType1.setName("Ivory");
			productType1.setSubCategory(subCat5);
			Description desc1 = new Description("en", "Ivory Finish");
			productType1.setDesc(desc1);

			productTypeDAO.insert(productType1);

			ProductType productType2 = new ProductType();
			productType2.setName("Magenta");
			productType2.setSubCategory(subCat5);
			Description desc2 = new Description("en", "Magenta Finish");
			productType2.setDesc(desc2);

			productTypeDAO.insert(productType2);

		}

		// ProductType 7
		List<SubCategory> subCategories6 = subCategoryDAO.findByName("Safes");

		if (subCategories6 != null && !subCategories6.isEmpty()) {

			SubCategory subCat6 = subCategories6.get(0);

			ProductType productType1 = new ProductType();
			productType1.setName("Ivory");
			productType1.setSubCategory(subCat6);
			Description desc1 = new Description("en", "Ivory Finish");
			productType1.setDesc(desc1);

			productTypeDAO.insert(productType1);

			ProductType productType2 = new ProductType();
			productType2.setName("Magenta");
			productType2.setSubCategory(subCat6);
			Description desc2 = new Description("en", "Magenta Finish");
			productType2.setDesc(desc2);

			productTypeDAO.insert(productType2);

		}

		// ProductType 8
		List<SubCategory> subCategories7 = subCategoryDAO
				.findByName("Bricks and Blocks");

		if (subCategories7 != null && !subCategories7.isEmpty()) {

			SubCategory subCat7 = subCategories7.get(0);

			ProductType productType1 = new ProductType();
			productType1.setName("Ivory");
			productType1.setSubCategory(subCat7);
			Description desc1 = new Description("en", "Ivory Finish");
			productType1.setDesc(desc1);

			productTypeDAO.insert(productType1);

			ProductType productType2 = new ProductType();
			productType2.setName("Magenta");
			productType2.setSubCategory(subCat7);
			Description desc2 = new Description("en", "Magenta Finish");
			productType2.setDesc(desc2);

			productTypeDAO.insert(productType2);

		}

		// ProductType 9
		List<SubCategory> subCategories8 = subCategoryDAO.findByName("Fencing");

		if (subCategories8 != null && !subCategories8.isEmpty()) {

			SubCategory subCat8 = subCategories8.get(0);

			ProductType productType1 = new ProductType();
			productType1.setName("Ivory");
			productType1.setSubCategory(subCat8);
			Description desc1 = new Description("en", "Ivory Finish");
			productType1.setDesc(desc1);

			productTypeDAO.insert(productType1);

			ProductType productType2 = new ProductType();
			productType2.setName("Magenta");
			productType2.setSubCategory(subCat8);
			Description desc2 = new Description("en", "Magenta Finish");
			productType2.setDesc(desc2);

			productTypeDAO.insert(productType2);

		}

		// ProductType 10
		List<SubCategory> subCategories9 = subCategoryDAO
				.findByName("Power tools");

		if (subCategories9 != null && !subCategories9.isEmpty()) {

			SubCategory subCat9 = subCategories9.get(0);

			ProductType productType1 = new ProductType();
			productType1.setName("Ivory");
			productType1.setSubCategory(subCat9);
			Description desc1 = new Description("en", "Ivory Finish");
			productType1.setDesc(desc1);

			productTypeDAO.insert(productType1);

			ProductType productType2 = new ProductType();
			productType2.setName("Magenta");
			productType2.setSubCategory(subCat9);
			Description desc2 = new Description("en", "Magenta Finish");
			productType2.setDesc(desc2);

			productTypeDAO.insert(productType2);

		}

		// ProductType 11
		List<SubCategory> subCategories10 = subCategoryDAO.findByName("Gysers");

		if (subCategories10 != null && !subCategories10.isEmpty()) {

			SubCategory subCat10 = subCategories10.get(0);

			ProductType productType1 = new ProductType();
			productType1.setName("Ivory");
			productType1.setSubCategory(subCat10);
			Description desc1 = new Description("en", "Ivory Finish");
			productType1.setDesc(desc1);

			productTypeDAO.insert(productType1);

			ProductType productType2 = new ProductType();
			productType2.setName("Magenta");
			productType2.setSubCategory(subCat10);
			Description desc2 = new Description("en", "Magenta Finish");
			productType2.setDesc(desc2);

			productTypeDAO.insert(productType2);

		}

		// ProductType 12
		List<SubCategory> subCategories11 = subCategoryDAO.findByName("HVAC");

		if (subCategories11 != null && !subCategories11.isEmpty()) {

			SubCategory subCat11 = subCategories11.get(0);

			ProductType productType1 = new ProductType();
			productType1.setName("Ivory");
			productType1.setSubCategory(subCat11);
			Description desc1 = new Description("en", "Ivory Finish");
			productType1.setDesc(desc1);

			productTypeDAO.insert(productType1);

			ProductType productType2 = new ProductType();
			productType2.setName("Magenta");
			productType2.setSubCategory(subCat11);
			Description desc2 = new Description("en", "Magenta Finish");
			productType2.setDesc(desc2);

			productTypeDAO.insert(productType2);

		}

	}

}
