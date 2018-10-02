package com.sarvah.mbg.project.service;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.springframework.data.geo.Point;

import com.sarvah.mbg.domain.mongo.aceproject.Bid;
import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.project.model.BidCountResponse;
import com.sarvah.mbg.project.model.BidDescriptionResponse;
import com.sarvah.mbg.project.model.BudgetResponse;
import com.sarvah.mbg.project.model.ProjectDescriptionResponse;

public interface ProjectService {

	/**
	 * Method to get all projects
	 * 
	 * @param name
	 * @param budget
	 * @param constructionType
	 * @param projectType
	 * @param status
	 * @param addressLine1
	 * @param addressLine2
	 * @param city
	 * @param state
	 * @param country
	 * @param zipcode
	 * @param email
	 * @param phoneType
	 * @param point
	 * @return
	 * @throws Exception
	 */
	Set<ProjectDescriptionResponse> getSystemProject(String name,
			String budget, String constructionType, String projectType,
			String status, String addressLine1, String addressLine2,
			String city, String state, String country, String zipcode,
			String email, String phoneType, String roleName, Point point)
			throws Exception;

	/**
	 * Method to get available projects to bid
	 * 
	 * @param budget
	 * @param constructionType
	 * @param budget2
	 * 
	 * @return
	 * @throws Exception
	 */
	List<ProjectDescriptionResponse> getAvailableProjects(String roleName)
			throws Exception;

	List<ProjectDescriptionResponse> getAdminManageProjects(String roleName)
			throws Exception;

	/**
	 * Method to get project by projectID
	 * 
	 * @Pathparam would be the projectID,by which the project details will
	 *            displayed as the result
	 * @param projid
	 * @return
	 * @throws MBGAppException
	 */
	Project getProjectById(String projid) throws Exception;

	/**
	 * Method for delete system level project based on project id. project id is
	 * necessary field
	 * 
	 * @param pId
	 * @return
	 * @throws MBGAppException
	 */
	Project deleteSystemProject(String projId) throws Exception;

	/**
	 * Method for get one or more number bids for project, project id is
	 * necessary field
	 * 
	 * @param roleName
	 * 
	 * @param pId
	 * @return
	 * @throws MBGAppException
	 */
	List<BidDescriptionResponse> getBidsForProject(String projectId,
			String roleName) throws Exception;

	/**
	 * Method to Count project bids by projectId
	 * 
	 * @Pathparam would be the projectId
	 * 
	 * @param projid
	 * @return
	 * @throws MBGAppException
	 */
	long countProjectBidsById(String projid) throws Exception;

	/**
	 * 
	 * @param projid
	 * @return
	 * @throws Exception
	 */

	BidCountResponse countProjectBidsByProjectAndRole(String projid)
			throws Exception;

	/**
	 * Method to create bid for a project
	 * 
	 * @param projid
	 * @param bidCreateRequestParam
	 * @return
	 * @throws MBGAppException
	 */
	Bid createBid(String userId, String projid, String desc, String assetId,
			String quoteAmt) throws Exception;

	/**
	 * Method for update bid for project, projectId and bidId is necessary
	 * field.
	 * 
	 * @param projId
	 * @param bidId
	 * @param bidUpdateRequestParam
	 * @return
	 * @throws MBGAppException
	 */
	Bid updateBidForProject(String projId, String bidId, String desc,
			String quoteAmount) throws Exception;

	/**
	 * Method for delete bid for Project, projectId and bidId is necessary
	 * field.
	 * 
	 * @param projId
	 * @param bidId
	 * @return
	 * @throws MBGAppException
	 */
	String deleteBidForProject(String projId, String bidId) throws Exception;

	// File and Image upload
	String uploadFile(String pid, String fileName, String fileTypeValue,
			InputStream fileInputStream, long contentLength) throws Exception;

	/**
	 * method to get the minimum and maximum budget from all projects
	 * 
	 * @return
	 */
	BudgetResponse getMinMaxBudget();

	List<ProjectDescriptionResponse> getAvailableProjectsToBid(String userId,
			String roleName);
}
