/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

import java.util.Date;
import java.util.List;

/**
 * @author Shivu
 *
 */
public class CustomerOrdersSummaryResponse {

	private int orderId;
	private String buildOnnOrderId;
	private double orderAmt;
	private double paymentDoneAmt;
	private double outstandingAmt;
	private Date orderCreatedDate;
	private String note;
	private Date paymentDoneDate;
	private List<PaymentDetailsResponse> paymentInfo;
	private boolean paymentDone;

	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the buildOnnOrderId
	 */
	public String getBuildOnnOrderId() {
		return buildOnnOrderId;
	}

	/**
	 * @param buildOnnOrderId
	 *            the buildOnnOrderId to set
	 */
	public void setBuildOnnOrderId(String buildOnnOrderId) {
		this.buildOnnOrderId = buildOnnOrderId;
	}

	/**
	 * @return the orderAmt
	 */
	public double getOrderAmt() {
		return orderAmt;
	}

	/**
	 * @param orderAmt
	 *            the orderAmt to set
	 */
	public void setOrderAmt(double orderAmt) {
		this.orderAmt = orderAmt;
	}

	/**
	 * @return the paymentDoneAmt
	 */
	public double getPaymentDoneAmt() {
		return paymentDoneAmt;
	}

	/**
	 * @param paymentDoneAmt
	 *            the paymentDoneAmt to set
	 */
	public void setPaymentDoneAmt(double paymentDoneAmt) {
		this.paymentDoneAmt = paymentDoneAmt;
	}

	/**
	 * @return the outstandingAmt
	 */
	public double getOutstandingAmt() {
		return outstandingAmt;
	}

	/**
	 * @param outstandingAmt
	 *            the outstandingAmt to set
	 */
	public void setOutstandingAmt(double outstandingAmt) {
		this.outstandingAmt = outstandingAmt;
	}

	/**
	 * @return the orderCreatedDate
	 */
	public Date getOrderCreatedDate() {
		return orderCreatedDate;
	}

	/**
	 * @param orderCreatedDate
	 *            the orderCreatedDate to set
	 */
	public void setOrderCreatedDate(Date orderCreatedDate) {
		this.orderCreatedDate = orderCreatedDate;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the paymentDoneDate
	 */
	public Date getPaymentDoneDate() {
		return paymentDoneDate;
	}

	/**
	 * @param paymentDoneDate
	 *            the paymentDoneDate to set
	 */
	public void setPaymentDoneDate(Date paymentDoneDate) {
		this.paymentDoneDate = paymentDoneDate;
	}

	/**
	 * @return the paymentInfo
	 */
	public List<PaymentDetailsResponse> getPaymentInfo() {
		return paymentInfo;
	}

	/**
	 * @param paymentInfo
	 *            the paymentInfo to set
	 */
	public void setPaymentInfo(List<PaymentDetailsResponse> paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	/**
	 * @return the paymentDone
	 */
	public boolean isPaymentDone() {
		return paymentDone;
	}

	/**
	 * @param paymentDone
	 *            the paymentDone to set
	 */
	public void setPaymentDone(boolean paymentDone) {
		this.paymentDone = paymentDone;
	}
}
