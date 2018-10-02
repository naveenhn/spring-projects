/**
 * 
 */
package com.sarvah.mbg.rest.catalog.model;

import java.util.Set;

/**
 * @author shivu
 *
 */
public class StorePromotionUpdateRequestParam {
	
	private String pName;

	private String desc;

	private String pType;

	private String discount;

	private Set<String> proIds;

	private String sDate;

	private String eDate;

	/**
	 * @return the pName
	 */
	public String getpName() {
		return pName;
	}

	/**
	 * @param pName the pName to set
	 */
	public void setpName(String pName) {
		this.pName = pName;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the pType
	 */
	public String getpType() {
		return pType;
	}

	/**
	 * @param pType the pType to set
	 */
	public void setpType(String pType) {
		this.pType = pType;
	}

	/**
	 * @return the discount
	 */
	public String getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(String discount) {
		this.discount = discount;
	}

	/**
	 * @return the proIds
	 */
	public Set<String> getProIds() {
		return proIds;
	}

	/**
	 * @param proIds the proIds to set
	 */
	public void setProIds(Set<String> proIds) {
		this.proIds = proIds;
	}

	/**
	 * @return the sDate
	 */
	public String getsDate() {
		return sDate;
	}

	/**
	 * @param sDate the sDate to set
	 */
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}

	/**
	 * @return the eDate
	 */
	public String geteDate() {
		return eDate;
	}

	/**
	 * @param eDate the eDate to set
	 */
	public void seteDate(String eDate) {
		this.eDate = eDate;
	}
	
	

}
