/**
 * 
 */
package com.sarvah.mbg.domain.mongo.common.contact;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sarvah.mbg.domain.mongo.common.AbstractDocument;

/**
 * @author naveen
 *
 */
@Document
public class Pincodes extends AbstractDocument {

	private String countrycode;
	@NotNull
	private Integer postalcode;
	private String placename;
	private String adminname1;
	private Integer admincode1;
	private String adminname2;
	private Integer admincode2;
	private String adminname3;
	private Integer admincode3;
	private Double latitude;
	private Double longitude;
	private Integer accuracy;
	
	/**
	 * @return the countrycode
	 */
	public String getCountrycode() {
		return countrycode;
	}
	
	/**
	 * @param countrycode
	 *            the countrycode to set
	 */
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
	
	/**
	 * @return the postalcode
	 */
	public Integer getPostalcode() {
		return postalcode;
	}
	
	/**
	 * @param postalcode
	 *            the postalcode to set
	 */
	public void setPostalcode(Integer postalcode) {
		this.postalcode = postalcode;
	}
	
	/**
	 * @return the placename
	 */
	public String getPlacename() {
		return placename;
	}
	
	/**
	 * @param placename
	 *            the placename to set
	 */
	public void setPlacename(String placename) {
		this.placename = placename;
	}
	
	/**
	 * @return the adminname1
	 */
	public String getAdminname1() {
		return adminname1;
	}
	
	/**
	 * @param adminname1
	 *            the adminname1 to set
	 */
	public void setAdminname1(String adminname1) {
		this.adminname1 = adminname1;
	}
	
	/**
	 * @return the admincode1
	 */
	public Integer getAdmincode1() {
		return admincode1;
	}
	
	/**
	 * @param admincode1
	 *            the admincode1 to set
	 */
	public void setAdmincode1(Integer admincode1) {
		this.admincode1 = admincode1;
	}
	
	/**
	 * @return the adminname2
	 */
	public String getAdminname2() {
		return adminname2;
	}
	
	/**
	 * @param adminname2
	 *            the adminname2 to set
	 */
	public void setAdminname2(String adminname2) {
		this.adminname2 = adminname2;
	}
	
	/**
	 * @return the admincode2
	 */
	public Integer getAdmincode2() {
		return admincode2;
	}
	
	/**
	 * @param admincode2
	 *            the admincode2 to set
	 */
	public void setAdmincode2(Integer admincode2) {
		this.admincode2 = admincode2;
	}
	
	/**
	 * @return the adminname3
	 */
	public String getAdminname3() {
		return adminname3;
	}
	
	/**
	 * @param adminname3
	 *            the adminname3 to set
	 */
	public void setAdminname3(String adminname3) {
		this.adminname3 = adminname3;
	}
	
	/**
	 * @return the admincode3
	 */
	public Integer getAdmincode3() {
		return admincode3;
	}
	
	/**
	 * @param admincode3
	 *            the admincode3 to set
	 */
	public void setAdmincode3(Integer admincode3) {
		this.admincode3 = admincode3;
	}
	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}
	
	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}
	
	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the accuracy
	 */
	public Integer getAccuracy() {
		return accuracy;
	}
	
	/**
	 * @param accuracy
	 *            the accuracy to set
	 */
	public void setAccuracy(Integer accuracy) {
		this.accuracy = accuracy;
	}


}
