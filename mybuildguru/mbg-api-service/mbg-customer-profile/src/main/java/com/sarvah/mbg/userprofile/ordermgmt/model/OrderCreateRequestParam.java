/**
 * 
 */
package com.sarvah.mbg.userprofile.ordermgmt.model;

import java.util.List;
import java.util.Set;

/**
 * @author shivu
 *
 */
public class OrderCreateRequestParam {

	private String shippingType;

	// orderDetail
	private List<ItemCreateRequestParam> items;

	// shipping Address
	private String shippingName;
	private String shippingContactPersonName;
	private String shippingAddressLine1;
	private String shippingAddressLine2;
	private String shippingEmail;
	private String shippingCity;
	private String shippingState;
	private String shippingCountry;
	private int shippingPincode;
	private String shippingContactNo;

	// billling Address
	private String billingName;
	private String billingContactPersonName;
	private String billingAddressLine1;
	private String billingAddressLine2;
	private String billingEmail;
	private String billingCity;
	private String billingState;
	private String billingCountry;
	private int billingPincode;
	private String billingContactNo;

	private String type;

	private String createdby;
	private String modifiedby;

	// payment type
	private Set<PaymentCreateRequestParam> paymentTypes;

	// mobile order
	private String forUserId;
	private String orderByMBG;

	private double totalSavings;

	private double shippingCharge;

	private double extraBenefits;

	private double taxAmt;

	/**
	 * @return the orderByMBG
	 */
	public String getOrderByMBG() {
		return orderByMBG;
	}

	/**
	 * @param orderByMBG
	 *            the orderByMBG to set
	 */
	public void setOrderByMBG(String orderByMBG) {
		this.orderByMBG = orderByMBG;
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
	 * @return the items
	 */
	public List<ItemCreateRequestParam> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<ItemCreateRequestParam> items) {
		this.items = items;
	}

	/**
	 * @return the shippingName
	 */
	public String getShippingName() {
		return shippingName;
	}

	/**
	 * @param shippingName
	 *            the shippingName to set
	 */
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	/**
	 * @return the billingName
	 */
	public String getBillingName() {
		return billingName;
	}

	/**
	 * @param billingName
	 *            the billingName to set
	 */
	public void setBillingName(String billingName) {
		this.billingName = billingName;
	}

	/**
	 * @return the shippingAddressLine1
	 */
	public String getShippingAddressLine1() {
		return shippingAddressLine1;
	}

	/**
	 * @param shippingAddressLine1
	 *            the shippingAddressLine1 to set
	 */
	public void setShippingAddressLine1(String shippingAddressLine1) {
		this.shippingAddressLine1 = shippingAddressLine1;
	}

	/**
	 * @return the shippingAddressLine2
	 */
	public String getShippingAddressLine2() {
		return shippingAddressLine2;
	}

	/**
	 * @param shippingAddressLine2
	 *            the shippingAddressLine2 to set
	 */
	public void setShippingAddressLine2(String shippingAddressLine2) {
		this.shippingAddressLine2 = shippingAddressLine2;
	}

	/**
	 * @return the shippingEmail
	 */
	public String getShippingEmail() {
		return shippingEmail;
	}

	/**
	 * @param shippingEmail
	 *            the shippingEmail to set
	 */
	public void setShippingEmail(String shippingEmail) {
		this.shippingEmail = shippingEmail;
	}

	/**
	 * @return the shippingCity
	 */
	public String getShippingCity() {
		return shippingCity;
	}

	/**
	 * @param shippingCity
	 *            the shippingCity to set
	 */
	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	/**
	 * @return the shippingState
	 */
	public String getShippingState() {
		return shippingState;
	}

