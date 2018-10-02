/**
 * 
 */
package com.sarvah.mbg.catalog.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.catalog.dao.mongo.CategoryDAO;
import com.sarvah.mbg.catalog.dao.mongo.ProductDAO;
import com.sarvah.mbg.catalog.dao.mongo.ProductTypeDAO;
import com.sarvah.mbg.catalog.dao.mongo.SubCategoryDAO;
import com.sarvah.mbg.catalog.service.ProductCategoryService;
import com.sarvah.mbg.catalog.service.model.BrandSummary;
import com.sarvah.mbg.catalog.service.model.CategorySummary;
import com.sarvah.mbg.catalog.service.model.ProductCustomSearch;
import com.sarvah.mbg.catalog.service.model.ProductTypeSummary;
import com.sarvah.mbg.catalog.service.model.SubCategorySummary;
import com.sarvah.mbg.domain.mongo.catalog.Category;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.ProductStatus;
import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.userprofile.dao.mongo.BrandDAO;

/**
 * @author shivu
 *
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	private static final Logger logger = LoggerFactory
			.getLogger(ProductCategoryServiceImpl.class);

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private SubCategoryDAO subCategoryDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private ProductTypeDAO productTypeDAO;

	@Autowired
	private BrandDAO brandDAO;

	/**
	 * Method to get the All Categories which is related to category
	 * 
	 * @return List<Category>
	 */
	@Override
	public List<Category> getAllCategories() {
		logger.debug("ProductCatalogServiceImple,getAllCategories()");
		List<Category> categoryList = categoryDAO.findAll();
		logger.debug("get all category " + categoryList);
		return categoryList;
	}

	/**
	 * Method to count the Category which is related to Category
	 * 
	 * @return long
	 */
	@Override
	public long getCategoryCount() {
		logger.debug("ProductCatalogServiceImple,getCategoryCount()");
		return categoryDAO.count();
	}

	/**
	 * Method to search category by name
	 * 
	 * @param name
	 * @return
	 */

	@Override
	public List<Category> searchCategories(String name) {
		Set<Category> categories = new HashSet<Category>();
		if (StringUtils.isNotBlank(name)) {
			logger.info("Searching category based on Name of categories");
			categories.addAll(categoryDAO.findByNameAllIgnoreCase(name));
			return new ArrayList<Category>(categories);
		} else {
			logger.info("Finding all categories");
			categories.addAll(categoryDAO.findAll());
			return new ArrayList<Category>(categories);
		}
	}

	/**
	 * Method to get count of category
	 * 
	 * @param name
	 * @return
	 */
	@Override
	public Long getCategoryCount(String name) {

		Long count = null;
		if (StringUtils.isNotBlank(name)) {
			logger.info("Counting category based on category name");
			count = categoryDAO.countByName(name);
		} else
			logger.info("Getting category count");
		count = categoryDAO.count();
		return count;

	}

	/**
	 * method for create category.
	 * 
	 * @param categoryCreateRequestParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public Category createCategory(String name, String desc) throws Exception {
		Category category = null;
		if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(desc)) {
			category = new Category();
			category.setName(name);
			Description desc1 = new Description();
			desc1.setLang("eng");
			desc1.setVal(desc);
			category.setDesc(desc1);
			logger.info("Creating category");
			categoryDAO.insert(category);
		} else {
			logger.info("Unable to create Category");
			throw new Exception("Unable to create Category");
		}
		return category;
	}

	/**
	 * method for searching the categories based on categoryId.
	 * 
	 * @paramcatid
	 * @return
	 * @throwsMBGAppException
	 */
	@Override
	public Category searchCategoriesById(String cateid) {
		Category category = null;
		if (StringUtils.isNotBlank(cateid)) {
			logger.info("Search category by catid");
			category = categoryDAO.findById(cateid);
		}
		return category;
	}

	/**
	 * method for update category based on categoryId.
	 * 
	 * @param catid
	 * @param categoryUpdateRequestParam
	 * @return
	 */
	@Override
	public Category updateCategory(String catid, String name, String desc)
			throws Exception {
		Category category = null;
		if (StringUtils.isNotBlank(catid)) {
			category = categoryDAO.findOne(catid);
			if (category != null) {
				if (StringUtils.isNotBlank(name)) {
					category.setName(name);
				}
				if (StringUtils.isNotBlank(desc)) {
					category.getDesc().setVal(desc);
				}
				logger.info("Updating category");
				categoryDAO.save(category);
			}
		} else {
			logger.info("Unable to update category");
			throw new Exception("Unable to update category");
		}
		return category;
	}

	/**
	 * Method to delete a category from the system
	 * 
	 * @throws Exception
	 */
	@Override
	public String deleteCategory(String catId) throws Exception {

		Set<SubCategory> subCategory = null;
		Category category = categoryDAO.findOne(catId);
		if (category != null) {
			subCategory = subCategoryDAO.findByCategory(category.getName());
			if (subCategory != null) {
				subCategoryDAO.delete(subCategory);
				categoryDAO.delete(catId);

			} else {
				logger.info("SubCategory doesn't exist");
				throw new Exception("SubCategory doesn't exist");
			}
		} else {
			logger.info("Category doesn't exist");
			throw new Exception("Category doesn't exist");
		}
		logger.info("Category deleted");
		return catId;
	}

	/**
	 * method for get the Subcategories based on categoryId.
	 * 
	 * @paramsubcat
	 * @return
	 * @throwsMBGAppException
	 */
	@Override
	public Set<SubCategory> searchSubCategories(String cateid) {

		Category category = null;
		Set<SubCategory> subCategories = new HashSet<SubCategory>();
		if (StringUtils.isNotBlank(cateid)) {
			{
				logger.info("Searching subcategorie by using the cateid ");
				category = categoryDAO.findOne(cateid);
				if (category != null) {
					subCategories = subCategoryDAO.findByCategory(category
							.getName());
				}
			}
		} else {
			logger.info("Cateid is empty ");
		}
		return subCategories;
	}

	/**
	 * method for get the subcategory based on subcategoryId.
	 * 
	 * @paramcatid
	 * @PathParamsubcatid
	 * @return
	 * @throwsMBGAppException
	 */
	@Override
	public SubCategory searchSubCategorieById(String cateid, String subcatid) {
		SubCategory subCategory = null;
		if (StringUtils.isNotBlank(cateid)) {
			Category category = categoryDAO.findById(cateid);
			if (category != null && StringUtils.isNotBlank(subcatid)) {
				logger.info("Search subCategories by using Subcatid");
				subCategory = subCategoryDAO.findById(subcatid);
			}
		}
		return subCategory;
	}

	/**
	 * Method to create new subCategory
	 * 
	 * @throws Exception
	 * 
	 */
	@Override
	public SubCategory createSubCategory(String catid, String desc,
			String subcatName) throws Exception {

		SubCategory subCategory = new SubCategory();

		if (StringUtils.isNotBlank(catid)) {
			Category category = categoryDAO.findById(catid);
			if (category != null && StringUtils.isNotBlank(desc)) {

				Description descr = new Description();
				descr.setLang("Eng");
				descr.setVal(desc);
				subCategory.setDesc(descr);
			}
			if (StringUtils.isNotBlank(subcatName)) {
				subCategory.setName(subcatName);
			}

			subCategory.setCategory(category.getName());

			subCategoryDAO.insert(subCategory);
		} else {
			logger.info("Category doesn't exist");
			throw new Exception("Category doesn't exist");
		}
		logger.info("SubCategory created");
		return subCategory;
	}

	/**
	 * method for update subCategory based on subcategoryId.
	 * 
	 * @param catid
	 * @param subcatid
	 * @param subCategoryUpdateRequestParam
	 * @return
	 */
	@Override
	public SubCategory updateSubCategory(String catid, String subcatid,
			String name, String desc) throws Exception {
		Category category = null;
		SubCategory subCategory = null;
		if (StringUtils.isNotBlank(catid) && StringUtils.isNotBlank(subcatid)) {
			category = categoryDAO.findOne(catid);
			subCategory = subCategoryDAO.findOne(subcatid);
			if (category != null && subCategory != null) {
				if (StringUtils.isNotBlank(name)) {
					subCategory.setName(name);
				}
				if (StringUtils.isNotBlank(desc)) {
					subCategory.getDesc().setVal(desc);
				}
				logger.info("Updating subcategory");
				subCategoryDAO.save(subCategory);
			} else {
				logger.info("category or subCategory is null");
				throw new Exception("category or subCategory is null");
			}
		} else {
			logger.info("categoryId and subCategoryId should not be null");
			throw new Exception(
					"categoryId and subCategoryId should not be null");
		}
		return subCategory;
	}

	/**
	 * Method for Delete SubCategory.
	 * 
	 * @paramcateid
	 */
	@Override
	public String deleteSubCategorieById(String cateid, String subcatid) {
		Category category = null;
		Set<SubCategory> subCategory = new HashSet<SubCategory>();
		if (StringUtils.isNotBlank(cateid)) {
			category = categoryDAO.findById(cateid);
			subCategory = subCategoryDAO.findByCategory(category.getName());
			if (subCategory != null) {
				if (StringUtils.isNotBlank(subcatid)) {
					logger.info("Delete SubCategorie by using Subcatidsucessfully");
					subCategoryDAO.delete(subcatid);
				}
			}
		}
		return subcatid;
	}

	/**
	 * Method to get product type
	 * 
	 * @throws Exception
	 */
	@Override
	public List<ProductType> getProductType(String catId, String subcatId)
			throws Exception {

		Set<ProductType> productType = null;

		if (StringUtils.isNotBlank(catId)) {
			Category category = categoryDAO.findOne(catId);
			if (category != null && StringUtils.isNotBlank(subcatId)) {
				SubCategory subCategory = subCategoryDAO.findOne(subcatId);
				if (subCategory != null) {
					productType = productTypeDAO.findBySubCategory(subCategory);
				} else {
					logger.info("SubCategory doesn't exist");
					throw new Exception("SubCategory doesn't exist");
				}
			}
		} else {
			logger.info("Category doesn't exist");
			throw new Exception("Category doesn't exist");
		}
		return (productType != null) ? new ArrayList<ProductType>(productType)
				: null;
	}

	/**
	 * Method for create ProductType
	 * 
	 * @param catid
	 * @param subcatid
	 * @param productTypeCreateRequestParam
	 * @return
	 */
	@Override
	public ProductType createProductType(String catid, String subcatid,
			String name, String desc) throws Exception {
		ProductType productType = null;
		if (StringUtils.isNotBlank(catid)) {
			Category category = categoryDAO.findOne(catid);
			if (category != null && StringUtils.isNotBlank(subcatid)) {
				SubCategory subCategory = subCategoryDAO.findOne(subcatid);
				if (subCategory != null) {
					productType = new ProductType();
					if (StringUtils.isNotBlank(name)) {
						productType.setName(name);
					}
					if (StringUtils.isNotBlank(desc)) {
						Description description = new Description();
						description.setLang("eng");
						description.setVal(desc);
						productType.setDesc(description);
					}
					productType.setSubCategory(subCategory);
					logger.info("Creating productType");
					productTypeDAO.insert(productType);
				}
			}
		} else {
			logger.info("Unable to create ProductType class");
			throw new Exception("Unable to create ProductType class");
		}
		return productType;
	}

	/**
	 * method for update ProductType.
	 * 
	 * @param catid
	 * @param subcatid
	 * @param prodtypid
	 * @param productTypeUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public ProductType updateProductType(String catid, String subcatid,
			String prodtypid, String name, String desc) throws Exception {
		ProductType productType = null;
		if (StringUtils.isNotBlank(catid)) {
			Category category = categoryDAO.findOne(catid);
			if (category != null && StringUtils.isNotBlank(subcatid)) {
				SubCategory subCategory = subCategoryDAO.findOne(subcatid);
				if (subCategory != null) {
					productType = productTypeDAO.findOne(prodtypid);
					if (productType != null) {
						logger.info("Validation of catid, subcatid and prodtypid is success");
						if (StringUtils.isNotBlank(name)) {
							productType.setName(name);
							logger.info("ProductType name is updated successfully");
						}
						if (StringUtils.isNotBlank(desc)) {
							productType.getDesc().setVal(desc);
							logger.info("ProductType description is updated successfully");
						}
						productTypeDAO.save(productType);
					}
				}
			}
		} else {
			throw new Exception("Unable to update productType");
		}
		return productType;
	}

	/**
	 * method for delete ProductType.
	 * 
	 * @param catid
	 * @param subcatid
	 * @param prodtypid
	 * @return
	 * @throws Exception
	 */
	@Override
	public String deleteProductType(String catid, String subcatid,
			String prodtypid) throws Exception {
		ProductType productType = null;
		if (StringUtils.isNotBlank(catid)) {
			Category category = categoryDAO.findOne(catid);
			if (category != null && StringUtils.isNotBlank(subcatid)) {
				SubCategory subCategory = subCategoryDAO.findOne(subcatid);
				if (subCategory != null) {
					productType = productTypeDAO.findOne(prodtypid);
					if (productType != null) {
						logger.info("Validation of catid, subcatid and prodtypid is success");
						productTypeDAO.delete(prodtypid);
						logger.info("ProductType deleted successsfull");
					}
				}
			}
		} else {
			throw new Exception("Unable to delete ProductType");
		}

		return prodtypid;
	}

	/**
	 * method for searching the categories
	 * 
	 * @param name
	 * 
	 * @return
	 * 
	 * @throws MBGAppException
	 */
	@Override
	public List<CategorySummary> getCategoriesSummary(String productType) {
		List<CategorySummary> categorySummaries = new ArrayList<CategorySummary>();
		List<Category> categories = categoryDAO.findAll();
		for (Category category : categories) {
			if (category.getName().equalsIgnoreCase("Building Materials")) {
				CategorySummary categorySummary = new CategorySummary(category);
				Set<SubCategory> subCategories = subCategoryDAO
						.findByCategory(category.getName());
				categorySummary.setSubCategories(getSubCategorySummary(
						subCategories, productType));

				ProductCustomSearch productCustomSearch = new ProductCustomSearch();
				String categoryName = category.getName();
				List<Category> categoriesList = new ArrayList<Category>();
				categoriesList = categoryDAO
						.findByNameAllIgnoreCase(categoryName);
				productCustomSearch.setCategories(categoriesList.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));

				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				productStatuses.add(ProductStatus.IN_BUILDONN);
				productCustomSearch.setProductStatuses(productStatuses);

				Long count = productDAO.count(productCustomSearch);
				if (count != 0) {
					categorySummary.setCategoryHaveProducts(true);
				} else {
					categorySummary.setCategoryHaveProducts(false);
				}

				categorySummaries.add(categorySummary);
				categories.remove(category);
				break;
			}
		}

		for (Category category : categories) {
			if (category.getName().equalsIgnoreCase("Plumbing")) {
				CategorySummary categorySummary = new CategorySummary(category);
				Set<SubCategory> subCategories = subCategoryDAO
						.findByCategory(category.getName());
				categorySummary.setSubCategories(getSubCategorySummary(
						subCategories, productType));

				ProductCustomSearch productCustomSearch = new ProductCustomSearch();
				String categoryName = category.getName();
				List<Category> categoriesList = new ArrayList<Category>();
				categoriesList = categoryDAO
						.findByNameAllIgnoreCase(categoryName);
				productCustomSearch.setCategories(categoriesList.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));

				String status = ProductStatus.IN_BUILDONN.toString();
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}
				productCustomSearch.setProductStatuses(productStatuses);

				Long count = productDAO.count(productCustomSearch);
				if (count != 0) {
					categorySummary.setCategoryHaveProducts(true);
				} else {
					categorySummary.setCategoryHaveProducts(false);
				}

				categorySummaries.add(categorySummary);
				categories.remove(category);
				break;
			}
		}

		for (Category category : categories) {
			if (category.getName().equalsIgnoreCase("Electricals")) {
				CategorySummary categorySummary = new CategorySummary(category);
				Set<SubCategory> subCategories = subCategoryDAO
						.findByCategory(category.getName());
				categorySummary.setSubCategories(getSubCategorySummary(
						subCategories, productType));

				ProductCustomSearch productCustomSearch = new ProductCustomSearch();
				String categoryName = category.getName();
				List<Category> categoriesList = new ArrayList<Category>();
				categoriesList = categoryDAO
						.findByNameAllIgnoreCase(categoryName);
				productCustomSearch.setCategories(categoriesList.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));

				String status = ProductStatus.IN_BUILDONN.toString();
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}
				productCustomSearch.setProductStatuses(productStatuses);

				Long count = productDAO.count(productCustomSearch);
				if (count != 0) {
					categorySummary.setCategoryHaveProducts(true);
				} else {
					categorySummary.setCategoryHaveProducts(false);
				}

				categorySummaries.add(categorySummary);
				categories.remove(category);
				break;
			}
		}

		for (Category category : categories) {
			if (category.getName().equalsIgnoreCase("Tools")) {
				CategorySummary categorySummary = new CategorySummary(category);
				Set<SubCategory> subCategories = subCategoryDAO
						.findByCategory(category.getName());
				categorySummary.setSubCategories(getSubCategorySummary(
						subCategories, productType));

				ProductCustomSearch productCustomSearch = new ProductCustomSearch();
				String categoryName = category.getName();
				List<Category> categoriesList = new ArrayList<Category>();
				categoriesList = categoryDAO
						.findByNameAllIgnoreCase(categoryName);
				productCustomSearch.setCategories(categoriesList.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));

				String status = ProductStatus.IN_BUILDONN.toString();
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}
				productCustomSearch.setProductStatuses(productStatuses);

				Long count = productDAO.count(productCustomSearch);
				if (count != 0) {
					categorySummary.setCategoryHaveProducts(true);
				} else {
					categorySummary.setCategoryHaveProducts(false);
				}

				categorySummaries.add(categorySummary);
				categories.remove(category);
				break;
			}
		}

		for (Category category : categories) {
			if (category.getName().equalsIgnoreCase("Hardware")) {
				CategorySummary categorySummary = new CategorySummary(category);
				Set<SubCategory> subCategories = subCategoryDAO
						.findByCategory(category.getName());
				categorySummary.setSubCategories(getSubCategorySummary(
						subCategories, productType));

				ProductCustomSearch productCustomSearch = new ProductCustomSearch();
				String categoryName = category.getName();
				List<Category> categoriesList = new ArrayList<Category>();
				categoriesList = categoryDAO
						.findByNameAllIgnoreCase(categoryName);
				productCustomSearch.setCategories(categoriesList.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));

				String status = ProductStatus.IN_BUILDONN.toString();
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}
				productCustomSearch.setProductStatuses(productStatuses);

				Long count = productDAO.count(productCustomSearch);
				if (count != 0) {
					categorySummary.setCategoryHaveProducts(true);
				} else {
					categorySummary.setCategoryHaveProducts(false);
				}

				categorySummaries.add(categorySummary);
				categories.remove(category);
				break;
			}
		}

		for (Category category : categories) {
			if (category.getName().equalsIgnoreCase("Doors and Windows")) {
				CategorySummary categorySummary = new CategorySummary(category);
				Set<SubCategory> subCategories = subCategoryDAO
						.findByCategory(category.getName());
				categorySummary.setSubCategories(getSubCategorySummary(
						subCategories, productType));

				ProductCustomSearch productCustomSearch = new ProductCustomSearch();
				String categoryName = category.getName();
				List<Category> categoriesList = new ArrayList<Category>();
				categoriesList = categoryDAO
						.findByNameAllIgnoreCase(categoryName);
				productCustomSearch.setCategories(categoriesList.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));

				String status = ProductStatus.IN_BUILDONN.toString();
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}
				productCustomSearch.setProductStatuses(productStatuses);

				Long count = productDAO.count(productCustomSearch);
				if (count != 0) {
					categorySummary.setCategoryHaveProducts(true);
				} else {
					categorySummary.setCategoryHaveProducts(false);
				}

				categorySummaries.add(categorySummary);
				categories.remove(category);
				break;
			}
		}

		for (Category category : categories) {
			if (category.getName().equalsIgnoreCase("Surface Finishes")) {
				CategorySummary categorySummary = new CategorySummary(category);
				Set<SubCategory> subCategories = subCategoryDAO
						.findByCategory(category.getName());
				categorySummary.setSubCategories(getSubCategorySummary(
						subCategories, productType));

				ProductCustomSearch productCustomSearch = new ProductCustomSearch();
				String categoryName = category.getName();
				List<Category> categoriesList = new ArrayList<Category>();
				categoriesList = categoryDAO
						.findByNameAllIgnoreCase(categoryName);
				productCustomSearch.setCategories(categoriesList.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));

				String status = ProductStatus.IN_BUILDONN.toString();
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}
				productCustomSearch.setProductStatuses(productStatuses);

				Long count = productDAO.count(productCustomSearch);
				if (count != 0) {
					categorySummary.setCategoryHaveProducts(true);
				} else {
					categorySummary.setCategoryHaveProducts(false);
				}

				categorySummaries.add(categorySummary);
				categories.remove(category);
				break;
			}
		}

		for (Category category : categories) {
			if (category.getName().equalsIgnoreCase("Interiors")) {
				CategorySummary categorySummary = new CategorySummary(category);
				Set<SubCategory> subCategories = subCategoryDAO
						.findByCategory(category.getName());
				categorySummary.setSubCategories(getSubCategorySummary(
						subCategories, productType));

				ProductCustomSearch productCustomSearch = new ProductCustomSearch();
				String categoryName = category.getName();
				List<Category> categoriesList = new ArrayList<Category>();
				categoriesList = categoryDAO
						.findByNameAllIgnoreCase(categoryName);
				productCustomSearch.setCategories(categoriesList.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));

				String status = ProductStatus.IN_BUILDONN.toString();
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}
				productCustomSearch.setProductStatuses(productStatuses);

				Long count = productDAO.count(productCustomSearch);
				if (count != 0) {
					categorySummary.setCategoryHaveProducts(true);
				} else {
					categorySummary.setCategoryHaveProducts(false);
				}

				categorySummaries.add(categorySummary);
				categories.remove(category);
				break;
			}
		}

		for (Category category : categories) {
			if (category.getName().equalsIgnoreCase("Security Systems")) {
				CategorySummary categorySummary = new CategorySummary(category);
				Set<SubCategory> subCategories = subCategoryDAO
						.findByCategory(category.getName());
				categorySummary.setSubCategories(getSubCategorySummary(
						subCategories, productType));

				ProductCustomSearch productCustomSearch = new ProductCustomSearch();
				String categoryName = category.getName();
				List<Category> categoriesList = new ArrayList<Category>();
				categoriesList = categoryDAO
						.findByNameAllIgnoreCase(categoryName);
				productCustomSearch.setCategories(categoriesList.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));

				String status = ProductStatus.IN_BUILDONN.toString();
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}
				productCustomSearch.setProductStatuses(productStatuses);

				Long count = productDAO.count(productCustomSearch);
				if (count != 0) {
					categorySummary.setCategoryHaveProducts(true);
				} else {
					categorySummary.setCategoryHaveProducts(false);
				}

				categorySummaries.add(categorySummary);
				categories.remove(category);
				break;
			}
		}

		for (Category category : categories) {
			if (category.getName().equalsIgnoreCase("Landscape")) {
				CategorySummary categorySummary = new CategorySummary(category);
				Set<SubCategory> subCategories = subCategoryDAO
						.findByCategory(category.getName());
				categorySummary.setSubCategories(getSubCategorySummary(
						subCategories, productType));

				ProductCustomSearch productCustomSearch = new ProductCustomSearch();
				String categoryName = category.getName();
				List<Category> categoriesList = new ArrayList<Category>();
				categoriesList = categoryDAO
						.findByNameAllIgnoreCase(categoryName);
				productCustomSearch.setCategories(categoriesList.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));

				String status = ProductStatus.IN_BUILDONN.toString();
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}
				productCustomSearch.setProductStatuses(productStatuses);

				Long count = productDAO.count(productCustomSearch);
				if (count != 0) {
					categorySummary.setCategoryHaveProducts(true);
				} else {
					categorySummary.setCategoryHaveProducts(false);
				}

				categorySummaries.add(categorySummary);
				categories.remove(category);
				break;
			}
		}

		for (Category category : categories) {
			if (category.getName().equalsIgnoreCase("Furnitures")) {
				CategorySummary categorySummary = new CategorySummary(category);
				Set<SubCategory> subCategories = subCategoryDAO
						.findByCategory(category.getName());
				categorySummary.setSubCategories(getSubCategorySummary(
						subCategories, productType));

				ProductCustomSearch productCustomSearch = new ProductCustomSearch();
				String categoryName = category.getName();
				List<Category> categoriesList = new ArrayList<Category>();
				categoriesList = categoryDAO
						.findByNameAllIgnoreCase(categoryName);
				productCustomSearch.setCategories(categoriesList.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));

				String status = ProductStatus.IN_BUILDONN.toString();
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}
				productCustomSearch.setProductStatuses(productStatuses);

				Long count = productDAO.count(productCustomSearch);
				if (count != 0) {
					categorySummary.setCategoryHaveProducts(true);
				} else {
					categorySummary.setCategoryHaveProducts(false);
				}

				categorySummaries.add(categorySummary);
				categories.remove(category);
				break;
			}
		}

		for (Category category : categories) {
			if (category.getName().equalsIgnoreCase("Painting")) {
				CategorySummary categorySummary = new CategorySummary(category);
				Set<SubCategory> subCategories = subCategoryDAO
						.findByCategory(category.getName());
				categorySummary.setSubCategories(getSubCategorySummary(
						subCategories, productType));

				ProductCustomSearch productCustomSearch = new ProductCustomSearch();
				String categoryName = category.getName();
				List<Category> categoriesList = new ArrayList<Category>();
				categoriesList = categoryDAO
						.findByNameAllIgnoreCase(categoryName);
				productCustomSearch.setCategories(categoriesList.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));

				String status = ProductStatus.IN_BUILDONN.toString();
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}
				productCustomSearch.setProductStatuses(productStatuses);

				Long count = productDAO.count(productCustomSearch);
				if (count != 0) {
					categorySummary.setCategoryHaveProducts(true);
				} else {
					categorySummary.setCategoryHaveProducts(false);
				}

				categorySummaries.add(categorySummary);
				categories.remove(category);
				break;
			}
		}

		for (Category category : categories) {
			if (category.getName().equalsIgnoreCase("Appliances")) {
				CategorySummary categorySummary = new CategorySummary(category);
				Set<SubCategory> subCategories = subCategoryDAO
						.findByCategory(category.getName());
				categorySummary.setSubCategories(getSubCategorySummary(
						subCategories, productType));

				ProductCustomSearch productCustomSearch = new ProductCustomSearch();
				String categoryName = category.getName();
				List<Category> categoriesList = new ArrayList<Category>();
				categoriesList = categoryDAO
						.findByNameAllIgnoreCase(categoryName);
				productCustomSearch.setCategories(categoriesList.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));

				String status = ProductStatus.IN_BUILDONN.toString();
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}
				productCustomSearch.setProductStatuses(productStatuses);

				Long count = productDAO.count(productCustomSearch);
				if (count != 0) {
					categorySummary.setCategoryHaveProducts(true);
				} else {
					categorySummary.setCategoryHaveProducts(false);
				}

				categorySummaries.add(categorySummary);
				categories.remove(category);
				break;
			}
		}

		for (Category category : categories) {
			if (category.getName().equalsIgnoreCase("Mechanical")) {
				CategorySummary categorySummary = new CategorySummary(category);
				Set<SubCategory> subCategories = subCategoryDAO
						.findByCategory(category.getName());
				categorySummary.setSubCategories(getSubCategorySummary(
						subCategories, productType));

				ProductCustomSearch productCustomSearch = new ProductCustomSearch();
				String categoryName = category.getName();
				List<Category> categoriesList = new ArrayList<Category>();
				categoriesList = categoryDAO
						.findByNameAllIgnoreCase(categoryName);
				productCustomSearch.setCategories(categoriesList.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));

				String status = ProductStatus.IN_BUILDONN.toString();
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}
				productCustomSearch.setProductStatuses(productStatuses);

				Long count = productDAO.count(productCustomSearch);
				if (count != 0) {
					categorySummary.setCategoryHaveProducts(true);
				} else {
					categorySummary.setCategoryHaveProducts(false);
				}

				categorySummaries.add(categorySummary);
				categories.remove(category);
				break;
			}
		}

		for (Category category : categories) {
			if (category.getName().equalsIgnoreCase("Service Providers")) {
				CategorySummary categorySummary = new CategorySummary(category);
				Set<SubCategory> subCategories = subCategoryDAO
						.findByCategory(category.getName());
				categorySummary.setSubCategories(getSubCategorySummary(
						subCategories, productType));

				ProductCustomSearch productCustomSearch = new ProductCustomSearch();
				String categoryName = category.getName();
				List<Category> categoriesList = new ArrayList<Category>();
				categoriesList = categoryDAO
						.findByNameAllIgnoreCase(categoryName);
				productCustomSearch.setCategories(categoriesList.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));

				String status = ProductStatus.IN_BUILDONN.toString();
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}
				productCustomSearch.setProductStatuses(productStatuses);

				Long count = productDAO.count(productCustomSearch);
				if (count != 0) {
					categorySummary.setCategoryHaveProducts(true);
				} else {
					categorySummary.setCategoryHaveProducts(false);
				}

				categorySummaries.add(categorySummary);
				categories.remove(category);
				break;
			}
		}
		if (categories != null && categories.size() != 0) {
			for (Category category : categories) {
				CategorySummary categorySummary = new CategorySummary(category);
				Set<SubCategory> subCategories = subCategoryDAO
						.findByCategory(category.getName());
				categorySummary.setSubCategories(getSubCategorySummary(
						subCategories, productType));

				ProductCustomSearch productCustomSearch = new ProductCustomSearch();
				String categoryName = category.getName();
				List<Category> categoriesList = new ArrayList<Category>();
				categoriesList = categoryDAO
						.findByNameAllIgnoreCase(categoryName);
				productCustomSearch.setCategories(categoriesList.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));

				String status = ProductStatus.IN_BUILDONN.toString();
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}
				productCustomSearch.setProductStatuses(productStatuses);

				Long count = productDAO.count(productCustomSearch);
				if (count != 0) {
					categorySummary.setCategoryHaveProducts(true);
				} else {
					categorySummary.setCategoryHaveProducts(false);
				}

				categorySummaries.add(categorySummary);
			}
		}
		return categorySummaries;
	}

	private List<SubCategorySummary> getSubCategorySummary(
			Collection<SubCategory> subCategories, String productType) {
		List<SubCategorySummary> subCategorySummaries = new ArrayList<SubCategorySummary>();

		if (StringUtils.isBlank(productType)) {
			productType = "YES";
		}

		for (SubCategory subCategory : subCategories) {
			if (subCategory != null) {
				SubCategorySummary subCategorySummary = new SubCategorySummary(
						subCategory);

				if (!productType.equalsIgnoreCase("NO")) {
					Set<ProductType> productTypes = productTypeDAO
							.findBySubCategory(subCategory);

					if (productTypes != null) {
						subCategorySummary
								.setProductTypes(getProductTypeSummary(productTypes));
					}
				}
				subCategorySummaries.add(subCategorySummary);
			}
		}

		return subCategorySummaries;
	}

	private List<ProductTypeSummary> getProductTypeSummary(
			Collection<ProductType> productTypes) {
		List<ProductTypeSummary> productTypeSummaries = new ArrayList<ProductTypeSummary>();
		Map<String, ProductTypeSummary> productTypeSummaryMap = new HashMap<>();
		for (ProductType productType : productTypes) {
			if (productType != null && productType.getId() != null
					&& productType.getName() != null) {
				ProductTypeSummary productTypeSummary = new ProductTypeSummary(
						productType);

				if (!productTypeSummaryMap.containsKey(productType.getName())) {
					productTypeSummaryMap.put(productType.getName(),
							productTypeSummary);
				}
			}
		}
		productTypeSummaries.addAll(productTypeSummaryMap.values());
		return productTypeSummaries;
	}

	@Override
	public CategorySummary getCategorySummary(String cateid) throws Exception {
		if (StringUtils.isNotBlank(cateid)) {
			Category category = categoryDAO.findOne(cateid);
			if (category != null) {
				Set<SubCategory> subCategories = subCategoryDAO
						.findByCategory(category.getName());
				CategorySummary categorySummary = new CategorySummary(category);

				categorySummary
						.setSubCategories(getSubCategorySummaryForCategorySummary(subCategories));

				return categorySummary;
			} else {
				throw new Exception(
						"Category is not exist in our system for category id "
								+ cateid);
			}
		} else {
			throw new Exception("Invalid category id");
		}
	}

	private List<SubCategorySummary> getSubCategorySummaryForCategorySummary(
			Collection<SubCategory> subCategories) {
		List<SubCategorySummary> subCategorySummaries = new ArrayList<SubCategorySummary>();

		for (SubCategory subCategory : subCategories) {
			if (subCategory != null) {
				SubCategorySummary subCategorySummary = new SubCategorySummary(
						subCategory);
				Set<ProductType> productTypes = productTypeDAO
						.findBySubCategory(subCategory);

				if (productTypes != null) {
					subCategorySummary
							.setProductTypes(getProductTypeSummaryForCategorySummary(productTypes));
				}
				subCategorySummary
						.setProductBrands(getBrandSummaryForCategorySummary(subCategory));
				subCategorySummaries.add(subCategorySummary);
			}
		}

		return subCategorySummaries;
	}

	private List<ProductTypeSummary> getProductTypeSummaryForCategorySummary(
			Collection<ProductType> productTypes) {
		List<ProductTypeSummary> productTypeSummaries = new ArrayList<ProductTypeSummary>();
		Map<String, ProductTypeSummary> productTypeSummaryMap = new HashMap<>();

		for (ProductType productType : productTypes) {
			if (productType != null && productType.getId() != null
					&& productType.getName() != null) {
				ProductTypeSummary productTypeSummary = new ProductTypeSummary(
						productType);

				Set<ProductType> productTypeSet = new HashSet<>();
				productTypeSet.add(productType);

				Long count = productDAO.countByProductTypesInAndStatus(
						productTypeSet, ProductStatus.IN_BUILDONN);

				if (count != 0) {
					productTypeSummary.setProductTypeHaveProducts(true);
				} else {
					productTypeSummary.setProductTypeHaveProducts(false);
				}

				if (!productTypeSummaryMap.containsKey(productType.getName())) {
					productTypeSummaryMap.put(productType.getName(),
							productTypeSummary);
				}
			}
		}
		productTypeSummaries.addAll(productTypeSummaryMap.values());
		return productTypeSummaries;
	}

	private List<BrandSummary> getBrandSummaryForCategorySummary(
			SubCategory subCategory) {
		List<BrandSummary> brandSummaries = new ArrayList<>();
		Set<String> subCategoryIds = new HashSet<>();
		subCategoryIds.add(subCategory.getId());
		Set<ProductBrand> brands = brandDAO
				.findBySubCategoryIdsIn(subCategoryIds);
		for (ProductBrand brand : brands) {
			BrandSummary brandSummary = new BrandSummary(brand);
			brandSummaries.add(brandSummary);
		}
		return brandSummaries;
	}

}
