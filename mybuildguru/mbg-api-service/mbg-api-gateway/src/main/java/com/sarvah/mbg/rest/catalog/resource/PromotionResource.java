/**
 * 
 */
package com.sarvah.mbg.rest.catalog.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.catalog.service.ProductCatalogService;
import com.sarvah.mbg.catalog.service.model.PromotionCountResponse;
import com.sarvah.mbg.catalog.service.model.PromotionDetailView;
import com.sarvah.mbg.catalog.service.model.PromotionResponse;
import com.sarvah.mbg.catalog.service.model.PromotionSummaryViewResponse;
import com.sarvah.mbg.domain.mongo.marketing.Promotion;
import com.sarvah.mbg.promotion.service.PromotionService;
import com.sarvah.mbg.rest.catalog.model.PromocodeCreateRequestParam;
import com.sarvah.mbg.rest.catalog.model.PromocodeUpdateRequestParam;
import com.sarvah.mbg.rest.catalog.model.PromotionSearchRequestParam;
import com.sarvah.mbg.rest.catalog.response.ManagePromotionResponse;
import com.sarvah.mbg.rest.exception.MBGAppException;
import com.sarvah.mbg.rest.model.Page;

/**
 * @author Shiva
 *
 */
@Component
@Path("/catalog/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PromotionResource {

	private static final Logger logger = LoggerFactory
			.getLogger(ProductResource.class);

	@Autowired
	private PromotionService promotionService;

	@Autowired
	private ProductCatalogService productCatalogService;

	/**
	 * Method to get all the promotions available in the system
	 * 
	 * @return
	 */
	@GET
	@Path("/promotions")
	public Response getAllPromotions(
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("20") @QueryParam("size") int size,
			@DefaultValue("createdDate,desc") @QueryParam("sort") String sortStr,
			@QueryParam("view") String view,
			@QueryParam("searchValue") String searchValue,
			@QueryParam("status") String status) {
		List<Promotion> promotions = null;
		ManagePromotionResponse managePromotionResponse = new ManagePromotionResponse();

		try {
			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManagePromotions")) {
				List<Order> orders = new ArrayList<Order>();
				String sortArray[] = sortStr.split(",");
				if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
					orders.add(new Order(Direction.DESC, sortArray[0]));
				else
					orders.add(new Order(Direction.ASC, sortArray[0]));

				Sort sort = new Sort(orders);

				PromotionSummaryViewResponse promotionSummaryView = promotionService
						.getAdminManagePromotions(searchValue, status, page,
								size, sort);
				Page retPage = new Page();

				retPage.setTotalPages(promotionSummaryView.getTotalPages());
				retPage.setTotalElements(promotionSummaryView
						.getTotalElements());
				retPage.setNumber(promotionSummaryView.getNumber());
				retPage.setSize(promotionSummaryView.getSize());
				managePromotionResponse.setPage(retPage);
				managePromotionResponse.setPromotions(promotionSummaryView
						.getPromotionSummaryView());
				return Response.ok(managePromotionResponse).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("GetAvailablePromotions")) {
				promotions = promotionService.getAllCurrentPromotions();
				return Response.ok(promotions).build();
			} else {
				promotions = promotionService.getAllPromotions();
				logger.info("getting all promotion");
				return Response.ok(promotions).build();
			}
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to search promotions", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * Method to get promotion by ID
	 * 
	 * @param promid
	 * @param view
	 * @return
	 */
	@GET
	@Path("/promotions/{promid}")
	public Response getPromotion(@PathParam("promid") String promid,
			@QueryParam("view") String view) {

		Response response = null;
		PromotionResponse promotionResponse = new PromotionResponse();
		PromotionDetailView promotion = null;

		try {

			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminPromotionViewDetails")) {
				PromotionDetailView productDetailView = promotionService
						.getPromotionAdminViewDetails(promid);
				response = Response.ok(productDetailView).build();
			} else {
				promotion = promotionService.getPromotionById(promid);
				promotionResponse.setPromotionDetailView(promotion);
				response = Response.ok(promotionResponse).build();
			}
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to search promotions", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return response;
	}

	/**
	 * Method for get promotion for the particular product.
	 * 
	 * @param prodId
	 * @param promotionSearchRequestParam
	 * @return
	 * @throws ParseException
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{pid}/promotion")
	public Response getPromotionForProduct(@PathParam("pid") String prodId,
			@BeanParam PromotionSearchRequestParam promotionSearchRequestParam)
			throws ParseException, MBGAppException {
		String pName = promotionSearchRequestParam.getPromotionName();
		String pType = promotionSearchRequestParam.getPromotionType();
		String discount = promotionSearchRequestParam.getDiscount();
		String sDate = promotionSearchRequestParam.getStartDate();
		String eDate = promotionSearchRequestParam.getEndDate();
		List<Promotion> promotion;
		try {
			promotion = promotionService.getPromotionForProduct(prodId, pName,
					pType, discount, sDate, eDate);
		} catch (Exception e) {
			throw new MBGAppException("unable to find promotion", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(promotion).build();
	}

	/**
	 * Method to get count of promotions for a product
	 * 
	 * @param pid
	 * @return
	 * @throws ParseException
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{pid}/promotion/count")
	public Response countPromotion(@PathParam("pid") String pid,
			@QueryParam("name") String name, @QueryParam("type") String type,
			@QueryParam("discount") String discount,
			@QueryParam("sDate") String sDate, @QueryParam("eDate") String eDate)
			throws ParseException, MBGAppException {

		Long promoCount = null;
		try {
			promoCount = promotionService.getPromotionCount(pid, name, type,
					discount, sDate, eDate);
		} catch (Exception e) {
			throw new MBGAppException("No data found", e, e.getMessage(),
					Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		return Response.ok(promoCount).build();
	}

	/**
	 * method for create promocode
	 * 
	 * @param promocodeCreateRequestParam
	 * @return
	 */
	@POST
	@Path("/promocode")
	public Response createPromocode(
			PromocodeCreateRequestParam promocodeCreateRequestParam) {
		Set<String> subCategories = promocodeCreateRequestParam
				.getSubCategories();
		Set<String> productIds = promocodeCreateRequestParam.getProductIds();
		Set<String> brands = promocodeCreateRequestParam.getBrands();
		String promoCode = promocodeCreateRequestParam.getPromoCode();
		String startDate = promocodeCreateRequestParam.getStartDate();
		String endDate = promocodeCreateRequestParam.getEndDate();
		String discount = promocodeCreateRequestParam.getDiscount();
		String desc = promocodeCreateRequestParam.getDesc();
		String promotionType = promocodeCreateRequestParam.getPromotionType();
		String promotionName = promocodeCreateRequestParam.getPromotionName();
		String status = promocodeCreateRequestParam.getStatus();
		try {
			Promotion promotion = promotionService.createPromoCode(
					subCategories, productIds, promoCode, startDate, endDate,
					discount, desc, promotionType, promotionName, status,
					brands);
			return Response.ok(promotion).build();
		} catch (Exception e) {
			throw new MBGAppException("unable to Create a promotion", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method for update promocode based on promocodeId.
	 * 
	 * @param promid
	 * @param promocodeUpdateRequestParam
	 * @return
	 */
	@PUT
	@Path("/promocode/{promid}")
	public Response updatePromocode(@PathParam("promid") String promid,
			PromocodeUpdateRequestParam promocodeUpdateRequestParam) {
		Set<String> subCategories = promocodeUpdateRequestParam
				.getSubCategories();
		Set<String> productIds = promocodeUpdateRequestParam.getProductIds();
		Set<String> brands = promocodeUpdateRequestParam.getBrands();
		String promoCode = promocodeUpdateRequestParam.getPromoCode();
		String startDate = promocodeUpdateRequestParam.getStartDate();
		String endDate = promocodeUpdateRequestParam.getEndDate();
		String discount = promocodeUpdateRequestParam.getDiscount();
		String desc = promocodeUpdateRequestParam.getDesc();
		String promotionType = promocodeUpdateRequestParam.getPromotionType();
		String promotionName = promocodeUpdateRequestParam.getPromotionName();
		String status = promocodeUpdateRequestParam.getStatus();

		try {
			Promotion promotion = promotionService.updatePromoCode(promid,
					subCategories, productIds, promoCode, startDate, endDate,
					discount, desc, promotionType, promotionName, status,
					brands);

			return Response.ok(promotion).build();
		} catch (Exception e) {
			throw new MBGAppException("unable to update a promotion", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method for delete promotion based on promotionId.
	 * 
	 * @param promid
	 * @return
	 */
	@DELETE
	@Path("/promocode/{promid}")
	public Response deletePromotion(@PathParam("promid") String promid) {

		try {
			Promotion promotion = promotionService.deletePromotion(promid);

			return Response.ok(promotion).build();
		} catch (Exception e) {
			throw new MBGAppException("unable to Delete a promotion", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method for get promotion count
	 * 
	 * @return
	 */
	@GET
	@Path("/promotions/count")
	public Response promotionsCount() {
		try {
			PromotionCountResponse PromotionCountResponse = promotionService
					.getAllPromotioscount();
			return Response.ok(PromotionCountResponse).build();
		} catch (Exception e) {
			throw new MBGAppException("unable to get the promotions count", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}
}
