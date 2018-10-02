package com.sarvah.mbg.userprofile.response;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;

public class SuperAdminOrderViewDetails {
	private String userId;
	private String orderId;
	private double orderAmount;
	private int noOfItems;
	private Date orderPlacedOn;
	private int estimatedDeliveryDate;
	private List<String> category;
	private Set<SubCategory> subCategory;
	private String deliveryPreference;
	private double shippingCharge;
	// orderstatus
	private String orderStatus;
	// admin
	private String customerName;
	private String emailId;
	private String phoneNumber;
	private List<GetUserOrderItemsResponse> items;
	private OrderAddress shippingAddress;
	private OrderAddress billingAddress;
	private double extraBenefits;
	private String invoiceNum;

	private String shippingType;
	private double orderPrice;
	private int totalItems;
	private Date orderedDate;
	private Set<PaymentDetailsResponse> paymentDetails;
	private List<OrderAuditResponse> orderAuditRespose;
	private List<String> invoices;
	private String dealerInvocie;

	/**
	 * @return the invoices
	 */
	public List<String> getInvoices() {
		return invoices;
	}

	/**
	 * @param invoices
	 *            the invoices to set
	 */
	public void setInvoices(List<String> invoices) {
		this.invoices = invoices;
	}

	/**
	 * @return the dealerInvocie
	 */
	public String getDealerInvocie() {
		return dealerInvocie;
	}

	/**
	 * @param dealerInvocie
	 *            the dealerInvocie to set
	 */
	public void setDealerInvocie(String dealerInvocie) {
		this.dealerInvocie = dealerInvocie;
	}

	/**
	 * @return the shippingType
	 */
	public String getShippingType() {
		return shippingType;
	}

	/**
	 * @param shippingType
	 *            the shippingType to set
	 */
	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	/**
	 * @return the orderPrice
	 */
	public double getOrderPrice() {
		return orderPrice;
	}

	/**
	 * @param orderPrice
	 *            the orderPrice to set
	 */
	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	/**
	 * @return the totalItems
	 */
	public int getTotalItems() {
		return totalItems;
	}

	/**
	 * @param totalItems
	 *            the totalItems to set
	 */
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	/**
	 * @return the orderedDate
	 */
	public Date getOrderedDate() {
		return orderedDate;
	}

	/**
	 * @param orderedDate
	 *            the orderedDate to set
	 */
	public void setOrderedDate(Date orderedDate) {
		this.orderedDate = orderedDate;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the orderAmount
	 */
	public double getOrderAmount() {
		return orderAmount;
	}

	/**
	 * @param orderAmount
	 *            the orderAmount to set
	 */
	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}

	/**
	 * @return the noOfItems
	 */
	public int getNoOfItems() {
		return noOfItems;
	}

	/**
	 * @param noOfItems
	 *            the noOfItems to set
	 */
	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}

	/**
	 * @return the orderPlacedOn
	 */
	public Date getOrderPlacedOn() {
		return orderPlacedOn;
	}

	/**
	 * @param orderPlacedOn
	 *            the orderPlacedOn to set
	 */
	public void setOrderPlacedOn(Date orderPlacedOn) {
		this.orderPlacedOn = orderPlacedOn;
	}

	/**
	 * @return the estimatedDeliveryDate
	 */
	public int getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}

	/**
	 * @param estimatedDeliveryDate
	 *            the estimatedDeliveryDate to set
	 */
	public void setEstimatedDeliveryDate(int estimatedDeliveryDate) {
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}

	/**
	 * @return the category
	 */
	public List<String> getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(List<String> category) {
		this.category = category;
	}

	/**
	 * @return the subCategory
	 */
	public Set<SubCategory> getSubCategory() {
		return subCategory;
	}

	/**
	 * @param subCategory
	 *            the subCategory to set
	 */
	public void setSubCategory(Set<SubCategory> subCategory) {
		this.subCategory = subCategory;
	}

	/**
	 * @return the deliveryPreference
	 */
	public String getDeliveryPreference() {
		return deliveryPreference;
	}

	/**
	 * @param deliveryPreference
	 *            the deliveryPreference to set
	 */
	public void setDeliveryPreference(String deliveryPreference) {
		this.deliveryPreference = deliveryPreference;
	}

	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus
	 *            the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 *            the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the items
	 */
	public List<GetUserOrderItemsResponse> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<GetUserOrderItemsResponse> items) {
		this.items = items;
	}

	/**
	 * @return the shippingAddress
	 */
	public OrderAddress getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * @param shippingAddress
	 *            the shippingAddress to set
	 */
	public void setShippingAddress(OrderAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * @return the billingAddress
	 */
	public OrderAddress getBillingAddress() {
		return billingAddress;
	}

	/**
	 * @param billingAddress
	 *            the billingAddress to set
	 */
	public void setBillingAddress(OrderAddress billingAddress) {
		this.billingAddress = billingAddress;
	}

	/**
	 * @return the orderAuditRespose
	 */
	public List<OrderAuditResponse> getOrderAuditRespose() {
		return orderAuditRespose;
	}

	/**
	 * @param orderAuditRespose
	 *            the orderAuditRespose to set
	 */
	public void setOrderAuditRespose(List<OrderAuditResponse> orderAuditRespose) {
		this.orderAuditRespose = orderAuditRespose;
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
	 * @return the paymentDetails
	 */
	public Set<PaymentDetailsResponse> getPaymentDetails() {
		return paymentDetails;
	}

	/**
	 * @param paymentDetails
	 *            the paymentDetails to set
	 */
	public void setPaymentDetails(Set<PaymentDetailsResponse> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public double getShippingCharge() {
		return shippingCharge;
	}

	public void setShippingCharge(double shippingCharge) {
		this.shippingCharge = shippingCharge;
	}

	/**
	 * @return the extraBenefits
	 */
	public double getExtraBenefits() {
		return extraBenefits;
	}

	/**
	 * @param extraBenefits
	 *            the extraBenefits to set
	 */
	public void setExtraBenefits(double extraBenefits) {
		this.extraBenefits = extraBenefits;
	}

	/**
	 * @return the invoiceNum
	 */
	public String getInvoiceNum() {
		return invoiceNum;
	}

	/**
	 * @param invoiceNum
	 *            the invoiceNum to set
	 */
	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

}