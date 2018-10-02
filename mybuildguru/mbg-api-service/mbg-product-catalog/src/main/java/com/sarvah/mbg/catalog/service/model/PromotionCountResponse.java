package com.sarvah.mbg.catalog.service.model;

/**
 * 
 * @author Raju
 *
 */

public class PromotionCountResponse {
	private Long promoCount;
	private long activePromoCount;
	private long inactivePromoCount;

	public Long getPromoCount() {
		return promoCount;
	}

	public void setPromoCount(Long promoCount) {
		this.promoCount = promoCount;
	}

	public long getActivePromoCount() {
		return activePromoCount;
	}

	public void setActivePromoCount(long activePromoCount) {
		this.activePromoCount = activePromoCount;
	}

	public long getInactivePromoCount() {
		return inactivePromoCount;
	}

	public void setInactivePromoCount(long inactivePromoCount) {
		this.inactivePromoCount = inactivePromoCount;
	}

}
