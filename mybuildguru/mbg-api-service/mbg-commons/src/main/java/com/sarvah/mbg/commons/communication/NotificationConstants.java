/**
 * 
 */
package com.sarvah.mbg.commons.communication;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shivu
 *
 */
public class NotificationConstants {
	// Admin Receiver
	// Both Email and SMS needs to be trigger
	public static final String ADMIN_SINGLEITEMORDER_PLACED = "admin/Placed_SingleItemOrder_Admin.vm";
	public static final String ADMIN_MULTIITEMORDER_PLACED = "admin/Placed_MultiItemOrder_Admin.vm";
	public static final String ADMIN_SINGLEITEM_CANCELLED = "admin/Cancelled_SingleItem_Admin.vm";
	public static final String ADMIN_MULTIITEM_CANCELLED = "admin/Cancelled_MultiItem_Admin.vm";
	public static final String ADMIN_SINGLEITEM_RETURNED = "admin/Returned_SingleItem_Admin.vm";
	public static final String ADMIN_MULTIITEM_RETURNED = "admin/Returned_MultiItem_Admin.vm";
	public static final String ADMIN_NEWREGISTRATIONALERT = "admin/NewRegistrationAlert.vm";
	public static final String ADMIN_PRODUCTENQUIRY = "admin/EnquiryForm_Admin.vm";
	public static final String ADMIN_SINGLEITEMQRWITHDELIVERYDATE = "admin/QuoteRequest_SingleItemWithDeliveryDate.vm";
	public static final String ADMIN_SINGLEITEMQRWITHOUTDELIVERYDATE = "admin/QuoteRequest_SingleItemWithoutDeliveryDate.vm";
	public static final String ADMIN_MULTIITEMQRWITHDELIVERYDATE = "admin/QuoteRequest_MultiItemWithDeliveryDate.vm";
	public static final String ADMIN_MULTIITEMQRWITHOUTDELIVERYDATE = "admin/QuoteRequest_MultiItemWithoutDeliveryDate.vm";
	public static final String ADMIN_QUOTEUPDATESELLERTOADMIN = "admin/QuoteUpdateSellerToAdmin.vm";
	public static final String ADMIN_REQUESTFORCALLBACK = "admin/RequestForCallBack_Admin.vm";
	// Only Email needs to be trigger
	public static final String ADMIN_ITEM_DISPATCHED = "admin/Dispatched_Item_Admin.vm";
	public static final String INSTANTORDERINFO = "admin/InstantOrderInfo.vm";
	public static final String REQUESTQUOTEINFO = "admin/RequestQuote.vm";

	// TeleAssociate Reciever
	public static final String TELEASSOCIATE_SINGLEITEMORDER_PLACED = "admin/Placed_SingleItemOrder_TeleAssociate.vm";
	public static final String TELEASSOCIATE_MULTIITEMORDER_PLACED = "admin/Placed_MultiItemOrder_TeleAssociate.vm";
	public static final String TELEASSOCIATE_SINGLEITEM_CANCELLED = "admin/Cancelled_SingleItem_TeleAssociate.vm";
	public static final String TELEASSOCIATE_MULTIITEM_CANCELLED = "admin/Cancelled_MultiItem_TeleAssociate.vm";
	public static final String TELEASSOCIATE_SINGLEITEM_RETURNED = "admin/Returned_SingleItem_TeleAssociate.vm";
	public static final String TELEASSOCIATE_MULTIITEM_RETURNED = "admin/Returned_MultiItem_TeleAssociate.vm";

	// TechAssociate Reciever
	public static final String TECHASSOCIATE_SINGLEITEMORDER_PLACED = "admin/Placed_SingleItemOrder_TechAssociate.vm";
	public static final String TECHASSOCIATE_MULTIITEMORDER_PLACED = "admin/Placed_MultiItemOrder_TechAssociate.vm";
	public static final String TECHASSOCIATE_SINGLEITEM_CANCELLED = "admin/Cancelled_SingleItem_TechAssociate.vm";
	public static final String TECHASSOCIATE_MULTIITEM_CANCELLED = "admin/Cancelled_MultiItem_TechAssociate.vm";
	public static final String TECHASSOCIATE_SINGLEITEM_RETURNED = "admin/Returned_SingleItem_TechAssociate.vm";
	public static final String TECHASSOCIATE_MULTIITEM_RETURNED = "admin/Returned_MultiItem_TechAssociate.vm";

