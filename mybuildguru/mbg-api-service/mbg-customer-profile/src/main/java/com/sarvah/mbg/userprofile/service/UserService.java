/**
 * 
 */
package com.sarvah.mbg.userprofile.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import javax.jms.JMSException;

import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;

import com.sarvah.mbg.commons.communication.UserCommException;
import com.sarvah.mbg.domain.common.asset.File;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.marketing.Promotion;
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

/**
 * @author naveen
 *
 */
public interface UserService {

	/**
	 * 
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param phoneNumber
	 * @param roleName
	 * @param fullName
	 * @param location
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	List<User> searchUser(String userName, String firstName, String lastName,
			String email, String phoneNumber, String roleName, String status,
			String fullName, String skillSetName, String operatingCityName,
			String area, Point location, int page, int size, Sort sort)
			throws Exception;

	Long getUserCountByRole(String roleName);

	Long getUserCountByArea(String area);

	Long getUserCountByRoleAndStatus(String roleName, String status);

	Long getUserCountByRoleAndStatusAndOperatingCity(String roleName,
			String status, String operatingCityName);

	Long getUserCountByRoleAndStatusAndSkillSet(String roleName, String status,
			String skillSetName);

	Long getMBGUserCount();

	/**
	 * method for super admin manage providers.
	 * 
	 * @return
	 */
	UserSummaryViewResponse getSuperAdminManageProviders(String pack,
			String status, String searchValue, String area, int page, int size,
			Sort sort);

	/**
	 * method for super admin manage dealers.
	 * 
	 * @return
	 */
	UserSummaryViewResponse getSuperAdminManageDealers(String pack,
			String status, String searchValue, String subCategory, String area,
			int page, int size, Sort sort);

	UserSummaryViewResponse getSellersBySubcategoryAndBrand(String subCategory,
			String brand, String productIds, int page, int size, Sort sort)
			throws Exception;

	/**
	 * Method for super admin manage business associate
	 * 
	 * @return
	 */
	UserSummaryViewResponse getSuperAdminManageBusinessAssociate(
			String searchValue, String status, String area, int page, int size,
			Sort sort);

	/**
	 * method for super admin manage architects.
	 * 
	 * @return
	 */
	UserSummaryViewResponse getSuperAdminManageArchitects(String pack,
			String status, String searchValue, String area, int page, int size,
			Sort sort);

	/**
	 * method for super admin manage interior designers.
	 * 
	 * @return
	 */
	UserSummaryViewResponse getSuperAdminManageInteriorDesigners(String pack,
			String status, String searchValue, String area, int page, int size,
			Sort sort);

	/**
	 * method for super admin manage ServivceProviders.
	 * 
	 * @return
	 */
	UserSummaryViewResponse getSuperAdminManageServivceProviders(
			String roleName, String status, String searchValue, String area,
			int page, int size, Sort sort);

	/**
	 * method for super admin manage end users.
	 * 
	 * @return
	 */
	UserSummaryViewResponse getSuperAdminManageEndusers(String searchValue,
			String status, String area, int page, int size, Sort sort);

	/**
	 * method for get Active and InActive User count
	 * 
	 * @return
	 */
	UsersStatusCount getUserStatusCount();

	/**
	 * Method to get the count of all buyable
	 * users(ie.,Enduser,Architect,Interior Designer,Business Associate)
	 * 
	 * @return
	 */
	BuyableUsersCount getBuyableUsersCount();

	/**
	 * 
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param phoneNumber
	 * @param fullname
	 * @return
	 * @throws Exception
	 */
	long countUser(String userName, String firstName, String lastName,
			String email, String phoneNumber, String fullname, String roleName,
			String status) throws Exception;

	/**
	 * 
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param fullName
	 * @param sex
	 * @param status
	 * @param addrId
	 * @param addrLine1
	 * @param addrLine2
	 * @param city
	 * @param state
	 * @param country
	 * @param zipcode
	 * @param point
	 * @param email
	 * @param phoneType
	 * @param phoneNumber
	 * @param primary
	 * @param role
	 * @return
	 * @throws Exception
	 */
	User createUser(String userName, String firstName, String lastName,
			String fullName, String sex, String status, String addrId,
			String addrLine1, String addrLine2, String city, String state,
			String country, String zipcode, Point point, String email,
			String phoneType, String phoneNumber, String primary, String role)
			throws Exception;

	/**
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	SingleUserResponse searchUserById(String uid) throws Exception;

	ProfileCompletenessResponse sendProfileCompletenessScore(String userId);

	/**
	 * method for super admin manage end user view details.
	 * 
	 * @param userid
	 * @return
	 */

