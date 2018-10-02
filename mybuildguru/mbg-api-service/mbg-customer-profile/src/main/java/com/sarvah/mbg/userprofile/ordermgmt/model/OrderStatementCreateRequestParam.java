/**
 * 
 */
package com.sarvah.mbg.userprofile.ordermgmt.model;

/**
 * @author Shivu
 *
 */
public class OrderStatementCreateRequestParam {
	private String date;
	private String statementId;
	private double orderAmt;
	private double paymentDoneAmt;
	private double outstandingAmt;
	private String userId;

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the statementId
	 */
	public String getStatementId() {
		return statementId;
	}

	/**
	 * @param statementId
	 *            the statementId to set
	 */
	public void setStatementId(String statementId) {
		this.statementId = statementId;
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
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
