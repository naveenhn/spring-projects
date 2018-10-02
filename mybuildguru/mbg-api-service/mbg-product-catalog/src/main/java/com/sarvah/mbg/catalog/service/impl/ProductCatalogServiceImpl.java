/**
 * 
 */
package com.sarvah.mbg.catalog.service.impl;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import SearchProductBySkuIdResponse.SearchProductBySkuIdResponse;

import com.sarvah.mbg.catalog.dao.mongo.CategoryDAO;
import com.sarvah.mbg.catalog.dao.mongo.ProductCommentDAO;
import com.sarvah.mbg.catalog.dao.mongo.ProductDAO;
import com.sarvah.mbg.catalog.dao.mongo.ProductPricingDAO;
import com.sarvah.mbg.catalog.dao.mongo.ProductQuantityTypeDAO;
import com.sarvah.mbg.catalog.dao.mongo.ProductTypeDAO;
import com.sarvah.mbg.catalog.dao.mongo.PromotionDAO;
import com.sarvah.mbg.catalog.dao.mongo.SearchKeywordStoreDAO;
import com.sarvah.mbg.catalog.dao.mongo.SubCategoryDAO;
import com.sarvah.mbg.catalog.service.ProductCatalogService;
import com.sarvah.mbg.catalog.service.model.AdminManageRequestQuote;
import com.sarvah.mbg.catalog.service.model.AdminManageRequestQuoteResponse;
import com.sarvah.mbg.catalog.service.model.DealerOnboardedProductDetails;
import com.sarvah.mbg.catalog.service.model.DisplaySameProductsResponse;
import com.sarvah.mbg.catalog.service.model.PriceResponse;
import com.sarvah.mbg.catalog.service.model.ProductCatalogDetails;
import com.sarvah.mbg.catalog.service.model.ProductCatalogResponse;
import com.sarvah.mbg.catalog.service.model.ProductCustomSearch;
import com.sarvah.mbg.catalog.service.model.ProductDetailView;
import com.sarvah.mbg.catalog.service.model.ProductDetailsResponse;
import com.sarvah.mbg.catalog.service.model.ProductPricingSummaryView;
import com.sarvah.mbg.catalog.service.model.ProductPricingSummaryViewResponse;
import com.sarvah.mbg.catalog.service.model.ProductSummaryView;
import com.sarvah.mbg.catalog.service.model.ProductSummaryViewResponse;
import com.sarvah.mbg.catalog.service.model.SearchFilters;
import com.sarvah.mbg.catalog.service.model.SearchKeywordResponse;
import com.sarvah.mbg.catalog.service.model.SimilarProductVariant;
import com.sarvah.mbg.commons.address.AddressLookupService;
import com.sarvah.mbg.commons.communication.UserCommException;
import com.sarvah.mbg.commons.communication.UserCommunicationService;
import com.sarvah.mbg.commons.communication.VariantConstants;
import com.sarvah.mbg.commons.storage.AzureFileStorage;
import com.sarvah.mbg.commons.storage.FileStorage;
import com.sarvah.mbg.domain.common.asset.File;
import com.sarvah.mbg.domain.common.asset.FileType;
import com.sarvah.mbg.domain.common.asset.Image;
import com.sarvah.mbg.domain.common.asset.Video;
import com.sarvah.mbg.domain.mongo.catalog.Category;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductAsset;
import com.sarvah.mbg.domain.mongo.catalog.ProductAttribute;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.ProductFaq;
import com.sarvah.mbg.domain.mongo.catalog.ProductPrice;
import com.sarvah.mbg.domain.mongo.catalog.ProductPricing;
import com.sarvah.mbg.domain.mongo.catalog.ProductQuantityType;
import com.sarvah.mbg.domain.mongo.catalog.ProductStatus;
import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.catalog.QuoteStatus;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.Price;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.dashboard.SearchKeywordStore;
import com.sarvah.mbg.domain.mongo.marketing.Promotion;
import com.sarvah.mbg.domain.mongo.review.Comment;
import com.sarvah.mbg.domain.mongo.review.ConsolidatedRating;
import com.sarvah.mbg.domain.mongo.review.Rating;
import com.sarvah.mbg.domain.mongo.store.Shipping;
import com.sarvah.mbg.domain.mongo.store.Store;
import com.sarvah.mbg.domain.mongo.store.StoreProductPricing;
import com.sarvah.mbg.domain.mongo.userprofile.QuoteProductPricingResponse;
import com.sarvah.mbg.domain.mongo.userprofile.QuoteProductResponse;
import com.sarvah.mbg.domain.mongo.userprofile.QuoteRequest;
import com.sarvah.mbg.domain.mongo.userprofile.QuoteRequestProduct;
import com.sarvah.mbg.domain.mongo.userprofile.QuoteRequestResponse;
import com.sarvah.mbg.domain.mongo.userprofile.SellerQuoteProductPricing;
import com.sarvah.mbg.domain.mongo.userprofile.SellerQuoteProductResponse;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.domain.mongo.userprofile.role.UserPackage;
import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.OrderDetails;
import com.sarvah.mbg.domain.sms.ItemInfoForCommunication;
import com.sarvah.mbg.domain.user.UserInfo;
import com.sarvah.mbg.domain.user.audit.UserAudit;
import com.sarvah.mbg.domain.user.audit.UserAuditType;
import com.sarvah.mbg.store.dao.mongo.StoreDAO;
import com.sarvah.mbg.store.dao.mongo.UITemplateDAO;
import com.sarvah.mbg.userprofile.dao.jpa.UserAuditRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserAuditTypeRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserOrderDetailRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserOrderRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserRepository;
import com.sarvah.mbg.userprofile.dao.mongo.BrandDAO;
import com.sarvah.mbg.userprofile.dao.mongo.QuoteRequestDAO;
import com.sarvah.mbg.userprofile.dao.mongo.QuoteRequestProductDAO;
import com.sarvah.mbg.userprofile.dao.mongo.SellerQuoteProductPricingDAO;
import com.sarvah.mbg.userprofile.dao.mongo.StoreProductPricingDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;
import com.sarvah.mbg.userprofile.response.ManualPagination;
import com.sarvah.mbg.userprofile.response.QuoteProductRequestParam;
import com.sarvah.mbg.userprofile.response.SellerQuoteProductPricingRequestParam;

/**
 * @author naveen
 *
 */
@Service
public class ProductCatalogServiceImpl implements ProductCatalogService {

	private static final Logger logger = LoggerFactory
			.getLogger(ProductCatalogServiceImpl.class);
	ProductValidation productValidation = new ProductValidation();

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private PromotionDAO promotionDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private ProductTypeDAO productTypeDAO;

	@Autowired
	private SubCategoryDAO subCategoryDAO;

	@Autowired
	private ProductCommentDAO productCommentDAO;

	@Autowired
	private StoreDAO storeDAO;

	@Autowired
	private FileStorage filestorage;

	@Autowired
	private BrandDAO brandDAO;

	@Autowired
	private ProductQuantityTypeDAO productQuantityTypeDAO;

	@Autowired
	private SearchKeywordStoreDAO searchKeywordStoreDAO;

	@Autowired
	MongoOperations operations;

	@Autowired
	private AddressLookupService addressLookupService;

	@Autowired
	private ManualPagination manualPagination;

	@Autowired
	private StoreProductPricingDAO storeProductPricingDAO;

	@Autowired
	private UserOrderRepository userOrderRepository;

	@Autowired
	private UserOrderDetailRepository userOrderDetailRepository;

	@Autowired
	private UserCommunicationService userCommunicationService;

	@Autowired
	private UITemplateDAO templateDAO;

	@Autowired
	private SellerQuoteProductPricingDAO sellerQuoteProductPricingDAO;

	@Autowired
	private QuoteRequestProductDAO quoteRequestProductDAO;

	@Autowired
	private QuoteRequestDAO quoteRequestDAO;

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAuditRepository userAuditRepository;

	@Autowired
	private UserAuditTypeRepository userAuditTypeRepository;

	@Autowired
	private ProductPricingDAO productPricingDAO;

	/**
	 * Method to search for product by skuid and name
	 * 
	 * @param skuId
	 * @param name
	 * @return
	 */
	@Override
	public List<Product> search(String skuId, String name) {
		if (skuId != null && !skuId.isEmpty() && name != null) {
			return productDAO.findBySkuIdAndName(skuId, name);

		}

		return productDAO.findAll();
	}

	/**
	 * Method to get the count of the product which is related to user It get
	 * the product count based on uId
	 * 
	 * @param userid
	 * @return long
	 * @throws Exception
	 */
	@Override
	public long getProductsCountForUser(String userid) throws Exception {
		long count = 0;
		if (StringUtils.isNotBlank(userid)) {
			count = productDAO.countByBrand_Provider_Id(userid);
			logger.info("Getting Products count based on user id");
		} else {
			logger.info("User id should not be blank");
			throw new Exception("User id should not be blank");
		}
		return count;
	}

	@Override
	public ProductCatalogResponse searchProductByKeyword(String keyword,
			String brand, int page, int size, Sort sort) throws Exception {
		ProductCatalogResponse productCatalogResponse = new ProductCatalogResponse();
		List<Product> products1 = new ArrayList<Product>();
		List<ProductCatalogDetails> productList = new ArrayList<ProductCatalogDetails>();
		ProductCustomSearch productCustomSearch = new ProductCustomSearch();
		ProductCustomSearch productCustomSearch1 = new ProductCustomSearch();
		ProductCustomSearch productCustomSearchForFilter = new ProductCustomSearch();
		Set<String> matchingNames = new HashSet<>();
		Page<Product> pageableObj = null;
		SearchFilters filters = null;

		// Search Value
		if (StringUtils.isNotBlank(keyword)) {
			SearchKeywordStore searchKeywordStore = new SearchKeywordStore();
			searchKeywordStore.setKeyword(keyword);
			searchKeywordStoreDAO.save(searchKeywordStore);

			List<String> categoryNames = new ArrayList<String>();
			ProductBrand productBrand = brandDAO.findByNameIgnoreCase(keyword);
			List<Category> catagories = categoryDAO
					.findByNameAllIgnoreCase(keyword);
			categoryNames.addAll(catagories.stream().map(c -> c.getName())
					.collect(Collectors.toList()));

			// Brand
			if (productBrand != null) {
				List<String> brandIds = new ArrayList<>();
				brandIds.add(productBrand.getId());
				// custom search
				productCustomSearch.setProductBrandIds(brandIds);
				productCustomSearchForFilter.setProductBrandIds(brandIds);
				// word match
				productCustomSearch1.setProductBrandIds(brandIds);
			} else if (StringUtils.isNotBlank(brand)) {
				String[] brandIds = brand.split(",");
				List<String> brands = new ArrayList<String>();
				for (String brandId : brandIds) {
					brands.add(brandId);
				}
				productCustomSearch.setProductBrandIds(brands);
				productCustomSearchForFilter.setProductBrandIds(brands);
				// word match
				productCustomSearch1.setProductBrandIds(brands);
			}
			// Category
			else if (categoryNames != null && categoryNames.size() != 0) {
				// custom search
				productCustomSearch.setCategories(categoryNames);
				productCustomSearchForFilter.setCategories(categoryNames);
				// word match
				productCustomSearch1.setCategories(categoryNames);
			}

			// SearchTest
			productCustomSearch1.setSearchText(keyword);
			List<ProductStatus> productStatuses = new ArrayList<>();
			productStatuses.add(ProductStatus.IN_BUILDONN);
			productCustomSearch1.setProductStatuses(productStatuses);

			pageableObj = productDAO.searchProducts(productCustomSearch1,
					new PageRequest(page, size, sort));

			if (pageableObj.getContent().size() != 0) {
				productCustomSearch.setSearchText(keyword);
				productCustomSearchForFilter.setSearchText(keyword);
			} else {
				String[] keyNames = keyword.split(" ");
				for (String keyName : keyNames) {
					if (!keyName.isEmpty()) {
						matchingNames.add(keyName);
						productCustomSearch1.setSearchText(keyName);
						Page<Product> pageable1 = productDAO.searchProducts(
								productCustomSearch1, new PageRequest(page,
										size, sort));
						if (pageable1.getContent().size() != 0) {
							products1.addAll(pageable1.getContent());
						}
					}
				}
			}
		}

		// status
		List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
		productStatuses.add(ProductStatus.IN_BUILDONN);
		productCustomSearch.setProductStatuses(productStatuses);
		productCustomSearchForFilter.setProductStatuses(productStatuses);

		if (pageableObj.getContent().size() != 0) {
			filters = getSearchFilterForKeywordSearch(productCustomSearchForFilter);
		} else {
			filters = getSearchFilterForKeywordSearchWordMatch(products1);
		}

		if (filters != null) {
			productCatalogResponse.setFilters(filters);

			Map<String, Long> subCatNameAndProductCountMap = filters
					.getSubCatNameAndProductCountMap();
			long max = 0;
			long temp = 0;
			for (long l : subCatNameAndProductCountMap.values()) {
				if (temp > l) {
					max = temp;

				} else {
					max = l;
					temp = max;
				}
			}
			List<String> subCategoryNames = new ArrayList<>();
			for (Map.Entry<String, Long> entry : subCatNameAndProductCountMap
					.entrySet()) {
				if (entry.getValue().equals(max)) {
					String str = entry.getKey();
					String[] strAry = str.split("-");
					subCategoryNames.add(strAry[0]);
				}
			}

			productCustomSearch.setSubCategoryNames(subCategoryNames);
		}
		// setting required fields for catalog
		Set<String> requiredFieldSet = new HashSet<>();
		requiredFieldSet.add("_id");
		requiredFieldSet.add("skuId");
		requiredFieldSet.add("name");
		requiredFieldSet.add("brand");
		requiredFieldSet.add("subcategories");
		requiredFieldSet.add("assets");
		requiredFieldSet.add("quantityType");
		productCustomSearch.setRequiredFields(requiredFieldSet);

		Page<Product> pageable = productDAO.searchProducts(productCustomSearch,
				new PageRequest(page, size, sort));

		products1.addAll(pageable.getContent());

		if (products1.size() != 0) {
			ProductCatalogDetails response = null;
			for (Product product : products1) {
				if (matchingNames.size() != 0) {
					boolean flag = true;
					for (String matchingName : matchingNames) {
						if (!StringUtils.containsIgnoreCase(product.getName(),
								matchingName)) {
							flag = false;
						}
					}
					if (flag) {
						response = new ProductCatalogDetails();
						// product id
						response.setId(product.getId());
						// name
						response.setName(product.getName());

						// assets
						if (product.getAssets() != null) {
							response.setAssets(product.getAssets());
						}

						// brand
						if (product.getBrand() != null) {
							response.setBrand(product.getBrand().getName());
						}
						// quantity type
						if (product.getQuantityType() != null) {
							response.setQuantityType(product.getQuantityType()
									.getName());
						}
						// SubCategory
						if (product.getSubcategories() != null) {
							response.setSubCategories(product
									.getSubcategories());
						}
						productList.add(response);
						productCatalogResponse.setFilters(null);
					}
				} else {
					response = new ProductCatalogDetails();
					// product id
					response.setId(product.getId());
					// name
					response.setName(product.getName());

					// assets
					if (product.getAssets() != null) {
						response.setAssets(product.getAssets());
					}

					// brand
					if (product.getBrand() != null) {
						response.setBrand(product.getBrand().getName());
					}
					// quantity type
					if (product.getQuantityType() != null) {
						response.setQuantityType(product.getQuantityType()
								.getName());
					}
					// SubCategory
					if (product.getSubcategories() != null) {
						response.setSubCategories(product.getSubcategories());
					}
					productList.add(response);
				}
			}
		} else {
			logger.info("Search did not return any result....!!!!");
		}

		productCatalogResponse.setSearchKeyword(keyword);
		productCatalogResponse.setProducts(productList);

		productCatalogResponse.setTotalPages(pageable.getTotalPages());
		productCatalogResponse.setTotalElements(pageable.getTotalElements());
		productCatalogResponse.setNumber(pageable.getNumber());
		productCatalogResponse.setSize(pageable.getSize());
		return productCatalogResponse;
	}

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
	@Override
	public ProductCatalogResponse searchProduct(String searchText,
			String categoryNames, String subCategory, String productsType,
			String brand, String pricing, String rating, String status,
			String zipcode, String city, String key, String value,
			boolean filter, boolean isSUbcategoryList, int page, int size,
			Sort sort, int limit) throws Exception {
		List<Product> productsForCatalog = new ArrayList<Product>();
		List<Product> productsAll = new ArrayList<Product>();
		List<Product> productsFiltered = new ArrayList<Product>();
		Page<Product> pageable = null;

		ProductCustomSearch productCustomSearch = new ProductCustomSearch();

		List<ProductCatalogDetails> productList = new ArrayList<ProductCatalogDetails>();

		ProductCatalogResponse productCatalogResponse = new ProductCatalogResponse();

		Map<String, List<String>> map = new HashMap<String, List<String>>();

		// zipcode
		if (StringUtils.isNotBlank(zipcode)) {
			Set<String> productIds = getDealersProductsIdsBasedOnZipcode(Integer
					.parseInt(zipcode));
			productCustomSearch.setProductIds(productIds);
		}

		// city
		if (StringUtils.isNotBlank(city)) {
			Set<String> productIds = getDealersProductsIdsBasedOnCity(city);
			if (productIds != null && productIds.size() != 0) {
				productCustomSearch.setProductIds(productIds);
			} else {
				logger.error("No products is Available for this city : {}",
						city);
				throw new Exception("No products is Available for this city :"
						+ city);
			}
		}

		// Categories
		if (StringUtils.isNotBlank(categoryNames)) {
			List<Category> categories = new ArrayList<Category>();
			String[] categoryList = categoryNames.split(",");
			for (String catNames : categoryList) {
				categories = categoryDAO.findByNameAllIgnoreCase(catNames);
			}
			productCustomSearch.setCategories(categories.stream()
					.map(c -> c.getName()).collect(Collectors.toList()));
		}

		// SubCategories
		if (StringUtils.isNotBlank(subCategory)) {
			List<SubCategory> subCategories = new ArrayList<>();
			String[] subCategoryList = subCategory.split(",");
			for (String subcatNames : subCategoryList) {
				subCategories = subCategoryDAO.findByName(subcatNames);
			}
			productCustomSearch.setSubCategoryNames(subCategories.stream()
					.map(s -> s.getName()).collect(Collectors.toList()));
		}

		// Product type
		if (StringUtils.isNotBlank(productsType)) {
			String[] productTypes = productsType.split(",");
			List<String> productTypeNames = new ArrayList<>();
			for (String productType : productTypes) {
				productTypeNames.add(productType);
			}
			productCustomSearch.setProductTypeNames(productTypeNames);
		}

		// Brands
		if (StringUtils.isNotBlank(brand)) {
			String[] brandIds = brand.split(",");
			List<String> brands = new ArrayList<String>();
			for (String brandId : brandIds) {
				brands.add(brandId);
			}
			productCustomSearch.setProductBrandIds(brands);
		}

		// Pricing
		if (StringUtils.isNotBlank(pricing)) {
			String[] maxminValue = pricing.split(",");
			if (maxminValue.length == 2) {
				double max = Double.parseDouble(maxminValue[0]) + 1;
				double min = Double.parseDouble(maxminValue[1]) - 1;
				productCustomSearch.setMinPrice(min);
				productCustomSearch.setMaxPrice(max);
			}
		}

		// Rating
		if (StringUtils.isNotBlank(rating)) {
			String[] maxminValue = rating.split(",");
			if (maxminValue.length == 2) {
				double max = Double.parseDouble(maxminValue[0]) + .1;
				double min = Double.parseDouble(maxminValue[1]) - .1;
				productCustomSearch.setMinRating(min);
				productCustomSearch.setMaxRating(max);
			}
		}

		// Search Value
		if (StringUtils.isNotBlank(searchText)) {
			SearchKeywordStore searchKeywordStore = new SearchKeywordStore();
			searchKeywordStore.setKeyword(searchText);
			searchKeywordStoreDAO.save(searchKeywordStore);
			List<Category> categories = new ArrayList<Category>();
			categories = categoryDAO.findByNameAllIgnoreCase(searchText);
			if (categories != null && categories.size() != 0) {
				productCustomSearch.setCategories(categories.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));
			} else {
				productCustomSearch.setSearchText(searchText);
			}
		}

		// Status
		if (StringUtils.isNotBlank(status)) {
			String[] statuses = status.split(",");
			List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
			for (int i = 0; i < statuses.length; i++) {
				productStatuses.add(ProductStatus.valueOf(statuses[i]));
			}
			productCustomSearch.setProductStatuses(productStatuses);
		} else {
			List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
			productStatuses.add(ProductStatus.IN_BUILDONN);
			productCustomSearch.setProductStatuses(productStatuses);
		}

		// Attribute
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
			if (map.containsKey(key)) {
				List<String> values = map.get(key);
				String[] valueList = value.split(",");
				for (int i = 0; i < valueList.length; i++) {
					values.add(valueList[i]);
				}
				map.put(key, values);
			} else {
				List<String> values = new ArrayList<>();
				String[] valueList = value.split(",");
				for (int i = 0; i < valueList.length; i++) {
					values.add(valueList[i]);
				}
				map.put(key, values);
			}

