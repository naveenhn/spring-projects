/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.resource;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.geo.Distance;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.dashboard.response.DashBoardProjectResponsesCount;
import com.sarvah.mbg.dashboard.response.DashboardBidProjectResponse;
import com.sarvah.mbg.dashboard.response.DashboardConstructionsCount;
import com.sarvah.mbg.dashboard.response.DashboardOnboardedRoleCount;
import com.sarvah.mbg.dashboard.response.DashboardOrdersCount;
import com.sarvah.mbg.dashboard.response.DashboardPostResponsesCount;
import com.sarvah.mbg.dashboard.response.DashboardProductBrandResponse;
import com.sarvah.mbg.dashboard.response.DashboardProductDetailResponse;
import com.sarvah.mbg.dashboard.response.DashboardProductResponse;
import com.sarvah.mbg.dashboard.response.DashboardProjectResponses;
import com.sarvah.mbg.dashboard.response.DashboardTeleUpdatedLeadsResponse;
import com.sarvah.mbg.dashboard.response.DashboardTotalSavings;
import com.sarvah.mbg.dashboard.response.UserCountOnPackage;
import com.sarvah.mbg.dashboard.service.DashboardService;
import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductStatus;
import com.sarvah.mbg.domain.mongo.dashboard.ProductNotOnBoardedNameStore;
import com.sarvah.mbg.domain.mongo.marketing.Banner;
import com.sarvah.mbg.rest.exception.MBGAppException;
import com.sarvah.mbg.userprofile.auth.model.ApiUser;

/**
 * @author shivu
 *
 */
