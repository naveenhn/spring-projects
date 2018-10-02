/**
 * 
 */
package com.sarvah.mbg.rest.catalog.response;

import java.util.List;

import com.sarvah.mbg.catalog.service.model.ProductSummaryView;
import com.sarvah.mbg.rest.model.AbstractCollectionResponse;

/**
 * @author shivu s
 *
 */
public class ManageProductResponse extends AbstractCollectionResponse {
	private List<ProductSummaryView> products;

	/**
	 * @return the products
	 */
	public List<ProductSummaryView> getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(List<ProductSummaryView> products) {
		this.products = products;
	}
}