			productCustomSearch.setAttributeSearchMap(map);
		}
		// limit
		if (limit > 0) {
			productCustomSearch.setLimit(limit);
		}
		// setting required fields for catalog
		Set<String> requiredFieldSet = new HashSet<>();
		requiredFieldSet.add("_id");
		requiredFieldSet.add("skuId");
		requiredFieldSet.add("name");
		requiredFieldSet.add("model");
		requiredFieldSet.add("brand");
		requiredFieldSet.add("subcategories");
		requiredFieldSet.add("price");
		requiredFieldSet.add("consolidatedRating");
		requiredFieldSet.add("assets");
		requiredFieldSet.add("quantityType");
		requiredFieldSet.add("productTypes");
		requiredFieldSet.add("attributes");
		productCustomSearch.setRequiredFields(requiredFieldSet);

		productsAll = productDAO.searchProducts(productCustomSearch);
		if (productsAll.size() != 0) {
			productsFiltered = productDAO.filterProducts(productsAll);
			int end = (page + 1) * size;
			try {
				productsForCatalog = size > productsFiltered.size() ? productsFiltered
						: productsFiltered
								.subList(page * size, end > productsFiltered
										.size() ? productsFiltered.size() : end);
			} catch (IllegalArgumentException e) {
				throw new Exception("No more products");
			}
			pageable = new PageImpl<>(productsForCatalog, new PageRequest(page,
					size, sort), productsFiltered.size());

			if (productsForCatalog.size() != 0) {
				ProductCatalogDetails response = null;

				for (Product product : productsForCatalog) {

					response = new ProductCatalogDetails();
					// product id
					response.setId(product.getId());
					// name
					response.setName(product.getName());
					Product prod = productDAO.findById(product.getId());
					if (prod != null) {
						if (prod.getName() != product.getName()) {
							response.setFullName(prod.getName());
						}
					}

					// brand
					if (product.getBrand() != null) {
						response.setBrand(product.getBrand().getName());
					}
					// rating
					if (product.getConsolidatedRating() != null) {
						response.setRating(product.getConsolidatedRating());
					}
					// assets
					if (product.getAssets() != null) {
						response.setAssets(product.getAssets());
					}
					// features
					if (product.getFeatures() != null) {
						response.setFeatures(product.getFeatures());
					}
					// mrp
					if (product.getPrice() != null) {
						response.setMrp(product.getPrice().getMrp());
					}
					// discount
					if (product.getPrice() != null) {
						response.setDiscount(product.getPrice().getPctSavings());
					}
					// selling price
					if (product.getPrice() != null) {
						response.setSellingPrice(product.getPrice()
								.getSelling());
					}

					// Right now below lines of code not needed so have
					// commented
					// List<StoreProductPricing> storeProductPricingList =
					// storeProductPricingDAO
					// .findByProductId(product.getId());
					// // seller availability
					// if (storeProductPricingList != null
					// && storeProductPricingList.size() != 0) {
					// for (StoreProductPricing storeProductPricing :
					// storeProductPricingList) {
					// if (storeProductPricing.getStockCount() > 0) {
					// response.setSeller(true);
					// break;
					// } else {
					// response.setSeller(false);
					// }
					// }
					// } else {
					// response.setSeller(false);
					// }

					// quantity type
					if (product.getQuantityType() != null) {
						response.setQuantityType(product.getQuantityType()
								.getName());
					}

					// attributes
					if (product.getAttributes() != null) {
						response.setAttributes(product.getAttributes());
					}

					// subCategory
					if (product.getSubcategories() != null) {
						response.setSubCategories(product.getSubcategories());
					}

					productList.add(response);
				}
			}

			productCatalogResponse.setProducts(productList);
			if (filter == true) {
				SearchFilters filters = getSearchFilterResult(productsAll,
						isSUbcategoryList);
				productCatalogResponse.setFilters(filters);
			}
			productCatalogResponse.setTotalPages(pageable.getTotalPages());
			productCatalogResponse
					.setTotalElements(pageable.getTotalElements());
			productCatalogResponse.setNumber(pageable.getNumber());
			productCatalogResponse.setSize(pageable.getSize());
		} else {
			logger.info("Search did not return any result....!!!!");
		}
		return productCatalogResponse;
	}

	public Set<String> getDealersProductsIdsBasedOnZipcode(Integer zipcode) {
		Set<String> productIds = new HashSet<>();
		List<User> users = userDAO
				.findByAddresses_ZipcodeAndRoles_NameIgnoreCase(zipcode,
						"DEALER");
		Set<Store> storeSet = new HashSet<>();
		Set<Product> productSet = new HashSet<>();
		if (users != null) {
			for (User user : users) {
				if (user != null) {
					Store store = storeDAO.findByUser(user);
					if (store != null) {
						storeSet.add(store);
					}
				}
			}

			if (storeSet != null && storeSet.size() != 0) {
				for (Store store : storeSet) {
					if (store.getProducts() != null
							&& store.getProducts().size() != 0) {
						List<Product> products = store.getProducts();
						productSet.addAll(products);
					}
				}
			}

			if (productSet != null && productSet.size() != 0) {
				for (Product product : productSet) {
					if (product != null) {
						productIds.add(product.getId());
					}
				}
			}
		} else {
			logger.info("No deales is available for " + zipcode + " city");
		}
		return productIds;
	}

	public Set<String> getDealersProductsIdsBasedOnCity(String city) {
		Set<String> productIds = new HashSet<>();

		List<User> users = userDAO.findByAddresses_CityAndRoles_NameIgnoreCase(
				city, "DEALER");
		Set<Store> storeSet = new HashSet<>();
		Set<Product> productSet = new HashSet<>();
		if (users != null) {
			for (User user : users) {
				Store store = storeDAO.findByUser(user);
				if (store != null) {
					storeSet.add(store);
				}
			}

			for (Store store : storeSet) {
				List<Product> products = store.getProducts();
				productSet.addAll(products);
			}

			for (Product product : productSet) {
				productIds.add(product.getId());
			}
		} else {
			logger.info("No deales is available for {} city", city);
		}

		return productIds;
	}

	public SearchFilters getSearchFilterResult(List<Product> allProducts,
			boolean isSUbcategoryList) {
		SearchFilters filters = null;

		if (allProducts.size() != 0) {
			filters = new SearchFilters();
			Map<String, ProductBrand> productBrandMap = new HashMap<String, ProductBrand>();
			Map<String, ProductType> productTypeMap = new HashMap<String, ProductType>();
			Map<String, SubCategory> subCategoryMap = new HashMap<String, SubCategory>();

			for (Product product : allProducts) {
				if (product != null) {
					// Brands
					if (product.getBrand() != null) {
						ProductBrand prodBrand = product.getBrand();
						if (!productBrandMap.containsKey(prodBrand.getName())) {
							productBrandMap.put(prodBrand.getName(), prodBrand);
						}
					}
					// ProductTypes
					if (product.getProductTypes() != null
							&& product.getProductTypes().size() != 0) {
						for (ProductType type : product.getProductTypes()) {
							if (type != null) {
								if (!productTypeMap.containsKey(type.getName())) {
									productTypeMap.put(type.getName(), type);
								}
							}
						}
					}
					// SubCategories
					if (isSUbcategoryList == true) {
						if (product.getSubcategories() != null) {
							for (SubCategory subCategory : product
									.getSubcategories()) {
								if (!subCategoryMap.containsKey(subCategory
										.getName())) {
									subCategoryMap.put(subCategory.getName(),
											subCategory);
								}
							}
						}
					}
				}
			}

			if (productBrandMap.size() != 0) {
				filters.setBrands(productBrandMap.values());
			}
			if (productTypeMap.size() != 0) {
				filters.setTypes(productTypeMap.values());
			}
			if (subCategoryMap.size() != 0) {
				filters.setSubCategories(subCategoryMap.values());
			}
		} else {
			logger.info("Filter Search did not return any result....!!!!");
		}
		return filters;
	}

	public SearchFilters getSearchFilterForKeywordSearch(
			ProductCustomSearch productCustomSearchForFilter) {
		SearchFilters filters = null;
		List<Product> allProducts = new ArrayList<Product>();

		// setting required fields for filter
		Set<String> requiredFieldSetForFilter = new HashSet<>();
		requiredFieldSetForFilter.add("price");
		requiredFieldSetForFilter.add("subcategories");
		productCustomSearchForFilter
				.setRequiredFields(requiredFieldSetForFilter);

		allProducts.addAll(productDAO
				.searchProducts(productCustomSearchForFilter));

		if (allProducts.size() != 0) {
			filters = new SearchFilters();

			Collection<SubCategory> subCategories = null;
			Map<String, SubCategory> subCategoryMap = new HashMap<String, SubCategory>();
			Map<String, Long> subCatNameAndProductCountMap = new HashMap<String, Long>();
			for (Product product : allProducts) {
				if (product != null) {

					// SubCategories
					subCategories = new HashSet<SubCategory>();
					if (product.getSubcategories() != null) {
						for (SubCategory subCategory : product
								.getSubcategories()) {
							if (!subCategoryMap.containsKey(subCategory
									.getName())) {
								subCategoryMap.put(subCategory.getName(),
										subCategory);
								List<String> subCatNameList = new ArrayList<>();
								subCatNameList.add(subCategory.getName());
								productCustomSearchForFilter
										.setSubCategoryNames(subCatNameList);
								String subCatAndCatName = subCategory.getName()
										+ "-" + subCategory.getCategory();
								subCatNameAndProductCountMap
										.put(subCatAndCatName,
												productDAO
														.count(productCustomSearchForFilter));
							}

						}
						subCategories = subCategoryMap.values();
					}
				}
			}
			filters.setSubCategories(subCategories);
			filters.setSubCatNameAndProductCountMap(subCatNameAndProductCountMap);

		} else {
			logger.info("Filter Search did not return any result....!!!!");
		}
		return filters;
	}

	public SearchFilters getSearchFilterForKeywordSearchWordMatch(
			List<Product> products) {

		SearchFilters filters = null;
		ProductCustomSearch productCustomSearchForFilter = new ProductCustomSearch();

		if (products.size() != 0) {
			filters = new SearchFilters();

			Collection<SubCategory> subCategories = null;
			Map<String, SubCategory> subCategoryMap = new HashMap<String, SubCategory>();
			Map<String, Long> subCatNameAndProductCountMap = new HashMap<String, Long>();
			for (Product product : products) {
				if (product != null) {

					// SubCategories
					subCategories = new HashSet<SubCategory>();
					if (product.getSubcategories() != null) {
						for (SubCategory subCategory : product
								.getSubcategories()) {
							if (!subCategoryMap.containsKey(subCategory
									.getName())) {
								subCategoryMap.put(subCategory.getName(),
										subCategory);
								List<String> subCatNameList = new ArrayList<>();
								subCatNameList.add(subCategory.getName());
								productCustomSearchForFilter
										.setSubCategoryNames(subCatNameList);
								String subCatAndCatName = subCategory.getName()
										+ "-" + subCategory.getCategory();
								subCatNameAndProductCountMap
										.put(subCatAndCatName,
												productDAO
														.count(productCustomSearchForFilter));
							}

						}
						subCategories = subCategoryMap.values();
					}
				}
			}
			filters.setSubCategories(subCategories);
			filters.setSubCatNameAndProductCountMap(subCatNameAndProductCountMap);

		} else {
			logger.info("Filter Search did not return any result....!!!!");
		}
		return filters;

	}

	/**
	 * method for get provider manage product
	 * 
	 * @return
	 */

	@Override
	public ProductSummaryViewResponse getProviderManageProduct(
			String providerId, String searchValue, String status,
			String categoryNames, String subCategory, int page, int size,
			Sort sort) {
		ProductCustomSearch productCustomSearch = new ProductCustomSearch();
		List<ProductSummaryView> productSummaryViewList = new ArrayList<ProductSummaryView>();
		ProductSummaryViewResponse productSummaryViewResponse = new ProductSummaryViewResponse();
		Page<Product> pageable = null;
		User user = userDAO.findById(providerId);
		if (user != null) {
			Set<Role> roles = user.getRoles();
			for (Role role : roles) {
				if (role.getName().equalsIgnoreCase("PROVIDER")) {
					List<Product> products = new ArrayList<>();
					ProductBrand brand = brandDAO.findByProvider(user);
					// brand
					if (brand != null) {
						List<String> brands = new ArrayList<String>();
						brands.add(brand.getId());
						productCustomSearch.setProductBrandIds(brands);
					}

					// Search Value
					if (StringUtils.isNotBlank(searchValue)) {
						productCustomSearch.setSearchText(searchValue);

					}

					// Status
					if (StringUtils.isNotBlank(status)) {
						String[] statuses = status.split(",");
						List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
						for (int i = 0; i < statuses.length; i++) {
							productStatuses.add(ProductStatus
									.valueOf(statuses[i]));
						}
						productCustomSearch.setProductStatuses(productStatuses);
					}

					// Categories
					if (StringUtils.isNotBlank(categoryNames)) {
						List<Category> categories = new ArrayList<Category>();
						String[] categoryList = categoryNames.split(",");
						for (String catNames : categoryList) {
							categories = categoryDAO
									.findByNameAllIgnoreCase(catNames);
						}
						productCustomSearch.setCategories(categories.stream()
								.map(c -> c.getName())
								.collect(Collectors.toList()));
					}

					// SubCategories
					if (StringUtils.isNotBlank(subCategory)) {
						List<SubCategory> subCategories = new ArrayList<>();
						List<String> subCategoriesName = new ArrayList<>();
						String[] subCategoryList = subCategory.split(",");
						for (String subcatNames : subCategoryList) {
							subCategories = subCategoryDAO
									.findByName(subcatNames);
							for (SubCategory SubCategory : subCategories) {
								subCategoriesName.add(SubCategory.getName());
							}
						}
						productCustomSearch
								.setSubCategoryNames(subCategoriesName);
					}
					// Require fields
					Set<String> requiredFieldSet = new HashSet<>();
					requiredFieldSet.add("_id");
					requiredFieldSet.add("name");
					requiredFieldSet.add("skuId");
					requiredFieldSet.add("model");
					requiredFieldSet.add("createdDate");
					requiredFieldSet.add("subcategories");
					requiredFieldSet.add("status");
					requiredFieldSet.add("brand");
					requiredFieldSet.add("price");
					requiredFieldSet.add("consolidatedRating");
					requiredFieldSet.add("desc");
					requiredFieldSet.add("assets");
					productCustomSearch.setRequiredFields(requiredFieldSet);

					pageable = productDAO.searchProducts(productCustomSearch,
							new PageRequest(page, size, sort));

					if (pageable.getSize() != 0) {
						products.addAll(pageable.getContent());
					} else {
						logger.info("Search did not return any result....!!!!");
					}

					for (Product product : products) {
						ProductSummaryView productSummaryView = new ProductSummaryView();
						productSummaryView.setProductId(product.getId());
						productSummaryView.setName(product.getName());
						productSummaryView.setSkuId(product.getSkuId());
						productSummaryView.setMrp(product.getPrice().getMrp());
						if (product.getModel() != null) {
							productSummaryView.setModel(product.getModel());
						}
						if (product.getCreatedDate() != null) {
							productSummaryView.setCreatedDate(product
									.getCreatedDate());
						}
						if (product.getStatus() != null) {
							productSummaryView.setStatus(product.getStatus());
						}
						if (product.getSubcategories() != null) {
							Set<SubCategory> subCategories = product
									.getSubcategories();
							productSummaryView.setSubCategory(subCategories);
							Set<Category> categoryList = new HashSet<Category>();
							for (SubCategory subcategory : subCategories) {
								categoryList.addAll(categoryDAO
										.findByNameAllIgnoreCase(subcategory
												.getCategory()));
							}
							productSummaryView.setMainCategory(categoryList);
						}
						Set<String> productIds = new HashSet<>();
						productIds.add(product.getId());
						List<Promotion> promotionSet = promotionDAO
								.findByProductIdsIn(productIds);
						if (promotionSet.size() != 0) {
							productSummaryView.setPromotionsApplied("YES");
						} else {
							productSummaryView.setPromotionsApplied("NO");
						}
						productSummaryViewList.add(productSummaryView);
					}
				}
			}
		}
		productSummaryViewResponse.setProducts(productSummaryViewList);
		productSummaryViewResponse.setTotalPages(pageable.getTotalPages());
		productSummaryViewResponse
				.setTotalElements(pageable.getTotalElements());
		productSummaryViewResponse.setNumber(pageable.getNumber());
		productSummaryViewResponse.setSize(pageable.getSize());
		return productSummaryViewResponse;
	}

	/**
	 * method for get super admin manage mbg product
	 * 
	 * @return
	 */
	@Override
	public ProductSummaryViewResponse getAdminManageProduct(String searchValue,
			String brand, String status, String categoryNames,
			String subCategory, int page, int size, Sort sort) {

		ProductCustomSearch productCustomSearch = new ProductCustomSearch();

		List<Product> products = new ArrayList<>();

		Page<Product> pageable = null;

		ProductSummaryViewResponse productSummaryViewResponse = new ProductSummaryViewResponse();

		List<ProductSummaryView> productSummaryViewList = new ArrayList<ProductSummaryView>();

		User user = userDAO.findByRoles_NameIsIgnoreCase("SUPERADMIN");
		if (user != null) {

			// Search Value
			if (StringUtils.isNotBlank(searchValue)) {
				productCustomSearch.setSearchText(searchValue);

			}

			// Brands
			if (StringUtils.isNotBlank(brand)) {
				String[] brandIds = brand.split(",");
				List<String> brands = new ArrayList<String>();
				for (String brandId : brandIds) {
					brands.add(brandId);
				}
				productCustomSearch.setProductBrandIds(brands);
			}

			// Status
			if (StringUtils.isNotBlank(status)) {
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}

				productCustomSearch.setProductStatuses(productStatuses);
			}

			// Categories
			if (StringUtils.isNotBlank(categoryNames)) {
				List<Category> categories = new ArrayList<Category>();
				String[] categoryList = categoryNames.split(",");
				for (String catNames : categoryList) {
					categories.addAll(categoryDAO
							.findByNameAllIgnoreCase(catNames));
				}
				productCustomSearch.setCategories(categories.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));
			}

			// SubCategories
			if (StringUtils.isNotBlank(subCategory)) {
				List<SubCategory> subCategories = new ArrayList<>();
				List<String> subCategoriesName = new ArrayList<>();
				String[] subCategoryList = subCategory.split(",");
				for (String subcatNames : subCategoryList) {
					subCategories = subCategoryDAO.findByName(subcatNames);
					for (SubCategory SubCategory : subCategories) {
						subCategoriesName.add(SubCategory.getName());
					}
				}
				productCustomSearch.setSubCategoryNames(subCategoriesName);
			}

			// Required fields
			Set<String> requiredFieldSet = new HashSet<>();
			requiredFieldSet.add("_id");
			requiredFieldSet.add("name");
			requiredFieldSet.add("skuId");
			requiredFieldSet.add("model");
			requiredFieldSet.add("createdDate");
			requiredFieldSet.add("subcategories");
			requiredFieldSet.add("status");
			requiredFieldSet.add("brand");
			requiredFieldSet.add("price");
			requiredFieldSet.add("consolidatedRating");
			requiredFieldSet.add("desc");
			requiredFieldSet.add("assets");
			productCustomSearch.setRequiredFields(requiredFieldSet);

			pageable = productDAO.searchProducts(productCustomSearch,
					new PageRequest(page, size, sort));

			if (pageable.getSize() != 0) {
				products.addAll(pageable.getContent());
			} else {
				logger.info("Search did not return any result....!!!!");
			}

		}

		for (Product product : products) {
			ProductSummaryView productSummaryView = new ProductSummaryView();
			// product id
			productSummaryView.setProductId(product.getId());
			// skuid
			productSummaryView.setSkuId(product.getSkuId());
			// product name
			if (product.getName() != null) {
				productSummaryView.setName(product.getName());
			}
			// model
			if (product.getModel() != null) {
				productSummaryView.setModel(product.getModel());
			}
			// price
			if (product.getPrice() != null) {
				productSummaryView.setMrp(product.getPrice().getMrp());
			}
			// brand
			if (product.getBrand() != null) {
				productSummaryView.setBrand(product.getBrand().getName());
			}
			// created date
			if (product.getCreatedDate() != null) {
				productSummaryView.setCreatedDate(product.getCreatedDate());
			}
			// product status
			if (product.getStatus() != null) {
				productSummaryView.setStatus(product.getStatus());
			}
			// category & subCategory
			if (product.getSubcategories() != null) {
				Set<SubCategory> subCategories = product.getSubcategories();
				productSummaryView.setSubCategory(subCategories);

				Set<Category> categoryList = new HashSet<Category>();
				for (SubCategory subcategory : subCategories) {
					categoryList
							.addAll(categoryDAO
									.findByNameAllIgnoreCase(subcategory
											.getCategory()));
				}
				productSummaryView.setMainCategory(categoryList);
			}

			Set<String> productIds = new HashSet<>();

			productIds.add(product.getId());

			// List<Promotion> promotionSet = promotionDAO
			// .findByProductIdsIn(productIds);
			//
			// if (promotionSet != null && promotionSet.size() != 0) {
			// productSummaryView.setPromotionsApplied("YES");
			// } else {
			// productSummaryView.setPromotionsApplied("NO");
			// }

			productSummaryViewList.add(productSummaryView);
		}
		productSummaryViewResponse.setProducts(productSummaryViewList);

		productSummaryViewResponse.setTotalPages(pageable.getTotalPages());
		productSummaryViewResponse
				.setTotalElements(pageable.getTotalElements());
		productSummaryViewResponse.setNumber(pageable.getNumber());
		productSummaryViewResponse.setSize(pageable.getSize());
		return productSummaryViewResponse;
	}

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
	@Override
	public ProductSummaryViewResponse getAdminManageSellerProduct(
			String dealerId, String searchValue, String brand, String status,
			String categoryNames, String subCategory, int page, int size,
			Sort sort) throws Exception {
		List<ProductSummaryView> productSummaryViewList = new ArrayList<ProductSummaryView>();
		ProductSummaryViewResponse productSummaryViewResponse = new ProductSummaryViewResponse();
		ProductCustomSearch productCustomSearch = new ProductCustomSearch();
		Page<Product> pageable = null;
		User user1 = userDAO.findById(dealerId);
		if (user1 != null) {
			if (user1.getRoles() != null) {
				Set<Role> roles = user1.getRoles();
				for (Role role : roles) {
					if (role.getName().equalsIgnoreCase("DEALER")) {

						Aggregation agg = Aggregation.newAggregation(
								Aggregation.match(Criteria.where("user").is(
										dealerId)),
								Aggregation.project("products").and("id"));

						// Converting the aggregation result into a List
						AggregationResults<StoreProduct> groupResults = mongoTemplate
								.aggregate(agg, Store.class, StoreProduct.class);
						List<StoreProduct> result = groupResults
								.getMappedResults();

						if (result.size() != 0) {
							List<Product> products = null;
							Set<String> prodIds = null;
							List<Product> productList = result.get(0).products;
							if (productList != null && productList.size() != 0) {

								products = new ArrayList<>();
								prodIds = new HashSet<String>();

								for (Product product : productList) {
									if (product != null) {
										prodIds.add(product.getId());
									}
								}

							} else {
								logger.info("Store doesn't have any products");
								throw new Exception(
										"Store doesn't have any products");
							}
							// Search Value
							if (StringUtils.isNotBlank(searchValue)) {
								productCustomSearch.setSearchText(searchValue);

							}

							// Brands
							if (StringUtils.isNotBlank(brand)) {
								String[] brandIds = brand.split(",");
								List<String> brands = new ArrayList<String>();
								for (String brandId : brandIds) {
									brands.add(brandId);
								}
								productCustomSearch.setProductBrandIds(brands);
							}

							// Status
							if (StringUtils.isNotBlank(status)) {
								String[] statuses = status.split(",");
								List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
								for (int i = 0; i < statuses.length; i++) {
									productStatuses.add(ProductStatus
											.valueOf(statuses[i]));
								}

								productCustomSearch
										.setProductStatuses(productStatuses);
							}

							// Categories
							if (StringUtils.isNotBlank(categoryNames)) {
								List<Category> categories = new ArrayList<Category>();
								String[] categoryList = categoryNames
										.split(",");
								for (String catNames : categoryList) {
									categories = categoryDAO
											.findByNameAllIgnoreCase(catNames);
								}
								productCustomSearch.setCategories(categories
										.stream().map(c -> c.getName())
										.collect(Collectors.toList()));
							}

							// SubCategories
							if (StringUtils.isNotBlank(subCategory)) {
								List<SubCategory> subCategories = new ArrayList<>();
								List<String> subCategoriesName = new ArrayList<>();
								String[] subCategoryList = subCategory
										.split(",");
								for (String subcatNames : subCategoryList) {
									subCategories = subCategoryDAO
											.findByName(subcatNames);
									for (SubCategory SubCategory : subCategories) {
										subCategoriesName.add(SubCategory
												.getName());
									}
								}
								productCustomSearch
										.setSubCategoryNames(subCategoriesName);
							}

							// productIds
							if (prodIds != null) {
								productCustomSearch.setProductIds(prodIds);
							}

							// Required fields
							Set<String> requiredFieldSet = new HashSet<>();
							requiredFieldSet.add("_id");
							requiredFieldSet.add("name");
							requiredFieldSet.add("skuId");
							requiredFieldSet.add("model");
							requiredFieldSet.add("createdDate");
							requiredFieldSet.add("subcategories");
							requiredFieldSet.add("status");
							requiredFieldSet.add("brand");
							requiredFieldSet.add("price");

							productCustomSearch
									.setRequiredFields(requiredFieldSet);

							pageable = productDAO.searchProducts(
									productCustomSearch, new PageRequest(page,
											size, sort));

							products.addAll(pageable.getContent());

							if (products.size() != 0) {
								for (Product product : products) {
									ProductSummaryView productSummaryView = new ProductSummaryView();

									productSummaryView.setProductId(product
											.getId());

									if (product.getBrand() != null) {
										productSummaryView.setBrand(product
												.getBrand().getName());
									}
									if (product.getName() != null) {
										productSummaryView.setName(product
												.getName());
									}

									productSummaryView.setSkuId(product
											.getSkuId());

									if (product.getModel() != null) {
										productSummaryView.setModel(product
												.getModel());
									}
									if (product.getCreatedDate() != null) {
										productSummaryView
												.setCreatedDate(product
														.getCreatedDate());
									}
									if (product.getStatus() != null) {
										productSummaryView.setStatus(product
												.getStatus());
									}
									if (product.getSubcategories() != null) {
										Set<SubCategory> subCategories = product
												.getSubcategories();
										productSummaryView
												.setSubCategory(subCategories);
										Set<Category> categoryList = new HashSet<Category>();
										for (SubCategory subcategory : subCategories) {
											categoryList
													.addAll(categoryDAO
															.findByNameAllIgnoreCase(subcategory
																	.getCategory()));
										}
										productSummaryView
												.setMainCategory(categoryList);
									}

									int quantity = 0;
									StoreProductPricing storeProductPricing = storeProductPricingDAO
											.findByStoreIdAndProductId(
													result.get(0).id,
													product.getId());

									if (storeProductPricing != null) {
										if (storeProductPricing.getStockCount() != null) {
											quantity = storeProductPricing
													.getStockCount();
										}

										if (storeProductPricing
												.getSellingPrice() != null) {
											productSummaryView
													.setSellingPrice(storeProductPricing
															.getSellingPrice());
										}
									}
									productSummaryView.setQuantity(quantity);

									productSummaryViewList
											.add(productSummaryView);
								}
							} else {
								logger.info("Search did not return any result....!!!!");
							}
						}
					}
				}
			}
		}
		productSummaryViewResponse.setProducts(productSummaryViewList);

		productSummaryViewResponse.setTotalPages(pageable.getTotalPages());
		productSummaryViewResponse
				.setTotalElements(pageable.getTotalElements());
		productSummaryViewResponse.setNumber(pageable.getNumber());
		productSummaryViewResponse.setSize(pageable.getSize());
		return productSummaryViewResponse;
	}

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
	@Override
	public ProductSummaryViewResponse getAdminManageProviderProduct(
			String providerId, String searchValue, String status,
			String categoryNames, String subCategory, int page, int size,
			Sort sort) {
		ProductCustomSearch productCustomSearch = new ProductCustomSearch();
		ProductSummaryViewResponse productSummaryViewResponse = new ProductSummaryViewResponse();
		Page<Product> pageable = null;
		List<ProductSummaryView> productSummaryViewList = new ArrayList<ProductSummaryView>();
		User user = userDAO.findById(providerId);
		if (user != null) {
			Set<Role> roles = user.getRoles();
			for (Role role : roles) {
				if (role.getName().equalsIgnoreCase("PROVIDER")) {
					List<Product> products = new ArrayList<>();
					ProductBrand brand = brandDAO.findByProvider(user);

					// brand
					if (brand != null) {
						List<String> brands = new ArrayList<String>();
						brands.add(brand.getId());
						productCustomSearch.setProductBrandIds(brands);
					}

					// Search Value
					if (StringUtils.isNotBlank(searchValue)) {
						productCustomSearch.setSearchText(searchValue);

					}

					// Status
					if (StringUtils.isNotBlank(status)) {
						String[] statuses = status.split(",");
						List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
						for (int i = 0; i < statuses.length; i++) {
							productStatuses.add(ProductStatus
									.valueOf(statuses[i]));
						}

						productCustomSearch.setProductStatuses(productStatuses);
					}

					// Categories
					if (StringUtils.isNotBlank(categoryNames)) {
						List<Category> categories = new ArrayList<Category>();
						String[] categoryList = categoryNames.split(",");
						for (String catNames : categoryList) {
							categories = categoryDAO
									.findByNameAllIgnoreCase(catNames);
						}
						productCustomSearch.setCategories(categories.stream()
								.map(c -> c.getName())
								.collect(Collectors.toList()));
					}

					// SubCategories
					if (StringUtils.isNotBlank(subCategory)) {
						List<SubCategory> subCategories = new ArrayList<>();
						List<String> subCategoriesName = new ArrayList<>();
						String[] subCategoryList = subCategory.split(",");
						for (String subcatNames : subCategoryList) {
							subCategories = subCategoryDAO
									.findByName(subcatNames);
							for (SubCategory SubCategory : subCategories) {
								subCategoriesName.add(SubCategory.getName());
							}
						}
						productCustomSearch
								.setSubCategoryNames(subCategoriesName);
					}
					// Required fields
					Set<String> requiredFieldSet = new HashSet<>();
					requiredFieldSet.add("_id");
					requiredFieldSet.add("name");
					requiredFieldSet.add("skuId");
					requiredFieldSet.add("model");
					requiredFieldSet.add("createdDate");
					requiredFieldSet.add("subcategories");
					requiredFieldSet.add("status");
					requiredFieldSet.add("brand");
					requiredFieldSet.add("price");
					requiredFieldSet.add("consolidatedRating");
					requiredFieldSet.add("desc");
					requiredFieldSet.add("assets");
					productCustomSearch.setRequiredFields(requiredFieldSet);

					pageable = productDAO.searchProducts(productCustomSearch,
							new PageRequest(page, size, sort));

					if (pageable.getSize() != 0) {
						products.addAll(pageable.getContent());
					} else {
						logger.info("Search did not return any result....!!!!");
					}

					for (Product product : products) {
						ProductSummaryView productSummaryView = new ProductSummaryView();
						productSummaryView.setProductId(product.getId());
						productSummaryView.setName(product.getName());
						productSummaryView.setSkuId(product.getSkuId());
						if (product.getModel() != null) {
							productSummaryView.setModel(product.getModel());
						}
						if (product.getCreatedDate() != null) {
							productSummaryView.setCreatedDate(product
									.getCreatedDate());
						}
						if (product.getStatus() != null) {
							productSummaryView.setStatus(product.getStatus());
						}
						if (product.getPrice() != null) {
							productSummaryView.setMrp(product.getPrice()
									.getMrp());
						}
						if (product.getSubcategories() != null) {
							Set<SubCategory> subCategories = product
									.getSubcategories();
							productSummaryView.setSubCategory(subCategories);
							Set<Category> categoryList = new HashSet<Category>();
							for (SubCategory subcategory : subCategories) {
								categoryList.addAll(categoryDAO
										.findByNameAllIgnoreCase(subcategory
												.getCategory()));
							}
							productSummaryView.setMainCategory(categoryList);
						}
						Set<String> productIds = new HashSet<>();
						productIds.add(product.getId());
						List<Promotion> promotionSet = promotionDAO
								.findByProductIdsIn(productIds);
						if (promotionSet.size() != 0) {
							productSummaryView.setPromotionsApplied("YES");
						} else {
							productSummaryView.setPromotionsApplied("NO");
						}
						productSummaryViewList.add(productSummaryView);
					}
				}
			}
		}
		productSummaryViewResponse.setProducts(productSummaryViewList);
		productSummaryViewResponse.setTotalPages(pageable.getTotalPages());
		productSummaryViewResponse
				.setTotalElements(pageable.getTotalElements());
		productSummaryViewResponse.setNumber(pageable.getNumber());
		productSummaryViewResponse.setSize(pageable.getSize());
		return productSummaryViewResponse;
	}

	/**
	 * Method for get Dealer manage product
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public ProductSummaryViewResponse getDealerManageProduct(String dealerId,
			String searchValue, String brand, String status,
			String categoryNames, String subCategory, int page, int size,
			Sort sort) throws Exception {
		List<ProductSummaryView> productSummaryViewList = new ArrayList<ProductSummaryView>();
		ProductSummaryViewResponse productSummaryViewResponse = new ProductSummaryViewResponse();
		ProductCustomSearch productCustomSearch = new ProductCustomSearch();
		Page<Product> pageable = null;
		User user1 = userDAO.findById(dealerId);
		if (user1 != null) {
			if (user1.getRoles() != null) {
				Set<Role> roles = user1.getRoles();
				for (Role role : roles) {
					if (role != null) {
						if (role.getName().equalsIgnoreCase("DEALER")) {
							// Calculating Agg time
							Aggregation agg = Aggregation.newAggregation(
									Aggregation.match(Criteria.where("user")
											.is(dealerId)), Aggregation
											.project("products").and("id"));

							// Converting the aggregation result into a List
							AggregationResults<StoreProduct> groupResults = mongoTemplate
									.aggregate(agg, Store.class,
											StoreProduct.class);
							List<StoreProduct> result = groupResults
									.getMappedResults();

							if (result.size() != 0) {
								List<Product> products = null;
								Set<String> prodIds = null;
								List<Product> productList = result.get(0).products;
								if (productList != null
										&& productList.size() != 0) {

									products = new ArrayList<>();
									prodIds = new HashSet<String>();

									for (Product product : productList) {
										if (product != null) {
											prodIds.add(product.getId());
										}
									}

								} else {
									logger.info("Store Doesn't have any products");
									throw new Exception(
											"Store Doesn't have any products");
								}
								// Search Value
								if (StringUtils.isNotBlank(searchValue)) {
									productCustomSearch
											.setSearchText(searchValue);
								}

								// Brands
								if (StringUtils.isNotBlank(brand)) {
									String[] brandIds = brand.split(",");
									List<String> brands = new ArrayList<String>();
									for (String brandId : brandIds) {
										brands.add(brandId);
									}
									productCustomSearch
											.setProductBrandIds(brands);
								}

								// Status
								if (StringUtils.isNotBlank(status)) {
									String[] statuses = status.split(",");
									List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
									for (int i = 0; i < statuses.length; i++) {
										productStatuses.add(ProductStatus
												.valueOf(statuses[i]));
									}

									productCustomSearch
											.setProductStatuses(productStatuses);
								}

								// productIds
								if (prodIds != null) {
									productCustomSearch.setProductIds(prodIds);
								}

								// Categories
								if (StringUtils.isNotBlank(categoryNames)) {
									List<Category> categories = new ArrayList<Category>();
									String[] categoryList = categoryNames
											.split(",");
									for (String catNames : categoryList) {
										categories = categoryDAO
												.findByNameAllIgnoreCase(catNames);
									}
									productCustomSearch
											.setCategories(categories
													.stream()
													.map(c -> c.getName())
													.collect(
															Collectors.toList()));
								}

								// SubCategories
								if (StringUtils.isNotBlank(subCategory)) {
									List<SubCategory> subCategories = new ArrayList<>();
									List<String> subCategoriesName = new ArrayList<>();
									String[] subCategoryList = subCategory
											.split(",");
									for (String subcatNames : subCategoryList) {
										subCategories = subCategoryDAO
												.findByName(subcatNames);
										for (SubCategory SubCategory : subCategories) {
											subCategoriesName.add(SubCategory
													.getName());
										}
									}
									productCustomSearch
											.setSubCategoryNames(subCategoriesName);
								}
								// Required fields
								Set<String> requiredFieldSet = new HashSet<>();
								requiredFieldSet.add("_id");
								requiredFieldSet.add("name");
								requiredFieldSet.add("skuId");
								requiredFieldSet.add("model");
								requiredFieldSet.add("createdDate");
								requiredFieldSet.add("subcategories");
								requiredFieldSet.add("status");
								requiredFieldSet.add("brand");
								requiredFieldSet.add("price");

								productCustomSearch
										.setRequiredFields(requiredFieldSet);

								pageable = productDAO.searchProducts(
										productCustomSearch, new PageRequest(
												page, size, sort));

								products.addAll(pageable.getContent());

								if (products.size() != 0) {
									for (Product product : products) {
										ProductSummaryView productSummaryView = new ProductSummaryView();
										productSummaryView.setProductId(product
												.getId());
										if (product.getBrand() != null) {
											productSummaryView.setBrand(product
													.getBrand().getName());
										}
										productSummaryView.setName(product
												.getName());

										productSummaryView.setSkuId(product
												.getSkuId());

										if (product.getModel() != null) {
											productSummaryView.setModel(product
													.getModel());
										}
										if (product.getCreatedDate() != null) {
											productSummaryView
													.setCreatedDate(product
															.getCreatedDate());
										}
										if (product.getStatus() != null) {
											productSummaryView
													.setStatus(product
															.getStatus());
										}
										if (product.getSubcategories() != null) {
											Set<SubCategory> subCategories = product
													.getSubcategories();
											productSummaryView
													.setSubCategory(subCategories);
											Set<Category> categoryList = new HashSet<Category>();
											for (SubCategory subcategory : subCategories) {
												if (subcategory.getCategory() != null) {
													categoryList
															.addAll(categoryDAO
																	.findByNameAllIgnoreCase(subcategory
																			.getCategory()));
												}
											}
											productSummaryView
													.setMainCategory(categoryList);
										}

										int quantity = 0;
										int localEstimateDeliveryTime = 0;
										int zonalEstimateDeliveryTime = 0;
										int nationalEstimateDeliveryTime = 0;

										StoreProductPricing storeProductPricing = storeProductPricingDAO
												.findByProductIdAndStoreId(
														product.getId(),
														result.get(0).id);

										if (storeProductPricing != null) {
											if (storeProductPricing
													.getStockCount() != null) {
												quantity = storeProductPricing
														.getStockCount();
											}
											if (storeProductPricing
													.getSellingPrice() != null) {
												productSummaryView
														.setSellingPrice(storeProductPricing
																.getSellingPrice());
											}
										}

										productSummaryView
												.setQuantity(quantity);
										productSummaryView
												.setLocalEstimateDeliveryTime(localEstimateDeliveryTime);
										productSummaryView
												.setZonalEstimateDeliveryTime(zonalEstimateDeliveryTime);
										productSummaryView
												.setNationalEstimateDeliveryTime(nationalEstimateDeliveryTime);
										productSummaryViewList
												.add(productSummaryView);
									}
								} else {
									logger.info("Search did not return any result....!!!!");
								}
							}
						}
					}
				}
			}
		}
		productSummaryViewResponse.setProducts(productSummaryViewList);

		productSummaryViewResponse.setTotalPages(pageable.getTotalPages());
		productSummaryViewResponse
				.setTotalElements(pageable.getTotalElements());
		productSummaryViewResponse.setNumber(pageable.getNumber());
		productSummaryViewResponse.setSize(pageable.getSize());
		return productSummaryViewResponse;
	}

	/**
	 * Method for display products count
	 * 
	 * @param productCountParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public long getProductsCount(String productName, String providerName,
			String category, String productsType, String pricing,
			String rating, String latestproducts, Point location,
			String accessory, String status, String brand) throws Exception {
		long productNumber = 0;

		if (StringUtils.isBlank(productName)
				&& StringUtils.isBlank(providerName)
				&& StringUtils.isBlank(category)
				&& StringUtils.isBlank(productsType)
				&& StringUtils.isBlank(pricing) && StringUtils.isBlank(rating)
				&& StringUtils.isBlank(latestproducts) && location == null
				&& StringUtils.isBlank(accessory)
				&& StringUtils.isBlank(status) && StringUtils.isBlank(brand)) {
			// return all products number
			logger.debug("No parameter pass return all product number");
			productNumber = productDAO.count();
			return productNumber;
		} else {
			if (StringUtils.isNotBlank(productName)) {
				logger.debug("ProductCatalogServiceImpl , getProductCount , pass product name");
				productNumber = productDAO.countByName(productName);
			}
			if (StringUtils.isNotBlank(providerName)) {
				logger.debug("ProductCatalogServiceImpl , getProductCount , pass provider name");
				List<User> user = userDAO.findByUsername(providerName,
						new PageRequest(0, 10));
				logger.debug("User size " + user.size());
				Iterator<User> iterator = user.iterator();
				while (iterator.hasNext()) {
					String userId = (iterator.next()).getId();
					logger.debug("User Id " + userId);
					productNumber = productDAO.countByBrand_Provider_Id(userId);
				}
			}

			if (StringUtils.isNotBlank(category)) {
				logger.debug("ProductCatalogServiceImpl , getProductCount , pass category name");
				List<Category> categories = categoryDAO
						.findByNameAllIgnoreCase(category);
				logger.debug("Category size " + categories.size());
				Iterator<Category> iterator = categories.iterator();
				while (iterator.hasNext()) {
					String categoryName = iterator.next().getName();
					productNumber = productDAO
							.countBySubcategories_Category(categoryName);
				}
			}
			if (StringUtils.isNotBlank(productsType)) {
				logger.debug("ProductCatalogServiceImpl , getProductCount , pass productsType name");
				productNumber = productDAO
						.countByproductTypes_Name(productsType);
			}
			if (StringUtils.isNotBlank(pricing)) {
				logger.debug("Pricing :");
				String[] maxminValue = pricing.split(",");
				if (maxminValue.length == 2) {
					double max = Double.parseDouble(maxminValue[0]) + 1;
					double min = Double.parseDouble(maxminValue[1]);

					logger.debug("Pricing : max value " + maxminValue[1]
							+ "min values " + maxminValue[0]);
					productNumber = productDAO
							.countByprice_MrpBetween(min, max);

				} else
					throw new Exception(
							"Error occured trying to search product : pricing needs both min and max value separated by comma");
			}
			if (StringUtils.isNotBlank(latestproducts)) {
				logger.debug("ProductCatalogServiceImpl , getProductCount , pass latestproducts yes/no");
				List<Product> productList = productDAO
						.findTop50AllByOrderByCreatedDateDesc();
				productNumber = productList.size();
			}
			if (StringUtils.isNotBlank(rating)) {
				List<Product> products = new ArrayList<>();
				String ratingStr[] = rating.split(",");
				System.out.println(ratingStr[0] + "  " + ratingStr[1]);
				double max = Double.parseDouble(ratingStr[0]) + .1;
				double min = Double.parseDouble(ratingStr[1]);
				System.out.println(max + " " + min);
				List<Comment> commentList = productCommentDAO
						.findByRating_ratingValBetween(min, max);
				Iterator<Comment> commentIterator = commentList.iterator();
				while (commentIterator.hasNext()) {
					Comment comment = commentIterator.next();
					Product product = productDAO.findOne(comment
							.getDiscussionId());
					if (product != null)
						products.add(product);
				}
				productNumber = products.size();
			}
			if (location != null) {
				List<User> users = new ArrayList<User>();
				Page<User> pageable = userDAO.findByAddresses_Location(
						location, new PageRequest(0, 10));
				users.addAll(pageable.getContent());
				Iterator<User> iterator = users.iterator();
				while (iterator.hasNext()) {
					User user = iterator.next();
					logger.debug("UserID:= " + user.getId());
					logger.debug("UserName:= " + user.getUsername());
					productNumber = productDAO.countByBrand_Provider_Id(user
							.getId());
				}
			}
			if (StringUtils.isNotBlank(accessory)) {
				logger.debug("ProductCatalogServiceImpl , searchProduct() "
						+ accessory);
				boolean accessoryBoolean = Boolean.parseBoolean(accessory);
				productNumber = productDAO.countByAccessory(accessoryBoolean);
			}
			if (StringUtils.isNotBlank(status)) {
				logger.debug("ProductCatalogServiceImpl , searchProduct "
						+ status);
				productNumber = productDAO.countByStatus(status);
			}
			if (StringUtils.isNotBlank(brand)) {
				ProductBrand productBrand = brandDAO.findByName(brand);
				productNumber = productDAO.countByBrand(productBrand);

			}
		}
		return productNumber;
	}

	/**
	 * Method for get particular product based on productId.
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@Override
	public ProductDetailsResponse getProductBypid(String pid) throws Exception {
		ProductDetailsResponse detailsResponse = new ProductDetailsResponse();
		if (StringUtils.isNotBlank(pid)) {
			Product product = productDAO.findOne(pid);
			if (product != null) {
				detailsResponse.setProductId(product.getId());

				detailsResponse.setProductName(product.getName());

				detailsResponse.setSkuId(product.getSkuId());

				if (product.getModel() != null) {
					detailsResponse.setModel(product.getModel());
				}

				if (product.getBrand() != null) {
					detailsResponse.setBrand(product.getBrand().getName());
				}
				if (product.getFaqs() != null) {
					detailsResponse.setFaqs(product.getFaqs());
				}
				if (product.getAccessories() != null) {
					detailsResponse.setAccessories(product.getAccessories());
				}
				if (product.getRelatedProducts() != null) {
					detailsResponse.setRelatedProducts(product
							.getRelatedProducts());
				}
				if (product.getPrice() != null) {
					detailsResponse.setPrice(Double.toString(product.getPrice()
							.getMrp()));
				}
				if (product.getAttributes() != null) {
					detailsResponse.setAttributes(product.getAttributes());
				}
				if (product.getFeatures() != null) {
					detailsResponse.setFeatures(product.getFeatures());
				}
				if (product.getSubcategories() != null) {
					detailsResponse
							.setSubcategories(product.getSubcategories());
				}
				if (product.getProductTypes() != null) {
					detailsResponse.setProductTypes(product.getProductTypes());
				}

				detailsResponse.setAccessory(Boolean.toString(product
						.isAccessory()));
				if (product.getTcDoc() != null) {
					detailsResponse.setTcDoc(product.getTcDoc());
				}
				if (product.getAssets() != null) {
					detailsResponse.setAssets(product.getAssets());
				}
				if (product.getConsolidatedRating() != null) {
					detailsResponse.setRating(product.getConsolidatedRating());
				}
				if (product.getQuantityType() != null) {
					detailsResponse.setProductQuantityType(product
							.getQuantityType());
				}
			} else {
				logger.info("Product doesn't exists");
				throw new Exception(
						"Product doesn't exists in our system for product id:"
								+ pid);
			}
		} else {
			logger.info("Product id null");
		}
		return detailsResponse;
	}

	/**
	 * Method for get Admin Product view details
	 * 
	 * @param pid
	 * @return
	 */
	@Override
	public ProductDetailView getSuperAdminProductviewDetails(String pid) {
		ProductDetailView productDetailView = null;
		if (StringUtils.isNotBlank(pid)) {
			Product product = productDAO.findById(pid);
			{
				if (product != null) {
					productDetailView = new ProductDetailView();
					// pid
					productDetailView.setpId(product.getId());
					// skuid
					productDetailView.setSkuid(product.getSkuId());
					// model
					if (product.getModel() != null) {
						productDetailView.setModel(product.getModel());
					}
					// name
					if (product.getName() != null) {
						productDetailView.setName(product.getName());
					}
					// price
					if (product.getPrice() != null) {
						productDetailView.setMrp(product.getPrice().getMrp());
					}
					// brand
					if (product.getBrand() != null) {
						productDetailView
								.setBrand(product.getBrand().getName());
					}
					// Last modified date
					if (product.getLastModifiedDate() != null) {
						productDetailView.setOnboardedDate(product
								.getLastModifiedDate());
					}
					// status
					if (product.getStatus() != null) {
						productDetailView.setStatus(product.getStatus().name());
					}
					// onboarded by
					if (product.getCreatedBy() != null) {
						String createdBy = product.getCreatedBy();
						User onBoardedBy = userDAO.findById(createdBy);
						if (onBoardedBy != null) {
							if (onBoardedBy.getFirstName() != null
									&& onBoardedBy.getLastName() != null) {
								productDetailView.setOnboardedBy(onBoardedBy
										.getFirstName()
										+ " "
										+ onBoardedBy.getLastName());
							} else if (onBoardedBy.getFirstName() != null) {
								productDetailView.setOnboardedBy(onBoardedBy
										.getFirstName());
							}
						}
					}

					if (product.getBrand() != null) {
						ProductBrand productBrand = product.getBrand();
						if (productBrand != null) {
							if (productBrand.getProvider() != null) {
								User user = productBrand.getProvider();
								if (user != null) {
									Set<Role> roles = user.getRoles();
									for (Role role : roles) {
										if (role.getUserPackage() != null) {
											productDetailView
													.setPackageName(role
															.getUserPackage()
															.getName());
										}
									}
								}
							}
						}
					}

					Set<String> productIds = new HashSet<>();
					productIds.add(product.getId());
					// promotion applied
					List<Promotion> promotionSet = promotionDAO
							.findByProductIdsIn(productIds);
					if (promotionSet.size() != 0) {
						productDetailView.setPromotionApplied("YES");
					} else {
						productDetailView.setPromotionApplied("NO");
					}

					// Set<Store> storeList =
					// storeDAO.findByProductsIn(product);
					int dealerCount = 0;
					int storeCount = 0;
					int cityCount = 0;
					Set<String> dealerIds = new HashSet<>();
					Set<String> citySet = new HashSet<>();

					// if (storeList != null && storeList.size() != 0) {
					// for (Store store : storeList) {
					// User user1 = store.getUser();
					// dealerIds.add(user1.getId());
					// storeCount++;
					// Set<Address> addresses = user1.getAddresses();
					//
					// for (Address address : addresses) {
					// citySet.add(address.getCity());
					// }
					// }
					// }

					for (String idCount : dealerIds) {
						dealerCount++;
						logger.info(idCount);
					}
					for (String cityCal : citySet) {
						cityCount++;
						logger.info(cityCal);
					}

					productDetailView.setNumberOfDealers(dealerCount);

					productDetailView.setNumberOfLocations(storeCount);

					productDetailView.setNumberOfCities(cityCount);
					// subcategory and category
					Set<SubCategory> subCategories = product.getSubcategories();
					if (subCategories != null && subCategories.size() != 0) {
						productDetailView.setSubCategory(subCategories);
						Set<Category> categoryList = new HashSet<Category>();
						for (SubCategory subcategory : subCategories) {
							categoryList.addAll(categoryDAO
									.findByNameAllIgnoreCase(subcategory
											.getCategory()));
						}
						productDetailView.setMainCategory(categoryList);
					}
				}
			}
		}
		return productDetailView;
	}

	/**
	 * Method for get Provider product view details
	 * 
	 * @param pid
	 * @return
	 */
	@Override
	public ProductDetailView getProviderProductViewDetails(String pid) {
		ProductDetailView productDetailView = null;
		if (StringUtils.isNotBlank(pid)) {
			Product product = productDAO.findById(pid);
			{
				if (product != null) {
					productDetailView = new ProductDetailView();
					productDetailView.setpId(product.getId());
					productDetailView.setModel(product.getModel());
					productDetailView.setSkuid(product.getSkuId());
					productDetailView.setName(product.getName());
					productDetailView.setMrp(product.getPrice().getMrp());
					productDetailView.setOnboardedDate(product
							.getLastModifiedDate());
					productDetailView.setStatus(product.getStatus().name());
					productDetailView.setOnboardedBy(product.getCreatedBy());
					ProductBrand productBrand = product.getBrand();
					if (productBrand != null) {
						User user = productBrand.getProvider();
						Set<Role> roles = user.getRoles();
						for (Role role : roles) {
							UserPackage userPackage = role.getUserPackage();
							if (userPackage != null) {
								productDetailView.setPackageName(userPackage
										.getName());
							}
						}
					}
					Set<String> productIds = new HashSet<>();
					productIds.add(product.getId());
					List<Promotion> promotionSet = promotionDAO
							.findByProductIdsIn(productIds);
					if (promotionSet.size() != 0) {
						productDetailView.setPromotionApplied("YES");
					} else {
						productDetailView.setPromotionApplied("NO");
					}
					Set<Store> storeList = new HashSet<Store>();
					storeList = storeDAO.findByProductsIn(product);
					int dealerCount = 0;
					int storeCount = 0;
					int cityCount = 0;
					Set<String> dealerIds = new HashSet<>();
					Set<String> citySet = new HashSet<>();
					for (Store store : storeList) {
						User user1 = store.getUser();
						dealerIds.add(user1.getId());
						storeCount++;
						Set<Address> addresses = user1.getAddresses();

						for (Address address : addresses) {
							citySet.add(address.getCity());
						}
					}
					for (String idCount : dealerIds) {
						dealerCount++;
						logger.info(idCount);
					}
					for (String cityCal : citySet) {
						cityCount++;
						logger.info(cityCal);
					}
					productDetailView.setNumberOfDealers(dealerCount);
					productDetailView.setNumberOfLocations(storeCount);
					productDetailView.setNumberOfCities(cityCount);
					Set<SubCategory> subCategories = product.getSubcategories();
					productDetailView.setSubCategory(subCategories);
					Set<Category> categoryList = new HashSet<Category>();
					for (SubCategory subcategory : subCategories) {
						categoryList.addAll(categoryDAO
								.findByNameAllIgnoreCase(subcategory
										.getCategory()));
					}
					productDetailView.setMainCategory(categoryList);
				}
			}
		}
		return productDetailView;
	}

	/**
	 * method for admin manage provider product view details
	 * 
	 * @param pid
	 * @param providerId
	 * @return
	 */
	@Override
	public ProductDetailView getAdminManageProviderProductViewDetails(
			String pid, String providerId) {
		ProductDetailView productDetailView = null;
		if (StringUtils.isNotBlank(providerId)) {
			User provider = userDAO.findById(providerId);
			if (provider != null) {
				if (StringUtils.isNotBlank(pid)) {
					Product product = productDAO.findById(pid);
					{
						if (product != null) {
							productDetailView = new ProductDetailView();
							// Id
							productDetailView.setpId(product.getId());

							// skuId
							productDetailView.setSkuid(product.getSkuId());

							// model
							if (product.getModel() != null) {
								productDetailView.setModel(product.getModel());
							}

							// product name
							if (product.getName() != null) {
								productDetailView.setName(product.getName());
							}
							// product
							if (product.getLastModifiedDate() != product
									.getLastModifiedDate()) {
								productDetailView.setOnboardedDate(product
										.getLastModifiedDate());
							}
							// status
							if (product.getStatus().name() != null) {
								productDetailView.setStatus(product.getStatus()
										.name());
							}
							// created date
							if (product.getCreatedBy() != null) {
								productDetailView.setOnboardedBy(product
										.getCreatedBy());
							}
							// Brand
							ProductBrand productBrand = product.getBrand();
							if (productBrand != null) {
								User user = productBrand.getProvider();
								Set<Role> roles = user.getRoles();
								for (Role role : roles) {
									UserPackage userPackage = role
											.getUserPackage();
									if (userPackage != null) {
										productDetailView
												.setPackageName(userPackage
														.getName());
									}
								}
							}
							// Promotion
							Set<String> productIds = new HashSet<>();
							productIds.add(product.getId());
							List<Promotion> promotionSet = promotionDAO
									.findByProductIdsIn(productIds);
							if (promotionSet.size() != 0) {
								productDetailView.setPromotionApplied("YES");
							} else {
								productDetailView.setPromotionApplied("NO");
							}
							Set<Store> storeList = new HashSet<Store>();
							storeList = storeDAO.findByProductsIn(product);
							int dealerCount = 0;
							int storeCount = 0;
							int cityCount = 0;
							Set<String> dealerIds = new HashSet<>();
							Set<String> citySet = new HashSet<>();
							for (Store store : storeList) {
								User user1 = store.getUser();
								dealerIds.add(user1.getId());
								storeCount++;
								Set<Address> addresses = user1.getAddresses();

								for (Address address : addresses) {
									citySet.add(address.getCity());
								}
							}
							for (String idCount : dealerIds) {
								dealerCount++;
								logger.info(idCount);
							}
							for (String cityCal : citySet) {
								cityCount++;
								logger.info(cityCal);
							}
							productDetailView.setNumberOfDealers(dealerCount);
							productDetailView.setNumberOfLocations(storeCount);
							productDetailView.setNumberOfCities(cityCount);
							Set<SubCategory> subCategories = product
									.getSubcategories();
							productDetailView.setSubCategory(subCategories);
							Set<Category> categoryList = new HashSet<Category>();
							for (SubCategory subcategory : subCategories) {
								categoryList.addAll(categoryDAO
										.findByNameAllIgnoreCase(subcategory
												.getCategory()));
							}
							productDetailView.setMainCategory(categoryList);
						}
					}
				}
			}
		}
		return productDetailView;
	}

	/**
	 * Method for get Dealer products view Details
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@Override
	public ProductDetailView getDealerProductViewDetails(String dealerId,
			String pid) throws Exception {
		ProductDetailView productDetailView = null;
		if (StringUtils.isNotBlank(dealerId) && StringUtils.isNotBlank(pid)) {
			User dealer = userDAO.findById(dealerId);
			Product product = productDAO.findById(pid);
			if (dealer != null && product != null) {
				boolean linktoMe = false;
				productDetailView = new ProductDetailView();
				if (product.getId() != null) {
					productDetailView.setpId(product.getId());
				}
				if (product.getModel() != null) {
					productDetailView.setModel(product.getModel());
				}
				if (product.getSkuId() != null) {
					productDetailView.setSkuid(product.getSkuId());
				}
				if (product.getName() != null) {
					productDetailView.setName(product.getName());
				}
				if (product.getQuantityType() != null) {
					productDetailView
							.setQuantityType(product.getQuantityType());
				}
				if (product.getBrand() != null) {
					productDetailView.setBrand(product.getBrand().getName());
				}
				if (product.getLastModifiedDate() != null) {
					productDetailView.setOnboardedDate(product
							.getLastModifiedDate());
				}
				if (product.getStatus() != null) {
					productDetailView.setStatus(product.getStatus().name());
				}

				Set<String> values = new HashSet<>();

				if (product.getAttributes().size() != 0) {
					Set<ProductAttribute> attributes = product.getAttributes();
					for (ProductAttribute productAttribute : attributes) {

						if (productAttribute.getKey()
								.equalsIgnoreCase("Length")) {
							values = productAttribute.getValues();

							if (values.size() != 0 && values != null) {
								productDetailView.setLength(values);
							} else {
								values.add("-1");
								productDetailView.setLength(values);
							}
						} else if (productAttribute.getKey().equalsIgnoreCase(
								"Breadth")) {
							values = productAttribute.getValues();
							if (values.size() != 0 && values != null) {
								productDetailView.setBreadth(values);
							} else {
								values.add("-1");
								productDetailView.setBreadth(values);
							}
						} else if (productAttribute.getKey().equalsIgnoreCase(
								"Height")) {
							values = productAttribute.getValues();
							if (values.size() != 0 && values != null) {
								productDetailView.setHeight(values);
							} else {
								values.add("-1");
								productDetailView.setHeight(values);
							}
						} else if (productAttribute.getKey().equalsIgnoreCase(
								"Weight")) {
							values = productAttribute.getValues();
							if (values.size() != 0 && values != null) {
								productDetailView.setWeight(values);
							} else {
								values.add("-1");
								productDetailView.setWeight(values);
							}
						}
					}
				}

				if (product.getSubcategories() != null) {
					Set<SubCategory> subCategories = product.getSubcategories();
					productDetailView.setSubCategory(subCategories);
					Set<Category> categoryList = new HashSet<Category>();
					for (SubCategory subcategory : subCategories) {
						if (subcategory.getCategory() != null) {
							categoryList.addAll(categoryDAO
									.findByNameAllIgnoreCase(subcategory
											.getCategory()));
						}
					}
					productDetailView.setMainCategory(categoryList);
				}

				if (product.getPrice() != null) {
					productDetailView.setMrp(product.getPrice().getMrp());
				}

				Aggregation agg = Aggregation.newAggregation(
						Aggregation.match(Criteria.where("user").is(dealerId)
								.and("products").in(pid)),
						Aggregation.project("storename").and("id"));

				// Converting the aggregation result into a List
				AggregationResults<StoreProduct> groupResults = mongoTemplate
						.aggregate(agg, Store.class, StoreProduct.class);
				List<StoreProduct> result = groupResults.getMappedResults();

				if (result.size() != 0) {
					linktoMe = true;
				}
				productDetailView.setLinkedToMe(linktoMe);

				StoreProductPricing storeProductPricing = storeProductPricingDAO
						.findByProductIdAndStoreId(pid, result.get(0).id);

				if (storeProductPricing != null) {
					String productId1 = storeProductPricing.getProductId();
					if (productId1.equalsIgnoreCase(pid)) {
						if (storeProductPricing.getStockCount() != null) {
							productDetailView.setQty(storeProductPricing
									.getStockCount());
						}
						if (storeProductPricing.getSellingPrice() != null) {
							productDetailView
									.setSellingPrice(storeProductPricing
											.getSellingPrice());
						}

						Shipping shipping = storeProductPricing.getShipping();
						if (shipping != null) {
							if (shipping.getMinLocalProcurementSLA() != null) {
								productDetailView
										.setMinLocalEstimateDeliveryTime(shipping
												.getMinLocalProcurementSLA());
							}
							if (shipping.getMinZonalProcurementSLA() != null) {
								productDetailView
										.setMinZonalEstimateDeliveryTime(shipping
												.getMinZonalProcurementSLA());
							}

							if (shipping.getMinNationalProcurementSLA() != null) {
								productDetailView
										.setMinNationalEstimateDeliveryTime(shipping
												.getMinNationalProcurementSLA());
							}
							if (shipping.getMaxLocalProcurementSLA() != null) {
								productDetailView
										.setMaxLocalEstimateDeliveryTime(shipping
												.getMaxLocalProcurementSLA());
							}
							if (shipping.getMaxZonalProcurementSLA() != null) {
								productDetailView
										.setMaxZonalEstimateDeliveryTime(shipping
												.getMaxZonalProcurementSLA());
							}
							if (shipping.getMaxNationalProcurementSLA() != null) {
								productDetailView
										.setMaxNationalEstimateDeliveryTime(shipping
												.getMaxNationalProcurementSLA());
							}
							if (shipping.getLocalShippingChrg() != null) {
								productDetailView
										.setLocalEstimateDeliveryChrg(shipping
												.getLocalShippingChrg());
							}
							if (shipping.getZonalShippingChrg() != null) {
								productDetailView
										.setZonalEstimateDeliveryChrg(shipping
												.getZonalShippingChrg());
							}
							if (shipping.getNationalShippingChrg() != null) {
								productDetailView
										.setNationalEstimateDeliveryChrg(shipping
												.getNationalShippingChrg());
							}
							if (shipping.getLocalDelivery() != null) {
								productDetailView.setLocalRegion(shipping
										.getLocalDelivery());
							}
							if (shipping.getZonalDelivery() != null) {
								productDetailView.setZonalRegion(shipping
										.getZonalDelivery());
							}
							if (shipping.getNationalDelivery() != null) {
								productDetailView.setNationalRegion(shipping
										.getNationalDelivery());
							}

						}
						if (storeProductPricing.getMinQuantityBuy() != null) {
							productDetailView
									.setMinQuantityBuy(storeProductPricing
											.getMinQuantityBuy());
						}
						if (storeProductPricing.getMbgShare() != null) {
							productDetailView.setMbgShare(storeProductPricing
									.getMbgShare());
						}

						if (storeProductPricing.getDiscount() != null) {
							productDetailView.setDiscount(storeProductPricing
									.getDiscount());
						}
						if (storeProductPricing.isStoreDelivery()) {
							productDetailView.setWillYouDeliver(true);
						} else {
							productDetailView.setWillYouDeliver(false);
						}
					}
				}
			}
		}
		Set<String> val = new HashSet<String>();
		val.add("-1");
		if (productDetailView.getLength() == null) {
			productDetailView.setLength(val);
		}
		if (productDetailView.getBreadth() == null) {
			productDetailView.setBreadth(val);
		}
		if (productDetailView.getHeight() == null) {
			productDetailView.setHeight(val);
		}
		if (productDetailView.getWeight() == null) {
			productDetailView.setWeight(val);
		}

		return productDetailView;
	}

	@Override
	public QuoteRequestResponse getAdminManageQuoteRequestViewDetails(
			String requestQuoteId) {
		QuoteRequest quoteRequest = null;
		QuoteRequestResponse quoteRequestResponse = null;
		if (StringUtils.isNotBlank(requestQuoteId)) {
			quoteRequest = quoteRequestDAO.findOne(requestQuoteId);
			if (quoteRequest != null) {
				quoteRequestResponse = new QuoteRequestResponse();
				// Id
				if (quoteRequest.getId() != null) {
					quoteRequestResponse.setId(quoteRequest.getId());
				}
				// Customer
				if (quoteRequest.getCustomer() != null) {
					quoteRequestResponse
							.setCustomer(quoteRequest.getCustomer());
				}
				// Zipcode
				if (quoteRequest.getZipcode() != null) {
					quoteRequestResponse.setZipcode(quoteRequest.getZipcode());
				}

				// Note
				if (quoteRequest.getNote() != null) {
					quoteRequestResponse.setNote(quoteRequest.getNote());
				}
				// Followup date
				if (quoteRequest.getFollowupDate() != null) {
					quoteRequestResponse.setFollowupDate(quoteRequest
							.getFollowupDate());
				}
				// Reason for reject
				if (quoteRequest.getReasonForReject() != null) {
					quoteRequestResponse.setReasonForReject(quoteRequest
							.getReasonForReject());
				}

				quoteRequestResponse
						.setQuoteSellingPriceIncludeTax(quoteRequest
								.isQuoteSellingPriceIncludeTax());

				Set<String> sellerIds = new HashSet<>();
				if (quoteRequest.getProducts() != null
						&& quoteRequest.getProducts().size() != 0) {

					for (QuoteRequestProduct product : quoteRequest
							.getProducts()) {
						if (product != null) {
							if (product.getQuoteProductPricings() != null
									&& product.getQuoteProductPricings().size() != 0) {
								for (SellerQuoteProductPricing quoteProductPricing : product
										.getQuoteProductPricings()) {
									if (quoteProductPricing != null) {
										sellerIds.add(quoteProductPricing
												.getSeller().getId());
									}
								}
							}
						}
					}
				}

				List<SellerQuoteProductResponse> sellerQuoteProductResponseSet = new ArrayList<>();
				for (String sellerId : sellerIds) {
					// seller
					SellerQuoteProductResponse sellerQuoteProductResponse = new SellerQuoteProductResponse();
					User seller = userDAO.findById(sellerId);
					sellerQuoteProductResponse.setSeller(seller);

					List<QuoteProductResponse> quoteProductResponseSet = new ArrayList<>();

					for (QuoteRequestProduct product : quoteRequest
							.getProducts()) {
						if (product != null) {
							Set<QuoteProductPricingResponse> productPricingsSet = new HashSet<>();
							if (product.getQuoteProductPricings() != null
									&& product.getQuoteProductPricings().size() != 0) {
								for (SellerQuoteProductPricing quoteProductPricing : product
										.getQuoteProductPricings()) {
									if (quoteProductPricing != null) {
										if (seller.getId().equalsIgnoreCase(
												quoteProductPricing.getSeller()
														.getId())) {
											QuoteProductResponse quoteProductResponse = new QuoteProductResponse();
											if (product.getId() != null) {
												quoteProductResponse
														.setQuoteProductId(product
																.getId());
											}
											if (product.getProductName() != null) {
												quoteProductResponse
														.setProductName(product
																.getProductName());
											}
											if (product.getProductId() != null) {
												quoteProductResponse
														.setProductId(product
																.getProductId());
											}
											if (product.getBrand() != null) {
												quoteProductResponse
														.setBrand(product
																.getBrand());
											}
											if (product.getQuantityType() != null) {
												quoteProductResponse
														.setQuantityType(product
																.getQuantityType());
											}
											if (product.getQuantity() != null) {
												quoteProductResponse
														.setQuantity(product
																.getQuantity());
											}

											for (SellerQuoteProductPricing quoteProductPricing1 : product
													.getQuoteProductPricings()) {
												if (quoteProductPricing1 != null) {
													QuoteProductPricingResponse quoteProductPricingResponse = new QuoteProductPricingResponse();
													if (quoteProductPricing1
															.getSeller() != null) {
														quoteProductPricingResponse
																.setSellerId(quoteProductPricing1
																		.getSeller()
																		.getId());
													}
													if (quoteProductPricing1
															.getId() != null) {
														quoteProductPricingResponse
																.setQuoteProductPricingId(quoteProductPricing1
																		.getId());
													}
													if (quoteProductPricing1
															.getMrp() != null) {
														quoteProductPricingResponse
																.setMrp(quoteProductPricing1
																		.getMrp());
													}
													if (quoteProductPricing1
															.getSellingPrice() != null) {
														quoteProductPricingResponse
																.setSellingPrice(quoteProductPricing1
																		.getSellingPrice());
													}
													if (quoteProductPricing1
															.getBuildOnnSellingPrice() != null) {
														quoteProductPricingResponse
																.setBuildOnnSellingPrice(quoteProductPricing1
																		.getBuildOnnSellingPrice());
													}
													if (quoteProductPricing1
															.getShippingCharge() != null) {
														quoteProductPricingResponse
																.setShippingCharge(quoteProductPricing1
																		.getShippingCharge());
														sellerQuoteProductResponse
																.setShippingCharge(quoteProductPricing1
																		.getShippingCharge());
													}

													if (quoteProductPricing1
															.getDiscount() != null) {
														quoteProductPricingResponse
																.setDiscount(quoteProductPricing1
																		.getDiscount());
													}
													if (quoteProductPricing1
															.getTax() != null) {
														quoteProductPricingResponse
																.setTax(quoteProductPricing1
																		.getTax());
													}
													// Total price
													if (product.getQuantity() != null
															&& quoteProductPricing1
																	.getBuildOnnSellingPrice() != null) {
														double totalAmount = product
																.getQuantity()
																* quoteProductPricing1
																		.getBuildOnnSellingPrice();

														double vatAmount = 0;
														// Vat price
														if (quoteProductPricing1
																.getTax() != null) {
															vatAmount = ((totalAmount * quoteProductPricing1
																	.getTax()) / 100);
															quoteProductPricingResponse
																	.setVatAmount(vatAmount);
														}

														quoteProductPricingResponse
																.setTotalPrice(totalAmount
																		+ vatAmount);
													}
													productPricingsSet
															.add(quoteProductPricingResponse);
												}
											}
											quoteProductResponse
													.setProductPricings(productPricingsSet);
											quoteProductResponseSet
													.add(quoteProductResponse);

										}
									}
								}
							}
						}
					}
					sellerQuoteProductResponse
							.setProducts(quoteProductResponseSet);
					sellerQuoteProductResponseSet
							.add(sellerQuoteProductResponse);
				}
				// Products
				quoteRequestResponse.setProducts(sellerQuoteProductResponseSet);
				// Buildonn QuoteRequestId
				if (quoteRequest.getBuildOnnQuoteRequestId() != null) {
					quoteRequestResponse.setBuildOnnQuoteRequestId(quoteRequest
							.getBuildOnnQuoteRequestId());
				}
				// Status
				if (quoteRequest.getStatus() != null) {
					quoteRequestResponse.setStatus(quoteRequest.getStatus()
							.toString());
				}
				// DeliveryDate
				if (quoteRequest.getDeliveryDate() != null) {
					quoteRequestResponse.setDeliveryDate(quoteRequest
							.getDeliveryDate());
				}
				// CreatedDate
				quoteRequestResponse.setCreatedDate(quoteRequest
						.getCreatedDate());
			}
		}
		return quoteRequestResponse;
	}

	@Override
	public QuoteRequestResponse getDealerManageQuoteRequestViewDetails(
			String quoteRequestId, String sellerId) {
		QuoteRequest quoteRequest = null;
		QuoteRequestResponse quoteRequestResponse = new QuoteRequestResponse();
		if (StringUtils.isNotBlank(sellerId)
				&& StringUtils.isNotBlank(quoteRequestId)) {
			User seller = userDAO.findById(sellerId);
			if (seller != null) {
				quoteRequest = quoteRequestDAO.findOne(quoteRequestId);
				if (quoteRequest != null) {
					// Id
					if (quoteRequest.getId() != null) {
						quoteRequestResponse.setId(quoteRequest.getId());
					}
					// Customer
					if (quoteRequest.getCustomer() != null) {
						quoteRequestResponse.setCustomer(quoteRequest
								.getCustomer());
					}
					// Zipcode
					if (quoteRequest.getZipcode() != null) {
						quoteRequestResponse.setZipcode(quoteRequest
								.getZipcode());
					}
					// Note
					if (quoteRequest.getNote() != null) {
						quoteRequestResponse.setNote(quoteRequest.getNote());
					}
					// FollowUp Date
					if (quoteRequest.getFollowupDate() != null) {
						quoteRequestResponse.setFollowupDate(quoteRequest
								.getFollowupDate());
					}
					// Reason for reject
					if (quoteRequest.getReasonForReject() != null) {
						quoteRequestResponse.setReasonForReject(quoteRequest
								.getReasonForReject());
					}
					// QuoteSellingPriceIncludeTax
					quoteRequestResponse
							.setQuoteSellingPriceIncludeTax(quoteRequest
									.isQuoteSellingPriceIncludeTax());

					List<SellerQuoteProductResponse> sellerQuoteProductResponseSet = new ArrayList<>();
					List<QuoteProductResponse> quoteProductResponseSet = new ArrayList<>();
					SellerQuoteProductResponse sellerQuoteProductResponse = new SellerQuoteProductResponse();
					sellerQuoteProductResponse.setSeller(seller);

					if (quoteRequest.getProducts() != null
							&& quoteRequest.getProducts().size() != 0) {
						for (QuoteRequestProduct product : quoteRequest
								.getProducts()) {
							if (product != null) {
								Set<QuoteProductPricingResponse> productPricingsSet = new HashSet<>();
								if (product.getQuoteProductPricings() != null
										&& product.getQuoteProductPricings()
												.size() != 0) {
									for (SellerQuoteProductPricing quoteProductPricing : product
											.getQuoteProductPricings()) {
										if (quoteProductPricing != null) {
											if (seller
													.getId()
													.equalsIgnoreCase(
															quoteProductPricing
																	.getSeller()
																	.getId())) {
												QuoteProductResponse quoteProductResponse = new QuoteProductResponse();
												if (product.getId() != null) {
													quoteProductResponse
															.setQuoteProductId(product
																	.getId());
												}
												if (product.getProductName() != null) {
													quoteProductResponse
															.setProductName(product
																	.getProductName());
												}
												if (product.getProductId() != null) {
													quoteProductResponse
															.setProductId(product
																	.getProductId());
												}
												if (product.getBrand() != null) {
													quoteProductResponse
															.setBrand(product
																	.getBrand());
												}
												if (product.getQuantityType() != null) {
													quoteProductResponse
															.setQuantityType(product
																	.getQuantityType());
												}
												if (product.getQuantity() != null) {
													quoteProductResponse
															.setQuantity(product
																	.getQuantity());
												}

												for (SellerQuoteProductPricing quoteProductPricing1 : product
														.getQuoteProductPricings()) {
													if (quoteProductPricing1 != null) {
														if (seller
																.getId()
																.equalsIgnoreCase(
																		quoteProductPricing1
																				.getSeller()
																				.getId())) {
															QuoteProductPricingResponse quoteProductPricingResponse = new QuoteProductPricingResponse();
															if (quoteProductPricing1
																	.getSeller() != null) {
																quoteProductPricingResponse
																		.setSellerId(quoteProductPricing1
																				.getSeller()
																				.getId());
															}
															if (quoteProductPricing1
																	.getId() != null) {
																quoteProductPricingResponse
																		.setQuoteProductPricingId(quoteProductPricing1
																				.getId());
															}

															if (quoteProductPricing1
																	.getMrp() != null) {
																quoteProductPricingResponse
																		.setMrp(quoteProductPricing1
																				.getMrp());
															}
															if (quoteProductPricing1
																	.getSellingPrice() != null) {
																quoteProductPricingResponse
																		.setSellingPrice(quoteProductPricing1
																				.getSellingPrice());
															}
															if (quoteProductPricing1
																	.getBuildOnnSellingPrice() != null) {
																quoteProductPricingResponse
																		.setBuildOnnSellingPrice(quoteProductPricing1
																				.getBuildOnnSellingPrice());
															}
															if (quoteProductPricing1
																	.getShippingCharge() != null) {
																quoteProductPricingResponse
																		.setShippingCharge(quoteProductPricing1
																				.getShippingCharge());
																sellerQuoteProductResponse
																		.setShippingCharge(quoteProductPricing1
																				.getShippingCharge());
															}
															// Extra
															// Benefits
															if (quoteProductPricing1
																	.getExtraBenefits() != null) {
																quoteProductPricingResponse
																		.setExtraBenefits(quoteProductPricing1
																				.getExtraBenefits());
																sellerQuoteProductResponse
																		.setExtraBenefits(quoteProductPricing1
																				.getExtraBenefits());
															}
															if (quoteProductPricing1
																	.getDiscount() != null) {
																quoteProductPricingResponse
																		.setDiscount(quoteProductPricing1
																				.getDiscount());
															}
															if (quoteProductPricing1
																	.getTax() != null) {
																quoteProductPricingResponse
																		.setTax(quoteProductPricing1
																				.getTax());
															}

															// Total price
															if (product
																	.getQuantity() != null
																	&& quoteProductPricing1
																			.getSellingPrice() != null) {
																double totalAmount = product
																		.getQuantity()
																		* quoteProductPricing1
																				.getSellingPrice();
																double vatAmount = 0;
																// Vat price
																if (quoteProductPricing1
																		.getTax() != null) {
																	vatAmount = ((totalAmount * quoteProductPricing1
																			.getTax()) / 100);
																	quoteProductPricingResponse
																			.setVatAmount(vatAmount);
																}

																quoteProductPricingResponse
																		.setTotalPrice(totalAmount
																				+ vatAmount);
															}
															productPricingsSet
																	.add(quoteProductPricingResponse);
														}
													}
												}

												quoteProductResponse
														.setProductPricings(productPricingsSet);
												quoteProductResponseSet
														.add(quoteProductResponse);
											}
										}
									}
								}
							}
						}
					}
					sellerQuoteProductResponse
							.setProducts(quoteProductResponseSet);
					sellerQuoteProductResponseSet
							.add(sellerQuoteProductResponse);
					quoteRequestResponse
							.setProducts(sellerQuoteProductResponseSet);
					// Buildonn QuoteId
					if (quoteRequest.getBuildOnnQuoteRequestId() != null) {
						quoteRequestResponse
								.setBuildOnnQuoteRequestId(quoteRequest
										.getBuildOnnQuoteRequestId());
					}
					// Status
					if (quoteRequest.getStatus() != null) {
						quoteRequestResponse.setStatus(quoteRequest.getStatus()
								.toString());
					}
					// DeliveryDate
					if (quoteRequest.getDeliveryDate() != null) {
						quoteRequestResponse.setDeliveryDate(quoteRequest
								.getDeliveryDate());
					}
					// CreatedDate
					quoteRequestResponse.setCreatedDate(quoteRequest
							.getCreatedDate());
				}
			}
		}
		return quoteRequestResponse;
	}

	@Override
	public QuoteRequestResponse getCustomerManageQuoteRequestViewDetails(
			String quoteRequestId, String customerId) {
		QuoteRequestResponse quoteRequestResponse = null;
		if (StringUtils.isNotBlank(quoteRequestId)
				&& StringUtils.isNotBlank(customerId)) {
			User customer = userDAO.findById(customerId);

			if (customer != null) {
				QuoteRequest quoteRequest = quoteRequestDAO
						.findByCustomerAndId(customer, quoteRequestId);
				if (quoteRequest != null) {
					quoteRequestResponse = new QuoteRequestResponse();
					// QuoteId
					if (quoteRequest.getId() != null) {
						quoteRequestResponse.setId(quoteRequest.getId());
					}
					// Customer
					if (quoteRequest.getCustomer() != null) {
						quoteRequestResponse.setCustomer(quoteRequest
								.getCustomer());
					}
					// zipcode
					if (quoteRequest.getZipcode() != null) {
						quoteRequestResponse.setZipcode(quoteRequest
								.getZipcode());
					}
					// note
					if (quoteRequest.getNote() != null) {
						quoteRequestResponse.setNote(quoteRequest.getNote());
					}
					// followup date
					if (quoteRequest.getFollowupDate() != null) {
						quoteRequestResponse.setFollowupDate(quoteRequest
								.getFollowupDate());
					}
					// Reason for reject
					if (quoteRequest.getReasonForReject() != null) {
						quoteRequestResponse.setReasonForReject(quoteRequest
								.getReasonForReject());
					}
					quoteRequestResponse
							.setQuoteSellingPriceIncludeTax(quoteRequest
									.isQuoteSellingPriceIncludeTax());
					Set<String> sellerIds = new HashSet<>();
					if (quoteRequest.getProducts() != null
							&& quoteRequest.getProducts().size() != 0) {
						for (QuoteRequestProduct product : quoteRequest
								.getProducts()) {
							if (product != null) {
								if (product.getQuoteProductPricings() != null
										&& product.getQuoteProductPricings()
												.size() != 0) {
									for (SellerQuoteProductPricing quoteProductPricing : product
											.getQuoteProductPricings()) {
										if (quoteProductPricing != null) {
											sellerIds.add(quoteProductPricing
													.getSeller().getId());
										}
									}
								}
							}
						}
					}

					List<SellerQuoteProductResponse> sellerQuoteProductResponseSet = new ArrayList<>();
					List<SellerQuoteProductResponse> sellerQuoteProductResponseSet1 = new ArrayList<>();
					List<QuoteProductResponse> quoteProductsNoSeller = new ArrayList<>();

					if (sellerIds != null && sellerIds.size() != 0) {
						for (String sellerId : sellerIds) {
							// seller
							SellerQuoteProductResponse sellerQuoteProductResponse = new SellerQuoteProductResponse();
							User seller = userDAO.findById(sellerId);
							if (seller != null) {
								sellerQuoteProductResponse.setSeller(seller);
								List<QuoteProductResponse> quoteProductResponseSet = new ArrayList<>();

								if (quoteRequest.getProducts() != null
										&& quoteRequest.getProducts().size() != 0) {

									for (QuoteRequestProduct product : quoteRequest
											.getProducts()) {
										if (product != null) {
											Set<String> sellerIds1 = new HashSet<>();

											boolean flag1 = true;
											if (product
													.getQuoteProductPricings() != null
													&& product
															.getQuoteProductPricings()
															.size() != 0) {
												for (SellerQuoteProductPricing quoteProductPricing : product
														.getQuoteProductPricings()) {
													if (quoteProductPricing != null) {
														sellerIds1
																.add(quoteProductPricing
																		.getSeller()
																		.getId());
													}
												}
											} else {
												boolean flag = true;
												flag1 = false;
												if (quoteProductsNoSeller
														.size() != 0) {
													for (QuoteProductResponse prod : quoteProductsNoSeller) {
														if (prod.getProductName()
																.equalsIgnoreCase(
																		product.getProductName())) {
															flag = false;
														}
													}
												}
												if (flag) {

													QuoteProductResponse quoteProductResponse = new QuoteProductResponse();
													quoteProductResponse
															.setQuoteProductId(product
																	.getId());
													if (product.getProductId() != null) {
														quoteProductResponse
																.setProductId(product
																		.getProductId());
													}
													if (product
															.getProductName() != null) {
														quoteProductResponse
																.setProductName(product
																		.getProductName());
													}
													if (product.getBrand() != null) {
														quoteProductResponse
																.setBrand(product
																		.getBrand());
													}
													if (product
															.getQuantityType() != null) {
														quoteProductResponse
																.setQuantityType(product
																		.getQuantityType());
													}
													if (product.getQuantity() != null) {
														quoteProductResponse
																.setQuantity(product
																		.getQuantity());
													}

													quoteProductsNoSeller
															.add(quoteProductResponse);
												}
											}

											if (flag1) {
												if (sellerIds1.contains(seller
														.getId())) {

													QuoteProductResponse quoteProductResponse = new QuoteProductResponse();
													Set<QuoteProductPricingResponse> productPricingsSet = new HashSet<>();
													if (product.getId() != null) {
														quoteProductResponse
																.setQuoteProductId(product
																		.getId());
													}
													if (product
															.getProductName() != null) {
														quoteProductResponse
																.setProductName(product
																		.getProductName());
													}
													if (product.getProductId() != null) {
														quoteProductResponse
																.setProductId(product
																		.getProductId());
													}
													if (product.getBrand() != null) {
														quoteProductResponse
																.setBrand(product
																		.getBrand());
													}
													if (product
															.getQuantityType() != null) {
														quoteProductResponse
																.setQuantityType(product
																		.getQuantityType());
													}
													if (product.getQuantity() != null) {
														quoteProductResponse
																.setQuantity(product
																		.getQuantity());
													}

													for (SellerQuoteProductPricing quoteProductPricing1 : product
															.getQuoteProductPricings()) {
														if (quoteProductPricing1 != null) {
															if (seller
																	.getId()
																	.equalsIgnoreCase(
																			quoteProductPricing1
																					.getSeller()
																					.getId())) {
																QuoteProductPricingResponse quoteProductPricingResponse = new QuoteProductPricingResponse();
																if (quoteProductPricing1
																		.getId() != null) {
																	quoteProductPricingResponse
																			.setQuoteProductPricingId(quoteProductPricing1
																					.getId());
																}
																if (quoteProductPricing1
																		.getSeller() != null) {
																	quoteProductPricingResponse
																			.setSellerId(quoteProductPricing1
																					.getSeller()
																					.getId());
																}
																if (quoteProductPricing1
																		.getMrp() != null) {
																	quoteProductPricingResponse
																			.setMrp(quoteProductPricing1
																					.getMrp());
																}
																if (quoteProductPricing1
																		.getSellingPrice() != null) {
																	quoteProductPricingResponse
																			.setSellingPrice(quoteProductPricing1
																					.getSellingPrice());
																}
																if (quoteProductPricing1
																		.getBuildOnnSellingPrice() != null) {
																	quoteProductPricingResponse
																			.setBuildOnnSellingPrice(quoteProductPricing1
																					.getBuildOnnSellingPrice());
																}
																if (quoteProductPricing1
																		.getShippingCharge() != null) {
																	quoteProductPricingResponse
																			.setShippingCharge(quoteProductPricing1
																					.getShippingCharge());
																	sellerQuoteProductResponse
																			.setShippingCharge(quoteProductPricing1
																					.getShippingCharge());
																}
																// Extra
																// Benefits
																if (quoteProductPricing1
																		.getExtraBenefits() != null) {
																	quoteProductPricingResponse
																			.setExtraBenefits(quoteProductPricing1
																					.getExtraBenefits());
																	sellerQuoteProductResponse
																			.setExtraBenefits(quoteProductPricing1
																					.getExtraBenefits());
																}
																// shared
																quoteProductPricingResponse
																		.setShared(quoteProductPricing1
																				.isShared());
																if (quoteProductPricing1
																		.getDiscount() != null) {
																	quoteProductPricingResponse
																			.setDiscount(quoteProductPricing1
																					.getDiscount());
																}
																if (quoteProductPricing1
																		.getTax() != null) {
																	quoteProductPricingResponse
																			.setTax(quoteProductPricing1
																					.getTax());
																}

																// Total price
																if (product
																		.getQuantity() != null
																		&& quoteProductPricing1
																				.getBuildOnnSellingPrice() != null) {
																	double totalAmount = product
																			.getQuantity()
																			* quoteProductPricing1
																					.getBuildOnnSellingPrice();

																	double vatAmount = 0;
																	// Vat price
																	if (quoteProductPricing1
																			.getTax() != null) {
																		vatAmount = ((totalAmount * quoteProductPricing1
																				.getTax()) / 100);
																		quoteProductPricingResponse
																				.setVatAmount(vatAmount);
																	}

																	quoteProductPricingResponse
																			.setTotalPrice(totalAmount
																					+ vatAmount);
																}
																productPricingsSet
																		.add(quoteProductPricingResponse);

															}
														}
													}
													quoteProductResponse
															.setProductPricings(productPricingsSet);

													quoteProductResponseSet
															.add(quoteProductResponse);
												}
											}
										}
									}
									sellerQuoteProductResponse
											.setProducts(quoteProductResponseSet);
									sellerQuoteProductResponseSet
											.add(sellerQuoteProductResponse);
								}
							}
						}
					} else {
						List<QuoteProductResponse> quoteProductResponseSet1 = new ArrayList<>();
						if (quoteRequest.getProducts() != null
								&& quoteRequest.getProducts().size() != 0) {
							SellerQuoteProductResponse sellerQuoteProductResponse11 = new SellerQuoteProductResponse();
							for (QuoteRequestProduct product : quoteRequest
									.getProducts()) {
								QuoteProductResponse quoteProductResponse = new QuoteProductResponse();
								quoteProductResponse.setQuoteProductId(product
										.getId());
								if (product.getProductId() != null) {
									quoteProductResponse.setProductId(product
											.getProductId());
								}
								if (product.getProductName() != null) {
									quoteProductResponse.setProductName(product
											.getProductName());
								}
								if (product.getBrand() != null) {
									quoteProductResponse.setBrand(product
											.getBrand());
								}
								if (product.getQuantityType() != null) {
									quoteProductResponse
											.setQuantityType(product
													.getQuantityType());
								}
								if (product.getQuantity() != null) {
									quoteProductResponse.setQuantity(product
											.getQuantity());
								}

								quoteProductResponseSet1
										.add(quoteProductResponse);

							}
							sellerQuoteProductResponse11
									.setProducts(quoteProductResponseSet1);
							sellerQuoteProductResponseSet1
									.add(sellerQuoteProductResponse11);
						}
					}
					// Products
					if (quoteProductsNoSeller != null
							&& quoteProductsNoSeller.size() != 0) {
						SellerQuoteProductResponse sellerQuoteProductResponse = new SellerQuoteProductResponse();
						sellerQuoteProductResponse
								.setProducts(quoteProductsNoSeller);
						sellerQuoteProductResponseSet1
								.add(sellerQuoteProductResponse);
						sellerQuoteProductResponseSet
								.addAll(sellerQuoteProductResponseSet1);

					}
					sellerQuoteProductResponseSet
							.addAll(sellerQuoteProductResponseSet1);
					quoteRequestResponse
							.setProducts(sellerQuoteProductResponseSet);
					if (quoteRequest.getBuildOnnQuoteRequestId() != null) {
						quoteRequestResponse
								.setBuildOnnQuoteRequestId(quoteRequest
										.getBuildOnnQuoteRequestId());
					}
					if (quoteRequest.getStatus() != null) {
						quoteRequestResponse.setStatus(quoteRequest.getStatus()
								.toString());
					}

					if (quoteRequest.getDeliveryDate() != null) {
						quoteRequestResponse.setDeliveryDate(quoteRequest
								.getDeliveryDate());
					}

					// CreatedDate
					quoteRequestResponse.setCreatedDate(quoteRequest
							.getCreatedDate());
				}
			}

		}
		return quoteRequestResponse;
	}

	public class StoreProduct {
		private String id;
		private List<Product> products;
	}

	/**
	 * method for admin manage dealer product view details
	 * 
	 * @param pid
	 * @param dealerId
	 * @return
	 * @throws Exception
	 */
	@Override
	public ProductDetailView getAdminManageDealerProductViewDetails(String pid,
			String dealerId) throws Exception {
		ProductDetailView productDetailView = null;
		if (StringUtils.isNotBlank(dealerId) && StringUtils.isNotBlank(pid)) {
			User dealer = userDAO.findById(dealerId);
			Product product = productDAO.findById(pid);
			if (dealer != null && product != null) {
				boolean linktoMe = false;

				productDetailView = new ProductDetailView();

				productDetailView.setpId(product.getId());

				if (product.getModel() != null) {
					productDetailView.setModel(product.getModel());
				}
				productDetailView.setSkuid(product.getSkuId());
				if (product.getName() != null) {
					productDetailView.setName(product.getName());
				}
				if (product.getQuantityType() != null) {
					productDetailView
							.setQuantityType(product.getQuantityType());
				}
				if (product.getBrand() != null) {
					productDetailView.setBrand(product.getBrand().getName());
				}
				if (product.getLastModifiedDate() != null) {
					productDetailView.setOnboardedDate(product
							.getLastModifiedDate());
				}
				if (product.getStatus() != null) {
					productDetailView.setStatus(product.getStatus().name());
				}
				if (product.getCreatedBy() != null) {
					productDetailView.setOnboardedBy(product.getCreatedBy());
				}

				Set<String> values = new HashSet<>();

				if (product.getAttributes().size() != 0) {
					Set<ProductAttribute> attributes = product.getAttributes();
					for (ProductAttribute productAttribute : attributes) {

						if (productAttribute.getKey()
								.equalsIgnoreCase("Length")) {
							values = productAttribute.getValues();

							if (values.size() != 0 && values != null) {
								productDetailView.setLength(values);
							} else {
								values.add("-1");
								productDetailView.setLength(values);
							}
						} else if (productAttribute.getKey().equalsIgnoreCase(
								"Breadth")) {
							values = productAttribute.getValues();
							if (values.size() != 0 && values != null) {
								productDetailView.setBreadth(values);
							} else {
								values.add("-1");
								productDetailView.setBreadth(values);
							}
						} else if (productAttribute.getKey().equalsIgnoreCase(
								"Height")) {
							values = productAttribute.getValues();
							if (values.size() != 0 && values != null) {
								productDetailView.setHeight(values);
							} else {
								values.add("-1");
								productDetailView.setHeight(values);
							}
						} else if (productAttribute.getKey().equalsIgnoreCase(
								"Weight")) {
							values = productAttribute.getValues();
							if (values.size() != 0 && values != null) {
								productDetailView.setWeight(values);
							} else {
								values.add("-1");
								productDetailView.setWeight(values);
							}
						}
					}
				}

				if (product.getSubcategories() != null) {
					Set<SubCategory> subCategories = product.getSubcategories();
					productDetailView.setSubCategory(subCategories);
					Set<Category> categoryList = new HashSet<Category>();
					for (SubCategory subcategory : subCategories) {
						if (subcategory.getCategory() != null) {
							categoryList.addAll(categoryDAO
									.findByNameAllIgnoreCase(subcategory
											.getCategory()));
						}
					}
					productDetailView.setMainCategory(categoryList);
				}

				if (product.getPrice() != null) {
					productDetailView.setMrp(product.getPrice().getMrp());
				}

				Aggregation agg = Aggregation.newAggregation(
						Aggregation.match(Criteria.where("user").is(dealerId)
								.and("products").in(pid)),
						Aggregation.project("storename").and("id"));

				// Converting the aggregation result into a List
				AggregationResults<StoreProduct> groupResults = mongoTemplate
						.aggregate(agg, Store.class, StoreProduct.class);
				List<StoreProduct> result = groupResults.getMappedResults();

				if (result.size() != 0) {
					linktoMe = true;
				}
				productDetailView.setLinkedToMe(linktoMe);

				StoreProductPricing storeProductPricing = storeProductPricingDAO
						.findByProductIdAndStoreId(pid, result.get(0).id);

				if (storeProductPricing != null) {

					if (storeProductPricing.getStockCount() != null) {
						productDetailView.setQty(storeProductPricing
								.getStockCount());
					}
					if (storeProductPricing.getSellingPrice() != null) {
						productDetailView.setSellingPrice(storeProductPricing
								.getSellingPrice());
					}

					Shipping shipping = storeProductPricing.getShipping();
					if (shipping != null) {
						if (shipping.getMinLocalProcurementSLA() != null) {
							productDetailView
									.setMinLocalEstimateDeliveryTime(shipping
											.getMinLocalProcurementSLA());
						}
						if (shipping.getMinZonalProcurementSLA() != null) {
							productDetailView
									.setMinZonalEstimateDeliveryTime(shipping
											.getMinZonalProcurementSLA());
						}

						if (shipping.getMinNationalProcurementSLA() != null) {
							productDetailView
									.setMinNationalEstimateDeliveryTime(shipping
											.getMinNationalProcurementSLA());
						}
						if (shipping.getMaxLocalProcurementSLA() != null) {
							productDetailView
									.setMaxLocalEstimateDeliveryTime(shipping
											.getMaxLocalProcurementSLA());
						}
						if (shipping.getMaxZonalProcurementSLA() != null) {
							productDetailView
									.setMaxZonalEstimateDeliveryTime(shipping
											.getMaxZonalProcurementSLA());
						}
						if (shipping.getMaxNationalProcurementSLA() != null) {
							productDetailView
									.setMaxNationalEstimateDeliveryTime(shipping
											.getMaxNationalProcurementSLA());
						}
						if (shipping.getLocalShippingChrg() != null) {
							productDetailView
									.setLocalEstimateDeliveryChrg(shipping
											.getLocalShippingChrg());
						}
						if (shipping.getZonalShippingChrg() != null) {
							productDetailView
									.setZonalEstimateDeliveryChrg(shipping
											.getZonalShippingChrg());
						}
						if (shipping.getNationalShippingChrg() != null) {
							productDetailView
									.setNationalEstimateDeliveryChrg(shipping
											.getNationalShippingChrg());
						}
						if (shipping.getLocalDelivery() != null) {
							productDetailView.setLocalRegion(shipping
									.getLocalDelivery());
						}
						if (shipping.getZonalDelivery() != null) {
							productDetailView.setZonalRegion(shipping
									.getZonalDelivery());
						}
						if (shipping.getNationalDelivery() != null) {
							productDetailView.setNationalRegion(shipping
									.getNationalDelivery());
						}

					}
					if (storeProductPricing.getMinQuantityBuy() != null) {
						productDetailView.setMinQuantityBuy(storeProductPricing
								.getMinQuantityBuy());
					}
					if (storeProductPricing.getMbgShare() != null) {
						productDetailView.setMbgShare(storeProductPricing
								.getMbgShare());
					}

					if (storeProductPricing.getDiscount() != null) {
						productDetailView.setDiscount(storeProductPricing
								.getDiscount());
					}
					if (storeProductPricing.isStoreDelivery()) {
						productDetailView.setWillYouDeliver(true);
					} else {
						productDetailView.setWillYouDeliver(false);
					}
				}
			}
		}
		Set<String> val = new HashSet<String>();
		val.add("-1");

		if (productDetailView.getLength() == null) {
			productDetailView.setLength(val);
		}
		if (productDetailView.getBreadth() == null) {
			productDetailView.setBreadth(val);
		}
		if (productDetailView.getHeight() == null) {
			productDetailView.setHeight(val);
		}
		if (productDetailView.getWeight() == null) {
			productDetailView.setWeight(val);
		}

		return productDetailView;
	}

	/**
	 * Method for delete a product based on productId.
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@Override
	public Product deleteProduct(String pid) throws Exception {
		Product product = null;
		if (StringUtils.isNotBlank(pid)) {
			product = productDAO.findById(pid);
			if (product != null) {

				product.setStatus(ProductStatus.DEACTIVATED);
				productDAO.save(product);
				logger.info("Product status has changed to DEACTIVATED");
			} else {
				logger.error("Product already in DEACTIVATED status");
				throw new Exception("Product already in DEACTIVATED status");
			}
		}
		return product;
	}

	/**
	 * Method to update product
	 * 
	 * @param pid
	 * @param productUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public Product updateProduct(/* String userId, */String pid, String name,
			String longName, String desc, Set<String> subCategoryIds,
			Set<String> productTypeIds, String productAssets,
			Set<ProductAttribute> productAttributes, String price,
			String accessory, List<String> relatedProducts,
			List<String> accessories, String status, String key,
			String isMultivaled, Set<String> values, String model,
			Set<ProductFaq> faqs, String brandName, String quantityType)
			throws Exception {
		Product product = productDAO.findOne(pid);
		logger.debug("get product object from Database : " + product);
		if (product != null) {
			if (StringUtils.isNotBlank(name)) {
				product.setName(name);
				longName = name + "-" + product.getSkuId();
				product.setLongName(longName);
			}
			if (StringUtils.isNotBlank(longName)) {
				product.setLongName(longName);
			}
			if (StringUtils.isNotBlank(model)) {
				product.setModel(model);
			}
			if (StringUtils.isNotBlank(desc)) {
				String descStr[] = desc.split(",");
				if (descStr.length == 2) {
					logger.debug("Description have to lang and val separated by comma "
							+ descStr[0] + "," + descStr[1]);
					Description descri = product.getDesc();
					descri.setLang(descStr[0]);
					descri.setVal(descStr[1]);
					product.setDesc(descri);
				} else {
					Description descri = product.getDesc();
					logger.debug("Description have only val " + descri);
					descri.setLang("en");
					descri.setVal(descStr[0]);
					product.setDesc(descri);
				}
			}

			if (subCategoryIds != null) {
				Set<SubCategory> subCategorySet = new HashSet<SubCategory>();
				for (String subcatId : subCategoryIds) {
					SubCategory subCategory = subCategoryDAO.findOne(subcatId);
					if (subCategory != null) {
						subCategorySet.add(subCategory);
					} else {
						throw new Exception("Invalid SubCategoryID");
					}
				}
				product.setSubcategories(subCategorySet);
			}

			if (productTypeIds != null) {
				Set<ProductType> productTypeSet = new HashSet<ProductType>();
				for (String productTypeId : productTypeIds) {
					ProductType productType = productTypeDAO
							.findOne(productTypeId);
					if (productType != null) {
						productTypeSet.add(productType);
					}
				}
				product.setProductTypes(productTypeSet);
			}
			if (productAttributes != null) {
				product.setAttributes(productAttributes);
			}
			if (StringUtils.isNotBlank(brandName)) {
				ProductBrand productBrand = brandDAO.findByName(brandName);
				if (productBrand != null) {
					product.setBrand(productBrand);
				} else {
					logger.info("Invalid BrandName");
					throw new Exception("Invalid BrandName");
				}
			}

			if (StringUtils.isNotBlank(quantityType)) {
				ProductQuantityType productQuantityType = productQuantityTypeDAO
						.findOne(quantityType);
				if (productQuantityType != null) {
					product.setQuantityType(productQuantityType);
				} else {
					logger.info("Product Quantity type doesn't exists");
				}
			}

			if (StringUtils.isNotBlank(accessory)) {
				boolean acce = Boolean.parseBoolean(accessory);
				product.setAccessory(acce);
			}
			if (StringUtils.isNotBlank(key)
					|| StringUtils.isNotBlank(isMultivaled) || values != null) {
				ProductAttribute productAttribute = new ProductAttribute();
				if (StringUtils.isNotBlank(key)) {
					productAttribute.setKey(key);
				}
				if (StringUtils.isNotBlank(isMultivaled)) {
					if (isMultivaled.equalsIgnoreCase("true")) {
						productAttribute.setMultiValued(true);
					} else {
						productAttribute.setMultiValued(false);
					}
					product.setFeatures(productAttribute);
				}
				if (values != null) {
					productAttribute.setValues(values);
					product.setFeatures(productAttribute);
				}
				product.setFeatures(productAttribute);
			}

			if (relatedProducts != null) {
				Set<String> relatedProductsSet = new HashSet<>();
				for (int i = 0; i < relatedProducts.size(); i++) {
					Product l_product = productDAO.findOne(relatedProducts
							.get(i));
					if (l_product != null) {
						relatedProductsSet.add(relatedProducts.get(i));
					} else {
						logger.error("Invalid relatedProductId : {}",
								relatedProducts.get(i));
					}
				}
				product.setRelatedProducts(relatedProductsSet);
			}
			if (accessories != null) {
				Set<String> accessoriesSet = new HashSet<>();
				for (int i = 0; i < accessories.size(); i++) {
					Product l_product = productDAO.findOne(accessories.get(i));
					if (l_product != null) {
						accessoriesSet.add(accessories.get(i));
					} else {
						logger.error("Invalid accessoriesId : {}",
								accessories.get(i));
					}
				}
				product.setAccessories(accessoriesSet);
			}
			if (StringUtils.isNotBlank(status)) {
				product.setStatus(ProductStatus.valueOf(status));
				if (status.equalsIgnoreCase("IN_BUILDONN")) {
					String onBoardedUserId = product.getCreatedBy();
					User user = userDAO.findById(onBoardedUserId);

					// later have to umcomment below line
					// Email and SMS
					// userCommunicationService.sendProductApprovedInfo(user,
					// product.getName());
				}
			}
			if (faqs != null) {
				product.setFaqs(faqs);
			}
			if (StringUtils.isNotBlank(price)) {
				String priceStr[] = price.split(",");
				if (priceStr.length == 2) {
					logger.debug("Price have symbol and value separated by comma "
							+ priceStr[0] + "," + priceStr[1]);
					Price p = product.getPrice();
					p.setCurrency(priceStr[0]);
					p.setMrp(Double.parseDouble(priceStr[1]));
					product.setPrice(p);
				} else {
					logger.debug("price have only val " + priceStr[0]);
					Price p = product.getPrice();
					p.setCurrency("₹");
					p.setMrp(Double.parseDouble(priceStr[0]));
					product.setPrice(p);
				}
			}
			product.setLastModifiedDate(new Date());

			// later have to umcomment below line
			// product.setLastModifiedBy(userId);

			productDAO.save(product);
			logger.debug("Product updated");

			// Adding subcategoryIds to brand
			if (StringUtils.isNotBlank(brandName)) {
				ProductBrand productBrand = brandDAO.findByName(brandName);
				if (productBrand != null) {
					if (productBrand.getSubCategoryIds() != null
							&& productBrand.getSubCategoryIds().size() != 0) {
						Set<String> subCatIds = productBrand
								.getSubCategoryIds();
						subCatIds.addAll(subCategoryIds);
						productBrand.setSubCategoryIds(subCatIds);
					} else {
						productBrand.setSubCategoryIds(subCategoryIds);
					}
					brandDAO.save(productBrand);
				} else {
					logger.info("Brand is not exists for BrandName : {}",
							brandName);
				}
			}

		} else {
			logger.error("Product not found ");
			throw new Exception("Sorry Product id does not match in database");
		}
		return product;
	}

	@Override
	public QuoteRequestProduct updateSellerQuoteProduct(
			String quoteRequestProductId, QuoteProductRequestParam product)
			throws UserCommException {
		QuoteRequestProduct quoteRequestProduct = null;
		String dealerId = null;
		if (StringUtils.isNotBlank(quoteRequestProductId)) {
			quoteRequestProduct = quoteRequestProductDAO
					.findOne(quoteRequestProductId);
			if (quoteRequestProduct != null && product != null) {
				if (product.getProductName() != null) {
					quoteRequestProduct
							.setProductName(product.getProductName());
				}
				if (product.getBrand() != null) {
					quoteRequestProduct.setBrand(product.getBrand());
				}
				if (product.getQuantityType() != null) {
					quoteRequestProduct.setQuantityType(product
							.getQuantityType());
				}
				if (product.getQuantity() != null) {
					quoteRequestProduct.setQuantity(product.getQuantity());
				}
				Set<SellerQuoteProductPricing> quoteProductPricings = quoteRequestProduct
						.getQuoteProductPricings();
				Set<SellerQuoteProductPricingRequestParam> pricings = product
						.getProductPricings();
				for (SellerQuoteProductPricing quoteProductPricing : quoteProductPricings) {
					for (SellerQuoteProductPricingRequestParam pricing : pricings) {
						if (quoteProductPricing.getSeller().getId()
								.equalsIgnoreCase(pricing.getSellerId())) {
							dealerId = pricing.getSellerId();
							if (pricing.getMrp() != null) {
								quoteProductPricing.setMrp(pricing.getMrp());
							}
							if (pricing.getSellingPrice() != null) {
								quoteProductPricing.setSellingPrice(pricing
										.getSellingPrice());
							}
							if (pricing.getShippingCharge() != null) {
								quoteProductPricing.setShippingCharge(pricing
										.getShippingCharge());
							}
							// Extra Benefits
							if (pricing.getExtraBenefits() != null) {
								quoteProductPricing.setExtraBenefits(pricing
										.getExtraBenefits());
							}
							if (pricing.getBuildOnnSellingPrice() != null) {
								quoteProductPricing
										.setBuildOnnSellingPrice(pricing
												.getBuildOnnSellingPrice());
							}
							if (pricing.getDiscount() != null) {
								quoteProductPricing.setDiscount(pricing
										.getDiscount());
							}
							if (pricing.getTax() != null) {
								quoteProductPricing.setTax(pricing.getTax());
							}

							sellerQuoteProductPricingDAO
									.save(quoteProductPricing);
						}
					}

				}
				quoteRequestProductDAO.save(quoteRequestProduct);
			}
		}
		if (product.getAlertType() == null) {
			product.setAlertType("NO");
		}
		if (product.getAlertType().equalsIgnoreCase("YES")
				&& StringUtils.isNotBlank(dealerId)) {
			User dealer = userDAO.findById(dealerId);
			if (dealer != null) {
				String sellerName = null;
				if (dealer.getFirstName() != null) {
					sellerName = dealer.getFirstName();
				}

				userCommunicationService.sendQuoteUpdateSellerToAdmin(dealer,
						sellerName, product.getItemCount(),
						product.getTotalQuoteAmount());
			}
		}
		return quoteRequestProduct;
	}

	/**
	 * File and Image Upload
	 * 
	 * @param form
	 * @return
	 * @throws IOException
	 * @throws MBGAppException
	 */
	@Override
	public synchronized Product uploadFile(String pid, String fileName,
			String fileTypeValue, InputStream fileInputStream,
			long contentLength) throws Exception {
		logger.info("Invoking uplaod API with Image Name " + fileName);
		System.out.println("Invoking uplaod API with Image Name " + fileName);
		String locationName = null;
		String url = null;
		Product product = null;
		String productName = null;
		if (StringUtils.isNotBlank(pid)) {
			logger.info("ProductResource : uploadFile : " + pid);
			product = productDAO.findOne(pid);
			if (product != null) {
				productName = product.getName().toUpperCase();
				logger.info(productName);

				if (fileTypeValue.equalsIgnoreCase("VIDEO")) {
					locationName = fileName;
				} else {
					locationName = "catalog/product/" + productName.charAt(0)
							+ "/" + productName.substring(0, 2) + "/"
							+ fileName;
				}
				logger.info(locationName);
				if (fileTypeValue.equalsIgnoreCase("FILE")) {
					url = filestorage.uploadFile(
							AzureFileStorage.FILE_CONTAINER, locationName,
							fileInputStream, contentLength);
				} else if (fileTypeValue.equalsIgnoreCase("IMAGE")) {
					url = filestorage.uploadFile(
							AzureFileStorage.IMG_CONTAINER, locationName,
							fileInputStream, contentLength);
				}
				product = productDAO.findOne(pid);
				ProductAsset productAsset = product.getAssets();
				if (productAsset != null) {
					if (fileTypeValue.equalsIgnoreCase("IMAGE")) {
						Image image = new Image(fileName, url);
						URL uri = new URL(url);
						logger.debug(url);
						java.awt.Image imageAwt = new ImageIcon(uri).getImage();
						int imgWidth = imageAwt.getWidth(null);
						int imgHeight = imageAwt.getHeight(null);
						logger.debug("Image Height " + imgHeight
								+ " And width " + imgWidth);
						image.setHeight(imgHeight);
						image.setWidth(imgWidth);
						image.setSize(uri.openConnection()
								.getContentLengthLong());
						logger.debug("Image Size set in image");
						image.setFileType(FileType.IMAGE);
						List<Image> imageList = productAsset.getImages();
						if (imageList != null) {
							logger.debug("ProductResource : uploadFile : "
									+ imageList);
							imageList.add(image);
						} else {
							imageList = new ArrayList<>();
							imageList.add(image);
						}
						productAsset.setImages(imageList);
						product.setAssets(productAsset);
						productDAO.save(product);
						logger.debug("ProductResource : uploadFile : "
								+ productAsset);
						System.out.println("ProductResource : uploadFile : "
								+ productAsset);
						logger.debug("Image uploaded " + fileName);
						System.out.println("Image uploaded " + fileName);
					}
					if (fileTypeValue.equalsIgnoreCase("IMAGE3D")) {
						Image image3D = new Image(fileName, url);
						URL uri = new URL(url);
						java.awt.Image imageAwt = new ImageIcon(uri).getImage();
						int imgWidth = imageAwt.getWidth(null);
						int imgHeight = imageAwt.getHeight(null);
						logger.debug("Image Height " + imgHeight
								+ " And width " + imgWidth);
						image3D.setHeight(imgHeight);
						image3D.setWidth(imgWidth);
						image3D.setSize(uri.openConnection()
								.getContentLengthLong());
						logger.debug("Image Size set in image3D");
						image3D.setFileType(FileType.IMAGE);
						List<Image> image3DList = productAsset.getImages3D();
						if (image3DList != null) {
							logger.debug("ProductResource : uploadFile : "
									+ image3DList);
							image3DList.add(image3D);
						} else {
							image3DList = new ArrayList<>();
							image3DList.add(image3D);
						}
						productAsset.setImages3D(image3DList);
						product.setAssets(productAsset);
						productDAO.save(product);
						logger.debug("ProductResource : uploadFile : image3D uploaded");
					}

					if (fileTypeValue.equalsIgnoreCase("FILE")) {
						File relatedFile = new File(fileName, url);
						URL uri = new URL(url);
						relatedFile.setSize(uri.openConnection()
								.getContentLengthLong());
						String[] filetype = fileName.split("\\.");
						relatedFile.setFileType(FileType.valueOf(filetype[1]
								.toUpperCase()));
						List<File> relatedFileList = productAsset
								.getRelatedFiles();
						if (relatedFileList != null) {
							logger.debug("ProductResource : uploadFile : "
									+ relatedFileList);
							relatedFileList.add(relatedFile);
						} else {
							relatedFileList = new ArrayList<>();
							relatedFileList.add(relatedFile);
						}
						productAsset.setRelatedFiles(relatedFileList);
						product.setAssets(productAsset);
						productDAO.save(product);
						logger.debug("ProductResource : uploadFile : RelatedFile uploaded");
					}

					if (fileTypeValue.equalsIgnoreCase("VIDEO")) {
						Video video = new Video(fileName, locationName);
						URL uri = new URL(locationName);
						video.setSize(uri.openConnection()
								.getContentLengthLong());
						video.setFileType(FileType.VIDEO);
						productAsset.setVideo(video);
						product.setAssets(productAsset);
						productDAO.save(product);
						logger.debug("ProductResource : uploadVideo : video uploaded");
					}
				} else {
					productAsset = new ProductAsset();
					if (fileTypeValue.equalsIgnoreCase("IMAGE")) {
						Image image = new Image(fileName, url);
						URL uri = new URL(url);
						java.awt.Image imageAwt = new ImageIcon(uri).getImage();
						int imgWidth = imageAwt.getWidth(null);
						int imgHeight = imageAwt.getHeight(null);
						logger.debug("Image Height " + imgHeight
								+ " And width " + imgWidth);
						image.setHeight(imgHeight);
						image.setWidth(imgWidth);
						image.setSize(uri.openConnection().getContentLength());
						logger.debug("image size set in image");
						image.setFileType(FileType.IMAGE);
						List<Image> imageList = new ArrayList<>();
						imageList.add(image);
						productAsset.setImages(imageList);
						product.setAssets(productAsset);
						productDAO.save(product);
						logger.debug("ProductResource : uploadFile : "
								+ productAsset);
						System.out.println("ProductResource : uploadFile : "
								+ productAsset);
						logger.debug("Image uploaded " + fileName);
						System.out.println("Image uploaded " + fileName);
					}
					if (fileTypeValue.equalsIgnoreCase("IMAGE3D")) {
						Image image3D = new Image(fileName, url);
						URL uri = new URL(url);
						java.awt.Image imageAwt = new ImageIcon(uri).getImage();
						int imgWidth = imageAwt.getWidth(null);
						int imgHeight = imageAwt.getHeight(null);
						logger.debug("Image Height " + imgHeight
								+ " And width " + imgWidth);
						image3D.setHeight(imgHeight);
						image3D.setWidth(imgWidth);
						image3D.setSize(uri.openConnection()
								.getContentLengthLong());
						logger.debug("Image size set in image3D");
						image3D.setFileType(FileType.IMAGE);
						List<Image> image3DList = new ArrayList<>();
						image3DList.add(image3D);
						productAsset.setImages3D(image3DList);
						product.setAssets(productAsset);
						productDAO.save(product);
						logger.debug("ProductResource : uploadFile : Image3D uploaded");
					}

					if (fileTypeValue.equalsIgnoreCase("FILE")) {
						File relatedFile = new File(fileName, url);
						URL uri = new URL(url);
						relatedFile.setSize(uri.openConnection()
								.getContentLengthLong());
						String[] filetype = fileName.split("\\.");
						relatedFile.setFileType(FileType.valueOf(filetype[1]
								.toUpperCase()));
						logger.debug("File size set ");
						List<File> fileList = new ArrayList<>();
						fileList.add(relatedFile);
						productAsset.setRelatedFiles(fileList);
						product.setAssets(productAsset);
						productDAO.save(product);
						logger.debug("ProductResource : uploadFile : RelatedFile uploaded");
					}
					if (fileTypeValue.equalsIgnoreCase("VIDEO")) {
						Video video = new Video(fileName, locationName);
						URL uri = new URL(locationName);
						video.setSize(uri.openConnection()
								.getContentLengthLong());
						video.setFileType(FileType.VIDEO);
						productAsset.setVideo(video);
						product.setAssets(productAsset);
						productDAO.save(product);
						logger.debug("ProductResource : uploadVideo : video uploaded");
					}
				}
			}
		} else {
			logger.error("Sorry Product id not match in database");
			throw new Exception("Sorry Product id not match in database");
		}
		return product;
	}

	@Override
	public String uploadInstantOrderImage(String mobile, String email,
			String customerName, String textInfo, String deliveryTime,
			String fileName, String fileTypeValue, InputStream fileInputStream,
			long contentLength) throws Exception {
		String locationName = null;
		String url = null;
		locationName = fileName;

		if (StringUtils.isNotBlank(fileName)
				&& StringUtils.isNotBlank(fileTypeValue)
				&& fileInputStream != null) {

			if (fileTypeValue.equalsIgnoreCase("IMAGE")) {
				url = filestorage.uploadFile(AzureFileStorage.IMG_CONTAINER,
						locationName, fileInputStream, contentLength);
			}

		}

		User user = userDAO.findByRoles_NameIsIgnoreCase("SUPERADMIN");

		// Sending snapshot order confirmation to customer
		if (StringUtils.isNotBlank(email)) {
			userCommunicationService.sendInstantOrderConfirmtion(customerName,
					email, mobile);
		}

		// Sending snapshot order to buildonn
		userCommunicationService.sendInstantOrderInfo(user, mobile, email,
				customerName, textInfo, deliveryTime, url);
		return url;
	}

	@Override
	public String sendProductEnquiryForm(String customerName, String mobile,
			String email, String textInfo, String deliveryTime)
			throws UserCommException {
		User user = userDAO.findByRoles_NameIsIgnoreCase("SUPERADMIN");
		// sending confirmation to customer
		userCommunicationService.sendProductEnquiryFormConfirmation(
				customerName, email, mobile);

		// sending enquiry form to admin
		userCommunicationService.sendProductEnquiryInfo(user, customerName,
				mobile, email, textInfo, deliveryTime);
		String url = "success";
		return url;
	}

	@Override
	public List<Product> searchProductByProvider(String pid, String userName,
			String fName, String lName, String email, String pnum,
			String roleName, String fullName) {
		List<User> userList = new ArrayList<User>();
		List<Product> productList = new ArrayList<Product>();
		if (StringUtils.isNotBlank(pid)) {
			if (StringUtils.isNotBlank(userName)) {
				userList = userDAO.findByUsername(userName, new PageRequest(0,
						10));
			} else if (StringUtils.isNotBlank(fName)) {
				Page<User> pageable = userDAO.findByFirstNameLike(fName,
						new PageRequest(0, 10));
				userList.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(lName)) {
				Page<User> pageable = userDAO.findByLastNameLike(lName,
						new PageRequest(0, 10));
				userList.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(email)) {
				userList = userDAO.findByAddresses_Email(email,
						new PageRequest(0, 10));
			} else if (StringUtils.isNotBlank(pnum)) {
				userList = userDAO.findByAddresses_PhoneNumbers_Number("+91-"
						+ pnum, new PageRequest(0, 10));
			} else if (StringUtils.isNotBlank(roleName)) {
				Page<User> pageable = userDAO.findByRoles_NameAllIgnoreCase(
						roleName, new PageRequest(0, 10));
				userList.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(fullName)) {
				userList = userDAO.findByFullName(fullName, new PageRequest(0,
						10));
			} else {
				Product prod = productDAO.findBySkuId(pid);
				productList.add(prod);
			}
		}
		Iterator<User> userIterator = userList.iterator();
		while (userIterator.hasNext()) {
			User u = userIterator.next();
			Page<Product> pageable = productDAO.findByBrand_Provider_Id(
					u.getId(), new PageRequest(0, 10));
			productList.addAll(pageable.getContent());
		}
		return productList;
	}

	/**
	 * Method for search dealers for particular product
	 * 
	 * @param pid
	 * @param zipcode
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public List<SellerInformation> searchDealers(String pid, int zipcode)
			throws Exception {

		boolean seller = false;
		Product product = null;

		Address address = null;
		Set<String> localStoreIds = new HashSet<String>();
		Set<Store> zonalStores = new HashSet<>();

		Set<String> zonalStoreIds = new HashSet<String>();
		Set<String> nationalStoreIds = new HashSet<String>();
		Set<String> allStoreIds = new HashSet<String>();

		Set<Store> allStoresList = new HashSet<Store>();

		List<PriceInfo> priceInfoList = new ArrayList<>();

		Set<Store> storeList3 = new HashSet<Store>();

		List<SellerInformation> sellers = new ArrayList<SellerInformation>();

		Double cost = null;

		if (StringUtils.isNotBlank(pid)) {
			product = productDAO.findOne(pid);
			if (product != null) {

				Set<Store> storeSet = storeDAO.findByProductsIn(product);
				for (Store store : storeSet) {
					StoreProductPricing storeProductPricing = storeProductPricingDAO
							.findByProductIdAndStoreId(pid, store.getId());
					if (storeProductPricing.getStockCount() > 0) {
						seller = true;
						break;
					}
				}
				if (seller) {
					// If the user enters the zipcode
					// for Product details page(get dealers for product)
					if (zipcode != 0) {
						// Get the Point(lat/long)

						Point point = addressLookupService
								.getGeoCodedAddress(zipcode);

						// Point point = new Point(12.930276, 77.589226);

						if (point != null) {
							// Get Address from the Point(Google
							// getReverserGeoCodeAddress)

							address = addressLookupService
									.getReverseGeoCodedAddress(point.getX(),
											point.getY());
						} else {
							logger.info("Point not found");
						}

						// Get local dealers(Compare with the proper zipcode of
						// the
						// dealer to decide as local dealer)
						List<User> localDealers = userDAO
								.findByAddresses_ZipcodeAndRoles_Name(zipcode,
										"DEALER");
						if (localDealers != null && localDealers.size() != 0) {
							for (User localDealer : localDealers) {
								Store store = storeDAO.findByUser(localDealer);
								if (store != null) {
									StoreProductPricing storeProductPricing = storeProductPricingDAO
											.findByProductIdAndStoreId(pid,
													store.getId());
									if (storeProductPricing != null
											&& storeProductPricing
													.getStockCount() != 0) {
										localStoreIds.add(storeDAO.findByUser(
												localDealer).getId());
									}
								}
							}

						} else {
							logger.info("No local dealers available");
						}

						// Zonal Dealers(Check if the dealer ready to deliver
						// within
						// state
						List<StoreProductPricing> zonalStoreProductPricings = storeProductPricingDAO
								.findByProductIdAndShipping_ZonalDeliveryIsTrue(pid);

						if (zonalStoreProductPricings != null
								&& zonalStoreProductPricings.size() != 0) {
							for (StoreProductPricing storeProductPricing : zonalStoreProductPricings) {
								if (storeProductPricing.getStockCount() != 0) {

									zonalStores.add(storeDAO
											.findOne(storeProductPricing
													.getStoreId()));
								}
							}
						}

						String city = address.getCity();
						String state = address.getState();

						for (Store store : zonalStores) {
							if (store != null) {
								if (store.getUser().getAddresses() != null) {
									for (Address storeAddress : store.getUser()
											.getAddresses()) {
										// Check for local dealers
										if (storeAddress.getCity() != null
												&& storeAddress.getCity()
														.equalsIgnoreCase(city)) {
											localStoreIds.add(store.getId());
											zonalStoreIds.remove(store.getId());
											// Check and zonal dealer who are
											// ready
											// to deliver within the state
											// obtained
											// from the pincode
										} else if (storeAddress.getState() != null) {
											if (storeAddress.getState()
													.equalsIgnoreCase(state)) {

												zonalStoreIds
														.add(store.getId());
											} else {
												logger.info("No zonal dealer available for the product");
											}
										}
									}
								}
							}
						}

						// National dealers(Dealers who are ready to deliver
						// across
						// nation
						List<StoreProductPricing> nationalStoreProductPricing = storeProductPricingDAO
								.findByProductIdAndShipping_NationalDeliveryIsTrue(pid);

						if (nationalStoreProductPricing != null
								&& nationalStoreProductPricing.size() != 0) {
							for (StoreProductPricing storeProductPricing : nationalStoreProductPricing) {
								if (storeProductPricing.getStockCount() != 0) {

									nationalStoreIds.add(storeDAO.findOne(
											storeProductPricing.getStoreId())
											.getId());
								}
							}
						}

						// Adding storeIds into collection
						allStoreIds.addAll(localStoreIds);
						allStoreIds.addAll(zonalStoreIds);
						allStoreIds.addAll(nationalStoreIds);

						// Adding all stores into collection
						for (String storeId : allStoreIds) {
							Store store = storeDAO.findOne(storeId);
							if (store != null) {
								allStoresList.add(store);
							}
						}

						// Iterating all the stores
						for (Store store : allStoresList) {
							for (String localStoreId : localStoreIds) {
								PriceInfo priceInfo = new PriceInfo();
								if (store.getId().equals(localStoreId)) {
									zonalStoreIds.remove(store.getId());
									nationalStoreIds.remove(store.getId());
									if (store.getUser() != null) {
										priceInfo.setDealerId(store.getUser()
												.getId());
									}
									priceInfo.setShippingType("Local Shipping");

									StoreProductPricing storeProductPricing = storeProductPricingDAO
											.findByProductIdAndStoreId(pid,
													store.getId());

									if (storeProductPricing.getMbgShare() != null
											&& storeProductPricing
													.getMbgShare() > 0) {

										double mbgShare = ((storeProductPricing
												.getMbgShare() / 100) * storeProductPricing
												.getSellingPrice());

										cost = storeProductPricing
												.getSellingPrice() + mbgShare;

										double finalDiscount = storeProductPricing
												.getMaxRetailPrice() - cost;

										priceInfo
												.setDiscountPercentage(((100 * finalDiscount) / storeProductPricing
														.getMaxRetailPrice()));

									} else {
										cost = storeProductPricing
												.getSellingPrice();

										if (storeProductPricing.getDiscount() != null) {
											priceInfo
													.setDiscountPercentage(storeProductPricing
															.getDiscount());
										}
									}

									priceInfo.setCost(cost);
									priceInfo.setMrp(storeProductPricing
											.getMaxRetailPrice());

									if (storeProductPricing.getTax() != null) {
										priceInfo.setTax(storeProductPricing
												.getTax());
									}
									if (storeProductPricing.getShipping()
											.getLocalShippingChrg() != null) {
										priceInfo
												.setShippingCharge(storeProductPricing
														.getShipping()
														.getLocalShippingChrg());
									} else {
										priceInfo.setShippingCharge(0.0);
									}

									priceInfo.setMinSla(storeProductPricing
											.getShipping()
											.getMinLocalProcurementSLA());
									priceInfo.setMaxSla(storeProductPricing
											.getShipping()
											.getMaxLocalProcurementSLA());
									priceInfo
											.setMinQuantityBuy(storeProductPricing
													.getMinQuantityBuy());

									priceInfoList.add(priceInfo);
								}
							}
							for (String zonalStoreId : zonalStoreIds) {
								PriceInfo priceInfo = new PriceInfo();
								if (store.getId().equals(zonalStoreId)) {
									nationalStoreIds.remove(store.getId());

									priceInfo.setDealerId(store.getUser()
											.getId());
									priceInfo.setShippingType("Zonal Shipping");

									StoreProductPricing storeProductPricing = storeProductPricingDAO
											.findByProductIdAndStoreId(pid,
													store.getId());

									if (storeProductPricing.getMbgShare() != null
											&& storeProductPricing
													.getMbgShare() > 0) {

										double mbgShare = (storeProductPricing
												.getSellingPrice() * storeProductPricing
												.getMbgShare()) / 100;

										cost = storeProductPricing
												.getSellingPrice() + mbgShare;

										double finalDiscount = storeProductPricing
												.getMaxRetailPrice() - cost;

										priceInfo
												.setDiscountPercentage(((100 * finalDiscount) / storeProductPricing
														.getMaxRetailPrice()));

									} else {
										cost = storeProductPricing
												.getSellingPrice();

										if (storeProductPricing.getDiscount() != null) {
											priceInfo
													.setDiscountPercentage(storeProductPricing
															.getDiscount());
										}
									}

									priceInfo.setCost(cost);
									priceInfo.setMrp(storeProductPricing
											.getMaxRetailPrice());

									if (storeProductPricing.getTax() != null) {
										priceInfo.setTax(storeProductPricing
												.getTax());
									}
									if (storeProductPricing.getShipping()
											.getZonalShippingChrg() != null) {
										priceInfo
												.setShippingCharge(storeProductPricing
														.getShipping()
														.getZonalShippingChrg());
									} else {
										priceInfo.setShippingCharge(0.0);
									}

									priceInfo.setMinSla(storeProductPricing
											.getShipping()
											.getMinZonalProcurementSLA());
									priceInfo.setMaxSla(storeProductPricing
											.getShipping()
											.getMaxZonalProcurementSLA());
									priceInfo
											.setMinQuantityBuy(storeProductPricing
													.getMinQuantityBuy());

									priceInfoList.add(priceInfo);

								}

							}
							for (String nationalStoreId : nationalStoreIds) {
								PriceInfo priceInfo = new PriceInfo();
								if (store.getId().equals(nationalStoreId)) {
									if (store.getUser() != null) {
										priceInfo.setDealerId(store.getUser()
												.getId());
									}
									priceInfo
											.setShippingType("National Shipping");

									StoreProductPricing storeProductPricing = storeProductPricingDAO
											.findByProductIdAndStoreId(pid,
													store.getId());

									if (storeProductPricing.getMbgShare() != null
											&& storeProductPricing
													.getMbgShare() > 0) {

										double mbgShare = (storeProductPricing
												.getSellingPrice() * storeProductPricing
												.getMbgShare()) / 100;

										cost = storeProductPricing
												.getSellingPrice() + mbgShare;

										double finalDiscount = storeProductPricing
												.getMaxRetailPrice() - cost;

										priceInfo
												.setDiscountPercentage(((100 * finalDiscount) / storeProductPricing
														.getMaxRetailPrice()));

									} else {
										cost = storeProductPricing
												.getSellingPrice();

										if (storeProductPricing.getDiscount() != null) {
											priceInfo
													.setDiscountPercentage(storeProductPricing
															.getDiscount());
										}
									}

									priceInfo.setCost(cost);
									priceInfo.setMrp(storeProductPricing
											.getMaxRetailPrice());

									if (storeProductPricing.getTax() != null) {
										priceInfo.setTax(storeProductPricing
												.getTax());
									}
									if (storeProductPricing.getShipping()
											.getNationalShippingChrg() != null) {
										priceInfo
												.setShippingCharge(storeProductPricing
														.getShipping()
														.getNationalShippingChrg());
									} else {
										priceInfo.setShippingCharge(0.0);
									}

									priceInfo.setMinSla(storeProductPricing
											.getShipping()
											.getMinNationalProcurementSLA());
									priceInfo.setMaxSla(storeProductPricing
											.getShipping()
											.getMaxNationalProcurementSLA());
									priceInfo
											.setMinQuantityBuy(storeProductPricing
													.getMinQuantityBuy());
									priceInfoList.add(priceInfo);
								}
							}
						}

						// Sort the list of dealers based on the selling
						// price(i.e.,
						// sellingPrice + shippingCharge)
						Collections.sort(priceInfoList, new PriceComparator());

						for (PriceInfo priceInfo : priceInfoList) {
							// Set the response object based on shipping type
							SellerInformation sellerInfo = new SellerInformation();
							System.out.println(priceInfo.getDealerId());
							if (priceInfo.getShippingType().equalsIgnoreCase(
									"Local Shipping")) {
								System.out.println("Local Shipping"
										+ priceInfo.getDealerId());

								Store store = storeDAO.findByUserId(priceInfo
										.getDealerId());
								if (store != null) {
									sellerInfo.setDealerId(priceInfo
											.getDealerId());

									String[] storeNameArray = store
											.getStorename().split(" ");
									StringBuffer storeName = new StringBuffer();
									for (String str : storeNameArray) {
										if (str.length() >= 2) {
											storeName.append(str
													.substring(0, 2));
										}
									}

									sellerInfo.setDealerName(storeName);

									sellerInfo.setProdId(pid);
									sellerInfo.setDiscountPercentage(priceInfo
											.getDiscountPercentage());
									sellerInfo.setSellingPrice(priceInfo
											.getCost());
									sellerInfo.setTax(priceInfo.getTax());
									sellerInfo.setMinSLA(priceInfo.getMinSla());
									sellerInfo.setMaxSLA(priceInfo.getMaxSla());
									sellerInfo.setMinQuantityBuy(priceInfo
											.getMinQuantityBuy());
									sellerInfo.setShippingCharge(priceInfo
											.getShippingCharge());

									if (store.getUser().getRating() != null) {
										sellerInfo.setRating(store.getUser()
												.getRating().getAvgRating()
												.getRatingVal());
									}
								}
							} else if (priceInfo.getShippingType()
									.equalsIgnoreCase("Zonal Shipping")) {
								System.out.println("Zonal Shipping"
										+ priceInfo.getDealerId());

								Store store = storeDAO.findByUserId(priceInfo
										.getDealerId());
								if (store != null) {
									sellerInfo.setDealerId(priceInfo
											.getDealerId());

									String[] storeNameArray = store
											.getStorename().split(" ");
									StringBuffer storeName = new StringBuffer();
									for (String str : storeNameArray) {
										if (str.length() >= 2) {
											storeName.append(str
													.substring(0, 2));
										}
									}
									sellerInfo.setDealerName(storeName);

									sellerInfo.setProdId(pid);
									sellerInfo.setDiscountPercentage(priceInfo
											.getDiscountPercentage());
									sellerInfo.setSellingPrice(priceInfo
											.getCost());
									sellerInfo.setTax(priceInfo.getTax());
									sellerInfo.setMinSLA(priceInfo.getMinSla());
									sellerInfo.setMaxSLA(priceInfo.getMaxSla());
									sellerInfo.setMinQuantityBuy(priceInfo
											.getMinQuantityBuy());
									sellerInfo.setShippingCharge(priceInfo
											.getShippingCharge());

									if (store.getUser().getRating() != null) {
										sellerInfo.setRating(store.getUser()
												.getRating().getAvgRating()
												.getRatingVal());
									}
								}
							} else if (priceInfo.getShippingType()
									.equalsIgnoreCase("National Shipping")) {
								System.out.println("National Shipping"
										+ priceInfo.getDealerId());
								Store store = storeDAO.findByUserId(priceInfo
										.getDealerId());
								if (store != null) {

									sellerInfo.setDealerId(priceInfo
											.getDealerId());

									String[] storeNameArray = store
											.getStorename().split(" ");
									StringBuffer storeName = new StringBuffer();
									for (String str : storeNameArray) {
										if (str.length() >= 2) {
											storeName.append(str
													.substring(0, 2));
										}
									}
									sellerInfo.setDealerName(storeName);

									sellerInfo.setProdId(pid);
									sellerInfo.setDiscountPercentage(priceInfo
											.getDiscountPercentage());
									sellerInfo.setSellingPrice(priceInfo
											.getCost());
									sellerInfo.setTax(priceInfo.getTax());
									sellerInfo.setMinSLA(priceInfo.getMinSla());
									sellerInfo.setMaxSLA(priceInfo.getMaxSla());
									sellerInfo.setMinQuantityBuy(priceInfo
											.getMinQuantityBuy());
									sellerInfo.setShippingCharge(priceInfo
											.getShippingCharge());

									if (store.getUser().getRating() != null) {
										sellerInfo.setRating(store.getUser()
												.getRating().getAvgRating()
												.getRatingVal());
									}
								}
							}
							sellers.add(sellerInfo);
						}
						// Return the sellers list for the given zipcode
						return sellers;
					}

					// If No zipcode is entered
					// For catalog purpose
					else {
						// Get all stores for the product
						List<Product> products = new ArrayList<>();
						products.add(product);
						storeList3 = storeDAO.findByProductsIn(products);

						// Iterate all the stores
						for (Store store : storeList3) {
							// Get StoreProductPricing of each product with
							// store
							StoreProductPricing storeProductPricing = storeProductPricingDAO
									.findByProductIdAndStoreId(pid,
											store.getId());

							if (storeProductPricing != null) {
								PriceInfo priceInfo = new PriceInfo();

								if (storeProductPricing.getShipping()
										.getNationalShippingChrg() != null) {

									if (storeProductPricing.getMbgShare() != null) {

										double mbgShare = (storeProductPricing
												.getSellingPrice() * storeProductPricing
												.getMbgShare()) / 100;

										cost = storeProductPricing
												.getSellingPrice() + mbgShare;

									} else {
										cost = storeProductPricing
												.getSellingPrice();
									}

									priceInfo.setCost(cost);

									priceInfo
											.setShippingCharge(storeProductPricing
													.getShipping()
													.getNationalShippingChrg());
									priceInfo.setMinSla(storeProductPricing
											.getShipping()
											.getMinNationalProcurementSLA());
								} else {

									if (storeProductPricing.getMbgShare() != null) {

										double mbgShare = (storeProductPricing
												.getSellingPrice() * storeProductPricing
												.getMbgShare()) / 100;

										cost = storeProductPricing
												.getSellingPrice() + mbgShare;

									} else {
										cost = storeProductPricing
												.getSellingPrice();
									}

									priceInfo.setCost(cost);
									priceInfo
											.setShippingCharge(storeProductPricing
													.getShipping()
													.getLocalShippingChrg());
									priceInfo.setMinSla(storeProductPricing
											.getShipping()
											.getMinLocalProcurementSLA());
								}
								if (storeProductPricing.getTax() != null) {
									priceInfo.setTax(storeProductPricing
											.getTax());
								}
								if (storeProductPricing.getDiscount() != null) {
									priceInfo
											.setDiscountPercentage(storeProductPricing
													.getDiscount());
								}
								priceInfo.setMinQuantityBuy(storeProductPricing
										.getMinQuantityBuy());
								priceInfo.setDealerId(store.getUser().getId());

								priceInfoList.add(priceInfo);
							}
						}
						// Sort the list of dealers based on the selling
						// price(i.e.,
						// sellingPrice + shippingCharge)
						Collections.sort(priceInfoList, new PriceComparator());

						for (PriceInfo priceInfo : priceInfoList) {
							// Set the response object
							SellerInformation sellerInfo = new SellerInformation();
							System.out.println(priceInfo.getDealerId());

							Store store = storeDAO.findByUserId(priceInfo
									.getDealerId());
							if (store != null) {
								sellerInfo.setDealerId(priceInfo.getDealerId());

								String[] storeNameArray = store.getStorename()
										.split(" ");
								StringBuffer storeName = new StringBuffer();
								for (String str : storeNameArray) {
									if (str.length() >= 2) {
										storeName.append(str.substring(0, 2));
									}
								}
								sellerInfo.setDealerName(storeName);

								sellerInfo.setProdId(pid);
								sellerInfo.setDiscountPercentage(priceInfo
										.getDiscountPercentage());
								sellerInfo.setSellingPrice(priceInfo.getCost());
								sellerInfo.setTax(priceInfo.getTax());
								sellerInfo.setMinSLA(priceInfo.getMinSla());
								sellerInfo.setMaxSLA(priceInfo.getMaxSla());
								sellerInfo.setMinQuantityBuy(priceInfo
										.getMinQuantityBuy());
								sellerInfo.setShippingCharge(priceInfo
										.getShippingCharge());

								if (store.getUser().getRating() != null) {
									sellerInfo.setRating(store.getUser()
											.getRating().getAvgRating()
											.getRatingVal());
								}
							}
							sellers.add(sellerInfo);
						}
					}
				}
			} else {
				logger.info("Invalid product");
				throw new Exception("Invalid Product");
			}
		} else {
			logger.info("Pid is null");
			throw new Exception("ProductId is null");
		}
		// Return all seller information
		return sellers;

	}

	/**
	 * Method to count dealers for a product
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@Override
	public Long countDealerForProduct(String pid) throws Exception {

		Long count = null;
		Product product = null;
		if (StringUtils.isNotBlank(pid)) {
			product = productDAO.findOne(pid);
			if (product != null) {
				count = storeDAO.countByProducts(product);
			} else {
				logger.info("Product doesn't exist");
				throw new Exception("Product doesn't exist");
			}
		}
		return count;
	}

	/**
	 * Method to get provideres for perticular product.
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@Override
	public User searchProviderForProduct(String pid) throws Exception {
		User provider = null;
		if (StringUtils.isNotBlank(pid)) {
			Product product = productDAO.findOne(pid);
			logger.info("valid product ID");
			if (product != null) {
				ProductBrand productBrand = product.getBrand();
				if (productBrand != null) {
					provider = productBrand.getProvider();
					logger.info("Getting number of providers for particular product is success");
				} else {
					logger.info("Getting number of providers for particular product failed");
				}
			}
		} else {
			logger.error("Product Id should not blank");
			throw new Exception("Product Id should not blank");
		}
		return provider;
	}

	/**
	 * Method to get related products for the product
	 * 
	 * @param pid
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public Set<ProductDetailsResponse> getRelatedProducts(String pid)
			throws Exception {
		Set<ProductDetailsResponse> relatedProducts = new HashSet<ProductDetailsResponse>();
		if (StringUtils.isNotBlank(pid)) {
			Product product = productDAO.findOne(pid);
			if (product != null) {
				Set<String> relatedProductIds = product.getRelatedProducts();
				for (Product relatedProduct : productDAO
						.findAll(relatedProductIds)) {
					if (product != null) {
						ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
						productDetailsResponse.setProductId(relatedProduct
								.getId());
						productDetailsResponse.setProductName(relatedProduct
								.getName());
						if (relatedProduct.getModel() != null) {
							productDetailsResponse.setModel(relatedProduct
									.getModel());
						}
						if (relatedProduct.getSkuId() != null) {
							productDetailsResponse.setSkuId(relatedProduct
									.getSkuId());
						}
						if (relatedProduct.getBrand() != null) {
							productDetailsResponse.setBrand(relatedProduct
									.getBrand().getName());
						}
						if (relatedProduct.getSubcategories() != null) {
							productDetailsResponse
									.setSubcategories(relatedProduct
											.getSubcategories());
						}
						if (relatedProduct.getFeatures() != null) {
							productDetailsResponse.setFeatures(relatedProduct
									.getFeatures());
						}
						if (relatedProduct.getAttributes() != null) {
							productDetailsResponse.setAttributes(relatedProduct
									.getAttributes());
						}
						if (relatedProduct.getFaqs() != null) {
							productDetailsResponse.setFaqs(relatedProduct
									.getFaqs());
						}
						if (relatedProduct.getPrice() != null) {
							productDetailsResponse
									.setPrice(String.valueOf(relatedProduct
											.getPrice().getMrp()));
						}
						if (relatedProduct.getProductTypes() != null) {
							productDetailsResponse
									.setProductTypes(relatedProduct
											.getProductTypes());
						}
						if (relatedProduct.isAccessory()) {
							productDetailsResponse.setAccessory(String
									.valueOf(relatedProduct.isAccessory()));
						}
						if (relatedProduct.getRelatedProducts() != null) {
							productDetailsResponse
									.setRelatedProducts(relatedProduct
											.getRelatedProducts());
						}
						if (relatedProduct.getAccessories() != null) {
							productDetailsResponse
									.setAccessories(relatedProduct
											.getAccessories());
						}
						if (relatedProduct.getAssets() != null) {
							productDetailsResponse.setAssets(relatedProduct
									.getAssets());
						}
						if (relatedProduct.getTcDoc() != null) {
							productDetailsResponse.setTcDoc(relatedProduct
									.getTcDoc());
						}
						if (relatedProduct.getConsolidatedRating() != null) {
							productDetailsResponse.setRating(relatedProduct
									.getConsolidatedRating());
						}
						if (relatedProduct.getQuantityType() != null) {
							productDetailsResponse
									.setProductQuantityType(relatedProduct
											.getQuantityType());
						}
						relatedProducts.add(productDetailsResponse);
					}
				}
				logger.info("Related accessories found");
			} else {
				logger.error(
						"Product is not exist in our system for productId : {}",
						pid);
				throw new Exception(
						"Product is not exist in our system for productId : "
								+ pid);
			}
		} else {
			logger.error("Product ID should not null");
			throw new Exception("Product ID should not null");
		}
		return relatedProducts;
	}

	/**
	 * Method to add related products for the product
	 * 
	 * @param pid
	 * @param relatedProducts
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public Set<String> createRelatedProducts(String pid,
			List<String> relatedProducts) throws Exception {
		Product product = null;
		Set<String> relatedProductId = null;
		if (StringUtils.isNotBlank(pid)) {
			product = productDAO.findOne(pid);
			if (product != null && relatedProducts != null) {
				for (String productId : relatedProducts) {
					Product product2 = productDAO.findById(productId);
					if (product2 != null) {
						product.getRelatedProducts().add(productId);
						logger.info("Related product added");
					}
				}
				productDAO.save(product);
				relatedProductId = product.getRelatedProducts();
			}
		} else {
			logger.info("ProductId should not be null");
			throw new Exception("ProductId should not be null");
		}
		return relatedProductId;
	}

	/**
	 * Method to update related products of a product
	 * 
	 * @param pid
	 * @param relProdIds
	 * @return
	 * @throws Exception
	 */
	@Override
	public Set<String> updateRelatedProducts(String pid,
			List<String> relatedProducts) throws Exception {
		Product product = null;
		Set<String> relatedProductId = null;
		if (StringUtils.isNotBlank(pid)) {
			product = productDAO.findOne(pid);
			if (product != null && relatedProducts != null) {
				for (String product1 : relatedProducts) {
					Product product2 = productDAO.findOne(product1);
					if (product2 != null) {
						product.getRelatedProducts().add(product1);
						logger.info("Related product added");
					}
				}
				productDAO.save(product);
				relatedProductId = product.getRelatedProducts();
			}
		} else {
			throw new Exception("Product Id should not be null");
		}
		return relatedProductId;
	}

	/**
	 * Method to remove related product of a product
	 * 
	 * @param pid
	 * @param relatedProducts
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public Set<String> deleteRelatedProducts(String pid,
			List<String> relatedProducts) throws Exception {
		Product product = null;
		Set<String> relatedProductList = null;
		if (StringUtils.isNotBlank(pid)) {
			product = productDAO.findOne(pid);
			if (product != null && relatedProducts != null) {
				logger.info("Relatedproductsid is Not Null");
				product.getRelatedProducts().removeAll(relatedProducts);
				productDAO.save(product);
				relatedProductList = product.getRelatedProducts();
			} else {
				logger.info("Relatedproductsid Null");
				product.getRelatedProducts().removeAll(
						product.getRelatedProducts());
				productDAO.save(product);
				relatedProductList = product.getRelatedProducts();
			}
		} else {
			logger.error("ProductId is Null");
			throw new Exception("ProductId is Null");
		}
		return relatedProductList;
	}

	/**
	 * Method to get related accessories of a product
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@Override
	public Set<ProductDetailsResponse> getRelatedAccsories(String pid)
			throws Exception {
		Set<ProductDetailsResponse> relatedAccessories = new HashSet<ProductDetailsResponse>();
		Product product = null;
		if (StringUtils.isNotBlank(pid)) {
			product = productDAO.findOne(pid);
			if (product != null) {
				Set<String> accessoriesIds = product.getAccessories();
				for (Product accesory : productDAO.findAll(accessoriesIds)) {

					ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
					productDetailsResponse.setProductId(accesory.getId());
					productDetailsResponse.setProductName(accesory.getName());
					if (accesory.getModel() != null) {
						productDetailsResponse.setModel(accesory.getModel());
					}
					if (accesory.getSkuId() != null) {
						productDetailsResponse.setSkuId(accesory.getSkuId());
					}
					if (accesory.getBrand() != null) {
						productDetailsResponse.setBrand(accesory.getBrand()
								.getName());
					}
					if (accesory.getSubcategories() != null) {
						productDetailsResponse.setSubcategories(accesory
								.getSubcategories());
					}
					if (accesory.getFeatures() != null) {
						productDetailsResponse.setFeatures(accesory
								.getFeatures());
					}
					if (accesory.getAttributes() != null) {
						productDetailsResponse.setAttributes(accesory
								.getAttributes());
					}
					if (accesory.getFaqs() != null) {
						productDetailsResponse.setFaqs(accesory.getFaqs());
					}
					if (accesory.getPrice() != null) {
						productDetailsResponse.setPrice(String.valueOf(accesory
								.getPrice().getMrp()));
					}
					if (accesory.getProductTypes() != null) {
						productDetailsResponse.setProductTypes(accesory
								.getProductTypes());
					}
					if (accesory.isAccessory()) {
						productDetailsResponse.setAccessory(String
								.valueOf(accesory.isAccessory()));
					}
					if (accesory.getRelatedProducts() != null) {
						productDetailsResponse.setRelatedProducts(accesory
								.getRelatedProducts());
					}
					if (accesory.getAccessories() != null) {
						productDetailsResponse.setAccessories(accesory
								.getAccessories());
					}
					if (accesory.getAssets() != null) {
						productDetailsResponse.setAssets(accesory.getAssets());
					}
					if (accesory.getTcDoc() != null) {
						productDetailsResponse.setTcDoc(accesory.getTcDoc());
					}
					if (accesory.getConsolidatedRating() != null) {
						productDetailsResponse.setRating(accesory
								.getConsolidatedRating());
					}
					if (accesory.getQuantityType() != null) {
						productDetailsResponse.setProductQuantityType(accesory
								.getQuantityType());
					}
					relatedAccessories.add(productDetailsResponse);
				}
				logger.info("Related accessories found");
			} else {
				logger.error(
						"Product is not exist in our system for productId : {}",
						pid);
				throw new Exception(
						"Product is not exist in our system for productId : "
								+ pid);
			}
		} else {
			logger.error("Product ID should not be null");
			throw new Exception("Product ID should not be null");
		}
		return relatedAccessories;
	}

	/**
	 * Method to add related accessories for the product
	 * 
	 * @param pid
	 * @param accessories
	 * @return
	 */
	@Override
	public Set<String> createRelatedAccessories(String pid,
			List<String> accessories) throws Exception {
		Set<String> accessoriesIds = null;
		if (StringUtils.isNotBlank(pid)) {
			Product product = productDAO.findOne(pid);
			if (product != null) {
				logger.info("valid prouctId");
				if (accessories != null) {
					logger.info("Accessories ID is not null");
					for (String productId : accessories) {
						if (StringUtils.isNotBlank(productId)) {
							Product product1 = productDAO.findOne(productId);
							if (product1 != null) {
								logger.info("Valid accessories ID");
								product.getAccessories().add(productId);
							}
						}
					}
					productDAO.save(product);
					accessoriesIds = product.getAccessories();
				} else {
					logger.error("AccessoriesIDs is empty");
					throw new Exception("AccessoriesIDs is empty");
				}
			}
		} else {
			logger.error("Product id should not be empty");
			throw new Exception("Product id should not be empty");
		}
		return accessoriesIds;
	}

	/**
	 * Method to update related accessories of the product
	 * 
	 * @param pid
	 * @param relatedAccessories
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public Set<String> updateRelatedAccessories(String pid,
			List<String> relatedAccessories) throws Exception {
		Product product = null;
		Set<String> relatedAccessoriesList = null;
		if (StringUtils.isNotBlank(pid)) {
			product = productDAO.findOne(pid);
			if (product != null && relatedAccessories != null) {
				for (String productId : relatedAccessories) {
					Product product2 = productDAO.findById(productId);
					if (product2 != null) {
						product.getAccessories().add(productId);
						logger.info("Related Accessories added");
					}
				}
				productDAO.save(product);
				relatedAccessoriesList = product.getAccessories();
			} else {
				logger.error("RelatedAccessories Id is null");
				throw new Exception("RelatedAccessories Id is null");
			}
		}
		return relatedAccessoriesList;
	}

	/**
	 * method to delete related accessories of a product
	 * 
	 * @param pid
	 * @param accessories
	 * @return
	 */
	@Override
	public Set<String> deleteRelatedAccessories(String pid,
			List<String> accessories) {
		Product product = null;
		if (StringUtils.isNotBlank(pid)) {
			product = productDAO.findOne(pid);
			if (product != null) {
				logger.info("Valid prouctId");
				logger.info("Deleting relatedAccessories");
				if (accessories != null) {
					logger.info("Accessories ID is not null");
					for (String aid : accessories) {
						Product product1 = productDAO.findOne(aid);
						if (product1 != null) {
							product.getAccessories().remove(aid);
						}
					}
				} else {
					product.getAccessories()
							.removeAll(product.getAccessories());
				}
				productDAO.save(product);
			}
		}
		return product.getAccessories();
	}

	/**
	 * Method for create new product
	 * 
	 * @param productCreateRequestParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public Product createProduct(String userId, String skuid, String name,
			String longName, String desc, Set<String> subcategoriesId,
			Set<String> productTypeIds, String productAssets, String price,
			String accessory, List<String> relatedProducts,
			List<String> accessories, String key1, Set<String> values1,
			String isMultiValued1, String model, Set<ProductFaq> faqs,
			Set<ProductAttribute> productAttributes, String brandName,
			String quantityType) throws Exception {
		Product product = new Product();
		if (StringUtils.isNotBlank(skuid)) {
			product.setSkuId(skuid);
		}
		if (StringUtils.isNotBlank(name)) {
			product.setName(name);
		}

		if (StringUtils.isNotBlank(model)) {
			product.setModel(model);
		}
		if (StringUtils.isNotBlank(desc)) {
			Description descr = new Description();
			descr.setLang("en");
			descr.setVal(desc);
			product.setDesc(descr);
		}
		if (subcategoriesId != null) {
			Set<SubCategory> subcategoriesList = new HashSet<>();
			for (String subCategories : subcategoriesId) {
				if (subCategories != null) {
					SubCategory subCategory = subCategoryDAO
							.findOne(subCategories);
					if (subCategory != null) {
						subcategoriesList.add(subCategory);
					}
				}
			}
			product.setSubcategories(subcategoriesList);
		}
		ProductType productType = null;
		if (productTypeIds != null) {

			Set<ProductType> productTypeSet = new HashSet<ProductType>();
			for (String productTypeId : productTypeIds) {
				productType = productTypeDAO.findOne(productTypeId);
				productTypeSet.add(productType);
			}

			product.setProductTypes(productTypeSet);
		}
		// product name contains brand and productType info
		longName = name + "-" + skuid;
		product.setLongName(longName);

		if (StringUtils.isNotBlank(brandName)) {
			ProductBrand productBrand = brandDAO.findByName(brandName);
			if (productBrand != null) {
				product.setBrand(productBrand);
			} else {
				logger.info("Brand is not exists for BrandName : {}", brandName);
			}
		}

		if (StringUtils.isNotBlank(quantityType)) {
			ProductQuantityType productQuantityType = productQuantityTypeDAO
					.findOne(quantityType);
			if (productQuantityType != null) {
				product.setQuantityType(productQuantityType);
			} else {
				logger.info("Product Quantity type doesn't exists");
			}
		}

		if (StringUtils.isNotBlank(price)) {
			Price price1 = new Price();
			price1.setCurrency("Rupee");
			price1.setMrp(Double.parseDouble(price));
			product.setPrice(price1);
		}
		ConsolidatedRating consolidatedRating = new ConsolidatedRating();
		Rating rating = new Rating();
		rating.setRatingVal(0.0);
		consolidatedRating.setAvgRating(rating);
		product.setConsolidatedRating(consolidatedRating);

		if (StringUtils.isNotBlank(accessory)) {
			boolean acc = Boolean.parseBoolean(accessory);
			product.setAccessory(acc);
		}
		if (relatedProducts != null) {
			Set<String> relatedProductsSet = new HashSet<>();
			for (int i = 0; i < relatedProducts.size(); i++) {
				Product l_product = productDAO.findOne(relatedProducts.get(i));
				if (l_product != null)
					relatedProductsSet.add(relatedProducts.get(i));
				else
					throw new Exception("Invalid relatedProductId "
							+ relatedProducts.get(i));
			}
			product.setRelatedProducts(relatedProductsSet);
		}
		if (accessories != null) {
			Set<String> accessoriesSet = new HashSet<>();
			for (int i = 0; i < accessories.size(); i++) {
				Product l_product = productDAO.findOne(accessories.get(i));
				if (l_product != null)
					accessoriesSet.add(accessories.get(i));
				else
					throw new Exception("Invalid accessoriesId "
							+ accessories.get(i));
			}
			product.setAccessories(accessoriesSet);
		}

		if (StringUtils.isNotBlank(productAssets) && productAssets != null) {
			ProductAsset productAsset = new ProductAsset();
			Video video = new Video();
			video.setUrl(productAssets);
			video.setName(name);
			video.setFileType(FileType.VIDEO);
			productAsset.setVideo(video);
			product.setAssets(productAsset);
		}

		product.setStatus(ProductStatus.SAVED);
		product.setCreatedDate(new Date());
		product.setLastModifiedDate(new Date());
		product.setCreatedBy(userId);
		product.setLastModifiedBy(userId);

		if (StringUtils.isNotBlank(key1) && values1 != null) {
			ProductAttribute productAttributes1 = new ProductAttribute();
			productAttributes1.setKey(key1);
			if (isMultiValued1.equalsIgnoreCase("true")) {
				productAttributes1.setMultiValued(true);
				productAttributes1.setValues(values1);
			} else {
				productAttributes1.setMultiValued(false);
			}
			product.setFeatures(productAttributes1);
		}
		if (faqs != null) {
			product.setFaqs(faqs);
		}
		if (productAttributes != null) {
			product.setAttributes(productAttributes);
		}

		if (productValidation.productValidation(product)) {
			productDAO.insert(product);
		}

		// Adding subcategoryIds to brand
		if (StringUtils.isNotBlank(brandName)) {
			ProductBrand productBrand = brandDAO.findByName(brandName);
			if (productBrand != null) {
				if (productBrand.getSubCategoryIds() != null
						&& productBrand.getSubCategoryIds().size() != 0) {
					Set<String> subCatIds = productBrand.getSubCategoryIds();
					subCatIds.addAll(subcategoriesId);
					productBrand.setSubCategoryIds(subCatIds);
				} else {
					productBrand.setSubCategoryIds(subcategoriesId);
				}
				brandDAO.save(productBrand);
			} else {
				logger.info("Brand is not exists for BrandName : {}", brandName);
			}
		}

		return product;
	}

	/**
	 * method for get all brands.
	 * 
	 * @return
	 */
	@Override
	public List<ProductBrand> getAllBrands() {
		logger.info("finding all the brands");
		return brandDAO.findAll();
	}

	/**
	 * method for get product count of onboarded by me.
	 * 
	 * @return
	 */
	@Override
	public long getOnBoardedByMe(String userId) {
		long onBoardedCount = 0;
		List<ProductBrand> brandList = new ArrayList<>();
		brandList = brandDAO.findByProvider_Id(userId);
		for (ProductBrand brand : brandList) {
			onBoardedCount = productDAO.countByBrand(brand);
		}
		return onBoardedCount;
	}

	/**
	 * method for get products onboarded by me.
	 * 
	 * @param view
	 * @return
	 */
	@Override
	public List<DealerOnboardedProductDetails> getAllProductsOnboardedMe(
			String userId) {
		List<DealerOnboardedProductDetails> dealerOnboardedProductDetailsList = new ArrayList<>();
		List<Product> productList = productDAO.findByCreatedBy(userId);
		for (Product product : productList) {
			DealerOnboardedProductDetails dealerOnboardedProductDetails = new DealerOnboardedProductDetails();
			dealerOnboardedProductDetails.setId(product.getId());
			// name
			dealerOnboardedProductDetails.setName(product.getName());
			// model
			dealerOnboardedProductDetails.setModel(product.getModel());

			// Assets
			if (product.getAssets() != null) {
				dealerOnboardedProductDetails.setAssets(product.getAssets());
			}
			// Subcategory && MainCategory
			Set<Category> mainCategory = new HashSet<>();
			Set<SubCategory> subcategoriesList = product.getSubcategories();
			for (SubCategory subcategory : subcategoriesList) {
				mainCategory.addAll(categoryDAO
						.findByNameAllIgnoreCase(subcategory.getCategory()));
			}
			dealerOnboardedProductDetails.setSubCategory(subcategoriesList);
			dealerOnboardedProductDetails.setMainCategory(mainCategory);
			// status
			dealerOnboardedProductDetails.setStatus(product.getStatus());
			dealerOnboardedProductDetailsList
					.add(dealerOnboardedProductDetails);
		}

		// }
		return dealerOnboardedProductDetailsList;
	}

	/**
	 * method for get products onboarded by me.
	 * 
	 * @param view
	 * @return
	 */
	@Override
	public List<Product> getAllonBoardedBymeProducts(String userId) {
		List<Product> productList = productDAO.findByCreatedBy(userId);
		return productList;
	}

	/**
	 * Method for get all ProductTypes
	 * 
	 * @param view
	 * @return
	 */
	@Override
	public List<ProductType> getSystemProductTypes() {
		List<ProductType> productTypes = productTypeDAO.findAll();
		return productTypes;
	}

	/**
	 * method for create product quantity type.
	 * 
	 * @param productQuantityCreateRequestParam
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public ProductQuantityType createProductQuantityType(String name,
			String desc) throws Exception {
		ProductQuantityType productQuantityType = null;
		if (StringUtils.isNotBlank(name)) {
			productQuantityType = new ProductQuantityType();
			productQuantityType.setName(name);
			if (StringUtils.isNotBlank(desc)) {
				Description desc1 = new Description();
				desc1.setLang("eng");
				desc1.setVal(desc);
				productQuantityType.setDesc(desc1);
			} else {
				logger.info("ProductQuantity type description null");
			}
			logger.info("Creating ProductQuantityType");
			productQuantityTypeDAO.insert(productQuantityType);
		} else {
			logger.error("Unable to create ProductQuantityType");
			throw new Exception("Unable to create ProductQuantityType");
		}
		return productQuantityType;
	}

	/**
	 * method for get product quantity types
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public List<ProductQuantityType> getProductQuantityType() {
		List<ProductQuantityType> productQuantityTypeList = null;

		productQuantityTypeList = productQuantityTypeDAO.findAll();

		return productQuantityTypeList;
	}

	/**
	 * Method to check promocode
	 * 
	 * @param pid
	 * @param dealerId
	 * @param promocode
	 * @return
	 * @throws Exception
	 */
	@Override
	public PriceResponse checkPromoCode(String pid, String dealerId,
			String promoCode) throws Exception {
		Promotion promotion = null;
		PriceResponse priceResponse = null;
		Double discountedPrice = null;
		Double mbgDiscountedPrice = null;

		if (StringUtils.isNotBlank(promoCode)) {
			promotion = promotionDAO.findByPromoCode(promoCode.toUpperCase());
			priceResponse = new PriceResponse();
			// Product level promotion
			if (promotion != null && promotion.getProductIds() != null
					&& promotion.isActive()) {
				logger.info("Product Level promotion");
				Date eDate = promotion.getEndDate();
				Date todayDate = new Date();

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String format1 = formatter.format(eDate);
				String format2 = formatter.format(todayDate);
				if (format2.compareTo(format1) <= 0) {
					for (String proId : promotion.getProductIds()) {
						if (proId.equalsIgnoreCase(pid)) {
							if (StringUtils.isNotBlank(dealerId)) {
								User dealer = userDAO.findOne(dealerId);
								if (dealer != null) {
									Store store = storeDAO.findByUser(dealer);
									if (store != null) {

										StoreProductPricing storeProductPricing = storeProductPricingDAO
												.findByProductIdAndStoreId(pid,
														store.getId());

										if (storeProductPricing != null) {
											if (storeProductPricing
													.getProductId()
													.equalsIgnoreCase(pid)) {

												// promotion name
												if (promotion.getName() != null) {
													priceResponse
															.setPromoName(promotion
																	.getName());
												}
												// promo code
												if (promotion.getPromoCode() != null) {
													priceResponse
															.setPromoCode(promotion
																	.getPromoCode());
												}

												Double mbgShare = storeProductPricing
														.getMbgShare();

												if (promotion.getDiscount() < mbgShare) {
													if (mbgShare > 0) {

														double mbgShareDiscountAmount = ((storeProductPricing
																.getMbgShare() / 100) * storeProductPricing
																.getSellingPrice());

														double newSellingPrice = storeProductPricing
																.getSellingPrice()
																+ mbgShareDiscountAmount;

														double mbgPromoDiscountAmt = ((promotion
																.getDiscount() / 100) * newSellingPrice);

														double sellingPriceAfterPromo = newSellingPrice
																- mbgPromoDiscountAmt;

														double savings = newSellingPrice
																- sellingPriceAfterPromo;

														// selling price
														priceResponse
																.setSellingPrice(newSellingPrice);

														// discount price
														priceResponse
																.setMbgDiscountedPrice(sellingPriceAfterPromo);

														// savings
														priceResponse
																.setSavings(savings);

													} else {
														discountedPrice = ((promotion
																.getDiscount() / 100) * storeProductPricing
																.getSellingPrice());

														mbgDiscountedPrice = storeProductPricing
																.getSellingPrice()
																- discountedPrice;

														// selling price
														priceResponse
																.setSellingPrice(storeProductPricing
																		.getSellingPrice());

														// discount price
														priceResponse
																.setMbgDiscountedPrice(mbgDiscountedPrice);

														// savings
														priceResponse
																.setSavings(discountedPrice);
													}

												} else {
													discountedPrice = ((storeProductPricing
															.getMbgShare() / 100) * storeProductPricing
															.getSellingPrice());

													mbgDiscountedPrice = storeProductPricing
															.getSellingPrice()
															- discountedPrice;

													// selling price
													priceResponse
															.setSellingPrice(storeProductPricing
																	.getSellingPrice());

													// discount
													priceResponse
															.setMbgDiscountedPrice(mbgDiscountedPrice);

													// savings
													priceResponse
															.setSavings(discountedPrice);
												}
											}
										}
									} else {
										logger.error("Dealer not available for the product");
										throw new Exception(
												"Dealer not available for the product");
									}
								} else {
									logger.error("Invalid dealer Id");
									throw new Exception("Invalid dealer Id");
								}
							} else {
								logger.error("Dealer Id is null");
								throw new Exception("Dealer Id cannot be null");
							}
						}
					}
				} else {
					logger.error("Promo code expired");
					throw new Exception("Promo code expired");
				}

			}
			// Category/SubCategory Level promotion
			else if (promotion != null && promotion.getSubCategory() != null
					&& promotion.isActive()) {
				logger.info("Category/SubCategory Level promotion");
				Date eDate = promotion.getEndDate();
				Date todayDate = new Date();

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String format1 = formatter.format(eDate);
				String format2 = formatter.format(todayDate);
				if (format2.compareTo(format1) <= 0) {
					Set<SubCategory> subCategories = promotion.getSubCategory();
					Product product = productDAO.findById(pid);
					Set<SubCategory> productSubCategories = product
							.getSubcategories();
					for (SubCategory prodSubCat : productSubCategories) {
						for (SubCategory promoSubCategory : subCategories) {
							if (prodSubCat.getId().equalsIgnoreCase(
									promoSubCategory.getId())) {
								if (StringUtils.isNotBlank(dealerId)) {
									User dealer = userDAO.findOne(dealerId);
									if (dealer != null) {
										Store store = storeDAO
												.findByUser(dealer);
										if (store != null) {

											StoreProductPricing storeProductPricing = storeProductPricingDAO
													.findByProductIdAndStoreId(
															pid, store.getId());
											if (storeProductPricing != null) {
												if (storeProductPricing
														.getProductId()
														.equalsIgnoreCase(pid)) {

													// promotion name
													if (promotion.getName() != null) {
														priceResponse
																.setPromoName(promotion
																		.getName());
													}
													// promo code
													if (promotion
															.getPromoCode() != null) {
														priceResponse
																.setPromoCode(promotion
																		.getPromoCode());
													}

													Double mbgShare = storeProductPricing
															.getMbgShare();

													if (promotion.getDiscount() < mbgShare) {
														if (mbgShare > 0) {

															double mbgShareDiscountAmount = ((storeProductPricing
																	.getMbgShare() / 100) * storeProductPricing
																	.getSellingPrice());

															double newSellingPrice = storeProductPricing
																	.getSellingPrice()
																	+ mbgShareDiscountAmount;

															double mbgPromoDiscountAmt = ((promotion
																	.getDiscount() / 100) * newSellingPrice);

															double sellingPriceAfterPromo = newSellingPrice
																	- mbgPromoDiscountAmt;

															double savings = newSellingPrice
																	- sellingPriceAfterPromo;

															// selling price
															priceResponse
																	.setSellingPrice(newSellingPrice);

															// discount price
															priceResponse
																	.setMbgDiscountedPrice(sellingPriceAfterPromo);

															// savings
															priceResponse
																	.setSavings(savings);

														} else {
															discountedPrice = ((promotion
																	.getDiscount() / 100) * storeProductPricing
																	.getSellingPrice());

															mbgDiscountedPrice = storeProductPricing
																	.getSellingPrice()
																	- discountedPrice;

															// selling price
															priceResponse
																	.setSellingPrice(storeProductPricing
																			.getSellingPrice());

															// discount
															priceResponse
																	.setMbgDiscountedPrice(mbgDiscountedPrice);

															// savings
															priceResponse
																	.setSavings(discountedPrice);
														}

													} else {
														discountedPrice = ((storeProductPricing
																.getMbgShare() / 100) * storeProductPricing
																.getSellingPrice());
														mbgDiscountedPrice = storeProductPricing
																.getSellingPrice()
																- discountedPrice;

														// selling price
														priceResponse
																.setSellingPrice(storeProductPricing
																		.getSellingPrice());

														// discount
														priceResponse
																.setMbgDiscountedPrice(mbgDiscountedPrice);

														// savings
														priceResponse
																.setSavings(discountedPrice);

													}
												}
											}
										} else {
											logger.error("Dealer not available for the product");
											throw new Exception(
													"Dealer not available for the product");
										}
									} else {
										logger.error("Invalid Dealer Id");
										throw new Exception("Invalid Dealer Id");
									}
								} else {
									logger.error("Dealer Id is null");
									throw new Exception(
											"Dealer Id cannot be null");
								}
							}
						}
					}
				} else {
					logger.error("Promo code expired");
					throw new Exception("Promo code expired");
				}
			}// Brand Level promotion
			else if (promotion != null && promotion.getBrands() != null
					&& promotion.isActive()) {
				logger.info("Brand Level promotion");
				Date eDate = promotion.getEndDate();
				Date todayDate = new Date();

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String format1 = formatter.format(eDate);
				String format2 = formatter.format(todayDate);
				if (format2.compareTo(format1) <= 0) {
					Set<ProductBrand> promoBrands = promotion.getBrands();
					Product product = productDAO.findById(pid);

					// Product Brand
					ProductBrand productBrand = product.getBrand();

					for (ProductBrand promoBrand : promoBrands) {
						if (promoBrand.getId().equalsIgnoreCase(
								productBrand.getId())) {
							if (StringUtils.isNotBlank(dealerId)) {
								User dealer = userDAO.findOne(dealerId);
								if (dealer != null) {
									Store store = storeDAO.findByUser(dealer);
									if (store != null) {

										StoreProductPricing storeProductPricing = storeProductPricingDAO
												.findByProductIdAndStoreId(pid,
														store.getId());
										if (storeProductPricing != null) {

											if (storeProductPricing
													.getProductId()
													.equalsIgnoreCase(pid)) {

												// promotion name
												if (promotion.getName() != null) {
													priceResponse
															.setPromoName(promotion
																	.getName());
												}
												// promo code
												if (promotion.getPromoCode() != null) {
													priceResponse
															.setPromoCode(promotion
																	.getPromoCode());
												}

												Double mbgShare = storeProductPricing
														.getMbgShare();

												if (promotion.getDiscount() < mbgShare) {
													if (mbgShare > 0) {

														double mbgShareDiscountAmount = ((storeProductPricing
																.getMbgShare() / 100) * storeProductPricing
																.getSellingPrice());

														double newSellingPrice = storeProductPricing
																.getSellingPrice()
																+ mbgShareDiscountAmount;

														double mbgPromoDiscountAmt = ((promotion
																.getDiscount() / 100) * newSellingPrice);

														double sellingPriceAfterPromo = newSellingPrice
																- mbgPromoDiscountAmt;

														double savings = newSellingPrice
																- sellingPriceAfterPromo;

														// selling price
														priceResponse
																.setSellingPrice(newSellingPrice);

														// discount price
														priceResponse
																.setMbgDiscountedPrice(sellingPriceAfterPromo);

														// savings
														priceResponse
																.setSavings(savings);

													} else {

														discountedPrice = ((promotion
																.getDiscount() / 100) * storeProductPricing
																.getSellingPrice());

														mbgDiscountedPrice = storeProductPricing
																.getSellingPrice()
																- discountedPrice;

														// selling price
														priceResponse
																.setSellingPrice(storeProductPricing
																		.getSellingPrice());
														// discount
														priceResponse
																.setMbgDiscountedPrice(mbgDiscountedPrice);

														// savings
														priceResponse
																.setSavings(discountedPrice);
													}
												} else {
													discountedPrice = ((storeProductPricing
															.getMbgShare() / 100) * storeProductPricing
															.getSellingPrice());
													mbgDiscountedPrice = storeProductPricing
															.getSellingPrice()
															- discountedPrice;

													// selling price
													priceResponse
															.setSellingPrice(storeProductPricing
																	.getSellingPrice());

													// discount
													priceResponse
															.setMbgDiscountedPrice(mbgDiscountedPrice);

													// savings
													priceResponse
															.setSavings(discountedPrice);
												}
											}
										}
									} else {
										logger.error("Dealer not available for the product");
										throw new Exception(
												"Dealer not available for the product");
									}
								} else {
									logger.error("Invalid dealer Id");
									throw new Exception("Invalid dealer Id");
								}

							} else {
								logger.error("Dealer Id is null");
								throw new Exception("Dealer Id cannot be empty");
							}
						} else {
							logger.error("Currently this Promotion is not applicable for this product");
							throw new Exception(
									"Currently this Promotion is not applicable for this product");
						}
					}
				} else {
					logger.error("Promo code expired");
					throw new Exception("Promo code expired");
				}

			}
		} else {
			logger.error("Promo code cannot be null");
			throw new Exception("Promo code cannot be null");
		}

		return priceResponse;
	}

	/**
	 * Method to get promo code for the product
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Promotion> getPromoCodeForProduct(String pid) throws Exception {

		List<Promotion> productPromtions = new ArrayList<Promotion>();

		List<Promotion> promotionList = new ArrayList<Promotion>();
		List<Promotion> finalPromotionList = new ArrayList<Promotion>();

		if (StringUtils.isNotBlank(pid)) {
			Product product = productDAO.findById(pid);
			if (product != null) {

				Set<String> prodIds = new HashSet<String>();
				prodIds.add(pid);

				productPromtions.addAll(promotionDAO
						.findByProductIdsIn(prodIds));

				Set<SubCategory> prodSubCat = product.getSubcategories();
				productPromtions.addAll(promotionDAO
						.findBySubCategoryIn(prodSubCat));

				ProductBrand prodBrand = product.getBrand();
				Set<ProductBrand> prodBrandList = new HashSet<ProductBrand>();
				prodBrandList.add(prodBrand);

				productPromtions.addAll(promotionDAO
						.findByBrandsIn(prodBrandList));

				promotionList.addAll(productPromtions);

				for (Promotion promotion : productPromtions) {
					if (promotion.isActive()) {

						Date eDate = promotion.getEndDate();
						Date todayDate = new Date();

						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd");
						String format1 = formatter.format(eDate);
						String format2 = formatter.format(todayDate);
						if (format2.compareTo(format1) <= 0) {
							finalPromotionList.add(promotion);
						} else {
							logger.error("Inactive Promotion(Promotion expired");
						}
					}
				}
			} else {
				logger.error("Product doesn't exist");
				throw new Exception("Product doesn't exist");
			}
		} else {
			logger.error("Product Id is null");
			throw new Exception("Product Id cannot be null");
		}
		return finalPromotionList;
	}

	/**
	 * method for get top search keywords
	 * 
	 * @return
	 */
	@Override
	public List<SearchKeywordResponse> getTopSearchedKeywords(int page,
			int size, Sort sort) {
		List<SearchKeywordResponse> keywordResponses = new ArrayList<>();

		AggregationResults<SearchKeywordStore> results = operations.aggregate(
				newAggregation(SearchKeywordStore.class, group("keyword")
						.count().as("count"), sort(Direction.DESC, "count")),
				SearchKeywordStore.class);
		List<SearchKeywordStore> keywordsList = results.getMappedResults();
		for (int i = 0; i < keywordsList.size(); i++) {
			if (i < 30) {
				String keywordStore = keywordsList.get(i).getId();
				if (keywordStore != null) {
					SearchKeywordResponse response = new SearchKeywordResponse();
					List<SearchKeywordStore> list = searchKeywordStoreDAO
							.findByKeyword(keywordStore);
					response.setKeyword(keywordStore);
					response.setCount(list.size());
					keywordResponses.add(response);
				}
			} else {
				break;
			}
		}
		return keywordResponses;
	}

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
	@Override
	public Product deleteFile(String pid, String fileUrl, String fileTypeValue)
			throws Exception {
		Product product = null;
		// Check if product id is null
		if (StringUtils.isNotBlank(pid)) {
			product = productDAO.findOne(pid);
			// Check if product exist
			if (product != null) {
				// Get all product assets
				ProductAsset productAsset = product.getAssets();
				// Check if productAssets exist
				if (productAsset != null) {

					if (fileTypeValue.equalsIgnoreCase("image")) {
						List<Image> images = productAsset.getImages();
						List<Image> images1 = new ArrayList<>();
						images1.addAll(productAsset.getImages());
						for (Image image : images1) {
							// Check for URL matching
							if (image.getUrl().equalsIgnoreCase(fileUrl)) {
								// Delete file from the Azure
								filestorage
										.deleteFile(
												AzureFileStorage.IMG_CONTAINER,
												fileUrl);
								// Remove image from the list
								images.remove(image);
							}
						}
					} else if (fileTypeValue.equalsIgnoreCase("file")) {
						List<File> files = productAsset.getRelatedFiles();
						List<File> files1 = new ArrayList<>();
						files1.addAll(productAsset.getRelatedFiles());
						for (File file : files1) {
							// Check for URL matching
							if (file.getUrl().equalsIgnoreCase(fileUrl)) {
								// Delete file from the Azure
								filestorage.deleteFile(
										AzureFileStorage.FILE_CONTAINER,
										fileUrl);
								// Remove file from the list
								files.remove(file);
							}
						}
					}
				}
				product.setAssets(productAsset);
				// Save changes
				productDAO.save(product);
			} else {
				logger.error("Product doesn't exist for ProductId : {}", pid);
				throw new Exception("Product doesn't exist for ProductId : "
						+ pid);
			}
		} else {
			logger.error("Product Id cannot be null");
			throw new Exception("Product Id cannot be null");
		}
		return product;
	}

	/**
	 * Method to update ProductQuantityType
	 * 
	 * @param qtyTypeId
	 * @param productQuantityUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public ProductQuantityType editProductQuantityType(String qtyTypeId,
			String name, String desc) throws Exception {

		ProductQuantityType productQuantityType = null;
		// Check if string is empty
		if (StringUtils.isNotBlank(qtyTypeId)) {
			productQuantityType = productQuantityTypeDAO.findOne(qtyTypeId);
			// Check if productQuantityType is null
			if (productQuantityType != null) {
				if (StringUtils.isNotBlank(name)) {
					productQuantityType.setName(name);
				}
				if (StringUtils.isNotBlank(desc)) {
					Description descr = new Description();
					descr.setLang("Eng");
					descr.setVal(desc);
					productQuantityType.setDesc(descr);
				}

				// Save changes in the DB
				productQuantityTypeDAO.save(productQuantityType);
			} else {
				logger.error("Product QuantityType doesn't exist");
				throw new Exception("Product QuantityType doesn't exist");
			}
		} else {
			logger.error("Product QuantityTypeId cannot be null");
			throw new Exception("Product QuantityTypeId cannot be null");
		}
		return productQuantityType;
	}

	/**
	 * Method to delete a productQuantityType
	 * 
	 * @param qtyTypeId
	 * @return
	 * @throws Exception
	 */
	@Override
	public ProductQuantityType deleteProductQuantityType(String qtyTypeId)
			throws Exception {
		ProductQuantityType productQuantityType = null;
		// Check if string is empty
		if (StringUtils.isNotBlank(qtyTypeId)) {
			productQuantityType = productQuantityTypeDAO.findOne(qtyTypeId);
			// Check if productQuantityType is null
			if (productQuantityType != null) {
				productQuantityTypeDAO.delete(productQuantityType);
			} else {
				logger.error("Product QuantityType doesn't exist");
				throw new Exception("Product QuantityType doesn't exist");
			}
		} else {
			logger.error("Product QuantityTypeId cannot be null");
			throw new Exception("Product QuantityTypeId cannot be null");
		}

		return productQuantityType;
	}

	/**
	 * method for get customer bought products
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@Override
	public Set<Product> getCustomerBoughtThisAlsoBought(String pid)
			throws Exception {
		Set<Product> customerBoughtProducts = new HashSet<Product>();
		Set<String> productIds = new HashSet<>();
		Product product = null;
		if (StringUtils.isNotBlank(pid)) {
			product = productDAO.findOne(pid);
			if (product != null) {
				List<OrderDetails> orderDetailsList = userOrderDetailRepository
						.findByItemId(pid);
				if (orderDetailsList.size() != 0) {
					for (OrderDetails orderDetails : orderDetailsList) {
						Order order = orderDetails.getOrder();
						if (order != null) {
							List<OrderDetails> items = new ArrayList<>();
							items = userOrderDetailRepository
									.findByOrder(order);
							for (OrderDetails item : items) {
								if (item.getItemId().equalsIgnoreCase(pid)) {
									logger.info(" Item and Product both are same");
								} else {
									Product product2 = productDAO.findOne(item
											.getItemId());
									if (product2 != null) {
										productIds.add(product2.getId());
									} else {
										logger.info(" product doesn't exists");
									}
								}
							}
						}
					}
					for (String productId : productIds) {
						Product product3 = productDAO.findOne(productId);
						if (product3 != null) {
							customerBoughtProducts.add(product3);
						}
					}
				}
			} else {
				logger.error("Invalid product ID");
				throw new Exception("Invalid product ID");
			}
		} else {
			logger.error("Product ID should not null");
			throw new Exception("Product ID should not null");
		}
		return customerBoughtProducts;
	}

	@Override
	public SearchProductBySkuIdResponse getProductBySkuId(String skuId)
			throws Exception {
		SearchProductBySkuIdResponse searchProductBySkuIdResponse = null;
		Product product = productDAO.findBySkuId(skuId);
		if (product != null) {
			searchProductBySkuIdResponse = new SearchProductBySkuIdResponse();
			if (product.getId() != null) {
				searchProductBySkuIdResponse.setProductId(product.getId());
			}
			if (product.getSkuId() != null) {
				searchProductBySkuIdResponse.setSkuId(product.getSkuId());
			}
			if (product.getName() != null) {
				searchProductBySkuIdResponse.setName(product.getName());
			}
			if (product.getModel() != null) {
				searchProductBySkuIdResponse.setModel(product.getModel());
			}
			if (product.getLongName() != null) {
				searchProductBySkuIdResponse.setLongName(product.getLongName());
			}
			if (product.getDesc() != null) {
				searchProductBySkuIdResponse.setDesc(product.getDesc());
			}
			if (product.getBrand() != null) {
				searchProductBySkuIdResponse.setBrand(product.getBrand());
			}

			if (product.getQuantityType() != null) {
				searchProductBySkuIdResponse.setQuantityType(product
						.getQuantityType());
			}
			if (product.getSubcategories() != null) {
				searchProductBySkuIdResponse.setSubcategories(product
						.getSubcategories());
			}
			if (product.getProductTypes() != null) {
				searchProductBySkuIdResponse.setProductTypes(product
						.getProductTypes());
			}
			if (product.getAssets() != null) {
				searchProductBySkuIdResponse.setAssets(product.getAssets());
			}
			if (product.getFeatures() != null) {
				searchProductBySkuIdResponse.setFeatures(product.getFeatures());
			}
			if (product.getAttributes() != null) {
				searchProductBySkuIdResponse.setAttributes(product
						.getAttributes());
			}
			if (product.getFaqs() != null) {
				searchProductBySkuIdResponse.setFaqs(product.getFaqs());
			}
			//
			if (product.getTcDoc() != null) {
				searchProductBySkuIdResponse.setTcDoc(product.getTcDoc());
			}
			if (product.getPrice() != null) {
				searchProductBySkuIdResponse.setPrice(product.getPrice());
			}

			if (product.getRelatedProducts() != null) {
				searchProductBySkuIdResponse.setRelatedProducts(product
						.getRelatedProducts());
			}
			if (product.getAccessories() != null) {
				searchProductBySkuIdResponse.setAccessories(product
						.getAccessories());
			}
			if (product.getStatus() != null) {
				searchProductBySkuIdResponse.setStatus(product.getStatus());
			}
			if (product.getConsolidatedRating() != null) {
				searchProductBySkuIdResponse.setConsolidatedRating(product
						.getConsolidatedRating());
			}
		} else {
			throw new Exception("Product is null for skuid " + skuId);
		}
		return searchProductBySkuIdResponse;
	}

	@Override
	public void adminBulkUpdatingStoreProductsInfo(String dealerId,
			String brand, String status, String subCategory,
			String nationalShippingCharge, String zonalShippingCharge,
			String localShippingCharge, String minNationalProcurementSLA,
			String maxNationalProcurementSLA, String minZonalProcurementSLA,
			String maxZonalProcurementSLA, String minLocalPocurementSLA,
			String maxLocalPocurementSLA, String mbgShare, String localRegion,
			String zonalRegion, String nationRegion, String discount,
			String willYouDeliver) throws Exception {

		ProductCustomSearch productCustomSearch = new ProductCustomSearch();

		List<Product> products = new ArrayList<>();

		User dealer = userDAO.findById(dealerId);

		if (dealer != null) {

			if (dealer.getRoles() != null) {
				Set<Role> roles = dealer.getRoles();
				for (Role role : roles) {
					if (role.getName().equalsIgnoreCase("DEALER")) {
						Store store = storeDAO.findByUser(dealer);
						if (store != null) {
							List<Product> productList = store.getProducts();

							Set<String> prodIds = new HashSet<String>();
							if (productList != null && productList.size() != 0) {
								for (Product product : productList) {
									prodIds.add(product.getId());
								}
							} else {
								throw new Exception(
										"Store Doesn't have any products");
							}

							// Brands
							if (StringUtils.isNotBlank(brand)) {
								String[] brandIds = brand.split(",");
								List<String> brands = new ArrayList<String>();
								for (String brandId : brandIds) {
									brands.add(brandId);
								}
								productCustomSearch.setProductBrandIds(brands);
							}

							// Status
							if (StringUtils.isNotBlank(status)) {
								String[] statuses = status.split(",");
								List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
								for (int i = 0; i < statuses.length; i++) {
									productStatuses.add(ProductStatus
											.valueOf(statuses[i]));
								}

								productCustomSearch
										.setProductStatuses(productStatuses);
							}

							// SubCategories
							if (StringUtils.isNotBlank(subCategory)) {
								List<SubCategory> subCategories = new ArrayList<>();
								List<String> subCategoriesName = new ArrayList<>();
								String[] subCategoryList = subCategory
										.split(",");
								for (String subcatNames : subCategoryList) {
									subCategories = subCategoryDAO
											.findByName(subcatNames);
									for (SubCategory SubCategory : subCategories) {
										subCategoriesName.add(SubCategory
												.getName());
									}
								}
								productCustomSearch
										.setSubCategoryNames(subCategoriesName);
							}

							// productIds
							if (prodIds != null) {
								productCustomSearch.setProductIds(prodIds);
							}

							// Required fields
							Set<String> requiredFieldSet = new HashSet<>();
							requiredFieldSet.add("_id");
							requiredFieldSet.add("name");
							requiredFieldSet.add("model");
							requiredFieldSet.add("createdDate");
							requiredFieldSet.add("subcategories");
							requiredFieldSet.add("status");
							requiredFieldSet.add("brand");
							requiredFieldSet.add("price");
							requiredFieldSet.add("consolidatedRating");
							requiredFieldSet.add("desc");
							requiredFieldSet.add("assets");
							productCustomSearch
									.setRequiredFields(requiredFieldSet);

							products = productDAO
									.searchProducts(productCustomSearch);

							for (Product product1 : products) {

								String productId = product1.getId();

								StoreProductPricing storeProductPricing = storeProductPricingDAO
										.findByProductIdAndStoreId(productId,
												store.getId());
								if (storeProductPricing != null) {
									if (storeProductPricing.getProductId()
											.equalsIgnoreCase(productId)) {

										Product product = productDAO
												.findOne(productId);
										storeProductPricing
												.setProductId(productId);
										storeProductPricing
												.setMaxRetailPrice(product
														.getPrice().getMrp());

										Shipping shipping = storeProductPricing
												.getShipping();
										if (StringUtils
												.isNotBlank(nationalShippingCharge)) {
											shipping.setNationalShippingChrg(Double
													.parseDouble(nationalShippingCharge));
										}
										if (StringUtils
												.isNotBlank(zonalShippingCharge)) {
											shipping.setZonalShippingChrg(Double
													.parseDouble(zonalShippingCharge));
										}
										if (StringUtils
												.isNotBlank(localShippingCharge)) {
											shipping.setLocalShippingChrg(Double
													.parseDouble(localShippingCharge));
										}
										if (StringUtils
												.isNotBlank(minNationalProcurementSLA)) {
											shipping.setMinNationalProcurementSLA(Integer
													.parseInt(minNationalProcurementSLA));
										}
										if (StringUtils
												.isNotBlank(maxNationalProcurementSLA)) {
											shipping.setMaxNationalProcurementSLA(Integer
													.parseInt(maxNationalProcurementSLA));
										}
										if (StringUtils
												.isNotBlank(minZonalProcurementSLA)) {
											shipping.setMinZonalProcurementSLA(Integer
													.parseInt(minZonalProcurementSLA));
										}
										if (StringUtils
												.isNotBlank(maxZonalProcurementSLA)) {
											shipping.setMaxZonalProcurementSLA(Integer
													.parseInt(maxZonalProcurementSLA));
										}
										if (StringUtils
												.isNotBlank(minLocalPocurementSLA)) {
											shipping.setMinLocalProcurementSLA(Integer
													.parseInt(minLocalPocurementSLA));
										}
										if (StringUtils
												.isNotBlank(maxLocalPocurementSLA)) {
											shipping.setMaxLocalProcurementSLA(Integer
													.parseInt(maxLocalPocurementSLA));
										}

										if (StringUtils.isNotBlank(mbgShare)) {
											storeProductPricing
													.setMbgShare(Double
															.parseDouble(mbgShare));
										}

										if (StringUtils.isNotBlank(localRegion)
												&& localRegion
														.equalsIgnoreCase("True")) {
											shipping.setLocalDelivery(true);
										} else {
											shipping.setLocalDelivery(false);
										}

										if (StringUtils.isNotBlank(zonalRegion)
												&& zonalRegion
														.equalsIgnoreCase("True")) {
											shipping.setZonalDelivery(true);
										} else {
											shipping.setZonalDelivery(false);
										}

										if (StringUtils
												.isNotBlank(nationRegion)
												&& nationRegion
														.equalsIgnoreCase("True")) {
											shipping.setNationalDelivery(true);
										} else {
											shipping.setNationalDelivery(false);
										}
										if (StringUtils.isNotBlank(discount)) {
											storeProductPricing
													.setDiscount(Double
															.parseDouble(discount));

											if (StringUtils
													.isNotBlank(discount)) {

												storeProductPricing
														.setDiscount(Double
																.parseDouble(discount));

												double sellingPrice;
												double discountedAmount = (storeProductPricing
														.getMaxRetailPrice() * storeProductPricing
														.getDiscount()) / 100;
												sellingPrice = storeProductPricing
														.getMaxRetailPrice()
														- discountedAmount;

												storeProductPricing
														.setSellingPrice(sellingPrice);

											}
										}
										if (StringUtils
												.isNotBlank(willYouDeliver)
												&& willYouDeliver
														.equalsIgnoreCase("True")) {
											storeProductPricing
													.setStoreDelivery(true);
										} else {
											storeProductPricing
													.setStoreDelivery(false);
										}

										storeProductPricing
												.setShipping(shipping);
										storeProductPricingDAO
												.save(storeProductPricing);
									}
									storeDAO.save(store);
								} else {
									logger.error(
											"StoreProductPricing is null for productId :{}",
											productId);
									throw new Exception(
											"StoreProductPricing is null for productId :"
													+ productId);
								}
							}
						} else {
							logger.error("store should not be null");
							throw new Exception("store should not be null");
						}
					} else {
						logger.error("User Role should be dealer");
						throw new Exception("User Role should be dealer");
					}
				}
			}
		} else {
			logger.error("User is not exist in our system for user id {}"
					+ dealerId);
			throw new Exception("User not exist in our system for user id :"
					+ dealerId);
		}
	}

	@Override
	public void dealerBulkUpdatingStoreProductsInfo(String dealerId,
			String brand, String status, String subCategory,
			String nationalShippingCharge, String zonalShippingCharge,
			String localShippingCharge, String minNationalProcurementSLA,
			String maxNationalProcurementSLA, String minZonalProcurementSLA,
			String maxZonalProcurementSLA, String minLocalPocurementSLA,
			String maxLocalPocurementSLA, String mbgShare, String localRegion,
			String zonalRegion, String nationRegion, String discount,
			String willYouDeliver) throws Exception {
		ProductCustomSearch productCustomSearch = new ProductCustomSearch();

		List<Product> products = new ArrayList<>();

		User dealer = userDAO.findById(dealerId);

		if (dealer != null) {

			if (dealer.getRoles() != null) {
				Set<Role> roles = dealer.getRoles();
				for (Role role : roles) {
					if (role.getName().equalsIgnoreCase("DEALER")) {
						Store store = storeDAO.findByUser(dealer);
						if (store != null) {
							List<Product> productList = store.getProducts();

							Set<String> prodIds = new HashSet<String>();
							if (productList != null && productList.size() != 0) {
								for (Product product : productList) {
									prodIds.add(product.getId());
								}
							} else {
								throw new Exception(
										"Store Doesn't have any products");
							}

							// Brands
							if (StringUtils.isNotBlank(brand)) {
								String[] brandIds = brand.split(",");
								List<String> brands = new ArrayList<String>();
								for (String brandId : brandIds) {
									brands.add(brandId);
								}
								productCustomSearch.setProductBrandIds(brands);
							}

							// Status
							if (StringUtils.isNotBlank(status)) {
								String[] statuses = status.split(",");
								List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
								for (int i = 0; i < statuses.length; i++) {
									productStatuses.add(ProductStatus
											.valueOf(statuses[i]));
								}

								productCustomSearch
										.setProductStatuses(productStatuses);
							}

							// SubCategories
							if (StringUtils.isNotBlank(subCategory)) {
								List<SubCategory> subCategories = new ArrayList<>();
								List<String> subCategoriesName = new ArrayList<>();
								String[] subCategoryList = subCategory
										.split(",");
								for (String subcatNames : subCategoryList) {
									subCategories = subCategoryDAO
											.findByName(subcatNames);
									for (SubCategory SubCategory : subCategories) {
										subCategoriesName.add(SubCategory
												.getName());
									}
								}
								productCustomSearch
										.setSubCategoryNames(subCategoriesName);
							}

							// productIds
							if (prodIds != null) {
								productCustomSearch.setProductIds(prodIds);
							}

							// Required fields
							Set<String> requiredFieldSet = new HashSet<>();
							requiredFieldSet.add("_id");
							requiredFieldSet.add("name");
							requiredFieldSet.add("model");
							requiredFieldSet.add("createdDate");
							requiredFieldSet.add("subcategories");
							requiredFieldSet.add("status");
							requiredFieldSet.add("brand");
							requiredFieldSet.add("price");
							requiredFieldSet.add("consolidatedRating");
							requiredFieldSet.add("desc");
							requiredFieldSet.add("assets");
							productCustomSearch
									.setRequiredFields(requiredFieldSet);

							products = productDAO
									.searchProducts(productCustomSearch);

							for (Product product1 : products) {

								String productId = product1.getId();

								StoreProductPricing storeProductPricing = storeProductPricingDAO
										.findByProductIdAndStoreId(productId,
												store.getId());
								if (storeProductPricing != null) {
									if (storeProductPricing.getProductId()
											.equalsIgnoreCase(productId)) {

										Product product = productDAO
												.findOne(productId);
										storeProductPricing
												.setProductId(productId);
										storeProductPricing
												.setMaxRetailPrice(product
														.getPrice().getMrp());

										Shipping shipping = storeProductPricing
												.getShipping();
										if (StringUtils
												.isNotBlank(nationalShippingCharge)) {
											shipping.setNationalShippingChrg(Double
													.parseDouble(nationalShippingCharge));
										}
										if (StringUtils
												.isNotBlank(zonalShippingCharge)) {
											shipping.setZonalShippingChrg(Double
													.parseDouble(zonalShippingCharge));
										}
										if (StringUtils
												.isNotBlank(localShippingCharge)) {
											shipping.setLocalShippingChrg(Double
													.parseDouble(localShippingCharge));
										}
										if (StringUtils
												.isNotBlank(minNationalProcurementSLA)) {
											shipping.setMinNationalProcurementSLA(Integer
													.parseInt(minNationalProcurementSLA));
										}
										if (StringUtils
												.isNotBlank(maxNationalProcurementSLA)) {
											shipping.setMaxNationalProcurementSLA(Integer
													.parseInt(maxNationalProcurementSLA));
										}
										if (StringUtils
												.isNotBlank(minZonalProcurementSLA)) {
											shipping.setMinZonalProcurementSLA(Integer
													.parseInt(minZonalProcurementSLA));
										}
										if (StringUtils
												.isNotBlank(maxZonalProcurementSLA)) {
											shipping.setMaxZonalProcurementSLA(Integer
													.parseInt(maxZonalProcurementSLA));
										}
										if (StringUtils
												.isNotBlank(minLocalPocurementSLA)) {
											shipping.setMinLocalProcurementSLA(Integer
													.parseInt(minLocalPocurementSLA));
										}
										if (StringUtils
												.isNotBlank(maxLocalPocurementSLA)) {
											shipping.setMaxLocalProcurementSLA(Integer
													.parseInt(maxLocalPocurementSLA));
										}

										if (StringUtils.isNotBlank(mbgShare)) {
											storeProductPricing
													.setMbgShare(Double
															.parseDouble(mbgShare));
										}

										if (StringUtils.isNotBlank(localRegion)
												&& localRegion
														.equalsIgnoreCase("True")) {
											shipping.setLocalDelivery(true);
										} else {
											shipping.setLocalDelivery(false);
										}

										if (StringUtils.isNotBlank(zonalRegion)
												&& zonalRegion
														.equalsIgnoreCase("True")) {
											shipping.setZonalDelivery(true);
										} else {
											shipping.setZonalDelivery(false);
										}

										if (StringUtils
												.isNotBlank(nationRegion)
												&& nationRegion
														.equalsIgnoreCase("True")) {
											shipping.setNationalDelivery(true);
										} else {
											shipping.setNationalDelivery(false);
										}
										if (StringUtils.isNotBlank(discount)) {

											storeProductPricing
													.setDiscount(Double
															.parseDouble(discount));

											double sellingPrice;
											double discountedAmount = (storeProductPricing
													.getMaxRetailPrice() * storeProductPricing
													.getDiscount()) / 100;
											sellingPrice = storeProductPricing
													.getMaxRetailPrice()
													- discountedAmount;

											storeProductPricing
													.setSellingPrice(sellingPrice);

										}
										if (StringUtils
												.isNotBlank(willYouDeliver)
												&& willYouDeliver
														.equalsIgnoreCase("True")) {
											storeProductPricing
													.setStoreDelivery(true);
										} else {
											storeProductPricing
													.setStoreDelivery(false);
										}

										storeProductPricing
												.setShipping(shipping);
										storeProductPricingDAO
												.save(storeProductPricing);
									}
									storeDAO.save(store);
								} else {
									logger.error(
											"StoreProductPricing is null for productId :{}",
											productId);
									throw new Exception(
											"StoreProductPricing is null for productId :"
													+ productId);
								}
							}
						} else {
							logger.error("store should not be null");
							throw new Exception("store should not be null");
						}
					} else {
						logger.error("User Role should be dealer");
						throw new Exception("User Role should be dealer");
					}
				}
			}
		} else {
			logger.error("User is not exist in our system for user id {}"
					+ dealerId);
			throw new Exception("User not exist in our system for user id :"
					+ dealerId);
		}
	}

	@Override
	public DisplaySameProductsResponse displaySameProducts(String productId)
			throws Exception {

		List<Product> products = null;
		Set<Product> productSet = null;
		String searchValue = null;
		ProductCustomSearch productCustomSearch = null;
		DisplaySameProductsResponse displaySameProductsResponse = null;
		Set<SimilarProductVariant> variants = new HashSet<>();

		if (StringUtils.isNotBlank(productId)) {

			Product product = productDAO.findById(productId);

			if (product != null) {

				productSet = new HashSet<Product>();
				productCustomSearch = new ProductCustomSearch();
				displaySameProductsResponse = new DisplaySameProductsResponse();

				String productName = product.getName();
				String[] productNameAray = productName.split("-");
				searchValue = productNameAray[0] + "-" + productNameAray[1]
						+ "-" + productNameAray[2];

				productCustomSearch.setSearchText(searchValue);

				String status = ProductStatus.IN_BUILDONN.toString();
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}

				productCustomSearch.setProductStatuses(productStatuses);

				products = productDAO.searchProducts(productCustomSearch);

				if (products != null && products.size() != 1) {
					productSet.addAll(products);
				}
				for (Product prod : productSet) {
					SimilarProductVariant variant = new SimilarProductVariant();
					String pName = prod.getName();
					String[] pNameAry = pName.split("-");
					variant.setVariant(pNameAry[3]);
					variant.setProductId(prod.getId());
					variants.add(variant);
				}

				displaySameProductsResponse.setProducts(productSet);

				Set<SubCategory> subcategories = product.getSubcategories();

				String subcatName = null;

				for (SubCategory subcat : subcategories) {
					subcatName = subcat.getName();
				}

				Map<String, String> map = VariantConstants
						.getVarientConstantMap();

				String variantName = map.get(subcatName);
				displaySameProductsResponse.setVaryingAttribute(variantName);
				displaySameProductsResponse.setVariants(variants);
			}

		}

		return displaySameProductsResponse;
	}

	@Override
	public AdminManageRequestQuoteResponse getAdminManageRequestQuote(
			String searchValue, String status, int page, int size, Sort sort) {
		AdminManageRequestQuoteResponse adminManageRequestQuoteResponse = new AdminManageRequestQuoteResponse();
		Page<QuoteRequest> pageable = null;
		Set<User> customers = null;
		List<QuoteRequest> quoteRequests = new ArrayList<>();
		if (StringUtils.isNotBlank(searchValue)) {
			customers = userDAO
					.findByUsernameLikeOrUserPhoneNumberLikeOrFirstNameLikeOrLastNameLikeOrFullNameLikeAllIgnoreCase(
							searchValue, searchValue, searchValue, searchValue,
							searchValue);
		}

		if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isNotBlank(status) && customers.size() != 0) {
			for (User customer : customers) {
				pageable = quoteRequestDAO.findByCustomer_IdAndStatus(
						customer.getId(), QuoteStatus.valueOf(status),
						new PageRequest(page, size, sort));

				if (pageable.getContent() != null) {
					quoteRequests.addAll(pageable.getContent());
				}
			}
		} else if (StringUtils.isNotBlank(searchValue) && customers.size() != 0
				&& StringUtils.isBlank(status)) {
			for (User customer : customers) {
				pageable = quoteRequestDAO.findByCustomer_Id(customer.getId(),
						new PageRequest(page, size, sort));

				if (pageable.getContent() != null) {
					quoteRequests.addAll(pageable.getContent());
				}
			}
		} else if (StringUtils.isNotBlank(searchValue)) {
			pageable = quoteRequestDAO
					.findByBuildOnnQuoteRequestIdLikeAllIgnoreCaseOrZipcodeOrFollowupDateLike(
							searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			if (pageable.getContent() != null) {
				quoteRequests.addAll(pageable.getContent());
			}
		} else if (StringUtils.isNotBlank(status)
				&& StringUtils.isBlank(searchValue)) {
			pageable = quoteRequestDAO.findByStatus(
					QuoteStatus.valueOf(status), new PageRequest(page, size,
							sort));
			if (pageable.getContent() != null) {
				quoteRequests.addAll(pageable.getContent());
			}
		} else {
			pageable = quoteRequestDAO
					.findAll(new PageRequest(page, size, sort));
			if (pageable.getContent() != null) {
				quoteRequests.addAll(pageable.getContent());
			}
		}

		List<AdminManageRequestQuote> requestQuotes = new ArrayList<>();
		if (quoteRequests.size() != 0) {
			for (QuoteRequest quoteRequest : quoteRequests) {
				AdminManageRequestQuote adminManageRequestQuote = new AdminManageRequestQuote();
				// QuoteRequestId
				if (quoteRequest.getId() != null) {
					adminManageRequestQuote.setQuoteRequestId(quoteRequest
							.getId());
				}
				// BuildOnnQuoteRequestId
				if (quoteRequest.getBuildOnnQuoteRequestId() != null) {
					adminManageRequestQuote
							.setBuildOnnQuoteRequestId(quoteRequest
									.getBuildOnnQuoteRequestId());
				}
				// Zipcode
				if (quoteRequest.getZipcode() != null) {
					adminManageRequestQuote.setZipcode(quoteRequest
							.getZipcode());
				}
				// Note
				if (quoteRequest.getNote() != null) {
					adminManageRequestQuote.setNote(quoteRequest.getNote());
				}

				// Followup date
				if (quoteRequest.getFollowupDate() != null) {
					adminManageRequestQuote.setFollowupDate(quoteRequest
							.getFollowupDate());
				}
				if (quoteRequest.getCustomer() != null) {
					// CustomerEmail
					if (quoteRequest.getCustomer().getUsername() != null) {
						adminManageRequestQuote.setCustomerEmail(quoteRequest
								.getCustomer().getUsername());
					}

					// CustomerContactNumber
					if (quoteRequest.getCustomer().getUserPhoneNumber() != null) {
						adminManageRequestQuote
								.setCustomerContactNumber(quoteRequest
										.getCustomer().getUserPhoneNumber());
					}
					String customerName = null;
					// CustomerName
					if (quoteRequest.getCustomer().getFirstName() != null
							&& quoteRequest.getCustomer().getLastName() != null) {
						customerName = quoteRequest.getCustomer()
								.getFirstName()
								+ " "
								+ quoteRequest.getCustomer().getLastName();
					} else if (quoteRequest.getCustomer().getFirstName() != null) {
						customerName = quoteRequest.getCustomer()
								.getFirstName();
					}
					adminManageRequestQuote.setCustomerName(customerName);
					// RoleName
					Set<Role> roles = quoteRequest.getCustomer().getRoles();
					for (Role role : roles) {
						adminManageRequestQuote.setRoleName(role.getName());
					}
				}

				// DeliveryDate
				if (quoteRequest.getDeliveryDate() != null) {
					adminManageRequestQuote.setDeliveryDate(quoteRequest
							.getDeliveryDate());
				}
				// ProductsName and sellersName
				Set<String> productsName = new HashSet<>();
				Set<String> sellersName = new HashSet<>();
				if (quoteRequest.getProducts() != null) {
					List<QuoteRequestProduct> products = quoteRequest
							.getProducts();
					for (QuoteRequestProduct product : products) {
						if (product != null) {
							if (product.getProductName() != null) {
								productsName.add(product.getProductName());
								if (product.getQuoteProductPricings() != null
										&& product.getQuoteProductPricings()
												.size() != 0) {

									Set<SellerQuoteProductPricing> productPricings = product
											.getQuoteProductPricings();

									for (SellerQuoteProductPricing productPricing : productPricings) {
										if (productPricing != null) {
											if (productPricing.getSeller() != null) {
												if (productPricing.getSeller()
														.getFirstName() != null) {
													sellersName
															.add(productPricing
																	.getSeller()
																	.getFirstName());
												}
											}
										}
									}
								}
							}
						}
					}
				}
				adminManageRequestQuote.setProductsName(productsName);
				adminManageRequestQuote.setSellersName(sellersName);
				if (quoteRequest.getCreatedDate() != null) {
					adminManageRequestQuote.setCreatedDate(quoteRequest
							.getCreatedDate());
				}
				if (quoteRequest.getStatus() != null) {
					adminManageRequestQuote.setStatus(quoteRequest.getStatus()
							.toString());
				}
				requestQuotes.add(adminManageRequestQuote);
			}

			adminManageRequestQuoteResponse.setRequestQuotes(requestQuotes);

			adminManageRequestQuoteResponse.setTotalPages(pageable
					.getTotalPages());
			adminManageRequestQuoteResponse.setTotalElements(pageable
					.getTotalElements());
			adminManageRequestQuoteResponse.setNumber(pageable.getNumber());
			adminManageRequestQuoteResponse.setSize(pageable.getSize());
		}
		return adminManageRequestQuoteResponse;
	}

	@Override
	public AdminManageRequestQuoteResponse getDealerManageRequestQuote(
			String searchValue, String dealerId, int page, int size, Sort sort) {
		AdminManageRequestQuoteResponse adminManageRequestQuoteResponse = new AdminManageRequestQuoteResponse();

		if (StringUtils.isNotBlank(dealerId)) {

			User dealer = userDAO.findById(dealerId);

			if (dealer != null) {

				Set<SellerQuoteProductPricing> sellerQuoteProductPricings = sellerQuoteProductPricingDAO
						.findBySeller_Id(dealerId);

				Set<QuoteRequestProduct> quoteRequestProducts = quoteRequestProductDAO
						.findByQuoteProductPricingsIn(sellerQuoteProductPricings);

				Page<QuoteRequest> pageable = null;
				if (StringUtils.isNotBlank(searchValue)) {

					pageable = quoteRequestDAO.findByProductsInAndZipcode(
							quoteRequestProducts, searchValue, new PageRequest(
									page, size, sort));

					if (pageable.getContent().size() == 0) {
						pageable = quoteRequestDAO
								.findByProductsInAndBuildOnnQuoteRequestIdLike(
										quoteRequestProducts, searchValue,
										new PageRequest(page, size, sort));
					}
					if (pageable.getContent().size() == 0) {
						pageable = quoteRequestDAO
								.findByProductsInAndFollowupDateLike(
										quoteRequestProducts, searchValue,
										new PageRequest(page, size, sort));
					}
				} else {
					pageable = quoteRequestDAO.findByProductsIn(
							quoteRequestProducts, new PageRequest(page, size,
									sort));
				}

				List<QuoteRequest> quoteRequests = new ArrayList<>();
				quoteRequests.addAll(pageable.getContent());

				List<AdminManageRequestQuote> requestQuotes = new ArrayList<>();
				for (QuoteRequest quoteRequest : quoteRequests) {
					AdminManageRequestQuote adminManageRequestQuote = new AdminManageRequestQuote();
					// QuoteRequestId
					if (quoteRequest.getId() != null) {
						adminManageRequestQuote.setQuoteRequestId(quoteRequest
								.getId());
					}
					// BuildOnnQuoteRequestId
					if (quoteRequest.getBuildOnnQuoteRequestId() != null) {
						adminManageRequestQuote
								.setBuildOnnQuoteRequestId(quoteRequest
										.getBuildOnnQuoteRequestId());
					}
					// Zipcode
					if (quoteRequest.getZipcode() != null) {
						adminManageRequestQuote.setZipcode(quoteRequest
								.getZipcode());
					}
					// Note
					if (quoteRequest.getNote() != null) {
						adminManageRequestQuote.setNote(quoteRequest.getNote());
					}
					// Followup date
					if (quoteRequest.getFollowupDate() != null) {
						adminManageRequestQuote.setFollowupDate(quoteRequest
								.getFollowupDate());
					}
					// CustomerEmail
					if (quoteRequest.getCustomer() != null) {
						if (quoteRequest.getCustomer().getUsername() != null) {
							adminManageRequestQuote
									.setCustomerEmail(quoteRequest
											.getCustomer().getUsername());
						}
						// CustomerContactNumber
						if (quoteRequest.getCustomer().getUserPhoneNumber() != null) {
							adminManageRequestQuote
									.setCustomerContactNumber(quoteRequest
											.getCustomer().getUserPhoneNumber());
						}
					}
					// DeliveryDate
					if (quoteRequest.getDeliveryDate() != null) {
						adminManageRequestQuote.setDeliveryDate(quoteRequest
								.getDeliveryDate());
					}

					// ProductsName and sellersName
					Set<String> productsName = new HashSet<>();
					if (quoteRequest.getProducts() != null
							&& quoteRequest.getProducts().size() != 0) {
						List<QuoteRequestProduct> products = quoteRequest
								.getProducts();
						for (QuoteRequestProduct product : products) {
							if (product != null) {
								if (product.getQuoteProductPricings() != null
										&& product.getQuoteProductPricings()
												.size() != 0) {
									Set<SellerQuoteProductPricing> quoteProductPricings = product
											.getQuoteProductPricings();

									for (SellerQuoteProductPricing sellerQuoteProductPricing : quoteProductPricings) {
										if (sellerQuoteProductPricing != null) {
											if (product.getProductName() != null
													&& sellerQuoteProductPricing
															.getSeller()
															.getId()
															.equalsIgnoreCase(
																	dealerId)) {
												if (product.getProductName() != null) {
													productsName.add(product
															.getProductName());
												}
											}
										}
									}
								}
							}
						}
					}
					adminManageRequestQuote.setProductsName(productsName);
					if (quoteRequest.getCreatedDate() != null) {
						adminManageRequestQuote.setCreatedDate(quoteRequest
								.getCreatedDate());
					}
					requestQuotes.add(adminManageRequestQuote);
				}

				adminManageRequestQuoteResponse.setRequestQuotes(requestQuotes);

				adminManageRequestQuoteResponse.setTotalPages(pageable
						.getTotalPages());
				adminManageRequestQuoteResponse.setTotalElements(pageable
						.getTotalElements());
				adminManageRequestQuoteResponse.setNumber(pageable.getNumber());
				adminManageRequestQuoteResponse.setSize(pageable.getSize());
			}
		}
		return adminManageRequestQuoteResponse;
	}

	@Override
	public AdminManageRequestQuoteResponse getCustomerManageRequestQuote(
			String searchValue, String customerId, int page, int size, Sort sort) {

		AdminManageRequestQuoteResponse adminManageRequestQuoteResponse = new AdminManageRequestQuoteResponse();

		if (StringUtils.isNotBlank(customerId)) {

			User customer = userDAO.findById(customerId);

			if (customer != null) {
				Page<QuoteRequest> pageable = null;
				if (StringUtils.isNotBlank(searchValue)) {

					pageable = quoteRequestDAO.findByCustomerAndZipcode(
							customer, searchValue, new PageRequest(page, size,
									sort));
					if (pageable.getContent().size() == 0) {
						pageable = quoteRequestDAO
								.findByCustomerAndBuildOnnQuoteRequestIdLikeAllIgnoreCase(
										customer, searchValue, new PageRequest(
												page, size, sort));
					}
					if (pageable.getContent().size() == 0) {
						pageable = quoteRequestDAO
								.findByCustomerAndFollowupDateLike(customer,
										searchValue, new PageRequest(page,
												size, sort));
					}
				} else {
					pageable = quoteRequestDAO.findByCustomer(customer,
							new PageRequest(page, size, sort));
				}

				List<QuoteRequest> quoteRequests = new ArrayList<>();

				if (pageable.getContent() != null) {
					quoteRequests.addAll(pageable.getContent());
				}

				List<AdminManageRequestQuote> requestQuotes = new ArrayList<>();
				if (quoteRequests.size() != 0) {
					for (QuoteRequest quoteRequest : quoteRequests) {
						AdminManageRequestQuote adminManageRequestQuote = new AdminManageRequestQuote();
						// QuoteRequestId
						if (quoteRequest.getId() != null) {
							adminManageRequestQuote
									.setQuoteRequestId(quoteRequest.getId());
						}
						// BuildOnnQuoteRequestId
						if (quoteRequest.getBuildOnnQuoteRequestId() != null) {
							adminManageRequestQuote
									.setBuildOnnQuoteRequestId(quoteRequest
											.getBuildOnnQuoteRequestId());
						}
						// Zipcode
						if (quoteRequest.getZipcode() != null) {
							adminManageRequestQuote.setZipcode(quoteRequest
									.getZipcode());
						}
						// Note
						if (quoteRequest.getNote() != null) {
							adminManageRequestQuote.setNote(quoteRequest
									.getNote());
						}
						// Followup date
						if (quoteRequest.getFollowupDate() != null) {
							adminManageRequestQuote
									.setFollowupDate(quoteRequest
											.getFollowupDate());
						}
						if (quoteRequest.getCustomer() != null) {
							// CustomerEmail
							if (quoteRequest.getCustomer().getUsername() != null) {
								adminManageRequestQuote
										.setCustomerEmail(quoteRequest
												.getCustomer().getUsername());
							}
							// CustomerContactNumber
							if (quoteRequest.getCustomer().getUserPhoneNumber() != null) {
								adminManageRequestQuote
										.setCustomerContactNumber(quoteRequest
												.getCustomer()
												.getUserPhoneNumber());
							}
						}
						// DeliveryDate
						if (quoteRequest.getDeliveryDate() != null) {
							adminManageRequestQuote
									.setDeliveryDate(quoteRequest
											.getDeliveryDate());
						}

						// ProductsName and sellersName
						Set<String> productsName = new HashSet<>();
						Set<String> sellersName = new HashSet<>();

						if (quoteRequest.getProducts() != null
								&& quoteRequest.getProducts().size() != 0) {
							List<QuoteRequestProduct> products = quoteRequest
									.getProducts();
							for (QuoteRequestProduct product : products) {
								if (product != null) {
									if (product.getProductName() != null) {
										productsName.add(product
												.getProductName());
									}

									if (product.getQuoteProductPricings() != null
											&& product
													.getQuoteProductPricings()
													.size() != 0) {
										Set<SellerQuoteProductPricing> productPricings = product
												.getQuoteProductPricings();

										for (SellerQuoteProductPricing productPricing : productPricings) {
											if (productPricing != null) {
												if (productPricing.getSeller() != null) {
													if (productPricing
															.getSeller()
															.getFirstName() != null) {
														sellersName
																.add(productPricing
																		.getSeller()
																		.getFirstName());
													}
												}
											}
										}
									}
								}
							}
						}
						adminManageRequestQuote.setProductsName(productsName);
						adminManageRequestQuote.setSellersName(sellersName);
						if (quoteRequest.getCreatedDate() != null) {
							adminManageRequestQuote.setCreatedDate(quoteRequest
									.getCreatedDate());
						}
						if (quoteRequest.getStatus() != null) {
							adminManageRequestQuote.setStatus(quoteRequest
									.getStatus().toString());
						}
						requestQuotes.add(adminManageRequestQuote);
					}

					adminManageRequestQuoteResponse
							.setRequestQuotes(requestQuotes);

					adminManageRequestQuoteResponse.setTotalPages(pageable
							.getTotalPages());
					adminManageRequestQuoteResponse.setTotalElements(pageable
							.getTotalElements());
					adminManageRequestQuoteResponse.setNumber(pageable
							.getNumber());
					adminManageRequestQuoteResponse.setSize(pageable.getSize());
				}
			}
		}
		return adminManageRequestQuoteResponse;
	}

	@Override
	public void productsStatusBulkUpdate(String subCategory, String brand,
			String status, Set<String> productIds, String newStatus)
			throws Exception {
		if (StringUtils.isNotBlank(subCategory)
				|| StringUtils.isNotBlank(brand)
				|| StringUtils.isNotBlank(status) || productIds.size() != 0) {
			ProductCustomSearch productCustomSearch = new ProductCustomSearch();
			// Brands
			if (StringUtils.isNotBlank(brand)) {
				String[] brandIds = brand.split(",");
				List<String> brands = new ArrayList<String>();
				for (String brandId : brandIds) {
					brands.add(brandId);
				}
				productCustomSearch.setProductBrandIds(brands);
			}

			// Status
			if (StringUtils.isNotBlank(status)) {
				String[] statuses = status.split(",");
				List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
				for (int i = 0; i < statuses.length; i++) {
					productStatuses.add(ProductStatus.valueOf(statuses[i]));
				}

				productCustomSearch.setProductStatuses(productStatuses);
			}

			// SubCategories
			if (StringUtils.isNotBlank(subCategory)) {
				List<SubCategory> subCategories = new ArrayList<>();
				List<String> subCategoriesName = new ArrayList<>();
				String[] subCategoryList = subCategory.split(",");
				for (String subcatNames : subCategoryList) {
					subCategories = subCategoryDAO.findByName(subcatNames);
					for (SubCategory SubCategory : subCategories) {
						subCategoriesName.add(SubCategory.getName());
					}
				}
				productCustomSearch.setSubCategoryNames(subCategoriesName);
			}
			// ProductIds
			if (productIds != null && productIds.size() != 0) {
				productCustomSearch.setProductIds(productIds);
			}

			List<Product> products = productDAO
					.searchProducts(productCustomSearch);

			for (Product product : products) {
				if (newStatus.equalsIgnoreCase(product.getStatus().name())) {
					logger.info("product " + product.getSkuId()
							+ " already in " + newStatus + " status");
				} else {
					logger.info("product " + product.getSkuId()
							+ " its status is changed to" + newStatus
							+ " status!Success");
					product.setStatus(ProductStatus.valueOf(newStatus));
				}
				productDAO.save(product);
			}
		} else {
			logger.error("At least one of these field should be required: Subcategory, Brand, Status, ProductIds");
			throw new Exception(
					"At least one of these field should be required: Subcategory, Brand, Status, ProductIds");
		}
	}

	@Override
	public void uploadRequestQuoteOrderImage(String userId, String productId,
			String customerName, String mobile, String email, String zipcode,
			String itemQuantity, String paymentMode, String creditDays,
			String deliveryTime, String textInfo, String brand,
			String changeAttribute, int page, int size, Sort sort)
			throws Exception {
		User admin = userDAO.findByRoles_NameIsIgnoreCase("SUPERADMIN");
		List<ItemInfoForCommunication> itemInfoList = new ArrayList<>();
//		if (StringUtils.isNotBlank(productId) && StringUtils.isNotBlank(userId)) {
//			String[] prodIdAry = productId.split(",");
//			String[] qtyAry = itemQuantity.split(",");

//			User mongoCustomer = userDAO.findById(userId);

//			QuoteRequest quoteRequest = new QuoteRequest();
//			// customer
//			quoteRequest.setCustomer(mongoCustomer);
//			// zipcode
//			if (StringUtils.isNotBlank(zipcode)) {
//				quoteRequest.setZipcode(zipcode);
//			}
//			// buildOnn Quote requestId
//			Page<QuoteRequest> pageable = quoteRequestDAO
//					.findAll(new PageRequest(0, 20, sort));
//			List<QuoteRequest> qrList = pageable.getContent();
//			if (qrList != null && qrList.size() > 0) {
//				QuoteRequest qr = qrList.get(0);
//				if (qr != null) {
//					String bOnnQRId = qr.getBuildOnnQuoteRequestId();
//					String[] bOnnQRIdAry = bOnnQRId.split("BNN");
//					int bOnnQRIdInt = Integer.parseInt(bOnnQRIdAry[1]);
//					bOnnQRIdInt++;
//					quoteRequest.setBuildOnnQuoteRequestId("BNN" + bOnnQRIdInt);
//				}
//			}
//			// status
//			quoteRequest.setStatus(QuoteStatus.CREATED);
//			// delivery date
//			if (StringUtils.isNotBlank(deliveryTime)) {
//				quoteRequest.setDeliveryDate(deliveryTime);
//			}
//			// note
//			if (StringUtils.isNotBlank(textInfo)) {
//				quoteRequest.setNote(textInfo);
//			}
//			// quote selling price including tax
//			quoteRequest.setQuoteSellingPriceIncludeTax(false);
//
//			List<QuoteRequestProduct> quoteRequestProducts = new ArrayList<>();

//			int i = 0;
//			for (String prodId : prodIdAry) {
//				Product product = productDAO.findOne(prodId);
//
//				ItemInfoForCommunication itemInfo = new ItemInfoForCommunication();
//				if (product != null) {
//					itemInfo.setItemId(product.getId());
//					if (product.getName() != null) {
//						itemInfo.setItemName(product.getName());
//					}
//
//					if (product.getAssets() != null) {
//						if (product.getAssets().getImages().get(0).getUrl() != null) {
//							itemInfo.setItemImage(product.getAssets()
//									.getImages().get(0).getUrl());
//						}
//					}
//
//					itemInfo.setItemQuantity(Integer.parseInt(qtyAry[i]));
//					if (product.getQuantityType() != null) {
//						itemInfo.setItemQuantityType(product.getQuantityType()
//								.getName());
//					}
//
//					QuoteRequestProduct quoteRequestProduct = new QuoteRequestProduct();
//					if (product.getName() != null) {
//						quoteRequestProduct.setProductName(product.getName());
//					}
//					quoteRequestProduct.setId(product.getId());
//
//					if (product.getBrand() != null) {
//						quoteRequestProduct.setBrand(product.getBrand()
//								.getName());
//					}
//					if (product.getQuantityType() != null) {
//						quoteRequestProduct.setQuantityType(product
//								.getQuantityType().getName());
//					}
//					quoteRequestProduct
//							.setQuantity(Integer.parseInt(qtyAry[i]));
//					// SellerQuoteProductPricing
//					SellerQuoteProductPricing sellerQuoteProductPricing = new SellerQuoteProductPricing();
//					User seller = userDAO.findById("586127d9e4b0780ae894e770");
//					if (seller != null) {
//						sellerQuoteProductPricing.setSeller(seller);
//						sellerQuoteProductPricing.setMrp(0.0);
//						sellerQuoteProductPricing.setSellingPrice(0.0);
//						sellerQuoteProductPricing.setBuildOnnSellingPrice(0.0);
//						sellerQuoteProductPricing.setShippingCharge(0.0);
//						sellerQuoteProductPricing.setDiscount(0.0);
//						sellerQuoteProductPricing.setTax(0.0);
//						sellerQuoteProductPricing.setExtraBenefits(0.0);
//						sellerQuoteProductPricing.setShared(false);
//
//						sellerQuoteProductPricingDAO
//								.save(sellerQuoteProductPricing);
//
//						Set<SellerQuoteProductPricing> sellerQuoteProductPricingSet = new HashSet<>();
//
//						sellerQuoteProductPricingSet
//								.add(sellerQuoteProductPricing);
//
//						quoteRequestProduct
//								.setQuoteProductPricings(sellerQuoteProductPricingSet);
//					}
//					quoteRequestProductDAO.save(quoteRequestProduct);
//					quoteRequestProducts.add(quoteRequestProduct);
//				} else {
//					throw new Exception(
//							"This product is not exist in our database");
//				}
//				i++;
//				itemInfoList.add(itemInfo);
//			}

//			quoteRequest.setProducts(quoteRequestProducts);
//			quoteRequestDAO.save(quoteRequest);
//
//			if (StringUtils.isBlank(creditDays)) {
//				creditDays = "Not Applicable";
//			}
//
//			if (StringUtils.isBlank(deliveryTime)) {
//				deliveryTime = "Not Applicable";
//			}

//			UserInfo customer = userRepository.findByMongoUserId(userId);
//
//			String note = "Login UserId: " + userId
//					+ " Given Details: CustomerName:" + customerName
//					+ " Email:" + email + " MobileNumber:" + mobile
//					+ " Zipcode:" + zipcode;

//			if (customer != null) {
//				updateUserAudit(customer, "QUOTEUSERINFO", note);
//			}

//			// Send Quote Request details sms to Admin
//			userCommunicationService.sendQuoteRequestInfoToAdmin(admin,
//					mongoCustomer, quoteRequest.getBuildOnnQuoteRequestId(),
//					zipcode, deliveryTime, itemInfoList);

//			// Sending request quote confirmation to customer
//			userCommunicationService.sendQuoteConfirmation(customerName, email,
//					mobile, quoteRequest.getBuildOnnQuoteRequestId());

			// Sending request quote email to admin
			userCommunicationService.sendRequestQuoteInfo(admin, customerName,
					mobile, email, zipcode, paymentMode, textInfo, creditDays,
					deliveryTime, itemInfoList, brand, changeAttribute);
		
	}

	@Override
	public boolean verifyBuildonnQuoteRequestId(String quoteRequestId) {
		boolean verify = false;
		if (StringUtils.isNotBlank(quoteRequestId)) {
			QuoteRequest quoteRequest = quoteRequestDAO
					.findByBuildOnnQuoteRequestId(quoteRequestId);
			if (quoteRequest != null) {
				verify = true;
			}
		}
		return verify;
	}

	private void updateUserAudit(UserInfo userInfo, String userAuditTypeVal,
			String note) {
		if (userInfo != null) {
			UserAudit userAudit = new UserAudit();
			// user id
			userAudit.setUserId(userInfo.getUserid());
			// user audit type
			UserAuditType userAuditType = userAuditTypeRepository
					.findByValue(userAuditTypeVal);
			userAudit.setUserAuditType(userAuditType);
			// note
			userAudit.setNote(note);
			// created by
			userAudit.setCreatedBy("Buildonn");
			// last modified by
			userAudit.setModifiedBy("Buildonn");
			// created date
			userAudit.setCreatedTime(new Date());
			// last modified date
			userAudit.setLastModifiedTime(new Date());

			userAuditRepository.save(userAudit);
		}
	}

	@Override
	public ProductPricing createProductPricing(String productId,
			Set<ProductPrice> productPrices, String note) throws Exception {
		ProductPricing productPricing = null;
		if (StringUtils.isNotBlank(productId)) {
			Product product = productDAO.findById(productId);
			if (product != null) {
				ProductPricing pPricing = productPricingDAO
						.findByProduct_Id(productId);
				if (pPricing != null) {
					throw new Exception(
							"Product Price List already exist for productId: "
									+ product.getId());
				}
				productPricing = new ProductPricing();
				productPricing.setProduct(product);

				if (productPrices != null) {
					productPricing.setProductPrices(productPrices);
				}
				if (StringUtils.isNotBlank(note)) {
					productPricing.setNote(note);
				}
				productPricingDAO.save(productPricing);
			} else {
				throw new Exception("Product is null for product Id: "
						+ productId);
			}
		} else {
			throw new Exception("Product Id should not be null");
		}
		return productPricing;
	}

	@Override
	public ProductPricingSummaryViewResponse getProductPricingList(
			String searchText, String categoryNames, String subCategory,
			String productsType, String brand, String pricing, String rating,
			String status, String zipcode, String city, String key,
			String value, int page, int size, Sort sort) {

		ProductCustomSearch productCustomSearch = new ProductCustomSearch();
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		ProductPricingSummaryViewResponse productPricingSummaryViewResponse = new ProductPricingSummaryViewResponse();
		List<ProductPricing> productPrices = new ArrayList<>();
		List<ProductPricingSummaryView> productPricingSummaryViewList = new ArrayList<>();

		// Categories
		if (StringUtils.isNotBlank(categoryNames)) {
			List<Category> categories = new ArrayList<Category>();
			String[] categoryList = categoryNames.split(",");
			for (String catNames : categoryList) {
				categories = categoryDAO.findByNameAllIgnoreCase(catNames);
			}
			productCustomSearch.setCategories(categories.stream()
					.map(c -> c.getName()).collect(Collectors.toList()));
		}

		// SubCategories
		if (StringUtils.isNotBlank(subCategory)) {
			List<SubCategory> subCategories = new ArrayList<>();
			List<String> subCategoriesName = new ArrayList<>();
			String[] subCategoryList = subCategory.split(",");
			for (String subcatNames : subCategoryList) {
				subCategories = subCategoryDAO.findByName(subcatNames);
				for (SubCategory subCategory1 : subCategories) {
					subCategoriesName.add(subCategory1.getName());
				}
			}
			productCustomSearch.setSubCategoryNames(subCategoriesName);
		}

		// Product type
		if (StringUtils.isNotBlank(productsType)) {
			String[] productTypes = productsType.split(",");
			List<String> productTypeNames = new ArrayList<>();
			for (String productType : productTypes) {
				productTypeNames.add(productType);
			}
			productCustomSearch.setProductTypeNames(productTypeNames);
		}

		// Brands
		if (StringUtils.isNotBlank(brand)) {
			String[] brandIds = brand.split(",");
			List<String> brands = new ArrayList<String>();
			for (String brandId : brandIds) {
				brands.add(brandId);
			}
			productCustomSearch.setProductBrandIds(brands);
		}

		// Pricing
		if (StringUtils.isNotBlank(pricing)) {
			String[] maxminValue = pricing.split(",");
			if (maxminValue.length == 2) {
				double max = Double.parseDouble(maxminValue[0]) + 1;
				double min = Double.parseDouble(maxminValue[1]) - 1;
				productCustomSearch.setMinPrice(min);
				productCustomSearch.setMaxPrice(max);
			}
		}

		// Rating
		if (StringUtils.isNotBlank(rating)) {
			String[] maxminValue = rating.split(",");
			if (maxminValue.length == 2) {
				double max = Double.parseDouble(maxminValue[0]) + .1;
				double min = Double.parseDouble(maxminValue[1]) - .1;
				productCustomSearch.setMinRating(min);
				productCustomSearch.setMaxRating(max);
			}
		}

		// Search Value
		if (StringUtils.isNotBlank(searchText)) {
			SearchKeywordStore searchKeywordStore = new SearchKeywordStore();
			searchKeywordStore.setKeyword(searchText);
			searchKeywordStoreDAO.save(searchKeywordStore);
			List<Category> categories = new ArrayList<Category>();
			categories = categoryDAO.findByNameAllIgnoreCase(searchText);
			if (categories != null && categories.size() != 0) {
				productCustomSearch.setCategories(categories.stream()
						.map(c -> c.getName()).collect(Collectors.toList()));
			} else {
				productCustomSearch.setSearchText(searchText);
			}
		}

		// Status
		if (StringUtils.isNotBlank(status)) {
			String[] statuses = status.split(",");
			List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
			for (int i = 0; i < statuses.length; i++) {
				productStatuses.add(ProductStatus.valueOf(statuses[i]));
			}

			productCustomSearch.setProductStatuses(productStatuses);
		}
		// else {
		// status = ProductStatus.IN_BUILDONN.toString();
		// String[] statuses = status.split(",");
		// List<ProductStatus> productStatuses = new ArrayList<ProductStatus>();
		// for (int i = 0; i < statuses.length; i++) {
		// productStatuses.add(ProductStatus.valueOf(statuses[i]));
		// }
		//
		// productCustomSearch.setProductStatuses(productStatuses);
		// }

		// Attribute
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
			if (map.containsKey(key)) {
				List<String> values = map.get(key);
				String[] valueList = value.split(",");
				for (int i = 0; i < valueList.length; i++) {
					values.add(valueList[i]);
				}
				map.put(key, values);
			} else {
				List<String> values = new ArrayList<>();
				String[] valueList = value.split(",");
				for (int i = 0; i < valueList.length; i++) {
					values.add(valueList[i]);
				}
				map.put(key, values);
			}

			productCustomSearch.setAttributeSearchMap(map);
		}

		// Required fields
		Set<String> requiredFieldSet = new HashSet<>();
		requiredFieldSet.add("_id");
		productCustomSearch.setRequiredFields(requiredFieldSet);

		List<Product> products = productDAO.searchProducts(productCustomSearch);

		Page<ProductPricing> pageable = productPricingDAO.findByProductIn(
				products, new PageRequest(page, size, sort));

		productPrices.addAll(pageable.getContent());
		if (productPrices != null && productPrices.size() != 0) {
			for (ProductPricing productPricing : productPrices) {
				if (productPricing != null
						&& productPricing.getProduct() != null) {

					ProductPricingSummaryView productPricingSummaryView = new ProductPricingSummaryView();
					// ProductPricingId
					productPricingSummaryView
							.setProductPricingId(productPricing.getId());
					// ProductId
					productPricingSummaryView.setProductId(productPricing
							.getProduct().getId());
					// ProductName
					productPricingSummaryView.setProductName(productPricing
							.getProduct().getName());
					// productPrices
					productPricingSummaryView.setProductPrices(productPricing
							.getProductPrices());

					productPricingSummaryViewList
							.add(productPricingSummaryView);
				}
			}

		}
		productPricingSummaryViewResponse
				.setProductPrices(productPricingSummaryViewList);
		productPricingSummaryViewResponse.setTotalPages(pageable
				.getTotalPages());
		productPricingSummaryViewResponse.setTotalElements(pageable
				.getTotalElements());
		productPricingSummaryViewResponse.setNumber(pageable.getNumber());
		productPricingSummaryViewResponse.setSize(pageable.getSize());
		return productPricingSummaryViewResponse;
	}

	@Override
	public ProductPricingSummaryView searchProductPricingById(
			String productPricingId) throws Exception {

		ProductPricingSummaryView ProductPricingSummaryView = null;
		Set<String> subCatNames = new HashSet<>();
		Set<String> categoryNames = new HashSet<>();
		if (StringUtils.isNotBlank(productPricingId)) {
			ProductPricing productPricing = productPricingDAO
					.findById(productPricingId);
			if (productPricing != null && productPricing.getProduct() != null) {
				ProductPricingSummaryView = new ProductPricingSummaryView();
				// productPricingId
				if (productPricing.getId() != null) {
					ProductPricingSummaryView
							.setProductPricingId(productPricing.getId());
				}
				// productId
				if (productPricing.getProduct().getId() != null) {
					ProductPricingSummaryView.setProductId(productPricing
							.getProduct().getId());
				}
				// skuId
				if (productPricing.getProduct().getSkuId() != null) {
					ProductPricingSummaryView.setSkuId(productPricing
							.getProduct().getSkuId());
				}
				// productName
				if (productPricing.getProduct().getName() != null) {
					ProductPricingSummaryView.setProductName(productPricing
							.getProduct().getName());
				}
				// category and subCategory
				if (productPricing.getProduct().getSubcategories() != null
						&& productPricing.getProduct().getSubcategories()
								.size() != 0) {
					Set<SubCategory> subCategories = productPricing
							.getProduct().getSubcategories();
					for (SubCategory subCat : subCategories) {
						if (subCat != null) {
							subCatNames.add(subCat.getName());
							categoryNames.add(subCat.getCategory());
						}
					}
					ProductPricingSummaryView.setCategory(categoryNames);
					ProductPricingSummaryView.setSubCategory(subCatNames);
				}
				// brand
				if (productPricing.getProduct().getBrand() != null) {
					ProductPricingSummaryView.setBrand(productPricing
							.getProduct().getBrand().getName());
				}
				// productPrices
				if (productPricing.getProductPrices() != null) {
					ProductPricingSummaryView.setProductPrices(productPricing
							.getProductPrices());
				}
			} else {
				throw new Exception("Product Pricing is null for Id: "
						+ productPricingId);
			}
		} else {
			throw new Exception("ProductPricing Id should not be null");
		}
		return ProductPricingSummaryView;
	}

	@Override
	public ProductPricing updateProductPricing(String productPricingId,
			Set<ProductPrice> productPrices, String note) throws Exception {
		ProductPricing productPricing = null;
		if (StringUtils.isNotBlank(productPricingId)) {
			productPricing = productPricingDAO.findById(productPricingId);
			if (productPricing != null) {

				if (productPrices != null) {
					productPricing.setProductPrices(productPrices);
				}
				if (StringUtils.isNotBlank(note)) {
					productPricing.setNote(note);
				}
				productPricingDAO.save(productPricing);
			} else {
				throw new Exception("Product Pricing is null for Id: "
						+ productPricingId);
			}
		} else {
			throw new Exception("ProductPricing Id should not be null");
		}
		return productPricing;
	}

	@Override
	public ProductPricing deleteProductPricing(String productPricingId)
			throws Exception {
		ProductPricing productPricing = null;
		if (StringUtils.isNotBlank(productPricingId)) {
			productPricing = productPricingDAO.findById(productPricingId);
			if (productPricing != null) {
				productPricingDAO.delete(productPricing);
			} else {
				throw new Exception("Product pricing is null");
			}
		} else {
			throw new Exception("Product Pricing Id should not be null");
		}
		return productPricing;
	}

	@Override
	public void sendRequestForCallBack(String name, String mobileNum,
			String requirement) throws Exception {
		if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(mobileNum)
				&& StringUtils.isNotBlank(requirement)) {
			userCommunicationService.sendRequestForCallBack(name, mobileNum,
					requirement);
		} else {
			throw new Exception(
					"Kindly enter Name, MobileNumber and Requirement");
		}
	}
}