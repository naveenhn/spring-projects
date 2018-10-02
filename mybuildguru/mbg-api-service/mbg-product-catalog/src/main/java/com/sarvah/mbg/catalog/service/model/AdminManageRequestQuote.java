/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import java.util.Date;
import java.util.Set;

/**
 * @author Shivu
 *
 */
public class AdminManageRequestQuote {
	private String quoteRequestId;
	private String buildOnnQuoteRequestId;
	private String zipcode;
	private String customerEmail;
	private String customerContactNumber;
	private String customerName;
	private String roleName;
	private Set<String> productsName;
	private Set<String> sellersName;
	private Date createdDate;
	private String status;
	private String deliveryDate;
	private String note;
	private String followupDate;

	/**
	 * @return the quoteRequestId
	 */
	public String getQuoteRequestId() {
		return quoteRequestId;
	}

	/**
	 * @param quoteRequestId
	 *            the quoteRequestId to set
	 */
	public void setQuoteRequestId(String quoteRequestId) {
		this.quoteRequestId = quoteRequestId;
	}

	/**
	 * @return the buildOnnQuoteRequestId
	 */
	public String getBuildOnnQuoteRequestId() {
		return buildOnnQuoteRequestId;
	}

	/**
	 * @param buildOnnQuoteRequestId
	 *            the buildOnnQuoteRequestId to set
	 */
	public void setBuildOnnQuoteRequestId(String buildOnnQuoteRequestId) {
		this.buildOnnQuoteRequestId = buildOnnQuoteRequestId;
	}

	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * @param zipcode
	 *            the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * @return the customerEmail
	 */
	public String getCustomerEmail() {
		return customerEmail;
	}

	/**
	 * @param customerEmail
	 *            the customerEmail to set
	 */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	/**
	 * @return the customerContactNumber
	 */
	public String getCustomerContactNumber() {
		return customerContactNumber;
	}

	/**
	 * @param customerContactNumber
	 *            the customerContactNumber to set
	 */
	public void setCustomerContactNumber(String customerContactNumber) {
		this.customerContactNumber = customerContactNumber;
	}

	/**
	 * @return the productsName
	 */
	public Set<String> getProductsName() {
		return productsName;
	}

	/**
	 * @param productsName
	 *            the productsName to set
	 */
	public void setProductsName(Set<String> productsName) {
		this.productsName = productsName;
	}

	/**
	 * @return the sellersName
	 */
	public Set<String> getSellersName() {
		return sellersName;
	}

	/**
	 * @param sellersName
	 *            the sellersName to set
	 */
	public void setSellersName(Set<String> sellersName) {
		this.sellersName = sellersName;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
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
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the followupDate
	 */
	public String getFollowupDate() {
		return followupDate;
	}

	/**
	 * @param followupDate
	 *            the followupDate to set
	 */
	public void setFollowupDate(String followupDate) {
		this.followupDate = followupDate;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