@Component
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserDashBoardResource {
	private static final Logger logger = LoggerFactory
			.getLogger(UserDashBoardResource.class);

	@Autowired
	private DashboardService dashboardService;

	/**
	 * method for how to bid the project.
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/dashboard/howtobidproject")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response howToBidProjects(@Context SecurityContext securityContext)
			throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		try {
			List<DashboardBidProjectResponse> howToBidProjectResponseList = dashboardService
					.getHowToBidProjectResponse();
			return Response.ok(howToBidProjectResponseList).build();
		} catch (Exception e) {
			throw new MBGAppException("Error occured during howtobidproject",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method for how to onboard the product.
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/dashboard/howtoonboardproduct")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response howToOnBoardProduct(@Context SecurityContext securityContext)
			throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		DashboardBidProjectResponse howToBidProjectResponse = new DashboardBidProjectResponse();
		try {
			howToBidProjectResponse = dashboardService.getHowToOnBoardProduct();
			return Response.ok(howToBidProjectResponse).build();
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured during how to on board a product", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method for how to bid video.
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/dashboard/howtobidvideo")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response howToBidVideo(@Context SecurityContext securityContext)
			throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		DashboardBidProjectResponse howToBidProjectResponse = new DashboardBidProjectResponse();
		try {
			howToBidProjectResponse = dashboardService.getHowToBidVideo();
			return Response.ok(howToBidProjectResponse).build();
		} catch (Exception e) {
			throw new MBGAppException("Error occured during howtobidVideo", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method for cuurent offers
	 * 
	 * @param page
	 * @param size
	 * @param sortList
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/dashboard/currentoffers")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response ongoingpromotions(
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("10") @QueryParam("size") int size,
			@DefaultValue("createdDate,desc") @QueryParam("sort") List<String> sortList,
			@QueryParam("view") String view,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		try {
			List<Product> productList = dashboardService.ongoingpromotions(
					page, size, getSort(sortList));
			return Response.ok(productList).build();
		} catch (Exception e) {
			throw new MBGAppException("Error occured during ongoingpromotions",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method for display product count based on status.
	 * 
	 * @param page
	 * @param size
	 * @param sortList
	 * @param uid
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{uid}/dashboard/productstatus")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response productStatus(
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("10") @QueryParam("size") int size,
			@DefaultValue("createdDate,desc") @QueryParam("sort") List<String> sortList,
			@PathParam("uid") String uid,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		try {
			String inMbg = "IN_MBG";
			String rejected = "REJECTED";
			String waitingApproval = "APPROVAL_PENDING";
			com.sarvah.mbg.dashboard.response.DashBoardStatusCount dashBoardProductStatus = dashboardService
					.getDashBoardProductStatus(uid, inMbg, rejected,
							waitingApproval);
			return Response.ok(dashBoardProductStatus).build();
		} catch (Exception e) {
			throw new MBGAppException("Error occured during status", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

	}

	/**
	 * method for get user order count based on status.
	 * 
	 * @param page
	 * @param size
	 * @param sortList
	 * @param uid
	 * @param view
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{uid}/dashboard/myorders")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response myOrders(
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("10") @QueryParam("size") int size,
			@DefaultValue("createdDate,desc") @QueryParam("sort") List<String> sortList,
			@PathParam("uid") String uid, @QueryParam("view") String view,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		try {
			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("DealerOrdersView")) {
				DashboardOrdersCount dashboardMyordersCount = dashboardService
						.getDealerOrders(uid);
				return Response.ok(dashboardMyordersCount).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminOrdersView")) {
				DashboardOrdersCount dashboardMyordersCount = dashboardService
						.getAllOrders(uid);
				return Response.ok(dashboardMyordersCount).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminOrdersSummary")) {
				DashboardOrdersCount dashboardMyordersCount = dashboardService
						.getAllOrdersSummary(uid);
				return Response.ok(dashboardMyordersCount).build();
			} else {
				DashboardOrdersCount dashboardMyordersCount = dashboardService
						.getMyOrders(uid);
				return Response.ok(dashboardMyordersCount).build();
			}
		} catch (Exception e) {
			throw new MBGAppException("Error occured during getting myorders",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method for new arrivals
	 * 
	 * @param page
	 * @param size
	 * @param sortList
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/dashboard/newarrivals")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response newArrivals(
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("10") @QueryParam("size") int size,
			@DefaultValue("createdDate,desc") @QueryParam("sort") String sortStr,
			@QueryParam("view") String view,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("newArrivedBrands")) {
			List<Order> orders = new ArrayList<Order>();
			String sortArray[] = sortStr.split(",");
			if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
				orders.add(new Order(Direction.DESC, sortArray[0]));
			else
				orders.add(new Order(Direction.ASC, sortArray[0]));

			Sort sort = new Sort(orders);

			try {
				List<DashboardProductBrandResponse> productBrandList = dashboardService
						.getNewArrivedBrands(page, size, sort);
				return Response.ok(productBrandList).build();
			} catch (Exception e) {
				throw new MBGAppException(
						"Error occured during getting newly Arrived brands", e,
						e.getMessage(), Status.BAD_REQUEST.getStatusCode(),
						1000);
			}
		} else {

			List<Order> orders = new ArrayList<Order>();
			String sortArray[] = sortStr.split(",");
			if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
				orders.add(new Order(Direction.DESC, sortArray[0]));
			else
				orders.add(new Order(Direction.ASC, sortArray[0]));

			Sort sort = new Sort(orders);

			try {
				List<DashboardProductDetailResponse> productList = dashboardService
						.getNewArrivals(page, size, sort);
				return Response.ok(productList).build();
			} catch (Exception e) {
				throw new MBGAppException(
						"Error occured during getting new Arrivals", e,
						e.getMessage(), Status.BAD_REQUEST.getStatusCode(),
						1000);
			}
		}
	}

	/**
	 * method to get responses for my post
	 * 
	 * @param page
	 * @param size
	 * @param sortList
	 * @param uid
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{uid}/dashboard/responsesformypost")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response responsesformypost(
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("10") @QueryParam("size") int size,
			@DefaultValue("createdDate,desc") @QueryParam("sort") List<String> sortList,
			@PathParam("uid") String uid,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		try {
			DashboardPostResponsesCount myPostResponsesCountParent = new DashboardPostResponsesCount();
			myPostResponsesCountParent = dashboardService
					.getResponseForMyPost(uid);
			return Response.ok(myPostResponsesCountParent).build();
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured during getting Responses for MyPost", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method for get particular dealers promotion
	 * 
	 * @param page
	 * @param size
	 * @param uid
	 * @param view
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{uid}/dashboard/mypromotions")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getDashboardPromotionForStore(
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("10") @QueryParam("size") int size,
			@PathParam("uid") String uid, @QueryParam("view") String view,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		try {
			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("mycurrentBanners")) {
				List<Banner> bannersList = dashboardService
						.getMyCurrentBanners(uid);
				return Response.ok(bannersList).build();
			} else {
				List<Product> promotion = dashboardService
						.getMyCurrentPromotions(page, size, uid);

				return Response.ok(promotion).build();
			}
		} catch (Exception e) {
			logger.info("Error occured during finding store promotion ");
			throw new MBGAppException(
					"Error occured during finding store promotion  ", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

	}

	/**
	 * method for display user bids
	 * 
	 * @param page
	 * @param size
	 * @param uid
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{uid}/dashboardprojectresponses/count")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getResponsesForMyBids(
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("10") @QueryParam("size") int size,
			@PathParam("uid") String uid,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		DashBoardProjectResponsesCount DashBoardProjectResponses = new DashBoardProjectResponsesCount();
		try {
			DashBoardProjectResponses = dashboardService.getResponsesForMyBids(
					page, size, uid);
		} catch (Exception e) {
			logger.info("Error occured during finding response count");
			throw new MBGAppException(
					"Error occured during finding response count", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(DashBoardProjectResponses).build();
	}

	/**
	 * method for display user project responses.
	 * 
	 * @param page
	 * @param size
	 * @param uid
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{uid}/dashboard/projectresponses")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getAllResponsesForMyBids(
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("10") @QueryParam("size") int size,
			@PathParam("uid") String uid,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		List<DashboardProjectResponses> dashboardProjectResponsesList = null;
		try {
			dashboardProjectResponsesList = dashboardService
					.getAllProjectResponses(page, size, uid);
		} catch (Exception e) {
			logger.info("Error occured during finding project Responses List");
			throw new MBGAppException(
					"Error occured during finding project Responses List", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		return Response.ok(dashboardProjectResponsesList).build();

	}

	/**
	 * method for display all active project bids.
	 * 
	 * @param page
	 * @param size
	 * @return
	 * @throws MBGAppException
	 */

	@GET
	@Path("/dashboard/projectsbids")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getprojectsbids(
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("10") @QueryParam("size") int size,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		List<Project> projectList = null;
		try {
			projectList = dashboardService.getAllProjectBids(page, size);
		} catch (Exception e) {
			logger.info("Error occured during finding projects bids Responses List");
			throw new MBGAppException(
					"Error occured during finding projects bids  Responses List",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		return Response.ok(projectList).build();

	}

	/**
	 * method for display the projects constructed around me.
	 * 
	 * @param page
	 * @param size
	 * @param uid
	 * @param distanceval
	 * @param type
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{uid}/dashboard/typeofconstructionaroundme")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getTypeOfConstructionsAroundme(
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("10") @QueryParam("size") int size,
			@PathParam("uid") String uid,
			@DefaultValue("5") @QueryParam("distance") double distanceval,
			@QueryParam("type") ProjectType type,
			@Context SecurityContext securityContext) throws MBGAppException {
		List<DashboardConstructionsCount> dashboardConstructionsCount = new ArrayList<>();
		Distance distance = new Distance(distanceval);

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		try {
			dashboardConstructionsCount = dashboardService
					.getAllTypeOfConstructionsAroundMe(page, size, uid,
							distance, type);
			return Response.ok(dashboardConstructionsCount).build();
		} catch (Exception e) {
			logger.info("Error occured during finding type of construction around me");
			throw new MBGAppException(
					"Error occured during finding type of construction around me",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method for display top five product.
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("dashboard/top5products")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getTop5Products(@Context SecurityContext securityContext)
			throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		List<DashboardProductResponse> dashboardProductResponseList = new ArrayList<>();
		try {
			dashboardProductResponseList = dashboardService.getTop5Products();
			return Response.ok(dashboardProductResponseList).build();
		} catch (Exception e) {
			logger.info("Error occured during finding top 5 products");
			throw new MBGAppException(
					"Error occured during finding top 5 products", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

	}

	/**
	 * method for get onboarded products count by role.
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/dashboard/onboardedproductsbyrole")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getOnboardedProductsByRole(
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		DashboardOnboardedRoleCount dashboardOnboardedRoleCount = new DashboardOnboardedRoleCount();
		try {
			dashboardOnboardedRoleCount = dashboardService.getOnboardedByRole();
			return Response.ok(dashboardOnboardedRoleCount).build();
		} catch (Exception e) {
			logger.info("Error occured during finding Onboarded by role");
			throw new MBGAppException(
					"Error occured during finding on boarded by role", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

	}

	/**
	 * method for display projects bided user count.
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/dashboard/projectupdates")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getProjectUpdates(@Context SecurityContext securityContext)
			throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		DashboardPostResponsesCount dashboardPostResponsesCount = new DashboardPostResponsesCount();
		try {
			dashboardPostResponsesCount = dashboardService
					.getAllProjectUpdates();
			return Response.ok(dashboardPostResponsesCount).build();
		} catch (Exception e) {
			logger.info("Error occured during finding projectt updates");
			throw new MBGAppException(
					"Error occured during finding project updates", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

	}

	/**
	 * method for display products count based on role.
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/dashboard/allproductsstatus")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response allProductStatus(@Context SecurityContext securityContext)
			throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		try {
			String inMbg = "IN_MBG";
			String rejected = "REJECTED";
			String waitingApproval = "APPROVAL_PENDING";
			String saved = ProductStatus.SAVED.toString();
			String deactivated = ProductStatus.DEACTIVATED.toString();

			com.sarvah.mbg.dashboard.response.DashBoardStatusCount dashBoardProductStatus = dashboardService
					.getAllProductsStatus(inMbg, rejected, waitingApproval,
							saved, deactivated);
			return Response.ok(dashBoardProductStatus).build();
		} catch (Exception e) {
			throw new MBGAppException("Error occured during status", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method for displaying all construction projects.
	 * 
	 * @param page
	 * @param size
	 * @param distanceval
	 * @param type
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/dashboard/constructionprojects")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getAllConstructions(
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("10") @QueryParam("size") int size,
			@DefaultValue("5") @QueryParam("distance") double distanceval,
			@QueryParam("type") ProjectType type,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		List<DashboardConstructionsCount> dashboardConstructionsCount = new ArrayList<>();
		Distance distance = new Distance(distanceval);
		String roleName = apiUser.getMbgRoles();
		try {
			dashboardConstructionsCount = dashboardService.getAllConstructions(
					page, size, distance, type, roleName);
			return Response.ok(dashboardConstructionsCount).build();
		} catch (Exception e) {
			logger.info("Error occured during finding type of construction ");
			throw new MBGAppException(
					"Error occured during finding type of construction ", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method for display launch website url
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/dashboard/launchawebsite")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response launchWebsite(@Context SecurityContext securityContext)
			throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		try {
			List<DashboardBidProjectResponse> howToBidProjectResponseList = dashboardService
					.launchWebSite();
			return Response.ok(howToBidProjectResponseList).build();
		} catch (Exception e) {
			throw new MBGAppException("Error occured during launch a website ",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method to display mostviewed products
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/dashboard/mostviewd")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response mostviewed(@Context SecurityContext securityContext)
			throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		try {
			List<DashboardProductDetailResponse> productList = dashboardService
					.mostViewed();
			return Response.ok(productList).build();
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured during searching the mostviewed products  ",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method to display most viewed produts by brands
	 * 
	 * @param brand
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/dashboard/mostviewdbybrand")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response mostviewedByBrand(@QueryParam("brand") String brand,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		try {
			List<Product> productList = dashboardService
					.mostViewedByBrand(brand);

			return Response.ok(productList).build();
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured during searching the mostviewed products  ",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method to display user count based on role.
	 * 
	 * @param roleName
	 * @return
	 */
	@GET
	@Path("/dashboard/usercountonroles")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getCountOfUserOnRoles(
			@QueryParam("roleName") String roleName,
			@Context SecurityContext securityContext) {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		try {
			UserCountOnPackage userCount = dashboardService
					.getUserCountOnPackage(roleName);
			return Response.ok(userCount).build();
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured during getting count of users on package",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * Method to get count of registered/active/inactive user based on role
	 * 
	 * @param roleName
	 * @return
	 */
	@GET
	@Path("/dashboard/userregistrationroles")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getRegisteredUserCountOnRoles(
			@QueryParam("roleName") String roleName,
			@QueryParam("view") String view,
			@Context SecurityContext securityContext) {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		UserCountOnPackage userCount = null;
		try {
			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("ServiceProvidersCount")) {
				userCount = dashboardService
						.getRegisteredServiceProvidersCountOnRoles();
			} else {
				userCount = dashboardService
						.getRegisteredUserCountOnRoles(roleName);
			}
			return Response.ok(userCount).build();
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured during getting count of users on package",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	private Sort getSort(List<String> sortList) {
		List<Order> orders = new ArrayList<Order>();
		for (String sortItem : sortList) {
			if (sortItem.split(",")[1].equalsIgnoreCase("desc"))
				orders.add(new Order(Direction.DESC, sortItem.split(",")[0]));
			else
				orders.add(new Order(Direction.ASC, sortItem.split(",")[0]));
		}
		return new Sort(orders);
	}

	protected static ApiUser getApiUserFromSecurityContext(
			SecurityContext securityContext) {

		if (securityContext != null) {

			ApiUser apiUser = (ApiUser) securityContext.getUserPrincipal();
			return apiUser;

		}

		return null;

	}

	/**
	 * Method to get total savings made by the user
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("{uid}/dashboard/totalsavings")
	public Response getMyTotalSavings(@PathParam("uid") String uid)
			throws Exception {

		DashboardTotalSavings totalSavings = null;
		try {
			totalSavings = dashboardService.getTotalSavings(uid);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured when trying to get total savings for you!!",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		return Response.ok(totalSavings).build();
	}

	@GET
	@Path("/productOrderedButNotOnBoarded")
	public Response getMostOrderedProductButNotOnBoarded(
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
		List<ProductNotOnBoardedNameStore> productNotOnBoardedNameStore = dashboardService
				.getMostOrderedProductButNotOnBoarded(page, size, sort);
		return Response.ok(productNotOnBoardedNameStore).build();
	}

	@GET
	@Path("/dashboard/teleTodayUpdatedLeads")
	public Response getTeleTodayUpdatedLeads() {
		List<DashboardTeleUpdatedLeadsResponse> productNotOnBoardedNameStore = dashboardService
				.getTeleTodayUpdatedLeads();
		return Response.ok(productNotOnBoardedNameStore).build();
	}

	@GET
	@Path("/dashboard/teleWeeklyUpdatedLeads")
	public Response getTeleWeeklyUpdatedLeads(
			@QueryParam("startDate") Long startDate,
			@QueryParam("endDate") Long endDate) {
		List<DashboardTeleUpdatedLeadsResponse> productNotOnBoardedNameStore = dashboardService
				.getTeleWeeklyUpdatedLeads(startDate, endDate);
		return Response.ok(productNotOnBoardedNameStore).build();
	}
}
