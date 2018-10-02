/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sarvah.mbg.domain.mongo.common.contact.PhoneType;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.UserStatus;
import com.sarvah.mbg.domain.mongo.userprofile.UserType;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.domain.mongo.userprofile.role.UserPackage;

/**
 * The Interface UserDAO.
 *
 * @author naveen
 */
public interface UserDAO extends MongoRepository<User, String> {

	/**
	 * Find by username.
	 *
	 * @param username
	 *            the username
	 * @return the list
	 */
	List<User> findByUsername(String username, Pageable pageable);

	/**
	 * 
	 * @param username
	 * @return
	 */
	User findByUsername(String username);

	/**
	 * 
	 * @param uid
	 * @return
	 */
	User findById(String uid);

	/**
	 * Find by first name and last name.
	 *
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param pageable
	 *            the pageable
	 * @return the page
	 */
	Page<User> findByFirstNameAndLastName(String firstName, String lastName,
			Pageable pageable);

	/**
	 * Find by addresses_ phone numbers_ type.
	 *
	 * @param type
	 *            the type
	 * @return the list
	 */
	List<User> findByAddresses_PhoneNumbers_Type(PhoneType type);

	List<User> findByFullName(String fullname, Pageable pageable);

	/**
	 * Find by addresses_ phone numbers_ number.
	 *
	 * @param number
	 *            the number
	 * @return the list
	 */
	List<User> findByAddresses_PhoneNumbers_Number(String number,
			Pageable pageable);

	/**
	 * Find by addresses_ email.
	 *
	 * @param email
	 *            the email
	 * @return the list
	 */
	List<User> findByAddresses_Email(String email, Pageable pageable);

	/**
	 * Find by first name like.
	 *
	 * @param firstName
	 *            the first name
	 * @param pageable
	 *            the pageable
	 * @return the page
	 */
	Page<User> findByFirstNameLike(String firstName, Pageable pageable);

	/**
	 * Find by last name like.
	 *
	 * @param lastName
	 *            the last name
	 * @param pageable
	 *            the pageable
	 * @return the page
	 */
	Page<User> findByLastNameLike(String lastName, Pageable pageable);

	/**
	 * Find by roles_ role name.
	 *
	 * @param roleName
	 *            the role name
	 * @param pageable
	 *            the pageable
	 * @return the page
	 */
	Page<User> findByRoles_NameAllIgnoreCase(String roleName, Pageable pageable);

	long countByRoles_Name(String roleName);

	Page<User> findByRoles_NameAndStatus(String roleName, String status,
			Pageable pageable);

	long countByRoles_NameAndStatus(String roleName, String status);

	List<User> findByRoles_Name(String roleName);

	List<User> findByRoles_NameAndStatus(String roleName, String status);

	/**
	 * Find by full name like and roles_ name.
	 *
	 * @param sName
	 *            the s name
	 * @param roleName
	 *            the role name
	 * @param pageable
	 *            the pageable
	 * @return the page
	 */
	Page<User> findByFullNameLikeAndRoles_Name(String sName, String roleName,
			Pageable pageable);

	/**
	 * Find by first name or last name or sex.
	 *
	 * @param fname
	 *            the fname
	 * @param lname
	 *            the lname
	 * @param sex
	 *            the sex
	 * @param pageable
	 *            the pageable
	 * @return the page
	 */
	Page<User> findByFirstNameOrLastNameOrSex(String fname, String lname,
			String sex, Pageable pageable);

	/**
	 * Find by addresses_ address line1 or addresses_ address line2 or
	 * addresses_ city or addresses_ country or addresses_ state or addresses_
	 * zipcode or addresses_ email.
	 *
	 * @param address1
	 *            the address1
	 * @param address2
	 *            the address2
	 * @param city
	 *            the city
	 * @param country
	 *            the country
	 * @param state
	 *            the state
	 * @param zipcode
	 *            the zipcode
	 * @param email
	 *            the email
	 * @param pageable
	 *            the pageable
	 * @return the page
	 */
	Page<User> findByAddresses_AddressLine1OrAddresses_AddressLine2OrAddresses_CityOrAddresses_CountryOrAddresses_StateOrAddresses_ZipcodeOrAddresses_Email(
			String address1, String address2, String city, String country,
			String state, Integer zipcode, String email, Pageable pageable);

