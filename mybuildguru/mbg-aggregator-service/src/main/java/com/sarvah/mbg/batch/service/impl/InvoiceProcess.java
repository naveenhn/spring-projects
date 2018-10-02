/**
 * 
 */
package com.sarvah.mbg.batch.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sarvah.mbg.batch.dao.jpa.InvoiceRepository;
import com.sarvah.mbg.batch.dao.jpa.OrderDetailsRepository;
import com.sarvah.mbg.batch.dao.jpa.OrderRepository;
import com.sarvah.mbg.batch.dao.jpa.OrderStatusRepository;
import com.sarvah.mbg.batch.dao.jpa.PaymentDetailsRepository;
import com.sarvah.mbg.batch.dao.jpa.UserInfoRepository;
import com.sarvah.mbg.batch.dao.jpa.UserOrderRepository;
import com.sarvah.mbg.batch.dao.mongo.ProductDAO;
import com.sarvah.mbg.batch.dao.mongo.StoreDAO;
import com.sarvah.mbg.batch.dao.mongo.StoreProductPricingDAO;
import com.sarvah.mbg.batch.dao.mongo.UserDAO;
import com.sarvah.mbg.batch.service.MBGCommandBase;
import com.sarvah.mbg.commons.communication.UserCommunicationService;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.store.Store;
import com.sarvah.mbg.domain.mongo.store.StoreProductPricing;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.OrderDetails;
import com.sarvah.mbg.domain.ordermgmt.OrderStatus;
import com.sarvah.mbg.domain.ordermgmt.payment.InvoiceDetails;
import com.sarvah.mbg.domain.ordermgmt.payment.PaymentDetails;
import com.sarvah.mbg.domain.ordermgmt.shipping.ShippingType;
import com.sarvah.mbg.invoicing.DealerInvoiceGenerator;
import com.sarvah.mbg.invoicing.UserInvoiceGenerator;
import com.sarvah.mbg.invoicing.model.Address;
import com.sarvah.mbg.invoicing.model.ItemInfoObject;
import com.sarvah.mbg.invoicing.model.ItemInfoObjectForDealer;
import com.sarvah.mbg.invoicing.model.OrderInfoObject;
import com.sarvah.mbg.invoicing.model.OrderInfoObjectForDealer;

/**
 * @author Shiva
 *
 */
@Component("InvoiceProcess")
public class InvoiceProcess implements MBGCommandBase {

	private static final Logger logger = LoggerFactory
			.getLogger(InvoiceProcess.class);

	@Value("${name:MBG}")
	private String name;

	@Override
	public void execute() throws Exception {
		System.out
				.println("Invoice generation Process ==== generating invoice for the order");
		logger.info("Invoice generation Process ==== generating invoice for the order");
		try {
			generateInvoice();
		} catch (Exception e) {
			logger.error("Error occured generating invoice ", e);
		}
	}

	@Autowired
	MongoOperations operations;

	/** The user repository. */
	@Autowired
	private UserInfoRepository userRepository;

	@Autowired
	private UserOrderRepository userOrderRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private StoreProductPricingDAO storeProductPricingDAO;

	@Autowired
	private StoreDAO storeDAO;

	@Autowired
	private PaymentDetailsRepository paymentDetailsRepository;

	@Autowired
	private UserInvoiceGenerator userInvoiceGenerator;

	@Autowired
	private DealerInvoiceGenerator dealerInvoiceGenerator;

	@Autowired
	private OrderStatusRepository orderStatusRepository;

	@Autowired
	private UserCommunicationService userCommunicationService;

