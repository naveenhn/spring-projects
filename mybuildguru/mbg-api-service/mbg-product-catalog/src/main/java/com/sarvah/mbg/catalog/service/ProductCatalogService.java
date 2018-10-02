/**
 * 
 */
package com.sarvah.mbg.catalog.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;

import SearchProductBySkuIdResponse.SearchProductBySkuIdResponse;

import com.sarvah.mbg.catalog.service.impl.SellerInformation;
import com.sarvah.mbg.catalog.service.model.AdminManageRequestQuoteResponse;
import com.sarvah.mbg.catalog.service.model.DealerOnboardedProductDetails;
import com.sarvah.mbg.catalog.service.model.DisplaySameProductsResponse;
import com.sarvah.mbg.catalog.service.model.PriceResponse;
import com.sarvah.mbg.catalog.service.model.ProductCatalogResponse;
import com.sarvah.mbg.catalog.service.model.ProductDetailView;
import com.sarvah.mbg.catalog.service.model.ProductDetailsResponse;
import com.sarvah.mbg.catalog.service.model.ProductPricingSummaryView;
import com.sarvah.mbg.catalog.service.model.ProductPricingSummaryViewResponse;
import com.sarvah.mbg.catalog.service.model.ProductSummaryViewResponse;
import com.sarvah.mbg.catalog.service.model.SearchKeywordResponse;
import com.sarvah.mbg.commons.communication.UserCommException;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductAttribute;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.ProductFaq;
import com.sarvah.mbg.domain.mongo.catalog.ProductPrice;
import com.sarvah.mbg.domain.mongo.catalog.ProductPricing;
import com.sarvah.mbg.domain.mongo.catalog.ProductQuantityType;
import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.marketing.Promotion;
import com.sarvah.mbg.domain.mongo.userprofile.QuoteRequestProduct;
import com.sarvah.mbg.domain.mongo.userprofile.QuoteRequestResponse;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.userprofile.response.QuoteProductRequestParam;

/**
 * @author naveen
 *
 */
public interface ProductCatalogService {

	/**
	 * Method to search for product
	 * 
	 * @param skuId
	 * @param name
	 * @return
	 */
	List<Product> search(String skuId, String name);

	/**
	 * Method for display products count
	 * 
	 * @param productCountParam
	 * @return
	 * @throws Exception
	 */
	long getProductsCount(String productName, String providerName,
			String category, String productsType, String pricing,
			String rating, String latestproduct, Point location,
			String accessory, String status, String brand) throws Exception;

	/**
	 * Method to get the count of the product which is related to user It get
	 * the product count based on uId
	 * 
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	long getProductsCountForUser(String userid) throws Exception;

	/**
	 * Method for perform product search operation
	 * 
	 * @param searchText
	 * @param category
	 * @param subCategory
	 * @param productsType
	 * @param brand
	 * @param pricing
	 * @param rating
	 * @param status
	 * @param zipcode
	 * @param key
	 * @param value
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	ProductCatalogResponse searchProduct(String searchText, String category,
			String subCategory, String productsType, String brand,
			String pricing, String rating, String status, String zipcode,
			String city, String key, String value, boolean filters,
			boolean isSUbcategoryList, int page, int size, Sort sort, int limit)
			throws Exception;

	ProductCatalogResponse searchProductByKeyword(String keyword, String brand,
			int page, int size, Sort sort) throws Exception;

	/**
	 * method for get provider manage product
	 * 
	 * @return
	 */

	ProductSummaryViewResponse getProviderManageProduct(String providerId,
			String searchValue, String status, String category,
			String subCategory, int page, int size, Sort sort);

	/**
	 * method for get super admin manage mbg product
	 * 
	 * @return
	 */
	ProductSummaryViewResponse getAdminManageProduct(String searchValue,
			String brand, String status, String category, String subCategory,
			int page, int size, Sort sort);