	UserSummaryViewDetails getManageEndUserDetailsView(String userId);

	UserSummaryViewDetails getManageTeleAssociateDetailsView(String userId);

	UserSummaryViewDetails getManageTechAssociateDetailsView(String userId);

	UserSummaryViewDetails getManageFieldAssociateDetailsView(String userId);

	/**
	 * Method to get Admin Manage Business Associate details
	 * 
	 * @param userid
	 * @return
	 */
	UserSummaryViewDetails getManageBusinessAssociateDetailsView(String userId);

	/**
	 * Method to perform update operation update is performed based on userid.
	 * here firstName,lastName and sex is updated
	 * 
	 * @param uid
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param fullName
	 * @param sex
	 * @param status
	 * @param websiteUrl
	 * @param vatNumber
	 * @param panNumber
	 * @param bankAccountNumber
	 * @param bankName
	 * @return
	 * @throws Exception
	 */
	User updateUser(ApiUser apiUser, String uid, String userName,
			String mobileNumber, String firstName, String lastName, String sex,
			String bankName, String bankAccountNumber, String panNumber,
			String vatNumber, String websiteUrl, String status, String desc,
			String contactName, String ifscCode, Set<String> otherSkillSets,
			Set<String> operatingCities, boolean updateUserInfoByAdmin,
			String title) throws Exception;

	/**
	 * method to delete the user here user is deleted based on userid.
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	User deleteUser(String uid) throws Exception;

	/**
	 * Method to get particular address of the user
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 * @throws Exception
	 */
	Address getUserAddressById(String uid, String aid) throws Exception;

	/**
	 * Method to perform update operation update is performed based on
	 * userid,addressId,addressLine1,addressLine2,city,state
	 * country,zipcode,email.
	 * 
	 * @param uid
	 * @param addressId
	 * @param addressLine1
	 * @param addressLine2
	 * @param city
	 * @param state
	 * @param country
	 * @param zipcode
	 * @param email
	 * @param mobileNumber
	 * @param phoneNumber
	 * @param phonetype
	 * @param phoneType1
	 * @param phoneType2
	 * @return
	 * @throws UserInputValidationException
	 * @throws Exception
	 */
	Set<Address> updateUserAddress(String uid, String addressId, String name,
			String contactPersonName, String addressLine1, String addressLine2,
			String city, String state, String country, String zipcode,
			String email, String phoneNumber, String mobileNumber,
			String officeType, String mobileType)
			throws UserInputValidationException, Exception;

	/**
	 * Method to perform search operation search is performed based on
	 * userid,addressId,addressLine1,addressLine2,city,state
	 * country,zipcode,email.
	 * 
	 * @param uid
	 * @param addressLine1
	 * @param addressLine2
	 * @param city
	 * @param state
	 * @param country
	 * @param zipcode
	 * @param email
	 * @return
	 * @throws Exception
	 */
	Set<Address> searchUserAddress(String uid, String addressLine1,
			String addressLine2, String city, String state, String country,
			String zipcode, String email) throws Exception;

	/**
	 * Method to delete user address user address is deleted based on userId and
	 * addressId
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 * @throws Exception
	 */
	String deleteUserAddresses(String uid, String aid) throws Exception;

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
	Address createAddress(String uId, String name, String contactPersonName,
			String addrLine1, String addrLine2, String city, String state,
			String country, String zipcode, String contactNumber,
			String branchTelNumber, String email) throws Exception;

	/**
	 * Method to update user role here user rolename and defaultpackage is
	 * updated based on userid.
	 * 
	 * @param userId
	 * @param roleName
	 * @param package1
	 * @param desc
	 * @return
	 * @throws Exception
	 */
	Role updateUserRole(String userId, String roleName, String package1,
			String desc) throws Exception;

	/**
	 * Method to search user role based on userid. here uid is mandatory
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	Set<Role> searchUserRole(String userId) throws Exception;

	/**
	 * method to disply all ServiceProviders name.
	 * 
	 * @return
	 */
	Set<String> getServiceProvidersList();

	/**
	 * Method to update user phone here user phoneNumber details is updated
	 * based on phonetype and number.
	 * 
	 * @param userId
	 * @param type
	 * @param number
	 * @param desc
	 * @param primary
	 * @return
	 * @throws Exception
	 */
	User updateUserPhone(String userId, String type, String number,
			String desc, String primary) throws Exception;

