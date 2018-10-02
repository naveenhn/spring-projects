/**
 * 
 */
package com.sarvah.mbg.userprofile.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sarvah.mbg.commons.communication.UserCommunicationService;
import com.sarvah.mbg.domain.common.asset.Image;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductAsset;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.common.contact.Phone;
import com.sarvah.mbg.domain.mongo.dashboard.ProductNotOnBoardedNameStore;
import com.sarvah.mbg.domain.mongo.store.Store;
import com.sarvah.mbg.domain.mongo.store.StoreProductPricing;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.domain.mongo.userprofile.role.UserPackage;
import com.sarvah.mbg.domain.ordermgmt.ItemStatus;
import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.OrderDetails;
import com.sarvah.mbg.domain.ordermgmt.OrderStatement;
import com.sarvah.mbg.domain.ordermgmt.OrderStatus;
import com.sarvah.mbg.domain.ordermgmt.audit.OrderAudit;
import com.sarvah.mbg.domain.ordermgmt.audit.OrderAuditType;
import com.sarvah.mbg.domain.ordermgmt.payment.InvoiceDetails;
import com.sarvah.mbg.domain.ordermgmt.payment.PaymentDetails;
import com.sarvah.mbg.domain.ordermgmt.payment.PaymentType;
import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;
import com.sarvah.mbg.domain.ordermgmt.shipping.ShippingType;
import com.sarvah.mbg.domain.sms.ItemInfoForCommunication;
import com.sarvah.mbg.domain.user.UserInfo;
import com.sarvah.mbg.notification.service.impl.NotificationServiceImpl;
import com.sarvah.mbg.product.dao.mongo.ProductNotOnBoardedNameStoreDAO;
import com.sarvah.mbg.store.dao.mongo.StoreDAO;
import com.sarvah.mbg.userprofile.dao.jpa.OrderAuditRepository;
import com.sarvah.mbg.userprofile.dao.jpa.OrderAuditTypeRepository;
import com.sarvah.mbg.userprofile.dao.jpa.OrderStatementRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserInvoiceRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserItemStatusRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserOrderAddressRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserOrderDetailRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserOrderRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserOrderStatusRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserPaymentDetailsRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserPaymentTypeRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserShippingTypeRepository;
import com.sarvah.mbg.userprofile.dao.mongo.OrderManageProductDAO;
import com.sarvah.mbg.userprofile.dao.mongo.StoreProductPricingDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;
import com.sarvah.mbg.userprofile.dao.role.mongo.UserPackageDAO;
import com.sarvah.mbg.userprofile.ordermgmt.model.ItemCreateRequestParam;
import com.sarvah.mbg.userprofile.ordermgmt.model.OrderCreateRequestParam;
import com.sarvah.mbg.userprofile.ordermgmt.model.OrderStatementCreateRequestParam;
import com.sarvah.mbg.userprofile.ordermgmt.model.PaymentCreateRequestParam;
import com.sarvah.mbg.userprofile.response.CustomerOrdersSummaryResponse;
import com.sarvah.mbg.userprofile.response.GetUserOrderItemsResponse;
import com.sarvah.mbg.userprofile.response.GetUserOrderResponse;
import com.sarvah.mbg.userprofile.response.ItemUpdateResponse;
import com.sarvah.mbg.userprofile.response.ManageOrderResponse;
import com.sarvah.mbg.userprofile.response.OrderAuditResponse;
import com.sarvah.mbg.userprofile.response.OrderManageView;
import com.sarvah.mbg.userprofile.response.OrderSummaryResponse;
import com.sarvah.mbg.userprofile.response.PaymentDetailsResponse;
import com.sarvah.mbg.userprofile.response.SuperAdminOrderViewDetails;
import com.sarvah.mbg.userprofile.response.TrackOrderStatusCount;
import com.sarvah.mbg.userprofile.response.TrackOrderSummary;
import com.sarvah.mbg.userprofile.service.UserOrderService;
import com.sarvah.mbg.userprofile.service.impl.UserServiceImpl.StoreProductCount;

/**
 * @author shivu
 *
 */