	// Provider Receiver
	// Both Email and SMS needs to be trigger
	public static final String PROVIDER_REGISTRATION = "admin/Registration_Provider.vm";
	public static final String PROVIDER_WELCOME = "admin/Welcome_Provider.vm";
	public static final String PROVIDER_PRODUCTAPPROVED = "admin/ProductApproved_Provider.vm";

	// Dealer Receiver
	// Both Email and SMS needs to be trigger
	public static final String DEALER_REGISTRATION = "admin/Registration_Dealer.vm";
	public static final String DEALER_WELCOME = "admin/Welcome_Dealer.vm";
	public static final String DEALER_PRODUCTAPPROVED = "admin/ProductApproved_Dealer.vm";
	public static final String DEALER_SINGLEITEM_CANCELLED = "admin/Cancelled_SingleItem_Dealer.vm";
	public static final String DEALER_MULTIITEM_CANCELLED = "admin/Cancelled_MultiItem_Dealer.vm";
	public static final String DEALER_SINGLEITEM_CONFIRMED = "admin/Confirmed_SingleItem_Dealer.vm";
	public static final String DEALER_MULTIITEM_CONFIRMED = "admin/Confirmed_MultiItem_Dealer.vm";
	public static final String DEALER_SINGLEITEM_RETURNED = "admin/Returned_SingleItem_Dealer.vm";
	public static final String DEALER_MULTIITEM_RETURNED = "admin/Returned_MultiItem_Dealer.vm";
	public static final String DEALER_SENDREQUIREMENT = "admin/SendRequirementToSeller.vm";
	public static final String DEALER_SENDREQUIREMENTFILE = "admin/SendRequirementFileToSeller.vm";
	public static final String DEALER_QUOTEREQUESTWITHFEWITEMS = "admin/QuoteRequestWithFewItems_Seller.vm";
	public static final String DEALER_QUOTEREQUESTWITHMOREITEMS = "admin/QuoteRequestWithMoreItems_Seller.vm";
	// Only Email needs to be trigger
	public static final String DEALER_ORDER_PLACED = "admin/Placed_Order_Dealer.vm";
	public static final String DEALER_ITEM_DISPATCHED = "admin/Dispatched_Item_Dealer.vm";
	public static final String DEALER_INVOICE = "admin/InvoiceForDealer.vm";

	// Architect Receiver
	// Both Email and SMS needs to be trigger
	public static final String ARCHITECT_REGISTRATION = "admin/Registration_Architect.vm";
	public static final String ARCHITECT_WELCOME = "admin/Welcome_Architect.vm";
	public static final String ARCHITECT_POSTEDPROJECTINFO = "admin/PostedProjectInfo_Architect.vm";

	// InteriorDesigner Receiver
	// Both Email and SMS needs to be trigger
	public static final String INTERIORDESIGNER_REGISTRATION = "admin/Registration_InteriorDesigner.vm";
	public static final String INTERIORDESIGNER_WELCOME = "admin/Welcome_InteriorDesigner.vm";
	public static final String INTERIORDESIGNER_POSTEDPROJECTINFO = "admin/PostedProjectInfo_InteriorDesigner.vm";

