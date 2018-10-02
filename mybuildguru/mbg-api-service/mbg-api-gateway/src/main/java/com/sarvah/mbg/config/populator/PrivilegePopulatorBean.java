package com.sarvah.mbg.config.populator;

import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.role.Action;
import com.sarvah.mbg.domain.mongo.userprofile.role.Constraint;
import com.sarvah.mbg.domain.mongo.userprofile.role.Privilege;
import com.sarvah.mbg.domain.mongo.userprofile.role.Resource;
import com.sarvah.mbg.userprofile.dao.mongo.ActionDAO;
import com.sarvah.mbg.userprofile.dao.mongo.PrivilegeDAO;

public class PrivilegePopulatorBean {

	PrivilegeDAO privilegeDAO;
	ActionDAO actionDAO;

	public PrivilegePopulatorBean(PrivilegeDAO privilegeDAO, ActionDAO actionDAO) {
		this.privilegeDAO = privilegeDAO;
		this.actionDAO = actionDAO;
	}

	public void initPrivileges() {
		privilegeDAO.deleteAll();

		/****** Privilege to add Address *****/
		// Silver ARCHITECT Add address
		Action silverArchAddAddressAction = actionDAO.findByName("Add");

		Privilege silverArchAddAddressPrivilege = new Privilege();
		silverArchAddAddressPrivilege
				.setName("SILVER ARCHITECT PRIVILEGE TO ADD ADDRESS");

		Description silverArchAddAddressDescription = new Description("eng",
				"Privilege to add addresses");
		silverArchAddAddressPrivilege.setDesc(silverArchAddAddressDescription);

		Resource silverArchAddAddressResource = new Resource();
		silverArchAddAddressResource.setName("Addresses");

		Constraint silverArchAddAddressConstraint = new Constraint();
		silverArchAddAddressConstraint.setValue("1");

		silverArchAddAddressPrivilege.setAction(silverArchAddAddressAction);
		silverArchAddAddressPrivilege.setResource(silverArchAddAddressResource);
		silverArchAddAddressPrivilege
				.setConstraint(silverArchAddAddressConstraint);

		privilegeDAO.insert(silverArchAddAddressPrivilege);

		// Silver INTERIORDESIGNER Add address
		Action silverIDAddAddressAction = actionDAO.findByName("Add");

		Privilege silverIDAddAddressPrivilege = new Privilege();
		silverIDAddAddressPrivilege
				.setName("SILVER INTERIORDESINGER PRIVILEGE TO ADD ADDRESS");

		Description silverIDAddAddressDescription = new Description("eng",
				"Privilege to add addresses");
		silverIDAddAddressPrivilege.setDesc(silverIDAddAddressDescription);

		Resource silverIDAddAddressResource = new Resource();
		silverIDAddAddressResource.setName("Addresses");

		Constraint silverIDAddAddressConstraint = new Constraint();
		silverIDAddAddressConstraint.setValue("1");

		silverIDAddAddressPrivilege.setAction(silverIDAddAddressAction);
		silverIDAddAddressPrivilege.setResource(silverIDAddAddressResource);
		silverIDAddAddressPrivilege.setConstraint(silverIDAddAddressConstraint);

		privilegeDAO.insert(silverIDAddAddressPrivilege);

		// Silver PROVIDER Add address
		Action silverProviderAddAddressAction = actionDAO.findByName("Add");

		Privilege silverProviderAddAddressPrivilege = new Privilege();
		silverProviderAddAddressPrivilege
				.setName("SILVER PROVIDER PRIVILEGE TO ADD ADDRESS");

		Description silverProviderAddAddressDescription = new Description(
				"eng", "Privilege to add addresses");
		silverProviderAddAddressPrivilege
				.setDesc(silverProviderAddAddressDescription);

		Resource silverProviderAddAddressResource = new Resource();
		silverProviderAddAddressResource.setName("Addresses");

		Constraint silverProviderAddAddressConstraint = new Constraint();
		silverProviderAddAddressConstraint.setValue("1");

		silverProviderAddAddressPrivilege
				.setAction(silverProviderAddAddressAction);
		silverProviderAddAddressPrivilege
				.setResource(silverProviderAddAddressResource);
		silverProviderAddAddressPrivilege
				.setConstraint(silverProviderAddAddressConstraint);

		privilegeDAO.insert(silverProviderAddAddressPrivilege);

		// Silver DEALER Add address
		Action silverDealerAddAddressAction = actionDAO.findByName("Add");

		Privilege silverDealerAddAddressPrivilege = new Privilege();
		silverDealerAddAddressPrivilege
				.setName("SILVER DEALER PRIVILEGE TO ADD ADDRESS");

		Description silverDealerAddAddressDescription = new Description("eng",
				"Privilege to add addresses");
		silverDealerAddAddressPrivilege
				.setDesc(silverDealerAddAddressDescription);

		Resource silverDealerAddAddressResource = new Resource();
		silverDealerAddAddressResource.setName("Addresses");

		Constraint silverDealerAddAddressConstraint = new Constraint();
		silverDealerAddAddressConstraint.setValue("1");

		silverDealerAddAddressPrivilege.setAction(silverDealerAddAddressAction);
		silverDealerAddAddressPrivilege
				.setResource(silverDealerAddAddressResource);
		silverDealerAddAddressPrivilege
				.setConstraint(silverDealerAddAddressConstraint);

		privilegeDAO.insert(silverDealerAddAddressPrivilege);

		// Gold ARCHITECT Add address privilege
		Action goldArchAddAddressAction = actionDAO.findByName("Add");

		Privilege goldArchAddAddressPrivilege = new Privilege();
		goldArchAddAddressPrivilege
				.setName("GOLD ARCHITECT PRIVILEGE TO ADD ADDRESS");

		Description goldArchAddAddressDescription = new Description("eng",
				"Privilege to add addresses");
		goldArchAddAddressPrivilege.setDesc(goldArchAddAddressDescription);

		Resource goldArchAddAddressResource = new Resource();
		goldArchAddAddressResource.setName("Addresses");

		Constraint goldArchAddAddressConstraint = new Constraint();
		goldArchAddAddressConstraint.setValue("2");

		goldArchAddAddressPrivilege.setAction(goldArchAddAddressAction);
		goldArchAddAddressPrivilege.setResource(goldArchAddAddressResource);
		goldArchAddAddressPrivilege.setConstraint(goldArchAddAddressConstraint);

		privilegeDAO.insert(goldArchAddAddressPrivilege);

		// Gold INTERIORDESINGER Add address privilege
		Action goldIDAddAddressAction = actionDAO.findByName("Add");

		Privilege goldIDAddAddressPrivilege = new Privilege();
		goldIDAddAddressPrivilege
				.setName("GOLD INTERIORDESIGNER PRIVILEGE TO ADD ADDRESS");

		Description goldIDAddAddressDescription = new Description("eng",
				"Privilege to add addresses");
		goldIDAddAddressPrivilege.setDesc(goldIDAddAddressDescription);

		Resource goldIDAddAddressResource = new Resource();
		goldIDAddAddressResource.setName("Addresses");

		Constraint goldIDAddAddressConstraint = new Constraint();
		goldIDAddAddressConstraint.setValue("2");

		goldIDAddAddressPrivilege.setAction(goldIDAddAddressAction);
		goldIDAddAddressPrivilege.setResource(goldIDAddAddressResource);
		goldIDAddAddressPrivilege.setConstraint(goldIDAddAddressConstraint);

		privilegeDAO.insert(goldIDAddAddressPrivilege);

		// Gold PROVIDER Add address privilege
		Action goldProviderAddAddressAction = actionDAO.findByName("Add");

		Privilege goldProviderAddAddressPrivilege = new Privilege();
		goldProviderAddAddressPrivilege
				.setName("GOLD PROVIDER PRIVILEGE TO ADD ADDRESS");

		Description goldProviderAddAddressDescription = new Description("eng",
				"Privilege to add addresses");
		goldProviderAddAddressPrivilege
				.setDesc(goldProviderAddAddressDescription);

		Resource goldProviderAddAddressResource = new Resource();
		goldProviderAddAddressResource.setName("Addresses");

		Constraint goldProviderAddAddressConstraint = new Constraint();
		goldProviderAddAddressConstraint.setValue("2");

		goldProviderAddAddressPrivilege.setAction(goldProviderAddAddressAction);
		goldProviderAddAddressPrivilege
				.setResource(goldProviderAddAddressResource);
		goldProviderAddAddressPrivilege
				.setConstraint(goldProviderAddAddressConstraint);

		privilegeDAO.insert(goldProviderAddAddressPrivilege);

		// Gold DEALER Add address privilege
		Action goldDealerAddAddressAction = actionDAO.findByName("Add");

		Privilege goldDealerAddAddressPrivilege = new Privilege();
		goldDealerAddAddressPrivilege
				.setName("GOLD DEALER PRIVILEGE TO ADD ADDRESS");

		Description goldDealerAddAddressDescription = new Description("eng",
				"Privilege to add addresses");
		goldDealerAddAddressPrivilege.setDesc(goldDealerAddAddressDescription);

		Resource goldDealerAddAddressResource = new Resource();
		goldDealerAddAddressResource.setName("Addresses");

		Constraint goldDealerAddAddressConstraint = new Constraint();
		goldDealerAddAddressConstraint.setValue("2");

		goldDealerAddAddressPrivilege.setAction(goldDealerAddAddressAction);
		goldDealerAddAddressPrivilege.setResource(goldDealerAddAddressResource);
		goldDealerAddAddressPrivilege
				.setConstraint(goldDealerAddAddressConstraint);

		privilegeDAO.insert(goldDealerAddAddressPrivilege);

		// Platinum ARCHITECT Add address privilege
		Action platinumArchAddAddressAction = actionDAO.findByName("Add");

		Privilege platinumArchAddAddressPrivilege = new Privilege();
		platinumArchAddAddressPrivilege
				.setName("PLATINUM ARCHITECT PRIVILEGE TO ADD ADDRESS");

		Description platinumArchAddAddressDescription = new Description("eng",
				"Privilege to add addresses");
		platinumArchAddAddressPrivilege
				.setDesc(platinumArchAddAddressDescription);

		Resource platinumArchAddAddressResource = new Resource();
		platinumArchAddAddressResource.setName("Addresses");

		Constraint platinumArchAddAddressConstraint = new Constraint();
		platinumArchAddAddressConstraint.setValue("5");

		platinumArchAddAddressPrivilege.setAction(platinumArchAddAddressAction);
		platinumArchAddAddressPrivilege
				.setResource(platinumArchAddAddressResource);
		platinumArchAddAddressPrivilege
				.setConstraint(platinumArchAddAddressConstraint);

		privilegeDAO.insert(platinumArchAddAddressPrivilege);

		// Platinum INTERIOR DESIGNER Add address privilege
		Action platinumIDAddAddressAction = actionDAO.findByName("Add");

		Privilege platinumIDAddAddressPrivilege = new Privilege();
		platinumIDAddAddressPrivilege
				.setName("PLATINUM INTERIORDESIGNER PRIVILEGE TO ADD ADDRESS");

		Description platinumIDAddAddressDescription = new Description("eng",
				"Privilege to add addresses");
		platinumIDAddAddressPrivilege.setDesc(platinumIDAddAddressDescription);

		Resource platinumIDAddAddressResource = new Resource();
		platinumIDAddAddressResource.setName("Addresses");

		Constraint platinumIDAddAddressConstraint = new Constraint();
		platinumIDAddAddressConstraint.setValue("5");

		platinumIDAddAddressPrivilege.setAction(platinumIDAddAddressAction);
		platinumIDAddAddressPrivilege.setResource(platinumIDAddAddressResource);
		platinumIDAddAddressPrivilege
				.setConstraint(platinumIDAddAddressConstraint);

		privilegeDAO.insert(platinumIDAddAddressPrivilege);

		// Platinum PROVIDER Add address privilege
		Action platinumProviderAddAddressAction = actionDAO.findByName("Add");

		Privilege platinumProviderAddAddressPrivilege = new Privilege();
		platinumProviderAddAddressPrivilege
				.setName("PLATINUM PROVIDER PRIVILEGE TO ADD ADDRESS");

		Description platinumProviderAddAddressDescription = new Description(
				"eng", "Privilege to add addresses");
		platinumProviderAddAddressPrivilege
				.setDesc(platinumProviderAddAddressDescription);

		Resource platinumProviderAddAddressResource = new Resource();
		platinumProviderAddAddressResource.setName("Addresses");

		Constraint platinumProviderAddAddressConstraint = new Constraint();
		platinumProviderAddAddressConstraint.setValue("5");

		platinumProviderAddAddressPrivilege
				.setAction(platinumProviderAddAddressAction);
		platinumProviderAddAddressPrivilege
				.setResource(platinumProviderAddAddressResource);
		platinumProviderAddAddressPrivilege
				.setConstraint(platinumProviderAddAddressConstraint);

		privilegeDAO.insert(platinumProviderAddAddressPrivilege);

		// Platinum DEALER Add address privilege
		Action platinumDealerAddAddressAction = actionDAO.findByName("Add");

		Privilege platinumDealerAddAddressPrivilege = new Privilege();
		platinumDealerAddAddressPrivilege
				.setName("PLATINUM DEALER PRIVILEGE TO ADD ADDRESS");

		Description platinumDealerAddAddressDescription = new Description(
				"eng", "Privilege to add addresses");
		platinumDealerAddAddressPrivilege
				.setDesc(platinumDealerAddAddressDescription);

		Resource platinumDealerAddAddressResource = new Resource();
		platinumDealerAddAddressResource.setName("Addresses");

		Constraint platinumDealerAddAddressConstraint = new Constraint();
		platinumDealerAddAddressConstraint.setValue("5");

		platinumDealerAddAddressPrivilege
				.setAction(platinumDealerAddAddressAction);
		platinumDealerAddAddressPrivilege
				.setResource(platinumDealerAddAddressResource);
		platinumDealerAddAddressPrivilege
				.setConstraint(platinumDealerAddAddressConstraint);

		privilegeDAO.insert(platinumDealerAddAddressPrivilege);

		/****** Privilege to add products *******/
		// Silver PROVIDER Add Product Privilege
		Action silverProviderAddProductAction = actionDAO.findByName("Add");

		Privilege silverProviderAddProductPrivilege = new Privilege();
		silverProviderAddProductPrivilege
				.setName("SILVER PROVIDER PRIVILEGE TO ADD PRODUCT");

		Description silverProviderAddProductDesc = new Description("eng",
				"Privilege to add products");
		silverProviderAddProductPrivilege.setDesc(silverProviderAddProductDesc);

		Resource silverProviderAddProductResource = new Resource();
		silverProviderAddProductResource.setName("Products");

		Constraint silverProviderAddProductConstraint = new Constraint();
		silverProviderAddProductConstraint.setValue("10");

		silverProviderAddProductPrivilege
				.setAction(silverProviderAddProductAction);
		silverProviderAddProductPrivilege
				.setResource(silverProviderAddProductResource);
		silverProviderAddProductPrivilege
				.setConstraint(silverProviderAddProductConstraint);

		privilegeDAO.insert(silverProviderAddProductPrivilege);

		// Gold PROVIDER Add Product Privilege
		Action goldProviderAddProductAction = actionDAO.findByName("Add");

		Privilege goldProviderAddProductPrivilege = new Privilege();
		goldProviderAddProductPrivilege
				.setName("GOLD PROVIDER PRIVILEGE TO ADD PRODUCT");

		Description goldProviderAddProductDesc = new Description("eng",
				"Privilege to add products");
		goldProviderAddProductPrivilege.setDesc(goldProviderAddProductDesc);

		Resource goldProviderAddProductResource = new Resource();
		goldProviderAddProductResource.setName("Products");

		Constraint goldProviderAddProductConstraint = new Constraint();
		goldProviderAddProductConstraint.setValue("20");

		goldProviderAddProductPrivilege.setAction(goldProviderAddProductAction);
		goldProviderAddProductPrivilege
				.setResource(goldProviderAddProductResource);
		goldProviderAddProductPrivilege
				.setConstraint(goldProviderAddProductConstraint);

		privilegeDAO.insert(goldProviderAddProductPrivilege);

		// Platinum PROVIDER add product privilege
		Action platinumProviderAddProductAction = actionDAO.findByName("Add");

		Privilege platinumProviderAddProductPrivilege = new Privilege();
		platinumProviderAddProductPrivilege
				.setName("PLATINUM PRIVILEGE TO ADD PRODUCT");

		Description platinumProviderAddProductDesc = new Description("eng",
				"Privilege to add products");
		platinumProviderAddProductPrivilege
				.setDesc(platinumProviderAddProductDesc);

		Resource platinumProviderAddResource = new Resource();
		platinumProviderAddResource.setName("Products");

		Constraint platinumProviderAddProductConstraint = new Constraint();
		platinumProviderAddProductConstraint.setValue("200");

		platinumProviderAddProductPrivilege
				.setAction(platinumProviderAddProductAction);
		platinumProviderAddProductPrivilege
				.setResource(platinumProviderAddResource);
		platinumProviderAddProductPrivilege
				.setConstraint(platinumProviderAddProductConstraint);

		privilegeDAO.insert(platinumProviderAddProductPrivilege);

		// Silver DEALER Add Product Privilege
		Action silverDealerAddProductAction = actionDAO.findByName("Add");

		Privilege silverDealerAddProductPrivilege = new Privilege();
		silverDealerAddProductPrivilege
				.setName("SILVER DEALER PRIVILEGE TO ADD PRODUCT");

		Description silverDealerAddProductDesc = new Description("eng",
				"Privilege to add products");
		silverDealerAddProductPrivilege.setDesc(silverDealerAddProductDesc);

		Resource silverDealerAddProductResource = new Resource();
		silverDealerAddProductResource.setName("Products");

		Constraint silverDealerAddProductConstraint = new Constraint();
		silverDealerAddProductConstraint.setValue("10");

		silverDealerAddProductPrivilege.setAction(silverDealerAddProductAction);
		silverDealerAddProductPrivilege
				.setResource(silverDealerAddProductResource);
		silverDealerAddProductPrivilege
				.setConstraint(silverDealerAddProductConstraint);

		privilegeDAO.insert(silverDealerAddProductPrivilege);

		// Gold DEALER Add Product Privilege
		Action goldDealerAddProductAction = actionDAO.findByName("Add");

		Privilege goldDealerAddProductPrivilege = new Privilege();
		goldDealerAddProductPrivilege
				.setName("GOLD DEALER PRIVILEGE TO ADD PRODUCT");

		Description goldDealerAddProductDesc = new Description("eng",
				"Privilege to add products");
		goldDealerAddProductPrivilege.setDesc(goldDealerAddProductDesc);

		Resource goldDealerAddProductResource = new Resource();
		goldDealerAddProductResource.setName("Products");

		Constraint goldDealerAddProductConstraint = new Constraint();
		goldDealerAddProductConstraint.setValue("20");

		goldDealerAddProductPrivilege.setAction(goldDealerAddProductAction);
		goldDealerAddProductPrivilege.setResource(goldDealerAddProductResource);
		goldDealerAddProductPrivilege
				.setConstraint(goldDealerAddProductConstraint);

		privilegeDAO.insert(goldDealerAddProductPrivilege);

		// Platinum DEALER add product privilege
		Action platinumDealerAddProductAction = actionDAO.findByName("Add");

		Privilege platinumDealerAddProductPrivilege = new Privilege();
		platinumDealerAddProductPrivilege
				.setName("PLATINUM DEALER PRIVILEGE TO ADD PRODUCT");

		Description platinumDealerAddProductDesc = new Description("eng",
				"Privilege to add products");
		platinumDealerAddProductPrivilege.setDesc(platinumDealerAddProductDesc);

		Resource platinumDealerAddResource = new Resource();
		platinumDealerAddResource.setName("Products");

		Constraint platinumDealerAddProductConstraint = new Constraint();
		platinumDealerAddProductConstraint.setValue("200");

		platinumDealerAddProductPrivilege
				.setAction(platinumDealerAddProductAction);
		platinumDealerAddProductPrivilege
				.setResource(platinumDealerAddResource);
		platinumDealerAddProductPrivilege
				.setConstraint(platinumDealerAddProductConstraint);

		privilegeDAO.insert(platinumDealerAddProductPrivilege);

		// Privilege to add projects
		// Silver Architect Add Project Privilege
		Action silverArchAddProjectAction = actionDAO.findByName("Add");

		Privilege silverArchAddProjectPrivilege = new Privilege();
		silverArchAddProjectPrivilege
				.setName("SILVER ARCHITECT PRIVILEGE TO ADD PROFILE");

		Description silverArchAddProjectDesc = new Description("eng",
				"Privilege to add projects");
		silverArchAddProjectPrivilege.setDesc(silverArchAddProjectDesc);

		Resource silverArchAddProjectResource = new Resource();
		silverArchAddProjectResource.setName("projects");

		Constraint silverAcrhAddProjectConstraint = new Constraint();
		silverAcrhAddProjectConstraint.setValue("10");

		silverArchAddProjectPrivilege.setAction(silverArchAddProjectAction);
		silverArchAddProjectPrivilege.setResource(silverArchAddProjectResource);
		silverArchAddProjectPrivilege
				.setConstraint(silverAcrhAddProjectConstraint);

		privilegeDAO.insert(silverArchAddProjectPrivilege);

		// Gold Architect project Privilege
		Action goldArchAddProjectAction = actionDAO.findByName("Add");

		Privilege goldArchAddProjectPrivilege = new Privilege();
		goldArchAddProjectPrivilege.setName("GOLD ARCHITECT PRIVILEGE TO ADD PROFILE");

		Description goldArchAddProjectDesc = new Description("eng",
				"Privilege to add projects");
		goldArchAddProjectPrivilege.setDesc(goldArchAddProjectDesc);

		Resource goldArchAddProjectResource = new Resource();
		goldArchAddProjectResource.setName("projects");

		Constraint goldArchAddProjectConstraint = new Constraint();
		goldArchAddProjectConstraint.setValue("20");

		goldArchAddProjectPrivilege.setAction(goldArchAddProjectAction);
		goldArchAddProjectPrivilege.setResource(goldArchAddProjectResource);
		goldArchAddProjectPrivilege.setConstraint(goldArchAddProjectConstraint);

		privilegeDAO.insert(goldArchAddProjectPrivilege);

		// Platinum Architect project privilege
		Action platinumArchAddProjectAction = actionDAO.findByName("Add");

		Privilege platinumArchAddProjectPrivilege = new Privilege();
		platinumArchAddProjectPrivilege
				.setName("PLATINUM ARCHITECT PRIVILEGE TO ADD PROFILE");

		Description platinumArchAddProjectDesc = new Description("eng",
				"Privilege to add projects");
		platinumArchAddProjectPrivilege.setDesc(platinumArchAddProjectDesc);

		Resource platinumArchAddProjectResource = new Resource();
		platinumArchAddProjectResource.setName("projects");

		Constraint platinumArchAddProjectConstraint = new Constraint();
		platinumArchAddProjectConstraint.setValue("200");

		platinumArchAddProjectPrivilege.setAction(platinumArchAddProjectAction);
		platinumArchAddProjectPrivilege
				.setResource(platinumArchAddProjectResource);
		platinumArchAddProjectPrivilege
				.setConstraint(platinumArchAddProjectConstraint);

		privilegeDAO.insert(platinumArchAddProjectPrivilege);

		// Privilege to add projects
		// Silver Interior Designer Add Project Privilege
		Action silverIDAddProjectAction = actionDAO.findByName("Add");

		Privilege silverIDAddProjectPrivilege = new Privilege();
		silverIDAddProjectPrivilege
				.setName("SILVER INTERIORDESGINER PRIVILEGE TO ADD PROFILE");

		Description silverIDAddProjectDesc = new Description("eng",
				"Privilege to add projects");
		silverIDAddProjectPrivilege.setDesc(silverIDAddProjectDesc);

		Resource silverIDAddProjectResource = new Resource();
		silverIDAddProjectResource.setName("projects");

		Constraint silverIDAddProjectConstraint = new Constraint();
		silverIDAddProjectConstraint.setValue("10");

		silverIDAddProjectPrivilege.setAction(silverIDAddProjectAction);
		silverIDAddProjectPrivilege.setResource(silverIDAddProjectResource);
		silverIDAddProjectPrivilege.setConstraint(silverIDAddProjectConstraint);

		privilegeDAO.insert(silverIDAddProjectPrivilege);

		// Gold Interior Designer Add project Privilege
		Action goldIDAddProjectAction = actionDAO.findByName("Add");

		Privilege goldIDAddProjectPrivilege = new Privilege();
		goldIDAddProjectPrivilege
				.setName("GOLD INTERIORDESIGNER PRIVILEGE TO ADD PROFILE");

		Description goldIDAddProjectDesc = new Description("eng",
				"Privilege to add projects");
		goldIDAddProjectPrivilege.setDesc(goldIDAddProjectDesc);

		Resource goldIDAddProjectResource = new Resource();
		goldIDAddProjectResource.setName("projects");

		Constraint goldIDAddProjectConstraint = new Constraint();
		goldIDAddProjectConstraint.setValue("20");

		goldIDAddProjectPrivilege.setAction(goldIDAddProjectAction);
		goldIDAddProjectPrivilege.setResource(goldIDAddProjectResource);
		goldIDAddProjectPrivilege.setConstraint(goldIDAddProjectConstraint);

		privilegeDAO.insert(goldIDAddProjectPrivilege);

		// Platinum Interior Designer Add project privilege
		Action platinumIDAddProjectAction = actionDAO.findByName("Add");

		Privilege platinumIDAddProjectPrivilege = new Privilege();
		platinumIDAddProjectPrivilege
				.setName("PLATINUM INTERIORDESIGNER PRIVILEGE TO ADD PROFILE");

		Description platinumIDAddProjectDesc = new Description("eng",
				"Privilege to add projects");
		platinumIDAddProjectPrivilege.setDesc(platinumIDAddProjectDesc);

		Resource platinumIDAddProjectResource = new Resource();
		platinumIDAddProjectResource.setName("projects");

		Constraint platinumIDAddProjectConstraint = new Constraint();
		platinumIDAddProjectConstraint.setValue("200");

		platinumIDAddProjectPrivilege.setAction(platinumIDAddProjectAction);
		platinumIDAddProjectPrivilege.setResource(platinumIDAddProjectResource);
		platinumIDAddProjectPrivilege
				.setConstraint(platinumIDAddProjectConstraint);

		privilegeDAO.insert(platinumIDAddProjectPrivilege);

		// Bid Project
		// Silver Architect Bid project privilege
		Action silverArchBidProjectAction = actionDAO.findByName("Bid");

		Privilege silverArchBidProjectPrivilege = new Privilege();
		silverArchBidProjectPrivilege
				.setName("SILVER ARCHITECT PRIVILEGE TO BID PROJECT");

		Description silverArchBidProjectDescription = new Description("eng",
				"Privilege to Bid for Project");
		silverArchBidProjectPrivilege.setDesc(silverArchBidProjectDescription);

		Resource silverArchBidProjectResource = new Resource();
		silverArchBidProjectResource.setName("Project");

		Constraint silverArchBidProjectConstraint = new Constraint();
		silverArchBidProjectConstraint.setValue("10");

		silverArchBidProjectPrivilege.setAction(silverArchBidProjectAction);
		silverArchBidProjectPrivilege.setResource(silverArchBidProjectResource);
		silverArchBidProjectPrivilege
				.setConstraint(silverArchBidProjectConstraint);

		privilegeDAO.insert(silverArchBidProjectPrivilege);

		// Gold Architect Bid project privilege
		Action goldArchBidProjAction = actionDAO.findByName("Bid");

		Privilege goldArchBidProjectPrivilege = new Privilege();
		goldArchBidProjectPrivilege
				.setName("GOLD ARCHITECT PRIVILEGE TO BID PROJECT");

		Description goldArchBidProjectDescription = new Description("eng",
				"Privilege to Bid for Project");
		goldArchBidProjectPrivilege.setDesc(goldArchBidProjectDescription);

		Resource goldArchBidProjectResource = new Resource();
		goldArchBidProjectResource.setName("Project");

		Constraint goldArchBidProjectConstraint = new Constraint();
		goldArchBidProjectConstraint.setValue("20");

		goldArchBidProjectPrivilege.setAction(goldArchBidProjAction);
		goldArchBidProjectPrivilege.setResource(goldArchBidProjectResource);
		goldArchBidProjectPrivilege.setConstraint(goldArchBidProjectConstraint);

		privilegeDAO.insert(goldArchBidProjectPrivilege);

		// Platinum Architect Bid project privilege
		Action platinumArchBidAction = actionDAO.findByName("Bid");

		Privilege platinumArchBidProjectPrivilege = new Privilege();
		platinumArchBidProjectPrivilege
				.setName("PLATINUM PRIVILEGE TO BID PROJECT");

		Description platinumArchBidProjectDescription = new Description("eng",
				"Privilege to Bid for Project");
		platinumArchBidProjectPrivilege
				.setDesc(platinumArchBidProjectDescription);

		Resource platinumArchBidProjectResource = new Resource();
		platinumArchBidProjectResource.setName("Project");

		Constraint platinumArchBidProjectConstraint = new Constraint();
		platinumArchBidProjectConstraint.setValue("200");

		platinumArchBidProjectPrivilege.setAction(platinumArchBidAction);
		platinumArchBidProjectPrivilege
				.setResource(platinumArchBidProjectResource);
		platinumArchBidProjectPrivilege
				.setConstraint(platinumArchBidProjectConstraint);

		privilegeDAO.insert(platinumArchBidProjectPrivilege);

		// Silver Interior Designer Bid project privilege
		Action silverIDBidProjectAction = actionDAO.findByName("Bid");

		Privilege silverIDBidProjectPrivilege = new Privilege();
		silverIDBidProjectPrivilege
				.setName("SILVER INTERIORDESINGER PRIVILEGE TO BID PROJECT");

		Description silverIDBidProjectDescription = new Description("eng",
				"Privilege to Bid for Project");
		silverIDBidProjectPrivilege.setDesc(silverIDBidProjectDescription);

		Resource silverIDBidProjectResource = new Resource();
		silverIDBidProjectResource.setName("Project");

		Constraint silverIDBidProjectConstraint = new Constraint();
		silverIDBidProjectConstraint.setValue("10");

		silverIDBidProjectPrivilege.setAction(silverIDBidProjectAction);
		silverIDBidProjectPrivilege.setResource(silverIDBidProjectResource);
		silverIDBidProjectPrivilege.setConstraint(silverIDBidProjectConstraint);

		privilegeDAO.insert(silverIDBidProjectPrivilege);

		// Gold Interior Designer Bid project privilege
		Action goldIDBidProjAction = actionDAO.findByName("Bid");

		Privilege goldIDBidProjectPrivilege = new Privilege();
		goldIDBidProjectPrivilege
				.setName("GOLD INTERIORDESIGNER PRIVILEGE TO BID PROJECT");

		Description goldIDBidProjectDescription = new Description("eng",
				"Privilege to Bid for Project");
		goldIDBidProjectPrivilege.setDesc(goldIDBidProjectDescription);

		Resource goldIDBidProjectResource = new Resource();
		goldIDBidProjectResource.setName("Project");

		Constraint goldIDBidProjectConstraint = new Constraint();
		goldIDBidProjectConstraint.setValue("20");

		goldIDBidProjectPrivilege.setAction(goldIDBidProjAction);
		goldIDBidProjectPrivilege.setResource(goldIDBidProjectResource);
		goldIDBidProjectPrivilege.setConstraint(goldIDBidProjectConstraint);

		privilegeDAO.insert(goldIDBidProjectPrivilege);

		// Platinum Interior Designer Bid project privilege
		Action platinumIDBidAction = actionDAO.findByName("Bid");

		Privilege platinumIDBidProjectPrivilege = new Privilege();
		platinumIDBidProjectPrivilege
				.setName("PLATINUM INTERIORDESIGNER PRIVILEGE TO BID PROJECT");

		Description platinumIDBidProjectDescription = new Description("eng",
				"Privilege to Bid for Project");
		platinumIDBidProjectPrivilege.setDesc(platinumIDBidProjectDescription);

		Resource platinumIDBidProjectResource = new Resource();
		platinumIDBidProjectResource.setName("Project");

		Constraint platinumIDBidProjectConstraint = new Constraint();
		platinumIDBidProjectConstraint.setValue("200");

		platinumIDBidProjectPrivilege.setAction(platinumIDBidAction);
		platinumIDBidProjectPrivilege.setResource(platinumIDBidProjectResource);
		platinumIDBidProjectPrivilege
				.setConstraint(platinumIDBidProjectConstraint);

		privilegeDAO.insert(platinumIDBidProjectPrivilege);

		// Silver Dealer Bid project privilege
		Action silverDealerBidProjectAction = actionDAO.findByName("Bid");

		Privilege silverDealerBidProjectPrivilege = new Privilege();
		silverDealerBidProjectPrivilege
				.setName("SILVER DEALER PRIVILEGE TO BID PROJECT");

		Description silverDealerBidProjectDescription = new Description("eng",
				"Privilege to Bid for Project");
		silverDealerBidProjectPrivilege
				.setDesc(silverDealerBidProjectDescription);

		Resource silverDealerBidProjectResource = new Resource();
		silverDealerBidProjectResource.setName("Project");

		Constraint silverDealerBidProjectConstraint = new Constraint();
		silverDealerBidProjectConstraint.setValue("10");

		silverDealerBidProjectPrivilege.setAction(silverDealerBidProjectAction);
		silverDealerBidProjectPrivilege
				.setResource(silverDealerBidProjectResource);
		silverDealerBidProjectPrivilege
				.setConstraint(silverDealerBidProjectConstraint);

		privilegeDAO.insert(silverDealerBidProjectPrivilege);

		// Gold Dealer Bid project privilege
		Action goldDealerBidProjAction = actionDAO.findByName("Bid");

		Privilege goldDealerDBidProjectPrivilege = new Privilege();
		goldDealerDBidProjectPrivilege
				.setName("GOLD DEALER PRIVILEGE TO BID PROJECT");

		Description goldDealerBidProjectDescription = new Description("eng",
				"Privilege to Bid for Project");
		goldDealerDBidProjectPrivilege.setDesc(goldDealerBidProjectDescription);

		Resource goldDealerBidProjectResource = new Resource();
		goldDealerBidProjectResource.setName("Project");

		Constraint goldDealerBidProjectConstraint = new Constraint();
		goldDealerBidProjectConstraint.setValue("20");

		goldDealerDBidProjectPrivilege.setAction(goldDealerBidProjAction);
		goldDealerDBidProjectPrivilege
				.setResource(goldDealerBidProjectResource);
		goldDealerDBidProjectPrivilege
				.setConstraint(goldDealerBidProjectConstraint);

		privilegeDAO.insert(goldDealerDBidProjectPrivilege);

		// Platinum Dealer Bid project privilege
		Action platinumDealerBidProjAction = actionDAO.findByName("Bid");

		Privilege platinumDealerBidProjectPrivilege = new Privilege();
		platinumDealerBidProjectPrivilege
				.setName("PLATINUM DEALER PRIVILEGE TO BID PROJECT");

		Description platinumDealerBidProjectDescription = new Description(
				"eng", "Privilege to Bid for Project");
		platinumDealerBidProjectPrivilege
				.setDesc(platinumDealerBidProjectDescription);

		Resource platinumDealerBidProjectResource = new Resource();
		platinumDealerBidProjectResource.setName("Project");

		Constraint platinumDealerBidProjectConstraint = new Constraint();
		platinumDealerBidProjectConstraint.setValue("200");

		platinumDealerBidProjectPrivilege
				.setAction(platinumDealerBidProjAction);
		platinumDealerBidProjectPrivilege
				.setResource(platinumDealerBidProjectResource);
		platinumDealerBidProjectPrivilege
				.setConstraint(platinumDealerBidProjectConstraint);

		privilegeDAO.insert(platinumDealerBidProjectPrivilege);

		// Privilege to Post project
		// Silver Architect Post project privilege
		Action silverArchPostProjectAction = actionDAO.findByName("Post");

		Privilege silverArchPostProjectPrivilege = new Privilege();
		silverArchPostProjectPrivilege
				.setName("SILVER ARCHITECT PRIVILEGE TO POST PROJECT");

		Description silverArchPostProjectDescription = new Description("eng",
				"Privilege to Post Project");
		silverArchPostProjectPrivilege
				.setDesc(silverArchPostProjectDescription);

		Resource silverArchPostProjectResource = new Resource();
		silverArchPostProjectResource.setName("Project");

		Constraint silverArchPostProjectConstraint = new Constraint();
		silverArchPostProjectConstraint.setValue("1");

		silverArchPostProjectPrivilege.setAction(silverArchPostProjectAction);
		silverArchPostProjectPrivilege
				.setResource(silverArchPostProjectResource);
		silverArchPostProjectPrivilege
				.setConstraint(silverArchPostProjectConstraint);

		privilegeDAO.insert(silverArchPostProjectPrivilege);

		// Gold Architect Post project privilege
		Action goldArchPostProjAction = actionDAO.findByName("Post");

		Privilege goldArchPostProjectPrivilege = new Privilege();
		goldArchPostProjectPrivilege
				.setName("GOLD ARCHITECT PRIVILEGE TO POST PROJECT");

		Description goldArchPostProjectDescription = new Description("eng",
				"Privilege to Post Project");
		goldArchPostProjectPrivilege.setDesc(goldArchPostProjectDescription);

		Resource goldArchPostProjectResource = new Resource();
		goldArchPostProjectResource.setName("Project");

		Constraint goldArchPostProjectConstraint = new Constraint();
		goldArchPostProjectConstraint.setValue("5");

		goldArchPostProjectPrivilege.setAction(goldArchPostProjAction);
		goldArchPostProjectPrivilege.setResource(goldArchPostProjectResource);
		goldArchPostProjectPrivilege
				.setConstraint(goldArchPostProjectConstraint);

		privilegeDAO.insert(goldArchPostProjectPrivilege);

		// Platinum Architect Post project privilege
		Action platinumArchPostProjAction = actionDAO.findByName("POST");

		Privilege platinumArchPostProjectPrivilege = new Privilege();
		platinumArchPostProjectPrivilege
				.setName("PLATINUM ARCHITECT PRIVILEGE TO POST PROJECT");

		Description platinumArchPostProjectDescription = new Description("eng",
				"Privilege to Post Project");
		platinumArchPostProjectPrivilege
				.setDesc(platinumArchPostProjectDescription);

		Resource platinumArchPostProjectResource = new Resource();
		platinumArchPostProjectResource.setName("Project");

		Constraint platinumArchPostProjectConstraint = new Constraint();
		platinumArchPostProjectConstraint.setValue("200");

		platinumArchPostProjectPrivilege.setAction(platinumArchPostProjAction);
		platinumArchPostProjectPrivilege
				.setResource(platinumArchPostProjectResource);
		platinumArchPostProjectPrivilege
				.setConstraint(platinumArchPostProjectConstraint);

		privilegeDAO.insert(platinumArchPostProjectPrivilege);

		// Privilege to Post project
		// Silver Interior Designer Post project privilege
		Action silverIDPostProjectAction = actionDAO.findByName("Post");

		Privilege silverIDPostProjectPrivilege = new Privilege();
		silverIDPostProjectPrivilege
				.setName("SILVER INTERIORDESIGNER PRIVILEGE TO POST PROJECT");

		Description silverIDPostProjectDescription = new Description("eng",
				"Privilege to Post Project");
		silverIDPostProjectPrivilege
				.setDesc(silverIDPostProjectDescription);

		Resource silverIDPostProjectResource = new Resource();
		silverIDPostProjectResource.setName("Project");

		Constraint silverIDPostProjectConstraint = new Constraint();
		silverIDPostProjectConstraint.setValue("1");

		silverIDPostProjectPrivilege.setAction(silverIDPostProjectAction);
		silverIDPostProjectPrivilege
				.setResource(silverIDPostProjectResource);
		silverIDPostProjectPrivilege
				.setConstraint(silverIDPostProjectConstraint);

		privilegeDAO.insert(silverIDPostProjectPrivilege);

		// Gold Interior Designer Post project privilege
		Action goldIDPostProjAction = actionDAO.findByName("Post");

		Privilege goldIDPostProjectPrivilege = new Privilege();
		goldIDPostProjectPrivilege
				.setName("GOLD INTERIORDESIGNER PRIVILEGE TO POST PROJECT");

		Description goldIDPostProjectDescription = new Description("eng",
				"Privilege to Post Project");
		goldIDPostProjectPrivilege.setDesc(goldIDPostProjectDescription);

		Resource goldIDPostProjectResource = new Resource();
		goldIDPostProjectResource.setName("Project");

		Constraint goldIDPostProjectConstraint = new Constraint();
		goldIDPostProjectConstraint.setValue("5");

		goldIDPostProjectPrivilege.setAction(goldIDPostProjAction);
		goldIDPostProjectPrivilege.setResource(goldIDPostProjectResource);
		goldIDPostProjectPrivilege
				.setConstraint(goldIDPostProjectConstraint);

		privilegeDAO.insert(goldIDPostProjectPrivilege);

		// Platinum Interior Desginer Post project privilege
		Action platinumIDPostProjAction = actionDAO.findByName("POST");

		Privilege platinumIDPostProjectPrivilege = new Privilege();
		platinumIDPostProjectPrivilege
				.setName("PLATINUM INTERIORDESIGNER PRIVILEGE TO POST PROJECT");

		Description platinumIDPostProjectDescription = new Description("eng",
				"Privilege to Post Project");
		platinumIDPostProjectPrivilege
				.setDesc(platinumIDPostProjectDescription);

		Resource platinumIDPostProjectResource = new Resource();
		platinumIDPostProjectResource.setName("Project");

		Constraint platinumIDPostProjectConstraint = new Constraint();
		platinumIDPostProjectConstraint.setValue("200");

		platinumIDPostProjectPrivilege.setAction(platinumIDPostProjAction);
		platinumIDPostProjectPrivilege
				.setResource(platinumIDPostProjectResource);
		platinumIDPostProjectPrivilege
				.setConstraint(platinumIDPostProjectConstraint);

		privilegeDAO.insert(platinumIDPostProjectPrivilege);

		// Add Promotions
		Action silverAddPromotionAction = actionDAO.findByName("Add");

		Privilege silverAddPromotionPrivilege = new Privilege();
		silverAddPromotionPrivilege
				.setName("SILVER PRIVILEGE TO ADD PROMOTION");

		Description silverAddPromotionDescription = new Description("eng",
				"Privilege to Add Promotion");
		silverAddPromotionPrivilege.setDesc(silverAddPromotionDescription);

		Resource silverAddPromotionResource = new Resource();
		silverAddPromotionResource.setName("Promotion");

		Constraint silverAddPromotionConstraint = new Constraint();
		silverAddPromotionConstraint.setValue("1");

		silverAddPromotionPrivilege.setAction(silverAddPromotionAction);
		silverAddPromotionPrivilege.setResource(silverAddPromotionResource);
		silverAddPromotionPrivilege.setConstraint(silverAddPromotionConstraint);

		privilegeDAO.insert(silverAddPromotionPrivilege);

		Action goldAddPromotionAction = actionDAO.findByName("Add");

		Privilege goldAddPromotionPrivilege = new Privilege();
		goldAddPromotionPrivilege.setName("GOLD PRIVILEGE TO ADD PROMOTION");

		Description goldAddPromotionDescription = new Description("eng",
				"Privilege to Add Promotion");
		goldAddPromotionPrivilege.setDesc(goldAddPromotionDescription);

		Resource goldAddPromotionResource = new Resource();
		goldAddPromotionResource.setName("Promotion");

		Constraint goldAddPromotionConstraint = new Constraint();
		goldAddPromotionConstraint.setValue("3");

		goldAddPromotionPrivilege.setAction(goldAddPromotionAction);
		goldAddPromotionPrivilege.setResource(goldAddPromotionResource);
		goldAddPromotionPrivilege.setConstraint(goldAddPromotionConstraint);

		privilegeDAO.insert(goldAddPromotionPrivilege);

		Action platinumAddPromotionAction = actionDAO.findByName("Add");

		Privilege platinumAddPromotionPrivilege = new Privilege();
		platinumAddPromotionPrivilege
				.setName("PLATINUM PRIVILEGE TO ADD PROMOTION");

		Description platinumAddPromotionDescription = new Description("eng",
				"Privilege to Add Promotion");
		platinumAddPromotionPrivilege.setDesc(platinumAddPromotionDescription);

		Resource platinumAddPromotionResource = new Resource();
		platinumAddPromotionResource.setName("Promotion");

		Constraint platinumAddPromotionConstraint = new Constraint();
		platinumAddPromotionConstraint.setValue("200");

		platinumAddPromotionPrivilege.setAction(platinumAddPromotionAction);
		platinumAddPromotionPrivilege.setResource(platinumAddPromotionResource);
		platinumAddPromotionPrivilege
				.setConstraint(platinumAddPromotionConstraint);

		privilegeDAO.insert(platinumAddPromotionPrivilege);
	}
}