@Service
public class UserOrderServiceImpl implements UserOrderService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Autowired
	private UserShippingTypeRepository shippingTypeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserOrderStatusRepository userOrderStatusRepository;

	@Autowired
	private UserOrderAddressRepository userOrderAddressRepository;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UserOrderDetailRepository userOrderDetailRepository;

	@Autowired
	private UserItemStatusRepository userItemStatusRepository;

	@Autowired
	private UserOrderRepository userOrderRepository;

	@Autowired
	private StoreDAO storeDAO;

	@Autowired
	private OrderManageProductDAO productDAO;

	@Autowired
	private OrderAuditRepository orderAuditRepository;

	@Autowired
	private OrderAuditTypeRepository orderAuditTypeRepository;

	@Autowired
	private UserCommunicationService userCommunicationService;

	@Autowired
	private UserPackageDAO userPackageDAO;

	@Autowired
	private NotificationServiceImpl notificationServiceImpl;

	@Autowired
	private UserPaymentDetailsRepository userPaymentDetailsRepository;

	@Autowired
	private UserPaymentTypeRepository userPaymentTypeRepository;

	@Autowired
	private StoreProductPricingDAO storeProductPricingDAO;

	@Autowired
	private UserInvoiceRepository invoiceRepository;

	@Autowired
	private OrderStatementRepository orderStatementRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	private ProductNotOnBoardedNameStoreDAO productNotOnBoardedNameStoreDAO;

	/**
	 * Method for create order
	 * 
	 * @param orderCreateRequestParam
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	@Override
	@Transactional
	public GetUserOrderResponse createOrder(String userId,
			OrderCreateRequestParam orderCreateRequestParam) throws Exception {
		String customerName = null;
		String customerZipcode = null;
		String roleName = null;
		// Creating order
		// Checking the input to decide whether the order is on behaif of
		// customer or customer only directly placing an order.
		if ((StringUtils.isNotBlank(orderCreateRequestParam.getOrderByMBG()))
				&& orderCreateRequestParam.getOrderByMBG().equalsIgnoreCase(
						"True")) {
			// Log for creating order by MBG on behalf of customer(end user)
			logger.info("Creating order for userId : {} and firstName : {}",
					orderCreateRequestParam.getForUserId(),
					orderCreateRequestParam.getCreatedby());
		} else {
			// Log for creating order by customer(end user)
			logger.info("Creating order for userId : {} and firstName : {}",
					userId, orderCreateRequestParam.getCreatedby());
		}

		Order order = null;

		if (StringUtils.isNotBlank(userId)) {
			Set<User> userSet = new HashSet<User>();
			Set<User> dealerSet = new HashSet<User>();
			User user = userDAO.findOne(userId);

			// Taking admin details to send order created info
			User adminuser = userDAO.findByRoles_NameIsIgnoreCase("SUPERADMIN");

			if (user != null) {
				order = new Order();
				// fetching shipping type
				ShippingType shippingTypeObj = shippingTypeRepository
						.findByShippingType(orderCreateRequestParam
								.getShippingType());
				order.setShippingType(shippingTypeObj);

				// Checking the input to decide whether the order is on behaif
				// of customer or customer only directly placing an order.
				if ((StringUtils.isNotBlank(orderCreateRequestParam
						.getOrderByMBG()))
						&& orderCreateRequestParam.getOrderByMBG()
								.equalsIgnoreCase("True")) {
					// MBG Guy placeing order behaif of customer
					// Taking customer details to send order created info
					User enduser = userDAO.findOne(orderCreateRequestParam
							.getForUserId());
					userSet.add(enduser);

					UserInfo userInfo = getCustomerFromOrderPlaceByMbg(orderCreateRequestParam
							.getForUserId());

					order.setUserInfo(userInfo);
					// name
					if (enduser.getFirstName() != null
							&& enduser.getLastName() != null) {
						customerName = enduser.getFirstName() + " "
								+ enduser.getLastName();
					} else if (enduser.getFirstName() != null) {
						customerName = enduser.getFirstName();
					}

					Set<Role> roles = enduser.getRoles();
					for (Role role : roles) {
						roleName = role.getName();
					}

				}
				// Customer placeing order
				else {
					// Taking customer details to send order created info
					userSet.add(user);
					UserInfo userInfo = userRepository
							.findByMongoUserId(userId);
					order.setUserInfo(userInfo);

					if (user.getFirstName() != null
							&& user.getLastName() != null) {
						customerName = user.getFirstName() + " "
								+ user.getLastName();
					} else if (user.getFirstName() != null) {
						customerName = user.getFirstName();
					}

					Set<Role> roles = user.getRoles();
					for (Role role : roles) {
						roleName = role.getName();
					}
				}

				List<OrderDetails> itemList = null;
				// Ordering for package
				if (StringUtils.isNotBlank(orderCreateRequestParam.getType())
						&& orderCreateRequestParam.getType().equalsIgnoreCase(
								"Package")) {
					itemList = getPackage(orderCreateRequestParam, order);

				}
				// Ordering for items
				else {
					itemList = getItems(orderCreateRequestParam, order);
				}

				// Setting item info
				order.setItems(itemList);
				int itemCount = itemList.size();

				// Get dealer details to send order created info
				Set<User> dealers = getDealers(orderCreateRequestParam);
				if (dealers != null) {
					dealerSet.addAll(dealers);
				}

				// Setting order status info
				OrderStatus orderStatusObj = userOrderStatusRepository
						.findByDescription("Placed");
				order.setOrderStatus(orderStatusObj);

				// Setting shipping address
				OrderAddress shippingAddress = getShippingAddress(orderCreateRequestParam);
				order.setShippingAddress(shippingAddress);

				// zipcode
				if (shippingAddress.getPincode() != 0) {
					customerZipcode = String.valueOf(shippingAddress
							.getPincode());
				} else {
					customerZipcode = "zipcode not given";
				}

				// Setting billing address
				OrderAddress billingAddress = getBillingAddress(orderCreateRequestParam);
				order.setBillingAddress(billingAddress);

				// Setting total savings
				order.setTotalSavings(orderCreateRequestParam.getTotalSavings());

				// Order level shipping chrg
				order.setShippingCharge(orderCreateRequestParam
						.getShippingCharge());

				// Extra Benefits
				order.setExtraBenefits(orderCreateRequestParam
						.getExtraBenefits());

				// Setting createdBy and modifiedBy
				order.setCreateBy(orderCreateRequestParam.getCreatedby());
				order.setLastModifiedBy(orderCreateRequestParam.getModifiedby());

				// Setting createdDate and modifiedDate
				order.setOrderedDate(new Date());
				order.setLastmodifiedDate(new Date());

				userOrderRepository.save(order);

				String paymentTypeForMBGOrderIdFormat = null;

				Set<PaymentCreateRequestParam> paymentTypes = orderCreateRequestParam
						.getPaymentTypes();

				int count = 0;
				boolean fundTransfer = false;
				double totalOrderPrice1 = 0.0;
				double ftTotalOrderPrice = 0.0;
				for (PaymentCreateRequestParam paymentType : paymentTypes) {
					if (paymentType.getPaymentTypeName() != null) {
						if (count == 0) {
							if (paymentType.getPaymentTypeName()
									.equalsIgnoreCase("DOC")) {
								paymentTypeForMBGOrderIdFormat = "CD";
							} else if (paymentType.getPaymentTypeName()
									.equalsIgnoreCase("NetBanking/Card")) {
								paymentTypeForMBGOrderIdFormat = "NB";
							} else if (paymentType.getPaymentTypeName()
									.equalsIgnoreCase("Fundtransfer")) {
								paymentTypeForMBGOrderIdFormat = "FT";
								fundTransfer = true;
								ftTotalOrderPrice = Double.valueOf(paymentType
										.getAmount());
							} else if (paymentType.getPaymentTypeName()
									.equalsIgnoreCase("BuildonnWallet")) {
								paymentTypeForMBGOrderIdFormat = "WA";
							} else if (paymentType.getPaymentTypeName()
									.equalsIgnoreCase("Promotion")) {
								paymentTypeForMBGOrderIdFormat = "PR";
							} else if (paymentType.getPaymentTypeName()
									.equalsIgnoreCase("Cheque")) {
								paymentTypeForMBGOrderIdFormat = "CQ";
							} else if (paymentType.getPaymentTypeName()
									.equalsIgnoreCase("Cash")) {
								paymentTypeForMBGOrderIdFormat = "CH";
							}
							count++;
						} else {
							if (paymentType.getPaymentTypeName()
									.equalsIgnoreCase("DOC")) {
								paymentTypeForMBGOrderIdFormat = paymentTypeForMBGOrderIdFormat
										+ "CD";
							} else if (paymentType.getPaymentTypeName()
									.equalsIgnoreCase("NetBanking/Card")) {
								paymentTypeForMBGOrderIdFormat = paymentTypeForMBGOrderIdFormat
										+ "NB";
							} else if (paymentType.getPaymentTypeName()
									.equalsIgnoreCase("Fundtransfer")) {
								paymentTypeForMBGOrderIdFormat = paymentTypeForMBGOrderIdFormat
										+ "FT";
								fundTransfer = true;
								ftTotalOrderPrice = Double.valueOf(paymentType
										.getAmount());
							} else if (paymentType.getPaymentTypeName()
									.equalsIgnoreCase("BuildonnWallet")) {
								paymentTypeForMBGOrderIdFormat = paymentTypeForMBGOrderIdFormat
										+ "WA";
							} else if (paymentType.getPaymentTypeName()
									.equalsIgnoreCase("Promotion")) {
								paymentTypeForMBGOrderIdFormat = paymentTypeForMBGOrderIdFormat
										+ "PR";
							} else if (paymentType.getPaymentTypeName()
									.equalsIgnoreCase("Cheque")) {
								paymentTypeForMBGOrderIdFormat = "CQ";
							} else if (paymentType.getPaymentTypeName()
									.equalsIgnoreCase("Cash")) {
								paymentTypeForMBGOrderIdFormat = "CH";
							}
						}
						// Total order amount
						totalOrderPrice1 = totalOrderPrice1
								+ Double.valueOf(paymentType.getAmount());
					}
				}

				char[] roleArray = roleName.toCharArray();

				String mbgOrderId = "B" + roleArray[0] + roleArray[1]
						+ paymentTypeForMBGOrderIdFormat
						+ String.format("%05d", order.getOrderId());

				order.setMbgOrderId(mbgOrderId);

				userOrderRepository.save(order);
				// Order Placed successfully...
				logger.info("Order Placed successfully. orderId : {}",
						order.getOrderId());

				// Setting PaymentDetails
				savePaymentDetails(orderCreateRequestParam, order);

				// Creating order created notification
				logger.info(
						"Creating order created notification for the orderId : {}",
						order.getOrderId());
				notificationServiceImpl.createNotification(adminuser.getId(),
						"Order has been placed", "Order has been placed",
						"Manage Orders");

				// Fetching order status for order audit purpose
				String status = order.getOrderStatus().getDescription();

				// Creating order audit info
				logger.info("Creating order audit info for the userId : {}",
						order.getOrderId());
				updateOrderAudit(order, "", status, "");

				String itemName = null;
				String itemQuantityAndType = null;
				String itemQuantity = null;
				String itemQuantityType = null;
				if (itemList.size() == 1) {
					List<ItemCreateRequestParam> items = orderCreateRequestParam
							.getItems();
					for (ItemCreateRequestParam item : items) {
						itemName = item.getItemName();
						itemQuantity = String.valueOf(item.getQuantity());
						if (item.getQuantityType() != null) {
							if (item.getItemId() != null
									&& item.getItemId() != "") {
								itemQuantityAndType = String.valueOf(item
										.getQuantity())
										+ " "
										+ item.getQuantityType() + "(s)";

								itemQuantityType = item.getQuantityType()
										+ "(s)";
							} else {
								itemQuantityAndType = String.valueOf(item
										.getQuantity())
										+ " "
										+ item.getQuantityType();

								itemQuantityType = item.getQuantityType();
							}

						} else {
							itemQuantityAndType = String.valueOf(item
									.getQuantity());
							itemQuantityType = "qty type not given";
						}
					}
				}

				double totalSavings = orderCreateRequestParam.getTotalSavings();
				String shippingType = order.getShippingType().getShippingType();

				DecimalFormat df = new DecimalFormat("####0.00");

				List<ItemInfoForCommunication> itemInfoForCommunicationList = getItemInfo(order);

				// Now below code is not using so commented later will uncomment
				// List<User> teleUsers = userDAO
				// .findByRoles_Name("MBGTELEASSOCIATE");

				// List<User> techUsers = userDAO
				// .findByRoles_Name("MBGTECHASSOCIATE");

				// Sending mail and sms to Admin while order placeing time.
				userCommunicationService.sendPlacedOrderInfoToAdmin(adminuser,
						order.getMbgOrderId(), itemCount, itemName,
						itemQuantityAndType, df.format(totalOrderPrice1),
						customerName, customerZipcode, df.format(totalSavings),
						df.format(orderCreateRequestParam.getTaxAmt()),
						itemInfoForCommunicationList);

				// Sending mail to Dealer(s) while order placeing
				// time(Fundtransfer mode).
				userCommunicationService.sendPlacedOrderInfoToDealers(
						dealerSet, order.getMbgOrderId(), itemCount, itemName,
						itemQuantityAndType, df.format(totalOrderPrice1),
						customerName, customerZipcode, shippingType,
						itemInfoForCommunicationList);

				if (fundTransfer) {
					// Sending mail and sms to customer while order placeing
					// time(Fundtransfer mode).
					userCommunicationService.sendBankDetailsForFundTransfer(
							userSet, order.getMbgOrderId(), itemCount,
							itemName, itemQuantity, itemQuantityType,
							df.format(ftTotalOrderPrice), customerName,
							customerZipcode, df.format(totalSavings),
							df.format(orderCreateRequestParam.getTaxAmt()),
							itemInfoForCommunicationList);
				} else {
					// Sending mail and sms to customer while order placeing
					// time
					userCommunicationService.sendPlacedOrderInfoToCustomer(
							userSet, order.getMbgOrderId(), itemCount,
							itemName, itemQuantityAndType,
							df.format(totalOrderPrice1), customerName,
							customerZipcode, df.format(totalSavings),
							df.format(orderCreateRequestParam.getTaxAmt()),
							itemInfoForCommunicationList);
				}

				// Now below code is not using so commented later will uncomment
				// Sending mail and sms to TeleAssociate while order placeing
				// time
				// userCommunicationService.sendPlacedOrderInfoToTeleAssociate(
				// teleUsers, order.getMbgOrderId(), itemCount, itemName,
				// itemQuantityAndType, df.format(totalOrderPrice1),
				// customerName, customerZipcode, df.format(totalSavings),
				// itemInfoForCommunicationList);

				// Sending mail and sms to TechAssociate while order placeing
				// time
				// userCommunicationService.sendPlacedOrderInfoToTechAssociate(
				// techUsers, order.getMbgOrderId(), itemCount, itemName,
				// itemQuantityAndType, df.format(totalOrderPrice1),
				// customerName, customerZipcode, df.format(totalSavings),
				// itemInfoForCommunicationList);

			} else {
				logger.error("User not exist for the userId : {}", userId);
				throw new Exception("User not exist for the userId : " + userId);
			}
		} else {
			logger.error("userId should not be empty. userId : {}", userId);
			throw new Exception("userId should not be empty. userId : "
					+ userId);
		}

		// Setting order created response object
		GetUserOrderResponse getUserOrderResponse = getUserOrderResponse(order);

		// Returning order created response object
		return getUserOrderResponse;
	}

	private List<ItemInfoForCommunication> getItemInfo(Order order) {
		List<ItemInfoForCommunication> itemInfoForCommunicationList = new ArrayList<ItemInfoForCommunication>();
		List<OrderDetails> items = order.getItems();
		DecimalFormat df = new DecimalFormat("####0.00");
		for (OrderDetails item : items) {
			ItemInfoForCommunication itemInfoForCommunication = new ItemInfoForCommunication();
			itemInfoForCommunication.setItemId(item.getItemId());
			itemInfoForCommunication.setItemName(item.getItemName());
			itemInfoForCommunication.setItemQuantity(item.getQuantity());
			itemInfoForCommunication.setItemUnitPrice(df.format(item
					.getSellingPrice()));
			String shippingCharge = null;
			if (item.getShippingCharge() == 0.0) {
				shippingCharge = "Free";
			} else {
				shippingCharge = df.format(item.getShippingCharge());
			}
			itemInfoForCommunication.setItemShippingChrg(shippingCharge);
			itemInfoForCommunication.setMinDeliveryTimeInDays(item
					.getMinDeliveryTimeInDays());
			itemInfoForCommunication.setMaxDeliveryTimeInDays(item
					.getMaxDeliveryTimeInDays());

			Product product = productDAO.findOne(item.getItemId());

			if (product != null) {
				ProductAsset assets = product.getAssets();
				if (assets != null) {
					List<Image> images = assets.getImages();
					for (Image image : images) {
						itemInfoForCommunication.setItemImage(image.getUrl());
						break;
					}
				}
			}

			if (product != null) {
				itemInfoForCommunication.setItemQuantityType(product
						.getQuantityType().getName());
			}
			double itemTotalPrice = item.getSellingPrice() * item.getQuantity();
			itemInfoForCommunication.setItemTotalPrice(df
					.format(itemTotalPrice));
			itemInfoForCommunicationList.add(itemInfoForCommunication);
		}

		return itemInfoForCommunicationList;
	}

	private GetUserOrderResponse getUserOrderResponse(Order order) {
		// Setting and returning order created response info
		GetUserOrderResponse getUserOrderResponse = new GetUserOrderResponse();
		// OrderId
		getUserOrderResponse.setOrderId(order.getMbgOrderId());
		// ShippingType
		getUserOrderResponse.setShippingType(order.getShippingType()
				.getShippingType());
		// Ordered customer first name
		getUserOrderResponse.setFirstName(order.getUserInfo().getFirstname());
		// Ordered customer last name
		getUserOrderResponse.setLastName(order.getUserInfo().getLastname());
		// Ordered customer username(emailId)
		getUserOrderResponse.setEmailAddress(order.getUserInfo().getUsername());
		// Order status
		getUserOrderResponse.setOrderStatus(order.getOrderStatus()
				.getDescription());

		List<OrderDetails> orderDetailsList = null;
		orderDetailsList = order.getItems();
		Set<GetUserOrderItemsResponse> itemset = new HashSet<>();
		// Setting item level information
		for (OrderDetails orderDetails : orderDetailsList) {
			GetUserOrderItemsResponse getUserOrderItemsResponse = new GetUserOrderItemsResponse();
			// Item Serial number
			getUserOrderItemsResponse.setItemId(orderDetails
					.getOrderDetailsId());
			// ItemId(productId)
			getUserOrderItemsResponse.setPid(orderDetails.getItemId());
			// ItemName
			getUserOrderItemsResponse.setItemName(orderDetails.getItemName());
			// Quantity
			getUserOrderItemsResponse.setQuantity(orderDetails.getQuantity());
			// Mrp
			getUserOrderItemsResponse.setMrp(orderDetails.getMrp());
			// Tax
			getUserOrderItemsResponse.setTax(orderDetails.getTax());
			// SellingPrice
			getUserOrderItemsResponse.setSellingPrice(orderDetails
					.getSellingPrice());
			// Shipping Charge
			getUserOrderItemsResponse.setShippingCharge(orderDetails
					.getShippingCharge());
			// Item Price
			getUserOrderItemsResponse.setTotalPrice(orderDetails
					.getTotalPrice());
			// DealerId
			getUserOrderItemsResponse.setDealerId(orderDetails.getDealerId());
			itemset.add(getUserOrderItemsResponse);
		}
		getUserOrderResponse.setItems(itemset);
		return getUserOrderResponse;
	}

	private void savePaymentDetails(
			OrderCreateRequestParam orderCreateRequestParam, Order order) {
		// Returning payment Details
		PaymentDetails paymentDetails = null;
		Set<PaymentCreateRequestParam> paymentTypes = orderCreateRequestParam
				.getPaymentTypes();
		for (PaymentCreateRequestParam paymentType : paymentTypes) {
			if (paymentType != null) {
				PaymentType paymentTypeObj = userPaymentTypeRepository
						.findByPaymentType(paymentType.getPaymentTypeName());
				if (paymentTypeObj != null) {
					paymentDetails = new PaymentDetails();
					// Payment Type
					paymentDetails.setPaymentType(paymentTypeObj);
					// Order info
					paymentDetails.setOrder(order);
					// Total order price
					paymentDetails.setAmount(Double.valueOf(paymentType
							.getAmount()));
					// currency
					paymentDetails.setCurrency("Rupee");
					// Payment status
					if (StringUtils.isNotBlank(paymentType.getPaymentStatus())) {
						if (paymentType.getPaymentStatus().equalsIgnoreCase(
								"Sucess")) {
							paymentDetails.setPaymentStatus(1);
						}
					}
					// CreatedDate
					paymentDetails.setPaymentDate(new Date());
					// ModifiedDate
					paymentDetails.setLastModifiedDate(new Date());
					// created By
					paymentDetails.setCreatedBy(orderCreateRequestParam
							.getCreatedby());
					// modified By
					paymentDetails.setLastModifiedBy(orderCreateRequestParam
							.getModifiedby());
					if (paymentType.getPaymentTypeName().equalsIgnoreCase(
							"Promotion")) {
						if (paymentType.getPromoName() != null
								&& paymentType.getProductId() != null
								&& paymentType.getDiscount() != null
								&& paymentType.getPromoCode() != null) {

							String metadata = paymentType.getPromoName() + ":"
									+ paymentType.getProductId() + ":"
									+ paymentType.getDiscount() + ":"
									+ paymentType.getPromoCode();

							paymentDetails.setPaymentMetadata(metadata);
						}
					}
					userPaymentDetailsRepository.save(paymentDetails);
				}
			}
		}
	}

	// Now we are not using below method, If needed will use this
	// private double getTotalOrderAmount(
	// OrderCreateRequestParam orderCreateRequestParam) {
	// // Returning total order price
	// double totalOrderAmount = 0;
	// double totalItemAmount = 0;
	// Set<ItemCreateRequestParam> items = orderCreateRequestParam.getItems();
	// for (ItemCreateRequestParam itemCreateRequestParam : items) {
	// totalItemAmount = (((itemCreateRequestParam.getQuantity()) *
	// (itemCreateRequestParam
	// .getSellingPrice())) + (itemCreateRequestParam
	// .getShippingCharge()));
	// totalOrderAmount = totalOrderAmount + totalItemAmount;
	// }
	// return totalOrderAmount;
	// }

	private Set<User> getDealers(OrderCreateRequestParam orderCreateRequestParam) {
		// Returning dealers details to send order created info.
		Set<User> dealerSet = new HashSet<>();
		Set<String> dealersIds = new HashSet<>();
		List<ItemCreateRequestParam> items = orderCreateRequestParam.getItems();
		for (ItemCreateRequestParam itemCreateRequestParam : items) {
			User dealer = userDAO
					.findById(itemCreateRequestParam.getDealerId());

			if (dealer != null) {
				if (dealerSet.size() != 0) {
					if (!dealersIds.contains(dealer.getId())) {
						dealerSet.add(dealer);
						dealersIds.add(dealer.getId());
					}
				} else {
					dealerSet.add(dealer);
					dealersIds.add(dealer.getId());
				}
			}
		}
		return dealerSet;
	}

	private OrderAddress getBillingAddress(
			OrderCreateRequestParam orderCreateRequestParam) {
		// Returning billing address
		OrderAddress billingAddress = new OrderAddress();
		billingAddress.setName(orderCreateRequestParam.getBillingName());
		if (StringUtils.isNotBlank(orderCreateRequestParam
				.getBillingContactPersonName())) {
			billingAddress.setContactPersonName(orderCreateRequestParam
					.getBillingContactPersonName());
		}
		billingAddress.setAddressLine1(orderCreateRequestParam
				.getBillingAddressLine1());
		billingAddress.setAddressLine2(orderCreateRequestParam
				.getBillingAddressLine2());
		billingAddress.setCity(orderCreateRequestParam.getBillingCity());
		billingAddress.setState(orderCreateRequestParam.getBillingState());
		billingAddress.setCountry(orderCreateRequestParam.getBillingCountry());
		billingAddress.setPincode(orderCreateRequestParam.getBillingPincode());
		billingAddress.setContactNo(orderCreateRequestParam
				.getBillingContactNo());
		billingAddress.setCreateBy(orderCreateRequestParam.getCreatedby());
		billingAddress.setLastModifiedBy(orderCreateRequestParam
				.getModifiedby());
		billingAddress.setCreatedDate(new Date());
		billingAddress.setLastmodifiedDate(new Date());
		return billingAddress;
	}

	private OrderAddress getShippingAddress(
			OrderCreateRequestParam orderCreateRequestParam) {
		// Returning shipping address
		OrderAddress shippingAddress = new OrderAddress();
		shippingAddress.setName(orderCreateRequestParam.getShippingName());
		if (StringUtils.isNotBlank(orderCreateRequestParam
				.getShippingContactPersonName())) {
			shippingAddress.setContactPersonName(orderCreateRequestParam
					.getShippingContactPersonName());
		}
		shippingAddress.setAddressLine1(orderCreateRequestParam
				.getShippingAddressLine1());
		shippingAddress.setAddressLine2(orderCreateRequestParam
				.getShippingAddressLine2());
		shippingAddress.setCity(orderCreateRequestParam.getShippingCity());
		shippingAddress.setState(orderCreateRequestParam.getShippingState());
		shippingAddress
				.setCountry(orderCreateRequestParam.getShippingCountry());
		shippingAddress
				.setPincode(orderCreateRequestParam.getShippingPincode());
		shippingAddress.setContactNo(orderCreateRequestParam
				.getShippingContactNo());
		shippingAddress.setCreateBy(orderCreateRequestParam.getCreatedby());
		shippingAddress.setLastModifiedBy(orderCreateRequestParam
				.getModifiedby());
		shippingAddress.setCreatedDate(new Date());
		shippingAddress.setLastmodifiedDate(new Date());
		return shippingAddress;
	}

	private List<OrderDetails> getItems(
			OrderCreateRequestParam orderCreateRequestParam, Order order) {
		// Ordering for items
		logger.info("Ordering for Product");
		double totalPrice = 0;
		List<ItemCreateRequestParam> items = orderCreateRequestParam.getItems();
		List<OrderDetails> itemList = new ArrayList<OrderDetails>();
		for (ItemCreateRequestParam itemCreateRequestParam : items) {
			logger.info("Ordering for items. itemId : {} and itemName : {}",
					itemCreateRequestParam.getItemId(),
					itemCreateRequestParam.getItemName());
			OrderDetails orderDetails = new OrderDetails();
			orderDetails.setOrder(order);
			// itemId
			orderDetails.setItemId(itemCreateRequestParam.getItemId());
			// itemName
			orderDetails.setItemName(itemCreateRequestParam.getItemName());

			if (StringUtils.isNotBlank(itemCreateRequestParam.getItemName())) {
				List<Product> prods = productDAO
						.findByNameLikeIgnoreCase(itemCreateRequestParam
								.getItemName());
				if (prods.size() == 0) {
					List<ProductNotOnBoardedNameStore> names = productNotOnBoardedNameStoreDAO
							.findByNameAllIgnoreCase(itemCreateRequestParam
									.getItemName());
					if (names.size() == 0) {
						ProductNotOnBoardedNameStore productNotOnBoardedNameStore = new ProductNotOnBoardedNameStore();
						productNotOnBoardedNameStore
								.setName(itemCreateRequestParam.getItemName());
						productNotOnBoardedNameStore.setOrderCount(1);
						productNotOnBoardedNameStoreDAO
								.save(productNotOnBoardedNameStore);
					} else {
						for (ProductNotOnBoardedNameStore name : names) {
							name.setOrderCount(name.getOrderCount() + 1);
							productNotOnBoardedNameStoreDAO.save(name);
						}
					}
				}
			}
			// quantity
			orderDetails.setQuantity(itemCreateRequestParam.getQuantity());
			// mrp
			orderDetails.setMrp(itemCreateRequestParam.getMrp());
			// tax
			orderDetails.setTax(itemCreateRequestParam.getTax());
			// selling price
			orderDetails.setSellingPrice(itemCreateRequestParam
					.getSellingPrice());
			// shipping charge
			if (orderCreateRequestParam.getShippingCharge() == 0) {
				orderDetails.setShippingCharge(itemCreateRequestParam
						.getShippingCharge());
			}
			// total item level price
			if (orderCreateRequestParam.getShippingCharge() == 0) {
				totalPrice = (((itemCreateRequestParam.getQuantity()) * (itemCreateRequestParam
						.getSellingPrice())) + (itemCreateRequestParam
						.getShippingCharge()));
			} else {
				totalPrice = (itemCreateRequestParam.getQuantity() * itemCreateRequestParam
						.getSellingPrice());
			}
			orderDetails.setTotalPrice(totalPrice);
			// dealerId
			orderDetails.setDealerId(itemCreateRequestParam.getDealerId());
			// createdBy
			orderDetails.setCreatedby(orderCreateRequestParam.getCreatedby());
			// modifiedBy
			orderDetails.setLastmodifiedby(orderCreateRequestParam
					.getModifiedby());
			// createdDate
			orderDetails.setCreatedDate(new Date());
			// modifiedDate
			orderDetails.setLastModifiedDate(new Date());
			// fetching item status
			ItemStatus itemStatusObj = userItemStatusRepository
					.findByDescription("Placed");
			orderDetails.setItemStatus(itemStatusObj);
			// minimum delivery time
			orderDetails.setMinDeliveryTimeInDays(itemCreateRequestParam
					.getMinDeliveryTimeInDays());
			// maximum delivery time
			orderDetails.setMaxDeliveryTimeInDays(itemCreateRequestParam
					.getMaxDeliveryTimeInDays());
			itemList.add(orderDetails);
		}
		return itemList;
	}

	private List<OrderDetails> getPackage(
			OrderCreateRequestParam orderCreateRequestParam, Order order) {
		// Ordering for package
		List<ItemCreateRequestParam> items = orderCreateRequestParam.getItems();
		List<OrderDetails> itemList = new ArrayList<OrderDetails>();
		for (ItemCreateRequestParam itemCreateRequestParam : items) {
			logger.info(
					"Ordering for Package. packageId : {} and packageName : {} and userId : {}",
					itemCreateRequestParam.getItemId(), itemCreateRequestParam
							.getItemName(), order.getUserInfo().getFirstname());
			OrderDetails orderDetails = new OrderDetails();
			UserPackage userPackage = userPackageDAO
					.findOne(itemCreateRequestParam.getItemId());

			if (userPackage != null) {

				orderDetails.setOrder(order);
				// packageId
				orderDetails.setItemId(itemCreateRequestParam.getItemId());
				// package name
				orderDetails.setItemName(itemCreateRequestParam.getItemName());
				// default value
				orderDetails.setQuantity(0);
				// mrp
				orderDetails.setMrp(itemCreateRequestParam.getMrp());
				// default value
				orderDetails.setTax(0.0);
				// default value
				orderDetails.setSellingPrice(0.0);
				// default value
				orderDetails.setShippingCharge(0.0);
				// total price is mrp
				orderDetails.setTotalPrice(itemCreateRequestParam.getMrp());
				// dealerId
				orderDetails.setDealerId(itemCreateRequestParam.getDealerId());
				// createdBy
				orderDetails.setCreatedby(orderCreateRequestParam
						.getCreatedby());
				// modifiedBy
				orderDetails.setLastmodifiedby(orderCreateRequestParam
						.getModifiedby());
				// createdDate
				orderDetails.setCreatedDate(new Date());
				// lastModifiedDate
				orderDetails.setLastModifiedDate(new Date());
				// fetching item status
				ItemStatus itemStatusObj = userItemStatusRepository
						.findByDescription("Placed");
				orderDetails.setItemStatus(itemStatusObj);
				// minimum delivery time
				orderDetails.setMinDeliveryTimeInDays(itemCreateRequestParam
						.getMinDeliveryTimeInDays());
				// maximum delivery time
				orderDetails.setMaxDeliveryTimeInDays(itemCreateRequestParam
						.getMaxDeliveryTimeInDays());
				itemList.add(orderDetails);
			}
		}
		return itemList;
	}

	private UserInfo getCustomerFromOrderPlaceByMbg(String forUserId)
			throws Exception {

		UserInfo userInfo = userRepository.findByMongoUserId(forUserId);

		if (userInfo != null) {
			return userInfo;
		} else {
			logger.error("User not exist in mysql DB for userId : {}",
					forUserId);
			throw new Exception("User not exist in mysql DB for userId : {}"
					+ forUserId);
		}
	}

	/**
	 * method for count orders
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Long getOrderCount(String userId) throws Exception {
		// Fetching customer order count
		logger.info("Fetching order count for the user with userId : {}",
				userId);
		Long orderCount = null;
		if (StringUtils.isNotBlank(userId)) {
			User mongoUser = userDAO.findOne(userId);
			UserInfo mysqlUser = userRepository.findByMongoUserId(userId);
			if (mongoUser != null && mysqlUser != null) {

				orderCount = userOrderRepository.countByUserInfo(mysqlUser);

			} else {
				logger.error("User is not exist in our system for userId : {}",
						userId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId);
			}
		}
		logger.info(
				"Fetching customer order count! Success. for the user with userId : {}",
				userId);
		return orderCount;
	}

	/**
	 * method for update order status
	 * 
	 * @param userId
	 * @param orderId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional
	public OrderStatus updateOrderStatus(String userId, String orderId,
			String paymentMethodName, String status, String paymentNumber,
			String paymentMetadata, String paymentStatus, String paymentAmount,
			String paymentDetailsId, String chequeDate, String bankName,
			String chequeAmount) throws Exception {
		// Updating user order status
		logger.info("Updating order status for the user with userId : {}",
				userId);
		logger.info(
				"Updating order status with these data userId : {} orderId : {} status : {} paymentNumber : {} paymentMetadata : {} paymentStatus : {}",
				userId, orderId, status, paymentNumber, paymentMetadata,
				paymentStatus);
		Order order = null;
		OrderStatus orderStatus = null;
		double totalOrderAmt = 0.0;
		double paymentDoneAmt = 0.0;
		double outStandingAmt = 0.0;
		if (StringUtils.isNotBlank(orderId) && StringUtils.isNotBlank(userId)) {
			order = userOrderRepository.findByMbgOrderId(orderId);
			User mongoUser = userDAO.findOne(userId);
			UserInfo mysqlUser = userRepository.findByMongoUserId(userId);
			// Cheching whether user is exist in our system or not
			if (order != null && mongoUser != null && mysqlUser != null) {

				if (StringUtils.isNotBlank(status)) {
					// Fetching order status
					orderStatus = userOrderStatusRepository
							.findByDescription(status);
					if (orderStatus != null) {

						order.setOrderStatus(orderStatus);
						userOrderRepository.save(order);

						// Saving order status update information through order
						// audit
						updateOrderAudit(order, "", status, "");

					} else {
						logger.error(
								"OrderStatus is null for status value : {}",
								status);
						throw new Exception(
								"OrderStatus is null for status value : "
										+ status);
					}

					if (status.equalsIgnoreCase("Cancelled")) {
						List<OrderDetails> items = order.getItems();
						for (OrderDetails item : items) {
							ItemStatus itemStatus = userItemStatusRepository
									.findByDescription(status);
							if (itemStatus != null) {
								item.setItemStatus(itemStatus);
							}
							userOrderDetailRepository.save(item);
							// Saving item status update information through
							// order
							// audit
							updateOrderAudit(order, item.getItemId(), status,
									"");
						}
					}
				}
				// Updating payment details
				Set<PaymentDetails> paymentDetailsSet = userPaymentDetailsRepository
						.findByOrderAndPaymentType_PaymentTypeIgnoreCase(order,
								paymentMethodName);

				PaymentDetails paymentDetails = null;

				if (paymentDetailsSet != null && paymentDetailsSet.size() != 0
						&& StringUtils.isNotBlank(paymentDetailsId)) {

					paymentDetails = userPaymentDetailsRepository
							.findOne(Integer.parseInt(paymentDetailsId));
					if (paymentDetails != null) {
						// Payment number
						if (StringUtils.isNotBlank(paymentNumber)) {
							paymentDetails.setPaymentNumber(paymentNumber);
						}

						// Payment metadata
						if (StringUtils.isNotBlank(paymentMetadata)) {
							paymentDetails.setPaymentMetadata(paymentMetadata);
						}

						// Payment status
						if (StringUtils.isNotBlank(paymentStatus)) {
							if (paymentStatus.equalsIgnoreCase("Sucess")) {
								paymentDetails.setPaymentStatus(1);
								// Setting Payment Done Date
								paymentDetails.setLastModifiedDate(new Date());
							} else {
								paymentDetails.setPaymentStatus(0);
							}
						}
						// Payment Amount
						if (StringUtils.isNotBlank(paymentAmount)) {
							paymentDetails.setAmount(Double
									.valueOf(paymentAmount));
						}

						// ChequeDate
						if (StringUtils.isNotBlank(chequeDate)) {
							paymentDetails.setChequeDate(chequeDate);
						}

						// BankName
						if (StringUtils.isNotBlank(bankName)) {
							paymentDetails.setBankName(bankName);
						}

						// Saving payment updated information
						userPaymentDetailsRepository.save(paymentDetails);

						// Getting total order payment amount
						Set<PaymentDetails> payments = order
								.getPaymentDetails();
						if (payments != null && payments.size() != 0) {
							for (PaymentDetails payment : payments) {
								if (payment.getPaymentStatus() == 1) {
									paymentDoneAmt = paymentDoneAmt
											+ payment.getAmount();
								}
							}
						}

						// Getting total order amount
						List<OrderDetails> items = order.getItems();
						for (OrderDetails item : items) {
							if (!item.getItemStatus().getDescription()
									.equalsIgnoreCase("UnConfirmed")
									&& !item.getItemStatus().getDescription()
											.equalsIgnoreCase("Returned")
									&& !item.getItemStatus().getDescription()
											.equalsIgnoreCase("Cancelled")) {
								if (item.getTax() != 0) {
									double itemTaxAmt = (item.getTax() * item
											.getTotalPrice()) / 100;
									totalOrderAmt = totalOrderAmt
											+ item.getTotalPrice() + itemTaxAmt
											+ item.getShippingCharge();
								} else {
									totalOrderAmt = totalOrderAmt
											+ item.getTotalPrice()
											+ item.getShippingCharge();
								}
							}
						}

						if (order.getShippingCharge() > 0) {
							totalOrderAmt = totalOrderAmt
									+ order.getShippingCharge();
						}

						// OutStandingAmt
						outStandingAmt = totalOrderAmt - paymentDoneAmt;
						// Payment done
						if (outStandingAmt <= 10) {
							order.setPaymentDone(true);
						}
						userOrderRepository.save(order);

						// Send OrderAmount update info to customer
						User customer = userDAO.findOne(order.getUserInfo()
								.getMongoUserId());

						if (customer != null
								&& paymentStatus.equalsIgnoreCase("Sucess")) {
							if (outStandingAmt == 0) {
								userCommunicationService
										.sendOrderAmountUpdateWithZeroOutstanding(
												customer, paymentAmount,
												outStandingAmt);
							} else {
								userCommunicationService
										.sendOrderAmountUpdateInfoToCustomer(
												customer, paymentAmount,
												outStandingAmt);
							}
						}

						if (!paymentStatus.equalsIgnoreCase("Sucess")
								&& StringUtils.isNotBlank(bankName)
								&& StringUtils.isNotBlank(paymentNumber)
								&& StringUtils.isNotBlank(chequeAmount)) {
							userCommunicationService
									.sendChequeCollectedInfoToCustomer(
											customer, bankName, paymentNumber,
											chequeDate, chequeAmount);
						}
					}
				} else {
					if (paymentMethodName.equalsIgnoreCase("NetBanking/Card")) {

						paymentDetailsSet = userPaymentDetailsRepository
								.findByOrderAndPaymentType_PaymentTypeIgnoreCase(
										order, "NetBanking/Card");

						for (PaymentDetails paymentDetail : paymentDetailsSet) {
							paymentDetails = paymentDetail;
						}

						if (paymentDetails != null) {
							// Payment number
							if (StringUtils.isNotBlank(paymentNumber)) {
								paymentDetails.setPaymentNumber(paymentNumber);
							}

							// Payment metadata
							if (StringUtils.isNotBlank(paymentMetadata)) {
								paymentDetails
										.setPaymentMetadata(paymentMetadata);
							}

							// Payment status
							if (StringUtils.isNotBlank(paymentStatus)) {
								if (paymentStatus.equalsIgnoreCase("Sucess")) {
									paymentDetails.setPaymentStatus(1);
									order.setPaymentDone(true);
									userOrderRepository.save(order);
									// Setting Payment Done Date
									paymentDetails
											.setLastModifiedDate(new Date());
								} else {
									paymentDetails.setPaymentStatus(0);
								}
							}
							// Payment Amount
							if (StringUtils.isNotBlank(paymentAmount)) {
								paymentDetails.setAmount(Double
										.valueOf(paymentAmount));
							}

							// Saving payment updated information
							userPaymentDetailsRepository.save(paymentDetails);
						}
					} else {
						PaymentType paymentTypeObj = userPaymentTypeRepository
								.findByPaymentType(paymentMethodName);

						paymentDetails = new PaymentDetails();
						// Payment Type
						paymentDetails.setPaymentType(paymentTypeObj);
						// Order info
						paymentDetails.setOrder(order);
						// Total order price
						if (StringUtils.isNotBlank(paymentAmount)) {
							paymentDetails.setAmount(Double
									.valueOf(paymentAmount));
						}
						// ChequeDate
						if (StringUtils.isNotBlank(chequeDate)) {
							paymentDetails.setChequeDate(chequeDate);
						}
						// BankName
						if (StringUtils.isNotBlank(bankName)) {
							paymentDetails.setBankName(bankName);
						}
						// currency
						paymentDetails.setCurrency("Rupee");

						// Payment Status
						if (StringUtils.isNotBlank(paymentStatus)) {
							if (paymentStatus.equalsIgnoreCase("Sucess")) {
								paymentDetails.setPaymentStatus(1);
							} else {
								paymentDetails.setPaymentStatus(0);
							}
						}
						// PaymentNumber
						if (StringUtils.isNotBlank(paymentNumber)) {
							paymentDetails.setPaymentNumber(paymentNumber);
						}
						// Payment Metadata
						if (StringUtils.isNotBlank(paymentMetadata)) {
							paymentDetails.setPaymentMetadata(paymentMetadata);
						}
						// created By
						paymentDetails.setCreatedBy("BuildOnn");
						// modified By
						paymentDetails.setLastModifiedBy("Buildonn");
						// Payment Date
						paymentDetails.setPaymentDate(new Date());
						// LastModified Date
						paymentDetails.setLastModifiedDate(new Date());

						userPaymentDetailsRepository.save(paymentDetails);

						// Getting total order payment amount
						Set<PaymentDetails> payments = order
								.getPaymentDetails();
						if (payments != null && payments.size() != 0) {
							for (PaymentDetails payment : payments) {
								if (payment.getPaymentStatus() == 1) {
									paymentDoneAmt = paymentDoneAmt
											+ payment.getAmount();
								}
							}
						}

						// Getting total order amount
						List<OrderDetails> items = order.getItems();
						for (OrderDetails item : items) {
							if (!item.getItemStatus().getDescription()
									.equalsIgnoreCase("UnConfirmed")
									&& !item.getItemStatus().getDescription()
											.equalsIgnoreCase("Returned")
									&& !item.getItemStatus().getDescription()
											.equalsIgnoreCase("Cancelled")) {
								if (item.getTax() != 0) {
									double itemTaxAmt = (item.getTax() * item
											.getTotalPrice()) / 100;
									totalOrderAmt = totalOrderAmt
											+ item.getTotalPrice() + itemTaxAmt
											+ item.getShippingCharge();
								} else {
									totalOrderAmt = totalOrderAmt
											+ item.getTotalPrice()
											+ item.getShippingCharge();
								}
							}
						}
						if (order.getShippingCharge() > 0) {
							totalOrderAmt = totalOrderAmt
									+ order.getShippingCharge();
						}

						// OutStandingAmt
						outStandingAmt = totalOrderAmt - paymentDoneAmt;
						// Payment done
						if (outStandingAmt <= 10) {
							order.setPaymentDone(true);
						}
						userOrderRepository.save(order);

						// Send OrderAmount update info to customer
						User customer = userDAO.findOne(order.getUserInfo()
								.getMongoUserId());

						if (customer != null
								&& paymentStatus.equalsIgnoreCase("Sucess")) {
							if (outStandingAmt == 0) {
								userCommunicationService
										.sendOrderAmountUpdateWithZeroOutstanding(
												customer, paymentAmount,
												outStandingAmt);
							} else {
								userCommunicationService
										.sendOrderAmountUpdateInfoToCustomer(
												customer, paymentAmount,
												outStandingAmt);
							}
						}

						if (!paymentStatus.equalsIgnoreCase("Sucess")
								&& StringUtils.isNotBlank(bankName)
								&& StringUtils.isNotBlank(paymentNumber)
								&& StringUtils.isNotBlank(chequeAmount)) {
							userCommunicationService
									.sendChequeCollectedInfoToCustomer(
											customer, bankName, paymentNumber,
											chequeDate, chequeAmount);
						}
					}
				}

			} else {
				logger.error(
						"User is not exist in our system for the userId : {} or order is not exist in our system for orderId : {}",
						userId, orderId);

				throw new Exception(
						"Status info is blank User is not exist in our system for the userId : "
								+ userId);
			}
		} else {
			logger.error(
					"OrderId, userId should not be blank. userId : {} orderId : {}",
					userId, orderId);

			throw new Exception(
					"OrderId and userId should not be blank. userId : "
							+ userId + "orderId : " + orderId);
		}
		logger.info(
				"OrderStatus is updated successfully for the order with orderId : {}",
				order.getOrderId());
		return orderStatus;
	}

	@Override
	@Transactional
	public User updateOrdersPayment(String userId, String orderIds,
			String paymentAmount, String paymentMetadata) throws Exception {

		User mongoUser = null;
		UserInfo mysqlUser = null;
		double payableAmt = Double.parseDouble(paymentAmount);

		if (StringUtils.isNotBlank(userId) && orderIds != null
				&& StringUtils.isNotBlank(paymentAmount)) {
			mongoUser = userDAO.findOne(userId);
			mysqlUser = userRepository.findByMongoUserId(userId);
			String[] orderIdsAry = orderIds.split(",");
			int count = 0;
			// Cheching whether user is exist in our system or not
			if (mongoUser != null && mysqlUser != null) {
				for (String orderId : orderIdsAry) {
					double totalOrderAmt = 0.0;
					double paymentNotDoneAmt = 0.0;
					double paymentDoneAmt = 0.0;
					double outStandingAmt = 0.0;
					Order order = userOrderRepository.findByMbgOrderId(orderId);
					if (order != null) {
						count++;
						if (order.getPaymentDetails() != null) {

							// Getting total order amount
							List<OrderDetails> items = order.getItems();
							for (OrderDetails item : items) {
								if (!item.getItemStatus().getDescription()
										.equalsIgnoreCase("UnConfirmed")
										&& !item.getItemStatus()
												.getDescription()
												.equalsIgnoreCase("Returned")
										&& !item.getItemStatus()
												.getDescription()
												.equalsIgnoreCase("Cancelled")) {
									if (item.getTax() != 0) {
										double itemTaxAmt = (item.getTax() * item
												.getTotalPrice()) / 100;
										totalOrderAmt = totalOrderAmt
												+ item.getTotalPrice()
												+ itemTaxAmt
												+ item.getShippingCharge();
									} else {
										totalOrderAmt = totalOrderAmt
												+ item.getTotalPrice()
												+ item.getShippingCharge();
									}
								}
							}

							if (order.getShippingCharge() > 0) {
								totalOrderAmt = totalOrderAmt
										+ order.getShippingCharge();
							}

							// Getting total order payment not done amount
							Set<PaymentDetails> payments = order
									.getPaymentDetails();
							if (payments != null) {
								for (PaymentDetails payment : payments) {
									if (payment.getPaymentStatus() == 0) {
										paymentNotDoneAmt = paymentNotDoneAmt
												+ payment.getAmount();
									}
								}
							}

							if (paymentNotDoneAmt > 0
									&& totalOrderAmt - paymentNotDoneAmt > 0) {

								// Payment
								Set<PaymentDetails> paymentDetails = order
										.getPaymentDetails();
								for (PaymentDetails paymentDetail : paymentDetails) {
									// Payment metadata
									if (StringUtils.isNotBlank(paymentMetadata)) {
										paymentDetail
												.setPaymentMetadata(paymentMetadata);
									}

									// Payment Amount
									if (payableAmt >= paymentNotDoneAmt) {
										paymentDetail.setAmount(totalOrderAmt);
										payableAmt = payableAmt
												- paymentNotDoneAmt;
									} else {
										paymentDetail
												.setAmount(payableAmt
														+ (totalOrderAmt - paymentNotDoneAmt));
										payableAmt = 0;
									}
									// Payment status
									paymentDetail.setPaymentStatus(1);

									// Last Modified Date
									paymentDetail
											.setLastModifiedDate(new Date());

									// Saving payment updated information
									userPaymentDetailsRepository
											.save(paymentDetails);
								}
							} else {
								double tempAmt = payableAmt;
								payableAmt = payableAmt - paymentNotDoneAmt;

								if (payableAmt >= 0) {
									// Payment
									Set<PaymentDetails> paymentDetails = order
											.getPaymentDetails();
									for (PaymentDetails paymentDetail : paymentDetails) {
										// Payment metadata
										if (StringUtils
												.isNotBlank(paymentMetadata)) {
											paymentDetail
													.setPaymentMetadata(paymentMetadata);
										}

										// PaymentAmt
										if (orderIdsAry.length != count) {
											paymentDetail
													.setAmount(totalOrderAmt);
										} else {
											paymentDetail.setAmount(tempAmt);
										}

										// Payment status
										paymentDetail.setPaymentStatus(1);

										// Last Modified Date
										paymentDetail
												.setLastModifiedDate(new Date());

										// Saving payment updated information
										userPaymentDetailsRepository
												.save(paymentDetails);
									}
								} else {
									if (tempAmt > 0) {
										// Payment
										Set<PaymentDetails> paymentDetails = order
												.getPaymentDetails();
										for (PaymentDetails paymentDetail : paymentDetails) {
											// Payment metadata
											if (StringUtils
													.isNotBlank(paymentMetadata)) {
												paymentDetail
														.setPaymentMetadata(paymentMetadata);
											}
											// Payment Amount
											paymentDetail.setAmount(tempAmt);
											// Payment status
											paymentDetail.setPaymentStatus(1);
											// Last Modified Date
											paymentDetail
													.setLastModifiedDate(new Date());
											// Saving payment updated
											// information
											userPaymentDetailsRepository
													.save(paymentDetails);
										}
									}
								}
							}
							// Getting total order payment amount
							Set<PaymentDetails> paymentDetails1 = order
									.getPaymentDetails();
							if (paymentDetails1 != null
									&& paymentDetails1.size() != 0) {
								for (PaymentDetails paymentDetail1 : paymentDetails1) {
									if (paymentDetail1.getPaymentStatus() == 1) {
										paymentDoneAmt = paymentDoneAmt
												+ paymentDetail1.getAmount();
									}
								}
							}

							// OutStandingAmt
							outStandingAmt = totalOrderAmt - paymentDoneAmt;
							// Payment done
							if (outStandingAmt <= 10) {
								order.setPaymentDone(true);
							}
							userOrderRepository.save(order);
						}
					}
				}
			} else {
				throw new Exception("User should not be null");
			}
		} else {
			throw new Exception(
					"User id, Order(s) id and Payment Amount should not be null");
		}
		return mongoUser;
	}

	@Override
	public String updateOrder(String userId, String orderId, String invoiceNum) {
		Order order = null;
		if (StringUtils.isNotBlank(orderId) && StringUtils.isNotBlank(userId)
				&& StringUtils.isNotBlank(invoiceNum)) {
			order = userOrderRepository.findByMbgOrderId(orderId);
			if (order != null) {
				order.setInvoiceNumber(invoiceNum);
				userOrderRepository.save(order);
			}
		}
		return orderId;
	}

	/**
	 * method for get order address
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<OrderAddress> getAddress(String userId, String orderId)
			throws Exception {
		logger.info(
				"Fetching order address of the customer with customerId : {} and orderId : {}",
				userId, orderId);
		Order order = null;
		User mongoUser = null;
		UserInfo mysqlUser = null;
		List<OrderAddress> orderAddress = new ArrayList<>();
		// Checking whether userId and orderId blank or not
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(orderId)) {
			order = userOrderRepository.findByMbgOrderId(orderId);
			// Checking order is null or not. If order is null no need to fetch
			// user
			if (order != null) {
				mongoUser = userDAO.findOne(userId);
				mysqlUser = userRepository.findByMongoUserId(userId);
			}
			// Cheching customer is exist in our system or not
			if (mongoUser != null && mysqlUser != null && order != null) {

				orderAddress.add(order.getBillingAddress());
				orderAddress.add(order.getShippingAddress());

			} else {
				logger.error(
						"User is not exist in our system for userId : {} or order is null for the orderId : {}",
						userId, orderId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId + " "
								+ "or order is null for the orderId : "
								+ orderId);
			}
		} else {
			logger.error(
					"userId and orderId should not be empty. userId : {} orderId : {}",
					userId, orderId);
			throw new Exception(
					"userId and orderId should not be empty. userId : "
							+ userId + " " + "orderId : " + orderId);
		}
		logger.info(
				"Fetching order address of the customer with customerId : {} and orderId : {}! Success",
				userId, orderId);
		return orderAddress;
	}

	/**
	 * method for update user order address based on addressId.
	 * 
	 * @param userId
	 * @param orderId
	 * @param aid
	 * @param orderAddressUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional
	public OrderAddress updateAddress(String userId, String orderId,
			String name, String contactPersonName, String aid,
			String addressLine1, String addressLine2, String city,
			String state, String country, String pincode, String contactNo)
			throws Exception {
		// Updating order address based on addressId
		logger.info(
				"Updating customer order address for customerId : {} and orderId : {}",
				userId, orderId);
		Order order = null;
		User mongoUser = null;
		UserInfo mysqlUser = null;
		OrderAddress orderAddress = null;
		// Checking userId, oredrId and addressId empty or not
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(orderId)
				&& StringUtils.isNotBlank(aid)) {
			order = userOrderRepository.findByMbgOrderId(orderId);
			// Checking order is null or not. If order is null then no need to
			// fetch user
			if (order != null) {
				mongoUser = userDAO.findOne(userId);
				mysqlUser = userRepository.findByMongoUserId(userId);
				orderAddress = userOrderAddressRepository.findOne(Integer
						.parseInt(aid));
			}
			// Checking customer and his order and orderAddress exist in our
			// system or not
			if (mongoUser != null && mysqlUser != null && order != null
					&& orderAddress != null) {
				// name
				if (StringUtils.isNotBlank(name)) {
					orderAddress.setName(name);
				}
				// Contact person name
				if (StringUtils.isNotBlank(contactPersonName)) {
					orderAddress.setContactPersonName(contactPersonName);
				}
				// Address Line1
				if (StringUtils.isNotBlank(addressLine1)) {
					orderAddress.setAddressLine1(addressLine1);
				}
				// Address Line2
				if (StringUtils.isNotBlank(addressLine2)) {
					orderAddress.setAddressLine2(addressLine2);
				}
				// City
				if (StringUtils.isNotBlank(city)) {
					orderAddress.setCity(city);
				}
				// State
				if (StringUtils.isNotBlank(state)) {
					orderAddress.setState(state);
				}
				// Country
				if (StringUtils.isNotBlank(country)) {
					orderAddress.setCountry(country);
				}
				// Pincode
				if (StringUtils.isNotBlank(pincode)) {
					orderAddress.setPincode(Integer.parseInt(pincode));
				}
				// Contact number
				if (StringUtils.isNotBlank(contactNo)) {
					orderAddress.setContactNo(contactNo);
				}

			} else {
				logger.error(
						"User is not exist in our system for userId : {} or order is null for orderId : {} or orderAddress is null for addressId : {}",
						userId, orderId, aid);

				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId + " "
								+ "or order is null for orderId : " + orderId
								+ ""
								+ "or orderAddress is null for addressId : "
								+ aid);
			}
		} else {
			logger.error(
					"userId, orderId and addressId should not be null. userId : {}  orderId : {}  addressId : {}",
					userId, orderId, aid);
			throw new Exception(
					"userId, orderId and addressId should not be null. userId : "
							+ userId + " " + "and orderId : " + orderId + " "
							+ "addressId : " + aid);
		}
		userOrderAddressRepository.save(orderAddress);
		logger.info(
				"Updating customer order address for customerId : {} and orderId : {}! Success",
				userId, orderId);
		return orderAddress;
	}

	/**
	 * Method to get all orders of particular user.
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@Override
	public TrackOrderSummary getUserOrders(String userid, int page, int size,
			Sort sort) throws Exception {
		logger.info("Fetching the orders based on customerId : {}", userid);
		TrackOrderSummary trackOrderSummary = new TrackOrderSummary();
		List<GetUserOrderResponse> orderDisplayResponseList = new ArrayList<>();
		Page<Order> pageable = null;
		List<com.sarvah.mbg.domain.ordermgmt.Order> orderList = new ArrayList<com.sarvah.mbg.domain.ordermgmt.Order>();
		UserInfo userInfo = null;
		Set<PaymentDetails> paymentDetailses = null;
		if (StringUtils.isNotBlank(userid)) {
			User user = userDAO.findOne(userid);
			userInfo = userRepository.findByMongoUserId(userid);
			if (user != null && userInfo != null) {
				pageable = userOrderRepository.findByUserInfo(userInfo,
						new PageRequest(page, size, sort));

				orderList.addAll(pageable.getContent());

				Collections.sort(orderList, new UserOrdersComparator());

				for (Order order : orderList) {
					GetUserOrderResponse orderDisplayResponse = new GetUserOrderResponse();

					InvoiceDetails invoice = null;
					String url = null;

					// OrderId
					orderDisplayResponse.setOrderId(order.getMbgOrderId());
					// ShippingType
					orderDisplayResponse.setShippingType(order
							.getShippingType().getShippingType());
					// FirstName
					orderDisplayResponse.setFirstName(order.getUserInfo()
							.getFirstname());
					// LastName
					orderDisplayResponse.setLastName(order.getUserInfo()
							.getLastname());
					// UserName
					if (order.getUserInfo().getUsername() != null) {
						orderDisplayResponse.setEmailAddress(order
								.getUserInfo().getUsername());
					} else if (order.getUserInfo().getPhonenumber() != null) {
						orderDisplayResponse.setEmailAddress(String
								.valueOf(order.getUserInfo().getPhonenumber()));
					}
					// Total savings
					orderDisplayResponse.setTotalSavings(order
							.getTotalSavings());
					// Order level shipping charge
					orderDisplayResponse.setShippingCharge(order
							.getShippingCharge());
					// ExtraBenefits
					orderDisplayResponse.setExtraBenefits(order
							.getExtraBenefits());
					// paymentDetails
					paymentDetailses = userPaymentDetailsRepository
							.findByOrder(order);
					Set<PaymentDetailsResponse> paymentDetailsResponseSet = new HashSet<>();
					for (PaymentDetails paymentDetails : paymentDetailses) {
						if (paymentDetails != null) {
							PaymentDetailsResponse paymentDetailsResponse = new PaymentDetailsResponse();
							// payment method name
							if (paymentDetails.getPaymentType() != null) {
								paymentDetailsResponse
										.setPaymentMethod(paymentDetails
												.getPaymentType()
												.getPaymentType());
							}
							// payment number
							paymentDetailsResponse
									.setPaymentNumber(paymentDetails
											.getPaymentNumber());
							// payment status
							paymentDetailsResponse
									.setPaymentStatus(paymentDetails
											.getPaymentStatus());
							// payment metadata
							if (paymentDetails.getPaymentMetadata() != null) {
								paymentDetailsResponse
										.setPaymentMetadata(paymentDetails
												.getPaymentMetadata());
							}
							// payment amount
							paymentDetailsResponse.setAmount(paymentDetails
									.getAmount());

							paymentDetailsResponseSet
									.add(paymentDetailsResponse);
						}
					}
					orderDisplayResponse
							.setPaymentDetails(paymentDetailsResponseSet);

					// order audit
					List<OrderAudit> orderAuditList = orderAuditRepository
							.findByOrderId(order.getOrderId());

					List<OrderAuditResponse> orderAuditResposeList = new ArrayList<>();

					for (OrderAudit orderAudit : orderAuditList) {

						OrderAuditResponse orderAuditRespose = new OrderAuditResponse();
						orderAuditRespose.setCreatedTime(orderAudit
								.getCreatedTime());
						orderAuditRespose.setLastModifiedTime(orderAudit
								.getLastModifiedTime());
						orderAuditRespose.setItemId(orderAudit.getItemId());
						orderAuditRespose.setNote(orderAudit.getNote());
						orderAuditRespose.setOrderId(orderAudit.getOrderId());
						orderAuditRespose.setOrderAuditType(orderAudit
								.getOrderAuditType());

						orderAuditResposeList.add(orderAuditRespose);
					}

					// Items
					Set<GetUserOrderItemsResponse> itemsDisplayResponseList = new HashSet<>();
					List<OrderDetails> items = userOrderDetailRepository
							.findByOrder(order);
					Set<Integer> itemStatusIds = new HashSet<Integer>();
					int estimatedDeliveryDate = 0;
					for (OrderDetails orderDetails : items) {
						GetUserOrderItemsResponse itemsDisplayResponse = new GetUserOrderItemsResponse();
						// Item serial number
						itemsDisplayResponse.setItemId(orderDetails
								.getOrderDetailsId());
						// Item(Product) Id
						itemsDisplayResponse.setPid(orderDetails.getItemId());
						// ItemName
						itemsDisplayResponse.setItemName(orderDetails
								.getItemName());
						// Quantity
						itemsDisplayResponse.setQuantity(orderDetails
								.getQuantity());
						Product product = productDAO.findOne(orderDetails
								.getItemId());
						if (product != null) {
							// assets
							itemsDisplayResponse.setProductAsset(product
									.getAssets());
							// subCategories
							itemsDisplayResponse.setSubCategories(product
									.getSubcategories());
						}
						// Mrp
						itemsDisplayResponse.setMrp(orderDetails.getMrp());
						// Tax
						itemsDisplayResponse.setTax(orderDetails.getTax());
						// Selling price
						itemsDisplayResponse.setSellingPrice(orderDetails
								.getSellingPrice());
						// Shipping charge
						itemsDisplayResponse.setShippingCharge(orderDetails
								.getShippingCharge());
						// Total price
						itemsDisplayResponse.setTotalPrice(orderDetails
								.getTotalPrice());

						// Tax price
						double taxPrice;
						if (order.getShippingCharge() == 0) {
							taxPrice = ((((orderDetails.getSellingPrice() * orderDetails
									.getQuantity()) + orderDetails
									.getShippingCharge()) * orderDetails
									.getTax()) / 100);
						} else {
							taxPrice = (((orderDetails.getSellingPrice() * orderDetails
									.getQuantity()) * orderDetails.getTax()) / 100);
						}

						itemsDisplayResponse.setTaxPrice(taxPrice);

						// Dealer id
						itemsDisplayResponse.setDealerId(orderDetails
								.getDealerId());
						// Item status
						itemsDisplayResponse.setItemStatus(orderDetails
								.getItemStatus());
						// Created By
						itemsDisplayResponse.setCreateBy(orderDetails
								.getCreatedby());
						// Modified By
						itemsDisplayResponse.setLastModifiedBy(orderDetails
								.getLastmodifiedby());
						// Created Date
						itemsDisplayResponse.setCreatedDate(orderDetails
								.getCreatedDate());
						// Last modified date
						itemsDisplayResponse.setLastmodifiedDate(orderDetails
								.getLastModifiedDate());
						// Item status
						itemStatusIds.add(orderDetails.getItemStatus()
								.getItemStatusId());

						for (PaymentDetails paymentDetails : paymentDetailses) {
							if (paymentDetails != null) {
								if (paymentDetails.getPaymentType()
										.getPaymentType()
										.equalsIgnoreCase("Promotion")) {
									String metadata = paymentDetails
											.getPaymentMetadata();
									String[] metadataAry = metadata.split(":");

									if (orderDetails.getItemId()
											.equalsIgnoreCase(metadataAry[1])) {
										itemsDisplayResponse
												.setPromotionName(metadataAry[0]);
										itemsDisplayResponse
												.setPromoCode(metadataAry[3]);
										itemsDisplayResponse
												.setDiscountedPrice(paymentDetails
														.getAmount());
										itemsDisplayResponse.setDiscount(Double
												.valueOf(metadataAry[2]));
									}
								}
							}
						}
						itemsDisplayResponseList.add(itemsDisplayResponse);

						// Calculating estimate delivery date
						if (estimatedDeliveryDate <= orderDetails
								.getMaxDeliveryTimeInDays()) {
							estimatedDeliveryDate = orderDetails
									.getMaxDeliveryTimeInDays();
						} else if (estimatedDeliveryDate > orderDetails
								.getMaxDeliveryTimeInDays()) {
						}

						List<OrderAudit> orderAuditList1 = orderAuditRepository
								.findByOrderIdAndItemId(order.getOrderId(),
										Integer.toString(orderDetails
												.getOrderDetailsId()));

						if (orderAuditList1.size() != 0
								&& orderAuditList1 != null) {
							for (OrderAudit orderAudit : orderAuditList1) {
								if (orderDetails.getItemStatus()
										.getDescription()
										.equalsIgnoreCase("Dispatched")
										&& orderAudit.getOrderAuditType()
												.getDescription()
												.equalsIgnoreCase("Dispatched")) {
									itemsDisplayResponse.setNote(orderAudit
											.getNote());
									break;
								}
							}
						}
					}
					// EstimatedDeliveryDate
					orderDisplayResponse
							.setEstimateDeliveryTime(estimatedDeliveryDate);
					int[] statusIdArray = new int[itemStatusIds.size()];
					int j = 0;
					for (int id : itemStatusIds) {
						statusIdArray[j] = id;
						j++;
					}

					// Calculating orderId based on itemId
					int minVal = 10;
					for (int i = 0; i < statusIdArray.length; i++) {
						if (minVal <= statusIdArray[i]) {
							continue;
						} else {
							minVal = statusIdArray[i];
						}
					}
					orderDisplayResponse.setItems(itemsDisplayResponseList);

					// Order status
					OrderStatus orderStatus = userOrderStatusRepository
							.findByOrderStatusId(minVal);
					order.setOrderStatus(orderStatus);
					userOrderRepository.save(order);
					orderDisplayResponse.setOrderStatus(orderStatus
							.getDescription());

					// invoice
					invoice = invoiceRepository.findByOrderAndUserId(order,
							userid);
					if (invoice != null) {
						url = invoice.getFileUrl();
						if (url != null) {
							orderDisplayResponse.setInvoice(url);
						} else {
							logger.info("Invoice url for Enduser null:{}",
									order.getOrderId());
						}
					} else {
						logger.info("Order invoice for Enduser null:{}",
								order.getOrderId());
					}

					// Shipping address
					orderDisplayResponse.setShippingAddress(order
							.getShippingAddress());
					// Billing address
					orderDisplayResponse.setBillingAddress(order
							.getBillingAddress());
					// Created By
					orderDisplayResponse.setCreateBy(order.getCreateBy());
					// Modified By
					orderDisplayResponse.setLastModifiedBy(order
							.getLastModifiedBy());
					// Ordered Date
					orderDisplayResponse.setOrderedDate(order.getOrderedDate());
					// LastModified Date
					orderDisplayResponse.setLastmodifiedDate(order
							.getLastmodifiedDate());
					orderDisplayResponse
							.setOrderAuditRespose(orderAuditResposeList);

					orderDisplayResponseList.add(orderDisplayResponse);
				}
			} else {
				logger.error("User is not exist in our system for userId : {}",
						userid);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userid);
			}

		} else {
			logger.error("User Id sholudn't be blank. userId : {}", userid);
			throw new Exception("User Id sholudn't be blank. userId : "
					+ userid);
		}
		trackOrderSummary.setOrderList(orderDisplayResponseList);
		trackOrderSummary.setTotalPages(pageable.getTotalPages());
		trackOrderSummary.setTotalElements(pageable.getTotalElements());
		trackOrderSummary.setNumber(pageable.getNumber());
		trackOrderSummary.setSize(pageable.getSize());
		logger.info("Fetching the orders based on customerId : {}! Success",
				userid);
		return trackOrderSummary;
	}

	/**
	 * Method to get user orders based on order id.
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 * @throws Exception
	 */

	@Override
	public GetUserOrderResponse getUserOrder(String userId, String orderId)
			throws Exception {
		logger.info("Fetching customer order based on customerId and orderId",
				userId, orderId);

		UserInfo userInfo = null;

		GetUserOrderResponse getOrderResponse = new GetUserOrderResponse();
		// Checking userId and orderId is empty or not
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(orderId)) {
			User user = userDAO.findOne(userId);
			userInfo = userRepository.findByMongoUserId(userId);
			Order order = userOrderRepository.findByMbgOrderIdAndUserInfo(
					orderId, userInfo);
			// Checking user and order is null or not
			if (user != null && userInfo != null && order != null) {
				// OrderId
				getOrderResponse.setOrderId(order.getMbgOrderId());
				// ShippingType
				getOrderResponse.setShippingType(order.getShippingType()
						.getShippingType());
				// FirstName
				getOrderResponse.setFirstName(order.getUserInfo()
						.getFirstname());
				// LastName
				getOrderResponse.setLastName(order.getUserInfo().getLastname());
				// EmailId
				if (order.getUserInfo().getUsername() != null) {
					getOrderResponse.setEmailAddress(order.getUserInfo()
							.getUsername());
				}

				// PhoneNumber
				if (order.getUserInfo().getPhonenumber() != null) {
					getOrderResponse.setPhoneNumber(order.getUserInfo()
							.getPhonenumber().toString());
				}

				// order level shipping charge
				getOrderResponse.setShippingCharge(order.getShippingCharge());

				// Extra Benefits
				getOrderResponse.setExtraBenefits(order.getExtraBenefits());

				List<OrderDetails> orderDetails = userOrderDetailRepository
						.findByOrder(order);
				Set<GetUserOrderItemsResponse> orderItemSet = new HashSet<>();

				Set<Integer> itemStatusIds = new HashSet<Integer>();
				// Items information
				for (OrderDetails orderDetail : orderDetails) {
					GetUserOrderItemsResponse getOrderItems = new GetUserOrderItemsResponse();
					// Item Serial number
					getOrderItems.setItemId(orderDetail.getOrderDetailsId());
					// Item(Product)Id
					getOrderItems.setPid(orderDetail.getItemId());
					// Item name
					getOrderItems.setItemName(orderDetail.getItemName());
					// Item Quantity
					getOrderItems.setQuantity(orderDetail.getQuantity());
					// Product Asset
					Product product = productDAO.findOne(orderDetail
							.getItemId());
					getOrderItems.setProductAsset(product.getAssets());
					// Mro
					getOrderItems.setMrp(orderDetail.getMrp());
					// Tax
					getOrderItems.setTax(orderDetail.getTax());
					// Selling price
					getOrderItems
							.setSellingPrice(orderDetail.getSellingPrice());
					// ShippingCharge
					getOrderItems.setShippingCharge(orderDetail
							.getShippingCharge());
					// TotalPrice
					getOrderItems.setTotalPrice(orderDetail.getTotalPrice());

					double taxPrice = ((((orderDetail.getSellingPrice() * orderDetail
							.getQuantity()) + orderDetail.getShippingCharge()) * orderDetail
							.getTax()) / 100);
					getOrderItems.setTaxPrice(taxPrice);

					// DealerId
					getOrderItems.setDealerId(orderDetail.getDealerId());
					// ItemStatus
					getOrderItems.setItemStatus(orderDetail.getItemStatus());
					// CreatedBy
					getOrderItems.setCreateBy(orderDetail.getCreatedby());
					// LastModifiedBy
					getOrderItems.setLastModifiedBy(orderDetail
							.getLastmodifiedby());
					// CreatedDate
					getOrderItems.setCreatedDate(orderDetail.getCreatedDate());
					// LastModifiedDate
					getOrderItems.setLastmodifiedDate(orderDetail
							.getLastModifiedDate());
					itemStatusIds.add(orderDetail.getItemStatus()
							.getItemStatusId());
					orderItemSet.add(getOrderItems);
				}
				int[] statusIdArray = new int[itemStatusIds.size()];
				int j = 0;
				for (int id : itemStatusIds) {
					statusIdArray[j] = id;
					j++;
				}
				// Calculating order status based on item status
				int minVal = 10;
				for (int i = 0; i < statusIdArray.length; i++) {
					if (minVal <= statusIdArray[i]) {
						continue;
					} else {
						minVal = statusIdArray[i];
					}
				}
				// Order Status
				OrderStatus orderStatus = userOrderStatusRepository
						.findByOrderStatusId(minVal);
				order.setOrderStatus(orderStatus);
				// Saving order
				userOrderRepository.save(order);
				// OrderStatus
				getOrderResponse.setOrderStatus(orderStatus.getDescription());
				// Items information
				getOrderResponse.setItems(orderItemSet);
				// Shipping Address
				getOrderResponse.setShippingAddress(order.getShippingAddress());
				// Billing Address
				getOrderResponse.setBillingAddress(order.getBillingAddress());
				// Created By
				getOrderResponse.setCreateBy(order.getCreateBy());
				// LastModified By
				getOrderResponse.setLastModifiedBy(order.getLastModifiedBy());
				// OrderedDate
				getOrderResponse.setOrderedDate(order.getOrderedDate());
				// Last Modified date
				getOrderResponse.setLastmodifiedDate(order
						.getLastmodifiedDate());

			} else {
				logger.error(
						"User is not exist in our system for userId : {} or order is null for orderId : {}",
						userId, orderId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId + " or order is null for orderId : "
								+ orderId);
			}

		} else {

			logger.error(
					"User Id and Order Id should not be null. userId : {} orderId : {}",
					userId, orderId);

			throw new Exception(
					"User Id and Order Id should not be null. userId : "
							+ userId + " orderId : " + orderId);
		}
		logger.info("Fetching customer order based on customerId and orderId! Success");
		return getOrderResponse;
	}

	/**
	 * Method to get user order status
	 * 
	 * @param uid
	 * @param orderid
	 * @return
	 * @throws Exception
	 */
	@Override
	public OrderStatus getUserOrderStatus(String userId, String orderId)
			throws Exception {
		logger.info("Fetching Order status based on orderId");
		Order order = null;
		User user = null;
		UserInfo userInfo = null;
		OrderStatus orderStatus = null;
		// Checking userId and orderId is empty or not
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(orderId)) {
			user = userDAO.findOne(userId);
			userInfo = userRepository.findByMongoUserId(userId);
			order = userOrderRepository.findByMbgOrderIdAndUserInfo(orderId,
					userInfo);
			// Checking user and order is empty or not
			if (user != null && userInfo != null && order != null) {
				orderStatus = new OrderStatus();
				List<OrderDetails> orderDetails = userOrderDetailRepository
						.findByOrder(order);

				Set<Integer> itemStatusIds = new HashSet<Integer>();

				for (OrderDetails orderDetail : orderDetails) {

					itemStatusIds.add(orderDetail.getItemStatus()
							.getItemStatusId());
				}
				int[] statusIdArray = new int[itemStatusIds.size()];
				int j = 0;
				for (int id : itemStatusIds) {
					statusIdArray[j] = id;
					j++;
				}
				// Calculating order status based on item status
				int minVal = 10;
				for (int i = 0; i < statusIdArray.length; i++) {
					if (minVal <= statusIdArray[i]) {
						continue;
					} else {
						minVal = statusIdArray[i];
					}
				}

				orderStatus = userOrderStatusRepository
						.findByOrderStatusId(minVal);
				order.setOrderStatus(orderStatus);
				// Creating order audit
				if (updateOrderAudit(order, "", orderStatus.getDescription(),
						"")) {
					userOrderRepository.save(order);
				}
				// Saving order
				userOrderRepository.save(order);
			} else {
				logger.error(
						"User is not exist in our system for userId : {} or Order is null for orderId : {}",
						userId, orderId);

				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId + " or Order is null for orderId : "
								+ orderId);
			}
		} else {
			logger.error(
					"UserId and OrderId should not empty. userId : {} orderId : {}",
					userId, orderId);
			throw new Exception(
					"UserId and OrderId should not empty. userId : " + userId
							+ " orderId : " + orderId);
		}

		logger.info("Fetching Order status based on orderId : {}! Success",
				orderId);
		return orderStatus;
	}

	/**
	 * Method to delete user Order
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional
	public OrderStatus deleteUserOrder(String userId, String orderId)
			throws Exception {
		logger.info("Cancelling the order based on orderId : {} userId : {}",
				orderId, userId);
		OrderStatus orderStatus = null;
		ItemStatus itemStatus = null;
		Order order = null;
		UserInfo userInfo = null;
		List<OrderDetails> itemList = null;
		String customerName = null;
		Integer customerZipcode = 0;
		double totalOrderAmount1 = 0.0;
		double totalItemAmount = 0.0;
		User adminuser = null;
		Set<User> userSet = new HashSet<>();
		Set<User> dealerSet = new HashSet<>();
		Set<String> dealersIds = new HashSet<>();
		// Checking userId and orderId is empty or not
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(orderId)) {
			User user = userDAO.findOne(userId);
			userInfo = userRepository.findByMongoUserId(userId);
			order = userOrderRepository.findByMbgOrderIdAndUserInfo(orderId,
					userInfo);
			// Checking user and order is null or not
			if (user != null && userInfo != null && order != null) {
				adminuser = userDAO.findByRoles_NameIsIgnoreCase("SUPERADMIN");
				userSet.add(user);
				if (user.getFirstName() != null && user.getLastName() != null) {
					customerName = user.getFirstName() + " "
							+ user.getLastName();
				} else if (user.getFirstName() != null) {
					customerName = user.getFirstName();
				}
				if (order.getShippingAddress() != null) {
					customerZipcode = order.getShippingAddress().getPincode();
				}
				if (order.getItems() != null && order.getItems().size() != 0) {
					itemList = order.getItems();
				}

				List<ItemInfoForCommunication> itemInfoForCommunicationList = new ArrayList<>();
				for (OrderDetails orderDetails : itemList) {
					ItemInfoForCommunication itemInfoForCommunication = new ItemInfoForCommunication();
					DecimalFormat df = new DecimalFormat("####0.00");
					itemStatus = userItemStatusRepository
							.findByDescription("Cancelled");
					if (itemStatus != null) {
						orderDetails.setItemStatus(itemStatus);
					}
					userOrderDetailRepository.save(orderDetails);
					totalItemAmount = (((orderDetails.getQuantity()) * (orderDetails
							.getSellingPrice())) + (orderDetails
							.getShippingCharge()));
					totalOrderAmount1 = totalOrderAmount1 + totalItemAmount;
					User dealer = null;
					if (orderDetails.getDealerId() != null) {
						dealer = userDAO.findById(orderDetails.getDealerId());
					}
					if (dealer != null) {
						if (dealerSet.size() != 0) {
							if (!dealersIds.contains(dealer.getId())) {
								dealerSet.add(dealer);
								dealersIds.add(dealer.getId());
							}
						} else {
							dealerSet.add(dealer);
							dealersIds.add(dealer.getId());
						}
					}
					// productId
					if (orderDetails.getItemId() != null) {
						itemInfoForCommunication.setItemId(orderDetails
								.getItemId());
					}
					// productName
					if (orderDetails.getItemName() != null) {
						itemInfoForCommunication.setItemName(orderDetails
								.getItemName());
					}
					// imageUrl
					if (!orderDetails.getItemId().isEmpty()) {
						String imageUrl = getItemImage(orderDetails.getItemId());
						itemInfoForCommunication.setItemImage(imageUrl);
					}
					// quantityType
					if (orderDetails.getItemId() != null) {
						String quantityType = getItemQuantityType(orderDetails
								.getItemId());
						itemInfoForCommunication
								.setItemQuantityType(quantityType);
					}
					// quantity
					itemInfoForCommunication.setItemQuantity(orderDetails
							.getQuantity());
					// unitPrice
					itemInfoForCommunication.setItemUnitPrice(df
							.format(orderDetails.getSellingPrice()));
					// totalItemPrice
					itemInfoForCommunication.setItemTotalPrice(df
							.format(totalItemAmount));

					// shipping chrg
					String shippingCharge = null;
					if (orderDetails.getShippingCharge() == 0.0) {
						shippingCharge = "Free";
					} else {
						shippingCharge = df.format(orderDetails
								.getShippingCharge());
					}
					itemInfoForCommunication
							.setItemShippingChrg(shippingCharge);
					// min delivery
					itemInfoForCommunication
							.setMinDeliveryTimeInDays(orderDetails
									.getMinDeliveryTimeInDays());
					// max delivery
					itemInfoForCommunication
							.setMaxDeliveryTimeInDays(orderDetails
									.getMaxDeliveryTimeInDays());

					itemInfoForCommunicationList.add(itemInfoForCommunication);
				}
				// Changing order status to cancel state
				orderStatus = userOrderStatusRepository
						.findByDescription("Cancelled");
				if (orderStatus != null) {
					order.setOrderStatus(orderStatus);
				}
				userOrderRepository.save(order);
				updateOrderAudit(order, "", order.getOrderStatus()
						.getDescription(), "");
				int itemCount = itemList.size();
				String itemName = null;
				Integer itemQuantity = 0;
				if (itemCount == 1) {
					for (OrderDetails item : itemList) {
						if (item.getItemName() != null) {
							itemName = item.getItemName();
						}
						itemQuantity = item.getQuantity();
					}
				}

				DecimalFormat df = new DecimalFormat("####0.00");

				List<User> teleUsers = userDAO
						.findByRoles_Name("MBGTELEASSOCIATE");

				List<User> techUsers = userDAO
						.findByRoles_Name("MBGTECHASSOCIATE");

				// Email and sms
				userCommunicationService.sendCancelOrderInfoToCustomer(userSet,
						order.getMbgOrderId(), itemCount, itemName,
						itemQuantity, df.format(totalOrderAmount1),
						customerName, customerZipcode,
						itemInfoForCommunicationList);

				userCommunicationService.sendCancelOrderInfoToAdmin(adminuser,
						order.getMbgOrderId(), itemCount, itemName,
						itemQuantity, df.format(totalOrderAmount1),
						customerName, customerZipcode,
						itemInfoForCommunicationList);

				userCommunicationService.sendCancelOrderInfoToDealers(
						dealerSet, order.getMbgOrderId(), itemCount, itemName,
						itemQuantity, df.format(totalOrderAmount1),
						customerName, customerZipcode,
						itemInfoForCommunicationList);

				userCommunicationService.sendCancelOrderInfoToTeleAssociate(
						teleUsers, order.getMbgOrderId(), itemCount, itemName,
						itemQuantity, df.format(totalOrderAmount1),
						customerName, customerZipcode,
						itemInfoForCommunicationList);

				userCommunicationService.sendCancelOrderInfoToTechAssociate(
						techUsers, order.getMbgOrderId(), itemCount, itemName,
						itemQuantity, df.format(totalOrderAmount1),
						customerName, customerZipcode,
						itemInfoForCommunicationList);
			} else {
				logger.debug("User is not exist in our system for useId : {}",
						userId);
				throw new Exception(
						"User is not exist in our system for useId : " + userId);
			}

		} else {
			logger.error(
					"User Id and Order Id should not be blank. userId : {} orderId : {}",
					userId, orderId);

			throw new Exception(
					"User Id and Order Id should not be blank. userId : "
							+ userId + " orderId : " + orderId);
		}

		logger.info(
				"Order cancelled(Soft delete) successfully based on orderId : {} userId : {}",
				orderId, userId);
		return orderStatus;
	}

	/**
	 * Method to get Order items of the user
	 * 
	 * @param uid
	 * @param orderid
	 * @return
	 * @throws Exception
	 */
	@Override
	public Set<GetUserOrderItemsResponse> getUserOrderItems(String userId,
			String orderId) throws Exception {
		logger.info(
				"Fetching user order items based on orderId : {} and userId : {}",
				orderId, userId);
		Set<GetUserOrderItemsResponse> itemDisplayList = new HashSet<>();
		UserInfo userInfo = new UserInfo();

		// Checking userId and orderId is null or not
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(orderId)) {
			User user = userDAO.findOne(userId);
			userInfo = userRepository.findByMongoUserId(userId);
			com.sarvah.mbg.domain.ordermgmt.Order order = userOrderRepository
					.findByMbgOrderIdAndUserInfo(orderId, userInfo);
			// Checking user and order is null or not
			if (user != null && userInfo != null && order != null) {
				List<OrderDetails> items = userOrderDetailRepository
						.findByOrder(order);
				for (OrderDetails orderDetails : items) {
					GetUserOrderItemsResponse itemsDisplayResponse = new GetUserOrderItemsResponse();
					// Item serial number
					itemsDisplayResponse.setItemId(orderDetails
							.getOrderDetailsId());
					// Item(Product) Id
					itemsDisplayResponse.setPid(orderDetails.getItemId());
					// Item name
					itemsDisplayResponse
							.setItemName(orderDetails.getItemName());
					// Quantity
					itemsDisplayResponse
							.setQuantity(orderDetails.getQuantity());
					// Product Asset
					Product product = productDAO.findOne(orderDetails
							.getItemId());
					itemsDisplayResponse.setProductAsset(product.getAssets());
					// Mrp
					itemsDisplayResponse.setMrp(orderDetails.getMrp());
					// Tax
					itemsDisplayResponse.setTax(orderDetails.getTax());
					// SellingPrice
					itemsDisplayResponse.setSellingPrice(orderDetails
							.getSellingPrice());
					// ShippingCharge
					itemsDisplayResponse.setShippingCharge(orderDetails
							.getShippingCharge());
					// Total Price
					itemsDisplayResponse.setTotalPrice(orderDetails
							.getTotalPrice());
					// DealerId
					itemsDisplayResponse
							.setDealerId(orderDetails.getDealerId());
					// ItemStatus
					itemsDisplayResponse.setItemStatus(orderDetails
							.getItemStatus());
					// CreatedBy
					itemsDisplayResponse.setCreateBy(orderDetails
							.getCreatedby());
					// ModifiedBy
					itemsDisplayResponse.setLastModifiedBy(orderDetails
							.getLastmodifiedby());
					// CreatedDate
					itemsDisplayResponse.setCreatedDate(orderDetails
							.getCreatedDate());
					// ModifiedDate
					itemsDisplayResponse.setLastmodifiedDate(orderDetails
							.getLastModifiedDate());
					itemDisplayList.add(itemsDisplayResponse);
				}
			} else {
				logger.debug(
						"User is not exist in our system for this userId : {} or order is null for orderId : {}",
						userId, orderId);
				throw new Exception(
						"User is not exist in our system for this userId : "
								+ userId + " or order is null for orderId : {}"
								+ orderId);
			}
		} else {
			logger.error(
					"User Id and OrderId should not be blank. userId : {} orderId : {}",
					userId, orderId);
			throw new Exception(
					"User Id and OrderId should not be blank. userId : "
							+ userId + " order : " + orderId);
		}
		logger.info(
				"Fetching user order items based on orderId : {} and userId : {}! Success",
				orderId, userId);
		return itemDisplayList;
	}

	@Override
	public ItemUpdateResponse updateItemsStatus(String userId, String orderId,
			Set<String> itemsId, String status, String note) throws Exception {
		logger.info(
				"Updating items status based on orderId : {} and userId : {}",
				orderId, userId);
		User user = null;
		Order order = null;
		UserInfo userInfo = null;
		ItemStatus itemStatus = null;
		String itemsName = null;
		String itemName = null;
		String customerName = null;
		Integer customerZipcode = 0;
		Integer itemQuantity = 0;
		Double totalItemAmount = 0.0;
		Integer minDeliveryTimeInDays = 0;
		Integer maxDeliveryTimeInDays = 0;
		String deliverBy = null;
		String shippigType = null;
		User dealer = null;
		int itemCount = 0;
		String address = null;
		ItemUpdateResponse itemUpdateResponse = new ItemUpdateResponse();
		List<ItemInfoForCommunication> itemInfoForCommunicationList = null;
		DecimalFormat df = new DecimalFormat("####0.00");
		Set<User> userSet = null;
		Set<String> dealersIds = new HashSet<>();
		// Checking userId and orderId is blank or not
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(orderId)
				&& itemsId != null && itemsId.size() != 0) {
			userSet = new HashSet<User>();
			User admin = userDAO.findByRoles_NameIsIgnoreCase("SUPERADMIN");
			// Taking admin information to send item(s) status changed info
			if (admin != null) {
				userSet.add(admin);
			}
			// Taking TeleAssociates information to send item(s) status changed
			// info
			List<User> teleUsers = userDAO.findByRoles_Name("MBGTELEASSOCIATE");
			if (teleUsers != null && teleUsers.size() != 0) {
				userSet.addAll(teleUsers);
			}
			// Taking TechAssociates information to send item(s) status changed
			// info
			List<User> techUsers = userDAO.findByRoles_Name("MBGTECHASSOCIATE");
			if (techUsers != null && techUsers.size() != 0) {
				userSet.addAll(techUsers);
			}
			// Fetching customer information based on userId
			user = userDAO.findOne(userId);
			userInfo = userRepository.findByMongoUserId(userId);
			order = userOrderRepository.findByMbgOrderIdAndUserInfo(orderId,
					userInfo);
			// Checking user and order is null or not
			if (user != null && userInfo != null && order != null) {
				// Taking customer information to send item(s) status changed
				// info
				userSet.add(user);
				if (user.getFirstName() != null && user.getLastName() != null) {
					customerName = user.getFirstName() + " "
							+ user.getLastName();
				} else {
					customerName = user.getFirstName();
				}

				shippigType = order.getShippingType().getShippingType();

				customerZipcode = order.getShippingAddress().getPincode();

				itemInfoForCommunicationList = new ArrayList<>();
				for (String itemId : itemsId) {
					ItemInfoForCommunication itemInfoForCommunication = new ItemInfoForCommunication();
					OrderDetails item = userOrderDetailRepository
							.findByOrderAndOrderDetailsId(order,
									Integer.parseInt(itemId));
					if (item != null) {
						dealer = userDAO.findById(item.getDealerId());
						// Taking dealer information to send item status changed
						// info
						if (dealer != null) {

							if (userSet.size() != 0) {
								if (!dealersIds.contains(dealer.getId())) {
									userSet.add(dealer);
									dealersIds.add(dealer.getId());
								}
							} else {
								userSet.add(dealer);
								dealersIds.add(dealer.getId());
							}
						}

						// SellByDealer or MBG
						Store store1 = storeDAO.findByUser_Id(item
								.getDealerId());
						if (store1 != null) {
							StoreProductPricing storeProductPricing = storeProductPricingDAO
									.findByProductIdAndStoreId(
											item.getItemId(), store1.getId());
							if (storeProductPricing != null) {
								Boolean sellByDealer = storeProductPricing
										.isStoreDelivery();
								if (sellByDealer) {
									deliverBy = "Seller";
								} else {
									deliverBy = "Buildonn";
								}
							} else {
								deliverBy = "Buildonn";
							}
						} else {
							deliverBy = "Buildonn";
						}

						itemName = item.getItemName();
						itemCount++;
						if (itemCount == 1) {
							itemsName = itemName;
						} else if (itemCount > 1) {
							itemsName = itemsName + "," + itemName;
						}

						itemQuantity = itemQuantity + item.getQuantity();
						totalItemAmount = item.getQuantity()
								* item.getSellingPrice();
						minDeliveryTimeInDays = item.getMinDeliveryTimeInDays();
						maxDeliveryTimeInDays = item.getMaxDeliveryTimeInDays();

						String itemId1 = String.valueOf(item
								.getOrderDetailsId());

						itemInfoForCommunication.setItemId(item.getItemId());

						itemInfoForCommunication
								.setDealerId(item.getDealerId());

						itemInfoForCommunication
								.setItemName(item.getItemName());

						if (StringUtils.isNotBlank(item.getItemId())) {
							String imageUrl = getItemImage(item.getItemId());
							itemInfoForCommunication.setItemImage(imageUrl);

							String quantityType = getItemQuantityType(item
									.getItemId());

							itemInfoForCommunication
									.setItemQuantityType(quantityType);
						}

						itemInfoForCommunication.setItemQuantity(item
								.getQuantity());

						itemInfoForCommunication.setItemUnitPrice(df
								.format(item.getSellingPrice()));

						itemInfoForCommunication.setItemTotalPrice(df
								.format(totalItemAmount));

						// shipping chrg
						String shippingCharge = null;
						if (item.getShippingCharge() == 0.0) {
							shippingCharge = "Free";
						} else {
							shippingCharge = df
									.format(item.getShippingCharge());
						}
						itemInfoForCommunication
								.setItemShippingChrg(shippingCharge);

						// min delivery
						itemInfoForCommunication.setMinDeliveryTimeInDays(item
								.getMinDeliveryTimeInDays());
						// max delivery
						itemInfoForCommunication.setMaxDeliveryTimeInDays(item
								.getMaxDeliveryTimeInDays());

						itemInfoForCommunicationList
								.add(itemInfoForCommunication);

						if (StringUtils.isNotBlank(itemId)
								&& itemId.equalsIgnoreCase(itemId1)) {

							if (StringUtils.isNotBlank(status)) {

								itemStatus = userItemStatusRepository
										.findByDescription(status);
								if (itemStatus != null) {
									// Updating item status
									item.setItemStatus(itemStatus);
									userOrderDetailRepository.save(item);

									itemUpdateResponse
											.setItemStatus(itemStatus);

									updateOrderAudit(order, itemId, status,
											note);
								}
							}
						}
					} else {
						logger.error("Item is null for ItemId : {}", itemId);
						throw new Exception("Item is null for ItemId : "
								+ itemId);
					}
				}
			} else {
				logger.error(
						"User is not exist in our system for userId : {} or Order is null for orderId : {}",
						userId, orderId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId + " or Order is null for orderId : "
								+ orderId);
			}
		} else {
			logger.error(
					"UserId and orderId should not be blank. userId : {} orderId : {}",
					userId, orderId);
			throw new Exception(
					"UserId and orderId should not be blank. userId : "
							+ userId + " orderId : " + orderId);
		}

		if (itemStatus.getDescription().equals("Cancelled")) {

			userCommunicationService.sendItemCancelledInfo(userSet,
					order.getMbgOrderId(), itemsName, itemQuantity,
					df.format(totalItemAmount), customerName, customerZipcode,
					itemInfoForCommunicationList, itemCount);

		} else if (itemStatus.getDescription().equals("Confirmed")) {

			OrderAddress orderBilingAddress = order.getBillingAddress();
			if (orderBilingAddress != null) {
				if (orderBilingAddress.getName() != null
						&& orderBilingAddress.getAddressLine1() != null
						&& orderBilingAddress.getCity() != null) {
					address = orderBilingAddress.getName() + ","
							+ orderBilingAddress.getAddressLine1() + ","
							+ orderBilingAddress.getCity() + "-"
							+ orderBilingAddress.getPincode();
				}
			}

			userCommunicationService
					.sendItemConfirmedInfo(userSet, order.getMbgOrderId(),
							itemsName, itemQuantity,
							df.format(totalItemAmount), minDeliveryTimeInDays,
							maxDeliveryTimeInDays, customerName,
							customerZipcode, deliverBy,
							itemInfoForCommunicationList, itemCount, address);

		} else if (itemStatus.getDescription().equals("UnConfirmed")) {

			userCommunicationService.sendItemUnConfirmedInfo(userSet,
					order.getMbgOrderId(), itemsName, itemQuantity,
					df.format(totalItemAmount), deliverBy,
					itemInfoForCommunicationList, itemCount);
		} else if (itemStatus.getDescription().equals("Packed")) {
			if (shippigType.equalsIgnoreCase("SELF PICKUP")) {

				String storeName = dealer.getFirstName();

				Set<Address> dealerAddresses = dealer.getAddresses();

				String dealerAdds = null;
				String dealerContectNumber = null;

				for (Address dealerAddress : dealerAddresses) {

					dealerAdds = dealerAddress.getAddressLine1();

					Set<Phone> phs = dealerAddress.getPhoneNumbers();

					if (phs != null) {
						for (Phone ph : phs) {
							if (ph.isPrimary()) {
								dealerContectNumber = ph.getNumber();
							}

						}
					}
				}

				userCommunicationService.sendItemPackedInforSelfPickup(user,
						order.getMbgOrderId(), itemsName, itemQuantity,
						df.format(totalItemAmount), deliverBy, storeName,
						dealerAdds, dealerContectNumber,
						itemInfoForCommunicationList, itemCount);
			} else {
				userCommunicationService.sendItemPackedInfo(user,
						order.getMbgOrderId(), itemsName, itemQuantity,
						df.format(totalItemAmount), deliverBy,
						itemInfoForCommunicationList, itemCount);
			}
		} else if (itemStatus.getDescription().equals("Dispatched")) {
			if (StringUtils.isBlank(note)) {
				note = "Not Given";
			}
			userCommunicationService.sendItemDispatchedInfo(userSet,
					order.getMbgOrderId(), itemsName, itemQuantity, note,
					df.format(totalItemAmount), deliverBy,
					itemInfoForCommunicationList, itemCount);
		} else if (itemStatus.getDescription().equals("Delivered")) {
			userCommunicationService.sendItemDeliveredInfo(userSet,
					order.getMbgOrderId(), itemsName, itemQuantity, deliverBy,
					itemInfoForCommunicationList, itemCount);
		} else if (itemStatus.getDescription().equals("Returned")) {

			userCommunicationService.sendItemReturnedInfo(userSet,
					order.getMbgOrderId(), itemsName, itemQuantity,
					customerName, customerZipcode, df.format(totalItemAmount),
					itemInfoForCommunicationList, itemCount);
		}

		logger.info(
				"Updating item status based on orderId : {} and userId : {}! Success",
				orderId, userId);

		return itemUpdateResponse;
	}

	private String getItemImage(String productId) {
		String imageUrl = null;
		ProductAsset assets = null;
		Product product = productDAO.findOne(productId);
		if (product != null) {
			assets = product.getAssets();
		}
		if (assets != null) {
			List<Image> images = null;
			if (assets.getImages() != null) {
				images = assets.getImages();
			}
			for (Image image : images) {
				if (image.getUrl() != null) {
					imageUrl = image.getUrl();
				}
				break;
			}
		}
		return imageUrl;
	}

	private String getItemQuantityType(String productId) {
		String quantityType = null;
		Product product = productDAO.findOne(productId);
		if (product != null) {
			if (product.getQuantityType() != null) {
				quantityType = product.getQuantityType().getName();
			}
		}

		return quantityType;
	}

	/**
	 * Method to delete user order item
	 * 
	 * @param uid
	 * @param orderid
	 * @param itemid
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional
	public ItemUpdateResponse deleteUserItem(String userId, String orderId,
			String itemId) throws Exception {
		logger.info(
				"Cancelling item based on itemId : {} orderId : {} and userId : {}",
				itemId, orderId, userId);
		OrderDetails orderDetails = null;
		ItemStatus itemStatus = null;
		UserInfo userInfo = null;
		String itemName = null;
		Integer itemQuantity = 0;
		double totalItemAmount = 0.0;
		String customerName = null;
		Integer customerZipcode = 0;

		ItemUpdateResponse itemUpdateResponse = new ItemUpdateResponse();
		// Checking userId orderId and ItemId is null or not
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(orderId)
				&& StringUtils.isNotBlank(itemId)) {
			ItemInfoForCommunication itemInfoForCommunication = new ItemInfoForCommunication();
			User user = userDAO.findOne(userId);
			userInfo = userRepository.findByMongoUserId(userId);
			com.sarvah.mbg.domain.ordermgmt.Order order = userOrderRepository
					.findByMbgOrderIdAndUserInfo(orderId, userInfo);
			// Checking user and order is null or not
			if (user != null && userInfo != null && order != null) {
				itemStatus = new ItemStatus();
				Set<User> userSet = new HashSet<User>();
				customerZipcode = order.getShippingAddress().getPincode();
				if (user.getFirstName() != null && user.getLastName() != null) {
					customerName = user.getFirstName() + " "
							+ user.getLastName();
				} else if (user.getFirstName() != null) {
					customerName = user.getFirstName();
				}
				// Taking admin information to send cancel item info
				List<User> superAdminList = userDAO
						.findByRoles_Name("SUPERADMIN");
				for (User admin : superAdminList) {
					userSet.add(admin);
				}
				// Taking customer information to send cancel item info
				userSet.add(user);

				orderDetails = userOrderDetailRepository
						.findByOrderAndOrderDetailsId(order,
								Integer.parseInt(itemId));

				// Checking item is null or not
				if (orderDetails != null) {
					// Taking dealer information to send cancel item info
					String dealerId = orderDetails.getDealerId();
					User dealer = userDAO.findById(dealerId);
					if (dealer != null) {
						userSet.add(dealer);
					}
					itemName = orderDetails.getItemName();
					itemQuantity = orderDetails.getQuantity();
					totalItemAmount = orderDetails.getQuantity()
							* orderDetails.getSellingPrice();
					// Deleting item(soft delete)
					ItemStatus itemStatus1 = userItemStatusRepository
							.findByDescription("cancelled");
					if (itemStatus1 != null) {
						int itemStatusId = itemStatus1.getItemStatusId();

						itemStatus.setItemStatusId(itemStatusId);

						itemStatus.setDescription(itemStatus1.getDescription());
						orderDetails.setItemStatus(itemStatus);
						itemUpdateResponse.setItemStatus(itemStatus);
					}
					userOrderDetailRepository.save(orderDetails);
				} else {
					logger.error(
							"Item is null for ItemId : {} and orderId : {}",
							itemId, orderId);
					throw new Exception("Item is null for ItemId : " + itemId
							+ " orderId : " + orderId);
				}
				userOrderRepository.save(order);

				DecimalFormat df = new DecimalFormat("####0.00");

				itemInfoForCommunication.setItemId(orderDetails.getItemId());

				itemInfoForCommunication
						.setItemName(orderDetails.getItemName());

				String itemUrl = getItemImage(orderDetails.getItemId());
				itemInfoForCommunication.setItemImage(itemUrl);

				String quantityType = getItemQuantityType(orderDetails
						.getItemId());

				itemInfoForCommunication.setItemQuantityType(quantityType);

				itemInfoForCommunication.setItemQuantity(orderDetails
						.getQuantity());

				itemInfoForCommunication.setItemUnitPrice(df
						.format(orderDetails.getSellingPrice()));

				itemInfoForCommunication.setItemTotalPrice(df
						.format(totalItemAmount));

				// shipping chrg
				itemInfoForCommunication.setItemShippingChrg(df
						.format(orderDetails.getShippingCharge()));
				// min delivery
				itemInfoForCommunication.setMinDeliveryTimeInDays(orderDetails
						.getMinDeliveryTimeInDays());
				// max delivery
				itemInfoForCommunication.setMaxDeliveryTimeInDays(orderDetails
						.getMaxDeliveryTimeInDays());

				List<User> teleUsers = userDAO
						.findByRoles_Name("MBGTELEASSOCIATE");
				if (teleUsers != null && teleUsers.size() != 0) {
					userSet.addAll(teleUsers);
				}

				List<User> techUsers = userDAO
						.findByRoles_Name("MBGTECHASSOCIATE");
				if (techUsers != null && techUsers.size() != 0) {
					userSet.addAll(techUsers);
				}

				userCommunicationService.sendItemCancelledInfo(userSet,
						order.getMbgOrderId(), itemName, itemQuantity,
						df.format(totalItemAmount), customerName,
						customerZipcode, itemInfoForCommunication);

			} else {

				logger.error("User is not exist in our system for userId : {}",
						userId);

				throw new Exception(
						"User is not exist in our system for userId : {}"
								+ userId);
			}

		} else {
			logger.error(
					"UserId, OrderId and ItemId should not be empty. userId : {} orderId : {} itemId : {}",
					userId, orderId, itemId);
			throw new Exception("User id should not be blank ");
		}
		logger.info(
				"Cancelling item based on itemId : {} orderId : {} and userId : {}! Success",
				itemId, orderId, userId);
		return itemUpdateResponse;
	}

	/**
	 * method for get all cancelled status order.
	 * 
	 * @param uid
	 * @return List<GetUserOrderResponse>
	 * @throws Exception
	 */
	@Override
	public TrackOrderSummary getUserCancelledOrders(String uid, int page,
			int size, Sort sort) throws Exception {
		// Fetching cancelled orders for customer based on customerId
		logger.info("Fetching Cancelled orders for CustomerId : {}", uid);

		List<GetUserOrderResponse> orderDisplayResponseList = null;
		TrackOrderSummary trackOrderSummary = null;
		Set<com.sarvah.mbg.domain.ordermgmt.Order> orderSet = null;
		UserInfo userInfo = null;
		Page<Order> pageable = null;

		// Checking userId is empty or not
		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findOne(uid);
			userInfo = userRepository.findByMongoUserId(uid);
			// Checking user is exist in our system
			if (user != null && userInfo != null) {
				orderDisplayResponseList = new ArrayList<>();
				trackOrderSummary = new TrackOrderSummary();
				orderSet = new HashSet<com.sarvah.mbg.domain.ordermgmt.Order>();
				// Fetching cancelled orders for customer
				pageable = userOrderRepository
						.findByUserInfoAndOrderStatus_Description(userInfo,
								"Cancelled", new PageRequest(page, size, sort));

				orderSet.addAll(pageable.getContent());
				if (orderSet.size() != 0) {
					for (Order order : orderSet) {
						// Setting cancel order response
						GetUserOrderResponse orderDisplayResponse = new GetUserOrderResponse();
						// OrderId
						orderDisplayResponse.setOrderId(order.getMbgOrderId());
						// FirstName
						orderDisplayResponse.setFirstName(order.getUserInfo()
								.getFirstname());
						// LastName
						orderDisplayResponse.setLastName(order.getUserInfo()
								.getLastname());
						// UserName
						orderDisplayResponse.setEmailAddress(order
								.getUserInfo().getUsername());
						// Order level shipping charge
						orderDisplayResponse.setShippingCharge(order
								.getShippingCharge());
						// Items
						Set<GetUserOrderItemsResponse> itemsDisplayResponseList = new HashSet<>();
						List<OrderDetails> items = userOrderDetailRepository
								.findByOrderAndItemStatus_Description(order,
										"cancelled");

						if (items.size() != 0) {
							for (OrderDetails orderDetails : items) {
								// Setting item level information for cancel
								// order response
								GetUserOrderItemsResponse itemsDisplayResponse = new GetUserOrderItemsResponse();
								// Item Serial Number
								itemsDisplayResponse.setItemId(orderDetails
										.getOrderDetailsId());
								// Item(Product) Id
								itemsDisplayResponse.setPid(orderDetails
										.getItemId());
								// ItemName
								itemsDisplayResponse.setItemName(orderDetails
										.getItemName());
								// Quantity
								itemsDisplayResponse.setQuantity(orderDetails
										.getQuantity());
								Product product = productDAO
										.findOne(orderDetails.getItemId());
								// Product Asset(to display item image purpose)
								itemsDisplayResponse.setProductAsset(product
										.getAssets());
								// Mrp
								itemsDisplayResponse.setMrp(orderDetails
										.getMrp());
								// Tax
								itemsDisplayResponse.setTax(orderDetails
										.getTax());
								// Selling Price
								itemsDisplayResponse
										.setSellingPrice(orderDetails
												.getSellingPrice());
								// Shipping Charge
								itemsDisplayResponse
										.setShippingCharge(orderDetails
												.getShippingCharge());
								// Total Price
								itemsDisplayResponse.setTotalPrice(orderDetails
										.getTotalPrice());
								// DealerId
								itemsDisplayResponse.setDealerId(orderDetails
										.getDealerId());
								// ItemStatus
								itemsDisplayResponse.setItemStatus(orderDetails
										.getItemStatus());
								// CreatedBy
								itemsDisplayResponse.setCreateBy(orderDetails
										.getCreatedby());
								// LastModifiedBy
								itemsDisplayResponse
										.setLastModifiedBy(orderDetails
												.getLastmodifiedby());
								// CreatedDate
								itemsDisplayResponse
										.setCreatedDate(orderDetails
												.getCreatedDate());
								// LastModifiedDate
								itemsDisplayResponse
										.setLastmodifiedDate(orderDetails
												.getLastModifiedDate());
								// Adding particular item information to
								// itemResponseList
								itemsDisplayResponseList
										.add(itemsDisplayResponse);
							}
							// Items info
							orderDisplayResponse
									.setItems(itemsDisplayResponseList);
							// Order status
							orderDisplayResponse.setOrderStatus(order
									.getOrderStatus().getDescription());
							// Shipping Address
							orderDisplayResponse.setShippingAddress(order
									.getShippingAddress());
							// Billing Address
							orderDisplayResponse.setBillingAddress(order
									.getBillingAddress());
							// CreatedBy
							orderDisplayResponse.setCreateBy(order
									.getCreateBy());
							// ModifiedBy
							orderDisplayResponse.setLastModifiedBy(order
									.getLastModifiedBy());
							// OrderedDate
							orderDisplayResponse.setOrderedDate(order
									.getOrderedDate());
							// LastModifiedDate
							orderDisplayResponse.setLastmodifiedDate(order
									.getLastmodifiedDate());
							orderDisplayResponseList.add(orderDisplayResponse);
						}
					}
				}

			} else {
				logger.error(
						"User doesn't exist in our system for the userId : {}",
						uid);
				throw new Exception(
						"User doesn't exist in our system for the userId : "
								+ uid);
			}

		} else {
			logger.error("User Id sholudn't be blank. userId : {}", uid);
			throw new Exception("User Id sholudn't be blank. userId : " + uid);
		}
		trackOrderSummary.setOrderList(orderDisplayResponseList);
		// Setting pagination things
		trackOrderSummary.setTotalPages(pageable.getTotalPages());
		trackOrderSummary.setTotalElements(pageable.getTotalElements());
		trackOrderSummary.setNumber(pageable.getNumber());
		trackOrderSummary.setSize(pageable.getSize());
		logger.info("Fetching Cancelled orders for CustomerId : {}! Success",
				uid);
		return trackOrderSummary;
	}

	/**
	 * method for Admin manage all orders
	 * 
	 * @param uid
	 * @param searchValue
	 * @param delivery
	 * @param status
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@Override
	public ManageOrderResponse getSuperAdminManageOrders(String uid,
			String searchValue, String delivery, String status, Long startDate,
			Long endDate, String customerName, String paymentDone, int page,
			int size, Sort sort) throws Exception {
		logger.info(
				"Fetching All orders for manage by Admin. Search criteria searchValue : {} delivery : {} status : {}",
				searchValue, delivery, status);
		List<OrderManageView> orderManageViewList = new ArrayList<>();
		ManageOrderResponse manageOrderResponse = new ManageOrderResponse();
		Set<PaymentDetails> paymentDetailses = null;
		Page<Order> pageable = null;
		List<Order> orderListForSummary = null;
		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findOne(uid);
			if (user != null) {
				List<Order> orderList = new ArrayList<Order>();
				orderListForSummary = new ArrayList<Order>();
				List<ShippingType> shippingTypeList = new ArrayList<>();
				List<OrderStatus> orderStatusList = new ArrayList<>();
				// filter criteria is null so fetching all orders
				if (StringUtils.isBlank(searchValue)
						&& StringUtils.isBlank(delivery)
						&& StringUtils.isBlank(status) && startDate == null
						&& endDate == null && StringUtils.isBlank(customerName)
						&& StringUtils.isBlank(paymentDone)) {
					logger.info("filter criteria is null so fetching all orders");
					pageable = userOrderRepository.findAll(new PageRequest(
							page, size, sort));
					orderList.addAll(pageable.getContent());
					// OrderSummary
					orderListForSummary = userOrderRepository.findAll();
				} else if (StringUtils.isNotBlank(searchValue)
						&& StringUtils.isNotBlank(delivery)
						&& StringUtils.isNotBlank(status) && startDate != null
						&& endDate != null) {
					Date sdate = new Date(startDate);
					Date edate = new Date(endDate);
					edate.setHours(edate.getHours() + 23);
					edate.setMinutes(edate.getMinutes() + 59);
					String[] deliveries = delivery.split(",");
					String[] statuses = status.split(",");
					for (int i = 0; i < deliveries.length; i++) {
						for (int j = 0; j < statuses.length; j++) {
							ShippingType shippingType = shippingTypeRepository
									.findByShippingType(deliveries[i]);
							shippingTypeList.add(shippingType);

							OrderStatus orderStatus = userOrderStatusRepository
									.findByDescription(statuses[j]);
							orderStatusList.add(orderStatus);
						}
					}
					pageable = userOrderRepository
							.findByMbgOrderIdAndShippingTypeInAndOrderStatusInAndOrderedDateBetween(
									searchValue, shippingTypeList,
									orderStatusList, sdate, edate,
									new PageRequest(page, size, sort));
					orderList.addAll(pageable.getContent());
					// Order summary
					orderListForSummary = userOrderRepository
							.findByMbgOrderIdAndShippingTypeInAndOrderStatusInAndOrderedDateBetween(
									searchValue, shippingTypeList,
									orderStatusList, sdate, edate);
				} else if (StringUtils.isBlank(searchValue)
						&& StringUtils.isNotBlank(delivery)
						&& StringUtils.isNotBlank(status) && startDate != null
						&& endDate != null) {
					Date sdate = new Date(startDate);
					Date edate = new Date(endDate);
					edate.setHours(edate.getHours() + 23);
					edate.setMinutes(edate.getMinutes() + 59);
					String[] deliveries = delivery.split(",");
					String[] statuses = status.split(",");
					for (int i = 0; i < deliveries.length; i++) {
						for (int j = 0; j < statuses.length; j++) {
							ShippingType shippingType = shippingTypeRepository
									.findByShippingType(deliveries[i]);
							shippingTypeList.add(shippingType);

							OrderStatus orderStatus = userOrderStatusRepository
									.findByDescription(statuses[j]);
							orderStatusList.add(orderStatus);
						}
					}
					pageable = userOrderRepository
							.findByShippingTypeInAndOrderStatusInAndOrderedDateBetween(
									shippingTypeList, orderStatusList, sdate,
									edate, new PageRequest(page, size, sort));
					orderList.addAll(pageable.getContent());
					// Order summary
					orderListForSummary = userOrderRepository
							.findByShippingTypeInAndOrderStatusInAndOrderedDateBetween(
									shippingTypeList, orderStatusList, sdate,
									edate);
				} else if (StringUtils.isBlank(searchValue)
						&& StringUtils.isBlank(delivery)
						&& StringUtils.isNotBlank(status) && startDate != null
						&& endDate != null) {
					Date sdate = new Date(startDate);
					Date edate = new Date(endDate);
					edate.setHours(edate.getHours() + 23);
					edate.setMinutes(edate.getMinutes() + 59);
					String[] statuses = status.split(",");

					for (int j = 0; j < statuses.length; j++) {
						OrderStatus orderStatus = userOrderStatusRepository
								.findByDescription(statuses[j]);
						orderStatusList.add(orderStatus);
					}

					pageable = userOrderRepository
							.findByOrderStatusInAndOrderedDateBetween(
									orderStatusList, sdate, edate,
									new PageRequest(page, size, sort));
					orderList.addAll(pageable.getContent());
					// Order summary
					orderListForSummary = userOrderRepository
							.findByOrderStatusInAndOrderedDateBetween(
									orderStatusList, sdate, edate);
				} else if (StringUtils.isBlank(searchValue)
						&& StringUtils.isNotBlank(delivery)
						&& StringUtils.isBlank(status) && startDate != null
						&& endDate != null) {
					Date sdate = new Date(startDate);
					Date edate = new Date(endDate);
					edate.setHours(edate.getHours() + 23);
					edate.setMinutes(edate.getMinutes() + 59);
					String[] deliveries = delivery.split(",");
					for (int i = 0; i < deliveries.length; i++) {
						ShippingType shippingType = shippingTypeRepository
								.findByShippingType(deliveries[i]);
						shippingTypeList.add(shippingType);
					}
					pageable = userOrderRepository
							.findByShippingTypeInAndOrderedDateBetween(
									shippingTypeList, sdate, edate,
									new PageRequest(page, size, sort));
					orderList.addAll(pageable.getContent());
					// Order summary
					orderListForSummary = userOrderRepository
							.findByShippingTypeInAndOrderedDateBetween(
									shippingTypeList, sdate, edate);
				} else if (StringUtils.isNotBlank(searchValue)
						&& StringUtils.isBlank(delivery)
						&& StringUtils.isBlank(status) && startDate != null
						&& endDate != null) {
					Date sdate = new Date(startDate);
					Date edate = new Date(endDate);
					edate.setHours(edate.getHours() + 23);
					edate.setMinutes(edate.getMinutes() + 59);
					pageable = userOrderRepository
							.findBymbgOrderIdAndOrderedDateBetween(
									shippingTypeList, sdate, edate,
									new PageRequest(page, size, sort));
					orderList.addAll(pageable.getContent());
					// Order summary
					orderListForSummary = userOrderRepository
							.findBymbgOrderIdAndOrderedDateBetween(
									shippingTypeList, sdate, edate);
				}
				// Fetching orders based on dates
				else if (startDate != null && endDate != null
						&& StringUtils.isBlank(status)
						&& StringUtils.isBlank(delivery)
						&& StringUtils.isBlank(searchValue)) {
					Date sdate = new Date(startDate);
					Date edate = new Date(endDate);
					edate.setHours(edate.getHours() + 23);
					edate.setMinutes(edate.getMinutes() + 59);
					pageable = userOrderRepository.findByOrderedDateBetween(
							sdate, edate, new PageRequest(page, size, sort));
					orderList.addAll(pageable.getContent());
					// Order summary
					orderListForSummary = userOrderRepository
							.findByOrderedDateBetween(sdate, edate);
				}
				// Fetching orders based on SearchValue, TypeOfDelivery and
				// Status
				else if (StringUtils.isNotBlank(searchValue)
						&& StringUtils.isNotBlank(delivery)
						&& StringUtils.isNotBlank(status) && startDate == null
						&& endDate == null) {
					String[] deliveries = delivery.split(",");
					String[] statuses = status.split(",");
					for (int i = 0; i < deliveries.length; i++) {
						for (int j = 0; j < statuses.length; j++) {
							ShippingType shippingType = shippingTypeRepository
									.findByShippingType(deliveries[i]);
							shippingTypeList.add(shippingType);

							OrderStatus orderStatus = userOrderStatusRepository
									.findByDescription(statuses[j]);
							orderStatusList.add(orderStatus);
						}
					}
					if (logger.isDebugEnabled()) {
						logger.debug(
								"Fetching orders based on SearchValue : {} type of deliver : {} and status : {}",
								searchValue, delivery, status);
					}
					pageable = userOrderRepository
							.findByMbgOrderIdAndShippingTypeInAndOrderStatusIn(
									searchValue, shippingTypeList,
									orderStatusList, new PageRequest(page,
											size, sort));
					orderList.addAll(pageable.getContent());
					// Order summary
					orderListForSummary = userOrderRepository
							.findByMbgOrderIdAndShippingTypeInAndOrderStatusIn(
									searchValue, shippingTypeList,
									orderStatusList);
				}
				// Fetching orders based on SearchValue and TypeOfDelivery
				else if (StringUtils.isNotBlank(searchValue)
						&& StringUtils.isNotBlank(delivery)
						&& StringUtils.isBlank(status) && startDate == null
						&& endDate == null) {
					String[] deliveries = delivery.split(",");
					for (int i = 0; i < deliveries.length; i++) {
						ShippingType shippingType = shippingTypeRepository
								.findByShippingType(deliveries[i]);
						shippingTypeList.add(shippingType);
					}
					if (logger.isDebugEnabled()) {
						logger.debug(
								"Fetching orders based on SearchValue : {} type of deliver : {}",
								searchValue, delivery);
					}
					pageable = userOrderRepository
							.findByMbgOrderIdAndShippingTypeIn(searchValue,
									shippingTypeList, new PageRequest(page,
											size, sort));
					orderList.addAll(pageable.getContent());
					// Order summary
					orderListForSummary = userOrderRepository
							.findByMbgOrderIdAndShippingTypeIn(searchValue,
									shippingTypeList);
				}
				// Fetching orders based on TypeOfDelivery and Status
				else if (StringUtils.isNotBlank(delivery)
						&& StringUtils.isNotBlank(status)
						&& StringUtils.isBlank(searchValue)
						&& startDate == null && endDate == null) {
					String[] deliveries = delivery.split(",");
					String[] statuses = status.split(",");
					for (int i = 0; i < deliveries.length; i++) {
						for (int j = 0; j < statuses.length; j++) {
							ShippingType shippingType = shippingTypeRepository
									.findByShippingType(deliveries[i]);
							shippingTypeList.add(shippingType);

							OrderStatus orderStatus = userOrderStatusRepository
									.findByDescription(statuses[j]);
							orderStatusList.add(orderStatus);
						}
					}
					if (logger.isDebugEnabled()) {
						logger.debug(
								"Fetching orders based on type of deliver : {} and status : {}",
								delivery, status);
					}
					pageable = userOrderRepository
							.findByShippingTypeInAndOrderStatusIn(
									shippingTypeList, orderStatusList,
									new PageRequest(page, size, sort));
					orderList.addAll(pageable.getContent());
					// Order summary
					orderListForSummary = userOrderRepository
							.findByShippingTypeInAndOrderStatusIn(
									shippingTypeList, orderStatusList);
				}
				// Fetching orders based on SearchValue and Status
				else if (StringUtils.isNotBlank(searchValue)
						&& StringUtils.isNotBlank(status)
						&& StringUtils.isBlank(delivery) && startDate == null
						&& endDate == null) {
					String[] statuses = status.split(",");
					for (int j = 0; j < statuses.length; j++) {
						OrderStatus orderStatus = userOrderStatusRepository
								.findByDescription(statuses[j]);
						orderStatusList.add(orderStatus);
					}
					if (logger.isDebugEnabled()) {
						logger.debug(
								"Fetching orders based on SearchValue : {} and status : {}",
								searchValue, status);
					}
					pageable = userOrderRepository
							.findByMbgOrderIdAndOrderStatusIn(searchValue,
									orderStatusList, new PageRequest(page,
											size, sort));
					orderList.addAll(pageable.getContent());
					// Order summary
					orderListForSummary = userOrderRepository
							.findByMbgOrderIdAndOrderStatusIn(searchValue,
									orderStatusList);
				}
				// Fetching orders based on SearchValue
				else if (StringUtils.isNotBlank(searchValue)
						&& StringUtils.isBlank(delivery)
						&& StringUtils.isBlank(status) && startDate == null
						&& endDate == null) {
					if (logger.isDebugEnabled()) {
						logger.debug(
								"Fetching orders based on SearchValue : {}",
								searchValue);
					}
					pageable = userOrderRepository
							.findByMbgOrderIdIgnoreCaseContaining(searchValue,
									new PageRequest(page, size, sort));
					orderList.addAll(pageable.getContent());
					// Order summary
					orderListForSummary.add(userOrderRepository
							.findByMbgOrderId(searchValue));
				}
				// Fetching orders based on TypeOfDelivery
				else if (StringUtils.isNotBlank(delivery)
						&& StringUtils.isBlank(searchValue)
						&& StringUtils.isBlank(status) && startDate == null
						& endDate == null) {
					String[] deliveries = delivery.split(",");
					for (int i = 0; i < deliveries.length; i++) {
						ShippingType shippingType = shippingTypeRepository
								.findByShippingType(deliveries[i]);
						shippingTypeList.add(shippingType);
					}
					if (logger.isDebugEnabled()) {
						logger.debug(
								"Fetching orders based on type of deliver : {}",
								delivery);
					}
					pageable = userOrderRepository
							.findByShippingTypeIn(shippingTypeList,
									new PageRequest(page, size, sort));
					orderList.addAll(pageable.getContent());
					// Order summary
					orderListForSummary = userOrderRepository
							.findByShippingTypeIn(shippingTypeList);
				}
				// Fetching orders based on Status
				else if (StringUtils.isNotBlank(status)
						&& StringUtils.isBlank(searchValue)
						&& StringUtils.isBlank(delivery) && startDate == null
						&& endDate == null) {
					String[] statuses = status.split(",");
					for (int j = 0; j < statuses.length; j++) {
						OrderStatus orderStatus = userOrderStatusRepository
								.findByDescription(statuses[j]);
						orderStatusList.add(orderStatus);
					}
					if (logger.isDebugEnabled()) {
						logger.debug("Fetching orders based on status : {}",
								status);
					}
					pageable = userOrderRepository.findByOrderStatusIn(
							orderStatusList, new PageRequest(page, size, sort));
					orderList.addAll(pageable.getContent());
					// Order summary
					orderListForSummary = userOrderRepository
							.findByOrderStatusIn(orderStatusList);
				}
				// Fetching orders based on customer name
				if (StringUtils.isNotBlank(customerName)
						&& StringUtils.isBlank(searchValue)
						&& StringUtils.isBlank(delivery)
						&& StringUtils.isBlank(status) && startDate == null
						&& endDate == null) {
					pageable = userOrderRepository
							.findByUserInfo_FirstnameIgnoreCaseContaining(
									customerName, new PageRequest(page, size,
											sort));
					if (pageable.getContent().size() == 0) {
						pageable = userOrderRepository
								.findByUserInfo_LastnameIgnoreCaseContaining(
										customerName, new PageRequest(page,
												size, sort));
					}
					orderList.addAll(pageable.getContent());
				}
				// PaymentDone
				if (StringUtils.isNotBlank(paymentDone)) {
					if (paymentDone.equalsIgnoreCase("TRUE")) {
						pageable = userOrderRepository.findByPaymentDone(true,
								new PageRequest(page, size, sort));
					} else {
						pageable = userOrderRepository.findByPaymentDone(false,
								new PageRequest(page, size, sort));
					}
					orderList.addAll(pageable.getContent());
				}
				for (Order order : orderList) {
					if (order != null) {
						OrderManageView orderManageView = new OrderManageView();
						List<String> categoryList = new ArrayList<>();
						Set<SubCategory> subCategoryListMain = new HashSet<>();
						double totalAmount = 0;
						double paymentDoneAmt = 0;
						int totalItems = 0;
						int noOfItems = 0;
						Set<String> itemsName = new HashSet<>();
						List<InvoiceDetails> invoice = null;
						List<String> url = new ArrayList<String>();
						if (order.getMbgOrderId() != null) {
							orderManageView.setOrderId(order.getMbgOrderId());
						}

						if (order.getUserInfo() != null) {
							UserInfo userInfo = order.getUserInfo();
							if (userInfo != null) {
								// CustomerName
								if (userInfo.getFirstname() != null
										&& userInfo.getLastname() != null) {
									orderManageView.setCustomerName(userInfo
											.getFirstname()
											+ " "
											+ userInfo.getLastname());
								} else if (userInfo.getFirstname() != null) {
									orderManageView.setCustomerName(userInfo
											.getFirstname());
								}

								// Contact Number
								if (userInfo.getPhonenumber() != null) {
									orderManageView
											.setContactNumber(String
													.valueOf(userInfo
															.getPhonenumber()));
								}
								// CustomerId
								if (userInfo.getMongoUserId() != null) {
									orderManageView.setCustomerId(userInfo
											.getMongoUserId());

									// Customer RoleName
									User customer = userDAO.findById(userInfo
											.getMongoUserId());
									if (customer != null) {
										if (customer.getRoles() != null) {
											Set<Role> roles = customer
													.getRoles();
											for (Role role : roles) {
												if (role != null) {
													if (role.getName() != null) {
														orderManageView
																.setCustomerRoleName(role
																		.getName());
													}
												}
											}
										}
									}
								}

							}
						}
						if (order.getOrderedDate() != null) {
							logger.info("Order date loop begin");
							long date = order.getOrderedDate().getTime();
							Date orderDate = new Date(date);
							orderManageView.setOrderedDate(orderDate);
							logger.info("Order date loop end");
						}
						List<OrderDetails> itemsList = new ArrayList<>();
						itemsList = userOrderDetailRepository
								.findByOrder(order);
						Set<String> dealerIdList = new HashSet<>();
						int estimatedDeliveryDate = 0;

						// paymentDetails
						paymentDetailses = userPaymentDetailsRepository
								.findByOrder(order);
						Set<PaymentDetailsResponse> paymentDetailsResponseSet = new HashSet<>();

						for (PaymentDetails paymentDetails : paymentDetailses) {
							if (paymentDetails != null) {
								PaymentDetailsResponse paymentDetailsResponse = new PaymentDetailsResponse();
								// payment method name
								if (paymentDetails.getPaymentType() != null) {
									if (paymentDetails.getPaymentType()
											.getPaymentType() != null) {
										paymentDetailsResponse
												.setPaymentMethod(paymentDetails
														.getPaymentType()
														.getPaymentType());
									}
								}

								// payment number
								if (paymentDetails.getPaymentNumber() != null) {
									paymentDetailsResponse
											.setPaymentNumber(paymentDetails
													.getPaymentNumber());
								}

								// payment status
								paymentDetailsResponse
										.setPaymentStatus(paymentDetails
												.getPaymentStatus());
								// payment metadata
								if (paymentDetails.getPaymentMetadata() != null) {
									paymentDetailsResponse
											.setPaymentMetadata(paymentDetails
													.getPaymentMetadata());
								}
								// payment amount
								paymentDetailsResponse.setAmount(paymentDetails
										.getAmount());
								// BankName
								if (paymentDetails.getBankName() != null) {
									paymentDetailsResponse
											.setBankName(paymentDetails
													.getBankName());
								}
								// chequeDate
								if (paymentDetails.getChequeDate() != null) {
									paymentDetailsResponse
											.setChequeDate(paymentDetails
													.getChequeDate());
								}

								paymentDetailsResponseSet
										.add(paymentDetailsResponse);
								if (paymentDetails.getPaymentStatus() == 1) {
									// PaymentDone Amt
									paymentDoneAmt = paymentDoneAmt
											+ paymentDetails.getAmount();
								}
							}
						}
						orderManageView
								.setPaymentDetails(paymentDetailsResponseSet);
						Set<Integer> itemStatusIds = new HashSet<Integer>();

						for (OrderDetails orderDetails : itemsList) {
							if (orderDetails != null) {
								Set<SubCategory> subCategoryList = new HashSet<>();
								if (orderDetails.getDealerId() != null) {
									String dealerId = orderDetails
											.getDealerId();
									dealerIdList.add(dealerId);
								}
								if (!orderDetails.getItemStatus()
										.getDescription()
										.equalsIgnoreCase("UnConfirmed")) {
									// Order amount
									double totalPrice = orderDetails
											.getTotalPrice();

									// tax amount
									double taxPrice;
									if (order.getShippingCharge() == 0) {
										taxPrice = ((((orderDetails
												.getSellingPrice() * orderDetails
												.getQuantity()) + orderDetails
												.getShippingCharge()) * orderDetails
												.getTax()) / 100);
									} else {
										taxPrice = (((orderDetails
												.getSellingPrice() * orderDetails
												.getQuantity()) * orderDetails
												.getTax()) / 100);
									}

									totalAmount = totalAmount + totalPrice
											+ taxPrice;
									// Number of items
									noOfItems = noOfItems + 1;

									// Items Name
									if (orderDetails.getItemName() != null) {
										itemsName.add(orderDetails
												.getItemName());
									}

									// Order Items
									int itemsQuantity = orderDetails
											.getQuantity();
									totalItems = totalItems + itemsQuantity;
								}

								// SubCategory
								if (orderDetails.getItemId() != null) {
									String itemId = orderDetails.getItemId();
									Product product = productDAO
											.findOne(itemId);
									if (product != null) {
										if (product.getSubcategories() != null) {
											subCategoryList = product
													.getSubcategories();
										}
									}
									// Category
									for (SubCategory subcategory : subCategoryList) {
										subCategoryListMain.add(subcategory);
										categoryList.add(subcategory
												.getCategory());
									}
								}
								// Calculating Estimate Delivery Date
								if (estimatedDeliveryDate <= orderDetails
										.getMaxDeliveryTimeInDays()) {
									estimatedDeliveryDate = orderDetails
											.getMaxDeliveryTimeInDays();
								} else if (estimatedDeliveryDate > orderDetails
										.getMaxDeliveryTimeInDays()) {
									continue;
								}
							}

							// Item status
							itemStatusIds.add(orderDetails.getItemStatus()
									.getItemStatusId());
						}
						orderManageView.setSubCategory(subCategoryListMain);
						orderManageView.setCategory(categoryList);
						// Estimated deliveryDate
						orderManageView
								.setEstimatedDeliveryDate(estimatedDeliveryDate);

						// invoice

						invoice = invoiceRepository.findByOrder(order);
						if (invoice != null && invoice.size() != 0) {
							for (InvoiceDetails details : invoice) {
								User user1 = userDAO.findById(details
										.getUserId());
								if (user1 != null) {
									Set<Role> roles = user1.getRoles();
									for (Role role : roles) {
										if (role.getName().equalsIgnoreCase(
												"ENDUSER")) {
											orderManageView
													.setEnduserInvocie(details
															.getFileUrl());
										} else {
											logger.info("Dealer invoice:{}",
													user1.getId());
											url.add(details.getFileUrl());
										}
									}
								}
							}
							orderManageView.setInvoices(url);
						} else {
							logger.info("Order invoice for Enduser null:{}",
									order.getOrderId());
						}

						int[] statusIdArray = new int[itemStatusIds.size()];
						int j = 0;
						for (int id : itemStatusIds) {
							statusIdArray[j] = id;
							j++;
						}

						// Calculating orderId based on itemId
						int minVal = 10;
						OrderStatus orderStatus = null;
						if (order.getItems().size() == 1) {

							for (int i = 0; i < statusIdArray.length; i++) {
								if (minVal <= statusIdArray[i]) {
									continue;
								} else {
									minVal = statusIdArray[i];
								}
							}
							// Order status
							orderStatus = userOrderStatusRepository
									.findByOrderStatusId(minVal);
						} else {

							for (int i = 0; i < statusIdArray.length; i++) {
								if (statusIdArray[i] != 3) {
									if (minVal <= statusIdArray[i]) {
										continue;
									} else {
										minVal = statusIdArray[i];
									}
								}
							}
							// Order status
							if (minVal == 10) {
								orderStatus = userOrderStatusRepository
										.findByOrderStatusId(3);
							} else {
								orderStatus = userOrderStatusRepository
										.findByOrderStatusId(minVal);
							}
						}

						order.setOrderStatus(orderStatus);
						userOrderRepository.save(order);
						orderManageView.setOrderStatus(orderStatus
								.getDescription());
						// ExtraBenefits
						totalAmount = totalAmount + order.getExtraBenefits();
						// Total price
						if (!order.getOrderStatus().getDescription()
								.equalsIgnoreCase("UnConfirmed")) {
							orderManageView.setTotalPrice(totalAmount
									+ order.getShippingCharge());
						} else {
							orderManageView.setTotalPrice(totalAmount);
						}
						// Total items
						orderManageView.setTotalItems(totalItems);
						// Number of Items
						orderManageView.setNoOfItems(noOfItems);
						// ItemsName
						orderManageView.setItemsName(itemsName);
						// Shipping type
						orderManageView.setShippingType(order.getShippingType()
								.getShippingType());
						// Order level shipping charge
						orderManageView.setShippingCharge(order
								.getShippingCharge());
						// TaxInvoiceNumber
						if (order.getInvoiceNumber() != null) {
							orderManageView.setInvoiceNumber(order
									.getInvoiceNumber());
						}
						// Payment done
						double res = totalAmount - paymentDoneAmt;
						if (res <= 10) {
							orderManageView.setPaymentDone(true);
							order.setPaymentDone(true);
							userOrderRepository.save(order);
						} else {
							orderManageView.setPaymentDone(false);
						}
						orderManageViewList.add(orderManageView);
					}
				}
			} else {
				logger.error("User is not exist in our system for userId : {}",
						uid);
				throw new Exception(
						"User is not exist in our system for userId : " + uid);
			}
		} else {
			logger.error("User id should not be empty. userId : {}", uid);
			throw new Exception("User id should not be empty. userId : " + uid);
		}
		// Order summary
		if (startDate != null && endDate != null) {
			manageOrderResponse
					.setOrderSummary(getOrderSummary(orderListForSummary));
		}
		manageOrderResponse.setOrderManageViewList(orderManageViewList);
		// Setting pagination fields
		manageOrderResponse.setTotalPages(pageable.getTotalPages());
		manageOrderResponse.setTotalElements(pageable.getTotalElements());
		manageOrderResponse.setNumber(pageable.getNumber());
		manageOrderResponse.setSize(pageable.getSize());
		logger.info("Fetching All orders for manage by Admin! Success");
		return manageOrderResponse;
	}

	public OrderSummaryResponse getOrderSummary(List<Order> orderListForSummary) {
		OrderSummaryResponse orderSummaryResponse = new OrderSummaryResponse();
		double osAmt = 0;
		int osItemsCount = 0;
		Set<UserInfo> users = new HashSet<>();
		Set<String> existingUsers = new HashSet<>();
		Set<String> newUsers = new HashSet<>();

		List<OrderDetails> osItemsList = new ArrayList<>();

		// Order Summary
		for (Order order : orderListForSummary) {
			users.add(order.getUserInfo());

			List<Order> orders = userOrderRepository.findByUserInfo(order
					.getUserInfo());
			String customerName = null;
			String roleName = null;
			User user = userDAO.findById(order.getUserInfo().getMongoUserId());
			Set<Role> roles = user.getRoles();
			for (Role role : roles) {
				roleName = role.getName();
			}
			if (order.getUserInfo().getFirstname() != null
					&& order.getUserInfo().getLastname() != null) {
				customerName = order.getUserInfo().getFirstname() + " "
						+ order.getUserInfo().getLastname();
			} else {
				customerName = order.getUserInfo().getFirstname();
			}

			if (orders.size() > 1) {
				existingUsers
						.add(customerName + ","
								+ order.getUserInfo().getMongoUserId() + ","
								+ roleName);
			} else if (orders.size() == 1) {
				newUsers.add(customerName + ","
						+ order.getUserInfo().getMongoUserId() + "," + roleName);
			}
			osItemsList = userOrderDetailRepository.findByOrder(order);
			for (OrderDetails item : osItemsList) {
				if (!item.getItemStatus().getDescription()
						.equalsIgnoreCase("UnConfirmed")) {
					// Order amount
					double totalPrice = item.getTotalPrice();

					// tax amount
					double taxPrice;
					if (order.getShippingCharge() == 0) {
						taxPrice = ((((item.getSellingPrice() * item
								.getQuantity()) + item.getShippingCharge()) * item
								.getTax()) / 100);
					} else {
						taxPrice = (((item.getSellingPrice() * item
								.getQuantity()) * item.getTax()) / 100);
					}

					osAmt = osAmt + totalPrice + taxPrice;
					// Number of items
					osItemsCount = osItemsCount + 1;
				}

			}
			// Total price
			if (!order.getOrderStatus().getDescription()
					.equalsIgnoreCase("UnConfirmed")) {
				osAmt = osAmt + order.getShippingCharge();
			}
		}
		orderSummaryResponse.setOrdersCount(orderListForSummary.size());
		orderSummaryResponse.setOrdersAmount(osAmt);
		orderSummaryResponse.setNumberOfUsers(users.size());
		orderSummaryResponse.setOldUsers(existingUsers);
		orderSummaryResponse.setNewUsers(newUsers);
		return orderSummaryResponse;
	}

	/**
	 * method for Dealer manage all orders
	 * 
	 * @param uid
	 * @param searchValue
	 * @param delivery
	 * @param status
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@Override
	public ManageOrderResponse getDealerManageOrders(String uid,
			String searchValue, String delivery, String status, int page,
			int size, Sort sort) throws Exception {
		logger.info(
				"Fetching orders belongs to dealer for manage by Dealer. dealerId : {}",
				uid);
		List<OrderManageView> orderManageViewList = new ArrayList<>();
		ManageOrderResponse manageOrderResponse = new ManageOrderResponse();
		List<Order> orderList = new ArrayList<>();
		List<Order> orderListForTotalElement = new ArrayList<>();
		List<ShippingType> shippingTypeList = new ArrayList<>();
		List<OrderStatus> orderStatusList = new ArrayList<>();
		Page<Order> pageable = null;
		List<OrderDetails> items = null;
		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findOne(uid);
			if (user != null) {
				// filter criteria is null so fetching all orders belongs to
				// dealerId
				if (StringUtils.isBlank(searchValue)
						&& StringUtils.isBlank(delivery)
						&& StringUtils.isBlank(status)) {
					if (logger.isDebugEnabled()) {
						logger.debug(
								"filter criteria is null so fetching all orders belongs to dealerId : {}",
								uid);
					}
					pageable = userOrderRepository
							.findDistinctOrderByItems_dealerId(uid,
									new PageRequest(page, size, sort));

					orderListForTotalElement = userOrderRepository
							.findDistinctOrderByItems_dealerId(uid);

					orderList.addAll(pageable.getContent());
				}
				// Fetching dealer orders based on SearchValue,typeOfDeliver and
				// status
				else if (StringUtils.isNotBlank(searchValue)
						&& StringUtils.isNotBlank(delivery)
						&& StringUtils.isNotBlank(status)) {
					String[] deliveries = delivery.split(",");
					String[] statuses = status.split(",");
					for (int i = 0; i < deliveries.length; i++) {
						for (int j = 0; j < statuses.length; j++) {
							ShippingType shippingType = shippingTypeRepository
									.findByShippingType(deliveries[i]);
							shippingTypeList.add(shippingType);

							OrderStatus orderStatus = userOrderStatusRepository
									.findByDescription(statuses[j]);
							orderStatusList.add(orderStatus);
						}
					}
					if (logger.isDebugEnabled()) {
						logger.debug(
								"Fetching dealer orders based on SearchValue : {} type of deliver : {} and status : {}",
								searchValue, delivery, status);
					}
					pageable = userOrderRepository
							.findDistinctOrderByMbgOrderIdAndItems_dealerIdAndShippingTypeInAndOrderStatusIn(
									searchValue, uid, shippingTypeList,
									orderStatusList, new PageRequest(page,
											size, sort));

					orderListForTotalElement = userOrderRepository
							.findDistinctOrderByMbgOrderIdAndItems_dealerIdAndShippingTypeInAndOrderStatusIn(
									searchValue, uid, shippingTypeList,
									orderStatusList);

					orderList.addAll(pageable.getContent());
				}
				// Fetching dealer orders based on SearchValue and typeOfDeliver
				else if (StringUtils.isNotBlank(searchValue)
						&& StringUtils.isNotBlank(delivery)
						&& StringUtils.isBlank(status)) {
					String[] deliveries = delivery.split(",");
					for (int i = 0; i < deliveries.length; i++) {
						ShippingType shippingType = shippingTypeRepository
								.findByShippingType(deliveries[i]);
						shippingTypeList.add(shippingType);
					}
					if (logger.isDebugEnabled()) {
						logger.debug(
								"Fetching dealer orders based on SearchValue : {} type of deliver : {}",
								searchValue, delivery);
					}
					pageable = userOrderRepository
							.findDistinctOrderByMbgOrderIdAndItems_dealerIdAndShippingTypeIn(
									searchValue, uid, shippingTypeList,
									new PageRequest(page, size, sort));

					orderListForTotalElement = userOrderRepository
							.findDistinctOrderByMbgOrderIdAndItems_dealerIdAndShippingTypeIn(
									searchValue, uid, shippingTypeList);

					orderList.addAll(pageable.getContent());
				}
				// Fetching dealer orders based on typeOfDeliver and status
				else if (StringUtils.isNotBlank(delivery)
						&& StringUtils.isNotBlank(status)
						&& StringUtils.isBlank(searchValue)) {
					String[] deliveries = delivery.split(",");
					String[] statuses = status.split(",");
					for (int i = 0; i < deliveries.length; i++) {
						for (int j = 0; j < statuses.length; j++) {
							ShippingType shippingType = shippingTypeRepository
									.findByShippingType(deliveries[i]);
							shippingTypeList.add(shippingType);

							OrderStatus orderStatus = userOrderStatusRepository
									.findByDescription(statuses[j]);
							orderStatusList.add(orderStatus);
						}
					}
					if (logger.isDebugEnabled()) {
						logger.debug(
								"Fetching dealer orders based on type of deliver : {} and status : {}",
								delivery, status);
					}
					pageable = userOrderRepository
							.findDistinctOrderByItems_dealerIdAndShippingTypeInAndOrderStatusIn(
									uid, shippingTypeList, orderStatusList,
									new PageRequest(page, size, sort));

					orderListForTotalElement = userOrderRepository
							.findDistinctOrderByItems_dealerIdAndShippingTypeInAndOrderStatusIn(
									uid, shippingTypeList, orderStatusList);

					orderList.addAll(pageable.getContent());
				}
				// Fetching dealer orders based on SearchValue and status
				else if (StringUtils.isNotBlank(searchValue)
						&& StringUtils.isNotBlank(status)
						&& StringUtils.isBlank(delivery)) {
					String[] statuses = status.split(",");
					for (int j = 0; j < statuses.length; j++) {
						OrderStatus orderStatus = userOrderStatusRepository
								.findByDescription(statuses[j]);
						orderStatusList.add(orderStatus);
					}
					if (logger.isDebugEnabled()) {
						logger.debug(
								"Fetching dealer orders based on SearchValue : {} and status : {}",
								searchValue, status);
					}
					pageable = userOrderRepository
							.findDistinctOrderByMbgOrderIdAndItems_dealerIdAndOrderStatusIn(
									searchValue, uid, orderStatusList,
									new PageRequest(page, size, sort));

					orderListForTotalElement = userOrderRepository
							.findDistinctOrderByMbgOrderIdAndItems_dealerIdAndOrderStatusIn(
									searchValue, uid, orderStatusList);

					orderList.addAll(pageable.getContent());
				}
				// Fetching dealer orders based on SearchValue
				else if (StringUtils.isNotBlank(searchValue)
						&& StringUtils.isBlank(delivery)
						&& StringUtils.isBlank(status)) {
					if (logger.isDebugEnabled()) {
						logger.debug(
								"Fetching dealer orders based on SearchValue : {}",
								searchValue);
					}
					pageable = userOrderRepository
							.findDistinctOrderByMbgOrderIdAndItems_dealerId(
									searchValue, uid, new PageRequest(page,
											size, sort));

					orderListForTotalElement = userOrderRepository
							.findDistinctOrderByMbgOrderIdAndItems_dealerId(
									searchValue, uid);

					orderList.addAll(pageable.getContent());
				}
				// Fetching dealer orders based on typeOfDeliver
				else if (StringUtils.isNotBlank(delivery)
						&& StringUtils.isBlank(searchValue)
						&& StringUtils.isBlank(status)) {
					String[] deliveries = delivery.split(",");
					for (int i = 0; i < deliveries.length; i++) {
						ShippingType shippingType = shippingTypeRepository
								.findByShippingType(deliveries[i]);
						shippingTypeList.add(shippingType);
					}
					if (logger.isDebugEnabled()) {
						logger.debug(
								"Fetching dealer orders based on type of deliver : {}",
								delivery);
					}
					pageable = userOrderRepository
							.findDistinctOrderByItems_dealerIdAndShippingTypeIn(
									uid, shippingTypeList, new PageRequest(
											page, size, sort));

					orderListForTotalElement = userOrderRepository
							.findDistinctOrderByItems_dealerIdAndShippingTypeIn(
									uid, shippingTypeList);

					orderList.addAll(pageable.getContent());
				}
				// Fetching dealer orders based on status
				else if (StringUtils.isNotBlank(status)
						&& StringUtils.isBlank(searchValue)
						&& StringUtils.isBlank(delivery)) {
					String[] statuses = status.split(",");
					for (int j = 0; j < statuses.length; j++) {
						OrderStatus orderStatus = userOrderStatusRepository
								.findByDescription(statuses[j]);
						orderStatusList.add(orderStatus);
					}
					if (logger.isDebugEnabled()) {
						logger.debug(
								"Fetching dealer orders based on status : {}",
								status);
					}
					pageable = userOrderRepository
							.findDistinctOrderByItems_dealerIdAndOrderStatusIn(
									uid, orderStatusList, new PageRequest(page,
											size, sort));

					orderListForTotalElement = userOrderRepository
							.findDistinctOrderByItems_dealerIdAndOrderStatusIn(
									uid, orderStatusList);

					orderList.addAll(pageable.getContent());
				}
				for (Order order : orderList) {
					items = userOrderDetailRepository.findByOrderAndDealerId(
							order, uid);
					if (items.size() != 0) {
						OrderManageView orderManageView = new OrderManageView();

						// Order id
						orderManageView.setOrderId(order.getMbgOrderId());
						// Ordered date
						long date = order.getOrderedDate().getTime();
						Date orderDate = new Date(date);
						orderManageView.setOrderedDate(orderDate);
						double totalAmount = 0;
						int totalItems = 0;
						int noOfItems = 0;
						List<String> categoryList = new ArrayList<>();
						Set<SubCategory> subCategoryListMain = new HashSet<>();
						int estimatedDeliveryDate = 0;
						for (OrderDetails orderDetails : items) {
							Set<SubCategory> subCategoryList = new HashSet<>();
							// Order amount
							double totalPrice = orderDetails.getTotalPrice();

							// Tax amount
							double taxPrice;
							if (order.getShippingCharge() == 0) {
								taxPrice = ((((orderDetails.getSellingPrice() * orderDetails
										.getQuantity()) + orderDetails
										.getShippingCharge()) * orderDetails
										.getTax()) / 100);
							} else {
								taxPrice = (((orderDetails.getSellingPrice() * orderDetails
										.getQuantity()) * orderDetails.getTax()) / 100);
							}

							totalAmount = totalAmount + totalPrice + taxPrice;

							// Order Items
							int itemsQuantity = orderDetails.getQuantity();
							totalItems = totalItems + itemsQuantity;
							// Number of items
							noOfItems = noOfItems + 1;
							// SubCategory
							String itemId = orderDetails.getItemId();
							if (itemId != null) {
								Product product = productDAO.findOne(itemId);
								if (product != null) {
									subCategoryList = product
											.getSubcategories();

									// Category
									for (SubCategory subcategory : subCategoryList) {
										subCategoryListMain.add(subcategory);
										categoryList.add(subcategory
												.getCategory());
									}
								}
							}
							// Calculating estimateDeliveryDate
							if (estimatedDeliveryDate <= orderDetails
									.getMaxDeliveryTimeInDays()) {
								estimatedDeliveryDate = orderDetails
										.getMaxDeliveryTimeInDays();
							} else if (estimatedDeliveryDate > orderDetails
									.getMaxDeliveryTimeInDays()) {
								continue;
							}

						}
						// EstimatedDeliveryDate
						orderManageView
								.setEstimatedDeliveryDate(estimatedDeliveryDate);
						orderManageView.setSubCategory(subCategoryListMain);
						orderManageView.setCategory(categoryList);
						// Shipping type
						if (order.getShippingType() != null) {
							orderManageView.setShippingType(order
									.getShippingType().getShippingType());
						}
						// Order status
						orderManageView.setOrderStatus(order.getOrderStatus()
								.getDescription());
						// ExtraBenefits
						totalAmount = totalAmount + order.getExtraBenefits();
						// Total price
						orderManageView.setTotalPrice(totalAmount
								+ order.getShippingCharge());
						// Total item
						orderManageView.setTotalItems(totalItems);
						// Number of items
						orderManageView.setNoOfItems(noOfItems);
						// order level shipping charge
						orderManageView.setShippingCharge(order
								.getShippingCharge());
						orderManageViewList.add(orderManageView);
					}
				}
			} else {
				logger.error("User not exist in our system for userId : {}",
						uid);
				throw new Exception(
						"User not exist in our system for userId : " + uid);
			}
		} else {
			logger.error("User id should not be blank. userId : {}", uid);
			throw new Exception("User id should not be blank. userId : " + uid);
		}
		manageOrderResponse.setOrderManageViewList(orderManageViewList);
		// Setting pagination fields
		if (pageable != null) {
			int totalElements = (int) orderListForTotalElement.size();
			if (totalElements % pageable.getSize() == 0) {
				manageOrderResponse.setTotalPages(totalElements
						/ pageable.getSize());
			} else {
				manageOrderResponse.setTotalPages((totalElements / pageable
						.getSize()) + 1);
			}
			manageOrderResponse.setTotalElements(totalElements);
			manageOrderResponse.setNumber(pageable.getNumber());
			manageOrderResponse.setSize(pageable.getSize());
		}
		return manageOrderResponse;
	}

	/**
	 * method for admin manage orders.
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 * @throws Exception
	 */

	@Override
	public SuperAdminOrderViewDetails getSuperAdminOrderViewDetails(
			String userId, String orderId) throws Exception {
		logger.info(
				"Fetching SuperAdminOrderViewDetails based on orderId : {} and userId : {}",
				orderId, userId);
		SuperAdminOrderViewDetails superAdminOrderViewDetails = null;
		Order order = null;
		User user = null;
		Set<PaymentDetails> paymentDetailses = null;
		// Checking userId and orderId null or not
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(orderId)) {
			user = userDAO.findOne(userId);
			UserInfo userInfo = userRepository.findByMongoUserId(userId);
			order = userOrderRepository.findByMbgOrderId(orderId);
			// Checking user and userInfo and order is null or not
			if (user != null && userInfo != null && order != null) {
				superAdminOrderViewDetails = new SuperAdminOrderViewDetails();
				double totalAmount = 0;
				int totalItems = 0;

				List<InvoiceDetails> invoice = null;
				List<String> urls = new ArrayList<String>();

				// Customer Id
				UserInfo customer = order.getUserInfo();
				if (customer != null) {
					superAdminOrderViewDetails.setUserId(customer
							.getMongoUserId());
				}

				// OrderId
				superAdminOrderViewDetails.setOrderId(order.getMbgOrderId());

				// Customer email
				if (order.getUserInfo().getUsername() != null) {
					superAdminOrderViewDetails.setEmailId(order.getUserInfo()
							.getUsername());
				}

				// Customer phonenumber
				if (order.getUserInfo().getPhonenumber() != null) {
					superAdminOrderViewDetails.setPhoneNumber(order
							.getUserInfo().getPhonenumber().toString());
				}

				// order level shipping charge
				superAdminOrderViewDetails.setShippingCharge(order
						.getShippingCharge());

				// TaxInvoice number
				superAdminOrderViewDetails.setInvoiceNum(order
						.getInvoiceNumber());

				// ExtraBenefits
				superAdminOrderViewDetails.setExtraBenefits(order
						.getExtraBenefits());

				// invoice
				invoice = invoiceRepository.findByOrder(order);
				if (invoice != null && invoice.size() != 0) {
					for (InvoiceDetails details : invoice) {
						if (details != null && details.getFileUrl() != null) {
							urls.add(details.getFileUrl());
						}
					}
					superAdminOrderViewDetails.setInvoices(urls);
				} else {
					logger.info("Order invoice for SuperAdmin null:{}",
							order.getOrderId());
				}

				// shippingType
				if (order.getShippingType().getShippingType() != null) {
					superAdminOrderViewDetails.setShippingType(order
							.getShippingType().getShippingType());
				}
				// paymentDetails
				paymentDetailses = userPaymentDetailsRepository
						.findByOrder(order);

				Set<PaymentDetailsResponse> paymentDetailsResponseSet = new HashSet<>();
				for (PaymentDetails paymentDetails : paymentDetailses) {
					if (paymentDetails != null) {
						PaymentDetailsResponse paymentDetailsResponse = new PaymentDetailsResponse();
						// paymentDetailsId
						paymentDetailsResponse.setPaymentDetailsId(String
								.valueOf(paymentDetails.getPaymentId()));
						// payment method name
						if (paymentDetails.getPaymentType() != null) {
							paymentDetailsResponse
									.setPaymentMethod(paymentDetails
											.getPaymentType().getPaymentType());
						}
						// payment number
						paymentDetailsResponse.setPaymentNumber(paymentDetails
								.getPaymentNumber());
						// payment status
						paymentDetailsResponse.setPaymentStatus(paymentDetails
								.getPaymentStatus());
						// payment metadata
						if (paymentDetails.getPaymentMetadata() != null) {
							paymentDetailsResponse
									.setPaymentMetadata(paymentDetails
											.getPaymentMetadata());
						}
						// payment amount
						paymentDetailsResponse.setAmount(paymentDetails
								.getAmount());
						// BankName
						if (paymentDetails.getBankName() != null) {
							paymentDetailsResponse.setBankName(paymentDetails
									.getBankName());
						}
						// ChequeDate
						if (paymentDetails.getChequeDate() != null) {
							paymentDetailsResponse.setChequeDate(paymentDetails
									.getChequeDate());
						}
						paymentDetailsResponseSet.add(paymentDetailsResponse);
					}
				}
				superAdminOrderViewDetails
						.setPaymentDetails(paymentDetailsResponseSet);
				// Shipping address
				OrderAddress shippingAddress = userOrderAddressRepository
						.findOne(order.getShippingAddress().getAddressId());
				if (shippingAddress != null) {
					superAdminOrderViewDetails
							.setShippingAddress(shippingAddress);
				}

				// Billing address
				OrderAddress billingAddress = userOrderAddressRepository
						.findOne(order.getBillingAddress().getAddressId());
				if (billingAddress != null) {
					superAdminOrderViewDetails
							.setBillingAddress(billingAddress);
				}

				Set<SubCategory> subCategoryListmain = new HashSet<>();
				// Order audit
				List<OrderAuditResponse> orderAuditResposeList = new ArrayList<>();
				List<OrderAudit> orderAuditList = orderAuditRepository
						.findByOrderId(order.getOrderId());
				if (orderAuditList != null && orderAuditList.size() != 0) {
					for (OrderAudit orderAudit : orderAuditList) {
						OrderAuditResponse orderAuditRespose = new OrderAuditResponse();
						orderAuditRespose.setCreatedTime(orderAudit
								.getCreatedTime());
						orderAuditRespose.setLastModifiedTime(orderAudit
								.getLastModifiedTime());
						orderAuditRespose.setItemId(orderAudit.getItemId());
						orderAuditRespose.setNote(orderAudit.getNote());
						orderAuditRespose.setOrderId(orderAudit.getOrderId());
						orderAuditRespose.setOrderAuditType(orderAudit
								.getOrderAuditType());
						orderAuditResposeList.add(orderAuditRespose);
					}
					superAdminOrderViewDetails
							.setOrderAuditRespose(orderAuditResposeList);
				}

				List<String> categoryList = new ArrayList<>();

				List<OrderDetails> orderDetailsList = userOrderDetailRepository
						.findByOrder(order);
				List<GetUserOrderItemsResponse> items = new ArrayList<>();
				Set<Integer> itemStatusIds = new HashSet<Integer>();
				int estimatedDeliveryDate = 0;
				// Items info
				for (OrderDetails orderDetails : orderDetailsList) {
					GetUserOrderItemsResponse getUserOrderItemsResponse = new GetUserOrderItemsResponse();
					// Item serial number
					getUserOrderItemsResponse.setItemId(orderDetails
							.getOrderDetailsId());
					// Item(Product) Id
					getUserOrderItemsResponse.setPid(orderDetails.getItemId());
					// Item name
					if (orderDetails.getItemName() != null) {
						getUserOrderItemsResponse.setItemName(orderDetails
								.getItemName());
					}
					// Checking SellByDealer or MBG
					Aggregation agg = Aggregation.newAggregation(Aggregation
							.match(Criteria.where("user").is(
									orderDetails.getDealerId())), Aggregation
							.project("storename").and("id"));

					// Converting the aggregation result into a List
					AggregationResults<StoreProductCount> groupResults = mongoTemplate
							.aggregate(agg, Store.class,
									StoreProductCount.class);
					List<StoreProductCount> result = groupResults
							.getMappedResults();

					boolean sellByDealer = false;
					if (result.size() != 0) {
						StoreProductPricing storeProductPricing = storeProductPricingDAO
								.findByProductIdAndStoreId(
										orderDetails.getItemId(),
										result.get(0).id);

						if (storeProductPricing != null) {
							sellByDealer = storeProductPricing
									.isStoreDelivery();
						}
					}

					if (sellByDealer) {
						getUserOrderItemsResponse.setSellName("Seller");
					} else {
						getUserOrderItemsResponse.setSellName("Buildonn");
					}

					// Quantity
					getUserOrderItemsResponse.setQuantity(orderDetails
							.getQuantity());
					// Product asset and subCategory
					Product product = productDAO.findOne(orderDetails
							.getItemId());
					Set<SubCategory> subCategoryList = new HashSet<>();
					if (product != null) {
						getUserOrderItemsResponse.setProductAsset(product
								.getAssets());
						subCategoryList = product.getSubcategories();
						getUserOrderItemsResponse
								.setSubCategories(subCategoryList);
					}
					// Mrp
					getUserOrderItemsResponse.setMrp(orderDetails.getMrp());
					// Tax
					getUserOrderItemsResponse.setTax(orderDetails.getTax());
					// SellingPrice
					getUserOrderItemsResponse.setSellingPrice(orderDetails
							.getSellingPrice());
					DecimalFormat df = new DecimalFormat("####0.00");
					// Discount
					getUserOrderItemsResponse.setDiscount(Double.valueOf(df
							.format((((orderDetails.getMrp() - orderDetails
									.getSellingPrice()) * 100) / orderDetails
									.getMrp()))));
					// Shipping charge
					getUserOrderItemsResponse.setShippingCharge(orderDetails
							.getShippingCharge());
					// Total price
					getUserOrderItemsResponse.setTotalPrice(orderDetails
							.getTotalPrice());

					// Tax price
					double taxPrice;
					if (order.getShippingCharge() == 0) {
						taxPrice = ((((orderDetails.getSellingPrice() * orderDetails
								.getQuantity()) + orderDetails
								.getShippingCharge()) * orderDetails.getTax()) / 100);
					} else {
						taxPrice = (((orderDetails.getSellingPrice() * orderDetails
								.getQuantity()) * orderDetails.getTax()) / 100);
					}

					getUserOrderItemsResponse.setTaxPrice(taxPrice);

					// Order Price
					totalAmount = totalAmount + orderDetails.getTotalPrice();

					// DealerId
					getUserOrderItemsResponse.setDealerId(orderDetails
							.getDealerId());

					// Item status
					getUserOrderItemsResponse.setItemStatus(orderDetails
							.getItemStatus());
					itemStatusIds.add(getUserOrderItemsResponse.getItemStatus()
							.getItemStatusId());

					for (PaymentDetails paymentDetails : paymentDetailses) {
						if (paymentDetails != null) {
							if (paymentDetails.getPaymentType()
									.getPaymentType()
									.equalsIgnoreCase("Promotion")) {
								String metadata = paymentDetails
										.getPaymentMetadata();
								String[] metadataAry = metadata.split(":");

								if (orderDetails.getItemId().equalsIgnoreCase(
										metadataAry[1])) {
									getUserOrderItemsResponse
											.setPromotionName(metadataAry[0]);
									getUserOrderItemsResponse
											.setPromoCode(metadataAry[3]);
									getUserOrderItemsResponse
											.setDiscountedPrice(paymentDetails
													.getAmount());
									getUserOrderItemsResponse
											.setDiscount(Double
													.valueOf(metadataAry[2]));
								}
							}
						}
					}
					items.add(getUserOrderItemsResponse);

					// Order Items
					int itemsQuantity = orderDetails.getQuantity();
					totalItems = totalItems + itemsQuantity;

					// Category
					for (SubCategory subcategory : subCategoryList) {
						subCategoryListmain.add(subcategory);
						categoryList.add(subcategory.getCategory());
					}
					// Calculating EstimatedDeliveryDate
					if (estimatedDeliveryDate <= orderDetails
							.getMaxDeliveryTimeInDays()) {
						estimatedDeliveryDate = orderDetails
								.getMaxDeliveryTimeInDays();
					} else if (estimatedDeliveryDate > orderDetails
							.getMaxDeliveryTimeInDays()) {
						continue;
					}

					List<OrderAudit> orderAuditList1 = orderAuditRepository
							.findByOrderIdAndItemId(order.getOrderId(), Integer
									.toString(orderDetails.getOrderDetailsId()));

					if (orderAuditList1.size() != 0 && orderAuditList1 != null) {

						for (OrderAudit orderAudit : orderAuditList1) {
							if (orderDetails.getItemStatus().getDescription()
									.equalsIgnoreCase("Dispatched")
									&& orderAudit.getOrderAuditType()
											.getDescription()
											.equalsIgnoreCase("Dispatched")) {
								getUserOrderItemsResponse.setNote(orderAudit
										.getNote());
								break;
							}
						}

					}
				}

				superAdminOrderViewDetails.setItems(items);
				// EstimatedDeliveryDate
				superAdminOrderViewDetails
						.setEstimatedDeliveryDate(estimatedDeliveryDate);

				int[] statusIdArray = new int[itemStatusIds.size()];
				int j = 0;
				for (int id : itemStatusIds) {
					statusIdArray[j] = id;
					j++;
				}
				// Calculating order status based on item status
				int minVal = 10;
				OrderStatus orderStatus = null;
				if (order.getItems().size() == 1) {
					for (int i = 0; i < statusIdArray.length; i++) {
						if (minVal <= statusIdArray[i]) {
							continue;
						} else {
							minVal = statusIdArray[i];
						}
					}
					// Order status
					orderStatus = userOrderStatusRepository
							.findByOrderStatusId(minVal);
				} else {

					for (int i = 0; i < statusIdArray.length; i++) {
						if (statusIdArray[i] != 3) {
							if (minVal <= statusIdArray[i]) {
								continue;
							} else {
								minVal = statusIdArray[i];
							}
						}
					}
					// Order status
					if (minVal == 10) {
						orderStatus = userOrderStatusRepository
								.findByOrderStatusId(3);
					} else {
						orderStatus = userOrderStatusRepository
								.findByOrderStatusId(minVal);
					}
				}
				// Category
				superAdminOrderViewDetails.setCategory(categoryList);
				// SubCategory
				superAdminOrderViewDetails.setSubCategory(subCategoryListmain);
				// Order price
				superAdminOrderViewDetails.setOrderPrice(totalAmount);
				// Total items
				superAdminOrderViewDetails.setTotalItems(totalItems);
				// Ordered date
				superAdminOrderViewDetails.setOrderedDate(order
						.getOrderedDate());
				// OrderStatus
				order.setOrderStatus(orderStatus);
				userOrderRepository.save(order);

				// Order status description
				if (orderStatus.getDescription() != null) {
					superAdminOrderViewDetails.setOrderStatus(orderStatus
							.getDescription());
				}
				// Customer name
				superAdminOrderViewDetails.setCustomerName(order.getUserInfo()
						.getFirstname());
				// UserName(email)
				if (order.getUserInfo().getUsername() != null) {
					superAdminOrderViewDetails.setEmailId(order.getUserInfo()
							.getUsername());
				}

			} else {
				logger.error(
						"User is not exist in our system for userId : {} or order is null for orderId : {}",
						userId, orderId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId + " or order is null for orderId : "
								+ orderId);
			}
		} else {
			logger.error(
					"User Id and Ordre Id should not be null. userId : {} orderId : {}",
					userId, orderId);
			throw new Exception(
					"User Id and Ordre Id should not be null. userId : "
							+ userId + " orderId : " + orderId);
		}
		logger.info(
				"Fetching SuperAdminOrderViewDetails based on orderId : {} and userId : {}! Success",
				orderId, userId);
		return superAdminOrderViewDetails;

	}

	/**
	 * method for dealer manage orders.
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@Override
	public SuperAdminOrderViewDetails getDealerOrderViewDetails(String userId,
			String orderId) throws Exception {
		logger.info(
				"Fetching DealerOrderViewDetails based on orderId : {} and dealerId : {}",
				orderId, userId);
		SuperAdminOrderViewDetails superAdminOrderViewDetails = null;
		Order order = null;
		Set<PaymentDetails> paymentDetailses = null;
		// Checking orderId and userId is empty or not
		if (StringUtils.isNotBlank(orderId) && StringUtils.isNotBlank(userId)) {
			order = userOrderRepository.findByMbgOrderId(orderId);
			User user = userDAO.findById(userId);
			// Checkin order and user is null or not
			if (order != null && user != null) {
				double orderAmount = 0.0;
				int totalItems = 0;
				superAdminOrderViewDetails = new SuperAdminOrderViewDetails();

				InvoiceDetails invoice = null;
				String url = null;

				// CustomerId
				superAdminOrderViewDetails.setUserId(order.getUserInfo()
						.getMongoUserId());
				// OrderId
				superAdminOrderViewDetails.setOrderId(order.getMbgOrderId());

				// Customer email
				if (order.getUserInfo().getUsername() != null) {
					superAdminOrderViewDetails.setEmailId(order.getUserInfo()
							.getUsername());
				}

				// Customer phonenumber
				if (order.getUserInfo().getPhonenumber() != null) {
					superAdminOrderViewDetails.setPhoneNumber(order
							.getUserInfo().getPhonenumber().toString());
				}
				// order level shipping charge
				superAdminOrderViewDetails.setShippingCharge(order
						.getShippingCharge());
				// ExtraBenefits
				superAdminOrderViewDetails.setExtraBenefits(order
						.getExtraBenefits());
				// invoice
				invoice = invoiceRepository.findByOrderAndUserId(order, userId);
				if (invoice != null && invoice.getFileUrl() != null) {
					url = invoice.getFileUrl();
					if (url != null) {
						superAdminOrderViewDetails.setDealerInvocie(url);
					} else {
						logger.info(
								"Dealer order invoice url is null for OrderId : {}",
								order.getOrderId());
					}
				} else {
					logger.info(
							"Dealer order invoice is null for OrderId : {}",
							order.getOrderId());
				}

				// ShippingType
				superAdminOrderViewDetails.setShippingType(order
						.getShippingType().getShippingType());

				Set<SubCategory> subCategoryListmain = new HashSet<>();
				List<String> categoryList = new ArrayList<>();

				Set<Integer> itemStatusIds = new HashSet<Integer>();

				List<OrderAuditResponse> orderAuditResposeList = new ArrayList<>();

				List<OrderAudit> orderAuditList = orderAuditRepository
						.findByOrderId(order.getOrderId());

				// Order audit
				for (OrderAudit orderAudit : orderAuditList) {
					OrderAuditResponse orderAuditRespose = new OrderAuditResponse();
					orderAuditRespose.setCreatedTime(orderAudit
							.getCreatedTime());
					orderAuditRespose.setLastModifiedTime(orderAudit
							.getLastModifiedTime());
					orderAuditRespose.setItemId(orderAudit.getItemId());
					orderAuditRespose.setNote(orderAudit.getNote());
					orderAuditRespose.setOrderId(orderAudit.getOrderId());
					orderAuditRespose.setOrderAuditType(orderAudit
							.getOrderAuditType());

					orderAuditResposeList.add(orderAuditRespose);
				}
				superAdminOrderViewDetails
						.setOrderAuditRespose(orderAuditResposeList);

				int estimatedDeliveryDate = 0;
				List<GetUserOrderItemsResponse> items = new ArrayList<>();

				List<OrderDetails> orderDetailsList = userOrderDetailRepository
						.findByOrderAndDealerId(order, userId);

				for (OrderDetails orderDetails : orderDetailsList) {
					GetUserOrderItemsResponse getUserOrderItemsResponse = new GetUserOrderItemsResponse();
					// Item serial number
					getUserOrderItemsResponse.setItemId(orderDetails
							.getOrderDetailsId());
					// Item(Product) Id
					getUserOrderItemsResponse.setPid(orderDetails.getItemId());
					// Item name
					if (orderDetails.getItemName() != null) {
						getUserOrderItemsResponse.setItemName(orderDetails
								.getItemName());
					}
					// SellByDealer or MBG
					Aggregation agg = Aggregation.newAggregation(Aggregation
							.match(Criteria.where("user").is(
									orderDetails.getDealerId())), Aggregation
							.project("storename").and("id"));

					// Converting the aggregation result into a List
					AggregationResults<StoreProductCount> groupResults = mongoTemplate
							.aggregate(agg, Store.class,
									StoreProductCount.class);
					List<StoreProductCount> result = groupResults
							.getMappedResults();
					boolean sellByDealer = false;
					if (result.size() != 0) {
						StoreProductPricing storeProductPricing = storeProductPricingDAO
								.findByProductIdAndStoreId(
										orderDetails.getItemId(),
										result.get(0).id);
						if (storeProductPricing != null) {
							sellByDealer = storeProductPricing
									.isStoreDelivery();
						}
					}

					if (sellByDealer) {
						getUserOrderItemsResponse.setSellName("Seller");
					} else {
						getUserOrderItemsResponse.setSellName("Buildonn");
					}

					// Quantity
					getUserOrderItemsResponse.setQuantity(orderDetails
							.getQuantity());

					// Mrp
					getUserOrderItemsResponse.setMrp(orderDetails.getMrp());

					// Tax
					getUserOrderItemsResponse.setTax(orderDetails.getTax());

					// Selling Price
					getUserOrderItemsResponse.setSellingPrice(orderDetails
							.getSellingPrice());

					// Shipping Charge
					getUserOrderItemsResponse.setShippingCharge(orderDetails
							.getShippingCharge());
					// Dealer Id
					getUserOrderItemsResponse.setDealerId(orderDetails
							.getDealerId());
					// Item Status
					getUserOrderItemsResponse.setItemStatus(orderDetails
							.getItemStatus());

					// Created By
					getUserOrderItemsResponse.setCreateBy(orderDetails
							.getCreatedby());

					// Modified By
					getUserOrderItemsResponse.setLastModifiedBy(orderDetails
							.getLastmodifiedby());

					// Created Date
					getUserOrderItemsResponse.setCreatedDate(orderDetails
							.getCreatedDate());

					// Modified Date
					getUserOrderItemsResponse.setLastmodifiedDate(orderDetails
							.getLastModifiedDate());

					// Total Price of Item
					getUserOrderItemsResponse.setTotalPrice(orderDetails
							.getTotalPrice());

					// Tax price
					double taxPrice;
					if (order.getShippingCharge() == 0) {
						taxPrice = ((((orderDetails.getSellingPrice() * orderDetails
								.getQuantity()) + orderDetails
								.getShippingCharge()) * orderDetails.getTax()) / 100);
					} else {
						taxPrice = (((orderDetails.getSellingPrice() * orderDetails
								.getQuantity()) * orderDetails.getTax()) / 100);
					}
					getUserOrderItemsResponse.setTaxPrice(taxPrice);

					// Order Price
					orderAmount = orderAmount + orderDetails.getTotalPrice();

					itemStatusIds.add(orderDetails.getItemStatus()
							.getItemStatusId());
					Set<SubCategory> subCategoryList = new HashSet<>();

					// Order Items
					int itemsQuantity = orderDetails.getQuantity();
					totalItems = totalItems + itemsQuantity;

					// subcategory
					String itemId = orderDetails.getItemId();
					if (itemId != null) {
						Product product = productDAO.findOne(itemId);
						if (product != null) {
							subCategoryList = product.getSubcategories();
							getUserOrderItemsResponse.setProductAsset(product
									.getAssets());
						}
					}

					getUserOrderItemsResponse
							.setEstimatedDeliveryDate(orderDetails
									.getMaxDeliveryTimeInDays());

					// paymentDetails
					paymentDetailses = userPaymentDetailsRepository
							.findByOrder(order);

					for (PaymentDetails paymentDetails : paymentDetailses) {
						if (paymentDetails != null) {
							if (paymentDetails.getPaymentType()
									.getPaymentType()
									.equalsIgnoreCase("Promotion")) {
								String metadata = paymentDetails
										.getPaymentMetadata();
								String[] metadataAry = metadata.split(":");

								if (orderDetails.getItemId().equalsIgnoreCase(
										metadataAry[1])) {
									getUserOrderItemsResponse
											.setPromotionName(metadataAry[0]);
									getUserOrderItemsResponse
											.setPromoCode(metadataAry[3]);
									getUserOrderItemsResponse
											.setDiscountedPrice(paymentDetails
													.getAmount());
									getUserOrderItemsResponse
											.setDiscount(Double
													.valueOf(metadataAry[2]));
								}
							}
						}
					}

					items.add(getUserOrderItemsResponse);

					// Category
					if (subCategoryList.size() != 0) {
						for (SubCategory subcategory : subCategoryList) {
							subCategoryListmain.add(subcategory);
							categoryList.add(subcategory.getCategory());
						}
					}
					// Calculating Estimate delivery date
					if (estimatedDeliveryDate <= orderDetails
							.getMaxDeliveryTimeInDays()) {
						estimatedDeliveryDate = orderDetails
								.getMaxDeliveryTimeInDays();
					} else if (estimatedDeliveryDate > orderDetails
							.getMaxDeliveryTimeInDays()) {
						continue;
					}

					List<OrderAudit> orderAuditList1 = orderAuditRepository
							.findByOrderIdAndItemId(order.getOrderId(), Integer
									.toString(orderDetails.getOrderDetailsId()));

					if (orderAuditList1.size() != 0 && orderAuditList1 != null) {

						for (OrderAudit orderAudit : orderAuditList1) {
							if (orderDetails.getItemStatus().getDescription()
									.equalsIgnoreCase("Dispatched")
									&& orderAudit.getOrderAuditType()
											.getDescription()
											.equalsIgnoreCase("Dispatched")) {
								getUserOrderItemsResponse.setNote(orderAudit
										.getNote());
								break;
							}
						}

					}
				}
				superAdminOrderViewDetails.setItems(items);

				// EstimatedDeliveryDate
				superAdminOrderViewDetails
						.setEstimatedDeliveryDate(estimatedDeliveryDate);
				// Category
				superAdminOrderViewDetails.setCategory(categoryList);
				// Subcategory
				superAdminOrderViewDetails.setSubCategory(subCategoryListmain);
				// Total order amount
				superAdminOrderViewDetails.setOrderPrice(orderAmount);
				// Total items
				superAdminOrderViewDetails.setTotalItems(totalItems);
				// Ordered date
				superAdminOrderViewDetails.setOrderedDate(order
						.getOrderedDate());

				int[] statusIdArray = new int[itemStatusIds.size()];
				int j = 0;
				for (int id : itemStatusIds) {
					statusIdArray[j] = id;
					j++;
				}
				// Calculating order status based on item status
				int minVal = 10;
				for (int i = 0; i < statusIdArray.length; i++) {
					if (minVal <= statusIdArray[i]) {
						continue;
					} else {
						minVal = statusIdArray[i];
					}
				}
				OrderStatus orderStatus = userOrderStatusRepository
						.findByOrderStatusId(minVal);
				// Ordered date
				superAdminOrderViewDetails.setOrderPlacedOn(order
						.getOrderedDate());

				// paymentDetails
				paymentDetailses = userPaymentDetailsRepository
						.findByOrder(order);
				Set<PaymentDetailsResponse> paymentDetailsResponseSet = new HashSet<>();
				for (PaymentDetails paymentDetails : paymentDetailses) {
					if (paymentDetails != null) {
						PaymentDetailsResponse paymentDetailsResponse = new PaymentDetailsResponse();
						// payment method name
						if (paymentDetails.getPaymentType() != null) {
							paymentDetailsResponse
									.setPaymentMethod(paymentDetails
											.getPaymentType().getPaymentType());
						}
						// payment number
						paymentDetailsResponse.setPaymentNumber(paymentDetails
								.getPaymentNumber());
						// payment status
						paymentDetailsResponse.setPaymentStatus(paymentDetails
								.getPaymentStatus());

						// payment metadata
						if (paymentDetails.getPaymentMetadata() != null) {
							paymentDetailsResponse
									.setPaymentMetadata(paymentDetails
											.getPaymentMetadata());
						}

						// payment amount
						paymentDetailsResponse.setAmount(paymentDetails
								.getAmount());

						paymentDetailsResponseSet.add(paymentDetailsResponse);
					}
				}
				superAdminOrderViewDetails
						.setPaymentDetails(paymentDetailsResponseSet);

				// Shipping Address
				superAdminOrderViewDetails.setShippingAddress(order
						.getShippingAddress());
				// OrderStatus
				order.setOrderStatus(orderStatus);
				userOrderRepository.save(order);
				superAdminOrderViewDetails.setOrderStatus(orderStatus
						.getDescription());
			} else {
				logger.error(
						"Order is null for orderId : {} or User is null for userId : {}",
						userId, orderId);
				throw new Exception("Order is null for orderId : " + orderId
						+ " or User is null for userId : " + userId);
			}
		} else {
			logger.error(
					"UserId and OrderId should not be empty. userId : {} orderId : {}",
					userId, orderId);
			throw new Exception(
					"UserId and OrderId should not be empty. userId : "
							+ userId + " " + "orderId : " + orderId);
		}
		logger.info(
				"Fetching DealerOrderViewDetails based on orderId : {} and dealerId : {}! Success",
				orderId, userId);
		return superAdminOrderViewDetails;
	}

	/**
	 * method for display user orders in sorting order based on order order
	 * status.
	 * 
	 * @param uid
	 * @param type
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	@Override
	public TrackOrderSummary getUserOrdersSorting(String uid, String status,
			int page, int size, Sort sort) throws Exception {
		logger.info(
				"Fetching orders in sorted order based on order status. OrderStatus : {}",
				status);
		List<GetUserOrderResponse> orderDisplayResponseList = new ArrayList<>();
		List<com.sarvah.mbg.domain.ordermgmt.Order> orderList = new ArrayList<com.sarvah.mbg.domain.ordermgmt.Order>();
		TrackOrderSummary trackOrderSummary = new TrackOrderSummary();
		Set<PaymentDetails> paymentDetailses = null;
		Page<Order> pageable = null;
		UserInfo userInfo = new UserInfo();
		if (StringUtils.isNotBlank(uid) && StringUtils.isNotBlank(status)) {
			User user = userDAO.findOne(uid);
			userInfo = userRepository.findByMongoUserId(uid);
			if (user != null && userInfo != null) {
				OrderStatus orderStatus = userOrderStatusRepository
						.findByDescription(status);
				if (orderStatus != null) {

					logger.info(
							"Fetching orders in sorted order based on order status : {}",
							orderStatus);

					pageable = userOrderRepository
							.findByUserInfoAndOrderStatus(userInfo,
									orderStatus, new PageRequest(page, size,
											sort));
					orderList.addAll(pageable.getContent());
					Collections.sort(orderList, new UserOrdersComparator());

					for (Order order : orderList) {
						GetUserOrderResponse orderDisplayResponse = new GetUserOrderResponse();
						InvoiceDetails invoice = null;
						String url = null;

						// Order Id
						orderDisplayResponse.setOrderId(order.getMbgOrderId());
						// ShippingType
						orderDisplayResponse.setShippingType(order
								.getShippingType().getShippingType());
						// FirstName
						orderDisplayResponse.setFirstName(order.getUserInfo()
								.getFirstname());
						// LastName
						orderDisplayResponse.setLastName(order.getUserInfo()
								.getLastname());
						// UserName
						orderDisplayResponse.setEmailAddress(order
								.getUserInfo().getUsername());

						// invoice
						invoice = invoiceRepository.findByOrderAndUserId(order,
								uid);
						if (invoice != null) {
							url = invoice.getFileUrl();
							if (url != null) {
								orderDisplayResponse.setInvoice(url);
							} else {
								logger.info(
										"Invoice url is null for OrderId: {}",
										order.getOrderId());

							}
						} else {
							logger.info("Invoice is null for OrderId: {}",
									order.getOrderId());
						}

						// paymentDetails
						paymentDetailses = userPaymentDetailsRepository
								.findByOrder(order);
						Set<PaymentDetailsResponse> paymentDetailsResponseSet = new HashSet<>();
						for (PaymentDetails paymentDetails : paymentDetailses) {
							if (paymentDetails != null) {
								PaymentDetailsResponse paymentDetailsResponse = new PaymentDetailsResponse();
								// payment method name
								if (paymentDetails.getPaymentType() != null) {
									paymentDetailsResponse
											.setPaymentMethod(paymentDetails
													.getPaymentType()
													.getPaymentType());
								}
								// payment number
								paymentDetailsResponse
										.setPaymentNumber(paymentDetails
												.getPaymentNumber());
								// payment status
								paymentDetailsResponse
										.setPaymentStatus(paymentDetails
												.getPaymentStatus());
								// payment metadata
								if (paymentDetails.getPaymentMetadata() != null) {
									paymentDetailsResponse
											.setPaymentMetadata(paymentDetails
													.getPaymentMetadata());
								}
								// payment amount
								paymentDetailsResponse.setAmount(paymentDetails
										.getAmount());

								paymentDetailsResponseSet
										.add(paymentDetailsResponse);
							}
						}
						orderDisplayResponse
								.setPaymentDetails(paymentDetailsResponseSet);

						// Items
						Set<GetUserOrderItemsResponse> itemsDisplayResponseList = new HashSet<>();
						List<OrderDetails> items = userOrderDetailRepository
								.findByOrder(order);
						Set<Integer> itemStatusIds = new HashSet<Integer>();
						int estimatedDeliveryDate = 0;
						for (OrderDetails orderDetails : items) {
							GetUserOrderItemsResponse itemsDisplayResponse = new GetUserOrderItemsResponse();
							// Item Serial Number
							itemsDisplayResponse.setItemId(orderDetails
									.getOrderDetailsId());
							// Item(Product) Id
							itemsDisplayResponse.setPid(orderDetails
									.getItemId());
							// Item Name
							itemsDisplayResponse.setItemName(orderDetails
									.getItemName());
							// Quantity
							itemsDisplayResponse.setQuantity(orderDetails
									.getQuantity());
							Product product = productDAO.findOne(orderDetails
									.getItemId());
							// Product Assets
							if (product != null) {
								itemsDisplayResponse.setProductAsset(product
										.getAssets());
							}
							// Mrp
							itemsDisplayResponse.setMrp(orderDetails.getMrp());
							// Tax
							itemsDisplayResponse.setTax(orderDetails.getTax());
							// Selling Price
							itemsDisplayResponse.setSellingPrice(orderDetails
									.getSellingPrice());
							// Shipping Charge
							itemsDisplayResponse.setShippingCharge(orderDetails
									.getShippingCharge());
							// Total Price
							itemsDisplayResponse.setTotalPrice(orderDetails
									.getTotalPrice());
							// Dealer Id
							itemsDisplayResponse.setDealerId(orderDetails
									.getDealerId());
							// Item Status
							itemsDisplayResponse.setItemStatus(orderDetails
									.getItemStatus());
							// Created By
							itemsDisplayResponse.setCreateBy(orderDetails
									.getCreatedby());
							// Lastmodified By
							itemsDisplayResponse.setLastModifiedBy(orderDetails
									.getLastmodifiedby());
							// Created Date
							itemsDisplayResponse.setCreatedDate(orderDetails
									.getCreatedDate());
							// Last Modified Date
							itemsDisplayResponse
									.setLastmodifiedDate(orderDetails
											.getLastModifiedDate());
							itemStatusIds.add(orderDetails.getItemStatus()
									.getItemStatusId());

							// Calculating estimateDeliveryDate
							if (estimatedDeliveryDate <= orderDetails
									.getMaxDeliveryTimeInDays()) {
								estimatedDeliveryDate = orderDetails
										.getMaxDeliveryTimeInDays();
							} else if (estimatedDeliveryDate > orderDetails
									.getMaxDeliveryTimeInDays()) {
								continue;
							}

							itemsDisplayResponseList.add(itemsDisplayResponse);
						}
						orderDisplayResponse.setItems(itemsDisplayResponseList);

						// EstimatedDeliveryDate
						orderDisplayResponse
								.setEstimateDeliveryTime(estimatedDeliveryDate);

						int[] statusIdArray = new int[itemStatusIds.size()];
						int j = 0;
						for (int id : itemStatusIds) {
							statusIdArray[j] = id;
							j++;
						}
						// Calculating Order status based on item status
						int minVal = 10;
						for (int i = 0; i < statusIdArray.length; i++) {
							if (minVal <= statusIdArray[i]) {
								continue;
							} else {
								minVal = statusIdArray[i];
							}
						}
						// Order status
						orderStatus = userOrderStatusRepository
								.findByOrderStatusId(minVal);
						orderDisplayResponse.setOrderStatus(orderStatus
								.getDescription());
						order.setOrderStatus(orderStatus);
						userOrderRepository.save(order);
						// Shipping Address
						orderDisplayResponse.setShippingAddress(order
								.getShippingAddress());
						// Billing Address
						orderDisplayResponse.setBillingAddress(order
								.getBillingAddress());
						// Created By
						orderDisplayResponse.setCreateBy(order.getCreateBy());
						// Lastmodified By
						orderDisplayResponse.setLastModifiedBy(order
								.getLastModifiedBy());
						// Ordered date
						orderDisplayResponse.setOrderedDate(order
								.getOrderedDate());
						// Last modified date
						orderDisplayResponse.setLastmodifiedDate(order
								.getLastmodifiedDate());
						// order level sorting
						orderDisplayResponse.setShippingCharge(order
								.getShippingCharge());
						orderDisplayResponseList.add(orderDisplayResponse);
					}
				}
			} else {
				logger.error("User is not exist in our system for userId : {}",
						uid);
				throw new Exception(
						"User is not exist in our system for userId : " + uid);
			}
		} else {
			logger.error(
					"UserId and OrderStatus sholudn't be blank. userId : {} orderStatus : {}",
					uid, status);
			throw new Exception(
					"UserId and OrderStatus sholudn't be blank. userId : "
							+ uid + " " + "orderStatus : " + status);
		}
		trackOrderSummary.setOrderList(orderDisplayResponseList);
		// Setting pagination fields
		trackOrderSummary.setTotalPages(pageable.getTotalPages());
		trackOrderSummary.setTotalElements(pageable.getTotalElements());
		trackOrderSummary.setNumber(pageable.getNumber());
		trackOrderSummary.setSize(pageable.getSize());
		logger.info(
				"Fetching orders in sorted order based on order status. OrderStatus : {}! Success",
				status);
		return trackOrderSummary;
	}

	/**
	 * method for display user order status count.
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@Override
	public TrackOrderStatusCount getAllItemsStatus(String uid) throws Exception {
		logger.info(
				"Fetching items count based on item status for userId : {}",
				uid);
		int placed = 0;
		int conformed = 0;
		int packed = 0;
		int dispatched = 0;
		int delivered = 0;
		int cancelled = 0;
		int unConformed = 0;
		int returned = 0;
		int count = 0;
		TrackOrderStatusCount trackOrderStatusCount = new TrackOrderStatusCount();
		List<com.sarvah.mbg.domain.ordermgmt.Order> orderList = new ArrayList<com.sarvah.mbg.domain.ordermgmt.Order>();
		UserInfo userInfo = new UserInfo();

		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findOne(uid);
			userInfo = userRepository.findByMongoUserId(uid);
			if (user != null && userInfo != null) {
				orderList = userOrderRepository.findByUserInfo(userInfo);
				for (Order order : orderList) {
					// Items
					List<OrderDetails> items = userOrderDetailRepository
							.findByOrder(order);
					for (OrderDetails orderDetails : items) {
						String status = orderDetails.getItemStatus()
								.getDescription();
						if (status.equalsIgnoreCase("Placed")) {
							placed++;
						} else if (status.equalsIgnoreCase("Confirmed")) {
							conformed++;
						} else if (status.equalsIgnoreCase("Packed")) {
							packed++;
						} else if (status.equalsIgnoreCase("Dispatched")) {
							dispatched++;
						} else if (status.equalsIgnoreCase("Delivered")) {
							delivered++;
						} else if (status.equalsIgnoreCase("Cancelled")) {
							cancelled++;
						} else if (status.equalsIgnoreCase("Unconfirmed")) {
							unConformed++;
						} else if (status.equalsIgnoreCase("Returned")) {
							returned++;
						}
					}
				}
				count = placed + conformed + packed + dispatched + delivered
						+ cancelled + unConformed + returned;
				trackOrderStatusCount.setPlaced(placed);
				trackOrderStatusCount.setConformed(conformed);
				trackOrderStatusCount.setPacked(packed);
				trackOrderStatusCount.setDispatched(dispatched);
				trackOrderStatusCount.setDelivered(delivered);
				trackOrderStatusCount.setCancelled(cancelled);
				trackOrderStatusCount.setUnConformed(unConformed);
				trackOrderStatusCount.setReturned(returned);
				trackOrderStatusCount.setCount(count);

			} else {
				logger.error("User is not exist in our system for userId : {}",
						uid);
				throw new Exception(
						"User is not exist in our system for userId : " + uid);
			}

		} else {
			logger.error("User Id sholudn't be blank. userId : {}", uid);
			throw new Exception("User Id sholudn't be blank. userId : " + uid);
		}
		logger.info(
				"Fetching items count based on item status for userId : {}! Success",
				uid);
		return trackOrderStatusCount;
	}

	@Override
	public TrackOrderStatusCount getUserOrderStatusCount(String userId,
			String orderId) throws Exception {
		logger.info(
				"Fetching items count based on item status and orderId : {} userId : {}",
				orderId, userId);
		TrackOrderStatusCount trackOrderStatusCount = new TrackOrderStatusCount();
		int placed = 0;
		int conformed = 0;
		int packed = 0;
		int dispatched = 0;
		int delivered = 0;

		UserInfo userInfo = null;
		// Checking userId and orderId is empty or not
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(orderId)) {
			User user = userDAO.findOne(userId);
			userInfo = userRepository.findByMongoUserId(userId);
			com.sarvah.mbg.domain.ordermgmt.Order order = userOrderRepository
					.findByMbgOrderIdAndUserInfo(orderId, userInfo);
			// Checking user and order is null or not
			if (user != null && userInfo != null && order != null) {
				List<OrderDetails> orderDetailsList = userOrderDetailRepository
						.findByOrder(order);
				for (OrderDetails orderDetails : orderDetailsList) {
					String status = orderDetails.getItemStatus()
							.getDescription();
					if (status.equalsIgnoreCase("Placed")) {
						placed++;
					} else if (status.equalsIgnoreCase("Confirmed")) {
						conformed++;
					} else if (status.equalsIgnoreCase("Packed")) {
						packed++;
					} else if (status.equalsIgnoreCase("Dispatched")) {
						dispatched++;
					} else if (status.equalsIgnoreCase("Delivered")) {
						delivered++;
					}
				}
				trackOrderStatusCount.setPlaced(placed);
				trackOrderStatusCount.setConformed(conformed);
				trackOrderStatusCount.setPacked(packed);
				trackOrderStatusCount.setDispatched(dispatched);
				trackOrderStatusCount.setDelivered(delivered);

			} else {
				logger.error(
						"User is not exist in our system for userId : {} or order is null for orderId : {}",
						userId, orderId);
				throw new Exception(
						"User is not exist in our system for userId : "
								+ userId + " or order is null for orderId : "
								+ orderId);
			}
		} else {
			logger.error(
					"User Id and order Id should not null. userId : {} orderId : {}",
					userId, orderId);
			throw new Exception("User id null");
		}
		logger.info(
				"Fetching items count based on item status and orderId : {} userId : {}! Success",
				orderId, userId);
		return trackOrderStatusCount;
	}

	private Boolean updateOrderAudit(Order order, String itemId, String status,
			String note) throws Exception {
		logger.info("Storing order info in order audit table");
		OrderAudit orderAudit = new OrderAudit();
		OrderAuditType orderAuditType = null;
		if (StringUtils.isBlank(itemId)) {
			if (order != null) {
				orderAudit.setOrderId(order.getOrderId());
				orderAuditType = orderAuditTypeRepository.findByValue(status);
				if (orderAuditType != null) {
					orderAudit.setOrderAuditType(orderAuditType);
					orderAudit.setCreatedTime(new Date());
					orderAudit.setLastModifiedTime(new Date());
					orderAudit.setCreatedBy("Buildonn");
					orderAudit.setModifiedBy("Buildonn");
					if (StringUtils.isNotBlank(note)) {
						orderAudit.setNote(note);
					} else {
						orderAudit.setNote("status of Order "
								+ order.getOrderId() + " changed to " + status
								+ " state");
					}
					orderAuditRepository.save(orderAudit);
					logger.info("Storing order info in order audit table! Success");
					return true;
				}
			}
		}
		if (StringUtils.isNotBlank(itemId)) {
			logger.info("Storing item updated info in order audit table");
			if (order != null) {
				List<OrderDetails> items = order.getItems();
				for (OrderDetails item : items) {
					String itemId1 = String.valueOf(item.getOrderDetailsId());
					if (StringUtils.isNotBlank(itemId)
							&& itemId.equalsIgnoreCase(itemId1)) {
						orderAudit.setOrderId(order.getOrderId());
						orderAudit.setItemId(itemId);
						orderAuditType = orderAuditTypeRepository
								.findByValue(status);
						if (orderAuditType != null) {
							orderAudit.setOrderAuditType(orderAuditType);
							orderAudit.setCreatedTime(new Date());
							orderAudit.setLastModifiedTime(new Date());
							orderAudit.setCreatedBy("Buildonn");
							orderAudit.setModifiedBy("Buildonn");
							if (StringUtils.isNotBlank(note)) {
								orderAudit.setNote(note);
							} else {
								orderAudit
										.setNote("Status of Item with itemId "
												+ itemId + " changed to "
												+ status + " state");
							}
							orderAuditRepository.save(orderAudit);
							logger.info("Storing item updated info in order audit table! Success");
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	@Transactional
	public List<CustomerOrdersSummaryResponse> getCustomerOrdersSummary(
			String uid) {
		List<CustomerOrdersSummaryResponse> ordersSummary = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("####0.00");
		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findById(uid);
			UserInfo userInfo = userRepository.findByMongoUserId(uid);
			if (user != null && userInfo != null) {
				List<Order> orders = userOrderRepository
						.findByUserInfo(userInfo);
				for (Order order : orders) {
					if (order != null) {
						double totalOrderAmt = 0.0;
						double paymentDoneAmt = 0.0;
						double outStandingAmt = 0.0;
						Date paymentDoneDate = null;
						CustomerOrdersSummaryResponse orderSummary = new CustomerOrdersSummaryResponse();
						orderSummary.setOrderId(order.getOrderId());
						orderSummary.setBuildOnnOrderId(order.getMbgOrderId());

						// Getting total order payment amount
						Set<PaymentDetails> payments = order
								.getPaymentDetails();
						List<PaymentDetailsResponse> paymentInfoList = new ArrayList<>();
						String note = null;
						int count = 0;
						if (payments != null && payments.size() != 0) {
							for (PaymentDetails payment : payments) {
								if (payment.getPaymentStatus() == 1) {
									// PaymentDone Amt
									paymentDoneAmt = paymentDoneAmt
											+ payment.getAmount();
									// Payment Date
									paymentDoneDate = payment
											.getLastModifiedDate();
									// Payment Info
									PaymentDetailsResponse paymentInfo = new PaymentDetailsResponse();
									paymentInfo.setPaymentDetailsId(String
											.valueOf(payment.getPaymentId()));
									paymentInfo.setPaymentMethod(payment
											.getPaymentType().getPaymentType());
									paymentInfo.setPaymentMetadata(payment
											.getPaymentMetadata());
									paymentInfo.setAmount(payment.getAmount());
									paymentInfo.setPaymentStatus(payment
											.getPaymentStatus());
									paymentInfoList.add(paymentInfo);
								}
								if (count == 0) {
									if (StringUtils.isNotBlank(payment
											.getPaymentMetadata())
											&& !payment.getPaymentMetadata()
													.equalsIgnoreCase("NA")) {
										note = payment.getPaymentMetadata();
									}
								} else {
									if (StringUtils.isNotBlank(payment
											.getPaymentMetadata())
											&& !payment.getPaymentMetadata()
													.equalsIgnoreCase("NA")) {
										note = note + "#"
												+ payment.getPaymentMetadata();
									}
								}
								count++;
							}
						}
						orderSummary.setNote(note);
						int itemCount = 0;
						// Getting total order amount
						List<OrderDetails> items = order.getItems();
						for (OrderDetails item : items) {
							if (!item.getItemStatus().getDescription()
									.equalsIgnoreCase("UnConfirmed")
									&& !item.getItemStatus().getDescription()
											.equalsIgnoreCase("Returned")
									&& !item.getItemStatus().getDescription()
											.equalsIgnoreCase("Cancelled")) {
								if (item.getTax() != 0) {
									double itemTaxAmt = (item.getTax() * item
											.getTotalPrice()) / 100;
									totalOrderAmt = totalOrderAmt
											+ item.getTotalPrice() + itemTaxAmt;
								} else {
									totalOrderAmt = totalOrderAmt
											+ item.getTotalPrice();
								}
								itemCount++;
							}
						}
						// ExtraBenefits
						totalOrderAmt = totalOrderAmt
								+ order.getExtraBenefits();
						// adding shipping charge into order amount
						totalOrderAmt = totalOrderAmt
								+ order.getShippingCharge();

						// OutStandingAmt
						outStandingAmt = totalOrderAmt - paymentDoneAmt;

						orderSummary.setOrderAmt(Double.valueOf(df
								.format(totalOrderAmt)));
						orderSummary.setPaymentDoneAmt(Double.valueOf(df
								.format(paymentDoneAmt)));
						orderSummary.setOutstandingAmt(Double.valueOf(df
								.format(outStandingAmt)));
						orderSummary
								.setOrderCreatedDate(order.getOrderedDate());
						orderSummary.setPaymentDoneDate(paymentDoneDate);
						orderSummary.setPaymentInfo(paymentInfoList);
						// Payment done
						if (outStandingAmt <= 10) {
							orderSummary.setPaymentDone(true);
						} else {
							orderSummary.setPaymentDone(false);
						}
						if (order.getOrderStatus().getDescription()
								.equalsIgnoreCase("UnConfirmed")
								|| order.getOrderStatus().getDescription()
										.equalsIgnoreCase("Returned")
								|| order.getOrderStatus().getDescription()
										.equalsIgnoreCase("Cancelled")) {
							if (itemCount > 0) {
								ordersSummary.add(orderSummary);
							}
						} else {
							ordersSummary.add(orderSummary);
						}
					}
				}
			}
		}
		return ordersSummary;
	}

	@Override
	public boolean verifyInvoiceNumber(String invoiceNum) {
		boolean invoiceNumVerify = false;
		if (StringUtils.isNotBlank(invoiceNum)) {
			Order order = userOrderRepository.findByInvoiceNumber(invoiceNum);
			if (order != null) {
				invoiceNumVerify = true;
			}
		}
		return invoiceNumVerify;
	}

	@Override
	public OrderStatement createOrderStatement(
			List<OrderStatementCreateRequestParam> ordersStatementCreateRequestParam)
			throws Exception {
		OrderStatement orderStatement = null;
		for (OrderStatementCreateRequestParam orderStatementCreateRequestParam : ordersStatementCreateRequestParam) {
			if (orderStatementCreateRequestParam != null) {
				orderStatement = new OrderStatement();
				// Date
				if (StringUtils.isNotBlank(orderStatementCreateRequestParam
						.getDate())) {
					orderStatement.setDate(orderStatementCreateRequestParam
							.getDate());
				}
				// StatementId
				List<OrderStatement> orderStmts = orderStatementRepository
						.findAll();
				if (orderStmts.size() == 0) {
					orderStatement.setStatementId("BNNOS001");
				} else {
					OrderStatement orderStmt = orderStmts
							.get(orderStmts.size() - 1);
					String[] orderStmtAry = orderStmt.getStatementId().split(
							"OS");
					int id = Integer.parseInt(orderStmtAry[1]);
					id++;
					orderStatement.setStatementId("BNNOS"
							+ String.format("%03d", id));
				}
				// OrderAmt
				orderStatement.setOrderAmt(orderStatementCreateRequestParam
						.getOrderAmt());
				// Payment Done Amt
				orderStatement
						.setPaymentDoneAmt(orderStatementCreateRequestParam
								.getPaymentDoneAmt());
				// OutStanding Amt
				orderStatement
						.setOutstandingAmt(orderStatementCreateRequestParam
								.getOutstandingAmt());
				// User
				User user = userDAO.findById(orderStatementCreateRequestParam
						.getUserId());
				if (user != null) {
					orderStatement.setUser(user);
				}
				// CreatedDate
				orderStatement.setCreatedDate(new Date());
				// LastModified Date
				orderStatement.setLastModifiedDate(new Date());
				orderStatementRepository.save(orderStatement);
			}
		}
		return orderStatement;
	}

	@Override
	public List<OrderStatement> getOrderStatements(String userId) {
		List<OrderStatement> orderStatements = null;
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findById(userId);
			orderStatements = orderStatementRepository.findByUser(user);
			if (user != null) {
				orderStatementRepository.findByUser(user);
			}
		}
		return orderStatements;
	}

	@Override
	public OrderStatement updateOrderStatement(String orderStatementId,
			OrderStatementCreateRequestParam orderStatementCreateRequestParam) {
		OrderStatement orderStatement = null;
		if (StringUtils.isNotBlank(orderStatementId)) {
			orderStatement = orderStatementRepository.findOne(orderStatementId);
			if (orderStatement != null) {
				// Date
				if (StringUtils.isNotBlank(orderStatementCreateRequestParam
						.getDate())) {
					orderStatement.setDate(orderStatementCreateRequestParam
							.getDate());
				}
				// OrderId or PaymentId
				if (StringUtils.isNotBlank(orderStatementCreateRequestParam
						.getStatementId())) {
					orderStatement
							.setStatementId(orderStatementCreateRequestParam
									.getStatementId());
				}
				// Order Amt
				orderStatement.setOrderAmt(orderStatementCreateRequestParam
						.getOrderAmt());
				// Payment Done Amt
				orderStatement
						.setPaymentDoneAmt(orderStatementCreateRequestParam
								.getPaymentDoneAmt());
				// OutStanding Amt
				orderStatement
						.setOutstandingAmt(orderStatementCreateRequestParam
								.getOutstandingAmt());
				// LastModified Date
				orderStatement.setLastModifiedDate(new Date());
				orderStatementRepository.save(orderStatement);
			}
		}
		return orderStatement;
	}

	@Override
	public OrderStatement delateOrderStatement(String orderStatementId) {
		OrderStatement orderStatement = null;
		if (StringUtils.isNotBlank(orderStatementId)) {
			orderStatement = orderStatementRepository.findOne(orderStatementId);
			if (orderStatement != null) {
				orderStatementRepository.delete(orderStatement);
			}
		}
		return orderStatement;
	}
}
