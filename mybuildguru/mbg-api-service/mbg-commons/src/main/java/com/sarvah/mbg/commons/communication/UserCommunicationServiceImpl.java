/**
 * 
 */
package com.sarvah.mbg.commons.communication;

import static com.sarvah.mbg.commons.communication.NotificationConstants.getEmailNotifMap;
import static com.sarvah.mbg.commons.communication.NotificationConstants.getSmsNotifiMap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.commons.email.MailSenderService;
import com.sarvah.mbg.commons.sms.SmsSenderService;
import com.sarvah.mbg.domain.mail.MailContent;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.common.contact.Phone;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.UserStatus;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.domain.sms.ItemInfoForCommunication;
import com.sarvah.mbg.domain.sms.SmsContent;
import com.sarvah.mbg.domain.sms.SmsContent.SmsType;

/**
 * @author shivu
 *
 */
@Service
public class UserCommunicationServiceImpl implements UserCommunicationService {
	private static final Logger logger = LoggerFactory
			.getLogger(UserCommunicationServiceImpl.class);

	@Autowired
	private MailSenderService mailSenderService;

	@Autowired
	private SmsSenderService smsSenderService;

	public void sendMail(String notificationName,
			Map<String, String> userInfoMap) throws JMSException {
		String orderId = userInfoMap.get("OrderId");
		String firstName = userInfoMap.get("FirstName");
		String lastName = userInfoMap.get("LastName");
		String fullName = userInfoMap.get("FullName");
		String subjectInfo = userInfoMap.get("SubjectInfo");
		String email = userInfoMap.get("Email");
		String token = userInfoMap.get("Token");
		String otp = userInfoMap.get("OTP");
		String itemCount = userInfoMap.get("ItemCount");
		String itemName = userInfoMap.get("ItemName");
		String itemQuantity = userInfoMap.get("ItemQuantity");
		String orderAmount = userInfoMap.get("OrderAmount");
		String zipcode = userInfoMap.get("Zipcode");
		String minDeliveryTimeInDays = userInfoMap.get("MinDeliveryTimeInDays");
		String maxDeliveryTimeInDays = userInfoMap.get("MaxDeliveryTimeInDays");
		String dispatchId = userInfoMap.get("DispatchId");
		String roleName = userInfoMap.get("RoleName");
		String architectOrInteriorContectNumber = userInfoMap
				.get("ContactNumber");
		String projectType = userInfoMap.get("ProjectType");
		String projectBudget = userInfoMap.get("ProjectBudget");
		String deliverBy = userInfoMap.get("DeliverBy");
		String instantOrderMobile = userInfoMap.get("InstantOrderMobile");
		String instantOrderEmail = userInfoMap.get("InstantOrderEmail");
		String instantOrderCustomerName = userInfoMap
				.get("InstantOrderCustomerName");
		String instantOrderTextInfo = userInfoMap.get("InstantOrderTextInfo");
		String instantOrderDeliveryTime = userInfoMap
				.get("InstantOrderDeliveryTime");
		String instantOrderImageUrl = userInfoMap.get("InstantOrderImageUrl");

		String invoiceUrlForDealer = userInfoMap.get("InvoiceUrlForDealer");

		String invoiceUrlForEnduser = userInfoMap.get("InvoiceUrlForEnduser");

		String creditDays = userInfoMap.get("CreditDays");

		String paymentMode = userInfoMap.get("PaymentMode");

		String date = userInfoMap.get("Date");
		logger.info("Sender Side email service running");
		Set<String> toSet = new HashSet<>();
		toSet.add(email);
		Set<String> bccSet = new HashSet<>();
		bccSet.add(email);
		MailContent mailContent = new MailContent("team-buildonn@buildonn.com",
				toSet, subjectInfo, firstName, lastName, fullName,
				notificationName, orderId, bccSet, token, otp, itemCount,
				itemName, itemQuantity, orderAmount, zipcode,
				minDeliveryTimeInDays, maxDeliveryTimeInDays, dispatchId,
				roleName, architectOrInteriorContectNumber, projectType,
				projectBudget, deliverBy, instantOrderMobile,
				instantOrderEmail, instantOrderCustomerName,
				instantOrderTextInfo, instantOrderDeliveryTime,
				instantOrderImageUrl, invoiceUrlForDealer,
				invoiceUrlForEnduser, creditDays, paymentMode, date);
		mailSenderService.send(mailContent);
		logger.info("sending Email to Azure Queue");
	}

