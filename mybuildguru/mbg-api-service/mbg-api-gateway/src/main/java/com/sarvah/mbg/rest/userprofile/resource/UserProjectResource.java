/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.resource;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.domain.mongo.aceproject.LeadProject;
import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.rest.exception.MBGAppException;
import com.sarvah.mbg.rest.model.Page;
import com.sarvah.mbg.rest.userprofile.model.LeadResponse;
import com.sarvah.mbg.rest.userprofile.model.ProjectCreateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.ProjectUpdateRequestParam;
import com.sarvah.mbg.userprofile.auth.model.ApiUser;
import com.sarvah.mbg.userprofile.ordermgmt.model.ManageLeadProjectResponse;
import com.sarvah.mbg.userprofile.response.BidResponses;
import com.sarvah.mbg.userprofile.response.ProjectResponse;
import com.sarvah.mbg.userprofile.service.UserProjectService;

/**
 * @author shivu
 *
 */
@Component
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserProjectResource {
	private static final Logger logger = LoggerFactory
			.getLogger(UserResource.class);

	@Autowired
	private UserProjectService userProjectService;

	/**
	 * Method to Get User projects
	 * 
	 * This method returns the list projects available for the particular user
	 * 
	 * @throws MBGAppException
	 * 
	 * @QueryParam would be the projectname,type,addr line1,addr
	 *             line2,city,zipcode with @PathParam being the Userid
	 *
	 */
	@GET
	@Path("/{uid}/projects")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getProjectsForUser(@PathParam("uid") String userId,
			@QueryParam("projName") String projName,
			@QueryParam("type") String type,
			@QueryParam("userType") String userType,
			@QueryParam("addrLine1") String addrLine1,
			@QueryParam("addrLine2") String addrLine2,
			@QueryParam("city") String city,
			@QueryParam("zipcode") String zipcode,
			@QueryParam("lat") String lat, @QueryParam("lon") String lon,
			@QueryParam("view") String view,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		Point point = null;
		if (StringUtils.isNotBlank(lat) && StringUtils.isNotBlank(lon)) {
			point = new Point(Double.parseDouble(lat), Double.parseDouble(lon));
		}
		List<ProjectResponse> projects = null;
		try {
			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AllBidResponses")) {
				List<BidResponses> bidResponses = userProjectService
						.getBidResponses(userId, projName, userType);
				return Response.ok(bidResponses).build();

			} else if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("AvailableToBid")) {
				List<Project> availableProjects = userProjectService
						.getAvailableProjects(userId);

				return Response.ok(availableProjects).build();
			}

			else {
				projects = userProjectService.getUserProjects(userId, projName,
						type, addrLine1, addrLine2, city, zipcode, point);
			}
		} catch (Exception e) {
			logger.info("unable to find user project");
			throw new MBGAppException("unable to find user project", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(projects).build();
	}

	/**
	 * Method for count user related projects, we can also count this projects
	 * based on bellow parameters.
	 * 
	 * @param userId
	 * @param name
	 * @param type
	 * @param address1
	 * @param address2
	 * @param city
	 * @return
	 * @throws MBGAppException
	 */

	@GET
	@Path("/{uid}/projects/count")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response countProjects(@PathParam("uid") String userId,
			@QueryParam("name") String name,
			@QueryParam("type") ProjectType type,
			@QueryParam("addrLine1") String address1,
			@QueryParam("addrLine2") String address2,
			@QueryParam("city") String city,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		Long count = null;
		try {
			count = userProjectService.countProjects(userId, name, type,
					address1, address2, city);
		} catch (Exception e) {
			logger.info("unable to count project for user");
			throw new MBGAppException("unable to count project for user", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(count).build();
	}

	/**
	 * Method for create user related projects, user id is necessary field.
	 * 
	 * @param userId
	 * @param projectCreateRequestParam
	 * @return
	 * @throws MBGAppException
	 */
	@POST
	@Path("/{uid}/projects")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response createProject(@PathParam("uid") String userId,
			ProjectCreateRequestParam projectCreateRequestParam,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		String name = projectCreateRequestParam.getProjectName();
		String desc = projectCreateRequestParam.getDesc();
		String budget = projectCreateRequestParam.getBudget();
		String projectType = projectCreateRequestParam.getProjectType();
		String userType = projectCreateRequestParam.getUserType();
		String status = projectCreateRequestParam.getStatus();
		String addrId = projectCreateRequestParam.getAddressId();
		String addrLine1 = projectCreateRequestParam.getAddrLine1();
		String addrLine2 = projectCreateRequestParam.getAddrLine2();
		String city = projectCreateRequestParam.getCity();
		String state = projectCreateRequestParam.getState();
		String country = projectCreateRequestParam.getCountry();
		String zipcode = projectCreateRequestParam.getZipcode();
		String latvalue = projectCreateRequestParam.getLatValue();
		String longvalue = projectCreateRequestParam.getLongValue();
		String email = projectCreateRequestParam.getEmail();
		String phoneType = projectCreateRequestParam.getPhoneType();
		String number = projectCreateRequestParam.getNumber();
		String constructionNew = projectCreateRequestParam.getConstructionNew();
		String asSoonRequired = projectCreateRequestParam.getAsSoonRequired();

		Point point = null;
		if (StringUtils.isNotBlank(latvalue)
				&& StringUtils.isNotBlank(longvalue)) {
			point = new Point(Double.parseDouble(latvalue),
					Double.parseDouble(longvalue));
		}
		Project project1 = null;
		try {
			project1 = userProjectService.createProject(userId, name, desc,
					budget, projectType, userType, addrId, addrLine1,
					addrLine2, city, state, country, zipcode, point, email,
					phoneType, number, status, constructionNew, asSoonRequired);
		} catch (Exception e) {
			logger.info("unable to create project for user");
			throw new MBGAppException("unable to create project for user", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(project1).build();
	}

	/**
	 * Method to Update User's project details
	 * 
	 * This method updates the project details of the particular user
	 * 
	 * @throws MBGAppException
	 * 
	 * @PutParam would be the projectname,type,budget,addr line1,addr
	 *           line2,city,state,country,zipcode,email,status with @PathParam
	 *           being the Userid and the projectId
	 */
	@PUT
	@Path("/{uid}/projects/{projid}")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response updateUserProjects(@PathParam("uid") String uid,
			@PathParam("projid") String projid,
			ProjectUpdateRequestParam projectUpdateRequestParam,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		String projName = projectUpdateRequestParam.getProjName();
		String projdesc = projectUpdateRequestParam.getDesc();
		String budget = projectUpdateRequestParam.getBudget();
		String type = projectUpdateRequestParam.getType();
		String addrLine1 = projectUpdateRequestParam.getAddressLine1();
		String addrLine2 = projectUpdateRequestParam.getAddressLine2();
		String city = projectUpdateRequestParam.getCity();
		String state = projectUpdateRequestParam.getState();
		String country = projectUpdateRequestParam.getCountry();
		String email = projectUpdateRequestParam.getEmail();
		String status = projectUpdateRequestParam.getStatus();
		String zipcode = projectUpdateRequestParam.getZipcode();
		Project projId;
		try {
			projId = userProjectService.updateUserProject(uid, projid,
					projName, projdesc, budget, type, addrLine1, addrLine2,
					city, state, country, zipcode, email, status);
		} catch (Exception e) {
			logger.info("unable to update user project");
			throw new MBGAppException("unable to update user project", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		return Response.ok(projId).build();
	}

	/**
	 * Method to delete the particular project of the user
	 * 
	 * Here the particular project of the particular user would be deleted
	 * 
	 * @throws MBGAppException
	 * 
	 * @PathParam will the userId and the ProjectId
	 */
	@DELETE
	@Path("/{uid}/projects/{projid}")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response deleteUserProject(@PathParam("uid") String uid,
			@PathParam("projid") String projid,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		String projId = null;
		try {
			projId = userProjectService.deleteUserProject(uid, projid);
		} catch (Exception e) {
			logger.info("unable to delete user project");
			throw new MBGAppException("unable to delete user project", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(projId).build();
	}

	@POST
	@Path("/{userId}/leadProject")
	public Response createLeadProject(@PathParam("userId") String userId,
			ProjectCreateRequestParam projectCreateRequestParam) {
		String name = projectCreateRequestParam.getProjectName();
		String projectType = projectCreateRequestParam.getProjectType();
		String note = projectCreateRequestParam.getDesc();
		String addrId = projectCreateRequestParam.getAddressId();
		String addrLine1 = projectCreateRequestParam.getAddrLine1();
		String addrLine2 = projectCreateRequestParam.getAddrLine2();
		String city = projectCreateRequestParam.getCity();
		String state = projectCreateRequestParam.getState();
		String country = projectCreateRequestParam.getCountry();
		String zipcode = projectCreateRequestParam.getZipcode();
		String area = projectCreateRequestParam.getArea();
		String customerResponse = projectCreateRequestParam
				.getCustomerResponse();

		String salesExecutiveId = projectCreateRequestParam
				.getSalesExecutiveId();
		String ownerId = projectCreateRequestParam.getOwnerId();
		String projectStage = projectCreateRequestParam.getProjectStage();
		String followupDate = projectCreateRequestParam.getFollowupDate();
		String contactedDate = projectCreateRequestParam.getContactedDate();
		String latVal = projectCreateRequestParam.getLatValue();
		String longVal = projectCreateRequestParam.getLongValue();
		String requirementNote = projectCreateRequestParam.getRequirementNote();
		String referencedBy = projectCreateRequestParam.getReferencedBy();
		String called = projectCreateRequestParam.getCalled();
		String reasonForReject = projectCreateRequestParam.getReasonForReject();
		String status = projectCreateRequestParam.getStatus();
		LeadProject teleProjects = null;
		try {
			teleProjects = userProjectService.createLeadProject(name,
					projectType, addrId, addrLine1, addrLine2, city, state,
					country, zipcode, area, salesExecutiveId, ownerId,
					projectStage, followupDate, contactedDate, note,
					requirementNote, referencedBy, called, reasonForReject,
					latVal, longVal, status, customerResponse);
		} catch (Exception e) {
			throw new MBGAppException("unable to create lead", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(teleProjects).build();
	}

	@GET
	@Path("/leadProjects")
	public Response getLeadProjects(@QueryParam("searchVal") String searchVal,
			@QueryParam("projName") String projName,
			@QueryParam("type") String type,
			@QueryParam("addrLine1") String addrLine1,
			@QueryParam("addrLine2") String addrLine2,
			@QueryParam("city") String city,
			@QueryParam("zipcode") String zipcode,
			@QueryParam("lat") String lat, @QueryParam("lon") String lon,
			@QueryParam("projectStage") String projectStage,
			@QueryParam("area") String area,
			@QueryParam("followupDate") String followupDate,
			@QueryParam("followupDateEntered") String followupDateEntered,
			@QueryParam("contactedDate") String contactedDate,
			@QueryParam("customerName") String customerName,
			@QueryParam("salesExecutiveName") String salesExecutiveId,
			@QueryParam("ownersCount") String ownersCount,
			@QueryParam("referencedBy") String referencedBy,
			@QueryParam("called") String called,
			@QueryParam("reasonForReject") String reasonForReject,
			@QueryParam("requirement") String requirement,
			@QueryParam("fieldAssociateId") String fieldAssociateId,
			@QueryParam("status") String status,
			@QueryParam("toFollowupDate") String toFollowupDate,
			@QueryParam("roleName") String roleName,
			@QueryParam("view") String view,
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("20") @QueryParam("size") int size,
			@DefaultValue("createdDate,desc") @QueryParam("sort") String sortVal) {
		Point point = null;
		if (StringUtils.isNotBlank(lat) && StringUtils.isNotBlank(lon)) {
			point = new Point(Double.parseDouble(lat), Double.parseDouble(lon));
		}

		List<Order> orders = new ArrayList<Order>();
		String sortArray[] = sortVal.split(",");
		if (StringUtils.equalsIgnoreCase(sortArray[1], "desc"))
			orders.add(new Order(Direction.DESC, sortArray[0]));
		else
			orders.add(new Order(Direction.ASC, sortArray[0]));

		Sort sort = new Sort(orders);

		if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("GetLeadProjectWithPagination")) {
			LeadResponse leadResponse = new LeadResponse();
			try {
				ManageLeadProjectResponse leadProjects = userProjectService
						.manageLeadProjects(searchVal, projName, type,
								addrLine1, addrLine2, city, zipcode,
								projectStage, area, followupDate,
								followupDateEntered, contactedDate,
								customerName, salesExecutiveId, ownersCount,
								referencedBy, called, reasonForReject,
								requirement, status, toFollowupDate, roleName,
								point, page, size, sort);
				leadResponse.setLeadProjects(leadProjects.getLeadProjects());
				Page retPage = new Page();
				retPage.setTotalPages(leadProjects.getTotalPages());
				retPage.setTotalElements(leadProjects.getTotalElements());
				retPage.setNumber(leadProjects.getNumber());
				retPage.setSize(leadProjects.getSize());
				leadResponse.setPage(retPage);

				leadResponse.setEndusers(leadProjects.getEndusers());
				leadResponse
						.setCivilEngineers(leadProjects.getCivilEngineers());
				leadResponse.setCivilContractors(leadProjects
						.getCivilContractors());
				leadResponse.setPlumbers(leadProjects.getPlumbers());
				leadResponse.setOthers(leadProjects.getOthers());
			} catch (Exception e) {
				throw new MBGAppException("unable to get leads", e,
						e.getMessage(), Status.BAD_REQUEST.getStatusCode(),
						1000);
			}
			return Response.ok(leadResponse).build();
		} else if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("FieldAssociateManageLeads")) {
			LeadResponse leadResponse = new LeadResponse();
			try {
				ManageLeadProjectResponse leadProjects = userProjectService
						.manageFieldAssociateLeads(fieldAssociateId, searchVal,
								projName, type, addrLine1, addrLine2, city,
								zipcode, projectStage, area, followupDate,
								followupDateEntered, contactedDate,
								customerName, ownersCount, referencedBy,
								called, reasonForReject, requirement, status,
								toFollowupDate, roleName, point, page, size,
								sort);
				leadResponse.setLeadProjects(leadProjects.getLeadProjects());
				Page retPage = new Page();
				retPage.setTotalPages(leadProjects.getTotalPages());
				retPage.setTotalElements(leadProjects.getTotalElements());
				retPage.setNumber(leadProjects.getNumber());
				retPage.setSize(leadProjects.getSize());
				leadResponse.setPage(retPage);

				leadResponse.setEndusers(leadProjects.getEndusers());
				leadResponse
						.setCivilEngineers(leadProjects.getCivilEngineers());
				leadResponse.setCivilContractors(leadProjects
						.getCivilContractors());
				leadResponse.setPlumbers(leadProjects.getPlumbers());
				leadResponse.setOthers(leadProjects.getOthers());
			} catch (Exception e) {
				throw new MBGAppException("unable to get leads", e,
						e.getMessage(), Status.BAD_REQUEST.getStatusCode(),
						1000);
			}
			return Response.ok(leadResponse).build();
		} else if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("FieldAssociateManageLeadsMap")) {

			List<LeadProject> projects = null;
			try {
				projects = userProjectService
						.manageFieldAssociateLeadProjectsMap(fieldAssociateId,
								projName, type, addrLine1, addrLine2, city,
								zipcode, projectStage, area, followupDate,
								followupDateEntered, contactedDate,
								customerName, referencedBy, called,
								reasonForReject, requirement, status,
								toFollowupDate, roleName, point, sort);
			} catch (Exception e) {
				throw new MBGAppException("unable to get leads", e,
						e.getMessage(), Status.BAD_REQUEST.getStatusCode(),
						1000);
			}
			return Response.ok(projects).build();
		} else if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("TodayUpdatedLeads")) {
			List<LeadProject> projects = null;
			try {
				projects = userProjectService.getTodayUpdatedLeads();
			} catch (Exception e) {
				throw new MBGAppException("unable to get today updated leads",
						e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(),
						1000);
			}
			return Response.ok(projects).build();
		} else if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("TodayUpdatedLeadsWithPagination")) {
			ManageLeadProjectResponse leadProjects = null;
			LeadResponse leadResponse = new LeadResponse();
			try {
				leadProjects = userProjectService
						.getTodayUpdatedLeadsWithPagination(ownersCount, page,
								size, sort);
				leadResponse.setLeadProjects(leadProjects.getLeadProjects());
				Page retPage = new Page();
				retPage.setTotalPages(leadProjects.getTotalPages());
				retPage.setTotalElements(leadProjects.getTotalElements());
				retPage.setNumber(leadProjects.getNumber());
				retPage.setSize(leadProjects.getSize());
				leadResponse.setPage(retPage);

				leadResponse.setEndusers(leadProjects.getEndusers());
				leadResponse
						.setCivilEngineers(leadProjects.getCivilEngineers());
				leadResponse.setCivilContractors(leadProjects
						.getCivilContractors());
				leadResponse.setPlumbers(leadProjects.getPlumbers());
				leadResponse.setOthers(leadProjects.getOthers());
			} catch (Exception e) {
				throw new MBGAppException("unable to get today updated leads",
						e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(),
						1000);
			}
			return Response.ok(leadResponse).build();
		} else if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("TodayTeleAssociateUpdatedLeads")) {
			List<LeadProject> projects = null;
			try {
				projects = userProjectService
						.getTodayTeleAssociateUpdatedLeads(fieldAssociateId);
			} catch (Exception e) {
				throw new MBGAppException(
						"Unable to get TeleAssociate today updated leads", e,
						e.getMessage(), Status.BAD_REQUEST.getStatusCode(),
						1000);
			}
			return Response.ok(projects).build();
		} else if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("TodayTeleAssociateUpdatedLeadsWithPage")) {
			ManageLeadProjectResponse leadProjects = null;
			LeadResponse leadResponse = new LeadResponse();
			try {
				leadProjects = userProjectService
						.getTodayTeleAssociateUpdatedLeadsWithPage(
								fieldAssociateId, ownersCount, page, size, sort);
				leadResponse.setLeadProjects(leadProjects.getLeadProjects());
				Page retPage = new Page();
				retPage.setTotalPages(leadProjects.getTotalPages());
				retPage.setTotalElements(leadProjects.getTotalElements());
				retPage.setNumber(leadProjects.getNumber());
				retPage.setSize(leadProjects.getSize());
				leadResponse.setPage(retPage);

				leadResponse.setEndusers(leadProjects.getEndusers());
				leadResponse
						.setCivilEngineers(leadProjects.getCivilEngineers());
				leadResponse.setCivilContractors(leadProjects
						.getCivilContractors());
				leadResponse.setPlumbers(leadProjects.getPlumbers());
				leadResponse.setOthers(leadProjects.getOthers());
			} catch (Exception e) {
				throw new MBGAppException(
						"Unable to get TeleAssociate today updated leads", e,
						e.getMessage(), Status.BAD_REQUEST.getStatusCode(),
						1000);
			}

			return Response.ok(leadResponse).build();
		} else {
			List<LeadProject> projects = null;
			try {
				projects = userProjectService.manageLeadProjects(projName,
						type, addrLine1, addrLine2, city, zipcode,
						projectStage, area, followupDate, followupDateEntered,
						contactedDate, customerName, salesExecutiveId,
						referencedBy, called, reasonForReject, requirement,
						status, toFollowupDate, roleName, point, sort);
			} catch (Exception e) {
				throw new MBGAppException("unable to get leads", e,
						e.getMessage(), Status.BAD_REQUEST.getStatusCode(),
						1000);
			}
			return Response.ok(projects).build();
		}
	}

	@GET
	@Path("/leadProjects/{leadId}")
	public Response getTeleProjectById(@PathParam("leadId") String leadId,
			@QueryParam("fieldAssociateId") String fieldAssociateId,
			@QueryParam("view") String view) {
		LeadProject project = null;
		try {
			if (StringUtils.isNotBlank(view)
					&& view.equalsIgnoreCase("FieldAssociateLeadViewDetails")) {
				project = userProjectService.getFieldAssociateLeadViewDetails(
						fieldAssociateId, leadId);
			} else {
				project = userProjectService.getLeadProjectViewDetails(leadId);
			}
		} catch (Exception e) {
			throw new MBGAppException("unable to get lead based on leadId", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(project).build();
	}

	@DELETE
	@Path("/leadProjects/{leadId}")
	public Response deleteLeadProjectById(@PathParam("leadId") String leadId) {
		LeadProject project = null;
		try {
			project = userProjectService.deleteLeadProjectById(leadId);
		} catch (Exception e) {
			throw new MBGAppException("unable to delete lead based on leadId",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(project).build();
	}

	@PUT
	@Path("/leadProjects/{leadId}")
	public Response updateLeadProjectById(@PathParam("leadId") String leadId,
			ProjectUpdateRequestParam projectUpdateRequestParam) {
		String projName = projectUpdateRequestParam.getProjName();
		String note = projectUpdateRequestParam.getDesc();
		String type = projectUpdateRequestParam.getType();
		String addrLine1 = projectUpdateRequestParam.getAddressLine1();
		String addrLine2 = projectUpdateRequestParam.getAddressLine2();
		String city = projectUpdateRequestParam.getCity();
		String state = projectUpdateRequestParam.getState();
		String country = projectUpdateRequestParam.getCountry();
		String zipcode = projectUpdateRequestParam.getZipcode();
		String latVal = projectUpdateRequestParam.getLatVal();
		String longVal = projectUpdateRequestParam.getLongVal();
		String projectStage = projectUpdateRequestParam.getProjectStage();
		String area = projectUpdateRequestParam.getArea();
		String followupDate = projectUpdateRequestParam.getFollowupDate();
		String contactedDate = projectUpdateRequestParam.getContactedDate();
		String salesExecutiveId = projectUpdateRequestParam
				.getSalesExecutiveId();
		String requirementNote = projectUpdateRequestParam.getRequirementNote();
		String referencedBy = projectUpdateRequestParam.getReferencedBy();
		String called = projectUpdateRequestParam.getCalled();
		String reasonForReject = projectUpdateRequestParam.getReasonForReject();
		String status = projectUpdateRequestParam.getStatus();
		String customerResponse = projectUpdateRequestParam
				.getCustomerResponse();
		LeadProject project = null;
		try {
			project = userProjectService.updateLeadProjectById(leadId,
					projName, note, requirementNote, type, addrLine1,
					addrLine2, city, state, country, zipcode, latVal, longVal,
					projectStage, area, followupDate, contactedDate,
					salesExecutiveId, referencedBy, called, reasonForReject,
					status, customerResponse);
		} catch (Exception e) {
			throw new MBGAppException("unable to update lead based on leadId",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(project).build();
	}

	protected static ApiUser getApiUserFromSecurityContext(
			SecurityContext securityContext) {
		if (securityContext != null) {
			ApiUser apiUser = (ApiUser) securityContext.getUserPrincipal();
			return apiUser;
		}
		return null;
	}
}
