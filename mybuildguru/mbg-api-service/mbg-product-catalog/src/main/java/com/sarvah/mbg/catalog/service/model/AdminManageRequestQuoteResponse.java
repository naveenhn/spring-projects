/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import java.util.List;

/**
 * @author Shivu
 *
 */
public class AdminManageRequestQuoteResponse {
	private int totalPages;
	private long totalElements;
	private int number;
	private int size;
	private List<AdminManageRequestQuote> requestQuotes;

	/**
	 * @return the totalPages
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * @param totalPages
	 *            the totalPages to set
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return the totalElements
	 */
	public long getTotalElements() {
		return totalElements;
	}

	/**
	 * @param totalElements
	 *            the totalElements to set
	 */
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the requestQuotes
	 */
	public List<AdminManageRequestQuote> getRequestQuotes() {
		return requestQuotes;
	}

	/**
	 * @param requestQuotes
	 *            the requestQuotes to set
	 */
	public void setRequestQuotes(List<AdminManageRequestQuote> requestQuotes) {
		this.requestQuotes = requestQuotes;
	}
}
