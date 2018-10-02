/**
 * 
 */
package com.sarvah.mbg.userprofile.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;

import com.sarvah.mbg.domain.mongo.aceproject.LeadProject;
import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.userprofile.ordermgmt.model.ManageLeadProjectResponse;
import com.sarvah.mbg.userprofile.response.BidResponses;
import com.sarvah.mbg.userprofile.response.ProjectResponse;

/**
 * @author shivu
 *
 */
public interface UserProjectService {
	/**
	 * Method to Get User projects
	 * 
	 * This method returns the list projects available for the particular user
	 * 
	 * @param uid
	 * @param projName
	 * @param type
	 * @param address1
	 * @param address2
	 * @param city
	 * @param zipcode
	 * @param point
	 * @return
	 * @throws Exception
	 */
	List<ProjectResponse> getUserProjects(String uid, String projName,
			String type, String address1, String address2, String city,
			String zipcode, Point point) throws Exception;

	/**
	 * Method to get bid responses for the particular project by either
	 * Architect/Interior Designer
	 * 
	 * @param userId
	 * @param projName
	 * @param userType
	 * @return
	 */
	List<BidResponses> getBidResponses(String userId, String projName,
			String userType);

	/**
	 * Method to get list of all available projects for the logged in
	 * Architect/interior designer
	 * 
	 * @param userId
	 * @return
	 */
	List<Project> getAvailableProjects(String userId);

	/**
	 * Method for count user related projects, we can also count this projects
	 * based on bellow parameters.
	 * 
	 * @param uid
	 * @param name
	 * @param type
	 * @param addrLine1
	 * @param addrLine2
	 * @param city
	 * @return
	 * @throws Exception
	 */
	Long countProjects(String uid, String name, ProjectType type,
			String addrLine1, String addrLine2, String city) throws Exception;

	/**
	 * Method to Post new Project
	 * 
	 * @param uid
	 * @param name
	 * @param desc
	 * @param budget
	 * @param projectType
	 * @param addrId
	 * @param addrLine1
	 * @param addrLine2
	 * @param city
	 * @param state
	 * @param country
	 * @param zipcode
	 * @param zipcode2
	 * @param point
	 * @param email
	 * @param phoneType
	 * @param number
	 * @param status
	 * @param constructionNew
	 * @return
	 * @throws Exception
	 */
	Project createProject(String uid, String name, String desc, String budget,
			String projectType, String userType, String addrId,
			String addrLine1, String addrLine2, String city, String state,
			String country, String zipcode, Point point, String email,
			String phoneType, String number, String status,
			String constructionNew, String asSoonRequired) throws Exception;

	/**
	 * Method to Update User's project details
	 * 
	 * @param uid
	 * @param projid
	 * @param projName
	 * @param projdesc
	 * @param budget
	 * @param type
	 * @param addrLine1
	 * @param addrLine2
	 * @param city
	 * @param state
	 * @param country
	 * @param zipcode
	 * @param email
	 * @param status
	 * @return
	 * @throws Exception
	 */
	Project updateUserProject(String uid, String projid, String projName,
			String projdesc, String budget, String type, String addrLine1,
			String addrLine2, String city, String state, String country,
			String zipcode, String email, String status) throws Exception;

	/**
	 * Method to delete the particular project of the user
	 * 
	 * @param uid
	 * @param projid
	 * @return
	 * @throws Exception
	 */
	String deleteUserProject(String uid, String projid) throws Exception;

	LeadProject createLeadProject(String name, String projectType,
			String addrId, String addrLine1, String addrLine2, String city,
			String state, String country, String zipcode, String area,
			String salesExecutiveId, String ownerId, String projectStage,
			String followupDate, String contactedDate, String note,
			String requirementNote, String referencedBy, String called,
			String reasonForReject, String latVal, String longVal,
			String status, String customerResponse) throws Exception;

	List<LeadProject> manageLeadProjects(String projName, String type,
			String addrLine1, String addrLine2, String city, String zipcode,
			String projectStage, String area, String followupDate,
			String followupDateEntered, String contactedDate,
			String customerName, String salesExecutiveId, String referencedBy,
			String called, String reasonForReject, String requirement,
			String status, String toFollowupDate, String roleName, Point point,
			Sort sort);

	List<LeadProject> getTodayUpdatedLeads();

	ManageLeadProjectResponse getTodayUpdatedLeadsWithPagination(
			String ownersCount, int page, int size, Sort sort);

	List<LeadProject> getTodayTeleAssociateUpdatedLeads(String fieldAssociateId);

	ManageLeadProjectResponse getTodayTeleAssociateUpdatedLeadsWithPage(
			String fieldAssociateId, String ownersCount, int page, int size,
			Sort sort);

	List<LeadProject> manageFieldAssociateLeadProjectsMap(
			String fieldAssociateId, String projName, String type,
			String addrLine1, String addrLine2, String city, String zipcode,
			String projectStage, String area, String followupDate,
			String followupDateEntered, String contactedDate,
			String customerName, String referencedBy, String called,
			String reasonForReject, String requirement, String status,
			String toFollowupDate, String roleName, Point point, Sort sort);

	ManageLeadProjectResponse manageLeadProjects(String searchVal,
			String projName, String type, String addrLine1, String addrLine2,
			String city, String zipcode, String projectStage, String area,
			String followupDate, String followupDateEntered,
			String contactedDate, String customerName, String salesExecutiveId,
			String ownersCount, String referencedBy, String called,
			String reasonForReject, String requirement, String status,
			String toFollowupDate, String roleName, Point point, int page,
			int size, Sort sort);

	ManageLeadProjectResponse manageFieldAssociateLeads(
			String fieldAssociateId, String searchVal, String projName,
			String type, String addrLine1, String addrLine2, String city,
			String zipcode, String projectStage, String area,
			String followupDate, String followupDateEntered,
			String contactedDate, String customerName, String ownersCount,
			String referencedBy, String called, String reasonForReject,
			String requirement, String status, String toFollowupDate,
			String roleName, Point point, int page, int size, Sort sort);

	LeadProject getLeadProjectViewDetails(String leadProjectId);

	LeadProject getFieldAssociateLeadViewDetails(String fieldAssociateId,
			String leadProjectId);

	LeadProject deleteLeadProjectById(String leadProjectId);

	LeadProject updateLeadProjectById(String leadId, String projName,
			String note, String requirementNote, String type, String addrLine1,
			String addrLine2, String city, String state, String country,
			String zipcode, String latVal, String longVal, String projectStage,
			String area, String followupDate, String contactedDate,
			String salesExecutiveId, String referencedBy, String called,
			String reasonForReject, String status, String customerResponse);
}
