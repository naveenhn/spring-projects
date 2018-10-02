/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.ordermgmt.OrderStatement;
import com.sarvah.mbg.domain.ordermgmt.OrderStatus;
import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;
import com.sarvah.mbg.rest.authorization.MBGSecurityContext;
import com.sarvah.mbg.rest.catalog.model.OrderAddressUpdateRequestParam;
import com.sarvah.mbg.rest.exception.MBGAppException;
import com.sarvah.mbg.rest.model.Page;
import com.sarvah.mbg.rest.order.response.OrderAddressResponse;
import com.sarvah.mbg.rest.order.response.OrderResponse;
import com.sarvah.mbg.rest.order.response.UserOrderResponse;
import com.sarvah.mbg.userprofile.auth.model.ApiUser;
import com.sarvah.mbg.userprofile.ordermgmt.model.ItemStatusUpdateRequestParam;
import com.sarvah.mbg.userprofile.ordermgmt.model.OrderCreateRequestParam;
import com.sarvah.mbg.userprofile.ordermgmt.model.OrderStatementCreateRequestParam;
import com.sarvah.mbg.userprofile.ordermgmt.model.OrderStatusUpdateRequestParam;
import com.sarvah.mbg.userprofile.response.CustomerOrdersSummaryResponse;
import com.sarvah.mbg.userprofile.response.GetUserOrderItemsResponse;
import com.sarvah.mbg.userprofile.response.GetUserOrderResponse;
import com.sarvah.mbg.userprofile.response.ItemUpdateResponse;
import com.sarvah.mbg.userprofile.response.ManageOrderResponse;
import com.sarvah.mbg.userprofile.response.SuperAdminOrderViewDetails;
import com.sarvah.mbg.userprofile.response.TrackOrderStatusCount;
import com.sarvah.mbg.userprofile.response.TrackOrderSummary;
import com.sarvah.mbg.userprofile.service.UserOrderService;

/**
 * @author shivu
 *
 */
