/**
 * 
 */
package com.sarvah.mbg.ordermgmt.response;

import java.util.Date;
import java.util.Set;

import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;

/**
 * @author shivu
 *
 */
public class GetOrdersResponseParent {
	private int orderId;
	private String shippingType;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private Integer estimateDeliveryTime;
	private String paymentMethod;
	private Set<GetOrderItemsResponseParent> items;
	private String orderStatus;
	private OrderAddress shippingAddress;
	private OrderAddress billingAddress;
	private String createBy;
	private String lastModifiedBy;
	private Date orderedDate;
	private Date lastmodifiedDate;
	
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
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
	 * @param estimateDeliveryTime the estimateDeliveryTime to set
	 */
	public void setEstimateDeliveryTime(Integer estimateDeliveryTime) {
		this.estimateDeliveryTime = estimateDeliveryTime;
	}
	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}
	/**
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the shippingType
	 */
	public String getShippingType() {
		return shippingType;
	}
	/**
	 * @param shippingType the shippingType to set
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
	 * @param firstName the firstName to set
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
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the items
	 */
	public Set<GetOrderItemsResponseParent> getItems() {
		return items;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(Set<GetOrderItemsResponseParent> items) {
		this.items = items;
	}
	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}
	/**
	 * @param orderStatus the orderStatus to set
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
	 * @param shippingAddress the shippingAddress to set
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
	 * @param billingAddress the billingAddress to set
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
	 * @param createBy the createBy to set
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
	 * @param lastModifiedBy the lastModifiedBy to set
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
	 * @param orderedDate the orderedDate to set
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
	 * @param lastmodifiedDate the lastmodifiedDate to set
	 */
	public void setLastmodifiedDate(Date lastmodifiedDate) {
		this.lastmodifiedDate = lastmodifiedDate;
	}
	

}
