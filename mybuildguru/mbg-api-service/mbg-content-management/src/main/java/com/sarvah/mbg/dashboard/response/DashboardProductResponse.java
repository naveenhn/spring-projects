package com.sarvah.mbg.dashboard.response;

import com.sarvah.mbg.domain.mongo.catalog.Product;

public class DashboardProductResponse {

	private Product product;
	private long count;

	// private long count;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
