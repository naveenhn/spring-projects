/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.model;

/**
 * @author shivu
 *
 */
public class BidCreateRequestParam {
	private String desc;

	private String quoteAmt;

	private String assetId;

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the quoteAmt
	 */
	public String getQuoteAmt() {
		return quoteAmt;
	}

	/**
	 * @param quoteAmt
	 *            the quoteAmt to set
	 */
	public void setQuoteAmt(String quoteAmt) {
		this.quoteAmt = quoteAmt;
	}

	/**
	 * @return the assetId
	 */
	public String getAssetId() {
		return assetId;
	}

	/**
	 * @param assetId
	 *            the assetId to set
	 */
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

}
