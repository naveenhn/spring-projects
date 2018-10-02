/**
 * 
 */
package com.sarvah.mbg.domain.user.auth;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

import com.sarvah.mbg.domain.user.UserInfo;

/**
 * @author naveen
 *
 */
@Entity
@Table(name = "authorization_token", schema = "mbgdb")
public class AuthorizationToken implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static Integer DEFAULT_TIME_TO_LIVE_IN_SECONDS = (60 * 60 * 12); // 12 hr

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "auth_token_id")
	private int tokenId;

	@OneToOne
	@JoinColumn(name = "user_id")
	private UserInfo user;

	private String token;

	@Column(name = "expirationdate_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryDate;

	@Column(name = "createdby")
	private String createBy;

	@Column(name = "modifiedby")
	private String lastModifiedBy;

	@Column(name = "createdtime_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "lastmodifiedtime_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastmodifiedDate;
	
	public AuthorizationToken() {
	}

	public AuthorizationToken(UserInfo user, String createdBy) {
		this(user, DEFAULT_TIME_TO_LIVE_IN_SECONDS, createdBy);
	}

	public AuthorizationToken(UserInfo user, int defaultTTLinSeconds, String createdBy) {
		this.token = UUID.randomUUID().toString();
		this.user = user;
		this.createdDate = new Date();
		this.lastmodifiedDate = createdDate;
		this.createBy = createdBy;
		this.lastModifiedBy = createdBy;
		this.expiryDate = new Date(System.currentTimeMillis() + (defaultTTLinSeconds * 1000L));
	}
	/**
	 * @return the tokenId
	 */
	public int getTokenId() {
		return tokenId;
	}

	/**
	 * @param tokenId
	 *            the tokenId to set
	 */
	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}

	/**
	 * @return the user
	 */
	public UserInfo getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(UserInfo user) {
		this.user = user;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
		
	}
	
	public void renewToken(String token) {
		this.token = token;
		setExpiryDate(new Date(System.currentTimeMillis() + (DEFAULT_TIME_TO_LIVE_IN_SECONDS * 1000L)));
		this.createdDate = new Date();
		this.lastmodifiedDate = createdDate;
	}
		
	

	/**
	 * @return the expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate
	 *            the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy
	 *            the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the lastmodifiedDate
	 */
	public Date getLastmodifiedDate() {
		return lastmodifiedDate;
	}

	/**
	 * @param lastmodifiedDate
	 *            the lastmodifiedDate to set
	 */
	public void setLastmodifiedDate(Date lastmodifiedDate) {
		this.lastmodifiedDate = lastmodifiedDate;
	}
	
	
	/**
	 * Checks for expired.
	 *
	 * @return true, if successful
	 */
	public boolean hasExpired() {
		DateTime tokenDate = new DateTime(getExpiryDate());
		return tokenDate.isBeforeNow();
	}

}
