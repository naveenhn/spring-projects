/**
 * 
 */
package com.sarvah.mbg.catalog.service;

import java.util.List;
import java.util.Set;

import com.sarvah.mbg.catalog.service.model.CategorySummary;
import com.sarvah.mbg.domain.mongo.catalog.Category;
import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;

/**
 * @author shivu
 *
 */
public interface ProductCategoryService {

	/**
	 * Method to get all categories
	 * 
	 * @return
	 */
	List<Category> getAllCategories();

	/**
	 * method for searching the categories
	 * 
	 * @param name
	 * 
	 * @return
	 * 
	 * @throws MBGAppException
	 */
	List<CategorySummary> getCategoriesSummary(String productType);

	CategorySummary getCategorySummary(String cateid) throws Exception;

	/**
	 * Method to category count
	 * 
	 * @return
	 */
	long getCategoryCount();

	/**
	 * Method to search category by name
	 * 
	 * @param name
	 * @return
	 */
	List<Category> searchCategories(String name);

	/**
	 * Method to get count of category
	 * 
	 * @param name
	 * @return
	 */
	Long getCategoryCount(String name);

	/**
	 * Method to create category
	 * 
	 * @param name
	 * @param desc
	 * @return
	 * @throws Exception
	 */
	Category createCategory(String name, String desc) throws Exception;

	/**
	 * method for searching the categories based on categoryId.
	 * 
	 * @paramcatid
	 * @return
	 * @throwsMBGAppException
	 */
	Category searchCategoriesById(String cateid);

	/**
	 * method for update category based on categoryId.
	 * 
	 * @param catid
	 * @param categoryUpdateRequestParam
	 * @return
	 */
	Category updateCategory(String catid, String name, String desc)
			throws Exception;

	/**
	 * Method to delete a category
	 * 
	 * @param catId
	 * @return
	 * @throws Exception
	 */
	String deleteCategory(String catId) throws Exception;

	/**
	 * method for get the Subcategories based on categoryId.
	 * 
	 * @paramsubcat
	 * @return
	 * @throwsMBGAppException
	 */
	Set<SubCategory> searchSubCategories(String cateid);

	/**
	 * Method to create subcategory
	 * 
	 * @param catid
	 * @param desc
	 * @param subcatName
	 * @return
	 * @throws Exception
	 */
	SubCategory createSubCategory(String catid, String desc, String subcatName)
			throws Exception;

	/**
	 * method for get the subcategory based on subcategoryId.
	 * 
	 * @paramcatid
	 * @PathParamsubcatid
	 * @return
	 * @throwsMBGAppException
	 */
	SubCategory searchSubCategorieById(String cateid, String subcatid);

	/**
	 * method for update subCategory based on subcategoryId.
	 * 
	 * @param catid
	 * @param subcatid
	 * @param subCategoryUpdateRequestParam
	 * @return
	 */
	SubCategory updateSubCategory(String catid, String subcatid, String name,
			String desc) throws Exception;

	/**
	 * Method to delete subcategory
	 * 
	 * @param cateid
	 * @param subcatid
	 * @return
	 */
	String deleteSubCategorieById(String cateid, String subcatid);

	/**
	 * Method to get product type
	 * 
	 * @param catId
	 * @param subcatId
	 * @return
	 * @throws Exception
	 */
	List<ProductType> getProductType(String catId, String subcatId)
			throws Exception;

	/**
	 * Method to create product type
	 * 
	 * @param catid
	 * @param subcatid
	 * @param name
	 * @param desc
	 * @return
	 * @throws Exception
	 */
	ProductType createProductType(String catid, String subcatid, String name,
			String desc) throws Exception;

	/**
	 * method for update ProductType.
	 * 
	 * @param catid
	 * @param subcatid
	 * @param prodtypid
	 * @param productTypeUpdateRequestParam
	 * @return
	 */
	ProductType updateProductType(String catid, String subcatid,
			String prodtypeid, String name, String desc) throws Exception;

	/**
	 * Method to delete a productType
	 * 
	 * @param catid
	 * @param subcatid
	 * @param prodtypeid
	 * @return
	 * @throws Exception
	 */
	String deleteProductType(String catid, String subcatid, String prodtypeid)
			throws Exception;
}
