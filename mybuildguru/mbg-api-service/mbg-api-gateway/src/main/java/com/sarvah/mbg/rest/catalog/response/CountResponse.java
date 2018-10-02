/**
 * 
 */
package com.sarvah.mbg.rest.catalog.response;

/**
 * @author shivu s
 *
 */
public class CountResponse {
	private long mbgProductCount;
	private long linkedProductCount;
	/**
	 * @return the mbgProductCount
	 */
	public long getMbgProductCount() {
		return mbgProductCount;
	}
	/**
	 * @param mbgProductCount the mbgProductCount to set
	 */
	public void setMbgProductCount(long mbgProductCount) {
		this.mbgProductCount = mbgProductCount;
	}
	/**
	 * @return the linkedProductCount
	 */
	public long getLinkedProductCount() {
		return linkedProductCount;
	}
	/**
	 * @param linkedProductCount the linkedProductCount to set
	 */
	public void setLinkedProductCount(long linkedProductCount) {
		this.linkedProductCount = linkedProductCount;
	}
}