	@Transactional
	private void generateInvoice() throws Exception {

		long invoiceCount = 0;
		List<Order> orders = new ArrayList<>();
		logger.info("Getting all orders...!");

		OrderStatus orderStatus = orderStatusRepository
				.findByDescription("Confirmed");

		orders = orderRepository.findByOrderStatus(orderStatus);

		for (Order order : orders) {

			// Iterate all the orders and get orders which are placed in last 5
			// days
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date orderDate = order.getOrderedDate();

			String dateInString = sdf.format(orderDate);
			Date date = sdf.parse(dateInString);

			System.out.println(date);// replace by logger

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			System.out.println(calendar);

			GregorianCalendar gc = new GregorianCalendar();

			int dayAfter = gc.get(Calendar.DAY_OF_YEAR);
			System.out.println(dayAfter);// replace by logger

			gc.roll(Calendar.DAY_OF_YEAR, -5);

			int dayBefore = gc.get(Calendar.DAY_OF_YEAR);

			System.out.println(dayBefore);// replace by logger

			if (dayAfter > dayBefore) {

				// Check if there is a existing invoice available for the
				// order
				List<InvoiceDetails> existingInvoice = invoiceRepository
						.findByOrder(order);

				// Check if there is a existing invoice available for the
				// order
				if (existingInvoice.size() == 0) {

					generateInvoiceForEnduser(order);

					generateInvoiceForDealer(order);

				}

				invoiceCount = invoiceRepository.countByOrder(order);

				Set<String> dealerIds = new HashSet<String>();
				for (OrderDetails orderDetails : order.getItems()) {
					dealerIds.add(orderDetails.getDealerId());
				}

				long dealersCount = dealerIds.size();

				if (invoiceCount < (dealersCount + 1)) {
					List<InvoiceDetails> createdInvoices = invoiceRepository
							.findByOrder(order);
					for (InvoiceDetails invoiceDetails : createdInvoices) {
						invoiceRepository.delete(invoiceDetails);
					}
				}
			}
		}
	}

