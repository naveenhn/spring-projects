/**
 * 
 */
package com.sarvah.mbg.rest.order.response;

import java.util.List;

import com.sarvah.mbg.rest.model.AbstractCollectionResponse;
import com.sarvah.mbg.userprofile.response.GetUserOrderResponse;

/**
 * @author shivu
 *
 */
public class OrderResponse extends AbstractCollectionResponse {

	private List<GetUserOrderResponse> orders;

	/**
	 * @return the orders
	 */
	public List<GetUserOrderResponse> getOrders() {
		return orders;
	}

	/**
	 * @param orders the orders to set
	 */
	public void setOrders(List<GetUserOrderResponse> orders) {
		this.orders = orders;
	}

}
