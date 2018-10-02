package com.sarvah.mbg.userprofile.ordermgmt.model;

/**
 * 
 * @author Raju
 *
 */
public class OrderStatusUpdateRequestParam {
	private String paymentMethodName;
	private String status;
	private String paymentNumber;
	private String paymentStatus;
	private String paymentMetadata;
	private String paymentAmount;
	private String paymentDetailsId;
	private String chequeDate;
	private String bankName;
	private String chequeAmount;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the paymentNumber
	 */
	public String getPaymentNumber() {
		return paymentNumber;
	}

	/**
	 * @param paymentNumber
	 *            the paymentNumber to set
	 */
	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	/**
	 * @return the paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus
	 *            the paymentStatus to set
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * @return the paymentMetadata
	 */
	public String getPaymentMetadata() {
		return paymentMetadata;
	}

	/**
	 * @param paymentMetadata
	 *            the paymentMetadata to set
	 */
	public void setPaymentMetadata(String paymentMetadata) {
		this.paymentMetadata = paymentMetadata;
	}

	/**
	 * @return the paymentMethodName
	 */
	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	/**
	 * @param paymentMethodName
	 *            the paymentMethodName to set
	 */
	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	/**
	 * @return the paymentAmount
	 */
	public String getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * @param paymentAmount
	 *            the paymentAmount to set
	 */
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	/**
	 * @return the paymentDetailsId
	 */
	public String getPaymentDetailsId() {
		return paymentDetailsId;
	}

	/**
	 * @param paymentDetailsId
	 *            the paymentDetailsId to set
	 */
	public void setPaymentDetailsId(String paymentDetailsId) {
		this.paymentDetailsId = paymentDetailsId;
	}

	/**
	 * @return the chequeDate
	 */
	public String getChequeDate() {
		return chequeDate;
	}

	/**
	 * @param chequeDate
	 *            the chequeDate to set
	 */
	public void setChequeDate(String chequeDate) {
		this.chequeDate = chequeDate;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the chequeAmount
	 */
	public String getChequeAmount() {
		return chequeAmount;
	}

	/**
	 * @param chequeAmount
	 *            the chequeAmount to set
	 */
	public void setChequeAmount(String chequeAmount) {
		this.chequeAmount = chequeAmount;
	}
}
