/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.domain.mongo.aceproject.Bid;
import com.sarvah.mbg.rest.exception.MBGAppException;
import com.sarvah.mbg.rest.userprofile.model.BidUpdateRequestParam;
import com.sarvah.mbg.userprofile.auth.model.ApiUser;
import com.sarvah.mbg.userprofile.response.BidResponses;
import com.sarvah.mbg.userprofile.service.UserBidService;

/**
 * @author shivu
 *
 */
@Component
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserBidResource {

	private static final Logger logger = LoggerFactory
			.getLogger(UserResource.class);

	@Autowired
	private UserBidService userBidService;

	/**
	 * Method to get number of bids related to user, user id is necessary field.
	 * 
	 * @param userId
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	@Path("/{uid}/bids")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response getBid(@PathParam("uid") String userId,
			@Context SecurityContext securityContext) throws MBGAppException {

		// get the apiuser consuming this api and pass around for decision
		// making
		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		List<BidResponses> bid = null;
		try {
			bid = userBidService.getAllBids(userId);

		} catch (Exception e) {
			logger.info("unable to find user bids for project");
			throw new MBGAppException("unable to find user bids for project",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(bid).build();
	}

	/**
	 * Method to Count User bids
	 * 
	 * This method returns the count of bids available for the particular user
	 * 
	 * @throws MBGAppException
	 * 
	 * @PathParam would be the userId, by which the bids for the particular user
	 *            will be obtained
	 */
	@GET
	@Path("/{uid}/bids/count")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response countUserBids(@PathParam("uid") String uid,
			@Context SecurityContext securityContext) throws MBGAppException {

		// get the apiuser consuming this api and pass around for decision
		// making
		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		long count;
		try {
			count = userBidService.countUserBids(uid);
		} catch (Exception e) {
			logger.info("unable to count user bids for project");
			throw new MBGAppException("unable to count user bids for project",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

		return Response.ok(count).build();
	}

	/**
	 * Method to Update user related bids, user id is necessary field.
	 * 
	 * @param userId
	 * @param bidId
	 * @param bidUpdateRequest
	 * @return
	 * @throws MBGAppException
	 */
	@PUT
	@Path("/{uid}/bids/{bidid}")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response updateBid(@PathParam("uid") String userId,
			@PathParam("bidid") String bidId,
			BidUpdateRequestParam bidUpdateRequest,
			@Context SecurityContext securityContext) throws MBGAppException {

		// get the apiuser consuming this api and pass around for decision
		// making
		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		String desc = bidUpdateRequest.getDesc();
		String quoteAmount = bidUpdateRequest.getQuoteAmount();
		Bid bid = null;
		try {
			bid = userBidService.updateBid(userId, bidId, desc, quoteAmount);
		} catch (Exception e) {
			logger.info("unable to update user bids for project");
			throw new MBGAppException("unable to update user bids for project",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(bid).build();
	}

	/**
	 * Method Delete a user bid
	 * 
	 * This method deletes a bid for the particular user
	 * 
	 * @throws MBGAppException
	 * 
	 * @PathParam would be userId and the bidId, by which the particular bid
	 *            gets deleted
	 * 
	 */
	@DELETE
	@Path("/{uid}/bids/{bidid}")
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response deleteUserBids(@PathParam("uid") String uid,
			@PathParam("bidid") String bidid,
			@Context SecurityContext securityContext) throws MBGAppException {

		// get the apiuser consuming this api and pass around for decision
		// making
		ApiUser apiUser = getApiUserFromSecurityContext(securityContext);
		logger.debug("" + apiUser);

		String bidId;
		try {
			bidId = userBidService.deleteUserBids(uid, bidid);
		} catch (Exception e) {
			logger.info("unable to delete user bids for project");
			throw new MBGAppException("unable to delete user bids for project",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
		return Response.ok(bidId).build();

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
