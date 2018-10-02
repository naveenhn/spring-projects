/**
 * 
 */
package com.sarvah.mbg.project.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author Shiva
 *
 */
public class BidDescriptionResponse {

	private String userId;
	private String fullName;
	private String bidDescription;
	private Date biddedDate;
	private Set<Address> address;
	private User user;

	private List<String> assetIds;

	/**
	 * @return the assetIds
	 */
	public List<String> getAssetIds() {
		return assetIds;
	}

	/**
	 * @param assetIds
	 *            the assetIds to set
	 */
	public void setAssetIds(List<String> assetIds) {
		this.assetIds = assetIds;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the bidDescription
	 */
	public String getBidDescription() {
		return bidDescription;
	}

	/**
	 * @param bidDescription
	 *            the bidDescription to set
	 */
	public void setBidDescription(String bidDescription) {
		this.bidDescription = bidDescription;
	}

	/**
	 * @return the biddedDate
	 */
	public Date getBiddedDate() {
		return biddedDate;
	}

	/**
	 * @param biddedDate
	 *            the biddedDate to set
	 */
	public void setBiddedDate(Date biddedDate) {
		this.biddedDate = biddedDate;
	}

	/**
	 * @return the address
	 */
	public Set<Address> getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Set<Address> address) {
		this.address = address;
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

}
