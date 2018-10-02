/**
 * 
 */
package com.sarvah.mbg.domain.ordermgmt;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sarvah.mbg.domain.mongo.common.AbstractDocument;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author Shivu
 *
 */
@Document
public class OrderStatement extends AbstractDocument {
	private String date;
	private String statementId;
	private double orderAmt;
	private double paymentDoneAmt;
	private double outstandingAmt;
	@NotNull
	@DBRef
	private User user;

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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
