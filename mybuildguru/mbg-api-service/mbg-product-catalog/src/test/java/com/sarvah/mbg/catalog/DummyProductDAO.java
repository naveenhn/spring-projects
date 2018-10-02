package com.sarvah.mbg.catalog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sarvah.mbg.catalog.dao.mongo.ProductDAO;
import com.sarvah.mbg.catalog.service.model.ProductCustomSearch;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.ProductStatus;
import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.Price;
import com.sarvah.mbg.domain.mongo.userprofile.User;

public class DummyProductDAO implements ProductDAO {

	@Override
	public <S extends Product> List<S> save(Iterable<S> entites) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Product> S insert(S entity) {
		// TODO Auto-generated method stub
		Product product = new Product();
		product.setSkuId(entity.getSkuId());
		product.setName(entity.getName());
		Description desc = new Description();
		product.setDesc(desc);

		return entity;
	}

	@Override
	public <S extends Product> List<S> insert(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Product> S save(S entity) {
		// TODO Auto-generated method stub
		return entity;
	}

	@Override
	public Product findOne(String id) {
		// TODO Auto-generated method stub

		Product product = new Product();
		User user = new User();
		user.setId("123");
		user.setFirstName("ramesh");
		product.setName("Bath tub");
		// product.setProvider(user);

		product.setId(id);
		Description desc = new Description();
		product.setDesc(desc);
		Price price = new Price();
		product.setPrice(price);
		Set<String> relatedIds = new HashSet<>();
		relatedIds.add("11111");
		relatedIds.add("22222");
		product.setRelatedProducts(relatedIds);
		Set<String> accessoryIds = new HashSet<>();
		accessoryIds.add("125");
		accessoryIds.add("250");
		product.setAccessories(accessoryIds);
		return product;

	}

	@Override
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Product> findAll(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Product entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends Product> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Product> findByNameLike(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findBySkuIdAndName(String skuid, String name) {
		// TODO Auto-generated method stub
		List<Product> productList = new ArrayList<>();

		Product product1 = new Product();
		product1.setId("ab1290345jfk59");
		product1.setName("XYZ-ABC");
		Product product2 = new Product();
		product2.setId("ab1290345jfk59");
		product2.setName("XYZ-ABC");
		productList.add(product1);
		productList.add(product2);

		return productList;
	}

	/*
	 * @Override public Page<Product> findByProvider_Id(String userId, Pageable
	 * pageable) { // TODO Auto-generated method stub // Product product=new
	 * Product(); // product.setName("bath tubs"); //
	 * 
	 * @SuppressWarnings("unchecked") // Page<Product>
	 * productPage=(Page<Product>) new PageRequest(0,20);
	 * 
	 * return new Page<Product>() {
	 * 
	 * @Override public Iterator<Product> iterator() { // TODO Auto-generated
	 * method stub return null; }
	 * 
	 * @Override public Pageable previousPageable() { // TODO Auto-generated
	 * method stub return null; }
	 * 
	 * @Override public Pageable nextPageable() { // TODO Auto-generated method
	 * stub return null; }
	 * 
	 * @Override public boolean isLast() { // TODO Auto-generated method stub
	 * return false; }
	 * 
	 * @Override public boolean isFirst() { // TODO Auto-generated method stub
	 * return false; }
	 * 
	 * @Override public boolean hasPrevious() { // TODO Auto-generated method
	 * stub return false; }
	 * 
	 * @Override public boolean hasNext() { // TODO Auto-generated method stub
	 * return false; }
	 * 
	 * @Override public boolean hasContent() { // TODO Auto-generated method
	 * stub return false; }
	 * 
	 * @Override public Sort getSort() { // TODO Auto-generated method stub
	 * return null; }
	 * 
	 * @Override public int getSize() { // TODO Auto-generated method stub
	 * return 10; }
	 * 
	 * @Override public int getNumberOfElements() { // TODO Auto-generated
	 * method stub return 0; }
	 * 
	 * @Override public int getNumber() { // TODO Auto-generated method stub
	 * return 0; }
	 * 
	 * @Override public List<Product> getContent() { // TODO Auto-generated
	 * method stub return null; }
	 * 
	 * @Override public int getTotalPages() { // TODO Auto-generated method stub
	 * return 0; }
	 * 
	 * @Override public long getTotalElements() { // TODO Auto-generated method
	 * stub return 0; } }; }
	 */

	@Override
	public Product findBySkuId(String skuid) {
		// TODO Auto-generated method stub
		Product product = new Product();
		product.setId("ab1290345jfk59");
		product.setSkuId(skuid);
		product.setName("XYZ-ABC");
		Description desc = new Description();
		Price price = new Price();
		product.setDesc(desc);
		product.setPrice(price);
		return product;
	}

	@Override
	public List<Product> findByCreatedDateOrderByCreatedDateDesc(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Product> findTop2AllByOrderByCreatedDateDesc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countByName(String productName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long countByproductTypes_Name(String productsType) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String deleteById(String id) {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public List<Product> findTop50AllByOrderByCreatedDateDesc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByAccessory(boolean accessory, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countByAccessory(boolean accessoryBoolean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long countByStatus(String status) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Product findById(String pid) {
		Product product = new Product();
		product.setId("ab1290345jfk59");
		return product;
	}

	@Override
	public List<Product> findByNameAllIgnoreCase(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @Override public List<Product> findByProviderAllIgnoreCase(User provider)
	 * { // TODO Auto-generated method stub return null; }
	 */

	@Override
	public Page<Product> findBySubcategories(SubCategory sc, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByModel(String model, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByBrand_Provider_Id(String userId,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByBrand_Provider_Id(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByBrand_Provider_Id(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByBrand_ProviderAllIgnoreCase(User provider) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByBrand(ProductBrand brand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByBrand_Name(String brand, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countByBrand(ProductBrand brand) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Product> findByCreatedBy(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			String productsType, double ratMin, double ratMax,
			ProductBrand productBrand, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByProductTypes_NameAndBrand(String productsType,
			ProductBrand productBrand, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetween(
			String productsType, double ratMin, double ratMax, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			double ratMin, double ratMax, ProductBrand productBrand,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByConsolidatedRating_AvgRating_RatingValBetween(
			double ratMin, double ratMax, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByBrand(ProductBrand productBrand,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countBySubcategories(SubCategory subCategoryObj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Product> findByStatus(ProductStatus status) {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public Page<Product> findBySubcategories_Category_Id(String categoryId) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public List<Product> findByConsolidatedRating_AvgRating_RatingValBetween(
			double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatus(ProductStatus inMbg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatus(ProductStatus inMbg, Pageable pagable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			ProductStatus inMbg, String searchValue, String searchValue2,
			String searchValue3, String searchValue4, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatusAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			ProductStatus inMbg, String searchVal1, String searchVal2,
			String searchVal3, String searchVal4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndProductTypes_NameAndBrand(
			ProductStatus inMbg, String productsType,
			ProductBrand productBrand, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, String productsType, double ratMin,
			double ratMax, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, double ratMin, double ratMax,
			ProductBrand productBrand, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndProductTypes_NameAllIgnoreCase(
			ProductStatus inMbg, String productsType, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, double ratMin, double ratMax, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndBrand_Provider_Id(ProductStatus inMbg,
			String userId, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndModel(ProductStatus inMbg,
			String model, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndSubcategories(ProductStatus inMbg,
			SubCategory subCategoryObj, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByStatusAndAccessory(ProductStatus inMbg,
			boolean accessoryBoolean, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByStatusAndSubcategories(ProductStatus inMbg,
			SubCategory subCategoryObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByStatusAndAccessory(ProductStatus inMbg,
			boolean accessoryBoolean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByStatusAndModel(ProductStatus inMbg, String model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByStatusAndBrand_Provider_Id(ProductStatus inMbg,
			String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByStatusAndBrand(ProductStatus inMbg,
			ProductBrand brandName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByStatusAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatusAndProductTypes_NameAllIgnoreCase(
			ProductStatus inMbg, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatusAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, double ratMin, double ratMax,
			ProductBrand productBrand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatusAndProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, String productsType, double ratMin,
			double ratMax) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatusAndProductTypes_NameAndBrand(ProductStatus inMbg,
			String productsType, ProductBrand productBrand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatusAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, double ratMin, double ratMax) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatusAndBrand(ProductStatus inMbg,
			ProductBrand productBrand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatusAndProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, String productsType, double ratMin,
			double ratMax, ProductBrand productBrand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatusOrStatus(ProductStatus inMbg,
			ProductStatus approvalPending) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByStatusAndProductTypes_NameAllIgnoreCase(
			ProductStatus inMbg, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByBrand_Provider(User user, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.catalog.dao.mongo.ProductDAOCustom#searchProducts(com.
	 * sarvah.mbg.catalog.service.model.ProductCustomSearch)
	 */
	@Override
	public List<Product> searchProducts(ProductCustomSearch productCustomSearch) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.catalog.dao.mongo.ProductDAOCustom#searchProducts(com.
	 * sarvah.mbg.catalog.service.model.ProductCustomSearch,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> searchProducts(
			ProductCustomSearch productCustomSearch, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.catalog.dao.mongo.ProductDAOCustom#count(com.sarvah.mbg
	 * .catalog.service.model.ProductCustomSearch)
	 */
	@Override
	public long count(ProductCustomSearch productCustomSearch) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.catalog.dao.mongo.ProductDAO#findByNameAllIgnoreCase(java
	 * .lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findByNameAllIgnoreCase(String searchValue1,
			String searchValue2, String searchValue3, String searchValue4,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.catalog.dao.mongo.ProductDAO#findByStatus(java.lang.String
	 * , org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findByStatus(String status, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.catalog.dao.mongo.ProductDAO#
	 * findByStatusAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand
	 * (com.sarvah.mbg.domain.mongo.catalog.ProductStatus, double, double,
	 * com.sarvah.mbg.domain.mongo.catalog.ProductBrand)
	 */
	@Override
	public List<Product> findByStatusAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, double ratMin, double ratMax,
			ProductBrand productBrand) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.catalog.dao.mongo.ProductDAO#
	 * findByStatusAndProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetween
	 * (com.sarvah.mbg.domain.mongo.catalog.ProductStatus, java.lang.String,
	 * double, double)
	 */
	@Override
	public List<Product> findByStatusAndProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, String productsType, double ratMin,
			double ratMax) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.catalog.dao.mongo.ProductDAO#
	 * findByStatusAndProductTypes_NameAndBrand
	 * (com.sarvah.mbg.domain.mongo.catalog.ProductStatus, java.lang.String,
	 * com.sarvah.mbg.domain.mongo.catalog.ProductBrand)
	 */
	@Override
	public List<Product> findByStatusAndProductTypes_NameAndBrand(
			ProductStatus inMbg, String string, ProductBrand productBrand) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.catalog.dao.mongo.ProductDAO#
	 * findByStatusAndProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand
	 * (com.sarvah.mbg.domain.mongo.catalog.ProductStatus, java.lang.String,
	 * double, double, com.sarvah.mbg.domain.mongo.catalog.ProductBrand)
	 */
	@Override
	public List<Product> findByStatusAndProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, String productsType, double ratMin,
			double ratMax, ProductBrand productBrand) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.catalog.dao.mongo.ProductDAO#
	 * findByStatusAndBrandAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuidAllIgnoreCase
	 * (com.sarvah.mbg.domain.mongo.catalog.ProductStatus,
	 * com.sarvah.mbg.domain.mongo.catalog.ProductBrand, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findByStatusAndBrandAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			ProductStatus valueOf, ProductBrand productBrand,
			String searchValue, String searchValue2, String searchValue3,
			String searchValue4, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.catalog.dao.mongo.ProductDAO#
	 * findByNameLikeOrLongNameLikeOrDesc_valLikeOrSkuidAllIgnoreCase
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findByNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			String searchValue, String searchValue2, String searchValue3,
			String searchValue4, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.catalog.dao.mongo.ProductDAO#
	 * findBySubcategories_NameInAndProductTypes_NameIn(java.util.List,
	 * java.util.List, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findBySubcategories_NameInAndProductTypes_NameIn(
			List<String> subCatNames, List<String> prodTypeNames,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.catalog.dao.mongo.ProductDAO#
	 * findBySubcategories_NameInOrProductTypes_NameIn(java.util.List,
	 * java.util.List, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findBySubcategories_NameInOrProductTypes_NameIn(
			List<String> subCatNames, List<String> prodTypeNames,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.catalog.dao.mongo.ProductDAO#
	 * findBySubcategories_NameAndProductTypes_Name(java.lang.String,
	 * java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findBySubcategories_NameAndProductTypes_Name(
			String subCatName, String productTypeName, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.catalog.dao.mongo.ProductDAO#findByStatusInAndBrandIn(
	 * java.util.List, java.util.List, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findByStatusInAndBrandIn(
			List<ProductStatus> statusList, List<ProductBrand> brandList,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.catalog.dao.mongo.ProductDAO#findByBrandIn(java.util.List,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findByBrandIn(List<ProductBrand> brandList,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.catalog.dao.mongo.ProductDAO#findByStatusIn(java.util.
	 * List, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findByStatusIn(List<ProductStatus> statusList,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.catalog.dao.mongo.ProductDAO#findByBrandAndStatusIn(com
	 * .sarvah.mbg.domain.mongo.catalog.ProductBrand, java.util.List,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findByBrandAndStatusIn(ProductBrand brand,
			List<ProductStatus> statusList, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.catalog.dao.mongo.ProductDAO#
	 * findByBrandAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuidAllIgnoreCase
	 * (com.sarvah.mbg.domain.mongo.catalog.ProductBrand, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findByBrandAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			ProductBrand brand, String searchValue, String searchValue2,
			String searchValue3, String searchValue4, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.catalog.dao.mongo.ProductDAO#
	 * findByStatusInAndBrandInAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuidAllIgnoreCase
	 * (java.util.List, java.util.List, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findByStatusInAndBrandInAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			List<ProductStatus> statusList, List<ProductBrand> brandList,
			String searchValue, String searchValue2, String searchValue3,
			String searchValue4, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.catalog.dao.mongo.ProductDAO#
	 * findByBrandInAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuidAllIgnoreCase
	 * (java.util.List, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findByBrandInAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			List<ProductBrand> brandList, String searchValue,
			String searchValue2, String searchValue3, String searchValue4,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.catalog.dao.mongo.ProductDAO#
	 * findByStatusInAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuidAllIgnoreCase
	 * (java.util.List, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findByStatusInAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			List<ProductStatus> statusList, String searchValue,
			String searchValue2, String searchValue3, String searchValue4,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.catalog.dao.mongo.ProductDAO#
	 * findByBrandAndStatusInAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuidAllIgnoreCase
	 * (com.sarvah.mbg.domain.mongo.catalog.ProductBrand, java.util.List,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Product> findByBrandAndStatusInAndNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			ProductBrand brand, List<ProductStatus> statusList,
			String searchValue, String searchValue2, String searchValue3,
			String searchValue4, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.catalog.dao.mongo.ProductDAO#
	 * findByNameLikeOrLongNameLikeOrDesc_valLikeOrSkuidAllIgnoreCase
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Product> findByNameLikeOrLongNameLikeOrDesc_valLikeOrSkuIdAllIgnoreCase(
			String searchText, String searchText2, String searchText3,
			String searchText4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusOrStatusOrStatus(ProductStatus inMbg,
			ProductStatus approvalPending, ProductStatus saved,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByprice_MrpBetween(Double from, Double to,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByPrice_Mrp(Double price, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countByprice_MrpBetween(Double double1, double d,
			PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long countByprice_MrpBetween(Double double1, double d) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Page<Product> findByProductTypes_NameAndPrice_MrpBetweenAndBrand(
			String productsType, double min, double max,
			ProductBrand productBrand, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByProductTypes_NameAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetween(
			String productsType, double min, double max, double ratMin,
			double ratMax, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			double min, double max, double ratMin, double ratMax,
			ProductBrand productBrand, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByProductTypes_NameAndPrice_MrpBetween(
			String productsType, double min, double max, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByPrice_MrpBetweenAndBrand(double min, double max,
			ProductBrand productBrand, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetween(
			double min, double max, double ratMin, double ratMax,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByPrice_MrpBetween(double min, double max,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByPrice_MrpBetween(double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndProductTypes_NameAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, String productsType, double min, double max,
			double ratMin, double ratMax, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndProductTypes_NameAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, String productsType, double ratMin,
			double ratMax, ProductBrand productBrand, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, double min, double max, double ratMin,
			double ratMax, ProductBrand productBrand, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndProductTypes_NameAndPrice_MrpBetween(
			ProductStatus inMbg, String productsType, double min, double max,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndPrice_MrpBetweenAndBrand(
			ProductStatus inMbg, double min, double max,
			ProductBrand productBrand, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, double min, double max, double ratMin,
			double ratMax, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndPrice_MrpBetween(ProductStatus inMbg,
			double min, double max, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByStatusAndPrice_MrpBetween(ProductStatus inMbg,
			double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatusAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, double min, double max, double ratMin,
			double ratMax) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatusAndPrice_MrpBetweenAndBrand(ProductStatus inMbg,
			double min, double max, ProductBrand productBrand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatusAndProductTypes_NameAndPrice_MrpBetween(
			ProductStatus inMbg, String productsType, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatusAndPrice_MrpBetween(ProductStatus inMbg,
			double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatusAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, double min, double max, double ratMin,
			double ratMax, ProductBrand productBrand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatusAndProductTypes_NameAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, String productsType, double min, double max,
			double ratMin, double ratMax) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatusAndProductTypes_NameAndPrice_MrpBetweenAndBrand(
			ProductStatus inMbg, String productsType, double min, double max,
			ProductBrand productBrand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByStatusAndProductTypes_NameAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, String productsType, double min, double max,
			double ratMin, double ratMax, ProductBrand productBrand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByStatusAndPrice_MrpBetweenAndBrand(
			ProductStatus inMbg, double min, double max,
			ProductBrand productBrand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByStatusAndProductTypes_NameAndPrice_MrpBetween(
			ProductStatus inMbg, String string, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByStatusAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, double min, double max, double ratMin,
			double ratMax, ProductBrand productBrand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByStatusAndProductTypes_NameAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetween(
			ProductStatus inMbg, String productsType, double min, double max,
			double ratMin, double ratMax) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByStatusAndProductTypes_NameAndPrice_MrpBetweenAndBrand(
			ProductStatus inMbg, String productsType, double min, double max,
			ProductBrand productBrand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findByStatusAndProductTypes_NameAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, String productsType, double min, double max,
			double ratMin, double ratMax, ProductBrand productBrand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndProductTypes_NameAndPrice_MrpBetweenAndConsolidatedRating_AvgRating_RatingValBetweenAndBrand(
			ProductStatus inMbg, String string, double min, double max,
			double ratMin, double ratMax, ProductBrand productBrand,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findByStatusAndProductTypes_NameAndPrice_MrpBetweenAndBrand(
			ProductStatus inMbg, String string, double min, double max,
			ProductBrand productBrand, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByProductTypesInAndStatus(Set<ProductType> productTypeSet,
			ProductStatus inBuildonn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> filterProducts(List<Product> products) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countBySubcategories_Category(String category) {
		// TODO Auto-generated method stub
		return 0;
	}
}
