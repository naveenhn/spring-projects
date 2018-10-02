/**
 * 
 */
package com.sarvah.mbg.domain.user;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author naveen
 *
 */
@Entity
@Table(name = "socialuser", schema = "mbgdb", uniqueConstraints= {@UniqueConstraint(columnNames= {"user_id", "providerid", "provideruserid"})})
public class SocialUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "socialuser_id")
	private int socialUserId;
	
	@ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name="user_id", nullable=false, updatable=false)
	private UserInfo user;
	
	@Column(name="providerid")
	private String providerId;
	
	@Column(name="secret")
	private String secret;
	
	@Column(name="accesstoken")
	private String accessToken;
	
	@Column(name="refreshtoken")
	private String refreshToken;
	
	@Column(name="provideruserid")
	private String providerUserId;
	
	@Column(name="providername")
	private String providerName;
	
	@Column(name="rank")
	private int rank;
	
	@Column(name="displayname")
	private String displayName;
	
	@Column(name="profileurl")
	private String profileUrl;
	
	@Column(name="imageurl")
	private String imageUrl;
	
	@Column(name="createdby")
	private String createdBy;
	
	@Column(name="modifiedby")
	private String modifiedBy;
	
	@Column(name="createdtime_dtm")
	private Date createdTime;
	
	@Column(name="lastmodifiedtime_dtm")
	private Date lastModifiedTime;
	
	@Column(name="expirationdate_dtm")
	private Date expirationDate;

	/**
	 * @return the socialUserId
	 */
	public int getSocialUserId() {
		return socialUserId;
	}

	/**
	 * @param socialUserId
	 *            the socialUserId to set
	 */
	public void setSocialUserId(int socialUserId) {
		this.socialUserId = socialUserId;
	}

	

	/**
	 * @return the providerId
	 */
	public String getProviderId() {
		return providerId;
	}

	/**
	 * @param providerId
	 *            the providerId to set
	 */
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	/**
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * @param secret
	 *            the secret to set
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken
	 *            the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * @param refreshToken
	 *            the refreshToken to set
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * @return the providerUserId
	 */
	public String getProviderUserId() {
		return providerUserId;
	}

	/**
	 * @param providerUserId
	 *            the providerUserId to set
	 */
	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	/**
	 * @return the providerName
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * @param providerName
	 *            the providerName to set
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank
	 *            the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the profileUrl
	 */
	public String getProfileUrl() {
		return profileUrl;
	}

	/**
	 * @param profileUrl
	 *            the profileUrl to set
	 */
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl
	 *            the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy
	 *            the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the createdTime
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	/**
	 * @param createdTime
	 *            the createdTime to set
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * @return the lastModifiedTime
	 */
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	/**
	 * @param lastModifiedTime
	 *            the lastModifiedTime to set
	 */
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate
	 *            the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the user
	 */
	public UserInfo getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserInfo user) {
		this.user = user;
	}

}
