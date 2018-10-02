/**
 * 
 */
package com.sarvah.mbg.rest.catalog.response;

import java.util.List;

import com.sarvah.mbg.catalog.service.model.ProductPricingSummaryView;
import com.sarvah.mbg.rest.model.AbstractCollectionResponse;

/**
 * @author Shivu
 *
 */
public class ManageProductPricingResponse extends AbstractCollectionResponse {
	private List<ProductPricingSummaryView> productPrices;

	/**
	 * @return the productPrices
	 */
	public List<ProductPricingSummaryView> getProductPrices() {
		return productPrices;
	}

	/**
	 * @param productPrices
	 *            the productPrices to set
	 */
	public void setProductPrices(List<ProductPricingSummaryView> productPrices) {
		this.productPrices = productPrices;
	}
}
