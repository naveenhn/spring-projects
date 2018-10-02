package com.sarvah.mbg.userprofile.response;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.sarvah.mbg.domain.mongo.catalog.SubCategory;

public class OrderManageView {

	private String orderId;
	private double orderAmount;
	private int noOfItems;
	private Set<String> itemsName;
	private Date orderPlacedOn;
	private List<String> category;
	private Set<SubCategory> subCategory;
	private String deliveryPreference;
	private String orderStatus;
	private Date orderedDate;
	private int estimatedDeliveryDate;
	private String shippingType;
	private double totalPrice;
	private int totalItems;
	private Set<PaymentDetailsResponse> paymentDetails;
	private List<String> invoices;
	private String enduserInvocie;
	private double shippingCharge;
	private String customerName;
	private String customerId;
	private String customerRoleName;
	private String contactNumber;
	private String invoiceNumber;
	private boolean paymentDone;

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
	 * @return the totalPrice
	 */
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice
	 *            the totalPrice to set
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
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
	 * @return the enduserInvocie
	 */
	public String getEnduserInvocie() {
		return enduserInvocie;
	}

	/**
	 * @param enduserInvocie
	 *            the enduserInvocie to set
	 */
	public void setEnduserInvocie(String enduserInvocie) {
		this.enduserInvocie = enduserInvocie;
	}

	public double getShippingCharge() {
		return shippingCharge;
	}

	public void setShippingCharge(double shippingCharge) {
		this.shippingCharge = shippingCharge;
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
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}

	/**
	 * @param contactNumber
	 *            the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the customerRoleName
	 */
	public String getCustomerRoleName() {
		return customerRoleName;
	}

	/**
	 * @param customerRoleName
	 *            the customerRoleName to set
	 */
	public void setCustomerRoleName(String customerRoleName) {
		this.customerRoleName = customerRoleName;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber
	 *            the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the paymentDone
	 */
	public boolean isPaymentDone() {
		return paymentDone;
	}

	/**
	 * @param paymentDone
	 *            the paymentDone to set
	 */
	public void setPaymentDone(boolean paymentDone) {
		this.paymentDone = paymentDone;
	}

	/**
	 * @return the itemsName
	 */
	public Set<String> getItemsName() {
		return itemsName;
	}

	/**
	 * @param itemsName
	 *            the itemsName to set
	 */
	public void setItemsName(Set<String> itemsName) {
		this.itemsName = itemsName;
	}
}
