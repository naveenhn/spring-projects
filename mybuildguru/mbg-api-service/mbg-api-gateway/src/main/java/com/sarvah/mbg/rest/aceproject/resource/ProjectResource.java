/**
 * 
 */
package com.sarvah.mbg.rest.aceproject.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.commons.storage.FileStorage;
import com.sarvah.mbg.domain.mongo.aceproject.Bid;
import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.project.model.BidCountResponse;
import com.sarvah.mbg.project.model.BidDescriptionResponse;
import com.sarvah.mbg.project.model.BudgetResponse;
import com.sarvah.mbg.project.model.ProjectDescriptionResponse;
import com.sarvah.mbg.project.service.ProjectService;
import com.sarvah.mbg.rest.authorization.MBGSecurityContext;
import com.sarvah.mbg.rest.exception.MBGAppException;
import com.sarvah.mbg.rest.userprofile.model.BidCreateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.BidUpdateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.ProjectGetRequestParam;
import com.sarvah.mbg.userprofile.auth.model.ApiUser;

/**
 * @author naveen
 *
 */
@Component
@Path("/projects")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProjectResource {
	private static final Logger logger = LoggerFactory
			.getLogger(ProjectResource.class);
	@Autowired
	private ProjectService projectService;

	@Autowired
	private FileStorage filestorage;

	/**
	 * Method for get system level projects, we can also get the project based
	 * on project parameters.
	 * 
	 * @param projectGetRequestParam
	 * @return
	 * @throws Exception
	 */
	@GET
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getAllProject(
			@BeanParam ProjectGetRequestParam projectGetRequestParam,
			@QueryParam("view") String view,
			@Context SecurityContext securityContext) throws Exception {

		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}

		String name = projectGetRequestParam.getPname();
		String budget = projectGetRequestParam.getBudget();
		String constructionType = projectGetRequestParam.getConstructionType();
		String projectType = projectGetRequestParam.getProjectType();
		String status = projectGetRequestParam.getStatus();
		String addressLine1 = projectGetRequestParam.getAddressLine1();
		String addressLine2 = projectGetRequestParam.getAddressLine2();
		String city = projectGetRequestParam.getCity();
		String state = projectGetRequestParam.getState();
		String country = projectGetRequestParam.getCountry();
		String zipcode = projectGetRequestParam.getZipcode();
		String email = projectGetRequestParam.getEmail();
		String phoneType = projectGetRequestParam.getPhoneType();
		String latname = projectGetRequestParam.getLatname();
		String longname = projectGetRequestParam.getLongname();
		String roleName = apiUser.getMbgRoles();
		Point point = null;
		if (org.apache.commons.lang.StringUtils.isNotBlank(latname)
				&& StringUtils.isNotBlank(longname)) {
			point = new Point(Double.parseDouble(latname),
					Double.parseDouble(longname));
		}

		if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("AvailableProjects")) {
			List<ProjectDescriptionResponse> projects = projectService
					.getAvailableProjects(roleName);
			return Response.ok(projects).build();
		} else if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("AdminManageProjects")) {
			List<ProjectDescriptionResponse> projects = projectService
					.getAdminManageProjects(roleName);
			return Response.ok(projects).build();
		} else if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("AvailableToBid")) {
			List<ProjectDescriptionResponse> availableProjects = projectService
					.getAvailableProjectsToBid(apiUser.getUid(), roleName);

			return Response.ok(availableProjects).build();
		} else {
			Set<ProjectDescriptionResponse> projects = projectService
					.getSystemProject(name, budget, constructionType,
							projectType, status, addressLine1, addressLine2,
							city, state, country, zipcode, email, phoneType,
							roleName, point);
			return Response.ok(projects).build();
		}
	}

	/**
	 * method to get the minimum and maximum budget from all projects
	 * 
	 * @return
	 */
	@GET
	@Path("/budget")
	public Response getMinMaxBudgetValue() {

		BudgetResponse budgetResponse = new BudgetResponse();

		budgetResponse = projectService.getMinMaxBudget();

		return Response.ok(budgetResponse).build();
	}

	/**
	 * Method to get project by projectID
	 * 
	 * @Pathparam would be the projectID,by which the project details will
	 *            displayed as the result
	 * @param projid
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{projid}")
	public Response getProjectById(@PathParam("projid") String projid)
			throws MBGAppException {

		Project project;
		try {
			project = projectService.getProjectById(projid);
		} catch (Exception e) {
			throw new MBGAppException("project not found", e, e.getMessage(),
					Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		return Response.ok(project).build();
	}

	/**
	 * Method for delete system level project based on project id. project id is
	 * necessary field
	 * 
	 * @param pId
	 * @return
	 * @throws MBGAppException
	 */
	@DELETE
	@Path("/{projid}")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response deleteProject(@PathParam("projid") String pId,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		Project project;
		try {
			project = projectService.deleteSystemProject(pId);
		} catch (Exception e) {
			throw new MBGAppException("unable to delete project", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(project).build();
	}

	/**
	 * Method for get one or more number bids for project, project id is
	 * necessary field
	 * 
	 * @param pId
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{projid}/bids")
	public Response getBidsForProject(@PathParam("projid") String pId,
			@QueryParam("roleName") String roleName) throws MBGAppException {
		List<BidDescriptionResponse> bids = null;
		try {
			bids = projectService.getBidsForProject(pId, roleName);
		} catch (Exception e) {
			throw new MBGAppException("unable get project bid", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(bids).build();
	}

	/**
	 * Method to Count project bids by projectId
	 * 
	 * @Pathparam would be the projectId
	 * 
	 * @param projid
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{projid}/bids/count")
	public Response countProjectBids(@PathParam("projid") String projid,
			@QueryParam("view") String view) throws MBGAppException {

		if (StringUtils.isNotBlank(view)
				&& view.equalsIgnoreCase("projectBidsCountByRolewise")) {
			try {
				BidCountResponse bidCountresponse = projectService
						.countProjectBidsByProjectAndRole(projid);
				return Response.ok(bidCountresponse).build();
			} catch (Exception e) {
				throw new MBGAppException(
						"unable to count projectBids based on the project and user role",
						e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(),
						1000);
			}

		} else {

			Long bidCount = null;
			try {
				bidCount = projectService.countProjectBidsById(projid);
			} catch (Exception e) {
				throw new MBGAppException("unable to count bids of project", e,
						e.getMessage(), Status.BAD_REQUEST.getStatusCode(),
						1000);
			}
			return Response.ok(bidCount).build();
		}

	}

	/**
	 * Method to create bid for a project
	 * 
	 * @param projid
	 * @param bidCreateRequestParam
	 * @return
	 * @throws MBGAppException
	 */
	@POST
	@Path("/{projid}/bids")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response createBid(@PathParam("projid") String projid,
			BidCreateRequestParam bidCreateRequestParam,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		String desc = bidCreateRequestParam.getDesc();
		String assetId = bidCreateRequestParam.getAssetId();
		String quoteAmt = bidCreateRequestParam.getQuoteAmt();

		Bid bid;
		try {
			bid = projectService.createBid(apiUser.getUid(), projid, desc,
					assetId, quoteAmt);
		} catch (Exception e) {
			throw new MBGAppException("unable to create bid", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(bid).build();
	}

	// File and Image upload
	@POST
	@Path("/file/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(FormDataMultiPart form) throws IOException,
			MBGAppException {
		String updatedId = null;
		String fileName = null;
		String bid = null;
		InputStream fileInputStream = null;
		long contentLength = 0;
		String fileTypeValue = null;
		try {
			Map<String, List<FormDataBodyPart>> formdatas = form.getFields();
			for (String partName : formdatas.keySet()) {
				List<FormDataBodyPart> fileparts = formdatas.get(partName);
				for (FormDataBodyPart formDataBodyPart : fileparts) {
					if (!formDataBodyPart.isSimple()) {
						ContentDisposition contentDisposition = formDataBodyPart
								.getContentDisposition();
						fileInputStream = formDataBodyPart
								.getValueAs(InputStream.class);
						fileName = contentDisposition.getFileName();
						contentLength = contentDisposition.getSize();
					} else {
						if (formDataBodyPart.getName().equals("bidId")) {
							bid = formDataBodyPart.getValue();
						}
						if (formDataBodyPart.getName().equals("fileType")) {
							fileTypeValue = formDataBodyPart.getValue();
						}
					}
				}
			}
			updatedId = projectService.uploadFile(bid, fileName, fileTypeValue,
					fileInputStream, contentLength);
		} catch (Exception e) {
			throw new MBGAppException("Error occured trying to Upload File", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		} finally {
			fileInputStream.close();
		}
		return Response.ok().entity("Files uploaded " + updatedId).build();
	}

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
	@PUT
	@Path("/{projid}/bids/{bidid}")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response updateBidForProject(@PathParam("projid") String projId,
			@PathParam("bidid") String bidId,
			BidUpdateRequestParam bidUpdateRequestParam,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		String desc = bidUpdateRequestParam.getDesc();
		String quoteAmount = bidUpdateRequestParam.getQuoteAmount();
		Bid bid;
		try {
			bid = projectService.updateBidForProject(projId, bidId, desc,
					quoteAmount);
		} catch (Exception e) {
			throw new MBGAppException("unable to update bid of project", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(bid).build();

	}

	/**
	 * Method for delete bid for Project, projectId and bidId is necessary
	 * field.
	 * 
	 * @param projId
	 * @param bidId
	 * @return
	 * @throws MBGAppException
	 */
	@DELETE
	@Path("/{projid}/bids/{bidid}")
	// @RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response deleteBidForProject(@PathParam("projid") String projId,
			@PathParam("bidid") String bidId,
			@Context SecurityContext securityContext) throws MBGAppException {

		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		String bId = null;
		try {
			bId = projectService.deleteBidForProject(projId, bidId);
		} catch (Exception e) {
			throw new MBGAppException("unable to delete bid of project", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(bId).build();
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
