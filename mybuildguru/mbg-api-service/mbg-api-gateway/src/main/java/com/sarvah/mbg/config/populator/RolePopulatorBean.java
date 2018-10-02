package com.sarvah.mbg.config.populator;

import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.ServiceProviders;
import com.sarvah.mbg.domain.mongo.userprofile.UserType;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.domain.mongo.userprofile.role.UserPackage;
import com.sarvah.mbg.userprofile.dao.mongo.RoleDAO;
import com.sarvah.mbg.userprofile.dao.role.mongo.UserPackageDAO;

public class RolePopulatorBean {

	RoleDAO roleDAO;
	UserPackageDAO userPackageDAO;

	public RolePopulatorBean(RoleDAO roleDAO, UserPackageDAO userPackageDAO) {
		this.roleDAO = roleDAO;
		this.userPackageDAO = userPackageDAO;
	}

	public void initRoles() {
		roleDAO.deleteAll();

		/********* PROVIDER ROLE ********/
		UserPackage userPack1 = userPackageDAO.findByName("SILVER PROVIDER");
		Role role1 = new Role();
		if (userPack1 != null) {
			role1.setName(UserType.PROVIDER.toString());
			role1.setUserPackage(userPack1);
			Description desc1 = new Description("eng", "Provider information");
			role1.setDesc(desc1);
			role1.setType(UserType.PROVIDER);
			roleDAO.insert(role1);
		}

		/*********** DEALER ROLE ***********/
		UserPackage userPack2 = userPackageDAO.findByName("GOLD DEALER");
		Role role2 = new Role();
		if (userPack2 != null) {
			role2.setName(UserType.DEALER.toString());

			role2.setUserPackage(userPack2);

			Description desc2 = new Description("eng", "Dealer information");
			role2.setDesc(desc2);
			role2.setType(UserType.DEALER);
			roleDAO.insert(role2);
		}

		/********** ARCHITECT ROLE *********** */
		UserPackage userPack3 = userPackageDAO.findByName("GOLD ARCHITECT");
		Role role3 = new Role();
		if (userPack3 != null) {
			role3.setName(ServiceProviders.ARCHITECT.toString());
			role3.setUserPackage(userPack3);

			Description desc3 = new Description("eng", "Architect information");
			role3.setDesc(desc3);
			role3.setType(UserType.SERVICEPROVIDER);
			roleDAO.insert(role3);
		}

		/********** INTERIORDESIGNER ROLE *********** */

		UserPackage userPack4 = userPackageDAO
				.findByName("SILVER INTERIORDESIGNER");
		Role role4 = new Role();
		if (userPack4 != null) {
			role4.setName(ServiceProviders.INTERIORDESIGNER.toString());
			role4.setUserPackage(userPack4);

			Description desc4 = new Description("eng",
					"Interior Designer information");
			role4.setDesc(desc4);
			role4.setType(UserType.SERVICEPROVIDER);
			roleDAO.insert(role4);
		}

		/********** ENDUSER ROLE *************** */
		UserPackage userPack5 = userPackageDAO.findByName("SILVER");
		Role role5 = new Role();
		if (userPack5 != null) {
			role5.setName(UserType.ENDUSER.toString());
			role5.setUserPackage(userPack5);

			Description desc5 = new Description("eng", "ENDUSER");
			role5.setDesc(desc5);
			role5.setType(UserType.ENDUSER);
			roleDAO.insert(role5);
		}

		/********** BUSINESSASSOCIATE ROLE *************** */
		UserPackage userPack6 = userPackageDAO.findByName("SILVER");
		Role role6 = new Role();
		if (userPack6 != null) {
			role6.setName(UserType.BUSINESSASSOCIATE.toString());
			role6.setUserPackage(userPack6);

			Description desc5 = new Description("eng", "BUSINESSASSOCIATE");
			role6.setDesc(desc5);
			role6.setType(UserType.BUSINESSASSOCIATE);
			roleDAO.insert(role6);
		}

		/********* SUPERADMIN ROLE *********** */
		UserPackage userPack7 = userPackageDAO.findByName("SILVER");
		Role role7 = new Role();
		if (userPack7 != null) {
			role7.setName(UserType.SUPERADMIN.toString());
			role7.setUserPackage(userPack6);

			Description desc7 = new Description("eng", "SUPERADMIN");
			role7.setDesc(desc7);
			role7.setType(UserType.SUPERADMIN);
			roleDAO.insert(role7);
		}

		/********* PLUMBER ROLE *********** */
		UserPackage userPack8 = userPackageDAO.findByName("SILVER");
		Role role8 = new Role();
		if (userPack8 != null) {
			role8.setName(ServiceProviders.PLUMBER.toString());
			role8.setUserPackage(userPack8);

			Description desc8 = new Description("eng", "PLUMBER info");
			role8.setDesc(desc8);
			role8.setType(UserType.SERVICEPROVIDER);
			roleDAO.insert(role8);
		}

		/********* PAINTER ROLE *********** */
		UserPackage userPack9 = userPackageDAO.findByName("SILVER");
		Role role9 = new Role();
		if (userPack9 != null) {
			role9.setName(ServiceProviders.PAINTER.toString());
			role9.setUserPackage(userPack9);

			Description desc9 = new Description("eng", "PAINTER info");
			role9.setDesc(desc9);
			role9.setType(UserType.SERVICEPROVIDER);
			roleDAO.insert(role9);
		}
		/********* CARPENTER ROLE *********** */
		UserPackage userPack10 = userPackageDAO.findByName("SILVER");
		Role role10 = new Role();
		if (userPack10 != null) {
			role10.setName(ServiceProviders.CARPENTER.toString());
			role10.setUserPackage(userPack10);

			Description desc10 = new Description("eng", "CARPENTER info");
			role10.setDesc(desc10);
			role10.setType(UserType.SERVICEPROVIDER);
			roleDAO.insert(role10);
		}
		/********* SITEENGINEER ROLE *********** */
		UserPackage userPack11 = userPackageDAO.findByName("SILVER");
		Role role11 = new Role();
		if (userPack11 != null) {
			role11.setName(ServiceProviders.CIVILENGINEER.toString());
			role11.setUserPackage(userPack11);

			Description desc11 = new Description("eng", "SITEENGINEER info");
			role11.setDesc(desc11);
			role11.setType(UserType.SERVICEPROVIDER);
			roleDAO.insert(role11);
		}
		/********* VASTHUCONSULTANTS ROLE *********** */
		UserPackage userPack12 = userPackageDAO.findByName("SILVER");
		Role role12 = new Role();
		if (userPack12 != null) {
			role12.setName(ServiceProviders.VASTHUCONSULTANTS.toString());
			role12.setUserPackage(userPack12);

			Description desc12 = new Description("eng",
					"VASTHUCONSULTANTS info");
			role12.setDesc(desc12);
			role12.setType(UserType.SERVICEPROVIDER);
			roleDAO.insert(role12);
		}
		/********* ELECTRICIANS ROLE *********** */
		UserPackage userPack13 = userPackageDAO.findByName("SILVER");
		Role role13 = new Role();
		if (userPack13 != null) {
			role13.setName(ServiceProviders.ELECTRICIANS.toString());
			role13.setUserPackage(userPack13);

			Description desc13 = new Description("eng", "ELECTRICIANS info");
			role13.setDesc(desc13);
			role13.setType(UserType.SERVICEPROVIDER);
			roleDAO.insert(role13);
		}
		/********* BIMCONSULTANTS ROLE *********** */
		UserPackage userPack14 = userPackageDAO.findByName("SILVER");
		Role role14 = new Role();
		if (userPack14 != null) {
			role14.setName(ServiceProviders.BIMCONSULTANTS.toString());
			role14.setUserPackage(userPack14);

			Description desc14 = new Description("eng", "BIMCONSULTANTS info");
			role14.setDesc(desc14);
			role14.setType(UserType.SERVICEPROVIDER);
			roleDAO.insert(role14);
		}
		/********* PROJECTCONSULTANT ROLE *********** */
		UserPackage userPack15 = userPackageDAO.findByName("SILVER");
		Role role15 = new Role();
		if (userPack15 != null) {
			role15.setName(ServiceProviders.PROJECTCONSULTANT.toString());
			role15.setUserPackage(userPack15);

			Description desc15 = new Description("eng",
					"PROJECTCONSULTANT info");
			role15.setDesc(desc15);
			role15.setType(UserType.SERVICEPROVIDER);
			roleDAO.insert(role15);
		}
		/********* LANDSCAPER ROLE *********** */
		UserPackage userPack16 = userPackageDAO.findByName("SILVER");
		Role role16 = new Role();
		if (userPack16 != null) {
			role16.setName(ServiceProviders.LANDSCAPEARCHITECT.toString());
			role16.setUserPackage(userPack16);

			Description desc16 = new Description("eng", "LANDSCAPER info");
			role16.setDesc(desc16);
			role16.setType(UserType.SERVICEPROVIDER);
			roleDAO.insert(role16);
		}
		/********* SURVEYOR ROLE *********** */
		UserPackage userPack18 = userPackageDAO.findByName("SILVER");
		Role role18 = new Role();
		if (userPack18 != null) {
			role18.setName(ServiceProviders.SURVEYOR.toString());
			role18.setUserPackage(userPack18);

			Description desc18 = new Description("eng", "SURVEYOR info");
			role18.setDesc(desc18);
			role18.setType(UserType.SERVICEPROVIDER);
			roleDAO.insert(role18);
		}
		/********* WATERDIVINER ROLE *********** */
		UserPackage userPack19 = userPackageDAO.findByName("SILVER");
		Role role19 = new Role();
		if (userPack19 != null) {
			role19.setName(ServiceProviders.WATERDIVINER.toString());
			role19.setUserPackage(userPack19);

			Description desc19 = new Description("eng", "WATERDIVINER info");
			role19.setDesc(desc19);
			role19.setType(UserType.SERVICEPROVIDER);
			roleDAO.insert(role19);
		}
		/********* VALUATOR ROLE *********** */
		UserPackage userPack20 = userPackageDAO.findByName("SILVER");
		Role role20 = new Role();
		if (userPack20 != null) {
			role20.setName(ServiceProviders.VALUATOR.toString());
			role20.setUserPackage(userPack20);

			Description desc20 = new Description("eng", "VALUATOR info");
			role20.setDesc(desc20);
			role20.setType(UserType.SERVICEPROVIDER);
			roleDAO.insert(role20);
		}
		/********* BOREWELLER ROLE *********** */
		UserPackage userPack21 = userPackageDAO.findByName("SILVER");
		Role role21 = new Role();
		if (userPack21 != null) {
			role21.setName(ServiceProviders.BOREWELL.toString());
			role21.setUserPackage(userPack21);

			Description desc21 = new Description("eng", "BOREWELLER info");
			role21.setDesc(desc21);
			role21.setType(UserType.SERVICEPROVIDER);
			roleDAO.insert(role21);
		}
		/********* FLOORINGCONTRACTOR ROLE *********** */
		UserPackage userPack22 = userPackageDAO.findByName("SILVER");
		Role role22 = new Role();
		if (userPack22 != null) {
			role22.setName(ServiceProviders.FLOORINGCONTRACTOR.toString());
			role22.setUserPackage(userPack22);

			Description desc22 = new Description("eng",
					"FLOORINGCONTRACTOR info");
			role22.setDesc(desc22);
			role22.setType(UserType.SERVICEPROVIDER);
			roleDAO.insert(role22);
		}
	}
}