	private void generateInvoiceForEnduser(Order order) throws Exception {

		Set<InvoiceDetails> invoices = new HashSet<InvoiceDetails>();

		// Creating orderInfoObject with necessary information
		// for invoice
		OrderInfoObject orderInfoObject = new OrderInfoObject();

		// Setting orderId and date
		orderInfoObject.setOrderId(order.getMbgOrderId());
		orderInfoObject.setOrderDate(order.getOrderedDate());

		// Getting and setting order's shipping type
		ShippingType shippingType = order.getShippingType();

		if (shippingType.getShippingTypeId() == 1) {
			orderInfoObject.setDeliveryPreference("Delivery To Address");
		} else if (shippingType.getShippingTypeId() == 2) {
			orderInfoObject.setDeliveryPreference("Self Pickup");
		}

		// Getting and setting order's payment details
		PaymentDetails paymentDetails = paymentDetailsRepository
				.findByOrder(order);

		if (paymentDetails.getPaymentType().getPaymentTypeId() == 1) {
			orderInfoObject.setPaymentMethod("Delivery On confirmation");
		} else if (paymentDetails.getPaymentType().getPaymentTypeId() == 2) {
			orderInfoObject.setPaymentMethod("Net Banking");
		} else if (paymentDetails.getPaymentType().getPaymentTypeId() == 3) {
			orderInfoObject.setPaymentMethod("Fund Transfer");
		} else if (paymentDetails.getPaymentType().getPaymentTypeId() == 4) {
			orderInfoObject.setPaymentMethod("Buildonn Wallet");
		}

		// Getting and setting order's billing address
		String billingPin = Integer.toString(order.getBillingAddress()
				.getPincode());

		Address billingAddress = new Address(order.getBillingAddress()
				.getName(), order.getBillingAddress().getAddressLine1(), order
				.getBillingAddress().getCity(), order.getBillingAddress()
				.getState(), order.getBillingAddress().getCountry(),
				billingPin, order.getBillingAddress().getContactNo());

		orderInfoObject.setBuyerAddress(billingAddress);

		// Getting and setting order's shipping address
		String shippingPin = Integer.toString(order.getShippingAddress()
				.getPincode());

		Address shippingAddress = new Address(order.getShippingAddress()
				.getName(), order.getShippingAddress().getAddressLine1(), order
				.getShippingAddress().getCity(), order.getShippingAddress()
				.getState(), order.getShippingAddress().getCountry(),
				shippingPin, order.getShippingAddress().getContactNo());

		orderInfoObject.setShippingAddress(shippingAddress);

		User user = userDAO.findOne(order.getUserInfo().getMongoUserId());
		// Getting and setting order's items
		Set<ItemInfoObject> itemInfoObjectSet = new HashSet<ItemInfoObject>();

		double totalAmount = 0.0;
		for (OrderDetails orderDetails : order.getItems()) {

			ItemInfoObject itemInfoObject = new ItemInfoObject();

			// Setting all necessary information about the items
			// in the order

			Product product = productDAO.findOne(orderDetails.getItemId());
			String qtyType = product.getQuantityType().getName() + "/"
					+ orderDetails.getQuantity();
			itemInfoObject.setQtyType(qtyType);

			String productName = product.getName() + " Prod. ID: "
					+ product.getSkuId();

			itemInfoObject.setName(productName);

			User dealer = userDAO.findOne(orderDetails.getDealerId());
			String dealerCity = null;
			for (com.sarvah.mbg.domain.mongo.common.contact.Address dealerAddress : dealer
					.getAddresses()) {
				dealerCity = dealerAddress.getCity();
			}
			String dealerNameWithCity = dealer.getFullName();

			String[] storeNameArray = dealerNameWithCity.split(" ");
			StringBuffer storeName = new StringBuffer();
			for (String str : storeNameArray) {
				if (str.length() >= 2) {
					storeName.append(str.substring(0, 2));
				}
			}

			itemInfoObject.setSellerName(storeName.append(" ").append(
					dealerCity));

			itemInfoObject.setQty(orderDetails.getQuantity());

			DecimalFormat df = new DecimalFormat("####0.00");
			itemInfoObject.setRate(df.format(orderDetails.getSellingPrice()));
			itemInfoObject.setTax("0.00");

			Store store = storeDAO.findByUserId(orderDetails.getDealerId());

			StoreProductPricing storeProductPricing = storeProductPricingDAO
					.findByProductIdAndStoreId(orderDetails.getItemId(),
							store.getId());

			double discount = storeProductPricing.getDiscount();

			DecimalFormat discDf = new DecimalFormat("####0.00");
			itemInfoObject.setDiscount(discDf.format(discount));

			double deliveryCharge = orderDetails.getShippingCharge();
			if (deliveryCharge == 0.0) {
				itemInfoObject.setDeliveryCharge("Free");
			} else {
				DecimalFormat deliDf = new DecimalFormat("####0.00");
				itemInfoObject.setDeliveryCharge(deliDf.format(deliveryCharge));
			}

			itemInfoObject.setAmount(df.format(orderDetails.getTotalPrice()));

			// Calculating order total amount
			totalAmount = totalAmount + orderDetails.getTotalPrice();

			itemInfoObjectSet.add(itemInfoObject);
		}

		orderInfoObject.setItems(itemInfoObjectSet);

		DecimalFormat totalDf = new DecimalFormat("####0.00");
		orderInfoObject.setTotalAmount(totalDf.format(totalAmount));

		DecimalFormat decimalFormat = new DecimalFormat("####0.00");
		orderInfoObject.setTotalSavings(decimalFormat.format(order
				.getTotalSavings()));

		orderInfoObject.setUserId(order.getUserInfo().getUserid());

		InvoiceDetails invoiceDetails = new InvoiceDetails();

		invoiceDetails.setOrder(order);

		invoices.add(invoiceDetails);

		invoiceDetails.setInvoiceDate(new Date());
		invoiceDetails.setLastModifiedDate(new Date());
		invoiceDetails.setCreatedBy("BONN");
		invoiceDetails.setLastModifiedBy("BONN");
		invoiceDetails.setUserId(order.getUserInfo().getMongoUserId());
		order.setInvoices(invoices);

		orderRepository.save(order);

		String url = null;
		InvoiceDetails savedInvoice1 = null;
		for (InvoiceDetails savedInvoice : order.getInvoices()) {

			if (savedInvoice.getUserId().equalsIgnoreCase(user.getId())) {

				savedInvoice1 = invoiceRepository.findByOrderAndUserId(order,
						user.getId());

				String mongoIdFormat = String.format("%05d",
						savedInvoice1.getTaxInvoiceId());

				orderInfoObject.setInvoiceId("BONNENOC" + mongoIdFormat);
				// Invoice for enduser
				url = userInvoiceGenerator
						.generateInvoiceForEnduser(orderInfoObject);

			}
		}

		savedInvoice1.setSentTouser(true);

		savedInvoice1.setFileUrl(url);

		String mongoIdFormat = String.format("%05d",
				savedInvoice1.getTaxInvoiceId());

		savedInvoice1.setMbgInvoiceId("BONNENOC" + mongoIdFormat);

		invoiceRepository.save(savedInvoice1);

		userCommunicationService.sendOrderInvoiceForEnduser(user,
				order.getMbgOrderId(), url);

		System.out.println("Enduser invoice generated: Success");
	}