	/**
	 * method for get admin manage seller product
	 * 
	 * @param dealerId
	 * @param searchValue
	 * @param brand
	 * @param status
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	ProductSummaryViewResponse getAdminManageSellerProduct(String dealerId,
			String searchValue, String brand, String status, String category,
			String subCategory, int page, int size, Sort sort) throws Exception;

	/**
	 * method to get super admin manage provider product
	 * 
	 * @param providerId
	 * @param searchValue
	 * @param status
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 */
	ProductSummaryViewResponse getAdminManageProviderProduct(String providerId,
			String searchValue, String status, String category,
			String subCategory, int page, int size, Sort sort);

	/**
	 * Method for get Dealer manage product
	 * 
	 * @return
	 * @throws Exception
	 */
	ProductSummaryViewResponse getDealerManageProduct(String userId,
			String searchValue, String brand, String status, String category,
			String subCategory, int page, int size, Sort sort) throws Exception;

	/**
	 * Method for get particular product based on productId.
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	ProductDetailsResponse getProductBypid(String pid) throws Exception;

	/**
	 * method for admin manage dealer product view details
	 * 
	 * @param pid
	 * @param dealerId
	 * @return
	 * @throws Exception
	 */
	ProductDetailView getAdminManageDealerProductViewDetails(String pid,
			String dealerId) throws Exception;

	/**
	 * method for admin manage provider product view details
	 * 
	 * @param pid
	 * @param providerId
	 * @return
	 */
	ProductDetailView getAdminManageProviderProductViewDetails(String pid,
			String providerId);

	/**
	 * Method for get Admin Product view details
	 * 
	 * @param pid
	 * @return
	 */
	ProductDetailView getSuperAdminProductviewDetails(String pid);

	/**
	 * Method for get Provider product view details
	 * 
	 * @param pid
	 * @return
	 */
	ProductDetailView getProviderProductViewDetails(String pid);

	/**
	 * Method for get Dealer products view Details
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	ProductDetailView getDealerProductViewDetails(String userId, String pid)
			throws Exception;

	QuoteRequestResponse getAdminManageQuoteRequestViewDetails(
			String quoteRequestId);

	QuoteRequestResponse getDealerManageQuoteRequestViewDetails(
			String quoteRequestId, String dealerId);

	QuoteRequestResponse getCustomerManageQuoteRequestViewDetails(
			String quoteRequestId, String customerId);

	/**
	 * Method for delete a product based on productId.
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	Product deleteProduct(String pid) throws Exception;

	/**
	 * 
	 * @param pid
	 * @param userName
	 * @param fName
	 * @param lName
	 * @param email
	 * @param pnum
	 * @param roleName
	 * @param fullName
	 * @return
	 */
	List<Product> searchProductByProvider(String pid, String userName,
			String fName, String lName, String email, String pnum,
			String roleName, String fullName);

	/**
	 * Method to count dealers for a product
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	Long countDealerForProduct(String pid) throws Exception;

	/**
	 * Method to search provider for the product
	 * 
	 * @param pid
	 * @return
	 * @throws MBGAppException
	 */
	User searchProviderForProduct(String pid) throws Exception;

	/**
	 * Method to get related products for the product
	 * 
	 * @param pid
	 * @return
	 * @throws MBGAppException
	 */
	Set<ProductDetailsResponse> getRelatedProducts(String pid) throws Exception;

	/**
	 * Method to add related products for the product
	 * 
	 * @param pid
	 * @param relatedProducts
	 * @return
	 * @throws MBGAppException
	 */
	Set<String> createRelatedProducts(String pid, List<String> relatedProducts)
			throws Exception;

	/**
	 * Method to update related products of a product
	 * 
	 * @param pid
	 * @param relProdIds
	 * @return
	 * @throws Exception
	 */
	Set<String> updateRelatedProducts(String pid, List<String> relProdIds)
			throws Exception;

	/**
	 * Method to remove related product of a product
	 * 
	 * @param pid
	 * @param relatedProducts
	 * @return
	 * @throws MBGAppException
	 */
	Set<String> deleteRelatedProducts(String pid, List<String> relatedProducts)
			throws Exception;

	/**
	 * Method to get related accessories of a product
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	Set<ProductDetailsResponse> getRelatedAccsories(String pid)
			throws Exception;

	/**
	 * Method to add related accessories for the product
	 * 
	 * @param pid
	 * @param accessories
	 * @return
	 */
	Set<String> createRelatedAccessories(String pid, List<String> accessories)
			throws Exception;

