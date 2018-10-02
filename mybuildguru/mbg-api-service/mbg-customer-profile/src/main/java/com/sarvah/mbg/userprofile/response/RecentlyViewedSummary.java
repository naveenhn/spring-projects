/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

import java.util.List;

/**
 * @author Shivu
 *
 */
public class RecentlyViewedSummary {
	private int totalPages;
	private long totalElements;
	private int number;
	private int size;
	private List<RecentlyViewedDetails> RecentlyViewedDetails;
	/**
	 * @return the totalPages
	 */
	public int getTotalPages() {
		return totalPages;
	}
	/**
	 * @param totalPages the totalPages to set
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
	 * @param totalElements the totalElements to set
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
	 * @param number the number to set
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
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	/**
	 * @return the recentlyViewedDetails
	 */
	public List<RecentlyViewedDetails> getRecentlyViewedDetails() {
		return RecentlyViewedDetails;
	}
	/**
	 * @param recentlyViewedDetails the recentlyViewedDetails to set
	 */
	public void setRecentlyViewedDetails(
			List<RecentlyViewedDetails> recentlyViewedDetails) {
		RecentlyViewedDetails = recentlyViewedDetails;
	}
	
}
