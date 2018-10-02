/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import java.util.List;

/**
 * @author shivu s
 *
 */
public class PromotionSummaryViewResponse {
	private int totalPages;
	private long totalElements;
	private int number;
	private int size;
	private List<PromotionSummaryView> promotionSummaryView;

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the promotionSummaryView
	 */
	public List<PromotionSummaryView> getPromotionSummaryView() {
		return promotionSummaryView;
	}

	/**
	 * @param promotionSummaryView
	 *            the promotionSummaryView to set
	 */
	public void setPromotionSummaryView(
			List<PromotionSummaryView> promotionSummaryView) {
		this.promotionSummaryView = promotionSummaryView;
	}
}