	// Enduser Receiver
	// Both Email and SMS needs to be trigger
	public static final String ENDUSER_REGISTRATION = "admin/Registration_Enduser.vm";
	public static final String ENDUSER_WELCOME = "admin/Welcome_Enduser.vm";
	public static final String ENDUSER_PLACEDSINGLEITEMORDERTHROUGHNEFT = "admin/PlacedSingleItemOrderThroughNEFT_Enduser.vm";
	public static final String ENDUSER_PLACEDMULTIITEMORDERTHROUGHNEFT = "admin/PlacedMultiItemOrderThroughNEFT_Enduser.vm";
	public static final String ENDUSER_ORDER_PLACED = "admin/Placed_Order_Enduser.vm";
	public static final String ENDUSER_SINGLEITEM_CANCELLED = "admin/Cancelled_SingleItem_Enduser.vm";
	public static final String ENDUSER_MULTIITEM_CANCELLED = "admin/Cancelled_MultiItem_Enduser.vm";
	public static final String ENDUSER_SINGLEITEM_PACKED = "admin/Packed_SingleItem_Enduser.vm";
	public static final String ENDUSER_MULTIITEM_PACKED = "admin/Packed_MultiItem_Enduser.vm";
	public static final String ENDUSER_SINGLEITEM_SELFPICKUP_PACKED = "admin/Packed_SingleItem_SelfPickUp_Enduser.vm";
	public static final String ENDUSER_MULTIITEM_SELFPICKUP_PACKED = "admin/Packed_MultiItem_SelfPickUp_Enduser.vm";
	public static final String ENDUSER_SINGLEITEM_CONFIRMED = "admin/Confirmed_SingleItem_Enduser.vm";
	public static final String ENDUSER_MULTIITEM_CONFIRMED = "admin/Confirmed_MultiItem_Enduser.vm";
	public static final String ENDUSER_SINGLEITEM_DISPATCHED = "admin/Dispatched_SingleItem_Enduser.vm";
	public static final String ENDUSER_MULTIITEM_DISPATCHED = "admin/Dispatched_MultiItem_Enduser.vm";
	public static final String ENDUSER_SINGLEITEM_DELIVERED = "admin/Delivered_SingleItem_Enduser.vm";
	public static final String ENDUSER_MULTIITEM_DELIVERED = "admin/Delivered_MultiItem_Enduser.vm";
	public static final String ENDUSER_SINGLEITEM_UNCONFIRMED = "admin/UnConfirmed_SingleItem_Enduser.vm";
	public static final String ENDUSER_MULTIITEM_UNCONFIRMED = "admin/UnConfirmed_MultiItem_Enduser.vm";
	public static final String ENDUSER_SINGLEITEM_RETURNED = "admin/Returned_SingleItem_Enduser.vm";
	public static final String ENDUSER_MULTIITEM_RETURNED = "admin/Returned_MultiItem_Enduser.vm";
	public static final String ENDUSER_BIDDONEINFO = "admin/BidDoneInfo_Enduser.vm";
	public static final String ENDUSER_INVOICE = "admin/InvoiceForEnduser.vm";
	public static final String ENDUSER_SERVICEPROVIDERINFOTOENDUSER = "admin/ServiceProviderDetailsToEnduser.vm";
	public static final String ENDUSER_ENQUIRYFORMCONFIRMATION = "admin/EnquiryFormConfirmation_Enduser.vm";
	public static final String ENDUSER_SNAPSHOTORDERCONFIRMATION = "admin/InstantOrderConfirmation_Enduser.vm";
	public static final String ENDUSER_SHAREQUOTEWITHCUSTOMER = "admin/ShareQuoteWithCustomer_Enduser.vm";
	public static final String ENDUSER_SHAREQUOTESMSWITHCUSTOMER = "admin/ShareQuoteSmsWithCustomer_Enduser.vm";
	// public static final String ENDUSER_GETQUOTECONFIRMATION =
	// "admin/GetQuoteConfirmation_Enduser.vm";
	public static final String ENDUSER_QUOTEREQUESTCONFIRMATION = "admin/QuoteRequestConfirmation_Enduser.vm";
	public static final String ENDUSER_ORDERAMOUNTUPDATE = "admin/OrderAmountUpdate_Enduser.vm";
	public static final String ENDUSER_ORDERAMOUNTUPDATEWITHZEROOUTSTADNING = "admin/OrderAmountUpdateWithZeroOutstanding_Enduser.vm";
	public static final String ENDUSER_CHEQUECOLLECTEDDATEINFO = "admin/ChequeCollectedInfo_Enduser.vm";

	public static final String ADMIN_UPDATEEMAILANDMOBILE = "admin/UpdateEmailAndMobileByAdmin.vm";
	public static final String ADMIN_UPDATEEMAIL = "admin/UpdateEmailByAdmin.vm";
	public static final String ADMIN_UPDATEMOBILE = "admin/UpdateMobileByAdmin.vm";
	public static final String ENDUSER_UPDATEEMAILANDMOBILE = "admin/UpdateEmailAndMobileByUser.vm";
	public static final String ENDUSER_UPDATEEMAIL = "admin/UpdateEmailByUser.vm";
	public static final String ENDUSER_UPDATEMOBILE = "admin/UpdateMobileByUser.vm";

