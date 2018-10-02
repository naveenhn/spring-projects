/**
 * 
 */
package com.sarvah.mbg.order.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.OrderStatus;
import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;
import com.sarvah.mbg.ordermgmt.response.GetOrderItemsResponseParent;
import com.sarvah.mbg.ordermgmt.response.GetOrdersResponseParent;

/**
 * @author shivu
 *
 */
public interface OrderService {
	/**
	 * method for get all orders
	 * 
	 * @return
	 * @throws Exception
	 */
	List<GetOrdersResponseParent> getOrder() throws Exception;

	/**
	 * method for get order addresses
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	List<OrderAddress> getAddress(int orderId) throws Exception;

	/**
	 * method for get order items, based on orderId
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	List<GetOrderItemsResponseParent> getItems(int orderId) throws Exception;

	// order new
	/**
	 * Method to get count of orders
	 * 
	 * @return
	 */
	Long countOrders();

	/**
	 * Method to get particular order
	 * 
	 * @param orderid
	 * @return
	 * @throws Exception
	 */
	GetOrdersResponseParent searchOrder(String orderid) throws Exception;

	/**
	 * Method to Get order Status
	 * 
	 * @param orderid
	 * @return
	 * @throws Exception
	 */
	OrderStatus getOrderStatus(String orderid) throws Exception;

	/**
	 * Method to update Order status
	 * 
	 * @param orderid
	 * @param statusName
	 * @return
	 * @throws Exception
	 */
	OrderStatus updateStatus(String orderid, String status) throws Exception;

	/**
	 * Method to delete Order
	 * 
	 * @param orderid
	 * @return
	 * @throws Exception
	 */
	String deleteOrder(String orderid) throws Exception;

	/**
	 * Method to update order Items
	 * 
	 * @param orderid
	 * @param itemid
	 * @param qty
	 * @param status
	 * @return
	 * @throws Exception
	 */
	Order updateItems(String orderid, String itemid, String qty, String status)
			throws Exception;

	/**
	 * Method to delete Item
	 * 
	 * @param orderid
	 * @param itemid
	 * @return
	 * @throws MBGAppException
	 */
	String deleteItem(String orderid, String itemid) throws Exception;

	/**
	 * Method to update Order Address
	 * 
	 * @param orderId
	 * @param addId
	 * @param addressLine1
	 * @param addressLine2
	 * @param city
	 * @param state
	 * @param country
	 * @param pincode
	 * @param contactNo
	 * @return
	 * @throws Exception
	 */
	OrderAddress updateAddress(String orderId, String addId,
			String addressLine1, String addressLine2, String city,
			String state, String country, String pincode, String contactNo)
			throws Exception;

	/**
	 * Method to get invoice of the order
	 * 
	 * @param orderId
	 * @return
	 * @throws MalformedURLException
	 */
	URL getInvoiceUrl(String orderId, String userId)
			throws MalformedURLException;

	/**
	 * Method to get dealer invoice of the order
	 * 
	 * @param orderId
	 * @param uid
	 * @param get
	 * @return
	 * @throws MalformedURLException
	 */
	URL getDealerInvoiceUrl(String orderId, String userId)
			throws MalformedURLException;
}
