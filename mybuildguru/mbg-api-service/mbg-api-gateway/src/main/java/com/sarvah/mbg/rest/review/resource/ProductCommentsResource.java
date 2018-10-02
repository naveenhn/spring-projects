/**
 * 
 */
package com.sarvah.mbg.rest.review.resource;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.domain.mongo.review.Comment;
import com.sarvah.mbg.rest.authorization.MBGSecurityContext;
import com.sarvah.mbg.rest.exception.MBGAppException;
import com.sarvah.mbg.rest.model.Page;
import com.sarvah.mbg.rest.review.response.ProductsCommentsResponse;
import com.sarvah.mbg.reviews.model.CommentCreateRequestParam;
import com.sarvah.mbg.reviews.model.CommentUpdateRequestParam;
import com.sarvah.mbg.reviews.service.ReviewService;
import com.sarvah.mbg.userprofile.auth.model.ApiUser;

/**
 * @author naveen
 * @author sumanth
 *
 */
@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/catalog/products/{productId}/comments")
public class ProductCommentsResource {
	private static final Logger logger = LoggerFactory
			.getLogger(ProductCommentsResource.class);

	@Autowired
	private ReviewService reviewService;

	/**
	 * Method to get comments on Product
	 * 
	 * @param productId
	 * @param page
	 * @param size
	 * @param sortList
	 * @return
	 * @throws MBGAppException
	 */
	@GET
	public Response search(
			@PathParam("productId") String productId,
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("10") @QueryParam("size") int size,
			@DefaultValue("createdDate,desc") @QueryParam("sort") List<String> sortList)
			throws MBGAppException {
		try {
			ProductsCommentsResponse response = new ProductsCommentsResponse();
			List<Order> orders = new ArrayList<Order>();
			for (String sortItem : sortList) {
				if (sortItem.split(",")[1].equalsIgnoreCase("desc"))
					orders.add(new Order(Direction.DESC, sortItem.split(",")[0]));
				else
					orders.add(new Order(Direction.ASC, sortItem.split(",")[0]));
			}
			Sort sort = new Sort(orders);
			response.setComments(reviewService.search(productId, page, size,
					sort));
			int totalElements = reviewService.count(productId);
			Page retPage = new Page(); // first page is 0, 2nd is 1 ....
			retPage.setNumber(page);
			retPage.setSize(size);
			retPage.setTotalElements(totalElements);
			retPage.setTotalPages(totalElements / size);
			response.setPage(retPage);
			return Response.ok(response).build();
		} catch (Exception e) {
			logger.info("Error occured during getting product commenets");
			throw new MBGAppException(
					"Error occured during getting product commenets", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * Method to get a count of comment on Product
	 * 
	 * @param productId
	 * @return
	 * @throws MBGAppException
	 */

	@Path("/count")
	@GET
	public Response getCount(@PathParam("productId") String productId)
			throws MBGAppException {
		try {
			int count = reviewService.count(productId);
			return Response.ok(count).build();
		} catch (Exception e) {
			logger.info("Error occured during getting products count");
			throw new MBGAppException(
					"Error occured during getting products count", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * Method to create comment on Product
	 * 
	 * @param productId
	 * @param commentCreateRequestParam
	 * @return
	 * @throws Exception
	 */
	@Path("/check")
	@POST
	@RolesAllowed({ "AUTHENTICATED", "ADMINISTRATOR" })
	public Response createComment(@PathParam("productId") String productId,
			CommentCreateRequestParam commentCreateRequestParam,
			@Context SecurityContext securityContext) throws Exception {
		// get the apiuser consuming this api and pass around for decision
		// making
		ApiUser apiUser = MBGSecurityContext
				.getApiUserFromSecurityContext(securityContext);
		if (logger.isDebugEnabled()) {
			logger.info("Logged in user information : {}", apiUser);
		}
		String ratingval = commentCreateRequestParam.getRatingVal();
		String desc = commentCreateRequestParam.getDesc();
		String slug = commentCreateRequestParam.getSlug();
		String text = commentCreateRequestParam.getText();
		String expert = commentCreateRequestParam.getExpert();

		Comment comment = reviewService.createComment(apiUser.getUid(),
				ratingval, desc, slug, text, expert, productId);
		try {
			return Response.ok(comment).build();
		} catch (Exception e) {
			logger.info("Error during product comment creation");
			throw new MBGAppException("Error during product comment creation",
					e, e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * Method to update a comment information
	 * 
	 * @param commentUpdateRequestParam
	 * @param productId
	 * @param commentId
	 * @return
	 * @throws Exception
	 */
	@Path("/{commentId}")
	@PUT
	public Response updateComment(
			CommentUpdateRequestParam commentUpdateRequestParam,
			@PathParam("productId") String productId,
			@PathParam("commentId") String commentId) throws Exception {

		String ratingval = commentUpdateRequestParam.getRatingVal();
		String desc = commentUpdateRequestParam.getDesc();
		String slug = commentUpdateRequestParam.getSlug();
		String text = commentUpdateRequestParam.getText();
		String expert = commentUpdateRequestParam.getExpert();

		Comment comment = reviewService.updateComment(ratingval, desc, slug,
				text, expert, productId, commentId);
		try {
			return Response.ok(comment).build();
		} catch (Exception e) {
			logger.info("Error occured during updating a product comment");
			throw new MBGAppException(
					"Error occured during updating a product comment", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * Method to Delete a comment
	 * 
	 * @param productId
	 * @param commentId
	 * @return
	 * @throws MBGAppException
	 */
	@Path("/{commentId}")
	@DELETE
	public Response remove(@PathParam("productId") String productId,
			@PathParam("commentId") String commentId) throws MBGAppException {
		try {
			String commentid = reviewService.remove(productId, commentId);
			return Response.ok(commentid).build();
		} catch (Exception e) {
			logger.info("Error occured during deleting a product comment");
			throw new MBGAppException(
					"Error occured during deleting a product comment", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

}