	// BusinessAssociate
	// Both Email and SMS needs to be trigger
	public static final String BUSINESSASSOCIATE_REGISTRATION = "admin/Registration_BusinessAssociate.vm";
	public static final String BUSINESSASSOCIATE_WELCOME = "admin/Welcome_BusinessAssociate.vm";

	// CivilEngineer
	// Both Email and SMS needs to be trigger
	public static final String CIVILENGINEER_REGISTRATION = "admin/Registration_CivilEngineer.vm";
	public static final String CIVILENGINEER_WELCOME = "admin/Welcome_CivilEngineer.vm";

	// ServiceProvider
	// Both Email and SMS needs to be trigger
	public static final String SERVICEPROVIDER_REGISTRATION = "admin/Registration_ServiceProvider.vm";
	public static final String SERVICEPROVIDER_WELCOME = "admin/Welcome_ServiceProvider.vm";
	public static final String ENDUSERDETAILSTOSERVICEPROVIDER = "admin/EnduserDetailsToServiceProvider.vm";
	public static final String ENDUSERDETAILSTOSERVICEPROVIDERWITHDESC = "admin/EnduserDetailsToServiceProviderWithDesc.vm";

	// All user Receiver
	// Both Email and SMS needs to be trigger
	public static final String FORGETPASSWORDOTP = "admin/ForgotPasswordOTP.vm";
	public static final String USER_CREDENTIALS = "admin/UserCredentials.vm";
	public static final String USER_RESETPASSWORD = "admin/ResetPassword.vm";
	public static final String USER_AMOUNTUPDATE = "admin/UserAmountUpdate.vm";

	private static Map<String, Boolean> EMAIL_MAP = null;
	private static Map<String, Boolean> SMS_MAP = null;

