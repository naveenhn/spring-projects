/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

import java.util.List;

/**
 * @author shivu
 *
 */
public class ManageOrderResponse {
	private int totalPages;
	private long totalElements;
	private int number;
	private int size;
	private List<OrderManageView> orderManageViewList;
	private OrderSummaryResponse orderSummary;

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

	public List<OrderManageView> getOrderManageViewList() {
		return orderManageViewList;
	}

	public void setOrderManageViewList(List<OrderManageView> orderManageViewList) {
		this.orderManageViewList = orderManageViewList;
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
