package com.sarvah.mbg.domain.sms;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsContent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SmsContent.class);

	private static final String PHONE_PATTERN = "^\\+{0,1}[0-9]{0,2}\\-{0,1}[0-9]{10}$";
	private String from;
	private Set<String> to;
	private String firstName;
	private String lastName;
	private String fullName;
	private String templateName;
	private String orderId;
	private SmsType type;
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
	private String address;
	private String instantOrderEmail;
	private String instantOrderMobile;
	private String instantOrderTextInfo;
	private String promoInfo;
	private String date;

	public enum SmsType {
		PROMOTIONAL, TRANSACTIONAL
	}

	public SmsContent(String from, Set<String> to, String firstName,
			String lastName, String fullName, String templateName,
			String orderId, SmsType type, String otp, String itemCount,
			String itemName, String itemQuantity, String orderAmount,
			String zipcode, String minDeliveryTimeInDays,
			String maxDeliveryTimeInDays, String dispatchId, String roleName,
			String architectOrInteriorContactNumber, String projectType,
			String projectBudget, String deliverBy, String address,
			String instantOrderEmail, String instantOrderMobile,
			String instantOrderTextInfo, String promoInfo, String date) {
		LOGGER.info("checking the from address");

		if (StringUtils.isBlank(from) || !validatePhoneNumber(from)) {
			throw new IllegalArgumentException(
					"from number does not match a valid phone number");
		}
		this.from = from;

		LOGGER.info("checking the to address");
		if (CollectionUtils.isEmpty(to) || !validatePhoneNumber(to)) {
			throw new IllegalArgumentException(
					"To number does not match a valid phone number");
		}
		this.to = to;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.templateName = templateName;
		this.orderId = orderId;
		this.type = type;
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
		this.address = address;
		this.instantOrderEmail = instantOrderEmail;
		this.instantOrderMobile = instantOrderMobile;
		this.instantOrderTextInfo = instantOrderTextInfo;
		this.promoInfo = promoInfo;
		this.date = date;
	}

	public String getInstantOrderEmail() {
		return instantOrderEmail;
	}

	public void setInstantOrderEmail(String instantOrderEmail) {
		this.instantOrderEmail = instantOrderEmail;
	}

	public String getInstantOrderMobile() {
		return instantOrderMobile;
	}

	public void setInstantOrderMobile(String instantOrderMobile) {
		this.instantOrderMobile = instantOrderMobile;
	}

	public String getInstantOrderTextInfo() {
		return instantOrderTextInfo;
	}

	public void setInstantOrderTextInfo(String instantOrderTextInfo) {
		this.instantOrderTextInfo = instantOrderTextInfo;
	}

	/**
	 * 
	 * @param emailStrings
	 * @return
	 */
	public boolean validatePhoneNumber(String... phones) {
		boolean valid = false;
		for (String phone : phones) {

			if (phone != null && phone.matches(PHONE_PATTERN)) {
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
	public boolean validatePhoneNumber(Collection<String> phone) {
		return validatePhoneNumber(phone.toArray(new String[phone.size()]));
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
	 * @return the type
	 */
	public SmsType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(SmsType type) {
		this.type = type;
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
	 * @return the promoInfo
	 */
	public String getPromoInfo() {
		return promoInfo;
	}

	/**
	 * @param promoInfo
	 *            the promoInfo to set
	 */
	public void setPromoInfo(String promoInfo) {
		this.promoInfo = promoInfo;
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
}
