/**
 * 
 */
package com.sarvah.mbg.rest.order.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.domain.ordermgmt.OrderStatus;
import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;
import com.sarvah.mbg.order.service.OrderService;
import com.sarvah.mbg.ordermgmt.response.GetOrderItemsResponseParent;
import com.sarvah.mbg.ordermgmt.response.GetOrdersResponseParent;
import com.sarvah.mbg.rest.authorization.MBGSecurityContext;
import com.sarvah.mbg.rest.catalog.model.OrderAddressUpdateRequestParam;
import com.sarvah.mbg.rest.exception.MBGAppException;
import com.sarvah.mbg.rest.order.response.OrderAddressResponse;
import com.sarvah.mbg.rest.order.response.OrderDetailsResponse;
import com.sarvah.mbg.userprofile.auth.model.ApiUser;

/**
 * The Class OrderResource.
 *
 * @author shivu
 */
@Component
@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(OrderResource.class);

	/** The order service. */
	@Autowired
	private OrderService orderService;

	/**
	 * method for get all orders.
	 *
	 * @return the order
	 */
	@GET
	public Response getOrder() {
		List<GetOrdersResponseParent> orders = null;
		try {
			orders = orderService.getOrder();
		} catch (Exception e) {
			throw new MBGAppException("Error occured during get order", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(orders).build();
	}

	/**
	 * method for get order addresses.
	 *
	 * @param orderId
	 *            the order id
	 * @return the address
	 */
	@GET
	@Path("/{orderId}/addresses")
	public Response getAddress(@PathParam("orderId") int orderId) {
		OrderAddressResponse orderAddressResponse = new OrderAddressResponse();
		List<OrderAddress> orderAddress = null;
		try {
			orderAddress = orderService.getAddress(orderId);
		} catch (Exception e) {
			throw new MBGAppException("Error occured during get order address",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		orderAddressResponse.setAddresses(orderAddress);
		return Response.ok(orderAddressResponse).build();
	}

	/**
	 * method for get order items, based on orderId.
	 *
	 * @param orderId
	 *            the order id
	 * @return the items
	 */
	@GET
	@Path("/{orderId}/items")
	public Response getItems(@PathParam("orderId") int orderId) {
		OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
		List<GetOrderItemsResponseParent> orderDetails = null;
		try {
			orderDetails = orderService.getItems(orderId);
		} catch (Exception e) {
			throw new MBGAppException("Error occured during get order items",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		orderDetailsResponse.setItems(orderDetails);
		return Response.ok(orderDetailsResponse).build();
	}

	// order new
	/**
	 * Method to get count of all orders in the system.
	 *
	 * @return the response
	 * @throws MBGAppException
	 *             the MBG app exception
	 */
	@GET
	@Path("/count")
	public Response countOrder() throws MBGAppException {
		Long count;
		try {
			count = orderService.countOrders();
		} catch (Exception e) {
			logger.info("Error occured trying to get order count");
			throw new MBGAppException("Error occured trying to get user count",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(count).build();
	}

	/**
	 * Method to get particular order.
	 *
	 * @param orderid
	 *            the orderid
	 * @return the order by id
	 * @throws MBGAppException
	 *             the MBG app exception
	 */
	@GET
	@Path("/{orderid}")
	public Response getOrderById(@PathParam("orderid") String orderid)
			throws MBGAppException {
		GetOrdersResponseParent order = new GetOrdersResponseParent();
		try {
			order = orderService.searchOrder(orderid);
		} catch (Exception e) {
			logger.info("Error occured during order search");
			throw new MBGAppException("Error occured during order search", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(order).build();
	}

	/**
	 * Method to get status of the particular order.
	 *
	 * @param orderid
	 *            the orderid
	 * @return the order status
	 * @throws MBGAppException
	 *             the MBG app exception
	 */
	@GET
	@Path("/{orderid}/status")
	public Response getOrderStatus(@PathParam("orderid") String orderid)
			throws MBGAppException {
		OrderStatus orderStatus = new OrderStatus();
		try {
			orderStatus = orderService.getOrderStatus(orderid);
		} catch (Exception e) {
			logger.info("Error occured during Orderstatus search");
			throw new MBGAppException(
					"Error occured during orderStatus search", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(orderStatus).build();
	}

	/**
	 * Method to update order status.
	 *
	 * @param orderid
	 *            the orderid
	 * @param status
	 *            the status
	 * @return the response
	 * @throws MBGAppException
	 *             the MBG app exception
	 */
	@PUT
	@Path("/{orderid}/status")
	public Response updateStatus(@PathParam("orderid") String orderid,
			@QueryParam("status") String status) throws MBGAppException {
		OrderStatus orderStatus = null;
		try {
			orderStatus = orderService.updateStatus(orderid, status);
		} catch (Exception e) {
			logger.info("Error occured trying to update status");
			throw new MBGAppException("Error occured trying to update status",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(orderStatus).build();
	}

	/**
	 * Method to delete Order.
	 *
	 * @param orderid
	 *            the orderid
	 * @return the response
	 * @throws MBGAppException
	 *             the MBG app exception
	 */
	@DELETE
	@Path("/{orderid}/cancel")
	public Response deleteOrder(@PathParam("orderid") String orderid)
			throws MBGAppException {
		String orderId;
		try {
			orderId = orderService.deleteOrder(orderid);
		} catch (Exception e) {
			logger.info("Error occured during order delete");
			throw new MBGAppException("Error occured during order delete", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(orderId).build();
	}

	/**
	 * Gets the invoice for order.
	 *
	 * @param orderId
	 *            the order id
	 * @return the invoice for order
	 */
	@GET
	@Path("/{orderid}/invoice")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	@Produces({ MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_JSON })
	public Response getInvoiceForOrder(@PathParam("orderid") String orderId,
			@QueryParam("get") String get, @Context HttpServletRequest request,
			@Context SecurityContext securityContext) {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		try {

			if (StringUtils.isNotBlank(get)
					&& get.equalsIgnoreCase("DealerInvoice")) {
				// Pass the logged in userId and orderId to get invoieUrl
				URL dealerInvoiceUrl = orderService.getDealerInvoiceUrl(
						orderId, apiUser.getUid());

				URLConnection urlConn = dealerInvoiceUrl.openConnection();

				if (!urlConn.getContentType().equalsIgnoreCase(
						"application/octet-stream")) {
					logger.error("FAILED.\n[Sorry. This is not a PDF.]"
							+ urlConn.getContentType());
					return Response.status(Status.BAD_REQUEST).build();
				} else {

					// read as stream and send to ui
					StreamingOutput stream = new StreamingOutput() {

						@Override
						public void write(OutputStream output)
								throws IOException, WebApplicationException {

							byte[] ba1 = new byte[1024];
							int baLength;

							// InputStream is1 = fileUrl.openStream();
							InputStream is1 = dealerInvoiceUrl.openStream();
							while ((baLength = is1.read(ba1)) != -1) {
								output.write(ba1, 0, baLength);
							}

							output.flush();
							is1.close();

						}
					};

					// set it to response

					ResponseBuilder responseBuilder = Response.ok(stream,
							MediaType.APPLICATION_OCTET_STREAM).header(
							"Content-Disposition",
							"attachment; filename=test.pdf");

					return responseBuilder.build();
				}

			} else {
				// get the url service class and replace here
				// URL fileUrl = new URL(
				// "https://mbgtest.blob.core.windows.net/files/catalog/product/D/Do/DomainModel.pdf");

				URL invoiceUrl = orderService.getInvoiceUrl(orderId,
						apiUser.getUid());

				URLConnection urlConn = invoiceUrl.openConnection();

				// URLConnection urlConn = fileUrl.openConnection();

				if (!urlConn.getContentType().equalsIgnoreCase(
						"application/octet-stream")) {
					logger.error("FAILED.\n[Sorry. This is not a PDF.]"
							+ urlConn.getContentType());
					return Response.status(Status.BAD_REQUEST).build();
				} else {

					// read as stream and send to ui
					StreamingOutput stream = new StreamingOutput() {

						@Override
						public void write(OutputStream output)
								throws IOException, WebApplicationException {

							byte[] ba1 = new byte[1024];
							int baLength;

							// InputStream is1 = fileUrl.openStream();
							InputStream is1 = invoiceUrl.openStream();
							while ((baLength = is1.read(ba1)) != -1) {
								output.write(ba1, 0, baLength);
							}

							output.flush();
							is1.close();

						}
					};

					// set it to response

					ResponseBuilder responseBuilder = Response.ok(stream,
							MediaType.APPLICATION_OCTET_STREAM).header(
							"Content-Disposition",
							"attachment; filename=test.pdf");

					return responseBuilder.build();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Response.status(Status.BAD_REQUEST).build();
	}

	/**
	 * Method to update order Items.
	 *
	 * @param orderid
	 *            the orderid
	 * @param itemid
	 *            the itemid
	 * @param qty
	 *            the qty
	 * @param status
	 *            the status
	 * @return the response
	 * @throws MBGAppException
	 *             the MBG app exception
	 */
	@PUT
	@Path("/{orderid}/items/{itemid}")
	public Response updateItems(@PathParam("orderid") String orderid,
			@PathParam("itemid") String itemid, @QueryParam("qty") String qty,
			@QueryParam("status") String status) throws MBGAppException {

		try {
			orderService.updateItems(orderid, itemid, qty, status);
		} catch (Exception e) {
			logger.info("Error occured trying to update Items");
			throw new MBGAppException("Error occured trying to update items",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok().build();
	}

	/**
	 * Method to delete Item.
	 *
	 * @param orderid
	 *            the orderid
	 * @param itemid
	 *            the itemid
	 * @return the response
	 * @throws MBGAppException
	 *             the MBG app exception
	 */
	@DELETE
	@Path("/{orderid}/items/{itemid}/cancel")
	public Response deleteItem(@PathParam("orderid") String orderid,
			@PathParam("itemid") String itemid) throws MBGAppException {
		String itemId;

		try {
			itemId = orderService.deleteItem(orderid, itemid);
		} catch (Exception e) {
			logger.info("Error occured during orderitem delete");
			throw new MBGAppException("Error occured during orderitem delete",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(itemId).build();
	}

	/**
	 * Method to update Order Address.
	 *
	 * @param orderId
	 *            the order id
	 * @param addId
	 *            the add id
	 * @param orderAddressUpdateRequestParam
	 *            the order address update request param
	 * @return the response
	 * @throws MBGAppException
	 *             the MBG app exception
	 */
	@PUT
	@Path("/{orderid}/addresses/{addid}")
	public Response updateOrderAddress(@PathParam("orderid") String orderId,
			@PathParam("addid") String addId,
			OrderAddressUpdateRequestParam orderAddressUpdateRequestParam)
			throws MBGAppException {

		OrderAddress orderAddress = new OrderAddress();

		String addressLine1 = orderAddressUpdateRequestParam.getAddressLine1();
		String addressLine2 = orderAddressUpdateRequestParam.getAddressLine2();
		String city = orderAddressUpdateRequestParam.getCity();
		String state = orderAddressUpdateRequestParam.getState();
		String country = orderAddressUpdateRequestParam.getCountry();
		String pincode = orderAddressUpdateRequestParam.getPincode();
		String contactNo = orderAddressUpdateRequestParam.getContactNo();

		try {

			orderAddress = orderService.updateAddress(orderId, addId,
					addressLine1, addressLine2, city, state, country, pincode,
					contactNo);
		} catch (Exception e) {
			logger.info("Error occured trying to update Order Address");
			throw new MBGAppException("Error occured trying to update Address",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		return Response.ok(orderAddress).build();
	}

}
