/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

/**
 * @author shivu s
 *
 */
public class SearchKeywordResponse {
	private String keyword;
	private long count;

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(long count) {
		this.count = count;
	}
}
