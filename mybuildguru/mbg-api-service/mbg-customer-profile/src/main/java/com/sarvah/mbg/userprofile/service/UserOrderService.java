/**
 * 
 */
package com.sarvah.mbg.userprofile.service;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;

import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.ordermgmt.OrderStatement;
import com.sarvah.mbg.domain.ordermgmt.OrderStatus;
import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;
import com.sarvah.mbg.userprofile.ordermgmt.model.OrderCreateRequestParam;
import com.sarvah.mbg.userprofile.ordermgmt.model.OrderStatementCreateRequestParam;
import com.sarvah.mbg.userprofile.response.CustomerOrdersSummaryResponse;
import com.sarvah.mbg.userprofile.response.GetUserOrderItemsResponse;
import com.sarvah.mbg.userprofile.response.GetUserOrderResponse;
import com.sarvah.mbg.userprofile.response.ItemUpdateResponse;
import com.sarvah.mbg.userprofile.response.ManageOrderResponse;
import com.sarvah.mbg.userprofile.response.SuperAdminOrderViewDetails;
import com.sarvah.mbg.userprofile.response.TrackOrderStatusCount;
import com.sarvah.mbg.userprofile.response.TrackOrderSummary;

/**
 * @author shivu
 *
 */
public interface UserOrderService {
	/**
	 * Method for create order
	 * 
	 * @param orderCreateRequestParam
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	GetUserOrderResponse createOrder(String userId,
			OrderCreateRequestParam OrderCreateRequestParam)
			throws ParseException, Exception;

	/**
	 * method for count orders
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	Long getOrderCount(String userId) throws Exception;

	/**
	 * method for update order status
	 * 
	 * @param userId
	 * @param orderId
	 * @param status
	 * @param paymenStatus
	 * @param paymentNumber
	 * @return
	 * @throws Exception
	 */
	OrderStatus updateOrderStatus(String userId, String orderId,
			String paymentMethodName, String status, String paymentNumber,
			String paymentMetadata, String paymenStatus, String paymentAmount,
			String paymentDetailsId, String chequeDate, String bankName,
			String chequeAmount) throws Exception;

	User updateOrdersPayment(String userId, String orderIds,
			String paymentAmount, String paymentMetadata) throws Exception;

	String updateOrder(String userId, String orderId, String invoiceNum);

	/**
	 * method for get order address
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	List<OrderAddress> getAddress(String userId, String orderId)
			throws Exception;

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
	OrderAddress updateAddress(String userId, String orderId, String name,
			String contactPersonName, String aid, String addressLine1,
			String addressLine2, String city, String state, String country,
			String pincode, String contactNo) throws Exception;

	/**
	 * Method to get all orders of particular user.
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	TrackOrderSummary getUserOrders(String uid, int page, int size, Sort sort)
			throws Exception;

	/**
	 * Method to get user orders based on order id.
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	GetUserOrderResponse getUserOrder(String userId, String orderId)
			throws Exception;

	/**
	 * Method to get user order status
	 * 
	 * @param uid
	 * @param orderid
	 * @return
	 * @throws Exception
	 */
	OrderStatus getUserOrderStatus(String uid, String orderid) throws Exception;

	/**
	 * Method to delete user Order
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	OrderStatus deleteUserOrder(String userId, String orderId) throws Exception;

	/**
	 * Method to get Order items of the user
	 * 
	 * @param uid
	 * @param orderid
	 * @return
	 * @throws Exception
	 */
	Set<GetUserOrderItemsResponse> getUserOrderItems(String uid, String orderid)
			throws Exception;

	// updating one or more item status at a time
	ItemUpdateResponse updateItemsStatus(String userId, String orderId,
			Set<String> itemsId, String status, String note) throws Exception;

	/**
	 * Method to delete user order item
	 * 
	 * @param uid
	 * @param orderid
	 * @param itemid
	 * @return
	 * @throws Exception
	 */
	ItemUpdateResponse deleteUserItem(String uid, String orderid, String itemid)
			throws Exception;

	/**
	 * method for get all cancelled status order.
	 * 
	 * @param uid
	 * @return List<GetUserOrderResponse>
	 * @throws Exception
	 */

	TrackOrderSummary getUserCancelledOrders(String uid, int page, int size,
			Sort sort) throws Exception;

	/**
	 * method for Admin manage all orders
	 * 
	 * @param uid
	 * @param searchValue
	 * @param delivery
	 * @param status
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	ManageOrderResponse getSuperAdminManageOrders(String uid,
			String searchValue, String delivery, String status, Long startDate,
			Long endDate, String customerName, String paymentDone, int page,
			int size, Sort sort) throws Exception;

	/**
	 * method for Dealer manage all orders
	 * 
	 * @param uid
	 * @param searchValue
	 * @param delivery
	 * @param status
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	ManageOrderResponse getDealerManageOrders(String uid, String searchValue,
			String delivery, String status, int page, int size, Sort sort)
			throws Exception;

	/**
	 * method for admin manage orders.
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	SuperAdminOrderViewDetails getSuperAdminOrderViewDetails(String userId,
			String orderId) throws Exception;

	/**
	 * method for dealer manage orders.
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	SuperAdminOrderViewDetails getDealerOrderViewDetails(String userId,
			String orderId) throws Exception;

	/**
	 * method for display user orders in sorting order based on order order
	 * status.
	 * 
	 * @param uid
	 * @param type
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	TrackOrderSummary getUserOrdersSorting(String uid, String type, int page,
			int size, Sort sort) throws Exception;

	/**
	 * method for display user order status count.
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	TrackOrderStatusCount getAllItemsStatus(String uid) throws Exception;

	TrackOrderStatusCount getUserOrderStatusCount(String uid, String orderid)
			throws Exception;

	List<CustomerOrdersSummaryResponse> getCustomerOrdersSummary(String uid);

	boolean verifyInvoiceNumber(String invoiceNum);

	OrderStatement createOrderStatement(
			List<OrderStatementCreateRequestParam> ordersStatementCreateRequestParam)
			throws Exception;

	List<OrderStatement> getOrderStatements(String userId);

	OrderStatement updateOrderStatement(String orderStatementId,
			OrderStatementCreateRequestParam orderStatementCreateRequestParam);

	OrderStatement delateOrderStatement(String orderStatementId);
}