	/**
	 * Method to search user based on user phone details here user is search
	 * based on phone details.here uid is mandatory
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	Set<String> searchUserPhone(String userId) throws Exception;

	/**
	 * Method to update the user status
	 * 
	 * @param userid
	 * @param userStatus
	 * @return
	 * @throws Exception
	 */
	UserStatus updateUserStatus(String userid, String userStatus)
			throws Exception;

	/**
	 * Method to search status of the user based on uid
	 * 
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	UserStatus getUserStatus(String userid) throws Exception;

	/**
	 * Method to search the package of the user
	 * 
	 * @param uId
	 * @param rId
	 * @return
	 * @throws Exception
	 */
	UserPackage getUserPackage(String uId) throws Exception;

	/**
	 * Method to update user level package of the user
	 * 
	 * @param userId
	 * @param roleId
	 * @param pName
	 * @param desc
	 * @return
	 * @throws Exception
	 */
	UserPackage updateUserPackage(String userId, String pName) throws Exception;

	// Recently Viewed
	/**
	 * Method to get recently viewed product It get recently viewed product
	 * Based on user Id
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	RecentlyViewedSummary getRecentlyViewedProduct(String uid, int page,
			int size, Sort sort) throws Exception;

	/**
	 * Method to create new Recently viewed
	 * 
	 * @param uId
	 * @param productIds
	 * @return
	 * @throws Exception
	 */
	RecentlyViewed createRecentlyViewedProduct(String uId,
			Set<String> productIds) throws Exception;

	/**
	 * Method to delete Recently viewed
	 * 
	 * @param uid
	 * @param rvid
	 * @return
	 * @throws Exception
	 */
	RecentlyViewed deleteRecentlyViewedProduct(String uid, Set<String> rvid)
			throws Exception;

	/**
	 * Method to update recently viewed
	 * 
	 * @param userId
	 * @param productIds
	 * @return
	 * @throws Exception
	 */
	RecentlyViewed updateRecentlyViewedProduct(String userId,
			Set<String> productIds) throws Exception;

	// role
	/**
	 * method for display all system roles
	 * 
	 * @return
	 */
	List<Role> getUserRole();

	/**
	 * method for search user role based on role id.
	 * 
	 * @param roleId
	 * @return
	 */
	Role getUserRoleById(String roleId);

	/**
	 * method for admin manage roles
	 */
	ManageRolesResponse manageRoles();

	/**
	 * methods for admin manage role view details
	 * 
	 * @param roleid
	 * @return
	 * @throws Exception
	 */
	ManageRolesView manageRoleViewDetails(String roleid) throws Exception;

	/**
	 * Method to count User roles
	 * 
	 * @return
	 */
	long countRoles();

	/**
	 * Method to add new role
	 * 
	 * @param roleName
	 * @param desc
	 * @param userPackageName
	 * @return
	 * @throws Exception
	 */
	Role createRole(String roleName, String desc, String userType)
			throws Exception;

	/**
	 * Method to update user role by Id.
	 * 
	 * @param roleid
	 * @param roleName
	 * @param desc
	 * @param packageName
	 * @return
	 * @throws Exception
	 */
	Role updateUserRoleById(String roleid, String roleName, String desc)
			throws Exception;

	/**
	 * Method to delete role by id.
	 * 
	 * @param roleid
	 * @return
	 * @throws Exception
	 */
	Role deleteUserRoleById(String roleid) throws Exception;

	// UserPackage
	/**
	 * Method to perform get all the packages for user role
	 * 
	 * @return
	 */
	List<UserPackage> getUserRolePackage();

	/**
	 * Method to create new System level user Package
	 * 
	 * @param packageName
	 * @param desc
	 * @param privilegeName
	 * @return
	 */
	UserPackage createPackage(String packageName, String desc,
			String privilegeName);

	/**
	 * method for update user package, packageId is necessary field.
	 * 
	 * @param pkgId
	 * @param pkgName
	 * @param desc
	 * @param privilegeName
	 * @return
	 * @throws Exception
	 */
	String updateUserRolePackage(String pkgId, String pkgName, String desc,
			String privilegeName) throws Exception;

	/**
	 * method for delete user package, packageId is necessary field.
	 * 
	 * @param pkgId
	 * @return
	 * @throws Exception
	 */
	String deletePackageById(String pkgId) throws Exception;

	// wishlist
	/**
	 * Method to get WishList It get WishList products Based on user Id
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	WishList getUserWishList(String uid) throws Exception;

	/**
	 * Method to create new WistList
	 * 
	 * @param uid
	 * @param productIds
	 * @return
	 * @throws Exception
	 */
	WishList createWishList(String uid, Set<String> productIds)
			throws Exception;

