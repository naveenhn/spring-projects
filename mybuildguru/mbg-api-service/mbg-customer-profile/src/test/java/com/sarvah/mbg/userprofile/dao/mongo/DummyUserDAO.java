/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;

import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.common.contact.Phone;
import com.sarvah.mbg.domain.mongo.common.contact.PhoneType;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.UserStatus;
import com.sarvah.mbg.domain.mongo.userprofile.UserType;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.domain.mongo.userprofile.role.UserPackage;

/**
 * @author naveen
 *
 */
public class DummyUserDAO implements UserDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.mongodb.repository.MongoRepository#save(java
	 * .lang.Iterable)
	 */
	@Override
	public <S extends User> List<S> save(Iterable<S> entites) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.mongodb.repository.MongoRepository#findAll()
	 */
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.mongodb.repository.MongoRepository#findAll(org
	 * .springframework.data.domain.Sort)
	 */
	@Override
	public List<User> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.mongodb.repository.MongoRepository#insert(java
	 * .lang.Object)
	 */
	@Override
	public <S extends User> S insert(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.mongodb.repository.MongoRepository#insert(java
	 * .lang.Iterable)
	 */
	@Override
	public <S extends User> List<S> insert(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.repository.PagingAndSortingRepository#findAll
	 * (org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<User> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.repository.CrudRepository#save(java.lang.Object)
	 */
	@Override
	public <S extends User> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#findOne(java.io.
	 * Serializable)
	 */
	@Override
	public User findOne(String id) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setId(id);
		Address address = new Address();
		address.setAddressId(1);
		Phone phone = new Phone();
		phone.setType(PhoneType.OFFICE);
		phone.setNumber("9663096116");
		Description desc1 = new Description();
		desc1.setLang("eng");
		desc1.setVal("office phone information");
		phone.setDesc(desc1);
		Set<Phone> phoneSet = new HashSet<>();
		phoneSet.add(phone);
		address.setPhoneNumbers(phoneSet);
		user.addAddress(address);
		Set<Role> roleSet = new HashSet<>();
		Role role = new Role();
		role.setId("12");
		Description desc = new Description();
		desc.setLang("eng");
		desc.setVal("role information");
		role.setDesc(desc);
		roleSet.add(role);
		user.setRoles(roleSet);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#exists(java.io.
	 * Serializable)
	 */
	@Override
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		if (id == null)
			return false;
		else
			return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.repository.CrudRepository#findAll(java.lang.
	 * Iterable)
	 */
	@Override
	public Iterable<User> findAll(Iterable<String> ids) {
		// TODO Auto-generated method stub
		List<User> userList = new ArrayList<>();
		User user1 = new User();
		userList.add(user1);
		return userList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#count()
	 */
	@Override
	public long count() {

		return 200;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#delete(java.io.
	 * Serializable)
	 */
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.repository.CrudRepository#delete(java.lang.Object
	 * )
	 */
	@Override
	public void delete(User entity) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.repository.CrudRepository#delete(java.lang.Iterable
	 * )
	 */
	@Override
	public void delete(Iterable<? extends User> entities) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#deleteAll()
	 */
	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.mongo.UserDAO#findByFirstNameAndLastName
	 * (java.lang.String, java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<User> findByFirstNameAndLastName(String firstName,
			String lastName, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.userprofile.dao.mongo.UserDAO#
	 * findByAddresses_PhoneNumbers_Type
	 * (com.sarvah.mbg.domain.mongo.common.contact.PhoneType)
	 */
	@Override
	public List<User> findByAddresses_PhoneNumbers_Type(PhoneType type) {
		// TODO Auto-generated method stub
		List<User> userList = new ArrayList<>();
		User user1 = new User();
		userList.add(user1);
		return userList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.mongo.UserDAO#findByFirstNameLike(java
	 * .lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<User> findByFirstNameLike(String firstName, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.mongo.UserDAO#findByLastNameLike(java.
	 * lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<User> findByLastNameLike(String lastName, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.mongo.UserDAO#findByRoles_RoleName(java
	 * .lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<User> findByRoles_Name(String roleName, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.userprofile.dao.mongo.UserDAO#
	 * findByFullNameLikeAndRoles_RoleName(java.lang.String, java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<User> findByFullNameLikeAndRoles_Name(String sName,
			String roleName, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.mongo.UserDAO#findByFirstNameOrLastNameOrSex
	 * (java.lang.String, java.lang.String, java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<User> findByFirstNameOrLastNameOrSex(String fname,
			String lname, String sex, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.userprofile.dao.mongo.UserDAO#
	 * findByAddresses_AddressLine1OrAddresses_AddressLine2OrAddresses_CityOrAddresses_CountryOrAddresses_StateOrAddresses_ZipcodeOrAddresses_Email
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<User> findByAddresses_AddressLine1OrAddresses_AddressLine2OrAddresses_CityOrAddresses_CountryOrAddresses_StateOrAddresses_ZipcodeOrAddresses_Email(
			String address1, String address2, String city, String country,
			String state, Integer zipcode, String email, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.userprofile.dao.mongo.UserDAO#
	 * findByAddresses_PhoneNumbers_TypeAndAddresses_PhoneNumbers_Number
	 * (java.lang.String, java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<User> findByAddresses_PhoneNumbers_TypeAndAddresses_PhoneNumbers_Number(
			String type, String number, Pageable pageable) {
		// TODO Auto-generated method stub

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.mongo.UserDAO#countByFirstName(java.lang
	 * .String)
	 */
	@Override
	public long countByFirstName(String firstName) {
		// TODO Auto-generated method stub
		return 20;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.mongo.UserDAO#countByLastName(java.lang
	 * .String)
	 */
	@Override
	public long countByLastName(String lastName) {
		// TODO Auto-generated method stub
		return 30;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.mongo.UserDAO#countByUsername(java.lang
	 * .String)
	 */
	@Override
	public long countByUsername(String username) {
		// TODO Auto-generated method stub
		return 100;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.mongo.UserDAO#countByFirstNameAndLastName
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public long countByFirstNameAndLastName(String firstName, String lastName) {
		// TODO Auto-generated method stub
		return 40;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.userprofile.dao.mongo.UserDAO#
	 * countByAddresses_PhoneNumbers_Number(java.lang.String)
	 */
	@Override
	public long countByAddresses_PhoneNumbers_Number(String number) {
		// TODO Auto-generated method stub
		return 20;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.mongo.UserDAO#countByAddresses_Email(java
	 * .lang.String)
	 */
	@Override
	public long countByAddresses_Email(String email) {
		// TODO Auto-generated method stub
		return 20;
	}

	@Override
	public String deleteByCreatedDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(String uid) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setId(uid);
		return user;
	}

	@Override
	public long countByFullName(String fullname) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User findByIdAndRoles(String uid, Role role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteByAddresses_AddressId(Integer aid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByAddresses_Location(Point location, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findByUsername(String username, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findByFullName(String fullname, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findByAddresses_PhoneNumbers_Number(String number,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findByAddresses_Email(String email, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUsername_AndAddresses_Zipcode(String uname, int zipcode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findByAddresses_Zipcode(int zipcode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findByRoles_Name(String roleName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countByRoles_Name(String roleName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Page<User> findByRoles_NameAndStatus(String roleName, String status,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countByRoles_NameAndStatus(String roleName, String status) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.mongo.UserDAO#findByRoles_NameAllIgnoreCase
	 * (java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<User> findByRoles_NameAllIgnoreCase(String roleName,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.mongo.UserDAO#findByStatus(com.sarvah.
	 * mbg.domain.mongo.userprofile.UserStatus)
	 */
	@Override
	public List<User> findByStatus(UserStatus valueOf) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.mongo.UserDAO#findByRoles_UserPackage_Name
	 * (java.lang.String)
	 */
	@Override
	public List<User> findByRoles_UserPackage_Name(String pack) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.userprofile.dao.mongo.UserDAO#
	 * findByRoles_UserPackage_NameAndStatus(java.lang.String,
	 * com.sarvah.mbg.domain.mongo.userprofile.UserStatus)
	 */
	@Override
	public List<User> findByRoles_UserPackage_NameAndStatus(String pack,
			UserStatus valueOf) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.mongo.UserDAO#findByRoles_NameAndStatus
	 * (java.lang.String, com.sarvah.mbg.domain.mongo.userprofile.UserStatus,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<User> findByRoles_NameAndStatus(String roleName,
			UserStatus valueOf, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.userprofile.dao.mongo.UserDAO#
	 * findByRoles_NameAndRoles_UserPackageAndFirstNameOrLastNameOrFullNameOrUsernameAllIgnoreCase
	 * (java.lang.String,
	 * com.sarvah.mbg.domain.mongo.userprofile.role.UserPackage,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<User> findByRoles_NameAndRoles_UserPackageAndFirstNameOrLastNameOrFullNameOrUsernameAllIgnoreCase(
			String string, UserPackage userPackage, String searchValue,
			String searchValue2, String searchValue3, String searchValue4,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.userprofile.dao.mongo.UserDAO#
	 * findByRoles_NameAndStatusAndFirstNameOrLastNameOrFullNameOrUsernameAllIgnoreCase
	 * (java.lang.String, com.sarvah.mbg.domain.mongo.userprofile.UserStatus,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<User> findByRoles_NameAndStatusAndFirstNameOrLastNameOrFullNameOrUsernameAllIgnoreCase(
			String string, UserStatus valueOf, String searchValue,
			String searchValue2, String searchValue3, String searchValue4,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.userprofile.dao.mongo.UserDAO#
	 * findByFirstNameOrLastNameOrFullNameOrUsername(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<User> findByFirstNameOrLastNameOrFullNameOrUsername(
			String string, String searchValue, String searchValue2,
			String searchValue3, String searchValue4, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.userprofile.dao.mongo.UserDAO#
	 * findByRoles_NameAndRoles_UserPackageIn(java.lang.String, java.util.Set,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<User> findByRoles_NameAndRoles_UserPackageIn(String string,
			Set<UserPackage> userPackageSet, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.userprofile.dao.mongo.UserDAO#
	 * findByRoles_NameAndRoles_UserPackageInAndStatus(java.lang.String,
	 * java.util.Set, com.sarvah.mbg.domain.mongo.userprofile.UserStatus,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<User> findByRoles_NameAndRoles_UserPackageInAndStatus(
			String string, Set<UserPackage> userPackageSet, UserStatus valueOf,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findByAddresses_ZipcodeAndRoles_NameIgnoreCase(
			Integer zipcode, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findByAddresses_ZipcodeAndRoles_Name(int zipcode,
			String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_NameAndStatus(UserType valueOf,
			UserStatus valueOf2, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_Type(UserType valueOf, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_TypeAndStatus(UserType valueOf,
			String status, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findByRoles_Type(UserType valueOf) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_TypeAndStatus(UserType valueOf,
			UserStatus valueOf2, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByRoles_NameIsIgnoreCase(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRolesIn(Set<Role> userRoles, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findByAddresses_CityAndRoles_NameIgnoreCase(String city,
			String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findByRoles_NameAndStatus(String roleName, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_NameAndStatusAndOtherSkillSetsIn(
			String roleName, String status, Set<String> skillsets,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByRoles_NameAndStatusAndOtherSkillSetsIn(String roleName,
			String status, Set<String> skillsets) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUsernameOrUserPhoneNumber(String username,
			String userPhoneNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUserPhoneNumber(String userPhoneNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUsernameAndUserPhoneNumber(String username,
			String contactNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_NameAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			String roleName, String firstName, String lastName,
			String fullName, String userName, String mobileNumber,
			String userMobileNumber, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_NameAndStatusAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			String roleName, UserStatus userStatus, String firstName,
			String lastName, String fullName, String userName,
			String mobileNumber, String userMobileNumber, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_NameAndRoles_UserPackageInOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			String roleName, Set<ObjectId> userPackages, String fullName,
			String userName, String mobileNumber, String userMobileNumber,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_NameAndRoles_UserPackageInAndStatusOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			String roleName, Set<ObjectId> userPackages, UserStatus status,
			String fullName, String userName, String mobileNumber,
			String userMobileNumber, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_TypeAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			UserType userType, String firstName, String lastName,
			String fullName, String userName, String mobileNumber,
			String userMobileNumber, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_TypeAndStatusOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			UserType roleType, UserStatus userStatus, String fullName,
			String userName, String mobileNumber, String userMobileNumber,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_NameAndStatusAndOperatingCitiesIn(
			String roleName, String status, Set<String> intrestedCities,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByRoles_NameAndStatusAndOperatingCitiesIn(String roleName,
			String status, Set<String> intrestedCities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<User> findByRoles_NameAllIgnoreCase(String roleName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<User> findByStatusAndRoles_NameIgnoreCase(UserStatus active,
			String roleName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_NameIgnoreCase(String roleName,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByRoles_NameIgnoreCase(String roleName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_NameIn(Set<String> roleNames,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_NameInAndStatus(Set<String> userRoles,
			UserStatus valueOf2, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_TypeAndRoles_NameInAndStatusOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			UserType userType, Set<String> roleNames, UserStatus userStatus,
			String fullName, String userName, String mobileNumber,
			String userMobileNumber, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_TypeAndRoles_NameInOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			UserType roleType, Set<String> rolesName, String fullName,
			String userName, String mobileNumber, String userMobileNumber,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_TypeAndRoles_NameIn(UserType valueOf,
			Set<String> userRoles, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_TypeAndRoles_NameInAndStatus(
			UserType valueOf, Set<String> rolesName, UserStatus valueOf2,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_NameInAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			Set<String> roleNames, String firstName, String lastName,
			String fullName, String userName, String mobileNumber,
			String userMobileNumber, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_NameInAndStatusAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberAllIgnoreCase(
			Set<String> roleNames, UserStatus status, String firstName,
			String lastName, String fullName, String userName,
			String mobileNumber, String userMobileNumber, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUsernameIgnoreCase(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByFirstNameLikeOrLastNameLikeOrFullNameLike(User customer,
			User customer2, User customer3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_NameAndStatusAndOperatingCitiesContains(
			String roleName, String status, String cityName, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findByIdIn(Set<String> userIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByIdIn(Set<String> userIds, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findByRoles_NameAndStatusAndOperatingCitiesIn(
			String roleName, String status, Set<String> intrestedCities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findByRoles_NameAndStatusAndOperatingCitiesContains(
			String roleName, String status, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<User> findByUsernameLikeOrUserPhoneNumberLikeOrFirstNameLikeOrLastNameLikeOrFullNameLikeAllIgnoreCase(
			String searchValue, String searchValue2, String searchValue3,
			String searchValue4, String searchValue5) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<User> findByFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrUserPhoneNumberLikeAllIgnoreCase(
			String firstName, String lastName, String fullName,
			String username, String phoneNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_NameAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberOrContactNameLikeAllIgnoreCase(
			String roleName, String firstName, String lastName,
			String fullName, String userName, String mobileNumber,
			String userMobileNumber, String contactName, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_TypeAndFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrAddresses_PhoneNumbers_NumberOrUserPhoneNumberOrContactNameLikeAllIgnoreCase(
			UserType userType, String firstName, String lastName,
			String fullName, String userName, String mobileNumber,
			String userMobileNumber, String contactName, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countByRoles_TypeAndStatusAllIgnoreCase(String roleType,
			String status) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long countByAddresses_AddressLine1ContainsAllIgnoreCase(String area) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findByAddresses_AddressLine1ContainsAllIgnoreCase(
			String area, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_NameAndAddresses_AddressLine1ContainsAllIgnoreCase(
			String string, String area, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_TypeAndAddresses_AddressLine1ContainsAllIgnoreCase(
			UserType valueOf, String area, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_TypeAndRoles_NameInAndAddresses_AddressLine1ContainsAllIgnoreCase(
			UserType valueOf, Set<String> rolesName, String area,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findByRoles_TypeAndRoles_NameInAndStatusAndAddresses_AddressLine1ContainsAllIgnoreCase(
			UserType valueOf, Set<String> rolesName, UserStatus valueOf2,
			String area, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