	/**
	 * 
	 * @return
	 */
	public static Map<String, Boolean> getEmailNotifMap() {

		if (EMAIL_MAP == null) {
			EMAIL_MAP = new HashMap<String, Boolean>();

			// Admin
			EMAIL_MAP.put(ADMIN_SINGLEITEMORDER_PLACED, true);
			EMAIL_MAP.put(ADMIN_MULTIITEMORDER_PLACED, true);
			EMAIL_MAP.put(ADMIN_SINGLEITEM_RETURNED, true);
			EMAIL_MAP.put(ADMIN_SINGLEITEM_RETURNED, true);
			EMAIL_MAP.put(ADMIN_SINGLEITEM_CANCELLED, true);
			EMAIL_MAP.put(ADMIN_MULTIITEM_CANCELLED, true);
			EMAIL_MAP.put(ADMIN_ITEM_DISPATCHED, true);
			EMAIL_MAP.put(INSTANTORDERINFO, true);
			EMAIL_MAP.put(REQUESTQUOTEINFO, true);
			EMAIL_MAP.put(ADMIN_NEWREGISTRATIONALERT, true);
			EMAIL_MAP.put(ADMIN_PRODUCTENQUIRY, true);
			EMAIL_MAP.put(ADMIN_SINGLEITEMQRWITHDELIVERYDATE, true);
			EMAIL_MAP.put(ADMIN_SINGLEITEMQRWITHOUTDELIVERYDATE, true);
			EMAIL_MAP.put(ADMIN_MULTIITEMQRWITHDELIVERYDATE, true);
			EMAIL_MAP.put(ADMIN_MULTIITEMQRWITHOUTDELIVERYDATE, true);
			EMAIL_MAP.put(ADMIN_REQUESTFORCALLBACK, true);
			// TeleAssociates
			EMAIL_MAP.put(TELEASSOCIATE_SINGLEITEMORDER_PLACED, true);
			EMAIL_MAP.put(TELEASSOCIATE_MULTIITEMORDER_PLACED, true);
			EMAIL_MAP.put(TELEASSOCIATE_SINGLEITEM_CANCELLED, true);
			EMAIL_MAP.put(TELEASSOCIATE_MULTIITEM_CANCELLED, true);
			EMAIL_MAP.put(TELEASSOCIATE_SINGLEITEM_RETURNED, true);
			EMAIL_MAP.put(TELEASSOCIATE_MULTIITEM_RETURNED, true);

			// TechAssociates
			EMAIL_MAP.put(TECHASSOCIATE_SINGLEITEMORDER_PLACED, true);
			EMAIL_MAP.put(TECHASSOCIATE_MULTIITEMORDER_PLACED, true);
			EMAIL_MAP.put(TECHASSOCIATE_SINGLEITEM_CANCELLED, true);
			EMAIL_MAP.put(TECHASSOCIATE_MULTIITEM_CANCELLED, true);
			EMAIL_MAP.put(TECHASSOCIATE_SINGLEITEM_RETURNED, true);
			EMAIL_MAP.put(TECHASSOCIATE_MULTIITEM_RETURNED, true);

			// Provider
			EMAIL_MAP.put(PROVIDER_REGISTRATION, true);
			EMAIL_MAP.put(PROVIDER_WELCOME, true);
			EMAIL_MAP.put(PROVIDER_PRODUCTAPPROVED, true);

			// Dealer
			EMAIL_MAP.put(DEALER_REGISTRATION, true);
			EMAIL_MAP.put(DEALER_WELCOME, true);
			EMAIL_MAP.put(DEALER_ORDER_PLACED, true);
			EMAIL_MAP.put(DEALER_PRODUCTAPPROVED, true);
			EMAIL_MAP.put(DEALER_SINGLEITEM_CONFIRMED, true);
			EMAIL_MAP.put(DEALER_MULTIITEM_CONFIRMED, true);
			EMAIL_MAP.put(DEALER_ITEM_DISPATCHED, true);
			EMAIL_MAP.put(DEALER_SINGLEITEM_RETURNED, true);
			EMAIL_MAP.put(DEALER_MULTIITEM_RETURNED, true);
			EMAIL_MAP.put(DEALER_SINGLEITEM_CANCELLED, true);
			EMAIL_MAP.put(DEALER_MULTIITEM_CANCELLED, true);
			EMAIL_MAP.put(DEALER_INVOICE, true);
			EMAIL_MAP.put(DEALER_SENDREQUIREMENT, true);
			EMAIL_MAP.put(DEALER_SENDREQUIREMENTFILE, true);
			EMAIL_MAP.put(DEALER_QUOTEREQUESTWITHFEWITEMS, true);
			EMAIL_MAP.put(DEALER_QUOTEREQUESTWITHMOREITEMS, true);

			// Architect
			EMAIL_MAP.put(ARCHITECT_REGISTRATION, true);
			EMAIL_MAP.put(ARCHITECT_WELCOME, true);
			EMAIL_MAP.put(ARCHITECT_POSTEDPROJECTINFO, true);

			// InteriorDesigner
			EMAIL_MAP.put(INTERIORDESIGNER_REGISTRATION, true);
			EMAIL_MAP.put(INTERIORDESIGNER_WELCOME, true);
			EMAIL_MAP.put(INTERIORDESIGNER_POSTEDPROJECTINFO, true);

			// Enduser
			EMAIL_MAP.put(ENDUSER_REGISTRATION, true);
			EMAIL_MAP.put(ENDUSER_WELCOME, true);
			EMAIL_MAP.put(ENDUSER_PLACEDSINGLEITEMORDERTHROUGHNEFT, true);
			EMAIL_MAP.put(ENDUSER_PLACEDMULTIITEMORDERTHROUGHNEFT, true);
			EMAIL_MAP.put(ENDUSER_ORDER_PLACED, true);
			EMAIL_MAP.put(ENDUSER_SINGLEITEM_PACKED, true);
			EMAIL_MAP.put(ENDUSER_MULTIITEM_PACKED, true);
			EMAIL_MAP.put(ENDUSER_SINGLEITEM_SELFPICKUP_PACKED, true);
			EMAIL_MAP.put(ENDUSER_MULTIITEM_SELFPICKUP_PACKED, true);
			EMAIL_MAP.put(ENDUSER_SINGLEITEM_CONFIRMED, true);
			EMAIL_MAP.put(ENDUSER_MULTIITEM_CONFIRMED, true);
			EMAIL_MAP.put(ENDUSER_SINGLEITEM_DISPATCHED, true);
			EMAIL_MAP.put(ENDUSER_MULTIITEM_DISPATCHED, true);
			EMAIL_MAP.put(ENDUSER_SINGLEITEM_DELIVERED, true);
			EMAIL_MAP.put(ENDUSER_MULTIITEM_DELIVERED, true);
			EMAIL_MAP.put(ENDUSER_SINGLEITEM_UNCONFIRMED, true);
			EMAIL_MAP.put(ENDUSER_MULTIITEM_UNCONFIRMED, true);
			EMAIL_MAP.put(ENDUSER_SINGLEITEM_RETURNED, true);
			EMAIL_MAP.put(ENDUSER_MULTIITEM_RETURNED, true);
			EMAIL_MAP.put(ENDUSER_SINGLEITEM_CANCELLED, true);
			EMAIL_MAP.put(ENDUSER_MULTIITEM_CANCELLED, true);
			EMAIL_MAP.put(ENDUSER_BIDDONEINFO, true);
			EMAIL_MAP.put(ENDUSER_INVOICE, true);
			EMAIL_MAP.put(ADMIN_UPDATEEMAILANDMOBILE, true);
			EMAIL_MAP.put(ADMIN_UPDATEEMAIL, true);
			EMAIL_MAP.put(ADMIN_UPDATEMOBILE, true);
			EMAIL_MAP.put(ENDUSER_UPDATEEMAILANDMOBILE, true);
			EMAIL_MAP.put(ENDUSER_UPDATEEMAIL, true);
			EMAIL_MAP.put(ENDUSER_UPDATEMOBILE, true);
			EMAIL_MAP.put(ENDUSER_SERVICEPROVIDERINFOTOENDUSER, true);
			EMAIL_MAP.put(ENDUSER_ENQUIRYFORMCONFIRMATION, true);
			EMAIL_MAP.put(ENDUSER_SNAPSHOTORDERCONFIRMATION, true);
			EMAIL_MAP.put(ENDUSER_SHAREQUOTEWITHCUSTOMER, true);
			// EMAIL_MAP.put(ENDUSER_GETQUOTECONFIRMATION, true);
			EMAIL_MAP.put(ENDUSER_QUOTEREQUESTCONFIRMATION, true);
			EMAIL_MAP.put(ENDUSER_ORDERAMOUNTUPDATE, true);

			// BusinessAsociate
			EMAIL_MAP.put(BUSINESSASSOCIATE_REGISTRATION, true);
			EMAIL_MAP.put(BUSINESSASSOCIATE_WELCOME, true);

			// CivilEngineer
			EMAIL_MAP.put(CIVILENGINEER_REGISTRATION, true);
			EMAIL_MAP.put(CIVILENGINEER_WELCOME, true);

			// ServiceProvider
			EMAIL_MAP.put(SERVICEPROVIDER_REGISTRATION, true);
			EMAIL_MAP.put(SERVICEPROVIDER_WELCOME, true);
			EMAIL_MAP.put(ENDUSERDETAILSTOSERVICEPROVIDER, true);
			EMAIL_MAP.put(ENDUSERDETAILSTOSERVICEPROVIDERWITHDESC, true);

			// for all user
			EMAIL_MAP.put(FORGETPASSWORDOTP, true);
			EMAIL_MAP.put(USER_CREDENTIALS, true);
			EMAIL_MAP.put(USER_RESETPASSWORD, true);
			EMAIL_MAP.put(USER_AMOUNTUPDATE, true);
		}

		return EMAIL_MAP;

	}

