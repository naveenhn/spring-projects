/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;

/**
 * @author shivu
 *
 */
public class GetUserOrderResponse {
	private String orderId;
	private String shippingType;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String phoneNumber;
	private Integer estimateDeliveryTime;
	private Set<PaymentDetailsResponse> paymentDetails;
	private Set<GetUserOrderItemsResponse> items;
	private String orderStatus;
	private OrderAddress shippingAddress;
	private OrderAddress billingAddress;
	private String createBy;
	private String lastModifiedBy;
	private Date orderedDate;
	private Date lastmodifiedDate;
	private List<OrderAuditResponse> orderAuditRespose;
	private double totalSavings;
	private String invoice;
	private double shippingCharge;
	private double extraBenefits;

	/**
	 * @return the invoice
	 */
	public String getInvoice() {
		return invoice;
	}

	/**
	 * @param invoice
	 *            the invoice to set
	 */
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the estimateDeliveryTime
	 */
	public Integer getEstimateDeliveryTime() {
		return estimateDeliveryTime;
	}

	/**
	 * @param estimateDeliveryTime
	 *            the estimateDeliveryTime to set
	 */
	public void setEstimateDeliveryTime(Integer estimateDeliveryTime) {
		this.estimateDeliveryTime = estimateDeliveryTime;
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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the items
	 */
	public Set<GetUserOrderItemsResponse> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(Set<GetUserOrderItemsResponse> items) {
		this.items = items;
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
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy
	 *            the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
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
	 * @return the lastmodifiedDate
	 */
	public Date getLastmodifiedDate() {
		return lastmodifiedDate;
	}

	/**
	 * @param lastmodifiedDate
	 *            the lastmodifiedDate to set
	 */
	public void setLastmodifiedDate(Date lastmodifiedDate) {
		this.lastmodifiedDate = lastmodifiedDate;
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
	 * @return the totalSavings
	 */
	public double getTotalSavings() {
		return totalSavings;
	}

	/**
	 * @param totalSavings
	 *            the totalSavings to set
	 */
	public void setTotalSavings(double totalSavings) {
		this.totalSavings = totalSavings;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
}
