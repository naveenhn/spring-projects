/**
 * 
 */
package com.sarvah.mbg.rest.catalog.response;

import java.util.List;

import com.sarvah.mbg.catalog.service.model.PromotionSummaryView;
import com.sarvah.mbg.rest.model.AbstractCollectionResponse;

/**
 * @author shivu
 *
 */
public class ManagePromotionResponse extends AbstractCollectionResponse {
	private List<PromotionSummaryView> promotions;

	/**
	 * @return the promotions
	 */
	public List<PromotionSummaryView> getPromotions() {
		return promotions;
	}

	/**
	 * @param promotions
	 *            the promotions to set
	 */
	public void setPromotions(List<PromotionSummaryView> promotions) {
		this.promotions = promotions;
	}
}