	/**
	 * Find by addresses_ phone numbers_ type and addresses_ phone numbers_
	 * number.
	 *
	 * @param type
	 *            the type
	 * @param number
	 *            the number
	 * @param pageable
	 *            the pageable
	 * @return the page
	 */
	Page<User> findByAddresses_PhoneNumbers_TypeAndAddresses_PhoneNumbers_Number(
			String type, String number, Pageable pageable);

	/**
	 * Count by first name.
	 *
	 * @param firstName
	 *            the first name
	 * @return the long
	 */
	long countByFirstName(String firstName);

	/**
	 * Count by last name.
	 *
	 * @param lastName
	 *            the last name
	 * @return the long
	 */
	long countByLastName(String lastName);

	/**
	 * Count by username.
	 *
	 * @param username
	 *            the username
	 * @return the long
	 */
	long countByUsername(String username);

	/**
	 * Count by first name and last name.
	 *
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @return the long
	 */
	long countByFirstNameAndLastName(String firstName, String lastName);

	/**
	 * Count by addresses_ phone numbers_ number.
	 *
	 * @param number
	 *            the number
	 * @return the long
	 */
	long countByAddresses_PhoneNumbers_Number(String number);

	/**
	 * Count by addresses_ email.
	 *
	 * @param email
	 *            the email
	 * @return the long
	 */
	long countByFullName(String fullname);

	long countByAddresses_Email(String email);

	String deleteByCreatedDate();

	User findByIdAndRoles(String uid, Role role);

	Integer deleteByAddresses_AddressId(Integer aid);

	Page<User> findByAddresses_Location(Point location, Pageable pageable);

	User findByUsername_AndAddresses_Zipcode(String uname, int zipcode);

	List<User> findByAddresses_Zipcode(int zipcode);

	List<User> findByStatus(UserStatus valueOf);

	List<User> findByRoles_UserPackage_Name(String pack);

	List<User> findByRoles_UserPackage_NameAndStatus(String pack,
			UserStatus valueOf);

	Page<User> findByRoles_NameAndRoles_UserPackageAndFirstNameOrLastNameOrFullNameOrUsernameAllIgnoreCase(
			String string, UserPackage userPackage, String searchValue,
			String searchValue2, String searchValue3, String searchValue4,
			Pageable pageable);

	Page<User> findByRoles_NameAndStatusAndFirstNameOrLastNameOrFullNameOrUsernameAllIgnoreCase(
			String string, UserStatus valueOf, String searchValue,
			String searchValue2, String searchValue3, String searchValue4,
			Pageable pageable);

	Page<User> findByFirstNameOrLastNameOrFullNameOrUsername(String string,
			String searchValue, String searchValue2, String searchValue3,
			String searchValue4, Pageable pageable);

	Page<User> findByRoles_Name(String string, Pageable pageable);

