/**
 * 
 */
package com.sarvah.mbg.rest.order.response;

import java.util.List;

import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;

/**
 * @author shivu
 *
 */
public class OrderAddressResponse {
	private List<OrderAddress> addresses;

	/**
	 * @return the addresses
	 */
	public List<OrderAddress> getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses
	 *            the addresses to set
	 */
	public void setAddresses(List<OrderAddress> addresses) {
		this.addresses = addresses;
	}
}