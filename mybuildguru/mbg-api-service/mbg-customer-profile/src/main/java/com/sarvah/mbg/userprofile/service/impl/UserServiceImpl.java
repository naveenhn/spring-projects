/**
 * 
 */
package com.sarvah.mbg.userprofile.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jms.JMSException;
import javax.swing.ImageIcon;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.commons.address.AddressLookupService;
import com.sarvah.mbg.commons.communication.UserCommException;
import com.sarvah.mbg.commons.communication.UserCommunicationService;
import com.sarvah.mbg.commons.storage.AzureFileStorage;
import com.sarvah.mbg.commons.storage.FileStorage;
import com.sarvah.mbg.domain.common.asset.File;
import com.sarvah.mbg.domain.common.asset.FileType;
import com.sarvah.mbg.domain.common.asset.Image;
import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.QuoteStatus;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.common.contact.Phone;
import com.sarvah.mbg.domain.mongo.common.contact.PhoneType;
import com.sarvah.mbg.domain.mongo.marketing.Promotion;
import com.sarvah.mbg.domain.mongo.marketing.PromotionType;
import com.sarvah.mbg.domain.mongo.review.Comment;
import com.sarvah.mbg.domain.mongo.store.Shipping;
import com.sarvah.mbg.domain.mongo.store.Store;
import com.sarvah.mbg.domain.mongo.store.StoreProductPricing;
import com.sarvah.mbg.domain.mongo.userprofile.QuoteRequest;
import com.sarvah.mbg.domain.mongo.userprofile.QuoteRequestProduct;
import com.sarvah.mbg.domain.mongo.userprofile.RecentlyViewed;
import com.sarvah.mbg.domain.mongo.userprofile.SellerQuoteProductPricing;
import com.sarvah.mbg.domain.mongo.userprofile.ServiceProviders;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.UserProjectProfile;
import com.sarvah.mbg.domain.mongo.userprofile.UserRelatedDocuments;
import com.sarvah.mbg.domain.mongo.userprofile.UserStatus;
import com.sarvah.mbg.domain.mongo.userprofile.UserType;
import com.sarvah.mbg.domain.mongo.userprofile.WishList;
import com.sarvah.mbg.domain.mongo.userprofile.role.Privilege;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.domain.mongo.userprofile.role.UserPackage;
import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.sms.ItemInfoForCommunication;
import com.sarvah.mbg.domain.user.UserInfo;
import com.sarvah.mbg.domain.user.audit.UserAudit;
import com.sarvah.mbg.domain.user.audit.UserAuditType;
import com.sarvah.mbg.privilege.service.PrivilegeService;
import com.sarvah.mbg.recentlyviewed.dao.mongo.RecentlyViewedDAO;
import com.sarvah.mbg.store.dao.mongo.StoreDAO;
import com.sarvah.mbg.userprofile.auth.model.ApiUser;
import com.sarvah.mbg.userprofile.dao.jpa.UserAuditRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserAuditTypeRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserOrderDetailRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserOrderRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserRepo;
import com.sarvah.mbg.userprofile.dao.jpa.UserRepository;
import com.sarvah.mbg.userprofile.dao.mongo.BrandDAO;
import com.sarvah.mbg.userprofile.dao.mongo.OrderManageProductDAO;
import com.sarvah.mbg.userprofile.dao.mongo.PrivilegeDAO;
import com.sarvah.mbg.userprofile.dao.mongo.ProductPromotionDAO;
import com.sarvah.mbg.userprofile.dao.mongo.PromotionBannerDAO;
import com.sarvah.mbg.userprofile.dao.mongo.QuoteRequestDAO;
import com.sarvah.mbg.userprofile.dao.mongo.QuoteRequestProductDAO;
import com.sarvah.mbg.userprofile.dao.mongo.RoleDAO;
import com.sarvah.mbg.userprofile.dao.mongo.SellerQuoteProductPricingDAO;
import com.sarvah.mbg.userprofile.dao.mongo.StoreProductPricingDAO;
import com.sarvah.mbg.userprofile.dao.mongo.StoreSubcategoryDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserBidDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserCommentDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserProjectDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserProjectProfileDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserRelatedDocumentsDAO;
import com.sarvah.mbg.userprofile.dao.mongo.WishListDAO;
import com.sarvah.mbg.userprofile.dao.role.mongo.UserPackageDAO;
import com.sarvah.mbg.userprofile.response.BuyableUsersCount;
import com.sarvah.mbg.userprofile.response.ContactInfoResponse;
import com.sarvah.mbg.userprofile.response.ManageDocs;
import com.sarvah.mbg.userprofile.response.ManageRolesResponse;
import com.sarvah.mbg.userprofile.response.ManageRolesView;
import com.sarvah.mbg.userprofile.response.ManualPagination;
import com.sarvah.mbg.userprofile.response.ProfileCompletenessResponse;
import com.sarvah.mbg.userprofile.response.QuoteCountResponse;
import com.sarvah.mbg.userprofile.response.QuoteProductRequestParam;
import com.sarvah.mbg.userprofile.response.RecentlyViewedDetails;
import com.sarvah.mbg.userprofile.response.RecentlyViewedSummary;
import com.sarvah.mbg.userprofile.response.SellerQuoteProductPricingRequestParam;
import com.sarvah.mbg.userprofile.response.SingleUserResponse;
import com.sarvah.mbg.userprofile.response.UserBalanceInfoResponse;
import com.sarvah.mbg.userprofile.response.UserSummaryView;
import com.sarvah.mbg.userprofile.response.UserSummaryViewDetails;
import com.sarvah.mbg.userprofile.response.UserSummaryViewResponse;
import com.sarvah.mbg.userprofile.response.UsersStatusCount;
import com.sarvah.mbg.userprofile.service.UserService;

