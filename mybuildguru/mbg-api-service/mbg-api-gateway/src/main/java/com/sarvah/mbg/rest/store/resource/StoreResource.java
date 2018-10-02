/**
 * 
 */
package com.sarvah.mbg.rest.store.resource;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.marketing.Promotion;
import com.sarvah.mbg.domain.mongo.store.Store;
import com.sarvah.mbg.domain.mongo.store.StoreProductPricing;
import com.sarvah.mbg.rest.catalog.model.ProductLinkOrUnLinkResponse;
import com.sarvah.mbg.rest.catalog.model.PromotionCreateRequestParam;
import com.sarvah.mbg.rest.catalog.model.StorePromotionUpdateRequestParam;
import com.sarvah.mbg.rest.catalog.response.CountResponse;
import com.sarvah.mbg.rest.exception.MBGAppException;
import com.sarvah.mbg.rest.model.Page;
import com.sarvah.mbg.rest.store.response.StoreResponse;
import com.sarvah.mbg.rest.userprofile.model.StoreCreateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.StoreUpdateRequestParam;
import com.sarvah.mbg.store.service.StoreService;
import com.sarvah.mbg.userprofile.auth.model.ApiUser;
import com.sarvah.mbg.userprofile.service.UserService;

/**
 * @author sumanth
 *
 */
