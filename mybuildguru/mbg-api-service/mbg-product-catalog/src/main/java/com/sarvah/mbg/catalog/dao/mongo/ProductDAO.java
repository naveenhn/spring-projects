/**
 * 
 */
package com.sarvah.mbg.catalog.dao.mongo;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.ProductStatus;
import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author naveen
 *
 */
public interface ProductDAO extends MongoRepository<Product, String>,
		ProductDAOCustom {

	/**
	 * FindByNameLike where you are searching for any product with that name
	 * 
	 * @param name
	 *            (name can be reqex)
	 * @return List<Product>
	 */
	List<Product> findByNameLike(String name);

	/**
	 * Find by skuID
	 * 
	 * @param skuid
	 * @return
	 */
	List<Product> findBySkuIdAndName(String skuid, String name);

	Product findById(String pid);

	Page<Product> findByBrand_Provider_Id(String userId, Pageable pageable);

	Long countByBrand_Provider_Id(String userId);

	List<Product> findByNameAllIgnoreCase(String name);

	List<Product> findByBrand_Provider_Id(String id);

	List<Product> findByCreatedDateOrderByCreatedDateDesc(String date);

	Page<Product> findByNameAllIgnoreCase(String searchValue1,
			String searchValue2, String searchValue3, String searchValue4,
			Pageable pageable);

	Page<Product> findBySubcategories(SubCategory sc, Pageable pageable);

	Page<Product> findByprice_MrpBetween(Double from, Double to,
			Pageable pageable);

	Page<Product> findByPrice_Mrp(Double price, Pageable pageable);

	List<Product> findByAccessory(boolean accessory, Pageable pageable);

	Product findBySkuId(String skuid);

	String deleteById(String id);

	List<Product> findTop50AllByOrderByCreatedDateDesc();

	long countByName(String productName);

	long countBySubcategories_Category(String category);

	long countByproductTypes_Name(String productsType);

	long countByprice_MrpBetween(Double double1, double d,
			PageRequest pageRequest);

	List<Product> findByBrand_ProviderAllIgnoreCase(User provider);

	long countByprice_MrpBetween(Double double1, double d);

	Page<Product> findByStatus(String status, Pageable pageable);

	long countByAccessory(boolean accessoryBoolean);

	long countByStatus(String status);

	Page<Product> findByModel(String model, Pageable pageable);

	Page<Product> findByBrand_Name(String brand, Pageable pageable);

	List<Product> findByBrand(ProductBrand brand);

	List<Product> findByCreatedBy(String uid);

	Page<Product> findByProductTypes_NameAndPrice_MrpBetweenAndBrand(
			String productsType, double min, double max,
			ProductBrand productBrand, Pageable pageable);

	Page<Product> findByProductTypes_NameAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetween(
			String productsType, double min, double max, double ratMin,
			double ratMax, Pageable pageable);

	Page<Product> findByProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			String productsType, double ratMin, double ratMax,
			ProductBrand productBrand, Pageable pageable);

	Page<Product> findByPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			double min, double max, double ratMin, double ratMax,
			ProductBrand productBrand, Pageable pageable);

	Page<Product> findByProductTypes_NameAndPrice_MrpBetween(
			String productsType, double min, double max, Pageable pageable);

	Page<Product> findByProductTypes_NameAndBrand(String productsType,
			ProductBrand productBrand, Pageable pageable);

	Page<Product> findByProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetween(
			String productsType, double ratMin, double ratMax, Pageable pageable);

	Page<Product> findByPrice_MrpBetweenAndBrand(double min, double max,
			ProductBrand productBrand, Pageable pageable);

	Page<Product> findByPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetween(
			double min, double max, double ratMin, double ratMax,
			Pageable pageable);

	Page<Product> findByConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			double ratMin, double ratMax, ProductBrand productBrand,
			Pageable pageable);

	Page<Product> findByPrice_MrpBetween(double min, double max,
			Pageable pageable);

	Page<Product> findByConsolidatedRating_AvgRating_RatingValBetween(
			double ratMin, double ratMax, Pageable pageable);

	Page<Product> findByBrand(ProductBrand productBrand, Pageable pageable);

	List<Product> findByPrice_MrpBetween(double min, double max);

	long countBySubcategories(SubCategory subCategoryObj);

	List<Product> findByConsolidatedRating_AvgRating_RatingValBetween(
			double min, double max);

	List<Product> findByStatus(ProductStatus inMbg);

	Page<Product> findByStatus(ProductStatus inMbg, Pageable pagable);

	Long countByStatus(ProductStatus inMbg);

	Page<Product> findByStatusAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			ProductStatus inMbg, String searchValue, String searchValue2,
			String searchValue3, String searchValue4, Pageable pageable);

	Long countByStatusAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			ProductStatus inMbg, String searchVal1, String searchVal2,
			String searchVal3, String searchVal4);

	Page<Product> findByStatusAndProductTypes_NameAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, String productsType, double min, double max,
			double ratMin, double ratMax, Pageable pageable);

	Page<Product> findByStatusAndProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, String productsType, double ratMin,
			double ratMax, ProductBrand productBrand, Pageable pageable);

	Page<Product> findByStatusAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, double min, double max, double ratMin,
			double ratMax, ProductBrand productBrand, Pageable pageable);

	Page<Product> findByStatusAndProductTypes_NameAndPrice_MrpBetween(
			ProductStatus inMbg, String productsType, double min, double max,
			Pageable pageable);

	Page<Product> findByStatusAndProductTypes_NameAndBrand(ProductStatus inMbg,
			String productsType, ProductBrand productBrand, Pageable pageable);

	Page<Product> findByStatusAndProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, String productsType, double ratMin,
			double ratMax, Pageable pageable);

	Page<Product> findByStatusAndPrice_MrpBetweenAndBrand(ProductStatus inMbg,
			double min, double max, ProductBrand productBrand, Pageable pageable);

	Page<Product> findByStatusAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, double min, double max, double ratMin,
			double ratMax, Pageable pageable);

	Page<Product> findByStatusAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, double ratMin, double ratMax,
			ProductBrand productBrand, Pageable pageable);

	Page<Product> findByStatusAndProductTypes_NameAllIgnoreCase(
			ProductStatus inMbg, String productsType, Pageable pageable);

	Page<Product> findByStatusAndPrice_MrpBetween(ProductStatus inMbg,
			double min, double max, Pageable pageable);

	Page<Product> findByStatusAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, double ratMin, double ratMax, Pageable pageable);

	Page<Product> findByStatusAndBrand_Provider_Id(ProductStatus inMbg,
			String userId, Pageable pageable);

	Page<Product> findByStatusAndModel(ProductStatus inMbg, String model,
			Pageable pageable);

	Page<Product> findByStatusAndSubcategories(ProductStatus inMbg,
			SubCategory subCategoryObj, Pageable pageable);

	List<Product> findByStatusAndAccessory(ProductStatus inMbg,
			boolean accessoryBoolean, Pageable pageable);

	List<Product> findByStatusAndSubcategories(ProductStatus inMbg,
			SubCategory subCategoryObj);

	List<Product> findByStatusAndAccessory(ProductStatus inMbg,
			boolean accessoryBoolean);

	List<Product> findByStatusAndModel(ProductStatus inMbg, String model);

	List<Product> findByStatusAndBrand_Provider_Id(ProductStatus inMbg,
			String userId);

	List<Product> findByStatusAndBrand(ProductStatus inMbg,
			ProductBrand brandName);

	List<Product> findByStatusAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, double min, double max);

	List<Product> findByStatusAndPrice_MrpBetween(ProductStatus inMbg,
			double min, double max);

	Long countByStatusAndProductTypes_NameAllIgnoreCase(ProductStatus inMbg,
			String string);

	Long countByStatusAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, double ratMin, double ratMax,
			ProductBrand productBrand);

	Long countByStatusAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, double min, double max, double ratMin,
			double ratMax);

	Long countByStatusAndPrice_MrpBetweenAndBrand(ProductStatus inMbg,
			double min, double max, ProductBrand productBrand);

	Long countByStatusAndProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, String productsType, double ratMin,
			double ratMax);

	Long countByStatusAndProductTypes_NameAndBrand(ProductStatus inMbg,
			String productsType, ProductBrand productBrand);

	Long countByStatusAndProductTypes_NameAndPrice_MrpBetween(
			ProductStatus inMbg, String productsType, double min, double max);

	Long countByStatusAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, double ratMin, double ratMax);

	Long countByStatusAndBrand(ProductStatus inMbg, ProductBrand productBrand);

	Long countByStatusAndPrice_MrpBetween(ProductStatus inMbg, double min,
			double max);

	Long countByStatusAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, double min, double max, double ratMin,
			double ratMax, ProductBrand productBrand);

	Long countByStatusAndProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, String productsType, double ratMin,
			double ratMax, ProductBrand productBrand);

	Long countByStatusAndProductTypes_NameAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, String productsType, double min, double max,
			double ratMin, double ratMax);

	Long countByStatusAndProductTypes_NameAndPrice_MrpBetweenAndBrand(
			ProductStatus inMbg, String productsType, double min, double max,
			ProductBrand productBrand);

	Long countByStatusAndProductTypes_NameAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, String productsType, double min, double max,
			double ratMin, double ratMax, ProductBrand productBrand);

	long countByBrand(ProductBrand brand);

	Page<Product> findByStatusOrStatusOrStatus(ProductStatus inMbg,
			ProductStatus approvalPending, ProductStatus saved,
			Pageable pageable);

	Long countByStatusOrStatus(ProductStatus inMbg,
			ProductStatus approvalPending);

	List<Product> findByStatusAndProductTypes_NameAllIgnoreCase(
			ProductStatus inMbg, String string);

	Page<Product> findByBrand_Provider(User user, Pageable pageable);

	List<Product> findByStatusAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, double ratMin, double ratMax,
			ProductBrand productBrand);

	List<Product> findByStatusAndPrice_MrpBetweenAndBrand(ProductStatus inMbg,
			double min, double max, ProductBrand productBrand);

	List<Product> findByStatusAndProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, String productsType, double ratMin,
			double ratMax);

	List<Product> findByStatusAndProductTypes_NameAndBrand(ProductStatus inMbg,
			String string, ProductBrand productBrand);

	List<Product> findByStatusAndProductTypes_NameAndPrice_MrpBetween(
			ProductStatus inMbg, String string, double min, double max);

	List<Product> findByStatusAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, double min, double max, double ratMin,
			double ratMax, ProductBrand productBrand);

	List<Product> findByStatusAndProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, String productsType, double ratMin,
			double ratMax, ProductBrand productBrand);

	List<Product> findByStatusAndProductTypes_NameAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, String productsType, double min, double max,
			double ratMin, double ratMax);

	List<Product> findByStatusAndProductTypes_NameAndPrice_MrpBetweenAndBrand(
			ProductStatus inMbg, String productsType, double min, double max,
			ProductBrand productBrand);

	List<Product> findByStatusAndProductTypes_NameAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, String productsType, double min, double max,
			double ratMin, double ratMax, ProductBrand productBrand);

	Page<Product> findByStatusAndBrandAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			ProductStatus valueOf, ProductBrand productBrand,
			String searchValue, String searchValue2, String searchValue3,
			String searchValue4, Pageable pageable);

	Page<Product> findByNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			String searchValue, String searchValue2, String searchValue3,
			String searchValue4, Pageable pageable);

	Page<Product> findBySubcategories_NameInAndProductTypes_NameIn(
			List<String> subCatNames, List<String> prodTypeNames,
			Pageable pageable);

	Page<Product> findBySubcategories_NameInOrProductTypes_NameIn(
			List<String> subCatNames, List<String> prodTypeNames,
			Pageable pageable);

	Page<Product> findBySubcategories_NameAndProductTypes_Name(
			String subCatName, String productTypeName, Pageable pageable);

	Page<Product> findByStatusAndProductTypes_NameAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, String string, double min, double max,
			double ratMin, double ratMax, ProductBrand productBrand,
			Pageable pageable);

	Page<Product> findByStatusAndProductTypes_NameAndPrice_MrpBetweenAndBrand(
			ProductStatus inMbg, String string, double min, double max,
			ProductBrand productBrand, Pageable pageable);

	Page<Product> findByStatusInAndBrandIn(List<ProductStatus> statusList,
			List<ProductBrand> brandList, Pageable pageable);

	Page<Product> findByBrandIn(List<ProductBrand> brandList, Pageable pageable);

	Page<Product> findByStatusIn(List<ProductStatus> statusList,
			Pageable pageable);

	Page<Product> findByBrandAndStatusIn(ProductBrand brand,
			List<ProductStatus> statusList, Pageable pageable);

	Page<Product> findByBrandAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			ProductBrand brand, String searchValue, String searchValue2,
			String searchValue3, String searchValue4, Pageable pageable);

	Page<Product> findByStatusInAndBrandInAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			List<ProductStatus> statusList, List<ProductBrand> brandList,
			String searchValue, String searchValue2, String searchValue3,
			String searchValue4, Pageable pageable);

	Page<Product> findByBrandInAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			List<ProductBrand> brandList, String searchValue,
			String searchValue2, String searchValue3, String searchValue4,
			Pageable pageable);

	Page<Product> findByStatusInAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			List<ProductStatus> statusList, String searchValue,
			String searchValue2, String searchValue3, String searchValue4,
			Pageable pageable);

	Page<Product> findByBrandAndStatusInAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			ProductBrand brand, List<ProductStatus> statusList,
			String searchValue, String searchValue2, String searchValue3,
			String searchValue4, Pageable pageable);

	List<Product> findByNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			String searchText, String searchText2, String searchText3,
			String searchText4);

	Long countByProductTypesInAndStatus(Set<ProductType> productTypeSet,
			ProductStatus inBuildonn);

}