	/**
	 * Method to update related accessories of the product
	 * 
	 * @param pid
	 * @param relatedAccessories
	 * @return
	 * @throws MBGAppException
	 */
	Set<String> updateRelatedAccessories(String pid,
			List<String> relatedAccessories) throws Exception;

	/**
	 * method to delete related accessories of a product
	 * 
	 * @param pid
	 * @param accessories
	 * @return
	 */
	Set<String> deleteRelatedAccessories(String pid, List<String> accessories);

	/**
	 * File and Image Upload
	 * 
	 * @param form
	 * @return
	 * @throws IOException
	 * @throws MBGAppException
	 */
	Product uploadFile(String pid, String fileName, String fileTypeValue,
			InputStream fileInputStream, long contentLength) throws Exception;

	String uploadInstantOrderImage(String mobile, String email,
			String customerName, String textInfo, String deliveryTime,
			String fileName, String fileTypeValue, InputStream fileInputStream,
			long contentLength) throws Exception;

	String sendProductEnquiryForm(String customerName, String mobile,
			String email, String textInfo, String deliveryTime)
			throws UserCommException;

	/**
	 * Method for search dealers for particular product
	 * 
	 * @param pid
	 * @param zipcode
	 * @return
	 * @throws MBGAppException
	 */
	List<SellerInformation> searchDealers(String pid, int zipcode)
			throws Exception;

	/**
	 * Method for create new product
	 * 
	 * @param productCreateRequestParam
	 * @return
	 * @throws Exception
	 */
	Product createProduct(String userId, String skuid, String name,
			String longName, String desc, Set<String> subcategoriesId,
			Set<String> productTypeIds, String productAssets, String price,
			String accessory, List<String> relatedProducts,
			List<String> accessories, String key1, Set<String> values1,
			String isMultiValued1, String model, Set<ProductFaq> faqs,
			Set<ProductAttribute> productAttributes, String brandName,
			String quantityType) throws Exception;

