package com.sarvah.mbg.config.populator;

import java.util.HashSet;
import java.util.Set;

import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.role.Privilege;
import com.sarvah.mbg.domain.mongo.userprofile.role.UserPackage;
import com.sarvah.mbg.userprofile.dao.mongo.PrivilegeDAO;
import com.sarvah.mbg.userprofile.dao.role.mongo.UserPackageDAO;

public class UserPackagePopulatorBean {

	UserPackageDAO userPackageDAO;
	PrivilegeDAO privilegeDAO;

	public UserPackagePopulatorBean(UserPackageDAO userPackageDAO,
			PrivilegeDAO privilegeDAO) {
		this.userPackageDAO = userPackageDAO;
		this.privilegeDAO = privilegeDAO;
	}

	public void initUserPackage() {

		userPackageDAO.deleteAll();

		/***** ARCHITECT USER PACKAGE *******/
		// SILVER Package
		UserPackage silArchUserPackage = new UserPackage();
		silArchUserPackage.setName("SILVER ARCHITECT");

		Description archSilPackageDesc = new Description("eng",
				"silver package");
		silArchUserPackage.setDesc(archSilPackageDesc);

		Privilege silArchPrivi1 = (Privilege) privilegeDAO
				.findByName("SILVER ARCHITECT PRIVILEGE TO ADD ADDRESS");

		Privilege silArchPrivi2 = (Privilege) privilegeDAO
				.findByName("SILVER ARCHITECT PRIVILEGE TO ADD PROJECT");

		Privilege silArchPrivi3 = (Privilege) privilegeDAO
				.findByName("SILVER ARCHITECT PRIVILEGE TO POST PROJECT");

		Privilege silArchPrivi4 = (Privilege) privilegeDAO
				.findByName("SILVER ARCHITECT PRIVILEGE TO BID PROJECT");

		Set<Privilege> silArchPrivilegeSet = new HashSet<Privilege>();

		silArchPrivilegeSet.add(silArchPrivi1);
		silArchPrivilegeSet.add(silArchPrivi2);
		silArchPrivilegeSet.add(silArchPrivi3);
		silArchPrivilegeSet.add(silArchPrivi4);

		silArchUserPackage.setPrivileges(silArchPrivilegeSet);

		userPackageDAO.insert(silArchUserPackage);

		// GOLD Package
		UserPackage goldArchUserPackage = new UserPackage();
		goldArchUserPackage.setName("GOLD ARCHITECT");

		Description archGoldPackageDesc = new Description("eng", "gold package");
		goldArchUserPackage.setDesc(archGoldPackageDesc);

		Set<Privilege> goldArchPrivilegeSet = new HashSet<Privilege>();

		Privilege archGoldPrivi1 = privilegeDAO
				.findByName("GOLD ARCHITECT PRIVILEGE TO ADD ADDRESS");

		Privilege archGoldPrivi2 = privilegeDAO
				.findByName("GOLD ARCHITECT PRIVILEGE TO ADD PROJECT");

		Privilege archGoldPrivi3 = privilegeDAO
				.findByName("GOLD ARCHITECT PRIVILEGE TO POST PROJECT");

		Privilege archGoldPrivi4 = privilegeDAO
				.findByName("GOLD ARCHITECT PRIVILEGE TO BID PROJECT");

		goldArchPrivilegeSet.add(archGoldPrivi1);
		goldArchPrivilegeSet.add(archGoldPrivi2);
		goldArchPrivilegeSet.add(archGoldPrivi3);
		goldArchPrivilegeSet.add(archGoldPrivi4);

		goldArchUserPackage.setPrivileges(goldArchPrivilegeSet);

		userPackageDAO.insert(goldArchUserPackage);

		// PLATINUM Package
		UserPackage platArchUserPackage = new UserPackage();
		platArchUserPackage.setName("PLATINUM ARCHITECT");

		Description archPlatPackageDesc = new Description("eng",
				"platinum package");
		platArchUserPackage.setDesc(archPlatPackageDesc);

		Set<Privilege> platniumPrivilegeSet = new HashSet<Privilege>();

		Privilege platPrivi1 = privilegeDAO
				.findByName("PLATINUM ARCHITECT PRIVILEGE TO ADD ADDRESS");

		Privilege platPrivi2 = privilegeDAO
				.findByName("PLATINUM ARCHITECT PRIVILEGE TO ADD PROJECT");

		Privilege platPrivi3 = privilegeDAO
				.findByName("PLATINUM ARCHITECT PRIVILEGE TO POST PROJECT");

		Privilege platPrivi4 = privilegeDAO
				.findByName("PLATINUM ARCHITECT PRIVILEGE TO BID PROJECT");

		platniumPrivilegeSet.add(platPrivi1);
		platniumPrivilegeSet.add(platPrivi2);
		platniumPrivilegeSet.add(platPrivi3);
		platniumPrivilegeSet.add(platPrivi4);

		platArchUserPackage.setPrivileges(platniumPrivilegeSet);

		userPackageDAO.insert(platArchUserPackage);

		/***** INTERIOR DESIGNER USER PACKAGE *******/
		// SILVER Package
		UserPackage silIDUserPackage = new UserPackage();
		silIDUserPackage.setName("SILVER INTERIORDESIGNER");

		Description SilPackageDesc = new Description("eng", "silver package");
		silIDUserPackage.setDesc(SilPackageDesc);

		Privilege silIDPrivi1 = (Privilege) privilegeDAO
				.findByName("SILVER INTERIORDESINGER PRIVILEGE TO ADD ADDRESS");

		Privilege silIDPrivi2 = (Privilege) privilegeDAO
				.findByName("SILVER INTERIORDESINGER PRIVILEGE TO ADD PROJECT");

		Privilege silIDPrivi3 = (Privilege) privilegeDAO
				.findByName("SILVER INTERIORDESINGER PRIVILEGE TO POST PROJECT");

		Privilege silIDPrivi4 = (Privilege) privilegeDAO
				.findByName("SILVER INTERIORDESINGER PRIVILEGE TO BID PROJECT");

		Set<Privilege> silIDPrivilegeSet = new HashSet<Privilege>();

		silIDPrivilegeSet.add(silIDPrivi1);
		silIDPrivilegeSet.add(silIDPrivi2);
		silIDPrivilegeSet.add(silIDPrivi3);
		silIDPrivilegeSet.add(silIDPrivi4);

		silIDUserPackage.setPrivileges(silIDPrivilegeSet);

		userPackageDAO.insert(silIDUserPackage);

		// GOLD Package
		UserPackage goldIDUserPackage = new UserPackage();
		goldIDUserPackage.setName("GOLD INTERIORDESIGNER");

		Description idGoldPackageDesc = new Description("eng", "gold package");
		goldIDUserPackage.setDesc(idGoldPackageDesc);

		Privilege goldIDPrivi1 = privilegeDAO
				.findByName("GOLD INTERIORDESINGER PRIVILEGE TO ADD ADDRESS");

		Privilege goldIDPrivi2 = privilegeDAO
				.findByName("GOLD INTERIORDESINGER PRIVILEGE TO ADD PROJECT");

		Privilege goldIDPrivi3 = privilegeDAO
				.findByName("GOLD INTERIORDESINGER PRIVILEGE TO POST PROJECT");

		Privilege goldIDPrivi4 = privilegeDAO
				.findByName("GOLD INTERIORDESINGER PRIVILEGE TO BID PROJECT");

		Set<Privilege> goldIDPrivilegeSet = new HashSet<Privilege>();

		goldIDPrivilegeSet.add(goldIDPrivi1);
		goldIDPrivilegeSet.add(goldIDPrivi2);
		goldIDPrivilegeSet.add(goldIDPrivi3);
		goldIDPrivilegeSet.add(goldIDPrivi4);

		goldIDUserPackage.setPrivileges(goldIDPrivilegeSet);

		userPackageDAO.insert(goldIDUserPackage);

		// PLATINUM Package
		UserPackage platIDUserPackage = new UserPackage();
		platIDUserPackage.setName("PLATINUM INTERIORDESIGNER");

		Description platPackageDesc = new Description("eng", "platinum package");
		platIDUserPackage.setDesc(platPackageDesc);

		Privilege platIDPrivi1 = privilegeDAO
				.findByName("PLATINUM INTERIORDESIGNER PRIVILEGE TO ADD ADDRESS");

		Privilege platIDPrivi2 = privilegeDAO
				.findByName("PLATINUM INTERIORDESIGNER PRIVILEGE TO ADD PROJECT");

		Privilege platIDPrivi3 = privilegeDAO
				.findByName("PLATINUM INTERIORDESIGNER PRIVILEGE TO POST PROJECT");

		Privilege platIDPrivi4 = privilegeDAO
				.findByName("PLATINUM INTERIORDESIGNER PRIVILEGE TO BID PROJECT");

		Set<Privilege> platinumIDPrivilegeSet = new HashSet<Privilege>();

		platinumIDPrivilegeSet.add(platIDPrivi1);
		platinumIDPrivilegeSet.add(platIDPrivi2);
		platinumIDPrivilegeSet.add(platIDPrivi3);
		platinumIDPrivilegeSet.add(platIDPrivi4);

		platIDUserPackage.setPrivileges(platinumIDPrivilegeSet);

		userPackageDAO.insert(platIDUserPackage);

		/***** PROVIDER USER PACKAGE *******/
		// SILVER Package
		UserPackage silProviderUserPackage = new UserPackage();
		silProviderUserPackage.setName("SILVER PROVIDER");

		Description providerSilPackagedesc = new Description("eng",
				"silver package");
		silProviderUserPackage.setDesc(providerSilPackagedesc);

		Privilege silProviderPrivi1 = (Privilege) privilegeDAO
				.findByName("SILVER PROVIDER PRIVILEGE TO ADD ADDRESS");

		Privilege silProviderPrivi2 = (Privilege) privilegeDAO
				.findByName("SILVER PROVIDER PRIVILEGE TO ADD PRODUCT");

		Set<Privilege> silProviderPrivilegeSet = new HashSet<Privilege>();

		silProviderPrivilegeSet.add(silProviderPrivi1);
		silProviderPrivilegeSet.add(silProviderPrivi2);

		silProviderUserPackage.setPrivileges(silProviderPrivilegeSet);

		userPackageDAO.insert(silProviderUserPackage);

		// GOLD Package
		UserPackage goldProviderUserPackage = new UserPackage();
		goldProviderUserPackage.setName("GOLD PROVIDER");

		Description providerGoldPackagedesc = new Description("eng",
				"gold package");
		goldProviderUserPackage.setDesc(providerGoldPackagedesc);

		Set<Privilege> goldProviderPrivilegeSet = new HashSet<Privilege>();

		Privilege goldProviderPrivi1 = (Privilege) privilegeDAO
				.findByName("GOLD PROVIDER PRIVILEGE TO ADD ADDRESS");

		Privilege goldProviderPrivi2 = (Privilege) privilegeDAO
				.findByName("GOLD PROVIDER PRIVILEGE TO ADD PRODUCT");

		goldProviderPrivilegeSet.add(goldProviderPrivi1);
		goldProviderPrivilegeSet.add(goldProviderPrivi2);

		goldProviderUserPackage.setPrivileges(goldProviderPrivilegeSet);

		userPackageDAO.insert(goldProviderUserPackage);

		// PLATINUM Package
		UserPackage platinumProviderUserPackage = new UserPackage();
		platinumProviderUserPackage.setName("PLATINUM PROVIDER");

		Description desc3 = new Description("eng", "platinum package");
		platinumProviderUserPackage.setDesc(desc3);
		Set<Privilege> platinumProviderPrivilegeSet = new HashSet<Privilege>();

		Privilege platinumProviderPrivi1 = privilegeDAO
				.findByName("PLATINUM PROVIDER PRIVILEGE TO ADD ADDRESS");

		Privilege platinumProviderPrivi2 = privilegeDAO
				.findByName("PLATINUM PROVIDER PRIVILEGE TO ADD PRODUCT");

		platinumProviderPrivilegeSet.add(platinumProviderPrivi1);
		platinumProviderPrivilegeSet.add(platinumProviderPrivi2);

		platinumProviderUserPackage.setPrivileges(platinumProviderPrivilegeSet);

		userPackageDAO.insert(platinumProviderUserPackage);

		/***** DEALER USER PACKAGE *******/
		// SILVER Package
		UserPackage silDealerUserPackage = new UserPackage();
		silDealerUserPackage.setName("SILVER DEALER");

		Description dealererSilPackagedesc = new Description("eng",
				"silver package");
		silDealerUserPackage.setDesc(dealererSilPackagedesc);

		Privilege silDealerPrivi1 = (Privilege) privilegeDAO
				.findByName("SILVER DEALER PRIVILEGE TO ADD ADDRESS");

		Privilege silDealerPrivi2 = (Privilege) privilegeDAO
				.findByName("SILVER DEALER PRIVILEGE TO BID PROJECT");

		Privilege silDealerPrivi3 = (Privilege) privilegeDAO
				.findByName("SILVER DEALER PRIVILEGE TO ADD PRODUCT");

		Set<Privilege> silDealerPrivilegeSet = new HashSet<Privilege>();

		silDealerPrivilegeSet.add(silDealerPrivi1);
		silDealerPrivilegeSet.add(silDealerPrivi2);
		silDealerPrivilegeSet.add(silDealerPrivi3);

		silDealerUserPackage.setPrivileges(silDealerPrivilegeSet);

		userPackageDAO.insert(silDealerUserPackage);

		// GOLD Package
		UserPackage goldDealerUserPackage = new UserPackage();
		goldDealerUserPackage.setName("GOLD DEALER");

		Description dealerGoldPackagedesc = new Description("eng",
				"gold package");
		goldDealerUserPackage.setDesc(dealerGoldPackagedesc);

		Set<Privilege> goldDealerPrivilegeSet = new HashSet<Privilege>();

		Privilege goldDealerPrivi1 = privilegeDAO
				.findByName("GOLD DEALER PRIVILEGE TO ADD ADDRESS");

		Privilege goldDealerPrivi2 = privilegeDAO
				.findByName("GOLD DEALER PRIVILEGE TO BID PROJECT");

		Privilege goldDealerPrivi3 = privilegeDAO
				.findByName("GOLD DEALER PRIVILEGE TO ADD PRODUCT");

		goldDealerPrivilegeSet.add(goldDealerPrivi1);
		goldDealerPrivilegeSet.add(goldDealerPrivi2);
		goldDealerPrivilegeSet.add(goldDealerPrivi3);

		goldDealerUserPackage.setPrivileges(goldDealerPrivilegeSet);

		userPackageDAO.insert(goldDealerUserPackage);

		// // PLATINUM Package
		UserPackage platinumDealerUserPackage = new UserPackage();
		platinumDealerUserPackage.setName("PLATINUM DEALER");

		Description dealerPlatinumPackageDesc = new Description("eng",
				"platinum package");
		platinumDealerUserPackage.setDesc(dealerPlatinumPackageDesc);
		Set<Privilege> platinumDealerPrivilegeSet = new HashSet<Privilege>();

		Privilege platinumDealerPrivi1 = privilegeDAO
				.findByName("PLATINUM DEALER PRIVILEGE TO ADD ADDRESS");

		Privilege platinumDealerPrivi2 = privilegeDAO
				.findByName("PLATINUM DEALER PRIVILEGE TO BID PROJECT");

		Privilege platinumDealerPrivi3 = privilegeDAO
				.findByName("PLATINUM DEALER PRIVILEGE TO ADD PRODUCT");

		platinumDealerPrivilegeSet.add(platinumDealerPrivi1);
		platinumDealerPrivilegeSet.add(platinumDealerPrivi2);
		platinumDealerPrivilegeSet.add(platinumDealerPrivi3);

		platinumDealerUserPackage.setPrivileges(platinumDealerPrivilegeSet);

		userPackageDAO.insert(platinumDealerUserPackage);

		/***** ENDUSER PACKAGE *******/
		// SILVER Package
		UserPackage silEnduserUserPackage = new UserPackage();
		silEnduserUserPackage.setName("SILVER");

		Description enduserSilPackagedesc = new Description("eng",
				"silver package");
		silEnduserUserPackage.setDesc(enduserSilPackagedesc);

		Privilege silEnduserPrivi1 = (Privilege) privilegeDAO
				.findByName("SILVER PRIVILEGE TO ADD PRODUCT");

		Privilege silEnduserPrivi2 = (Privilege) privilegeDAO
				.findByName("SILVER PRIVILEGE TO ADD PROJECT");

		Set<Privilege> silEnduserPrivilegeSet = new HashSet<Privilege>();

		silEnduserPrivilegeSet.add(silEnduserPrivi1);
		silEnduserPrivilegeSet.add(silEnduserPrivi2);

		silEnduserUserPackage.setPrivileges(silEnduserPrivilegeSet);

		userPackageDAO.insert(silEnduserUserPackage);

		// GOLD Package
		UserPackage goldPackage = new UserPackage();
		goldPackage.setName("GOLD");

		Description goldPackagedesc = new Description("eng", "gold package");
		goldPackage.setDesc(goldPackagedesc);

		Privilege goldPrivi1 = (Privilege) privilegeDAO
				.findByName("GOLD PRIVILEGE TO ADD PRODUCT");

		Privilege goldPrivi2 = (Privilege) privilegeDAO
				.findByName("GOLD PRIVILEGE TO ADD PROJECT");

		Set<Privilege> goldPrivilegeSet = new HashSet<Privilege>();

		goldPrivilegeSet.add(goldPrivi1);
		goldPrivilegeSet.add(goldPrivi2);

		goldPackage.setPrivileges(goldPrivilegeSet);

		userPackageDAO.insert(goldPackage);

		// PLATINUM Package
		UserPackage platinumPackage = new UserPackage();
		platinumPackage.setName("PLATINUM");

		Description platinumPackagedesc = new Description("eng",
				"platinum package");
		platinumPackage.setDesc(platinumPackagedesc);

		Privilege platinumPrivi1 = (Privilege) privilegeDAO
				.findByName("PLATINUM PRIVILEGE TO ADD PRODUCT");

		Privilege platinumPrivi2 = (Privilege) privilegeDAO
				.findByName("PLATINUM PRIVILEGE TO ADD PROJECT");

		Set<Privilege> platinumPrivilegeSet = new HashSet<Privilege>();

		platinumPrivilegeSet.add(platinumPrivi1);
		platinumPrivilegeSet.add(platinumPrivi2);

		platinumPackage.setPrivileges(platinumPrivilegeSet);

		userPackageDAO.insert(platinumPackage);
	}

}
