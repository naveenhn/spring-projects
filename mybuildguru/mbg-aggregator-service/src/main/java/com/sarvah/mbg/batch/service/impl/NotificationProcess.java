package com.sarvah.mbg.batch.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.batch.dao.jpa.UserOrderRepository;
import com.sarvah.mbg.batch.dao.mongo.NotificationChangeEventDAO;
import com.sarvah.mbg.batch.dao.mongo.NotificationDAO;
import com.sarvah.mbg.batch.dao.mongo.ProductDAO;
import com.sarvah.mbg.batch.dao.mongo.ProjectDAO;
import com.sarvah.mbg.batch.dao.mongo.UserDAO;
import com.sarvah.mbg.batch.model.NotificationText;
import com.sarvah.mbg.batch.service.MBGCommandBase;
import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.notification.Notification;
import com.sarvah.mbg.domain.mongo.userprofile.notification.NotificationChangeEvent;
import com.sarvah.mbg.domain.mongo.userprofile.notification.NotificationChangeEventType;
import com.sarvah.mbg.domain.mongo.userprofile.notification.NotificationType;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.domain.ordermgmt.Order;

/**
 * 
 * @author Raju
 *
 */
@Component("NotificationProcess")
public class NotificationProcess implements MBGCommandBase {

	private static final Logger logger = LoggerFactory
			.getLogger(NotificationProcess.class);

	@Value("${name:Naveen}")
	private String name;