@Component
@Path("/users/{dealerId}/store")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StoreResource {
	private static final Logger logger = LoggerFactory
			.getLogger(StoreResource.class);

	@Autowired
	private StoreService storeService;

	@Autowired
	private UserService userService;

	/**
	 * method for get store based on dealerId and store name.
	 * 
	 * @param dealerId
	 * @param storename
	 * @param page
	 * @param size
	 * @param sortList
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	public Response search(
			@PathParam("dealerId") String dealerId,
			@QueryParam("storename") String storename,
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("10") @QueryParam("size") int size,
			@DefaultValue("createdDate,desc") @QueryParam("sort") List<String> sortList)
			throws MBGAppException {
		try {
			StoreResponse response = new StoreResponse();
			List<Order> orders = new ArrayList<Order>();
			for (String sortItem : sortList) {
				if (sortItem.split(",")[1].equalsIgnoreCase("desc"))
					orders.add(new Order(Direction.DESC, sortItem.split(",")[0]));
				else
					orders.add(new Order(Direction.ASC, sortItem.split(",")[0]));
			}
			Sort sort = new Sort(orders);
			response.setStores(storeService.findStore(dealerId, storename,
					page, size, sort));
			int totalElements = storeService.count(dealerId);
			Page retPage = new Page(); // first page is 0, 2nd is 1 ....
			retPage.setNumber(page);
			retPage.setSize(size);
			retPage.setTotalElements(totalElements);
			retPage.setTotalPages(totalElements / size);
			response.setPage(retPage);
			return Response.ok(response).build();
		} catch (Exception e) {
			throw new MBGAppException("Error occured during store search", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method for get store count based on dealerId.
	 * 
	 * @param dealerId
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/count")
	public Response countStores(@PathParam("dealerId") String dealerId)
			throws MBGAppException {
		int count;
		try {
			count = storeService.count(dealerId);
		} catch (Exception e) {
			throw new MBGAppException("No data found", e, e.getMessage(),
					Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(count).build();
	}

	/**
	 * Method to create new store
	 * 
	 * @param dealerId
	 * @param storeCreateRequestParam
	 * @return
	 */
	@POST
	public Response createStore(@PathParam("dealerId") String dealerId,
			StoreCreateRequestParam storeCreateRequestParam) {

		String storeName = storeCreateRequestParam.getStoreName();
		String desc = storeCreateRequestParam.getDesc();
		String templateId = storeCreateRequestParam.getTemplateId();
		List<String> productIds = storeCreateRequestParam.getProIds();
		String mrp = storeCreateRequestParam.getMrp();
		String sellingPrice = storeCreateRequestParam.getSellingPrice();
		String listingStatus = storeCreateRequestParam.getListingStatus();
		String fulfilledBy = storeCreateRequestParam.getFulfilledBy();
		String stockCount = storeCreateRequestParam.getStockCount();
		String minQtyBuy = storeCreateRequestParam.getMinQuantityBuy();
		String tax = storeCreateRequestParam.getTax();
		String mbgShare = storeCreateRequestParam.getMbgShare();
		String discount = storeCreateRequestParam.getDiscount();
		String storeDelivery = storeCreateRequestParam.getIsStoreDelivery();

		String nationalShippingCharge = storeCreateRequestParam
				.getNationalShippingCharge();
		String nationalDelivery = storeCreateRequestParam.getNationRegion();
		String minNationalProcurementSLA = storeCreateRequestParam
				.getMinNationalProcurementSLA();
		String maxNationalProcurementSLA = storeCreateRequestParam
				.getMaxNationalProcurementSLA();

		String zonalShippingCharge = storeCreateRequestParam
				.getZonalShippingCharge();
		String zonalDelivery = storeCreateRequestParam.getZonalRegion();
		String minZonalProcurementSLA = storeCreateRequestParam
				.getMinZonalProcurementSLA();
		String maxZonalProcurementSLA = storeCreateRequestParam
				.getMaxZonalProcurementSLA();

		String localShippingCharge = storeCreateRequestParam
				.getLocalShippingCharge();
		String localDelivery = storeCreateRequestParam.getLocalRegion();
		String minLocalProcurementSLA = storeCreateRequestParam
				.getMinLocalProcurementSLA();
		String maxLocalProcurementSLA = storeCreateRequestParam
				.getMaxLocalProcurementSLA();

		Store store = null;

		try {
			store = storeService.createStore(dealerId, storeName, desc,
					templateId, productIds, mrp, sellingPrice, listingStatus,
					fulfilledBy, stockCount, minQtyBuy, tax, mbgShare,
					discount, storeDelivery, nationalShippingCharge,
					nationalDelivery, minNationalProcurementSLA,
					maxNationalProcurementSLA, zonalShippingCharge,
					zonalDelivery, minZonalProcurementSLA,
					maxZonalProcurementSLA, localShippingCharge, localDelivery,
					minLocalProcurementSLA, maxLocalProcurementSLA);
		} catch (Exception e) {
			throw new MBGAppException("Error occured during store creation", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(store).build();
	}

	/**
	 * Method to update store information
	 * 
	 * @param dealerId
	 * @param storeId
	 * @param storeUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("/{sid}")
	public Response updateStoreInformation(
			@PathParam("dealerId") String dealerId,
			@PathParam("sid") String storeId,
			StoreUpdateRequestParam storeUpdateRequestParam) throws Exception {

		Store store = null;
		String storeName = storeUpdateRequestParam.getStoreName();
		String subCategory = storeUpdateRequestParam.getSubCategory();
		String brands = storeUpdateRequestParam.getBrands();

		try {
			store = storeService.updateStoreInformation(dealerId, storeId,
					storeName, subCategory, brands);
		} catch (Exception e) {
			throw new Exception("Updating store information failed");
		}
		return Response.ok(store).build();
	}

	/**
	 * Method to update store product pricing.
	 * 
	 * @param dealerId
	 * @param storeId
	 * @param productId
	 * @param storeUpdateRequestParam
	 * @return
	 */
	@PUT
	@Path("/products/{pid}")
	public Response updateStoreProductInformation(
			@PathParam("dealerId") String dealerId,
			@PathParam("pid") String productId,
			StoreUpdateRequestParam storeUpdateRequestParam) {

		String storeName = storeUpdateRequestParam.getStoreName();
		String desc = storeUpdateRequestParam.getDesc();
		String templateId = storeUpdateRequestParam.getTemplateId();
		String sellingPrice = storeUpdateRequestParam.getSellingPrice();
		String listingStatus = storeUpdateRequestParam.getListingStatus();
		String fulfilledBy = storeUpdateRequestParam.getFulfilledBy();
		String nationalShippingCharge = storeUpdateRequestParam
				.getNationalShippingCharge();
		String zonalShippingCharge = storeUpdateRequestParam
				.getZonalShippingCharge();
		String localShippingCharge = storeUpdateRequestParam
				.getLocalShippingCharge();
		String minNationalProcurementSLA = storeUpdateRequestParam
				.getMinNationalProcurementSLA();
		String maxNationalProcurementSLA = storeUpdateRequestParam
				.getMaxNationalProcurementSLA();
		String minZonalProcurementSLA = storeUpdateRequestParam
				.getMinZonalProcurementSLA();
		String maxZonalProcurementSLA = storeUpdateRequestParam
				.getMaxZonalProcurementSLA();
		String minLocalPocurementSLA = storeUpdateRequestParam
				.getMinLocalProcurementSLA();
		String maxLocalPocurementSLA = storeUpdateRequestParam
				.getMaxLocalProcurementSLA();
		String minQuantityBuy = storeUpdateRequestParam.getMinQuantityBuy();
		String stockCount = storeUpdateRequestParam.getStockCount();
		String tax = storeUpdateRequestParam.getTax();
		String mbgShare = storeUpdateRequestParam.getMbgShare();
		String localRegion = storeUpdateRequestParam.getLocalRegion();
		String zonalRegion = storeUpdateRequestParam.getZonalRegion();
		String nationRegion = storeUpdateRequestParam.getNationRegion();
		String discount = storeUpdateRequestParam.getDiscount();
		String storeDelivery = storeUpdateRequestParam.getWillYouDeliver();

		StoreProductPricing storeProduct = null;

		try {
			storeProduct = storeService.updateStoreProductInformation(dealerId,
					productId, storeName, desc, templateId, sellingPrice,
					listingStatus, fulfilledBy, nationalShippingCharge,
					zonalShippingCharge, localShippingCharge,
					minNationalProcurementSLA, maxNationalProcurementSLA,
					minZonalProcurementSLA, maxZonalProcurementSLA,
					minLocalPocurementSLA, maxLocalPocurementSLA,
					minQuantityBuy, stockCount, tax, mbgShare, localRegion,
					zonalRegion, nationRegion, discount, storeDelivery);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured during StoreProductPricing update", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		return Response.ok(storeProduct).build();
	}

	/**
	 * Method for get promotion for store.
	 * 
	 * @param dealerId
	 * @param sid
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{sid}/promotions")
	public Response getPromotionForStore(
			@PathParam("dealerId") String dealerId, @PathParam("sid") String sid)
			throws MBGAppException {
		List<Promotion> promotion = null;
		try {
			promotion = userService.getPromotionForStore(dealerId, sid);
		} catch (Exception e) {
			logger.info("Error occured during finding promotion for store");
			throw new MBGAppException(
					"Error occured during finding promotion for store", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(promotion).build();
	}

	/**
	 * Method to get count of promotions for the store
	 * 
	 * @param dealerid
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("{sid}/promotions/count")
	public Response getPromotionCountForStore(
			@PathParam("dealerId") String dealerid, @PathParam("sid") String sid)
			throws Exception {
		Long count = null;
		try {
			count = userService.getPromotionCountForUser(dealerid, sid);
		} catch (Exception e) {
			logger.info("Error occured during counting promotion for store");
			throw new MBGAppException(
					"Error occured during counting promotion for store", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(count).build();
	}

	/**
	 * Method for create promotion for user store.
	 * 
	 * @param dealerId
	 * @param sid
	 * @param promotionCreateRequestParam
	 * @return
	 * @throws ParseException
	 * @throws MBGAppException
	 */
	@POST
	@Path("/{sid}/promotions")
	public Response createPromotionForUserStore(
			@PathParam("dealerId") String dealerId,
			@PathParam("sid") String sid,
			PromotionCreateRequestParam promotionCreateRequestParam)
			throws ParseException, MBGAppException {
		String promotionName = promotionCreateRequestParam.getPromotionName();
		String desc = promotionCreateRequestParam.getDesc();
		String promotionType = promotionCreateRequestParam.getPromotionType();
		Set<String> productIds = promotionCreateRequestParam.getProductIds();
		String discount = promotionCreateRequestParam.getDiscount();
		String sDate = promotionCreateRequestParam.getStartDate();
		String eDate = promotionCreateRequestParam.getEndDate();
		Promotion promotion = null;
		try {
			promotion = userService.createPromotionForUserStore(dealerId, sid,
					promotionName, desc, promotionType, productIds, discount,
					sDate, eDate);
		} catch (Exception e) {
			logger.info("Error occured during create promotion for store");
			throw new MBGAppException(
					"Error occured during create promotion for store", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(promotion).build();
	}

	/**
	 * Method to update the promotion for the store
	 * 
	 * @param dealerid
	 * @param storeid
	 * @param promoid
	 * @param storePromotionUpdateParam
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("{sid}/promotions/{promid}")
	public Response updateStorePromotion(
			@PathParam("dealerId") String dealerid,
			@PathParam("sid") String storeid,
			@PathParam("promid") String promoid,
			StorePromotionUpdateRequestParam storePromotionUpdateParam)
			throws Exception {
		Promotion promotion = null;
		String pName = storePromotionUpdateParam.getpName();
		String desc = storePromotionUpdateParam.getDesc();
		String pType = storePromotionUpdateParam.getpType();
		String discount = storePromotionUpdateParam.getDiscount();
		Set<String> productIds = storePromotionUpdateParam.getProIds();
		String sDate = storePromotionUpdateParam.getsDate();
		String eDate = storePromotionUpdateParam.geteDate();
		try {
			promotion = userService.updateStorePromotion(dealerid, storeid,
					promoid, pName, desc, pType, productIds, discount, sDate,
					eDate);
		} catch (Exception e) {
			logger.info("unable to update store promotion");
			throw new MBGAppException("unable to update store promotion", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(promotion).build();
	}

	/**
	 * Method for delete Promotion For user store.
	 * 
	 * @param dealerId
	 * @param sid
	 * @param promid
	 * @return
	 * @throws MBGAppException
	 */
	@DELETE
	@Path("/{sid}/promotions/{promid}")
	public Response deletePromotionForUserStore(
			@PathParam("dealerId") String dealerId,
			@PathParam("sid") String sid, @PathParam("promid") String promid)
			throws MBGAppException {
		String promId = null;
		try {
			promId = userService.deletePromotionForUserStore(dealerId, sid,
					promid);
		} catch (Exception e) {
			logger.info("unable to delete store promotion");
			throw new MBGAppException("unable to delete store promotion", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(promId).build();
	}

	/**
	 * method for get linked products count.
	 * 
	 * @param dealerId
	 * @return
	 */
	@GET
	@Path("/products/count")
	public Response getLinkedProductsCount(
			@PathParam("dealerId") String dealerId) {
		long count = 0;
		CountResponse countResponse = new CountResponse();
		try {
			count = userService.getLinkedProductsCount(dealerId);
			countResponse.setLinkedProductCount(count);
		} catch (Exception e) {
			logger.info("Error in getting the Linked products count");
			throw new MBGAppException(
					"Error in getting the Linked products count", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(countResponse).build();
	}

	/**
	 * method for unlink product from store.
	 * 
	 * @param dealerId
	 * @param productLinkOrUnLinkResponse
	 * @return
	 */
	@PUT
	@Path("/products/unlink")
	public Response unlinkProducts(@PathParam("dealerId") String dealerId,
			ProductLinkOrUnLinkResponse productLinkOrUnLinkResponse) {
		Product product = null;
		Set<String> prodIds = productLinkOrUnLinkResponse.getProdIds();
		try {
			product = userService.unlinkProduct(dealerId, prodIds);

		} catch (Exception e) {
			logger.info("unable to delete product in store ");
			throw new MBGAppException("unable to delete product in store ", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(product).build();
	}

	/**
	 * method for link products to Store.
	 * 
	 * @param dealerId
	 * @param productLinkOrUnLinkResponse
	 * @return
	 */
	@PUT
	@Path("/products/link")
	public Response linkProduct(@PathParam("dealerId") String dealerId,
			ProductLinkOrUnLinkResponse productLinkOrUnLinkResponse,
			@Context SecurityContext securityContext) {

		Product product = null;
		Set<String> prodIds = productLinkOrUnLinkResponse.getProdIds();
		try {
			product = userService.linkProduct(dealerId, prodIds);
		} catch (Exception e) {
			logger.info("unable to link the product to store");
			throw new MBGAppException("unable to link the product to store", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(product).build();
	}

	protected static ApiUser getApiUserFromSecurityContext(
			SecurityContext securityContext) {

		if (securityContext != null) {

			ApiUser apiUser = (ApiUser) securityContext.getUserPrincipal();
			return apiUser;
		}
		return null;
	}
}
