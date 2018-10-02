/**
 * 
 */
package com.sarvah.mbg.domain.user.auth;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

import com.sarvah.mbg.domain.user.UserInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class VerificationToken.
 *
 * @author naveen
 */
@Entity
@Table(name = "verification_token", schema = "mbgdb")
public class VerificationToken implements Serializable {

	/** The Constant DEFAULT_EXPIRY_TIME_IN_MINS. */
	private static final int DEFAULT_EXPIRY_TIME_IN_MINS = 60 * 24; // 24 hours
	
	/** The Constant TOKEN_PREPEND. */
	private static final String TOKEN_PREPEND = "MBG-";


	/** The token id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "verification_token_id")
	private int tokenId;

	/** The token type. */
	@Enumerated(EnumType.STRING)
	@Column(name = "tokentype")
	private VerificationTokenType tokenType;

	/** The user. */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserInfo user;

	/** The token. */
	private String token;

	/** The verified. */
	private boolean verified;

	/** The expiry date. */
	@Column(name = "expirydate_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryDate;

	/** The create by. */
	@Column(name = "createdby")
	private String createBy;

	/** The last modified by. */
	@Column(name = "modifiedby")
	private String lastModifiedBy;

	/** The created date. */
	@Column(name = "createdtime_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	/** The lastmodified date. */
	@Column(name = "lastmodifiedtime_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastmodifiedDate;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new verification token.
	 */
	protected VerificationToken() {
	}



	/**
	 * Instantiates a new verification token.
	 *
	 * @param userInfo the user info
	 * @param tokenType the token type
	 * @param expirationTimeInMinutes the expiration time in minutes
	 * @param createdBy the created by
	 */
	public VerificationToken(UserInfo userInfo, VerificationTokenType tokenType, int expirationTimeInMinutes,
			String createdBy) {
		this.token = TOKEN_PREPEND + UUID.randomUUID().toString();
		this.tokenType = tokenType;
		this.user = userInfo;
		this.createBy = createdBy;
		this.lastModifiedBy = createdBy;
		this.createdDate = new Date();
		this.lastmodifiedDate = createdDate;
		this.expiryDate = calculateExpiryDate(expirationTimeInMinutes);
	}
	
	/**
	 * Instantiates a new verification token but in this case the token string is given
	 *
	 * @param userInfo the user info
	 * @param token the token
	 * @param tokenType the token type
	 * @param expirationTimeInMinutes the expiration time in minutes
	 * @param createdBy the created by
	 */
	public VerificationToken(UserInfo userInfo, String token, VerificationTokenType tokenType, int expirationTimeInMinutes,
			String createdBy) {
		this.token = token;
		this.tokenType = tokenType;
		this.user = userInfo;
		this.createBy = createdBy;
		this.lastModifiedBy = createdBy;
		this.createdDate = new Date();
		this.lastmodifiedDate = createdDate;
		this.expiryDate = calculateExpiryDate(expirationTimeInMinutes);
	}

	/**
	 * Gets the token id.
	 *
	 * @return the tokenId
	 */
	public int getTokenId() {
		return tokenId;
	}

	/**
	 * Sets the token id.
	 *
	 * @param tokenId            the tokenId to set
	 */
	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}

	/**
	 * Gets the token type.
	 *
	 * @return the tokenType
	 */
	public VerificationTokenType getTokenType() {
		return tokenType;
	}

	/**
	 * Sets the token type.
	 *
	 * @param tokenType            the tokenType to set
	 */
	public void setTokenType(VerificationTokenType tokenType) {
		this.tokenType = tokenType;
	}



	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Checks if is verified.
	 *
	 * @return the verified
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * Sets the verified.
	 *
	 * @param verified            the verified to set
	 */
	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	/**
	 * Gets the expiry date.
	 *
	 * @return the expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * Sets the expiry date.
	 *
	 * @param expiryDate            the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * Gets the creates the by.
	 *
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * Sets the creates the by.
	 *
	 * @param createBy            the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * Gets the last modified by.
	 *
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * Sets the last modified by.
	 *
	 * @param lastModifiedBy            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}


	/**
	 * Gets the lastmodified date.
	 *
	 * @return the lastmodifiedDate
	 */
	public Date getLastmodifiedDate() {
		return lastmodifiedDate;
	}

	/**
	 * Sets the lastmodified date.
	 *
	 * @param lastmodifiedDate            the lastmodifiedDate to set
	 */
	public void setLastmodifiedDate(Date lastmodifiedDate) {
		this.lastmodifiedDate = lastmodifiedDate;
	}

	/**
	 * Gets the created date.
	 *
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Calculate expiry date.
	 *
	 * @param expiryTimeInMinutes the expiry time in minutes
	 * @return the date
	 */
	private Date calculateExpiryDate(int expiryTimeInMinutes) {
		DateTime now = new DateTime();
		if (expiryTimeInMinutes == 0) {
			expiryTimeInMinutes = DEFAULT_EXPIRY_TIME_IN_MINS;
		}
		return now.plusMinutes(expiryTimeInMinutes).toDate();
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

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public UserInfo getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user            the user to set
	 */
	public void setUser(UserInfo user) {
		this.user = user;
	}

}