/**
 * @author naveen
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	private static final String PHONE_PATTERN = "^\\+{0,1}[0-9]{0,2}\\-{0,1}[0-9]{10}$";

	@Autowired
	private UserValidation userValidation;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private RecentlyViewedDAO recentlyViewedDAO;

	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private UserPackageDAO userPackageDAO;

	@Autowired
	private WishListDAO wishListDAO;

	@Autowired
	private PrivilegeDAO privilegeDAO;

	@Autowired
	private StoreDAO storeDAO;

	@Autowired
	private UserBidDAO userBidDAO;

	@Autowired
	private PromotionBannerDAO bannerDAO;

	@Autowired
	private ProductPromotionDAO promotionDAO;

	@Autowired
	private UserCommunicationService userCommunicationService;

	@Autowired
	private PrivilegeService privilegeService;

	@Autowired
	private OrderManageProductDAO productDAO;

	@Autowired
	private StoreSubcategoryDAO subCategoryDAO;

	@Autowired
	private BrandDAO brandDAO;

	@Autowired
	private UserCommentDAO userCommentDAO;

	@Autowired
	private UserProjectProfileDAO userProjectProfileDAO;

	@Autowired
	private UserProjectDAO userProjectDAO;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserAuditRepository userAuditRepository;

	@Autowired
	private UserAuditTypeRepository userAuditTypeRepository;

	@Autowired
	private UserOrderRepository userOrderRepository;

	@Autowired
	private UserOrderDetailRepository userOrderDetailRepository;

	@Autowired
	private FileStorage filestorage;

	@Autowired
	private UserRelatedDocumentsDAO userRelatedDocumentsDAO;

	@Autowired
	private AddressLookupService addressLookupService;

	@Autowired
	private StoreProductPricingDAO storeProductPricingDAO;

	@Autowired
	private SellerQuoteProductPricingDAO sellerQuoteProductPricingDAO;

	@Autowired
	private QuoteRequestProductDAO quoteRequestProductDAO;

	@Autowired
	private QuoteRequestDAO quoteRequestDAO;

	@Autowired
	MongoOperations operations;

	@Autowired
	MongoTemplate mongoTemplate;

	/**
	 * Method for search user operation Search is performed based on
	 * username,firstname,lastname,email,phonenumber,usertype,rolename and
	 * fullName. validation and pagination is implemented.
	 * 
	 * @param userSearchParam
	 * @return List<User>
	 * @throws MBGAppException
	 */
	@Override
	public List<User> searchUser(String userName, String firstName,
			String lastName, String email, String phoneNumber, String roleName,
			String status, String fullName, String skillSetName,
			String operatingCityName, String area, Point location, int page,
			int size, Sort sort) throws Exception {
		logger.info("Fetching User based on filter/without filter criteria");
		List<User> users = new ArrayList<User>();
		/*
		 * This condition is for checking all the fields are blank If all the
		 * fields are blank it will return all the users in DB.
		 */
		if (StringUtils.isBlank(userName) && StringUtils.isBlank(firstName)
				&& StringUtils.isBlank(lastName) && StringUtils.isBlank(email)
				&& StringUtils.isBlank(phoneNumber)
				&& StringUtils.isBlank(roleName) && StringUtils.isBlank(status)
				&& StringUtils.isBlank(fullName) && StringUtils.isBlank(area)
				&& location == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Filter criteria is null so fetching all Buildonn users");
			}
			users.addAll(userDAO.findAll(new PageRequest(page, size, sort))
					.getContent());
			return new ArrayList<User>(users);
		}
		/*
		 * if any of the fields are specified/given, result will be displayed
		 * based on the user request.
		 */
		else {
			// searching user based on location.
			if (location != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetching all the users in this perticular location");
				}
				Page<User> pageable = userDAO.findByAddresses_Location(
						location, new PageRequest(page, size, sort));
				users.addAll(pageable.getContent());
			}

			// searching user based on fullName and roleName
			if (StringUtils.isNotBlank(fullName)
					&& StringUtils.isNotBlank(roleName)) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetching all the user based on fullName and roleName");
				}
				Page<User> pageable = userDAO.findByFullNameLikeAndRoles_Name(
						fullName, roleName, new PageRequest(page, size, sort));
				users.addAll(pageable.getContent());
			}
			// searching user based on fullName.
			else if (StringUtils.isNotBlank(fullName)) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetching the users based on fullName");
				}
				users.addAll(userDAO.findByFullName(fullName, new PageRequest(
						page, size, sort)));
			}
			// searching service providers based on role name,status and
			// operating city names
			if (StringUtils.isNotBlank(roleName)
					&& StringUtils.isNotBlank(status)
					&& StringUtils.isNotBlank(operatingCityName)
					&& StringUtils.isBlank(skillSetName)) {
				Set<String> intrestedCities = new HashSet<>();

				if (logger.isDebugEnabled()) {
					logger.debug("fetching all the users based on roleName, status and intrestedCityName");
				}
				Page<User> pageable = null;

				if (operatingCityName.contains("World")) {
					intrestedCities.add(operatingCityName);
					pageable = userDAO
							.findByRoles_NameAndStatusAndOperatingCitiesIn(
									roleName, status, intrestedCities,
									new PageRequest(page, size, sort));
					users.addAll(pageable.getContent());
				} else if (operatingCityName.contains("India")) {
					intrestedCities.add(operatingCityName);
					pageable = userDAO
							.findByRoles_NameAndStatusAndOperatingCitiesIn(
									roleName, status, intrestedCities,
									new PageRequest(page, size, sort));
					users.addAll(pageable.getContent());
				} else {
					Set<User> usersSet = new HashSet<User>();

					usersSet.addAll(userDAO
							.findByRoles_NameAndStatusAndOperatingCitiesContains(
									roleName, status, "World"));

					usersSet.addAll(userDAO
							.findByRoles_NameAndStatusAndOperatingCitiesContains(
									roleName, status, "India"));

					String[] intrestedCityArray = operatingCityName.split(",");

					for (String intrestedCity : intrestedCityArray) {
						intrestedCities.add(intrestedCity);
					}

					usersSet.addAll(userDAO
							.findByRoles_NameAndStatusAndOperatingCitiesIn(
									roleName, status, intrestedCities));

					Set<String> userIds = new HashSet<>();

					for (User user : usersSet) {
						if (user != null) {
							userIds.add(user.getId());
						}
					}

					pageable = userDAO.findByIdIn(userIds, new PageRequest(
							page, size, sort));
					users.addAll(pageable.getContent());
				}

			}
			// searching user based on roleName, status and skillset
			else if (StringUtils.isNotBlank(roleName)
					&& StringUtils.isNotBlank(status)
					&& StringUtils.isNotBlank(skillSetName)
					&& StringUtils.isBlank(operatingCityName)) {
				Set<String> skillsets = new HashSet<>();
				String[] skillSetArray = skillSetName.split(",");

				for (String skillSet : skillSetArray) {
					skillsets.add(skillSet);
				}

				if (logger.isDebugEnabled()) {
					logger.debug("fetching all the users based on roleName, status and skillSet");
				}
				Page<User> pageable = userDAO
						.findByRoles_NameAndStatusAndOtherSkillSetsIn(roleName,
								status, skillsets, new PageRequest(page, size,
										sort));
				users.addAll(pageable.getContent());
			}
			// searching user based on roleName and status
			else if (StringUtils.isNotBlank(roleName)
					&& StringUtils.isNotBlank(status)
					&& StringUtils.isBlank(skillSetName)
					&& StringUtils.isBlank(operatingCityName)) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetching all the users based on roleName and status");
				}
				Page<User> pageable = userDAO.findByRoles_NameAndStatus(
						roleName, status, new PageRequest(page, size, sort));
				users.addAll(pageable.getContent());
			}

			// searching user based on roleName
			else if (StringUtils.isNotBlank(roleName)) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetching all the users based on roleName");
				}
				Page<User> pageable = userDAO.findByRoles_NameIgnoreCase(
						roleName, new PageRequest(page, size, sort));
				users.addAll(pageable.getContent());
			}

			// searching user based on firstName and lastName
			if (StringUtils.isNotBlank(firstName)
					&& StringUtils.isNotBlank(lastName)) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetching the user based on firstName and lastName");
				}
				Page<User> pageable = userDAO.findByFirstNameAndLastName(
						firstName, lastName, new PageRequest(0, 10));
				users.addAll(pageable.getContent());
			}
			// searching user based on firstName
			else if (StringUtils.isNotBlank(firstName)) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetching the user based on firstName");
				}
				Page<User> pageable = userDAO.findByFirstNameLike(firstName,
						new PageRequest(0, 10));
				pageable.getContent();
				pageable.getSize();
				pageable.getSort();
				users.addAll(pageable.getContent());
			}
			// searching user based on lastName
			else if (StringUtils.isNotBlank(lastName)) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetching all the user based on lastName");
				}
				Page<User> pageable = userDAO.findByLastNameLike(lastName,
						new PageRequest(0, 10));
				pageable.getContent();
				pageable.getSize();
				pageable.getSort();
				users.addAll(pageable.getContent());
			}
			// searching user based on phoneNumber.
			if (StringUtils.isNotBlank(phoneNumber)) {

				if (logger.isDebugEnabled()) {
					logger.debug("fetching user based on phoneNumber");
				}
				if (userValidation.validatePhoneNumber(phoneNumber)) {
					users.addAll(userDAO.findByAddresses_PhoneNumbers_Number(
							phoneNumber, new PageRequest(page, size, sort)));
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("error! Invalid phone number");
					}
				}
			}
			// searching user based on email.
			if (StringUtils.isNotBlank(email)) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetching user based on email");
				}
				if (userValidation.validateEmail(email)) {
					users.addAll(userDAO.findByAddresses_Email(email,
							new PageRequest(page, size, sort)));
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("error! invalid email format");
					}
				}
			}
			// searching user based on userName(email).
			if (StringUtils.isNotBlank(userName)) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetching user based on userName");
				}
				if (userValidation.validateEmail(userName)) {
					users.addAll(userDAO.findByUsername(userName.toLowerCase(),
							new PageRequest(page, size, sort)));
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("error! invalid username format");
					}
				}
			}

			// searching user based on area
			if (StringUtils.isNotBlank(area)) {
				users.addAll(userDAO
						.findByAddresses_AddressLine1ContainsAllIgnoreCase(
								area, new PageRequest(page, size, sort)));
			}

			List<User> usersList = new ArrayList<>();
			for (User user : users) {
				if (user != null) {
					Set<Role> roles = user.getRoles();
					for (Role role : roles) {
						if (role != null && role.getUserPackage() != null) {
							UserPackage userPackage = role.getUserPackage();
							if (userPackage != null
									&& userPackage.getName() != null) {
								user.setTitle(userPackage.getName());
							}
						}
					}
					usersList.add(user);
				}
			}
			logger.info("Fetching User based on filter/without filter criteria! Success");
			return usersList;
		}
	}

	@Override
	public Long getMBGUserCount() {
		return userDAO.count();
	}

	@Override
	public Long getUserCountByRole(String roleName) {
		return userDAO.countByRoles_NameIgnoreCase(roleName);
	}

	@Override
	public Long getUserCountByRoleAndStatus(String roleName, String status) {
		return userDAO.countByRoles_NameAndStatus(roleName, status);
	}

	@Override
	public Long getUserCountByArea(String area) {
		return userDAO.countByAddresses_AddressLine1ContainsAllIgnoreCase(area);
	}

	/**
	 * method for super admin manage providers.
	 * 
	 * @return
	 */
	@Override
	public UserSummaryViewResponse getSuperAdminManageProviders(String pack,
			String status, String searchValue, String area, int page, int size,
			Sort sort) {
		logger.info("Fetching Providers based on filter/withOutFilter criteria");
		List<UserSummaryView> manageProviderViewsList = new ArrayList<>();
		UserSummaryViewResponse userSummaryViewResponse = new UserSummaryViewResponse();
		Page<User> pageable = null;
		List<User> usersList = new ArrayList<>();
		// searching all providers
		if (StringUtils.isBlank(pack) && StringUtils.isBlank(status)
				&& StringUtils.isBlank(searchValue)
				&& StringUtils.isBlank(area)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Filter criteria is null so fetching all Providers");
			}
			pageable = userDAO.findByRoles_NameAllIgnoreCase("PROVIDER",
					new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering providers based on searchValue(firstName,LastName,fullName
		// and userName), package and status
		else if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isNotBlank(pack)
				&& StringUtils.isNotBlank(status)) {
			String packs[] = pack.split(",");
			Set<ObjectId> userPackageObjectIds = new HashSet<ObjectId>();
			for (int i = 0; i < packs.length; i++) {
				UserPackage userPackage = userPackageDAO.findByName(packs[i]);
				userPackageObjectIds.add(new ObjectId(userPackage.getId()));
			}
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching Providers based on - SearchValue : {} Packages : {} Status : {}",
						searchValue, pack, status);
			}
			pageable = userDAO
					.findByRoles_NameAndRoles_UserPackageInAndStatusOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"PROVIDER", userPackageObjectIds,
							UserStatus.valueOf(status), searchValue,
							searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering providers based on package and status
		else if (StringUtils.isNotBlank(pack) && StringUtils.isNotBlank(status)
				&& StringUtils.isBlank(searchValue)) {
			Set<UserPackage> userPackages = new HashSet<>();
			String packs[] = pack.split(",");
			for (int i = 0; i < packs.length; i++) {
				UserPackage userPackage = userPackageDAO.findByName(packs[i]);
				userPackages.add(userPackage);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching Providers based on - Packages : {} Status : {}",
						pack, status);
			}
			pageable = userDAO.findByRoles_NameAndRoles_UserPackageInAndStatus(
					"PROVIDER", userPackages, UserStatus.valueOf(status),
					new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering providers based on searchValue(firstName,LastName,fullName
		// and userName) and status
		else if (StringUtils.isNotBlank(status)
				&& StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(pack)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching Providers based on - SearchValue : {} Status : {}",
						searchValue, status);
			}
			pageable = userDAO
					.findByRoles_NameAndStatusAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"PROVIDER", UserStatus.valueOf(status),
							searchValue, searchValue, searchValue, searchValue,
							searchValue, searchValue, new PageRequest(page,
									size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering providers based on searchValue(firstName,LastName,fullName
		// and userName) and package
		else if (StringUtils.isNotBlank(pack)
				&& StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(status)) {
			Set<ObjectId> userPackageObjectIds = new HashSet<ObjectId>();
			String packs[] = pack.split(",");
			for (int i = 0; i < packs.length; i++) {
				UserPackage userPackage = userPackageDAO.findByName(packs[i]);
				userPackageObjectIds.add(new ObjectId(userPackage.getId()));
			}
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching Providers based on - SearchValue : {} Packages : {}",
						searchValue, pack);
			}
			pageable = userDAO
					.findByRoles_NameAndRoles_UserPackageInOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"PROVIDER", userPackageObjectIds, searchValue,
							searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering providers based on package.
		else if (StringUtils.isNotBlank(pack) && StringUtils.isBlank(status)
				&& StringUtils.isBlank(searchValue)) {
			Set<UserPackage> userPackages = new HashSet<>();
			String packs[] = pack.split(",");
			for (int i = 0; i < packs.length; i++) {
				UserPackage userPackage = userPackageDAO.findByName(packs[i]);
				userPackages.add(userPackage);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching Providers based on - Packages : {} Status : {}",
						pack, status);
			}
			pageable = userDAO
					.findByRoles_NameAndRoles_UserPackageIn("PROVIDER",
							userPackages, new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering providers based on status.
		else if (StringUtils.isNotBlank(status) && StringUtils.isBlank(pack)
				&& StringUtils.isBlank(searchValue)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Fetching Providers based on - Status : {}",
						status);
			}
			pageable = userDAO.findByRoles_NameAndStatus("PROVIDER", UserStatus
					.valueOf(status), new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering providers based on searchValue(firstName,LastName,fullName
		// and userName).
		else if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(pack) && StringUtils.isBlank(status)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Fetching Providers based on - SearchValue : {}",
						searchValue);
			}
			pageable = userDAO
					.findByRoles_NameAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberOrContactNameLikeAllIgnoreCase(
							"PROVIDER", searchValue, searchValue, searchValue,
							searchValue, searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));

			usersList.addAll(pageable.getContent());
		}
		// searching provider based on area
		else if (StringUtils.isNotBlank(area)
				&& StringUtils.isBlank(searchValue)
				&& StringUtils.isBlank(status) && StringUtils.isBlank(pack)) {
			pageable = userDAO
					.findByRoles_NameAndAddresses_AddressLine1ContainsAllIgnoreCase(
							"PROVIDER", area, new PageRequest(page, size, sort));

			usersList.addAll(pageable.getContent());
		}
		for (User user : usersList) {
			UserInfo userInfo = userRepository.findByMongoUserId(user.getId());
			if (user != null && userInfo != null) {
				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					if (role.getName().equalsIgnoreCase("Provider")) {
						if (logger.isDebugEnabled()) {
							logger.debug("ProviderName : {} ProviderId : {}",
									user.getFullName(), user.getId());
						}
						UserSummaryView userSummaryView = new UserSummaryView();
						if (role.getUserPackage() != null) {
							userSummaryView.setPackageName(role
									.getUserPackage().getName());
						}
						ProductBrand brand = brandDAO.findByProvider(user);
						List<String> brandNameList = new ArrayList<>();
						if (brand != null) {
							brandNameList.add(brand.getName());
						}
						// User Id
						userSummaryView.setUserId(user.getId());
						// Email
						if (userInfo.getUsername() != null) {
							userSummaryView.setEmailId(userInfo.getUsername());
						}
						// PhoneNumber
						if (userInfo.getPhonenumber() != null) {
							userSummaryView.setPhoneNumber(userInfo
									.getPhonenumber().toString());
						}
						// verified
						if (userInfo.isVerified() == false
								&& userInfo.getUsername() == null) {
							userSummaryView.setVerified(true);
						} else {
							userSummaryView.setVerified(false);
						}
						// Brand Name
						userSummaryView.setBrandName(brandNameList);
						// Provider contact person Name
						if (user.getContactName() != null) {
							userSummaryView.setName(user.getContactName());
						}
						// Provider Created Date
						userSummaryView.setCreatedDate(user.getCreatedDate());
						// Pan Number
						if (user.getPanNumber() != null) {
							userSummaryView.setPanNumber(user.getPanNumber());
						}
						// Status
						userSummaryView.setStatus(user.getStatus());
						manageProviderViewsList.add(userSummaryView);
					}
				}
			}
		}
		userSummaryViewResponse.setUserSummaryView(manageProviderViewsList);
		// setting page attributes
		userSummaryViewResponse.setTotalPages(pageable.getTotalPages());
		userSummaryViewResponse.setTotalElements(pageable.getTotalElements());
		userSummaryViewResponse.setNumber(pageable.getNumber());
		userSummaryViewResponse.setSize(pageable.getSize());
		logger.info("Fetching Providers based on filter/withOutFilter criteria! Success");
		return userSummaryViewResponse;
	}

	/**
	 * method for super admin manage dealers.
	 * 
	 * @return
	 */
	@Override
	public UserSummaryViewResponse getSuperAdminManageDealers(String pack,
			String status, String searchValue, String subCategory, String area,
			int page, int size, Sort sort) {
		logger.info("Fetching Dealers based on filter/withOutFilter criteria");
		List<UserSummaryView> manageDealerViewList = new ArrayList<>();
		UserSummaryViewResponse userSummaryViewResponse = new UserSummaryViewResponse();
		Page<User> pageable = null;
		List<User> usersList = new ArrayList<>();
		// searching all dealers.
		if (StringUtils.isBlank(pack) && StringUtils.isBlank(status)
				&& StringUtils.isBlank(searchValue)
				&& StringUtils.isBlank(area)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Filter criteria is null so fetching all Dealers");
			}
			pageable = userDAO.findByRoles_NameAllIgnoreCase("DEALER",
					new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering dealers based on searchValue,package and status.
		else if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isNotBlank(pack)
				&& StringUtils.isNotBlank(status)) {
			String packs[] = pack.split(",");
			Set<ObjectId> userPackageObjectIds = new HashSet<ObjectId>();

			for (int i = 0; i < packs.length; i++) {
				UserPackage userPackage = userPackageDAO.findByName(packs[i]);
				userPackageObjectIds.add(new ObjectId(userPackage.getId()));
			}
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching Dealers based on - SearchValue : {} Packages : {} Status : {}",
						searchValue, pack, status);
			}
			pageable = userDAO
					.findByRoles_NameAndRoles_UserPackageInAndStatusOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"DEALER", userPackageObjectIds,
							UserStatus.valueOf(status), searchValue,
							searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering dealers based on package and status.
		else if (StringUtils.isNotBlank(pack) && StringUtils.isNotBlank(status)
				&& StringUtils.isBlank(searchValue)) {
			Set<UserPackage> userPackages = new HashSet<>();
			String packs[] = pack.split(",");
			for (int i = 0; i < packs.length; i++) {
				UserPackage userPackage = userPackageDAO.findByName(packs[i]);
				userPackages.add(userPackage);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching Dealers based on - Packages : {} Status : {}",
						pack, status);
			}

			pageable = userDAO.findByRoles_NameAndRoles_UserPackageInAndStatus(
					"DEALER", userPackages, UserStatus.valueOf(status),
					new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering dealers based on searchValue and status.
		else if (StringUtils.isNotBlank(status)
				&& StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(pack)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching Dealers based on - SearchValue : {} Status : {}",
						searchValue, status);
			}
			pageable = userDAO
					.findByRoles_NameAndStatusAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"DEALER", UserStatus.valueOf(status), searchValue,
							searchValue, searchValue, searchValue, searchValue,
							searchValue, new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering dealers based on searchValue and package.
		else if (StringUtils.isNotBlank(pack)
				&& StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(status)) {
			Set<ObjectId> userPackageObjectIds = new HashSet<ObjectId>();
			String packs[] = pack.split(",");
			for (int i = 0; i < packs.length; i++) {

				UserPackage userPackage = userPackageDAO.findByName(packs[i]);
				userPackageObjectIds.add(new ObjectId(userPackage.getId()));
			}
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching Dealers based on - SearchValue : {} Packages : {}",
						searchValue, pack);
			}
			pageable = userDAO
					.findByRoles_NameAndRoles_UserPackageInOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"DEALER", userPackageObjectIds, searchValue,
							searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering dealers based on package.
		else if (StringUtils.isNotBlank(pack) && StringUtils.isBlank(status)
				&& StringUtils.isBlank(searchValue)) {
			Set<UserPackage> userPackages = new HashSet<>();
			String packs[] = pack.split(",");
			for (int i = 0; i < packs.length; i++) {
				UserPackage userPackage = userPackageDAO.findByName(packs[i]);
				userPackages.add(userPackage);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Fetching Dealers based on - Packages : {}", pack);
			}
			pageable = userDAO.findByRoles_NameAndRoles_UserPackageIn("DEALER",
					userPackages, new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering dealers based on status.
		else if (StringUtils.isNotBlank(status) && StringUtils.isBlank(pack)
				&& StringUtils.isBlank(searchValue)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Fetching Dealers based on - Status : {}", status);
			}
			pageable = userDAO.findByRoles_NameAndStatus("DEALER", UserStatus
					.valueOf(status), new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering dealers based on searchValue.
		else if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(pack) && StringUtils.isBlank(status)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Fetching Dealers based on - SearchValue : {}",
						searchValue);
			}
			pageable = userDAO
					.findByRoles_NameAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberOrContactNameLikeAllIgnoreCase(
							"DEALER", searchValue, searchValue, searchValue,
							searchValue, searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching dealer based on area
		else if (StringUtils.isNotBlank(area)
				&& StringUtils.isBlank(searchValue)
				&& StringUtils.isBlank(status) && StringUtils.isBlank(pack)) {
			pageable = userDAO
					.findByRoles_NameAndAddresses_AddressLine1ContainsAllIgnoreCase(
							"DEALER", area, new PageRequest(page, size, sort));

			usersList.addAll(pageable.getContent());
		}
		for (User user : usersList) {
			UserInfo userInfo = userRepository.findByMongoUserId(user.getId());
			if (userInfo != null) {

				if (logger.isDebugEnabled()) {
					logger.debug("DealerName : {} DealerId : {}",
							user.getFullName(), user.getId());
				}

				UserSummaryView manageDealerView = new UserSummaryView();
				// User id
				manageDealerView.setUserId(user.getId());
				// DealerName
				if (user.getContactName() != null) {
					manageDealerView.setName(user.getContactName());
				}
				// createdDate
				if (user.getCreatedDate() != null) {
					manageDealerView.setCreatedDate(user.getCreatedDate());
				}
				// Email
				if (userInfo.getUsername() != null) {
					manageDealerView.setEmailId(userInfo.getUsername());
				}
				// PhoneNumber
				if (userInfo.getPhonenumber() != null) {
					manageDealerView.setPhoneNumber(userInfo.getPhonenumber()
							.toString());
				}

				// verified
				if (userInfo.isVerified() == false
						&& userInfo.getUsername() == null) {
					manageDealerView.setVerified(true);
				} else {
					manageDealerView.setVerified(false);
				}

				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					// package
					if (role.getUserPackage() != null) {
						manageDealerView.setPackageName(role.getUserPackage()
								.getName());
					}
				}

				// status
				if (user.getStatus() != null) {
					manageDealerView.setStatus(user.getStatus());
				}
				// TIN number
				if (user.getVatNumber() != null) {
					manageDealerView.setVatNumber(user.getVatNumber());
				}
				// Pan number
				if (user.getPanNumber() != null) {
					manageDealerView.setPanNumber(user.getPanNumber());
				}

				// total shops
				manageDealerView.setTotalShops(user.getAddresses().size());

				// StoreList
				List<String> shopNameList = new ArrayList<>();

				shopNameList.add(user.getFirstName());

				manageDealerView.setShopname(shopNameList);

				manageDealerViewList.add(manageDealerView);
			}
		}
		userSummaryViewResponse.setUserSummaryView(manageDealerViewList);
		// setting page attributes.
		userSummaryViewResponse.setTotalPages(pageable.getTotalPages());
		userSummaryViewResponse.setTotalElements(pageable.getTotalElements());
		userSummaryViewResponse.setNumber(pageable.getNumber());
		userSummaryViewResponse.setSize(pageable.getSize());
		logger.info("Fetching Dealers based on filter/withOutFilter criteria! Success");
		return userSummaryViewResponse;
	}

	@Override
	public UserSummaryViewResponse getSellersBySubcategoryAndBrand(
			String subCategory, String brand, String productIds, int page,
			int size, Sort sort) throws Exception {
		List<UserSummaryView> manageDealerViewList = new ArrayList<>();
		UserSummaryViewResponse userSummaryViewResponse = new UserSummaryViewResponse();
		Set<SubCategory> subCategories = null;
		Set<SubCategory> subCategoriesFinal = new HashSet<>();
		Set<ProductBrand> brands = new HashSet<>();
		List<User> dealers = new ArrayList<>();

		if (StringUtils.isNotBlank(productIds)) {
			// products
			String[] productIdAry = productIds.split(",");
			Set<String> dealerIdSet = new HashSet<>();
			Set<User> dealerSet = new HashSet<>();
			for (String productId : productIdAry) {
				Set<Store> stores = null;
				Product product = productDAO.findOne(productId);
				List<Product> prodList = new ArrayList<>();
				if (product != null) {
					prodList.add(product);
				}

				stores = storeDAO.findByProductsIn(prodList);

				if (stores != null) {
					for (Store store : stores) {
						if (store.getUser() != null) {
							dealerIdSet.add(store.getUser().getId());
						}
					}
				}
			}
			for (String dealerId : dealerIdSet) {
				User dealer = userDAO.findById(dealerId);
				if (dealer != null) {
					dealerSet.add(dealer);
				}
			}
			dealers.addAll(dealerSet);
		} else if (StringUtils.isNotBlank(subCategory)
				&& StringUtils.isNotBlank(brand)) {

			// subCategories
			String[] subCategoryNames = subCategory.split(",");
			for (String subCategoryName : subCategoryNames) {
				subCategories = subCategoryDAO.findByName(subCategoryName);
				if (subCategories != null) {
					subCategoriesFinal.addAll(subCategories);
				}
			}

			// brands
			String[] brandsIds = brand.split(",");
			for (String brandId : brandsIds) {
				ProductBrand productBrand = brandDAO.findOne(brandId);
				if (productBrand != null) {
					brands.add(productBrand);
				}
			}

			Set<Store> stores = storeDAO.findBySubcategoriesInAndBrandsIn(
					subCategoriesFinal, brands);
			for (Store store : stores) {
				dealers.add(store.getUser());
			}
		} else if (StringUtils.isNotBlank(subCategory)) {

			// subCategories
			String[] subCategoryNames = subCategory.split(",");
			for (String subCategoryName : subCategoryNames) {
				subCategories = subCategoryDAO.findByName(subCategoryName);
				if (subCategories != null) {
					subCategoriesFinal.addAll(subCategories);
				}
			}

			Set<Store> stores = storeDAO
					.findBySubcategoriesIn(subCategoriesFinal);

			for (Store store : stores) {
				dealers.add(store.getUser());
			}
		} else if (StringUtils.isNotBlank(brand)) {
			// brands
			String[] brandsIds = brand.split(",");
			for (String brandId : brandsIds) {
				ProductBrand productBrand = brandDAO.findOne(brandId);
				if (productBrand != null) {
					brands.add(productBrand);
				}
			}

			Set<Store> stores = storeDAO.findByBrandsIn(brands);

			for (Store store : stores) {
				dealers.add(store.getUser());
			}
		}

		ManualPagination mp = new ManualPagination();

		int totalElements = dealers.size();

		int totalPages = totalElements / size;

		if (dealers != null && dealers.size() != 0) {

			List<User> dealers1 = new ArrayList<>();

			dealers1.addAll(dealers.subList(mp.getPageElementStartNumber(page,
					size), mp.getPageElementEndNumber(page, size,
					totalElements, totalPages)));

			for (User dealer : dealers1) {

				if (dealer != null) {
					UserInfo userInfo = userRepository.findByMongoUserId(dealer
							.getId());
					if (userInfo != null) {
						Set<Role> roles = dealer.getRoles();
						if (roles != null) {
							for (Role role : roles) {
								if (role != null) {
									String roleName = role.getName();
									if (roleName.equalsIgnoreCase("DEALER")) {

										if (logger.isDebugEnabled()) {
											logger.debug(
													"DealerName : {} DealerId : {}",
													dealer.getFullName(),
													dealer.getId());
										}
										UserSummaryView manageDealerView = new UserSummaryView();
										manageDealerView.setUserId(dealer
												.getId());
										// DealerName
										if (dealer.getContactName() != null) {
											manageDealerView.setName(dealer
													.getContactName());
										}
										// createdDate
										if (dealer.getCreatedDate() != null) {
											Date date = dealer.getCreatedDate();
											manageDealerView
													.setCreatedDate(date);
										}
										// Email
										if (userInfo.getUsername() != null) {
											manageDealerView
													.setEmailId(userInfo
															.getUsername());
										}
										// PhoneNumber
										if (userInfo.getPhonenumber() != null) {
											manageDealerView
													.setPhoneNumber(userInfo
															.getPhonenumber()
															.toString());
										}

										// verified
										if (userInfo.isVerified() == false
												&& userInfo.getUsername() == null) {
											manageDealerView.setVerified(true);
										} else {
											manageDealerView.setVerified(false);
										}

										// package
										if (role.getUserPackage() != null) {
											manageDealerView
													.setPackageName(role
															.getUserPackage()
															.getName());
										}
										// status
										if (dealer.getStatus() != null) {
											manageDealerView.setStatus(dealer
													.getStatus());
										}
										// TIN number
										if (dealer.getVatNumber() != null) {
											manageDealerView
													.setVatNumber(dealer
															.getVatNumber());
										}
										// Pan number
										if (dealer.getPanNumber() != null) {
											manageDealerView
													.setPanNumber(dealer
															.getPanNumber());
										}
										// StoreList
										List<String> shopNameList = new ArrayList<>();
										Store store = storeDAO
												.findByUser(dealer);
										if (store != null) {
											if (dealer.getAddresses() != null) {
												Set<Address> addresses = dealer
														.getAddresses();
												manageDealerView
														.setTotalShops(addresses
																.size());
											}
											// Number of products
											long totalProducts = 0;
											Set<String> productIdList = new HashSet<>();
											String name = null;
											if (store.getStorename() != null) {
												name = store.getStorename();
											}
											shopNameList.add(name);
											if (store.getProducts() != null
													&& store.getProducts()
															.size() != 0) {
												List<Product> productList = store
														.getProducts();
												for (Product product : productList) {
													if (product != null) {
														String productId = product
																.getId();
														productIdList
																.add(productId);
													}
												}
												for (int i = 0; i <= productIdList
														.size() - 1; i++) {
													totalProducts++;
												}
											}
											manageDealerView
													.setTotalProducts(totalProducts);
											// later we have to change
											manageDealerView
													.setEstimatedDeliveryDate(7);
											manageDealerView
													.setShopname(shopNameList);
										}
										manageDealerViewList
												.add(manageDealerView);
									}
								}
							}
						}
					}
				}

			}
		}

		userSummaryViewResponse.setUserSummaryView(manageDealerViewList);
		// setting page attributes.
		userSummaryViewResponse.setTotalPages(totalPages);
		userSummaryViewResponse.setTotalElements(totalElements);
		userSummaryViewResponse.setNumber(page);
		userSummaryViewResponse.setSize(size);
		return userSummaryViewResponse;
	}

	/**
	 * Method for super admin manage business associate
	 * 
	 * @return
	 */
	@Override
	public UserSummaryViewResponse getSuperAdminManageBusinessAssociate(
			String searchValue, String status, String area, int page, int size,
			Sort sort) {
		logger.info("Fetching BusinessAssociate based on filter/withOutFilter criteria");
		List<UserSummaryView> manageEnduserViewList = new ArrayList<>();
		UserSummaryViewResponse userSummaryViewResponse = new UserSummaryViewResponse();
		Page<User> pageable = null;
		Integer activeUserCount = 0;
		Integer inactiveUserCount = 0;
		List<User> usersList = new ArrayList<>();
		// searching all business associate
		if (StringUtils.isBlank(searchValue) && StringUtils.isBlank(status)
				&& StringUtils.isBlank(area)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Filter criteria is null so fetching all BusinessAssociate");
			}
			pageable = userDAO.findByRoles_Name("BUSINESSASSOCIATE",
					new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering business associate based on searchValue and status.
		else if (StringUtils.isNotBlank(status)
				&& StringUtils.isNotBlank(searchValue)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching BUSINESSASSOCIATE based on - SearchValue : {} Status : {}",
						searchValue, status);
			}
			pageable = userDAO
					.findByRoles_NameAndStatusAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"BUSINESSASSOCIATE", UserStatus.valueOf(status),
							searchValue, searchValue, searchValue, searchValue,
							searchValue, searchValue, new PageRequest(page,
									size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering business associate based on searchValue.
		else if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(status)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching BUSINESSASSOCIATE based on - SearchValue : {}",
						status);
			}
			pageable = userDAO
					.findByRoles_NameAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberOrContactNameLikeAllIgnoreCase(
							"BUSINESSASSOCIATE", searchValue, searchValue,
							searchValue, searchValue, searchValue, searchValue,
							searchValue, new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering business associate based on status.
		else if (StringUtils.isNotBlank(status)
				&& StringUtils.isBlank(searchValue)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching BUSINESSASSOCIATE based on - Status : {}",
						status);
			}
			pageable = userDAO.findByRoles_NameAndStatus("BUSINESSASSOCIATE",
					UserStatus.valueOf(status), new PageRequest(page, size,
							sort));
			usersList.addAll(pageable.getContent());
		}
		// searching BusinessAssociate based on area
		else if (StringUtils.isNotBlank(area)
				&& StringUtils.isBlank(searchValue)
				&& StringUtils.isBlank(status)) {
			pageable = userDAO
					.findByRoles_NameAndAddresses_AddressLine1ContainsAllIgnoreCase(
							"BUSINESSASSOCIATE", area, new PageRequest(page,
									size, sort));

			usersList.addAll(pageable.getContent());
		}
		for (User user : usersList) {
			UserInfo userInfo = userRepository.findByMongoUserId(user.getId());
			if (user != null && userInfo != null) {
				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					String roleName = role.getName();
					if (roleName.equalsIgnoreCase("BUSINESSASSOCIATE")) {
						if (logger.isDebugEnabled()) {
							logger.debug(
									"BusinessAssociateName : {} BusinessAssociateId : {}",
									user.getFullName(), user.getId());
						}

						UserSummaryView manageBusinessAssociateView = new UserSummaryView();
						manageBusinessAssociateView.setUserId(user.getId());
						// first name
						manageBusinessAssociateView.setFirstName(user
								.getFirstName());
						// last name
						manageBusinessAssociateView.setLastName(user
								.getLastName());
						// full name
						manageBusinessAssociateView.setFullName(user
								.getFullName());
						// Email
						if (userInfo.getUsername() != null) {
							manageBusinessAssociateView.setEmailId(userInfo
									.getUsername());
						}
						// PhoneNumber
						if (userInfo.getPhonenumber() != null) {
							manageBusinessAssociateView.setPhoneNumber(userInfo
									.getPhonenumber().toString());
						}

						// verified
						if (userInfo.isVerified() == false
								&& userInfo.getUsername() == null) {
							manageBusinessAssociateView.setVerified(true);
						} else {
							manageBusinessAssociateView.setVerified(false);
						}

						// Pan number
						manageBusinessAssociateView.setPanNumber(user
								.getPanNumber());
						// created date
						manageBusinessAssociateView.setCreatedDate(user
								.getCreatedDate());
						// Mobile
						for (Address address : user.getAddresses()) {
							manageBusinessAssociateView.setAddress(address);
							for (Phone phone : address.getPhoneNumbers()) {
								if (phone.getType().name()
										.equalsIgnoreCase("MOBILE")) {
									manageBusinessAssociateView.setMobile(phone
											.getNumber());
								}
							}
						}
						// status
						manageBusinessAssociateView.setStatus(user.getStatus());
						// Invested Balance
						manageBusinessAssociateView.setInvestedBalance(user
								.getInvestedBalance());

						manageBusinessAssociateView.setCurrentBalance(user
								.getCurrentBalance());

						// number of products purchased
						List<Order> orderList = userOrderRepository
								.findByUserInfo(userInfo);

						if (orderList != null && orderList.size() != 0) {
							manageBusinessAssociateView
									.setTotalPurchasedOrders(orderList.size());
							Date orderedDate = orderList.get(
									orderList.size() - 1).getOrderedDate();
							Date currentDate = new Date();

							int diffInDays = (int) ((currentDate.getTime() - orderedDate
									.getTime()) / (1000 * 60 * 60 * 24));

							if (diffInDays >= 1) {
								manageBusinessAssociateView
										.setLastOrderDays(diffInDays);
							} else if (diffInDays == 0) {
								manageBusinessAssociateView.setLastOrderDays(1);
							}
						}

						manageEnduserViewList.add(manageBusinessAssociateView);
					}
				}
				if (user.getStatus().equals(UserStatus.ACTIVE)) {
					activeUserCount++;
				}
				if (user.getStatus().equals(UserStatus.INACTIVE)) {
					inactiveUserCount++;
				}
			}
		}
		userSummaryViewResponse.setUserSummaryView(manageEnduserViewList);
		// setting page attributes.
		userSummaryViewResponse.setTotalPages(pageable.getTotalPages());
		userSummaryViewResponse.setTotalElements(pageable.getTotalElements());
		userSummaryViewResponse.setNumber(pageable.getNumber());
		userSummaryViewResponse.setSize(pageable.getSize());
		logger.info("Fetching BusinessAssociate based on filter/withOutFilter criteria! Success");
		return userSummaryViewResponse;
	}

	/**
	 * method for super admin manage architects.
	 * 
	 * @return
	 */
	public UserSummaryViewResponse getSuperAdminManageArchitects(String pack,
			String status, String searchValue, String area, int page, int size,
			Sort sort) {
		logger.info("Fetching Architects based on filter/withOutFilter criteria! Success");
		List<UserSummaryView> manageArchitectsViewList = new ArrayList<>();
		UserSummaryViewResponse userSummaryViewResponse = new UserSummaryViewResponse();
		Page<User> pageable = null;
		List<User> usersList = new ArrayList<>();

		// searching all architects.
		if (StringUtils.isBlank(pack) && StringUtils.isBlank(status)
				&& StringUtils.isBlank(searchValue)
				&& StringUtils.isBlank(area)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Filter criteria is null so fetching all Architects");
			}
			pageable = userDAO.findByRoles_NameAllIgnoreCase("ARCHITECT",
					new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering the architect based on searchValue,package,status.
		else if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isNotBlank(pack)
				&& StringUtils.isNotBlank(status)) {
			String packs[] = pack.split(",");
			Set<ObjectId> userPackageObjectIds = new HashSet<ObjectId>();
			for (int i = 0; i < packs.length; i++) {
				UserPackage userPackage = userPackageDAO.findByName(packs[i]);
				userPackageObjectIds.add(new ObjectId(userPackage.getId()));
			}
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching Architects based on - SearchValue : {} Packages : {} Status : {}",
						searchValue, pack, status);
			}
			pageable = userDAO
					.findByRoles_NameAndRoles_UserPackageInAndStatusOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"ARCHITECT", userPackageObjectIds,
							UserStatus.valueOf(status), searchValue,
							searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering the architect based on package and status.
		else if (StringUtils.isNotBlank(pack) && StringUtils.isNotBlank(status)
				&& StringUtils.isBlank(searchValue)) {
			Set<UserPackage> userPackages = new HashSet<>();
			String packs[] = pack.split(",");
			for (int i = 0; i < packs.length; i++) {
				UserPackage userPackage = userPackageDAO.findByName(packs[i]);
				userPackages.add(userPackage);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching Architects based on - Packages : {} Status : {}",
						pack, status);
			}
			pageable = userDAO.findByRoles_NameAndRoles_UserPackageInAndStatus(
					"ARCHITECT", userPackages, UserStatus.valueOf(status),
					new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering the architect based on searchValue and status.
		else if (StringUtils.isNotBlank(status)
				&& StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(pack)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching Architects based on - SearchValue : {} Status : {}",
						searchValue, status);
			}
			pageable = userDAO
					.findByRoles_NameAndStatusAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"ARCHITECT", UserStatus.valueOf(status),
							searchValue, searchValue, searchValue, searchValue,
							searchValue, searchValue, new PageRequest(page,
									size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering the architect based on searchValue and package.
		else if (StringUtils.isNotBlank(pack)
				&& StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(status)) {
			Set<ObjectId> userPackageObjectIds = new HashSet<ObjectId>();
			String packs[] = pack.split(",");
			for (int i = 0; i < packs.length; i++) {
				UserPackage userPackage = userPackageDAO.findByName(packs[i]);
				userPackageObjectIds.add(new ObjectId(userPackage.getId()));
			}
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching Architects based on - SearchValue : {} Packages : {}",
						searchValue, pack);
			}
			pageable = userDAO
					.findByRoles_NameAndRoles_UserPackageInOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"ARCHITECT", userPackageObjectIds, searchValue,
							searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering the architect based on package.
		else if (StringUtils.isNotBlank(pack) && StringUtils.isBlank(status)
				&& StringUtils.isBlank(searchValue)) {
			Set<UserPackage> userPackages = new HashSet<>();
			String packs[] = pack.split(",");
			for (int i = 0; i < packs.length; i++) {
				UserPackage userPackage = userPackageDAO.findByName(packs[i]);
				userPackages.add(userPackage);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Fetching Architects based on - Packages : {}",
						pack);
			}
			pageable = userDAO.findByRoles_NameAndRoles_UserPackageIn(
					"ARCHITECT", userPackages,
					new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering the architect based on status.
		else if (StringUtils.isNotBlank(status) && StringUtils.isBlank(pack)
				&& StringUtils.isBlank(searchValue)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Fetching Architects based on - Status : {}",
						status);
			}
			pageable = userDAO.findByRoles_NameAndStatus("ARCHITECT",
					UserStatus.valueOf(status), new PageRequest(page, size,
							sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering the architect based on searchValue.
		else if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(pack) && StringUtils.isBlank(status)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Fetching Architects based on - SearchValue : {}",
						searchValue);
			}
			pageable = userDAO
					.findByRoles_NameAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberOrContactNameLikeAllIgnoreCase(
							"ARCHITECT", searchValue, searchValue, searchValue,
							searchValue, searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching Architect based on area
		else if (StringUtils.isNotBlank(area)
				&& StringUtils.isBlank(searchValue)
				&& StringUtils.isBlank(status) && StringUtils.isBlank(pack)) {
			pageable = userDAO
					.findByRoles_NameAndAddresses_AddressLine1ContainsAllIgnoreCase(
							"ARCHITECT", area,
							new PageRequest(page, size, sort));

			usersList.addAll(pageable.getContent());
		}
		for (User user : usersList) {
			UserInfo userInfo = userRepository.findByMongoUserId(user.getId());
			if (user != null && userInfo != null) {
				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					String roleName = role.getName();
					if (roleName.equalsIgnoreCase("ARCHITECT")) {
						if (logger.isDebugEnabled()) {
							logger.debug("ArchitectName : {} ArchitectId : {}",
									user.getFullName(), user.getId());
						}
						UserSummaryView architectsView = new UserSummaryView();

						// Id
						architectsView.setUserId(user.getId());

						// Name
						if (user.getFirstName() != null
								&& user.getLastName() != null) {
							architectsView.setName(user.getFirstName() + " "
									+ user.getLastName());
						} else {
							architectsView.setName(user.getFirstName());
						}

						// Email
						if (userInfo.getUsername() != null) {
							architectsView.setEmailId(userInfo.getUsername());
						}
						// PhoneNumber
						if (userInfo.getPhonenumber() != null) {
							architectsView.setPhoneNumber(userInfo
									.getPhonenumber().toString());
						}

						// verified
						if (userInfo.isVerified() == false
								&& userInfo.getUsername() == null) {
							architectsView.setVerified(true);
						} else {
							architectsView.setVerified(false);
						}

						architectsView.setCreatedDate(user.getCreatedDate());
						// package
						if (role.getUserPackage() != null) {
							architectsView.setPackageName(role.getUserPackage()
									.getName());
						}
						// number of products purchased
						List<Order> orderList = userOrderRepository
								.findByUserInfo(userInfo);

						if (orderList != null && orderList.size() != 0) {
							architectsView.setTotalPurchasedOrders(orderList
									.size());
							Date orderedDate = orderList.get(
									orderList.size() - 1).getOrderedDate();
							Date currentDate = new Date();

							int diffInDays = (int) ((currentDate.getTime() - orderedDate
									.getTime()) / (1000 * 60 * 60 * 24));
							if (diffInDays >= 1) {
								architectsView.setLastOrderDays(diffInDays);
							} else if (diffInDays == 0) {
								architectsView.setLastOrderDays(1);
							}
						}
						architectsView.setStatus(user.getStatus());
						// bids
						long count = userBidDAO.countByUser(user);
						architectsView.setBidsCount(count);
						Set<Address> addresses = user.getAddresses();
						architectsView.setTotalOffices(addresses.size());
						manageArchitectsViewList.add(architectsView);
					}
				}
			}
		}
		userSummaryViewResponse.setUserSummaryView(manageArchitectsViewList);
		// setting page attributes
		userSummaryViewResponse.setTotalPages(pageable.getTotalPages());
		userSummaryViewResponse.setTotalElements(pageable.getTotalElements());
		userSummaryViewResponse.setNumber(pageable.getNumber());
		userSummaryViewResponse.setSize(pageable.getSize());
		logger.info("Fetching Architects based on filter/withOutFilter criteria! Success");
		return userSummaryViewResponse;
	}

	/**
	 * method for super admin manage interior designers.
	 * 
	 * @return
	 */
	@Override
	public UserSummaryViewResponse getSuperAdminManageInteriorDesigners(
			String pack, String status, String searchValue, String area,
			int page, int size, Sort sort) {
		logger.info("Fetching InteriorDesigners based on filter/withOutFilter criteria! Success");
		List<UserSummaryView> manageArchitectsViewList = new ArrayList<>();
		UserSummaryViewResponse userSummaryViewResponse = new UserSummaryViewResponse();
		Page<User> pageable = null;
		List<User> usersList = new ArrayList<>();
		// searching all InteriorDesigners
		if (StringUtils.isBlank(pack) && StringUtils.isBlank(status)
				&& StringUtils.isBlank(searchValue)
				&& StringUtils.isBlank(area)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Filter criteria is null so fetching all InteriorDesigners");
			}
			pageable = userDAO.findByRoles_NameAllIgnoreCase(
					"INTERIORDESIGNER", new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering the InteriorDesigners based on searchValue,package and
		// status.
		else if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isNotBlank(pack)
				&& StringUtils.isNotBlank(status)) {
			String packs[] = pack.split(",");
			Set<ObjectId> userPackageObjectIds = new HashSet<ObjectId>();
			for (int i = 0; i < packs.length; i++) {
				UserPackage userPackage = userPackageDAO.findByName(packs[i]);
				userPackageObjectIds.add(new ObjectId(userPackage.getId()));
			}
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching InteriorDesigners based on - SearchValue : {} Packages : {} Status : {}",
						searchValue, pack, status);
			}
			pageable = userDAO
					.findByRoles_NameAndRoles_UserPackageInAndStatusOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"INTERIORDESIGNER", userPackageObjectIds,
							UserStatus.valueOf(status), searchValue,
							searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering the InteriorDesigners based on package and status
		else if (StringUtils.isNotBlank(pack) && StringUtils.isNotBlank(status)
				&& StringUtils.isBlank(searchValue)) {
			Set<UserPackage> userPackages = new HashSet<>();
			String packs[] = pack.split(",");
			for (int i = 0; i < packs.length; i++) {
				UserPackage userPackage = userPackageDAO.findByName(packs[i]);
				userPackages.add(userPackage);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching InteriorDesigners based on - Packages : {} Status : {}",
						pack, status);
			}
			pageable = userDAO
					.findByRoles_NameAndRoles_UserPackageInAndStatus(
							"INTERIORDESIGNER", userPackages, UserStatus
									.valueOf(status), new PageRequest(page,
									size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering the InteriorDesigners based on searchValue and status
		else if (StringUtils.isNotBlank(status)
				&& StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(pack)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching InteriorDesigners based on - SearchValue : {} Status : {}",
						searchValue, status);
			}
			pageable = userDAO
					.findByRoles_NameAndStatusAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"INTERIORDESIGNER", UserStatus.valueOf(status),
							searchValue, searchValue, searchValue, searchValue,
							searchValue, searchValue, new PageRequest(page,
									size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering the InteriorDesigners based on searchValue and package.
		else if (StringUtils.isNotBlank(pack)
				&& StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(status)) {
			Set<ObjectId> userPackageObjectIds = new HashSet<ObjectId>();
			String packs[] = pack.split(",");
			for (int i = 0; i < packs.length; i++) {
				UserPackage userPackage = userPackageDAO.findByName(packs[i]);
				userPackageObjectIds.add(new ObjectId(userPackage.getId()));
			}
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching InteriorDesigners based on - SearchValue : {} Packages : {}",
						searchValue, pack);
			}
			pageable = userDAO
					.findByRoles_NameAndRoles_UserPackageInOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"INTERIORDESIGNER", userPackageObjectIds,
							searchValue, searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering the InteriorDesigners based on package and status
		else if (StringUtils.isNotBlank(pack) && StringUtils.isBlank(status)
				&& StringUtils.isBlank(searchValue)) {
			Set<UserPackage> userPackages = new HashSet<>();
			String packs[] = pack.split(",");
			for (int i = 0; i < packs.length; i++) {
				UserPackage userPackage = userPackageDAO.findByName(packs[i]);
				userPackages.add(userPackage);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching InteriorDesigners based on - Packages : {}",
						pack);
			}
			pageable = userDAO.findByRoles_NameAndRoles_UserPackageIn(
					"INTERIORDESIGNER", userPackages, new PageRequest(page,
							size, sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering the InteriorDesigners based on status.
		else if (StringUtils.isNotBlank(status) && StringUtils.isBlank(pack)
				&& StringUtils.isBlank(searchValue)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching InteriorDesigners based on - Status : {}",
						status);
			}
			pageable = userDAO.findByRoles_NameAndStatus("INTERIORDESIGNER",
					UserStatus.valueOf(status), new PageRequest(page, size,
							sort));
			usersList.addAll(pageable.getContent());
		}
		// filtering the InteriorDesigners based on searchValue.
		else if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(pack) && StringUtils.isBlank(status)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching InteriorDesigners based on - SearchValue : {}",
						searchValue);
			}
			pageable = userDAO
					.findByRoles_NameAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberOrContactNameLikeAllIgnoreCase(
							"INTERIORDESIGNER", searchValue, searchValue,
							searchValue, searchValue, searchValue, searchValue,
							searchValue, new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching Interior Designer based on area
		else if (StringUtils.isNotBlank(area)
				&& StringUtils.isBlank(searchValue)
				&& StringUtils.isBlank(status) && StringUtils.isBlank(pack)) {
			pageable = userDAO
					.findByRoles_NameAndAddresses_AddressLine1ContainsAllIgnoreCase(
							"INTERIORDESIGNER", area, new PageRequest(page,
									size, sort));

			usersList.addAll(pageable.getContent());
		}
		for (User user : usersList) {
			UserInfo userInfo = userRepository.findByMongoUserId(user.getId());
			if (user != null && userInfo != null) {
				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					String roleName = role.getName();
					if (roleName.equalsIgnoreCase("INTERIORDESIGNER")) {
						if (logger.isDebugEnabled()) {
							logger.debug(
									"InteriorDesignerName : {} InteriorDesignerId : {}",
									user.getFullName(), user.getId());
						}
						UserSummaryView interiorDesignerView = new UserSummaryView();

						// Id
						interiorDesignerView.setUserId(user.getId());

						// Name
						if (user.getFirstName() != null
								&& user.getLastName() != null) {
							interiorDesignerView.setName(user.getFirstName()
									+ " " + user.getLastName());
						} else {
							interiorDesignerView.setName(user.getFirstName());
						}

						// Email
						if (userInfo.getUsername() != null) {
							interiorDesignerView.setEmailId(userInfo
									.getUsername());
						}
						// PhoneNumber
						if (userInfo.getPhonenumber() != null) {
							interiorDesignerView.setPhoneNumber(userInfo
									.getPhonenumber().toString());
						}
						// verified
						if (userInfo.isVerified() == false
								&& userInfo.getUsername() == null) {
							interiorDesignerView.setVerified(true);
						} else {
							interiorDesignerView.setVerified(false);
						}

						interiorDesignerView.setCreatedDate(user
								.getCreatedDate());
						// package
						if (role.getUserPackage() != null) {
							interiorDesignerView.setPackageName(role
									.getUserPackage().getName());
						}
						// number of products purchased
						List<Order> orderList = userOrderRepository
								.findByUserInfo(userInfo);

						if (orderList != null && orderList.size() != 0) {
							interiorDesignerView
									.setTotalPurchasedOrders(orderList.size());
							Date orderedDate = orderList.get(
									orderList.size() - 1).getOrderedDate();
							Date currentDate = new Date();

							int diffInDays = (int) ((currentDate.getTime() - orderedDate
									.getTime()) / (1000 * 60 * 60 * 24));
							if (diffInDays >= 1) {
								interiorDesignerView
										.setLastOrderDays(diffInDays);
							} else if (diffInDays == 0) {
								interiorDesignerView.setLastOrderDays(1);
							}
						}
						interiorDesignerView.setStatus(user.getStatus());
						// bids
						long count = userBidDAO.countByUser(user);
						interiorDesignerView.setBidsCount(count);
						Set<Address> addresses = user.getAddresses();
						interiorDesignerView.setTotalOffices(addresses.size());
						manageArchitectsViewList.add(interiorDesignerView);
					}
				}
			}
		}
		userSummaryViewResponse.setUserSummaryView(manageArchitectsViewList);
		// setting page attributes.
		userSummaryViewResponse.setTotalPages(pageable.getTotalPages());
		userSummaryViewResponse.setTotalElements(pageable.getTotalElements());
		userSummaryViewResponse.setNumber(pageable.getNumber());
		userSummaryViewResponse.setSize(pageable.getSize());
		logger.info("Fetching InteriorDesigners based on filter/withOutFilter criteria! Success");
		return userSummaryViewResponse;
	}

	/**
	 * method for super admin manage ServivceProviders.
	 * 
	 * @return
	 */
	@Override
	public UserSummaryViewResponse getSuperAdminManageServivceProviders(
			String roleName, String status, String searchValue, String area,
			int page, int size, Sort sort) {
		logger.info("Fetching ServivceProviders based on filter/withOutFilter criteria");
		List<UserSummaryView> manageServiceProviderViewList = new ArrayList<>();
		UserSummaryViewResponse userSummaryViewResponse = new UserSummaryViewResponse();
		Page<User> pageable = null;
		List<User> usersList = new ArrayList<>();
		// searching all service providers
		if (StringUtils.isBlank(roleName) && StringUtils.isBlank(status)
				&& StringUtils.isBlank(searchValue)
				&& StringUtils.isBlank(area)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Filter criteria is null so fetching all ServivceProviders");
			}
			pageable = userDAO.findByRoles_Type(UserType
					.valueOf("SERVICEPROVIDER"), new PageRequest(page, size,
					sort));
			usersList.addAll(pageable.getContent());
		}
		// searching service providers based on searchValue,roleName and status.
		else if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isNotBlank(roleName)
				&& StringUtils.isNotBlank(status) && StringUtils.isBlank(area)) {

			Set<String> rolesName = new HashSet<>();
			String roles[] = roleName.split(",");
			for (int i = 0; i < roles.length; i++) {
				rolesName.add(roles[i]);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching ServivceProviders based on - Roles : {} SearchValue : {} Status : {}",
						roleName, searchValue, status);
			}
			pageable = userDAO
					.findByRoles_TypeAndRoles_NameInAndStatusOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							UserType.valueOf("SERVICEPROVIDER"), rolesName,
							UserStatus.valueOf(status), searchValue,
							searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		} else if (StringUtils.isNotBlank(roleName)
				&& StringUtils.isNotBlank(status)
				&& StringUtils.isNotBlank(area)
				&& StringUtils.isBlank(searchValue)) {
			Set<String> rolesName = new HashSet<>();
			String roles[] = roleName.split(",");
			for (int i = 0; i < roles.length; i++) {
				rolesName.add(roles[i]);
			}
			pageable = userDAO
					.findByRoles_TypeAndRoles_NameInAndStatusAndAddresses_AddressLine1ContainsAllIgnoreCase(
							UserType.valueOf("SERVICEPROVIDER"), rolesName,
							UserStatus.valueOf(status), area, new PageRequest(
									page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching service providers based on roleName and status.
		else if (StringUtils.isNotBlank(roleName)
				&& StringUtils.isNotBlank(status)
				&& StringUtils.isBlank(searchValue)
				&& StringUtils.isBlank(area)) {

			Set<String> rolesName = new HashSet<>();
			String roles[] = roleName.split(",");
			for (int i = 0; i < roles.length; i++) {
				rolesName.add(roles[i]);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching ServivceProviders based on - Roles : {} Status : {}",
						roleName, status);
			}
			pageable = userDAO
					.findByRoles_TypeAndRoles_NameInAndStatus(UserType
							.valueOf("SERVICEPROVIDER"), rolesName, UserStatus
							.valueOf(status), new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching service providers based on searchValue and status.
		else if (StringUtils.isNotBlank(status)
				&& StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(roleName) && StringUtils.isBlank(area)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching ServivceProviders based on - SearchValue : {} Status : {}",
						searchValue, status);
			}
			pageable = userDAO
					.findByRoles_TypeAndStatusOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							UserType.valueOf("SERVICEPROVIDER"),
							UserStatus.valueOf(status), searchValue,
							searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching service providers based on searchValue and roleName.
		else if (StringUtils.isNotBlank(roleName)
				&& StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(status) && StringUtils.isBlank(area)) {

			Set<String> rolesName = new HashSet<>();
			String roles[] = roleName.split(",");
			for (int i = 0; i < roles.length; i++) {
				rolesName.add(roles[i]);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching ServivceProviders based on - Roles : {} SearchValue : {}",
						roleName, searchValue);
			}
			pageable = userDAO
					.findByRoles_TypeAndRoles_NameInOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							UserType.valueOf("SERVICEPROVIDER"), rolesName,
							searchValue, searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		} else if (StringUtils.isNotBlank(roleName)
				&& StringUtils.isNotBlank(area) && StringUtils.isBlank(status)
				&& StringUtils.isBlank(searchValue)) {
			Set<String> rolesName = new HashSet<>();
			String roles[] = roleName.split(",");
			for (int i = 0; i < roles.length; i++) {
				rolesName.add(roles[i]);
			}
			pageable = userDAO
					.findByRoles_TypeAndRoles_NameInAndAddresses_AddressLine1ContainsAllIgnoreCase(
							UserType.valueOf("SERVICEPROVIDER"), rolesName,
							area, new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching service providers based on roleName.
		else if (StringUtils.isNotBlank(roleName)
				&& StringUtils.isBlank(status)
				&& StringUtils.isBlank(searchValue)
				&& StringUtils.isBlank(area)) {

			Set<String> rolesName = new HashSet<>();
			String roles[] = roleName.split(",");
			for (int i = 0; i < roles.length; i++) {
				rolesName.add(roles[i]);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching ServivceProviders based on - Roles : {}",
						roleName);
			}
			pageable = userDAO.findByRoles_TypeAndRoles_NameIn(
					UserType.valueOf("SERVICEPROVIDER"), rolesName,
					new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());

		}
		// searching service providers based on status.
		else if (StringUtils.isNotBlank(status)
				&& StringUtils.isBlank(roleName)
				&& StringUtils.isBlank(searchValue)
				&& StringUtils.isBlank(area)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching ServivceProviders based on - Status : {}",
						status);
			}
			pageable = userDAO.findByRoles_TypeAndStatus(UserType
					.valueOf("SERVICEPROVIDER"), UserStatus.valueOf(status),
					new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching service providers based on searchValue.
		else if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(roleName) && StringUtils.isBlank(status)
				&& StringUtils.isBlank(area)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching ServivceProviders based on - SearchValue : {}",
						searchValue);
			}
			pageable = userDAO
					.findByRoles_TypeAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberOrContactNameLikeAllIgnoreCase(
							UserType.valueOf("SERVICEPROVIDER"), searchValue,
							searchValue, searchValue, searchValue, searchValue,
							searchValue, searchValue, new PageRequest(page,
									size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching ServiceProvider based on area
		else if (StringUtils.isNotBlank(area)
				&& StringUtils.isBlank(searchValue)
				&& StringUtils.isBlank(status) && StringUtils.isBlank(roleName)) {
			pageable = userDAO
					.findByRoles_TypeAndAddresses_AddressLine1ContainsAllIgnoreCase(
							UserType.valueOf("SERVICEPROVIDER"), area,
							new PageRequest(page, size, sort));

			usersList.addAll(pageable.getContent());
		}
		if (usersList.size() != 0) {
			for (User user : usersList) {
				UserInfo userInfo = userRepository.findByMongoUserId(user
						.getId());
				if (user != null && userInfo != null) {
					if (logger.isDebugEnabled()) {
						logger.debug(
								"ServiceProviderName : {} ServiceProviderId : {}",
								user.getFullName(), user.getId());
					}
					UserSummaryView manageServiceProviderView = new UserSummaryView();
					// Id
					manageServiceProviderView.setUserId(user.getId());
					// First name
					manageServiceProviderView.setFirstName(user.getFirstName());
					// Last name
					manageServiceProviderView.setLastName(user.getLastName());

					// Full Name
					if (user.getFirstName() != null
							&& user.getLastName() != null) {
						manageServiceProviderView.setFullName(user
								.getFirstName() + " " + user.getLastName());
					} else {
						manageServiceProviderView.setFullName(user
								.getFirstName());
					}

					// Email
					if (userInfo.getUsername() != null) {
						manageServiceProviderView.setEmailId(userInfo
								.getUsername());
					}
					// PhoneNumber
					if (userInfo.getPhonenumber() != null) {
						manageServiceProviderView.setPhoneNumber(userInfo
								.getPhonenumber().toString());
					}
					// verified
					if (userInfo.isVerified() == false
							&& userInfo.getUsername() == null) {
						manageServiceProviderView.setVerified(true);
					} else {
						manageServiceProviderView.setVerified(false);
					}

					manageServiceProviderView.setStatus(user.getStatus());
					manageServiceProviderView.setCreatedDate(user
							.getCreatedDate());
					Set<Address> addresses = user.getAddresses();
					for (Address address : addresses) {
						Set<Phone> phones = address.getPhoneNumbers();
						for (Phone phone : phones) {
							if (phone.getType().equals(PhoneType.MOBILE)) {
								if (phone.getNumber() != null) {
									manageServiceProviderView
											.setPhoneNumber(phone.getNumber());
								}
							}
						}
					}
					Set<Role> roles = user.getRoles();
					for (Role role : roles) {
						manageServiceProviderView.setRole(role.getName());
					}

					// number of products purchased
					List<Order> orderList = userOrderRepository
							.findByUserInfo(userInfo);

					if (orderList != null && orderList.size() != 0) {
						manageServiceProviderView
								.setTotalPurchasedOrders(orderList.size());
						Date orderedDate = orderList.get(orderList.size() - 1)
								.getOrderedDate();
						Date currentDate = new Date();

						int diffInDays = (int) ((currentDate.getTime() - orderedDate
								.getTime()) / (1000 * 60 * 60 * 24));
						if (diffInDays >= 1) {
							manageServiceProviderView
									.setLastOrderDays(diffInDays);
						} else if (diffInDays == 0) {
							manageServiceProviderView.setLastOrderDays(1);
						}
					}
					manageServiceProviderViewList
							.add(manageServiceProviderView);
				}
			}
		}
		userSummaryViewResponse
				.setUserSummaryView(manageServiceProviderViewList);
		// setting page attributes
		userSummaryViewResponse.setTotalPages(pageable.getTotalPages());
		userSummaryViewResponse.setTotalElements(pageable.getTotalElements());
		userSummaryViewResponse.setNumber(pageable.getNumber());
		userSummaryViewResponse.setSize(pageable.getSize());
		logger.info("Fetching ManageServivceProviders based on filter/withOutFilter criteria! Success");
		return userSummaryViewResponse;
	}

	/**
	 * method for super admin manage end users.
	 * 
	 * @return
	 */
	@Override
	public UserSummaryViewResponse getSuperAdminManageEndusers(
			String searchValue, String status, String area, int page, int size,
			Sort sort) {
		logger.info("Fetching Endusers based on filter/withOutFilter criteria");
		List<UserSummaryView> manageEnduserViewList = new ArrayList<>();
		UserSummaryViewResponse userSummaryViewResponse = new UserSummaryViewResponse();
		Page<User> pageable = null;
		List<User> usersList = new ArrayList<>();
		// searching all Endusers
		if (StringUtils.isBlank(searchValue) && StringUtils.isBlank(status)
				&& StringUtils.isBlank(area)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Filter criteria is null so fetching all Endusers");
			}
			pageable = userDAO.findByRoles_Name("ENDUSER", new PageRequest(
					page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching Enduser based on status and searchValue
		else if (StringUtils.isNotBlank(status)
				&& StringUtils.isNotBlank(searchValue)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching Endusers based on - SearchValue : {} Status : {}",
						searchValue, status);
			}
			pageable = userDAO
					.findByRoles_NameAndStatusAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"ENDUSER", UserStatus.valueOf(status), searchValue,
							searchValue, searchValue, searchValue, searchValue,
							searchValue, new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching Enduser based on searchValue
		else if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(status)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Fetching Endusers based on - SearchValue : {}",
						searchValue);
			}

			pageable = userDAO
					.findByRoles_NameAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"ENDUSER", searchValue, searchValue, searchValue,
							searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching Enduser based on status
		else if (StringUtils.isNotBlank(status)
				&& StringUtils.isBlank(searchValue)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Fetching Endusers based on - Status : {}", status);
			}
			pageable = userDAO.findByRoles_NameAndStatus("ENDUSER", UserStatus
					.valueOf(status), new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching Enduser based on area
		else if (StringUtils.isNotBlank(area)
				&& StringUtils.isBlank(searchValue)
				&& StringUtils.isBlank(status)) {
			pageable = userDAO
					.findByRoles_NameAndAddresses_AddressLine1ContainsAllIgnoreCase(
							"ENDUSER", area, new PageRequest(page, size, sort));

			usersList.addAll(pageable.getContent());
		}
		for (User user : usersList) {
			UserInfo userInfo = userRepository.findByMongoUserId(user.getId());
			if (user != null && userInfo != null) {
				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					String roleName = role.getName();
					if (roleName.equalsIgnoreCase("ENDUSER")) {
						if (logger.isDebugEnabled()) {
							logger.debug("EnduserName : {} EnduserId : {}",
									user.getFullName(), user.getId());
						}
						UserSummaryView manageEnduserView = new UserSummaryView();
						// userId
						manageEnduserView.setUserId(user.getId());
						// first name
						manageEnduserView.setFirstName(user.getFirstName());
						// last name
						if (user.getLastName() != null) {
							manageEnduserView.setLastName(user.getLastName());
						}
						// Full Name
						if (user.getFirstName() != null
								&& user.getLastName() != null) {
							manageEnduserView.setFullName(user.getFirstName()
									+ " " + user.getLastName());
						} else {
							manageEnduserView.setFullName(user.getFirstName());
						}
						// Email
						if (userInfo.getUsername() != null) {
							manageEnduserView
									.setEmailId(userInfo.getUsername());
						}
						// PhoneNumber
						if (userInfo.getPhonenumber() != null) {
							manageEnduserView.setPhoneNumber(userInfo
									.getPhonenumber().toString());
						}
						// verified
						if (userInfo.isVerified() == false
								&& userInfo.getUsername() == null) {
							manageEnduserView.setVerified(true);
						} else {
							manageEnduserView.setVerified(false);
						}

						// created date
						manageEnduserView.setCreatedDate(user.getCreatedDate());
						// Mobile
						if (user.getAddresses() != null) {
							for (Address address : user.getAddresses()) {
								manageEnduserView.setAddress(address);
								Set<Phone> phones = address.getPhoneNumbers();
								for (Phone phone : phones) {
									if (phone.getType()
											.equals(PhoneType.MOBILE)) {
										manageEnduserView.setMobile(phone
												.getNumber());
									}
								}
							}
						}
						// status
						manageEnduserView.setStatus(user.getStatus());

						// ShortList
						WishList wishList = wishListDAO.findByUser(user);
						if (wishList != null) {
							if (wishList.getProductIds() != null
									&& wishList.getProductIds().size() != 0) {
								manageEnduserView
										.setTotalshortlistedProducts(wishList
												.getProductIds().size());
							}
						}
						// number of products purchased
						List<Order> orderList = userOrderRepository
								.findByUserInfo(userInfo);

						if (orderList != null && orderList.size() != 0) {
							manageEnduserView.setTotalPurchasedOrders(orderList
									.size());
							Date orderedDate = orderList.get(
									orderList.size() - 1).getOrderedDate();
							Date currentDate = new Date();

							int diffInDays = (int) ((currentDate.getTime() - orderedDate
									.getTime()) / (1000 * 60 * 60 * 24));
							if (diffInDays >= 1) {
								manageEnduserView.setLastOrderDays(diffInDays);
							} else if (diffInDays == 0) {
								manageEnduserView.setLastOrderDays(1);
							}
						}

						// Posted Project
						List<Project> projectList = userProjectDAO
								.findByUser(user);
						manageEnduserView.setNoOfPostedProjects(projectList
								.size());
						List<ProjectType> projectType = new ArrayList<>();
						List<Project> postedProjects = new ArrayList<Project>();
						for (Project project : projectList) {
							ProjectType type = project.getType();
							projectType.add(type);
							postedProjects.add(project);
						}
						manageEnduserView.setPostedProjects(postedProjects);
						manageEnduserView.setProjectType(projectType);
						manageEnduserViewList.add(manageEnduserView);
					}
				}
			}
		}
		userSummaryViewResponse.setUserSummaryView(manageEnduserViewList);
		// setting page attributes
		userSummaryViewResponse.setTotalPages(pageable.getTotalPages());
		userSummaryViewResponse.setTotalElements(pageable.getTotalElements());
		userSummaryViewResponse.setNumber(pageable.getNumber());
		userSummaryViewResponse.setSize(pageable.getSize());
		logger.info("Fetching Endusers based on filter/withOutFilter criteria! Success");
		return userSummaryViewResponse;
	}

	@Override
	public UserSummaryViewResponse getSuperAdminManageTeleAssociate(
			String searchValue, String status, int page, int size, Sort sort) {
		logger.info("Fetching TeleAssociate based on filter/withOutFilter criteria");

		List<UserSummaryView> manageTeleAssociateViewList = new ArrayList<>();
		UserSummaryViewResponse userSummaryViewResponse = new UserSummaryViewResponse();
		Page<User> pageable = null;
		List<User> usersList = new ArrayList<>();
		// searching all TeleAssociates
		if (StringUtils.isBlank(searchValue) && StringUtils.isBlank(status)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Filter criteria is null so fetching all TeleAssociates");
			}
			pageable = userDAO.findByRoles_Name("MBGTELEASSOCIATE",
					new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching TeleAssociates based on status and searchValue
		else if (StringUtils.isNotBlank(status)
				&& StringUtils.isNotBlank(searchValue)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching TeleAssociate based on - SearchValue : {} Status : {}",
						searchValue, status);
			}
			pageable = userDAO
					.findByRoles_NameAndStatusAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"MBGTELEASSOCIATE", UserStatus.valueOf(status),
							searchValue, searchValue, searchValue, searchValue,
							searchValue, searchValue, new PageRequest(page,
									size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching TeleAssociates based on searchValue
		else if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(status)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching TeleAssociate based on - SearchValue : {}",
						searchValue);
			}

			pageable = userDAO
					.findByRoles_NameAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"MBGTELEASSOCIATE", searchValue, searchValue,
							searchValue, searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching TeleAssociates based on status
		else if (StringUtils.isNotBlank(status)
				&& StringUtils.isBlank(searchValue)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Fetching TeleAssociate based on - Status : {}",
						status);
			}
			pageable = userDAO.findByRoles_NameAndStatus("MBGTELEASSOCIATE",
					UserStatus.valueOf(status), new PageRequest(page, size,
							sort));
			usersList.addAll(pageable.getContent());
		}

		for (User user : usersList) {
			UserInfo userInfo = userRepository.findByMongoUserId(user.getId());
			if (user != null && userInfo != null) {
				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					String roleName = role.getName();
					if (roleName.equalsIgnoreCase("MBGTELEASSOCIATE")) {
						UserSummaryView manageTeleAssociateView = new UserSummaryView();
						// userId
						manageTeleAssociateView.setUserId(user.getId());
						// first name
						manageTeleAssociateView.setFirstName(user
								.getFirstName());
						// last name
						if (user.getLastName() != null) {
							manageTeleAssociateView.setLastName(user
									.getLastName());
						}
						// Full Name
						if (user.getFirstName() != null
								&& user.getLastName() != null) {
							manageTeleAssociateView.setFullName(user
									.getFirstName() + " " + user.getLastName());
						} else {
							manageTeleAssociateView.setFullName(user
									.getFirstName());
						}

						// CustomerCode
						char[] fNameArray = user.getFirstName().toUpperCase()
								.toCharArray();

						String customerCode = "BONNTL" + fNameArray[0]
								+ fNameArray[1] + fNameArray[2]
								+ String.format("%05d", userInfo.getUserid());

						manageTeleAssociateView.setCustomerCode(customerCode);

						// Email
						if (userInfo.getUsername() != null) {
							manageTeleAssociateView.setEmailId(userInfo
									.getUsername());
						}
						// PhoneNumber
						if (userInfo.getPhonenumber() != null) {
							manageTeleAssociateView.setPhoneNumber(userInfo
									.getPhonenumber().toString());
						}
						// verified
						if (userInfo.isVerified() == false
								&& userInfo.getUsername() == null) {
							manageTeleAssociateView.setVerified(true);
						} else {
							manageTeleAssociateView.setVerified(false);
						}

						// created date
						manageTeleAssociateView.setCreatedDate(user
								.getCreatedDate());

						// status
						manageTeleAssociateView.setStatus(user.getStatus());

						manageTeleAssociateViewList
								.add(manageTeleAssociateView);
					}
				}
			}
		}
		userSummaryViewResponse.setUserSummaryView(manageTeleAssociateViewList);
		// setting page attributes
		userSummaryViewResponse.setTotalPages(pageable.getTotalPages());
		userSummaryViewResponse.setTotalElements(pageable.getTotalElements());
		userSummaryViewResponse.setNumber(pageable.getNumber());
		userSummaryViewResponse.setSize(pageable.getSize());
		logger.info("Fetching TeleAssociate based on filter/withOutFilter criteria! Success");
		return userSummaryViewResponse;
	}

	@Override
	public UserSummaryViewResponse getSuperAdminManageTechAssociate(
			String searchValue, String status, int page, int size, Sort sort) {
		logger.info("Fetching TechAssociate based on filter/withOutFilter criteria");

		List<UserSummaryView> manageTechAssociateViewList = new ArrayList<>();
		UserSummaryViewResponse userSummaryViewResponse = new UserSummaryViewResponse();
		Page<User> pageable = null;
		List<User> usersList = new ArrayList<>();
		// searching all TechAssociates
		if (StringUtils.isBlank(searchValue) && StringUtils.isBlank(status)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Filter criteria is null so fetching all TechAssociates");
			}
			pageable = userDAO.findByRoles_Name("MBGTECHASSOCIATE",
					new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching TechAssociates based on status and searchValue
		else if (StringUtils.isNotBlank(status)
				&& StringUtils.isNotBlank(searchValue)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching TechAssociate based on - SearchValue : {} Status : {}",
						searchValue, status);
			}
			pageable = userDAO
					.findByRoles_NameAndStatusAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"MBGTECHASSOCIATE", UserStatus.valueOf(status),
							searchValue, searchValue, searchValue, searchValue,
							searchValue, searchValue, new PageRequest(page,
									size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching TechAssociates based on searchValue
		else if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(status)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching TechAssociates based on - SearchValue : {}",
						searchValue);
			}

			pageable = userDAO
					.findByRoles_NameAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"MBGTECHASSOCIATE", searchValue, searchValue,
							searchValue, searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching TechAssociates based on status
		else if (StringUtils.isNotBlank(status)
				&& StringUtils.isBlank(searchValue)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Fetching TechAssociate based on - Status : {}",
						status);
			}
			pageable = userDAO.findByRoles_NameAndStatus("MBGTECHASSOCIATE",
					UserStatus.valueOf(status), new PageRequest(page, size,
							sort));
			usersList.addAll(pageable.getContent());
		}

		for (User user : usersList) {
			UserInfo userInfo = userRepository.findByMongoUserId(user.getId());
			if (user != null && userInfo != null) {
				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					String roleName = role.getName();
					if (roleName.equalsIgnoreCase("MBGTECHASSOCIATE")) {
						UserSummaryView manageTechAssociateView = new UserSummaryView();
						// userId
						manageTechAssociateView.setUserId(user.getId());
						// first name
						manageTechAssociateView.setFirstName(user
								.getFirstName());
						// last name
						if (user.getLastName() != null) {
							manageTechAssociateView.setLastName(user
									.getLastName());
						}
						// Full Name
						if (user.getFirstName() != null
								&& user.getLastName() != null) {
							manageTechAssociateView.setFullName(user
									.getFirstName() + " " + user.getLastName());
						} else {
							manageTechAssociateView.setFullName(user
									.getFirstName());
						}

						// CustomerCode
						char[] fNameArray = user.getFirstName().toUpperCase()
								.toCharArray();

						String customerCode = "BONNTH" + fNameArray[0]
								+ fNameArray[1] + fNameArray[2]
								+ String.format("%05d", userInfo.getUserid());

						manageTechAssociateView.setCustomerCode(customerCode);

						// Email
						if (userInfo.getUsername() != null) {
							manageTechAssociateView.setEmailId(userInfo
									.getUsername());
						}
						// PhoneNumber
						if (userInfo.getPhonenumber() != null) {
							manageTechAssociateView.setPhoneNumber(userInfo
									.getPhonenumber().toString());
						}
						// verified
						if (userInfo.isVerified() == false
								&& userInfo.getUsername() == null) {
							manageTechAssociateView.setVerified(true);
						} else {
							manageTechAssociateView.setVerified(false);
						}

						// created date
						manageTechAssociateView.setCreatedDate(user
								.getCreatedDate());

						// status
						manageTechAssociateView.setStatus(user.getStatus());

						manageTechAssociateViewList
								.add(manageTechAssociateView);
					}
				}
			}
		}
		userSummaryViewResponse.setUserSummaryView(manageTechAssociateViewList);
		// setting page attributes
		userSummaryViewResponse.setTotalPages(pageable.getTotalPages());
		userSummaryViewResponse.setTotalElements(pageable.getTotalElements());
		userSummaryViewResponse.setNumber(pageable.getNumber());
		userSummaryViewResponse.setSize(pageable.getSize());
		logger.info("Fetching TechAssociate based on filter/withOutFilter criteria! Success");
		return userSummaryViewResponse;
	}

	@Override
	public UserSummaryViewResponse getSuperAdminManageFieldAssociate(
			String searchValue, String status, int page, int size, Sort sort) {

		logger.info("Fetching FieldAssociate based on filter/withOutFilter criteria");

		List<UserSummaryView> manageFieldAssociateViewList = new ArrayList<>();
		UserSummaryViewResponse userSummaryViewResponse = new UserSummaryViewResponse();
		Page<User> pageable = null;
		List<User> usersList = new ArrayList<>();
		// searching all TechAssociates
		if (StringUtils.isBlank(searchValue) && StringUtils.isBlank(status)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Filter criteria is null so fetching all FieldAssociates");
			}
			pageable = userDAO.findByRoles_Name("FIELDASSOCIATE",
					new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching TechAssociates based on status and searchValue
		else if (StringUtils.isNotBlank(status)
				&& StringUtils.isNotBlank(searchValue)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching FieldAssociate based on - SearchValue : {} Status : {}",
						searchValue, status);
			}
			pageable = userDAO
					.findByRoles_NameAndStatusAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"FIELDASSOCIATE", UserStatus.valueOf(status),
							searchValue, searchValue, searchValue, searchValue,
							searchValue, searchValue, new PageRequest(page,
									size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching TechAssociates based on searchValue
		else if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(status)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"Fetching FieldAssociates based on - SearchValue : {}",
						searchValue);
			}

			pageable = userDAO
					.findByRoles_NameAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							"FIELDASSOCIATE", searchValue, searchValue,
							searchValue, searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}
		// searching TechAssociates based on status
		else if (StringUtils.isNotBlank(status)
				&& StringUtils.isBlank(searchValue)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Fetching FieldAssociate based on - Status : {}",
						status);
			}
			pageable = userDAO.findByRoles_NameAndStatus("FIELDASSOCIATE",
					UserStatus.valueOf(status), new PageRequest(page, size,
							sort));
			usersList.addAll(pageable.getContent());
		}

		for (User user : usersList) {
			UserInfo userInfo = userRepository.findByMongoUserId(user.getId());
			if (user != null && userInfo != null) {
				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					String roleName = role.getName();
					if (roleName.equalsIgnoreCase("FIELDASSOCIATE")) {
						UserSummaryView manageFieldAssociateView = new UserSummaryView();
						// userId
						manageFieldAssociateView.setUserId(user.getId());
						// first name
						manageFieldAssociateView.setFirstName(user
								.getFirstName());
						// last name
						if (user.getLastName() != null) {
							manageFieldAssociateView.setLastName(user
									.getLastName());
						}
						// Full Name
						if (user.getFirstName() != null
								&& user.getLastName() != null) {
							manageFieldAssociateView.setFullName(user
									.getFirstName() + " " + user.getLastName());
						} else {
							manageFieldAssociateView.setFullName(user
									.getFirstName());
						}

						// CustomerCode
						char[] fNameArray = user.getFirstName().toUpperCase()
								.toCharArray();

						String customerCode = "BONNFA" + fNameArray[0]
								+ fNameArray[1] + fNameArray[2]
								+ String.format("%05d", userInfo.getUserid());

						manageFieldAssociateView.setCustomerCode(customerCode);

						// Email
						if (userInfo.getUsername() != null) {
							manageFieldAssociateView.setEmailId(userInfo
									.getUsername());
						}
						// PhoneNumber
						if (userInfo.getPhonenumber() != null) {
							manageFieldAssociateView.setPhoneNumber(userInfo
									.getPhonenumber().toString());
						}
						// verified
						if (userInfo.isVerified() == false
								&& userInfo.getUsername() == null) {
							manageFieldAssociateView.setVerified(true);
						} else {
							manageFieldAssociateView.setVerified(false);
						}

						// created date
						manageFieldAssociateView.setCreatedDate(user
								.getCreatedDate());

						// status
						manageFieldAssociateView.setStatus(user.getStatus());

						manageFieldAssociateViewList
								.add(manageFieldAssociateView);
					}
				}
			}
		}
		userSummaryViewResponse
				.setUserSummaryView(manageFieldAssociateViewList);
		// setting page attributes
		userSummaryViewResponse.setTotalPages(pageable.getTotalPages());
		userSummaryViewResponse.setTotalElements(pageable.getTotalElements());
		userSummaryViewResponse.setNumber(pageable.getNumber());
		userSummaryViewResponse.setSize(pageable.getSize());
		logger.info("Fetching FieldAssociate based on filter/withOutFilter criteria! Success");
		return userSummaryViewResponse;
	}

	/**
	 * method for get Active and InActive User count
	 * 
	 * @return
	 */
	@Override
	public UsersStatusCount getUserStatusCount() {
		logger.info("Fetching Active and InActive user count");
		UsersStatusCount statusCount = new UsersStatusCount();
		List<User> activeUsersList = new ArrayList<User>();
		List<User> inActiveUserList = new ArrayList<User>();
		List<User> userList = userDAO.findAll();
		for (User user : userList) {
			UserStatus userStaus = user.getStatus();
			if (userStaus == UserStatus.ACTIVE) {
				activeUsersList.add(user);
			} else if (userStaus == UserStatus.INACTIVE) {
				inActiveUserList.add(user);
			}
		}
		statusCount.setActiveUsers(activeUsersList.size());
		statusCount.setInActiveUSers(inActiveUserList.size());
		if (logger.isDebugEnabled()) {
			logger.debug("Buildonn Active Users Count : {}",
					activeUsersList.size());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Buildonn InActive Users Count : {}",
					inActiveUserList.size());
		}
		logger.info("Fetching Active and InActive user count! Success");
		return statusCount;
	}

	/**
	 * Method to get the count of buyable users(ie.,Enduser,Architect,Interior
	 * Designer,Business Associate)
	 */
	@Override
	public BuyableUsersCount getBuyableUsersCount() {

		BuyableUsersCount buyableUsersCount = new BuyableUsersCount();

		// Get count of all active endusers
		long enduserCount = userDAO.countByRoles_NameAndStatus("ENDUSER",
				"ACTIVE");
		buyableUsersCount.setEndusersCount((int) enduserCount);

		// Get count of all active business associates
		long businessAssociatesCount = userDAO.countByRoles_NameAndStatus(
				"BUSINESSASSOCIATE", "ACTIVE");
		buyableUsersCount
				.setBusinessAssoicateCount((int) businessAssociatesCount);

		// Get count of all active serviceproviders
		long serviceProvidersCount = userDAO
				.countByRoles_TypeAndStatusAllIgnoreCase("SERVICEPROVIDER",
						"ACTIVE");
		buyableUsersCount.setServiceProvidersCount((int) serviceProvidersCount);

		// Returning the buyableuserscount response object
		return buyableUsersCount;
	}

	/**
	 * method to perform count operation, Count is performed based on
	 * firstname,lastname,both firstname&lastname,username,email and
	 * phonenumber.
	 * 
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param phoneNumber
	 * @param fullName
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public long countUser(String userName, String firstName, String lastName,
			String email, String phoneNumber, String fullName, String roleName,
			String status) throws Exception {
		long res = 0;
		logger.info("Fetching user count");
		/*
		 * This condition is for checking all the fields are blank If all the
		 * fields are blank it will return count of all the user.
		 */
		if (StringUtils.isBlank(userName) && StringUtils.isBlank(firstName)
				&& StringUtils.isBlank(lastName) && StringUtils.isBlank(email)
				&& StringUtils.isBlank(phoneNumber)
				&& StringUtils.isBlank(fullName)
				&& StringUtils.isBlank(roleName) && StringUtils.isBlank(status)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Filter criteria is null so fetching all user count");
			}
			return userDAO.count();
		}
		/*
		 * if any of the fields are specified/given, result will be displayed
		 * based on the user request.
		 */
		else {
			if (StringUtils.isNotBlank(firstName)
					&& StringUtils.isNotBlank(lastName)) {
				if (logger.isDebugEnabled()) {
					logger.debug(
							"Fetching user count based on firstName : {} and lastName : {}",
							firstName, lastName);
				}
				res = userDAO.countByFirstNameAndLastName(firstName, lastName);
			} else if (StringUtils.isNotBlank(firstName)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Fetching user count based on firstName : {}",
							firstName);
				}
				res = userDAO.countByFirstName(firstName);
			} else if (StringUtils.isNotBlank(lastName)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Fetching user count based on lastName : {}",
							lastName);
				}
				res = userDAO.countByLastName(lastName);
			}

			if (StringUtils.isNotBlank(roleName)
					&& StringUtils.isNotBlank(status)) {
				if (logger.isDebugEnabled()) {
					logger.debug(
							"Fetching user count based on roleName : {} and status : {}",
							roleName, status);
				}
				res = userDAO.countByRoles_NameAndStatus(roleName, status);
			} else if (StringUtils.isNotBlank(roleName)) {
				res = userDAO.countByRoles_Name(roleName);
				if (logger.isDebugEnabled()) {
					logger.debug("Fetching user count based on roleName : {}",
							roleName);
				}
			}

			if (StringUtils.isNotBlank(phoneNumber)) {
				if (userValidation.validatePhoneNumber(phoneNumber)) {
					if (logger.isDebugEnabled()) {
						logger.debug(
								"Fetching user count based on phoneNumber : {}",
								phoneNumber);
					}
					res = userDAO
							.countByAddresses_PhoneNumbers_Number(phoneNumber);
				} else {
					logger.info("Invalid phone number");
				}
			}

			if (StringUtils.isNotBlank(email)) {
				if (userValidation.validateEmail(email)) {
					if (logger.isDebugEnabled()) {
						logger.debug("Fetching user count based on email : {}",
								email);
					}
					res = userDAO.countByAddresses_Email(email);
					logger.info("Counting user based on email");
				} else {
					logger.info("Invalid email");
				}
			}

			if (StringUtils.isNotBlank(userName)) {
				if (userValidation.validateEmail(userName)) {
					if (logger.isDebugEnabled()) {
						logger.debug(
								"Fetching user count based on UserName(Email) : {}",
								userName);
					}
					res = userDAO.countByUsername(userName);
					logger.info("Counting user based on userName", userName);
				} else {
					logger.info("Invalid UserName(Email)");
				}
			}

			if (StringUtils.isNotBlank(fullName)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Fetching user count based on fullName : {}",
							fullName);
				}
				res = userDAO.countByFullName(fullName);
			}
			logger.info("Fetching user count! Success");
			return res;
		}
	}

	/**
	 * Method to create new User,The user is created with the fields
	 * username,firstname,lastname,sex,usertype,address,package,role and status.
	 * 
	 * @param userCreateRequestParam
	 * @return
	 * @throws Exception
	 */

	@Override
	public User createUser(String userName, String firstName, String lastName,
			String fullName, String sex, String status, String addrId,
			String addrLine1, String addrLine2, String city, String state,
			String country, String zipcode, Point point, String email,
			String phoneType, String phoneNumber, String primary, String role)
			throws Exception {
		User user = new User();
		if (StringUtils.isNotBlank(userName)) {
			/*
			 * checking userName(email) format
			 */
			if (userValidation.validateEmail(userName)) {
				user.setUsername(userName);
			} else {
				logger.info("invalid userName format");
				throw new Exception("invalid userName format");
			}
		}
		if (StringUtils.isNotBlank(firstName)) {
			user.setFirstName(firstName);
		}
		if (StringUtils.isNotBlank(lastName)) {
			user.setLastName(lastName);
		}
		if (StringUtils.isNotBlank(fullName)) {
			user.setFullName(fullName);
		}
		if (StringUtils.isNotBlank(sex)) {
			user.setSex(sex);
		}
		if (StringUtils.isNotBlank(status)) {
			user.setStatus(UserStatus.valueOf(status));
		}
		Address addr = new Address();
		Set<Address> addressSet = new HashSet<>();
		if (StringUtils.isNotBlank(addrId)) {
			addr.setAddressId(Integer.parseInt(addrId));
		}
		if (StringUtils.isNotBlank(addrLine1)) {
			addr.setAddressLine1(addrLine1);
		}
		if (StringUtils.isNotBlank(addrLine2)) {
			addr.setAddressLine2(addrLine2);
		}
		if (StringUtils.isNotBlank(city)) {
			addr.setCity(city);
		}
		if (StringUtils.isNotBlank(state)) {
			addr.setState(state);
		}
		if (StringUtils.isNotBlank(country)) {
			addr.setCountry(country);
		}
		if (StringUtils.isNotBlank(zipcode)) {
			addr.setZipcode(Integer.parseInt(zipcode));
		}
		/*
		 * checking email format
		 */
		if (StringUtils.isNotBlank(email)) {
			if (userValidation.validateEmail(email)) {
				addr.setEmail(email);
			} else {
				logger.info("invalid email format");
				throw new Exception("invalid email format");
			}
		}
		if (point != null) {
			addr.setLocation(point);
		}

		Phone phone = new Phone();
		Set<Phone> phoneSet = new HashSet<>();
		if (StringUtils.isNotBlank(phoneType)) {
			phone.setType(PhoneType.valueOf(phoneType));
		}
		if (StringUtils.isNotBlank(phoneNumber)) {
			phone.setNumber(phoneNumber);
		}
		if (StringUtils.isNotBlank(primary)) {
			phone.setPrimary(Boolean.valueOf(primary));
		}
		phoneSet.add(phone);
		if (phoneSet != null) {
			addr.setPhoneNumbers(phoneSet);
		}
		addressSet.add(addr);
		if (addressSet != null) {
			user.setAddresses(addressSet);
		}
		Set<Role> roleSet = new HashSet<>();
		if (StringUtils.isNotBlank(role)) {
			Role role1 = roleDAO.findByName(role);
			roleSet.add(role1);
		}

		if (roleSet != null) {
			user.setRoles(roleSet);
		}
		if (userValidation.userValidation(user)) {
			/*
			 * inserting user to mongo DB
			 */
			userDAO.insert(user);

			logger.info("User inserted successfully.");
		} else {
			logger.error("user creation failed");
			throw new Exception("user creation failed");
		}
		return user;
	}

	/**
	 * 
	 * method to search the user here user is search based on userid.
	 * 
	 * @param userid
	 * @return
	 * @throws Exception
	 * @throws MBGAppException
	 */
	@Override
	public SingleUserResponse searchUserById(String uid) throws Exception {
		// Fetching user based on userId
		logger.info("Fetching user based on userId : {}", uid);
		SingleUserResponse singleUserResponse = new SingleUserResponse();
		User user = null;
		// Checking userId is empty
		if (StringUtils.isNotBlank(uid)) {
			user = userDAO.findById(uid);
			UserInfo userInfo = userRepository.findByMongoUserId(uid);
			// Checking user is exist in our system
			if (user != null && userInfo != null) {
				Set<Role> userRoles = user.getRoles();
				for (Role userRole : userRoles) {

					// UserId
					singleUserResponse.setId(user.getId());
					// Email
					if (userInfo.getUsername() != null) {
						singleUserResponse.setUsername(userInfo.getUsername());
					}
					// PhoneNumber
					if (userInfo.getPhonenumber() != null) {
						singleUserResponse.setPhoneNumber(userInfo
								.getPhonenumber().toString());
					}
					// verified
					if (userInfo.isVerified() == false
							&& userInfo.getUsername() == null) {
						singleUserResponse.setVerified(true);
					} else {
						singleUserResponse.setVerified(false);
					}

					// FirstName
					singleUserResponse.setFirstName(user.getFirstName());
					// LastName
					if (user.getLastName() != null) {
						singleUserResponse.setLastName(user.getLastName());
					}
					// FullName
					if (user.getFirstName() != null
							&& user.getLastName() != null) {
						singleUserResponse.setFullName(user.getFirstName()
								+ " " + user.getLastName());
					} else {
						singleUserResponse.setFullName(user.getFirstName());
					}

					// Status
					singleUserResponse.setStatus(user.getStatus().name());
					// Created date
					singleUserResponse.setCreatedDate(user.getCreatedDate());
					// ActiveSince
					if (user.getActiveSince() != null) {
						singleUserResponse
								.setActiveSince(user.getActiveSince());
					}
					// Description
					singleUserResponse.setDesc(user.getDesc());
					// ProfileImage
					singleUserResponse.setProfilePicture(user
							.getProfilePicture());
					// contact Name
					if (user.getContactName() != null) {
						singleUserResponse
								.setContactName(user.getContactName());
					}
					// skill set
					if (user.getOtherSkillSets() != null) {
						singleUserResponse.setOtherSkillSets(user
								.getOtherSkillSets());
					}
					// operating cities
					if (user.getOperatingCities() != null) {
						singleUserResponse.setOperatingCities(user
								.getOperatingCities());
					}
					// Current Balance
					singleUserResponse.setCurrentBalance(user
							.getCurrentBalance());

					// Invested Balance
					singleUserResponse.setInvestedBalance(user
							.getInvestedBalance());
					// CustomerCode
					char[] fNameArray = user.getFirstName().toUpperCase()
							.toCharArray();

					char[] roleArray = userRole.getName().toUpperCase()
							.toCharArray();

					String customerCode = "BONN" + roleArray[0] + roleArray[1]
							+ fNameArray[0] + fNameArray[1] + fNameArray[2]
							+ String.format("%05d", userInfo.getUserid());

					singleUserResponse.setCustomerCode(customerCode);

					// User package name
					UserPackage userPackage = userRole.getUserPackage();
					if (userPackage != null) {
						singleUserResponse
								.setPackageName(userPackage.getName());
					}
					// Title
					if (user.getTitle() != null) {
						singleUserResponse.setTitle(user.getTitle());
					}
					// Vat Number
					if (user.getVatNumber() != null) {
						singleUserResponse.setVatNumber(user.getVatNumber());
					}
				}

			} else {
				logger.error("User is not exist in our system for userId : {}",
						uid);
				throw new Exception(
						"User is not exist in our system for userId : " + uid);
			}
		} else {
			logger.error("User id should not be blank. userId : {}", uid);
			throw new Exception("User id should not be blank. userId : " + uid);
		}
		logger.info("Fetching user based on userId : {}! Success", uid);
		return singleUserResponse;
	}

	@Override
	public ProfileCompletenessResponse sendProfileCompletenessScore(
			String userId) {
		int registration = 25;
		int contactDetails = 20;
		int profileDescription = 15;
		int profileImage = 5;
		int portfolios = 25;
		int services = 5;
		int cities = 5;
		int totalScore = 0;
		ProfileCompletenessResponse profileCompletenessResponse = null;
		if (StringUtils.isNotBlank(userId)) {

			User user = userDAO.findById(userId);
			if (user != null) {
				profileCompletenessResponse = new ProfileCompletenessResponse();
				// Registered User
				totalScore = totalScore + registration;
				profileCompletenessResponse.setRegistration(registration);
				// UpdateContactInfo
				if (user.getUsername() != null
						&& user.getUserPhoneNumber() != null) {
					totalScore = totalScore + contactDetails;
					profileCompletenessResponse
							.setContactDetails(contactDetails);
				}

				// Profile Description
				if (user.getDesc() != null) {
					totalScore = totalScore + profileDescription;
					profileCompletenessResponse
							.setProfileDescription(profileDescription);
				}

				// ProfileImage
				if (user.getProfilePicture() != null) {
					totalScore = totalScore + profileImage;
					profileCompletenessResponse.setProfileImage(profileImage);
				}

				List<UserProjectProfile> userProjectProfiles = userProjectProfileDAO
						.findByUser(user);

				// Portfolio
				if (userProjectProfiles != null
						&& userProjectProfiles.size() != 0) {
					totalScore = totalScore + portfolios;
					profileCompletenessResponse.setPortfolios(portfolios);
				}

				// Services
				if (user.getOtherSkillSets() != null
						&& user.getOtherSkillSets().size() != 0) {
					totalScore = totalScore + services;
					profileCompletenessResponse.setServices(services);
				}

				// cities
				if (user.getOperatingCities() != null
						&& user.getOperatingCities().size() != 0) {
					totalScore = totalScore + cities;
					profileCompletenessResponse.setCities(cities);
				}
			}
		}
		return profileCompletenessResponse;
	}

	/**
	 * method for super admin manage end user view details.
	 * 
	 * @param userid
	 * @return
	 */
	@Override
	public UserSummaryViewDetails getManageEndUserDetailsView(String userid) {
		logger.info("Fetching EndUser Information based on EndUserId");
		UserSummaryViewDetails adminManageEnduserViewDetails = null;
		if (StringUtils.isNotBlank(userid)) {
			User user = userDAO.findOne(userid);
			UserInfo userInfo = userRepository.findByMongoUserId(userid);
			if (user != null && userInfo != null) {
				Set<Role> enduserRoles = user.getRoles();
				for (Role enduserRole : enduserRoles) {
					if (enduserRole.getName().equalsIgnoreCase("ENDUSER")) {
						adminManageEnduserViewDetails = new UserSummaryViewDetails();
						// UserId
						adminManageEnduserViewDetails.setUserId(user.getId());
						// FirstName
						adminManageEnduserViewDetails.setFirstName(user
								.getFirstName());
						// LastName
						if (user.getLastName() != null) {
							adminManageEnduserViewDetails.setLastName(user
									.getLastName());
						}
						// FullName
						if (user.getFirstName() != null
								&& user.getLastName() != null) {
							adminManageEnduserViewDetails.setFullName(user
									.getFirstName() + " " + user.getLastName());
						} else {
							adminManageEnduserViewDetails.setFullName(user
									.getFirstName());
						}
						// Email
						if (userInfo.getUsername() != null) {
							adminManageEnduserViewDetails.setEmailId(userInfo
									.getUsername());
						}
						// PhoneNumber
						if (userInfo.getPhonenumber() != null) {
							adminManageEnduserViewDetails
									.setPhoneNumber(userInfo.getPhonenumber()
											.toString());
						}

						// verified
						if (userInfo.isVerified() == false
								&& userInfo.getUsername() == null) {
							adminManageEnduserViewDetails.setVerified(true);
						} else {
							adminManageEnduserViewDetails.setVerified(false);
						}

						if (user.getAddresses() != null) {
							adminManageEnduserViewDetails.setAddresses(user
									.getAddresses());
						}

						adminManageEnduserViewDetails.setCreatedDate(user
								.getCreatedDate());

						if (user.getActiveSince() != null) {
							adminManageEnduserViewDetails.setActiveSince(user
									.getActiveSince());
						}

						adminManageEnduserViewDetails.setStatus(user
								.getStatus());

						// Current Balance
						adminManageEnduserViewDetails.setCurrentBalance(user
								.getCurrentBalance());

						// Invested Balance
						adminManageEnduserViewDetails.setInvestedBalance(user
								.getInvestedBalance());

						// CustomerCode
						char[] fNameArray = user.getFirstName().toUpperCase()
								.toCharArray();

						String customerCode = "BONNEN" + fNameArray[0]
								+ fNameArray[1] + fNameArray[2]
								+ String.format("%05d", userInfo.getUserid());

						adminManageEnduserViewDetails
								.setCustomerCode(customerCode);

						if (user.getDesc() != null) {
							adminManageEnduserViewDetails.setDesc(user
									.getDesc().getVal());
						}

						// Vat Number
						if (user.getVatNumber() != null) {
							adminManageEnduserViewDetails.setVatNumber(user
									.getVatNumber());
						}

						// ShortList
						WishList wishList = wishListDAO.findByUser(user);
						if (wishList != null) {
							if (wishList.getProductIds() != null
									&& wishList.getProductIds().size() != 0) {
								adminManageEnduserViewDetails
										.setTotalshortlistedProducts(wishList
												.getProductIds().size());
							}
						}
						// number of products purchased
						List<Order> orderList = userOrderRepository
								.findByUserInfo(userInfo);

						if (orderList != null && orderList.size() != 0) {
							adminManageEnduserViewDetails
									.setTotalPurchasedOrders(orderList.size());
						}

						// Posted Project
						List<Project> projectList = userProjectDAO
								.findByUser(user);
						List<ProjectType> projectType = new ArrayList<>();
						for (Project project : projectList) {
							ProjectType type = project.getType();
							projectType.add(type);
						}
						adminManageEnduserViewDetails
								.setProjectType(projectType);
						adminManageEnduserViewDetails
								.setProjectList(projectList);
					}
				}
			} else {
				logger.info("User doesn't exists");
			}
		} else {
			logger.info("user id null");
		}
		logger.info("Fetching EndUser Information based on EndUserId! Success");
		return adminManageEnduserViewDetails;
	}

	@Override
	public UserSummaryViewDetails getManageTeleAssociateDetailsView(
			String userid) {
		logger.info("Fetching TeleAssociate Information based on Id : {}",
				userid);
		UserSummaryViewDetails adminManageTeleAssociateViewDetails = null;
		if (StringUtils.isNotBlank(userid)) {
			User user = userDAO.findOne(userid);
			UserInfo userInfo = userRepository.findByMongoUserId(userid);
			if (user != null && userInfo != null) {
				Set<Role> enduserRoles = user.getRoles();
				for (Role enduserRole : enduserRoles) {
					if (enduserRole.getName().equalsIgnoreCase(
							"MBGTELEASSOCIATE")) {
						adminManageTeleAssociateViewDetails = new UserSummaryViewDetails();
						// UserId
						adminManageTeleAssociateViewDetails.setUserId(user
								.getId());
						// FirstName
						adminManageTeleAssociateViewDetails.setFirstName(user
								.getFirstName());
						// LastName
						if (user.getLastName() != null) {
							adminManageTeleAssociateViewDetails
									.setLastName(user.getLastName());
						}
						// FullName
						if (user.getFirstName() != null
								&& user.getLastName() != null) {
							adminManageTeleAssociateViewDetails
									.setFullName(user.getFirstName() + " "
											+ user.getLastName());
						} else {
							adminManageTeleAssociateViewDetails
									.setFullName(user.getFirstName());
						}
						// Email
						if (userInfo.getUsername() != null) {
							adminManageTeleAssociateViewDetails
									.setEmailId(userInfo.getUsername());
						}
						// PhoneNumber
						if (userInfo.getPhonenumber() != null) {
							adminManageTeleAssociateViewDetails
									.setPhoneNumber(userInfo.getPhonenumber()
											.toString());
						}

						// verified
						if (userInfo.isVerified() == false
								&& userInfo.getUsername() == null) {
							adminManageTeleAssociateViewDetails
									.setVerified(true);
						} else {
							adminManageTeleAssociateViewDetails
									.setVerified(false);
						}

						if (user.getAddresses() != null) {
							adminManageTeleAssociateViewDetails
									.setAddresses(user.getAddresses());
						}

						adminManageTeleAssociateViewDetails.setCreatedDate(user
								.getCreatedDate());

						if (user.getActiveSince() != null) {
							adminManageTeleAssociateViewDetails
									.setActiveSince(user.getActiveSince());
						}

						adminManageTeleAssociateViewDetails.setStatus(user
								.getStatus());

						// CustomerCode
						char[] fNameArray = user.getFirstName().toUpperCase()
								.toCharArray();

						String customerCode = "BONNTL" + fNameArray[0]
								+ fNameArray[1] + fNameArray[2]
								+ String.format("%05d", userInfo.getUserid());

						adminManageTeleAssociateViewDetails
								.setCustomerCode(customerCode);

						if (user.getDesc() != null) {
							adminManageTeleAssociateViewDetails.setDesc(user
									.getDesc().getVal());
						}
						// Current Balance
						adminManageTeleAssociateViewDetails
								.setCurrentBalance(user.getCurrentBalance());

						// Invested Balance
						adminManageTeleAssociateViewDetails
								.setInvestedBalance(user.getInvestedBalance());
					}
				}
			} else {
				logger.info("User doesn't exists");
			}
		} else {
			logger.info("user id null");
		}
		logger.info("Fetching TeleAssociate Information based on his Id! Success");
		return adminManageTeleAssociateViewDetails;
	}

	@Override
	public UserSummaryViewDetails getManageTechAssociateDetailsView(
			String userId) {
		logger.info("Fetching TechAssociate Information based on Id : {}",
				userId);
		UserSummaryViewDetails adminManageTechAssociateViewDetails = null;
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findOne(userId);
			UserInfo userInfo = userRepository.findByMongoUserId(userId);
			if (user != null && userInfo != null) {
				Set<Role> enduserRoles = user.getRoles();
				for (Role enduserRole : enduserRoles) {
					if (enduserRole.getName().equalsIgnoreCase(
							"MBGTECHASSOCIATE")) {
						adminManageTechAssociateViewDetails = new UserSummaryViewDetails();
						// UserId
						adminManageTechAssociateViewDetails.setUserId(user
								.getId());
						// FirstName
						adminManageTechAssociateViewDetails.setFirstName(user
								.getFirstName());
						// LastName
						if (user.getLastName() != null) {
							adminManageTechAssociateViewDetails
									.setLastName(user.getLastName());
						}
						// FullName
						if (user.getFirstName() != null
								&& user.getLastName() != null) {
							adminManageTechAssociateViewDetails
									.setFullName(user.getFirstName() + " "
											+ user.getLastName());
						} else {
							adminManageTechAssociateViewDetails
									.setFullName(user.getFirstName());
						}
						// Email
						if (userInfo.getUsername() != null) {
							adminManageTechAssociateViewDetails
									.setEmailId(userInfo.getUsername());
						}
						// PhoneNumber
						if (userInfo.getPhonenumber() != null) {
							adminManageTechAssociateViewDetails
									.setPhoneNumber(userInfo.getPhonenumber()
											.toString());
						}

						// verified
						if (userInfo.isVerified() == false
								&& userInfo.getUsername() == null) {
							adminManageTechAssociateViewDetails
									.setVerified(true);
						} else {
							adminManageTechAssociateViewDetails
									.setVerified(false);
						}

						if (user.getAddresses() != null) {
							adminManageTechAssociateViewDetails
									.setAddresses(user.getAddresses());
						}

						adminManageTechAssociateViewDetails.setCreatedDate(user
								.getCreatedDate());

						if (user.getActiveSince() != null) {
							adminManageTechAssociateViewDetails
									.setActiveSince(user.getActiveSince());
						}

						adminManageTechAssociateViewDetails.setStatus(user
								.getStatus());

						// CustomerCode
						char[] fNameArray = user.getFirstName().toUpperCase()
								.toCharArray();

						String customerCode = "BONNTH" + fNameArray[0]
								+ fNameArray[1] + fNameArray[2]
								+ String.format("%05d", userInfo.getUserid());

						adminManageTechAssociateViewDetails
								.setCustomerCode(customerCode);

						if (user.getDesc() != null) {
							adminManageTechAssociateViewDetails.setDesc(user
									.getDesc().getVal());
						}
						// Current Balance
						adminManageTechAssociateViewDetails
								.setCurrentBalance(user.getCurrentBalance());

						// Invested Balance
						adminManageTechAssociateViewDetails
								.setInvestedBalance(user.getInvestedBalance());
					}
				}
			} else {
				logger.info("User doesn't exists");
			}
		} else {
			logger.info("user id null");
		}
		logger.info("Fetching TechAssociate Information based on his Id! Success");
		return adminManageTechAssociateViewDetails;
	}

	@Override
	public UserSummaryViewDetails getManageFieldAssociateDetailsView(
			String userId) {

		logger.info("Fetching FieldAssociate Information based on Id : {}",
				userId);
		UserSummaryViewDetails adminManageFieldAssociateViewDetails = null;
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findOne(userId);
			UserInfo userInfo = userRepository.findByMongoUserId(userId);
			if (user != null && userInfo != null) {
				Set<Role> enduserRoles = user.getRoles();
				for (Role enduserRole : enduserRoles) {
					if (enduserRole.getName()
							.equalsIgnoreCase("FIELDASSOCIATE")) {
						adminManageFieldAssociateViewDetails = new UserSummaryViewDetails();
						// UserId
						adminManageFieldAssociateViewDetails.setUserId(user
								.getId());
						// FirstName
						adminManageFieldAssociateViewDetails.setFirstName(user
								.getFirstName());
						// LastName
						if (user.getLastName() != null) {
							adminManageFieldAssociateViewDetails
									.setLastName(user.getLastName());
						}
						// FullName
						if (user.getFirstName() != null
								&& user.getLastName() != null) {
							adminManageFieldAssociateViewDetails
									.setFullName(user.getFirstName() + " "
											+ user.getLastName());
						} else {
							adminManageFieldAssociateViewDetails
									.setFullName(user.getFirstName());
						}
						// Email
						if (userInfo.getUsername() != null) {
							adminManageFieldAssociateViewDetails
									.setEmailId(userInfo.getUsername());
						}
						// PhoneNumber
						if (userInfo.getPhonenumber() != null) {
							adminManageFieldAssociateViewDetails
									.setPhoneNumber(userInfo.getPhonenumber()
											.toString());
						}

						// verified
						if (userInfo.isVerified() == false
								&& userInfo.getUsername() == null) {
							adminManageFieldAssociateViewDetails
									.setVerified(true);
						} else {
							adminManageFieldAssociateViewDetails
									.setVerified(false);
						}

						if (user.getAddresses() != null) {
							adminManageFieldAssociateViewDetails
									.setAddresses(user.getAddresses());
						}

						adminManageFieldAssociateViewDetails
								.setCreatedDate(user.getCreatedDate());

						if (user.getActiveSince() != null) {
							adminManageFieldAssociateViewDetails
									.setActiveSince(user.getActiveSince());
						}

						adminManageFieldAssociateViewDetails.setStatus(user
								.getStatus());

						// CustomerCode
						char[] fNameArray = user.getFirstName().toUpperCase()
								.toCharArray();

						String customerCode = "BONNFA" + fNameArray[0]
								+ fNameArray[1] + fNameArray[2]
								+ String.format("%05d", userInfo.getUserid());

						adminManageFieldAssociateViewDetails
								.setCustomerCode(customerCode);

						if (user.getDesc() != null) {
							adminManageFieldAssociateViewDetails.setDesc(user
									.getDesc().getVal());
						}
						// Current Balance
						adminManageFieldAssociateViewDetails
								.setCurrentBalance(user.getCurrentBalance());

						// Invested Balance
						adminManageFieldAssociateViewDetails
								.setInvestedBalance(user.getInvestedBalance());
					}
				}
			} else {
				logger.info("User doesn't exists");
			}
		} else {
			logger.info("user id null");
		}
		logger.info("Fetching FieldAssociate Information based on his Id! Success");
		return adminManageFieldAssociateViewDetails;
	}

	/**
	 * Method to get Admin Manage Business Associate details
	 */
	@Override
	public UserSummaryViewDetails getManageBusinessAssociateDetailsView(
			String userId) {
		logger.info("Fetching BusinessAssociate Information based on BusinessAssociateId");
		UserSummaryViewDetails adminManageBusinessAssociateViewDetails = null;
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findOne(userId);
			UserInfo userInfo = userRepository.findByMongoUserId(userId);
			if (user != null && userInfo != null) {
				Set<Role> businessAssociateRoles = user.getRoles();
				for (Role businessAssociateRole : businessAssociateRoles) {
					if (businessAssociateRole.getName().equalsIgnoreCase(
							"BUSINESSASSOCIATE")) {
						adminManageBusinessAssociateViewDetails = new UserSummaryViewDetails();
						// userId
						adminManageBusinessAssociateViewDetails.setUserId(user
								.getId());
						// firstname
						adminManageBusinessAssociateViewDetails
								.setFirstName(user.getFirstName());
						// lastname
						if (user.getLastName() != null) {
							adminManageBusinessAssociateViewDetails
									.setLastName(user.getLastName());
						}
						// FullName
						if (user.getFirstName() != null
								&& user.getLastName() != null) {
							adminManageBusinessAssociateViewDetails
									.setFullName(user.getFirstName() + " "
											+ user.getLastName());
						} else {
							adminManageBusinessAssociateViewDetails
									.setFullName(user.getFirstName());
						}

						// Address
						if (user.getAddresses() != null) {
							adminManageBusinessAssociateViewDetails
									.setAddresses(user.getAddresses());
						}

						// PAN number
						if (user.getPanNumber() != null) {
							adminManageBusinessAssociateViewDetails
									.setPanNumber(user.getPanNumber());
						}
						// Vat Number
						if (user.getVatNumber() != null) {
							adminManageBusinessAssociateViewDetails
									.setVatNumber(user.getVatNumber());
						}
						// Bank Name
						if (user.getBankName() != null) {
							adminManageBusinessAssociateViewDetails
									.setBankName(user.getBankName());
						}
						// Bank Account Number
						if (user.getBankAccountNumber() != null) {
							adminManageBusinessAssociateViewDetails
									.setBankAccountNumber(user
											.getBankAccountNumber());
						}
						if (user.getIfscCode() != null) {
							adminManageBusinessAssociateViewDetails
									.setIfscCode(user.getIfscCode());
						}
						// Email
						if (userInfo.getUsername() != null) {
							adminManageBusinessAssociateViewDetails
									.setEmailId(userInfo.getUsername());
						}
						// PhoneNumber
						if (userInfo.getPhonenumber() != null) {
							adminManageBusinessAssociateViewDetails
									.setPhoneNumber(userInfo.getPhonenumber()
											.toString());
						}

						// verified
						if (userInfo.isVerified() == false
								&& userInfo.getUsername() == null) {
							adminManageBusinessAssociateViewDetails
									.setVerified(true);
						} else {
							adminManageBusinessAssociateViewDetails
									.setVerified(false);
						}

						// created date
						adminManageBusinessAssociateViewDetails
								.setCreatedDate(user.getCreatedDate());

						if (user.getActiveSince() != null) {
							adminManageBusinessAssociateViewDetails
									.setActiveSince(user.getActiveSince());
						}

						// customer code
						char[] fNameArray = user.getFirstName().toUpperCase()
								.toCharArray();

						String customerCode = "BONNBA" + fNameArray[0]
								+ fNameArray[1] + fNameArray[2]
								+ String.format("%05d", userInfo.getUserid());

						adminManageBusinessAssociateViewDetails
								.setCustomerCode(customerCode);

						// Balance
						if (user.getInvestedBalance() != null) {
							adminManageBusinessAssociateViewDetails
									.setInvestedBalance(user
											.getInvestedBalance());
						}

						if (user.getCurrentBalance() != null) {
							adminManageBusinessAssociateViewDetails
									.setCurrentBalance(user.getCurrentBalance());
						}
						// status
						adminManageBusinessAssociateViewDetails.setStatus(user
								.getStatus());
						// user description
						if (user.getDesc() != null) {
							adminManageBusinessAssociateViewDetails
									.setDesc(user.getDesc().getVal());
						}
						// no shortlisted products
						WishList wishList = wishListDAO.findByUser(user);

						if (wishList != null
								&& wishList.getProductIds().size() != 0) {
							adminManageBusinessAssociateViewDetails
									.setTotalshortlistedProducts(wishList
											.getProductIds().size());
						}
						// number of products purchased
						if (userInfo != null) {
							List<Order> orderList = userOrderRepository
									.findByUserInfo(userInfo);
							if (orderList != null && orderList.size() != 0) {
								adminManageBusinessAssociateViewDetails
										.setTotalPurchasedOrders(orderList
												.size());
							}
						}
					}
				}
			} else {
				logger.info("User doesn't exists");
			}
		} else {
			logger.info("user id null");
		}
		logger.info("Fetching BusinessAssociate Information based on BusinessAssociateId! Success");
		return adminManageBusinessAssociateViewDetails;
	}

	/**
	 * Method to perform update operation update is performed based on userid.
	 * here firstName,lastName and sex is updated
	 * 
	 * @param userid
	 * @param userUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public User updateUser(ApiUser apiUser, String userId, String userName,
			String mobileNumber, String firstName, String lastName, String sex,
			String bankName, String bankAccountNumber, String panNumber,
			String vatNumber, String websiteUrl, String status, String desc,
			String contactName, String ifscCode, Set<String> otherSkillSets,
			Set<String> operatingCities, boolean updateUserInfoByAdmin,
			String title) throws Exception {
		logger.info("Updating userInfo based on userId : {}", userId);
		User user = null;
		UserInfo userInfo = null;
		if (StringUtils.isNotBlank(userId)) {
			user = userDAO.findOne(userId);
			userInfo = userRepository.findByMongoUserId(userId);
			if (user != null && userInfo != null) {

				if (StringUtils.isNotBlank(firstName)) {
					if (logger.isDebugEnabled()) {
						logger.debug("Updating firstName : {}", firstName);
					}

					user.setFirstName(firstName);

					userInfo.setFirstname(firstName);

					if (user.getLastName() != null) {
						user.setFullName(firstName + " " + user.getLastName());
					} else {
						user.setFullName(firstName);
					}

					Store store = storeDAO.findByUser(user);
					if (store != null) {
						store.setStorename(firstName);
						storeDAO.save(store);
					}
				}

				if (StringUtils.isNotBlank(lastName)) {
					if (logger.isDebugEnabled()) {
						logger.debug("Updating lastName : {}", lastName);
					}
					user.setLastName(lastName);

					userInfo.setLastname(lastName);

					user.setFullName(user.getFirstName() + " " + lastName);
				}

				// Admin updating user email id here need to send otp to user
				if (user.getUsername() != null
						&& StringUtils.isNotBlank(userName)) {
					if (!userName.equalsIgnoreCase(user.getUsername())) {
						if (StringUtils.isNotBlank(userName)
								&& updateUserInfoByAdmin == true) {
							if (logger.isDebugEnabled()) {
								logger.debug("Updating userName : {} by Admin",
										userName);
							}
							boolean validUserName = EmailValidator
									.getInstance().isValid(userName);

							if (!validUserName) {
								logger.error("Unable to update  username, username is not a valid email id");
								throw new Exception(
										"Username is not a valid email id");
							}

							User userVerify = userDAO.findByUsername(userName
									.toLowerCase());

							if (userVerify != null) {
								logger.error("Unable to update  username, username is already exists in the Database");
								throw new Exception(
										"Duplicate username, a user with same username already existed");
							} else {
								userInfo.setUsername(userName.toLowerCase());
								userRepository.save(userInfo);
								user.setUsername(userName);
							}
						}
						// user directly updating his email id
						else if (StringUtils.isNotBlank(userName)
								&& updateUserInfoByAdmin == false) {
							if (logger.isDebugEnabled()) {
								logger.debug("Updating userName : {}", userName);
							}
							boolean validUserName = EmailValidator
									.getInstance().isValid(userName);

							if (!validUserName) {
								logger.error("Unable to update  username, username is not a valid email id");
								throw new Exception(
										"Username is not a valid email id");
							}
							User userVerify = userDAO.findByUsername(userName
									.toLowerCase());
							if (userVerify != null) {
								logger.error("Unable to update  username, username is already exists in the Database");
								throw new Exception(
										"Duplicate username, a user with same username already existed");
							} else {
								if (userInfo != null) {

									userInfo.setUserchangedelta("username="
											+ userName.toLowerCase() + ":");

									userRepository.save(userInfo);
								}
							}
						}
					}
				} else {

					if (StringUtils.isNotBlank(userName)
							&& updateUserInfoByAdmin == true) {
						if (logger.isDebugEnabled()) {
							logger.debug("Updating userName : {} by Admin",
									userName);
						}
						boolean validUserName = EmailValidator.getInstance()
								.isValid(userName);

						if (!validUserName) {
							logger.error("Unable to update  username, username is not a valid email id");
							throw new Exception(
									"Username is not a valid email id");
						}

						User userVerify = userDAO.findByUsername(userName
								.toLowerCase());

						if (userVerify != null) {
							logger.error("Unable to update  username, username is already exists in the Database");
							throw new Exception(
									"Duplicate username, a user with same username already existed");
						} else {
							userInfo.setUsername(userName.toLowerCase());
							userRepository.save(userInfo);
							user.setUsername(userName.toLowerCase());
						}
					}
					// user directly updating his email id
					else if (StringUtils.isNotBlank(userName)
							&& updateUserInfoByAdmin == false) {
						if (logger.isDebugEnabled()) {
							logger.debug("Updating userName : {}", userName);
						}
						boolean validUserName = EmailValidator.getInstance()
								.isValid(userName);

						if (!validUserName) {
							logger.error("Unable to update  username, username is not a valid email id");
							throw new Exception(
									"Username is not a valid email id");
						}
						User userVerify = userDAO.findByUsername(userName
								.toLowerCase());
						if (userVerify != null) {
							logger.error("Unable to update  username, username is already exists in the Database");
							throw new Exception(
									"Duplicate username, a user with same username already existed");
						} else {
							if (userInfo != null) {

								userInfo.setUserchangedelta("username="
										+ userName.toLowerCase() + ":");

								userRepository.save(userInfo);
							}
						}
					}
				}

				// Admin updating user mobile number
				if (user.getUserPhoneNumber() != null
						&& StringUtils.isNotBlank(mobileNumber)) {
					if (!mobileNumber.equals(user.getUserPhoneNumber())) {
						if (StringUtils.isNotBlank(mobileNumber)
								&& updateUserInfoByAdmin == true) {
							if (StringUtils.isNotBlank(mobileNumber)) {
								if (logger.isDebugEnabled()) {
									logger.debug(
											"Updating mobile number : {} by admin",
											mobileNumber);
								}

								boolean validPhoneNumber = validatePhoneNumber(mobileNumber);

								if (!validPhoneNumber) {
									logger.error("Unable to update  mobile number it's in invalid format");
									throw new Exception(
											"Unable to update  mobile number it's in invalid format");
								}

								User userVerify = userDAO
										.findByUserPhoneNumber(mobileNumber);

								if (userVerify != null) {
									logger.error("Unable to update the mobile number, user is already exists in the Database for this mobile number");
									throw new Exception(
											"Unable to update the mobile number, user is already exists in the Database for this mobile number");
								} else {

									userInfo.setPhonenumber(Long
											.valueOf(mobileNumber));
									userRepository.save(userInfo);
									user.setUserPhoneNumber(mobileNumber);
								}

							}
						} else if (StringUtils.isNotBlank(mobileNumber)
								&& updateUserInfoByAdmin == false) {

							if (StringUtils.isNotBlank(mobileNumber)) {
								if (logger.isDebugEnabled()) {
									logger.debug("Updating mobile number : {}",
											mobileNumber);
								}

								boolean validPhoneNumber = validatePhoneNumber(mobileNumber);

								if (!validPhoneNumber) {
									logger.error("Unable to update  mobile number it's in invalid format");
									throw new Exception(
											"Unable to update  mobile number it's in invalid format");
								}

								User userVerify = userDAO
										.findByUserPhoneNumber(mobileNumber);

								if (userVerify != null) {
									logger.error("Unable to update the mobile number, user is already exists in the Database for this mobile number");
									throw new Exception(
											"Unable to update the mobile number, user is already exists in the Database for this mobile number");
								} else {

									userInfo.setUserchangedelta("phonenumber="
											+ mobileNumber + ":");

									userRepository.save(userInfo);
								}

							}
						}
					}
				} else {

					if (StringUtils.isNotBlank(mobileNumber)
							&& updateUserInfoByAdmin == true) {
						if (StringUtils.isNotBlank(mobileNumber)) {
							if (logger.isDebugEnabled()) {
								logger.debug(
										"Updating mobile number : {} by admin",
										mobileNumber);
							}

							boolean validPhoneNumber = validatePhoneNumber(mobileNumber);

							if (!validPhoneNumber) {
								logger.error("Unable to update  mobile number it's in invalid format");
								throw new Exception(
										"Unable to update  mobile number it's in invalid format");
							}

							User userVerify = userDAO
									.findByUserPhoneNumber(mobileNumber);

							if (userVerify != null) {
								logger.error("Unable to update the mobile number, user is already exists in the Database for this mobile number");
								throw new Exception(
										"Unable to update the mobile number, user is already exists in the Database for this mobile number");
							} else {

								userInfo.setPhonenumber(Long
										.valueOf(mobileNumber));
								userRepository.save(userInfo);
								user.setUserPhoneNumber(mobileNumber);
							}

						}
					} else if (StringUtils.isNotBlank(mobileNumber)
							&& updateUserInfoByAdmin == false) {

						if (StringUtils.isNotBlank(mobileNumber)) {
							if (logger.isDebugEnabled()) {
								logger.debug("Updating mobile number : {}",
										mobileNumber);
							}

							boolean validPhoneNumber = validatePhoneNumber(mobileNumber);

							if (!validPhoneNumber) {
								logger.error("Unable to update  mobile number it's in invalid format");
								throw new Exception(
										"Unable to update  mobile number it's in invalid format");
							}

							User userVerify = userDAO
									.findByUserPhoneNumber(mobileNumber);

							if (userVerify != null) {
								logger.error("Unable to update the mobile number, user is already exists in the Database for this mobile number");
								throw new Exception(
										"Unable to update the mobile number, user is already exists in the Database for this mobile number");
							} else {

								userInfo.setUserchangedelta("phonenumber="
										+ mobileNumber + ":");

								userRepository.save(userInfo);
							}

						}
					}
				}

				if (StringUtils.isNotBlank(ifscCode)) {
					user.setIfscCode(ifscCode);
				}

				// Updating ServiceProvider other skills
				if (otherSkillSets != null && otherSkillSets.size() != 0) {
					user.setOtherSkillSets(otherSkillSets);
				}

				// Updating ServiceProvider intrested working cities
				if (operatingCities != null && operatingCities.size() != 0) {
					user.setOperatingCities(operatingCities);
				}

				if (StringUtils.isNotBlank(sex)) {
					if (logger.isDebugEnabled()) {
						logger.debug("Updating gender info : {}", sex);
					}
					user.setSex(sex);
				}
				if (StringUtils.isNotBlank(bankName)) {
					if (logger.isDebugEnabled()) {
						logger.debug("Updating bankName : {}", bankName);
					}
					user.setBankName(bankName);
				}
				if (StringUtils.isNotBlank(bankAccountNumber)) {
					if (logger.isDebugEnabled()) {
						logger.debug("Updating bankAccountNumber : {}",
								bankAccountNumber);
					}
					user.setBankAccountNumber(bankAccountNumber);
				}
				if (StringUtils.isNotBlank(panNumber)) {
					if (logger.isDebugEnabled()) {
						logger.debug("Updating panNumber : {}", panNumber);
					}
					user.setPanNumber(panNumber);
				}
				if (StringUtils.isNotBlank(vatNumber)) {
					if (logger.isDebugEnabled()) {
						logger.debug("Updating vatNumber : {}", vatNumber);
					}
					user.setVatNumber(vatNumber);
				}
				if (StringUtils.isNotBlank(websiteUrl)) {
					if (logger.isDebugEnabled()) {
						logger.debug("Updating websiteUrl : {}", websiteUrl);
					}
					user.setWebsiteUrl(websiteUrl);
				}
				if (StringUtils.isNotBlank(status)) {

					if (user.getStatus().equals(UserStatus.ACTIVE)
							&& status.equalsIgnoreCase("ACTIVE")) {
						logger.info("User already in Active state... since"
								+ user.getActiveSince());
					} else if (user.getStatus().equals(UserStatus.INACTIVE)
							&& status.equalsIgnoreCase("INACTIVE")) {
						logger.info("User already in InActive state");
					} else {
						if (logger.isDebugEnabled()) {
							logger.debug("Updating user status : {}", status);
						}
						user.setStatus(UserStatus.valueOf(status));

						if (status.equals("ACTIVE")) {
							user.setActiveSince(new Date());

							userCommunicationService
									.sendVerifyAndWecomeInfo(user);

							if (StringUtils.isNotBlank(userInfo.getUsername())) {
								userInfo.setVerified(true);
								userInfo.setUserNameVerified(true);
							}

							if (StringUtils.isNotBlank(userInfo
									.getPhonenumber().toString())) {
								userInfo.setVerified(true);
								userInfo.setUserPhoneVerified(true);
							}
						}
					}
				}
				if (StringUtils.isNotBlank(desc)) {
					if (logger.isDebugEnabled()) {
						logger.debug("Updating user description : {}", desc);
					}
					Description descr = null;
					if (user.getDesc() != null) {
						descr = user.getDesc();
						descr.setVal(desc);
					} else {
						descr = new Description();
						descr.setLang("Eng");
						descr.setVal(desc);
					}
					user.setDesc(descr);
				}

				if (StringUtils.isNotBlank(contactName)) {
					user.setContactName(contactName);
				}

				if (StringUtils.isNotBlank(title)) {
					user.setTitle(title);
				}

				userDAO.save(user);

				userRepository.save(userInfo);

				String userUpdatedInfo = "User " + userInfo.getUserid()
						+ " information is updated successfully";
				// User info auditing
				updateUserAudit(userInfo, "USERINFO", userUpdatedInfo);

				logger.info("UserInfo updated successfully for userId : {}",
						userId);
			} else {
				logger.error("User is not exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId);
			}
		} else {
			logger.error("UserId should not be null");
			throw new Exception("UserId should not be null");
		}
		logger.info("Updating userInfo based on userId! Success");
		return user;
	}

	/**
	 * method to delete the user here user is deleted based on userid.
	 * 
	 * @param userid
	 * @return
	 * @throws Exception
	 * @throws MBGAppException
	 */

	@Override
	public User deleteUser(String userId) throws Exception {
		logger.info("Deleting user based on userId");

		User user = null;
		if (StringUtils.isNotBlank(userId)) {
			user = userDAO.findOne(userId);
			UserInfo userInfo = userRepository.findByMongoUserId(userId);
			if (user != null && userInfo != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("Deleting user based on userId : {}", userId);
				}
				userDAO.delete(user);

			} else {
				logger.error(
						"User does not exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User does not exist in our system for userId : "
								+ userId);
			}
		} else {
			logger.error("User id should not be empty");
			throw new Exception("User id should not be empty");
		}
		logger.info("User Deleted successfully");
		return user;
	}

	/**
	 * Method to get particular address of the user
	 * 
	 * @throws Exception
	 * 
	 */
	@Override
	public Address getUserAddressById(String userId, String addrsId)
			throws Exception {
		logger.info(
				"Fetching user address based on addressId : {} and userId : {}",
				addrsId, userId);
		User user = null;
		Address returnAddress = null;
		if (StringUtils.isNotBlank(userId)) {
			user = userDAO.findById(userId);
			if (user != null) {
				// Iterate all the addresses
				for (Address address : user.getAddresses()) {
					// Get the address Id
					String aid1 = String.valueOf(address.getAddressId());
					// Check for the match of Address ID
					if (aid1.equals(addrsId)) {
						returnAddress = address;
					}
				}
			} else {
				logger.error("User is not exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId);
			}
		} else {
			logger.error("UserId should not be null");
			throw new Exception("UserId should not be null");
		}
		logger.info("Fetching user address based on addressId! Success");
		return returnAddress;
	}

	/**
	 * Method to perform update operation update is performed based on
	 * userid,addressId,addressLine1,addressLine2,city,state
	 * country,zipcode,email.
	 * 
	 * @param userId
	 * @param addrId
	 * @param addressUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@Override
	public Set<Address> updateUserAddress(String userId, String addrsId,
			String name, String contactPersonName, String addressLine1,
			String addressLine2, String city, String state, String country,
			String zipcode, String email, String phoneNumber,
			String mobileNumber, String officeType, String mobileType)
			throws Exception {
		logger.info(
				"Updating user address based on addressId : {} and userId : {}",
				addrsId, userId);
		User user = null;
		UserInfo userInfo = null;
		Set<Address> addressList = new HashSet<>();
		if (StringUtils.isNotBlank(userId)) {
			user = userDAO.findOne(userId);
			userInfo = userRepository.findByMongoUserId(userId);
			if (user != null && userInfo != null) {
				Set<Address> addressList1 = user.getAddresses();
				for (Address address : addressList1) {
					String aid1 = String.valueOf(address.getAddressId());
					if (StringUtils.isNotBlank(addrsId)
							&& addrsId.equalsIgnoreCase(aid1)) {

						if (StringUtils.isNotBlank(addrsId)) {
							address.setAddressId(Integer.parseInt(addrsId));
						}
						if (StringUtils.isNotBlank(name)) {
							address.setName(name);
						}
						if (StringUtils.isNotBlank(contactPersonName)) {
							address.setContactPersonName(contactPersonName);
						}
						if (StringUtils.isNotBlank(addressLine1)) {
							address.setAddressLine1(addressLine1);
						}
						if (StringUtils.isNotBlank(addressLine2)) {
							address.setAddressLine2(addressLine2);
						}
						if (StringUtils.isNotBlank(city)) {
							address.setCity(city);
						}
						if (StringUtils.isNotBlank(state)) {
							address.setState(state);
						}
						if (StringUtils.isNotBlank(country)) {
							address.setCountry(country);
						}
						if (StringUtils.isNotBlank(zipcode)) {
							address.setZipcode(Integer.parseInt(zipcode));
						}
						if (StringUtils.isNotBlank(email)) {
							userValidation.validateEmail(email);
							address.setEmail(email);
						}

						// lat and log implementation
						Point point1 = null;

						// Based on address some time point(lat and long) value
						// is not coming properly so below lines of code is
						// commented and calculating point based on zipcode

						// String addressPoint = address.getAddressLine1() + ","
						// + address.getAddressLine2() + ","
						// + address.getCity() + "," + address.getState()
						// + "," + address.getCountry() + ","
						// + address.getZipcode();
						// point1 = addressLookupService
						// .getGeoCodedAddress(addressPoint);
						// if (point1 != null) {
						// address.setLocation(point1);
						// } else {
						// logger.info("based on Address search lat and lon null ");
						// point1 = addressLookupService
						// .getGeoCodedAddress(Integer
						// .parseInt(zipcode));
						// if (point1 != null) {
						// address.setLocation(point1);
						// }
						// }

						if (StringUtils.isNotBlank(zipcode)) {
							point1 = addressLookupService
									.getGeoCodedAddress(Integer
											.parseInt(zipcode));
							if (point1 != null) {
								address.setLocation(point1);
							}
						}

						Set<Phone> numbers = new HashSet<>();
						Set<Phone> phoneNumberSet = address.getPhoneNumbers();
						boolean officeAddrFirstTime = true;
						for (Phone phone : phoneNumberSet) {
							PhoneType pty = phone.getType();
							if (pty == PhoneType.OFFICE) {
								if (StringUtils.isNotBlank(officeType)
										&& StringUtils.isNotBlank(phoneNumber)) {
									PhoneType pType1 = PhoneType
											.valueOf(officeType.toUpperCase());
									if (pType1 == phone.getType().OFFICE) {
										phone.setNumber(phoneNumber);
										phone.setType(pType1);
										phone.setPrimary(false);
										officeAddrFirstTime = false;
									}
								}
							} else if (pty == PhoneType.MOBILE) {
								if (StringUtils.isNotBlank(mobileType)
										&& StringUtils.isNotBlank(mobileNumber)) {
									PhoneType pType2 = PhoneType
											.valueOf(mobileType.toUpperCase());
									if (pType2 == phone.getType().MOBILE) {
										phone.setNumber(mobileNumber);
										phone.setType(pType2);
										phone.setPrimary(true);
									}
								}
							}
							numbers.add(phone);
						}

						if (officeAddrFirstTime) {
							if (StringUtils.isNotBlank(officeType)
									&& StringUtils.isNotBlank(phoneNumber)) {
								PhoneType pType1 = PhoneType.valueOf(officeType
										.toUpperCase());
								Phone newPhone = null;
								if (pType1 == PhoneType.OFFICE) {
									newPhone = new Phone();
									newPhone.setNumber(phoneNumber);
									newPhone.setType(pType1);
									newPhone.setPrimary(false);
									numbers.add(newPhone);
								}
							}
						}

						address.setPhoneNumbers(numbers);
					}
					addressList.add(address);
				}
				user.setAddresses(addressList);
				userDAO.save(user);

				String userAddressUpdatedInfo = "User " + userInfo.getUserid()
						+ " address is updated successfully";
				// User info auditing
				updateUserAudit(userInfo, "USERADDRESS", userAddressUpdatedInfo);

			} else {

				logger.error("User is not exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId);
			}
		} else {
			logger.error("User Id should not be null");
			throw new Exception("User Id should not be null");
		}
		logger.info("Updating user address based on addressId! Success");
		return addressList;
	}

	/**
	 * Method to perform search operationsearch is performed based on
	 * userid,addressId,addressLine1,addressLine2,city,state
	 * country,zipcode,email.
	 * 
	 * @param userId
	 * @param addressSearchRequestParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public Set<Address> searchUserAddress(String userId, String addressLine1,
			String addressLine2, String city, String state, String country,
			String zipcode, String email) throws Exception {
		logger.info("Fetching user addresses based on userId : {}", userId);
		List<User> users = new LinkedList<User>();
		Set<Address> address = new HashSet<>();
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findOne(userId);
			if (user != null) {
				if (logger.isDebugEnabled()) {
					logger.debug(
							"Fetching user addresses based on userId : {}",
							userId);
				}
				if (StringUtils.isBlank(addressLine1)
						&& StringUtils.isBlank(addressLine2)
						&& StringUtils.isBlank(city)
						&& StringUtils.isBlank(state)
						&& StringUtils.isBlank(country)
						&& StringUtils.isBlank(zipcode)
						&& StringUtils.isBlank(email)) {
					address = user.getAddresses();
				} else {
					Page<User> pageable = userDAO
							.findByAddresses_AddressLine1OrAddresses_AddressLine2OrAddresses_CityOrAddresses_CountryOrAddresses_StateOrAddresses_ZipcodeOrAddresses_Email(
									addressLine1, addressLine2, city, country,
									state, Integer.parseInt(zipcode), email,
									new PageRequest(0, 10));
					users.addAll(pageable.getContent());
					address = pageable.getContent().get(0).getAddresses();
				}
			} else {
				logger.error("User is not exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId);
			}
		} else {
			logger.error("UserId should not be null");
			throw new Exception("UserId should not be null");
		}
		logger.info("Fetching user addresses based on userId! Success");
		return address;
	}

	/**
	 * Method to delete user addressuser address is deleted based on userId and
	 * addressId
	 * 
	 * @param userId
	 * @param aid
	 * @return
	 * @throws Exception
	 */
	@Override
	public String deleteUserAddresses(String userId, String addrsId)
			throws Exception {
		logger.info("deleting user address based on addressId");
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findOne(userId);
			if (user != null) {
				for (Address address : user.getAddresses()) {
					String aid1 = String.valueOf(address.getAddressId());
					if (aid1.equals(addrsId)) {
						user.getAddresses().remove(address);
						userDAO.save(user);
						break;
					}
				}
			} else {
				logger.error("User is not exist in our system for userId : ",
						userId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId);
			}
		} else {
			logger.error("User Id should not be null");
			throw new Exception("User Id should not be null");
		}
		logger.info("deleting user address based on addressId! Success");
		return addrsId;
	}

	/**
	 * Method to add new Address
	 * 
	 * @param uId
	 * @param aId
	 * @param addrLine1
	 * @param addrLine2
	 * @param city
	 * @param state
	 * @param country
	 * @param zipcode
	 * @param email
	 * @param point
	 * @return
	 * @throws Exception
	 */
	@Override
	public Address createAddress(String userId, String name,
			String contactPersonName, String addrLine1, String addrLine2,
			String city, String state, String country, String zipcode,
			String contactNumber, String branchTelNumber, String email)
			throws Exception {
		logger.info("Creating user address for userId : {}", userId);
		User user = null;
		Address address = null;
		String roleName = null;
		if (StringUtils.isNotBlank(userId)) {
			user = userDAO.findOne(userId);
			if (user != null) {
				address = new Address();

				Set<Address> addresses = user.getAddresses();
				int addressesId = 0;
				for (Address addr : addresses) {
					if (addressesId < addr.getAddressId()) {
						addressesId = addr.getAddressId();
					}
				}
				address.setAddressId((addressesId + 1));

				if (StringUtils.isNotBlank(name)) {
					address.setName(name);
				}
				if (StringUtils.isNotBlank(contactPersonName)) {
					address.setContactPersonName(contactPersonName);
				}
				if (StringUtils.isNotBlank(addrLine1)) {
					address.setAddressLine1(addrLine1);
				}
				if (StringUtils.isNotBlank(addrLine2)) {
					address.setAddressLine2(addrLine2);
				}
				if (StringUtils.isNotBlank(city)) {
					address.setCity(city);
				}
				if (StringUtils.isNotBlank(state)) {
					address.setState(state);
				}
				if (StringUtils.isNotBlank(country)) {
					address.setCountry(country);
				}
				if (StringUtils.isNotBlank(zipcode)) {
					address.setZipcode(Integer.parseInt(zipcode));
				}
				if (StringUtils.isNotBlank(email)) {
					userValidation.validateEmail(email);
					address.setEmail(email);
				}
				Set<Phone> phoneSet = new HashSet<>();
				if (StringUtils.isNotBlank(contactNumber)) {
					Phone phone1 = new Phone();
					phone1.setNumber(contactNumber);
					phone1.setType(PhoneType.MOBILE);
					phone1.setPrimary(true);
					phoneSet.add(phone1);
				}
				if (StringUtils.isNotBlank(branchTelNumber)) {
					Phone phone2 = new Phone();
					phone2.setNumber(branchTelNumber);
					phone2.setType(PhoneType.OFFICE);
					phone2.setPrimary(false);
					phoneSet.add(phone2);
				}
				address.setPhoneNumbers(phoneSet);
				// lat and log implementation
				Point point1 = null;
				String addressPoint = addrLine1 + "," + addrLine2 + "," + city
						+ "," + state + "," + country + "," + zipcode;
				point1 = addressLookupService.getGeoCodedAddress(addressPoint);
				if (point1 != null) {
					address.setLocation(point1);
				} else {
					point1 = addressLookupService.getGeoCodedAddress(Integer
							.parseInt(zipcode));
					if (point1 != null) {
						address.setLocation(point1);
					}
				}

				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					roleName = role.getName();
				}

				addresses.add(address);
				user.setAddresses(addresses);
				userDAO.save(user);

				// Right now we are not using package structure so following
				// code is commented
				// if (roleName.equalsIgnoreCase("PROVIDER")
				// || roleName.equalsIgnoreCase("DEALER")
				// || roleName.equalsIgnoreCase("ARCHITECT")
				// || roleName.equalsIgnoreCase("INTERIORDESIGNER")) {
				// if (privilegeService.checkPrivilege(userId, "ADD",
				// "ADDRESS")) {
				// user.addAddress(address);
				// logger.info("Adding user Address");
				// } else {
				// throw new Exception(
				// "Cannot Add Address. Kindly upgrade your package");
				// }
				// } else {
				// user.addAddress(address);
				// logger.info("Adding user Address");
				// }

			} else {
				logger.error("User is not exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId);
			}

		} else {
			logger.error("User Id should not be null");
			throw new Exception("User Id should not be null");
		}
		return address;
	}

	/**
	 * Method to update user role here user rolename and defaultpackage is
	 * updated based on userid.
	 * 
	 * @param userId
	 * @param roleId
	 * @param userRoleUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public Role updateUserRole(String userId, String roleId, String roleName,
			String desc) throws Exception {
		logger.info("Updating user role based on roleId : {} and userId : {}",
				roleId, userId);
		User user = null;
		UserPackage userPackage = null;
		Role role = null;
		Set<Role> roles = new HashSet<>();
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(roleId)) {
			user = userDAO.findOne(userId);
			if (user != null) {
				role = roleDAO.findOne(roleId);
				if (role != null) {
					if (StringUtils.isNotBlank(roleName)) {
						role.setName(roleName);
					}
					if (StringUtils.isNotBlank(desc)) {
						role.getDesc().setVal(desc);
					}
					String queryString = "SILVER " + roleName;
					userPackage = userPackageDAO.findByName(queryString);
					if (userPackage != null) {
						role.setUserPackage(userPackage);
					} else {
						String queryString1 = "SILVER";
						userPackage = userPackageDAO.findByName(queryString1);
						role.setUserPackage(userPackage);
					}
					roles.add(role);
					user.setRoles(roles);
					userDAO.save(user);
				} else {
					throw new Exception("Role is null for roleId:" + roleId);
				}
			} else {
				logger.error("User is not exist for userId : {}", userId);
				throw new Exception("User is not exist for userId : " + userId);
			}
		} else {
			logger.error("User Id and role Id should not be empty");
			throw new Exception("User Id and role Id should not be empty");
		}
		logger.info("Updating user role based on roleId! Success");
		return role;
	}

	/**
	 * Method to search user role based on userid. here uid is mandatory.
	 * 
	 * @param userId
	 * @param roleName
	 * @return
	 * @throws Exception
	 */
	@Override
	public Set<Role> searchUserRole(String userId) throws Exception {
		logger.info("Fetching user role based on userId : {}", userId);
		Set<Role> roles = null;
		User user = null;
		if (StringUtils.isNotBlank(userId)) {
			user = userDAO.findOne(userId);
			if (user != null) {
				roles = user.getRoles();
			} else {
				logger.error("User is not exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId);
			}
		} else {
			logger.error("UserId should not empty");
			throw new Exception("UserId should not empty");
		}
		logger.info("Fetching user roles based on userId! Success");
		return roles;
	}

	/**
	 * method to display all ServiceProviders name.
	 * 
	 * @return
	 */
	@Override
	public Set<String> getServiceProvidersList() {
		logger.info("Fetching All ServiceProviders List in Buildonn");

		Set<String> serviceProvidersName = new HashSet<>();

		ServiceProviders[] values = ServiceProviders.values();

		for (ServiceProviders roleName : values) {
			serviceProvidersName.add(roleName.name());
		}

		logger.info("Fetching All ServiceProviders List in Buildonn! Success");
		return serviceProvidersName;
	}

	/**
	 * Method to update user phonehere user phoneNumber details is updated based
	 * on phonetype and number.
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public User updateUserPhone(String userId, String type, String number,
			String desc, String primary) throws Exception {
		logger.info("Updating user phone info based on userId : {}", userId);
		User user = null;
		if (StringUtils.isNotBlank(userId)) {
			user = userDAO.findOne(userId);
			if (user != null) {
				for (Address addr : user.getAddresses()) {
					if (addr != null) {
						for (Phone phone : addr.getPhoneNumbers()) {
							if (phone != null) {
								if (StringUtils.isNotBlank(type)) {
									PhoneType pType = PhoneType.valueOf(type
											.toUpperCase());
									if (pType != null
											&& phone.getType() == pType
											&& StringUtils.equalsIgnoreCase(
													number, phone.getNumber())) {
										phone.setType(pType);
									}
									if (StringUtils.isNotBlank(number)
											&& phone.getType() == pType) {
										phone.setNumber(number);
									}
									if (StringUtils.isNotBlank(desc)
											&& phone.getType() == pType) {
										phone.getDesc().setVal(desc);
									}
									if (StringUtils.isNotBlank(primary)) {
										phone.setPrimary(Boolean
												.valueOf(primary));
									}
								}
							}
						}
					}
					userDAO.save(user);
				}
			} else {
				logger.error("User is not exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId);
			}
		} else {
			logger.error("User Id should not be null");
			throw new Exception("User Id should not be null");
		}
		logger.info("updating user phone info! Success");
		return user;
	}

	/**
	 * Method to search user based on user phone details here user is search
	 * based on phone details.here uid is mandatory
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Set<String> searchUserPhone(String userId) throws Exception {
		logger.info("Fetching user phone info");
		Set<String> userPhoneSet = new HashSet<>();
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findOne(userId);
			if (user != null) {
				Set<Address> addresses = user.getAddresses();
				for (Address address : addresses) {
					Set<Phone> phoneSet = address.getPhoneNumbers();
					for (Phone phone : phoneSet) {
						userPhoneSet.add(phone.getNumber());
					}
				}
			} else {
				logger.error("User is not exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId);
			}
		} else {
			logger.error("UserId should not null");
			throw new Exception("UserId should not null");
		}
		logger.info("Fetching user phone info! Success");
		return userPhoneSet;
	}

	/**
	 * Method to update the user status
	 * 
	 * This method updates the status of the user and updates it based on the
	 * user input
	 * 
	 * @param userid
	 * @param userStatus
	 * @return
	 * @throws Exception
	 * @throws MBGAppException
	 */
	@Override
	public UserStatus updateUserStatus(String userId, String status)
			throws Exception {
		logger.info("Updating user status based on userId : {}", userId);
		User user = null;
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(status)) {
			user = userDAO.findOne(userId);
			if (user != null) {

				if (user.getStatus().equals(UserStatus.ACTIVE)
						&& status.equalsIgnoreCase("ACTIVE")) {
					logger.info("User already in Active state... since"
							+ user.getActiveSince());
				} else if (user.getStatus().equals(UserStatus.INACTIVE)
						&& status.equalsIgnoreCase("INACTIVE")) {
					logger.info("User already in InActive state");
				} else {
					UserInfo userInfo = userRepository.findByMongoUserId(user
							.getId());
					if (userInfo != null) {
						user.setStatus(UserStatus.valueOf(status));
						if (status.equals("ACTIVE")) {
							user.setActiveSince(new Date());
							userCommunicationService
									.sendVerifyAndWecomeInfo(user);

							if (userInfo.getUsername() != null
									&& StringUtils.isNotBlank(userInfo
											.getUsername())) {
								userInfo.setVerified(true);
								userInfo.setUserNameVerified(true);
							}
							if (userInfo.getPhonenumber() != null
									&& StringUtils.isNotBlank(userInfo
											.getPhonenumber().toString())) {
								userInfo.setVerified(true);
								userInfo.setUserPhoneVerified(true);
							}

							userRepository.save(userInfo);
						}

						String userStatusChangedInfo = "User "
								+ userInfo.getUserid()
								+ " status is successfully changed to "
								+ status + " status";
						updateUserAudit(userInfo, "STATUS",
								userStatusChangedInfo);

					}
				}

			} else {
				logger.error(
						"User is  not exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User is  not exist in our system for userId : "
								+ userId);
			}

			userDAO.save(user);
			logger.info("User status updated successfully");
		} else {
			logger.error("User Id and status sholud not be null");
			throw new Exception("User Id and status sholud not be null");
		}
		return user.getStatus();
	}

	/**
	 * 
	 * Method to search status of the user based on uid
	 * 
	 * This method returns status of the user based on the User ID
	 * 
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserStatus getUserStatus(String userId) throws Exception {
		logger.info("Fetching user status based on userId : {}", userId);
		User user = null;
		UserStatus userStatus = null;
		if (StringUtils.isNotBlank(userId)) {
			user = userDAO.findOne(userId);
			if (user != null) {
				userStatus = user.getStatus();
			} else {
				logger.error("User is not exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId);
			}
		} else {
			logger.error("User id should not be blank");
			throw new Exception("User id should not be blank");
		}
		logger.info("Fetching user status! Success");
		return userStatus;
	}

	/**
	 * Method to search the package of the user
	 * 
	 * This method return the Package assigned to the particular user
	 * 
	 * @param userid
	 * @return
	 * @throws Exception
	 * @throws MBGAppException
	 */
	@Override
	public UserPackage getUserPackage(String userId) throws Exception {
		logger.info("Fetching user package based on userId : {}", userId);
		UserPackage userPackage = null;
		Set<Role> roles = null;
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findOne(userId);
			if (user != null) {
				roles = user.getRoles();
				for (Role role : roles) {
					userPackage = role.getUserPackage();
				}
			} else {
				logger.error("User is not exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId);
			}
		} else {
			logger.error("UserId should not be null");
			throw new Exception("UserId should not be null");
		}
		logger.info("Fetching user package based on userId! Success");
		return userPackage;
	}

	/**
	 * Method to update user level package of the user
	 * 
	 * This method the updates package of the particular user
	 * 
	 * @PathParam would be the userID and roleID. PUT parameters would be the
	 *            package name and description
	 *
	 * @param userId
	 * @param roleId
	 * @param packageParam
	 * @return
	 * @throws Exception
	 * @throws MBGAppException
	 */
	@Override
	public UserPackage updateUserPackage(String userId, String pName)
			throws Exception {
		logger.info("Updating user package based on userId : {}", userId);
		UserPackage userPackage = null;
		Set<Role> roles = null;
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findOne(userId);
			if (user != null) {
				roles = user.getRoles();
				for (Role role : roles) {
					if (StringUtils.isNotBlank(pName)) {
						userPackage = userPackageDAO.findByName(pName);
						if (userPackage != null) {
							role.setUserPackage(userPackage);
						}
					}
				}
				user.setRoles(roles);
				userDAO.save(user);
			} else {
				logger.error("User is not exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId);
			}
		} else {
			logger.error("User Id should not be empty");
			throw new Exception("User Id should not be empty");
		}
		logger.info("Updating user package based on userId! Success");
		return userPackage;
	}

	// recently viewed
	/**
	 * Method to get recently viewed product It get recently viewed product
	 * Based on user Id
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public RecentlyViewedSummary getRecentlyViewedProduct(String userId,
			int page, int size, Sort sort) throws Exception {
		logger.info("Fetching RecentlyViewed based on userId : {}", userId);
		List<RecentlyViewedDetails> recentlyViewedDetailsList = null;
		RecentlyViewed recentlyViewed = null;
		Set<String> productIds = null;
		Page<Product> pageable = null;
		List<Product> products = new ArrayList<>();
		RecentlyViewedSummary recentlyViewedSummary = new RecentlyViewedSummary();
		if (StringUtils.isNotBlank(userId)) {
			recentlyViewed = recentlyViewedDAO.findByUser_Id(userId);
			if (recentlyViewed != null) {
				recentlyViewedDetailsList = new ArrayList<>();
				productIds = recentlyViewed.getProductIds();
				pageable = productDAO.findByIdIn(productIds, new PageRequest(
						page, size, sort));
				products.addAll(pageable.getContent());
				for (Product product : products) {

					if (product != null) {
						RecentlyViewedDetails recentlyViewedDetails = new RecentlyViewedDetails();
						recentlyViewedDetails.setId(product.getId());
						recentlyViewedDetails.setName(product.getName());
						if (product.getAssets().getImages() != null) {
							recentlyViewedDetails.setImage(product.getAssets()
									.getImages().get(0).getUrl());
						}
						if (product.getSubcategories() != null) {
							recentlyViewedDetails.setSubcategories(product
									.getSubcategories());
						}
						recentlyViewedDetailsList.add(recentlyViewedDetails);
					}

				}

				recentlyViewedSummary
						.setRecentlyViewedDetails(recentlyViewedDetailsList);
				recentlyViewedSummary.setTotalPages(pageable.getTotalPages());
				recentlyViewedSummary.setTotalElements(pageable
						.getTotalElements());
				recentlyViewedSummary.setNumber(pageable.getNumber());
				recentlyViewedSummary.setSize(pageable.getSize());
			}

		} else {
			logger.error("User id should not be empty");
			throw new Exception("User id should not be empty");
		}
		logger.info("Fetching RecentlyViewed! Success");
		return recentlyViewedSummary;
	}

	/**
	 * Method to create new Recently viewed
	 * 
	 * @param userId
	 * @param productIds
	 * @return
	 * @throws Exception
	 */
	@Override
	public RecentlyViewed createRecentlyViewedProduct(String uId,
			Set<String> productIds) throws Exception {
		RecentlyViewed recentlyViewed = null;
		if (StringUtils.isNotBlank(uId) && userDAO.exists(uId)) {
			recentlyViewed = recentlyViewedDAO.findByUser_Id(uId);
			if (recentlyViewed == null) {
				logger.info("creating RecentlyViewed");
				recentlyViewed = new RecentlyViewed();
				User user = userDAO.findById(uId);
				recentlyViewed.setUser(user);
				recentlyViewed.setProductIds(productIds);
				recentlyViewedDAO.insert(recentlyViewed);
				logger.info("RecentlyView created successfully");
			} else {
				logger.info("Adding products to RecentlyViewed List");
				recentlyViewed.getProductIds().addAll(productIds);
				recentlyViewedDAO.save(recentlyViewed);
				logger.debug("Product successfully added to Recently viewed list");
			}
		} else {
			logger.error("User id should not be balnk");
			throw new Exception("User id should not be balnk");
		}
		return recentlyViewed;
	}

	/**
	 * Method to delete Recently viewed
	 * 
	 * @param userId
	 * @param pIds
	 * @return
	 * @throws Exception
	 */
	@Override
	public RecentlyViewed deleteRecentlyViewedProduct(String userId,
			Set<String> pids) throws Exception {
		RecentlyViewed rv = null;
		if (pids == null) {
			logger.info("Deleting recently viewed object");
			rv = recentlyViewedDAO.findByUser_Id(userId);
			recentlyViewedDAO.delete(rv);
		} else if (pids != null && !pids.isEmpty()) {
			logger.info("Deleting recently viewed product id form the Set of recently viewed product id's");
			rv = recentlyViewedDAO.findByUser_Id(userId);
			rv.getProductIds().removeAll(pids);
			recentlyViewedDAO.save(rv);
		} else {
			logger.error("Unable to delete recently viewed product");
			throw new Exception("Unable to delete recently viewed product");
		}
		return rv;
	}

	/**
	 * Method to update recently viewed
	 * 
	 * @param userId
	 * @param productIds
	 * @return
	 * @throws Exception
	 */
	@Override
	public RecentlyViewed updateRecentlyViewedProduct(String userId,
			Set<String> productIds) throws Exception {
		logger.info("Updaing recently viewed products info for userId : {}",
				userId);
		RecentlyViewed recentlyViewed = null;
		if (StringUtils.isNotBlank(userId)) {
			recentlyViewed = recentlyViewedDAO.findByUser_Id(userId);
			recentlyViewed.getProductIds().addAll(productIds);
			recentlyViewedDAO.save(recentlyViewed);
		} else {
			logger.error("User id should not be empty");
			throw new Exception("User id should not be empty");
		}
		logger.info("Updaing recently viewed products info! Success");
		return recentlyViewed;
	}

	// Roles
	/**
	 * method for display all system roles
	 * 
	 * @return List of role object
	 */
	@Override
	public List<Role> getUserRole() {
		logger.info("Displaying all the Buildonn user roles");
		List<Role> roleList = roleDAO.findAll();
		return roleList;
	}

	/**
	 * method for admin manage roles
	 */
	@Override
	public ManageRolesResponse manageRoles() {
		logger.info("Fetching all Roles in Buildonn");
		ManageRolesResponse manageRolesResponse = new ManageRolesResponse();
		List<Role> roles = roleDAO.findAll();
		List<ManageRolesView> manageRoleViewList = new ArrayList<ManageRolesView>();
		if (roles != null) {
			for (Role role : roles) {
				if (logger.isDebugEnabled()) {
					logger.debug("RoleName : {} RoleId : {}", role.getName(),
							role.getId());
				}
				ManageRolesView manageRoleView = new ManageRolesView();
				manageRoleView.setRoleId(role.getId());
				manageRoleView.setRoleName(role.getName());
				manageRoleView.setCreatedDate(role.getCreatedDate());
				List<User> users = userDAO.findByRoles_Name(role.getName());
				manageRoleView.setNumberOfUser(users.size());
				if (users.size() != 0) {
					manageRoleView.setStatus("ACTIVE");
				} else {
					manageRoleView.setStatus("INACTIVE");
				}
				manageRoleViewList.add(manageRoleView);
			}
		}
		manageRolesResponse.setRoles(manageRoleViewList);
		manageRolesResponse.setTotalRoles(roles.size());
		logger.info("Fetching all Roles in Buildonn! Success");
		return manageRolesResponse;
	}

	/**
	 * methods for admin manage role view details
	 * 
	 * @param roleid
	 * @return
	 * @throws Exception
	 */
	@Override
	public ManageRolesView manageRoleViewDetails(String roleid)
			throws Exception {
		logger.info("Fetching Admin Manage RoleViewDetails based on roleId");
		ManageRolesView manageRolesView = null;
		if (StringUtils.isNotBlank(roleid)) {
			Role role = roleDAO.findById(roleid);
			if (role != null) {
				manageRolesView = new ManageRolesView();
				manageRolesView.setRoleId(role.getId());
				manageRolesView.setRoleName(role.getName());
				manageRolesView.setCreatedDate(role.getCreatedDate());
				manageRolesView.setDescription(role.getDesc().getVal());
				List<User> users = userDAO.findByRoles_Name(role.getName());

				manageRolesView.setNumberOfUser(users.size());
				if (users.size() != 0) {
					manageRolesView.setStatus("ACTIVE");
				} else {
					manageRolesView.setStatus("INACTIVE");
				}

			}
		} else {
			logger.error("roleId should not be null");
			throw new Exception("roleId should not be null");
		}
		logger.info("Fetching Admin Manage RoleViewDetails based on roleId! Success");
		return manageRolesView;
	}

	/**
	 * method for search user role based on role id.
	 * 
	 * @param roleId
	 * @return
	 */
	@Override
	public Role getUserRoleById(String roleId) {
		logger.info("Fetching role based on roleId");
		Role role = null;
		if (StringUtils.isNotBlank(roleId)) {
			role = roleDAO.findById(roleId);
		}
		logger.info("Fetching role based on roleId! Success");
		return role;
	}

	/**
	 * Method to count User roles
	 * 
	 * This method gives the number of roles in the system
	 * 
	 * @return
	 */
	@Override
	public long countRoles() {
		logger.info("Fetching total role count in Buildonn");
		Long count = roleDAO.count();
		logger.info("Fetching total role count in Buildonn! Success");
		return count;
	}

	/**
	 * Method to add new role
	 * 
	 * This method is to add new role in the system
	 * 
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@Override
	public Role createRole(String roleName, String desc, String userType)
			throws Exception {
		logger.info("Creating new role in Buildonn");
		Role role = new Role();
		if (StringUtils.isNotBlank(roleName) && StringUtils.isNotBlank(desc)
				&& StringUtils.isNotBlank(userType)) {
			role.setName(roleName);

			Description desc1 = new Description();
			desc1.setVal("eng");
			desc1.setVal(desc);
			role.setDesc(desc1);

			role.setType(UserType.valueOf(userType));
		} else {
			logger.error("RoleName, description and userType should not be empty");
			throw new Exception(
					"RoleName, description and userType should not be empty");
		}

		UserPackage userPack = userPackageDAO.findByName("SILVER");
		if (userPack != null) {
			role.setUserPackage(userPack);
		}
		roleDAO.insert(role);
		logger.info("New role is created successfully");
		return role;
	}

	/**
	 * Method to update user role by Id.
	 * 
	 * @param roleId
	 * @param name
	 * @param lang
	 * @param val
	 * @return
	 * @throws Exception
	 */
	@Override
	public Role updateUserRoleById(String roleId, String roleName, String desc)
			throws Exception {
		logger.info("Updating role based on roleId : {}", roleId);
		Role role = null;
		if (StringUtils.isNotBlank(roleId)) {
			role = roleDAO.findOne(roleId);
			if (role != null) {
				if (StringUtils.isNotBlank(roleName)) {
					role.setName(roleName);
				}
				if (StringUtils.isNotBlank(desc)) {
					Description des = role.getDesc();
					des.setVal(desc);
					role.setDesc(des);
				}
				roleDAO.save(role);
			} else {
				logger.error("Role is not exist for roleId : {}", roleId);
				throw new Exception("Role is not exist for roleId " + roleId);
			}
		} else {
			logger.error("Role Id should not be null");
			throw new Exception("Role Id should not be null");
		}
		logger.info("Updating role based on roleId! Success");
		return role;
	}

	/**
	 * Method to delete role by id.
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Role deleteUserRoleById(String roleId) throws Exception {
		logger.info("Deleting role based on roleId : {}", roleId);
		Role role = null;
		if (StringUtils.isNotBlank(roleId)) {
			role = roleDAO.findOne(roleId);
			if (role != null) {
				roleDAO.delete(roleId);
			} else {
				logger.error("Role is not exist for roleId : {}", roleId);
				throw new Exception("Role is not exist for roleId : " + roleId);
			}
		} else {
			logger.error("Role Id should not be null");
			throw new Exception("Role Id should not be null");
		}
		logger.info("Deleting role based on roleId response! Success");
		return role;
	}

	// UserPackage
	/**
	 * Method to perform get all the packages for user role
	 * 
	 * @return List<Packages>
	 */
	@Override
	public List<UserPackage> getUserRolePackage() {
		logger.info("Fetching all packages in Buildonn");
		List<UserPackage> userPackage = userPackageDAO.findAll();
		logger.info("Fetching all packages in Buildonn! Success");
		return userPackage;
	}

	/**
	 * Method to Create new package
	 * 
	 * This method creates new System level Package
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserPackage createPackage(String packageName, String desc,
			String privilegeName) {
		logger.info("Creating new package in Buildonn");
		UserPackage userPackage = new UserPackage();
		if (StringUtils.isNotBlank(packageName)) {
			userPackage.setName(packageName);
		}
		if (StringUtils.isNotBlank(desc)) {
			Description desc1 = new Description();
			desc1.setLang("eng");
			desc1.setVal(desc);
			userPackage.setDesc(desc1);
		}
		if (StringUtils.isNotBlank(privilegeName)) {
			Privilege privileges = privilegeDAO.findByName(privilegeName);
			userPackage.setPrivileges((Set<Privilege>) privileges);
		}
		userPackageDAO.insert(userPackage);
		logger.info("Creating new package in Buildonn! Success");
		return userPackage;
	}

	/**
	 * method for update user package, packageId is necessary field.
	 * 
	 * @param pkgid
	 * @param userPackageUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateUserRolePackage(String pkgId, String pkgName,
			String desc, String privilegeName) throws Exception {
		logger.info("Updating package info based on packageId : {}", pkgId);
		if (StringUtils.isNotBlank(pkgId)) {
			UserPackage userPackage = userPackageDAO.findOne(pkgId);
			if (userPackage != null) {
				if (StringUtils.isNotBlank(pkgName)) {
					userPackage.setName(pkgName);
				}
				if (StringUtils.isNotBlank(desc)) {
					Description desc1 = userPackage.getDesc();
					desc1.setVal("desc");
				}
				userPackageDAO.save(userPackage);
			} else {
				logger.error("Package is not exist for packageId : {}", pkgId);
				throw new Exception("Package is not exist for packageId : "
						+ pkgId);
			}
		}
		logger.info("Updating package info based on packageId! Success");
		return pkgId;
	}

	/**
	 * method for delete user package, packageId is necessary field.
	 * 
	 * @param pkgId
	 * @return
	 * @throws Exception
	 */
	@Override
	public String deletePackageById(String pkgId) throws Exception {
		logger.info("Deleting package based on packageId");
		if (StringUtils.isNotBlank(pkgId)) {
			UserPackage userPackage = userPackageDAO.findOne(pkgId);
			if (userPackage != null) {
				userPackageDAO.delete(pkgId);
			} else {
				logger.error("Package is not exist");
				throw new Exception("Package is not exist");
			}
		}
		logger.info("Deleting package based on packageId response! Success");
		return pkgId;
	}

	// WishList

	/**
	 * Method to get WishList It get WishList products Based on user Id
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public WishList getUserWishList(String uid) throws Exception {
		logger.info("Displaying user wishlist");
		WishList wishList = null;
		if (StringUtils.isNotBlank(uid)) {
			wishList = wishListDAO.findByUser_Id(uid);
		} else {
			logger.error("User Id should not be empty");
			throw new Exception("User Id should not be empty");
		}
		return wishList;
	}

	/**
	 * Method to create new WistList
	 * 
	 * @param userId
	 * @param productIds
	 * @return
	 * @throws Exception
	 */
	@Override
	public WishList createWishList(String uid, Set<String> productIds)
			throws Exception {
		WishList wishList = null;
		UserInfo userInfo = userRepository.findByMongoUserId(uid);
		if (StringUtils.isNotBlank(uid) && userDAO.exists(uid)) {
			wishList = wishListDAO.findByUser_Id(uid);
			if (wishList == null) {
				wishList = new WishList();
				User user = userDAO.findById(uid);
				wishList.setUser(user);
				wishList.setProductIds(productIds);
				wishListDAO.insert(wishList);
				logger.info("user wishlist created successfully");
			} else {
				wishList.getProductIds().addAll(productIds);
				wishListDAO.save(wishList);
				logger.info("productIds added to WishList successfully");
			}
			String productId = null;
			for (String pid : productIds) {
				productId = pid;
			}
			Product product = productDAO.findOne(productId);

			Set<SubCategory> subcategories = null;
			String productName = null;
			String categoryName = null;
			String subCategoryName = null;

			if (product != null) {
				productName = product.getName();
				subcategories = product.getSubcategories();
			}

			for (SubCategory subCategory : subcategories) {
				subCategoryName = subCategory.getName();
				categoryName = subCategory.getCategory();
			}

			String note = "User shortlisted product " + productName
					+ " Category: " + categoryName + " SubCategory: "
					+ subCategoryName;

			updateUserAudit(userInfo, "SHORTLIST", note);
		} else {
			logger.error("unable to create wishList");
			throw new Exception("unable to create wishList");
		}
		return wishList;
	}

	/**
	 * Method to update the Wishlist
	 * 
	 * This method adds/update product in the wishlist
	 * 
	 * @throws Exception
	 */
	@Override
	public WishList updateWishList(String userId, String wlId,
			Set<String> productIds) throws Exception {
		WishList wishList = null;
		if (StringUtils.isNotBlank(userId)) {
			wishList = wishListDAO.findByUser_Id(userId);
			wishList.getProductIds().addAll(productIds);
			wishListDAO.save(wishList);
			logger.info("WishList updated successfully");
		} else {
			logger.error("User id should not be blank");
			throw new Exception("User id should not be blank");
		}
		return wishList;
	}

	/**
	 * Delete product from wishlist
	 * 
	 * This method deletes the set products from the wishlist.
	 * 
	 */
	@Override
	public WishList deleteWishListProduct(String uid, String wishId,
			Set<String> pids) {
		WishList wishList = null;
		if (pids == null) {
			wishList = wishListDAO.findByUser_Id(uid);
			wishListDAO.delete(wishList);
			logger.info("user wishList deleted successfully");
		} else {
			if (pids != null && !pids.isEmpty()) {
				wishList = wishListDAO.findByUser_Id(uid);
				wishList.getProductIds().removeAll(pids);
				wishListDAO.save(wishList);
				logger.info("product Ids Removed from wishlist successfully");
			}
		}
		return wishList;
	}

	/**
	 * Method for get promotion for store.
	 * 
	 * @param dealerId
	 * @param sid
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public List<Promotion> getPromotionForStore(String uid, String sid)
			throws Exception {
		List<Promotion> promotions = null;
		if (StringUtils.isNotBlank(uid) && StringUtils.isNotBlank(sid)) {
			User user = userDAO.findById(uid);
			if (user != null) {
				Store store = storeDAO.findByUser(user);
				if (store != null) {
					logger.info("finding store promotion");
					promotions = promotionDAO.findByDealer(user);
				} else {
					logger.error("store does not exist");
					throw new Exception("store does not exist");
				}
			} else {
				logger.error("user does not exist");
				throw new Exception("user does not exist");
			}
		}
		return promotions;
	}

	/**
	 * Method to get count of promotions for the store
	 * 
	 * @param dealerid
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	@Override
	public Long getPromotionCountForUser(String uid, String sid)
			throws Exception {

		Long count = null;
		if (StringUtils.isNotBlank(uid) && StringUtils.isNotBlank(sid)) {
			User user = userDAO.findOne(uid);
			if (user != null) {
				Store store = storeDAO.findByUser(user);
				if (store != null) {
					logger.info("counting store promotion");
					count = promotionDAO.countByDealer(user);
				} else {
					logger.error("Store doesn't exists");
					throw new Exception("Store doesn't exists");
				}

			} else {
				logger.error("User doesn't exists");
				throw new Exception("User doesn't exists");
			}
		}
		return count;
	}

	/**
	 * Method for create promotion for user store.
	 * 
	 * @param dealerId
	 * @param sid
	 * @param promotionCreateRequestParam
	 * @return
	 * @throws ParseException
	 * @throws MBGAppException
	 */
	@Override
	public Promotion createPromotionForUserStore(String dealerId, String sid,
			String promotionName, String desc, String promotionType,
			Set<String> productIds, String discount, String sDate, String eDate)
			throws Exception {
		Promotion promotion = null;
		if (StringUtils.isNotBlank(dealerId)) {
			User user = userDAO.findById(dealerId);
			if (user != null) {
				if (StringUtils.isNotBlank(sid)) {
					Store store = storeDAO.findOne(sid);
					if (store != null) {
						promotion = new Promotion();
						if (StringUtils.isNotBlank(promotionName)) {
							promotion.setName(promotionName);
						}
						if (StringUtils.isNotBlank(desc)) {
							Description desc1 = new Description();
							desc1.setLang("eng");
							desc1.setVal(desc);
							promotion.setDesc(desc1);
						}
						if (StringUtils.isNotBlank(promotionType)) {
							promotion.setType(PromotionType
									.valueOf(promotionType));
						}
						promotion.setDealer(user);
						if (productIds != null) {
							promotion.setProductIds(productIds);
						}
						if (StringUtils.isNotBlank(discount)) {
							promotion.setDiscount(Double.parseDouble(discount));
						}
						if (StringUtils.isNotBlank(sDate)) {
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
									"dd/mm/yyyy");
							Date startDate = simpleDateFormat.parse(sDate);
							promotion.setStartDate(startDate);
						}
						if (StringUtils.isNotBlank(eDate)) {
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
									"dd/mm/yyyy");
							Date endDate = simpleDateFormat.parse(eDate);
							promotion.setEndDate(endDate);
						}
						logger.info("creating promotion for user store");
						promotionDAO.save(promotion);
					} else {
						logger.error("store does not exist");
						throw new Exception("store does not exist");
					}
				}
			} else {
				logger.error("user does not exist");
				throw new Exception("user does not exist");
			}
		}
		return promotion;
	}

	/**
	 * Method to update the promotion for the store
	 * 
	 * @param dealerid
	 * @param storeid
	 * @param promoid
	 * @param storePromotionUpdateParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public Promotion updateStorePromotion(String dealerid, String storeid,
			String promoid, String pName, String desc, String pType,
			Set<String> productIds, String discount, String sDate, String eDate)
			throws Exception {
		Promotion promotion = null;
		if (StringUtils.isNotBlank(dealerid)) {
			User user = userDAO.findById(dealerid);
			if (user != null) {
				if (StringUtils.isNotBlank(storeid)) {
					Store store = storeDAO.findOne(storeid);
					if (store != null) {
						promotion = promotionDAO.findOne(promoid);
					} else {
						throw new Exception("Store doesn't exist");
					}
					if (promotion != null) {
						logger.info("updating store promotion");
						if (StringUtils.isNotBlank(pName)) {
							promotion.setName(pName);
							logger.info("Promotion name updated");
						}
						if (StringUtils.isNotBlank(desc)) {
							promotion.getDesc().setVal(desc);
							logger.info("Promotion description updated");
						}
						if (StringUtils.isNotBlank(pType)) {
							promotion.setType(PromotionType.valueOf(pType));
							logger.info("Promotion type updated");
						}
						if (productIds != null) {
							promotion.getProductIds().addAll(productIds);
							logger.info("Product list updated");
						}
						if (StringUtils.isNotBlank(discount)) {
							promotion.setDiscount(Double.valueOf(discount));
							logger.info("Promotion discount updated");
						}
						if (StringUtils.isNotBlank(sDate)) {
							SimpleDateFormat formatter = new SimpleDateFormat(
									"dd/mm/yyyy");
							Date startDate = formatter.parse(sDate);
							promotion.setStartDate(startDate);
							logger.info("Promotion start Date updated");
						}
						if (StringUtils.isNotBlank(eDate)) {
							SimpleDateFormat formatter = new SimpleDateFormat(
									"dd/mm/yyyy");
							Date endDate = formatter.parse(eDate);
							promotion.setEndDate(endDate);
							logger.info("Promotion end Date updated");
						}
						promotionDAO.save(promotion);
					} else {
						logger.error("Promotion doesn't exist");
						throw new Exception("Promotion doesn't exist");
					}
				}
			} else {
				logger.error("User doesn't exists");
				throw new Exception("User doesn't exists");
			}
		}
		return promotion;
	}

	/**
	 * Method for delete Promotion For user store.
	 * 
	 * @param dealerId
	 * @param sid
	 * @param promid
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public String deletePromotionForUserStore(String uId, String sId,
			String promId) throws Exception {
		if (StringUtils.isNotBlank(uId) && StringUtils.isNotBlank(sId)
				&& StringUtils.isNotBlank(promId)) {
			User user = userDAO.findById(uId);
			if (user != null) {

				Store store = storeDAO.findByIdAndUser(sId, user);
				if (store != null) {
					logger.info("deleting promotion of the store");
					promotionDAO.delete(promId);
				} else {
					logger.error("store is empty");
					throw new Exception("store is empty");
				}
			} else {
				logger.error("user is empty");
				throw new Exception("user is empty");
			}
		} else {
			throw new Exception(
					"userId, StoreId and PromotionId should not be empty");
		}
		return promId;
	}

	/**
	 * method for admin manage dealer view details
	 * 
	 * @throws Exception
	 */
	@Override
	public UserSummaryViewDetails getManageDealerDetailsView(String dealerId)
			throws Exception {
		logger.info("Fetching Dealer Information based on dealerId : {}",
				dealerId);

		UserSummaryViewDetails adminManageDealerViewDetails = null;
		if (StringUtils.isNotBlank(dealerId)) {
			User dealer = userDAO.findOne(dealerId);
			UserInfo dealerInfo = userRepository.findByMongoUserId(dealerId);
			if (dealer != null && dealerInfo != null) {

				adminManageDealerViewDetails = new UserSummaryViewDetails();
				// UserId
				adminManageDealerViewDetails.setUserId(dealer.getId());
				// FirstName
				if (dealer.getFirstName() != null) {
					adminManageDealerViewDetails.setFirstName(dealer
							.getFirstName());
				}
				// LastName
				if (dealer.getLastName() != null) {
					adminManageDealerViewDetails.setLastName(dealer
							.getLastName());
				}
				// FullName
				if (dealer.getFirstName() != null
						&& dealer.getLastName() != null) {
					adminManageDealerViewDetails.setFullName(dealer
							.getFirstName() + " " + dealer.getLastName());
				} else {
					if (dealer.getFirstName() != null) {
						adminManageDealerViewDetails.setFullName(dealer
								.getFirstName());
					}
				}

				if (dealer.getContactName() != null) {
					adminManageDealerViewDetails.setContactName(dealer
							.getContactName());
				}
				if (dealer.getCreatedDate() != null) {
					adminManageDealerViewDetails.setCreatedDate(dealer
							.getCreatedDate());
				}
				if (dealer.getActiveSince() != null) {
					adminManageDealerViewDetails.setActiveSince(dealer
							.getActiveSince());
				}
				// Email
				if (dealerInfo.getUsername() != null) {
					adminManageDealerViewDetails.setEmailId(dealerInfo
							.getUsername());
				}
				// PhoneNumber
				if (dealerInfo.getPhonenumber() != null) {
					adminManageDealerViewDetails.setPhoneNumber(dealerInfo
							.getPhonenumber().toString());
				}

				// verified
				if (dealerInfo.isVerified() == false
						&& dealerInfo.getUsername() == null) {
					adminManageDealerViewDetails.setVerified(true);
				} else {
					adminManageDealerViewDetails.setVerified(false);
				}

				// Status
				if (dealer.getStatus() != null) {
					adminManageDealerViewDetails.setStatus(dealer.getStatus());
				}
				// Address
				if (dealer.getAddresses() != null) {
					adminManageDealerViewDetails.setAddresses(dealer
							.getAddresses());
					adminManageDealerViewDetails.setTotalShops(dealer
							.getAddresses().size());
				}
				// BankInformation
				if (dealer.getBankName() != null) {
					adminManageDealerViewDetails.setBankName(dealer
							.getBankName());
				}
				if (dealer.getBankAccountNumber() != null) {
					adminManageDealerViewDetails.setBankAccountNumber(dealer
							.getBankAccountNumber());
				}
				if (dealer.getPanNumber() != null) {
					adminManageDealerViewDetails.setPanNumber(dealer
							.getPanNumber());
				}
				if (dealer.getVatNumber() != null) {
					adminManageDealerViewDetails.setVatNumber(dealer
							.getVatNumber());
				}

				if (dealer.getWebsiteUrl() != null) {
					adminManageDealerViewDetails.setWebsiteUrl(dealer
							.getWebsiteUrl());
				}
				if (dealer.getIfscCode() != null) {
					adminManageDealerViewDetails.setIfscCode(dealer
							.getIfscCode());
				}
				// Buildonn Customer code
				char[] fNameArray = dealer.getFirstName().toUpperCase()
						.toCharArray();
				String customerCode = "BONNDE" + fNameArray[0] + fNameArray[1]
						+ fNameArray[2]
						+ String.format("%05d", dealerInfo.getUserid());
				adminManageDealerViewDetails.setCustomerCode(customerCode);
				// Dealer description
				if (dealer.getDesc() != null) {
					adminManageDealerViewDetails.setDesc(dealer.getDesc()
							.getVal());
				}
				// Package name
				Set<Role> roles = dealer.getRoles();
				for (Role role : roles) {
					if (role.getUserPackage() != null) {
						adminManageDealerViewDetails.setPackageName(role
								.getUserPackage().getName());
					}
				}

				// StoreList
				List<String> shopNameList = new ArrayList<>();

				// Store name
				if (dealer.getFirstName() != null) {
					shopNameList.add(dealer.getFirstName());
					adminManageDealerViewDetails.setShopName(shopNameList);
				}
				// onboarded products count
				adminManageDealerViewDetails
						.setTotalOnboardedProducts(productDAO
								.countByCreatedBy(dealer.getId()));
				// Current Balance
				adminManageDealerViewDetails.setCurrentBalance(dealer
						.getCurrentBalance());
				// Invested Balance
				adminManageDealerViewDetails.setInvestedBalance(dealer
						.getInvestedBalance());
			} else {
				logger.error(
						"User doesn't exists in our system for userId : {}",
						dealerId);
				throw new Exception(
						"User doesn't exists in our system for userId : {}"
								+ dealerId);
			}
		} else {
			logger.error("User Id should not null");
			throw new Exception("User Id should not null");
		}
		logger.info(
				"Fetching Dealer Information based on dealerId : {}! Success",
				dealerId);
		return adminManageDealerViewDetails;
	}

	/**
	 * method for admin manage provider view details
	 * 
	 * @throws Exception
	 */
	@Override
	public UserSummaryViewDetails getManageProviderDetailsView(String userId)
			throws Exception {
		logger.info("Fetching Provider Information based on ProviderId : {}",
				userId);
		UserSummaryViewDetails adminManageProviderViewDetails = null;
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findOne(userId);
			UserInfo userInfo = userRepository.findByMongoUserId(userId);
			if (user != null && userInfo != null) {
				Set<Role> providerRoles = user.getRoles();
				for (Role providerRole : providerRoles) {
					if (providerRole.getName().equalsIgnoreCase("PROVIDER")) {
						adminManageProviderViewDetails = new UserSummaryViewDetails();
						adminManageProviderViewDetails.setUserId(user.getId());
						ProductBrand brand = brandDAO.findByProvider(user);
						List<String> brandNameList = new ArrayList<>();
						int totalProducts = 0;
						if (brand != null) {
							brandNameList.add(brand.getName());
							totalProducts = (int) productDAO
									.countByBrand(brand);
						}
						adminManageProviderViewDetails
								.setBrandName(brandNameList);
						// FirstName
						adminManageProviderViewDetails.setFirstName(user
								.getFirstName());

						// LastName
						if (user.getLastName() != null) {
							adminManageProviderViewDetails.setLastName(user
									.getLastName());
						}
						// FullName
						if (user.getFirstName() != null
								&& user.getLastName() != null) {
							adminManageProviderViewDetails.setFullName(user
									.getFirstName() + " " + user.getLastName());
						} else {
							adminManageProviderViewDetails.setFullName(user
									.getFirstName());
						}
						if (user.getContactName() != null) {
							adminManageProviderViewDetails.setContactName(user
									.getContactName());
						}
						// Email
						if (userInfo.getUsername() != null) {
							adminManageProviderViewDetails.setEmailId(userInfo
									.getUsername());
						}
						// PhoneNumber
						if (userInfo.getPhonenumber() != null) {
							adminManageProviderViewDetails
									.setPhoneNumber(userInfo.getPhonenumber()
											.toString());
						}
						// verified
						if (userInfo.isVerified() == false
								&& userInfo.getUsername() == null) {
							adminManageProviderViewDetails.setVerified(true);
						} else {
							adminManageProviderViewDetails.setVerified(false);
						}

						// BankInformation
						if (user.getBankName() != null) {
							adminManageProviderViewDetails.setBankName(user
									.getBankName());
						}
						if (user.getBankAccountNumber() != null) {
							adminManageProviderViewDetails
									.setBankAccountNumber(user
											.getBankAccountNumber());
						}
						if (user.getPanNumber() != null) {
							adminManageProviderViewDetails.setPanNumber(user
									.getPanNumber());
						}
						if (user.getVatNumber() != null) {
							adminManageProviderViewDetails.setVatNumber(user
									.getVatNumber());
						}

						char[] fNameArray = user.getFirstName().toUpperCase()
								.toCharArray();

						String customerCode = "BONNPR" + fNameArray[0]
								+ fNameArray[1] + fNameArray[2]
								+ String.format("%05d", userInfo.getUserid());

						adminManageProviderViewDetails
								.setCustomerCode(customerCode);
						if (user.getWebsiteUrl() != null) {
							adminManageProviderViewDetails.setWebsiteUrl(user
									.getWebsiteUrl());
						}
						if (user.getDesc() != null) {
							adminManageProviderViewDetails.setDesc(user
									.getDesc().getVal());
						}

						// Provider created date
						adminManageProviderViewDetails.setCreatedDate(user
								.getCreatedDate());

						if (user.getActiveSince() != null) {
							adminManageProviderViewDetails.setActiveSince(user
									.getActiveSince());
						}
						// Package
						Set<Role> roles = user.getRoles();
						for (Role role : roles) {
							if (role.getUserPackage() != null) {
								adminManageProviderViewDetails
										.setPackageName(role.getUserPackage()
												.getName());
							}
						}
						// total products
						adminManageProviderViewDetails
								.setTotalProducts(totalProducts);
						// status
						adminManageProviderViewDetails.setStatus(user
								.getStatus());
						// addresses
						adminManageProviderViewDetails.setAddresses(user
								.getAddresses());
						// Current Balance
						adminManageProviderViewDetails.setCurrentBalance(user
								.getCurrentBalance());
						// Invested Balance
						adminManageProviderViewDetails.setInvestedBalance(user
								.getInvestedBalance());
					}
				}
			} else {
				logger.error(
						"User doesn't exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User doesn't exist in our system for userId : "
								+ userId);
			}
		} else {
			logger.error("UserId should not null");
			throw new Exception("UserId should not null");
		}
		logger.info(
				"Fetching Provider Information based on ProviderId : {}! Success",
				userId);
		return adminManageProviderViewDetails;
	}

	/**
	 * method for admin manage InteriorDesigner view details
	 * 
	 * @throws Exception
	 */
	@Override
	public UserSummaryViewDetails getManageInteriorDesignerDetailsView(
			String userId) throws Exception {
		logger.info(
				"Fetching InteriorDesigner Information based on InteriorDesignerId : {}",
				userId);
		UserSummaryViewDetails adminManageInteriorDesignerViewDetails = null;
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findOne(userId);
			UserInfo userInfo = userRepository.findByMongoUserId(userId);
			if (user != null && userInfo != null) {
				Set<Role> interiorDesignerRoles = user.getRoles();
				for (Role interiorDesignerRole : interiorDesignerRoles) {
					if (interiorDesignerRole.getName().equalsIgnoreCase(
							"INTERIORDESIGNER")) {
						adminManageInteriorDesignerViewDetails = new UserSummaryViewDetails();
						// Id
						adminManageInteriorDesignerViewDetails.setUserId(user
								.getId());
						// First name
						adminManageInteriorDesignerViewDetails
								.setFirstName(user.getFirstName());
						// Last name
						if (user.getLastName() != null) {
							adminManageInteriorDesignerViewDetails
									.setLastName(user.getLastName());
						}
						// Contact name
						if (user.getContactName() != null) {
							adminManageInteriorDesignerViewDetails
									.setContactName(user.getContactName());
						}

						// Full Name
						if (user.getFirstName() != null
								&& user.getLastName() != null) {
							adminManageInteriorDesignerViewDetails
									.setFullName(user.getFirstName() + " "
											+ user.getLastName());
						} else {
							adminManageInteriorDesignerViewDetails
									.setFullName(user.getFirstName());
						}

						if (user.getCreatedDate() != null) {
							adminManageInteriorDesignerViewDetails
									.setCreatedDate(user.getCreatedDate());
						}

						if (user.getActiveSince() != null) {
							adminManageInteriorDesignerViewDetails
									.setActiveSince(user.getActiveSince());
						}
						// Email
						if (userInfo.getUsername() != null) {
							adminManageInteriorDesignerViewDetails
									.setEmailId(userInfo.getUsername());
						}
						// PhoneNumber
						if (userInfo.getPhonenumber() != null) {
							adminManageInteriorDesignerViewDetails
									.setPhoneNumber(userInfo.getPhonenumber()
											.toString());
						}

						// verified
						if (userInfo.isVerified() == false
								&& userInfo.getUsername() == null) {
							adminManageInteriorDesignerViewDetails
									.setVerified(true);
						} else {
							adminManageInteriorDesignerViewDetails
									.setVerified(false);
						}

						if (user.getAddresses() != null) {
							adminManageInteriorDesignerViewDetails
									.setAddresses(user.getAddresses());
							adminManageInteriorDesignerViewDetails
									.setTotalOffices(user.getAddresses().size());
						}

						adminManageInteriorDesignerViewDetails.setStatus(user
								.getStatus());
						// other skills
						if (user.getOtherSkillSets() != null) {
							adminManageInteriorDesignerViewDetails
									.setOtherSkillSets(user.getOtherSkillSets());
						}
						// operating cities
						if (user.getOperatingCities() != null) {
							adminManageInteriorDesignerViewDetails
									.setOperatingCities(user
											.getOperatingCities());
						}

						// Current Balance
						adminManageInteriorDesignerViewDetails
								.setCurrentBalance(user.getCurrentBalance());

						// Invested Balance
						adminManageInteriorDesignerViewDetails
								.setInvestedBalance(user.getInvestedBalance());

						// CustomerCode
						char[] fNameArray = user.getFirstName().toUpperCase()
								.toCharArray();

						String customerCode = "BONNID" + fNameArray[0]
								+ fNameArray[1] + fNameArray[2]
								+ String.format("%05d", userInfo.getUserid());

						adminManageInteriorDesignerViewDetails
								.setCustomerCode(customerCode);

						// BankInformation
						if (user.getBankName() != null) {
							adminManageInteriorDesignerViewDetails
									.setBankName(user.getBankName());
						}
						if (user.getBankAccountNumber() != null) {
							adminManageInteriorDesignerViewDetails
									.setBankAccountNumber(user
											.getBankAccountNumber());
						}
						if (user.getPanNumber() != null) {
							adminManageInteriorDesignerViewDetails
									.setPanNumber(user.getPanNumber());
						}
						// Vat number
						if (user.getVatNumber() != null) {
							adminManageInteriorDesignerViewDetails
									.setVatNumber(user.getVatNumber());
						}
						if (user.getWebsiteUrl() != null) {
							adminManageInteriorDesignerViewDetails
									.setWebsiteUrl(user.getWebsiteUrl());
						}
						if (user.getDesc() != null) {
							adminManageInteriorDesignerViewDetails.setDesc(user
									.getDesc().getVal());
						}
						Set<Role> roles = user.getRoles();
						for (Role role : roles) {
							if (role.getUserPackage() != null) {
								String packageName = role.getUserPackage()
										.getName();
								adminManageInteriorDesignerViewDetails
										.setPackageName(packageName);
							}
						}
						long count = userBidDAO.countByUser(user);
						adminManageInteriorDesignerViewDetails
								.setBidsCount(count);
						// rating
						Double rating = 0.0;
						List<Comment> commentList = userCommentDAO
								.findByDiscussionId(userId);
						long count1 = userCommentDAO
								.countByDiscussionId(userId);
						if (commentList != null) {
							for (Comment comment : commentList) {
								Double ratingval = comment.getRating()
										.getRatingVal();
								rating = rating + ratingval;
							}
							double value = rating / count1;
							adminManageInteriorDesignerViewDetails
									.setRating(value);
						}

						// Portfolio Projects
						List<Image> profileImages = new ArrayList<>();
						List<UserProjectProfile> userProjectProfileList = userProjectProfileDAO
								.findByUser(user);
						if (userProjectProfileList != null
								&& userProjectProfileList.size() != 0) {
							for (UserProjectProfile userProjectProfile : userProjectProfileList) {
								if (userProjectProfile.getProfileImages() != null
										&& userProjectProfile
												.getProfileImages().size() != 0) {
									List<Image> images = userProjectProfile
											.getProfileImages();
									for (Image image : images) {
										profileImages.add(image);
									}
								}
							}
							adminManageInteriorDesignerViewDetails
									.setPortfolio(profileImages);
						}
						// Title
						if (user.getTitle() != null) {
							adminManageInteriorDesignerViewDetails
									.setTitle(user.getTitle());
						}
					}
				}
			} else {
				logger.error(
						"User doesn't exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User doesn't exist in our system for userId : "
								+ userId);
			}
		} else {
			logger.error("User Id should not be null");
			throw new Exception("User Id should not be null");
		}
		logger.info(
				"Fetching InteriorDesigner Information based on InteriorDesignerId : {}! Success",
				userId);
		return adminManageInteriorDesignerViewDetails;
	}

	/**
	 * method for admin manage Architect view details
	 * 
	 * @throws Exception
	 */
	@Override
	public UserSummaryViewDetails getManageArchitectDetailsView(String userId)
			throws Exception {
		logger.info("Fetching Architect Information based on ArchitectId : {}",
				userId);
		UserSummaryViewDetails adminManageArchitectViewDetails = null;

		if (StringUtils.isNotBlank(userId)) {

			User user = userDAO.findOne(userId);
			UserInfo userInfo = userRepository.findByMongoUserId(userId);

			if (user != null && userInfo != null) {
				Set<Role> architectRoles = user.getRoles();
				for (Role architectRole : architectRoles) {
					if (architectRole.getName().equalsIgnoreCase("ARCHITECT")) {
						adminManageArchitectViewDetails = new UserSummaryViewDetails();
						// UserId
						adminManageArchitectViewDetails.setUserId(user.getId());
						// FirstName
						adminManageArchitectViewDetails.setFirstName(user
								.getFirstName());
						// LastName
						if (user.getLastName() != null) {
							adminManageArchitectViewDetails.setLastName(user
									.getLastName());
						}

						// Full Name
						if (user.getFirstName() != null
								&& user.getLastName() != null) {
							adminManageArchitectViewDetails.setFullName(user
									.getFirstName() + " " + user.getLastName());
						} else {
							adminManageArchitectViewDetails.setFullName(user
									.getFirstName());
						}

						// ContactName
						if (user.getContactName() != null) {
							adminManageArchitectViewDetails.setContactName(user
									.getContactName());
						}
						// CreatedDate
						if (user.getCreatedDate() != null) {
							adminManageArchitectViewDetails.setCreatedDate(user
									.getCreatedDate());
						}

						if (user.getActiveSince() != null) {
							adminManageArchitectViewDetails.setActiveSince(user
									.getActiveSince());
						}
						// skill set
						if (user.getOtherSkillSets() != null) {
							adminManageArchitectViewDetails
									.setOtherSkillSets(user.getOtherSkillSets());
						}
						// operating cities
						if (user.getOperatingCities() != null) {
							adminManageArchitectViewDetails
									.setOperatingCities(user
											.getOperatingCities());
						}
						// Addresses
						if (user.getAddresses() != null) {
							Set<Address> addresses = user.getAddresses();
							adminManageArchitectViewDetails.setAddresses(user
									.getAddresses());
							adminManageArchitectViewDetails
									.setTotalOffices(addresses.size());
						}
						// Email
						if (userInfo.getUsername() != null) {
							adminManageArchitectViewDetails.setEmailId(userInfo
									.getUsername());
						}
						// PhoneNumber
						if (userInfo.getPhonenumber() != null) {
							adminManageArchitectViewDetails
									.setPhoneNumber(userInfo.getPhonenumber()
											.toString());
						}

						// verified
						if (userInfo.isVerified() == false
								&& userInfo.getUsername() == null) {
							adminManageArchitectViewDetails.setVerified(true);
						} else {
							adminManageArchitectViewDetails.setVerified(false);
						}

						// status
						adminManageArchitectViewDetails.setStatus(user
								.getStatus());

						// Current Balance
						adminManageArchitectViewDetails.setCurrentBalance(user
								.getCurrentBalance());

						// Invested Balance
						adminManageArchitectViewDetails.setInvestedBalance(user
								.getInvestedBalance());

						// customer code
						char[] fNameArray = user.getFirstName().toUpperCase()
								.toCharArray();

						String customerCode = "BONNAR" + fNameArray[0]
								+ fNameArray[1] + fNameArray[2]
								+ String.format("%05d", userInfo.getUserid());

						adminManageArchitectViewDetails
								.setCustomerCode(customerCode);

						// BankInformation
						if (user.getBankName() != null) {
							adminManageArchitectViewDetails.setBankName(user
									.getBankName());
						}
						// account number
						if (user.getBankAccountNumber() != null) {
							adminManageArchitectViewDetails
									.setBankAccountNumber(user
											.getBankAccountNumber());
						}
						// pan number
						if (user.getPanNumber() != null) {
							adminManageArchitectViewDetails.setPanNumber(user
									.getPanNumber());
						}
						// vat number
						if (user.getVatNumber() != null) {
							adminManageArchitectViewDetails.setVatNumber(user
									.getVatNumber());
						}

						if (user.getWebsiteUrl() != null) {
							adminManageArchitectViewDetails.setWebsiteUrl(user
									.getWebsiteUrl());
						}
						if (user.getDesc() != null) {
							adminManageArchitectViewDetails.setDesc(user
									.getDesc().getVal());
						}
						Set<Role> roles = user.getRoles();
						for (Role role : roles) {
							if (role.getUserPackage() != null) {
								if (role.getUserPackage() != null) {
									String packageName = role.getUserPackage()
											.getName();
									adminManageArchitectViewDetails
											.setPackageName(packageName);
								}
							}
							UserPackage userPackage = role.getUserPackage();
							if (userPackage != null) {
								if (userPackage.getPrivileges() != null) {
									Set<Privilege> privilegeSet = userPackage
											.getPrivileges();
									Set<String> featuresSet = new HashSet<String>();

									for (Privilege privilege : privilegeSet) {
										featuresSet.add(privilege.getName());
									}
									adminManageArchitectViewDetails
											.setFeatures(featuresSet);
								}
							}
						}
						long count = userBidDAO.countByUser(user);
						adminManageArchitectViewDetails.setBidsCount(count);
						// rating
						Double rating = 0.0;
						List<Comment> commentList = userCommentDAO
								.findByDiscussionId(userId);
						long count1 = userCommentDAO
								.countByDiscussionId(userId);
						for (Comment comment : commentList) {
							Double ratingval = comment.getRating()
									.getRatingVal();
							rating = rating + ratingval;
						}
						double value = rating / count1;
						adminManageArchitectViewDetails.setRating(value);
						// Portfolio Projects
						List<Image> profileImages = new ArrayList<>();
						List<UserProjectProfile> userProjectProfileList = userProjectProfileDAO
								.findByUser(user);
						if (userProjectProfileList != null
								&& userProjectProfileList.size() != 0) {
							for (UserProjectProfile userProjectProfile : userProjectProfileList) {
								if (userProjectProfile.getProfileImages() != null
										&& userProjectProfile
												.getProfileImages().size() != 0) {
									List<Image> images = userProjectProfile
											.getProfileImages();
									for (Image image : images) {
										profileImages.add(image);
									}
								}
							}
							adminManageArchitectViewDetails
									.setPortfolio(profileImages);
						}
						// Title
						if (user.getTitle() != null) {
							adminManageArchitectViewDetails.setTitle(user
									.getTitle());
						}
					}
				}
			} else {
				logger.error(
						"User doesn't exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User doesn't exist in our system for userId : "
								+ userId);
			}
		} else {
			logger.error("User Id should not be null");
			throw new Exception("User Id should not be null");
		}
		logger.info("Fetching Architect Information based on ArchitectId! Success");
		return adminManageArchitectViewDetails;
	}

	/**
	 * method for admin manage ServiceProvider view details
	 * 
	 * @throws Exception
	 */
	@Override
	public UserSummaryViewDetails getManageServiceProviderDetailsView(
			String userId) throws Exception {
		UserSummaryViewDetails superAdminManageServiceProviderViewDetails = null;
		if (StringUtils.isNotBlank(userId)) {
			logger.info("Fetching ServiceProvider Information based on ServiceProviderId");
			User user = userDAO.findById(userId);
			UserInfo userInfo = userRepository.findByMongoUserId(userId);
			if (user != null && userInfo != null) {
				Set<Role> serviceProviderRoles = user.getRoles();
				for (Role serviceProviderRole : serviceProviderRoles) {
					if (serviceProviderRole.getType().name()
							.equalsIgnoreCase("SERVICEPROVIDER")) {
						superAdminManageServiceProviderViewDetails = new UserSummaryViewDetails();
						// Id
						superAdminManageServiceProviderViewDetails
								.setUserId(user.getId());
						// First name
						superAdminManageServiceProviderViewDetails
								.setFirstName(user.getFirstName());
						// Last name
						if (user.getLastName() != null) {
							superAdminManageServiceProviderViewDetails
									.setLastName(user.getLastName());
						}
						// Full Name
						if (user.getFirstName() != null
								&& user.getLastName() != null) {
							superAdminManageServiceProviderViewDetails
									.setFullName(user.getFirstName() + " "
											+ user.getLastName());
						} else {
							superAdminManageServiceProviderViewDetails
									.setFullName(user.getFirstName());
						}

						if (user.getContactName() != null) {
							superAdminManageServiceProviderViewDetails
									.setContactName(user.getContactName());
						}
						// Email
						if (userInfo.getUsername() != null) {
							superAdminManageServiceProviderViewDetails
									.setEmailId(userInfo.getUsername());
						}
						// PhoneNumber
						if (userInfo.getPhonenumber() != null) {
							superAdminManageServiceProviderViewDetails
									.setPhoneNumber(userInfo.getPhonenumber()
											.toString());
						}
						// verified
						if (userInfo.isVerified() == false
								&& userInfo.getUsername() == null) {
							superAdminManageServiceProviderViewDetails
									.setVerified(true);
						} else {
							superAdminManageServiceProviderViewDetails
									.setVerified(false);
						}
						if (user.getProfilePicture() != null) {
							superAdminManageServiceProviderViewDetails
									.setPhoto(user.getProfilePicture());
						}
						// other skills
						if (user.getOtherSkillSets() != null) {
							superAdminManageServiceProviderViewDetails
									.setOtherSkillSets(user.getOtherSkillSets());
						}

						superAdminManageServiceProviderViewDetails
								.setCreatedDate(user.getCreatedDate());

						if (user.getActiveSince() != null) {
							superAdminManageServiceProviderViewDetails
									.setActiveSince(user.getActiveSince());
						}

						// operating cities
						if (user.getOperatingCities() != null) {
							superAdminManageServiceProviderViewDetails
									.setOperatingCities(user
											.getOperatingCities());
						}
						// Current Balance
						superAdminManageServiceProviderViewDetails
								.setCurrentBalance(user.getCurrentBalance());

						// Invested Balance
						superAdminManageServiceProviderViewDetails
								.setInvestedBalance(user.getInvestedBalance());
						// CustomerCode
						char[] fNameArray = user.getFirstName().toUpperCase()
								.toCharArray();

						char[] roleNameArray = serviceProviderRole.getName()
								.toUpperCase().toCharArray();

						String customerCode = "BONN" + roleNameArray[0]
								+ roleNameArray[1] + fNameArray[0]
								+ fNameArray[1] + fNameArray[2]
								+ String.format("%05d", userInfo.getUserid());

						superAdminManageServiceProviderViewDetails
								.setCustomerCode(customerCode);

						superAdminManageServiceProviderViewDetails
								.setAddresses(user.getAddresses());
						superAdminManageServiceProviderViewDetails
								.setStatus(user.getStatus());
						superAdminManageServiceProviderViewDetails
								.setCreatedDate(user.getCreatedDate());
						if (user.getDesc() != null) {
							superAdminManageServiceProviderViewDetails
									.setDesc(user.getDesc().getVal());
						}

						Set<Role> roles = user.getRoles();
						for (Role role : roles) {
							superAdminManageServiceProviderViewDetails
									.setRoleName(role.getName());
						}
						// Title
						if (user.getTitle() != null) {
							superAdminManageServiceProviderViewDetails
									.setTitle(user.getTitle());
						}

						// WebSite URL
						if (user.getWebsiteUrl() != null) {
							superAdminManageServiceProviderViewDetails
									.setWebsiteUrl(user.getWebsiteUrl());
						}
						// Vat Number
						if (user.getVatNumber() != null) {
							superAdminManageServiceProviderViewDetails
									.setVatNumber(user.getVatNumber());
						}
					}
				}
			} else {
				logger.error(
						"User doesn't exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User doesn't exist in our system for userId : "
								+ userId);
			}
		} else {
			logger.error("User Id should not be null");
			throw new Exception("User Id should not be null");
		}
		logger.info(
				"Fetching ServiceProvider Information based on ServiceProviderId : {}! Success",
				userId);
		return superAdminManageServiceProviderViewDetails;
	}

	/**
	 * Method to add profile projects
	 * 
	 * @param userId
	 * @param profileName
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserProjectProfile addProfileProjects(String userId,
			String profileName, String desc, ProjectType projectTypes)
			throws Exception {
		UserProjectProfile userProjectProfile = new UserProjectProfile();
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findById(userId);
			if (user != null) {

				if (StringUtils.isNotBlank(profileName)) {
					userProjectProfile.setProfileName(profileName);
				}
				if (StringUtils.isNotBlank(desc)) {
					Description descr = new Description();
					descr.setLang("Eng");
					descr.setVal(desc);
					userProjectProfile.setDesc(descr);
				}
				if (projectTypes != null) {
					userProjectProfile.setProjectTypesHandled(projectTypes);
				}
				userProjectProfile.setUser(user);
			} else {
				throw new Exception("User doesn't exist");
			}

			// if (privilegeService.checkPrivilege(userId, "ADD", "PROFILE")) {
			// userProjectProfileDAO.save(userProjectProfile);
			// logger.info("Adding user Projects");
			// } else {
			// throw new Exception(
			// "Cannot Add more Projects, upgrade your package...!!");
			// }

			userProjectProfileDAO.save(userProjectProfile);
			logger.info("Adding user Projects");

		} else {
			throw new Exception("User Id should not be null");
		}

		return userProjectProfile;
	}

	/**
	 * Method to get Profile projects
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<UserProjectProfile> getMyProfileProjects(String userId,
			String projectType) throws Exception {

		List<UserProjectProfile> userProjects = null;

		if (StringUtils.isNotBlank(userId)) {

			User user = userDAO.findById(userId);
			if (user != null) {
				if (StringUtils.isNotBlank(projectType)) {
					userProjects = userProjectProfileDAO
							.findByUserAndProjectTypesHandled(user,
									ProjectType.valueOf(projectType));
				} else {
					userProjects = userProjectProfileDAO.findByUser(user);
				}
			} else {
				throw new Exception("User doesn't exist");
			}
		} else {
			logger.info("User Id cannot be null");
			throw new Exception("User ID cannot be NULL");
		}
		return userProjects;
	}

	/**
	 * Method to get details about particular project
	 * 
	 * @param userId
	 * @param projId
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserProjectProfile getMyProfileProjectDetails(String userId,
			String projId) throws Exception {
		UserProjectProfile userProjectProfile = new UserProjectProfile();
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findById(userId);
			if (user != null && StringUtils.isNotBlank(projId)) {
				userProjectProfile = userProjectProfileDAO.findOne(projId);
			} else {
				throw new Exception("User doesn't exist");
			}
		} else {
			throw new Exception("User ID cannot be NULL");
		}
		return userProjectProfile;
	}

	public class StoreProductCount {
		private int total;
		public String id;
	}

	/**
	 * method for get linked products count.
	 * 
	 * @param dealerId
	 * @return
	 */
	@Override
	public long getLinkedProductsCount(String dealerId) throws Exception {
		long count = 0;
		if (StringUtils.isNotBlank(dealerId)) {
			User user = userDAO.findOne(dealerId);
			if (user != null) {
				Aggregation agg = Aggregation.newAggregation(
						Aggregation.match(Criteria.where("user").is(dealerId)),
						Aggregation.project("storename").and("products").size()
								.as("total"));

				// Converting the aggregation result into a List
				AggregationResults<StoreProductCount> groupResults = mongoTemplate
						.aggregate(agg, Store.class, StoreProductCount.class);
				List<StoreProductCount> result = groupResults
						.getMappedResults();
				if (result.size() != 0) {
					count = result.get(0).total;
				}
			} else {
				logger.error("User doesn't exist");
				throw new Exception("User doesn't exist");
			}
		} else {
			logger.error("User ID cannot be NULL");
			throw new Exception("User ID cannot be NULL");
		}

		return count;
	}

	/**
	 * method for unlink product from store.
	 * 
	 * @param dealerId
	 * @param productLinkOrUnLinkResponse
	 * @return
	 */
	@Override
	public Product unlinkProduct(String dealerId, Set<String> prodIds)
			throws Exception {
		Product product = null;

		List<Product> productList = new ArrayList<>();

		if (StringUtils.isNotBlank(dealerId)) {
			User user = userDAO.findOne(dealerId);

			if (user != null) {
				Store store = storeDAO.findByUser(user);
				if (store != null && prodIds != null) {
					for (String pid : prodIds) {
						product = productDAO.findOne(pid);
						if (product != null) {

							logger.info("pid:" + product.getId());
							productList = store.getProducts();

							for (Product product1 : productList) {
								if (product1.getId().equalsIgnoreCase(
										product.getId())) {
									productList.remove(product1);
									break;
								}
							}
							StoreProductPricing storeProductPricing = storeProductPricingDAO
									.findByProductIdAndStoreId(product.getId(),
											store.getId());
							if (storeProductPricing != null) {
								if (storeProductPricing.getProductId()
										.equalsIgnoreCase(product.getId())) {
									storeProductPricingDAO
											.delete(storeProductPricing);
								}
							}

						} else {
							logger.error("Product doesn't exists");
							throw new Exception("Product doesn't exists");
						}
					}
					store.setProducts(productList);
					storeDAO.save(store);
				} else {
					logger.error("Store doesn't exists or product Id can'nt be null");
					throw new Exception(
							"Store doesn't exists or product Id can'nt be null");
				}

			} else {
				logger.error("User is null");
				throw new Exception("User is null");
			}

		} else {
			logger.error("Dealer Id cannot be null");
			throw new Exception("Dealer Id cannot be null");
		}

		return product;
	}

	/**
	 * method for link products to Store.
	 * 
	 * @param dealerId
	 * @param productLinkOrUnLinkResponse
	 * @return
	 */
	@Override
	public Product linkProduct(String dealerId, Set<String> prodIds)
			throws Exception {
		Product product = null;
		List<Product> productList = new ArrayList<>();
		if (StringUtils.isNotBlank(dealerId)) {
			User user = userDAO.findOne(dealerId);

			if (user != null) {
				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					if (role.getName().equalsIgnoreCase("DEALER")) {
						Store store = storeDAO.findByUser(user);
						if (store != null && prodIds != null) {
							productList = store.getProducts();
							Set<String> storeProductIds = new HashSet<>();
							Set<String> linkingProdIds = new HashSet<>();
							for (Product storeProduct : productList) {
								storeProductIds.add(storeProduct.getId());
							}
							boolean flag = true;
							for (String pid : prodIds) {
								for (String productId : storeProductIds) {
									if (pid.equals(productId)) {
										logger.info(pid
												+ " this product already exist in your store");
										flag = false;
										break;
									}
									flag = true;
								}
								if (flag) {
									linkingProdIds.add(pid);
								}
							}

							for (String pid : linkingProdIds) {
								product = productDAO.findOne(pid);
								if (product != null) {
									productList.add(product);

									StoreProductPricing storeProductPricing = new StoreProductPricing(
											store.getId(), pid,
											product.getSkuId());

									storeProductPricing
											.setMaxRetailPrice(product
													.getPrice().getMrp());
									storeProductPricing.setSellingPrice(product
											.getPrice().getMrp());

									storeProductPricing.setFulfilledBy(user
											.getFullName());
									storeProductPricing.setStockCount(0);

									Shipping shipping = new Shipping();
									shipping.setLocalDelivery(true);
									shipping.setLocalShippingChrg(0.0);
									shipping.setZonalDelivery(false);
									shipping.setNationalDelivery(false);

									storeProductPricing.setShipping(shipping);

									storeProductPricingDAO
											.save(storeProductPricing);

									logger.info("pid:" + product.getId());
								} else {
									logger.info("Product doesn't exists");
									throw new Exception(
											"Product doesn't exists");
								}
							}
							store.setProducts(productList);
							storeDAO.save(store);

						} else if (store == null && prodIds != null) {
							Store newStore = new Store();
							newStore.setStorename(user.getFirstName());
							newStore.setUser(user);
							newStore.setCreatedDate(new Date());
							newStore.setLastModifiedDate(new Date());
							newStore.setCreatedBy(user.getId());
							newStore.setLastModifiedBy(user.getId());
							List<Product> products = new ArrayList<Product>();
							for (String pid : prodIds) {
								Product product1 = productDAO.findOne(pid);
								if (product1 != null) {
									products.add(product1);
								}
							}
							newStore.setProducts(products);
							storeDAO.save(newStore);

							for (String pid : prodIds) {
								Product product2 = productDAO.findOne(pid);
								StoreProductPricing storeProductPricing = new StoreProductPricing(
										newStore.getId(), product2.getId(),
										product2.getSkuId());

								storeProductPricing.setMaxRetailPrice(product2
										.getPrice().getMrp());
								storeProductPricing.setSellingPrice(product2
										.getPrice().getMrp());
								storeProductPricing.setFulfilledBy(user
										.getFullName());
								storeProductPricing.setStockCount(0);
								Shipping shipping = new Shipping();
								shipping.setLocalDelivery(true);
								shipping.setLocalShippingChrg(0.0);
								shipping.setZonalDelivery(false);
								shipping.setNationalDelivery(false);

								storeProductPricing.setShipping(shipping);

								storeProductPricingDAO
										.save(storeProductPricing);
							}
						} else {
							logger.error("product Id can't be null");
							throw new Exception("product Id can't be null");
						}
					} else {
						throw new Exception("your not a dealer");
					}
				}
			} else {
				logger.error("User is null");
				throw new Exception("User is null");
			}

		} else {
			logger.error("Dealer Id cannot be null");
			throw new Exception("Dealer Id cannot be null");
		}

		return product;
	}

	/**
	 * method for upload user profile image.
	 * 
	 * @param uid
	 * @param fileName
	 * @param fileTypeValue
	 * @param fileInputStream
	 * @param contentLength
	 * @return
	 * @throws Exception
	 */
	@Override
	public synchronized User uploadUserImage(String uid, String fileName,
			String fileTypeValue, InputStream fileInputStream,
			long contentLength) throws Exception {
		String locationName = null;
		String url = null;
		User user = null;
		String userName = null;
		System.out.println("Name:" + fileName);
		if (StringUtils.isNotBlank(uid)) {
			logger.info("UserResource: uploadFile : " + uid);
			user = userDAO.findOne(uid);
			if (user != null) {
				userName = user.getFullName().toUpperCase();
				logger.info(userName);
				locationName = "users/" + userName.charAt(0) + "/"
						+ userName.substring(0, 2) + "/" + fileName;
				logger.info(locationName);
				if (fileTypeValue.equals("Image")) {

					url = filestorage.uploadFile(
							AzureFileStorage.IMG_CONTAINER, locationName,
							fileInputStream, contentLength);
				}

				Thread.sleep(2000);
				if (StringUtils.equals(fileTypeValue.toLowerCase(), "image")) {
					Image image = new Image(fileName, url);
					URL uri = new URL(url);
					logger.debug(url);
					java.awt.Image imageAwt = new ImageIcon(uri).getImage();
					int imgWidth = imageAwt.getWidth(null);
					int imgHeight = imageAwt.getHeight(null);
					logger.debug("Image Height " + imgHeight + " And width "
							+ imgWidth);
					image.setHeight(imgHeight);
					image.setWidth(imgWidth);
					image.setSize(uri.openConnection().getContentLengthLong());
					logger.debug("Image Size set in image");
					image.setFileType(FileType.IMAGE);
					user.setProfilePicture(image);
					userDAO.save(user);
					logger.debug("UserResource : uploadFile : image uploaded");
				}
			}
		} else {
			throw new Exception("Sorry user id did not match in database");
		}
		return user;
	}

	/**
	 * method for upload user profile project image.
	 * 
	 * @param form
	 * @return
	 * @throws IOException
	 * @throws MBGAppException
	 */
	@Override
	public synchronized UserProjectProfile uploadProjectImage(
			String userProjId, String fileName, String fileTypeValue,
			InputStream fileInputStream, long contentLength) throws Exception {
		String locationName = null;
		String url = null;
		UserProjectProfile userProjectProfile = null;
		String projectName = null;
		System.out.println("FileName: " + fileName);
		if (StringUtils.isNotBlank(userProjId)) {
			logger.info("ProjectProfileResource : uploadFile : " + userProjId);
			userProjectProfile = userProjectProfileDAO.findOne(userProjId);
			if (userProjectProfile != null) {
				projectName = userProjectProfile.getProfileName().toUpperCase();
				logger.info(projectName);
				locationName = "user/userprojectprofile/"
						+ projectName.charAt(0) + "/"
						+ projectName.substring(0, 2) + "/" + fileName;
				logger.info(locationName);
				if (fileTypeValue.equals("File")) {

					url = filestorage.uploadFile(
							AzureFileStorage.FILE_CONTAINER, locationName,
							fileInputStream, contentLength);
				} else {
					url = filestorage.uploadFile(
							AzureFileStorage.IMG_CONTAINER, locationName,
							fileInputStream, contentLength);
				}
				if (StringUtils.equals(fileTypeValue.toLowerCase(), "image")) {
					Image image = new Image(fileName, url);
					URL uri = new URL(url);
					logger.debug(url);
					java.awt.Image imageAwt = new ImageIcon(uri).getImage();
					int imgWidth = imageAwt.getWidth(null);
					int imgHeight = imageAwt.getHeight(null);
					logger.debug("Image Height " + imgHeight + " And width "
							+ imgWidth);
					image.setHeight(imgHeight);
					image.setWidth(imgWidth);
					image.setSize(uri.openConnection().getContentLengthLong());
					logger.debug("Image Size set in image");
					image.setFileType(FileType.IMAGE);
					List<Image> imageList = userProjectProfile
							.getProfileImages();
					if (imageList != null) {
						logger.debug("UserProjectResource : uploadFile : "
								+ imageList);
						imageList.add(image);
					} else {
						imageList = new ArrayList<>();
						imageList.add(image);
					}
					userProjectProfile.setProfileImages(imageList);
					userProjectProfileDAO.save(userProjectProfile);
					logger.debug("UserProjectResource : uploadFile : image uploaded");
				} else {
					if (StringUtils
							.equals(fileTypeValue.toLowerCase(), "image")) {
						Image image = new Image(fileName, url);
						URL uri = new URL(url);
						java.awt.Image imageAwt = new ImageIcon(uri).getImage();
						int imgWidth = imageAwt.getWidth(null);
						int imgHeight = imageAwt.getHeight(null);
						logger.debug("Image Height " + imgHeight
								+ " And width " + imgWidth);
						image.setHeight(imgHeight);
						image.setWidth(imgWidth);
						image.setSize(uri.openConnection().getContentLength());
						logger.debug("image size set in image");
						image.setFileType(FileType.IMAGE);
						List<Image> imageList = new ArrayList<>();
						imageList.add(image);
						userProjectProfile.setProfileImages(imageList);
						userProjectProfileDAO.save(userProjectProfile);
						logger.debug("UserProjectResource : uploadFile : Image uploaded");
					}
				}
			}
		} else {
			throw new Exception(
					"Sorry user profile project id not match in database");
		}
		return userProjectProfile;
	}

	/**
	 * method for create user related document
	 * 
	 * @param userTypes
	 * @param status
	 * @return
	 */
	@Override
	public UserRelatedDocuments createUserRelatedDocuments(
			Set<UserType> userTypes, String status) {
		UserRelatedDocuments userRelatedDocuments = new UserRelatedDocuments();
		if (userTypes != null) {
			userRelatedDocuments.setForUserTypes(userTypes);
		}
		if (StringUtils.isNotBlank(status)) {
			if (status.equalsIgnoreCase("Active")) {
				logger.info("status is Active");
				userRelatedDocuments.setActive(true);
			} else {
				logger.info("status is Inactive");
				userRelatedDocuments.setActive(false);
			}
		}
		userRelatedDocumentsDAO.insert(userRelatedDocuments);
		return userRelatedDocuments;
	}

	/**
	 * method for upload terms and condition file.
	 * 
	 * @param fileName
	 * @param fileTypeValue
	 * @param fileInputStream
	 * @param contentLength
	 * @param userRelatedDocumentId
	 * @return
	 * @throws Exception
	 */
	@Override
	public String uploadTermsAndConditions(String fileName,
			String fileTypeValue, InputStream fileInputStream,
			long contentLength, String userRelatedDocumentId) throws Exception {

		String locationName = null;
		String url = null;
		UserRelatedDocuments userRelatedDocuments = null;

		if (StringUtils.isNotBlank(userRelatedDocumentId)) {
			logger.info("UserRelatedDocument : uploadFile : "
					+ userRelatedDocumentId);

			userRelatedDocuments = userRelatedDocumentsDAO
					.findOne(userRelatedDocumentId);
			if (userRelatedDocuments != null) {

				locationName = "users/documents/" + fileName.charAt(0) + "/"
						+ fileName.substring(0, 2) + "/" + fileName;
				logger.info(locationName);
				if (fileTypeValue.equals("File")) {

					url = filestorage.uploadFile(
							AzureFileStorage.FILE_CONTAINER, locationName,
							fileInputStream, contentLength);
				}

				if (StringUtils.equals(fileTypeValue.toLowerCase(), "file")) {
					File relatedFile = new File(fileName, url);
					URL uri = new URL(url);
					relatedFile.setSize(uri.openConnection()
							.getContentLengthLong());
					relatedFile.setFileType(FileType.DOC);
					userRelatedDocuments.setFile(relatedFile);
					userRelatedDocumentsDAO.save(userRelatedDocuments);
					logger.debug("UserResource : uploadFile : userRelatedFile uploaded");
				}
			} else {
				logger.error("Sorry user Related DocumentId not match in database");
				throw new Exception(
						"Sorry user Related DocumentId not match in database");
			}
		} else {
			logger.error("userRelatedDocumentId cannot be null");
			throw new Exception("userRelatedDocumentId cannot be null");
		}

		return userRelatedDocuments.getId();
	}

	/**
	 * method to get terms and condition file
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	@Override
	public File getAdminFile(String fileName) throws Exception {
		File file = null;
		if (StringUtils.isNotBlank(fileName)) {

			List<UserRelatedDocuments> userRelatedDocumentsList = userRelatedDocumentsDAO
					.findAll();
			for (UserRelatedDocuments userDocuments : userRelatedDocumentsList) {
				if (fileName
						.equalsIgnoreCase(userDocuments.getFile().getName())
						&& userDocuments.isActive()) {
					file = userDocuments.getFile();
				}
			}

		} else {
			logger.error("File Name can not be null");
			throw new Exception("File Name can not be null");
		}
		return file;
	}

	/**
	 * method to get the document based on documentId.
	 * 
	 * @param documentId
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserRelatedDocuments getAdminViewDocDetails(String documentId)
			throws Exception {
		UserRelatedDocuments userRelatedDocuments = null;
		if (StringUtils.isNotBlank(documentId)) {
			userRelatedDocuments = userRelatedDocumentsDAO.findOne(documentId);

		} else {
			logger.error("documentId can not be null");
			throw new Exception("documentId can not be null");
		}

		return userRelatedDocuments;
	}

	/**
	 * method for Admin manage terms and condition file.
	 * 
	 * @return
	 */
	@Override
	public ManageDocs AdminManageDocs() {
		ManageDocs manageDocs = new ManageDocs();
		int activeCount = 0;
		int inActiveCount = 0;

		List<File> fileList = new ArrayList<File>();
		List<com.sarvah.mbg.userprofile.response.AdminManageDocs> manageDocsList = new ArrayList<>();
		List<UserRelatedDocuments> userRelatedDocumentsList = userRelatedDocumentsDAO
				.findAll();

		for (UserRelatedDocuments documents : userRelatedDocumentsList) {
			com.sarvah.mbg.userprofile.response.AdminManageDocs adminManageDocs = new com.sarvah.mbg.userprofile.response.AdminManageDocs();
			adminManageDocs.setDocumentId(documents.getId());
			File file = documents.getFile();
			if (file != null) {
				fileList.add(file);
				adminManageDocs.setFile(file);
			}
			adminManageDocs.setUserTypes(documents.getForUserTypes());
			if (documents.isActive()) {
				activeCount++;
				adminManageDocs.setStatus("ACTIVE");
			} else {
				inActiveCount++;
				adminManageDocs.setStatus("INACTIVE");
			}
			manageDocsList.add(adminManageDocs);
		}

		manageDocs.setAdminManageDocs(manageDocsList);
		manageDocs.setTotalDocs(fileList.size());
		manageDocs.setActiveCount(activeCount);
		manageDocs.setInActiveCount(inActiveCount);
		return manageDocs;
	}

	/**
	 * method for update document based on documentId.
	 * 
	 * @param documentId
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserRelatedDocuments updateFile(String documentId) throws Exception {
		UserRelatedDocuments userRelatedDocuments = null;
		if (StringUtils.isNotBlank(documentId)) {
			userRelatedDocuments = userRelatedDocumentsDAO.findOne(documentId);
			if (userRelatedDocuments != null && userRelatedDocuments.isActive()) {
				userRelatedDocuments.setActive(false);
				userRelatedDocumentsDAO.save(userRelatedDocuments);
			} else {
				logger.error("userRelatedDocument doesn't exists in database or status can not be null");
				throw new Exception(
						"userRelatedDocument doesn't exists in database or status can not be null");
			}
		} else {
			logger.error("documentId can not be null");
			throw new Exception("documentId can not be null");
		}
		return userRelatedDocuments;
	}

	/**
	 * Method to get balance available with the user(Business Associate)
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserBalanceInfoResponse getUserBalance(String uid) throws Exception {
		UserBalanceInfoResponse userBalanceInfo = new UserBalanceInfoResponse();
		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findOne(uid);
			if (user != null) {
				userBalanceInfo.setCurrentBalance(user.getCurrentBalance());
				userBalanceInfo.setInvsetedBalance(user.getInvestedBalance());
			} else {
				logger.error("Invalid user");
				throw new Exception("Invalid user");
			}

		} else {
			logger.error("User Id cannot be null");
			throw new Exception("User Id cannot be null");
		}
		return userBalanceInfo;
	}

	/**
	 * Method to update balance
	 * 
	 * @param uid
	 * @param newBalance
	 * @param balance
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserBalanceInfoResponse updateUserBalance(String uid,
			String totalPrice, String newBalance, String note) throws Exception {
		UserBalanceInfoResponse userBalanceInfoResponse = new UserBalanceInfoResponse();
		double balance = 0.0;
		String userBalanceUpdatedInfo = null;
		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findOne(uid);
			UserInfo userInfo = userRepository.findByMongoUserId(uid);
			if (user != null && userInfo != null
					&& StringUtils.isNotBlank(totalPrice)
					&& StringUtils.isBlank(newBalance)) {
				balance = user.getCurrentBalance();
				double totalAmount = Double.parseDouble(totalPrice);
				if (balance >= totalAmount) {
					balance = balance - totalAmount;
					user.setCurrentBalance(balance);

					userDAO.save(user);

					if (StringUtils.isBlank(note)) {
						userBalanceUpdatedInfo = "User " + userInfo.getUserid()
								+ " balance is deducted of rs  " + totalPrice;
					} else {
						userBalanceUpdatedInfo = "User " + userInfo.getUserid()
								+ " balance is deducted of rs  " + totalPrice
								+ " Note: " + note;
					}

					updateUserAudit(userInfo, "BALANCE", userBalanceUpdatedInfo);

					userBalanceInfoResponse.setCurrentBalance(user
							.getCurrentBalance());
					userBalanceInfoResponse.setInvsetedBalance(user
							.getInvestedBalance());

				} else {
					logger.info("Insufficient balance ");
					throw new Exception("Insufficient balance");
				}
			} else if (user != null && StringUtils.isNotBlank(newBalance)
					&& StringUtils.isBlank(totalPrice)) {
				double addBalance = Double.parseDouble(newBalance);
				if (user.getCurrentBalance() != null) {
					user.setCurrentBalance(addBalance
							+ user.getCurrentBalance());
				} else {
					user.setCurrentBalance(addBalance);
				}
				if (user.getInvestedBalance() != null) {
					user.setInvestedBalance(addBalance
							+ user.getInvestedBalance());
				} else {
					user.setInvestedBalance(addBalance);
				}

				userDAO.save(user);

				if (StringUtils.isBlank(note)) {
					userBalanceUpdatedInfo = "User " + userInfo.getUserid()
							+ " balance is modified with new balance "
							+ newBalance;
				} else {
					userBalanceUpdatedInfo = "User " + userInfo.getUserid()
							+ " balance is modified with new balance "
							+ newBalance + " Note: " + note;
				}

				updateUserAudit(userInfo, "BALANCE", userBalanceUpdatedInfo);

				userCommunicationService.sendUserAmountUpdateInfo(user,
						newBalance, user.getCurrentBalance());

				userBalanceInfoResponse.setCurrentBalance(user
						.getCurrentBalance());
				userBalanceInfoResponse.setInvsetedBalance(user
						.getInvestedBalance());
				return userBalanceInfoResponse;
			}

		} else {
			logger.error("User Id cannot be null");
			throw new Exception("User Id cannot be null");
		}
		return userBalanceInfoResponse;
	}

	/**
	 * method for verify whether username already exist in the database or not.
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean serachUserName(String userName) throws Exception {

		logger.info(
				"Verifying username is already exist or not for username : {}",
				userName);

		boolean userVerification = false;

		User user = null;
		List<UserInfo> userInfo = null;

		if (StringUtils.isNotBlank(userName)) {

			boolean validUserName = EmailValidator.getInstance().isValid(
					userName);

			boolean validPhoneNumber = validatePhoneNumber(userName);

			if (validUserName) {
				user = userDAO.findByUsername(userName.toLowerCase());
				userInfo = userRepo.findByUsername(userName.toLowerCase());
			} else if (validPhoneNumber) {
				user = userDAO.findByUserPhoneNumber(userName);
				userInfo = userRepo.findByPhonenumber(Long.valueOf(userName));
			} else {
				logger.info("Invali email or phoneNumber");
			}

			if (user != null || userInfo.size() != 0) {
				userVerification = true;
				if (logger.isDebugEnabled()) {
					logger.debug("UserName : {} is exist in our system",
							userName);
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("UserName : {} is not exist in our system",
							userName);
				}
			}

		} else {
			logger.error("Usename can't be null");
			throw new Exception("Usename can't be null");
		}
		logger.info(
				"Verifying username is already exist or not for username : {}! Success",
				userName);
		return userVerification;
	}

	/**
	 * Method to delete the particular profile Project
	 * 
	 * @param userId
	 * @param projid
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserProjectProfile deleteMyProfileProject(String userId,
			String projid) throws Exception {
		UserProjectProfile userProjectProfile = userProjectProfileDAO
				.findOne(projid);
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findOne(userId);
			if (user != null && StringUtils.isNotBlank(projid)) {

				userProjectProfileDAO.delete(userProjectProfile);

			} else {
				throw new Exception("User doesn't exist");
			}
		} else {
			throw new Exception("User ID cannot be NULL");
		}
		return userProjectProfile;
	}

	@Override
	public UserSummaryViewResponse getSuperAdminManageUsers(String status,
			String searchValue, int page, int size, Sort sort) {
		// Fetching users to manage by admin
		List<UserSummaryView> manageusersList = new ArrayList<>();
		UserSummaryViewResponse userSummaryViewResponse = new UserSummaryViewResponse();
		Page<User> pageable = null;
		List<User> usersList = new ArrayList<>();

		Set<String> roleNames = new HashSet<>();
		roleNames.add("ENDUSER");
		roleNames.add("BUSINESSASSOCIATE");
		roleNames.add("ARCHITECT");
		roleNames.add("INTERIORDESIGNER");
		roleNames.add("PLUMBER");
		roleNames.add("PAINTER");
		roleNames.add("CARPENTER");
		roleNames.add("CIVILENGINEER");
		roleNames.add("VASTHUCONSULTANTS");
		roleNames.add("ELECTRICIANS");
		roleNames.add("BIMCONSULTANTS");
		roleNames.add("PROJECTCONSULTANT");
		roleNames.add("LANDSCAPEARCHITECT");
		roleNames.add("SURVEYOR");
		roleNames.add("WATERDIVINER");
		roleNames.add("VALUATOR");
		roleNames.add("BOREWELL");
		roleNames.add("FLOORINGCONTRACTOR");
		roleNames.add("FABRICATOR");
		roleNames.add("CIVILCONTRACTOR");
		roleNames.add("GEOLOGIST");
		roleNames.add("ELECTRICALCONSULTANT");

		// Fetching user based on userRoles
		if (StringUtils.isBlank(searchValue) && StringUtils.isBlank(status)) {
			pageable = userDAO.findByRoles_NameIn(roleNames, new PageRequest(
					page, size, sort));
			usersList.addAll(pageable.getContent());
		} else if (StringUtils.isNotBlank(searchValue)
				&& StringUtils.isBlank(status)) {
			pageable = userDAO
					.findByRoles_NameInAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							roleNames, searchValue, searchValue, searchValue,
							searchValue, searchValue, searchValue,
							new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		} else if (StringUtils.isNotBlank(status)
				&& StringUtils.isBlank(searchValue)) {
			pageable = userDAO.findByRoles_NameInAndStatus(roleNames,
					UserStatus.valueOf(status), new PageRequest(page, size,
							sort));
			usersList.addAll(pageable.getContent());
		} else if (StringUtils.isNotBlank(status)
				&& StringUtils.isNotBlank(searchValue)) {
			pageable = userDAO
					.findByRoles_NameInAndStatusAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
							roleNames, UserStatus.valueOf(status), searchValue,
							searchValue, searchValue, searchValue, searchValue,
							searchValue, new PageRequest(page, size, sort));
			usersList.addAll(pageable.getContent());
		}

		for (User user : usersList) {
			UserInfo userInfo = userRepository.findByMongoUserId(user.getId());
			if (user != null && userInfo != null) {
				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					UserSummaryView userSummaryView = new UserSummaryView();
					// User id
					userSummaryView.setUserId(user.getId());
					// First name
					userSummaryView.setFullName(user.getFirstName());
					// Email
					if (userInfo.getUsername() != null) {
						userSummaryView.setEmailId(userInfo.getUsername());
					}
					// PhoneNumber
					if (userInfo.getPhonenumber() != null) {
						userSummaryView.setPhoneNumber(userInfo
								.getPhonenumber().toString());
					}
					// verified
					if (userInfo.isVerified() == false
							&& userInfo.getUsername() == null) {
						userSummaryView.setVerified(true);
					} else {
						userSummaryView.setVerified(false);
					}
					// Status
					userSummaryView.setStatus(user.getStatus());
					// Role
					userSummaryView.setRole(role.getName());
					// Created date
					userSummaryView.setCreatedDate(user.getCreatedDate());

					// ShortList
					WishList wishList = wishListDAO.findByUser(user);
					if (wishList != null) {
						if (wishList.getProductIds() != null
								&& wishList.getProductIds().size() != 0) {
							userSummaryView
									.setTotalshortlistedProducts(wishList
											.getProductIds().size());
						}
					}

					// number of products purchased
					List<Order> orderList = userOrderRepository
							.findByUserInfo(userInfo);

					if (orderList != null && orderList.size() != 0) {
						userSummaryView.setTotalPurchasedOrders(orderList
								.size());
						Date orderedDate = orderList.get(orderList.size() - 1)
								.getOrderedDate();
						Date currentDate = new Date();

						int diffInDays = (int) ((currentDate.getTime() - orderedDate
								.getTime()) / (1000 * 60 * 60 * 24));
						if (diffInDays >= 1) {
							userSummaryView.setLastOrderDays(diffInDays);
						} else if (diffInDays == 0) {
							userSummaryView.setLastOrderDays(1);
						}
					}

					// customer code
					char[] fNameArray = user.getFirstName().toUpperCase()
							.toCharArray();
					System.out.println(fNameArray.length);
					if (fNameArray.length > 2) {
						String customerCode = "BONN" + fNameArray[0]
								+ fNameArray[1] + fNameArray[2]
								+ String.format("%05d", userInfo.getUserid());

						userSummaryView.setCustomerCode(customerCode);
					}

					manageusersList.add(userSummaryView);
				}
			}
		}

		userSummaryViewResponse.setUserSummaryView(manageusersList);
		// setting page attributes.
		userSummaryViewResponse.setTotalPages(pageable.getTotalPages());
		userSummaryViewResponse.setTotalElements(pageable.getTotalElements());
		userSummaryViewResponse.setNumber(pageable.getNumber());
		userSummaryViewResponse.setSize(pageable.getSize());
		return userSummaryViewResponse;
	}

	@Override
	public UserSummaryViewDetails getManageUserDetailsView(String userId)
			throws Exception {
		UserSummaryViewDetails userSummaryViewDetails = new UserSummaryViewDetails();
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findById(userId);
			UserInfo userInfo = userRepository.findByMongoUserId(userId);
			if (user != null && userInfo != null) {
				Set<Role> userRoles = user.getRoles();
				for (Role userRole : userRoles) {
					// UserId
					userSummaryViewDetails.setUserId(user.getId());
					// FirstName
					userSummaryViewDetails.setFirstName(user.getFirstName());
					// LastName
					if (user.getLastName() != null) {
						userSummaryViewDetails.setLastName(user.getLastName());
					}

					// FullName
					if (user.getFirstName() != null
							&& user.getLastName() != null) {
						userSummaryViewDetails.setFullName(user.getFirstName()
								+ " " + user.getLastName());
					} else {
						userSummaryViewDetails.setFullName(user.getFirstName());
					}
					// Email
					if (userInfo.getUsername() != null) {
						userSummaryViewDetails.setEmailId(userInfo
								.getUsername());
					}
					// PhoneNumber
					if (userInfo.getPhonenumber() != null) {
						userSummaryViewDetails.setPhoneNumber(userInfo
								.getPhonenumber().toString());
					}
					// verified
					if (userInfo.isVerified() == false
							&& userInfo.getUsername() == null) {
						userSummaryViewDetails.setVerified(true);
					} else {
						userSummaryViewDetails.setVerified(false);
					}
					// other skills
					if (user.getOtherSkillSets() != null) {
						userSummaryViewDetails.setOtherSkillSets(user
								.getOtherSkillSets());
					}

					// operating cities
					if (user.getOperatingCities() != null) {
						userSummaryViewDetails.setOperatingCities(user
								.getOperatingCities());
					}

					// Current Balance
					userSummaryViewDetails.setCurrentBalance(user
							.getCurrentBalance());

					// Invested Balance
					userSummaryViewDetails.setInvestedBalance(user
							.getInvestedBalance());

					if (user.getAddresses() != null) {
						userSummaryViewDetails
								.setAddresses(user.getAddresses());
					}
					userSummaryViewDetails.setStatus(user.getStatus());

					userSummaryViewDetails
							.setCreatedDate(user.getCreatedDate());

					if (user.getActiveSince() != null) {
						userSummaryViewDetails.setActiveSince(user
								.getActiveSince());
					}

					// ShortList
					WishList wishList = wishListDAO.findByUser(user);
					if (wishList != null) {
						if (wishList.getProductIds() != null
								&& wishList.getProductIds().size() != 0) {
							userSummaryViewDetails
									.setTotalshortlistedProducts(wishList
											.getProductIds().size());
						}
					}
					// number of products purchased
					List<Order> orderList = userOrderRepository
							.findByUserInfo(userInfo);

					if (orderList != null && orderList.size() != 0) {
						userSummaryViewDetails
								.setTotalPurchasedOrders(orderList.size());
					}

					// customer code
					char[] fNameArray = user.getFirstName().toUpperCase()
							.toCharArray();

					char[] roleArray = userRole.getName().toUpperCase()
							.toCharArray();

					String customerCode = "BONN" + roleArray[0] + roleArray[1]
							+ fNameArray[0] + fNameArray[1] + fNameArray[2]
							+ String.format("%05d", userInfo.getUserid());

					userSummaryViewDetails.setCustomerCode(customerCode);

					// Title
					if (user.getTitle() != null) {
						userSummaryViewDetails.setTitle(user.getTitle());
					}
					// Vat Number
					if (user.getVatNumber() != null) {
						userSummaryViewDetails
								.setVatNumber(user.getVatNumber());
					}
				}
			} else {
				logger.error("User doesn't exists in the system");
				throw new Exception("User doesn't exists in the system");
			}

		} else {
			logger.error("Userid cannot be null");
			throw new Exception("Userid cannot be null");
		}

		return userSummaryViewDetails;

	}

	@Override
	public void sendContactInfo(ContactInfoResponse contactInfoResponse)
			throws UserCommException {
		String name = contactInfoResponse.getName();
		String email = contactInfoResponse.getEmail();
		String mobileNumber = contactInfoResponse.getMobileNumber();
		String zipcode = contactInfoResponse.getZipcode();
		String description = contactInfoResponse.getDescription();
		String serviceProviderId = contactInfoResponse.getServiceProviderId();
		String constructionType = contactInfoResponse.getConstructionType();
		String asSoonRequired = contactInfoResponse.getAsSoonRequired();
		String city = null;

		String roleName = null;
		String toEmail = null;
		String toPhoneNumber = null;
		String spFullName = null;
		String spZipcode = null;

		User enduser = null;
		enduser = userDAO.findByUsernameIgnoreCase(email);
		if (enduser != null) {
			Set<Address> addresses = enduser.getAddresses();
			for (Address address : addresses) {
				city = address.getCity();
			}
		} else {
			enduser = userDAO.findByUserPhoneNumber(mobileNumber);
			if (enduser != null) {
				Set<Address> addresses = enduser.getAddresses();
				for (Address address : addresses) {
					city = address.getCity();
				}
			}
		}

		if (city == null) {
			city = "City name not given";
		}

		if (zipcode == null) {
			zipcode = "Zipcode not given";
		}

		User user = userDAO.findById(serviceProviderId);
		UserInfo userInfo = userRepository.findByMongoUserId(serviceProviderId);
		if (user != null && userInfo != null) {

			Set<Role> roles = user.getRoles();
			for (Role role : roles) {
				roleName = role.getName();
			}

			Set<Address> addresses = user.getAddresses();

			for (Address address : addresses) {
				spZipcode = address.getZipcode().toString();
			}

			if (userInfo.getUsername() != null) {
				toEmail = userInfo.getUsername();
			}

			if (userInfo.getPhonenumber() != null) {
				toPhoneNumber = userInfo.getPhonenumber().toString();
			}

			if (userInfo.getFirstname() != null
					&& userInfo.getLastname() != null) {
				spFullName = userInfo.getFirstname() + " "
						+ userInfo.getLastname();
			} else {
				spFullName = userInfo.getFirstname();
			}

			if (email == null) {
				email = "EmailId not given";
			}
			if (mobileNumber == null) {
				mobileNumber = "MobileNum not given";
			}
			// Sending enduser contact details to service provider
			userCommunicationService.sendContactInfo(name, email, mobileNumber,
					zipcode, description, toEmail, toPhoneNumber, roleName,
					constructionType, asSoonRequired, city);

			// Sending service provider contact details to enduser
			userCommunicationService.sendServiceProviderInfoForEnduser(
					spFullName, toEmail, toPhoneNumber, spZipcode, roleName,
					email, mobileNumber);

			String note = "User " + name
					+ " tried to contact our ServiceProvider " + spFullName;

			updateUserAudit(userInfo, "CONTACTINFO", note);
		}

	}

	@Override
	public Long getUserCountByRoleAndStatusAndOperatingCity(String roleName,
			String status, String operatingCityName) {

		Set<String> intrestedCities = new HashSet<>();

		if (logger.isDebugEnabled()) {
			logger.debug("fetching all the users based on roleName, status and intrestedCityName");
		}
		List<User> users = new ArrayList<>();

		if (operatingCityName.contains("World")) {
			intrestedCities.add(operatingCityName);
			users = userDAO.findByRoles_NameAndStatusAndOperatingCitiesIn(
					roleName, status, intrestedCities);
		} else if (operatingCityName.contains("India")) {
			intrestedCities.add(operatingCityName);
			users = userDAO.findByRoles_NameAndStatusAndOperatingCitiesIn(
					roleName, status, intrestedCities);
		} else {
			Set<User> usersSet = new HashSet<User>();

			usersSet.addAll(userDAO
					.findByRoles_NameAndStatusAndOperatingCitiesContains(
							roleName, status, "World"));

			usersSet.addAll(userDAO
					.findByRoles_NameAndStatusAndOperatingCitiesContains(
							roleName, status, "India"));

			String[] intrestedCityArray = operatingCityName.split(",");

			for (String intrestedCity : intrestedCityArray) {
				intrestedCities.add(intrestedCity);
			}

			usersSet.addAll(userDAO
					.findByRoles_NameAndStatusAndOperatingCitiesIn(roleName,
							status, intrestedCities));

			Set<String> userIds = new HashSet<>();

			for (User user : usersSet) {
				userIds.add(user.getId());
			}

			users = userDAO.findByIdIn(userIds);
		}
		long count = users.size();
		return count;
	}

	@Override
	public Long getUserCountByRoleAndStatusAndSkillSet(String roleName,
			String status, String skillSetName) {
		Set<String> skillsets = new HashSet<>();
		String[] skillSetArray = skillSetName.split(",");

		for (String skillSet : skillSetArray) {
			skillsets.add(skillSet);
		}
		return userDAO.countByRoles_NameAndStatusAndOtherSkillSetsIn(roleName,
				status, skillsets);
	}

	@Override
	public Set<String> getUsersContactNum(String roleId) throws Exception {
		Set<String> contactNumbers = null;
		Role role = roleDAO.findById(roleId);
		if (role != null) {
			contactNumbers = new HashSet<>();
			List<User> users = userDAO.findByRoles_Name(role.getName());
			for (User user : users) {
				if (user != null) {
					UserInfo userInfo = userRepository.findByMongoUserId(user
							.getId());
					if (userInfo != null && userInfo.getPhonenumber() != null) {
						contactNumbers.add(String.valueOf(userInfo
								.getPhonenumber()));
					} else {
						logger.error(
								"User not exist in mysql database for roleId : {} or phonenumber field is null",
								roleId);
					}
				} else {
					logger.error(
							"User not exist in mongo database for roleId : {}",
							roleId);
				}
			}
		} else {
			logger.error("Role not exist in database for roleId: {}", roleId);
		}
		return contactNumbers;
	}

	@Override
	public Set<String> getUsersEmailId(String roleId) {
		Set<String> emailIds = null;
		Role role = roleDAO.findById(roleId);
		if (role != null) {
			emailIds = new HashSet<>();
			List<User> users = userDAO.findByRoles_Name(role.getName());
			for (User user : users) {
				if (user != null) {
					UserInfo userInfo = userRepository.findByMongoUserId(user
							.getId());
					if (userInfo != null && userInfo.getUsername() != null) {
						emailIds.add(userInfo.getUsername());
					} else {
						logger.error(
								"User not exist in mysql database for roleId : {} or phonenumber field is null",
								roleId);
					}
				} else {
					logger.error(
							"User not exist in mongo database for roleId : {}",
							roleId);
				}
			}
		} else {
			logger.error("Role not exist in database for roleId: {}", roleId);
		}
		return emailIds;
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

	/**
	 * Updating user project profile
	 * 
	 * @throws Exception
	 * 
	 */
	@Override
	public UserProjectProfile updateProfileProjects(String userId,
			String profileName, String desc, String projectType,
			String profileProjectid) throws Exception {

		UserProjectProfile userProjectProfile = new UserProjectProfile();
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findById(userId);
			if (user != null) {
				if (StringUtils.isNotBlank(profileProjectid)) {
					userProjectProfile = userProjectProfileDAO
							.findOne(profileProjectid);
					if (userProjectProfile != null) {
						if (StringUtils.isNotBlank(profileName)) {
							userProjectProfile.setProfileName(profileName);
						}
						if (StringUtils.isNotBlank(desc)) {
							Description descr = new Description();
							descr.setLang("Eng");
							descr.setVal(desc);
							userProjectProfile.setDesc(descr);
						}
						if (projectType != null) {
							userProjectProfile
									.setProjectTypesHandled(ProjectType
											.valueOf(projectType));
						}

						userProjectProfileDAO.save(userProjectProfile);

						logger.info("updating user Projects");
					} else {
						throw new Exception("User Profile doesn't exist");
					}
				} else {
					throw new Exception("User Profile ProjectId can'nt be null");
				}
			} else {
				throw new Exception("User doesn't exist");
			}

		} else {
			throw new Exception("User Id should not be null");
		}

		return userProjectProfile;
	}

	@Override
	public UserProjectProfile deleteProfileProjectFile(String userId,
			String projid, String fileUrl, String fileType) throws Exception {

		UserProjectProfile userProjectProfile = null;
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findOne(userId);
			if (user != null && StringUtils.isNotBlank(projid)) {
				userProjectProfile = userProjectProfileDAO.findOne(projid);
				if (userProjectProfile != null) {

					List<Image> images = userProjectProfile.getProfileImages();
					if (images != null) {

						if (fileType.equalsIgnoreCase("image")) {

							List<Image> images1 = new ArrayList<>();
							images1.addAll(userProjectProfile
									.getProfileImages());
							for (Image image : images1) {
								// Check for URL matching
								if (image.getUrl().equalsIgnoreCase(fileUrl)) {
									// Delete file from the Azure
									filestorage.deleteFile(
											AzureFileStorage.IMG_CONTAINER,
											fileUrl);
									// Remove image from the list
									images.remove(image);
								}
							}
						}

					}
					userProjectProfile.setProfileImages(images);
					userProjectProfileDAO.save(userProjectProfile);
				} else {
					logger.error(
							"UserProjectProfile doesn't exist for UserProjectProfileID : {}",
							projid);
					throw new Exception(
							"UserProjectProfile doesn't exist for UserProjectProfileID : "
									+ projid);
				}
			} else {
				logger.error("user doesn't exist in the system or Project Id cannot be null");
				throw new Exception(
						"user doesn't exist in the system or Project Id cannot be null");
			}
		} else {
			logger.error("User Id cannot be null");
			throw new Exception("User Id cannot be null");
		}

		return userProjectProfile;
	}

	@Override
	public QuoteRequest sendRequirementToSellers(String customerId,
			String zipcode, List<QuoteProductRequestParam> products,
			String buildonnQuoteRequestId, String deliveryDate,
			Double buildOnnShippingCharge, String note, String alertType,
			String followupDate, String reasonForReject,
			String quoteSellingPriceIncludeTax) throws Exception {
		QuoteRequest quoteRequest = null;
		String itemNameAndQuantityTypeList = null;
		int itemCount = 0;
		List<ItemInfoForCommunication> items = new ArrayList<>();
		Set<User> dealers = new HashSet<>();
		Set<String> dealersIds = new HashSet<>();
		if (StringUtils.isNotBlank(customerId)) {
			User customer = userDAO.findById(customerId);
			if (customer != null) {
				int quoteCount = quoteRequestDAO
						.countByBuildOnnQuoteRequestId(buildonnQuoteRequestId);
				if (quoteCount > 0) {
					throw new Exception(
							"Quote already exist for BuildOnn QuoteId: "
									+ buildonnQuoteRequestId);
				}
				quoteRequest = new QuoteRequest();
				// Customer
				quoteRequest.setCustomer(customer);
				// Zipcode
				quoteRequest.setZipcode(zipcode);
				// Note
				quoteRequest.setNote(note);
				// followupdate
				quoteRequest.setFollowupDate(followupDate);
				// Seller Quote Product
				List<QuoteRequestProduct> quoteRequestProductSet = new ArrayList<>();

				for (QuoteProductRequestParam product : products) {
					QuoteRequestProduct quoteRequestProduct = new QuoteRequestProduct();
					if (StringUtils.isNotBlank(product.getProductName())) {
						quoteRequestProduct.setProductName(product
								.getProductName());
					} else {
						throw new Exception("Product Name is null");
					}
					quoteRequestProduct.setProductId(product.getProductId());
					quoteRequestProduct.setBrand(product.getBrand());
					quoteRequestProduct.setQuantityType(product
							.getQuantityType());
					quoteRequestProduct.setQuantity(product.getQuantity());

					Set<SellerQuoteProductPricing> sellerQuoteProductPricingSet = null;
					if (product.getProductPricings() != null
							&& product.getProductPricings().size() != 0) {
						sellerQuoteProductPricingSet = new HashSet<>();
						for (SellerQuoteProductPricingRequestParam sellerProductPricing : product
								.getProductPricings()) {
							User seller = userDAO.findById(sellerProductPricing
									.getSellerId());
							if (seller != null) {
								SellerQuoteProductPricing productPricing = new SellerQuoteProductPricing();
								productPricing.setSeller(seller);
								productPricing.setMrp(sellerProductPricing
										.getMrp());
								productPricing
										.setSellingPrice(sellerProductPricing
												.getSellingPrice());
								productPricing
										.setBuildOnnSellingPrice(sellerProductPricing
												.getBuildOnnSellingPrice());
								productPricing
										.setShippingCharge(sellerProductPricing
												.getShippingCharge());
								productPricing.setDiscount(sellerProductPricing
										.getDiscount());
								productPricing.setTax(sellerProductPricing
										.getTax());
								// Extra Benefits
								productPricing
										.setExtraBenefits(sellerProductPricing
												.getExtraBenefits());
								sellerQuoteProductPricingDAO
										.save(productPricing);
								sellerQuoteProductPricingSet
										.add(productPricing);
								if (dealers.size() != 0) {
									if (!dealersIds.contains(seller.getId())) {
										dealers.add(seller);
										dealersIds.add(seller.getId());
									}
								} else {
									dealers.add(seller);
									dealersIds.add(seller.getId());
								}
							}
						}
					}
					quoteRequestProduct
							.setQuoteProductPricings(sellerQuoteProductPricingSet);
					quoteRequestProductDAO.save(quoteRequestProduct);
					quoteRequestProductSet.add(quoteRequestProduct);

					ItemInfoForCommunication item = new ItemInfoForCommunication();
					item.setItemId(product.getProductId());
					item.setItemName(product.getProductName());
					item.setBrand(product.getBrand());
					item.setItemQuantityType(product.getQuantityType());
					item.setItemQuantity(product.getQuantity());
					items.add(item);
					if (itemCount == 0) {
						if (product.getProductId() != null
								&& product.getProductId() != "") {
							itemNameAndQuantityTypeList = product
									.getProductName()
									+ "-"
									+ product.getQuantity()
									+ " "
									+ product.getQuantityType() + "(s)" + ",";
						} else {
							itemNameAndQuantityTypeList = product
									.getProductName()
									+ "-"
									+ product.getQuantity()
									+ " "
									+ product.getQuantityType() + ",";
						}
					} else {
						if (itemCount < 3) {
							if (product.getProductId() != null
									&& product.getProductId() != "") {
								itemNameAndQuantityTypeList = itemNameAndQuantityTypeList
										+ product.getProductName()
										+ "-"
										+ product.getQuantity()
										+ " "
										+ product.getQuantityType()
										+ "(s)"
										+ ",";
							} else {
								itemNameAndQuantityTypeList = itemNameAndQuantityTypeList
										+ product.getProductName()
										+ "-"
										+ product.getQuantity()
										+ " "
										+ product.getQuantityType() + ",";
							}
						}
					}

					itemCount++;
				}
				quoteRequest.setProducts(quoteRequestProductSet);

				quoteRequest.setBuildOnnQuoteRequestId(buildonnQuoteRequestId);
				quoteRequest.setDeliveryDate(deliveryDate);
				quoteRequest.setStatus(QuoteStatus.CREATED);
				if (StringUtils.isNotBlank(reasonForReject)) {
					quoteRequest.setReasonForReject(reasonForReject);
				}
				if (StringUtils.isNotBlank(quoteSellingPriceIncludeTax)
						&& quoteSellingPriceIncludeTax.equalsIgnoreCase("TRUE")) {
					quoteRequest.setQuoteSellingPriceIncludeTax(true);
				} else {
					quoteRequest.setQuoteSellingPriceIncludeTax(false);
				}

				quoteRequestDAO.save(quoteRequest);

				String zipcode1 = null;
				String deliveryDate1 = null;
				if (quoteRequest.getZipcode() != null) {
					zipcode1 = quoteRequest.getZipcode();
				} else {
					zipcode1 = "Not Given";
				}

				if (quoteRequest.getDeliveryDate() != null) {
					deliveryDate1 = quoteRequest.getDeliveryDate();
				} else {
					deliveryDate1 = "Not Given";
				}

				if (StringUtils.isBlank(alertType)) {
					alertType = "NOt Given";
				}

				// Send Quote Request confirmation to customer
				if (!alertType.equalsIgnoreCase("SaveToDraft")) {
					// Send Quote Request details to dealer
					if (alertType.equalsIgnoreCase("SendToSeller")) {
						if (dealers.size() != 0) {
							userCommunicationService.sendQuoteRequestToDealer(
									dealers,
									quoteRequest.getBuildOnnQuoteRequestId(),
									itemNameAndQuantityTypeList, itemCount,
									zipcode);
						}
					}

					userCommunicationService
							.sendQuoteRequestConfirmationToCustomer(customer,
									quoteRequest.getBuildOnnQuoteRequestId(),
									zipcode1, deliveryDate1, items);

					User admin = userDAO
							.findByRoles_NameIsIgnoreCase("SUPERADMIN");
					// Send Quote Request details to Admin
					userCommunicationService.sendQuoteRequestInfoToAdmin(admin,
							customer, quoteRequest.getBuildOnnQuoteRequestId(),
							zipcode1, deliveryDate1, items);
				}
			} else {
				throw new Exception("Customer is null for customerId:{}"
						+ customerId);
			}
		} else {
			throw new Exception("Customer id should not be blank");
		}
		return quoteRequest;
	}

	@Override
	public QuoteRequest updateQuoteRequest(String userId,
			String quoteRequestId, String status,
			List<QuoteProductRequestParam> prods, String zipcode,
			String deliveryDate, String bonnQuoteRequestId,
			Double buildOnnShippingCharge, String note, String alertType,
			String followupDate, String reasonForReject) throws Exception {

		QuoteRequest quoteRequest = null;
		if (StringUtils.isNotBlank(quoteRequestId)) {
			quoteRequest = quoteRequestDAO.findOne(quoteRequestId);
			if (quoteRequest != null) {
				// status
				if (StringUtils.isNotBlank(status)) {
					quoteRequest.setStatus(QuoteStatus.valueOf(status));
				}
				// zipcode
				if (StringUtils.isNotBlank(zipcode)) {
					quoteRequest.setZipcode(zipcode);
				}
				// delivery date
				if (StringUtils.isNotBlank(deliveryDate)) {
					quoteRequest.setDeliveryDate(deliveryDate);
				}

				// Buildonn quoteRequestId
				if (StringUtils.isNotBlank(bonnQuoteRequestId)) {
					quoteRequest.setBuildOnnQuoteRequestId(bonnQuoteRequestId);
				}

				// Note
				if (StringUtils.isNotBlank(note)) {
					quoteRequest.setNote(note);
				}
				// followup date
				if (StringUtils.isNotBlank(followupDate)) {
					quoteRequest.setFollowupDate(followupDate);
				}
				// reasonForReject
				if (StringUtils.isNotBlank(reasonForReject)) {
					quoteRequest.setReasonForReject(reasonForReject);
				}
				// product
				List<QuoteRequestProduct> products = quoteRequest.getProducts();
				Map<Integer, QuoteRequestProduct> newProdMap = new HashMap<>();
				int indexCount = -1;
				if (prods != null && prods.size() != 0) {
					for (QuoteProductRequestParam quoteProductRequestParam : prods) {
						indexCount++;
						for (QuoteRequestProduct quoteRequestProduct : products) {

							if (quoteProductRequestParam.getQuoteProductId() != null) {

								if (quoteRequestProduct.getId()
										.equalsIgnoreCase(
												quoteProductRequestParam
														.getQuoteProductId())) {
									quoteRequestProduct
											.setProductName(quoteProductRequestParam
													.getProductName());
									quoteRequestProduct
											.setProductId(quoteProductRequestParam
													.getProductId());
									quoteRequestProduct
											.setBrand(quoteProductRequestParam
													.getBrand());
									quoteRequestProduct
											.setQuantityType(quoteProductRequestParam
													.getQuantityType());
									quoteRequestProduct
											.setQuantity(quoteProductRequestParam
													.getQuantity());

									Set<SellerQuoteProductPricingRequestParam> productPricings = quoteProductRequestParam
											.getProductPricings();
									Set<String> latestSellerIds = new HashSet<String>();
									if (productPricings != null
											&& productPricings.size() != 0) {
										for (SellerQuoteProductPricingRequestParam productPricingRequestParam : productPricings) {
											latestSellerIds
													.add(productPricingRequestParam
															.getSellerId());
										}
									} else {
										if (quoteRequestProduct
												.getQuoteProductPricings() != null
												&& quoteRequestProduct
														.getQuoteProductPricings()
														.size() != 0) {

											for (SellerQuoteProductPricing quoteProductPricing : quoteRequestProduct
													.getQuoteProductPricings()) {
												if (quoteProductPricing != null) {
													sellerQuoteProductPricingDAO
															.delete(quoteProductPricing);
													quoteRequestProduct
															.getQuoteProductPricings()
															.remove(quoteProductPricing);
													quoteRequestProductDAO
															.save(quoteRequestProduct);
												}
											}
											break;
										}
									}

									Set<SellerQuoteProductPricing> quoteProductPricings = new HashSet<>();
									if (quoteRequestProduct
											.getQuoteProductPricings() != null
											&& quoteRequestProduct
													.getQuoteProductPricings()
													.size() != 0) {
										quoteProductPricings = quoteRequestProduct
												.getQuoteProductPricings();
									}
									Set<String> sellerIds = new HashSet<>();
									if (quoteProductPricings != null
											&& quoteProductPricings.size() != 0) {
										for (SellerQuoteProductPricing quoteProductPricing : quoteProductPricings) {
											if (quoteProductPricing != null) {
												sellerIds
														.add(quoteProductPricing
																.getSeller()
																.getId());
											}
										}
									}

									Set<SellerQuoteProductPricing> newSellerPricingSet = new HashSet<>();

									for (SellerQuoteProductPricingRequestParam productPricing : productPricings) {
										if (quoteProductPricings != null
												&& quoteProductPricings.size() != 0) {
											for (SellerQuoteProductPricing quoteProductPricing : quoteProductPricings) {
												if (quoteProductPricing != null) {
													if (sellerIds
															.contains(productPricing
																	.getSellerId())) {
														if (quoteProductPricing
																.getSeller()
																.getId()
																.equalsIgnoreCase(
																		productPricing
																				.getSellerId())) {
															quoteProductPricing
																	.setMrp(productPricing
																			.getMrp());
															quoteProductPricing
																	.setSellingPrice(productPricing
																			.getSellingPrice());
															quoteProductPricing
																	.setBuildOnnSellingPrice(productPricing
																			.getBuildOnnSellingPrice());
															quoteProductPricing
																	.setShippingCharge(productPricing
																			.getShippingCharge());

															quoteProductPricing
																	.setDiscount(productPricing
																			.getDiscount());
															quoteProductPricing
																	.setTax(productPricing
																			.getTax());
															// Extra Benefits
															quoteProductPricing
																	.setExtraBenefits(productPricing
																			.getExtraBenefits());
															sellerQuoteProductPricingDAO
																	.save(quoteProductPricing);
														}
													} else {
														SellerQuoteProductPricing sellerQuoteProductPricing = new SellerQuoteProductPricing();
														User seller = userDAO
																.findById(productPricing
																		.getSellerId());
														sellerQuoteProductPricing
																.setSeller(seller);
														sellerQuoteProductPricing
																.setMrp(productPricing
																		.getMrp());
														sellerQuoteProductPricing
																.setSellingPrice(productPricing
																		.getSellingPrice());
														sellerQuoteProductPricing
																.setBuildOnnSellingPrice(productPricing
																		.getBuildOnnSellingPrice());
														sellerQuoteProductPricing
																.setShippingCharge(productPricing
																		.getShippingCharge());
														sellerQuoteProductPricing
																.setDiscount(productPricing
																		.getDiscount());
														sellerQuoteProductPricing
																.setTax(productPricing
																		.getTax());
														// Extra Benefits
														sellerQuoteProductPricing
																.setExtraBenefits(productPricing
																		.getExtraBenefits());
														sellerQuoteProductPricingDAO
																.save(sellerQuoteProductPricing);
														newSellerPricingSet
																.add(sellerQuoteProductPricing);
													}

													if (!latestSellerIds
															.contains(quoteProductPricing
																	.getSeller()
																	.getId())) {
														sellerQuoteProductPricingDAO
																.delete(quoteProductPricing);
													}
												}
											}
										} else {
											SellerQuoteProductPricing sellerQuoteProductPricing = new SellerQuoteProductPricing();
											User seller = userDAO
													.findById(productPricing
															.getSellerId());
											sellerQuoteProductPricing
													.setSeller(seller);
											sellerQuoteProductPricing
													.setMrp(productPricing
															.getMrp());
											sellerQuoteProductPricing
													.setSellingPrice(productPricing
															.getSellingPrice());
											sellerQuoteProductPricing
													.setBuildOnnSellingPrice(productPricing
															.getBuildOnnSellingPrice());
											sellerQuoteProductPricing
													.setShippingCharge(productPricing
															.getShippingCharge());
											sellerQuoteProductPricing
													.setDiscount(productPricing
															.getDiscount());
											sellerQuoteProductPricing
													.setTax(productPricing
															.getTax());
											// Extra Benefits
											sellerQuoteProductPricing
													.setExtraBenefits(productPricing
															.getExtraBenefits());
											sellerQuoteProductPricingDAO
													.save(sellerQuoteProductPricing);
											newSellerPricingSet
													.add(sellerQuoteProductPricing);

										}
									}

									if (newSellerPricingSet != null
											&& newSellerPricingSet.size() != 0) {
										quoteProductPricings
												.addAll(newSellerPricingSet);
									}
									if (quoteRequestProduct
											.getQuoteProductPricings() != null
											&& quoteRequestProduct
													.getQuoteProductPricings()
													.size() != 0) {
										quoteRequestProduct
												.getQuoteProductPricings()
												.addAll(quoteProductPricings);
									} else {
										quoteRequestProduct
												.setQuoteProductPricings(quoteProductPricings);
									}
									quoteRequestProductDAO
											.save(quoteRequestProduct);
								}
							} else {
								QuoteRequestProduct quoteRequestProduct1 = new QuoteRequestProduct();
								if (StringUtils
										.isNotBlank(quoteProductRequestParam
												.getProductName())) {
									quoteRequestProduct1
											.setProductName(quoteProductRequestParam
													.getProductName());
								} else {
									throw new Exception("Product Name is null");
								}
								quoteRequestProduct1
										.setProductId(quoteProductRequestParam
												.getProductId());
								quoteRequestProduct1
										.setBrand(quoteProductRequestParam
												.getBrand());
								quoteRequestProduct1
										.setQuantityType(quoteProductRequestParam
												.getQuantityType());
								quoteRequestProduct1
										.setQuantity(quoteProductRequestParam
												.getQuantity());

								Set<SellerQuoteProductPricing> sellerQuoteProductPricingSet = null;

								if (quoteProductRequestParam
										.getProductPricings() != null
										&& quoteProductRequestParam
												.getProductPricings().size() != 0) {
									sellerQuoteProductPricingSet = new HashSet<>();
									for (SellerQuoteProductPricingRequestParam sellerProductPricing : quoteProductRequestParam
											.getProductPricings()) {
										User seller = userDAO
												.findById(sellerProductPricing
														.getSellerId());
										if (seller != null) {
											SellerQuoteProductPricing productPricing = new SellerQuoteProductPricing();
											productPricing.setSeller(seller);
											productPricing
													.setMrp(sellerProductPricing
															.getMrp());
											productPricing
													.setSellingPrice(sellerProductPricing
															.getSellingPrice());
											productPricing
													.setBuildOnnSellingPrice(sellerProductPricing
															.getBuildOnnSellingPrice());
											productPricing
													.setShippingCharge(sellerProductPricing
															.getShippingCharge());
											productPricing
													.setDiscount(sellerProductPricing
															.getDiscount());
											productPricing
													.setTax(sellerProductPricing
															.getTax());
											// Extra Benefits
											productPricing
													.setExtraBenefits(sellerProductPricing
															.getExtraBenefits());
											sellerQuoteProductPricingDAO
													.save(productPricing);
											sellerQuoteProductPricingSet
													.add(productPricing);
										}
									}
								}
								quoteRequestProduct1
										.setQuoteProductPricings(sellerQuoteProductPricingSet);
								quoteRequestProductDAO
										.save(quoteRequestProduct1);
								newProdMap
										.put(indexCount, quoteRequestProduct1);
								break;
							}
						}
					}
				}
				Set<Entry<Integer, QuoteRequestProduct>> newProdEntrySet = newProdMap
						.entrySet();
				for (Entry<Integer, QuoteRequestProduct> newProdEntry : newProdEntrySet) {
					quoteRequest.getProducts().add(newProdEntry.getKey(),
							newProdEntry.getValue());
				}
				quoteRequestDAO.save(quoteRequest);
			}
		}

		if (StringUtils.isBlank(alertType)) {
			alertType = "Not Given";
		}

		if (StringUtils.isBlank(deliveryDate)) {
			deliveryDate = "Not Given";
		}

		Set<User> dealers = new HashSet<>();
		String sellerId = null;
		if (alertType.equalsIgnoreCase("SaveAndAlertToCustomer")
				|| alertType.equalsIgnoreCase("SendToSeller")) {
			String itemNameAndQuantityTypeList = null;
			int itemCount = 0;
			List<ItemInfoForCommunication> items = new ArrayList<>();
			for (QuoteProductRequestParam prod : prods) {
				ItemInfoForCommunication item = new ItemInfoForCommunication();
				item.setItemId(prod.getProductId());
				item.setItemName(prod.getProductName());
				item.setBrand(prod.getBrand());
				item.setItemQuantityType(prod.getQuantityType());
				item.setItemQuantity(prod.getQuantity());
				items.add(item);
				if (itemCount == 0) {
					if (prod.getProductId() != null
							&& prod.getProductId() != "") {
						itemNameAndQuantityTypeList = prod.getProductName()
								+ "-" + prod.getQuantity() + " "
								+ prod.getQuantityType() + "(s)" + ",";
					} else {
						itemNameAndQuantityTypeList = prod.getProductName()
								+ "-" + prod.getQuantity() + " "
								+ prod.getQuantityType() + ",";
					}
				} else {
					if (itemCount < 3) {
						if (prod.getProductId() != null
								&& prod.getProductId() != "") {
							itemNameAndQuantityTypeList = itemNameAndQuantityTypeList
									+ prod.getProductName()
									+ "-"
									+ prod.getQuantity()
									+ " "
									+ prod.getQuantityType() + "(s)" + ",";
						} else {
							itemNameAndQuantityTypeList = itemNameAndQuantityTypeList
									+ prod.getProductName()
									+ "-"
									+ prod.getQuantity()
									+ " "
									+ prod.getQuantityType() + ",";
						}
					}
				}

				if (itemCount == 0) {
					Set<SellerQuoteProductPricingRequestParam> productPricings = prod
							.getProductPricings();
					for (SellerQuoteProductPricingRequestParam productPricing : productPricings) {
						sellerId = productPricing.getSellerId();
					}
				}

				itemCount++;
			}

			if (StringUtils.isNotBlank(sellerId)) {
				User dealer = userDAO.findById(sellerId);
				if (dealer != null) {
					dealers.add(dealer);
				}
			}

			// Send Quote Request details to dealer
			if (alertType.equalsIgnoreCase("SendToSeller")) {
				if (dealers.size() != 0) {
					userCommunicationService.sendQuoteRequestToDealer(dealers,
							quoteRequest.getBuildOnnQuoteRequestId(),
							itemNameAndQuantityTypeList, itemCount,
							quoteRequest.getZipcode());
				}
			}

			userCommunicationService.sendQuoteRequestConfirmationToCustomer(
					quoteRequest.getCustomer(),
					quoteRequest.getBuildOnnQuoteRequestId(),
					quoteRequest.getZipcode(), deliveryDate, items);

			User admin = userDAO.findByRoles_NameIsIgnoreCase("SUPERADMIN");
			// Send Quote Request details to Admin
			userCommunicationService.sendQuoteRequestInfoToAdmin(admin,
					quoteRequest.getCustomer(),
					quoteRequest.getBuildOnnQuoteRequestId(),
					quoteRequest.getZipcode(), deliveryDate, items);
		}
		return quoteRequest;
	}

	public void shareQuoteWithCustomer(String userId, String quoteRequestId,
			List<ItemInfoForCommunication> products) throws UserCommException {
		double orderAmount = 0.0;
		if (StringUtils.isNotBlank(userId)) {
			User customer = userDAO.findById(userId);
			if (customer != null) {
				for (ItemInfoForCommunication product : products) {
					orderAmount = orderAmount
							+ (Double.valueOf(product.getItemQuantity()) * Double
									.valueOf(product.getItemPrice()));

					if (product.getItemShippingChrg() != null) {
						orderAmount = orderAmount
								+ Double.valueOf(product.getItemShippingChrg());
					}

					if (product.getQuoteProductPricingId() != null) {
						SellerQuoteProductPricing sellerQuoteProductPricing = sellerQuoteProductPricingDAO
								.findOne(product.getQuoteProductPricingId());
						if (sellerQuoteProductPricing != null
								&& product.getShared().equalsIgnoreCase("YES")) {
							sellerQuoteProductPricing.setShared(true);
						}
						sellerQuoteProductPricingDAO
								.save(sellerQuoteProductPricing);
					}

				}
				userCommunicationService.shareQuoteWithCustomer(customer,
						quoteRequestId, orderAmount, products);
			}
		}
	}

	@Override
	public void sendPromoSms(String promoInfo, String mobileNumbers)
			throws JMSException {
		userCommunicationService.sendPromoSms(promoInfo, mobileNumbers);
	}

	@Override
	public void sendPromoSmsByRolewise(String promoInfo, Set<String> roleNames)
			throws JMSException, InterruptedException {
		String mobileNumbers = null;
		Set<String> mobileNumberSet = new HashSet<>();
		for (String roleName : roleNames) {
			Role role = roleDAO.findByNameAllIgnoreCase(roleName);
			if (role != null) {
				List<User> users = userDAO.findByRoles_NameAndStatus(
						role.getName(), "ACTIVE");
				for (User user : users) {
					if (user != null) {
						UserInfo userInfo = userRepository
								.findByMongoUserId(user.getId());
						if (userInfo != null
								&& userInfo.getPhonenumber() != null) {
							mobileNumberSet.add(String.valueOf(userInfo
									.getPhonenumber()));
						} else {
							logger.error(
									"User not exist in mysql database for roleId : {} or phonenumber field is null",
									role.getId());
						}
					} else {
						logger.error(
								"User not exist in mongo database for roleId : {}",
								role.getId());
					}
				}
			} else {
				logger.error("Role not exist in database for given rolename");
			}

		}
		int count = 0;
		int totCount = 0;
		if (mobileNumberSet != null && mobileNumberSet.size() > 0) {
			for (String mobileNum : mobileNumberSet) {
				if (count == 0) {
					mobileNumbers = mobileNum;
				} else {
					mobileNumbers = mobileNumbers + "," + mobileNum;
				}
				count++;
				totCount++;
				if (count == 200) {
					userCommunicationService.sendPromoSms(promoInfo,
							mobileNumbers);
					Thread.sleep(500);
					count = 0;
					mobileNumbers = null;
				}
				if (count != 200 && totCount == mobileNumberSet.size()) {
					userCommunicationService.sendPromoSms(promoInfo,
							mobileNumbers);
					Thread.sleep(500);
					count = 0;
					mobileNumbers = null;
				}
			}
		}
	}

	@Override
	public void deleteQuoteRequest(String userId, String quoteId,
			String productId) {
		QuoteRequest quoteRequest = null;
		if (StringUtils.isNotBlank(quoteId)
				&& StringUtils.isNotBlank(productId)) {
			quoteRequest = quoteRequestDAO.findOne(quoteId);
			if (quoteRequest != null) {
				QuoteRequestProduct quoteRequestProduct = quoteRequestProductDAO
						.findOne(productId);
				if (quoteRequestProduct != null) {
					Set<SellerQuoteProductPricing> quoteProductPricings = quoteRequestProduct
							.getQuoteProductPricings();
					if (quoteProductPricings != null
							&& quoteProductPricings.size() != 0) {
						for (SellerQuoteProductPricing sellerQuoteProductPricing : quoteProductPricings) {
							if (sellerQuoteProductPricing != null) {
								sellerQuoteProductPricingDAO
										.delete(sellerQuoteProductPricing);
							}
						}
					}
					quoteRequestProductDAO.delete(quoteRequestProduct);

					List<QuoteRequestProduct> products = quoteRequest
							.getProducts();

					for (QuoteRequestProduct product : products) {
						if (product != null
								&& product.getId().equalsIgnoreCase(productId)) {
							quoteRequest.getProducts().remove(product);
							quoteRequestDAO.save(quoteRequest);
							break;
						}
					}
				}
				if (quoteRequest.getProducts().size() == 0) {
					quoteRequestDAO.delete(quoteRequest);
				}
			}
		}
	}

	@Override
	public Set<String> getStoreNamesByRole(String roleName) {
		Set<String> storeNames = new HashSet<>();
		Set<User> users = userDAO.findByStatusAndRoles_NameIgnoreCase(
				UserStatus.ACTIVE, roleName);
		for (User user : users) {
			if (user.getContactName() != null) {
				storeNames.add(user.getContactName());
			}
		}
		return storeNames;
	}

	@Override
	public QuoteCountResponse getQuoteCount() {
		int created = quoteRequestDAO.countByStatus(QuoteStatus.CREATED);
		int confirmed = quoteRequestDAO.countByStatus(QuoteStatus.CONFIRMED);
		int onHold = quoteRequestDAO.countByStatus(QuoteStatus.ON_HOLD);
		int rejected = quoteRequestDAO.countByStatus(QuoteStatus.REJECTED);

		QuoteCountResponse quoteCount = new QuoteCountResponse();
		quoteCount.setCreated(created);
		quoteCount.setConfirmed(confirmed);
		quoteCount.setOnHold(onHold);
		quoteCount.setRejected(rejected);
		return quoteCount;
	}

	@Override
	public QuoteRequest deleteQuoteByQuoteId(String quoteId) throws Exception {
		QuoteRequest quoteRequest = null;
		if (StringUtils.isNotBlank(quoteId)) {
			quoteRequest = quoteRequestDAO.findOne(quoteId);
			if (quoteRequest != null) {
				List<QuoteRequestProduct> products = quoteRequest.getProducts();
				for (QuoteRequestProduct prod : products) {
					Set<SellerQuoteProductPricing> quoteProductPricings = prod
							.getQuoteProductPricings();
					for (SellerQuoteProductPricing prodPricing : quoteProductPricings) {
						sellerQuoteProductPricingDAO.delete(prodPricing);
					}
					quoteRequestProductDAO.delete(prod);
				}
				quoteRequestDAO.delete(quoteRequest);
			} else {
				throw new Exception("Quote is null for ID: " + quoteId);
			}
		}
		return quoteRequest;
	}

	@Override
	public List<UserSummaryView> getSellersByBrandAndSubcategory(
			String searchValue) {
		Set<Store> stores = new HashSet<>();
		Set<User> users = new HashSet<>();
		List<UserSummaryView> manageDealerViewList = new ArrayList<UserSummaryView>();
		if (StringUtils.isNotBlank(searchValue)) {
			ProductBrand brand = brandDAO.findByNameAllIgnoreCase(searchValue);
			if (brand != null) {
				stores = storeDAO.findByBrandsIn(brand);
			}
			if (stores.size() == 0) {
				Set<SubCategory> subCategory = subCategoryDAO
						.findByNameAllIgnoreCase(searchValue);
				if (subCategory != null && subCategory.size() != 0) {
					stores = storeDAO.findBySubcategoriesIn(subCategory);
				}
			}
		}

		if (stores.size() != 0) {
			for (Store store : stores) {
				if (store != null) {
					users.add(store.getUser());
				}
			}
		} else {
			users.addAll(userDAO.findByRoles_NameAllIgnoreCase("DEALER"));
		}

		for (User user : users) {

			UserInfo userInfo = userRepository.findByMongoUserId(user.getId());
			if (userInfo != null) {
				UserSummaryView manageDealerView = new UserSummaryView();
				// User id
				manageDealerView.setUserId(user.getId());
				// DealerName
				if (user.getContactName() != null) {
					manageDealerView.setName(user.getContactName());
				}
				// createdDate
				if (user.getCreatedDate() != null) {
					manageDealerView.setCreatedDate(user.getCreatedDate());
				}
				// Email
				if (userInfo.getUsername() != null) {
					manageDealerView.setEmailId(userInfo.getUsername());
				}
				// PhoneNumber
				if (userInfo.getPhonenumber() != null) {
					manageDealerView.setPhoneNumber(userInfo.getPhonenumber()
							.toString());
				}

				// verified
				if (userInfo.isVerified() == false
						&& userInfo.getUsername() == null) {
					manageDealerView.setVerified(true);
				} else {
					manageDealerView.setVerified(false);
				}

				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					// package
					if (role.getUserPackage() != null) {
						manageDealerView.setPackageName(role.getUserPackage()
								.getName());
					}
				}

				// status
				if (user.getStatus() != null) {
					manageDealerView.setStatus(user.getStatus());
				}
				// TIN number
				if (user.getVatNumber() != null) {
					manageDealerView.setVatNumber(user.getVatNumber());
				}
				// Pan number
				if (user.getPanNumber() != null) {
					manageDealerView.setPanNumber(user.getPanNumber());
				}

				// total shops
				manageDealerView.setTotalShops(user.getAddresses().size());

				// StoreList
				List<String> shopNameList = new ArrayList<>();

				shopNameList.add(user.getFirstName());

				manageDealerView.setShopname(shopNameList);

				manageDealerViewList.add(manageDealerView);
			}

		}
		return manageDealerViewList;
	}

	@Override
	public User deleteUserProfileById(String userId) throws Exception {
		User user = null;
		if (StringUtils.isNotBlank(userId)) {
			user = userDAO.findById(userId);
			if (user != null) {
				Image image = user.getProfilePicture();
				if (image != null) {
					if (image.getFileType().name().equalsIgnoreCase("image")) {
						// Deleting image from the Azure
						filestorage.deleteFile(AzureFileStorage.IMG_CONTAINER,
								image.getUrl());
						user.setProfilePicture(null);
					}
					userDAO.save(user);
				}
			}
		}
		return user;
	}

	@Override
	public QuoteCountResponse getCustomerQuotesCountByStatus(String userId) {
		QuoteCountResponse quoteCountResponse = null;
		if (StringUtils.isNotBlank(userId)) {
			User customer = userDAO.findById(userId);
			if (customer != null) {
				quoteCountResponse = new QuoteCountResponse();
				// QuoteCreated
				quoteCountResponse
						.setCreated(quoteRequestDAO.countByCustomerAndStatus(
								customer, QuoteStatus.CREATED));
				// QuoteConfirmed
				quoteCountResponse.setConfirmed(quoteRequestDAO
						.countByCustomerAndStatus(customer,
								QuoteStatus.CONFIRMED));
				// QuoteOnHold
				quoteCountResponse
						.setOnHold(quoteRequestDAO.countByCustomerAndStatus(
								customer, QuoteStatus.ON_HOLD));
				// QuoteRejected
				quoteCountResponse.setRejected(quoteRequestDAO
						.countByCustomerAndStatus(customer,
								QuoteStatus.REJECTED));
			}
		}
		return quoteCountResponse;
	}
}