	@Query("{'roles.name' : ?0 , $or : [{'firstName' : {$regex : ?1, $options: 'i'}}, {'lastName' : {$regex : ?2, $options: 'i'}}, {'fullName' : {$regex : ?3, $options: 'i'}},{'username' : {$regex : ?4, $options: 'i'}},{'addresses.phoneNumbers.number' : {$regex : ?5, $options: 'i'}}, {'userPhoneNumber' : {$regex : ?6, $options: 'i'}}]}")
	Page<User> findByRoles_NameAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			String roleName, String firstName, String lastName,
			String fullName, String userName, String mobileNumber,
			String userMobileNumber, Pageable pageable);

	@Query("{'roles.name' : ?0 , $or : [{'firstName' : {$regex : ?1, $options: 'i'}}, {'lastName' : {$regex : ?2, $options: 'i'}}, {'fullName' : {$regex : ?3, $options: 'i'}},{'username' : {$regex : ?4, $options: 'i'}},{'addresses.phoneNumbers.number' : {$regex : ?5, $options: 'i'}}, {'userPhoneNumber' : {$regex : ?6, $options: 'i'}}, {'contactName' : {$regex : ?7, $options: 'i'}}]}")
	Page<User> findByRoles_NameAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberOrContactNameLikeAllIgnoreCase(
			String roleName, String firstName, String lastName,
			String fullName, String userName, String mobileNumber,
			String userMobileNumber, String contactName, Pageable pageable);

	Page<User> findByRoles_Type(UserType valueOf, Pageable pageable);

	@Query("{'roles.type' : ?0 , $or : [{'firstName' : {$regex : ?1, $options: 'i'}}, {'lastName' : {$regex : ?2, $options: 'i'}}, {'fullName' : {$regex : ?3, $options: 'i'}},{'username' : {$regex : ?4, $options: 'i'}},{'addresses.phoneNumbers.number' : {$regex : ?5, $options: 'i'}}, {'userPhoneNumber' : {$regex : ?6, $options: 'i'}}]}")
	Page<User> findByRoles_TypeAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			UserType userType, String firstName, String lastName,
			String fullName, String userName, String mobileNumber,
			String userMobileNumber, Pageable pageable);

	@Query("{'roles.type' : ?0 , $or : [{'firstName' : {$regex : ?1, $options: 'i'}}, {'lastName' : {$regex : ?2, $options: 'i'}}, {'fullName' : {$regex : ?3, $options: 'i'}},{'username' : {$regex : ?4, $options: 'i'}},{'addresses.phoneNumbers.number' : {$regex : ?5, $options: 'i'}}, {'userPhoneNumber' : {$regex : ?6, $options: 'i'}}, {'contactName' : {$regex : ?7, $options: 'i'}}]}")
	Page<User> findByRoles_TypeAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberOrContactNameLikeAllIgnoreCase(
			UserType userType, String firstName, String lastName,
			String fullName, String userName, String mobileNumber,
			String userMobileNumber, String contactName, Pageable pageable);

	Page<User> findByRoles_TypeAndStatus(UserType valueOf, String status,
			Pageable pageable);

	Page<User> findByRoles_NameAndRoles_UserPackageIn(String string,
			Set<UserPackage> userPackageSet, Pageable pageable);

	Page<User> findByRoles_NameAndRoles_UserPackageInAndStatus(String string,
			Set<UserPackage> userPackageSet, UserStatus valueOf,
			Pageable pageable);

	@Query("{$and : [ {'roles.name' : ?0}, {'status' : ?1}, { $or : [{'fullName' : {$regex : ?2, $options: 'i'}}, {'username' : {$regex : ?3, $options: 'i'}},{'addresses.phoneNumbers.number' : {$regex : ?4, $options: 'i'}}, {'userPhoneNumber' : {$regex : ?5, $options: 'i'}}]}]}")
	Page<User> findByRoles_NameAndStatusAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			String roleName, UserStatus userStatus, String firstName,
			String lastName, String fullName, String userName,
			String mobileNumber, String userMobileNumber, Pageable pageable);

	@Query("{$and : [ {'roles.name' : ?0}, {'roles.userPackage.$id' : {$in : ?1}}, { $or : [{'fullName' : {$regex : ?2, $options: 'i'}}, {'username' : {$regex : ?3, $options: 'i'}},{'addresses.phoneNumbers.number' : {$regex : ?4, $options: 'i'}}, {'userPhoneNumber' : {$regex : ?5, $options: 'i'}}]}]}")
	Page<User> findByRoles_NameAndRoles_UserPackageInOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			String roleName, Set<ObjectId> userPackages, String fullName,
			String userName, String mobileNumber, String userMobileNumber,
			Pageable pageable);

	@Query("{$and : [ {'roles.type' : ?0}, {'roles_Name' : {$in : ?1}}, {'status' : ?2}, { $or : [{'fullName' : {$regex : ?3, $options: 'i'}}, {'username' : {$regex : ?4, $options: 'i'}},{'addresses.phoneNumbers.number' : {$regex : ?5, $options: 'i'}}, {'userPhoneNumber' : {$regex : ?6, $options: 'i'}}]}]}")
	Page<User> findByRoles_TypeAndRoles_NameInAndStatusOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			UserType userType, Set<String> roleNames, UserStatus userStatus,
			String fullName, String userName, String mobileNumber,
			String userMobileNumber, Pageable pageable);

	@Query("{$and : [ {'roles.type' : ?0}, {'roles_Name' : {$in : ?1}}, { $or : [{'fullName' : {$regex : ?2, $options: 'i'}}, {'username' : {$regex : ?3, $options: 'i'}},{'addresses.phoneNumbers.number' : {$regex : ?4, $options: 'i'}}, {'userPhoneNumber' : {$regex : ?5, $options: 'i'}}]}]}")
	Page<User> findByRoles_TypeAndRoles_NameInOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			UserType roleType, Set<String> rolesName, String fullName,
			String userName, String mobileNumber, String userMobileNumber,
			Pageable pageable);

	@Query("{$and : [ {'roles.name' : ?0}, {'roles.userPackage.$id' : {$in : ?1}}, {'status' : ?2}, { $or : [{'fullName' : {$regex : ?3, $options: 'i'}}, {'username' : {$regex : ?4, $options: 'i'}},{'addresses.phoneNumbers.number' : {$regex : ?5, $options: 'i'}}, {'userPhoneNumber' : {$regex : ?6, $options: 'i'}}]}]}")
	Page<User> findByRoles_NameAndRoles_UserPackageInAndStatusOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			String roleName, Set<ObjectId> userPackages, UserStatus status,
			String fullName, String userName, String mobileNumber,
			String userMobileNumber, Pageable pageable);

	@Query("{$and : [ {'roles.type' : ?0}, {'status' : ?1}, { $or : [{'fullName' : {$regex : ?2, $options: 'i'}}, {'username' : {$regex : ?3, $options: 'i'}},{'addresses.phoneNumbers.number' : {$regex : ?4, $options: 'i'}},{'userPhoneNumber' : {$regex : ?5, $options: 'i'}}]}]}")
	Page<User> findByRoles_TypeAndStatusOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			UserType roleType, UserStatus userStatus, String fullName,
			String userName, String mobileNumber, String userMobileNumber,
			Pageable pageable);

	List<User> findByRoles_Type(UserType valueOf);

	List<User> findByAddresses_ZipcodeAndRoles_NameIgnoreCase(Integer zipcode,
			String string);

	List<User> findByAddresses_ZipcodeAndRoles_Name(int zipcode, String string);

	Page<User> findByRoles_NameAndStatus(UserType valueOf, UserStatus valueOf2,
			Pageable pageable);

	Page<User> findByRoles_NameAndStatus(String roleName, UserStatus valueOf,
			Pageable pageable);

	Page<User> findByRoles_TypeAndStatus(UserType valueOf, UserStatus valueOf2,
			Pageable pageable);

	Page<User> findByRoles_TypeAndRoles_NameIn(UserType valueOf,
			Set<String> userRoles, Pageable pageable);

	Page<User> findByRoles_TypeAndRoles_NameInAndStatus(UserType valueOf,
			Set<String> rolesName, UserStatus valueOf2, Pageable pageable);

	User findByRoles_NameIsIgnoreCase(String string);

	@Query("{'roles_Name' : {$in : ?0} , $or : [{'firstName' : {$regex : ?1, $options: 'i'}}, {'lastName' : {$regex : ?2, $options: 'i'}}, {'fullName' : {$regex : ?3, $options: 'i'}},{'username' : {$regex : ?4, $options: 'i'}}, {'addresses.phoneNumbers.number' : {$regex : ?5, $options: 'i'}},{'userPhoneNumber' : {$regex : ?6, $options: 'i'}}]}")
	Page<User> findByRoles_NameInAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			Set<String> roleNames, String firstName, String lastName,
			String fullName, String userName, String mobileNumber,
			String userMobileNumber, Pageable pageable);

	@Query("{'roles' : {$in : ?0}}")
	Page<User> findByRolesIn(Set<Role> userRoles, Pageable pageable);

	List<User> findByAddresses_CityAndRoles_NameIgnoreCase(String city,
			String string);

	Page<User> findByRoles_NameInAndStatus(Set<String> userRoles,
			UserStatus valueOf2, Pageable pageable);

	@Query("{$and : [ {'roles_Name' : {$in : ?0}}, {'status' : ?1}, { $or : [{'firstName' : {$regex : ?2, $options: 'i'}}, {'lastName' : {$regex : ?3, $options: 'i'}},{'fullName' : {$regex : ?4, $options: 'i'}}, {'username' : {$regex : ?5, $options: 'i'}},{'addresses.phoneNumbers.number' : {$regex : ?6, $options: 'i'}}, {'userPhoneNumber' : {$regex : ?7, $options: 'i'}}]}]}")
	Page<User> findByRoles_NameInAndStatusAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			Set<String> roleNames, UserStatus status, String firstName,
			String lastName, String fullName, String userName,
			String mobileNumber, String userMobileNumber, Pageable pageable);

	Page<User> findByRoles_NameAndStatusAndOtherSkillSetsIn(String roleName,
			String status, Set<String> skillsets, Pageable pageable);

	Long countByRoles_NameAndStatusAndOtherSkillSetsIn(String roleName,
			String status, Set<String> skillsets);

	User findByUsernameOrUserPhoneNumber(String username, String userPhoneNumber);

	User findByUserPhoneNumber(String userPhoneNumber);

	User findByUsernameAndUserPhoneNumber(String username, String contactNumber);

	Page<User> findByRoles_NameAndStatusAndOperatingCitiesIn(String roleName,
			String status, Set<String> intrestedCities, Pageable pageable);

	Long countByRoles_NameAndStatusAndOperatingCitiesIn(String roleName,
			String status, Set<String> intrestedCities);

	Set<User> findByRoles_NameAllIgnoreCase(String roleName);

	Set<User> findByStatusAndRoles_NameIgnoreCase(UserStatus active,
			String roleName);

	Page<User> findByRoles_NameIgnoreCase(String roleName, Pageable pageable);

	Long countByRoles_NameIgnoreCase(String roleName);

	Page<User> findByRoles_NameIn(Set<String> roleNames, Pageable pageable);

	User findByUsernameIgnoreCase(String email);

	Set<User> findByUsernameLikeOrUserPhoneNumberLikeOrFirstNameLikeOrLastNameLikeOrFullNameLikeAllIgnoreCase(
			String searchValue, String searchValue2, String searchValue3,
			String searchValue4, String searchValue5);

	User findByFirstNameLikeOrLastNameLikeOrFullNameLike(User customer,
			User customer2, User customer3);

	Set<User> findByFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrUserPhoneNumberLikeAllIgnoreCase(
			String firstName, String lastName, String fullName,
			String username, String phoneNumber);

	Page<User> findByRoles_NameAndStatusAndOperatingCitiesContains(
			String roleName, String status, String cityName, Pageable pageable);

	List<User> findByIdIn(Set<String> userIds);

	Page<User> findByIdIn(Set<String> userIds, Pageable pageable);

	List<User> findByRoles_NameAndStatusAndOperatingCitiesIn(String roleName,
			String status, Set<String> intrestedCities);

	List<User> findByRoles_NameAndStatusAndOperatingCitiesContains(
			String roleName, String status, String string);

	long countByRoles_TypeAndStatusAllIgnoreCase(String roleType, String status);

	Long countByAddresses_AddressLine1ContainsAllIgnoreCase(String area);

	List<User> findByAddresses_AddressLine1ContainsAllIgnoreCase(String area,
			Pageable pageable);

	Page<User> findByRoles_NameAndAddresses_AddressLine1ContainsAllIgnoreCase(
			String string, String area, Pageable pageable);

	Page<User> findByRoles_TypeAndAddresses_AddressLine1ContainsAllIgnoreCase(
			UserType valueOf, String area, Pageable pageable);

	Page<User> findByRoles_TypeAndRoles_NameInAndAddresses_AddressLine1ContainsAllIgnoreCase(
			UserType valueOf, Set<String> rolesName, String area,
			Pageable pageable);

	Page<User> findByRoles_TypeAndRoles_NameInAndStatusAndAddresses_AddressLine1ContainsAllIgnoreCase(
			UserType valueOf, Set<String> rolesName, UserStatus valueOf2,
			String area, Pageable pageable);
}
