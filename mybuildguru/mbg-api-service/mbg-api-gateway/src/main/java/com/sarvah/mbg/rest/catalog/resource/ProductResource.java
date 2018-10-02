/**
 * 
 */
package com.sarvah.mbg.rest.catalog.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import SearchProductBySkuIdResponse.SearchProductBySkuIdResponse;

import com.sarvah.mbg.catalog.service.ProductCatalogService;
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
import com.sarvah.mbg.promotion.service.PromotionService;
import com.sarvah.mbg.rest.authorization.MBGSecurityContext;
import com.sarvah.mbg.rest.catalog.model.BulkUpdateStoreProductsRequestParam;
import com.sarvah.mbg.rest.catalog.model.ImageOrFileDeleteRequestParam;
import com.sarvah.mbg.rest.catalog.model.ProductCountParam;
import com.sarvah.mbg.rest.catalog.model.ProductCreateRequestParam;
import com.sarvah.mbg.rest.catalog.model.ProductPricingCreateRequestParam;
import com.sarvah.mbg.rest.catalog.model.ProductPricingUpdateRequestParam;
import com.sarvah.mbg.rest.catalog.model.ProductQuantityCreateRequestParam;
import com.sarvah.mbg.rest.catalog.model.ProductQuantityTypeUpdateRequestParam;
import com.sarvah.mbg.rest.catalog.model.ProductSearchRequestParam;
import com.sarvah.mbg.rest.catalog.model.ProductUpdateRequestParam;
import com.sarvah.mbg.rest.catalog.model.ProductsStatusUpdateResponse;
import com.sarvah.mbg.rest.catalog.response.CountResponse;
import com.sarvah.mbg.rest.catalog.response.ManageProductPricingResponse;
import com.sarvah.mbg.rest.catalog.response.ManageProductResponse;
import com.sarvah.mbg.rest.catalog.response.ManageRequestQuoteResponse;
import com.sarvah.mbg.rest.catalog.response.ProductBrandResponse;
import com.sarvah.mbg.rest.catalog.response.ProductResponse;
import com.sarvah.mbg.rest.exception.MBGAppException;
import com.sarvah.mbg.rest.model.Page;
import com.sarvah.mbg.userprofile.auth.model.ApiUser;
import com.sarvah.mbg.userprofile.response.QuoteProductRequestParam;
import com.sarvah.mbg.userprofile.response.RequestQuoteProductRequestParam;

/**
 * @author naveen
 *
 */