	private void generateInvoiceForDealer(Order order) throws Exception {

		Set<InvoiceDetails> invoices = new HashSet<InvoiceDetails>();

		Set<String> dealerIds = new HashSet<String>();
		for (OrderDetails orderDetails : order.getItems()) {
			dealerIds.add(orderDetails.getDealerId());
		}

		List<OrderDetails> dealerItems = new ArrayList<OrderDetails>();
		for (String dealerId : dealerIds) {

			User user = userDAO.findOne(dealerId);
			dealerItems.addAll(orderDetailsRepository.findByOrderAndDealerId(
					order, dealerId));

			// Creating orderInfoObject with necessary information
			// for invoice
			OrderInfoObjectForDealer orderInfoObjectForDealer = new OrderInfoObjectForDealer();

			// Setting orderId and date
			orderInfoObjectForDealer.setOrderId(order.getMbgOrderId());
			orderInfoObjectForDealer.setOrderDate(order.getOrderedDate());

			// Getting and setting order's shipping type
			ShippingType shippingType = new ShippingType();
			shippingType = order.getShippingType();

			if (shippingType.getShippingTypeId() == 1) {
				orderInfoObjectForDealer
						.setDeliveryPreference("Delivery To Address");
			} else if (shippingType.getShippingTypeId() == 2) {
				orderInfoObjectForDealer.setDeliveryPreference("Self Pickup");
			}

			// Getting and setting order's payment details
			PaymentDetails paymentDetails = new PaymentDetails();
			paymentDetails = paymentDetailsRepository.findByOrder(order);

			if (paymentDetails.getPaymentType().getPaymentTypeId() == 1) {
				orderInfoObjectForDealer
						.setPaymentMethod("Delivery On confirmation");
			} else if (paymentDetails.getPaymentType().getPaymentTypeId() == 2) {
				orderInfoObjectForDealer.setPaymentMethod("Net Banking");
			} else if (paymentDetails.getPaymentType().getPaymentTypeId() == 3) {
				orderInfoObjectForDealer.setPaymentMethod("Fund Transfer");
			} else if (paymentDetails.getPaymentType().getPaymentTypeId() == 4) {
				orderInfoObjectForDealer.setPaymentMethod("Buildonn Wallet");
			}

			// Getting and setting order's billing address
			String billingPin = Integer.toString(order.getBillingAddress()
					.getPincode());
			Address billingAddress = new Address(order.getBillingAddress()
					.getName(), order.getBillingAddress().getAddressLine1(),
					order.getBillingAddress().getCity(), order
							.getBillingAddress().getState(), order
							.getBillingAddress().getCountry(), billingPin,
					order.getBillingAddress().getContactNo());
			orderInfoObjectForDealer.setBuyerAddress(billingAddress);

			// Getting and setting order's shipping address
			String shippingPin = Integer.toString(order.getShippingAddress()
					.getPincode());
			Address shippingAddress = new Address(order.getShippingAddress()
					.getName(), order.getShippingAddress().getAddressLine1(),
					order.getShippingAddress().getCity(), order
							.getShippingAddress().getState(), order
							.getShippingAddress().getCountry(), shippingPin,
					order.getShippingAddress().getContactNo());
			orderInfoObjectForDealer.setShippingAddress(shippingAddress);

			Set<ItemInfoObjectForDealer> itemInfoObjectSetForDealers = new HashSet<ItemInfoObjectForDealer>();
			double totalAmount = 0.0;

			for (OrderDetails dealerItem : dealerItems) {

				ItemInfoObjectForDealer itemInfoObjectForDealer = new ItemInfoObjectForDealer();

				// Setting all necessary information about the items
				// in the order

				Product product = productDAO.findOne(dealerItem.getItemId());
				String qtyType = product.getQuantityType().getName() + "/"
						+ dealerItem.getQuantity();
				itemInfoObjectForDealer.setQtyType(qtyType);

				String productName = product.getName() + " Prod. ID: "
						+ product.getSkuId();

				itemInfoObjectForDealer.setName(productName);

				itemInfoObjectForDealer.setQty(dealerItem.getQuantity());

				DecimalFormat df = new DecimalFormat("####0.00");
				itemInfoObjectForDealer.setRate(df.format(dealerItem
						.getSellingPrice()));
				itemInfoObjectForDealer.setTax("0.00");

				Store store = storeDAO.findByUserId(dealerItem.getDealerId());

				StoreProductPricing storeProductPricing = storeProductPricingDAO
						.findByProductIdAndStoreId(dealerItem.getItemId(),
								store.getId());

				double discount = storeProductPricing.getDiscount();

				DecimalFormat discDf = new DecimalFormat("####0.00");
				itemInfoObjectForDealer.setDiscount(discDf.format(discount));

				double deliveryCharge = dealerItem.getShippingCharge();
				if (deliveryCharge == 0.0) {
					itemInfoObjectForDealer.setDeliveryCharge("Free");
				} else {
					DecimalFormat deliDf = new DecimalFormat("####0.00");
					itemInfoObjectForDealer.setDeliveryCharge(deliDf
							.format(deliveryCharge));
				}
				DecimalFormat totalDf = new DecimalFormat("####0.00");
				itemInfoObjectForDealer.setAmount(totalDf.format(totalAmount));

				User dealer = userDAO.findOne(dealerItem.getDealerId());
				String dealerCity = null;
				for (com.sarvah.mbg.domain.mongo.common.contact.Address dealerAddress : dealer
						.getAddresses()) {
					dealerCity = dealerAddress.getCity();
				}
				String dealerName = dealer.getFullName();
				String[] storeNameArray = dealerName.split(" ");
				StringBuffer storeName = new StringBuffer();
				for (String str : storeNameArray) {
					if (str.length() >= 2) {
						storeName.append(str.substring(0, 2));
					}
				}

				itemInfoObjectForDealer.setSellerName(storeName.append(" ")
						.append(dealerCity));

				itemInfoObjectForDealer.setAmount(df.format(dealerItem
						.getTotalPrice()));

				// Calculating order total amount
				totalAmount = totalAmount + dealerItem.getTotalPrice();

				itemInfoObjectSetForDealers.add(itemInfoObjectForDealer);
			}
			orderInfoObjectForDealer.setItems(itemInfoObjectSetForDealers);

			DecimalFormat totalDf = new DecimalFormat("####0.00");
			orderInfoObjectForDealer
					.setTotalAmount(totalDf.format(totalAmount));

			orderInfoObjectForDealer.setUserId(order.getUserInfo().getUserid());

			orderInfoObjectForDealer.setUserId(order.getUserInfo().getUserid());

			InvoiceDetails invoiceDetails = new InvoiceDetails();

			invoiceDetails.setOrder(order);

			invoices.add(invoiceDetails);

			invoiceDetails.setInvoiceDate(new Date());
			invoiceDetails.setLastModifiedDate(new Date());
			invoiceDetails.setCreatedBy("BONN");
			invoiceDetails.setLastModifiedBy("BONN");
			invoiceDetails.setUserId(dealerId);

			order.setInvoices(invoices);

			orderRepository.save(order);

			String url = null;
			InvoiceDetails savedInvoice1 = null;
			for (InvoiceDetails savedInvoice : order.getInvoices()) {

				if (savedInvoice.getUserId().equalsIgnoreCase(dealerId)) {

					savedInvoice1 = invoiceRepository.findByOrderAndUserId(
							order, user.getId());

					String mongoIdFormat = String.format("%05d",
							savedInvoice1.getTaxInvoiceId());

					orderInfoObjectForDealer.setInvoiceId("BONNDEOC"
							+ mongoIdFormat);

					// Invoice for dealer
					url = dealerInvoiceGenerator
							.generateInvoiceForDealer(orderInfoObjectForDealer);
				}
			}

			savedInvoice1.setSentTouser(true);

			savedInvoice1.setFileUrl(url);

			String mongoIdFormat = String.format("%05d",
					savedInvoice1.getTaxInvoiceId());

			savedInvoice1.setMbgInvoiceId("BONNDEOC" + mongoIdFormat);

			invoiceRepository.save(savedInvoice1);

			userCommunicationService.sendOrderInvoiceForDealer(user,
					order.getMbgOrderId(), url);

			System.out.println("Dealer invoice generated: Success");
		}
	}
}