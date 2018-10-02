/**
 * 
 */
package com.sarvah.mbg.domain.mongo.marketing;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sarvah.mbg.domain.common.asset.Image;
import com.sarvah.mbg.domain.mongo.common.AbstractDocument;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author naveen
 *
 */
@Document
public class Banner extends AbstractDocument {

	private Description desc;


	@NotNull(message = "Banner owner cannot be null")
	@DBRef
	@JsonIgnore
	private User user;
	
	private List<String> productIds; //only for dealers

	@NotNull(message = "Banner start date cannot be null")
	private Date startDate;

	@NotNull(message = "Banner end date cannot be null")
	private Date endDate;



	private Image bannerImage;

	private String location;


	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}


	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the desc
	 */
	public Description getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(Description desc) {
		this.desc = desc;
	}

	/**
	 * @return the bannerImage
	 */
	public Image getBannerImage() {
		return bannerImage;
	}

	/**
	 * @param bannerImage
	 *            the bannerImage to set
	 */
	public void setBannerImage(Image bannerImage) {
		this.bannerImage = bannerImage;
	}

	/**
	 * @return the productIds
	 */
	public List<String> getProductIds() {
		return productIds;
	}

	/**
	 * @param productIds the productIds to set
	 */
	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}

}
