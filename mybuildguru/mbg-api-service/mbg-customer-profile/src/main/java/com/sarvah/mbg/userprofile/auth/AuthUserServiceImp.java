/**
 * 
 */
package com.sarvah.mbg.userprofile.auth;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.geo.Point;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sarvah.mbg.commons.address.AddressLookupService;
import com.sarvah.mbg.commons.communication.UserCommException;
import com.sarvah.mbg.commons.communication.UserCommunicationService;
import com.sarvah.mbg.commons.security.oath.otp.TOTP;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.common.contact.Phone;
import com.sarvah.mbg.domain.mongo.common.contact.PhoneType;
import com.sarvah.mbg.domain.mongo.store.Shipping;
import com.sarvah.mbg.domain.mongo.store.Store;
import com.sarvah.mbg.domain.mongo.store.StoreProductPricing;
import com.sarvah.mbg.domain.mongo.userprofile.ServiceProviders;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.UserStatus;
import com.sarvah.mbg.domain.mongo.userprofile.UserType;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.domain.mongo.userprofile.role.UserPackage;
import com.sarvah.mbg.domain.user.AuthRoles;
import com.sarvah.mbg.domain.user.UserInfo;
import com.sarvah.mbg.domain.user.audit.UserAudit;
import com.sarvah.mbg.domain.user.audit.UserAuditType;
import com.sarvah.mbg.domain.user.auth.AuthorizationToken;
import com.sarvah.mbg.domain.user.auth.VerificationToken;
import com.sarvah.mbg.domain.user.auth.VerificationTokenType;
import com.sarvah.mbg.store.dao.mongo.StoreDAO;
import com.sarvah.mbg.store.dao.mongo.StoreProductDAO;
import com.sarvah.mbg.userprofile.auth.exception.AuthenticationException;
import com.sarvah.mbg.userprofile.auth.exception.AuthorizationException;
import com.sarvah.mbg.userprofile.auth.exception.TokenAlreadyVerifiedException;
import com.sarvah.mbg.userprofile.auth.exception.TokenExpiredException;
import com.sarvah.mbg.userprofile.auth.exception.TokenNotFoundException;
import com.sarvah.mbg.userprofile.auth.exception.UserCreationException;
import com.sarvah.mbg.userprofile.auth.model.ApiUser;
import com.sarvah.mbg.userprofile.auth.model.AuthorizationRequest;
import com.sarvah.mbg.userprofile.auth.model.CreateUserBean;
import com.sarvah.mbg.userprofile.auth.model.RegisterUserBean;
import com.sarvah.mbg.userprofile.dao.jpa.UserAuditRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserAuditTypeRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserRepo;
import com.sarvah.mbg.userprofile.dao.jpa.UserRepository;
import com.sarvah.mbg.userprofile.dao.jpa.VerificationTokenRepository;
import com.sarvah.mbg.userprofile.dao.mongo.RoleDAO;
import com.sarvah.mbg.userprofile.dao.mongo.StoreProductPricingDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;
import com.sarvah.mbg.userprofile.dao.role.mongo.UserPackageDAO;

// TODO: Auto-generated Javadoc
/**
 * IMP: DO NOT TOUCH THIS CLASS WITHOUT CONSULTING NAVEEN @9845936902 OR
 * naveen.hn.kumar@gmail.com
 *
 * @author naveen
 */
