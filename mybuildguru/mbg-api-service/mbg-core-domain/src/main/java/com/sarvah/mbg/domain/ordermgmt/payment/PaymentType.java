/**
 * 
 */
package com.sarvah.mbg.domain.ordermgmt.payment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author naveen
 *
 */
@Entity
@Table(name = "payment_type", schema = "mbgdb")
public class PaymentType {

	@Id
	@Column(name = "payment_type_id")
	private int paymentTypeId;

	@Column(name = "payment_type")
	private String paymentType;

	/**
	 * @return the paymentTypeId
	 */
	public int getPaymentTypeId() {
		return paymentTypeId;
	}

	/**
	 * @param paymentTypeId
	 *            the paymentTypeId to set
	 */
	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType
	 *            the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

}
