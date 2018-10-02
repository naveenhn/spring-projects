/**
 * 
 */
package com.sarvah.mbg.domain.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sarvah.mbg.domain.user.auth.AuthorizationToken;
import com.sarvah.mbg.domain.user.auth.VerificationToken;

/**
 * @author naveen
 *
 */
@Entity
@Table(name = "user", schema = "mbgdb")
public class UserInfo implements Serializable, UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int userid;

	@Column(nullable = false, name = "hashedpassword")
	private String hashedPassword;

	@Column(nullable = false)
	private String firstname;

	//@Column(nullable = false)
	private String lastname;

	//@Column(nullable = false)
	private String username;

	@Column(name = "phonenumber")
	private Long phonenumber;

	@Column(name = "userchange_delta")
	private String userchangedelta;

	@Column(nullable = false, columnDefinition = "TINYINT", length = 1)
	private boolean verified;
	
	@Column(name="username_verified", columnDefinition = "TINYINT", length = 1)
	private boolean userNameVerified;
	
	@Column(name ="userphone_verified", columnDefinition = "TINYINT", length = 1)
	private boolean userPhoneVerified;

	@Enumerated(EnumType.STRING)
	private AuthRoles role;

	@Column(name = "mongo_useridref")
	private String mongoUserId;

	@Column(nullable = false)
	private String createdby;

	@Column(nullable = false)
	private String modifiedby;

	@Column(name = "createdtime_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdtime;

	@Column(name = "lastmodifiedtime_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastmodifiedtime;

	@OneToMany(mappedBy = "user", targetEntity = VerificationToken.class, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VerificationToken> verificationTokens = new ArrayList<>();

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private AuthorizationToken authorizationToken;

	public UserInfo() {
		// default constructor
	}

	public UserInfo(String username, String password, String firstName,
			String lastName, AuthRoles authRoles, String createdBy) {
		this.username = username;
		this.hashedPassword = password;
		this.firstname = firstName;
		this.lastname = lastName;
		this.role = authRoles;
		this.createdby = createdBy;
		this.modifiedby = createdBy;

	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *            the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 *            the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the username
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the createdtime
	 */
	public Date getCreatedtime() {
		return createdtime;
	}

	/**
	 * @param createdtime
	 *            the createdtime to set
	 */
	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}

	/**
	 * @return the createdby
	 */
	public String getCreatedby() {
		return createdby;
	}

	/**
	 * @param createdby
	 *            the createdby to set
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	/**
	 * @return the modifiedby
	 */
	public String getModifiedby() {
		return modifiedby;
	}

	/**
	 * @param modifiedby
	 *            the modifiedby to set
	 */
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	/**
	 * @return the lastmodifiedtime
	 */
	public Date getLastmodifiedtime() {
		return lastmodifiedtime;
	}

	/**
	 * @param lastmodifiedtime
	 *            the lastmodifiedtime to set
	 */
	public void setLastmodifiedtime(Date lastmodifiedtime) {
		this.lastmodifiedtime = lastmodifiedtime;
	}

	@Override
	public String toString() {

		return String
				.format("Userinfo[id=%s, username=%s, firstname=%s, lastname=%s, role=%s, isVerified=%s ]",
						userid, username, firstname, lastname, role, verified);
	}

	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		GrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
		authorities.add(authority);
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return new String(this.hashedPassword);
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * @return the mongoUserId
	 */
	public String getMongoUserId() {
		return mongoUserId;
	}

	/**
	 * @param mongoUserId
	 *            the mongoUserId to set
	 */
	public void setMongoUserId(String mongoUserId) {
		this.mongoUserId = mongoUserId;
	}

	/**
	 * @return the hashedPassword
	 */
	public String getHashedPassword() {
		return hashedPassword;
	}

	/**
	 * @param hashedPassword
	 *            the hashedPassword to set
	 */
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	/**
	 * @return the verificationTokens
	 */
	public synchronized List<VerificationToken> getVerificationTokens() {
		return verificationTokens;
	}

	/**
	 * @param verificationTokens
	 *            the verificationTokens to set
	 */
	public synchronized void addVerificationToken(
			VerificationToken verificationToken) {
		this.verificationTokens.add(verificationToken);
	}

	/**
	 * @return the authorizationToken
	 */
	public synchronized AuthorizationToken getAuthorizationToken() {
		return authorizationToken;
	}

	/**
	 * @param authorizationToken
	 *            the authorizationToken to set
	 */
	public synchronized void setAuthorizationToken(
			AuthorizationToken authorizationToken) {
		this.authorizationToken = authorizationToken;
	}

	/**
	 * @return the role
	 */
	public AuthRoles getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(AuthRoles role) {
		this.role = role;
	}

	/**
	 * @param verificationTokens
	 *            the verificationTokens to set
	 */
	public void setVerificationTokens(List<VerificationToken> verificationTokens) {
		this.verificationTokens = verificationTokens;
	}

	/**
	 * @return the verified
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * @param verified
	 *            the verified to set
	 */
	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	/**
	 * @return the phonenumber
	 */
	public Long getPhonenumber() {
		return phonenumber;
	}

	/**
	 * @param phonenumber
	 *            the phonenumber to set
	 */
	public void setPhonenumber(Long phonenumber) {
		this.phonenumber = phonenumber;
	}

	/**
	 * @return the userchangedelta
	 */
	public String getUserchangedelta() {
		return userchangedelta;
	}

	/**
	 * @param userchangedelta the userchangedelta to set
	 */
	public void setUserchangedelta(String userchangedelta) {
		this.userchangedelta = userchangedelta;
	}

	/**
	 * @return the userNameVerified
	 */
	public boolean isUserNameVerified() {
		return userNameVerified;
	}

	/**
	 * @param userNameVerified the userNameVerified to set
	 */
	public void setUserNameVerified(boolean userNameVerified) {
		this.userNameVerified = userNameVerified;
	}

	/**
	 * @return the userPhoneVerified
	 */
	public boolean isUserPhoneVerified() {
		return userPhoneVerified;
	}

	/**
	 * @param userPhoneVerified the userPhoneVerified to set
	 */
	public void setUserPhoneVerified(boolean userPhoneVerified) {
		this.userPhoneVerified = userPhoneVerified;
	}

}