	public void sendMail(String notificationName,
			Map<String, String> userInfoMap,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws JMSException {
		String orderId = userInfoMap.get("OrderId");
		String userId = userInfoMap.get("UserId");
		String firstName = userInfoMap.get("FirstName");
		String lastName = userInfoMap.get("LastName");
		String fullName = userInfoMap.get("FullName");
		String subjectInfo = userInfoMap.get("SubjectInfo");
		String email = userInfoMap.get("Email");
		String token = userInfoMap.get("Token");
		String otp = userInfoMap.get("OTP");
		String itemCount = userInfoMap.get("ItemCount");
		String itemName = userInfoMap.get("ItemName");
		String itemQuantity = userInfoMap.get("ItemQuantity");
		String orderAmount = userInfoMap.get("OrderAmount");
		String zipcode = userInfoMap.get("Zipcode");
		String minDeliveryTimeInDays = userInfoMap.get("MinDeliveryTimeInDays");
		String maxDeliveryTimeInDays = userInfoMap.get("MaxDeliveryTimeInDays");
		String dispatchId = userInfoMap.get("DispatchId");
		String roleName = userInfoMap.get("RoleName");
		String architectOrInteriorContectNumber = userInfoMap
				.get("ContactNumber");
		String projectType = userInfoMap.get("ProjectType");
		String projectBudget = userInfoMap.get("ProjectBudget");
		String deliverBy = userInfoMap.get("DeliverBy");
		String shippingType = userInfoMap.get("ShippingType");
		String totalSavings = userInfoMap.get("TotalSavings");
		String address = userInfoMap.get("Address");
		String instantOrderCustomerName = userInfoMap
				.get("InstantOrderCustomerName");
		String instantOrderMobile = userInfoMap.get("InstantOrderMobile");
		String instantOrderEmail = userInfoMap.get("InstantOrderEmail");
		String paymentMode = userInfoMap.get("PaymentMode");
		String creditDays = userInfoMap.get("CreditDays");
		String instantOrderDeliveryTime = userInfoMap
				.get("InstantOrderDeliveryTime");
		String instantOrderTextInfo = userInfoMap.get("InstantOrderTextInfo");
		String taxAmt = userInfoMap.get("TaxAmt");

		logger.info("Sender Side email service running");
		Set<String> toSet = new HashSet<>();
		toSet.add(email);
		Set<String> bccSet = new HashSet<>();
		bccSet.add(email);
		MailContent mailContent = new MailContent("team-buildonn@buildonn.com",
				toSet, subjectInfo, userId, firstName, lastName, fullName,
				notificationName, orderId, bccSet, token, otp, itemCount,
				itemName, itemQuantity, orderAmount, zipcode,
				minDeliveryTimeInDays, maxDeliveryTimeInDays, dispatchId,
				roleName, architectOrInteriorContectNumber, projectType,
				projectBudget, deliverBy, itemInfoForCommunicationList,
				shippingType, totalSavings, address, instantOrderCustomerName,
				instantOrderMobile, instantOrderEmail, paymentMode, creditDays,
				instantOrderDeliveryTime, instantOrderTextInfo, taxAmt);
		mailSenderService.send(mailContent);
		logger.info("sending Email to Azure Queue");
	}

	public void sendSMS(String notificationName, Map<String, String> userInfoMap)
			throws JMSException {
		String orderId = userInfoMap.get("OrderId");
		String firstName = userInfoMap.get("FirstName");
		String lastName = userInfoMap.get("LastName");
		String fullName = userInfoMap.get("FullName");
		String type = userInfoMap.get("Type");
		String otp = userInfoMap.get("OTP");
		String phoneNumber = userInfoMap.get("PhoneNumber");
		String itemCount = userInfoMap.get("ItemCount");
		String itemName = userInfoMap.get("ItemName");
		String itemQuantity = userInfoMap.get("ItemQuantity");
		String orderAmount = userInfoMap.get("OrderAmount");
		String zipcode = userInfoMap.get("Zipcode");
		String minDeliveryTimeInDays = userInfoMap.get("MinDeliveryTimeInDays");
		String maxDeliveryTimeInDays = userInfoMap.get("MaxDeliveryTimeInDays");
		String dispatchId = userInfoMap.get("DispatchId");
		String roleName = userInfoMap.get("RoleName");
		String architectOrInteriorContectNumber = userInfoMap
				.get("ContactNumber");
		String projectType = userInfoMap.get("ProjectType");
		String projectBudget = userInfoMap.get("ProjectBudget");
		String deliverBy = userInfoMap.get("DeliverBy");
		String address = userInfoMap.get("Address");
		String instantOrderEmail = userInfoMap.get("InstantOrderEmail");
		String instantOrderMobile = userInfoMap.get("InstantOrderMobile");
		String instantOrderTextInfo = userInfoMap.get("InstantOrderTextInfo");
		String promoInfo = userInfoMap.get("PromoInfo");
		String date = userInfoMap.get("Date");
		logger.info("Sender side sms program is running");
		Set<String> to = new HashSet<String>();
		String[] phNoAry = phoneNumber.split(",");
		for (String phNo : phNoAry) {
			to.add(phNo);
		}

		SmsContent smscontent = new SmsContent("9916406611", to, firstName,
				lastName, fullName, notificationName, orderId,
				SmsType.valueOf(type), otp, itemCount, itemName, itemQuantity,
				orderAmount, zipcode, minDeliveryTimeInDays,
				maxDeliveryTimeInDays, dispatchId, roleName,
				architectOrInteriorContectNumber, projectType, projectBudget,
				deliverBy, address, instantOrderEmail, instantOrderMobile,
				instantOrderTextInfo, promoInfo, date);
		smsSenderService.send(smscontent);
		logger.info("Sending SMS to Azure Queue");
	}

	// Sending Registration info mail and sms
	@Override
	public void sendRegistrationInfo(User user, String token)
			throws UserCommException {
		logger.info(
				"Requesting to send Registration Email and SMS for userName : {}",
				user.getUsername());
		String notificationName = null;
		String phno = null;
		String email = null;
		Map<String, String> map = new HashMap<>();
		Set<Role> userRole = user.getRoles();
		for (Role role : userRole) {
			if (role.getName().equalsIgnoreCase("PROVIDER")) {
				notificationName = NotificationConstants.PROVIDER_REGISTRATION;
				map.put("SubjectInfo", "Buildonn: Signup Verification mail!!");
			} else if (role.getName().equalsIgnoreCase("DEALER")) {
				notificationName = NotificationConstants.DEALER_REGISTRATION;
				map.put("SubjectInfo", "Buildonn: Signup Verification mail!!");
			} else if (role.getName().equalsIgnoreCase("ARCHITECT")) {
				notificationName = NotificationConstants.ARCHITECT_REGISTRATION;
				map.put("SubjectInfo", "Buildonn: Signup Verification mail!!");
			} else if (role.getName().equalsIgnoreCase("INTERIORDESIGNER")) {
				notificationName = NotificationConstants.INTERIORDESIGNER_REGISTRATION;
				map.put("SubjectInfo", "Buildonn: Signup Verification mail!!");
			} else if (role.getName().equalsIgnoreCase("ENDUSER")) {
				notificationName = NotificationConstants.ENDUSER_REGISTRATION;
				map.put("SubjectInfo", "Buildonn: Signup Verification mail!!");
			} else if (role.getName().equalsIgnoreCase("BUSINESSASSOCIATE")) {
				notificationName = NotificationConstants.BUSINESSASSOCIATE_REGISTRATION;
				map.put("SubjectInfo", "Buildonn: Signup Verification mail!!");
			} else if (role.getName().equalsIgnoreCase("CIVILENGINEER")) {
				notificationName = NotificationConstants.CIVILENGINEER_REGISTRATION;
				map.put("SubjectInfo", "Buildonn: Signup Verification mail!!");
			} else if (role.getName().equalsIgnoreCase("PLUMBER")
					|| role.getName().equalsIgnoreCase("PAINTER")
					|| role.getName().equalsIgnoreCase("CARPENTER")
					|| role.getName().equalsIgnoreCase("VASTHUCONSULTANTS")
					|| role.getName().equalsIgnoreCase("ELECTRICIANS")
					|| role.getName().equalsIgnoreCase("BIMCONSULTANTS")
					|| role.getName().equalsIgnoreCase("PROJECTCONSULTANT")
					|| role.getName().equalsIgnoreCase("LANDSCAPER")
					|| role.getName().equalsIgnoreCase("SURVEYOR")
					|| role.getName().equalsIgnoreCase("WATERDIVINER")
					|| role.getName().equalsIgnoreCase("VALUATOR")
					|| role.getName().equalsIgnoreCase("BOREWELLER")
					|| role.getName().equalsIgnoreCase("FLOORINGCONTRACTOR")
					|| role.getName().equalsIgnoreCase("FABRICATOR")
					|| role.getName().equalsIgnoreCase("GEOLOGIST")
					|| role.getName().equalsIgnoreCase("ELECTRICALCONSULTANT")) {
				notificationName = NotificationConstants.SERVICEPROVIDER_REGISTRATION;
				map.put("SubjectInfo", "Buildonn: Signup Verification mail!!");
			}

			if (user.getFirstName() != null) {
				map.put("FirstName", user.getFirstName());
			}
			if (user.getLastName() != null) {
				map.put("LastName", user.getLastName());
			}
			if (user.getFullName() != null) {
				map.put("FullName", user.getFullName());
			}

			map.put("Token", token);
			map.put("Type", "TRANSACTIONAL");

			if (user.getUsername() != null) {
				email = user.getUsername();
				map.put("Email", email);
			}

			if (user.getUserPhoneNumber() != null) {
				phno = user.getUserPhoneNumber();
				map.put("PhoneNumber", phno);
			} else {
				Set<Address> addresses = user.getAddresses();
				for (Address addrs : addresses) {
					Set<Phone> phs = addrs.getPhoneNumbers();
					if (phs != null) {
						for (Phone ph : phs) {
							if (ph.isPrimary()) {
								phno = ph.getNumber();
								map.put("PhoneNumber", phno);
							}
						}
					}
				}
			}

		}
		try {
			if (StringUtils.isNotBlank(user.getId())
					&& StringUtils.isNotBlank(notificationName)) {
				if (getEmailNotifMap().containsKey(notificationName)
						&& getEmailNotifMap().get(notificationName)
						&& email != null) {
					sendMail(notificationName, map);
				}
				if (getSmsNotifiMap().containsKey(notificationName)
						&& getSmsNotifiMap().get(notificationName)
						&& phno != null) {
					sendSMS(notificationName, map);
				}
			}
		} catch (JMSException e) {
			logger.error(
					"JMS Exception occured while sending Registration mail/sms for username {}",
					user.getUsername());
			throw new UserCommException(
					"JMS Exception occured while sending Registration mail/sms for user",
					e);
		}
	}

	// Sending welcome info mail and sms
	@Override
	public void sendVerifyAndWecomeInfo(User user) throws UserCommException {
		logger.info(
				"Requesting to send welcome Email and SMS for username : {}",
				user.getUsername());
		String notificationName = null;
		String phno = null;
		String email = null;
		Map<String, String> map = new HashMap<>();
		Set<Role> userRole = user.getRoles();
		for (Role role : userRole) {
			if (role.getName().equalsIgnoreCase("PROVIDER")) {
				notificationName = NotificationConstants.PROVIDER_WELCOME;
				map.put("SubjectInfo",
						"Welcome to Buildonn!! Congratulations on joining the Buildonn family!!");
			} else if (role.getName().equalsIgnoreCase("DEALER")) {
				notificationName = NotificationConstants.DEALER_WELCOME;
				map.put("SubjectInfo",
						"Welcome to Buildonn!! Congratulations on joining the Buildonn family!!");
			} else if (role.getName().equalsIgnoreCase("ARCHITECT")) {
				notificationName = NotificationConstants.ARCHITECT_WELCOME;
				map.put("SubjectInfo",
						"Welcome to Buildonn!! Congratulations on joining the Buildonn family!!");
			} else if (role.getName().equalsIgnoreCase("INTERIORDESIGNER")) {
				notificationName = NotificationConstants.INTERIORDESIGNER_WELCOME;
				map.put("SubjectInfo",
						"Welcome to Buildonn!! Congratulations on joining the Buildonn family!!");
			} else if (role.getName().equalsIgnoreCase("ENDUSER")) {
				notificationName = NotificationConstants.ENDUSER_WELCOME;
				map.put("SubjectInfo",
						"Welcome to Buildonn!! Congratulations on joining the Buildonn family!!");
			} else if (role.getName().equalsIgnoreCase("BUSINESSASSOCIATE")) {
				notificationName = NotificationConstants.BUSINESSASSOCIATE_WELCOME;
				map.put("SubjectInfo",
						"Welcome to Buildonn!! Congratulations on joining the Buildonn family!!");
			} else if (role.getName().equalsIgnoreCase("CIVILENGINEER")) {
				notificationName = NotificationConstants.CIVILENGINEER_WELCOME;
				map.put("SubjectInfo",
						"Welcome to Buildonn!! Congratulations on joining the Buildonn family!!");
			}
		}

		if (user.getFirstName() != null) {
			map.put("FirstName", user.getFirstName());
		}
		if (user.getLastName() != null) {
			map.put("LastName", user.getLastName());
		}
		if (user.getFullName() != null) {
			map.put("FullName", user.getFullName());
		}
		map.put("Type", "TRANSACTIONAL");

		if (user.getUsername() != null) {
			email = user.getUsername();
			map.put("Email", email);
		}

		if (user.getUserPhoneNumber() != null) {
			phno = user.getUserPhoneNumber();
			map.put("PhoneNumber", phno);
		} else {
			Set<Address> addresses = user.getAddresses();
			for (Address addrs : addresses) {
				Set<Phone> phs = addrs.getPhoneNumbers();
				if (phs != null) {
					for (Phone ph : phs) {
						if (ph.isPrimary()) {
							phno = ph.getNumber();
							map.put("PhoneNumber", phno);
						}

					}
				}
			}
		}

		try {
			if (StringUtils.isNotBlank(user.getId())
					&& StringUtils.isNotBlank(notificationName)) {
				if (getEmailNotifMap().containsKey(notificationName)
						&& getEmailNotifMap().get(notificationName)
						&& email != null) {
					sendMail(notificationName, map);
				}
				if (getSmsNotifiMap().containsKey(notificationName)
						&& getSmsNotifiMap().get(notificationName)
						&& phno != null) {
					sendSMS(notificationName, map);
				}
			}

		} catch (JMSException e) {
			logger.error(
					"JMS Exception occured while sending verification mail/sms for username : {} ",
					user.getUsername());
			throw new UserCommException(
					"JMS Exception occured while sending Verification mail/sms for user",
					e);
		}
	}

	// Sending otp info mail and sms
	@Override
	public void sendOTPInfo(String phonenumber, String otp, String email,
			String userName) throws UserCommException {
		logger.info(
				"Requesting to send ForgetPasswordOtp Email and SMS for phoneNumber : {} and emailId : {}",
				phonenumber, email);
		Map<String, String> map = new HashMap<>();
		String notificationName = null;

		notificationName = NotificationConstants.FORGETPASSWORDOTP;
		map.put("SubjectInfo", "Buildonn: Forgot password OTP");

		map.put("FullName", userName);
		map.put("OTP", otp);
		map.put("Email", email);
		map.put("PhoneNumber", phonenumber);
		map.put("Type", "TRANSACTIONAL");
		try {
			if (StringUtils.isNotBlank(notificationName)) {

				if (getEmailNotifMap().containsKey(notificationName)
						&& getEmailNotifMap().get(notificationName)
						&& email != null) {
					sendMail(notificationName, map);
				}

				if (getSmsNotifiMap().containsKey(notificationName)
						&& getSmsNotifiMap().get(notificationName)
						&& phonenumber != null) {
					sendSMS(notificationName, map);
				}
			}

		} catch (JMSException e) {
			logger.debug(
					"JMS Exception occured while sending otp mail/sms for phoneNumber : {} and emailId : {}",
					phonenumber, email);

			throw new UserCommException(
					"JMS Exception occured while sending otp mail/sms for user",
					e);
		}
	}

	@Override
	public void sendResetPasswordInfo(String fullName, String email,
			String phonenumber) throws UserCommException {

		Map<String, String> map = new HashMap<>();
		String notificationName = null;

		notificationName = NotificationConstants.USER_RESETPASSWORD;
		map.put("SubjectInfo", "Buildonn: Your password is changed");

		map.put("FullName", fullName);
		map.put("Email", email);
		map.put("PhoneNumber", phonenumber);
		map.put("Type", "TRANSACTIONAL");
		try {
			if (StringUtils.isNotBlank(notificationName)) {

				if (getEmailNotifMap().containsKey(notificationName)
						&& getEmailNotifMap().get(notificationName)
						&& email != null) {
					sendMail(notificationName, map);
				}

				if (getSmsNotifiMap().containsKey(notificationName)
						&& getSmsNotifiMap().get(notificationName)
						&& phonenumber != null) {
					sendSMS(notificationName, map);
				}
			}

		} catch (JMSException e) {
			logger.debug("JMS Exception occured while sending reset password information");

			throw new UserCommException(
					"JMS Exception occured while sending reset password information",
					e);
		}
	}

	@Override
	public void sendUserUpdateOTPInfo(String phonenumber, String otp,
			String email, String userName) throws UserCommException {
		logger.info(
				"Requesting to send ForgetPasswordOtp Email and SMS for phoneNumber : {} and emailId : {}",
				phonenumber, email);
		Map<String, String> map = new HashMap<>();
		String notificationName = null;

		notificationName = NotificationConstants.FORGETPASSWORDOTP;
		map.put("SubjectInfo", "Buildonn: Username update OTP");

		map.put("FullName", userName);
		map.put("OTP", otp);
		map.put("Email", email);
		map.put("PhoneNumber", phonenumber);
		map.put("Type", "TRANSACTIONAL");
		try {
			if (StringUtils.isNotBlank(notificationName)) {

				if (getEmailNotifMap().containsKey(notificationName)
						&& getEmailNotifMap().get(notificationName)
						&& email != null) {
					sendMail(notificationName, map);
				}

				if (getSmsNotifiMap().containsKey(notificationName)
						&& getSmsNotifiMap().get(notificationName)
						&& phonenumber != null) {
					sendSMS(notificationName, map);
				}
			}

		} catch (JMSException e) {
			logger.debug(
					"JMS Exception occured while sending otp mail/sms for phoneNumber : {} and emailId : {}",
					phonenumber, email);

			throw new UserCommException(
					"JMS Exception occured while sending otp mail/sms for user",
					e);
		}
	}

	// Sending place order info mail and sms to customer
	@Override
	public void sendPlacedOrderInfoToCustomer(Set<User> users, String orderId,
			Integer itemCount, String itemName, String itemQuantityAndType,
			String orderAmount, String customerName, String customerZipcode,
			String totalSavings, String taxAmt,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException {
		logger.info("Requesting for send Order Placed Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();

		for (User user : users) {

			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (!(role.getName().equalsIgnoreCase("SUPERADMIN")
						|| role.getName().equalsIgnoreCase("DEALER")
						|| role.getName().equalsIgnoreCase("MBGTECHASSOCIATE") || role
						.getName().equalsIgnoreCase("MBGTELEASSOCIATE"))) {
					notificationName = NotificationConstants.ENDUSER_ORDER_PLACED;

					map.put("SubjectInfo", "Buildonn: Order " + orderId
							+ " has been successfully placed");

					map.put("UserId", user.getId());
					map.put("FullName", customerName);
					map.put("OrderId", orderId);
					map.put("Type", "TRANSACTIONAL");
					map.put("ItemCount", String.valueOf(itemCount));
					map.put("ItemName", itemName);
					map.put("ItemQuantity", itemQuantityAndType);
					map.put("OrderAmount", orderAmount);
					map.put("Zipcode", customerZipcode);
					map.put("TotalSavings", totalSavings);
					map.put("TaxAmt", taxAmt);

					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						Set<Address> addresses = user.getAddresses();
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}
								}
							}
						}
					}

					try {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										itemInfoForCommunicationList);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}

					} catch (JMSException e) {

						logger.error("JMS Exception occured while sending Order Placed mail/sms for user");

						throw new UserCommException(
								"JMS Exception occured while sending Order Placed mail/sms for user",
								e);
					}
				}
			}
		}
	}

	// Sending place order info mail and sms to admin
	@Override
	public void sendPlacedOrderInfoToAdmin(User user, String orderId,
			Integer itemCount, String itemName, String itemQuantityAndType,
			String orderAmount, String customerName, String customerZipcode,
			String totalSavings, String taxAmt,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException {
		logger.info("Requesting for send Order Placed Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		if (user != null) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (role.getName().equalsIgnoreCase("SUPERADMIN")) {
					if (itemCount == 1) {
						notificationName = NotificationConstants.ADMIN_SINGLEITEMORDER_PLACED;
						map.put("SubjectInfo", "Buildonn: Knock Knock!! Order "
								+ orderId + " - Placed from " + customerName
								+ " zipcode : " + customerZipcode);
					} else if (itemCount > 1) {
						notificationName = NotificationConstants.ADMIN_MULTIITEMORDER_PLACED;
						map.put("SubjectInfo",
								"Buildonn: Knock Knock!! Order ID: " + orderId
										+ " - Placed from " + customerName
										+ " zipcode : " + customerZipcode);
					}

					map.put("FullName", customerName);
					map.put("OrderId", orderId);
					map.put("Type", "TRANSACTIONAL");
					map.put("ItemCount", String.valueOf(itemCount));
					map.put("ItemName", itemName);
					map.put("ItemQuantity", itemQuantityAndType);
					map.put("OrderAmount", orderAmount);
					map.put("Zipcode", customerZipcode);
					map.put("TotalSavings", totalSavings);
					map.put("TaxAmt", taxAmt);
					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						Set<Address> addresses = user.getAddresses();
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}

								}
							}
						}
					}

					try {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										itemInfoForCommunicationList);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}

					} catch (JMSException e) {

						logger.error("JMS Exception occured while sending Order Placed mail/sms for user");

						throw new UserCommException(
								"JMS Exception occured while sending Order Placed mail/sms for user",
								e);
					}
				}
			}
		}
	}

	// Sending place order info mail and sms to TeleAssociate
	@Override
	public void sendPlacedOrderInfoToTeleAssociate(List<User> teleUsers,
			String orderId, Integer itemCount, String itemName,
			String itemQuantityAndType, String orderAmount,
			String customerName, String customerZipcode, String totalSavings,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException {
		logger.info("Requesting for send Order Placed Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		for (User user : teleUsers) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (role.getName().equalsIgnoreCase("MBGTELEASSOCIATE")) {
					if (itemCount == 1) {
						notificationName = NotificationConstants.TELEASSOCIATE_SINGLEITEMORDER_PLACED;
						map.put("SubjectInfo", "Buildonn: Knock Knock!! Order "
								+ orderId + " - Placed from " + customerName
								+ " zipcode : " + customerZipcode);
					} else if (itemCount > 1) {
						notificationName = NotificationConstants.TELEASSOCIATE_MULTIITEMORDER_PLACED;
						map.put("SubjectInfo", "Buildonn: Knock Knock!! Order "
								+ orderId + " - Placed from " + customerName
								+ " zipcode : " + customerZipcode);
					}

					map.put("FullName", customerName);
					map.put("OrderId", orderId);
					map.put("Type", "TRANSACTIONAL");
					map.put("ItemCount", String.valueOf(itemCount));
					map.put("ItemName", itemName);
					map.put("ItemQuantity", itemQuantityAndType);
					map.put("OrderAmount", orderAmount);
					map.put("Zipcode", customerZipcode);
					map.put("TotalSavings", totalSavings);

					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						Set<Address> addresses = user.getAddresses();
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}

								}
							}
						}
					}
					try {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										itemInfoForCommunicationList);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}

					} catch (JMSException e) {

						logger.error("JMS Exception occured while sending Order Placed mail/sms for user");

						throw new UserCommException(
								"JMS Exception occured while sending Order Placed mail/sms for user",
								e);
					}
				}
			}
		}
	}

	@Override
	public void sendPlacedOrderInfoToTechAssociate(List<User> techUsers,
			String orderId, Integer itemCount, String itemName,
			String itemQuantityAndType, String orderAmount,
			String customerName, String customerZipcode, String totalSavings,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException {
		logger.info("Requesting for send Order Placed Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		for (User user : techUsers) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (role.getName().equalsIgnoreCase("MBGTECHASSOCIATE")) {
					if (itemCount == 1) {
						notificationName = NotificationConstants.TECHASSOCIATE_SINGLEITEMORDER_PLACED;
						map.put("SubjectInfo", "Buildonn: Knock Knock!! Order "
								+ orderId + " - Placed from " + customerName
								+ " zipcode : " + customerZipcode);
					} else if (itemCount > 1) {
						notificationName = NotificationConstants.TECHASSOCIATE_MULTIITEMORDER_PLACED;
						map.put("SubjectInfo", "Buildonn: Knock Knock!! Order "
								+ orderId + " - Placed from " + customerName
								+ " zipcode : " + customerZipcode);
					}

					map.put("FullName", customerName);
					map.put("OrderId", orderId);
					map.put("Type", "TRANSACTIONAL");
					map.put("ItemCount", String.valueOf(itemCount));
					map.put("ItemName", itemName);
					map.put("ItemQuantity", itemQuantityAndType);
					map.put("OrderAmount", orderAmount);
					map.put("Zipcode", customerZipcode);
					map.put("TotalSavings", totalSavings);

					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						Set<Address> addresses = user.getAddresses();
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}

								}
							}
						}
					}

					try {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										itemInfoForCommunicationList);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}

					} catch (JMSException e) {

						logger.error("JMS Exception occured while sending Order Placed mail/sms for user");

						throw new UserCommException(
								"JMS Exception occured while sending Order Placed mail/sms for user",
								e);
					}
				}
			}
		}
	}

	@Override
	public void sendPlacedOrderInfoToDealers(Set<User> users, String orderId,
			Integer itemCount, String itemName, String itemQuantityAndType,
			String orderAmount, String customerName, String customerZipcode,
			String shippingType,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException {
		logger.info("Requesting for send Order Placed Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		for (User user : users) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {

				if (role.getName().equalsIgnoreCase("DEALER")) {
					notificationName = NotificationConstants.DEALER_ORDER_PLACED;
					map.put("SubjectInfo", "Buildonn: Knock Knock!! Order "
							+ orderId + " - Placed from " + customerName
							+ " zipcode : " + customerZipcode);

					map.put("FullName", customerName);
					map.put("OrderId", orderId);
					map.put("Type", "TRANSACTIONAL");
					map.put("ItemCount", String.valueOf(itemCount));
					map.put("ItemName", itemName);
					map.put("ItemQuantity", itemQuantityAndType);
					map.put("OrderAmount", orderAmount);
					map.put("Zipcode", customerZipcode);
					map.put("ShippingType", shippingType);

					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						Set<Address> addresses = user.getAddresses();
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}
								}
							}
						}
					}

					try {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										itemInfoForCommunicationList);
							}
						}

					} catch (JMSException e) {

						logger.error("JMS Exception occured while sending Order Placed mail/sms for user");

						throw new UserCommException(
								"JMS Exception occured while sending Order Placed mail/sms for user",
								e);
					}
				}
			}
		}

	}

	@Override
	public void sendCancelOrderInfoToCustomer(Set<User> users, String orderId,
			Integer itemCount, String itemName, Integer ItemQuantity,
			String orderAmount, String customerName, Integer customerZipcode,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException {
		logger.info("Requesting for send Order Cancelled Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		for (User user : users) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (!(role.getName().equalsIgnoreCase("SUPERADMIN")
						|| role.getName().equalsIgnoreCase("DEALER")
						|| role.getName().equalsIgnoreCase("MBGTECHASSOCIATE") || role
						.getName().equalsIgnoreCase("MBGTELEASSOCIATE"))) {
					if (itemCount == 1) {
						notificationName = NotificationConstants.ENDUSER_SINGLEITEM_CANCELLED;
						map.put("SubjectInfo",
								"Cancel order Info with Buildonn");
					} else if (itemCount > 1) {
						notificationName = NotificationConstants.ENDUSER_MULTIITEM_CANCELLED;
						map.put("SubjectInfo",
								"Cancel order Info with Buildonn");
					}

					map.put("FullName", customerName);
					map.put("OrderId", orderId);
					map.put("Type", "TRANSACTIONAL");
					map.put("ItemCount", String.valueOf(itemCount));
					map.put("ItemName", itemName);
					map.put("ItemQuantity", String.valueOf(ItemQuantity));
					map.put("OrderAmount", orderAmount);
					map.put("Zipcode", String.valueOf(customerZipcode));

					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						Set<Address> addresses = user.getAddresses();
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}

								}
							}
						}
					}
					try {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										itemInfoForCommunicationList);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}

					} catch (JMSException e) {
						logger.error("JMS Exception occured while sending Order Cancelled mail/sms for user");

						throw new UserCommException(
								"JMS Exception occured while sending Order Cancelled mail/sms for user",
								e);
					}
				}
			}
		}
	}

	@Override
	public void sendCancelOrderInfoToAdmin(User user, String orderId,
			Integer itemCount, String itemName, Integer ItemQuantity,
			String orderAmount, String customerName, Integer customerZipcode,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException {
		logger.info("Requesting for send Order Cancelled Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		if (user != null) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (role.getName().equalsIgnoreCase("SUPERADMIN")) {
					if (itemCount == 1) {
						notificationName = NotificationConstants.ADMIN_SINGLEITEM_CANCELLED;
						map.put("SubjectInfo",
								"Buildonn: Knock Knock!! Order ID: " + orderId
										+ " – Cancelled from " + customerName
										+ " zipcode : " + customerZipcode);
					} else if (itemCount > 1) {
						notificationName = NotificationConstants.ADMIN_MULTIITEM_CANCELLED;
						map.put("SubjectInfo",
								"Buildonn: Knock Knock!! Order ID: " + orderId
										+ " – Cancelled from " + customerName
										+ " zipcode : " + customerZipcode);
					}
					map.put("FullName", customerName);
					map.put("OrderId", orderId);
					map.put("Type", "TRANSACTIONAL");
					map.put("ItemCount", String.valueOf(itemCount));
					map.put("ItemName", itemName);
					map.put("ItemQuantity", String.valueOf(ItemQuantity));
					map.put("OrderAmount", orderAmount);
					map.put("Zipcode", String.valueOf(customerZipcode));

					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						Set<Address> addresses = user.getAddresses();
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}
								}
							}
						}
					}

					try {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										itemInfoForCommunicationList);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}

					} catch (JMSException e) {
						logger.error("JMS Exception occured while sending Order Cancelled mail/sms for user");

						throw new UserCommException(
								"JMS Exception occured while sending Order Cancelled mail/sms for user",
								e);
					}
				}
			}
		}
	}

	@Override
	public void sendCancelOrderInfoToTeleAssociate(List<User> teleUsers,
			String orderId, Integer itemCount, String itemName,
			Integer ItemQuantity, String orderAmount, String customerName,
			Integer customerZipcode,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException {
		logger.info("Requesting for send Order Cancelled Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		for (User user : teleUsers) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (role.getName().equalsIgnoreCase("MBGTELEASSOCIATE")) {
					if (itemCount == 1) {
						notificationName = NotificationConstants.TELEASSOCIATE_SINGLEITEM_CANCELLED;
						map.put("SubjectInfo",
								"Buildonn: Knock Knock!! Order ID: " + orderId
										+ " – Cancelled from " + customerName
										+ " zipcode : " + customerZipcode);

					} else if (itemCount > 1) {

						notificationName = NotificationConstants.TELEASSOCIATE_MULTIITEM_CANCELLED;
						map.put("SubjectInfo",
								"Buildonn: Knock Knock!! Order ID: " + orderId
										+ " – Cancelled from " + customerName
										+ " zipcode : " + customerZipcode);
					}

					map.put("FullName", customerName);
					map.put("OrderId", orderId);
					map.put("Type", "TRANSACTIONAL");
					map.put("ItemCount", String.valueOf(itemCount));
					map.put("ItemName", itemName);
					map.put("ItemQuantity", String.valueOf(ItemQuantity));
					map.put("OrderAmount", orderAmount);
					map.put("Zipcode", String.valueOf(customerZipcode));

					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						Set<Address> addresses = user.getAddresses();
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}

								}
							}
						}
					}
					try {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										itemInfoForCommunicationList);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}

					} catch (JMSException e) {
						logger.error("JMS Exception occured while sending Order Cancelled mail/sms for user");

						throw new UserCommException(
								"JMS Exception occured while sending Order Cancelled mail/sms for user",
								e);
					}
				}
			}
		}

	}

	@Override
	public void sendCancelOrderInfoToTechAssociate(List<User> techUsers,
			String orderId, Integer itemCount, String itemName,
			Integer ItemQuantity, String orderAmount, String customerName,
			Integer customerZipcode,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException {
		logger.info("Requesting for send Order Cancelled Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		for (User user : techUsers) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (role.getName().equalsIgnoreCase("MBGTECHASSOCIATE")) {
					if (itemCount == 1) {
						notificationName = NotificationConstants.TECHASSOCIATE_SINGLEITEM_CANCELLED;
						map.put("SubjectInfo",
								"Buildonn: Knock Knock!! Order ID: " + orderId
										+ " – Cancelled from " + customerName
										+ " zipcode : " + customerZipcode);

					} else if (itemCount > 1) {
						notificationName = NotificationConstants.TECHASSOCIATE_MULTIITEM_CANCELLED;
						map.put("SubjectInfo",
								"Buildonn: Knock Knock!! Order ID: " + orderId
										+ " – Cancelled from " + customerName
										+ " zipcode : " + customerZipcode);
					}

					map.put("FullName", customerName);
					map.put("OrderId", orderId);
					map.put("Type", "TRANSACTIONAL");
					map.put("ItemCount", String.valueOf(itemCount));
					map.put("ItemName", itemName);
					map.put("ItemQuantity", String.valueOf(ItemQuantity));
					map.put("OrderAmount", orderAmount);
					map.put("Zipcode", String.valueOf(customerZipcode));

					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						Set<Address> addresses = user.getAddresses();
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}
								}
							}
						}
					}

					try {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										itemInfoForCommunicationList);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}

					} catch (JMSException e) {
						logger.error("JMS Exception occured while sending Order Cancelled mail/sms for user");

						throw new UserCommException(
								"JMS Exception occured while sending Order Cancelled mail/sms for user",
								e);
					}
				}
			}
		}

	}

	@Override
	public void sendCancelOrderInfoToDealers(Set<User> users, String orderId,
			Integer itemCount, String itemName, Integer ItemQuantity,
			String orderAmount, String customerName, Integer customerZipcode,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException {
		logger.info("Requesting for send Order Cancelled Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;

		Map<String, String> map = new HashMap<>();
		for (User user : users) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (role.getName().equalsIgnoreCase("DEALER")) {
					if (itemCount == 1) {
						notificationName = NotificationConstants.DEALER_SINGLEITEM_CANCELLED;
						map.put("SubjectInfo",
								"Buildonn: Knock Knock!! Order ID:" + orderId
										+ " – Cancelled from " + customerName
										+ " zipcode : " + customerZipcode);
					} else if (itemCount > 1) {
						notificationName = NotificationConstants.DEALER_MULTIITEM_CANCELLED;
						map.put("SubjectInfo",
								"Buildonn: Knock Knock!! Order ID:" + orderId
										+ " – Cancelled from " + customerName
										+ " zipcode : " + customerZipcode);
					}
					map.put("FullName", customerName);
					map.put("OrderId", orderId);
					map.put("Type", "TRANSACTIONAL");
					map.put("ItemCount", String.valueOf(itemCount));
					map.put("ItemName", itemName);
					map.put("ItemQuantity", String.valueOf(ItemQuantity));
					map.put("OrderAmount", orderAmount);
					map.put("Zipcode", String.valueOf(customerZipcode));
					Set<Address> addresses = user.getAddresses();

					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}

								}
							}
						}
					}

					try {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										itemInfoForCommunicationList);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}

					} catch (JMSException e) {
						logger.error("JMS Exception occured while sending Order Cancelled mail/sms for user");

						throw new UserCommException(
								"JMS Exception occured while sending Order Cancelled mail/sms for user",
								e);
					}
				}
			}
		}
	}

	@Override
	public void sendItemCancelledInfo(Set<User> users, String orderId,
			String itemName, Integer ItemQuantity, String orderAmount,
			String customerName, Integer customerZipcode,
			ItemInfoForCommunication itemInfoForCommunication)
			throws UserCommException {
		logger.info("Requesting for send Item Cancelled Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		for (User user : users) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {

				if (role.getName().equalsIgnoreCase("SUPERADMIN")) {
					notificationName = NotificationConstants.ADMIN_SINGLEITEM_CANCELLED;
					map.put("SubjectInfo", "Buildonn: Knock Knock!! Order ID: "
							+ orderId + " – Cancelled from " + customerName
							+ "zipcode " + customerZipcode);
				} else if (role.getName().equalsIgnoreCase("DEALER")) {
					notificationName = NotificationConstants.DEALER_SINGLEITEM_CANCELLED;
					map.put("SubjectInfo", "Buildonn: Knock Knock!! Order ID: "
							+ orderId + " – Cancelled from " + customerName
							+ "zipcode " + customerZipcode);
				} else if (role.getName().equalsIgnoreCase("MBGTELEASSOCIATE")) {
					notificationName = NotificationConstants.TELEASSOCIATE_SINGLEITEM_CANCELLED;
					map.put("SubjectInfo",
							"Buildonn: Cancellation of your order " + orderId
									+ " with buildonn.com");
				} else if (role.getName().equalsIgnoreCase("MBGTECHASSOCIATE")) {
					notificationName = NotificationConstants.TECHASSOCIATE_SINGLEITEM_CANCELLED;
					map.put("SubjectInfo",
							"Buildonn: Cancellation of your order " + orderId
									+ " with buildonn.com");
				} else {
					notificationName = NotificationConstants.ENDUSER_SINGLEITEM_CANCELLED;
					map.put("SubjectInfo",
							"Buildonn: Cancellation of your order " + orderId
									+ " with buildonn.com");
				}

				map.put("FullName", customerName);
				map.put("OrderId", orderId);
				map.put("Type", "TRANSACTIONAL");
				map.put("ItemName", itemName);
				map.put("ItemQuantity", String.valueOf(ItemQuantity));
				map.put("OrderAmount", orderAmount);
				map.put("Zipcode", String.valueOf(customerZipcode));

				if (user.getUsername() != null) {
					email = user.getUsername();
					map.put("Email", email);
				}

				if (user.getUserPhoneNumber() != null) {
					phno = user.getUserPhoneNumber();
					map.put("PhoneNumber", phno);
				} else {
					Set<Address> addresses = user.getAddresses();
					for (Address addrs : addresses) {
						Set<Phone> phs = addrs.getPhoneNumbers();
						if (phs != null) {
							for (Phone ph : phs) {
								if (ph.isPrimary()) {
									phno = ph.getNumber();
									map.put("PhoneNumber", phno);
								}
							}
						}
					}
				}
				List<ItemInfoForCommunication> itemInfoForCommunicationList = new ArrayList<>();
				itemInfoForCommunicationList.add(itemInfoForCommunication);
				try {
					if (StringUtils.isNotBlank(user.getId())
							&& StringUtils.isNotBlank(notificationName)) {
						if (getEmailNotifMap().containsKey(notificationName)
								&& getEmailNotifMap().get(notificationName)
								&& email != null) {
							sendMail(notificationName, map,
									itemInfoForCommunicationList);
						}
						if (getSmsNotifiMap().containsKey(notificationName)
								&& getSmsNotifiMap().get(notificationName)
								&& phno != null) {
							sendSMS(notificationName, map);
						}
					}

				} catch (JMSException e) {

					logger.error("JMS Exception occured while sending Item Cancelled mail/sms for user");

					throw new UserCommException(
							"JMS Exception occured while sending Item Cancelled mail/sms for user",
							e);
				}
			}
		}
	}

	@Override
	public void sendItemCancelledInfo(Set<User> users, String orderId,
			String itemName, Integer ItemQuantity, String orderAmount,
			String customerName, Integer customerZipcode,
			List<ItemInfoForCommunication> itemInfoForCommunicationList,
			int itemCount) throws UserCommException {
		logger.info("Requesting for send Item Cancelled Email and SMS");

		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		List<ItemInfoForCommunication> dealerItemInfoForCommunicationList = null;

		for (User user : users) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {

				if (role.getName().equalsIgnoreCase("SUPERADMIN")
						&& itemCount == 1) {
					notificationName = NotificationConstants.ADMIN_SINGLEITEM_CANCELLED;
					map.put("SubjectInfo", "Buildonn: Knock Knock!! Order ID: "
							+ orderId + " – Cancelled from " + customerName
							+ "zipcode " + customerZipcode);
				} else if (role.getName().equalsIgnoreCase("SUPERADMIN")
						&& itemCount > 1) {
					notificationName = NotificationConstants.ADMIN_MULTIITEM_CANCELLED;
					map.put("SubjectInfo", "Buildonn: Knock Knock!! Order ID: "
							+ orderId + " – Cancelled from " + customerName
							+ "zipcode " + customerZipcode);
				} else if (role.getName().equalsIgnoreCase("DEALER")
						&& itemCount == 1) {
					notificationName = NotificationConstants.DEALER_SINGLEITEM_CANCELLED;
					map.put("SubjectInfo", "Buildonn: Knock Knock!! Order ID: "
							+ orderId + " – Cancelled from " + customerName
							+ "zipcode " + customerZipcode);
				} else if (role.getName().equalsIgnoreCase("DEALER")
						&& itemCount > 1) {
					notificationName = NotificationConstants.DEALER_MULTIITEM_CANCELLED;
					map.put("SubjectInfo", "Buildonn: Knock Knock!! Order ID: "
							+ orderId + " – Cancelled from " + customerName
							+ "zipcode " + customerZipcode);
				} else if (role.getName().equalsIgnoreCase("MBGTELEASSOCIATE")
						&& itemCount == 1) {
					notificationName = NotificationConstants.TELEASSOCIATE_SINGLEITEM_CANCELLED;
					map.put("SubjectInfo",
							"Buildonn: Cancellation of your order " + orderId
									+ " with buildonn.com");
				} else if (role.getName().equalsIgnoreCase("MBGTELEASSOCIATE")
						&& itemCount > 1) {
					notificationName = NotificationConstants.TELEASSOCIATE_MULTIITEM_CANCELLED;
					map.put("SubjectInfo",
							"Buildonn: Cancellation of your order " + orderId
									+ " with buildonn.com");
				} else if (role.getName().equalsIgnoreCase("MBGTECHASSOCIATE")
						&& itemCount == 1) {
					notificationName = NotificationConstants.TECHASSOCIATE_SINGLEITEM_CANCELLED;
					map.put("SubjectInfo",
							"Buildonn: Cancellation of your order " + orderId
									+ " with buildonn.com");
				} else if (role.getName().equalsIgnoreCase("MBGTECHASSOCIATE")
						&& itemCount > 1) {
					notificationName = NotificationConstants.TECHASSOCIATE_MULTIITEM_CANCELLED;
					map.put("SubjectInfo",
							"Buildonn: Cancellation of your order " + orderId
									+ " with buildonn.com");
				}

				else if (itemCount == 1) {
					notificationName = NotificationConstants.ENDUSER_SINGLEITEM_CANCELLED;
					map.put("SubjectInfo",
							"Buildonn: Cancellation of your order " + orderId
									+ " with buildonn.com");
				} else if (itemCount > 1) {
					notificationName = NotificationConstants.ENDUSER_MULTIITEM_CANCELLED;
					map.put("SubjectInfo",
							"Buildonn: Cancellation of your order " + orderId
									+ " with buildonn.com");
				}

				map.put("FullName", customerName);
				map.put("OrderId", orderId);
				map.put("Type", "TRANSACTIONAL");
				map.put("ItemName", itemName);
				map.put("ItemQuantity", String.valueOf(ItemQuantity));
				map.put("OrderAmount", orderAmount);
				map.put("Zipcode", String.valueOf(customerZipcode));
				map.put("ItemCount", String.valueOf(itemCount));

				if (user.getUsername() != null) {
					email = user.getUsername();
					map.put("Email", email);
				}

				if (user.getUserPhoneNumber() != null) {
					phno = user.getUserPhoneNumber();
					map.put("PhoneNumber", phno);
				} else {
					Set<Address> addresses = user.getAddresses();
					for (Address addrs : addresses) {
						Set<Phone> phs = addrs.getPhoneNumbers();
						if (phs != null) {
							for (Phone ph : phs) {
								if (ph.isPrimary()) {
									phno = ph.getNumber();
									map.put("PhoneNumber", phno);
								}
							}
						}
					}
				}
				try {
					if (role.getName().equalsIgnoreCase("DEALER")) {
						dealerItemInfoForCommunicationList = new ArrayList<>();
						for (ItemInfoForCommunication ItemInfoForCommunication : itemInfoForCommunicationList) {
							if (user.getId().equalsIgnoreCase(
									ItemInfoForCommunication.getDealerId())) {
								dealerItemInfoForCommunicationList
										.add(ItemInfoForCommunication);
							}
						}
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										dealerItemInfoForCommunicationList);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}
					} else {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										itemInfoForCommunicationList);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}
					}

				} catch (JMSException e) {

					logger.error("JMS Exception occured while sending Item Cancelled mail/sms for user");

					throw new UserCommException(
							"JMS Exception occured while sending Item Cancelled mail/sms for user",
							e);
				}
			}
		}
	}

	@Override
	public void sendItemConfirmedInfo(Set<User> users, String orderId,
			String itemName, Integer itemQuantity, String orderAmount,
			Integer minDeliveryTimeInDays, Integer maxDeliveryTimeInDays,
			String customerName, Integer customerZipcode, String deliveryBy,
			List<ItemInfoForCommunication> itemInfoForCommunicationList,
			int itemCount, String address) throws UserCommException {
		logger.info("Requesting for send Item Confirmed Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		List<ItemInfoForCommunication> dealerItemInfoForCommunicationList = null;
		Map<String, String> map = new HashMap<>();
		for (User user : users) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (!(role.getName().equalsIgnoreCase("SUPERADMIN")
						|| role.getName().equalsIgnoreCase("MBGTECHASSOCIATE") || role
						.getName().equalsIgnoreCase("MBGTELEASSOCIATE"))) {

					if (role.getName().equalsIgnoreCase("DEALER")
							&& itemCount == 1) {
						notificationName = NotificationConstants.DEALER_SINGLEITEM_CONFIRMED;
						map.put("SubjectInfo", "Buildonn: Order ID: " + orderId
								+ " : " + itemName
								+ " confirmed by you to be delivered");
					} else if (role.getName().equalsIgnoreCase("DEALER")
							&& itemCount > 1) {
						notificationName = NotificationConstants.DEALER_MULTIITEM_CONFIRMED;
						map.put("SubjectInfo", "Buildonn: Order ID: " + orderId
								+ " : " + itemName
								+ " confirmed by you to be delivered");
					} else if (itemCount == 1) {
						notificationName = NotificationConstants.ENDUSER_SINGLEITEM_CONFIRMED;
						map.put("SubjectInfo",
								"Buildonn: Your item is confirmed to be delivered to you");
					} else if (itemCount > 1) {
						notificationName = NotificationConstants.ENDUSER_MULTIITEM_CONFIRMED;
						map.put("SubjectInfo",
								"Buildonn: Your item is confirmed to be delivered to you");
					}

					map.put("FullName", customerName);
					map.put("OrderId", orderId);
					map.put("Type", "TRANSACTIONAL");
					map.put("ItemName", itemName);
					map.put("ItemQuantity", String.valueOf(itemQuantity));
					map.put("OrderAmount", orderAmount);
					map.put("MinDeliveryTimeInDays",
							String.valueOf(minDeliveryTimeInDays));
					map.put("MaxDeliveryTimeInDays",
							String.valueOf(maxDeliveryTimeInDays));
					map.put("Zipcode", String.valueOf(customerZipcode));
					map.put("DeliverBy", deliveryBy);
					map.put("InstantOrderTextInfo", address);
					map.put("ItemCount", String.valueOf(itemCount));

					Set<Address> addresses = user.getAddresses();

					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}
								}
							}
						}
					}

					try {
						if (role.getName().equalsIgnoreCase("DEALER")) {
							dealerItemInfoForCommunicationList = new ArrayList<>();
							for (ItemInfoForCommunication ItemInfoForCommunication : itemInfoForCommunicationList) {
								if (user.getId().equalsIgnoreCase(
										ItemInfoForCommunication.getDealerId())) {
									dealerItemInfoForCommunicationList
											.add(ItemInfoForCommunication);
								}
							}
							if (StringUtils.isNotBlank(user.getId())
									&& StringUtils.isNotBlank(notificationName)) {
								if (getEmailNotifMap().containsKey(
										notificationName)
										&& getEmailNotifMap().get(
												notificationName)
										&& email != null) {
									sendMail(notificationName, map,
											dealerItemInfoForCommunicationList);
								}
								if (getSmsNotifiMap().containsKey(
										notificationName)
										&& getSmsNotifiMap().get(
												notificationName)
										&& phno != null) {
									sendSMS(notificationName, map);
								}
							}
						} else {
							if (StringUtils.isNotBlank(user.getId())
									&& StringUtils.isNotBlank(notificationName)) {
								if (getEmailNotifMap().containsKey(
										notificationName)
										&& getEmailNotifMap().get(
												notificationName)
										&& email != null) {
									sendMail(notificationName, map,
											itemInfoForCommunicationList);
								}
								if (getSmsNotifiMap().containsKey(
										notificationName)
										&& getSmsNotifiMap().get(
												notificationName)
										&& phno != null) {
									sendSMS(notificationName, map);
								}
							}
						}
					} catch (JMSException e) {
						logger.error("JMS Exception occured while sending Item Confirmed mail/sms for user");

						throw new UserCommException(
								"JMS Exception occured while sending Item Confirmed mail/sms for user",
								e);
					}
				}
			}
		}
	}

	public void sendItemUnConfirmedInfo(Set<User> users, String orderId,
			String itemName, Integer itemQuantity, String orderAmount,
			String deliveryBy,
			List<ItemInfoForCommunication> itemInfoForCommunicationList,
			int itemCount) throws UserCommException {
		logger.info("Requesting for send Item UnConfirmed Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		for (User user : users) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (!(role.getName().equalsIgnoreCase("SUPERADMIN")
						|| role.getName().equalsIgnoreCase("DEALER")
						|| role.getName().equalsIgnoreCase("MBGTECHASSOCIATE") || role
						.getName().equalsIgnoreCase("MBGTELEASSOCIATE"))) {
					if (itemCount == 1) {
						notificationName = NotificationConstants.ENDUSER_SINGLEITEM_UNCONFIRMED;
						map.put("SubjectInfo",
								"Buildonn: Your item is unconfirmed to be delivered to you");
					} else if (itemCount > 1) {
						notificationName = NotificationConstants.ENDUSER_MULTIITEM_UNCONFIRMED;
						map.put("SubjectInfo",
								"Buildonn: Your item is unconfirmed to be delivered to you");
					}

					if (user.getFirstName() != null) {
						map.put("FirstName", user.getFirstName());
					}
					if (user.getLastName() != null) {
						map.put("LastName", user.getLastName());
					}
					if (user.getFullName() != null) {
						map.put("FullName", user.getFullName());
					}
					map.put("OrderId", orderId);
					map.put("Type", "TRANSACTIONAL");
					map.put("ItemName", itemName);
					map.put("ItemQuantity", String.valueOf(itemQuantity));
					map.put("DeliverBy", deliveryBy);
					map.put("OrderAmount", orderAmount);
					map.put("ItemCount", String.valueOf(itemCount));

					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						Set<Address> addresses = user.getAddresses();
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}
								}
							}
						}
					}
					try {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										itemInfoForCommunicationList);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}

					} catch (JMSException e) {
						logger.error("JMS Exception occured while sending Item UnConfirmed mail/sms for user");

						throw new UserCommException(
								"JMS Exception occured while sending Item UnConfirmed mail/sms for user",
								e);
					}
				}
			}
		}
	}

	@Override
	public void sendItemDispatchedInfo(Set<User> users, String orderId,
			String itemName, Integer itemQuantity, String dispatchId,
			String orderAmount, String deliveryBy,
			List<ItemInfoForCommunication> itemInfoForCommunicationList,
			int itemCount) throws UserCommException {
		logger.info("Requesting for send Item Dispatched Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		for (User user : users) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (!(role.getName().equalsIgnoreCase("SUPERADMIN")
						|| role.getName().equalsIgnoreCase("DEALER")
						|| role.getName().equalsIgnoreCase("MBGTECHASSOCIATE") || role
						.getName().equalsIgnoreCase("MBGTELEASSOCIATE"))) {
					if (itemCount == 1) {
						notificationName = NotificationConstants.ENDUSER_SINGLEITEM_DISPATCHED;

						map.put("SubjectInfo",
								"Buildonn: item dispatched for order "
										+ orderId);
					} else if (itemCount > 1) {

						notificationName = NotificationConstants.ENDUSER_MULTIITEM_DISPATCHED;

						map.put("SubjectInfo",
								"Buildonn: item dispatched for order "
										+ orderId);
					}

					if (user.getFirstName() != null) {
						map.put("FirstName", user.getFirstName());
					}
					if (user.getLastName() != null) {
						map.put("LastName", user.getLastName());
					}
					if (user.getFullName() != null) {
						map.put("FullName", user.getFullName());
					}
					map.put("OrderId", orderId);
					map.put("Type", "TRANSACTIONAL");
					map.put("ItemName", itemName);
					map.put("ItemQuantity", String.valueOf(itemQuantity));
					map.put("DispatchId", dispatchId);
					map.put("OrderAmount", orderAmount);
					map.put("DeliverBy", deliveryBy);
					map.put("ItemCount", String.valueOf(itemCount));
					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						Set<Address> addresses = user.getAddresses();
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}

								}
							}
						}
					}

					try {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										itemInfoForCommunicationList);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}

					} catch (JMSException e) {
						logger.error("JMS Exception occured while sending Item Dispatched mail/sms for user");

						throw new UserCommException(
								"JMS Exception occured while sending Item Dispatched mail/sms for user",
								e);
					}
				}
			}
		}
	}

	@Override
	public void sendItemDeliveredInfo(Set<User> users, String orderId,
			String itemName, Integer itemQuantity, String deliveryBy,
			List<ItemInfoForCommunication> itemInfoForCommunicationList,
			int itemCount) throws UserCommException {
		logger.info("Requesting for send Item Delivered Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		for (User user : users) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (!(role.getName().equalsIgnoreCase("SUPERADMIN")
						|| role.getName().equalsIgnoreCase("DEALER")
						|| role.getName().equalsIgnoreCase("MBGTECHASSOCIATE") || role
						.getName().equalsIgnoreCase("MBGTELEASSOCIATE"))) {
					if (itemCount == 1) {
						notificationName = NotificationConstants.ENDUSER_SINGLEITEM_DELIVERED;

						map.put("SubjectInfo",
								"Buildonn: Delivery confirmation of order "
										+ orderId);
					} else if (itemCount > 1) {

						notificationName = NotificationConstants.ENDUSER_MULTIITEM_DELIVERED;

						map.put("SubjectInfo",
								"Buildonn: Delivery confirmation of order "
										+ orderId);
					}

					if (user.getFirstName() != null) {
						map.put("FirstName", user.getFirstName());
					}
					if (user.getLastName() != null) {
						map.put("LastName", user.getLastName());
					}
					if (user.getFullName() != null) {
						map.put("FullName", user.getFullName());
					}
					map.put("OrderId", orderId);
					map.put("Type", "TRANSACTIONAL");
					map.put("ItemName", itemName);
					map.put("ItemQuantity", String.valueOf(itemQuantity));
					map.put("DeliverBy", deliveryBy);
					map.put("ItemCount", String.valueOf(itemCount));

					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						Set<Address> addresses = user.getAddresses();
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}

								}
							}
						}
					}

					try {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										itemInfoForCommunicationList);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}

					} catch (JMSException e) {
						logger.error("JMS Exception occured while sending Item Delivered mail/sms for user");
						throw new UserCommException(
								"JMS Exception occured while sending Item Delivered mail/sms for user",
								e);
					}
				}
			}
		}
	}

	@Override
	public void sendItemReturnedInfo(Set<User> users, String orderId,
			String itemName, Integer itemQuantity, String customerName,
			Integer customerZipcode, String orderAmount,
			List<ItemInfoForCommunication> itemInfoForCommunicationList,
			int itemCount) throws UserCommException {
		logger.info("Requesting for send Item Returned Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		for (User user : users) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {

				if (role.getName().equalsIgnoreCase("SUPERADMIN")
						&& itemCount == 1) {
					notificationName = NotificationConstants.ADMIN_SINGLEITEM_RETURNED;
					map.put("SubjectInfo", "Buildonn: Knock Knock!! Order ID: "
							+ orderId + " Returned from " + customerName
							+ " zipcode : " + customerZipcode);
				} else if (role.getName().equalsIgnoreCase("SUPERADMIN")
						&& itemCount > 1) {
					notificationName = NotificationConstants.ADMIN_MULTIITEM_RETURNED;
					map.put("SubjectInfo", "Buildonn: Knock Knock!! Order ID: "
							+ orderId + " Returned from " + customerName
							+ " zipcode : " + customerZipcode);
				} else if (role.getName().equalsIgnoreCase("DEALER")
						&& itemCount == 1) {
					notificationName = NotificationConstants.DEALER_SINGLEITEM_RETURNED;
					map.put("SubjectInfo", "Buildonn: Knock Knock!! Order ID: "
							+ orderId + " Returned from " + customerName
							+ " zipcode : " + customerZipcode);
				} else if (role.getName().equalsIgnoreCase("DEALER")
						&& itemCount > 1) {
					notificationName = NotificationConstants.DEALER_MULTIITEM_RETURNED;
					map.put("SubjectInfo", "Buildonn: Knock Knock!! Order ID: "
							+ orderId + " Returned from " + customerName
							+ " zipcode : " + customerZipcode);
				} else if (role.getName().equalsIgnoreCase("MBGTELEASSOCIATE")
						&& itemCount == 1) {
					notificationName = NotificationConstants.TELEASSOCIATE_SINGLEITEM_RETURNED;
					map.put("SubjectInfo", "Buildonn: return of your order "
							+ orderId + " with buildonn.com");
				} else if (role.getName().equalsIgnoreCase("MBGTELEASSOCIATE")
						&& itemCount > 1) {
					notificationName = NotificationConstants.TELEASSOCIATE_MULTIITEM_RETURNED;
					map.put("SubjectInfo", "Buildonn: return of your order "
							+ orderId + " with buildonn.com");
				} else if (role.getName().equalsIgnoreCase("MBGTECHASSOCIATE")
						&& itemCount == 1) {
					notificationName = NotificationConstants.TECHASSOCIATE_SINGLEITEM_RETURNED;
					map.put("SubjectInfo", "Buildonn: return of your order "
							+ orderId + " with buildonn.com");
				} else if (role.getName().equalsIgnoreCase("MBGTECHASSOCIATE")
						&& itemCount > 1) {
					notificationName = NotificationConstants.TECHASSOCIATE_MULTIITEM_RETURNED;
					map.put("SubjectInfo", "Buildonn: return of your order "
							+ orderId + " with buildonn.com");
				} else if (itemCount == 1) {
					notificationName = NotificationConstants.ENDUSER_SINGLEITEM_RETURNED;

					map.put("SubjectInfo", "Buildonn: Order  " + orderId
							+ " Returned by you");
				} else if (itemCount > 1) {
					notificationName = NotificationConstants.ENDUSER_MULTIITEM_RETURNED;

					map.put("SubjectInfo", "Buildonn: Order  " + orderId
							+ " Returned by you");
				}

				map.put("FullName", customerName);
				map.put("OrderId", orderId);
				map.put("Type", "TRANSACTIONAL");
				map.put("ItemName", itemName);
				map.put("ItemQuantity", String.valueOf(itemQuantity));
				map.put("Zipcode", String.valueOf(customerZipcode));
				map.put("OrderAmount", orderAmount);
				map.put("ItemCount", String.valueOf(itemCount));

				if (user.getUsername() != null) {
					email = user.getUsername();
					map.put("Email", email);
				}

				if (user.getUserPhoneNumber() != null) {
					phno = user.getUserPhoneNumber();
					map.put("PhoneNumber", phno);
				} else {
					Set<Address> addresses = user.getAddresses();
					for (Address addrs : addresses) {
						Set<Phone> phs = addrs.getPhoneNumbers();
						if (phs != null) {
							for (Phone ph : phs) {
								if (ph.isPrimary()) {
									phno = ph.getNumber();
									map.put("PhoneNumber", phno);
								}

							}
						}
					}
				}

				try {
					if (StringUtils.isNotBlank(user.getId())
							&& StringUtils.isNotBlank(notificationName)) {
						if (getEmailNotifMap().containsKey(notificationName)
								&& getEmailNotifMap().get(notificationName)
								&& email != null) {
							sendMail(notificationName, map,
									itemInfoForCommunicationList);
						}
						if (getSmsNotifiMap().containsKey(notificationName)
								&& getSmsNotifiMap().get(notificationName)
								&& phno != null) {
							sendSMS(notificationName, map);
						}
					}

				} catch (JMSException e) {
					logger.error("JMS Exception occured while sending Item Returned mail/sms for user");

					throw new UserCommException(
							"JMS Exception occured while sending Item Returned mail/sms for user",
							e);
				}
			}
		}
	}

	@Override
	public void sendBidDoneInfo(User user, String architectOrInteriorName,
			String architectOrInteriorRole, Integer architectOrInteriorZipcode,
			String architectOrInteriorContactNo) throws UserCommException {
		logger.info("Requesting for send Bid done Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		Set<Role> userRole = user.getRoles();
		for (Role role : userRole) {
			if (role.getName().equalsIgnoreCase("ENDUSER")) {
				notificationName = NotificationConstants.ENDUSER_BIDDONEINFO;
				map.put("SubjectInfo",
						"Buildonn: Bid response for your project from "
								+ architectOrInteriorRole);
				map.put("FullName", architectOrInteriorName);
				map.put("Type", "TRANSACTIONAL");
				map.put("Zipcode", String.valueOf(architectOrInteriorZipcode));
				map.put("RoleName", architectOrInteriorName);
				map.put("ContactNumber", architectOrInteriorContactNo);

				if (user.getUsername() != null) {
					email = user.getUsername();
					map.put("Email", email);
				}

				if (user.getUserPhoneNumber() != null) {
					phno = user.getUserPhoneNumber();
					map.put("PhoneNumber", phno);
				} else {
					Set<Address> addresses = user.getAddresses();
					for (Address addrs : addresses) {
						Set<Phone> phs = addrs.getPhoneNumbers();
						if (phs != null) {
							for (Phone ph : phs) {
								if (ph.isPrimary()) {
									phno = ph.getNumber();
									map.put("PhoneNumber", phno);
								}
							}
						}
					}
				}
				try {
					if (StringUtils.isNotBlank(user.getId())
							&& StringUtils.isNotBlank(notificationName)) {
						if (getEmailNotifMap().containsKey(notificationName)
								&& getEmailNotifMap().get(notificationName)
								&& email != null) {
							sendMail(notificationName, map);
						}

						if (getSmsNotifiMap().containsKey(notificationName)
								&& getSmsNotifiMap().get(notificationName)
								&& phno != null) {
							sendSMS(notificationName, map);
						}
					}
				} catch (JMSException e) {

					logger.error("JMS Exception occured while sending Bid done mail/sms for user");

					throw new UserCommException(
							"JMS Exception occured while sending Bid done mail/sms for user",
							e);
				}
			}
		}
	}

	@Override
	public void sendProductApprovedInfo(User user, String productName)
			throws UserCommException {
		logger.info("Requesting for send ProductApprovedInfo Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		if (user != null) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (role.getName().equalsIgnoreCase("PROVIDER")
						|| role.getName().equalsIgnoreCase("DEALER")) {

					if (role.getName().equalsIgnoreCase("PROVIDER")) {
						notificationName = NotificationConstants.PROVIDER_PRODUCTAPPROVED;

						map.put("SubjectInfo",
								"Buildonn: Approved Your product "
										+ productName);
					} else if (role.getName().equalsIgnoreCase("DEALER")) {
						notificationName = NotificationConstants.DEALER_PRODUCTAPPROVED;

						map.put("SubjectInfo",
								"Buildonn: Approved Your product "
										+ productName);
					}

					map.put("FullName", user.getFullName());
					map.put("ItemName", productName);
					map.put("Type", "TRANSACTIONAL");

					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						Set<Address> addresses = user.getAddresses();
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}
								}
							}
						}
					}
					try {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}

					} catch (JMSException e) {
						logger.error("JMS Exception occured while sending ProductApprovedInfo mail/sms for user");

						throw new UserCommException(
								"JMS Exception occured while sending ProductApprovedInfo mail/sms for user",
								e);
					}
				}
			}
		}
	}

	@Override
	public void sendBankDetailsForFundTransfer(Set<User> users, String orderId,
			Integer itemCount, String itemName, String itemQuantity,
			String itemQuantityType, String orderAmount, String customerName,
			String customerZipcode, String totalSavings, String taxAmt,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException {
		logger.info("Requesting for BankDetailsForFundTransfer Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		for (User user : users) {

			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (!(role.getName().equalsIgnoreCase("SUPERADMIN")
						|| role.getName().equalsIgnoreCase("DEALER")
						|| role.getName().equalsIgnoreCase("MBGTECHASSOCIATE") || role
						.getName().equalsIgnoreCase("MBGTELEASSOCIATE"))) {

					if (itemCount == 1) {
						notificationName = NotificationConstants.ENDUSER_PLACEDSINGLEITEMORDERTHROUGHNEFT;

						map.put("SubjectInfo",
								"Buildonn: Payment request for amount "
										+ orderAmount + "Order Id: " + orderId);
					} else if (itemCount > 1) {
						notificationName = NotificationConstants.ENDUSER_PLACEDMULTIITEMORDERTHROUGHNEFT;

						map.put("SubjectInfo",
								"Buildonn: Payment request for amount "
										+ orderAmount + "Order Id: " + orderId);
					}
					map.put("UserId", user.getId());
					map.put("FullName", customerName);
					map.put("OrderId", orderId);
					map.put("Type", "TRANSACTIONAL");
					map.put("ItemCount", String.valueOf(itemCount));
					map.put("ItemName", itemName);
					map.put("ItemQuantity", itemQuantity);
					map.put("OrderAmount", orderAmount);
					map.put("Zipcode", customerZipcode);
					map.put("TotalSavings", totalSavings);
					map.put("InstantOrderTextInfo", itemQuantityType);
					map.put("TaxAmt", taxAmt);

					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						Set<Address> addresses = user.getAddresses();
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}

								}
							}
						}
					}

					try {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										itemInfoForCommunicationList);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}

					} catch (JMSException e) {
						logger.error("JMS Exception occured while sending BankDetailsForFundTransfer mail/sms for user");

						throw new UserCommException(
								"JMS Exception occured while sending BankDetailsForFundTransfer mail/sms for user",
								e);
					}
				}
			}
		}
	}

	@Override
	public void sendItemPackedInfo(User user, String orderId, String itemName,
			Integer itemQuantity, String orderAmount, String deliveryBy,
			List<ItemInfoForCommunication> itemInfoForCommunicationList,
			int itemCount) throws UserCommException {
		logger.info("Requesting to send ItemPackedInfo Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		if (user != null) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (!(role.getName().equalsIgnoreCase("SUPERADMIN")
						|| role.getName().equalsIgnoreCase("DEALER")
						|| role.getName().equalsIgnoreCase("MBGTECHASSOCIATE") || role
						.getName().equalsIgnoreCase("MBGTELEASSOCIATE"))) {

					if (itemCount == 1) {
						notificationName = NotificationConstants.ENDUSER_SINGLEITEM_PACKED;

						map.put("SubjectInfo", "Buildonn: item of order "
								+ orderId + "  is packed for dispatch"
								+ orderId);
					} else if (itemCount > 1) {

						notificationName = NotificationConstants.ENDUSER_MULTIITEM_PACKED;

						map.put("SubjectInfo", "Buildonn: item of order "
								+ orderId + "  is packed for dispatch"
								+ orderId);
					}
					if (user.getFirstName() != null) {
						map.put("FirstName", user.getFirstName());
					}
					if (user.getLastName() != null) {
						map.put("LastName", user.getLastName());
					}
					if (user.getFullName() != null) {
						map.put("FullName", user.getFullName());
					}
					map.put("OrderId", orderId);
					map.put("Type", "TRANSACTIONAL");
					map.put("ItemName", itemName);
					map.put("ItemQuantity", String.valueOf(itemQuantity));
					map.put("DeliverBy", deliveryBy);
					map.put("OrderAmount", orderAmount);
					map.put("ItemCount", String.valueOf(itemCount));

					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						Set<Address> addresses = user.getAddresses();
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}

								}
							}
						}
					}
					try {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										itemInfoForCommunicationList);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}

					} catch (JMSException e) {
						logger.error("JMS Exception occured while sending ItemPackedInfo mail/sms for user");

						throw new UserCommException(
								"JMS Exception occured while sending ItemPackedInfo mail/sms for user",
								e);
					}
				}
			}
		}

	}

	@Override
	public void sendProjectPostedInfo(Set<User> userSet, String customerName,
			Integer customerZipcode, String projectType, String projectBudget,
			String constructionType) throws UserCommException {
		logger.info("Requesting to send ProjectPostedInfo Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		for (User user : userSet) {
			if (user.getStatus().equals(UserStatus.ACTIVE)) {
				Set<Role> userRole = user.getRoles();
				for (Role role : userRole) {
					if (role.getName().equalsIgnoreCase("ARCHITECT")
							|| role.getName().equalsIgnoreCase(
									"INTERIORDESIGNER")) {
						if (role.getName().equalsIgnoreCase("ARCHITECT")) {
							notificationName = NotificationConstants.ARCHITECT_POSTEDPROJECTINFO;
							map.put("SubjectInfo",
									"Buildonn: Bid for your new project");
						} else if (role.getName().equalsIgnoreCase(
								"INTERIORDESIGNER")) {
							notificationName = NotificationConstants.INTERIORDESIGNER_POSTEDPROJECTINFO;
							map.put("SubjectInfo",
									"Buildonn: Bid for your new project");
						}

						map.put("FullName", customerName);
						map.put("Zipcode", String.valueOf(customerZipcode));
						map.put("ProjectType", projectType);
						map.put("ProjectBudget", projectBudget);
						map.put("Type", "TRANSACTIONAL");
						map.put("InstantOrderTextInfo", constructionType);

						if (user.getUsername() != null) {
							email = user.getUsername();
							map.put("Email", email);
						}

						if (user.getUserPhoneNumber() != null) {
							phno = user.getUserPhoneNumber();
							map.put("PhoneNumber", phno);
						} else {
							Set<Address> addresses = user.getAddresses();
							for (Address addrs : addresses) {
								Set<Phone> phs = addrs.getPhoneNumbers();
								if (phs != null) {
									for (Phone ph : phs) {
										if (ph.isPrimary()) {
											phno = ph.getNumber();
											map.put("PhoneNumber", phno);
										}

									}
								}
							}
						}
						try {
							if (StringUtils.isNotBlank(user.getId())
									&& StringUtils.isNotBlank(notificationName)) {
								if (getEmailNotifMap().containsKey(
										notificationName)
										&& getEmailNotifMap().get(
												notificationName)
										&& email != null) {
									sendMail(notificationName, map);
								}
								if (getSmsNotifiMap().containsKey(
										notificationName)
										&& getSmsNotifiMap().get(
												notificationName)
										&& phno != null) {
									sendSMS(notificationName, map);
								}
							}

						} catch (JMSException e) {
							logger.error("JMS Exception occured while sending ProjectPostedInfo mail/sms for user");

							throw new UserCommException(
									"JMS Exception occured while sending ProjectPostedInfo mail/sms for user",
									e);
						}
					}
				}
			}
		}
	}

	@Override
	public void sendInstantOrderInfo(User user, String mobile, String emailId,
			String customerName, String textInfo, String deliveryTime,
			String imageUrl) throws UserCommException {
		logger.info("Requesting for send Instant Order Placed Email");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		if (user != null) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (role.getName().equalsIgnoreCase("SUPERADMIN")) {

					notificationName = NotificationConstants.INSTANTORDERINFO;
					map.put("SubjectInfo", "You have a snapshot Requirement1!");

				}

				map.put("Type", "TRANSACTIONAL");
				map.put("InstantOrderMobile", mobile);
				map.put("InstantOrderEmail", emailId);
				map.put("InstantOrderCustomerName", customerName);
				map.put("InstantOrderTextInfo", textInfo);
				map.put("InstantOrderDeliveryTime", deliveryTime);
				map.put("InstantOrderImageUrl", imageUrl);

				if (user.getUsername() != null) {
					email = user.getUsername();
					map.put("Email", email);
				}

				if (user.getUserPhoneNumber() != null) {
					phno = user.getUserPhoneNumber();
					map.put("PhoneNumber", phno);
				} else {
					Set<Address> addresses = user.getAddresses();
					for (Address addrs : addresses) {
						Set<Phone> phs = addrs.getPhoneNumbers();
						if (phs != null) {
							for (Phone ph : phs) {
								if (ph.isPrimary()) {
									phno = ph.getNumber();
									map.put("PhoneNumber", phno);
								}
							}
						}
					}
				}
				try {
					if (StringUtils.isNotBlank(user.getId())
							&& StringUtils.isNotBlank(notificationName)
							&& email != null) {
						if (getEmailNotifMap().containsKey(notificationName)
								&& getEmailNotifMap().get(notificationName)) {
							sendMail(notificationName, map);
						}
					}

				} catch (JMSException e) {

					logger.error("JMS Exception occured while sending InstantOrder mail");

					throw new UserCommException(
							"JMS Exception occured while sending InstantOrder mail",
							e);
				}
			}
		}
	}

	@Override
	public void sendProductEnquiryInfo(User user, String customerName,
			String mobile, String emailId, String textInfo, String deliveryTime)
			throws UserCommException {
		logger.info("Requesting for send Instant Order Placed Email");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		if (user != null) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (role.getName().equalsIgnoreCase("SUPERADMIN")) {

					notificationName = NotificationConstants.INSTANTORDERINFO;
					map.put("SubjectInfo", "You have a Product Enquiry form!");

				}

				map.put("Type", "TRANSACTIONAL");
				map.put("InstantOrderMobile", mobile);
				map.put("InstantOrderEmail", emailId);
				map.put("InstantOrderCustomerName", customerName);
				map.put("InstantOrderTextInfo", textInfo);
				map.put("InstantOrderDeliveryTime", deliveryTime);

				if (user.getUsername() != null) {
					email = user.getUsername();
					map.put("Email", email);
				}

				if (user.getUserPhoneNumber() != null) {
					phno = user.getUserPhoneNumber();
					map.put("PhoneNumber", phno);
				} else {
					Set<Address> addresses = user.getAddresses();
					for (Address addrs : addresses) {
						Set<Phone> phs = addrs.getPhoneNumbers();
						if (phs != null) {
							for (Phone ph : phs) {
								if (ph.isPrimary()) {
									phno = ph.getNumber();
									map.put("PhoneNumber", phno);
								}

							}
						}
					}
				}
				try {
					if (StringUtils.isNotBlank(user.getId())
							&& StringUtils.isNotBlank(notificationName)
							&& email != null) {
						if (getEmailNotifMap().containsKey(notificationName)
								&& getEmailNotifMap().get(notificationName)) {
							sendMail(notificationName, map);
						}
					}

				} catch (JMSException e) {

					logger.error("JMS Exception occured while sending ProductEnquiry Form mail");

					throw new UserCommException(
							"JMS Exception occured while sending ProductEnquiry Form mail",
							e);
				}
			}
		}
	}

	@Override
	public void sendRequestQuoteInfo(User user, String customerName,
			String mobile, String emailId, String zipcode, String paymentMode,
			String textInfo, String creditDays, String deliveryTime,
			List<ItemInfoForCommunication> itemInfoList, String brand,
			String changeAttribute) throws UserCommException {
		logger.info("Requesting for send Request quote Order Email");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		if (user != null) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (role.getName().equalsIgnoreCase("SUPERADMIN")) {

					notificationName = NotificationConstants.REQUESTQUOTEINFO;
					map.put("SubjectInfo",
							"You have a RequestQuote Requirement1!");

				}

				map.put("Type", "TRANSACTIONAL");
				map.put("InstantOrderCustomerName", customerName);
				map.put("InstantOrderMobile", mobile);
				map.put("InstantOrderEmail", emailId);
				map.put("Zipcode", zipcode);
				map.put("PaymentMode", paymentMode);

				map.put("InstantOrderTextInfo", textInfo);
				map.put("CreditDays", creditDays);
				map.put("InstantOrderDeliveryTime", deliveryTime);
				map.put("RoleName", brand);
				map.put("Token", changeAttribute);

				if (user.getUsername() != null) {
					email = user.getUsername();
					map.put("Email", "sj.sarvahventures@gmail.com");
				}

				if (user.getUserPhoneNumber() != null) {
					phno = user.getUserPhoneNumber();
					map.put("PhoneNumber", phno);
				} else {
					Set<Address> addresses = user.getAddresses();
					for (Address addrs : addresses) {
						Set<Phone> phs = addrs.getPhoneNumbers();
						if (phs != null) {
							for (Phone ph : phs) {
								if (ph.isPrimary()) {
									phno = ph.getNumber();
									map.put("PhoneNumber", phno);
								}

							}
						}
					}
				}
				try {
					if (StringUtils.isNotBlank(user.getId())
							&& StringUtils.isNotBlank(notificationName)
							&& email != null) {
						if (getEmailNotifMap().containsKey(notificationName)
								&& getEmailNotifMap().get(notificationName)) {
							sendMail(notificationName, map, itemInfoList);
						}
					}

				} catch (JMSException e) {

					logger.error("JMS Exception occured while sending InstantOrder mail");

					throw new UserCommException(
							"JMS Exception occured while sending InstantOrder mail",
							e);
				}
			}
		}
	}

	@Override
	public void sendOrderInvoiceForDealer(User dealer, String orderId,
			String url) throws UserCommException {
		logger.info("Requesting for send order copy Email");
		String notificationName = null;
		String email = null;
		Map<String, String> map = new HashMap<>();
		Set<Role> userRole = dealer.getRoles();
		for (Role role : userRole) {
			if (role.getName().equalsIgnoreCase("DEALER")) {
				notificationName = NotificationConstants.DEALER_INVOICE;
				map.put("SubjectInfo", "Buildonn DEALER: New Order copy order "
						+ orderId);

				map.put("OrderId", orderId);
				map.put("FullName", dealer.getFullName());
				map.put("Type", "TRANSACTIONAL");
				map.put("InvoiceUrlForDealer", url);

				if (dealer.getUsername() != null) {
					email = dealer.getUsername();
					map.put("Email", email);
				}

				try {
					if (StringUtils.isNotBlank(dealer.getId())
							&& StringUtils.isNotBlank(notificationName)) {
						if (getEmailNotifMap().containsKey(notificationName)
								&& getEmailNotifMap().get(notificationName)
								&& email != null) {
							sendMail(notificationName, map);
						}
					}
				} catch (JMSException e) {

					logger.error("JMS Exception occured while sending order copy mail for dealer");

					throw new UserCommException(
							"JMS Exception occured while sending order copy mail for dealer",
							e);
				}
			}

		}
	}

	@Override
	public void sendOrderInvoiceForEnduser(User user, String orderId, String url)
			throws UserCommException {
		logger.info("Requesting for send order copy Email");
		String notificationName = null;
		Map<String, String> map = new HashMap<>();
		String email = null;
		notificationName = NotificationConstants.ENDUSER_INVOICE;

		map.put("SubjectInfo", "Buildonn Customer: Your Order copy order "
				+ orderId);
		map.put("OrderId", orderId);
		map.put("FullName", user.getFullName());
		map.put("Type", "TRANSACTIONAL");
		map.put("InvoiceUrlForEnduser", url);

		if (user.getUsername() != null) {
			email = user.getUsername();
			map.put("Email", email);
		}

		try {
			if (StringUtils.isNotBlank(user.getId())
					&& StringUtils.isNotBlank(notificationName)) {
				if (getEmailNotifMap().containsKey(notificationName)
						&& getEmailNotifMap().get(notificationName)
						&& email != null) {
					sendMail(notificationName, map);
				}
			}
		} catch (JMSException e) {

			logger.error("JMS Exception occured while sending order copy mail for dealer");

			throw new UserCommException(
					"JMS Exception occured while sending order copy mail for dealer",
					e);
		}
	}

	@Override
	public void sendResendVerificationTokenInfo(User user, String token)
			throws UserCommException {
		// Not needed for now
	}

	@Override
	public void sendForgotPasswordInfo(User mongoUser, String base64EncodedToken) {
		// Not needed for now

	}

	@Override
	public void sendItemPackedInforSelfPickup(User user, String orderId,
			String itemName, Integer itemQuantity, String orderAmount,
			String deliveryBy, String storeName, String dealerAddress,
			String dealerContectNumber,
			List<ItemInfoForCommunication> itemInfoForCommunicationList,
			int itemCount) throws UserCommException {
		logger.info("Requesting to send ItemPackedInfo Email and SMS");
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		if (user != null) {
			Set<Role> userRole = user.getRoles();
			for (Role role : userRole) {
				if (!(role.getName().equalsIgnoreCase("SUPERADMIN")
						|| role.getName().equalsIgnoreCase("DEALER")
						|| role.getName().equalsIgnoreCase("MBGTECHASSOCIATE") || role
						.getName().equalsIgnoreCase("MBGTELEASSOCIATE"))) {
					if (itemCount == 1) {
						notificationName = NotificationConstants.ENDUSER_SINGLEITEM_SELFPICKUP_PACKED;

						map.put("SubjectInfo", "Buildonn: item of order "
								+ orderId + "  is packed for dispatch"
								+ orderId);
					} else if (itemCount > 1) {
						notificationName = NotificationConstants.ENDUSER_MULTIITEM_SELFPICKUP_PACKED;

						map.put("SubjectInfo", "Buildonn: item of order "
								+ orderId + "  is packed for dispatch"
								+ orderId);
					}
					map.put("FirstName", storeName);
					if (user.getLastName() != null) {
						map.put("LastName", user.getLastName());
					}
					map.put("FullName", user.getFullName());
					map.put("OrderId", orderId);
					map.put("Type", "TRANSACTIONAL");
					map.put("ItemName", itemName);
					map.put("ItemQuantity", String.valueOf(itemQuantity));
					map.put("DeliverBy", deliveryBy);
					map.put("OrderAmount", orderAmount);
					map.put("ContactNumber", dealerContectNumber);
					map.put("Address", dealerAddress);

					if (user.getUsername() != null) {
						email = user.getUsername();
						map.put("Email", email);
					}

					if (user.getUserPhoneNumber() != null) {
						phno = user.getUserPhoneNumber();
						map.put("PhoneNumber", phno);
					} else {
						Set<Address> addresses = user.getAddresses();
						for (Address addrs : addresses) {
							Set<Phone> phs = addrs.getPhoneNumbers();
							if (phs != null) {
								for (Phone ph : phs) {
									if (ph.isPrimary()) {
										phno = ph.getNumber();
										map.put("PhoneNumber", phno);
									}

								}
							}
						}
					}
					try {
						if (StringUtils.isNotBlank(user.getId())
								&& StringUtils.isNotBlank(notificationName)) {
							if (getEmailNotifMap()
									.containsKey(notificationName)
									&& getEmailNotifMap().get(notificationName)
									&& email != null) {
								sendMail(notificationName, map,
										itemInfoForCommunicationList);
							}
							if (getSmsNotifiMap().containsKey(notificationName)
									&& getSmsNotifiMap().get(notificationName)
									&& phno != null) {
								sendSMS(notificationName, map);
							}
						}

					} catch (JMSException e) {
						logger.error("JMS Exception occured while sending ItemPackedInfo mail/sms for user");

						throw new UserCommException(
								"JMS Exception occured while sending ItemPackedInfo mail/sms for user",
								e);
					}
				}
			}
		}
	}

	@Override
	public void sendContactInfo(String name, String email, String mobile,
			String zipcode, String description, String toEmail,
			String toPhoneNumber, String roleName, String constructionType,
			String asSoonRequired, String city) throws UserCommException {
		Map<String, String> map = new HashMap<>();
		String notificationName = null;
		if (description == null) {
			notificationName = NotificationConstants.ENDUSERDETAILSTOSERVICEPROVIDER;
		} else {
			notificationName = NotificationConstants.ENDUSERDETAILSTOSERVICEPROVIDERWITHDESC;
		}
		map.put("SubjectInfo", "Buildonn: " + name
				+ " from Buildonn.com tried contacting you");
		map.put("RoleName", roleName);
		map.put("FullName", name);
		map.put("InstantOrderEmail", email);
		map.put("InstantOrderMobile", mobile);
		map.put("Zipcode", zipcode);
		map.put("InstantOrderTextInfo", description);
		map.put("Email", toEmail);
		map.put("PhoneNumber", toPhoneNumber);
		map.put("Type", "TRANSACTIONAL");
		map.put("ProjectType", constructionType);
		map.put("DeliverBy", asSoonRequired);
		map.put("DeliverBy", asSoonRequired);
		map.put("MinDeliveryTimeInDays", "2");
		map.put("MaxDeliveryTimeInDays", "5");
		map.put("Address", city);
		try {
			if (StringUtils.isNotBlank(notificationName)) {
				if (getEmailNotifMap().containsKey(notificationName)
						&& getEmailNotifMap().get(notificationName)
						&& StringUtils.isNotBlank(toEmail)) {
					sendMail(notificationName, map);
				}

				if (getSmsNotifiMap().containsKey(notificationName)
						&& getSmsNotifiMap().get(notificationName)
						&& StringUtils.isNotBlank(toPhoneNumber)) {
					sendSMS(notificationName, map);
				}
			}
		} catch (JMSException e) {
			logger.error("JMS Exception occured while sending contactInfo mail/sms for user");

			throw new UserCommException(
					"JMS Exception occured while sending contactInfo mail/sms for user",
					e);
		}
	}

	@Override
	public void sendServiceProviderInfoForEnduser(String spFullName,
			String email, String mobile, String zipcode, String roleName,
			String toEmail, String toMobile) throws UserCommException {
		Map<String, String> map = new HashMap<>();
		String notificationName = NotificationConstants.ENDUSER_SERVICEPROVIDERINFOTOENDUSER;
		map.put("SubjectInfo", "Buildonn: "
				+ " ServiceProvider contact details");
		map.put("RoleName", roleName);
		map.put("FullName", spFullName);
		map.put("InstantOrderEmail", email);
		map.put("InstantOrderMobile", mobile);
		map.put("Zipcode", zipcode);
		map.put("Email", toEmail);
		map.put("PhoneNumber", toMobile);
		map.put("Type", "TRANSACTIONAL");

		try {
			if (StringUtils.isNotBlank(notificationName)) {
				if (getEmailNotifMap().containsKey(notificationName)
						&& getEmailNotifMap().get(notificationName)
						&& StringUtils.isNotBlank(toEmail)) {
					sendMail(notificationName, map);
				}

				if (getSmsNotifiMap().containsKey(notificationName)
						&& getSmsNotifiMap().get(notificationName)
						&& StringUtils.isNotBlank(toMobile)) {
					sendSMS(notificationName, map);
				}
			}
		} catch (JMSException e) {
			logger.error("JMS Exception occured while sending ServiceProvider contact details mail/sms for user");

			throw new UserCommException(
					"JMS Exception occured while sending ServiceProvider contact details mail/sms for user",
					e);
		}
	}

	@Override
	public void sendUserCredentials(String userName, String mobileNumber,
			String password) throws JMSException {
		Map<String, String> map = new HashMap<>();
		String notificationName = NotificationConstants.USER_CREDENTIALS;

		map.put("SubjectInfo", "Buildonn: User Credentials");
		map.put("InstantOrderEmail", userName);
		map.put("InstantOrderMobile", mobileNumber);
		map.put("InstantOrderTextInfo", password);
		map.put("Email", userName);
		map.put("PhoneNumber", mobileNumber);
		map.put("Type", "TRANSACTIONAL");

		if (StringUtils.isNotBlank(notificationName)) {
			if (getEmailNotifMap().containsKey(notificationName)
					&& getEmailNotifMap().get(notificationName)
					&& StringUtils.isNotBlank(userName)) {
				sendMail(notificationName, map);
			}

			if (getSmsNotifiMap().containsKey(notificationName)
					&& getSmsNotifiMap().get(notificationName)
					&& StringUtils.isNotBlank(mobileNumber)) {
				sendSMS(notificationName, map);
			}
		}
	}

	@Override
	public void sendNewRegistrationAlert(User user) throws JMSException {
		Map<String, String> map = new HashMap<>();
		String roleName = null;
		String email = null;
		String email1 = null;
		String mobileNumber = null;

		// Username
		if (user.getUsername() != null) {
			email = user.getUsername();
			email1 = user.getUsername();
		} else {
			email = "Not given";
		}

		// MobileNumber
		if (user.getUserPhoneNumber() != null) {
			mobileNumber = user.getUserPhoneNumber().toString();
		} else {
			mobileNumber = "Not given";
		}

		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			if (role != null) {
				roleName = role.getName();
			}
		}

		String notificationName = NotificationConstants.ADMIN_NEWREGISTRATIONALERT;
		map.put("SubjectInfo", "Buildonn: New Registration Alert");
		map.put("RoleName", roleName);
		map.put("InstantOrderEmail", email);
		map.put("InstantOrderMobile", mobileNumber);
		map.put("Email", "sj.sarvahventures@gmail.com");
		map.put("PhoneNumber", "9972662662,9741773773,9066604081");
		map.put("Type", "TRANSACTIONAL");
		map.put("Date", new Date().toString());

		Set<Address> addresses = user.getAddresses();
		for (Address addrs : addresses) {
			if (addrs != null) {
				if (addrs.getAddressLine1() != null) {
					String addressLine1 = addrs.getAddressLine1();
					String str1 = null;
					char[] charAry = addressLine1.toCharArray();
					int i = 0;
					if (charAry.length < 100) {
						map.put("InstantOrderTextInfo", addressLine1);
					} else if (charAry.length > 100) {
						for (char c : charAry) {
							if (i == 0) {
								str1 = Character.toString(c);
							} else {
								if (i < 100) {
									str1 = str1 + c;
								} else {
									break;
								}
							}
							i++;
						}
						map.put("InstantOrderTextInfo", str1);
					}
				} else {
					map.put("InstantOrderTextInfo", "Not given");
				}
			}
		}

		if (StringUtils.isNotBlank(notificationName)) {
			if (getEmailNotifMap().containsKey(notificationName)
					&& getEmailNotifMap().get(notificationName)
					&& StringUtils.isNotBlank(email1)) {
				sendMail(notificationName, map);
			}

			if (getSmsNotifiMap().containsKey(notificationName)
					&& getSmsNotifiMap().get(notificationName)
					&& StringUtils.isNotBlank(mobileNumber)) {
				sendSMS(notificationName, map);
			}
		}

	}

	@Override
	public void sendPromoSms(String promoInfo, String mobileNumbers)
			throws JMSException {
		Map<String, String> map = new HashMap<>();
		map.put("PhoneNumber", mobileNumbers);
		if (StringUtils.isNotBlank(promoInfo)) {
			map.put("PromoInfo", promoInfo);
		}
		map.put("Type", "PROMOTIONAL");
		sendSMS("", map);
	}

	public void shareQuoteWithCustomer(User customer, String quoteRequestId,
			double orderAmount, List<ItemInfoForCommunication> products)
			throws UserCommException {
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		if (customer != null) {
			if (customer.getUsername() != null) {
				notificationName = NotificationConstants.ENDUSER_SHAREQUOTEWITHCUSTOMER;
				map.put("SubjectInfo", "Buildonn: Best Quote Information");
			} else {
				notificationName = NotificationConstants.ENDUSER_SHAREQUOTESMSWITHCUSTOMER;
			}

			map.put("Type", "TRANSACTIONAL");
			map.put("OrderId", quoteRequestId);
			map.put("OrderAmount", String.valueOf(orderAmount));

			if (customer.getUsername() != null) {
				email = customer.getUsername();
				map.put("Email", email);
				map.put("InstantOrderEmail", email);
			}

			if (customer.getUserPhoneNumber() != null) {
				phno = customer.getUserPhoneNumber();
				map.put("PhoneNumber", phno);
			} else {
				Set<Address> addresses = customer.getAddresses();
				for (Address addrs : addresses) {
					Set<Phone> phs = addrs.getPhoneNumbers();
					if (phs != null) {
						for (Phone ph : phs) {
							if (ph.isPrimary()) {
								phno = ph.getNumber();
								map.put("PhoneNumber", phno);
							}

						}
					}
				}
			}
			try {
				if (StringUtils.isNotBlank(customer.getId())
						&& StringUtils.isNotBlank(notificationName)) {
					if (getEmailNotifMap().containsKey(notificationName)
							&& getEmailNotifMap().get(notificationName)
							&& email != null) {
						sendMail(notificationName, map, products);
					}
					if (getSmsNotifiMap().containsKey(notificationName)
							&& getSmsNotifiMap().get(notificationName)
							&& phno != null) {
						// Once we had proper sms template then will uncomment
						// below link
						// sendSMS(notificationName, map);
					}
				}

			} catch (JMSException e) {
				logger.error("JMS Exception occured while sharing quote with customer");

				throw new UserCommException(
						"JMS Exception occured while sharing quote with customer",
						e);
			}
		}
	}

	@Override
	public void sendProductEnquiryFormConfirmation(String customerName,
			String email, String mobileNumber) throws UserCommException {

		String notificationName = null;

		Map<String, String> map = new HashMap<>();

		notificationName = NotificationConstants.ENDUSER_ENQUIRYFORMCONFIRMATION;

		map.put("SubjectInfo", "Buildonn: Product enquiry confirmation");
		map.put("Type", "TRANSACTIONAL");
		map.put("FullName", customerName);
		map.put("Email", email);
		map.put("PhoneNumber", mobileNumber);

		try {
			if (StringUtils.isNotBlank(notificationName)) {
				if (getEmailNotifMap().containsKey(notificationName)
						&& getEmailNotifMap().get(notificationName)
						&& email != null) {
					sendMail(notificationName, map);
				}

				if (getSmsNotifiMap().containsKey(notificationName)
						&& getSmsNotifiMap().get(notificationName)
						&& mobileNumber != null) {
					sendSMS(notificationName, map);
				}
			}

		} catch (JMSException e) {
			logger.error("JMS Exception occured while sending product enquiry confirmation to customer");

			throw new UserCommException(
					"JMS Exception occured while sending product enquiry confirmation to customer",
					e);
		}
	}

	@Override
	public void sendInstantOrderConfirmtion(String customerName, String email,
			String mobileNumber) throws UserCommException {

		String notificationName = null;

		Map<String, String> map = new HashMap<>();

		notificationName = NotificationConstants.ENDUSER_SNAPSHOTORDERCONFIRMATION;

		map.put("SubjectInfo", "Buildonn: Snapshot order confirmation");
		map.put("Type", "TRANSACTIONAL");
		map.put("FullName", customerName);
		map.put("Email", email);
		map.put("PhoneNumber", mobileNumber);
		try {
			if (StringUtils.isNotBlank(notificationName)) {
				if (getEmailNotifMap().containsKey(notificationName)
						&& getEmailNotifMap().get(notificationName)
						&& email != null) {
					sendMail(notificationName, map);
				}

				// if (getSmsNotifiMap().containsKey(notificationName)
				// && getSmsNotifiMap().get(notificationName)
				// && mobileNumber != null) {
				// sendSMS(notificationName, map);
				// }
			}

		} catch (JMSException e) {
			logger.error("JMS Exception occured while sending snapshot order confirmation to customer");

			throw new UserCommException(
					"JMS Exception occured while sending snapshot order confirmation to customer",
					e);
		}
	}

	@Override
	public void sendQuoteConfirmation(String customerName, String email,
			String mobile, String quoteOrderId) throws UserCommException {
		String notificationName = null;

		Map<String, String> map = new HashMap<>();

		notificationName = NotificationConstants.ENDUSER_QUOTEREQUESTCONFIRMATION;

		map.put("SubjectInfo", "Buildonn: Get Quote confirmation");
		map.put("Type", "TRANSACTIONAL");
		map.put("FullName", customerName);
		map.put("Email", email);
		map.put("PhoneNumber", mobile);
		map.put("OrderId", quoteOrderId);
		try {
			if (StringUtils.isNotBlank(notificationName)) {
				if (getEmailNotifMap().containsKey(notificationName)
						&& getEmailNotifMap().get(notificationName)
						&& email != null) {
					sendMail(notificationName, map);
				}

				if (getSmsNotifiMap().containsKey(notificationName)
						&& getSmsNotifiMap().get(notificationName)
						&& mobile != null) {
					sendSMS(notificationName, map);
				}
			}

		} catch (JMSException e) {
			logger.error("JMS Exception occured while sending quote request confirmation to customer");

			throw new UserCommException(
					"JMS Exception occured while sending quote request confirmation to customer",
					e);
		}
	}

	@Override
	public void sendQuoteRequestConfirmationToCustomer(User customer,
			String quoteRequestId, String zipcode, String deliveryDate,
			List<ItemInfoForCommunication> products) throws UserCommException {
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		if (customer != null) {
			notificationName = NotificationConstants.ENDUSER_QUOTEREQUESTCONFIRMATION;

			map.put("SubjectInfo", "Buildonn: Quote Request confirmation");
			map.put("Type", "TRANSACTIONAL");
			map.put("OrderId", quoteRequestId);
			map.put("Zipcode", zipcode);
			map.put("InstantOrderDeliveryTime", deliveryDate);

			if (customer.getUsername() != null) {
				email = customer.getUsername();
				map.put("Email", email);
			}

			if (customer.getUserPhoneNumber() != null) {
				phno = customer.getUserPhoneNumber();
				map.put("PhoneNumber", phno);
			} else {
				Set<Address> addresses = customer.getAddresses();
				for (Address addrs : addresses) {
					Set<Phone> phs = addrs.getPhoneNumbers();
					if (phs != null) {
						for (Phone ph : phs) {
							if (ph.isPrimary()) {
								phno = ph.getNumber();
								map.put("PhoneNumber", phno);
							}

						}
					}
				}
			}
			try {
				if (StringUtils.isNotBlank(customer.getId())
						&& StringUtils.isNotBlank(notificationName)) {
					if (getEmailNotifMap().containsKey(notificationName)
							&& getEmailNotifMap().get(notificationName)
							&& email != null) {
						// sendMail(notificationName, map, products);
					}
					if (getSmsNotifiMap().containsKey(notificationName)
							&& getSmsNotifiMap().get(notificationName)
							&& phno != null) {
						sendSMS(notificationName, map);
					}
				}

			} catch (JMSException e) {
				logger.error("JMS Exception occured while sharing quote with customer");

				throw new UserCommException(
						"JMS Exception occured while sharing quote with customer",
						e);
			}
		}
	}

	@Override
	public void sendQuoteRequestToDealer(Set<User> dealers,
			String quoteRequestId, String itemNameAndQuantityTypeList,
			int itemCount, String customerZipcode) throws UserCommException {
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		for (User dealer : dealers) {
			if (dealer != null) {
				if (itemCount <= 3) {
					notificationName = NotificationConstants.DEALER_QUOTEREQUESTWITHFEWITEMS;
				} else {
					notificationName = NotificationConstants.DEALER_QUOTEREQUESTWITHMOREITEMS;
				}

				map.put("SubjectInfo", "Buildonn: Quote Request Requirement");
				map.put("Type", "TRANSACTIONAL");
				map.put("OrderId", quoteRequestId);
				map.put("ItemName", itemNameAndQuantityTypeList);
				map.put("ItemCount", String.valueOf(itemCount - 3));
				map.put("Zipcode", customerZipcode);

				if (dealer.getUsername() != null) {
					email = dealer.getUsername();
					map.put("Email", email);
				}

				if (dealer.getUserPhoneNumber() != null) {
					phno = dealer.getUserPhoneNumber();
					map.put("PhoneNumber", phno);
				} else {
					Set<Address> addresses = dealer.getAddresses();
					for (Address addrs : addresses) {
						Set<Phone> phs = addrs.getPhoneNumbers();
						if (phs != null) {
							for (Phone ph : phs) {
								if (ph.isPrimary()) {
									phno = ph.getNumber();
									map.put("PhoneNumber", phno);
								}
							}
						}
					}
				}
				try {
					if (StringUtils.isNotBlank(dealer.getId())
							&& StringUtils.isNotBlank(notificationName)) {
						if (getEmailNotifMap().containsKey(notificationName)
								&& getEmailNotifMap().get(notificationName)
								&& email != null) {
							sendMail(notificationName, map);
						}
						if (getSmsNotifiMap().containsKey(notificationName)
								&& getSmsNotifiMap().get(notificationName)
								&& phno != null) {
							sendSMS(notificationName, map);
						}
					}

				} catch (JMSException e) {
					logger.error("JMS Exception occured while sharing quote with customer");

					throw new UserCommException(
							"JMS Exception occured while sharing quote with customer",
							e);
				}
			}
		}
	}

	@Override
	public void sendQuoteRequestInfoToAdmin(User admin, User customer,
			String quoteRequestId, String zipcode, String deliveryDate,
			List<ItemInfoForCommunication> products) throws UserCommException {
		String notificationName = null;
		String email = null;
		String phno = null;
		String itemCount = null;
		Map<String, String> map = new HashMap<>();
		String itemName = null;
		String quantity = null;
		String quantityType = null;
		if (admin != null) {
			if (products.size() == 1 && deliveryDate != null) {
				notificationName = NotificationConstants.ADMIN_SINGLEITEMQRWITHDELIVERYDATE;
				itemName = products.get(0).getItemName();
				quantity = String.valueOf(products.get(0).getItemQuantity());
				if (products.get(0).getItemId() != null
						&& products.get(0).getItemId() != "") {
					quantityType = products.get(0).getItemQuantityType()
							+ "(s)";
				} else {
					quantityType = products.get(0).getItemQuantityType();
				}
			} else if (products.size() == 1 && deliveryDate == null) {
				notificationName = NotificationConstants.ADMIN_SINGLEITEMQRWITHOUTDELIVERYDATE;
				itemName = products.get(0).getItemName();
				quantity = String.valueOf(products.get(0).getItemQuantity());
				quantityType = products.get(0).getItemQuantityType() + "(s)";
			} else if (products.size() > 1 && deliveryDate != null) {
				notificationName = NotificationConstants.ADMIN_MULTIITEMQRWITHDELIVERYDATE;
			} else if (products.size() > 1 && deliveryDate == null) {
				notificationName = NotificationConstants.ADMIN_MULTIITEMQRWITHOUTDELIVERYDATE;
			}

			long days = 0;
			if (StringUtils.isNotBlank(deliveryDate)
					&& !StringUtils.equalsIgnoreCase(deliveryDate, "Not Given")
					&& !deliveryDate.contains("days")) {
				Date d1 = new Date();
				String strDate1 = d1.toString();
				String[] date1 = strDate1.split(" ");

				Integer m1 = null;

				if (date1[1].equalsIgnoreCase("Jan")) {
					m1 = 01;
				} else if (date1[1].equalsIgnoreCase("Feb")) {
					m1 = 02;
				} else if (date1[1].equalsIgnoreCase("Mar")) {
					m1 = 03;
				} else if (date1[1].equalsIgnoreCase("Apr")) {
					m1 = 04;
				} else if (date1[1].equalsIgnoreCase("May")) {
					m1 = 05;
				} else if (date1[1].equalsIgnoreCase("Jun")) {
					m1 = 06;
				} else if (date1[1].equalsIgnoreCase("Jul")) {
					m1 = 07;
				} else if (date1[1].equalsIgnoreCase("Aug")) {
					m1 = 8;
				} else if (date1[1].equalsIgnoreCase("Sep")) {
					m1 = 9;
				} else if (date1[1].equalsIgnoreCase("Oct")) {
					m1 = 10;
				} else if (date1[1].equalsIgnoreCase("Nov")) {
					m1 = 11;
				} else if (date1[1].equalsIgnoreCase("Dec")) {
					m1 = 12;
				}

				Calendar cal = Calendar.getInstance();

				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date1[2]));
				cal.set(Calendar.MONTH, m1);
				cal.set(Calendar.YEAR, Integer.parseInt(date1[5]));
				Date firstDate = cal.getTime();

				String[] date2 = deliveryDate.split("/");
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date2[0]));
				cal.set(Calendar.MONTH, Integer.parseInt(date2[1]));
				cal.set(Calendar.YEAR, Integer.parseInt(date2[2]));
				Date secondDate = cal.getTime();

				long diff = secondDate.getTime() - firstDate.getTime();

				days = diff / 1000 / 60 / 60 / 24;

			}

			if (deliveryDate.contains("days")) {
				String[] strAry = deliveryDate.split(" ");
				String[] strAry1 = strAry[0].split("-");
				days = Long.valueOf(strAry1[1]);
			}

			if (days == 0) {
				days = 1;
			}
			String fullName = null;
			if (customer.getFirstName() != null
					&& customer.getLastName() != null
					&& customer.getUserPhoneNumber() != null) {
				fullName = customer.getFirstName() + " "
						+ customer.getLastName() + "("
						+ customer.getUserPhoneNumber() + ")";
			} else if (customer.getFirstName() != null
					&& customer.getUserPhoneNumber() != null) {
				fullName = customer.getFirstName() + "("
						+ customer.getUserPhoneNumber() + ")";
			} else if (customer.getFirstName() != null) {
				fullName = customer.getFirstName();
			}

			itemCount = String.valueOf(products.size());

			map.put("SubjectInfo", "Buildonn: Quote Request Requirement");
			map.put("Type", "TRANSACTIONAL");
			map.put("FullName", fullName);
			map.put("OrderId", quoteRequestId);
			map.put("Zipcode", zipcode);
			map.put("ItemCount", itemCount);
			map.put("MaxDeliveryTimeInDays", String.valueOf(days));
			map.put("ItemName", itemName);
			map.put("ItemQuantity", quantity);
			map.put("InstantOrderTextInfo", quantityType);

			if (admin.getUsername() != null) {
				email = admin.getUsername();
				map.put("Email", email);
			}

			if (admin.getUserPhoneNumber() != null) {
				phno = admin.getUserPhoneNumber();
				map.put("PhoneNumber", phno);
			} else {
				Set<Address> addresses = admin.getAddresses();
				for (Address addrs : addresses) {
					Set<Phone> phs = addrs.getPhoneNumbers();
					if (phs != null) {
						for (Phone ph : phs) {
							if (ph.isPrimary()) {
								phno = ph.getNumber();
								map.put("PhoneNumber", phno);
							}

						}
					}
				}
			}
			try {
				if (StringUtils.isNotBlank(notificationName)) {
					if (getEmailNotifMap().containsKey(notificationName)
							&& getEmailNotifMap().get(notificationName)
							&& email != null) {
						sendMail(notificationName, map);
					}
					if (getSmsNotifiMap().containsKey(notificationName)
							&& getSmsNotifiMap().get(notificationName)
							&& phno != null) {
						sendSMS(notificationName, map);
					}
				}

			} catch (JMSException e) {
				logger.error("JMS Exception occured while sending QuoteRequest order info");

				throw new UserCommException(
						"JMS Exception occured while sending QuoteRequest order info",
						e);
			}
		}
	}

	@Override
	public void sendOrderAmountUpdateInfoToCustomer(User customer,
			String updatingAmt, double outStandingAmt) throws UserCommException {
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		if (customer != null) {
			notificationName = NotificationConstants.ENDUSER_ORDERAMOUNTUPDATE;
			map.put("SubjectInfo", "Buildonn: Order amount update info");

			Date date = new Date();
			map.put("Type", "TRANSACTIONAL");
			map.put("InstantOrderTextInfo", updatingAmt);
			map.put("OrderAmount", String.valueOf(outStandingAmt));
			map.put("Date", String.valueOf(date));
			if (customer.getUsername() != null) {
				email = customer.getUsername();
				map.put("Email", email);
			}

			if (customer.getUserPhoneNumber() != null) {
				phno = customer.getUserPhoneNumber();
				map.put("PhoneNumber", phno);
			} else {
				Set<Address> addresses = customer.getAddresses();
				for (Address addrs : addresses) {
					Set<Phone> phs = addrs.getPhoneNumbers();
					if (phs != null) {
						for (Phone ph : phs) {
							if (ph.isPrimary()) {
								phno = ph.getNumber();
								map.put("PhoneNumber", phno);
							}
						}
					}
				}
			}

			try {
				if (StringUtils.isNotBlank(notificationName)) {
					if (getEmailNotifMap().containsKey(notificationName)
							&& getEmailNotifMap().get(notificationName)
							&& email != null) {
						sendMail(notificationName, map);
					}
					if (getSmsNotifiMap().containsKey(notificationName)
							&& getSmsNotifiMap().get(notificationName)
							&& phno != null) {
						sendSMS(notificationName, map);
					}
				}

			} catch (JMSException e) {
				logger.error("JMS Exception occured while sending order amount update info to customer");

				throw new UserCommException(
						"JMS Exception occured while sending order amount update info to customer",
						e);
			}
		}
	}

	@Override
	public void sendOrderAmountUpdateWithZeroOutstanding(User customer,
			String updatingAmt, double outStandingAmt) throws UserCommException {
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		if (customer != null) {
			notificationName = NotificationConstants.ENDUSER_ORDERAMOUNTUPDATEWITHZEROOUTSTADNING;
			map.put("SubjectInfo", "Buildonn: Order amount update info");

			Date date = new Date();
			map.put("Type", "TRANSACTIONAL");
			map.put("InstantOrderTextInfo", updatingAmt);
			map.put("OrderAmount", String.valueOf(outStandingAmt));
			map.put("Date", String.valueOf(date));
			if (customer.getUsername() != null) {
				email = customer.getUsername();
				map.put("Email", email);
			}

			if (customer.getUserPhoneNumber() != null) {
				phno = customer.getUserPhoneNumber();
				map.put("PhoneNumber", phno);
			} else {
				Set<Address> addresses = customer.getAddresses();
				for (Address addrs : addresses) {
					Set<Phone> phs = addrs.getPhoneNumbers();
					if (phs != null) {
						for (Phone ph : phs) {
							if (ph.isPrimary()) {
								phno = ph.getNumber();
								map.put("PhoneNumber", phno);
							}
						}
					}
				}
			}

			try {
				if (StringUtils.isNotBlank(notificationName)) {
					// if (getEmailNotifMap().containsKey(notificationName)
					// && getEmailNotifMap().get(notificationName)
					// && email != null) {
					// sendMail(notificationName, map);
					// }
					if (getSmsNotifiMap().containsKey(notificationName)
							&& getSmsNotifiMap().get(notificationName)
							&& phno != null) {
						sendSMS(notificationName, map);
					}
				}

			} catch (JMSException e) {
				logger.error("JMS Exception occured while sending order amount update info to customer");

				throw new UserCommException(
						"JMS Exception occured while sending order amount update info to customer",
						e);
			}
		}
	}

	@Override
	public void sendUserAmountUpdateInfo(User user, String updatingAmt,
			double availableAmt) throws UserCommException {
		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		if (user != null) {
			notificationName = NotificationConstants.USER_AMOUNTUPDATE;
			map.put("SubjectInfo",
					"Buildonn: Your Balance is successfully updated ");
			Date date = new Date();
			map.put("Type", "TRANSACTIONAL");
			map.put("InstantOrderTextInfo", updatingAmt);
			map.put("OrderAmount", String.valueOf(availableAmt));
			map.put("Date", String.valueOf(date));
		}

		if (user.getUsername() != null) {
			email = user.getUsername();
			map.put("Email", email);
		}

		if (user.getUserPhoneNumber() != null) {
			phno = user.getUserPhoneNumber();
			map.put("PhoneNumber", phno);
		} else {
			Set<Address> addresses = user.getAddresses();
			for (Address addrs : addresses) {
				Set<Phone> phs = addrs.getPhoneNumbers();
				if (phs != null) {
					for (Phone ph : phs) {
						if (ph.isPrimary()) {
							phno = ph.getNumber();
							map.put("PhoneNumber", phno);
						}
					}
				}
			}
		}

		try {
			if (StringUtils.isNotBlank(notificationName)) {
				if (getEmailNotifMap().containsKey(notificationName)
						&& getEmailNotifMap().get(notificationName)
						&& email != null) {
					sendMail(notificationName, map);
				}
				if (getSmsNotifiMap().containsKey(notificationName)
						&& getSmsNotifiMap().get(notificationName)
						&& phno != null) {
					sendSMS(notificationName, map);
				}
			}

		} catch (JMSException e) {
			logger.error("JMS Exception occured while sending order amount update info to customer");

			throw new UserCommException(
					"JMS Exception occured while sending order amount update info to customer",
					e);
		}
	}

	@Override
	public void sendQuoteUpdateSellerToAdmin(User dealer, String sellerName,
			int itemCount, double totalQuoteAmount) throws UserCommException {
		String notificationName = NotificationConstants.ADMIN_QUOTEUPDATESELLERTOADMIN;
		String phno = "9916406611";
		Map<String, String> map = new HashMap<>();
		map.put("FullName", sellerName);
		map.put("PhoneNumber", phno);
		map.put("Type", "TRANSACTIONAL");
		map.put("ItemCount", String.valueOf(itemCount));
		map.put("OrderAmount", String.valueOf(totalQuoteAmount));
		try {
			if (StringUtils.isNotBlank(notificationName)) {

				if (getSmsNotifiMap().containsKey(notificationName)
						&& getSmsNotifiMap().get(notificationName)
						&& phno != null) {
					sendSMS(notificationName, map);
				}
			}

		} catch (JMSException e) {
			logger.error("JMS Exception occured while sending quote updated info to admin");

			throw new UserCommException(
					"JMS Exception occured while sending quote updated info to admin",
					e);
		}
	}

	@Override
	public void sendChequeCollectedInfoToCustomer(User customer,
			String bankName, String chequeNumber, String chequeDate,
			String chequeAmount) throws UserCommException {

		String notificationName = null;
		String email = null;
		String phno = null;
		Map<String, String> map = new HashMap<>();
		if (customer != null) {
			notificationName = NotificationConstants.ENDUSER_CHEQUECOLLECTEDDATEINFO;
			map.put("SubjectInfo",
					"Buildonn: Cheque is successfully collected ");

			map.put("Type", "TRANSACTIONAL");
			map.put("InstantOrderTextInfo", bankName);
			map.put("OrderAmount", chequeAmount);
			map.put("Date", chequeDate);
			map.put("OTP", chequeNumber);
			if (customer.getUsername() != null) {
				email = customer.getUsername();
				map.put("Email", email);
			}

			if (customer.getUserPhoneNumber() != null) {
				phno = customer.getUserPhoneNumber();
				map.put("PhoneNumber", phno);
			} else {
				Set<Address> addresses = customer.getAddresses();
				for (Address addrs : addresses) {
					Set<Phone> phs = addrs.getPhoneNumbers();
					if (phs != null) {
						for (Phone ph : phs) {
							if (ph.isPrimary()) {
								phno = ph.getNumber();
								map.put("PhoneNumber", phno);
							}
						}
					}
				}
			}

			try {
				if (StringUtils.isNotBlank(notificationName)) {
					// if (getEmailNotifMap().containsKey(notificationName)
					// && getEmailNotifMap().get(notificationName)
					// && email != null) {
					// sendMail(notificationName, map);
					// }
					if (getSmsNotifiMap().containsKey(notificationName)
							&& getSmsNotifiMap().get(notificationName)
							&& phno != null) {
						sendSMS(notificationName, map);
					}
				}

			} catch (JMSException e) {
				logger.error("JMS Exception occured while sending cheque collected confirmation to customer");

				throw new UserCommException(
						"JMS Exception occured while sending cheque collected confirmation to customer",
						e);
			}
		}

	}

	@Override
	public void sendRequestForCallBack(String name, String mobileNum,
			String requirement) throws UserCommException {
		Map<String, String> map = new HashMap<>();
		map.put("FullName", name);
		map.put("InstantOrderMobile", mobileNum);
		map.put("InstantOrderTextInfo", requirement);
		map.put("Email", "sj.sarvahventures@gmail.com");
		map.put("PhoneNumber", "9916406611");
		map.put("Type", "TRANSACTIONAL");
		map.put("SubjectInfo", "Buildonn: Request For CallBack");
		String notificationName = NotificationConstants.ADMIN_REQUESTFORCALLBACK;
		try {
			if (StringUtils.isNotBlank(notificationName)) {
				if (getEmailNotifMap().containsKey(notificationName)
						&& getEmailNotifMap().get(notificationName)) {
					sendMail(notificationName, map);
				}
				if (getSmsNotifiMap().containsKey(notificationName)
						&& getSmsNotifiMap().get(notificationName)) {
					sendSMS(notificationName, map);
				}
			}
		} catch (JMSException e) {
			logger.error("JMS Exception occured while sending RequestForCallBack eMail/sms to Admin");

			throw new UserCommException(
					"JMS Exception occured while sending RequestForCallBack eMail/sms to Admin",
					e);
		}
	}
}