	/**
	 * @param shippingState
	 *            the shippingState to set
	 */
	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}

	/**
	 * @return the shippingCountry
	 */
	public String getShippingCountry() {
		return shippingCountry;
	}

	/**
	 * @param shippingCountry
	 *            the shippingCountry to set
	 */
	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}

	/**
	 * @return the shippingPincode
	 */
	public int getShippingPincode() {
		return shippingPincode;
	}

	/**
	 * @param shippingPincode
	 *            the shippingPincode to set
	 */
	public void setShippingPincode(int shippingPincode) {
		this.shippingPincode = shippingPincode;
	}

	/**
	 * @return the shippingContactNo
	 */
	public String getShippingContactNo() {
		return shippingContactNo;
	}

	/**
	 * @param shippingContactNo
	 *            the shippingContactNo to set
	 */
	public void setShippingContactNo(String shippingContactNo) {
		this.shippingContactNo = shippingContactNo;
	}

	/**
	 * @return the billingAddressLine1
	 */
	public String getBillingAddressLine1() {
		return billingAddressLine1;
	}

	/**
	 * @param billingAddressLine1
	 *            the billingAddressLine1 to set
	 */
	public void setBillingAddressLine1(String billingAddressLine1) {
		this.billingAddressLine1 = billingAddressLine1;
	}

	/**
	 * @return the billingAddressLine2
	 */
	public String getBillingAddressLine2() {
		return billingAddressLine2;
	}

	/**
	 * @param billingAddressLine2
	 *            the billingAddressLine2 to set
	 */
	public void setBillingAddressLine2(String billingAddressLine2) {
		this.billingAddressLine2 = billingAddressLine2;
	}

	/**
	 * @return the billingEmail
	 */
	public String getBillingEmail() {
		return billingEmail;
	}

	/**
	 * @param billingEmail
	 *            the billingEmail to set
	 */
	public void setBillingEmail(String billingEmail) {
		this.billingEmail = billingEmail;
	}

	/**
	 * @return the billingCity
	 */
	public String getBillingCity() {
		return billingCity;
	}

	/**
	 * @param billingCity
	 *            the billingCity to set
	 */
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}

	/**
	 * @return the billingState
	 */
	public String getBillingState() {
		return billingState;
	}

	/**
	 * @param billingState
	 *            the billingState to set
	 */
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}

	/**
	 * @return the billingCountry
	 */
	public String getBillingCountry() {
		return billingCountry;
	}

	/**
	 * @param billingCountry
	 *            the billingCountry to set
	 */
	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}

	/**
	 * @return the billingPincode
	 */
	public int getBillingPincode() {
		return billingPincode;
	}

	/**
	 * @param billingPincode
	 *            the billingPincode to set
	 */
	public void setBillingPincode(int billingPincode) {
		this.billingPincode = billingPincode;
	}

	/**
	 * @return the billingContactNo
	 */
	public String getBillingContactNo() {
		return billingContactNo;
	}

	/**
	 * @param billingContactNo
	 *            the billingContactNo to set
	 */
	public void setBillingContactNo(String billingContactNo) {
		this.billingContactNo = billingContactNo;
	}

	/**
	 * @return the createdby
	 */
	public String getCreatedby() {
		return createdby;
	}

	/**
	 * @param createdby
	 *            the createdby to set
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	/**
	 * @return the modifiedby
	 */
	public String getModifiedby() {
		return modifiedby;
	}

	/**
	 * @param modifiedby
	 *            the modifiedby to set
	 */
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the forUserId
	 */
	public String getForUserId() {
		return forUserId;
	}

	/**
	 * @param forUserId
	 *            the forUserId to set
	 */
	public void setForUserId(String forUserId) {
		this.forUserId = forUserId;
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
	 * @return the paymentTypes
	 */
	public Set<PaymentCreateRequestParam> getPaymentTypes() {
		return paymentTypes;
	}

	/**
	 * @param paymentTypes
	 *            the paymentTypes to set
	 */
	public void setPaymentTypes(Set<PaymentCreateRequestParam> paymentTypes) {
		this.paymentTypes = paymentTypes;
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
	 * @return the taxAmt
	 */
	public double getTaxAmt() {
		return taxAmt;
	}

	/**
	 * @param taxAmt
	 *            the taxAmt to set
	 */
	public void setTaxAmt(double taxAmt) {
		this.taxAmt = taxAmt;
	}

	/**
	 * @return the shippingContactPersonName
	 */
	public String getShippingContactPersonName() {
		return shippingContactPersonName;
	}

	/**
	 * @param shippingContactPersonName
	 *            the shippingContactPersonName to set
	 */
	public void setShippingContactPersonName(String shippingContactPersonName) {
		this.shippingContactPersonName = shippingContactPersonName;
	}

	/**
	 * @return the billingContactPersonName
	 */
	public String getBillingContactPersonName() {
		return billingContactPersonName;
	}

	/**
	 * @param billingContactPersonName
	 *            the billingContactPersonName to set
	 */
	public void setBillingContactPersonName(String billingContactPersonName) {
		this.billingContactPersonName = billingContactPersonName;
	}
}
