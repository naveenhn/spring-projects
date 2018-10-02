package com.sarvah.mbg.rest.order.response;

import java.util.List;

import com.sarvah.mbg.rest.model.AbstractCollectionResponse;
import com.sarvah.mbg.userprofile.response.OrderManageView;

public class ManageOrderResponse extends AbstractCollectionResponse {
	private List<OrderManageView> orderManageView;

	public List<OrderManageView> getOrderManageView() {
		return orderManageView;
	}

	public void setOrderManageView(List<OrderManageView> orderManageView) {
		this.orderManageView = orderManageView;
	}

}
