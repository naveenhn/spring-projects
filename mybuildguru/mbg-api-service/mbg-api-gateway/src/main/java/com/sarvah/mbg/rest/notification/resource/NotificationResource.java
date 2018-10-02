/**
 * 
 */
package com.sarvah.mbg.rest.notification.resource;

import java.util.ArrayList;
import java.util.List;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.domain.mongo.userprofile.notification.Notification;
import com.sarvah.mbg.notification.service.NotificationService;
import com.sarvah.mbg.rest.exception.MBGAppException;
import com.sarvah.mbg.rest.model.Page;
import com.sarvah.mbg.rest.notification.response.NotificationResponse;
import com.sarvah.mbg.rest.userprofile.model.NotificationCreateRequestParam;
import com.sarvah.mbg.rest.userprofile.model.NotificationUpdateRequestParam;
import com.sarvah.mbg.userprofile.service.UserService;

/**
 * @author sumanth
 *
 */
@Component
@Path("/users/{userId}/notifications")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NotificationResource {
	private static final Logger logger = LoggerFactory
			.getLogger(NotificationResource.class);

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private UserService userService;

	@GET
	public Response search(
			@PathParam("userId") String userId,
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("10") @QueryParam("size") int size,
			@DefaultValue("createdDate,desc") @QueryParam("sort") List<String> sortList)
			throws MBGAppException {
		try {
			NotificationResponse response = new NotificationResponse();
			List<Order> orders = new ArrayList<Order>();
			for (String sortItem : sortList) {
				if (sortItem.split(",")[1].equalsIgnoreCase("desc"))
					orders.add(new Order(Direction.DESC, sortItem.split(",")[0]));
				else
					orders.add(new Order(Direction.ASC, sortItem.split(",")[0]));
			}
			Sort sort = new Sort(orders);
			response.setNotifications(notificationService.search(userId, page,
					size, sort));
			int totalElements = notificationService.count(userId);
			Page retPage = new Page(); // first page is 0, 2nd is 1 ....
			retPage.setNumber(page);
			retPage.setSize(size);
			retPage.setTotalElements(totalElements);
			retPage.setTotalPages(totalElements / size);
			response.setPage(retPage);
			return Response.ok(response).build();
		} catch (Exception e) {
			logger.info("Error occured during get");
			throw new MBGAppException("Error occured during get", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	@Path("/count")
	@GET
	public Response getCount(@PathParam("userId") String userId)
			throws MBGAppException {
		try {
			int count = notificationService.count(userId);
			return Response.ok(count).build();
		} catch (Exception e) {
			logger.info("Error occured during getting count ");
			throw new MBGAppException("Error occured during getting count", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * Method to create new Notification
	 * 
	 * @param userId
	 * @param notificationCreateRequestParam
	 * @return
	 * @throws MBGAppException
	 * @throws Exception
	 */
	@POST
	public Response add(@PathParam("userId") String userId,
			NotificationCreateRequestParam notificationCreateRequestParam)
			throws MBGAppException, Exception {

		String type = notificationCreateRequestParam.getType();
		String text = notificationCreateRequestParam.getText();
		String desc = notificationCreateRequestParam.getDesc();
		String archieved = notificationCreateRequestParam.getArchieved();
		String acknowledged = notificationCreateRequestParam.getAcknowledged();

		try {

			Notification notification = notificationService.addNotification(
					userId, type, text, desc, archieved, acknowledged);
			return Response.ok(notification).build();
		} catch (Exception e) {
			logger.info("Error occured during creation");
			throw new MBGAppException("Error during notification creation", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * Method to update Notification
	 * 
	 * @param userId
	 * @param notificationId
	 * @param notificationUpdateRequestParam
	 * @return
	 * @throws MBGAppException
	 * @throws Exception
	 */
	@Path("/{notid}")
	@PUT
	public Response update(@PathParam("userId") String userId,
			@PathParam("notid") String notificationId,
			NotificationUpdateRequestParam notificationUpdateRequestParam)
			throws MBGAppException, Exception {

		String type = notificationUpdateRequestParam.getType();
		String text = notificationUpdateRequestParam.getText();
		String desc = notificationUpdateRequestParam.getDesc();
		String archieved = notificationUpdateRequestParam.getArchieved();
		String acknowledged = notificationUpdateRequestParam.getAcknowledged();

		try {

			Notification notification = notificationService.editNotification(
					userId, notificationId, type, text, desc, archieved,
					acknowledged);

			return Response.ok(notification).build();

		} catch (Exception e) {
			logger.info("Error occured during update");
			throw new MBGAppException("Error occured during update", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}
	}

	/**
	 * Method to delete a notification
	 * 
	 * @param userId
	 * @param notificationId
	 * @return
	 * @throws MBGAppException
	 * @throws Exception
	 */
	@Path("/{notid}")
	@DELETE
	public Response remove(@PathParam("userId") String userId,
			@PathParam("notid") String notificationId) throws MBGAppException,
			Exception {
		// Idempotent now, returns success on delete. Shd handle delete failure?
		try {
			String notid = notificationService.deleteNotification(userId,
					notificationId);
			return Response.ok(notid).build();// anything else?
		} catch (Exception e) {
			logger.info("Error occured during deletion");
			throw new MBGAppException("Error occured during deletion", e,
					e.getMessage(), Status.BAD_REQUEST.getStatusCode(), 1000);
		}

	}

}