	@Override
	public void execute() {
		logger.info("Notification Service running ==== Notifications processing");
		try {
			generateNotications();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** The userOrderRepository **/
	@Autowired
	private UserOrderRepository userOrderRepository;

	/** The user DAO **/
	@Autowired
	private UserDAO userDAO;

	/** The Notification DAO **/
	@Autowired
	private NotificationDAO notificationDAO;

	/** The NotificationChangeEvent DAO **/
	@Autowired
	private NotificationChangeEventDAO noticationchangeEventDAO;

	/** The Product DAO **/
	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private ProjectDAO projectDAO;

	private void generateNotications() throws IOException, ParseException {
		List<User> userList = userDAO.findAll();
		logger.info("-----User Notification processing------");
		logger.info("Count of users: " + userList.size());

		List<Notification> adminOrderNotifications = new ArrayList<Notification>();
		List<Notification> adminProductNotifications = new ArrayList<Notification>();
		List<Notification> adminUsersNotifications = new ArrayList<Notification>();
		List<Notification> projectNotifications = new ArrayList<Notification>();

		for (User user : userList) {
			List<NotificationChangeEvent> notificationChangeEventsList = noticationchangeEventDAO
					.findByUserId(user.getId());
			for (NotificationChangeEvent notificationChangeEvent : notificationChangeEventsList) {

				SimpleDateFormat sdf = new SimpleDateFormat(
						"dd/MMM/yyyy hh:mm:ss");
				Date notificationDate = notificationChangeEvent
						.getLastModifiedDate();

				String dateInString = sdf.format(notificationDate);
				Date date = sdf.parse(dateInString);

				System.out.println(date);

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				System.out.println(calendar);

				GregorianCalendar gc = new GregorianCalendar();

				int dayAfter = gc.get(Calendar.DAY_OF_YEAR);
				gc.roll(Calendar.DAY_OF_YEAR, -1);
				System.out.println(dayAfter);

				int dayBefore = gc.get(Calendar.DAY_OF_YEAR);
				if (dayAfter > dayBefore) {
					// gc.roll(Calendar.YEAR, -1);

					String notificationSubject = notificationChangeEvent
							.getSubject();

					switch (notificationSubject) {
					case "Order":

						if (notificationChangeEvent.getChangeType().equals(
								NotificationChangeEventType.ADD)) {

							List<Order> allOrders = userOrderRepository
									.findAll();
							for (Order order : allOrders) {
								SimpleDateFormat sdf1 = new SimpleDateFormat(
										"dd/MMM/yyyy hh:mm:ss");
								Date orderDate = order.getLastmodifiedDate();

								String dateInString1 = sdf1.format(orderDate);
								Date date1 = sdf1.parse(dateInString1);

								System.out.println(date1);

								Calendar calendar1 = Calendar.getInstance();
								calendar1.setTime(date1);
								System.out.println(calendar1);

								GregorianCalendar gc1 = new GregorianCalendar();

								int dayAfter1 = gc1.get(Calendar.DAY_OF_YEAR);
								gc1.roll(Calendar.DAY_OF_YEAR, -1);
								System.out.println(dayAfter1);

								int dayBefore1 = gc1.get(Calendar.DAY_OF_YEAR);
								if (dayAfter1 > dayBefore1) {

									if (order.getOrderStatus().getDescription()
											.equalsIgnoreCase("Placed")) {
										SimpleDateFormat ordersdf = new SimpleDateFormat(
												"dd/MMM/yyyy hh:mm:ss");
										Date orderDate1 = order
												.getLastmodifiedDate();

										String orderDateInString1 = ordersdf
												.format(orderDate1);
										Date orderDateString = ordersdf
												.parse(orderDateInString1);

										System.out.println(orderDateString);

										Calendar orderCalendar1 = Calendar
												.getInstance();
										orderCalendar1.setTime(orderDateString);
										System.out.println(orderCalendar1);

										GregorianCalendar ordergc1 = new GregorianCalendar();

										int orderDayAfter1 = ordergc1
												.get(Calendar.DAY_OF_YEAR);
										ordergc1.roll(Calendar.DAY_OF_YEAR, -1);
										System.out.println(orderDayAfter1);

										int orderDayBefore1 = ordergc1
												.get(Calendar.DAY_OF_YEAR);
										if (orderDayAfter1 > orderDayBefore1) {

											Notification notification1 = new Notification();
											NotificationText notificationText = new NotificationText();

											notification1.setUser(user);

											Description descr1 = new Description();
											descr1.setLang("Eng");
											descr1.setVal(notificationText.text27);
											notification1.setDesc(descr1);

											Long count = notificationDAO
													.countByDesc_Val(descr1
															.getVal());
											if (count == 0) {
												count = 1l;
											}

											notification1
													.setNotificationSubject("Order");

											notification1.setText(count + " "
													+ notificationText.text27);

											notificationDAO.save(notification1);

											adminOrderNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text27));

											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text27);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("Order");

											notification2.setText(count + " "
													+ notificationText.text27);
											notificationDAO.save(notification2);

											for (Notification notification : adminOrderNotifications) {
												notificationDAO
														.delete(notification);
											}
										} else {
											return;
										}
									}
								}

							}
						}
						if (notificationChangeEvent.getChangeType().equals(
								NotificationChangeEventType.UPDATE)) {

							List<Order> allOrders = userOrderRepository
									.findAll();
							for (Order order : allOrders) {
								SimpleDateFormat ordersdf = new SimpleDateFormat(
										"dd/MMM/yyyy hh:mm:ss");
								Date orderDate1 = order.getLastmodifiedDate();

								String orderDateInString1 = ordersdf
										.format(orderDate1);
								Date orderDateString = ordersdf
										.parse(orderDateInString1);

								System.out.println(orderDateString);

								Calendar orderCalendar1 = Calendar
										.getInstance();
								orderCalendar1.setTime(orderDateString);
								System.out.println(orderCalendar1);

								GregorianCalendar ordergc1 = new GregorianCalendar();

								int orderDayAfter1 = ordergc1
										.get(Calendar.DAY_OF_YEAR);
								ordergc1.roll(Calendar.DAY_OF_YEAR, -1);
								System.out.println(orderDayAfter1);

								int orderDayBefore1 = ordergc1
										.get(Calendar.DAY_OF_YEAR);
								if (orderDayAfter1 > orderDayBefore1) {

									if (order.getOrderStatus().getDescription()
											.equalsIgnoreCase("Cancelled")) {

										Notification notification1 = new Notification();
										NotificationText notificationText = new NotificationText();

										notification1.setUser(user);

										Description descr1 = new Description();
										descr1.setLang("Eng");
										descr1.setVal(notificationText.text28);
										notification1.setDesc(descr1);

										Long count = notificationDAO
												.countByDesc_Val(descr1
														.getVal());
										if (count == 0) {
											count = 1l;
										}

										notification1
												.setNotificationSubject("Order");

										notification1.setText(count + " "
												+ notificationText.text28);

										notificationDAO.save(notification1);

										adminOrderNotifications
												.addAll(notificationDAO
														.findByUserAndDesc_Val(
																user,
																notificationText.text28));

										Notification notification2 = new Notification();

										notification2.setUser(user);

										Description descr2 = new Description();
										descr2.setLang("Eng");
										descr2.setVal(notificationText.text28);
										notification2.setDesc(descr2);

										notification2
												.setNotificationSubject("Order");

										notification2.setText(count + " "
												+ notificationText.text28);
										notificationDAO.save(notification2);

									} else if (order.getOrderStatus()
											.getDescription()
											.equalsIgnoreCase("Confirmed")) {

										Notification notification1 = new Notification();
										NotificationText notificationText = new NotificationText();

										notification1.setUser(user);

										Description descr1 = new Description();
										descr1.setLang("Eng");
										descr1.setVal(notificationText.text29);
										notification1.setDesc(descr1);

										Long count = notificationDAO
												.countByDesc_Val(descr1
														.getVal());
										if (count == 0) {
											count = 1l;
										}

										notification1
												.setNotificationSubject("Order");

										notification1.setText(count + " "
												+ notificationText.text29);

										notificationDAO.save(notification1);

										adminOrderNotifications
												.addAll(notificationDAO
														.findByUserAndDesc_Val(
																user,
																notificationText.text29));

										Notification notification2 = new Notification();

										notification2.setUser(user);

										Description descr2 = new Description();
										descr2.setLang("Eng");
										descr2.setVal(notificationText.text29);
										notification2.setDesc(descr2);

										notification2
												.setNotificationSubject("Order");

										notification2.setText(count + " "
												+ notificationText.text29);
										notificationDAO.save(notification2);
									} else if (order.getOrderStatus()
											.getDescription()
											.equalsIgnoreCase("Unconfirmed")) {

										Notification notification1 = new Notification();
										NotificationText notificationText = new NotificationText();

										notification1.setUser(user);

										Description descr1 = new Description();
										descr1.setLang("Eng");
										descr1.setVal(notificationText.text30);
										notification1.setDesc(descr1);

										Long count = notificationDAO
												.countByDesc_Val(descr1
														.getVal());
										if (count == 0) {
											count = 1l;
										}

										notification1
												.setNotificationSubject("Order");

										notification1.setText(count + " "
												+ notificationText.text30);

										notificationDAO.save(notification1);

										adminOrderNotifications
												.addAll(notificationDAO
														.findByUserAndDesc_Val(
																user,
																notificationText.text30));

										Notification notification2 = new Notification();

										notification2.setUser(user);

										Description descr2 = new Description();
										descr2.setLang("Eng");
										descr2.setVal(notificationText.text30);
										notification2.setDesc(descr2);

										notification2
												.setNotificationSubject("Order");

										notification2.setText(count + " "
												+ notificationText.text30);
										notificationDAO.save(notification2);
									} else if (order.getOrderStatus()
											.getDescription()
											.equalsIgnoreCase("Packed")) {

										Notification notification1 = new Notification();
										NotificationText notificationText = new NotificationText();

										notification1.setUser(user);

										Description descr1 = new Description();
										descr1.setLang("Eng");
										descr1.setVal(notificationText.text31);
										notification1.setDesc(descr1);

										Long count = notificationDAO
												.countByDesc_Val(descr1
														.getVal());
										if (count == 0) {
											count = 1l;
										}

										notification1
												.setNotificationSubject("Order");

										notification1.setText(count + " "
												+ notificationText.text31);

										notificationDAO.save(notification1);

										adminOrderNotifications
												.addAll(notificationDAO
														.findByUserAndDesc_Val(
																user,
																notificationText.text31));
										Notification notification2 = new Notification();

										notification2.setUser(user);

										Description descr2 = new Description();
										descr2.setLang("Eng");
										descr2.setVal(notificationText.text31);
										notification2.setDesc(descr2);

										notification2
												.setNotificationSubject("Order");

										notification2.setText(count + " "
												+ notificationText.text31);
										notificationDAO.save(notification2);
									} else if (order.getOrderStatus()
											.getDescription()
											.equalsIgnoreCase("Dispatched")) {

										Notification notification1 = new Notification();
										NotificationText notificationText = new NotificationText();

										notification1.setUser(user);

										Description descr1 = new Description();
										descr1.setLang("Eng");
										descr1.setVal(notificationText.text32);
										notification1.setDesc(descr1);

										Long count = notificationDAO
												.countByDesc_Val(descr1
														.getVal());
										if (count == 0) {
											count = 1l;
										}

										notification1
												.setNotificationSubject("Order");

										notification1.setText(count + " "
												+ notificationText.text32);

										notificationDAO.save(notification1);

										adminOrderNotifications
												.addAll(notificationDAO
														.findByUserAndDesc_Val(
																user,
																notificationText.text32));
										Notification notification2 = new Notification();

										notification2.setUser(user);

										Description descr2 = new Description();
										descr2.setLang("Eng");
										descr2.setVal(notificationText.text32);
										notification2.setDesc(descr2);

										notification2
												.setNotificationSubject("Order");

										notification2.setText(count + " "
												+ notificationText.text32);
										notificationDAO.save(notification2);
									} else if (order.getOrderStatus()
											.getDescription()
											.equalsIgnoreCase("Delivered")) {

										Notification notification1 = new Notification();
										NotificationText notificationText = new NotificationText();

										notification1.setUser(user);

										Description descr1 = new Description();
										descr1.setLang("Eng");
										descr1.setVal(notificationText.text33);
										notification1.setDesc(descr1);

										Long count = notificationDAO
												.countByDesc_Val(descr1
														.getVal());
										if (count == 0) {
											count = 1l;
										}

										notification1
												.setNotificationSubject("Order");

										notification1.setText(count + " "
												+ notificationText.text33);

										notificationDAO.save(notification1);

										adminOrderNotifications
												.addAll(notificationDAO
														.findByUserAndDesc_Val(
																user,
																notificationText.text33));
										Notification notification2 = new Notification();

										notification2.setUser(user);

										Description descr2 = new Description();
										descr2.setLang("Eng");
										descr2.setVal(notificationText.text33);
										notification2.setDesc(descr2);

										notification2
												.setNotificationSubject("Order");

										notification2.setText(count + " "
												+ notificationText.text33);
										notificationDAO.save(notification2);
									} else if (order.getOrderStatus()
											.getDescription()
											.equalsIgnoreCase("Returned")) {

										Notification notification1 = new Notification();
										NotificationText notificationText = new NotificationText();

										notification1.setUser(user);

										Description descr1 = new Description();
										descr1.setLang("Eng");
										descr1.setVal(notificationText.text34);
										notification1.setDesc(descr1);

										Long count = notificationDAO
												.countByDesc_Val(descr1
														.getVal());
										if (count == 0) {
											count = 1l;
										}

										notification1
												.setNotificationSubject("Order");

										notification1.setText(count + " "
												+ notificationText.text34);

										notificationDAO.save(notification1);

										adminOrderNotifications
												.addAll(notificationDAO
														.findByUserAndDesc_Val(
																user,
																notificationText.text34));
										Notification notification2 = new Notification();

										notification2.setUser(user);

										Description descr2 = new Description();
										descr2.setLang("Eng");
										descr2.setVal(notificationText.text34);
										notification2.setDesc(descr2);

										notification2
												.setNotificationSubject("Order");

										notification2.setText(count + " "
												+ notificationText.text34);
										notificationDAO.save(notification2);
									}
								} else {
									return;
								}
							}
						}
						break;

					case "Product":
						if (notificationChangeEvent.getChangeType().equals(
								NotificationChangeEventType.UPDATE)) {
							List<Product> allProducts = productDAO
									.findByCreatedBy(notificationChangeEvent
											.getUserId());
							for (Product product : allProducts) {
								if (product.getCreatedBy().equalsIgnoreCase(
										notificationChangeEvent.getUserId())) {
									SimpleDateFormat sdf1 = new SimpleDateFormat(
											"dd/MMM/yyyy hh:mm:ss");
									Date orderDate = product
											.getLastModifiedDate();

									String dateInString1 = sdf1
											.format(orderDate);
									Date date1 = sdf1.parse(dateInString1);

									System.out.println(date1);

									Calendar calendar1 = Calendar.getInstance();
									calendar1.setTime(date1);
									System.out.println(calendar1);

									GregorianCalendar gc1 = new GregorianCalendar();

									int dayAfter1 = gc1
											.get(Calendar.DAY_OF_YEAR);
									gc1.roll(Calendar.DAY_OF_YEAR, -1);
									System.out.println(dayAfter1);

									int dayBefore1 = gc1
											.get(Calendar.DAY_OF_YEAR);
									if (dayAfter1 > dayBefore1) {

										// Add time compare logic here
										if (product.getStatus().name()
												.equalsIgnoreCase("IN_BUILDONN")) {

											Notification notification1 = new Notification();
											NotificationText notificationText = new NotificationText();

											notification1.setUser(user);

											Description descr1 = new Description();
											descr1.setLang("Eng");
											descr1.setVal(notificationText.text2);
											notification1.setDesc(descr1);

											Long count = notificationDAO
													.countByDesc_Val(descr1
															.getVal());
											if (count == 0) {
												count = 1l;
											}
											notification1
													.setNotificationSubject("Product");

											notification1.setText(count + " "
													+ notificationText.text2);

											notificationDAO.save(notification1);

											adminProductNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text2));

											notification1.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text2);
											notification1.setDesc(descr2);

											notification1
													.setNotificationSubject("Product");

											notification1.setText(count + " "
													+ notificationText.text2);
											notificationDAO.save(notification1);

										} else if (product
												.getStatus()
												.name()
												.equalsIgnoreCase(
														"APPROVAL_PENDING")) {

											Notification notification1 = new Notification();
											NotificationText notificationText = new NotificationText();

											notification1.setUser(user);

											Description descr1 = new Description();
											descr1.setLang("Eng");
											descr1.setVal(notificationText.text1);
											notification1.setDesc(descr1);

											Long count = notificationDAO
													.countByDesc_Val(descr1
															.getVal());
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text1);
											notificationDAO.save(notification1);

											adminProductNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text1));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text1);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("Product");

											notification2.setText(count + " "
													+ notificationText.text1);
											notificationDAO.save(notification2);
										} else if (product.getStatus().name()
												.equalsIgnoreCase("REJECTED")) {

											Notification notification1 = new Notification();
											NotificationText notificationText = new NotificationText();

											notification1.setUser(user);

											Description descr1 = new Description();
											descr1.setLang("Eng");
											descr1.setVal(notificationText.text3);
											notification1.setDesc(descr1);

											Long count = notificationDAO
													.countByDesc_Val(descr1
															.getVal());
											if (count == 0) {
												count = 1l;
											}
											notification1
													.setNotificationSubject("Product");

											notification1.setText(count + " "
													+ notificationText.text3);
											notificationDAO.save(notification1);

											adminProductNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text3));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text3);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("Product");

											notification2.setText(count + " "
													+ notificationText.text3);
											notificationDAO.save(notification2);
										}
									} else {
										return;
									}
								}
							}
						}

						break;

					case "User":
						List<User> newUsers = userDAO.findAll();
						for (User newUser : newUsers) {

							SimpleDateFormat ordersdf = new SimpleDateFormat(
									"dd/MMM/yyyy hh:mm:ss");
							Date userDate1 = user.getLastModifiedDate();

							String orderDateInString1 = ordersdf
									.format(userDate1);
							Date orderDateString = ordersdf
									.parse(orderDateInString1);

							System.out.println(orderDateString);

							Calendar orderCalendar1 = Calendar.getInstance();
							orderCalendar1.setTime(orderDateString);
							System.out.println(orderCalendar1);

							GregorianCalendar ordergc1 = new GregorianCalendar();

							int orderDayAfter1 = ordergc1
									.get(Calendar.DAY_OF_YEAR);
							ordergc1.roll(Calendar.DAY_OF_YEAR, -1);
							System.out.println(orderDayAfter1);

							int orderDayBefore1 = ordergc1
									.get(Calendar.DAY_OF_YEAR);
							if (orderDayAfter1 > orderDayBefore1) {

								NotificationText notificationText = new NotificationText();

								if (notificationChangeEvent
										.getChangeType()
										.equals(NotificationChangeEventType.ADD)) {

									for (Role role : newUser.getRoles()) {
										if (role.getName().equalsIgnoreCase(
												"Dealer")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text5);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByDesc_Val(notificationText.text5);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text5);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);

											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text5));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text5);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text5);
											notificationDAO.save(notification2);

										} else if (role.getName()
												.equalsIgnoreCase("Provider")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text6);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByDesc_Val(notificationText.text6);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text6);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text6));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text6);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text6);
											notificationDAO.save(notification2);

										} else if (role.getName()
												.equalsIgnoreCase(
														"BUSINESSASSOCIATE")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text7);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByDesc_Val(notificationText.text7);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text7);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text7));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text7);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text7);
											notificationDAO.save(notification2);
										} else if (role.getName()
												.equalsIgnoreCase("Plumber")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text8);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByDesc_Val(notificationText.text8);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text8);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text8));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text8);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text8);
											notificationDAO.save(notification2);
										} else if (role.getName()
												.equalsIgnoreCase("Painter")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text9);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByDesc_Val(notificationText.text9);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text9);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text9));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text9);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text9);
											notificationDAO.save(notification2);

										} else if (role.getName()
												.equalsIgnoreCase("Carpenter")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text10);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByDesc_Val(notificationText.text10);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text10);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text10));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text10);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text10);
											notificationDAO.save(notification2);
										} else if (role.getName()
												.equalsIgnoreCase(
														"SiteEngineer")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text11);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByDesc_Val(notificationText.text11);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text11);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text11));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text11);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text11);
											notificationDAO.save(notification2);

										} else if (role.getName()
												.equalsIgnoreCase(
														"VastuConsultant")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text12);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByDesc_Val(notificationText.text12);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text12);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text12));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text12);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text12);
											notificationDAO.save(notification2);
										} else if (role
												.getName()
												.equalsIgnoreCase("Electrician")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text13);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByDesc_Val(notificationText.text13);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text13);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text13));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text13);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text13);
											notificationDAO.save(notification2);
										} else if (role.getName()
												.equalsIgnoreCase(
														"BIMConsultant")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text14);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByDesc_Val(notificationText.text14);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text14);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text14));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text14);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text14);
											notificationDAO.save(notification2);
										} else if (role.getName()
												.equalsIgnoreCase(
														"ProjectConsultant")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text15);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByText(notificationText.text15);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text15);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text15));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text15);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text15);
											notificationDAO.save(notification2);
										} else if (role.getName()
												.equalsIgnoreCase("Landscaper")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text16);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByText(notificationText.text16);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text16);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text16));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text16);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text16);
											notificationDAO.save(notification2);
										} else if (role.getName()
												.equalsIgnoreCase(
														"CivilContractor")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text17);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByText(notificationText.text17);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text17);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text17));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text17);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text17);
											notificationDAO.save(notification2);
										} else if (role.getName()
												.equalsIgnoreCase("Surveyor")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text18);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByText(notificationText.text18);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text18);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text18));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text18);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text18);
											notificationDAO.save(notification2);
											for (Notification notification : adminUsersNotifications) {
												notificationDAO
														.delete(notification);
											}
										} else if (role.getName()
												.equalsIgnoreCase(
														"WaterDiviner")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text19);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByText(notificationText.text19);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text19);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text19));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text19);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text19);
											notificationDAO.save(notification2);
										} else if (role.getName()
												.equalsIgnoreCase("Valuator")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text20);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByText(notificationText.text20);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text20);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text20));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text20);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text20);
											notificationDAO.save(notification2);
										} else if (role.getName()
												.equalsIgnoreCase("Boreweller")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text21);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByText(notificationText.text21);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text21);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text21));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text21);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text21);
											notificationDAO.save(notification2);
										} else if (role.getName()
												.equalsIgnoreCase(
														"FlooringContractor")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text22);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByText(notificationText.text22);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text22);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text22));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text22);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text22);
											notificationDAO.save(notification2);
										} else if (role.getName()
												.equalsIgnoreCase("Security")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text23);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByText(notificationText.text23);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text23);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text23));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text23);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text23);
											notificationDAO.save(notification2);
										} else if (role.getName()
												.equalsIgnoreCase("Architect")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text24);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByText(notificationText.text24);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text24);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text24));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text24);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text24);
											notificationDAO.save(notification2);
										} else if (role.getName()
												.equalsIgnoreCase(
														"InteriorDesigner")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text25);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByText(notificationText.text25);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text25);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text25));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text25);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text25);
											notificationDAO.save(notification2);
										} else if (role.getName()
												.equalsIgnoreCase("Enduser")) {
											Notification notification1 = new Notification();

											notification1.setUser(user);
											Description descr1 = new Description();
											descr1.setVal(notificationText.text26);
											notification1.setDesc(descr1);

											long count = notificationDAO
													.countByText(notificationText.text26);
											if (count == 0) {
												count = 1l;
											}
											notification1.setText(count + " "
													+ notificationText.text26);
											notification1
													.setType(NotificationType.ALERTS);
											notification1
													.setNotificationSubject("User");
											notificationDAO.save(notification1);
											adminUsersNotifications
													.addAll(notificationDAO
															.findByUserAndDesc_Val(
																	user,
																	notificationText.text26));
											Notification notification2 = new Notification();

											notification2.setUser(user);

											Description descr2 = new Description();
											descr2.setLang("Eng");
											descr2.setVal(notificationText.text26);
											notification2.setDesc(descr2);

											notification2
													.setNotificationSubject("User");

											notification2.setText(count + " "
													+ notificationText.text26);
											notificationDAO.save(notification2);
										}
									}
								}
							} else {
								return;
							}
						}
						break;

					case "Project":

						if (notificationChangeEvent.getChangeType().equals(
								NotificationChangeEventType.ADD)) {

							List<Project> projects = projectDAO.findAll();
							for (Project project : projects) {

								SimpleDateFormat ordersdf = new SimpleDateFormat(
										"dd/MMM/yyyy hh:mm:ss");
								Date projectDate1 = project
										.getLastModifiedDate();

								String projectDateInString1 = ordersdf
										.format(projectDate1);
								Date projectDateString = ordersdf
										.parse(projectDateInString1);

								System.out.println(projectDateString);

								Calendar projectCalendar1 = Calendar
										.getInstance();
								projectCalendar1.setTime(projectDateString);
								System.out.println(projectCalendar1);

								GregorianCalendar projectgc1 = new GregorianCalendar();

								int projectDayAfter1 = projectgc1
										.get(Calendar.DAY_OF_YEAR);
								projectgc1.roll(Calendar.DAY_OF_YEAR, -1);
								System.out.println(projectDayAfter1);

								int projectDayBefore1 = projectgc1
										.get(Calendar.DAY_OF_YEAR);
								if (projectDayAfter1 > projectDayBefore1) {
									Notification notification1 = new Notification();

									notification1.setUser(user);

									Description descr1 = new Description();
									descr1.setLang("Eng");
									descr1.setVal("Project Notification ");
									notification1.setDesc(descr1);

									NotificationText notificationText = new NotificationText();

									long count = notificationDAO
											.countByText(notificationText.text4);
									if (count == 0) {
										count = 1l;
									}
									notification1.setText(count + " "
											+ notificationText.text4);
									notification1
											.setType(NotificationType.ALERTS);

									notification1
											.setNotificationSubject("Project");
									notificationDAO.save(notification1);

									projectNotifications.addAll(notificationDAO
											.findByUserAndDesc_Val(user,
													notificationText.text4));
									Notification notification2 = new Notification();

									notification2.setUser(user);

									Description descr2 = new Description();
									descr2.setLang("Eng");
									descr2.setVal(notificationText.text4);
									notification2.setDesc(descr2);

									notification2
											.setNotificationSubject("User");

									notification2.setText(count + " "
											+ notificationText.text4);
									notificationDAO.save(notification2);

								}
							}
						}
						break;
					}
				}
			}
		}

		for (Notification notification : adminOrderNotifications) {
			notificationDAO.delete(notification);
		}

		for (Notification notification : adminProductNotifications) {
			notificationDAO.delete(notification);
		}

		for (Notification notification : adminUsersNotifications) {
			notificationDAO.delete(notification);
		}

		for (Notification notification : projectNotifications) {
			notificationDAO.delete(notification);
		}
	}
}