	/**
	 * 
	 * @return
	 */
	public static Map<String, Boolean> getSmsNotifiMap() {

		if (SMS_MAP == null) {
			SMS_MAP = new HashMap<String, Boolean>();
			// Admin
			SMS_MAP.put(ADMIN_SINGLEITEMORDER_PLACED, true);
			SMS_MAP.put(ADMIN_MULTIITEMORDER_PLACED, true);
			SMS_MAP.put(ADMIN_SINGLEITEM_RETURNED, true);
			SMS_MAP.put(ADMIN_MULTIITEM_RETURNED, true);
			SMS_MAP.put(ADMIN_SINGLEITEM_CANCELLED, true);
			SMS_MAP.put(ADMIN_MULTIITEM_CANCELLED, true);
			SMS_MAP.put(ADMIN_NEWREGISTRATIONALERT, true);
			SMS_MAP.put(ADMIN_SINGLEITEMQRWITHDELIVERYDATE, true);
			SMS_MAP.put(ADMIN_SINGLEITEMQRWITHOUTDELIVERYDATE, true);
			SMS_MAP.put(ADMIN_MULTIITEMQRWITHDELIVERYDATE, true);
			SMS_MAP.put(ADMIN_MULTIITEMQRWITHOUTDELIVERYDATE, true);
			SMS_MAP.put(ADMIN_QUOTEUPDATESELLERTOADMIN, true);
			SMS_MAP.put(ADMIN_REQUESTFORCALLBACK, true);
			// TeleAssociates
			SMS_MAP.put(TELEASSOCIATE_SINGLEITEMORDER_PLACED, true);
			SMS_MAP.put(TELEASSOCIATE_MULTIITEMORDER_PLACED, true);
			SMS_MAP.put(TELEASSOCIATE_SINGLEITEM_CANCELLED, true);
			SMS_MAP.put(TELEASSOCIATE_MULTIITEM_CANCELLED, true);
			SMS_MAP.put(TELEASSOCIATE_SINGLEITEM_RETURNED, true);
			SMS_MAP.put(TELEASSOCIATE_MULTIITEM_RETURNED, true);

			// TechAssociates
			SMS_MAP.put(TECHASSOCIATE_SINGLEITEMORDER_PLACED, true);
			SMS_MAP.put(TECHASSOCIATE_MULTIITEMORDER_PLACED, true);
			SMS_MAP.put(TECHASSOCIATE_SINGLEITEM_CANCELLED, true);
			SMS_MAP.put(TECHASSOCIATE_MULTIITEM_CANCELLED, true);
			SMS_MAP.put(TECHASSOCIATE_SINGLEITEM_RETURNED, true);
			SMS_MAP.put(TECHASSOCIATE_MULTIITEM_RETURNED, true);

			// Provider
			SMS_MAP.put(PROVIDER_REGISTRATION, true);
			SMS_MAP.put(PROVIDER_WELCOME, true);
			SMS_MAP.put(PROVIDER_PRODUCTAPPROVED, true);

			// Dealer
			SMS_MAP.put(DEALER_REGISTRATION, true);
			SMS_MAP.put(DEALER_WELCOME, true);
			SMS_MAP.put(DEALER_PRODUCTAPPROVED, true);
			SMS_MAP.put(DEALER_SINGLEITEM_CONFIRMED, true);
			SMS_MAP.put(DEALER_MULTIITEM_CONFIRMED, true);
			SMS_MAP.put(DEALER_ITEM_DISPATCHED, true);
			SMS_MAP.put(DEALER_SINGLEITEM_RETURNED, true);
			SMS_MAP.put(DEALER_MULTIITEM_RETURNED, true);
			SMS_MAP.put(DEALER_SINGLEITEM_CANCELLED, true);
			SMS_MAP.put(DEALER_MULTIITEM_CANCELLED, true);
			SMS_MAP.put(DEALER_SENDREQUIREMENT, true);
			SMS_MAP.put(DEALER_QUOTEREQUESTWITHFEWITEMS, true);
			SMS_MAP.put(DEALER_QUOTEREQUESTWITHMOREITEMS, true);
			// Architect
			SMS_MAP.put(ARCHITECT_REGISTRATION, true);
			SMS_MAP.put(ARCHITECT_WELCOME, true);
			SMS_MAP.put(ARCHITECT_POSTEDPROJECTINFO, true);

			// InteriorDesigner
			SMS_MAP.put(INTERIORDESIGNER_REGISTRATION, true);
			SMS_MAP.put(INTERIORDESIGNER_WELCOME, true);
			SMS_MAP.put(INTERIORDESIGNER_POSTEDPROJECTINFO, true);

			// Enduser
			SMS_MAP.put(ENDUSER_REGISTRATION, true);
			SMS_MAP.put(ENDUSER_WELCOME, true);
			SMS_MAP.put(ENDUSER_PLACEDSINGLEITEMORDERTHROUGHNEFT, true);
			SMS_MAP.put(ENDUSER_PLACEDMULTIITEMORDERTHROUGHNEFT, true);
			SMS_MAP.put(ENDUSER_ORDER_PLACED, true);
			SMS_MAP.put(ENDUSER_SINGLEITEM_PACKED, true);
			SMS_MAP.put(ENDUSER_MULTIITEM_PACKED, true);
			SMS_MAP.put(ENDUSER_SINGLEITEM_SELFPICKUP_PACKED, true);
			SMS_MAP.put(ENDUSER_MULTIITEM_SELFPICKUP_PACKED, true);
			SMS_MAP.put(ENDUSER_SINGLEITEM_CONFIRMED, true);
			SMS_MAP.put(ENDUSER_MULTIITEM_CONFIRMED, true);
			SMS_MAP.put(ENDUSER_SINGLEITEM_DISPATCHED, true);
			SMS_MAP.put(ENDUSER_MULTIITEM_DISPATCHED, true);
			SMS_MAP.put(ENDUSER_SINGLEITEM_DELIVERED, true);
			SMS_MAP.put(ENDUSER_MULTIITEM_DELIVERED, true);
			SMS_MAP.put(ENDUSER_SINGLEITEM_UNCONFIRMED, true);
			SMS_MAP.put(ENDUSER_MULTIITEM_UNCONFIRMED, true);
			SMS_MAP.put(ENDUSER_SINGLEITEM_RETURNED, true);
			SMS_MAP.put(ENDUSER_MULTIITEM_RETURNED, true);
			SMS_MAP.put(ENDUSER_SINGLEITEM_CANCELLED, true);
			SMS_MAP.put(ENDUSER_MULTIITEM_CANCELLED, true);
			SMS_MAP.put(ENDUSER_BIDDONEINFO, true);
			SMS_MAP.put(ADMIN_UPDATEEMAILANDMOBILE, true);
			SMS_MAP.put(ADMIN_UPDATEEMAIL, true);
			SMS_MAP.put(ADMIN_UPDATEMOBILE, true);
			SMS_MAP.put(ENDUSER_UPDATEEMAILANDMOBILE, true);
			SMS_MAP.put(ENDUSER_UPDATEEMAIL, true);
			SMS_MAP.put(ENDUSER_UPDATEMOBILE, true);
			SMS_MAP.put(ENDUSER_SERVICEPROVIDERINFOTOENDUSER, true);
			SMS_MAP.put(ENDUSER_ENQUIRYFORMCONFIRMATION, true);
			SMS_MAP.put(ENDUSER_SNAPSHOTORDERCONFIRMATION, true);
			SMS_MAP.put(ENDUSER_SHAREQUOTEWITHCUSTOMER, true);
			SMS_MAP.put(ENDUSER_SHAREQUOTESMSWITHCUSTOMER, true);
			// SMS_MAP.put(ENDUSER_GETQUOTECONFIRMATION, true);
			SMS_MAP.put(ENDUSER_QUOTEREQUESTCONFIRMATION, true);
			SMS_MAP.put(ENDUSER_ORDERAMOUNTUPDATE, true);
			SMS_MAP.put(ENDUSER_ORDERAMOUNTUPDATEWITHZEROOUTSTADNING, true);
			SMS_MAP.put(ENDUSER_CHEQUECOLLECTEDDATEINFO, true);

			// BusinessAsociate
			SMS_MAP.put(BUSINESSASSOCIATE_REGISTRATION, true);
			SMS_MAP.put(BUSINESSASSOCIATE_WELCOME, true);

			// CivilEngineer
			SMS_MAP.put(CIVILENGINEER_REGISTRATION, true);
			SMS_MAP.put(CIVILENGINEER_WELCOME, true);

			// ServiceProvider
			SMS_MAP.put(SERVICEPROVIDER_REGISTRATION, true);
			SMS_MAP.put(SERVICEPROVIDER_WELCOME, true);
			SMS_MAP.put(ENDUSERDETAILSTOSERVICEPROVIDER, true);
			SMS_MAP.put(ENDUSERDETAILSTOSERVICEPROVIDERWITHDESC, true);

			// for all user
			SMS_MAP.put(FORGETPASSWORDOTP, true);
			SMS_MAP.put(USER_CREDENTIALS, true);
			SMS_MAP.put(USER_RESETPASSWORD, true);
			SMS_MAP.put(USER_AMOUNTUPDATE, true);
		}
		return SMS_MAP;
	}
}