	/**
	 * Method to update the wishlist
	 * 
	 * @param userId
	 * @param wlId
	 * @param productIds
	 * @return
	 * @throws Exception
	 */
	WishList updateWishList(String userId, String wlId, Set<String> productIds)
			throws Exception;

	/**
	 * Method to delete a product from the wishlist
	 * 
	 * @param uid
	 * @param wlid
	 * @param productIds
	 * @return
	 */
	WishList deleteWishListProduct(String uid, String wlid,
			Set<String> productIds);

	/**
	 * Method for get promotion for store.
	 * 
	 * @param dealerId
	 * @param sid
	 * @return
	 * @throws MBGAppException
	 */
	List<Promotion> getPromotionForStore(String uid, String sid)
			throws Exception;

	/**
	 * Method to get count of promotions for the store
	 * 
	 * @param dealerid
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	Long getPromotionCountForUser(String uid, String sid) throws Exception;

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
	Promotion createPromotionForUserStore(String dealerId, String sid,
			String promotionName, String desc, String promotionType,
			Set<String> productIds, String discount, String sDate, String eDate)
			throws ParseException, Exception;

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
	Promotion updateStorePromotion(String dealerid, String storeid,
			String promoid, String pName, String desc, String pType,
			Set<String> productIds, String discount, String sDate, String eDate)
			throws ParseException, Exception;

	/**
	 * Method for delete Promotion For user store.
	 * 
	 * @param dealerId
	 * @param sid
	 * @param promid
	 * @return
	 * @throws MBGAppException
	 */
	String deletePromotionForUserStore(String uId, String sid, String promId)
			throws Exception;

	/**
	 * method for admin manage dealer view details
	 * 
	 * @throws Exception
	 */
	UserSummaryViewDetails getManageDealerDetailsView(String dealerId)
			throws Exception;

	/**
	 * method for admin manage provider view details
	 * 
	 * @throws Exception
	 */
	UserSummaryViewDetails getManageProviderDetailsView(String userid)
			throws Exception;

	/**
	 * method for admin manage Architect view details
	 * 
	 * @throws Exception
	 */
	UserSummaryViewDetails getManageArchitectDetailsView(String userid)
			throws Exception;

	/**
	 * method for admin manage InteriorDesigner view details
	 * 
	 * @throws Exception
	 */
	UserSummaryViewDetails getManageInteriorDesignerDetailsView(String userid)
			throws Exception;

	/**
	 * method for admin manage ServiceProvider view details
	 * 
	 * @throws Exception
	 */
	UserSummaryViewDetails getManageServiceProviderDetailsView(String uid)
			throws Exception;

	/**
	 * Method to add profile projects
	 * 
	 * @param userId
	 * @param profileName
	 * @return
	 * @throws Exception
	 */
	UserProjectProfile addProfileProjects(String userId, String profileName,
			String desc, ProjectType projectTypes) throws Exception;

	/**
	 * Method to get Profile projects
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<UserProjectProfile> getMyProfileProjects(String userId,
			String projectType) throws Exception;

	/**
	 * Method to get details about particular project
	 * 
	 * @param userId
	 * @param projId
	 * @return
	 * @throws Exception
	 */
	UserProjectProfile getMyProfileProjectDetails(String userId, String projId)
			throws Exception;

	/**
	 * method for get linked products count.
	 * 
	 * @param dealerId
	 * @return
	 */
	long getLinkedProductsCount(String dealerId) throws Exception;

	/**
	 * method for unlink product from store.
	 * 
	 * @param dealerId
	 * @param productLinkOrUnLinkResponse
	 * @return
	 */
	Product unlinkProduct(String dealerId, Set<String> prodIds)
			throws Exception;

	/**
	 * method for link products to Store.
	 * 
	 * @param dealerId
	 * @param productLinkOrUnLinkResponse
	 * @return
	 */
	Product linkProduct(String dealerId, Set<String> prodIds) throws Exception;

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
	User uploadUserImage(String uid, String fileName, String fileTypeValue,
			InputStream fileInputStream, long contentLength) throws Exception;

	/**
	 * method for upload user profile project image.
	 * 
	 * @param form
	 * @return
	 * @throws IOException
	 * @throws MBGAppException
	 */
	UserProjectProfile uploadProjectImage(String userProjId, String fileName,
			String fileTypeValue, InputStream fileInputStream,
			long contentLength) throws Exception;

	/**
	 * method for create user related document
	 * 
	 * @param userTypes
	 * @param status
	 * @return
	 */
	UserRelatedDocuments createUserRelatedDocuments(Set<UserType> userTypes,
			String status);

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
	String uploadTermsAndConditions(String fileName, String fileTypeValue,
			InputStream fileInputStream, long contentLength,
			String userRelatedDocumentId) throws Exception;

