package com.sarvah.mbg.store.dao.mongo;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.store.Store;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author Shiva
 *
 */
public interface StoreDAO extends MongoRepository<Store, String> {
	/**
	 * 
	 * @param dealerId
	 * @return
	 */
	Store findByUserId(String dealerId);

	/**
	 * 
	 * @param dealerId
	 * @param pageable
	 * @return
	 */
	Page<Store> findByUserId(String dealerId, Pageable pageable);

	/**
	 * 
	 * @param dealerId
	 * @return
	 */
	int countByUserId(String dealerId);

	/**
	 * 
	 * @param product
	 * @return
	 */
	Set<Store> findByProductsIn(Product product);

	// @Query(value = "{storename : ?0 }", fields = "{products.id : 1 }")
	Store findByStorename(String store);

	/**
	 * 
	 * @param product
	 * @return
	 */
	long countByProducts(Product product);

	/**
	 * 
	 * @param prodId
	 * @param p
	 * @param d
	 * @return
	 */
	List<Store> findByProducts_IdAndUser_Addresses_LocationNear(String prodId,
			Point p, Distance d);

	long countByProducts(String uid);

	Store findByIdAndUser(String storeId, User dealer);

	Store findByUser(User user);

	@Query("{'storeProductPricings' : {$elemMatch : {'productId':'?0', 'zonalDelivery' : ?1}}}")
	List<Store> findByStoreProductPricings_ProductIdAndStoreProductPricings_ZonalDeliveryIsTrue(
			String id, boolean b);

	@Query("{'storeProductPricings' : {$elemMatch : {'productId':'?0', 'nationalDelivery' : ?1}}}")
	List<Store> findByStoreProductPricings_ProductIdAndStoreProductPricings_NationalDeliveryIsTrue(
			String id, boolean b);

	@Query("{'storeProductPricings' : {$elemMatch : {'productId':'?0', $or:[{'localDelivery': ?1}, {'zonalDelivery' : ?2}, {'nationalDelivery' : ?3}]}}}")
	List<Store> findByStoreProductPricings_ProductIdAndLocalDeliveryIsTrueOrZonalDeliveryIsTrueOrNationalDeliveryIsTrue(
			String productId, boolean isLocalDelivery, boolean isZonalDelivery,
			boolean isNationalDelivery);

	Set<Store> findBySubcategoriesInAndProductsIn(
			Set<SubCategory> subCategoriesFinal, List<Product> products);

	Set<Store> findBySubcategoriesIn(Set<SubCategory> subCategoriesFinal);

	Set<Store> findByBrandsIn(Set<ProductBrand> brands);

	Set<Store> findBySubcategoriesInAndBrandsIn(
			Set<SubCategory> subCategoriesFinal, Set<ProductBrand> brands);

	Set<Store> findBySubcategoriesInAndBrandsInAndProductsIn(
			Set<SubCategory> subCategoriesFinal, Set<ProductBrand> brands,
			List<Product> products);

	Set<Store> findByProductsIn(List<Product> products);

	Set<Store> findByBrandsInAndProductsIn(Set<ProductBrand> brands,
			List<Product> products);

	Store findByUser_Id(String dealerId);

	Store findByUserAndProducts_Id(User user, String pid);

	Set<Store> findByBrandsIn(ProductBrand brand);
}