@Component
@Path("/catalog/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

	private static final Logger logger = LoggerFactory
			.getLogger(ProductResource.class);

	@Autowired
	private ProductCatalogService productCatalogService;

	@Autowired
	private PromotionService promotionService;

	/**
	 * Method for get all ProductTypes
	 * 
	 * @param view
	 * @return
	 */
	@GET
	@Path("/producttypes")
	public Response getAllProductTypes(@QueryParam("view") String view) {
		List<ProductType> producttypes = productCatalogService
				.getSystemProductTypes();
		return Response.ok(producttypes).build();
	}

	/**
	 * Method for search products
	 * 
	 * @param productSearchparam
	 * @param page
	 * @param size
	 * @param sortStr
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@GET
	public Response findProducts(
			@BeanParam ProductSearchRequestParam productSearchparam,
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("20") @QueryParam("size") int size,
			@DefaultValue("createdDate,desc") @QueryParam("sort") String sortStr,
			@QueryParam("view") String view,
			@QueryParam("productId") String productId,
			@QueryParam("dealerId") String dealerId,
			@QueryParam("providerId") String providerId,
			@QueryParam("customerId") String customerId,
			@QueryParam("limit") int limit,
			@Context SecurityContext securityContext) throws Exception {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);

		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		String searchValue = productSearchparam.getSearchValue();
		String skuId = productSearchparam.getSkuId();
		String category = productSearchparam.getCategory();
		String subCategory = productSearchparam.getSubCategory();
		String productsType = productSearchparam.getProductsType();
		String pricing = productSearchparam.getPrice();
		String rating = productSearchparam.getRating();
		String status = productSearchparam.getStatus();
		String brand = productSearchparam.getBrand();
		String zipcode = productSearchparam.getZipcode();
		String city = productSearchparam.getCity();
		String key = productSearchparam.getKey();
		String value = productSearchparam.getValue();
		boolean filters = productSearchparam.isFilter();
		boolean isSUbcategoryList = productSearchparam.isSubcategoryList();

		ManageProductResponse manageProductResponse = new ManageProductResponse();
		ProductSummaryViewResponse productSummaryViewResponse = new ProductSummaryViewResponse();
		Response response = null;
		try {

			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("ProviderManageProduct")) {

				List<Order> orders = new ArrayList<Order>();
				String sortArray[] = sortStr.split(",");
				if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
					orders.add(new Order(Direction.DESC, sortArray[0]));
				else
					orders.add(new Order(Direction.ASC, sortArray[0]));

				Sort sort = new Sort(orders);

				productSummaryViewResponse = productCatalogService
						.getProviderManageProduct(apiUser.getUid(),
								searchValue, status, category, subCategory,
								page, size, sort);

				Page retPage = new Page();

				retPage.setTotalPages(productSummaryViewResponse
						.getTotalPages());
				retPage.setTotalElements(productSummaryViewResponse
						.getTotalElements());
				retPage.setNumber(productSummaryViewResponse.getNumber());
				retPage.setSize(productSummaryViewResponse.getSize());
				manageProductResponse.setPage(retPage);
				manageProductResponse.setProducts(productSummaryViewResponse
						.getProducts());

				response = Response.ok(manageProductResponse).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageProduct")
					&& StringUtils.isNotBlank(dealerId)) {

				List<Order> orders = new ArrayList<Order>();
				String sortArray[] = sortStr.split(",");
				if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
					orders.add(new Order(Direction.DESC, sortArray[0]));
				else
					orders.add(new Order(Direction.ASC, sortArray[0]));

				Sort sort = new Sort(orders);

				productSummaryViewResponse = productCatalogService
						.getAdminManageSellerProduct(dealerId, searchValue,
								brand, status, category, subCategory, page,
								size, sort);

				Page retPage = new Page();
				retPage.setTotalPages(productSummaryViewResponse
						.getTotalPages());
				retPage.setTotalElements(productSummaryViewResponse
						.getTotalElements());
				retPage.setNumber(productSummaryViewResponse.getNumber());
				retPage.setSize(productSummaryViewResponse.getSize());
				manageProductResponse.setPage(retPage);
				manageProductResponse.setProducts(productSummaryViewResponse
						.getProducts());

				response = Response.ok(manageProductResponse).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageProduct")
					&& StringUtils.isNotBlank(providerId)) {

				List<Order> orders = new ArrayList<Order>();
				String sortArray[] = sortStr.split(",");
				if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
					orders.add(new Order(Direction.DESC, sortArray[0]));
				else
					orders.add(new Order(Direction.ASC, sortArray[0]));

				Sort sort = new Sort(orders);

				productSummaryViewResponse = productCatalogService
						.getAdminManageProviderProduct(providerId, searchValue,
								status, category, subCategory, page, size, sort);

				Page retPage = new Page();
				retPage.setTotalPages(productSummaryViewResponse
						.getTotalPages());
				retPage.setTotalElements(productSummaryViewResponse
						.getTotalElements());
				retPage.setNumber(productSummaryViewResponse.getNumber());
				retPage.setSize(productSummaryViewResponse.getSize());
				manageProductResponse.setPage(retPage);
				manageProductResponse.setProducts(productSummaryViewResponse
						.getProducts());

				response = Response.ok(manageProductResponse).build();

			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageProduct")) {

				List<Order> orders = new ArrayList<Order>();
				String sortArray[] = sortStr.split(",");
				if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
					orders.add(new Order(Direction.DESC, sortArray[0]));
				else
					orders.add(new Order(Direction.ASC, sortArray[0]));

				Sort sort = new Sort(orders);

				productSummaryViewResponse = productCatalogService
						.getAdminManageProduct(searchValue, brand, status,
								category, subCategory, page, size, sort);
				Page retPage = new Page();
				retPage.setTotalPages(productSummaryViewResponse
						.getTotalPages());
				retPage.setTotalElements(productSummaryViewResponse
						.getTotalElements());
				retPage.setNumber(productSummaryViewResponse.getNumber());
				retPage.setSize(productSummaryViewResponse.getSize());

				manageProductResponse.setPage(retPage);
				manageProductResponse.setProducts(productSummaryViewResponse
						.getProducts());
				response = Response.ok(manageProductResponse).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("DealerManageProduct")) {

				List<Order> orders = new ArrayList<Order>();
				String sortArray[] = sortStr.split(",");
				if (StringUtils.equalsIgnoreCase(sortArray[1], "desc")) {
					orders.add(new Order(Direction.DESC, sortArray[0]));
				} else {
					orders.add(new Order(Direction.ASC, sortArray[0]));
				}

				Sort sort = new Sort(orders);

				productSummaryViewResponse = productCatalogService
						.getDealerManageProduct(apiUser.getUid(), searchValue,
								brand, status, category, subCategory, page,
								size, sort);

				Page retPage = new Page();
				retPage.setTotalPages(productSummaryViewResponse
						.getTotalPages());
				retPage.setTotalElements(productSummaryViewResponse
						.getTotalElements());
				retPage.setNumber(productSummaryViewResponse.getNumber());
				retPage.setSize(productSummaryViewResponse.getSize());
				manageProductResponse.setPage(retPage);
				manageProductResponse.setProducts(productSummaryViewResponse
						.getProducts());

				response = Response.ok(manageProductResponse).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("SearchByKeyword")) {
				List<Order> orders = new ArrayList<Order>();
				String sortArray[] = sortStr.split(",");
				if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
					orders.add(new Order(Direction.DESC, sortArray[0]));
				else
					orders.add(new Order(Direction.ASC, sortArray[0]));

				Sort sort = new Sort(orders);

				ProductCatalogResponse productCatalogResponse = productCatalogService
						.searchProductByKeyword(searchValue, brand, page, size,
								sort);

				ProductResponse productResponse = new ProductResponse();
				Page retPage = new Page();
				retPage.setTotalPages(productCatalogResponse.getTotalPages());
				retPage.setTotalElements(productCatalogResponse
						.getTotalElements());
				retPage.setNumber(productCatalogResponse.getNumber());
				retPage.setSize(productCatalogResponse.getSize());
				productResponse.setPage(retPage);
				productResponse.setProducts(productCatalogResponse
						.getProducts());
				productResponse.setSearchKeyword(productCatalogResponse
						.getSearchKeyword());
				productResponse.setFilters(productCatalogResponse.getFilters());
				response = Response.ok(productResponse).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("SearchBySkuID")) {
				SearchProductBySkuIdResponse product = productCatalogService
						.getProductBySkuId(skuId);
				response = Response.ok(product).build();
			} else if (StringUtils.isNotBlank(productId)
					&& StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("GetSameProducts")) {

				DisplaySameProductsResponse displaySameProductsResponse = productCatalogService
						.displaySameProducts(productId);

				response = Response.ok(displaySameProductsResponse).build();

			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageQuoteRequest")) {

				List<Order> orders = new ArrayList<Order>();
				String sortArray[] = sortStr.split(",");
				if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
					orders.add(new Order(Direction.DESC, sortArray[0]));
				else
					orders.add(new Order(Direction.ASC, sortArray[0]));

				Sort sort = new Sort(orders);
				AdminManageRequestQuoteResponse adminManageRequestQuoteResponse = productCatalogService
						.getAdminManageRequestQuote(searchValue, status, page,
								size, sort);

				ManageRequestQuoteResponse manageRequestQuoteResponse = new ManageRequestQuoteResponse();

				Page retPage = new Page();
				retPage.setTotalPages(adminManageRequestQuoteResponse
						.getTotalPages());
				retPage.setTotalElements(adminManageRequestQuoteResponse
						.getTotalElements());
				retPage.setNumber(adminManageRequestQuoteResponse.getNumber());
				retPage.setSize(adminManageRequestQuoteResponse.getSize());
				manageRequestQuoteResponse.setPage(retPage);
				manageRequestQuoteResponse
						.setRequestQuotes(adminManageRequestQuoteResponse
								.getRequestQuotes());

				response = Response.ok(manageRequestQuoteResponse).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("DealerManageQuoteRequest")) {

				List<Order> orders = new ArrayList<Order>();
				String sortArray[] = sortStr.split(",");
				if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
					orders.add(new Order(Direction.DESC, sortArray[0]));
				else
					orders.add(new Order(Direction.ASC, sortArray[0]));

				Sort sort = new Sort(orders);
				AdminManageRequestQuoteResponse dealerManageRequestQuoteResponse = productCatalogService
						.getDealerManageRequestQuote(searchValue, dealerId,
								page, size, sort);

				ManageRequestQuoteResponse manageRequestQuoteResponse = new ManageRequestQuoteResponse();

				Page retPage = new Page();
				retPage.setTotalPages(dealerManageRequestQuoteResponse
						.getTotalPages());
				retPage.setTotalElements(dealerManageRequestQuoteResponse
						.getTotalElements());
				retPage.setNumber(dealerManageRequestQuoteResponse.getNumber());
				retPage.setSize(dealerManageRequestQuoteResponse.getSize());
				manageRequestQuoteResponse.setPage(retPage);
				manageRequestQuoteResponse
						.setRequestQuotes(dealerManageRequestQuoteResponse
								.getRequestQuotes());

				response = Response.ok(manageRequestQuoteResponse).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("CustomerManageQuoteRequest")) {

				List<Order> orders = new ArrayList<Order>();
				String sortArray[] = sortStr.split(",");
				if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
					orders.add(new Order(Direction.DESC, sortArray[0]));
				else
					orders.add(new Order(Direction.ASC, sortArray[0]));

				Sort sort = new Sort(orders);
				AdminManageRequestQuoteResponse dealerManageRequestQuoteResponse = productCatalogService
						.getCustomerManageRequestQuote(searchValue, customerId,
								page, size, sort);

				ManageRequestQuoteResponse manageRequestQuoteResponse = new ManageRequestQuoteResponse();

				Page retPage = new Page();
				retPage.setTotalPages(dealerManageRequestQuoteResponse
						.getTotalPages());
				retPage.setTotalElements(dealerManageRequestQuoteResponse
						.getTotalElements());
				retPage.setNumber(dealerManageRequestQuoteResponse.getNumber());
				retPage.setSize(dealerManageRequestQuoteResponse.getSize());
				manageRequestQuoteResponse.setPage(retPage);
				manageRequestQuoteResponse
						.setRequestQuotes(dealerManageRequestQuoteResponse
								.getRequestQuotes());

				response = Response.ok(manageRequestQuoteResponse).build();

			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("VerifyBuildonnQuoteRequestId")) {

				boolean verify = productCatalogService
						.verifyBuildonnQuoteRequestId(productId);

				response = Response.ok(verify).build();
			} else {
				List<Order> orders = new ArrayList<Order>();
				String sortArray[] = sortStr.split(",");
				if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
					orders.add(new Order(Direction.DESC, sortArray[0]));
				else
					orders.add(new Order(Direction.ASC, sortArray[0]));

				Sort sort = new Sort(orders);

				ProductCatalogResponse productCatalogResponse = productCatalogService
						.searchProduct(searchValue, category, subCategory,
								productsType, brand, pricing, rating, status,
								zipcode, city, key, value, filters,
								isSUbcategoryList, page, size, sort, limit);
				ProductResponse productResponse = new ProductResponse();
				Page retPage = new Page();
				retPage.setTotalPages(productCatalogResponse.getTotalPages());
				retPage.setTotalElements(productCatalogResponse
						.getTotalElements());
				retPage.setNumber(productCatalogResponse.getNumber());
				retPage.setSize(productCatalogResponse.getSize());
				productResponse.setPage(retPage);
				productResponse.setProducts(productCatalogResponse
						.getProducts());
				productResponse.setFilters(productCatalogResponse.getFilters());
				response = Response.ok(productResponse).build();
			}
		} catch (Exception e) {
			throw new MBGAppException("Error occured trying to search product",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return response;
	}

	/**
	 * Method for display products count
	 * 
	 * @param productCountParam
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/count")
	public Response count(@BeanParam ProductCountParam productCountParam)
			throws Exception {

		String productName = productCountParam.getProductName();
		String providerName = productCountParam.getProviderName();
		String category = productCountParam.getCategory();
		String productsType = productCountParam.getProductsType();
		String pricing = productCountParam.getPrice();
		String rating = productCountParam.getRating();
		String latestproduct = productCountParam.getLatestproducts();
		String accessory = productCountParam.getAccessory();
		String status = productCountParam.getStatus();
		String lat = productCountParam.getLat();
		String lon = productCountParam.getLon();
		String brand = productCountParam.getBrand();
		Point point = null;
		if (StringUtils.isNotBlank(lat) && StringUtils.isNotBlank(lon)) {
			point = new Point(Double.parseDouble(lat), Double.parseDouble(lon));
		}
		CountResponse countResponse = new CountResponse();
		long productCount = productCatalogService.getProductsCount(productName,
				providerName, category, productsType, pricing, rating,
				latestproduct, point, accessory, status, brand);
		countResponse.setMbgProductCount(productCount);
		return Response.ok(countResponse).build();

	}

	/**
	 * Method to search for particular product
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/{pid}")
	public Response searchProductByPid(@PathParam("pid") String pid,
			@QueryParam("view") String view,
			@QueryParam("dealerId") String dealerId,
			@QueryParam("providerId") String providerId,
			@QueryParam("customerId") String customerId,
			@QueryParam("pincode") String pincode,
			@Context SecurityContext securityContext) throws Exception {

		// get the apiuser consuming this api and pass around for decision
		// making
		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		Response response = null;
		try {
			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminProductViewDetails")
					&& StringUtils.isNotBlank(dealerId)) {
				ProductDetailView ProductDetailView = productCatalogService
						.getAdminManageDealerProductViewDetails(pid, dealerId);
				response = Response.ok(ProductDetailView).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminProductViewDetails")
					&& StringUtils.isNotBlank(providerId)) {
				ProductDetailView ProductDetailView = productCatalogService
						.getAdminManageProviderProductViewDetails(pid,
								providerId);
				response = Response.ok(ProductDetailView).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminProductViewDetails")) {
				ProductDetailView ProductDetailView = productCatalogService
						.getSuperAdminProductviewDetails(pid);
				response = Response.ok(ProductDetailView).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("ProviderProductViewDetails")) {
				ProductDetailView ProductDetailView = productCatalogService
						.getProviderProductViewDetails(pid);
				response = Response.ok(ProductDetailView).build();

			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("DealerProductViewDetails")) {
				ProductDetailView productDetailView = productCatalogService
						.getDealerProductViewDetails(apiUser.getUid(), pid);
				response = Response.ok(productDetailView).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageQuoteRequestViewDetails")) {
				QuoteRequestResponse quoteRequestResponse = productCatalogService
						.getAdminManageQuoteRequestViewDetails(pid);
				response = Response.ok(quoteRequestResponse).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("DealerManageQuoteRequestViewDetails")) {
				QuoteRequestResponse quoteRequestResponse = productCatalogService
						.getDealerManageQuoteRequestViewDetails(pid, dealerId);
				response = Response.ok(quoteRequestResponse).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("CustomerManageQuoteRequestViewDetails")) {

				QuoteRequestResponse quoteRequestResponse = productCatalogService
						.getCustomerManageQuoteRequestViewDetails(pid,
								customerId);
				response = Response.ok(quoteRequestResponse).build();

			} else {
				ProductDetailsResponse productDetailsResponse = productCatalogService
						.getProductBypid(pid);
				response = Response.ok(productDetailsResponse).build();
			}
		} catch (Exception e) {
			logger.error("Error occured try to search the product"
					+ e.getMessage());
			throw new MBGAppException(
					"Error occured trying to search the product", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return response;
	}

	/**
	 * Method for create new product
	 * 
	 * @param productCreateRequestParam
	 * @return
	 * @throws Exception
	 */

	@POST
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response createProduct(
			ProductCreateRequestParam productCreateRequestParam,
			@Context SecurityContext securityContext) throws Exception {
		// get the apiuser consuming this api and pass around for decision
		// making
		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}
		String skuid = productCreateRequestParam.getSkuid();
		String name = productCreateRequestParam.getName();
		String longName = productCreateRequestParam.getLongName();
		String model = productCreateRequestParam.getModel();
		String desc = productCreateRequestParam.getDesc();
		Set<String> subcategoriesId = productCreateRequestParam
				.getSubcategories();
		Set<String> productTypeIds = productCreateRequestParam
				.getProductTypes();
		String productAssets = productCreateRequestParam.getProductAssets();
		String key1 = productCreateRequestParam.getKey1();
		String isMultiValued1 = productCreateRequestParam.isMultiValued1();
		Set<String> values1 = productCreateRequestParam.getValues1();
		String price = productCreateRequestParam.getPrice();
		String accessory = productCreateRequestParam.getAccessory();
		List<String> relatedProducts = productCreateRequestParam
				.getRelatedProducts();
		List<String> accessories = productCreateRequestParam.getAccessories();
		Set<ProductFaq> faqs = productCreateRequestParam.getFaqs();
		Set<ProductAttribute> productAttributes = productCreateRequestParam
				.getProductAttributes();
		String brandName = productCreateRequestParam.getBrandName();
		String quantityType = productCreateRequestParam.getQuantityType();
		Product product = null;
		try {
			product = productCatalogService.createProduct(apiUser.getUid(),
					skuid, name, longName, desc, subcategoriesId,
					productTypeIds, productAssets, price, accessory,
					relatedProducts, accessories, key1, values1,
					isMultiValued1, model, faqs, productAttributes, brandName,
					quantityType);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to add the product", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.accepted(product).build();
	}

	/**
	 * File Upload
	 * 
	 * @param form
	 * @return
	 * @throws IOException
	 * @throws MBGAppException
	 */
	@POST
	@Path("/file/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(FormDataMultiPart form) throws IOException,
			MBGAppException {
		Product product = null;
		String fileName = null;
		String pid = null;
		InputStream fileInputStream = null;
		long contentLength = 0;
		String fileTypeValue = null;
		try {
			Map<String, List<FormDataBodyPart>> formdatas = form.getFields();
			for (String partName : formdatas.keySet()) {
				List<FormDataBodyPart> fileparts = formdatas.get(partName);
				for (FormDataBodyPart formDataBodyPart : fileparts) {
					if (!formDataBodyPart.isSimple()) {
						ContentDisposition contentDisposition = formDataBodyPart
								.getContentDisposition();
						fileInputStream = formDataBodyPart
								.getValueAs(InputStream.class);
						fileName = contentDisposition.getFileName();
						contentLength = contentDisposition.getSize();
					} else {
						if (formDataBodyPart.getName().equals("productId")) {
							pid = formDataBodyPart.getValue();
						}
						if (formDataBodyPart.getName().equals("fileType")) {
							fileTypeValue = formDataBodyPart.getValue();
						}
					}
				}
			}
			product = productCatalogService.uploadFile(pid, fileName,
					fileTypeValue, fileInputStream, contentLength);
		} catch (Exception e) {
			throw new MBGAppException("Error occured trying to Upload File", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		} finally {
			fileInputStream.close();
		}
		return Response.ok(product).build();
	}

	@POST
	@Path("/file/uploadInstantOrderImage")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadInstantOrderImage(FormDataMultiPart form)
			throws IOException, MBGAppException {
		String fileName = null;
		String mobileNumber = null;
		String emailId = null;
		String customerName = null;
		String textInfo = null;
		String deliveryTime = null;
		String url = null;
		InputStream fileInputStream = null;
		long contentLength = 0;
		String fileTypeValue = null;
		String type = null;
		try {
			Map<String, List<FormDataBodyPart>> formdatas = form.getFields();
			for (String partName : formdatas.keySet()) {
				List<FormDataBodyPart> fileparts = formdatas.get(partName);
				for (FormDataBodyPart formDataBodyPart : fileparts) {
					if (!formDataBodyPart.isSimple()) {
						ContentDisposition contentDisposition = formDataBodyPart
								.getContentDisposition();
						fileInputStream = formDataBodyPart
								.getValueAs(InputStream.class);
						fileName = contentDisposition.getFileName();
						contentLength = contentDisposition.getSize();
					} else {
						if (formDataBodyPart.getName().equals("mobileNumber")) {
							mobileNumber = formDataBodyPart.getValue();
						}
						if (formDataBodyPart.getName().equals("emailId")) {
							emailId = formDataBodyPart.getValue();
						}
						if (formDataBodyPart.getName().equals("customerName")) {
							customerName = formDataBodyPart.getValue();
						}
						if (formDataBodyPart.getName().equals("textInfo")) {
							textInfo = formDataBodyPart.getValue();
						}
						if (formDataBodyPart.getName().equals("deliveryTime")) {
							deliveryTime = formDataBodyPart.getValue();
						}
						if (formDataBodyPart.getName().equals("fileType")) {
							fileTypeValue = formDataBodyPart.getValue();
						}
						if (formDataBodyPart.getName().equals("type")) {
							type = formDataBodyPart.getValue();
						}
					}
				}
			}
			if (StringUtils.isNotBlank(type)
					&& type.equalsIgnoreCase("ProductEnquiryForm")) {
				url = productCatalogService.sendProductEnquiryForm(
						customerName, mobileNumber, emailId, textInfo,
						deliveryTime);
			} else {

				url = productCatalogService.uploadInstantOrderImage(
						mobileNumber, emailId, customerName, textInfo,
						deliveryTime, fileName, fileTypeValue, fileInputStream,
						contentLength);
			}
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to Upload File or sending ProductEnquiry form",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}

		return Response.ok(url).build();
	}

	/**
	 * Method for delete a product based on productId.
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Path("/{pid}")
	public Response deleteProductById(@PathParam("pid") String pid)
			throws Exception {
		Product product = null;
		try {
			product = productCatalogService.deleteProduct(pid);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to delete the product", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(product).build();
	}

	/**
	 * Method to update product
	 * 
	 * @param pid
	 * @param productUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("/{pid}")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response updateProductByPid(@PathParam("pid") String pid,
			@QueryParam("view") String view,
			ProductUpdateRequestParam productUpdateRequestParam,
			@Context SecurityContext securityContext) throws Exception {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		String name = productUpdateRequestParam.getName();
		String longName = productUpdateRequestParam.getLongName();
		String desc = productUpdateRequestParam.getDesc();
		Set<String> subCategoryId = productUpdateRequestParam
				.getSubcategories();
		Set<String> productTypes = productUpdateRequestParam.getProductTypes();
		String productAssets = productUpdateRequestParam.getProductAssets();
		Set<ProductAttribute> productAttributes = productUpdateRequestParam
				.getProductAttributes();
		String price = productUpdateRequestParam.getPrice();
		String accessory = productUpdateRequestParam.getAccessory();
		List<String> relatedProducts = productUpdateRequestParam
				.getRelatedProducts();
		List<String> accessories = productUpdateRequestParam.getAccessories();
		String status = productUpdateRequestParam.getStatus();
		String key = productUpdateRequestParam.getKey();
		String isMultivaled = productUpdateRequestParam.getIsMultivalued();
		Set<String> values = productUpdateRequestParam.getValues();
		String model = productUpdateRequestParam.getModel();
		Set<ProductFaq> faqs = productUpdateRequestParam.getFaqs();
		String brandName = productUpdateRequestParam.getBrandName();
		String quantityType = productUpdateRequestParam.getQuantityType();

		if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("UpdateSellerQuoteRequest")) {
			QuoteProductRequestParam product = productUpdateRequestParam
					.getProduct();

			QuoteRequestProduct quoteRequestProduct = productCatalogService
					.updateSellerQuoteProduct(pid, product);
			return Response.ok(quoteRequestProduct).build();
		} else {

			Product product = null;

			try {
				product = productCatalogService.updateProduct(/* apiUser.getUid(), */
				pid, name, longName, desc, subCategoryId, productTypes,
						productAssets, productAttributes, price, accessory,
						relatedProducts, accessories, status, key,
						isMultivaled, values, model, faqs, brandName,
						quantityType);

			} catch (Exception e) {
				throw new MBGAppException(
						"Error occured trying to update product", e,
						e.getMessage(), Status.BAD_REQUEST.getStatusCode(),
						1000);
			}
			return Response.ok(product).build();
		}
	}

	/**
	 * Method for search dealers for particular product
	 * 
	 * @param pid
	 * @param zipcode
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{pid}/dealers")
	public Response searchDealers(@PathParam("pid") String pid,
			@QueryParam("zipcode") int zipcode) throws MBGAppException {
		List<SellerInformation> dealers = null;
		try {
			dealers = productCatalogService.searchDealers(pid, zipcode);
			return Response.ok(dealers).build();
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to search the dealers", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

	}

	/**
	 * Method to count dealers for a product
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/{pid}/dealers/count")
	public Response countDealerForProduct(@PathParam("pid") String pid)
			throws Exception {

		Long count = null;
		try {
			count = productCatalogService.countDealerForProduct(pid);
		} catch (Exception e) {
			throw new MBGAppException("No data found", e, e.getMessage(),
					Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		return Response.ok(count).build();
	}

	/**
	 * Method to search provider for the product
	 * 
	 * @param pid
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{pid}/provider")
	public Response searchProviderForProduct(@PathParam("pid") String pid)
			throws MBGAppException {
		User provider = null;
		try {
			provider = productCatalogService.searchProviderForProduct(pid);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to search the Provider", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(provider).build();
	}

	/**
	 * Method to get related products for the product
	 * 
	 * @param pid
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{pid}/relatedproducts")
	public Response getRelatedProducts(@PathParam("pid") String pid)
			throws MBGAppException {
		Set<ProductDetailsResponse> relatedProductIds = null;
		try {
			relatedProductIds = productCatalogService.getRelatedProducts(pid);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to search the RelatedProducts", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(relatedProductIds).build();
	}

	/**
	 * Method to add related products for the product
	 * 
	 * @param pid
	 * @param relatedProducts
	 * @return
	 * @throws MBGAppException
	 */
	@POST
	@Path("/{pid}/relatedproducts")
	public Response createRelatedProducts(@PathParam("pid") String pid,
			List<String> relatedProducts) throws MBGAppException {
		try {
			Set<String> relatedProductList = productCatalogService
					.createRelatedProducts(pid, relatedProducts);
			return Response.ok(relatedProductList).build();
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to Create the RelatedProducts", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

	}

	/**
	 * Method to update related products of a product
	 * 
	 * @param pid
	 * @param relProdIds
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("/{pid}/relatedproducts")
	public Response updateRelatedProducts(@PathParam("pid") String pid,
			List<String> relProdIds) throws Exception {

		Set<String> realatedProduct = null;
		try {
			realatedProduct = productCatalogService.updateRelatedProducts(pid,
					relProdIds);
		} catch (Exception e) {
			throw new MBGAppException("No data found", e, e.getMessage(),
					Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(realatedProduct).build();
	}

	/**
	 * Method to remove related product of a product
	 * 
	 * @param pid
	 * @param relatedProducts
	 * @return
	 * @throws MBGAppException
	 */
	@DELETE
	@Path("/{pid}/relatedproducts")
	public Response deleteRelatedProducts(@PathParam("pid") String pid,
			List<String> relatedProducts) throws MBGAppException {
		try {
			Set<String> relatedProductList = productCatalogService
					.deleteRelatedProducts(pid, relatedProducts);
			return Response.ok(relatedProductList).build();
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to Delete the RelatedProducts", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * Method to get related accessories of a product
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/{pid}/relatedaccessories")
	public Response getRelatedAccessories(@PathParam("pid") String pid)
			throws Exception {

		Set<ProductDetailsResponse> relatedAccessories = null;
		try {
			relatedAccessories = productCatalogService.getRelatedAccsories(pid);
		} catch (Exception e) {
			throw new MBGAppException("No data found", e, e.getMessage(),
					Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		return Response.ok(relatedAccessories).build();
	}

	/**
	 * Method to add related accessories for the product
	 * 
	 * @param pid
	 * @param accessories
	 * @return
	 */
	@POST
	@Path("/{pid}/relatedaccessories")
	public Response createRelatedAccessories(@PathParam("pid") String pid,
			List<String> accessories) {

		Set<String> accessories1 = null;
		try {
			accessories1 = productCatalogService.createRelatedAccessories(pid,
					accessories);
		} catch (Exception e) {
		}

		return Response.ok(accessories1).build();
	}

	/**
	 * Method to update related accessories of the product
	 * 
	 * @param pid
	 * @param relatedAccessories
	 * @return
	 * @throws MBGAppException
	 */
	@PUT
	@Path("/{pid}/relatedaccessories")
	public Response UpdateRelatedAccessories(@PathParam("pid") String pid,
			List<String> relatedAccessories) throws MBGAppException {
		try {
			Set<String> relatedAccessoriesList = productCatalogService
					.updateRelatedAccessories(pid, relatedAccessories);
			return Response.ok(relatedAccessoriesList).build();
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to Create the RelatedProducts", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method to delete related accessories of a product
	 * 
	 * @param pid
	 * @param accessories
	 * @return
	 */
	@DELETE
	@Path("/{pid}/relatedaccessories")
	public Response deleteRelatedAccessories(@PathParam("pid") String pid,
			List<String> accessories) {
		Set<String> accessories1 = null;
		accessories1 = productCatalogService.deleteRelatedAccessories(pid,
				accessories);
		return Response.ok(accessories1).build();
	}

	/**
	 * method for get all brands.
	 * 
	 * @return
	 */
	@GET
	@Path("/brands")
	public Response getAllBrands() {
		ProductBrandResponse productBrandResponse = new ProductBrandResponse();
		List<ProductBrand> productbrand = productCatalogService.getAllBrands();
		productBrandResponse.setBrands(productbrand);
		return Response.ok(productBrandResponse).build();
	}

	/**
	 * method for get product count of onboarded by me.
	 * 
	 * @return
	 */
	@GET
	@Path("/onboardedbyme/count")
	public Response getOnBoardedByMeCount(
			@Context SecurityContext securityContext) {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		long count = productCatalogService.getOnBoardedByMe(apiUser.getUid());
		return Response.ok(count).build();
	}

	/**
	 * method for get products onboarded by me.
	 * 
	 * @param view
	 * @return
	 */
	@GET
	@Path("/onboardedbyme")
	public Response getOnBoardedByMe(@PathParam("view") String view,
			@Context SecurityContext securityContext) {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		List<DealerOnboardedProductDetails> dealerOnboardedProductDetails = new ArrayList<>();

		dealerOnboardedProductDetails = productCatalogService
				.getAllProductsOnboardedMe(apiUser.getUid());
		return Response.ok(dealerOnboardedProductDetails).build();

	}

	/**
	 * method for create product quantity type.
	 * 
	 * @param productQuantityCreateRequestParam
	 * @return
	 * @throws MBGAppException
	 */
	@POST
	@Path("/productquantitytype")
	public Response createProductQuantityType(
			ProductQuantityCreateRequestParam productQuantityCreateRequestParam)
			throws MBGAppException {
		ProductQuantityType productQuantityType = null;
		String name = productQuantityCreateRequestParam.getName();
		String desc = productQuantityCreateRequestParam.getDesc();
		try {
			productQuantityType = productCatalogService
					.createProductQuantityType(name, desc);
		} catch (Exception e) {
			logger.info("Error occured trying to create a productQuantity type");
			throw new MBGAppException(
					"Error occured trying to create a productQuantity type", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(productQuantityType).build();
	}

	/**
	 * method for get product quantity types
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/productquantitytype")
	public Response getProductQuantityType() throws MBGAppException {

		try {
			List<ProductQuantityType> productQuantityTypeList = productCatalogService
					.getProductQuantityType();
			return Response.ok(productQuantityTypeList).build();
		} catch (Exception e) {
			logger.info("Error occured trying to create a productQuantity type");
			throw new MBGAppException(
					"Error occured trying to create a productQuantity type", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

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
	@GET
	@Path("/{pid}/dealers/{dealerid}/checkpromo/{promocode}")
	public Response checkPromoCode(@PathParam("pid") String pid,
			@PathParam("dealerid") String dealerId,
			@PathParam("promocode") String promocode) {

		PriceResponse priceResponse = null;
		try {
			priceResponse = productCatalogService.checkPromoCode(pid, dealerId,
					promocode);
		} catch (Exception e) {
			logger.info("Error occured trying to check promocode");
			throw new MBGAppException(
					"Error occured trying to check promocode", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		return Response.ok(priceResponse).build();
	}

	/**
	 * Method to get promocode for the product
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/{pid}/promocode")
	public Response getPromoCodeForProduct(@PathParam("pid") String pid)
			throws Exception {

		List<Promotion> promoCodeList = new ArrayList<>();
		try {
			promoCodeList = productCatalogService.getPromoCodeForProduct(pid);
			return Response.ok(promoCodeList).build();
		} catch (Exception e) {
			throw new Exception("No promo code available for the product");
		}
	}

	/**
	 * method for get top search keywords
	 * 
	 * @return
	 */
	@GET
	@Path("/topsearchedkeywords")
	public Response getTopSearchedKeyWords(
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("20") @QueryParam("size") int size,
			@DefaultValue("createdDate,desc") @QueryParam("sort") String sortStr) {

		List<Order> orders = new ArrayList<Order>();
		String sortArray[] = sortStr.split(",");
		if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
			orders.add(new Order(Direction.DESC, sortArray[0]));
		else
			orders.add(new Order(Direction.ASC, sortArray[0]));

		Sort sort = new Sort(orders);

		List<SearchKeywordResponse> stringList = productCatalogService
				.getTopSearchedKeywords(page, size, sort);
		return Response.ok(stringList).build();
	}

	/**
	 * File Delete
	 * 
	 * Method to delete file/remove from the product and Azure
	 * 
	 * @param pid
	 * @param fileUrl
	 * @param fileType
	 * @return
	 * @throws IOException
	 * @throws MBGAppException
	 */
	@PUT
	@Path("/file/delete/{pid}")
	public Response deleteFile(@PathParam("pid") String pid,
			ImageOrFileDeleteRequestParam imageOrFileDeleteRequestParam)
			throws IOException, MBGAppException {
		Product product = null;
		String fileUrl = imageOrFileDeleteRequestParam.getUrl();
		String fileType = imageOrFileDeleteRequestParam.getFileType();
		try {
			product = productCatalogService.deleteFile(pid, fileUrl, fileType);
		} catch (Exception e) {
			throw new MBGAppException("Error occured trying to delete File", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(product).build();
	}

	/**
	 * Method to update ProductQuantityType
	 * 
	 * @param qtyTypeId
	 * @param productQuantityUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("/productquantitytype/{qtyTypeId}")
	public Response editProductQuantityType(
			@PathParam("qtyTypeId") String qtyTypeId,
			ProductQuantityTypeUpdateRequestParam productQuantityUpdateRequestParam)
			throws Exception {

		String name = productQuantityUpdateRequestParam.getName();
		String desc = productQuantityUpdateRequestParam.getDesc();

		try {
			ProductQuantityType productQuantityType = productCatalogService
					.editProductQuantityType(qtyTypeId, name, desc);
			return Response.ok(productQuantityType).build();
		} catch (Exception e) {
			logger.info("Error occured trying to update productQuantity type");
			throw new MBGAppException(
					"Error occured trying to update a productQuantity type", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

	}

	/**
	 * Method to delete a productQuantityType
	 * 
	 * @param qtyTypeId
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Path("/productquantitytype/{qtyTypeId}")
	public Response deleteProductQuantityType(
			@PathParam("qtyTypeId") String qtyTypeId) throws Exception {

		try {
			ProductQuantityType productQuantityType = productCatalogService
					.deleteProductQuantityType(qtyTypeId);
			return Response.ok(productQuantityType).build();
		} catch (Exception e) {
			logger.info("Error occured trying to delete a productQuantity type");
			throw new MBGAppException(
					"Error occured trying to delete a productQuantity type", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

	}

	/**
	 * method for get customer bought products
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/{pid}/customerbought")
	public Response getCustomerbought(@PathParam("pid") String pid)
			throws Exception {

		Set<Product> custBoughtResponse = null;
		try {
			custBoughtResponse = productCatalogService
					.getCustomerBoughtThisAlsoBought(pid);
		} catch (Exception e) {
			throw new MBGAppException("No data found", e, e.getMessage(),
					Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		return Response.ok(custBoughtResponse).build();
	}

	@PUT
	@Path("/adminBulkUpdateStoreProducts")
	public Response adminBulkUpdateStoreProductsInfo(
			@BeanParam ProductSearchRequestParam productSearchparam,
			@QueryParam("dealerId") String dealerId,
			BulkUpdateStoreProductsRequestParam bulkUpdateStoreProductsRequestParam)
			throws Exception {
		String status = productSearchparam.getStatus();
		String brand = productSearchparam.getBrand();
		String subCategory = productSearchparam.getSubCategory();

		String nationalShippingCharge = bulkUpdateStoreProductsRequestParam
				.getNationalShippingCharge();
		String zonalShippingCharge = bulkUpdateStoreProductsRequestParam
				.getZonalShippingCharge();
		String localShippingCharge = bulkUpdateStoreProductsRequestParam
				.getLocalShippingCharge();
		String minNationalProcurementSLA = bulkUpdateStoreProductsRequestParam
				.getMinNationalProcurementSLA();
		String maxNationalProcurementSLA = bulkUpdateStoreProductsRequestParam
				.getMaxNationalProcurementSLA();
		String minZonalProcurementSLA = bulkUpdateStoreProductsRequestParam
				.getMinZonalProcurementSLA();
		String maxZonalProcurementSLA = bulkUpdateStoreProductsRequestParam
				.getMaxZonalProcurementSLA();
		String minLocalPocurementSLA = bulkUpdateStoreProductsRequestParam
				.getMinLocalProcurementSLA();
		String maxLocalPocurementSLA = bulkUpdateStoreProductsRequestParam
				.getMaxLocalProcurementSLA();
		String mbgShare = bulkUpdateStoreProductsRequestParam.getMbgShare();
		String localRegion = bulkUpdateStoreProductsRequestParam
				.getLocalRegion();
		String zonalRegion = bulkUpdateStoreProductsRequestParam
				.getZonalRegion();
		String nationRegion = bulkUpdateStoreProductsRequestParam
				.getNationRegion();
		String discount = bulkUpdateStoreProductsRequestParam.getDiscount();
		String storeDelivery = bulkUpdateStoreProductsRequestParam
				.getWillYouDeliver();

		productCatalogService
				.adminBulkUpdatingStoreProductsInfo(dealerId, brand, status,
						subCategory, nationalShippingCharge,
						zonalShippingCharge, localShippingCharge,
						minNationalProcurementSLA, maxNationalProcurementSLA,
						minZonalProcurementSLA, maxZonalProcurementSLA,
						minLocalPocurementSLA, maxLocalPocurementSLA, mbgShare,
						localRegion, zonalRegion, nationRegion, discount,
						storeDelivery);

		return Response.ok().build();
	}

	@PUT
	@Path("/dealerBulkUpdateStoreProducts")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response dealerBulkUpdateStoreProductsInfo(
			@BeanParam ProductSearchRequestParam productSearchparam,
			BulkUpdateStoreProductsRequestParam bulkUpdateStoreProductsRequestParam,
			@Context SecurityContext securityContext) throws Exception {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);

		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		String status = productSearchparam.getStatus();
		String brand = productSearchparam.getBrand();
		String subCategory = productSearchparam.getSubCategory();

		String nationalShippingCharge = bulkUpdateStoreProductsRequestParam
				.getNationalShippingCharge();
		String zonalShippingCharge = bulkUpdateStoreProductsRequestParam
				.getZonalShippingCharge();
		String localShippingCharge = bulkUpdateStoreProductsRequestParam
				.getLocalShippingCharge();
		String minNationalProcurementSLA = bulkUpdateStoreProductsRequestParam
				.getMinNationalProcurementSLA();
		String maxNationalProcurementSLA = bulkUpdateStoreProductsRequestParam
				.getMaxNationalProcurementSLA();
		String minZonalProcurementSLA = bulkUpdateStoreProductsRequestParam
				.getMinZonalProcurementSLA();
		String maxZonalProcurementSLA = bulkUpdateStoreProductsRequestParam
				.getMaxZonalProcurementSLA();
		String minLocalPocurementSLA = bulkUpdateStoreProductsRequestParam
				.getMinLocalProcurementSLA();
		String maxLocalPocurementSLA = bulkUpdateStoreProductsRequestParam
				.getMaxLocalProcurementSLA();
		String mbgShare = bulkUpdateStoreProductsRequestParam.getMbgShare();
		String localRegion = bulkUpdateStoreProductsRequestParam
				.getLocalRegion();
		String zonalRegion = bulkUpdateStoreProductsRequestParam
				.getZonalRegion();
		String nationRegion = bulkUpdateStoreProductsRequestParam
				.getNationRegion();
		String discount = bulkUpdateStoreProductsRequestParam.getDiscount();
		String storeDelivery = bulkUpdateStoreProductsRequestParam
				.getWillYouDeliver();

		productCatalogService.dealerBulkUpdatingStoreProductsInfo(
				apiUser.getUid(), brand, status, subCategory,
				nationalShippingCharge, zonalShippingCharge,
				localShippingCharge, minNationalProcurementSLA,
				maxNationalProcurementSLA, minZonalProcurementSLA,
				maxZonalProcurementSLA, minLocalPocurementSLA,
				maxLocalPocurementSLA, mbgShare, localRegion, zonalRegion,
				nationRegion, discount, storeDelivery);

		return Response.ok().build();
	}

	@PUT
	@Path("/status")
	public Response productsStatusBulkUpdate(
			ProductsStatusUpdateResponse productsStatusUpdateResponse) {

		String subCategory = productsStatusUpdateResponse.getSubCategory();
		String brand = productsStatusUpdateResponse.getBrand();
		String status = productsStatusUpdateResponse.getStatus();
		Set<String> productIds = productsStatusUpdateResponse.getProductIds();
		String newStatus = productsStatusUpdateResponse.getNewStatus();

		try {
			productCatalogService.productsStatusBulkUpdate(subCategory, brand,
					status, productIds, newStatus);
		} catch (Exception e) {
			throw new MBGAppException(
					"At least you have to select any one of these : Subcategory, Brand, Status, ProductIds",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok().build();
	}

	@POST
	@Path("/uploadRequestQuote")
	public Response uploadRequestQuote(
			RequestQuoteProductRequestParam requestQuoteRequestParam)
			throws MBGAppException {
		String userId = requestQuoteRequestParam.getUserId();
		String customerName = requestQuoteRequestParam.getCustomerName();
		String mobileNumber = requestQuoteRequestParam.getMobileNumber();
		String emailId = requestQuoteRequestParam.getEmail();
		String zipcode = requestQuoteRequestParam.getZipcode();

		String productId = requestQuoteRequestParam.getProductId();
		String itemQuantity = requestQuoteRequestParam.getQuantity();
		String paymentMode = requestQuoteRequestParam.getPaymentMode();
		String creditDays = requestQuoteRequestParam.getCreditDays();
		String deliveryTime = requestQuoteRequestParam.getDeliveryDate();
		String textInfo = requestQuoteRequestParam.getAdditionalInfo();

		String brand = requestQuoteRequestParam.getBrand();
		String changeAttribute = requestQuoteRequestParam.getChangeAttribute();

		int page = 0;
		int size = 20;
		String sortVal = "createdDate,desc";
		List<Order> orders = new ArrayList<Order>();
		String sortArray[] = sortVal.split(",");
		if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
			orders.add(new Order(Direction.DESC, sortArray[0]));
		else
			orders.add(new Order(Direction.ASC, sortArray[0]));

		Sort sort = new Sort(orders);
		try {
			productCatalogService.uploadRequestQuoteOrderImage(userId,
					productId, customerName, mobileNumber, emailId, zipcode,
					itemQuantity, paymentMode, creditDays, deliveryTime,
					textInfo, brand, changeAttribute, page, size, sort);

		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to send RequestQuote", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		return Response.ok().build();
	}

	@POST
	@Path("/productpricing")
	public Response createProductPricing(
			ProductPricingCreateRequestParam productPricingCreateRequestParam) {
		String productId = productPricingCreateRequestParam.getProductId();
		Set<ProductPrice> ProductPrices = productPricingCreateRequestParam
				.getProductPrices();
		String note = productPricingCreateRequestParam.getNote();
		ProductPricing priductPrcing = null;
		try {
			priductPrcing = productCatalogService.createProductPricing(
					productId, ProductPrices, note);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured while create product pricing List", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(priductPrcing).build();
	}

	@GET
	@Path("/productpricing")
	public Response getProductPricing(
			@BeanParam ProductSearchRequestParam productSearchparam,
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("20") @QueryParam("size") int size,
			@DefaultValue("createdDate,desc") @QueryParam("sort") String sortStr) {
		String searchValue = productSearchparam.getSearchValue();
		String category = productSearchparam.getCategory();
		String subCategory = productSearchparam.getSubCategory();
		String productsType = productSearchparam.getProductsType();
		String pricing = productSearchparam.getPrice();
		String rating = productSearchparam.getRating();
		String status = productSearchparam.getStatus();
		String brand = productSearchparam.getBrand();
		String zipcode = productSearchparam.getZipcode();
		String city = productSearchparam.getCity();
		String key = productSearchparam.getKey();
		String value = productSearchparam.getValue();
		ProductPricingSummaryViewResponse productPricings = null;
		ManageProductPricingResponse productPricingsResponse = new ManageProductPricingResponse();
		try {
			List<Order> orders = new ArrayList<Order>();
			String sortArray[] = sortStr.split(",");
			if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
				orders.add(new Order(Direction.DESC, sortArray[0]));
			else
				orders.add(new Order(Direction.ASC, sortArray[0]));

			Sort sort = new Sort(orders);

			productPricings = productCatalogService.getProductPricingList(
					searchValue, category, subCategory, productsType, brand,
					pricing, rating, status, zipcode, city, key, value, page,
					size, sort);
			Page retPage = new Page();
			retPage.setTotalPages(productPricings.getTotalPages());
			retPage.setTotalElements(productPricings.getTotalElements());
			retPage.setNumber(productPricings.getNumber());
			retPage.setSize(productPricings.getSize());
			productPricingsResponse.setPage(retPage);
			productPricingsResponse.setProductPrices(productPricings
					.getProductPrices());
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured while fetching product pricing List", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(productPricingsResponse).build();
	}

	@GET
	@Path("/productpricing/{ppid}")
	public Response searchProductPricingById(
			@PathParam("ppid") String productPricingId) {

		ProductPricingSummaryView priductPrcing = null;
		try {
			priductPrcing = productCatalogService
					.searchProductPricingById(productPricingId);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured while searching product pricing List bby id",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(priductPrcing).build();
	}

	@PUT
	@Path("/productpricing/{ppid}")
	public Response updateProductPricing(
			ProductPricingUpdateRequestParam productPricingUpdateRequestParam,
			@PathParam("ppid") String productPricingId) {
		Set<ProductPrice> productPrices = productPricingUpdateRequestParam
				.getProductPrices();
		String note = productPricingUpdateRequestParam.getNote();
		ProductPricing priductPrcing = null;
		try {
			priductPrcing = productCatalogService.updateProductPricing(
					productPricingId, productPrices, note);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured while updating product pricing List", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(priductPrcing).build();
	}

	@DELETE
	@Path("/productpricing/{ppid}")
	public Response deleteProductPricing(
			@PathParam("ppid") String productPricingId) {
		ProductPricing priductPrcing = null;
		try {
			priductPrcing = productCatalogService
					.deleteProductPricing(productPricingId);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured while deleting product pricing List", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(priductPrcing).build();
	}

	@POST
	@Path("/requestforcallback")
	public Response sendRequestForCallBack(@QueryParam("name") String name,
			@QueryParam("mobileNum") String mobileNum,
			@QueryParam("requirement") String requirement) {
		try {
			productCatalogService.sendRequestForCallBack(name, mobileNum,
					requirement);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured while sending RequestForCallBack to Admin",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok().build();
	}
}
