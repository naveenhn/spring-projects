/**
 * 
 */
package com.sarvah.mbg.domain.ordermgmt.shipping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author naveen
 *
 */
@Entity
@Table(name = "shipping_type", schema = "mbgdb")
public class ShippingType {

	@Id
	@Column(name = "shipping_type_id")
	private int shippingTypeId;

	@Column(name = "shipping_type")
	private String shippingType;

	/**
	 * @return the shippingTypeId
	 */
	public int getShippingTypeId() {
		return shippingTypeId;
	}

	/**
	 * @param shippingTypeId
	 *            the shippingTypeId to set
	 */
	public void setShippingTypeId(int shippingTypeId) {
		this.shippingTypeId = shippingTypeId;
	}

	/**
	 * @return the shippingType
	 */
	public String getShippingType() {
		return shippingType;
	}

	/**
	 * @param shippingType
	 *            the shippingType to set
	 */
	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

}
