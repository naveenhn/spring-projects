package com.sarvah.mbg.config.populator;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.geo.Point;

import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.common.contact.Phone;
import com.sarvah.mbg.domain.mongo.common.contact.PhoneType;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.UserStatus;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.userprofile.dao.mongo.RoleDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;

public class UserPopulatorBean {
	RoleDAO roleDAO;
	UserDAO userDAO;

	// UserPackageDAO userPackageDAO;
	public UserPopulatorBean(UserDAO userDAO, RoleDAO roleDAO) {
		this.userDAO = userDAO;
		this.roleDAO = roleDAO;
	}

	public void initUsers() {
		//userDAO.deleteAll();
		// user1
		User user1 = new User();
		user1.setId("2");
		user1.setUsername("admin@jaquar.com");
		user1.setFirstName("Jaquar");
		user1.setLastName("Company");
		user1.setFullName("Jaquar Company");
		user1.setStatus(UserStatus.ACTIVE);

		Address address1 = new Address();
		address1.setAddressId(1);
		address1.setAddressLine1("No 42, Navodaya");
		address1.setAddressLine2("23rd Cross, Ittamadu Main Rd, Bsk 3rd Stage");
		address1.setCity("Bengaluru");
		address1.setState("karnataka");
		address1.setCountry("India");
		address1.setZipcode(560085);
		address1.setEmail("admin@jaquar123.com");
		Point point1 = new Point(12.917328, 77.583392);
		address1.setLocation(point1);

		Set<Phone> phonecollection1 = new HashSet<>();
		Phone phone1 = new Phone();
		phone1.setType(PhoneType.MOBILE);
		phone1.setNumber("+91-9611652281");
		Description desc1 = new Description();
		desc1.setLang("eng");
		desc1.setVal("mobile number info");
		phone1.setDesc(desc1);
		phone1.setPrimary(true);
		phonecollection1.add(phone1);

		phone1 = new Phone();
		phone1.setType(PhoneType.OFFICE);
		phone1.setNumber("+91-0845659874");
		Description desc2 = new Description();
		desc2.setLang("eng");
		desc2.setVal("office number info");
		phone1.setDesc(desc2);
		phone1.setPrimary(false);
		phonecollection1.add(phone1);

		address1.setPhoneNumbers(phonecollection1);
		user1.addAddress(address1);

		Address address12 = new Address();
		address12.setAddressId(1);
		address12.setAddressLine1("No 42, Navodaya");
		address12
				.setAddressLine2("23rd Cross, Ittamadu Main Rd, vijaynagar 3rd Stage");
		address12.setCity("Bengaluru");
		address12.setState("karnataka");
		address12.setCountry("India");
		address12.setZipcode(560040);
		address12.setEmail("admin@jaquar123.com");
		Point point12 = new Point(12.917328, 77.583392);
		address12.setLocation(point12);

		Set<Phone> phonecollection12 = new HashSet<>();
		Phone phone12 = new Phone();
		phone12.setType(PhoneType.MOBILE);
		phone12.setNumber("+91-9611652281");
		Description desc12 = new Description();
		desc12.setLang("eng");
		desc12.setVal("mobile number info");
		phone12.setDesc(desc1);
		phone12.setPrimary(true);
		phonecollection12.add(phone12);

		phone12 = new Phone();
		phone12.setType(PhoneType.OFFICE);
		phone12.setNumber("+91-0845659874");
		Description desc122 = new Description();
		desc122.setLang("eng");
		desc122.setVal("office number info");
		phone12.setDesc(desc122);
		phone12.setPrimary(false);
		phonecollection1.add(phone12);

		address12.setPhoneNumbers(phonecollection12);
		user1.addAddress(address12);
		user1.setVatNumber("IN9874569874V");
		user1.setPanNumber("856547895642");
		Role role1 = roleDAO.findByName("PROVIDER");
		Set<Role> roleSet1 = new HashSet<>();
		roleSet1.add(role1);
		user1.setRoles(roleSet1);

		userDAO.insert(user1);

		// **********
		// user2
		User user2 = new User();
		user2.setId("3");
		user2.setUsername("admin@cera.com");
		user2.setFirstName("Cera");
		user2.setLastName("Company");
		user2.setFullName("Cera Company");
		user2.setStatus(UserStatus.ACTIVE);

		Address address2 = new Address();
		address2.setAddressId(2);
		address2.setAddressLine1("No 56, Bengaluru");
		address2.setAddressLine2("23rd Cross, HAL Main Rd, HAL 2nd Stage");
		address2.setCity("Bengaluru");
		address2.setState("karnataka");
		address2.setCountry("India");
		address2.setZipcode(560008);
		address2.setEmail("admin@cera123.com");
		Point point2 = new Point(12.827124, 77.682919);
		address2.setLocation(point2);

		Set<Phone> phonecollection2 = new HashSet<>();
		Phone phone2 = new Phone();
		phone2.setType(PhoneType.MOBILE);
		phone2.setNumber("+91-9661234568");
		phone2 = new Phone();
		phonecollection2.add(phone2);
		phone2.setType(PhoneType.OFFICE);
		phone2.setNumber("+91-0745659885");
		Description desc3 = new Description();
		desc3.setLang("eng");
		desc3.setVal("mobile number info");
		phone2.setDesc(desc3);
		phone2.setPrimary(true);
		phonecollection2.add(phone2);
		address2.setPhoneNumbers(phonecollection2);
		user2.addAddress(address2);
		user2.setVatNumber("IN5844569874V");
		user2.setPanNumber("547847895642");
		Role role2 = roleDAO.findByName("DEALER");
		Set<Role> roleSet2 = new HashSet<Role>();
		roleSet2.add(role2);
		user2.setRoles(roleSet2);

		userDAO.insert(user2);

		// ************
		// user3
		User user3 = new User();
		user3.setId("5666a85929bfa3183ab40068");
		user3.setUsername("admin@hindware.com");
		user3.setFirstName("Hindware");
		user3.setLastName("Company");
		user3.setFullName("Hindware Company");
		user3.setStatus(UserStatus.ACTIVE);

		Address address3 = new Address();
		address3.setAddressId(3);
		address3.setAddressLine1("No 56, Kurnool");
		address3.setAddressLine2("23rd Cross, KNL Main Rd, KNL 3rd Stage");
		address3.setCity("Kurnool");
		address3.setState("Andhra Pradesh");
		address3.setCountry("India");
		address3.setZipcode(518001);
		address3.setEmail("admin@hindware123.com");
		Point point3 = new Point(15.817460, 78.044609);
		address3.setLocation(point3);

		Set<Phone> phonecollection3 = new HashSet<>();
		Phone phone3 = new Phone();
		phone3.setType(PhoneType.MOBILE);
		phone3.setNumber("+91-9900123456");
		Description desc4 = new Description();
		desc4.setLang("eng");
		desc4.setVal("mobile number info");
		phone3.setDesc(desc4);
		phonecollection3.add(phone3);
		phone3 = new Phone();
		phone3.setType(PhoneType.OFFICE);
		phone3.setNumber("+91-0645659864");
		Description desc5 = new Description();
		desc5.setLang("eng");
		desc5.setVal("office number info");
		phone3.setDesc(desc5);
		phonecollection3.add(phone3);
		address3.setPhoneNumbers(phonecollection3);
		user3.addAddress(address3);
		user3.setVatNumber("IN71274569874V");
		user3.setPanNumber("365547895642");
		Role role3 = roleDAO.findByName("ARCHITECT");
		Set<Role> roleSet3 = new HashSet<>();
		roleSet3.add(role3);
		user3.setRoles(roleSet3);
		userDAO.insert(user3);

		// ******
		// user4
		User user4 = new User();
		user4.setUsername("admin@havells.com");
		user4.setFirstName("Havells");
		user4.setLastName("Company");
		user4.setFullName("Havells Company");
		user4.setStatus(UserStatus.ACTIVE);

		Address address4 = new Address();
		address4.setAddressId(4);
		address4.setAddressLine1("No 54, Anantapur");
		address4.setAddressLine2("12rd Cross,Tata Mainroad, ATP");
		address4.setCity("Anantapur");
		address4.setState("Andhra Pradesh");
		address4.setCountry("India");
		address4.setZipcode(515001);
		address4.setEmail("admin@havells123.com");
		Point point4 = new Point(14.659314, 77.604506);
		address4.setLocation(point4);

		Set<Phone> phonecollection4 = new HashSet<>();
		Phone phone4 = new Phone();
		phone4.setType(PhoneType.MOBILE);
		phone4.setNumber("+91-9665566223");
		phone4 = new Phone();
		phonecollection4.add(phone4);
		phone4.setType(PhoneType.OFFICE);
		phone4.setNumber("+91-07456001122");
		Description desc6 = new Description();
		desc6.setLang("eng");
		desc6.setVal("mobile number info");
		phone4.setDesc(desc6);
		phone4.setPrimary(true);
		phonecollection4.add(phone4);
		address4.setPhoneNumbers(phonecollection4);
		user4.addAddress(address4);
		user4.setVatNumber("IN2574569874V");
		user4.setPanNumber("146547895642");
		Role role4 = roleDAO.findByName("ENDUSER");
		Set<Role> roleSet4 = new HashSet<>();
		roleSet4.add(role4);
		user4.setRoles(roleSet4);
		userDAO.insert(user4);

		// ******
		// user5
		User user5 = new User();
		user5.setId("1");
		user5.setUsername("admin@godrej.com");
		user5.setFirstName("Godrej");
		user5.setLastName("Company");
		user5.setFullName("Godrej Company");
		user5.setStatus(UserStatus.ACTIVE);

		Address address5 = new Address();
		address5.setAddressId(5);
		address5.setAddressLine1("No 52, Mysuru");
		address5.setAddressLine2("27rd Cross, RK nagar Main Rd, Mysore 4th stage");
		address5.setCity("mysuru");
		address5.setState("karnataka");
		address5.setCountry("India");
		address5.setZipcode(570022);
		address5.setEmail("admin@godrej123.com");
		Point point5 = new Point(12.291861, 76.652856);
		address5.setLocation(point5);

		Set<Phone> phonecollection5 = new HashSet<>();
		Phone phone5 = new Phone();
		phone5.setType(PhoneType.MOBILE);
		phone5.setNumber("+91-9665566113");
		phone5 = new Phone();
		phonecollection5.add(phone5);
		phone5.setType(PhoneType.OFFICE);
		phone5.setNumber("+91-07456121122");
		Description desc51 = new Description();
		desc51.setLang("eng");
		desc51.setVal("mobile number info");
		phone5.setDesc(desc51);
		phone5.setPrimary(true);
		phonecollection5.add(phone5);
		address5.setPhoneNumbers(phonecollection5);
		user5.addAddress(address5);
		user5.setVatNumber("IN2454569874V");
		user5.setPanNumber("658547895642");
		Role role5 = roleDAO.findByName("SUPERADMIN");
		Set<Role> roleSet5 = new HashSet<>();
		roleSet5.add(role5);
		user5.setRoles(roleSet5);
		userDAO.insert(user5);

		// *********
		// user6
		User user6 = new User();
		user6.setId("4");
		user6.setUsername("admin@godrejs.com");
		user6.setFirstName("Godrej");
		user6.setLastName("Company");
		user6.setFullName("Godrej Company");
		user6.setStatus(UserStatus.INACTIVE);

		Address address6 = new Address();
		address6.setAddressId(6);
		address6.setAddressLine1("No 52, Annur");
		address6.setAddressLine2("Annur Main Rd, Hunsur rd, mysuru");
		address6.setCity("mysuru");
		address6.setState("karnataka");
		address6.setCountry("India");
		address6.setZipcode(571114);
		address6.setEmail("admin@godrejs.com");
		Point point6 = new Point(12.291861, 76.652856);
		address6.setLocation(point6);

		Set<Phone> phonecollection6 = new HashSet<>();
		Phone phone6 = new Phone();
		phone6.setType(PhoneType.MOBILE);
		phone6.setNumber("+91-9665566113");
		phone6 = new Phone();
		phonecollection6.add(phone6);
		phone6.setType(PhoneType.OFFICE);
		phone6.setNumber("+91-07456121122");
		Description desc61 = new Description();
		desc61.setLang("eng");
		desc61.setVal("mobile number info");
		phone6.setDesc(desc61);
		phone6.setPrimary(true);
		phonecollection6.add(phone6);
		address6.setPhoneNumbers(phonecollection6);

		// address2
		Address address66 = new Address();
		address66.setAddressId(2);
		address66.setAddressLine1("No 75, Bangalore");
		address66.setAddressLine2("46rd Cross, Kamakya Main Rd, Bsk 3rd stage");
		address66.setCity("bengaluru");
		address66.setState("karnataka");
		address66.setCountry("India");
		address66.setZipcode(560085);
		address66.setEmail("admin@godrejs.com");
		Point point66 = new Point(12.291861, 76.652856);
		address66.setLocation(point66);

		Set<Phone> phonecollection66 = new HashSet<>();
		Phone phone66 = new Phone();
		phone66.setType(PhoneType.MOBILE);
		phone66.setNumber("+91-9665566113");
		phone66 = new Phone();
		phonecollection66.add(phone66);
		phone66.setType(PhoneType.OFFICE);
		phone66.setNumber("+91-07456121122");
		Description desc612 = new Description();
		desc612.setLang("eng");
		desc612.setVal("mobile number info");
		phone66.setDesc(desc612);
		phone66.setPrimary(true);
		phonecollection66.add(phone66);
		address66.setPhoneNumbers(phonecollection66);

		user6.addAddress(address6);
		user6.addAddress(address66);
		user6.setVatNumber("IN4584569874V");
		user6.setPanNumber("821547895642");
		Role role6 = roleDAO.findByName("ARCHITECT");
		Set<Role> roleSet6 = new HashSet<>();
		roleSet6.add(role6);
		user6.setRoles(roleSet6);
		userDAO.insert(user6);

		// ************
		// user7
		User user7 = new User();
		user7.setUsername("admin@godrejes.com");
		user7.setFirstName("Godrej");
		user7.setLastName("Company");
		user7.setFullName("Godrej Company");
		user7.setStatus(UserStatus.ACTIVE);

		Address address7 = new Address();
		address7.setAddressId(7);
		address7.setAddressLine1("No 52, Bangalore");
		address7.setAddressLine2("48th cross, BTM layout, bengaluru");
		address7.setCity("Bengaluru");
		address7.setState("karnataka");
		address7.setCountry("India");
		address7.setZipcode(560029);
		address7.setEmail("admin@godrejes.com");
		Point point7 = new Point(12.291861, 76.652856);
		address7.setLocation(point7);

		Set<Phone> phonecollection7 = new HashSet<>();
		Phone phone7 = new Phone();
		phone7.setType(PhoneType.MOBILE);
		phone7.setNumber("+91-9665566113");
		phone7 = new Phone();
		phonecollection7.add(phone7);
		phone7.setType(PhoneType.OFFICE);
		phone7.setNumber("+91-07456121122");
		Description desc71 = new Description();
		desc71.setLang("eng");
		desc71.setVal("mobile number info");
		phone7.setDesc(desc71);
		phone7.setPrimary(true);
		phonecollection7.add(phone7);
		address7.setPhoneNumbers(phonecollection7);
		user7.addAddress(address7);
		user7.setVatNumber("IN4584569874V");
		user7.setPanNumber("821547895642");
		Role role7 = roleDAO.findByName("INTERIORDESIGNER");
		Set<Role> roleSet7 = new HashSet<>();
		roleSet7.add(role7);
		user7.setRoles(roleSet7);
		userDAO.insert(user7);

		// user8
		User user8 = new User();
		user8.setUsername("admin@godrejesss.com");
		user8.setFirstName("Godrej");
		user8.setLastName("Company");
		user8.setFullName("Godrej Company");
		user8.setStatus(UserStatus.ACTIVE);

		Address address8 = new Address();
		address8.setAddressId(8);
		address8.setAddressLine1("No 77, Madikeri");
		address8.setAddressLine2("bus stop Main Rd, Madikeri");
		address8.setCity("Kodagu");
		address8.setState("karnataka");
		address8.setCountry("India");
		address8.setZipcode(571201);
		address8.setEmail("admin@godrejesss.com");
		Point point8 = new Point(12.291861, 76.652856);
		address8.setLocation(point8);

		Set<Phone> phonecollection8 = new HashSet<>();
		Phone phone8 = new Phone();
		phone8.setType(PhoneType.MOBILE);
		phone8.setNumber("+91-9665566113");
		phone8 = new Phone();
		phonecollection8.add(phone8);
		phone8.setType(PhoneType.OFFICE);
		phone8.setNumber("+91-07456121122");
		Description desc81 = new Description();
		desc81.setLang("eng");
		desc81.setVal("mobile number info");
		phone8.setDesc(desc81);
		phone8.setPrimary(true);
		phonecollection8.add(phone8);
		address8.setPhoneNumbers(phonecollection8);
		user8.addAddress(address8);
		user8.setVatNumber("IN4584569874V");
		user8.setPanNumber("821547895642");
		Role role8 = roleDAO.findByName("BUSINESSASSOCIATE");
		Set<Role> roleSet8 = new HashSet<>();
		roleSet8.add(role8);
		user8.setRoles(roleSet8);
		userDAO.insert(user8);

		// user9
		User user9 = new User();
		user9.setUsername("admin@camlin.com");
		user9.setFirstName("Camlin");
		user9.setLastName("Company");
		user9.setFullName("Camlin Company");
		user9.setStatus(UserStatus.ACTIVE);

		Address address9 = new Address();
		address9.setAddressId(8);
		address9.setAddressLine1("No 48, Chittor");
		address9.setAddressLine2("7th main road, chittor");
		address9.setCity("Chittor");
		address9.setState("Andhra Pradesh");
		address9.setCountry("India");
		address9.setZipcode(517001);
		address9.setEmail("admin@camlin.com");
		Point point9 = new Point(12.291861, 76.652856);
		address9.setLocation(point9);

		Set<Phone> phonecollection9 = new HashSet<>();
		Phone phone9 = new Phone();
		phone9.setType(PhoneType.MOBILE);
		phone9.setNumber("+91-9665566113");
		phone9 = new Phone();
		phonecollection9.add(phone9);
		phone9.setType(PhoneType.OFFICE);
		phone9.setNumber("+91-07456121122");
		Description desc91 = new Description();
		desc91.setLang("eng");
		desc91.setVal("mobile number info");
		phone9.setDesc(desc91);
		phone9.setPrimary(true);
		phonecollection9.add(phone9);
		address9.setPhoneNumbers(phonecollection9);
		user9.addAddress(address9);
		user9.setVatNumber("IN4584569874V");
		user9.setPanNumber("821547895642");
		Role role9 = roleDAO.findByName("DEALER");
		Set<Role> roleSet9 = new HashSet<>();
		roleSet9.add(role9);
		user9.setRoles(roleSet9);
		userDAO.insert(user9);

		// user10
		User user10 = new User();
		user10.setUsername("admin@sangeetha.com");
		user10.setFirstName("Sangeetha");
		user10.setLastName("Company");
		user10.setFullName("Sangeetha Company");
		user10.setStatus(UserStatus.ACTIVE);

		Address address10 = new Address();
		address10.setAddressId(8);
		address10.setAddressLine1("no. 46, 21st main road,");
		address10.setAddressLine2("27rd Cross, Kanyakumari Main Rd, Nagercoil");
		address10.setCity("Nagercoil");
		address10.setState("Tamil Nadu");
		address10.setCountry("India");
		address10.setZipcode(629001);
		address10.setEmail("admin@sangeetha.com");
		Point point10 = new Point(12.291861, 76.652856);
		address10.setLocation(point10);

		Set<Phone> phonecollection10 = new HashSet<>();
		Phone phone10 = new Phone();
		phone10.setType(PhoneType.MOBILE);
		phone10.setNumber("+91-9665566113");
		phone10 = new Phone();
		phonecollection10.add(phone10);
		phone10.setType(PhoneType.OFFICE);
		phone10.setNumber("+91-07456121122");
		Description desc101 = new Description();
		desc101.setLang("eng");
		desc101.setVal("mobile number info");
		phone10.setDesc(desc101);
		phone10.setPrimary(true);
		phonecollection10.add(phone10);
		address10.setPhoneNumbers(phonecollection10);
		user10.addAddress(address10);
		user10.setVatNumber("IN4584569874V");
		user10.setPanNumber("821547895642");
		Role role10 = roleDAO.findByName("DEALER");
		Set<Role> roleSet10 = new HashSet<>();
		roleSet10.add(role10);
		user10.setRoles(roleSet10);
		userDAO.insert(user10);
	}
}
