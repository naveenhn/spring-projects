/**
 * 
 */
package com.sarvah.mbg.rest.order.response;

import java.util.List;

import com.sarvah.mbg.ordermgmt.response.GetOrderItemsResponseParent;

/**
 * @author shivu
 *
 */
public class OrderDetailsResponse {
	List<GetOrderItemsResponseParent> items;

	/**
	 * @return the items
	 */
	public List<GetOrderItemsResponseParent> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<GetOrderItemsResponseParent> items) {
		this.items = items;
	}
}