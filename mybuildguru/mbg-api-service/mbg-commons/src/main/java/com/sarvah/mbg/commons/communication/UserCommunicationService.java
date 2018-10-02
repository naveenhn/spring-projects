package com.sarvah.mbg.commons.communication;

import java.util.List;
import java.util.Set;

import javax.jms.JMSException;

import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.sms.ItemInfoForCommunication;

public interface UserCommunicationService {
	void sendRegistrationInfo(User user, String token) throws UserCommException;

	void sendVerifyAndWecomeInfo(User user) throws UserCommException;

	void sendOTPInfo(String phonenumber, String otp, String email,
			String userFullName) throws UserCommException;

	void sendResetPasswordInfo(String fullName, String emailId,
			String mobileNumber) throws UserCommException;

	void sendUserUpdateOTPInfo(String phonenumber, String otp, String email,
			String userFullName) throws UserCommException;

	void sendPlacedOrderInfoToCustomer(Set<User> userSet, String orderId,
			Integer itemCount, String itemName, String ItemQuantity,
			String orderAmount, String customerName, String customerZipcode,
			String totalSavings, String taxAmt,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException;

	void sendPlacedOrderInfoToAdmin(User user, String orderId,
			Integer itemCount, String itemName, String ItemQuantity,
			String orderAmount, String customerName, String customerZipcode,
			String totalSavings, String taxAmt,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException;

	void sendPlacedOrderInfoToDealers(Set<User> userSet, String orderId,
			Integer itemCount, String itemName, String ItemQuantity,
			String orderAmount, String customerName, String customerZipcode,
			String shippingType,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException;

	void sendItemPackedInfo(User user, String orderId, String itemName,
			Integer itemQuantity, String orderAmount, String deliveryBy,
			List<ItemInfoForCommunication> itemInfoForCommunicationList,
			int itemCount) throws UserCommException;

	void sendItemPackedInforSelfPickup(User user, String orderId,
			String itemName, Integer itemQuantity, String orderAmount,
			String deliveryBy, String storeName, String dealerAddress,
			String dealerContectNumber,
			List<ItemInfoForCommunication> itemInfoForCommunicationList,
			int itemCount) throws UserCommException;

	void sendCancelOrderInfoToCustomer(Set<User> user, String orderId,
			Integer itemCount, String itemName, Integer ItemQuantity,
			String orderAmount, String customerName, Integer customerZipcode,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException;

	void sendCancelOrderInfoToAdmin(User user, String orderId,
			Integer itemCount, String itemName, Integer ItemQuantity,
			String orderAmount, String customerName, Integer customerZipcode,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException;

	void sendCancelOrderInfoToTeleAssociate(List<User> user, String orderId,
			Integer itemCount, String itemName, Integer ItemQuantity,
			String orderAmount, String customerName, Integer customerZipcode,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException;

	void sendCancelOrderInfoToTechAssociate(List<User> user, String orderId,
			Integer itemCount, String itemName, Integer ItemQuantity,
			String orderAmount, String customerName, Integer customerZipcode,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException;

	void sendCancelOrderInfoToDealers(Set<User> user, String orderId,
			Integer itemCount, String itemName, Integer ItemQuantity,
			String orderAmount, String customerName, Integer customerZipcode,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException;

	void sendItemCancelledInfo(Set<User> user, String orderId, String itemName,
			Integer ItemQuantity, String orderAmount, String customerName,
			Integer customerZipcode,
			ItemInfoForCommunication itemInfoForCommunication)
			throws UserCommException;

	void sendItemCancelledInfo(Set<User> user, String orderId, String itemName,
			Integer ItemQuantity, String orderAmount, String customerName,
			Integer customerZipcode,
			List<ItemInfoForCommunication> itemInfoForCommunicationList,
			int itemCount) throws UserCommException;

	void sendItemConfirmedInfo(Set<User> user, String orderId, String itemName,
			Integer itemQuantity, String orderAmount,
			Integer minDeliveryTimeInDays, Integer maxDeliveryTimeInDays,
			String customerName, Integer customerZipcode, String deliveryBy,
			List<ItemInfoForCommunication> itemInfoForCommunicationList,
			int itemCount, String address) throws UserCommException;

	void sendItemUnConfirmedInfo(Set<User> user, String orderId,
			String itemName, Integer itemQuantity, String orderAmount,
			String deliveryBy,
			List<ItemInfoForCommunication> itemInfoForCommunicationList,
			int itemCount) throws UserCommException;

	void sendItemDispatchedInfo(Set<User> user, String orderId,
			String itemName, Integer itemQuantity, String dispatchId,
			String orderAmount, String deliveryBy,
			List<ItemInfoForCommunication> itemInfoForCommunicationList,
			int itemCount) throws UserCommException;

	void sendItemDeliveredInfo(Set<User> user, String orderId, String itemName,
			Integer itemQuantity, String deliveryBy,
			List<ItemInfoForCommunication> itemInfoForCommunicationList,
			int itemCount) throws UserCommException;

	void sendItemReturnedInfo(Set<User> user, String orderId, String itemName,
			Integer itemQuantity, String customerName, Integer customerZipcode,
			String orderAmount,
			List<ItemInfoForCommunication> itemInfoForCommunicationList,
			int itemCount) throws UserCommException;

	void sendBidDoneInfo(User endUser, String architectOrInteriorName,
			String architectOrInteriorRole, Integer architectOrInteriorZipcode,
			String architectOrInteriorContactNo) throws UserCommException;

	void sendProductApprovedInfo(User user, String productName)
			throws UserCommException;

	void sendBankDetailsForFundTransfer(Set<User> userSet, String orderId,
			Integer itemCount, String itemName, String itemQuantity,
			String itemQuantityType, String orderAmount, String customerName,
			String customerZipcode, String totalSavings, String taxAmt,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException;

	void sendProjectPostedInfo(Set<User> userSet, String customerName,
			Integer customerZipcode, String projectType, String projectBudget,
			String constructionType) throws UserCommException;

	void sendPlacedOrderInfoToTeleAssociate(List<User> user, String orderId,
			Integer itemCount, String itemName, String ItemQuantity,
			String orderAmount, String customerName, String customerZipcode,
			String totalSavings,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException;

	void sendPlacedOrderInfoToTechAssociate(List<User> user, String orderId,
			Integer itemCount, String itemName, String ItemQuantity,
			String orderAmount, String customerName, String customerZipcode,
			String totalSavings,
			List<ItemInfoForCommunication> itemInfoForCommunicationList)
			throws UserCommException;

	void sendInstantOrderConfirmtion(String customerName, String email,
			String mobileNumber) throws UserCommException;

	void sendInstantOrderInfo(User user, String mobile, String email,
			String customerName, String textInfo, String deliveryTime,
			String imageUrl) throws UserCommException;

	void sendProductEnquiryInfo(User user, String customerName, String mobile,
			String email, String textInfo, String deliveryTime)
			throws UserCommException;

	void sendProductEnquiryFormConfirmation(String customerName, String email,
			String mobileNumber) throws UserCommException;

	void sendQuoteConfirmation(String customerName, String email,
			String mobile, String orderId) throws UserCommException;

	void sendRequestQuoteInfo(User user, String customerName, String mobile,
			String email, String zipcode, String paymentMode, String textInfo,
			String creditDays, String deliveryTime,
			List<ItemInfoForCommunication> itemInfoList, String brand,
			String changeAttribute) throws UserCommException;

	void sendOrderInvoiceForDealer(User user, String orderId, String url)
			throws UserCommException;

	void sendOrderInvoiceForEnduser(User user, String orderId, String url)
			throws UserCommException;

	void sendResendVerificationTokenInfo(User mongoUser,
			String base64EncodedToken) throws UserCommException;

	void sendForgotPasswordInfo(User mongoUser, String base64EncodedToken);

	void sendContactInfo(String name, String email, String mobile,
			String zipcode, String description, String toEmail,
			String toPhoneNumber, String roleName, String constructionType,
			String asSoonRequired, String city) throws UserCommException;

	void sendServiceProviderInfoForEnduser(String spFullName, String email,
			String mobile, String zipcode, String roleName, String toEmail,
			String toMobile) throws UserCommException;

	void sendUserCredentials(String userName, String mobileNumber,
			String password) throws JMSException;

	void sendNewRegistrationAlert(User user) throws JMSException;

	void sendPromoSms(String promoInfo, String mobileNumbers)
			throws JMSException;

	void shareQuoteWithCustomer(User customer, String quoteRequestId,
			double orderAmount, List<ItemInfoForCommunication> products)
			throws UserCommException;

	void sendQuoteRequestConfirmationToCustomer(User customer,
			String quoteRequestId, String zipcode, String deliveryDate,
			List<ItemInfoForCommunication> products) throws UserCommException;

	void sendQuoteRequestInfoToAdmin(User admin, User customer,
			String quoteRequestId, String zipcode, String deliveryDate,
			List<ItemInfoForCommunication> products) throws UserCommException;

	void sendQuoteRequestToDealer(Set<User> dealers, String quoteRequestId,
			String itemNameAndQuantityTypeList, int itemCount,
			String customerZipcode) throws UserCommException;

	void sendOrderAmountUpdateInfoToCustomer(User customer, String updatingAmt,
			double outStandingAmt) throws UserCommException;

	void sendOrderAmountUpdateWithZeroOutstanding(User customer,
			String updatingAmt, double outStandingAmt) throws UserCommException;

	void sendChequeCollectedInfoToCustomer(User customer, String bankName,
			String chequeNumber, String chequeDate, String chequeAmount)
			throws UserCommException;

	void sendUserAmountUpdateInfo(User user, String updatingAmt,
			double availableAmtf) throws UserCommException;

	void sendQuoteUpdateSellerToAdmin(User dealer, String sellerName,
			int itemCount, double totalQuoteAmount) throws UserCommException;

	void sendRequestForCallBack(String name, String mobileNum,
			String requirement) throws UserCommException;
}