	/**
	 * method to get terms and condition file
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	File getAdminFile(String fileName) throws Exception;

	/**
	 * method to get the document based on documentId.
	 * 
	 * @param documentId
	 * @return
	 * @throws Exception
	 */
	UserRelatedDocuments getAdminViewDocDetails(String documentId)
			throws Exception;

	/**
	 * method for Admin manage terms and condition file.
	 * 
	 * @return
	 */
	ManageDocs AdminManageDocs();

	/**
	 * method for update document based on documentId.
	 * 
	 * @param documentId
	 * @return
	 * @throws Exception
	 */
	UserRelatedDocuments updateFile(String documentId) throws Exception;

	/**
	 * Method to get balance available with the user(Business Associate)
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	UserBalanceInfoResponse getUserBalance(String uid) throws Exception;

	/**
	 * Method to update balance
	 * 
	 * @param uid
	 * @param newBalance
	 * @param balance
	 * @return
	 * @throws Exception
	 */
	UserBalanceInfoResponse updateUserBalance(String uid, String totalPrice,
			String newBalance, String note) throws Exception;

	/**
	 * method for verify whether username already exist in the database or not.
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	boolean serachUserName(String userName) throws Exception;

	/**
	 * Method to delete the particular profile Project
	 * 
	 * @param userId
	 * @param projid
	 * @return
	 * @throws Exception
	 */
	UserProjectProfile deleteMyProfileProject(String userId, String projid)
			throws Exception;

	UserSummaryViewResponse getSuperAdminManageUsers(String roleName,
			String status, int page, int size, Sort sort);

	UserSummaryViewResponse getSuperAdminManageTeleAssociate(
			String searchValue, String status, int page, int size, Sort sort);

	UserSummaryViewResponse getSuperAdminManageTechAssociate(
			String searchValue, String status, int page, int size, Sort sort);

	UserSummaryViewResponse getSuperAdminManageFieldAssociate(
			String searchValue, String status, int page, int size, Sort sort);

	UserSummaryViewDetails getManageUserDetailsView(String userid)
			throws Exception;

	void sendContactInfo(ContactInfoResponse contactInfoResponse)
			throws UserCommException;

	Set<String> getUsersContactNum(String roleId) throws Exception;

	Set<String> getUsersEmailId(String roleId);

	/**
	 * 
	 * @param userId
	 * @param profileName
	 * @param desc
	 * @param projectTypes
	 * @param profileProjectid
	 * @return
	 * @throws Exception
	 */
	UserProjectProfile updateProfileProjects(String userId, String profileName,
			String desc, String projectType, String profileProjectid)
			throws Exception;

	/**
	 * 
	 * @param projid
	 * @param fileUrl
	 * @param fileType
	 * @return
	 * @throws Exception
	 */

	UserProjectProfile deleteProfileProjectFile(String userId, String projid,
			String fileUrl, String fileType) throws Exception;

	QuoteRequest sendRequirementToSellers(String customerId, String zipcode,
			List<QuoteProductRequestParam> products,
			String buildonnQuoteRequestId, String deliveryDate,
			Double buildOnnShippingCharge, String note, String alertType,
			String followupDate, String reasonForReject,
			String quoteSellingPriceIncludeTax) throws Exception;

	QuoteRequest updateQuoteRequest(String userId, String quoteRequestId,
			String status, List<QuoteProductRequestParam> products,
			String zipcode, String deliveryDate, String bonnQuoteRequestId,
			Double buildOnnShippingCharge, String note, String alertType,
			String followupDate, String reasonForReject) throws Exception;

	void deleteQuoteRequest(String userId, String quoteId, String productId);

	void shareQuoteWithCustomer(String userId, String quoteRequestId,
			List<ItemInfoForCommunication> products) throws UserCommException;

	void sendPromoSms(String promoInfo, String mobileNumbers)
			throws JMSException;

	void sendPromoSmsByRolewise(String promoInfo, Set<String> roleNames)
			throws JMSException, InterruptedException;

	Set<String> getStoreNamesByRole(String roleName);

	QuoteCountResponse getQuoteCount();

	QuoteRequest deleteQuoteByQuoteId(String quoteId) throws Exception;

	List<UserSummaryView> getSellersByBrandAndSubcategory(String searchValue);

	User deleteUserProfileById(String userId) throws Exception;

	QuoteCountResponse getCustomerQuotesCountByStatus(String userId);
}
