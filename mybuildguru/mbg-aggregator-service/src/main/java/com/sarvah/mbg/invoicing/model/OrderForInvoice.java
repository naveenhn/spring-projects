/**
 * 
 */
package com.sarvah.mbg.invoicing.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author naveen
 *
 */

/**
 * @author Shiva
 *
 */
public class OrderForInvoice {

	// Invoice related information
	private String invoiceId;
	private Date invoiceDate;

	// Order related information
	private String orderId;
	private Date orderDate;

	// Buyer address
	private Address buyerAddress;

	// Shipping Address
	private Address shippingAddress;

	// Item information
	public List<Item> items = new ArrayList<>();

	// Total information
	public String totalAmount;
	public String totalSavings;

	// Delivery information
	private String deliveryPreference;

	// Payment method
	private String paymentMethod;

	private int userId;

	public OrderForInvoice(OrderInfoObject orderInfoObject) {

		this.invoiceId = orderInfoObject.getInvoiceId();
		this.invoiceDate = new Date();

		this.orderId = orderInfoObject.getOrderId();
		this.orderDate = orderInfoObject.getOrderDate();

		this.deliveryPreference = orderInfoObject.getDeliveryPreference();
		this.paymentMethod = orderInfoObject.getPaymentMethod();

		// Per Item information
		for (ItemInfoObject item : orderInfoObject.getItems()) {

			this.items
					.add(new Item(item.getName(), item.getRate(),
							item.getQty(), item.getAmount(), item
									.getSellerName(), item.getQtyType(), item
									.getDiscount(), item.getDeliveryCharge()));
		}

		// Billing address
		this.buyerAddress = new Address(orderInfoObject.getBuyerAddress()
				.getName(),
				orderInfoObject.getBuyerAddress().getAddressLine1(),
				orderInfoObject.getBuyerAddress().getCity(), orderInfoObject
						.getBuyerAddress().getState(), orderInfoObject
						.getBuyerAddress().getCountry(), orderInfoObject
						.getBuyerAddress().getZip(), orderInfoObject
						.getBuyerAddress().getMobile() + "");

		// Shipping address
		this.shippingAddress = new Address(""
				+ orderInfoObject.getShippingAddress().getName(),
				orderInfoObject.getShippingAddress().getAddressLine1(),
				orderInfoObject.getShippingAddress().getCity(), orderInfoObject
						.getShippingAddress().getState(), orderInfoObject
						.getShippingAddress().getCountry(), orderInfoObject
						.getShippingAddress().getZip(), orderInfoObject
						.getShippingAddress().getMobile() + "");

		// Total Amount
		this.totalAmount = orderInfoObject.getTotalAmount();

		// Total Savings
		this.totalSavings = orderInfoObject.getTotalSavings();

		this.userId = orderInfoObject.getUserId();
	}

	/**
	 * @return the invoiceId
	 */
	public String getInvoiceId() {
		return invoiceId;
	}

	/**
	 * @param invoiceId
	 *            the invoiceId to set
	 */
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	/**
	 * @return the invoiceDate
	 */
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * @return the buyerAddress
	 */
	public Address getBuyerAddress() {
		return buyerAddress;
	}

	/**
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate
	 *            the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return the shippingAddress
	 */
	public Address getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * @param shippingAddress
	 *            the shippingAddress to set
	 */
	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * @return the totalAmount
	 */
	public String getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @return the totalSavings
	 */
	public String getTotalSavings() {
		return totalSavings;
	}

	/**
	 * @param totalAmount
	 *            the totalAmount to set
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @param totalSavings
	 *            the totalSavings to set
	 */
	public void setTotalSavings(String totalSavings) {
		this.totalSavings = totalSavings;
	}

	/**
	 * @return the deliveryPreference
	 */
	public String getDeliveryPreference() {
		return deliveryPreference;
	}

	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param deliveryPreference
	 *            the deliveryPreference to set
	 */
	public void setDeliveryPreference(String deliveryPreference) {
		this.deliveryPreference = deliveryPreference;
	}

	/**
	 * @param paymentMethod
	 *            the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

}