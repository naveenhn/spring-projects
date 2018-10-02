package com.sarvah.mbg.domain.mail;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sarvah.mbg.domain.sms.ItemInfoForCommunication;

public class MailContent implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MailContent.class);
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	/**
	 * MailContent fields
	 */
	private String from;

	private Set<String> to;

	private Set<String> cc;

	private Set<String> bcc;

	private String contentType;

	private String templateName;

	private String userId;

	private String firstName;

	private String lastName;

	private String fullName;

	private String orderId;

	private String subjectInfo;

	private String token;

	private String otp;

	private String itemCount;

	private String itemName;

	private String itemQuantity;

	private String orderAmount;

	private String zipcode;

	private String minDeliveryTimeInDays;

	private String maxDeliveryTimeInDays;

	private String dispatchId;

	private String roleName;

	private String architectOrInteriorContactNumber;

	private String projectType;

	private String projectBudget;

	private String deliverBy;

	private List<ItemInfoForCommunication> itemInfoForCommunicationList;

	private String shippingType;

	private String totalSavings;

	private String taxAmt;

	private String instantOrderMobile;

	private String instantOrderEmail;

	private String instantOrderCustomerName;

	private String instantOrderTextInfo;

	private String instantOrderDeliveryTime;

	private String instantOrderImageUrl;

	private String invoiceUrlForDealer;

	private String invoiceUrlForCustomer;

	private String address;

	private String creditDays;

	private String paymentMode;

	private String date;

	public MailContent(String from, Set<String> to, String subjectInfo,
			String firstName, String lastName, String fullName,
			String templateName, String orderId, Set<String> bcc, String token,
			String otp, String itemCount, String itemName, String itemQuantity,
			String orderAmount, String zipcode, String minDeliveryTimeInDays,
			String maxDeliveryTimeInDays, String dispatchId, String roleName,
			String architectOrInteriorContactNumber, String projectType,
			String projectBudget, String deliverBy, String instantOrderMobile,
			String instantOrderEmail, String instantOrderCustomerName,
			String instantOrderTextInfo, String instantOrderDeliveryTime,
			String instantOrderImageUrl, String invoiceUrlForDealer,
			String invoiceUrlForCustomer, String creditDays,
			String paymentMode, String date) {
		LOGGER.info("validating the from address");
		if (StringUtils.isEmpty(from) || !validateEmail(from)) {
			throw new IllegalArgumentException("From");
		}
		this.from = from;

		LOGGER.info("validating the to address");
		if (CollectionUtils.isEmpty(to) || !validateEmail(to)) {
			throw new IllegalArgumentException(
					"To addresses does not match a valid email");
		}
		this.to = to;

		LOGGER.info("validating the bcc address");
		if (CollectionUtils.isEmpty(bcc) || !validateEmail(bcc)) {
			throw new IllegalArgumentException(
					"Bcc addresses does not match a valid email");
		}
		this.bcc = bcc;

		if (StringUtils.isEmpty(subjectInfo)) {
			throw new IllegalArgumentException("SubjectInfo");
		}
		this.subjectInfo = subjectInfo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.templateName = templateName;
		this.orderId = orderId;
		this.token = token;
		this.otp = otp;
		this.itemCount = itemCount;
		this.itemName = itemName;
		this.itemQuantity = itemQuantity;
		this.orderAmount = orderAmount;
		this.zipcode = zipcode;
		this.minDeliveryTimeInDays = minDeliveryTimeInDays;
		this.maxDeliveryTimeInDays = maxDeliveryTimeInDays;
		this.dispatchId = dispatchId;
		this.roleName = roleName;
		this.architectOrInteriorContactNumber = architectOrInteriorContactNumber;
		this.projectType = projectType;
		this.projectBudget = projectBudget;
		this.deliverBy = deliverBy;
		this.instantOrderMobile = instantOrderMobile;
		this.instantOrderEmail = instantOrderEmail;
		this.instantOrderCustomerName = instantOrderCustomerName;
		this.instantOrderTextInfo = instantOrderTextInfo;
		this.instantOrderDeliveryTime = instantOrderDeliveryTime;
		this.instantOrderImageUrl = instantOrderImageUrl;
		this.invoiceUrlForDealer = invoiceUrlForDealer;
		this.invoiceUrlForCustomer = invoiceUrlForCustomer;
		this.creditDays = creditDays;
		this.paymentMode = paymentMode;
		this.date = date;
	}

	public MailContent(String from, Set<String> to, String subjectInfo,
			String userId, String firstName, String lastName, String fullName,
			String templateName, String orderId, Set<String> bcc, String token,
			String otp, String itemCount, String itemName, String itemQuantity,
			String orderAmount, String zipcode, String minDeliveryTimeInDays,
			String maxDeliveryTimeInDays, String dispatchId, String roleName,
			String architectOrInteriorContactNumber, String projectType,
			String projectBudget, String deliverBy,
			List<ItemInfoForCommunication> itemInfoForCommunicationList,
			String shippingType, String totalSavings, String address,
			String instantOrderCustomerName, String instantOrderMobile,
			String instantOrderEmail, String paymentMode, String creditDays,
			String instantOrderDeliveryTime, String instantOrderTextInfo,
			String taxAmt) {
		LOGGER.info("validating the from address");
		if (StringUtils.isEmpty(from) || !validateEmail(from)) {
			throw new IllegalArgumentException("From");
		}
		this.from = from;

		LOGGER.info("validating the to address");
		if (CollectionUtils.isEmpty(to) || !validateEmail(to)) {
			throw new IllegalArgumentException(
					"To addresses does not match a valid email");
		}
		this.to = to;

		LOGGER.info("validating the bcc address");
		if (CollectionUtils.isEmpty(bcc) || !validateEmail(bcc)) {
			throw new IllegalArgumentException(
					"Bcc addresses does not match a valid email");
		}
		this.bcc = bcc;

		if (StringUtils.isEmpty(subjectInfo)) {
			throw new IllegalArgumentException("SubjectInfo");
		}
		this.subjectInfo = subjectInfo;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.templateName = templateName;
		this.orderId = orderId;
		this.token = token;
		this.otp = otp;
		this.itemCount = itemCount;
		this.itemName = itemName;
		this.itemQuantity = itemQuantity;
		this.orderAmount = orderAmount;
		this.zipcode = zipcode;
		this.minDeliveryTimeInDays = minDeliveryTimeInDays;
		this.maxDeliveryTimeInDays = maxDeliveryTimeInDays;
		this.dispatchId = dispatchId;
		this.roleName = roleName;
		this.architectOrInteriorContactNumber = architectOrInteriorContactNumber;
		this.projectType = projectType;
		this.projectBudget = projectBudget;
		this.deliverBy = deliverBy;
		this.itemInfoForCommunicationList = itemInfoForCommunicationList;
		this.shippingType = shippingType;
		this.totalSavings = totalSavings;
		this.address = address;
		this.instantOrderCustomerName = instantOrderCustomerName;
		this.instantOrderMobile = instantOrderMobile;
		this.instantOrderEmail = instantOrderEmail;
		this.paymentMode = paymentMode;
		this.creditDays = creditDays;
		this.instantOrderDeliveryTime = instantOrderDeliveryTime;
		this.instantOrderTextInfo = instantOrderTextInfo;
		this.taxAmt = taxAmt;
	}

	/**
	 * 
	 * @param emailStrings
	 * @return
	 */
	public boolean validateEmail(String... emailStrings) {
		boolean valid = false;
		for (String email : emailStrings) {

			if (email != null && email.matches(EMAIL_PATTERN)) {
				valid = true;
			}
		}

		return valid;

	}

	/**
	 * 
	 * @param emails
	 * @return
	 */
	public boolean validateEmail(Collection<String> emails) {
		return validateEmail(emails.toArray(new String[emails.size()]));
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public Set<String> getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(Set<String> to) {
		this.to = to;
	}

	/**
	 * @return the cc
	 */
	public Set<String> getCc() {
		return cc;
	}

	/**
	 * @param cc
	 *            the cc to set
	 */
	public void setCc(Set<String> cc) {
		this.cc = cc;
	}

	/**
	 * @return the bcc
	 */
	public Set<String> getBcc() {
		return bcc;
	}

	/**
	 * @param bcc
	 *            the bcc to set
	 */
	public void setBcc(Set<String> bcc) {
		this.bcc = bcc;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType
	 *            the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @param templateName
	 *            the templateName to set
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
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
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the otp
	 */
	public String getOtp() {
		return otp;
	}

	/**
	 * @param otp
	 *            the otp to set
	 */
	public void setOtp(String otp) {
		this.otp = otp;
	}

	/**
	 * @return the itemCount
	 */
	public String getItemCount() {
		return itemCount;
	}

	/**
	 * @param itemCount
	 *            the itemCount to set
	 */
	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName
	 *            the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the itemQuantity
	 */
	public String getItemQuantity() {
		return itemQuantity;
	}

	/**
	 * @param itemQuantity
	 *            the itemQuantity to set
	 */
	public void setItemQuantity(String itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	/**
	 * @return the orderAmount
	 */
	public String getOrderAmount() {
		return orderAmount;
	}

	/**
	 * @param orderAmount
	 *            the orderAmount to set
	 */
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
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
	 * @return the minDeliveryTimeInDays
	 */
	public String getMinDeliveryTimeInDays() {
		return minDeliveryTimeInDays;
	}

	/**
	 * @param minDeliveryTimeInDays
	 *            the minDeliveryTimeInDays to set
	 */
	public void setMinDeliveryTimeInDays(String minDeliveryTimeInDays) {
		this.minDeliveryTimeInDays = minDeliveryTimeInDays;
	}

	/**
	 * @return the maxDeliveryTimeInDays
	 */
	public String getMaxDeliveryTimeInDays() {
		return maxDeliveryTimeInDays;
	}

	/**
	 * @param maxDeliveryTimeInDays
	 *            the maxDeliveryTimeInDays to set
	 */
	public void setMaxDeliveryTimeInDays(String maxDeliveryTimeInDays) {
		this.maxDeliveryTimeInDays = maxDeliveryTimeInDays;
	}

	/**
	 * @return the dispatchId
	 */
	public String getDispatchId() {
		return dispatchId;
	}

	/**
	 * @param dispatchId
	 *            the dispatchId to set
	 */
	public void setDispatchId(String dispatchId) {
		this.dispatchId = dispatchId;
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

	/**
	 * @return the architectOrInteriorContactNumber
	 */
	public String getArchitectOrInteriorContactNumber() {
		return architectOrInteriorContactNumber;
	}

	/**
	 * @param architectOrInteriorContactNumber
	 *            the architectOrInteriorContactNumber to set
	 */
	public void setArchitectOrInteriorContactNumber(
			String architectOrInteriorContactNumber) {
		this.architectOrInteriorContactNumber = architectOrInteriorContactNumber;
	}

	/**
	 * @return the projectType
	 */
	public String getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            the projectType to set
	 */
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	/**
	 * @return the projectBudget
	 */
	public String getProjectBudget() {
		return projectBudget;
	}

	/**
	 * @param projectBudget
	 *            the projectBudget to set
	 */
	public void setProjectBudget(String projectBudget) {
		this.projectBudget = projectBudget;
	}

	/**
	 * @return the deliverBy
	 */
	public String getDeliverBy() {
		return deliverBy;
	}

	/**
	 * @param deliverBy
	 *            the deliverBy to set
	 */
	public void setDeliverBy(String deliverBy) {
		this.deliverBy = deliverBy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
		// return super.toString();
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the subjectInfo
	 */
	public String getSubjectInfo() {
		return subjectInfo;
	}

	/**
	 * @param subjectInfo
	 *            the subjectInfo to set
	 */
	public void setSubjectInfo(String subjectInfo) {
		this.subjectInfo = subjectInfo;
	}

	/**
	 * @return the itemInfoForCommunicationList
	 */
	public List<ItemInfoForCommunication> getItemInfoForCommunicationList() {
		return itemInfoForCommunicationList;
	}

	/**
	 * @param itemInfoForCommunicationList
	 *            the itemInfoForCommunicationList to set
	 */
	public void setItemInfoForCommunicationList(
			List<ItemInfoForCommunication> itemInfoForCommunicationList) {
		this.itemInfoForCommunicationList = itemInfoForCommunicationList;
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
	 * @return the totalSavings
	 */
	public String getTotalSavings() {
		return totalSavings;
	}

	/**
	 * @param totalSavings
	 *            the totalSavings to set
	 */
	public void setTotalSavings(String totalSavings) {
		this.totalSavings = totalSavings;
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
	 * @return the instantOrderMobile
	 */
	public String getInstantOrderMobile() {
		return instantOrderMobile;
	}

	/**
	 * @param instantOrderMobile
	 *            the instantOrderMobile to set
	 */
	public void setInstantOrderMobile(String instantOrderMobile) {
		this.instantOrderMobile = instantOrderMobile;
	}

	/**
	 * @return the instantOrderEmail
	 */
	public String getInstantOrderEmail() {
		return instantOrderEmail;
	}

	/**
	 * @param instantOrderEmail
	 *            the instantOrderEmail to set
	 */
	public void setInstantOrderEmail(String instantOrderEmail) {
		this.instantOrderEmail = instantOrderEmail;
	}

	/**
	 * @return the instantOrderCustomerName
	 */
	public String getInstantOrderCustomerName() {
		return instantOrderCustomerName;
	}

	/**
	 * @param instantOrderCustomerName
	 *            the instantOrderCustomerName to set
	 */
	public void setInstantOrderCustomerName(String instantOrderCustomerName) {
		this.instantOrderCustomerName = instantOrderCustomerName;
	}

	/**
	 * @return the instantOrderTextInfo
	 */
	public String getInstantOrderTextInfo() {
		return instantOrderTextInfo;
	}

	/**
	 * @param instantOrderTextInfo
	 *            the instantOrderTextInfo to set
	 */
	public void setInstantOrderTextInfo(String instantOrderTextInfo) {
		this.instantOrderTextInfo = instantOrderTextInfo;
	}

	/**
	 * @return the instantOrderDeliveryTime
	 */
	public String getInstantOrderDeliveryTime() {
		return instantOrderDeliveryTime;
	}

	/**
	 * @param instantOrderDeliveryTime
	 *            the instantOrderDeliveryTime to set
	 */
	public void setInstantOrderDeliveryTime(String instantOrderDeliveryTime) {
		this.instantOrderDeliveryTime = instantOrderDeliveryTime;
	}

	/**
	 * @return the instantOrderImageUrl
	 */
	public String getInstantOrderImageUrl() {
		return instantOrderImageUrl;
	}

	/**
	 * @param instantOrderImageUrl
	 *            the instantOrderImageUrl to set
	 */
	public void setInstantOrderImageUrl(String instantOrderImageUrl) {
		this.instantOrderImageUrl = instantOrderImageUrl;
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
	 * @return the invoiceUrlForDealer
	 */
	public String getInvoiceUrlForDealer() {
		return invoiceUrlForDealer;
	}

	/**
	 * @param invoiceUrlForDealer
	 *            the invoiceUrlForDealer to set
	 */
	public void setInvoiceUrlForDealer(String invoiceUrlForDealer) {
		this.invoiceUrlForDealer = invoiceUrlForDealer;
	}

	/**
	 * @return the invoiceUrlForCustomer
	 */
	public String getInvoiceUrlForCustomer() {
		return invoiceUrlForCustomer;
	}

	/**
	 * @param invoiceUrlForCustomer
	 *            the invoiceUrlForCustomer to set
	 */
	public void setInvoiceUrlForCustomer(String invoiceUrlForCustomer) {
		this.invoiceUrlForCustomer = invoiceUrlForCustomer;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode
	 *            the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @return the creditDays
	 */
	public String getCreditDays() {
		return creditDays;
	}

	/**
	 * @param creditDays
	 *            the creditDays to set
	 */
	public void setCreditDays(String creditDays) {
		this.creditDays = creditDays;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the taxAmt
	 */
	public String getTaxAmt() {
		return taxAmt;
	}

	/**
	 * @param taxAmt
	 *            the taxAmt to set
	 */
	public void setTaxAmt(String taxAmt) {
		this.taxAmt = taxAmt;
	}
}
