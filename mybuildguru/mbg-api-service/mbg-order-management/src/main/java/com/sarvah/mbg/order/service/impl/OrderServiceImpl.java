/**
 * 
 */
package com.sarvah.mbg.order.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sarvah.mbg.domain.ordermgmt.ItemStatus;
import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.OrderDetails;
import com.sarvah.mbg.domain.ordermgmt.OrderStatus;
import com.sarvah.mbg.domain.ordermgmt.payment.InvoiceDetails;
import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;
import com.sarvah.mbg.order.dao.jpa.InvoiceRepository;
import com.sarvah.mbg.order.dao.jpa.ItemStatusRepository;
import com.sarvah.mbg.order.dao.jpa.OrderAddressRepository;
import com.sarvah.mbg.order.dao.jpa.OrderDetailsRepository;
import com.sarvah.mbg.order.dao.jpa.OrderRepository;
import com.sarvah.mbg.order.dao.jpa.OrderStatusRepository;
import com.sarvah.mbg.order.dao.jpa.ShippingTypeRepository;
import com.sarvah.mbg.order.service.OrderService;
import com.sarvah.mbg.ordermgmt.response.GetOrderItemsResponseParent;
import com.sarvah.mbg.ordermgmt.response.GetOrdersResponseParent;

/**
 * @author shivu
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = LoggerFactory
			.getLogger(OrderServiceImpl.class);
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ShippingTypeRepository shippingTypeRepository;

	@Autowired
	private OrderStatusRepository orderStatusRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private ItemStatusRepository itemStatusRepository;

	@Autowired
	private OrderAddressRepository orderAddressRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	/**
	 * method for get all orders
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<GetOrdersResponseParent> getOrder() throws Exception {
		List<Order> orders = orderRepository.findAll();
		List<GetOrdersResponseParent> getOrders = new ArrayList<>();
		Collections.sort(orders, new OrdersComparator());

		if (orders != null) {
			for (Order order : orders) {
				GetOrdersResponseParent getOrderResponse = new GetOrdersResponseParent();

				getOrderResponse.setOrderId(order.getOrderId());

				getOrderResponse.setShippingType(order.getShippingType()
						.getShippingType());

				getOrderResponse.setFirstName(order.getUserInfo()
						.getFirstname());
				getOrderResponse.setLastName(order.getUserInfo().getLastname());
				getOrderResponse.setEmailAddress(order.getUserInfo()
						.getUsername());
				getOrderResponse.setPaymentMethod("Cash On Delivery");

				List<OrderDetails> orderDetails = orderDetailsRepository
						.findByOrder(order);
				Set<GetOrderItemsResponseParent> orderItemSet = new HashSet<>();
				int estimatedDeliveryDate = 0;
				for (OrderDetails orderDetail : orderDetails) {
					GetOrderItemsResponseParent getOrderItems = new GetOrderItemsResponseParent();
					getOrderItems.setItemId(orderDetail.getItemId());
					getOrderItems.setItemName(orderDetail.getItemName());
					getOrderItems.setQuantity(orderDetail.getQuantity());
					getOrderItems.setMrp(orderDetail.getMrp());
					getOrderItems.setTax(orderDetail.getTax());
					getOrderItems
							.setSellingPrice(orderDetail.getSellingPrice());
					getOrderItems.setShippingCharge(orderDetail
							.getShippingCharge());
					getOrderItems.setTotalPrice(orderDetail.getTotalPrice());
					getOrderItems.setDealerId(orderDetail.getDealerId());
					getOrderItems.setItemStatus(orderDetail.getItemStatus());
					getOrderItems.setCreateBy(orderDetail.getCreatedby());
					getOrderItems.setLastModifiedBy(orderDetail
							.getLastmodifiedby());
					getOrderItems.setCreatedDate(orderDetail.getCreatedDate());
					getOrderItems.setLastmodifiedDate(orderDetail
							.getLastModifiedDate());
					orderItemSet.add(getOrderItems);
					if (estimatedDeliveryDate <= orderDetail
							.getMaxDeliveryTimeInDays()) {
						estimatedDeliveryDate = orderDetail
								.getMaxDeliveryTimeInDays();
					} else if (estimatedDeliveryDate > orderDetail
							.getMaxDeliveryTimeInDays()) {
						continue;
					}
				}
				getOrderResponse.setEstimateDeliveryTime(estimatedDeliveryDate);
				getOrderResponse.setItems(orderItemSet);

				getOrderResponse.setOrderStatus(order.getOrderStatus()
						.getDescription());

				getOrderResponse.setShippingAddress(order.getShippingAddress());

				getOrderResponse.setBillingAddress(order.getBillingAddress());

				getOrderResponse.setCreateBy(order.getCreateBy());
				getOrderResponse.setLastModifiedBy(order.getLastModifiedBy());

				getOrderResponse.setOrderedDate(order.getOrderedDate());

				getOrderResponse.setLastmodifiedDate(order
						.getLastmodifiedDate());

				getOrders.add(getOrderResponse);
			}
		} else {
			logger.info("orders is null");
			throw new Exception("orders is null");
		}
		return getOrders;
	}

	/**
	 * method for get order addresses
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<OrderAddress> getAddress(int orderid) throws Exception {
		List<OrderAddress> orderAddressSet = new ArrayList<>();
		Order order = orderRepository.findOne(orderid);
		if (order != null) {
			OrderAddress billingAdress = order.getBillingAddress();
			orderAddressSet.add(billingAdress);
			OrderAddress shippingAddress = order.getShippingAddress();
			orderAddressSet.add(shippingAddress);
		} else {
			logger.info("order is null");
			throw new Exception("order is null");
		}
		return orderAddressSet;
	}

	/**
	 * method for get order items, based on orderId
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<GetOrderItemsResponseParent> getItems(int orderId)
			throws Exception {
		Order order = orderRepository.findOne(orderId);
		List<OrderDetails> orderDetailsList = null;
		List<GetOrderItemsResponseParent> oredrItems = new ArrayList<>();
		if (order != null) {
			orderDetailsList = orderDetailsRepository.findByOrder(order);
			for (OrderDetails orderDetails : orderDetailsList) {
				GetOrderItemsResponseParent getOrderItemsResponse = new GetOrderItemsResponseParent();
				getOrderItemsResponse.setItemId(orderDetails.getItemId());
				getOrderItemsResponse.setItemName(orderDetails.getItemName());
				getOrderItemsResponse.setItemStatus(orderDetails
						.getItemStatus());
				getOrderItemsResponse.setQuantity(orderDetails.getQuantity());
				getOrderItemsResponse.setMrp(orderDetails.getMrp());
				getOrderItemsResponse.setTax(orderDetails.getTax());
				getOrderItemsResponse.setSellingPrice(orderDetails
						.getSellingPrice());
				getOrderItemsResponse.setShippingCharge(orderDetails
						.getShippingCharge());
				getOrderItemsResponse.setTotalPrice(orderDetails
						.getTotalPrice());
				getOrderItemsResponse.setDealerId(orderDetails.getDealerId());
				getOrderItemsResponse.setCreateBy(orderDetails.getCreatedby());
				getOrderItemsResponse.setLastModifiedBy(orderDetails
						.getLastmodifiedby());
				getOrderItemsResponse.setCreatedDate(orderDetails
						.getCreatedDate());
				getOrderItemsResponse.setLastmodifiedDate(orderDetails
						.getLastModifiedDate());
				oredrItems.add(getOrderItemsResponse);
			}

		} else {
			logger.info("order is null");
			throw new Exception("order is null");
		}
		return oredrItems;
	}

	/**
	 * Method to get count of orders
	 * 
	 * @return
	 */
	@Override
	public Long countOrders() {
		Long count = null;
		count = orderRepository.count();

		return count;
	}

	/**
	 * Method to get particular order
	 * 
	 * @param orderid
	 * @return
	 * @throws Exception
	 */
	@Override
	public GetOrdersResponseParent searchOrder(String orderId) throws Exception {

		Order order = new Order();
		GetOrdersResponseParent getOrderResponse = new GetOrdersResponseParent();
		if (StringUtils.isNotBlank(orderId)) {
			order = orderRepository.findByMbgOrderId(orderId);
		} else {
			logger.info("OrderId cannot be null");
			throw new Exception("OrderId cannot be null");
		}

		if (order != null) {

			getOrderResponse.setOrderId(order.getOrderId());

			getOrderResponse.setShippingType(order.getShippingType()
					.getShippingType());

			getOrderResponse.setFirstName(order.getUserInfo().getFirstname());
			getOrderResponse.setLastName(order.getUserInfo().getLastname());

			List<OrderDetails> orderDetails = orderDetailsRepository
					.findByOrder(order);
			Set<GetOrderItemsResponseParent> orderItemSet = new HashSet<>();
			for (OrderDetails orderDetail : orderDetails) {
				GetOrderItemsResponseParent getOrderItems = new GetOrderItemsResponseParent();
				getOrderItems.setItemId(orderDetail.getItemId());
				getOrderItems.setItemName(orderDetail.getItemName());
				getOrderItems.setQuantity(orderDetail.getQuantity());
				getOrderItems.setMrp(orderDetail.getMrp());
				getOrderItems.setTax(orderDetail.getTax());
				getOrderItems.setSellingPrice(orderDetail.getSellingPrice());
				getOrderItems
						.setShippingCharge(orderDetail.getShippingCharge());
				getOrderItems.setTotalPrice(orderDetail.getTotalPrice());
				getOrderItems.setDealerId(orderDetail.getDealerId());
				getOrderItems.setItemStatus(orderDetail.getItemStatus());
				getOrderItems.setCreateBy(orderDetail.getCreatedby());
				getOrderItems
						.setLastModifiedBy(orderDetail.getLastmodifiedby());
				getOrderItems.setCreatedDate(orderDetail.getCreatedDate());
				getOrderItems.setLastmodifiedDate(orderDetail
						.getLastModifiedDate());
				orderItemSet.add(getOrderItems);
			}

			getOrderResponse.setItems(orderItemSet);

			getOrderResponse.setOrderStatus(order.getOrderStatus()
					.getDescription());

			getOrderResponse.setShippingAddress(order.getShippingAddress());

			getOrderResponse.setBillingAddress(order.getBillingAddress());

			getOrderResponse.setCreateBy(order.getCreateBy());
			getOrderResponse.setLastModifiedBy(order.getLastModifiedBy());

			getOrderResponse.setOrderedDate(order.getOrderedDate());

			getOrderResponse.setLastmodifiedDate(order.getLastmodifiedDate());
		}

		else {
			logger.info("Order doesn't exist");
			throw new Exception("Order doesn't exist");
		}
		return getOrderResponse;
	}

	/**
	 * Method to Get order Status
	 * 
	 * @param orderid
	 * @return
	 * @throws Exception
	 */
	@Override
	public OrderStatus getOrderStatus(String orderId) throws Exception {
		Order order = new Order();
		OrderStatus orderStatus = new OrderStatus();
		if (StringUtils.isNotBlank(orderId)) {
			order = orderRepository.findByMbgOrderId(orderId);
			if (order != null) {
				orderStatus = order.getOrderStatus();
			} else {
				logger.info("Order doesn't exist");
				throw new Exception("Order doesn't exist");
			}
		} else {
			logger.info("Order id Null");
			throw new Exception("Order id should not be blank");
		}
		return orderStatus;
	}

	/**
	 * Method to update Order status
	 * 
	 * @param orderid
	 * @param statusName
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional
	public OrderStatus updateStatus(String orderId, String status)
			throws Exception {
		OrderStatus orderStatus = new OrderStatus();
		Order order = null;
		if (StringUtils.isNotBlank(orderId)) {
			order = orderRepository.findByMbgOrderId(orderId);
			if (order != null) {
				if (StringUtils.isNotBlank(status)) {

					OrderStatus orderStatus1 = orderStatusRepository
							.findByDescription(status);

					int orderStatusId = orderStatus1.getOrderStatusId();

					orderStatus.setOrderStatusId(orderStatusId);
					orderStatus.setDescription(status);

					order.setOrderStatus(orderStatus);
				}
				orderRepository.save(order);
			} else {
				logger.info("Order doesn't exists");
				throw new Exception("Order doesn't exists");
			}

		} else {
			logger.info("OrderId should not be null");
			throw new Exception("OrderId should not be null");
		}
		return orderStatus;
	}

	/**
	 * Method to delete Order
	 * 
	 * @param orderid
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional
	public String deleteOrder(String orderId) throws Exception {
		Order order = new Order();
		if (StringUtils.isNotBlank(orderId)) {
			order = orderRepository.findByMbgOrderId(orderId);

			if (order != null) {
				orderRepository.deleteByMbgOrderId(orderId);
			} else {
				logger.info("Order doesn't exist");
				throw new Exception("Order doesn't exist");
			}
		} else {
			logger.info("Order id is Null");
			throw new Exception("Order id should not be blank");
		}
		return orderId;
	}

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
	@Override
	@Transactional
	public Order updateItems(String orderId, String itemId, String qty,
			String status) throws Exception {

		Order order = null;
		ItemStatus itemStatus = new ItemStatus();
		List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();

		if (StringUtils.isNotBlank(orderId)) {
			order = orderRepository.findByMbgOrderId(orderId);
			if (order != null) {
				for (OrderDetails items : order.getItems()) {

					String itemId1 = String.valueOf(items.getItemId());

					if (StringUtils.isNotBlank(itemId)
							&& itemId.equalsIgnoreCase(itemId1)) {
						if (StringUtils.isNotBlank(status)) {

							ItemStatus itemStatus1 = itemStatusRepository
									.findByDescription(status);

							int itemStatusId = itemStatus1.getItemStatusId();

							itemStatus.setItemStatusId(itemStatusId);
							itemStatus1.setDescription(status);

							itemStatus.setDescription(status);
							items.setItemStatus(itemStatus);

						}
						if (StringUtils.isNotBlank(qty)) {
							int quantity = Integer.parseInt(qty);
							items.setQuantity(quantity);

							Double totalPrice = ((items.getQuantity()) * (items
									.getSellingPrice()))
									+ items.getShippingCharge();

							items.setTotalPrice(totalPrice);
						}
					} else {
						logger.info("Item doesn't exist");
						throw new Exception("Item doesn't exist");
					}
					orderDetails.add(items);
					order.setItems(orderDetails);
				}
			} else {
				logger.info("Order doesn't exist");
				throw new Exception("Order doesn't exist");
			}

		} else {
			logger.info("OrderId should not be null");
			throw new Exception("OrderId should not be null");
		}
		orderRepository.save(order);
		return order;
	}

	/**
	 * Method to delete Item
	 * 
	 * @param orderid
	 * @param itemid
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	@Transactional
	public String deleteItem(String orderId, String itemId) throws Exception {
		Order order = new Order();
		if (StringUtils.isNotBlank(orderId)) {
			order = orderRepository.findByMbgOrderId(orderId);
			OrderDetails orderDetails = new OrderDetails();
			if (order != null) {
				orderDetails = orderDetailsRepository.findByOrderAndItemId(
						order, itemId);
				if (orderDetails != null) {
					int orderDetailsId = orderDetails.getOrderDetailsId();
					orderDetailsRepository.delete(orderDetailsId);
				} else {
					logger.info("Invalid Item ");
					throw new Exception("Invalid Item");
				}
			} else {
				logger.info(" Invalid Order");
				throw new Exception("Invalid Order");
			}
		} else {
			logger.info("Order id Null");
			throw new Exception("Order id should not be blank");
		}
		return itemId;
	}

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

	@Override
	@Transactional
	public OrderAddress updateAddress(String orderId, String aid,
			String addressLine1, String addressLine2, String city,
			String state, String country, String pincode, String contactNo)
			throws Exception {
		Order order = null;
		OrderAddress orderAddress = null;

		if (StringUtils.isNotBlank(orderId)) {
			order = orderRepository.findOne(Integer.parseInt(orderId));
			if (order != null) {
				if (StringUtils.isNotBlank(aid)) {
					orderAddress = orderAddressRepository.findOne(Integer
							.parseInt(aid));
					if (orderAddress != null) {
						if (StringUtils.isNotBlank(addressLine1)) {
							orderAddress.setAddressLine1(addressLine1);
						}
						if (StringUtils.isNotBlank(addressLine2)) {
							orderAddress.setAddressLine2(addressLine2);
						}
						if (StringUtils.isNotBlank(city)) {
							orderAddress.setCity(city);
						}
						if (StringUtils.isNotBlank(state)) {
							orderAddress.setState(state);
						}
						if (StringUtils.isNotBlank(country)) {
							orderAddress.setCountry(country);
						}
						if (StringUtils.isNotBlank(pincode)) {
							orderAddress.setPincode(Integer.parseInt(pincode));
						}
						if (StringUtils.isNotBlank(contactNo)) {
							orderAddress.setContactNo(contactNo);
						}
					} else {
						logger.info("Address doesn't exist");
						throw new Exception("Address doesn't exist");
					}
				} else {
					logger.info("AddressId should not be null");
					throw new Exception("AddressId should not be null");
				}
			} else {
				logger.info("Order doesn't exist");
				throw new Exception("Order doesn't exist");
			}
		} else {
			logger.info("OrderId should not be null");
			throw new Exception("OrderId should not be null");
		}
		orderAddressRepository.save(orderAddress);
		return orderAddress;
	}

	/**
	 * Method to get invoice of the order
	 * 
	 * @param orderId
	 * @return
	 * @throws MalformedURLException
	 */
	@Override
	public URL getInvoiceUrl(String orderId, String userId)
			throws MalformedURLException {

		InvoiceDetails invoice = null;

		// Get order object
		Order order = orderRepository.findByMbgOrderId(orderId);

		// Get invoice for the order object
		invoice = invoiceRepository.findByOrderAndUserId(order, userId);

		// Get the URL from the invoice
		URL url = new URL(invoice.getFileUrl());

		// Return the invoice url
		return url;
	}

	@Override
	public URL getDealerInvoiceUrl(String orderId, String uid)
			throws MalformedURLException {

		InvoiceDetails invoice = null;
		URL url = null;

		// Get order object
		Order order = orderRepository.findByMbgOrderId(orderId);

		// Get invoice invoice for the order and the logged in user(dealer)
		invoice = invoiceRepository.findByOrderAndUserId(order, uid);

		// Get the URL from the invoice
		url = new URL(invoice.getFileUrl());

		// Return the invoice url
		return url;
	}
}
