/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.catalog.service.ProductCatalogService;
import com.sarvah.mbg.catalog.service.model.QuoteCreateRequestParam;
import com.sarvah.mbg.commons.communication.UserCommException;
import com.sarvah.mbg.domain.common.asset.File;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.userprofile.QuoteRequest;
import com.sarvah.mbg.domain.mongo.userprofile.RecentlyViewed;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.UserProjectProfile;
import com.sarvah.mbg.domain.mongo.userprofile.UserRelatedDocuments;
import com.sarvah.mbg.domain.mongo.userprofile.UserStatus;
import com.sarvah.mbg.domain.mongo.userprofile.UserType;
import com.sarvah.mbg.domain.mongo.userprofile.WishList;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.domain.mongo.userprofile.role.UserPackage;
import com.sarvah.mbg.domain.sms.ItemInfoForCommunication;
import com.sarvah.mbg.domain.user.auth.VerificationToken;
import com.sarvah.mbg.privilege.service.PrivilegeService;
import com.sarvah.mbg.product.dao.mongo.ProductNotOnBoardedNameStoreDAO;
import com.sarvah.mbg.rest.authorization.MBGSecurityContext;
import com.sarvah.mbg.rest.catalog.model.ImageOrFileDeleteRequestParam;
import com.sarvah.mbg.rest.catalog.response.RecentlyViewedResponse;
import com.sarvah.mbg.rest.exception.MBGAppException;
import com.sarvah.mbg.rest.model.MessageResponse;
import com.sarvah.mbg.rest.model.Page;
import com.sarvah.mbg.rest.userprofile.model.AddressCreateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.AddressSearchRequestParam;
import com.sarvah.mbg.rest.userprofile.model.AddressUpdateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.BalanceUpdateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.ProfileProjectCreateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.ProfileProjectUpdateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.PromotionInfoRequestParam;
import com.sarvah.mbg.rest.userprofile.model.RegisterUserRequestParam;
import com.sarvah.mbg.rest.userprofile.model.RoleCreateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.RoleUpdateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.StatusUpdateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.UserCreateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.UserLoginRequestParam;
import com.sarvah.mbg.rest.userprofile.model.UserPackageCreateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.UserPackageUpdateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.UserPhoneRequestParam;
import com.sarvah.mbg.rest.userprofile.model.UserRelatedDocumentsCreateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.UserResetPasswordRequestParam;
import com.sarvah.mbg.rest.userprofile.model.UserRoleUpdateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.UserSearchRequestParam;
import com.sarvah.mbg.rest.userprofile.model.UserSocialLoginRequestParam;
import com.sarvah.mbg.rest.userprofile.model.UserUpdateRequestParam;
import com.sarvah.mbg.rest.userprofile.response.LoginUserResponse;
import com.sarvah.mbg.rest.userprofile.response.ManageUserResponse;
import com.sarvah.mbg.rest.userprofile.response.RegisterUserResponse;
import com.sarvah.mbg.rest.userprofile.response.UsersResponse;
import com.sarvah.mbg.userprofile.auth.AuthUserService;
import com.sarvah.mbg.userprofile.auth.exception.AuthenticationException;
import com.sarvah.mbg.userprofile.auth.exception.TokenAlreadyVerifiedException;
import com.sarvah.mbg.userprofile.auth.exception.TokenExpiredException;
import com.sarvah.mbg.userprofile.auth.exception.TokenNotFoundException;
import com.sarvah.mbg.userprofile.auth.model.ApiUser;
import com.sarvah.mbg.userprofile.exception.UserInputValidationException;
import com.sarvah.mbg.userprofile.response.BuyableUsersCount;
import com.sarvah.mbg.userprofile.response.ContactInfoResponse;
import com.sarvah.mbg.userprofile.response.ManageDocs;
import com.sarvah.mbg.userprofile.response.ManageRolesResponse;
import com.sarvah.mbg.userprofile.response.ManageRolesView;
import com.sarvah.mbg.userprofile.response.ProfileCompletenessResponse;
import com.sarvah.mbg.userprofile.response.QuoteCountResponse;
import com.sarvah.mbg.userprofile.response.RecentlyViewedSummary;
import com.sarvah.mbg.userprofile.response.QuoteProductRequestParam;
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
@Component
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	private static final Logger logger = LoggerFactory
			.getLogger(UserResource.class);

	@Autowired
	private UserService userService;

	@Autowired
	private ProductCatalogService productService;

	@Autowired
	private PrivilegeService privilegeService;

	@Autowired
	private AuthUserService authUserService;

	@Autowired
	private ProductNotOnBoardedNameStoreDAO productNotOnBoardedNameStoreDAO;

	/**
	 * Method for search user operation, Search is performed based on
	 * username,firstname,lastname,email,phonenumber,usertype,rolename and
	 * fullName. validation and pagination is implemented.
	 * 
	 * @param userSearchRequest
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	public Response searchUser(
			@BeanParam UserSearchRequestParam userSearchRequest,
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("20") @QueryParam("size") int size,
			@DefaultValue("createdDate,desc") @QueryParam("sort") String sortVal,
			@QueryParam("view") String view,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		UsersResponse userResponse = new UsersResponse();
		UserSummaryViewResponse userSummaryViewResponse = new UserSummaryViewResponse();
		ManageUserResponse manageUserResponse = new ManageUserResponse();
		try {
			String searchValue = userSearchRequest.getSearchValue();
			String userName = userSearchRequest.getUserName();
			String firstName = userSearchRequest.getFirstName();
			String lastName = userSearchRequest.getLastName();
			String email = userSearchRequest.getEmail();
			String phoneNumber = userSearchRequest.getPhoneNumber();
			String roleName = userSearchRequest.getRoleName();
			String status = userSearchRequest.getStatus();
			String fullName = userSearchRequest.getFullName();
			String pack = userSearchRequest.getPack();
			String lat = userSearchRequest.getLat();
			String lon = userSearchRequest.getLon();
			Point point = null;
			String skillSetName = userSearchRequest.getSkillSetName();
			String operatingCityName = userSearchRequest.getOperatingCityName();
			String subCategory = userSearchRequest.getSubCategory();
			String brand = userSearchRequest.getBrand();
			String productIds = userSearchRequest.getProductIds();
			String area = userSearchRequest.getArea();

			if (StringUtils.isNotBlank(lat) && StringUtils.isNotBlank(lon)) {
				point = new Point(Double.parseDouble(lat),
						Double.parseDouble(lon));
			}
			// default sorting order is descending order.
			// if user pass sort value asc then sorting order is asscending.
			// if user pass sort value desc then sorting order is descending
			// order.
			List<Order> orders = new ArrayList<Order>();
			String sortArray[] = sortVal.split(",");
			if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
				orders.add(new Order(Direction.DESC, sortArray[0]));
			else
				orders.add(new Order(Direction.ASC, sortArray[0]));

			Sort sort = new Sort(orders);
			// super admin manage Providers.
			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageProviders")) {
				logger.info("Requesting for AdminManageProviders");
				userSummaryViewResponse = userService
						.getSuperAdminManageProviders(pack, status,
								searchValue, area, page, size, sort);
				Page retPage = new Page();
				retPage.setTotalPages(userSummaryViewResponse.getTotalPages());
				retPage.setTotalElements(userSummaryViewResponse
						.getTotalElements());
				retPage.setNumber(userSummaryViewResponse.getNumber());
				retPage.setSize(userSummaryViewResponse.getSize());
				manageUserResponse.setPage(retPage);
				manageUserResponse.setUsers(userSummaryViewResponse
						.getUserSummaryView());
				logger.info("Returning AdminManageProviders response! Success");
				return Response.ok(manageUserResponse).build();
			}
			// super admin manage Dealers.
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageDealers")) {
				logger.info("Requesting for AdminManageDealers");
				userSummaryViewResponse = userService
						.getSuperAdminManageDealers(pack, status, searchValue,
								subCategory, area, page, size, sort);
				Page retPage = new Page();
				retPage.setTotalPages(userSummaryViewResponse.getTotalPages());
				retPage.setTotalElements(userSummaryViewResponse
						.getTotalElements());
				retPage.setNumber(userSummaryViewResponse.getNumber());
				retPage.setSize(userSummaryViewResponse.getSize());
				manageUserResponse.setPage(retPage);
				manageUserResponse.setUsers(userSummaryViewResponse
						.getUserSummaryView());
				logger.info("Returning AdminManageDealers response! Success");
				return Response.ok(manageUserResponse).build();
			}
			// super admin manage BusinessAssociate.
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageBusinessAssociate")) {
				logger.info("Requesting for AdminManageBusinessAssociate");
				userSummaryViewResponse = userService
						.getSuperAdminManageBusinessAssociate(searchValue,
								status, area, page, size, sort);
				Page retPage = new Page();
				retPage.setTotalPages(userSummaryViewResponse.getTotalPages());
				retPage.setTotalElements(userSummaryViewResponse
						.getTotalElements());
				retPage.setNumber(userSummaryViewResponse.getNumber());
				retPage.setSize(userSummaryViewResponse.getSize());
				manageUserResponse.setPage(retPage);
				manageUserResponse.setUsers(userSummaryViewResponse
						.getUserSummaryView());
				logger.info("Returning AdminManageBusinessAssociate response! Success");
				return Response.ok(manageUserResponse).build();
			}
			// super admin manage Architects.
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageArchitects")) {
				logger.info("Requesting for AdminManageArchitects");
				userSummaryViewResponse = userService
						.getSuperAdminManageArchitects(pack, status,
								searchValue, area, page, size, sort);
				Page retPage = new Page();
				retPage.setTotalPages(userSummaryViewResponse.getTotalPages());
				retPage.setTotalElements(userSummaryViewResponse
						.getTotalElements());
				retPage.setNumber(userSummaryViewResponse.getNumber());
				retPage.setSize(userSummaryViewResponse.getSize());
				manageUserResponse.setPage(retPage);
				manageUserResponse.setUsers(userSummaryViewResponse
						.getUserSummaryView());
				logger.info("Returning AdminManageArchitects response! Success");
				return Response.ok(manageUserResponse).build();
			}
			// super admin manage InteriorDesigners.
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageInteriorDesigners")) {
				logger.info("Requesting for AdminManageInteriorDesigners");
				userSummaryViewResponse = userService
						.getSuperAdminManageInteriorDesigners(pack, status,
								searchValue, area, page, size, sort);
				Page retPage = new Page();
				retPage.setTotalPages(userSummaryViewResponse.getTotalPages());
				retPage.setTotalElements(userSummaryViewResponse
						.getTotalElements());
				retPage.setNumber(userSummaryViewResponse.getNumber());
				retPage.setSize(userSummaryViewResponse.getSize());
				manageUserResponse.setPage(retPage);
				manageUserResponse.setUsers(userSummaryViewResponse
						.getUserSummaryView());
				logger.info("Returning AdminManageInteriorDesigners response! Success");
				return Response.ok(manageUserResponse).build();

			}
			// super admin manage Endusers.
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageEndusers")) {
				logger.info("Requesting for AdminManageEndusers");
				userSummaryViewResponse = userService
						.getSuperAdminManageEndusers(searchValue, status, area,
								page, size, sort);
				Page retPage = new Page();
				retPage.setTotalPages(userSummaryViewResponse.getTotalPages());
				retPage.setTotalElements(userSummaryViewResponse
						.getTotalElements());
				retPage.setNumber(userSummaryViewResponse.getNumber());
				retPage.setSize(userSummaryViewResponse.getSize());
				manageUserResponse.setPage(retPage);
				manageUserResponse.setUsers(userSummaryViewResponse
						.getUserSummaryView());
				logger.info("Returning AdminManageEndusers response! Success");
				return Response.ok(manageUserResponse).build();
			}
			// super admin manage ServiceProvider.
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageServiceProvider")) {
				logger.info("Requesting for AdminManageServiceProvider");
				userSummaryViewResponse = userService
						.getSuperAdminManageServivceProviders(roleName, status,
								searchValue, area, page, size, sort);
				Page retPage = new Page();
				retPage.setTotalPages(userSummaryViewResponse.getTotalPages());
				retPage.setTotalElements(userSummaryViewResponse
						.getTotalElements());
				retPage.setNumber(userSummaryViewResponse.getNumber());
				retPage.setSize(userSummaryViewResponse.getSize());
				manageUserResponse.setPage(retPage);
				manageUserResponse.setUsers(userSummaryViewResponse
						.getUserSummaryView());
				logger.info("Returning AdminManageServiceProvider response! Success");
				return Response.ok(manageUserResponse).build();
			}
			// super admin manage ManageRoles.
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("ManageRoles")) {
				logger.info("Requesting for ManageRoles");
				ManageRolesResponse manageRolesView = userService.manageRoles();
				logger.info("Returning ManageRoles response! Success");
				return Response.ok(manageRolesView).build();
			}
			// checking whether username already exist or not
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("UsernameVerification")) {
				logger.info(
						"Requesting for UsernameVerification. username : {}",
						userName);
				boolean username = userService.serachUserName(userName);
				logger.info("Returning UsernameVerification response! Success");
				return Response.ok(username).build();
			}
			// Super admin manage users
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageUsers")) {

				logger.info("Requesting for AdminManageUsers");

				userSummaryViewResponse = userService.getSuperAdminManageUsers(
						status, searchValue, page, size, sort);

				Page retPage = new Page();

				retPage.setTotalPages(userSummaryViewResponse.getTotalPages());
				retPage.setTotalElements(userSummaryViewResponse
						.getTotalElements());
				retPage.setNumber(userSummaryViewResponse.getNumber());
				retPage.setSize(userSummaryViewResponse.getSize());

				manageUserResponse.setPage(retPage);

				manageUserResponse.setUsers(userSummaryViewResponse
						.getUserSummaryView());

				logger.info("Returing AdminManageUsers response");
				return Response.ok(manageUserResponse).build();
			} // super admin manage Endusers.
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageTeleAssociate")) {
				logger.info("Requesting for AdminManageTeleAssociate");
				userSummaryViewResponse = userService
						.getSuperAdminManageTeleAssociate(searchValue, status,
								page, size, sort);
				Page retPage = new Page();
				retPage.setTotalPages(userSummaryViewResponse.getTotalPages());
				retPage.setTotalElements(userSummaryViewResponse
						.getTotalElements());
				retPage.setNumber(userSummaryViewResponse.getNumber());
				retPage.setSize(userSummaryViewResponse.getSize());
				manageUserResponse.setPage(retPage);
				manageUserResponse.setUsers(userSummaryViewResponse
						.getUserSummaryView());
				logger.info("Returning AdminManageTeleAssociate response! Success");
				return Response.ok(manageUserResponse).build();
			} // super admin manage Endusers.
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageTechAssociate")) {
				logger.info("Requesting for AdminManageTechAssociate");
				userSummaryViewResponse = userService
						.getSuperAdminManageTechAssociate(searchValue, status,
								page, size, sort);
				Page retPage = new Page();
				retPage.setTotalPages(userSummaryViewResponse.getTotalPages());
				retPage.setTotalElements(userSummaryViewResponse
						.getTotalElements());
				retPage.setNumber(userSummaryViewResponse.getNumber());
				retPage.setSize(userSummaryViewResponse.getSize());
				manageUserResponse.setPage(retPage);
				manageUserResponse.setUsers(userSummaryViewResponse
						.getUserSummaryView());
				logger.info("Returning AdminManageTechAssociate response! Success");
				return Response.ok(manageUserResponse).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageFieldAssociate")) {

				logger.info("Requesting for AdminManageFieldAssociate");
				userSummaryViewResponse = userService
						.getSuperAdminManageFieldAssociate(searchValue, status,
								page, size, sort);
				Page retPage = new Page();
				retPage.setTotalPages(userSummaryViewResponse.getTotalPages());
				retPage.setTotalElements(userSummaryViewResponse
						.getTotalElements());
				retPage.setNumber(userSummaryViewResponse.getNumber());
				retPage.setSize(userSummaryViewResponse.getSize());
				manageUserResponse.setPage(retPage);
				manageUserResponse.setUsers(userSummaryViewResponse
						.getUserSummaryView());
				logger.info("Returning AdminManageFieldAssociate response! Success");
				return Response.ok(manageUserResponse).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("GetSellersForBestQuote")) {
				userSummaryViewResponse = userService
						.getSellersBySubcategoryAndBrand(subCategory, brand,
								productIds, page, size, sort);

				Page retPage = new Page();
				retPage.setTotalPages(userSummaryViewResponse.getTotalPages());
				retPage.setTotalElements(userSummaryViewResponse
						.getTotalElements());
				retPage.setNumber(userSummaryViewResponse.getNumber());
				retPage.setSize(userSummaryViewResponse.getSize());
				manageUserResponse.setPage(retPage);
				manageUserResponse.setUsers(userSummaryViewResponse
						.getUserSummaryView());

				return Response.ok(manageUserResponse).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("GetStoreNamesByRole")) {

				Set<String> storeNames = userService
						.getStoreNamesByRole(roleName);

				return Response.ok(storeNames).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("GetSellersByBrandAndSubcategory")) {
				List<UserSummaryView> users = userService
						.getSellersByBrandAndSubcategory(searchValue);
				manageUserResponse.setUsers(users);
				return Response.ok(manageUserResponse).build();
			}
			// search user
			else {
				logger.info("Requesting for SearchUser");
				List<User> users = userService.searchUser(userName, firstName,
						lastName, email, phoneNumber, roleName, status,
						fullName, skillSetName, operatingCityName, area, point,
						page, size, sort);
				Page retPage = new Page();
				int totalElement = 0;

				if (StringUtils.isNotBlank(roleName)
						&& StringUtils.isNotBlank(status)
						&& StringUtils.isNotBlank(operatingCityName)
						&& StringUtils.isBlank(skillSetName)) {
					long mbgUserCount = userService
							.getUserCountByRoleAndStatusAndOperatingCity(
									roleName, status, operatingCityName);
					totalElement = (int) mbgUserCount;
				} else if (StringUtils.isNotBlank(roleName)
						&& StringUtils.isNotBlank(status)
						&& StringUtils.isNotBlank(skillSetName)
						&& StringUtils.isBlank(operatingCityName)) {
					long mbgUserCount = userService
							.getUserCountByRoleAndStatusAndSkillSet(roleName,
									status, skillSetName);
					totalElement = (int) mbgUserCount;
				} else if (StringUtils.isNotBlank(roleName)
						&& StringUtils.isNotBlank(status)
						&& StringUtils.isBlank(skillSetName)
						&& StringUtils.isBlank(operatingCityName)) {
					long mbgUserCount = userService
							.getUserCountByRoleAndStatus(roleName, status);
					totalElement = (int) mbgUserCount;
				} else if (StringUtils.isNotBlank(roleName)) {
					long mbgUserCount = userService
							.getUserCountByRole(roleName);
					totalElement = (int) mbgUserCount;
				} else if (StringUtils.isNotBlank(area)) {
					long mbgUserCount = userService.getUserCountByArea(area);
					totalElement = (int) mbgUserCount;
				} else {
					long mbgUserCount = userService.getMBGUserCount();
					totalElement = (int) mbgUserCount;
				}
				retPage.setNumber(page);
				retPage.setSize(size);
				retPage.setTotalElements(totalElement);
				if (totalElement % size == 0)
					retPage.setTotalPages(totalElement / size);
				else
					retPage.setTotalPages((totalElement / size) + 1);
				userResponse.setPage(retPage);
				userResponse.setUsers(users);
				logger.info("Returning SearchUser response! Success");
			}
		} catch (Exception e) {
			throw new MBGAppException("Error occured during user search", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning AdminManageProviders response! Success");
		return Response.ok(userResponse).build();
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

	@GET
	@PermitAll
	@Path("/count")
	public Response countUser(@QueryParam("username") String userName,
			@QueryParam("fname") String firstName,
			@QueryParam("lname") String lastName,
			@QueryParam("email") String email,
			@QueryParam("pnum") String phoneNumber,
			@QueryParam("fullname") String fullName,
			@QueryParam("roleName") String roleName,
			@QueryParam("status") String status,
			@QueryParam("view") String view,
			@Context SecurityContext securityContext) throws MBGAppException {

		// get the apiuser consuming this api and pass around for decision
		// making
		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}
		Long count;
		try {
			logger.info("Requesting for Active and InActive user count");
			if ((StringUtils.isNotBlank(view))
					&& view.equalsIgnoreCase("UsersStatusCount")) {
				UsersStatusCount usersStatusCount = userService
						.getUserStatusCount();
				logger.info("Returning Active and InActive user count! Success");
				return Response.ok(usersStatusCount).build();
			} else if ((StringUtils.isNotBlank(view))
					&& view.equalsIgnoreCase("BuyableUsersCount")) {
				logger.info("Requesting to get the count of all buyable users!!");
				BuyableUsersCount buyableUsersCount = userService
						.getBuyableUsersCount();
				logger.info("Returning the count of all buyable users!!");
				return Response.ok(buyableUsersCount).build();
			} else {
				logger.info("Requesting for user count");
				count = userService.countUser(userName, firstName, lastName,
						email, phoneNumber, fullName, roleName, status);
				logger.info("Returning user count! Success");
				return Response.ok(count).build();
			}
		} catch (Exception e) {
			throw new MBGAppException("Error occured trying to get user count",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
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
	@POST
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response createUser(UserCreateRequestParam registerUserRequestParam,
			@Context HttpServletRequest request,
			@Context SecurityContext securityContext) throws MBGAppException {
		logger.info("Requesting for createUser");

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		if (registerUserRequestParam != null) {
			logger.info("Register user request from ip - {}",
					getRemoteAddress(request));
			try {
				ApiUser apiUser1 = authUserService
						.createNewUser(registerUserRequestParam);
				// user created successfully
				RegisterUserResponse registerUserResponse = new RegisterUserResponse(
						apiUser1, Status.CREATED, 2001,
						"User registered successfully and verification email sent to "
								+ apiUser1.getUsername());
				logger.info("Returning user created response! Success");
				return Response.ok(registerUserResponse).build();
			} catch (Exception e) {
				throw new MBGAppException("Error registering new user", e,
						Status.BAD_REQUEST.getStatusCode(), 3006);
			}
		} else {
			throw new MBGAppException(
					"Error registering new user, request params not valid",
					Status.BAD_REQUEST.getStatusCode(), 3006);
		}
	}

	/**
	 * method to search the user here user is search based on userid.
	 * 
	 * @param userid
	 * @return
	 * @throws MBGAppException
	 */

	@GET
	@Path("/{uid}")
	public Response searchUserById(@PathParam("uid") String userId,
			@QueryParam("view") String view,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}
		try {
			// method for admin manage dealer view details
			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminDealerViewDetails")) {
				logger.info(
						"Requesting for AdminDealerViewDetails. DealerId : {}",
						userId);
				UserSummaryViewDetails user = userService
						.getManageDealerDetailsView(userId);
				logger.info("Returning AdminDealerViewDetails response! Success");
				return Response.ok(user).build();
			}
			// method for admin manage provider view details
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminProviderViewDetails")) {
				logger.info(
						"Requesting for AdminProviderViewDetails. ProviderId : {}",
						userId);
				UserSummaryViewDetails user = userService
						.getManageProviderDetailsView(userId);
				logger.info("Returning AdminProviderViewDetails response! Success");
				return Response.ok(user).build();
			}
			// method for super admin manage end user view details.
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminEndUserViewDetails")) {
				logger.info(
						"Requesting for AdminEndUserViewDetails. EnduserId : {}",
						userId);
				UserSummaryViewDetails user = userService
						.getManageEndUserDetailsView(userId);
				logger.info("Returning AdminEndUserViewDetails response! Success");
				return Response.ok(user).build();
			}
			// method for super admin manage TeleAssociate view details.
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminTeleAssociateViewDetails")) {
				logger.info(
						"Requesting for AdminTeleAssociateViewDetails. Id : {}",
						userId);
				UserSummaryViewDetails user = userService
						.getManageTeleAssociateDetailsView(userId);
				logger.info("Returning AdminTeleAssociateViewDetails response! Success");
				return Response.ok(user).build();
			}
			// method for super admin manage TechAssociate view details.
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminTechAssociateViewDetails")) {
				logger.info(
						"Requesting for AdminTechAssociateViewDetails. Id : {}",
						userId);
				UserSummaryViewDetails user = userService
						.getManageTechAssociateDetailsView(userId);
				logger.info("Returning AdminTechAssociateViewDetails response! Success");
				return Response.ok(user).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminFieldAssociateViewDetails")) {

				logger.info(
						"Requesting for AdminFieldAssociateViewDetails. Id : {}",
						userId);
				UserSummaryViewDetails user = userService
						.getManageFieldAssociateDetailsView(userId);
				logger.info("Returning AdminFieldAssociateViewDetails response! Success");
				return Response.ok(user).build();

			}
			// Method to get Admin Manage Business Associate details.
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminBusinessAssociateDetails")) {
				logger.info(
						"Requesting for AdminBusinessAssociateDetails. BusinessAssociateId : {}",
						userId);
				UserSummaryViewDetails user = userService
						.getManageBusinessAssociateDetailsView(userId);
				logger.info("Returning AdminBusinessAssociateDetails response! Success");
				return Response.ok(user).build();
			}
			// method for admin manage Architect view details
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminArchitectViewDetails")) {
				logger.info(
						"Requesting for AdminArchitectViewDetails. ArchitectId : {}",
						userId);
				UserSummaryViewDetails user = userService
						.getManageArchitectDetailsView(userId);
				logger.info("Returning AdminArchitectViewDetails response! Success");
				return Response.ok(user).build();
			}
			// method for admin manage InteriorDesigner view details
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminInteriorDesignerViewDetails")) {
				logger.info(
						"Requesting for AdminInteriorDesignerViewDetails. InteriorDesignerId : {}",
						userId);
				UserSummaryViewDetails user = userService
						.getManageInteriorDesignerDetailsView(userId);
				logger.info("Returning AdminInteriorDesignerViewDetails response! Success");
				return Response.ok(user).build();
			}
			// method for admin manage ServiceProvider view details
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminServiceProviderViewDetails")) {
				logger.info(
						"Requesting for AdminServiceProviderViewDetails. ServiceProviderId : {}",
						userId);
				UserSummaryViewDetails user = userService
						.getManageServiceProviderDetailsView(userId);
				logger.info("Returning AdminServiceProviderViewDetails response! Success");
				return Response.ok(user).build();
			}
			// method for admin manage users view details
			else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AdminManageUsersViewDetails")) {
				logger.info(
						"Requesting for AdminManageUsersViewDetails. userId : {}",
						userId);
				UserSummaryViewDetails user = userService
						.getManageUserDetailsView(userId);
				logger.info("Returning AdminManageUsersViewDetails response! Success");
				return Response.ok(user).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("ProfileCompleteness")) {
				ProfileCompletenessResponse profileCompletenessResponse = userService
						.sendProfileCompletenessScore(userId);
				return Response.ok(profileCompletenessResponse).build();
			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("GetCustomerQuotesCountByStatus")) {
				QuoteCountResponse quoteCountResponse = userService
						.getCustomerQuotesCountByStatus(userId);
				return Response.ok(quoteCountResponse).build();
			}
			// getting user based on userId
			else {
				logger.info("Requesting for Search user based on userId : {}",
						userId);
				SingleUserResponse user = userService.searchUserById(userId);
				logger.info("Returning Search user based on userId response! Success");
				return Response.ok(user).build();
			}
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured during finding user based on userid", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

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
	@PUT
	@Path("/{uid}")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response updateUser(@PathParam("uid") String userId,
			UserUpdateRequestParam userUpdateRequestParam,
			@Context SecurityContext securityContext) throws Exception {
		logger.info("Requesting for update user based on userId : {}", userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}
		User user1;
		try {
			String userName = userUpdateRequestParam.getUserName();
			String mobileNumber = userUpdateRequestParam.getMobileNumber();
			String firstName = userUpdateRequestParam.getFirstName();
			String lastName = userUpdateRequestParam.getLastName();
			String sex = userUpdateRequestParam.getSex();
			String bankName = userUpdateRequestParam.getBankName();
			String bankAccountNumber = userUpdateRequestParam
					.getBankAccountNumber();
			String panNumber = userUpdateRequestParam.getPanNumber();
			String vatNumber = userUpdateRequestParam.getVatNumber();
			String websiteUrl = userUpdateRequestParam.getWebsiteUrl();
			String status = userUpdateRequestParam.getStatus();
			String desc = userUpdateRequestParam.getDesc();
			String contactName = userUpdateRequestParam.getContactName();
			String ifscCode = userUpdateRequestParam.getIfscCode();
			Set<String> otherSkillSets = userUpdateRequestParam
					.getOtherSkillSets();
			Set<String> operatingCities = userUpdateRequestParam
					.getOperatingCities();

			boolean updateUserInfoByAdmin = userUpdateRequestParam
					.isUpdateUserInfoByAdmin();
			String title = userUpdateRequestParam.getTitle();

			user1 = userService.updateUser(apiUser, userId, userName,
					mobileNumber, firstName, lastName, sex, bankName,
					bankAccountNumber, panNumber, vatNumber, websiteUrl,
					status, desc, contactName, ifscCode, otherSkillSets,
					operatingCities, updateUserInfoByAdmin, title);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to update the user", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning update user based on userId response! Success");
		return Response.ok(user1).build();
	}

	/**
	 * method to delete the user here user is deleted based on userid.
	 * 
	 * @param userid
	 * @return
	 * @throws MBGAppException
	 */
	@DELETE
	@Path("/{uid}")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response deleteUserById(@PathParam("uid") String userId,
			@Context SecurityContext securityContext,
			@QueryParam("view") String view) throws MBGAppException {
		logger.info("Requesting for delete user based on userId : {}", userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		User user = null;
		try {
			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("DeleteProfileImage")) {
				user = userService.deleteUserProfileById(userId);
			} else {
				user = userService.deleteUser(userId);
			}
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to delete the user", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning user deleted information! success");
		return Response.ok(user).build();
	}

	/**
	 * Method to perform search operation search is performed based on
	 * userid,addressId,addressLine1,addressLine2,city,state
	 * country,zipcode,email.
	 * 
	 * @param userId
	 * @param addressSearchRequestParam
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/{uid}/addresses")
	public Response searchUserAddress(@PathParam("uid") String userId,
			@BeanParam AddressSearchRequestParam addressSearchRequestParam,
			@Context SecurityContext securityContext) throws Exception {

		logger.info("Requesting for get user addresses based on userId : {}",
				userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		String addrLine1 = addressSearchRequestParam.getAddrLine1();
		String addrLine2 = addressSearchRequestParam.getAddrLine2();
		String city = addressSearchRequestParam.getCity();
		String state = addressSearchRequestParam.getState();
		String country = addressSearchRequestParam.getCountry();
		String zipcode = addressSearchRequestParam.getZipcode();
		String email = addressSearchRequestParam.getEmail();
		Set<Address> address = null;
		try {
			address = userService.searchUserAddress(userId, addrLine1,
					addrLine2, city, state, country, zipcode, email);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to search user address", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning user addresses response! Success");
		return Response.ok(address).build();
	}

	/**
	 * Method to get particular address of the user
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 */
	@GET
	@Path("/{uid}/addresses/{aid}")
	public Response getUserAddress(@PathParam("uid") String userId,
			@PathParam("aid") String addrsId) {
		logger.info(
				"Requesting for get user address based on addressId : {} and userId : {}",
				addrsId, userId);
		Address address = null;

		try {
			address = userService.getUserAddressById(userId, addrsId);

		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to search user address", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning user address based on addressId! Success");
		return Response.ok(address).build();
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
	@PUT
	@Path("/{uid}/addresses/{aid}")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response updateUserAddress(@PathParam("uid") String userId,
			@PathParam("aid") String addrsId,
			AddressUpdateRequestParam addressUpdateRequestParam,
			@Context SecurityContext securityContext) throws Exception {
		logger.info(
				"Requesting for update user address based on addressId : {} and userId : {}",
				addrsId, userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		String name = addressUpdateRequestParam.getName();
		String contactPersonName = addressUpdateRequestParam
				.getContactPersonName();
		String addressLine1 = addressUpdateRequestParam.getAddrLine1();
		String addressLine2 = addressUpdateRequestParam.getAddrLine2();
		String city = addressUpdateRequestParam.getCity();
		String state = addressUpdateRequestParam.getState();
		String country = addressUpdateRequestParam.getCountry();
		String zipcode = addressUpdateRequestParam.getZipcode();
		String email = addressUpdateRequestParam.getEmail();
		String phoneNumber = addressUpdateRequestParam.getPhoneNumber();
		String mobileNumber = addressUpdateRequestParam.getMobileNumber();
		String officeType = addressUpdateRequestParam.getOfficeType();
		String mobileType = addressUpdateRequestParam.getMobileType();
		Set<Address> userAddresses = null;
		try {
			userAddresses = userService.updateUserAddress(userId, addrsId,
					name, contactPersonName, addressLine1, addressLine2, city,
					state, country, zipcode, email, phoneNumber, mobileNumber,
					officeType, mobileType);
		} catch (Exception e) {
			throw new MBGAppException("Error occured trying to add the user",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning update user address based on addressId response! Success");
		return Response.ok(userAddresses).build();
	}

	/**
	 * Method to delete user address user address is deleted based on userId and
	 * addressId
	 * 
	 * @param userId
	 * @param aid
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Path("/{uid}/addresses/{aid}")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response deleteUserAddress(@PathParam("uid") String userId,
			@PathParam("aid") String addrsId,
			@Context SecurityContext securityContext) throws Exception {
		logger.info(
				"Requesting for delete user address based on addressId : {} and userId : {}",
				addrsId, userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		String addressId = null;
		try {
			addressId = userService.deleteUserAddresses(userId, addrsId);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to delete user address", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning delete user address based on addressId response! Success");
		return Response.ok(addressId).build();
	}

	/**
	 * Method to add new Address
	 * 
	 * @param uId
	 * @param addressCreateRequestParam
	 * @return
	 * @throws UserInputValidationException
	 * @throws MBGAppException
	 */
	@POST
	@Path("/{uid}/addresses")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response addAddress(@PathParam("uid") String userId,
			AddressCreateRequestParam addressCreateRequestParam,
			@Context SecurityContext securityContext)
			throws UserInputValidationException, MBGAppException {
		logger.info("Requesting for add user address for userId : {}", userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}
		String name = addressCreateRequestParam.getName();
		String contactPersonName = addressCreateRequestParam
				.getContactPersonName();
		String addrLine1 = addressCreateRequestParam.getAddrLine1();
		String addrLine2 = addressCreateRequestParam.getAddrLine2();
		String city = addressCreateRequestParam.getCity();
		String state = addressCreateRequestParam.getState();
		String country = addressCreateRequestParam.getCountry();
		String zipcode = addressCreateRequestParam.getZipcode();
		String contactNumber = addressCreateRequestParam.getContactNumber();
		String branchTelNumber = addressCreateRequestParam.getBranchTelNumber();
		String email = addressCreateRequestParam.getEmail();
		Address address = null;
		try {
			address = userService.createAddress(userId, name,
					contactPersonName, addrLine1, addrLine2, city, state,
					country, zipcode, contactNumber, branchTelNumber, email);
		} catch (Exception e) {
			throw new MBGAppException("Can't Add new Address", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning add user address response! Success");
		return Response.ok(address).build();
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
	@PUT
	@Path("/{uid}/roles/{roleid}")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response updateUserRole(@PathParam("uid") String userId,
			@PathParam("roleid") String roleId,
			UserRoleUpdateRequestParam userRoleUpdateRequestParam,
			@Context SecurityContext securityContext) throws Exception {
		logger.info(
				"Requesting for update user role based on roleId : {} and userId : {}",
				roleId, userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		Role userRole = null;
		try {
			String roleName = userRoleUpdateRequestParam.getRoleName();
			String desc = userRoleUpdateRequestParam.getDesc();
			userRole = userService.updateUserRole(userId, roleId, roleName,
					desc);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to update user role", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning update user role response! Success");
		return Response.ok(userRole).build();
	}

	/**
	 * Method to search user role based on userid. here uid is mandatory
	 * 
	 * @param userId
	 * @param roleName
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/{uid}/roles")
	public Response searchUserRole(@PathParam("uid") String userId,
			@Context SecurityContext securityContext) throws Exception {
		logger.info("Requesting for fetching user role based on userId : {}",
				userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		Set<Role> roles = null;
		try {
			roles = userService.searchUserRole(userId);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to search user role", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning user role response! Success");
		return Response.ok(roles).build();
	}

	/**
	 * Method to update user phone here user phoneNumber details is updated
	 * based on phonetype and number.
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("/{uid}/phones")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response updateUserPhone(@PathParam("uid") String userId,
			UserPhoneRequestParam userPhoneRequestParam,
			@Context SecurityContext securityContext) throws Exception {
		logger.info("Requesting for update user phone based on userId : {}",
				userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		User user = null;
		try {
			String phoneType = userPhoneRequestParam.getPhoneType();
			String phoneNumber = userPhoneRequestParam.getPhoneNumber();
			String desc = userPhoneRequestParam.getDesc();
			String primary = userPhoneRequestParam.getPrimary();
			user = userService.updateUserPhone(userId, phoneType, phoneNumber,
					desc, primary);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to update user phone", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning update user phone response! Success");
		return Response.ok(user).build();
	}

	/**
	 * Method to search user based on user phone details here user is search
	 * based on phone details.here uid is mandatory
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/{uid}/phones")
	public Response searchUserPhone(@PathParam("uid") String userId,
			@Context SecurityContext securityContext) throws Exception {
		logger.info(
				"Requesting for fetch user phone info based on userId : {}",
				userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		Set<String> userPhoneSet;
		try {
			userPhoneSet = userService.searchUserPhone(userId);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to search user phones", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning user phone info response! Success");
		return Response.ok(userPhoneSet).build();
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
	 * @throws MBGAppException
	 */
	@PUT
	@Path("/{uid}/status")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response updateUserStatus(@PathParam("uid") String userId,
			StatusUpdateRequestParam statusUpdateRequestParam,
			@Context SecurityContext securityContext) throws MBGAppException {
		logger.info("Requesting for update user status based on userId : {}",
				userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		UserStatus userStatus = null;
		String status = statusUpdateRequestParam.getStatus();
		try {
			userStatus = userService.updateUserStatus(userId, status);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to update user status", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning user status update response! Success");
		return Response.ok(userStatus).build();
	}

	/**
	 * Method to search status of the user based on uid
	 * 
	 * This method returns status of the user based on the User ID
	 * 
	 * @param userid
	 * @return
	 * @throws MBGAppException
	 * @throws Exception
	 */
	@GET
	@Path("/{uid}/status")
	public Response getUserStatus(@PathParam("uid") String userId,
			@Context SecurityContext securityContext) throws MBGAppException {
		logger.info("Requesting for fetch user status based on userId : {}",
				userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		UserStatus userStatus;
		try {
			userStatus = userService.getUserStatus(userId);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to find user status", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning user status response! Success");
		return Response.ok(userStatus).build();
	}

	/**
	 * Method to search the package of the user
	 * 
	 * This method return the Package assigned to the particular user
	 * 
	 * @param userid
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{uid}/roles/packages")
	public Response searchUserPackage(@PathParam("uid") String userId,
			@Context SecurityContext securityContext) throws MBGAppException {
		logger.info("Requesting for fetch user package based on userId : {}",
				userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		UserPackage packages = null;
		try {
			packages = userService.getUserPackage(userId);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to get user package", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning fetch user package response! Success");
		return Response.ok(packages).build();
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
	 * @throws MBGAppException
	 */
	@PUT
	@Path("/{uid}/roles/packages")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response updatePackage(@PathParam("uid") String userId,
			UserPackageUpdateRequestParam packageRequestParam,
			@Context SecurityContext securityContext) throws MBGAppException {
		logger.info("Requesting for update user package based in userId : {}",
				userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		String pName = packageRequestParam.getName();
		UserPackage userPackage = null;
		try {
			userPackage = userService.updateUserPackage(userId, pName);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to update user package", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning update user package response! Success");
		return Response.ok(userPackage).build();
	}

	/**
	 * Method to get the count of the product which is related to user It get
	 * the product count based on uId
	 * 
	 * @param userid
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{uid}/products/count")
	public Response getUserProductsCount(@PathParam("uid") String userid,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		Long productCount = null;
		try {
			productCount = productService.getProductsCountForUser(userid);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying get product count for user", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(productCount).build();
	}

	// recently viewed
	/**
	 * Method to get recently viewed product It get recently viewed product
	 * Based on user Id
	 * 
	 * @param userId
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{uid}/recentlyviewed")
	public Response getRecentlyViewed(
			@PathParam("uid") String userId,
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("15") @QueryParam("size") int size,
			@DefaultValue("createdDate,desc") @QueryParam("sort") String sortVal,
			@Context SecurityContext securityContext) throws MBGAppException {
		logger.info("Requesting for RecentlyViewed based on userId : {}",
				userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		RecentlyViewedSummary recentlyViewedSummary = null;
		RecentlyViewedResponse recentlyViewedResponse = new RecentlyViewedResponse();
		List<Order> orders = new ArrayList<Order>();
		String sortArray[] = sortVal.split(",");
		if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
			orders.add(new Order(Direction.DESC, sortArray[0]));
		else
			orders.add(new Order(Direction.ASC, sortArray[0]));

		Sort sort = new Sort(orders);
		try {
			recentlyViewedSummary = userService.getRecentlyViewedProduct(
					userId, page, size, sort);
			Page retPage = new Page();

			retPage.setTotalPages(recentlyViewedSummary.getTotalPages());
			retPage.setTotalElements(recentlyViewedSummary.getTotalElements());
			retPage.setNumber(recentlyViewedSummary.getNumber());
			retPage.setSize(recentlyViewedSummary.getSize());

			recentlyViewedResponse.setPage(retPage);
			recentlyViewedResponse
					.setRecentlyViewedResponse(recentlyViewedSummary
							.getRecentlyViewedDetails());
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying get recently viewed product of user",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning RecentlyViewed Response! Success");
		return Response.ok(recentlyViewedResponse).build();
	}

	/**
	 * Method to create new Recently viewed
	 * 
	 * @param userId
	 * @param productIds
	 * @return
	 * @throws MBGAppException
	 * @throws Exception
	 */
	@POST
	@Path("/{uid}/recentlyviewed")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response createRecentlyViewed(@PathParam("uid") String userId,
			Set<String> productIds, @Context SecurityContext securityContext)
			throws MBGAppException {
		logger.info("Requesting for creating RecentlyViewed for user Id : {}",
				userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		RecentlyViewed rv = null;
		try {
			rv = userService.createRecentlyViewedProduct(userId, productIds);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying create recently viewed object", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning created RecentlyViewed response! success");
		return Response.ok(rv).build();
	}

	/**
	 * Method to delete Recently viewed
	 * 
	 * @param userId
	 * @param pIds
	 * @return
	 * @throws MBGAppException
	 */
	@DELETE
	@Path("/{uid}/recentlyviewed")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response deleteRecentlyUsed(@PathParam("uid") String userId,
			Set<String> pIds, @Context SecurityContext securityContext)
			throws MBGAppException {
		logger.info("Requesting for delete RecentlyViewed for userId : {}",
				userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		RecentlyViewed recentlyViewed;
		try {
			recentlyViewed = userService.deleteRecentlyViewedProduct(userId,
					pIds);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying delete recently viewed object", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning deleted RecentlyViewed response! Success");
		return Response.ok(recentlyViewed).build();
	}

	/**
	 * Method to update recently viewed
	 * 
	 * @param userId
	 * @param productIds
	 * @return
	 * @throws MBGAppException
	 */
	@PUT
	@Path("/{uid}/recentlyviewed")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response updateRecentlyViewed(@PathParam("uid") String userId,
			Set<String> productIds, @Context SecurityContext securityContext)
			throws MBGAppException {
		logger.info("Requesting for update recently viewed for userId : {}",
				userId);

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		RecentlyViewed rv = null;
		try {
			rv = userService.updateRecentlyViewedProduct(userId, productIds);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to update recently viewed object", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning update recently viewed response! success");
		return Response.ok(rv).build();
	}

	// Roles
	/**
	 * method for searching user Roles
	 * 
	 * @return List of role object
	 */
	@GET
	@Path("/roles")
	public Response getUserRoles(@QueryParam("view") String view) {
		Set<String> serviceProvidersList = new HashSet<>();
		if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("ServiceProviderList")) {
			logger.info("Requesting for display all service provider list");
			serviceProvidersList = userService.getServiceProvidersList();
			logger.info("Returning display all service provider list! Success");
			return Response.ok(serviceProvidersList).build();
		} else if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("AdminManageRoles")) {
			logger.info("Requesting for fetch all roles in MBG");
			ManageRolesResponse ManageRolesView = userService.manageRoles();
			logger.info("Returning fetch all roles in MBG! Success");
			return Response.ok(ManageRolesView).build();
		} else {
			List<Role> role = userService.getUserRole();
			return Response.ok(role).build();
		}
	}

	/**
	 * method for search user role based on role id.
	 * 
	 * @param roleid
	 * @param view
	 * @return
	 */
	@GET
	@Path("/roles/{roleId}")
	public Response getUserRole(@PathParam("roleId") String roleId,
			@QueryParam("view") String view) {

		ManageRolesView ManageRolesView = null;
		if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("AdminRoleViewDetails")) {
			logger.info(
					"Requesting for Admin manage RoleViewDetails based on roleId : {}",
					roleId);
			try {
				ManageRolesView = userService.manageRoleViewDetails(roleId);
			} catch (Exception e) {
				throw new MBGAppException(
						"Error occured trying to Fetch user role based on roleId",
						e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(),
						1000);
			}
			logger.info("Returning Admin manage RoleViewDetails response! Success");
			return Response.ok(ManageRolesView).build();
		} else {
			logger.info("Requesting for fetch role based on roleId");
			Role role = userService.getUserRoleById(roleId);
			logger.info("Returning fetch role based on roleId response! Success");
			return Response.ok(role).build();
		}
	}

	@GET
	@Path("/roles/{roleId}/contacts")
	public Response getUsersContacts(@PathParam("roleId") String roleId,
			@QueryParam("type") String type) {
		Set<String> mobileNumbers = null;
		Set<String> emailIds = null;
		try {
			if (StringUtils.isNotBlank(type)
					&& type.equalsIgnoreCase("EmailIds")) {
				emailIds = userService.getUsersEmailId(roleId);
				return Response.ok(emailIds).build();
			} else {
				mobileNumbers = userService.getUsersContactNum(roleId);
				return Response.ok(mobileNumbers).build();
			}
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured during fetch users contact number based on roleId",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * Method to count User roles
	 * 
	 * This method gives the number of roles in the system
	 * 
	 * @return
	 */
	@GET
	@Path("/roles/count")
	public Response countRoles() {
		logger.info("Requesting for total role count in MBG");
		long count = userService.countRoles();
		logger.info("Returning total role count in MBG response! Success");
		return Response.ok(count).build();

	}

	/**
	 * Method to add new role
	 * 
	 * This method is to add new role in the system
	 * 
	 * @param role
	 * @return
	 * @throws MBGAppException
	 */
	@POST
	@Path("/roles")
	public Response addRole(RoleCreateRequestParam roleCreateRequestParam)
			throws MBGAppException {
		logger.info("Requesting for create new role in MBG");
		String roleName = roleCreateRequestParam.getRoleName();
		String desc = roleCreateRequestParam.getDesc();
		String userType = roleCreateRequestParam.getUserType();
		Role role = null;
		try {
			role = userService.createRole(roleName, desc, userType);
		} catch (Exception e) {
			throw new MBGAppException("Error occured trying create new role",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning newly created role response! Success");
		return Response.ok(role).build();
	}

	/**
	 * Method to update user role by Id.
	 * 
	 * @param roleId
	 * @param name
	 * @param lang
	 * @param val
	 * @return
	 * @throws MBGAppException
	 */
	@PUT
	@Path("/roles/{roleid}")
	public Response updateUserRoleById(@PathParam("roleid") String roleId,
			RoleUpdateRequestParam roleUpdateRequestParam)
			throws MBGAppException {
		logger.info("Requesting for update role based on roleId : {}", roleId);
		String roleName = roleUpdateRequestParam.getRoleName();
		String desc = roleUpdateRequestParam.getDesc();
		Role role = null;
		try {
			role = userService.updateUserRoleById(roleId, roleName, desc);
		} catch (Exception e) {
			throw new MBGAppException("Error occured trying update user role",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning update role based on roleId response! Success");
		return Response.ok(role).build();
	}

	/**
	 * Method to delete role by id.
	 * 
	 * @param roleId
	 * @return
	 * @throws MBGAppException
	 */
	@DELETE
	@Path("/roles/{roleid}")
	public Response deleteUserRoleById(@PathParam("roleid") String roleId)
			throws MBGAppException {
		logger.info("Requesting for delete role based on roleId : {}", roleId);
		Role role = null;
		try {
			role = userService.deleteUserRoleById(roleId);
		} catch (Exception e) {
			throw new MBGAppException("Error occured trying delete user role",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning delete role based on roleId response! Success");
		return Response.ok(role).build();
	}

	/**
	 * Method to perform get all the packages for user role
	 * 
	 * @return List<Packages>
	 */
	@GET
	@Path("/roles/userpackages")
	public Response getUserPackage() {
		logger.info("Requesting for fetch all packages in MBG");
		List<UserPackage> userPack = userService.getUserRolePackage();
		logger.info("Returning all packages in MBG response! Success");
		return Response.ok(userPack).build();
	}

	/**
	 * Method to create new System level user Package
	 * 
	 * @param userPackage
	 * @return
	 */
	@POST
	@Path("roles/userpackages")
	public Response createUserPackage(
			UserPackageCreateRequestParam userPackageCreateRequestParam) {
		logger.info("Requesting for create new package");
		String packageName = userPackageCreateRequestParam.getName();
		String desc = userPackageCreateRequestParam.getDesc();
		String privilegeName = userPackageCreateRequestParam.getPrivilegeName();
		UserPackage userPack = userService.createPackage(packageName, desc,
				privilegeName);
		logger.info("Returning newly created package response! Success");
		return Response.ok(userPack).build();
	}

	/**
	 * method for update user package, packageId is necessary field.
	 * 
	 * @param pkgid
	 * @param userPackageUpdateRequestParam
	 * @return
	 * @throws MBGAppException
	 */
	@PUT
	@Path("/roles/userpackages/{pkgid}")
	public Response updateUserPackage(@PathParam("pkgid") String packageId,
			UserPackageUpdateRequestParam userPackageUpdateRequestParam)
			throws MBGAppException {
		logger.info("Requesting for update package based on packageId : {}",
				packageId);
		String packageName = userPackageUpdateRequestParam.getName();
		String desc = userPackageUpdateRequestParam.getDesc();
		String privilegeName = userPackageUpdateRequestParam.getPrivilegeName();
		String pkgId = null;
		try {
			pkgId = userService.updateUserRolePackage(packageId, packageName,
					desc, privilegeName);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying update user package", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning update package based on packageId response! Success");
		return Response.ok(pkgId).build();
	}

	/**
	 * method for delete user package, packageId is necessary field.
	 * 
	 * @param pkgId
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Path("roles/userpackages/{pkgid}")
	public Response deleteUserPackage(@PathParam("pkgid") String pkgId)
			throws Exception {
		logger.info("Requesting for delete package based on packageId : {}",
				pkgId);
		String pkid = null;
		try {
			pkid = userService.deletePackageById(pkgId);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying delete user package", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		logger.info("Returning delete package based on packageId response! Success");
		return Response.ok(pkid).build();
	}

	// wishList

	/**
	 * Method to get WishList It get WishList products Based on user Id
	 * 
	 * @param userId
	 * @return
	 * @throws MBGAppException
	 * @throws Exception
	 */
	@GET
	@Path("/{uid}/wishlist")
	public Response getUserWishList(@PathParam("uid") String userId,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		WishList wishList = null;
		try {
			wishList = userService.getUserWishList(userId);
		} catch (Exception e) {
			throw new MBGAppException("unable to find wishlist for user", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(wishList).build();
	}

	/**
	 * Method to create new WistList
	 * 
	 * @param userId
	 * @param productIds
	 * @return
	 * @throws MBGAppException
	 * @throws Exception
	 */
	@POST
	@Path("/{uid}/wishlist")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response createWishList(@PathParam("uid") String userId,
			Set<String> productIds, @Context SecurityContext securityContext)
			throws MBGAppException {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		WishList wishList = null;
		try {
			wishList = userService.createWishList(userId, productIds);
		} catch (Exception e) {
			throw new MBGAppException("unable to create wishlist", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(wishList).build();
	}

	/**
	 * Method to update the wishlist
	 * 
	 * @Pathparam would be userID and the wishlistID. PUT parameters will be the
	 *            set of ProductIDs to inserted into the wishlist
	 * 
	 * @param userId
	 * @param wishId
	 * @param prodIds
	 * @return
	 * @throws MBGAppException
	 */
	@PUT
	@Path("/{uid}/wishlist/{wlid}")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response updateWishList(@PathParam("uid") String userId,
			@PathParam("wlid") String wishId, Set<String> prodIds,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		WishList rvprods = null;
		try {
			rvprods = userService.updateWishList(userId, wishId, prodIds);
		} catch (Exception e) {
			throw new MBGAppException("unable to update wishlist", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(rvprods).build();

	}

	/**
	 * Method to delete a product from the wishlist
	 * 
	 * @pathParam would be the userID and the wishlistID
	 * 
	 *            DELETE Parameter will be set of ProductIDs to be deleted from
	 *            the wishlist
	 * 
	 * @param userId
	 * @param wisId
	 * @param prodIds
	 * @return
	 * @throws MBGAppException
	 */
	@DELETE
	@Path("/{uid}/wishlist/{wlid}")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response deleteWishlist(@PathParam("uid") String userId,
			@PathParam("wlid") String wishId, Set<String> prodIds,
			@Context SecurityContext securityContext) {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		WishList wishList = userService.deleteWishListProduct(userId, wishId,
				prodIds);
		return Response.ok(wishList).build();
	}

	/**************************************
	 * User Registration & Login endpoints -start
	 ***********************************/

	@PermitAll
	@Path("/register")
	@POST
	public Response registerUser(
			RegisterUserRequestParam registerUserRequestParam,
			@Context HttpServletRequest request) throws MBGAppException {

		if (registerUserRequestParam != null) {

			logger.info("Register user request from ip - {}",
					getRemoteAddress(request));

			try {

				ApiUser apiUser = authUserService
						.registerNewUser(registerUserRequestParam);

				// user created successfully
				RegisterUserResponse registerUserResponse = new RegisterUserResponse(
						apiUser, Status.CREATED, 2001,
						"User registered successfully and verification email sent to "
								+ apiUser.getUsername());
				return Response.ok(registerUserResponse).build();
			} catch (Exception e) {
				throw new MBGAppException("Error registering new user", e,
						Status.BAD_REQUEST.getStatusCode(), 3006);
			}
		} else {
			throw new MBGAppException(
					"Error registering new user, request params not valid",
					Status.BAD_REQUEST.getStatusCode(), 3006);
		}

	}

	/**
	 * method for social login.
	 * 
	 * @param provider
	 * @param accessToken
	 * @return
	 * @throws AuthenticationException
	 */
	@PermitAll
	@Path("login/{provider}")
	@POST
	public Response socialLogin(@PathParam("provider") String provider,
			UserSocialLoginRequestParam socialLoginRequestParam) {

		try {
			ApiUser apiUser = authUserService.socialLogin(provider,
					socialLoginRequestParam.getAccessToken());

			LoginUserResponse loginUserResponse = new LoginUserResponse(
					apiUser, Status.OK, 2000, "Login successful");
			return Response.ok(loginUserResponse).build();

		} catch (AuthenticationException e) {
			throw new MBGAppException("Error authenticating user", e,
					Status.BAD_REQUEST.getStatusCode(), 3006);
		}

	}

	/**
	 * method for user login.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return the api user
	 * @throws AuthenticationException
	 *             the authentication exception
	 */
	@PermitAll
	@Path("/login")
	@POST
	public Response login(UserLoginRequestParam userLoginRequestParam,
			@Context HttpServletRequest request) {
		if (userLoginRequestParam != null) {
			try {

				logger.info("Login request from ip - {}",
						getRemoteAddress(request));

				ApiUser apiUser = authUserService.authenticate(
						userLoginRequestParam.getUsername(),
						userLoginRequestParam.getPassword());
				// send login response
				LoginUserResponse loginUserResponse = new LoginUserResponse(
						apiUser, Status.OK, 2000, "Login successful");
				return Response.ok(loginUserResponse).build();

			} catch (Exception e) {
				throw new MBGAppException("Error authenticating user", e,
						Status.BAD_REQUEST.getStatusCode(), 3006);
			}
		} else {
			throw new MBGAppException(
					"Error authenticating user, login params not valid",
					Status.BAD_REQUEST.getStatusCode(), 3006);
		}

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
	@PermitAll
	@Path("/tokens/{token}")
	@POST
	public Response verifyToken(@PathParam("token") String base64EncodedToken,
			@QueryParam("username") String username) {
		try {
			VerificationToken token = authUserService.verify(
					base64EncodedToken, username);

			Set<Role> roles = userService.searchUserRole(token.getUser()
					.getMongoUserId());
			String roleName = null;
			for (Role role : roles) {
				roleName = role.getName();
			}
			return Response
					.ok()
					.entity(new MessageResponse(Status.OK, 2001,
							"User & email verified successful",
							"Token verified " + roleName)).build();

		} catch (TokenAlreadyVerifiedException e) {
			throw new MBGAppException("Token is already verified", e,
					"Token is already verified",
					Status.CONFLICT.getStatusCode(), 3006);
		} catch (TokenNotFoundException e) {
			throw new MBGAppException("Invalid OTP", e, "Invalid OTP",
					Status.NOT_FOUND.getStatusCode(), 3006);
		} catch (TokenExpiredException e) {
			throw new MBGAppException("Token has expired", e,
					"Token has expired", Status.FORBIDDEN.getStatusCode(), 3006);
		} catch (Exception e) {
			throw new MBGAppException("Unable to verify user", e,
					"Unable to verify user",
					Status.BAD_REQUEST.getStatusCode(), 3006);
		}

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
	@PermitAll
	@Path("/tokens")
	@POST
	public Response sendVerificationToken(String emailAddress) {
		try {
			authUserService.sendVerificationToken(emailAddress);

			MessageResponse response = new MessageResponse(Status.CREATED,
					2000, "Verification Token Created",
					"Verification token sent successfully to " + emailAddress);
			return Response.ok(response).build();
		} catch (AuthenticationException e) {
			throw new MBGAppException(
					"Error sending verification token to user, request params not valid",
					Status.BAD_REQUEST.getStatusCode(), 3008);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error sending verification token to user", e,
					Status.BAD_REQUEST.getStatusCode(), 3006);
		}
	}

	/**
	 * Send forgot password token.
	 *
	 * @param username
	 *            the username
	 * @return the verification token
	 * @throws AuthenticationException
	 *             the authentication exception
	 * @throws Exception
	 *             the exception
	 */
	@PermitAll
	@Path("/login/tokens")
	@POST
	public Response sendForgotPasswordToken(String emailAddress) {
		try {
			authUserService.sendForgotPasswordToken(emailAddress);
			MessageResponse response = new MessageResponse(Status.CREATED,
					2000, "Forgot Password Token Created",
					"Verification token sent successfully to " + emailAddress);
			return Response.ok(response).build();
		} catch (AuthenticationException e) {
			throw new MBGAppException(
					"Error sending verification token to user, request params not valid",
					Status.BAD_REQUEST.getStatusCode(), 3010);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error sending verification token to user", e,
					Status.BAD_REQUEST.getStatusCode(), 3006);
		}
	}

	/**
	 * Send user otp.
	 *
	 * @param username
	 *            the username
	 * @return the verification token
	 * @throws AuthenticationException
	 *             the authentication exception
	 */
	@PermitAll
	@Path("/login/otp")
	@POST
	public Response sendOTP(@QueryParam("type") String type,
			@QueryParam("userId") String userId, String emailAddress) {
		try {
			if (type != null && type.equalsIgnoreCase("UsernameUpdate")) {
				authUserService.sendUsernameUpdateOTP(emailAddress, userId);
				MessageResponse response = new MessageResponse(Status.CREATED,
						2000, "OTP sent",
						"OTP is sent to registered Mobilenumber/Email");
				return Response.ok(response).build();
			} else {
				authUserService.sendUserOTP(emailAddress);
				MessageResponse response = new MessageResponse(Status.CREATED,
						2000, "OTP sent",
						"OTP is sent to registered Mobilenumber/Email");
				return Response.ok(response).build();
			}

		} catch (AuthenticationException e) {
			throw new MBGAppException(
					"Error sending OTP to user, request params not valid",
					Status.BAD_REQUEST.getStatusCode(), 3010);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error while sending OTP to user, request params not valid",
					e, Status.BAD_REQUEST.getStatusCode(), 3006);
		}
	}

	/**
	 * method for reset password using otp
	 * 
	 * @param base64EncodedToken
	 * @param userResetPasswordRequestParam
	 * @return
	 */
	@PermitAll
	@Path("/login/otp/{token}")
	@POST
	public Response resetPasswordUsingOTP(
			@PathParam("token") String base64EncodedToken,
			@QueryParam("type") String type,
			@QueryParam("userId") String userId,
			UserResetPasswordRequestParam userResetPasswordRequestParam) {
		try {
			MessageResponse response = null;
			if (type != null && type.equalsIgnoreCase("UsernameUpdate")) {
				authUserService.updateUsernameUsingOTP(base64EncodedToken,
						userResetPasswordRequestParam.getUsername(), userId);
				response = new MessageResponse(Status.CREATED, 2000,
						"reset password success",
						"User password is reseted successfully");
			} else {
				authUserService.resetPasswordUsingOTP(
						userResetPasswordRequestParam.getUsername(),
						base64EncodedToken,
						userResetPasswordRequestParam.getPassword());
				response = new MessageResponse(Status.CREATED, 2000,
						"username update success",
						"username is updated successfully");
			}
			return Response.ok(response).build();
		} catch (TokenNotFoundException e) {
			throw new MBGAppException("Invalid Token", e,
					Status.BAD_REQUEST.getStatusCode(), 3006);
		} catch (Exception e) {
			throw new MBGAppException("Error occured while reseting password",
					e, Status.BAD_REQUEST.getStatusCode(), 3006);
		}
	}

	/**
	 * method for reset password.
	 * 
	 * @param base64EncodedToken
	 * @param password
	 * @return
	 */
	@PermitAll
	@Path("/login/tokens/{token}")
	@POST
	public Response resetPassword(
			@PathParam("token") String base64EncodedToken, String password) {
		try {
			authUserService.resetPassword(base64EncodedToken, password);
			MessageResponse response = new MessageResponse(Status.OK, 2000,
					"Successfully Reset The Password",
					"password has be successfuly reset for the user to login");
			return Response.ok(response).build();
		} catch (TokenAlreadyVerifiedException e) {
			throw new MBGAppException("Token is already verified",
					Status.CONFLICT.getStatusCode(), 3006);
		} catch (TokenNotFoundException e) {
			throw new MBGAppException("Token is not found in the repository",
					Status.NOT_FOUND.getStatusCode(), 3006);
		} catch (TokenExpiredException e) {
			throw new MBGAppException("Token has expired",
					Status.FORBIDDEN.getStatusCode(), 3006);
		} catch (Exception e) {
			throw new MBGAppException("Error resetting user password", e,
					Status.BAD_REQUEST.getStatusCode(), 3006);
		}
	}

	/**************************************
	 * User Registration & Login endpoints -end
	 ***********************************/

	/**
	 * method for check privilege.
	 * 
	 * @param uid
	 * @param action
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/{uid}/privilege")
	public Response checkPrivilege(@PathParam("uid") String uid,
			@QueryParam("action") String action,
			@QueryParam("resource") String resource,
			@Context SecurityContext securityContext) throws Exception {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		Boolean check = privilegeService.checkPrivilege(uid, action, resource);
		return Response.ok(check).build();
	}

	/******** Profile Project **********/
	/**
	 * Method to post profile projects
	 * 
	 * @param userId
	 * @param profileName
	 * @return
	 */
	@Path("/{uid}/profileprojects")
	@POST
	public Response addProfileProjects(@PathParam("uid") String userId,
			ProfileProjectCreateRequestParam profileProjectCreateRequestParam) {

		UserProjectProfile userProjectProfile = new UserProjectProfile();
		String profileName = profileProjectCreateRequestParam.getProfileName();
		String desc = profileProjectCreateRequestParam.getDesc();
		ProjectType projectTypes = profileProjectCreateRequestParam
				.getProjectTypes();
		try {
			userProjectProfile = userService.addProfileProjects(userId,
					profileName, desc, projectTypes);
		} catch (Exception e) {
			throw new MBGAppException("Error occured adding profile projects",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(userProjectProfile).build();

	}

	/**
	 * Method to get profile projects
	 * 
	 * @param userId
	 * @return
	 */
	@Path("/{uid}/profileprojects")
	@GET
	public Response getMyProfileProjects(@PathParam("uid") String userId,
			@QueryParam("projectType") String projectType,
			@Context SecurityContext securityContext) {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		List<UserProjectProfile> userProjectProfile = new ArrayList<>();

		try {
			userProjectProfile = userService.getMyProfileProjects(userId,
					projectType);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured retrieving profile projects", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(userProjectProfile).build();

	}

	/**
	 * Method to get particular project of the user
	 * 
	 * @param userId
	 * @param projId
	 * @return
	 */
	@Path("/{uid}/profileprojects/{projid}")
	@GET
	public Response getMyProfileProjectDetails(@PathParam("uid") String userId,
			@PathParam("projid") String projId,
			@Context SecurityContext securityContext) {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		UserProjectProfile userProjectProfile = new UserProjectProfile();

		try {
			userProjectProfile = userService.getMyProfileProjectDetails(userId,
					projId);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured retrieving profile projects", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(userProjectProfile).build();

	}

	/**
	 * 
	 * @param userId
	 * @param projid
	 * @param profileProjectUpdateRequestParam
	 * @return
	 */
	@PUT
	@Path("/{uid}/profileprojects/{projid}")
	public Response updateProfileProjects(@PathParam("uid") String userId,
			@PathParam("projid") String projid,
			ProfileProjectUpdateRequestParam profileProjectUpdateRequestParam) {

		UserProjectProfile userProjectProfile = new UserProjectProfile();

		String profileName = profileProjectUpdateRequestParam.getProfileName();
		String desc = profileProjectUpdateRequestParam.getDesc();
		String projectType = profileProjectUpdateRequestParam.getProjectType();
		try {
			userProjectProfile = userService.updateProfileProjects(userId,
					profileName, desc, projectType, projid);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured updating profile projects", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(userProjectProfile).build();

	}

	/**
	 * Method to delete profile project of the user
	 * 
	 * @param userId
	 * @param projid
	 * @return
	 */
	@Path("{uid}/profileprojects/{projid}")
	@DELETE
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response deleteMyProfileProject(@PathParam("uid") String userId,
			@PathParam("projid") String projid,
			@Context SecurityContext securityContext) {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		UserProjectProfile userProjectProfile = null;
		try {
			userProjectProfile = userService.deleteMyProfileProject(userId,
					projid);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to delete the profile project", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(userProjectProfile).build();
	}

	private String getRemoteAddress(final HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaders("X-Forwarded-For");
		if (headers != null) {
			while (headers.hasMoreElements()) {
				final String[] ips = headers.nextElement().split(",");
				for (String ipString : ips) {
					if (!StringUtils.equalsIgnoreCase(ipString, "unknown")) {
						return ipString;
					}
				}
			}
		}
		// atleast return the remote addresss
		return request.getRemoteAddr();

	}

	/**
	 * method for upload user profile image.
	 * 
	 * @param form
	 * @return
	 * @throws IOException
	 * @throws MBGAppException
	 */
	@POST
	@Path("/userimage/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadUserImage(FormDataMultiPart form) throws IOException,
			MBGAppException {
		User user = null;
		String fileName = null;
		String uid = null;
		InputStream fileInputStream = null;
		long contentLength = 0;
		String fileTypeValue = null;
		try {
			Map<String, List<FormDataBodyPart>> formdatas = form.getFields();
			for (String partName : formdatas.keySet()) {
				List<FormDataBodyPart> fileparts = formdatas.get(partName);
				for (FormDataBodyPart formDataBodyPart : fileparts) {
					if (!formDataBodyPart.isSimple()) {
						ContentDisposition contentDisposition = formDataBodyPart
								.getContentDisposition();
						fileInputStream = formDataBodyPart
								.getValueAs(InputStream.class);
						fileName = contentDisposition.getFileName();
						contentLength = contentDisposition.getSize();
					} else {
						if (formDataBodyPart.getName().equals("userId")) {
							uid = formDataBodyPart.getValue();
						}
						if (formDataBodyPart.getName().equals("fileType")) {
							fileTypeValue = formDataBodyPart.getValue();
						}
					}
				}
			}
			user = userService.uploadUserImage(uid, fileName, fileTypeValue,
					fileInputStream, contentLength);
		} catch (Exception e) {
			throw new MBGAppException("Error occured trying to Upload File", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		} finally {
			fileInputStream.close();
		}
		return Response.ok(user).build();
	}

	// upload project image
	/**
	 * method for upload user profile project image.
	 * 
	 * @param form
	 * @return
	 * @throws IOException
	 * @throws MBGAppException
	 */
	@POST
	@Path("/projectimage/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadProjectImage(FormDataMultiPart form)
			throws IOException, MBGAppException {
		String fileName = null;
		String userProjId = null;
		InputStream fileInputStream = null;
		long contentLength = 0;
		String fileTypeValue = null;
		UserProjectProfile userProjectProfile = null;
		try {
			Map<String, List<FormDataBodyPart>> formdatas = form.getFields();
			for (String partName : formdatas.keySet()) {
				List<FormDataBodyPart> fileparts = formdatas.get(partName);
				for (FormDataBodyPart formDataBodyPart : fileparts) {
					if (!formDataBodyPart.isSimple()) {
						ContentDisposition contentDisposition = formDataBodyPart
								.getContentDisposition();
						fileInputStream = formDataBodyPart
								.getValueAs(InputStream.class);
						fileName = contentDisposition.getFileName();
						contentLength = contentDisposition.getSize();
					} else {
						if (formDataBodyPart.getName().equals("profileId")) {
							userProjId = formDataBodyPart.getValue();
						}
						if (formDataBodyPart.getName().equals("fileType")) {
							fileTypeValue = formDataBodyPart.getValue();
						}
					}
				}
			}
			userProjectProfile = userService.uploadProjectImage(userProjId,
					fileName, fileTypeValue, fileInputStream, contentLength);
		} catch (Exception e) {
			throw new MBGAppException("Error occured trying to Upload File", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		} finally {
			fileInputStream.close();
		}
		return Response.ok(userProjectProfile).build();
	}

	/**
	 * method for create user related document
	 * 
	 * @param userTypes
	 * @param status
	 * @return
	 */
	@POST
	@Path("/documents")
	public Response uploadUserRealtedDocumentsInfo(
			UserRelatedDocumentsCreateRequestParam userRelatedDocumentsCreateRequestParam) {
		UserRelatedDocuments userRelatedDocuments = null;

		Set<UserType> userTypes = userRelatedDocumentsCreateRequestParam
				.getUserTypes();
		String status = userRelatedDocumentsCreateRequestParam.getStatus();
		try {
			userRelatedDocuments = userService.createUserRelatedDocuments(
					userTypes, status);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to create  User Related Documents",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(userRelatedDocuments).build();

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
	@POST
	@Path("/documents/file/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadUserRelatedDocumetse(FormDataMultiPart form)
			throws IOException, MBGAppException {
		String updatedId = null;
		String fileName = null;
		String userRelatedDocumentId = null;
		InputStream fileInputStream = null;
		long contentLength = 0;
		String fileTypeValue = null;
		try {
			Map<String, List<FormDataBodyPart>> formdatas = form.getFields();
			for (String partName : formdatas.keySet()) {
				List<FormDataBodyPart> fileparts = formdatas.get(partName);
				for (FormDataBodyPart formDataBodyPart : fileparts) {
					if (!formDataBodyPart.isSimple()) {
						ContentDisposition contentDisposition = formDataBodyPart
								.getContentDisposition();
						fileInputStream = formDataBodyPart
								.getValueAs(InputStream.class);
						fileName = contentDisposition.getFileName();
						contentLength = contentDisposition.getSize();
					} else {

						if (formDataBodyPart.getName().equals(
								"userRelatedDocId")) {
							userRelatedDocumentId = formDataBodyPart.getValue();
						}

						if (formDataBodyPart.getName().equals("fileType")) {
							fileTypeValue = formDataBodyPart.getValue();
						}
					}
				}
			}
			updatedId = userService.uploadTermsAndConditions(fileName,
					fileTypeValue, fileInputStream, contentLength,
					userRelatedDocumentId);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to Upload Terms and condition File ",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		} finally {
			fileInputStream.close();
		}
		return Response.ok().entity("Files uploaded for the ID " + updatedId)
				.build();
	}

	/**
	 * method to get terms and condition file
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/documents")
	public Response getAdminFiles(@QueryParam("filename") String fileName,
			@QueryParam("status") String status, @QueryParam("view") String view)
			throws Exception {
		try {
			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("ManageDocs")) {
				ManageDocs manageDocs = userService.AdminManageDocs();
				return Response.ok(manageDocs).build();
			} else {
				File file = userService.getAdminFile(fileName);
				return Response.ok(file).build();
			}

		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to searching the File ", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	@GET
	@Path("/documents/{documentId}")
	public Response AdminViewDocumentDetails(
			@PathParam("documentId") String documentId) throws Exception {
		try {
			UserRelatedDocuments relatedDocuments = userService
					.getAdminViewDocDetails(documentId);
			return Response.ok(relatedDocuments).build();
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to searching the File ", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * method for update document based on documentId.
	 * 
	 * @param documentId
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("/documents/{documentId}")
	public Response UpdateFile(@PathParam("documentId") String documentId,
			@QueryParam("status") String status,
			@QueryParam("filename") String fileName) throws Exception {
		try {
			UserRelatedDocuments userRelatedDocument = userService
					.updateFile(documentId);
			return Response.ok(userRelatedDocument).build();
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured trying to update the File status ", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * Method to get available balance of the user(Business Associate)
	 * 
	 * @param uid
	 * @return
	 */
	@GET
	@Path("{uid}/balance")
	public Response getBalance(@PathParam("uid") String uid,
			@Context SecurityContext securityContext) {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		UserBalanceInfoResponse userBalanceInfo = new UserBalanceInfoResponse();
		try {
			userBalanceInfo = userService.getUserBalance(uid);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured retrieving profile projects", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		return Response.ok(userBalanceInfo).build();
	}

	/**
	 * Method to modify balance
	 * 
	 * @param uid
	 * @param balanceUpdateRequestParam
	 * @return
	 */
	@PUT
	@Path("{uid}/balance")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response updateUserBalance(@PathParam("uid") String uid,
			BalanceUpdateRequestParam balanceUpdateRequestParam,
			@Context SecurityContext securityContext) {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		String totalPrice = balanceUpdateRequestParam.getTotalPrice();
		String newBalance = balanceUpdateRequestParam.getNewBalance();
		String note = balanceUpdateRequestParam.getNote();
		UserBalanceInfoResponse userBalanceInfo = null;
		try {
			userBalanceInfo = userService.updateUserBalance(uid, totalPrice,
					newBalance, note);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured retrieving profile projects", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		return Response.ok(userBalanceInfo).build();
	}

	@Path("/contact")
	@POST
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response sendContactInfo(@QueryParam("view") String view,
			ContactInfoResponse contactInfoResponse,
			@Context SecurityContext securityContext) {

		if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("established")) {
			try {
				userService.sendContactInfo(contactInfoResponse);
			} catch (UserCommException e) {
				throw new MBGAppException(
						"Error occured while sending contact info", e,
						e.getMessage(), Status.BAD_REQUEST.getStatusCode(),
						1000);
			}
		}

		return Response.ok().build();
	}

	/**
	 * 
	 * @param projid
	 * @param imageOrFileDeleteRequestParam
	 * @return
	 * @throws IOException
	 * @throws MBGAppException
	 */
	@PUT
	@Path("/{uid}/file/delete/{projid}")
	public Response deleteFile(@PathParam("projid") String projid,
			@PathParam("uid") String userId,
			ImageOrFileDeleteRequestParam imageOrFileDeleteRequestParam)
			throws IOException, MBGAppException {
		UserProjectProfile userProjectProfile = null;
		String fileUrl = imageOrFileDeleteRequestParam.getUrl();
		String fileType = imageOrFileDeleteRequestParam.getFileType();
		try {

			userProjectProfile = userService.deleteProfileProjectFile(userId,
					projid, fileUrl, fileType);
		} catch (Exception e) {
			throw new MBGAppException(
					"Error occured while deleting user Profile project Image",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(userProjectProfile).build();
	}

	@POST
	@Path("/sendrequestquotetosellers")
	public Response sendRequestQuoteToSellers(
			QuoteCreateRequestParam productRequirementRequestParam) {
		String customerId = productRequirementRequestParam.getCustomerId();
		String zipcode = productRequirementRequestParam.getZipcode();
		List<QuoteProductRequestParam> quotesInfo = productRequirementRequestParam
				.getProducts();
		String buildonnQuoteRequestId = productRequirementRequestParam
				.getBonnQuoteRequestId();
		String deliveryDate = productRequirementRequestParam.getDeliveryDate();
		Double buildOnnShippingCharge = productRequirementRequestParam
				.getBuildOnnShippingCharge();
		String note = productRequirementRequestParam.getNote();
		String alertType = productRequirementRequestParam.getAlertType();
		String followupDate = productRequirementRequestParam.getFollowupDate();
		String reasonForReject = productRequirementRequestParam
				.getReasonForReject();
		String quoteSellingPriceIncludeTax = productRequirementRequestParam
				.getQuoteSellingPriceIncludeTax();
		try {
			QuoteRequest quoteRequest = userService.sendRequirementToSellers(
					customerId, zipcode, quotesInfo, buildonnQuoteRequestId,
					deliveryDate, buildOnnShippingCharge, note, alertType,
					followupDate, reasonForReject, quoteSellingPriceIncludeTax);
			return Response.ok(quoteRequest).build();
		} catch (Exception e) {
			throw new MBGAppException("Error occured while creating quote", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	@PUT
	@Path("/{uid}/quote/{quoteId}")
	public Response updateQuoteRequest(@PathParam("uid") String userId,
			@PathParam("quoteId") String quoteId,
			@QueryParam("status") String status,
			QuoteCreateRequestParam productRequirementRequestParam) {
		QuoteRequest quoteRequest = null;
		String zipcode = null;
		String deliveryDate = null;
		List<QuoteProductRequestParam> quotesInfo = null;
		String bonnQuoteRequestId = null;
		Double buildOnnShippingCharge = null;
		String note = null;
		String alertType = null;
		String followupDate = null;
		String reasonForReject = null;
		if (productRequirementRequestParam != null) {
			quotesInfo = productRequirementRequestParam.getProducts();
			zipcode = productRequirementRequestParam.getZipcode();
			deliveryDate = productRequirementRequestParam.getDeliveryDate();
			bonnQuoteRequestId = productRequirementRequestParam
					.getBonnQuoteRequestId();
			buildOnnShippingCharge = productRequirementRequestParam
					.getBuildOnnShippingCharge();
			note = productRequirementRequestParam.getNote();
			alertType = productRequirementRequestParam.getAlertType();
			followupDate = productRequirementRequestParam.getFollowupDate();
			reasonForReject = productRequirementRequestParam
					.getReasonForReject();
		}

		try {
			quoteRequest = userService.updateQuoteRequest(userId, quoteId,
					status, quotesInfo, zipcode, deliveryDate,
					bonnQuoteRequestId, buildOnnShippingCharge, note,
					alertType, followupDate, reasonForReject);
		} catch (Exception e) {
			throw new MBGAppException("Error occured while updating Quote", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(quoteRequest).build();
	}

	@DELETE
	@Path("/{uid}/quote/{quoteId}/product/{pid}")
	public Response deleteQuoteProduct(@PathParam("uid") String userId,
			@PathParam("quoteId") String quoteId,
			@PathParam("pid") String productId) {
		userService.deleteQuoteRequest(userId, quoteId, productId);
		return Response.ok().build();
	}

	@POST
	@Path("/{uid}/shareQuoteWithCustomer")
	public Response shareQuoteWithCustomer(@PathParam("uid") String userId,
			@QueryParam("quoteRequestId") String quoteRequestId,
			List<ItemInfoForCommunication> products) {
		try {
			userService
					.shareQuoteWithCustomer(userId, quoteRequestId, products);
		} catch (UserCommException e) {
			throw new MBGAppException(
					"Error occured while sharing best quote with customer", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok().build();
	}

	@POST
	@Path("/promoinfo")
	public Response sendPromotionalSms(
			PromotionInfoRequestParam promotionInfoRequestParam,
			@QueryParam("view") String view) {
		String promoInfo = promotionInfoRequestParam.getPromoInfo();
		String mobileNumbers = promotionInfoRequestParam.getMobileNumbers();
		Set<String> roleNames = promotionInfoRequestParam.getRoleNames();
		try {
			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("SendSmsByRoleWise")) {
				userService.sendPromoSmsByRolewise(promoInfo, roleNames);
			} else {
				userService.sendPromoSms(promoInfo, mobileNumbers);
			}
		} catch (JMSException e) {
			throw new MBGAppException("Unable to send promotional sms ", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@GET
	@Path("/quote/count")
	public Response getQuoteCount() {
		QuoteCountResponse quoteCount = userService.getQuoteCount();
		return Response.ok(quoteCount).build();
	}

	@DELETE
	@Path("quote/{quoteId}")
	public Response deleteQuoteByQuoteId(@PathParam("quoteId") String quoteId) {
		QuoteRequest quoteRequest;
		try {
			quoteRequest = userService.deleteQuoteByQuoteId(quoteId);
		} catch (Exception e) {
			throw new MBGAppException("Unable to delete Quote", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(quoteRequest).build();
	}
}