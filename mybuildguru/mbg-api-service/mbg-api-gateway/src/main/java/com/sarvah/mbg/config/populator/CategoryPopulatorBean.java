package com.sarvah.mbg.config.populator;

import com.sarvah.mbg.catalog.dao.mongo.CategoryDAO;
import com.sarvah.mbg.domain.mongo.catalog.Category;
import com.sarvah.mbg.domain.mongo.common.Description;

public class CategoryPopulatorBean {
	CategoryDAO categoryDAO;

	public CategoryPopulatorBean(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	public void initCategories() {

		categoryDAO.deleteAll();

		// Category 1
		Category category = new Category();
		category.setName("Plumbing");
		Description desc = new Description("en", "Plumbing");
		category.setDesc(desc);
		categoryDAO.insert(category);

		// Category 2
		Category category1 = new Category();
		category1.setName("Electricals");
		Description desc1 = new Description("en", "Electricals");
		category1.setDesc(desc1);
		categoryDAO.insert(category1);

		// Category 3
		Category category2 = new Category();
		category2.setName("Painting");
		Description desc2 = new Description("en", "Painting");
		category2.setDesc(desc2);
		categoryDAO.insert(category2);

		// Category 4
		Category category3 = new Category();
		category3.setName("Doors and Windows");
		Description desc3 = new Description("en", "Doors and Windows");
		category3.setDesc(desc3);
		categoryDAO.insert(category3);

		// Category 5
		Category category4 = new Category();
		category4.setName("Surface Finishes");
		Description desc4 = new Description("en", "Surface Finishes");
		category4.setDesc(desc4);
		categoryDAO.insert(category4);

		// Category 6
		Category category5 = new Category();
		category5.setName("Interiors");
		Description desc5 = new Description("en", "Interiors");
		category5.setDesc(desc5);
		categoryDAO.insert(category5);

		// Category 7
		Category category6 = new Category();
		category6.setName("Security Systems");
		Description desc6 = new Description("en", "Security Systems");
		category6.setDesc(desc6);
		categoryDAO.insert(category6);

		// Category 8
		Category category7 = new Category();
		category7.setName("Building Materials");
		Description desc7 = new Description("en", "Building Materials");
		category7.setDesc(desc7);
		categoryDAO.insert(category7);

		// Category 9
		Category category8 = new Category();
		category8.setName("Landsacpe");
		Description desc8 = new Description("en", "Landsacpe");
		category8.setDesc(desc8);
		categoryDAO.insert(category8);

		// Category 10
		Category category9 = new Category();
		category9.setName("Tools and Hardware");
		Description desc9 = new Description("en", "Tools and Hardware");
		category9.setDesc(desc9);
		categoryDAO.insert(category9);

		// Category 11
		Category category10 = new Category();
		category10.setName("Appliances");
		Description desc10 = new Description("en", "Appliances");
		category10.setDesc(desc10);
		categoryDAO.insert(category10);

		// Category 12
		Category category11 = new Category();
		category11.setName("Mechanical");
		Description desc11 = new Description("en", "Mechanical");
		category11.setDesc(desc11);
		categoryDAO.insert(category11);

		// Category 13
		Category category12 = new Category();
		category12.setName("Service Providers");
		Description desc12 = new Description("en", "Service Providers");
		category12.setDesc(desc12);
		categoryDAO.insert(category12);

	}
}
