/**
 * 
 */
package com.sarvah.mbg.invoicing.model;

import java.util.Date;
import java.util.Set;

/**
 * @author Shiva
 *
 */
public class OrderInfoObjectForDealer {

	// Order related information
	private String orderId;
	private Date orderDate;

	// Invoice related information
	private String invoiceId;
	private Date InvoiceDate;

	// Billing Address
	private Address buyerAddress;

	// Shipping Address
	private Address shippingAddress;

	// Item related information
	private Set<ItemInfoObjectForDealer> items;

	// Total Amount of the order
	private String totalAmount;
	// Total savings in the order
	private String totalSavings;

	// Delivery information
	private String deliveryPreference;
	// Payment method
	private String paymentMethod;

	// In order to to know for which user(dealer/enduser) the invoice belongs
	private int userId;

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
	 * @return the invoiceId
	 */
	public String getInvoiceId() {
		return invoiceId;
	}

	/**
	 * @return the invoiceDate
	 */
	public Date getInvoiceDate() {
		return InvoiceDate;
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
	public Set<ItemInfoObjectForDealer> getItems() {
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
	 * @param invoiceId
	 *            the invoiceId to set
	 */
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	/**
	 * @param invoiceDate
	 *            the invoiceDate to set
	 */
	public void setInvoiceDate(Date invoiceDate) {
		InvoiceDate = invoiceDate;
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
	public void setItems(Set<ItemInfoObjectForDealer> items) {
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