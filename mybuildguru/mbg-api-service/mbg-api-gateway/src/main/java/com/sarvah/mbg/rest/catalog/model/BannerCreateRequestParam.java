/**
 * 
 */
package com.sarvah.mbg.rest.catalog.model;

import java.util.List;

/**
 * @author shivu s
 *
 */
public class BannerCreateRequestParam {

	private String name;
	private String place;
	private String startDate;
	private String endDate;
	private List<String> proIds;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @param place
	 *            the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * @return the staetDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param staetDate
	 *            the staetDate to set
	 */
	public void setStartDate(String staetDate) {
		this.startDate = staetDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<String> getProIds() {
		return proIds;
	}

	public void setProIds(List<String> proIds) {
		this.proIds = proIds;
	}

}
