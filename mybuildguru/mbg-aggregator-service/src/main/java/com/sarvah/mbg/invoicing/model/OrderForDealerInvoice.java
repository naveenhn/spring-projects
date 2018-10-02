/**
 * 
 */
package com.sarvah.mbg.invoicing.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Shiva
 *
 */

public class OrderForDealerInvoice {

	// Invoice related information
	private String invoiceId;
	private Date invoiceDate;

	// Order related information
	private String orderId;
	private Date orderDate;

	// Buyer address
	private Address buyerAddress;

	// Shipping address
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

	public OrderForDealerInvoice(OrderInfoObjectForDealer orderForDealerInvoice) {

		this.invoiceId = orderForDealerInvoice.getInvoiceId();
		this.invoiceDate = new Date();

		this.orderId = orderForDealerInvoice.getOrderId();
		this.orderDate = orderForDealerInvoice.getOrderDate();

		this.deliveryPreference = orderForDealerInvoice.getDeliveryPreference();
		this.paymentMethod = orderForDealerInvoice.getPaymentMethod();

		// Per Item information
		for (ItemInfoObjectForDealer item : orderForDealerInvoice.getItems()) {

			this.items
					.add(new Item(item.getName(), item.getRate(),
							item.getQty(), item.getAmount(), item
									.getSellerName(), item.getQtyType(), item
									.getDiscount(), item.getDeliveryCharge()));
		}

		// Billing address
		this.buyerAddress = new Address(orderForDealerInvoice.getBuyerAddress()
				.getName(), orderForDealerInvoice.getBuyerAddress()
				.getAddressLine1(), orderForDealerInvoice.getBuyerAddress()
				.getCity(), orderForDealerInvoice.getBuyerAddress().getState(),
				orderForDealerInvoice.getBuyerAddress().getCountry(),
				orderForDealerInvoice.getBuyerAddress().getZip(),
				orderForDealerInvoice.getBuyerAddress().getMobile() + "");

		// Shipping address
		this.shippingAddress = new Address(""
				+ orderForDealerInvoice.getShippingAddress().getName(),
				orderForDealerInvoice.getShippingAddress().getAddressLine1(),
				orderForDealerInvoice.getShippingAddress().getCity(),
				orderForDealerInvoice.getShippingAddress().getState(),
				orderForDealerInvoice.getShippingAddress().getCountry(),
				orderForDealerInvoice.getShippingAddress().getZip(),
				orderForDealerInvoice.getShippingAddress().getMobile() + "");

		// Total Amount
		this.totalAmount = orderForDealerInvoice.getTotalAmount();

		// Total Savings
		this.totalSavings = orderForDealerInvoice.getTotalSavings();

		this.userId = orderForDealerInvoice.getUserId();
	}

	/**
	 * @return the invoiceId
	 */
	public String getInvoiceId() {
		return invoiceId;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
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
	 * @return the shippingAddress
	 */
	public Address getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
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
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param invoiceId
	 *            the invoiceId to set
	 */
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @param orderDate
	 *            the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @param invoiceDate
	 *            the invoiceDate to set
	 */
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	/**
	 * @param buyerAddress
	 *            the buyerAddress to set
	 */
	public void setBuyerAddress(Address buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	/**
	 * @param shippingAddress
	 *            the shippingAddress to set
	 */
	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<Item> items) {
		this.items = items;
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
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

}