@Service
public class AuthUserServiceImp implements AuthUserService, UserDetailsService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(AuthUserServiceImp.class);

	private static final String PHONE_PATTERN = "^\\+{0,1}[0-9]{0,2}\\-{0,1}[0-9]{10}$";

	/** The user dao. */
	@Autowired
	private UserDAO userDAO; // mongo user repo

	/** The role dao. */
	@Autowired
	private RoleDAO roleDAO; // mongo role repo

	/** The user repository. */
	@Autowired
	private UserRepository userRepository; // jpa user repo

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserAuditRepository userAuditRepository;

	@Autowired
	private UserAuditTypeRepository userAuditTypeRepository;

	/** The verification token repository. */
	@Autowired
	private VerificationTokenRepository verificationTokenRepository; // jpa
																		// verifcation
																		// token
																		// repo

	/** The password encoder. */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/** The user communication service. */
	@Autowired
	private UserCommunicationService userCommunicationService;

	/** The address lookup service. */
	@Autowired
	private AddressLookupService addressLookupService;

	/** The user package DAO. */
	@Autowired
	private UserPackageDAO userPackageDAO;

	/** The connection factory locator. */
	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;

	/** The jpa users connection repository. */
	@Autowired
	@Qualifier("jpaUserConnectionRepository")
	private UsersConnectionRepository jpaUsersConnectionRepository;

	@Autowired
	private StoreDAO storeDAO;

	@Autowired
	private StoreProductDAO productDAO;

	@Autowired
	private StoreProductPricingDAO storeProductPricingDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.auth.AuthUserService#registerNewUser(com.sarvah
	 * .mbg.userprofile.auth.model.RegisterUserBean)
	 */
	@Override
	@Transactional
	public ApiUser registerNewUser(RegisterUserBean registerUserBean)
			throws UserCreationException {

		if (registerUserBean != null
				&& (isNotBlank(registerUserBean.getUsername()) || isNotBlank(registerUserBean
						.getContactNumber()))) {
			String username = registerUserBean.getUsername();
			String password = registerUserBean.getPassword();
			String contactNumber = registerUserBean.getContactNumber();

			UserInfo userInfo = null;
			User user = null;

			if (isNotBlank(contactNumber) && isNotBlank(username)) {

				boolean validPhoneNumber = validatePhoneNumber(contactNumber);

				boolean validUserName = EmailValidator.getInstance().isValid(
						username);

				if (!validPhoneNumber || !validUserName) {
					logger.error("Unable to create new user, phonenumber/email is not a valid format");
					throw new UserCreationException(
							"User cannot be created with invalid phone number or email id");
				}

				Long phnNumber = Long.valueOf(contactNumber);

				// check if the user already exists in both mysql and mongodb
				userInfo = userRepository.findByUsernameAndPhonenumber(
						username, phnNumber);

				user = userDAO.findByUsernameAndUserPhoneNumber(username,
						contactNumber);

				if (userInfo != null && user != null) {
					throw new UserCreationException(
							"Duplicate user, a user with same phonenumber and email already exists");
				}

			} else if (isNotBlank(contactNumber)
					&& StringUtils.isBlank(username)) {

				if (StringUtils.isBlank(contactNumber)
						|| !validatePhoneNumber(contactNumber)) {
					logger.error("Unable to create new user, phonenumber is not a valid format");
					throw new UserCreationException(
							"User cannot be created with invalid phone number");
				}

				Long phnNumber = Long.valueOf(contactNumber);

				// check if the user already exists in both mysql and mongodb
				userInfo = userRepository.findByPhonenumber(phnNumber);

				user = userDAO.findByUserPhoneNumber(contactNumber);

				if (userInfo != null && user != null) {
					throw new UserCreationException(
							"Duplicate user, a user with same phone number already exists");
				}

			} else if (isNotBlank(username)
					&& StringUtils.isBlank(contactNumber)) {

				boolean validUserName = EmailValidator.getInstance().isValid(
						username);

				if (!validUserName) {
					logger.error("Unable to create new user, email is not a valid format");
					throw new UserCreationException(
							"User cannot be created with invalid email id");
				}

				// check if the user already exists in both mysql and mongodb
				userInfo = userRepository.findByUsername(username);

				user = userDAO.findByUsername(username);

				if (userInfo != null && user != null) {
					throw new UserCreationException(
							"Duplicate user, a user with same email already exists");
				}
			}

			try {

				if (user == null) {

					user = new User();
				}

				user.setUserPhoneNumber(contactNumber);

				if (isNotBlank(username)) {
					user.setUsername(username.toLowerCase());
				}

				user.setFirstName(registerUserBean.getFirstName());

				if (StringUtils.isNotBlank(registerUserBean.getLastName())) {
					user.setLastName(registerUserBean.getLastName());
				}

				// Set user fullname
				setUserInfo(registerUserBean, user, 1);

				user.setContactName(registerUserBean.getContactName());

				user.setStatus(UserStatus.INACTIVE);

				user.setWebsiteUrl(registerUserBean.getWebsiteUrl());

				// Set user default package
				setUserInfo(registerUserBean, user, 2);

				// Set user bank info
				setUserInfo(registerUserBean, user, 3);

				// for Service providers Specific
				if (registerUserBean.getOtherSkillSets() != null) {
					user.setOtherSkillSets(registerUserBean.getOtherSkillSets());
				}

				// If the user is Architect,InteriorDesigner,Plumber....etc
				// while registration time we have to set some Dummy
				// password.
				password = setTempPassword(registerUserBean, user, password);

				Address address = new Address();
				// creating for the first time so its the first address
				address.setAddressId(1);
				if (registerUserBean.getFirstName() != null
						&& registerUserBean.getLastName() != null) {
					address.setName(registerUserBean.getFirstName() + " "
							+ registerUserBean.getLastName());
				} else if (registerUserBean.getFirstName() != null) {
					address.setName(registerUserBean.getFirstName());
				}
				address.setAddressLine1(registerUserBean.getAddressLine1());
				address.setAddressLine2(registerUserBean.getAddressLine2());
				address.setCity(registerUserBean.getCity());
				address.setState(registerUserBean.getState());
				address.setCountry(registerUserBean.getCountry());
				address.setZipcode(registerUserBean.getZipcode());

				// lat and log implementation
				Point point = null;

				// Based on address some time point(lat and long) value is not
				// coming properly so below lines of code is commented and
				// calculating point based on zipcode

				// String addressPoint = registerUserBean.getAddressLine1() +
				// ","
				// + registerUserBean.getAddressLine2() + ","
				// + registerUserBean.getCity() + ","
				// + registerUserBean.getState() + ","
				// + registerUserBean.getCountry() + ","
				// + registerUserBean.getZipcode();
				// point =
				// addressLookupService.getGeoCodedAddress(addressPoint);
				// if (point != null) {
				// address.setLocation(point);
				// } else {
				// logger.info("based on Address search lat and lon null ");
				// point = addressLookupService
				// .getGeoCodedAddress(registerUserBean.getZipcode());
				// if (point != null) {
				// address.setLocation(point);
				// }
				// }

				point = addressLookupService
						.getGeoCodedAddress(registerUserBean.getZipcode());
				if (point != null) {
					address.setLocation(point);
				}

				address.setEmail(registerUserBean.getUsername());

				Set<Phone> phoneSet = new HashSet<>();
				Phone phone = new Phone();
				phone.setPrimary(true);
				phone.setNumber(registerUserBean.getContactNumber());
				phone.setType(PhoneType.MOBILE);
				phoneSet.add(phone);

				Phone phone1 = null;
				if (registerUserBean.getBranchTelNumber() != null) {
					phone1 = new Phone();
					phone1.setPrimary(false);
					phone1.setNumber(registerUserBean.getBranchTelNumber());
					phone1.setType(PhoneType.OFFICE);
					phoneSet.add(phone1);
				}

				address.setPhoneNumbers(phoneSet);
				user.addAddress(address);

				// save to mongo
				user = userDAO.save(user);

				// create user in mysql
				userInfo = createUserInfo(registerUserBean, username, password);

				// add phone number as well
				if (StringUtils.isNotEmpty(registerUserBean.getContactNumber())) {
					Long phonenumber = Long.parseLong(registerUserBean
							.getContactNumber());

					userInfo.setPhonenumber(phonenumber);
				}

				userInfo.setCreatedtime(new Date());

				userInfo.setLastmodifiedtime(new Date());

				// IMP: this is where there is link establised between mongo &
				// SQL for any JustInCase reason
				userInfo.setMongoUserId(user.getId());
				// special case, hence enclose with try, just in case something
				// fails, revert mongo save

				VerificationToken verificationToken = null;
				try {
					// save to sqldb

					if (isNotBlank(username)) {

						// create a verification token
						verificationToken = new VerificationToken(userInfo,
								VerificationTokenType.EmailRegistration, 0,
								"SYSTEM");
						// add to user the verification token
						userInfo.addVerificationToken(verificationToken);

					}

					// save
					userInfo = userRepository.save(userInfo);

					logger.info("User Registered successfully");
					logger.debug("VerificationToken" + verificationToken);

				} catch (Exception e) {
					// save failed, revert mongo save as well
					userDAO.delete(user.getId());
					throw e;
				}

				// Dealer registration time creating a store
				setUserInfo(registerUserBean, user, 4);

				// send tokens

				// send otp to registered mobile number
				if (isNotBlank(contactNumber)) {
					sendUserOTP(contactNumber);
				}

				// send verification token to registered email id
				if (isNotBlank(username)) {
					// Base64EncodeedToken
					String base64EncodedToken = Base64.getEncoder()
							.encodeToString(
									verificationToken.getToken().getBytes());

					// sending register mail with token
					userCommunicationService.sendRegistrationInfo(user,
							base64EncodedToken);
				}

				// New Registration Email Alert for Sapna ma'am and sms alert
				// for Rishabh Sir,Sapna ma'am,Shivu and Raju
				userCommunicationService.sendNewRegistrationAlert(user);

				// New Registration Alert sms to Rishabh sir
				// not neeeded
				// userCommunicationService.sendNewRegistrationAlertSms(user);

				// sending register sms
				if (isNotBlank(contactNumber)) {
					userCommunicationService
							.sendRegistrationInfo(user, "token");
				}

				String userCreatedInfo = null;
				String balanceDepositingInfo = null;

				if (userInfo.getFirstname() != null
						&& userInfo.getLastname() != null) {
					userCreatedInfo = "User " + userInfo.getFirstname() + " "
							+ userInfo.getLastname()
							+ " is successfully registered";
				} else if (userInfo.getFirstname() != null) {
					userCreatedInfo = "User " + userInfo.getFirstname()
							+ " is successfully registered";
				}
				// Auditing user created info
				updateUserAudit(userInfo, "REGISTER", userCreatedInfo);

				if (registerUserBean.getBalance() != 0.0) {
					balanceDepositingInfo = "Balance is modified with amount "
							+ registerUserBean.getBalance();
					// Auditing balance information
					updateUserAudit(userInfo, "BALANCE", balanceDepositingInfo);
				}

				ApiUser apiUser = new ApiUser(userInfo, user.getId());
				// set the actual role as above
				String mbgRoles = getRolesStr(user.getRoles());
				apiUser.setMbgRoles(mbgRoles);

				return apiUser;

			} catch (Exception e) {
				logger.error("Unexpected error in storing user details", e);
				throw new UserCreationException(
						"Unexpected error in storing user details", e);
			}

		} else {
			throw new UserCreationException(
					"Unable to create new user, it does not have minimum properties like username and password");
		}

	}

	private String setTempPassword(RegisterUserBean registerUserBean,
			User user, String password) {
		if (StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
				ServiceProviders.PLUMBER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.PAINTER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.CARPENTER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.ELECTRICIANS.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.PROJECTCONSULTANT.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.LANDSCAPEARCHITECT.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.SURVEYOR.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.WATERDIVINER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.VALUATOR.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.BOREWELL.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.FLOORINGCONTRACTOR.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.VASTHUCONSULTANTS.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.BIMCONSULTANTS.toString())) {
			password = getTempPassword(10);
		}
		return password;
	}

	private UserInfo createUserInfo(RegisterUserBean registerUserBean,
			String username, String password) {
		UserInfo userInfo = null;
		String username1 = null;
		if (username != null) {
			username1 = username.toLowerCase();
		}
		if (StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
				UserType.DEALER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						UserType.PROVIDER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.ARCHITECT.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.INTERIORDESIGNER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.PLUMBER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.PAINTER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.CARPENTER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.ELECTRICIANS.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.PROJECTCONSULTANT.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.LANDSCAPEARCHITECT.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.CIVILENGINEER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.SURVEYOR.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.WATERDIVINER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.VALUATOR.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.BOREWELL.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.FLOORINGCONTRACTOR.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.VASTHUCONSULTANTS.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.BIMCONSULTANTS.toString())) {

			userInfo = new UserInfo(username1,
					passwordEncoder.encode(password),
					registerUserBean.getFirstName(),
					registerUserBean.getContactName(), AuthRoles.AUTHENTICATED,
					"SYSTEM");
		} else {
			userInfo = new UserInfo(username1,
					passwordEncoder.encode(password),
					registerUserBean.getFirstName(),
					registerUserBean.getLastName(), AuthRoles.AUTHENTICATED,
					"SYSTEM");
		}
		return userInfo;
	}

	private UserInfo createUserInfo(CreateUserBean registerUserBean,
			String username, String password) {
		UserInfo userInfo = null;
		String username1 = null;
		if (username != null) {
			username1 = username.toLowerCase();
		}
		if (StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
				UserType.DEALER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						UserType.PROVIDER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.ARCHITECT.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.INTERIORDESIGNER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.PLUMBER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.PAINTER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.CARPENTER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.ELECTRICIANS.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.PROJECTCONSULTANT.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.LANDSCAPEARCHITECT.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.CIVILENGINEER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.SURVEYOR.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.WATERDIVINER.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.VALUATOR.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.BOREWELL.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.FLOORINGCONTRACTOR.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.VASTHUCONSULTANTS.toString())
				|| StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
						ServiceProviders.BIMCONSULTANTS.toString())) {

			userInfo = new UserInfo(username1,
					passwordEncoder.encode(password),
					registerUserBean.getFirstName(),
					registerUserBean.getContactName(), AuthRoles.AUTHENTICATED,
					"SYSTEM");
		} else {
			userInfo = new UserInfo(username1,
					passwordEncoder.encode(password),
					registerUserBean.getFirstName(),
					registerUserBean.getLastName(), AuthRoles.AUTHENTICATED,
					"SYSTEM");
		}
		return userInfo;
	}

	private void setUserInfo(RegisterUserBean registerUserBean, User user,
			int val) {
		switch (val) {
		case 1:
			if (StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
					UserType.DEALER.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							UserType.PROVIDER.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.ARCHITECT.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.INTERIORDESIGNER.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.PLUMBER.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.PAINTER.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.CARPENTER.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.ELECTRICIANS.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.PROJECTCONSULTANT.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.LANDSCAPEARCHITECT.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.CIVILENGINEER.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.CIVILCONTRACTOR.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.SURVEYOR.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.WATERDIVINER.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.VALUATOR.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.BOREWELL.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.FLOORINGCONTRACTOR.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.VASTHUCONSULTANTS.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.BIMCONSULTANTS.toString())) {
				user.setFullName(registerUserBean.getFirstName());
			} else {
				if (StringUtils.isNotBlank(registerUserBean.getLastName())) {
					user.setFullName(registerUserBean.getFirstName() + " "
							+ registerUserBean.getLastName());
				} else {
					user.setFullName(registerUserBean.getFirstName());
				}
			}
			break;

		case 2:
			// add the default roles first depending on type
			com.sarvah.mbg.domain.mongo.userprofile.role.Role role = roleDAO
					.findByName(registerUserBean.getUserType().toUpperCase());
			Set<Role> roles = new HashSet<>();

			// Setting Default package for dealer,provider,architect and
			// interiordesigner.
			if (StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
					UserType.DEALER.toString())) {
				UserPackage userPackage = userPackageDAO
						.findByName("SILVER DEALER");
				role.setUserPackage(userPackage);
			} else if (StringUtils.equalsIgnoreCase(
					registerUserBean.getUserType(),
					UserType.PROVIDER.toString())) {
				UserPackage userPackage = userPackageDAO
						.findByName("SILVER PROVIDER");
				role.setUserPackage(userPackage);
			} else if (StringUtils.equalsIgnoreCase(
					registerUserBean.getUserType(),
					ServiceProviders.ARCHITECT.toString())) {
				UserPackage userPackage = userPackageDAO
						.findByName("SILVER ARCHITECT");
				role.setUserPackage(userPackage);
			} else if (StringUtils.equalsIgnoreCase(
					registerUserBean.getUserType(),
					ServiceProviders.INTERIORDESIGNER.toString())) {
				UserPackage userPackage = userPackageDAO
						.findByName("SILVER INTERIORDESIGNER");
				role.setUserPackage(userPackage);
			}
			roles.add(role);
			user.setRoles(roles);
			break;

		case 3:
			if (StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
					UserType.DEALER.toString())) {
				// set bank related data
				user.setBankAccountNumber(registerUserBean
						.getBankAccountNumber());
				user.setBankName(registerUserBean.getBankName());
				if (registerUserBean.getVatNumber() != null) {
					user.setVatNumber(registerUserBean.getVatNumber());
				}
				if (registerUserBean.getPanNumber() != null) {
					user.setPanNumber(registerUserBean.getPanNumber());
				}
				if (registerUserBean.getIfscCode() != null) {
					user.setIfscCode(registerUserBean.getIfscCode());
				}
			} else if (StringUtils.equalsIgnoreCase(
					registerUserBean.getUserType(),
					UserType.PROVIDER.toString())) {
				if (registerUserBean.getVatNumber() != null) {
					user.setVatNumber(registerUserBean.getVatNumber());
				}
				if (registerUserBean.getPanNumber() != null) {
					user.setPanNumber(registerUserBean.getPanNumber());
				}
			}

			// If user is BusinessAssociate We have set invested and current
			// balance of BusinessAssociate.
			else if (StringUtils.equalsIgnoreCase(
					registerUserBean.getUserType(),
					UserType.BUSINESSASSOCIATE.toString())) {
				user.setBankAccountNumber(registerUserBean
						.getBankAccountNumber());
				user.setBankName(registerUserBean.getBankName());
				user.setPanNumber(registerUserBean.getPanNumber());
				user.setIfscCode(registerUserBean.getIfscCode());
				if (registerUserBean.getBalance() >= 0) {
					user.setInvestedBalance(registerUserBean.getBalance());
					user.setCurrentBalance(registerUserBean.getBalance());
				} else {
					user.setInvestedBalance(0.0);
					user.setCurrentBalance(0.0);
				}
			}
			break;

		case 4:
			// dealer registration time creating a store
			if (StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
					UserType.DEALER.toString())) {

				Store newStore = new Store();
				newStore.setStorename(user.getFirstName());
				newStore.setUser(user);
				newStore.setCreatedDate(new Date());
				newStore.setLastModifiedDate(new Date());
				newStore.setCreatedBy(user.getId());
				newStore.setLastModifiedBy(user.getId());
				List<Product> products = new ArrayList<Product>();

				Product product = productDAO
						.findOne("57a99d62e4b09240a6b18611");

				if (product != null) {
					products.add(product);
				}

				newStore.setProducts(products);
				storeDAO.save(newStore);

				StoreProductPricing storeProductPricing = new StoreProductPricing(
						newStore.getId(), product.getId(), product.getSkuId());

				storeProductPricing.setMaxRetailPrice(product.getPrice()
						.getMrp());
				storeProductPricing
						.setSellingPrice(product.getPrice().getMrp());
				storeProductPricing.setFulfilledBy(user.getFullName());
				storeProductPricing.setStockCount(0);
				Shipping shipping = new Shipping();
				shipping.setLocalDelivery(true);
				shipping.setLocalShippingChrg(0.0);
				shipping.setZonalDelivery(false);
				shipping.setNationalDelivery(false);

				storeProductPricing.setShipping(shipping);

				storeProductPricingDAO.save(storeProductPricing);
			}
			break;
		}
	}

	private void setUserInfo(CreateUserBean registerUserBean, User user, int val) {
		switch (val) {
		case 1:
			if (StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
					UserType.DEALER.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							UserType.PROVIDER.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.ARCHITECT.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.INTERIORDESIGNER.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.PLUMBER.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.PAINTER.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.CARPENTER.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.ELECTRICIANS.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.PROJECTCONSULTANT.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.LANDSCAPEARCHITECT.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.CIVILENGINEER.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.CIVILCONTRACTOR.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.SURVEYOR.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.WATERDIVINER.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.VALUATOR.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.BOREWELL.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.FLOORINGCONTRACTOR.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.VASTHUCONSULTANTS.toString())
					|| StringUtils.equalsIgnoreCase(
							registerUserBean.getUserType(),
							ServiceProviders.BIMCONSULTANTS.toString())) {
				user.setFullName(registerUserBean.getFirstName());
			} else {
				if (StringUtils.isNotBlank(registerUserBean.getLastName())) {
					user.setFullName(registerUserBean.getFirstName() + " "
							+ registerUserBean.getLastName());
				} else {
					user.setFullName(registerUserBean.getFirstName());
				}
			}
			break;

		case 2:
			// add the default roles first depending on type
			com.sarvah.mbg.domain.mongo.userprofile.role.Role role = roleDAO
					.findByName(registerUserBean.getUserType().toUpperCase());
			Set<Role> roles = new HashSet<>();

			// Setting Default package for dealer,provider,architect and
			// interiordesigner.
			// if (StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
			// UserType.DEALER.toString())) {
			// UserPackage userPackage = userPackageDAO
			// .findByName("SILVER DEALER");
			// role.setUserPackage(userPackage);
			// } else if (StringUtils.equalsIgnoreCase(
			// registerUserBean.getUserType(),
			// UserType.PROVIDER.toString())) {
			// UserPackage userPackage = userPackageDAO
			// .findByName("SILVER PROVIDER");
			// role.setUserPackage(userPackage);
			// } else if (StringUtils.equalsIgnoreCase(
			// registerUserBean.getUserType(),
			// ServiceProviders.ARCHITECT.toString())) {
			// UserPackage userPackage = userPackageDAO
			// .findByName("SILVER ARCHITECT");
			// role.setUserPackage(userPackage);
			// } else if (StringUtils.equalsIgnoreCase(
			// registerUserBean.getUserType(),
			// ServiceProviders.INTERIORDESIGNER.toString())) {
			// UserPackage userPackage = userPackageDAO
			// .findByName("SILVER INTERIORDESIGNER");
			// role.setUserPackage(userPackage);
			// }

			if (StringUtils.isNotBlank(registerUserBean.getPackName())) {
				UserPackage userPackage = userPackageDAO
						.findByName(registerUserBean.getPackName());
				role.setUserPackage(userPackage);
			}
			roles.add(role);
			user.setRoles(roles);
			break;

		case 3:
			if (StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
					UserType.DEALER.toString())) {
				// set bank related data
				if (registerUserBean.getBankAccountNumber() != null) {
					user.setBankAccountNumber(registerUserBean
							.getBankAccountNumber());
				}
				if (registerUserBean.getBankName() != null) {
					user.setBankName(registerUserBean.getBankName());
				}
				if (registerUserBean.getVatNumber() != null) {
					user.setVatNumber(registerUserBean.getVatNumber());
				}
				if (registerUserBean.getPanNumber() != null) {
					user.setPanNumber(registerUserBean.getPanNumber());
				}
				if (registerUserBean.getIfscCode() != null) {
					user.setIfscCode(registerUserBean.getIfscCode());
				}
			} else if (StringUtils.equalsIgnoreCase(
					registerUserBean.getUserType(),
					UserType.PROVIDER.toString())) {
				if (registerUserBean.getVatNumber() != null) {
					user.setVatNumber(registerUserBean.getVatNumber());
				}
				if (registerUserBean.getPanNumber() != null) {
					user.setPanNumber(registerUserBean.getPanNumber());
				}
			}

			// If user is BusinessAssociate We have set invested and current
			// balance of BusinessAssociate.
			else if (StringUtils.equalsIgnoreCase(
					registerUserBean.getUserType(),
					UserType.BUSINESSASSOCIATE.toString())) {
				user.setBankAccountNumber(registerUserBean
						.getBankAccountNumber());
				user.setBankName(registerUserBean.getBankName());
				user.setPanNumber(registerUserBean.getPanNumber());
				user.setIfscCode(registerUserBean.getIfscCode());
				if (registerUserBean.getBalance() >= 0) {
					user.setInvestedBalance(registerUserBean.getBalance());
					user.setCurrentBalance(registerUserBean.getBalance());
				} else {
					user.setInvestedBalance(0.0);
					user.setCurrentBalance(0.0);
				}
			}
			break;

		case 4:
			// dealer registration time creating a store
			if (StringUtils.equalsIgnoreCase(registerUserBean.getUserType(),
					UserType.DEALER.toString())) {

				Store newStore = new Store();
				newStore.setStorename(user.getFirstName());
				newStore.setUser(user);
				newStore.setCreatedDate(new Date());
				newStore.setLastModifiedDate(new Date());
				newStore.setCreatedBy(user.getId());
				newStore.setLastModifiedBy(user.getId());
				List<Product> products = new ArrayList<Product>();

				Product product = productDAO
						.findOne("57a99d62e4b09240a6b18611");

				if (product != null) {
					products.add(product);
				}

				newStore.setProducts(products);
				storeDAO.save(newStore);

				StoreProductPricing storeProductPricing = new StoreProductPricing(
						newStore.getId(), product.getId(), product.getSkuId());

				storeProductPricing.setMaxRetailPrice(product.getPrice()
						.getMrp());
				storeProductPricing
						.setSellingPrice(product.getPrice().getMrp());
				storeProductPricing.setFulfilledBy(user.getFullName());
				storeProductPricing.setStockCount(0);
				Shipping shipping = new Shipping();
				shipping.setLocalDelivery(true);
				shipping.setLocalShippingChrg(0.0);
				shipping.setZonalDelivery(false);
				shipping.setNationalDelivery(false);

				storeProductPricing.setShipping(shipping);

				storeProductPricingDAO.save(storeProductPricing);
			}
			break;
		}
	}

	/**
	 * Gets the roles str.
	 *
	 * @param roles
	 *            the roles
	 * @return the roles str
	 */
	private String getRolesStr(Set<Role> roles) {
		StringBuilder sb = new StringBuilder();
		if (roles != null) {
			int count = 0;
			for (Role role : roles) {
				if (role != null) {
					sb.append(role.getName());
					if (count < (roles.size() - 1)) {
						sb.append("|");
						count++;
					}
				}

			}
		}

		return sb.toString();

	}

	/**
	 * method for user login.
	 *
	 * @param username
	 *            the username
	 * @param rawPassword
	 *            the raw password
	 * @return the api user
	 * @throws AuthenticationException
	 *             the authentication exception
	 */
	@Override
	@Transactional
	public ApiUser authenticate(String username, String rawPassword)
			throws AuthenticationException {
		try {

			UserInfo userInfo = null;
			User user = null;

			if (EmailValidator.getInstance().isValid(username)) {
				userInfo = userRepository.findByUsername(username);

				user = userDAO.findByUsername(username.toLowerCase());

				if (userInfo == null || user == null) {
					throw new AuthenticationException("User " + username
							+ " not present in our system");
				}

				if (!userInfo.isVerified() || !userInfo.isUserNameVerified()) {
					throw new AuthenticationException("User " + username
							+ " is not verified in our system");
				}

			} else {
				userInfo = userRepository.findByPhonenumber(Long
						.valueOf(username));
				user = userDAO.findByUserPhoneNumber(username);

				if (userInfo == null || user == null) {
					throw new AuthenticationException("User " + username
							+ " not present in our system");
				}

				if (!userInfo.isVerified() || !userInfo.isUserPhoneVerified()) {
					throw new AuthenticationException("User " + username
							+ " is not verified in our system");
				}
			}

			boolean passwordValid = passwordEncoder.matches(rawPassword,
					userInfo.getHashedPassword());

			boolean userVerifiedByMBG = (user.getStatus().compareTo(
					UserStatus.ACTIVE) == 0);

			// In order to throw proper exception for password mismatch and
			// unverified user, we are checking these cases seperately
			if (passwordValid) {
				if (userVerifiedByMBG) {
					logger.info("User credentials are Valid and user is active in our system");
				} else {
					throw new AuthenticationException(
							"User is not verified in Buildonn");
				}

			} else {
				throw new AuthenticationException("Password does not match");
			}

			AuthorizationToken token = userInfo.getAuthorizationToken();
			boolean tokenChanged = false;

			if (token == null) {

				token = new AuthorizationToken(userInfo, "SYSTEM");
				tokenChanged = true;

			} else if (token.hasExpired()) {
				token.renewToken(UUID.randomUUID().toString());
				tokenChanged = true;
			}

			if (tokenChanged) {
				userInfo.setAuthorizationToken(token);
				userRepository.save(userInfo);
			}

			ApiUser apiUser = new ApiUser(userInfo, user.getId());
			// set the actual role as above
			String mbgRoles = getRolesStr(user.getRoles());
			apiUser.setMbgRoles(mbgRoles);
			// set the token
			apiUser.setToken(token.getToken());
			// set token expires
			apiUser.setExpires(token.getExpiryDate().getTime());

			apiUser.setUserPhoneNumber(user.getUserPhoneNumber());
			logger.info("User login to Buildonn successfully");
			logger.debug("AuthorizationToken" + token);
			return apiUser;

		} catch (Exception e) {
			if (e instanceof AuthenticationException)
				throw e;
			logger.error("Unexpected error occured while authenticating user",
					e);
			throw new AuthenticationException(
					"Unexpected error occured while authenticating user");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.auth.AuthUserService#getUser(java.lang.String)
	 */
	@Override
	public ApiUser getUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.userprofile.auth.AuthUserService#saveUser()
	 */
	@Override
	public ApiUser saveUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		UserInfo userInfo = userRepository.findByUsername(username.trim());
		if (userInfo == null)
			throw new UsernameNotFoundException(
					"Unable to locate user with username - " + username);

		return userInfo;
	}

	/**
	 * method for verifyToken.
	 *
	 * @param base64EncodedToken
	 *            the base64 encoded token
	 * @return the verification token
	 * @throws TokenAlreadyVerifiedException
	 *             the token already verified exception
	 * @throws TokenNotFoundException
	 *             the token not found exception
	 * @throws TokenExpiredException
	 *             the token expired exception
	 * @throws Exception
	 *             the exception
	 */
	@Override
	@Transactional
	public VerificationToken verify(String base64EncodedToken, String username)
			throws TokenAlreadyVerifiedException, TokenNotFoundException,
			TokenExpiredException, Exception {
		logger.debug("Verfying token from db");
		Assert.notNull(base64EncodedToken);

		String rawtoken = new String(Base64.getDecoder().decode(
				base64EncodedToken));

		VerificationToken token = verificationTokenRepository
				.findByToken(rawtoken);

		if (token == null) {
			// throw token not preset exception
			throw new TokenNotFoundException("Invalid Token");
		}

		if (token.hasExpired()) {
			// throw token expired exception
			throw new TokenExpiredException(
					"Token has expired, should request new token");
		}

		if (StringUtils.isNotBlank(username)
				&& EmailValidator.getInstance().isValid(username)) {
			if (token.isVerified() && token.getUser().isVerified()
					&& token.getUser().isUserNameVerified()) {
				// throw already verified exception
				throw new TokenAlreadyVerifiedException("User already verified");
			}
		} else if (StringUtils.isNotBlank(username)
				&& validatePhoneNumber(username)) {
			if (token.isVerified() && token.getUser().isVerified()
					&& token.getUser().isUserPhoneVerified()) {
				// throw already verified exception
				throw new TokenAlreadyVerifiedException("User already verified");
			}
		} else if (token.isVerified() || token.getUser().isVerified()) {
			// throw already verified exception
			throw new TokenAlreadyVerifiedException("User already verified");
		}

		token.setVerified(true);

		token.getUser().setVerified(true);
		UserInfo userInfo = null;
		if (!username.equalsIgnoreCase("null") && username != " ") {
			userInfo = token.getUser();
			boolean validUserName = EmailValidator.getInstance().isValid(
					username);
			if (validUserName) {
				userInfo.setUserNameVerified(true);
			}
			boolean validPhoneNumber = validatePhoneNumber(username);
			if (validPhoneNumber) {
				userInfo.setUserPhoneVerified(true);
			}
		} else {
			userInfo = token.getUser();
			if (token.getTokenType().compareTo(VerificationTokenType.OTP) == 0) {
				userInfo.setUserPhoneVerified(true);
			} else {
				userInfo.setUserNameVerified(true);
			}
		}

		userRepository.save(userInfo);

		// sending register mail with token

		User mongoUser = userDAO.findOne(userInfo.getMongoUserId());
		Set<Role> roles = mongoUser.getRoles();
		// if user role is enduser at verification time changing his status to
		// active
		for (Role role : roles) {
			if (role.getName().equalsIgnoreCase("ENDUSER")) {
				mongoUser.setStatus(UserStatus.ACTIVE);
				mongoUser.setActiveSince(new Date());
				userCommunicationService.sendVerifyAndWecomeInfo(mongoUser);
			}
		}
		userDAO.save(mongoUser);
		logger.info("User verified successfully");
		logger.debug("VerificationToken" + token);
		return token;
	}

	/**
	 * Check if the end user is end user.
	 *
	 * @param user
	 *            the user
	 * @return true, if is user end user
	 */
	@SuppressWarnings("unused")
	private boolean isUserEndUser(User user) {

		Set<Role> userRoles = user.getRoles();

		for (Role role : userRoles) {
			if (role != null
					&& StringUtils.equalsIgnoreCase(role.getName(),
							UserType.ENDUSER.toString())) {

				return true;

			}
		}

		return false;
	}

	/**
	 * method for Send verification token.
	 *
	 * @param username
	 *            the username
	 * @return the verification token
	 * @throws AuthenticationException
	 *             the authentication exception
	 * @throws Exception
	 *             the exception
	 */
	@Override
	@Transactional
	public VerificationToken sendVerificationToken(String username)
			throws AuthenticationException, Exception {
		Assert.notNull(username);
		// check if the user already exists
		UserInfo userInfo = userRepository.findByUsername(username.trim()
				.toLowerCase());

		if (userInfo == null) {
			// unable to find the user in our repository
			throw new AuthenticationException(
					"Unable to find the user in our system, Invalid User!");
		}

		VerificationToken verificationToken = new VerificationToken(userInfo,
				VerificationTokenType.EmailRegistration, 0, "SYSTEM");
		userInfo.addVerificationToken(verificationToken);
		userRepository.save(userInfo);
		logger.debug("VerificationToken generated successfully of the user {}",
				username);
		// send token again to user

		String base64EncodedToken = Base64.getEncoder().encodeToString(
				verificationToken.getToken().getBytes());
		User mongoUser = userDAO.findOne(userInfo.getMongoUserId());

		userCommunicationService.sendResendVerificationTokenInfo(mongoUser,
				base64EncodedToken);
		logger.info("Successfully Resend the verification token for the request user");
		return verificationToken;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.auth.AuthUserService#sendForgotPasswordToken
	 * (java.lang.String)
	 */
	@Override
	@Transactional
	public VerificationToken sendForgotPasswordToken(String username)
			throws AuthenticationException, Exception {
		Assert.notNull(username);
		// check if the user already exists
		UserInfo userInfo = userRepository.findByUsername(username.trim()
				.toLowerCase());

		if (userInfo == null) {
			// unable to find the user in our repository
			throw new AuthenticationException(
					"Unable to find the user in our system, Invalid User!");
		}
		// create a verification token with default expiry
		VerificationToken verificationToken = new VerificationToken(userInfo,
				VerificationTokenType.LostPassword, 0, "SYSTEM");
		userInfo.addVerificationToken(verificationToken);
		userRepository.save(userInfo);
		logger.debug("LostPasswordToken generated successfully of the user {}",
				username);

		// send token again to user
		String base64EncodedToken = Base64.getEncoder().encodeToString(
				verificationToken.getToken().getBytes());
		User mongoUser = userDAO.findOne(userInfo.getMongoUserId());
		userCommunicationService.sendForgotPasswordInfo(mongoUser,
				base64EncodedToken);
		logger.info("Successfully Resend the verification token for the request user");
		return verificationToken;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.auth.AuthUserService#resetPassword(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	@Transactional
	public VerificationToken resetPassword(String base64EncodedToken,
			String password) throws Exception {
		logger.debug("Verfying reset password token");
		Assert.notNull(base64EncodedToken,
				"Encoded token for lostPassword cannot be null");
		Assert.notNull(password, "Change password cannot be null");
		String rawtoken = new String(Base64.getDecoder().decode(
				base64EncodedToken));
		VerificationToken token = verificationTokenRepository
				.findByToken(rawtoken);
		if (token == null) {
			// throw token not preset exception
			throw new TokenNotFoundException();
		}

		if (token.hasExpired()) {
			// throw token expired exception
			throw new TokenExpiredException(
					"Token has expired, should request new token");
		}

		if (token.isVerified()) {
			// throw already verified exception
			throw new TokenAlreadyVerifiedException(
					"Token is already used, please request new token to reset password again");
		}

		if (token.getTokenType().compareTo(VerificationTokenType.LostPassword) != 0) {
			// user trying something nasty, you should only use the lost
			// password token to reset the password
			throw new TokenNotFoundException(
					"Unable to find the token type for lost password, please request again!");
		}

		// every thing looks good, go ahead and change password

		UserInfo user = token.getUser();
		user.setHashedPassword(passwordEncoder.encode(password));
		token.setVerified(true);
		user.setLastmodifiedtime(new Date());
		userRepository.save(user);
		return token;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.auth.AuthUserService#sendUserOTP(java.lang
	 * .String)
	 */
	@Override
	public VerificationToken sendUserOTP(String username)
			throws AuthenticationException, UserCommException {
		Assert.notNull(username);
		logger.info("Sending OTP token for the user {}", username);

		// check if the user exists
		UserInfo userInfo = userRepository.findByUsername(username.trim()
				.toLowerCase());

		if (userInfo == null) {
			// check if phone number
			boolean validPhoneNumber = validatePhoneNumber(username);
			if (validPhoneNumber) {
				userInfo = userRepository.findByPhonenumber(Long
						.valueOf(username));
			}
			if (userInfo == null) {
				// unable to find the user in our repository
				throw new AuthenticationException(
						"Unable to find the user in our system, Invalid User!");
			}
		}

		// Generate a OTP
		Date now = new Date();
		long t = now.getTime();
		String otp = TOTP.generateTOTP("98459369029845936902",
				Long.toString(t), "6");

		// create a verification token with default expiry
		VerificationToken verificationToken = new VerificationToken(userInfo,
				otp, VerificationTokenType.OTP, 30, "SYSTEM");
		userInfo.addVerificationToken(verificationToken);
		userRepository.save(userInfo);
		logger.debug("OTP generated successfully of the user {}", username);

		// send OTP to User using SMS
		Long phonenumber = null;
		String ph = null;
		String emaiId = null;
		if (userInfo.getPhonenumber() != null) {
			phonenumber = userInfo.getPhonenumber();
			ph = phonenumber.toString();
		}

		if (userInfo.getUsername() != null) {
			emaiId = userInfo.getUsername();
		}

		userCommunicationService.sendOTPInfo(ph, otp, emaiId,
				userInfo.getFirstname());
		logger.info("OTP sent successfully for the requested user");
		return verificationToken;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.auth.AuthUserService#sendUsernameUpdateOTP
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public VerificationToken sendUsernameUpdateOTP(String username,
			String userId) throws AuthenticationException, UserCommException {
		Assert.notNull(username);
		logger.info("Sending OTP token for the user {}", username);

		// check if the user exists
		UserInfo userInfo = userRepository.findByMongoUserId(userId);

		if (userInfo == null) {
			// unable to find the user in our repository
			throw new AuthenticationException(
					"Unable to find the user in our system, Invalid User!");
		}

		// Generate a OTP
		Date now = new Date();
		long t = now.getTime();
		String otp = TOTP.generateTOTP("98459369029845936902",
				Long.toString(t), "6");

		// create a verification token with default expiry
		VerificationToken verificationToken = new VerificationToken(userInfo,
				otp, VerificationTokenType.OTP, 30, "SYSTEM");
		userInfo.addVerificationToken(verificationToken);
		userRepository.save(userInfo);
		logger.debug("OTP generated successfully of the user {}", username);

		// send OTP to User using SMS
		boolean validUserName = EmailValidator.getInstance().isValid(username);

		boolean validPhoneNumber = validatePhoneNumber(username);

		String emaiId = null;
		String phonenumber = null;

		if (validUserName) {
			emaiId = username;
		}
		if (validPhoneNumber) {
			phonenumber = username;
		}

		userCommunicationService.sendUserUpdateOTPInfo(phonenumber, otp,
				emaiId, userInfo.getFirstname());
		logger.info("OTP sent successfully for the requested user");
		return verificationToken;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.auth.AuthUserService#resetPasswordUsingOTP
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public VerificationToken resetPasswordUsingOTP(String username,
			String base64EncodedToken, String password)
			throws TokenNotFoundException, TokenExpiredException,
			TokenAlreadyVerifiedException, UserCommException {

		logger.debug("Resetting user password using OTP");
		Assert.notNull(base64EncodedToken, "OTP cannot be null");
		Assert.notNull(password, "Change password cannot be null");
		String decodedToken = new String(Base64.getDecoder().decode(
				base64EncodedToken));

		boolean validUserName = EmailValidator.getInstance().isValid(username);

		boolean validPhoneNumber = validatePhoneNumber(username);

		VerificationToken token = null;

		if (validUserName) {
			token = verificationTokenRepository
					.findByUser_UsernameAndTokenAndTokenType(
							username.toLowerCase(), decodedToken,
							VerificationTokenType.OTP);
		} else if (validPhoneNumber) {
			token = verificationTokenRepository
					.findByUser_PhonenumberAndTokenAndTokenType(
							Long.valueOf(username), decodedToken,
							VerificationTokenType.OTP);
		} else {
			logger.info("Invalid Email/phonenumber");
		}

		if (token == null) {
			// throw token not preset exception
			throw new TokenNotFoundException(
					"Invalid Token! Please enter valid token.");
		}

		if (token.hasExpired()) {
			// throw token expired exception
			throw new TokenExpiredException(
					"Token has expired, should request new token");
		}

		if (token.isVerified()) {
			// throw already verified exception
			throw new TokenAlreadyVerifiedException(
					"OTP already used, please request new OTP to reset password again");
		}

		// every thing looks good, go ahead and change password

		UserInfo user = token.getUser();
		user.setHashedPassword(passwordEncoder.encode(password));
		token.setVerified(true);
		user.setLastmodifiedtime(new Date());
		userRepository.save(user);

		String fullName = null;
		if (user.getFirstname() != null && user.getLastname() != null) {
			fullName = user.getFirstname() + " " + user.getLastname();
		} else if (user.getFirstname() != null) {
			fullName = user.getFirstname();
		}
		String email = null;
		Long mobileNumber = null;
		if (user.getUsername() != null) {
			email = user.getUsername();
		}
		if (user.getPhonenumber() != null) {
			mobileNumber = user.getPhonenumber();
		}
		String mobile = null;
		if (mobileNumber != null) {
			mobile = mobileNumber.toString();
		}
		userCommunicationService.sendResetPasswordInfo(fullName, email, mobile);

		String passwordUpdatedInfo = "User " + user.getUserid()
				+ " password is changed";
		// User info auditing
		updateUserAudit(user, "PASSWORD", passwordUpdatedInfo);
		return token;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.auth.AuthUserService#updateUsernameUsingOTP
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	public VerificationToken updateUsernameUsingOTP(String base64EncodedToken,
			String username, String userId) throws TokenNotFoundException,
			TokenExpiredException, TokenAlreadyVerifiedException, Exception {
		logger.debug("updating username using OTP");
		Assert.notNull(base64EncodedToken, "OTP cannot be null");
		String decodedToken = new String(Base64.getDecoder().decode(
				base64EncodedToken));

		UserInfo user = userRepository.findByMongoUserId(userId);

		if (user == null) {
			throw new Exception("Plase enter valid userId");
		}

		VerificationToken token = verificationTokenRepository
				.findByTokenAndTokenTypeAndUser(decodedToken,
						VerificationTokenType.OTP, user);

		if (token == null) {
			// throw token not preset exception
			throw new TokenNotFoundException(
					"Invalid Token! Please enter valid Token.");
		}

		if (token.hasExpired()) {
			// throw token expired exception
			throw new TokenExpiredException(
					"Token has expired, should request new token");
		}

		if (token.isVerified()) {
			// throw already verified exception
			throw new TokenAlreadyVerifiedException(
					"OTP already used, please request new OTP to reset password again");
		}

		Map<String, String> changeDeltaMap = new HashMap<>();

		String[] deltaValues = user.getUserchangedelta().split(":");

		for (String deltaValue : deltaValues) {

			String[] keyAndValuePair = deltaValue.split("=");
			if (keyAndValuePair.length == 2) {
				changeDeltaMap.put(keyAndValuePair[0], keyAndValuePair[1]);
			}

		}

		boolean validUserName = EmailValidator.getInstance().isValid(username);

		boolean validPhoneNumber = validatePhoneNumber(username);

		// every thing looks good, go ahead and update username
		if (validUserName) {
			String newUsername = changeDeltaMap.get("username");

			user.setUsername(newUsername);
			token.setVerified(true);
			user.setLastmodifiedtime(new Date());
			userRepository.save(user);
			// Updating username in mongodb
			User mongoUser = userDAO.findById(user.getMongoUserId());
			mongoUser.setUsername(newUsername);
			userDAO.save(mongoUser);
			String usernameUpdatedInfo = "User " + user.getUserid()
					+ " username is changed, new username is " + newUsername;
			// User info auditing
			updateUserAudit(user, "USERINFO", usernameUpdatedInfo);
		} else if (validPhoneNumber) {
			String newPhoneNumber = changeDeltaMap.get("phonenumber");

			user.setPhonenumber(Long.valueOf(newPhoneNumber));
			token.setVerified(true);
			user.setLastmodifiedtime(new Date());
			userRepository.save(user);
			// Updating phone number in mongodb
			User mongoUser = userDAO.findById(user.getMongoUserId());
			mongoUser.setUserPhoneNumber(newPhoneNumber);
			userDAO.save(mongoUser);
			String userPhoneUpdatedInfo = "User " + user.getUserid()
					+ " phonenumber is changed, new phonenumber is "
					+ newPhoneNumber;
			// User info auditing
			updateUserAudit(user, "USERINFO", userPhoneUpdatedInfo);
		} else {
			logger.info("Invalid email or mobile number");
		}

		return token;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.auth.AuthUserService#createNewUser(com.sarvah
	 * .mbg.userprofile.auth.model.CreateUserBean)
	 */
	@Override
	public ApiUser createNewUser(CreateUserBean registerUserBean)
			throws UserCreationException {
		logger.info("Creating new user");
		if (registerUserBean != null
				&& (isNotBlank(registerUserBean.getUsername()) || isNotBlank(registerUserBean
						.getContactNumber()))) {
			String username = registerUserBean.getUsername();
			String password = registerUserBean.getPassword();
			String contactNumber = registerUserBean.getContactNumber();

			UserInfo userInfo = null;
			List<UserInfo> userInfoList = null;
			User user = null;

			if (isNotBlank(contactNumber) && isNotBlank(username)) {

				boolean validPhoneNumber = validatePhoneNumber(contactNumber);

				boolean validUserName = EmailValidator.getInstance().isValid(
						username);

				if (!validPhoneNumber || !validUserName) {
					logger.error("Unable to create new user, phonenumber/email is not a valid format");
					throw new UserCreationException(
							"User cannot be created with invalid phone number or email id");
				}

				Long phnNumber = Long.valueOf(contactNumber);

				// check if the user already exists in both mysql and mongodb
				userInfo = userRepository.findByUsernameAndPhonenumber(
						username, phnNumber);

				user = userDAO.findByUsernameAndUserPhoneNumber(username,
						contactNumber);

				if (userInfo != null && user != null
						&& equalsIgnoreCase(userInfo.getUsername(), username)) {
					throw new UserCreationException(
							"Duplicate user, a user with same phonenumber and email already exists");
				}

			} else if (isNotBlank(contactNumber)
					&& StringUtils.isBlank(username)) {

				if (StringUtils.isBlank(contactNumber)
						|| !validatePhoneNumber(contactNumber)) {
					logger.error("Unable to create new user, phonenumber is not a valid format");
					throw new UserCreationException(
							"User cannot be created with invalid phone number");
				}

				Long phnNumber = Long.valueOf(contactNumber);

				// check if the user already exists in both mysql and mongodb
				// userInfo = userRepository.findByPhonenumber(phnNumber);

				userInfo = userRepository.findByPhonenumber(phnNumber);

				user = userDAO.findByUserPhoneNumber(contactNumber);

				if (userInfo != null
						&& user != null
						&& equalsIgnoreCase(userInfo.getPhonenumber()
								.toString(), phnNumber.toString())) {
					throw new UserCreationException(
							"Duplicate user, a user with same phone number already exists");
				}

				userInfoList = userRepo.findByPhonenumber(phnNumber);
				if (userInfoList.size() != 0 || user != null) {
					throw new UserCreationException(
							"Duplicate user, a user with same phone number already exists");
				}

			} else if (isNotBlank(username)
					&& StringUtils.isBlank(contactNumber)) {

				boolean validUserName = EmailValidator.getInstance().isValid(
						username);

				if (!validUserName) {
					logger.error("Unable to create new user, email is not a valid format");
					throw new UserCreationException(
							"User cannot be created with invalid email id");
				}

				// check if the user already exists in both mysql and mongodb
				userInfo = userRepository.findByUsername(username);

				user = userDAO.findByUsername(username);

				if (userInfo != null && user != null
						&& equalsIgnoreCase(userInfo.getUsername(), username)) {
					throw new UserCreationException(
							"Duplicate user, a user with same email already exists");
				}

				userInfoList = userRepo.findByUsername(username);
				if (userInfoList.size() != 0 || user != null) {
					throw new UserCreationException(
							"Duplicate user, a user with same phone number already exists");
				}
			}

			try {

				if (user == null) {

					user = new User();
				}

				user.setUserPhoneNumber(contactNumber);

				if (isNotBlank(username)) {
					user.setUsername(username.toLowerCase());
				}

				user.setFirstName(registerUserBean.getFirstName());

				if (StringUtils.isNotBlank(registerUserBean.getLastName())) {
					user.setLastName(registerUserBean.getLastName());
				}

				// Set user fullname
				setUserInfo(registerUserBean, user, 1);

				user.setContactName(registerUserBean.getContactName());

				user.setStatus(UserStatus.valueOf(registerUserBean.getStatus()));

				if (registerUserBean.getStatus().equals("ACTIVE")) {
					user.setActiveSince(new Date());
				}

				Description desc = new Description();
				desc.setLang("eng");
				desc.setVal(registerUserBean.getDesc());
				user.setDesc(desc);

				user.setWebsiteUrl(registerUserBean.getWebsiteUrl());

				// Set user default package
				setUserInfo(registerUserBean, user, 2);

				// Set user bank info and Business associate balance
				setUserInfo(registerUserBean, user, 3);
				if (registerUserBean.getVatNumber() != null) {
					user.setVatNumber(registerUserBean.getVatNumber());
				}

				// for Service providers Specific
				if (registerUserBean.getOtherSkillSets() != null) {
					user.setOtherSkillSets(registerUserBean.getOtherSkillSets());
				}
				// for Service providers Specific
				if (registerUserBean.getOperatingCities() != null) {
					user.setOperatingCities(registerUserBean
							.getOperatingCities());
				}

				// Set temporary password
				password = getTempPassword(10);

				Address address = new Address();
				// creating for the first time so its the first address
				address.setAddressId(1);
				if (registerUserBean.getFirstName() != null
						&& registerUserBean.getLastName() != null) {
					address.setName(registerUserBean.getFirstName() + " "
							+ registerUserBean.getLastName());
				} else if (registerUserBean.getFirstName() != null) {
					address.setName(registerUserBean.getFirstName());
				}
				address.setAddressLine1(registerUserBean.getAddressLine1());
				address.setAddressLine2(registerUserBean.getAddressLine2());
				address.setCity(registerUserBean.getCity());
				address.setState(registerUserBean.getState());
				address.setCountry(registerUserBean.getCountry());
				address.setZipcode(registerUserBean.getZipcode());

				// lat and log implementation
				Point point = null;
				// Based on address some time point(lat and long) value is not
				// coming properly so below lines of code is commented and
				// calculating point based on zipcode

				// String addressPoint = registerUserBean.getAddressLine1() +
				// ","
				// + registerUserBean.getAddressLine2() + ","
				// + registerUserBean.getCity() + ","
				// + registerUserBean.getState() + ","
				// + registerUserBean.getCountry() + ","
				// + registerUserBean.getZipcode();
				// point =
				// addressLookupService.getGeoCodedAddress(addressPoint);
				// if (point != null) {
				// address.setLocation(point);
				// } else {
				// point = addressLookupService
				// .getGeoCodedAddress(registerUserBean.getZipcode());
				// if (point != null) {
				// address.setLocation(point);
				// }
				// }

				point = addressLookupService
						.getGeoCodedAddress(registerUserBean.getZipcode());
				if (point != null) {
					address.setLocation(point);
				}

				if (registerUserBean.getUsername() != null) {
					address.setEmail(registerUserBean.getUsername()
							.toLowerCase());
				}
				Set<Phone> phoneSet = new HashSet<>();
				Phone phone = new Phone();
				phone.setPrimary(true);
				phone.setNumber(registerUserBean.getContactNumber());
				phone.setType(PhoneType.MOBILE);
				phoneSet.add(phone);

				Phone phone1 = null;
				if (registerUserBean.getBranchTelNumber() != null) {
					phone1 = new Phone();
					phone1.setPrimary(false);
					phone1.setNumber(registerUserBean.getBranchTelNumber());
					phone1.setType(PhoneType.OFFICE);
					phoneSet.add(phone1);
				}

				address.setPhoneNumbers(phoneSet);
				user.addAddress(address);

				// save to mongo
				user = userDAO.save(user);

				// mongoid to mysql, now save userInfo object to mysql which is
				// primarlily for auth only

				// create user in mysql
				userInfo = createUserInfo(registerUserBean, username, password);

				// add phone number as well
				if (StringUtils.isNotEmpty(registerUserBean.getContactNumber())) {
					Long phonenumber = Long.parseLong(registerUserBean
							.getContactNumber());

					userInfo.setPhonenumber(phonenumber);
				}

				userInfo.setCreatedtime(new Date());

				userInfo.setLastmodifiedtime(new Date());

				// IMP: this is where there is link establised between mongo &
				// SQL for any JustInCase reason
				userInfo.setMongoUserId(user.getId());
				// special case, hence enclose with try, just in case something
				// fails, revert mongo save

				VerificationToken verificationToken = null;
				try {
					// save to sqldb

					if (isNotBlank(username)) {

						// create a verification token
						verificationToken = new VerificationToken(userInfo,
								VerificationTokenType.EmailRegistration, 0,
								"SYSTEM");
						// add to user the verification token
						userInfo.addVerificationToken(verificationToken);
					}

					if (isNotBlank(contactNumber)) {
						verificationToken = new VerificationToken(userInfo, "",
								VerificationTokenType.OTP, 30, "SYSTEM");
						userInfo.addVerificationToken(verificationToken);
					}

					if (registerUserBean.getStatus().equals("ACTIVE")) {
						if (isNotBlank(username)) {
							userInfo.setVerified(true);
							userInfo.setUserNameVerified(true);
						}
						if (isNotBlank(contactNumber)) {
							userInfo.setVerified(true);
							userInfo.setUserPhoneVerified(true);
						}
					}

					// save
					userInfo = userRepository.save(userInfo);

					logger.info("User Registered successfully");
					logger.debug("VerificationToken" + verificationToken);

				} catch (Exception e) {
					// save failed, revert mongo save as well
					userDAO.delete(user.getId());
					throw e;
				}

				// Sending credentials to user
				if (StringUtils.equalsIgnoreCase(
						registerUserBean.getUserType(),
						UserType.DEALER.toString())
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(),
								UserType.PROVIDER.toString())
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(),
								ServiceProviders.ARCHITECT.toString())
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(),
								ServiceProviders.INTERIORDESIGNER.toString())
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(),
								ServiceProviders.CIVILENGINEER.toString())
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(),
								UserType.BUSINESSASSOCIATE.toString())
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(),
								UserType.ENDUSER.toString())) {
					userCommunicationService.sendUserCredentials(
							registerUserBean.getUsername(),
							registerUserBean.getContactNumber(), password);
				}

				// Send registration email and sms to ServiceProvier
				if (StringUtils.equalsIgnoreCase(
						registerUserBean.getUserType(), "PLUMBER")
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(), "PAINTER")
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(), "CARPENTER")
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(),
								"VASTHUCONSULTANTS")
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(), "ELECTRICIANS")
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(),
								"BIMCONSULTANTS")
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(),
								"PROJECTCONSULTANT")
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(), "LANDSCAPER")
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(), "SURVEYOR")
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(), "WATERDIVINER")
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(), "VALUATOR")
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(), "BOREWELLER")
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(),
								"FLOORINGCONTRACTOR")
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(), "FABRICATOR")
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(), "GEOLOGIST")
						|| StringUtils.equalsIgnoreCase(
								registerUserBean.getUserType(),
								"ELECTRICALCONSULTANT")) {
					userCommunicationService.sendRegistrationInfo(user, " ");
				}

				// Dealer registration time creating a store
				setUserInfo(registerUserBean, user, 4);

				String userCreatedInfo = null;
				String balanceDepositingInfo = null;

				if (userInfo.getFirstname() != null
						&& userInfo.getLastname() != null) {
					userCreatedInfo = "User " + userInfo.getFirstname() + " "
							+ userInfo.getLastname()
							+ " is successfully created ";
				} else if (userInfo.getFirstname() != null) {
					userCreatedInfo = "User " + userInfo.getFirstname()
							+ " is successfully created ";
				}
				// Auditing user created info
				updateUserAudit(userInfo, "CREATED", userCreatedInfo);

				if (registerUserBean.getBalance() != 0.0) {
					balanceDepositingInfo = "Balance is modified with amount "
							+ registerUserBean.getBalance();
					// Auditing balance information
					updateUserAudit(userInfo, "BALANCE", balanceDepositingInfo);
				}

				ApiUser apiUser = new ApiUser(userInfo, user.getId());
				// set the actual role as above
				String mbgRoles = getRolesStr(user.getRoles());
				apiUser.setMbgRoles(mbgRoles);
				return apiUser;
			} catch (Exception e) {
				logger.error("Unexpected error in storing user details", e);
				throw new UserCreationException(
						"Unexpected error in storing user details", e);
			}
		} else {
			throw new UserCreationException(
					"Unable to create new user, it does not have minimum properties like username and password");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.auth.AuthUserService#authorize(com.sarvah.
	 * mbg.userprofile.auth.model.AuthorizationRequest)
	 */
	@Override
	public ApiUser authorize(AuthorizationRequest authorizationRequest)
			throws AuthorizationException {
		// logic to check request signing etc. so in the authorization request
		// you will have authtoken base64 encoded, request date base64 encode
		// and nonce base64 encoded
		// authtoken is actually a combination of user_id +":" +
		// Base64encode(sha256(actual_auth-token:requestDateString-nonce))

		if (authorizationRequest != null
				&& StringUtils.isNoneBlank(
						authorizationRequest.getAuthorizationToken(),
						authorizationRequest.getRequestDate(),
						authorizationRequest.getNonceToken())) {
			String combinedAuthToken = authorizationRequest
					.getAuthorizationToken();
			String requestDateStr = authorizationRequest.getRequestDate();
			String nonce = authorizationRequest.getNonceToken();

			// lets first break combinedauthtoken (original userid :
			// hashedtoken)
			String[] tokens = combinedAuthToken.split(":");
			if (tokens == null || tokens.length != 2)
				throw new AuthorizationException(
						"AuthToken is not in right format");

			String userId = tokens[0];
			String hashedToken = tokens[1];

			UserInfo user = userRepository.findByMongoUserId(userId);

			if (isAuthorized(user, hashedToken, requestDateStr, nonce)) {

				User mongoUser = userDAO.findById(userId);

				ApiUser apiUser = new ApiUser(user, userId);
				String mbgRoles = getRolesStr(mongoUser.getRoles());
				apiUser.setMbgRoles(mbgRoles);

				return apiUser;

			} else {
				throw new AuthorizationException(
						"User is not authorized to make request, please check your credentials and login");
			}

		}

		return null;

	}

	/**
	 * method for social login.
	 *
	 * @param provider
	 *            the provider
	 * @param accessToken
	 *            the access token
	 * @return the api user
	 * @throws AuthenticationException
	 *             the authentication exception
	 */
	@Override
	public ApiUser socialLogin(String provider, String accessToken)
			throws AuthenticationException {

		try {

			OAuth2ConnectionFactory<?> connectionFactory = (OAuth2ConnectionFactory<?>) connectionFactoryLocator
					.getConnectionFactory(provider);
			Connection<?> connection = connectionFactory
					.createConnection(new AccessGrant(accessToken));

			List<String> userIds = jpaUsersConnectionRepository
					.findUserIdsWithConnection(connection);
			if (userIds == null || userIds.isEmpty()) {
				// throw authentication
				throw new AuthenticationException(
						"Unable to associate user for the social connection");
			}
			Integer userId = Integer.valueOf(userIds.get(0));
			UserInfo userInfo = userRepository.findOne(userId);

			if (userInfo == null) {
				throw new AuthenticationException(
						"Unable to find the user for social login");
			}

			AuthorizationToken token = userInfo.getAuthorizationToken();
			boolean tokenChanged = false;

			if (token == null) {

				token = new AuthorizationToken(userInfo, "SYSTEM");
				tokenChanged = true;

			} else if (token.hasExpired()) {
				token.renewToken(UUID.randomUUID().toString());
				tokenChanged = true;
			}

			userInfo.setAuthorizationToken(token);
			userRepository.save(userInfo);
			logger.info("User Login to Buildonn successfully through social connection");

			if (tokenChanged) {
				// if token is new or token has changed then only persist it
				// back
				userInfo.setAuthorizationToken(token);
				userRepository.save(userInfo);
			}
			User mongoUser = userDAO.findById(userInfo.getMongoUserId());

			ApiUser apiUser = new ApiUser(userInfo, mongoUser.getId());
			// set the actual role as above
			String mbgRoles = getRolesStr(mongoUser.getRoles());
			apiUser.setMbgRoles(mbgRoles);
			// set the token
			apiUser.setToken(token.getToken());
			// set token expires
			apiUser.setExpires(token.getExpiryDate().getTime());
			return apiUser;
		} catch (Exception e) {
			throw new AuthenticationException(
					"Unable to authenticate the user using" + provider);
		}

	}

	/**
	 * Checks if is authorized.
	 *
	 * @param user
	 *            the user
	 * @param requestHashedToken
	 *            the request hashed token
	 * @param requestDateStr
	 *            the request date str
	 * @param nonce
	 *            the nonce
	 * @return true, if is authorized
	 */
	private boolean isAuthorized(UserInfo user, String requestHashedToken,
			String requestDateStr, String nonce) {

		if (user != null) {

			AuthorizationToken authorizationToken = user
					.getAuthorizationToken();
			String hashedAuthToken = constructHashedToken(
					authorizationToken.getToken(), requestDateStr, nonce);
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Comparing authtokens - requestAuthToken: {} authtoken : {}, HasAuthTokenExpired : {} ",
						requestHashedToken, hashedAuthToken,
						authorizationToken.hasExpired());
			}
			if (StringUtils.equals(requestHashedToken, hashedAuthToken)
					&& !authorizationToken.hasExpired()) {
				return true;
			}

		}

		return false;
	}

	/**
	 * Construct hashed token.
	 *
	 * @param authToken
	 *            the auth token
	 * @param requestDateStr
	 *            the request date str
	 * @param nonce
	 *            the nonce
	 * @return the string
	 */
	private String constructHashedToken(String authToken,
			String requestDateStr, String nonce) {
		String unencodedString = authToken + ":" + requestDateStr + "-" + nonce;
		String hashedValue = DigestUtils.sha256Hex(unencodedString);
		return Base64.getEncoder().encodeToString(
				hashedValue.getBytes(StandardCharsets.UTF_8));
	}

	/** The Constant symbols. */
	private static final char[] symbols;

	static {
		StringBuilder tmp = new StringBuilder();
		for (char ch = '0'; ch <= '9'; ++ch)
			tmp.append(ch);
		for (char ch = 'a'; ch <= 'z'; ++ch)
			tmp.append(ch);
		symbols = tmp.toString().toCharArray();
	}

	/**
	 * Gets the temp password.
	 *
	 * @param length
	 *            the length
	 * @return the temp password
	 */
	private String getTempPassword(int length) {
		Random rand = new Random(9845936902l);
		char[] buf = new char[length];
		for (int idx = 0; idx < buf.length; ++idx)
			buf[idx] = symbols[rand.nextInt(symbols.length)];
		return new String(buf);
	}

	public boolean validatePhoneNumber(String... phones) {
		boolean valid = false;
		for (String phone : phones) {

			if (phone != null && phone.matches(PHONE_PATTERN)) {
				valid = true;
			}
		}

		return valid;

	}

	private void updateUserAudit(UserInfo userInfo, String userAuditTypeVal,
			String note) {
		if (userInfo != null) {
			UserAudit userAudit = new UserAudit();
			// user id
			userAudit.setUserId(userInfo.getUserid());
			// user audit type
			UserAuditType userAuditType = userAuditTypeRepository
					.findByValue(userAuditTypeVal);
			userAudit.setUserAuditType(userAuditType);
			// note
			userAudit.setNote(note);
			// created by
			userAudit.setCreatedBy("Buildonn");
			// last modified by
			userAudit.setModifiedBy("Buildonn");
			// created date
			userAudit.setCreatedTime(new Date());
			// last modified date
			userAudit.setLastModifiedTime(new Date());

			userAuditRepository.save(userAudit);
		}
	}
}