	/**
	 * Method to update product
	 * 
	 * @param pid
	 * @param productUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	Product updateProduct(/* String userId, */String pid, String name,
			String longName, String desc, Set<String> subCategoryIds,
			Set<String> productTypes, String productAssets,
			Set<ProductAttribute> productAttributes, String price,
			String provider, List<String> relatedProducts2,
			List<String> relatedProducts, String status2, String status,
			String key, Set<String> values, String model, Set<ProductFaq> faqs,
			String brandName, String quantityType) throws Exception;

	QuoteRequestProduct updateSellerQuoteProduct(String quoteRequestProductId,
			QuoteProductRequestParam product) throws UserCommException;

	/**
	 * method for get all brands.
	 * 
	 * @return
	 */
	List<ProductBrand> getAllBrands();

	/**
	 * method for get product count of onboarded by me.
	 * 
	 * @return
	 */
	long getOnBoardedByMe(String userId);

	/**
	 * method for get products onboarded by me.
	 * 
	 * @param view
	 * @return
	 */
	List<DealerOnboardedProductDetails> getAllProductsOnboardedMe(String userId);

	/**
	 * method for get products onboarded by me.
	 * 
	 * @param view
	 * @return
	 */
	List<Product> getAllonBoardedBymeProducts(String userId);

	/**
	 * Method for get all ProductTypes
	 * 
	 * @param view
	 * @return
	 */
	List<ProductType> getSystemProductTypes();

	/**
	 * method for create product quantity type.
	 * 
	 * @param productQuantityCreateRequestParam
	 * @return
	 * @throws MBGAppException
	 */
	ProductQuantityType createProductQuantityType(String name, String desc)
			throws Exception;

	/**
	 * method for get product quantity types
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	List<ProductQuantityType> getProductQuantityType();

	/**
	 * Method to check promocode
	 * 
	 * @param pid
	 * @param dealerId
	 * @param promocode
	 * @return
	 * @throws Exception
	 */
	PriceResponse checkPromoCode(String pid, String dealerId, String promocode)
			throws Exception;

	/**
	 * Method to get promo code for the product
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	List<Promotion> getPromoCodeForProduct(String pid) throws Exception;

	/**
	 * method for get top search keywords
	 * 
	 * @return
	 */
	List<SearchKeywordResponse> getTopSearchedKeywords(int page, int size,
			Sort sort);

	/**
	 * File delete
	 * 
	 * Method to delete file/remove from the product and Azure
	 * 
	 * @param pid
	 * @param fileUrl
	 * @param fileType
	 * @return
	 * @throws Exception
	 */
	Product deleteFile(String pid, String fileUrl, String fileType)
			throws Exception;

	/**
	 * Method to update ProductQuantityType
	 * 
	 * @param qtyTypeId
	 * @param name
	 * @param desc
	 * @return
	 * @throws Exception
	 */
	ProductQuantityType editProductQuantityType(String qtyTypeId, String name,
			String desc) throws Exception;

	/**
	 * Method to delete ProductQuantityType
	 * 
	 * @param qtyTypeId
	 * @return
	 * @throws Exception
	 */
	ProductQuantityType deleteProductQuantityType(String qtyTypeId)
			throws Exception;

	/**
	 * method for get customer bought products
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	Set<Product> getCustomerBoughtThisAlsoBought(String pid) throws Exception;

	SearchProductBySkuIdResponse getProductBySkuId(String skuId)
			throws Exception;

	void adminBulkUpdatingStoreProductsInfo(String dealerId, String brand,
			String status, String subCategory, String nationalShippingCharge,
			String zonalShippingCharge, String localShippingCharge,
			String minNationalProcurementSLA, String maxNationalProcurementSLA,
			String minZonalProcurementSLA, String maxZonalProcurementSLA,
			String minLocalPocurementSLA, String maxLocalPocurementSLA,
			String mbgShare, String localRegion, String zonalRegion,
			String nationRegion, String discount, String willYouDeliver)
			throws Exception;

	void dealerBulkUpdatingStoreProductsInfo(String dealerId, String brand,
			String status, String subCategory, String nationalShippingCharge,
			String zonalShippingCharge, String localShippingCharge,
			String minNationalProcurementSLA, String maxNationalProcurementSLA,
			String minZonalProcurementSLA, String maxZonalProcurementSLA,
			String minLocalPocurementSLA, String maxLocalPocurementSLA,
			String mbgShare, String localRegion, String zonalRegion,
			String nationRegion, String discount, String willYouDeliver)
			throws Exception;

	DisplaySameProductsResponse displaySameProducts(String productId)
			throws Exception;

	AdminManageRequestQuoteResponse getAdminManageRequestQuote(
			String searchValue, String status, int page, int size, Sort sort);

	AdminManageRequestQuoteResponse getDealerManageRequestQuote(
			String searchValue, String dealerId, int page, int size, Sort sort);

	AdminManageRequestQuoteResponse getCustomerManageRequestQuote(
			String searchValue, String customerId, int page, int size, Sort sort);

	void productsStatusBulkUpdate(String subCategory, String brand,
			String status, Set<String> productIds, String newStatus)
			throws Exception;

	void uploadRequestQuoteOrderImage(String userId, String productId,
			String customerName, String mobile, String email, String zipcode,
			String itemQuantity, String paymentMode, String craditDays,
			String deliveryTime, String textInfo, String brand,
			String changeAttribute, int page, int size, Sort sort)
			throws Exception;

	boolean verifyBuildonnQuoteRequestId(String quoteRequestId);

	ProductPricing createProductPricing(String productId,
			Set<ProductPrice> productPrices, String note) throws Exception;

	ProductPricingSummaryViewResponse getProductPricingList(String searchText,
			String categoryNames, String subCategory, String productsType,
			String brand, String pricing, String rating, String status,
			String zipcode, String city, String key, String value, int page,
			int size, Sort sort);

	ProductPricingSummaryView searchProductPricingById(String productPricingId)
			throws Exception;

	ProductPricing updateProductPricing(String productPricingId,
			Set<ProductPrice> productPrices, String note) throws Exception;

	ProductPricing deleteProductPricing(String productPricingId)
			throws Exception;

	void sendRequestForCallBack(String name, String mobileNum,
			String requirement) throws Exception;
}