@Component
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserOrderResource {
	private static final Logger logger = LoggerFactory
			.getLogger(UserResource.class);

	@Autowired
	private UserOrderService userOrderService;

	/**
	 * Method for create order
	 * 
	 * @param orderCreateRequestParam
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/{userid}/orders")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response createOrder(@PathParam("userid") String userId,
			OrderCreateRequestParam orderCreateRequestParam,
			@Context SecurityContext securityContext) {

		logger.info(
				"Requesting to create an order for the user with userId : {}",
				userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		GetUserOrderResponse getUserOrderResponse = null;

		try {
			getUserOrderResponse = userOrderService.createOrder(userId,
					orderCreateRequestParam);
		} catch (Exception e) {
			throw new MBGAppException("Unable to create the order", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		logger.info(
				"Returning created order response for user with userId : {}",
				userId);

		return Response.ok(getUserOrderResponse).build();
	}

	/**
	 * method for count orders
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/{userId}/orders/count")
	public Response getOrderCount(@PathParam("userId") String userId,
			@Context SecurityContext securityContext) {
		logger.info("Requesting Orders count for the user with userId : {}",
				userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.debug("Logged in user information : {}" + apiUser);
		}

		Long orderCont;
		try {
			orderCont = userOrderService.getOrderCount(userId);
		} catch (Exception e) {
			throw new MBGAppException("Unable to count user orders", e,
					e.getMessage(),
					Status.INTERNAL_SERVER_ERROR.getStatusCode(), 1000);
		}
		logger.info("Returning Orders count for the user with userId : {}",
				userId);
		return Response.ok(orderCont).build();
	}

	/**
	 * method for update order status
	 * 
	 * @param userId
	 * @param orderId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("/{userId}/orders/{orderId}/status")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response updateOrderStatus(@PathParam("userId") String userId,
			@PathParam("orderId") String orderId,
			OrderStatusUpdateRequestParam orderStatusUpdateRequestParam,
			@Context SecurityContext securityContext) {
		logger.info(
				"Requesting to update order status for the user with userId : {}",
				userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);

		if (logger.isDebugEnabled()) {
			logger.debug("Logged in user information : {}" + apiUser);
		}

		OrderStatus orderStatus;
		try {
			String paymentMethodName = orderStatusUpdateRequestParam
					.getPaymentMethodName();
			String status = orderStatusUpdateRequestParam.getStatus();
			String paymentNumber = orderStatusUpdateRequestParam
					.getPaymentNumber();
			String paymentMetadata = orderStatusUpdateRequestParam
					.getPaymentMetadata();
			String paymenStatus = orderStatusUpdateRequestParam
					.getPaymentStatus();

			String paymentAmount = orderStatusUpdateRequestParam
					.getPaymentAmount();

			String paymentDetailsId = orderStatusUpdateRequestParam
					.getPaymentDetailsId();
			String chequeDate = orderStatusUpdateRequestParam.getChequeDate();
			String bankName = orderStatusUpdateRequestParam.getBankName();
			String chequeAmount = orderStatusUpdateRequestParam
					.getChequeAmount();

			orderStatus = userOrderService.updateOrderStatus(userId, orderId,
					paymentMethodName, status, paymentNumber, paymentMetadata,
					paymenStatus, paymentAmount, paymentDetailsId, chequeDate,
					bankName, chequeAmount);
		} catch (Exception e) {
			throw new MBGAppException("Unable to update order status", e,
					e.getMessage(),
					Status.INTERNAL_SERVER_ERROR.getStatusCode(), 1000);
		}
		logger.info(
				"Returning update order status response for the user with userId : {}",
				userId);
		return Response.ok(orderStatus).build();
	}

	@PUT
	@Path("/{userId}/orders")
	public Response updateMultipleOrdersPayment(
			@PathParam("userId") String userId,
			@QueryParam("orderIds") String orderIds,
			@QueryParam("paymentAmount") String paymentAmount,
			@QueryParam("paymentMetadata") String paymentMetadata) {
		User user = null;
		try {
			user = userOrderService.updateOrdersPayment(userId, orderIds,
					paymentAmount, paymentMetadata);
		} catch (Exception e) {
			throw new MBGAppException("Unable to update order(s) payment", e,
					e.getMessage(),
					Status.INTERNAL_SERVER_ERROR.getStatusCode(), 1000);
		}
		return Response.ok(user).build();
	}

	@PUT
	@Path("/{userId}/orders/{orderId}")
	public Response updateOrder(@PathParam("userId") String userId,
			@PathParam("orderId") String orderId,
			@QueryParam("invoiceNum") String invoiceNum) {
		String oId = userOrderService.updateOrder(userId, orderId, invoiceNum);
		return Response.ok(oId).build();
	}

	/**
	 * method for get order address
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/{userId}/orders/{orderId}/address")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getAddress(@PathParam("userId") String userId,
			@PathParam("orderId") String orderId,
			@Context SecurityContext securityContext) {
		logger.info(
				"Requesting to fetch ordered address of thr customer with customerId : {} and orderId : {}",
				userId, orderId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.debug("Logged in user information : {}" + apiUser);
		}

		OrderAddressResponse orderAddressResponse = new OrderAddressResponse();
		List<OrderAddress> orderAddress;
		try {
			orderAddress = userOrderService.getAddress(userId, orderId);
		} catch (Exception e) {
			throw new MBGAppException("Unable to get order address", e,
					e.getMessage(),
					Status.INTERNAL_SERVER_ERROR.getStatusCode(), 1000);
		}
		orderAddressResponse.setAddresses(orderAddress);
		logger.info(
				"Returning ordered address of the customer with customerId : {}",
				userId);
		return Response.ok(orderAddressResponse).build();
	}

	/**
	 * method for update user order address based on addressId.
	 * 
	 * @param userId
	 * @param orderId
	 * @param aid
	 * @param orderAddressUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("/{userId}/orders/{orderId}/address/{aid}")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response updateAddress(@PathParam("userId") String userId,
			@PathParam("orderId") String orderId, @PathParam("aid") String aid,
			OrderAddressUpdateRequestParam orderAddressUpdateRequestParam,
			@Context SecurityContext securityContext) {
		logger.info(
				"Requesting to update customer order address for customerId : {} and orderId : {}",
				userId, orderId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.debug("Logged in user information : {}" + apiUser);
		}

		String name = orderAddressUpdateRequestParam.getName();
		String contactPersonName = orderAddressUpdateRequestParam
				.getContactPersonName();
		String addressLine1 = orderAddressUpdateRequestParam.getAddressLine1();
		String addressLine2 = orderAddressUpdateRequestParam.getAddressLine2();
		String city = orderAddressUpdateRequestParam.getCity();
		String state = orderAddressUpdateRequestParam.getState();
		String country = orderAddressUpdateRequestParam.getCountry();
		String contactNo = orderAddressUpdateRequestParam.getContactNo();
		String pincode = orderAddressUpdateRequestParam.getPincode();
		OrderAddress orderAddress;
		try {
			orderAddress = userOrderService.updateAddress(userId, orderId,
					name, contactPersonName, aid, addressLine1, addressLine2,
					city, state, country, pincode, contactNo);
		} catch (Exception e) {
			throw new MBGAppException("Unable to delete user orderitem", e,
					e.getMessage(),
					Status.INTERNAL_SERVER_ERROR.getStatusCode(), 1000);
		}
		logger.info(
				"Returning update customer order address for customerId : {} and orderId :{}",
				userId, orderId);
		return Response.ok(orderAddress).build();
	}

	/**
	 * Method to get all Orders of particular user.
	 * 
	 * @param uid
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{userid}/orders")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response searchUserOrders(
			@PathParam("userid") String uid,
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("20") @QueryParam("size") int size,
			@DefaultValue("orderedDate,desc") @QueryParam("sort") String sortStr,
			@QueryParam("view") String view, @QueryParam("type") String type,
			@QueryParam("searchValue") String searchValue,
			@QueryParam("delivery") String delivery,
			@QueryParam("status") String status,
			@QueryParam("startDate") Long startDate,
			@QueryParam("endDate") Long endDate,
			@QueryParam("customerName") String customerName,
			@QueryParam("paymentDone") String paymentDone,
			@QueryParam("invoiceNum") String invoiceNum,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.debug("Logged in user information : {}" + apiUser);
		}

		UserOrderResponse userOrderResponse = new UserOrderResponse();
		OrderResponse orderResponse = new OrderResponse();
		try {
			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("CancelledOrders")) {
				logger.info(
						"Requesting to fetch Cancelled Orders for the CustomeerId : {}",
						uid);
				List<Order> orders = new ArrayList<Order>();
				String sortArray[] = sortStr.split(",");
				if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
					orders.add(new Order(Direction.DESC, sortArray[0]));
				else
					orders.add(new Order(Direction.ASC, sortArray[0]));

				Sort sort = new Sort(orders);
				TrackOrderSummary trackOrderSummary = userOrderService
						.getUserCancelledOrders(uid, page, size, sort);
				logger.info(
						"Returning Cancelled Orders for CustomerId : {}! Success",
						uid);
				return Response.ok(trackOrderSummary).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageOrders")) {
				logger.info("Requesting for AdminManageOrders");
				ManageOrderResponse manageOrderResponse = null;
				List<Order> orders = new ArrayList<Order>();
				String sortArray[] = sortStr.split(",");
				if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
					orders.add(new Order(Direction.DESC, sortArray[0]));
				else
					orders.add(new Order(Direction.ASC, sortArray[0]));

				Sort sort = new Sort(orders);
				manageOrderResponse = userOrderService
						.getSuperAdminManageOrders(uid, searchValue, delivery,
								status, startDate, endDate, customerName,
								paymentDone, page, size, sort);
				Page retPage = new Page();
				retPage.setTotalPages(manageOrderResponse.getTotalPages());
				retPage.setTotalElements(manageOrderResponse.getTotalElements());
				retPage.setNumber(manageOrderResponse.getNumber());
				retPage.setSize(manageOrderResponse.getSize());

				userOrderResponse.setPage(retPage);
				userOrderResponse.setOrders(manageOrderResponse
						.getOrderManageViewList());
				userOrderResponse.setOrderSummary(manageOrderResponse
						.getOrderSummary());
				logger.info("Returning AdminManageOrders response! Success");
				return Response.ok(userOrderResponse).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("DealerManageOrders")) {
				logger.info(
						"Requesting for DealerManageOrders for dealerId : {}",
						uid);
				List<Order> orders = new ArrayList<Order>();
				ManageOrderResponse manageOrderResponse = null;
				String sortArray[] = sortStr.split(",");
				if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
					orders.add(new Order(Direction.DESC, sortArray[0]));
				else
					orders.add(new Order(Direction.ASC, sortArray[0]));
				Sort sort = new Sort(orders);
				manageOrderResponse = userOrderService.getDealerManageOrders(
						uid, searchValue, delivery, status, page, size, sort);
				Page retPage = new Page();
				retPage.setTotalPages(manageOrderResponse.getTotalPages());
				retPage.setTotalElements(manageOrderResponse.getTotalElements());
				retPage.setNumber(manageOrderResponse.getNumber());
				retPage.setSize(manageOrderResponse.getSize());

				userOrderResponse.setPage(retPage);
				userOrderResponse.setOrders(manageOrderResponse
						.getOrderManageViewList());
				logger.info(
						"Returning DealerManageOrders response for dealerId : {}! Success",
						uid);
				return Response.ok(userOrderResponse).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("Ordersorting")) {
				logger.info("Requesting for Order sort");
				List<Order> orders = new ArrayList<Order>();
				String sortArray[] = sortStr.split(",");
				if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
					orders.add(new Order(Direction.DESC, sortArray[0]));
				else
					orders.add(new Order(Direction.ASC, sortArray[0]));
				Sort sort = new Sort(orders);

				TrackOrderSummary trackOrderSummary = userOrderService
						.getUserOrdersSorting(uid, type, page, size, sort);
				Page retPage = new Page();
				retPage.setTotalPages(trackOrderSummary.getTotalPages());
				retPage.setTotalElements(trackOrderSummary.getTotalElements());
				retPage.setNumber(trackOrderSummary.getNumber());
				retPage.setSize(trackOrderSummary.getSize());
				orderResponse.setPage(retPage);
				orderResponse.setOrders(trackOrderSummary.getOrderList());
				logger.info("Returning sorted order response! Success");
				return Response.ok(orderResponse).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AllItemsStatus")) {
				logger.info(
						"Requesting for items count based on item status for userId : {}",
						uid);
				TrackOrderStatusCount trackOrderStatusCount = userOrderService
						.getAllItemsStatus(uid);
				logger.info(
						"Returning items count based on item status for userId : {}! Success",
						uid);
				return Response.ok(trackOrderStatusCount).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("CustomerOrdersSummary")) {
				List<CustomerOrdersSummaryResponse> ordersSummary = userOrderService
						.getCustomerOrdersSummary(uid);
				return Response.ok(ordersSummary).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("InvoiceNumVerification")) {
				boolean invoiceNumVerify = userOrderService
						.verifyInvoiceNumber(invoiceNum);
				return Response.ok(invoiceNumVerify).build();
			} else {
				logger.info(
						"Requesting for customer orders based on customerId : {}",
						uid);
				List<Order> orders = new ArrayList<Order>();
				String sortArray[] = sortStr.split(",");
				if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
					orders.add(new Order(Direction.DESC, sortArray[0]));
				else
					orders.add(new Order(Direction.ASC, sortArray[0]));

				Sort sort = new Sort(orders);
				TrackOrderSummary trackOrderSummary = userOrderService
						.getUserOrders(uid, page, size, sort);

				Page retPage = new Page();
				retPage.setTotalPages(trackOrderSummary.getTotalPages());
				retPage.setTotalElements(trackOrderSummary.getTotalElements());
				retPage.setNumber(trackOrderSummary.getNumber());
				retPage.setSize(trackOrderSummary.getSize());

				orderResponse.setPage(retPage);
				orderResponse.setOrders(trackOrderSummary.getOrderList());
				logger.info(
						"Returning customer orders based on customerId : {}! Success",
						uid);
				return Response.ok(orderResponse).build();
			}
		} catch (Exception e) {
			throw new MBGAppException("Unable to search user orders", e,
					e.getMessage(),
					Status.INTERNAL_SERVER_ERROR.getStatusCode(), 1000);
		}
	}

	/**
	 * Get User Order based on order id.
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/{userid}/orders/{orderid}")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getUserOrder(@PathParam("userid") String userId,
			@PathParam("orderid") String orderId,
			@QueryParam("view") String view,
			@Context SecurityContext securityContext) {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.debug("Logged in user information : {}" + apiUser);
		}

		GetUserOrderResponse order = null;
		SuperAdminOrderViewDetails superAdminOrderViewDetails = new SuperAdminOrderViewDetails();
		try {
			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminOrderViewDetails")) {
				logger.info(
						"Requesting for AdminOrderViewDetails based on orderId : {} and customerId : {}",
						orderId, userId);
				superAdminOrderViewDetails = userOrderService
						.getSuperAdminOrderViewDetails(userId, orderId);
				logger.info(
						"Returning AdminOrderViewDetails response based on orderId : {} and customerId : {}! Success",
						orderId, userId);
				return Response.ok(superAdminOrderViewDetails).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("DealerOrderViewDetails")) {
				logger.info(
						"Requesting for DealerOrderViewDetails based on orderId : {} and dealerId : {}",
						orderId, userId);
				superAdminOrderViewDetails = userOrderService
						.getDealerOrderViewDetails(userId, orderId);
				logger.info(
						"Returning DealerOrderViewDetails response based on orderId : {} and dealerId : {}",
						orderId, userId);
				return Response.ok(superAdminOrderViewDetails).build();
			} else {
				logger.info(
						"Requesting for Fetch order based on orderId : {} and userId : {}",
						orderId, userId);
				order = userOrderService.getUserOrder(userId, orderId);
				logger.info(
						"Returning order based on orderId : {} and userId : {}! Success",
						orderId, userId);
				return Response.ok(order).build();
			}
		} catch (Exception e) {
			throw new MBGAppException("Unable to search user orders", e,
					e.getMessage(),
					Status.INTERNAL_SERVER_ERROR.getStatusCode(), 1000);
		}

	}

	/**
	 * Method to get user Order status
	 * 
	 * @param uid
	 * @param orderid
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{userid}/orders/{orderid}/status")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response searchUSerOrderStatus(@PathParam("userid") String uid,
			@PathParam("orderid") String orderId,
			@Context SecurityContext securityContext) throws MBGAppException {
		logger.info("Requesting for Fetch OrderStatus based on orderId : {}",
				orderId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);

		if (logger.isDebugEnabled()) {
			logger.debug("Logged in user information : {}" + apiUser);
		}
		OrderStatus orderStatus = null;
		try {
			orderStatus = userOrderService.getUserOrderStatus(uid, orderId);
		} catch (Exception e) {
			throw new MBGAppException("Unable to search user orderStatus", e,
					e.getMessage(),
					Status.INTERNAL_SERVER_ERROR.getStatusCode(), 1000);
		}
		logger.info("Return OrderStatus based on orderId : {}! Success",
				orderId);
		return Response.ok(orderStatus).build();
	}

	/**
	 * Method to get user Order status
	 * 
	 * @param uid
	 * @param orderid
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{userid}/orders/{orderid}/status/count")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getUSerOrderStatusCount(@PathParam("userid") String uid,
			@PathParam("orderid") String orderId,
			@Context SecurityContext securityContext) throws MBGAppException {
		logger.info(
				"Requesting for items count based on item status and orderId : {}",
				orderId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.debug("Logged in user information : {}" + apiUser);
		}

		TrackOrderStatusCount trackOrderStatusCount;
		try {
			trackOrderStatusCount = userOrderService.getUserOrderStatusCount(
					uid, orderId);
		} catch (Exception e) {

			throw new MBGAppException(
					"Unable to get items count based on ItemStatus", e,
					e.getMessage(),
					Status.INTERNAL_SERVER_ERROR.getStatusCode(), 1000);
		}

		logger.info(
				"Returning items count based on item status and orderId : {}! Success",
				orderId);
		return Response.ok(trackOrderStatusCount).build();
	}

	/**
	 * Method to delete user Order
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 */
	@DELETE
	@Path("/{userid}/orders/{orderid}/cancel")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response deleteUserOrder(@PathParam("userid") String userId,
			@PathParam("orderid") String orderId,
			@Context SecurityContext securityContext) {
		logger.info(
				"Requesting for cancel order based on orderId : {} userId : {}",
				orderId, userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.debug("Logged in user information : {}" + apiUser);
		}

		OrderStatus orderStatus = null;
		try {
			orderStatus = userOrderService.deleteUserOrder(userId, orderId);
		} catch (Exception e) {
			throw new MBGAppException("Unable to delete user order", e,
					e.getMessage(),
					Status.INTERNAL_SERVER_ERROR.getStatusCode(), 1000);
		}
		logger.info(
				"Returning cancel order response based on orderId : {} userId : {}! Success",
				orderId, userId);
		return Response.ok(orderStatus).build();
	}

	/**
	 * Method to search order items
	 * 
	 * @param uid
	 * @param orderid
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{userid}/orders/{orderid}/items")
	public Response searchUSerOrderItems(@PathParam("userid") String userId,
			@PathParam("orderid") String orderId,
			@Context SecurityContext securityContext) throws MBGAppException {
		logger.info(
				"Requesting for user order items based on orderId : {} and userId : {}",
				orderId, userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);

		if (logger.isDebugEnabled()) {
			logger.debug("Logged in user information : {}" + apiUser);
		}

		Set<GetUserOrderItemsResponse> itemDisplayResponseList = new HashSet<>();
		try {
			itemDisplayResponseList = userOrderService.getUserOrderItems(
					userId, orderId);
		} catch (Exception e) {
			throw new MBGAppException("unable to search user orderitems", e,
					e.getMessage(),
					Status.INTERNAL_SERVER_ERROR.getStatusCode(), 1000);
		}
		logger.info(
				"Returning user order items based on orderId : {} userId : {}! Success",
				orderId, userId);
		return Response.ok(itemDisplayResponseList).build();
	}

	@PUT
	@Path("/{userid}/orders/{orderid}/items")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response updateItemStatus(@PathParam("userid") String userId,
			@PathParam("orderid") String orderId,
			ItemStatusUpdateRequestParam itemStatusUpdateRequestParam,
			@Context SecurityContext securityContext) {
		logger.info(
				"Requesting for update items status based on orderId : {} and userId : {}",
				orderId, userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		logger.debug("Logged in user information : {}" + apiUser);

		ItemUpdateResponse itemStatus = null;
		String status = itemStatusUpdateRequestParam.getStatus();
		String note = itemStatusUpdateRequestParam.getNote();
		Set<String> itemsId = itemStatusUpdateRequestParam.getItemsId();
		try {
			itemStatus = userOrderService.updateItemsStatus(userId, orderId,
					itemsId, status, note);
		} catch (Exception e) {
			throw new MBGAppException("Unable to update user order item info",
					e, e.getMessage(),
					Status.INTERNAL_SERVER_ERROR.getStatusCode(), 1000);
		}
		logger.info(
				"Returning item status update response based on orderId : {} and userId : {}",
				orderId, userId);
		return Response.ok(itemStatus).build();
	}

	/**
	 * Method to delete user Order items
	 * 
	 * @param uid
	 * @param orderid
	 * @param itemid
	 * @return
	 * @throws MBGAppException
	 */
	@DELETE
	@Path("/{userid}/orders/{orderid}/items/{itemid}/cancel")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response deleteUserOrderItems(@PathParam("userid") String userId,
			@PathParam("orderid") String orderId,
			@PathParam("itemid") String itemId,
			@Context SecurityContext securityContext) throws MBGAppException {
		logger.info(
				"Requesting for cancel item based on itemId : {} orderId : {} and userId : {}",
				itemId, orderId, userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		logger.debug("Logged in user information : {}" + apiUser);

		ItemUpdateResponse itemStatus = null;
		try {
			itemStatus = userOrderService.deleteUserItem(userId, orderId,
					itemId);
		} catch (Exception e) {
			logger.info("Error occured during user orderitem delete");
			throw new MBGAppException("unable to delete user orderitem", e,
					e.getMessage(),
					Status.INTERNAL_SERVER_ERROR.getStatusCode(), 1000);
		}
		logger.info(
				"Returning cancel item response based on itemId : {} orderId : {} and userId : {}",
				itemId, orderId, userId);
		return Response.ok(itemStatus).build();
	}

	@POST
	@Path("/{userid}/orderStatements")
	public Response createOrderStatement(
			@PathParam("userid") String userId,
			List<OrderStatementCreateRequestParam> ordersStatementCreateRequestParam) {
		OrderStatement orderStatement;
		try {
			orderStatement = userOrderService
					.createOrderStatement(ordersStatementCreateRequestParam);
		} catch (Exception e) {
			throw new MBGAppException("Unable to create order statement", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(orderStatement).build();
	}

	@GET
	@Path("/{userid}/orderStatements")
	public Response getOrderStatement(@QueryParam("userId") String userId) {
		List<OrderStatement> orderStatements = userOrderService
				.getOrderStatements(userId);
		return Response.ok(orderStatements).build();
	}

	@PUT
	@Path("/{userid}/orderStatements/{orderStatementId}")
	public Response updateOrderStatement(@PathParam("userid") String userId,
			@PathParam("orderStatementId") String orderStatementId,
			OrderStatementCreateRequestParam orderStatementCreateRequestParam) {
		OrderStatement orderStatement = userOrderService.updateOrderStatement(
				orderStatementId, orderStatementCreateRequestParam);
		return Response.ok(orderStatement).build();
	}

	@DELETE
	@Path("/{userid}/orderStatements/{orderStatementId}")
	public Response deleteOrderStatement(
			@PathParam("orderStatementId") String orderStatementId) {
		OrderStatement orderStatement = userOrderService
				.delateOrderStatement(orderStatementId);
		return Response.ok(orderStatement).build();
	}
}
