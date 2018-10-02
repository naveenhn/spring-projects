/**
 * 
 */
package com.sarvah.mbg.rest.order.response;

import java.util.List;

import com.sarvah.mbg.rest.model.AbstractCollectionResponse;
import com.sarvah.mbg.userprofile.response.OrderManageView;
import com.sarvah.mbg.userprofile.response.OrderSummaryResponse;

/**
 * @author shivu
 *
 */
public class UserOrderResponse extends AbstractCollectionResponse {
	private List<OrderManageView> orders;
	private OrderSummaryResponse orderSummary;

	/**
	 * @return the orders
	 */
	public List<OrderManageView> getOrders() {
		return orders;
	}

	/**
	 * @param orders
	 *            the orders to set
	 */
	public void setOrders(List<OrderManageView> orders) {
		this.orders = orders;
	}

	/**
	 * @return the orderSummary
	 */
	public OrderSummaryResponse getOrderSummary() {
		return orderSummary;
	}

	/**
	 * @param orderSummary
	 *            the orderSummary to set
	 */
	public void setOrderSummary(OrderSummaryResponse orderSummary) {
		this.orderSummary = orderSummary;
	}
}
