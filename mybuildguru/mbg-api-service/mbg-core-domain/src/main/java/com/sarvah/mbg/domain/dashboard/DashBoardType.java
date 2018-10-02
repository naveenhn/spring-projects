/**
 * 
 */
package com.sarvah.mbg.domain.dashboard;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author naveen
 *
 */
@Entity
@Table(name = "dashboard_type", schema = "mbgdb")
public class DashBoardType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "dashboard_type_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer dashboardTypeId;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private boolean provider;

	@Column(nullable = false)
	private boolean admin;

	@Column(nullable = false)
	private boolean dealer;

	@Column(nullable = false)
	private boolean architect;

	@Column(nullable = false)
	private boolean interiordesigner;

	@Column(nullable = false)
	private boolean enduser;



	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the provider
	 */
	public boolean isProvider() {
		return provider;
	}

	/**
	 * @param provider
	 *            the provider to set
	 */
	public void setProvider(boolean provider) {
		this.provider = provider;
	}

	/**
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * @param admin
	 *            the admin to set
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * @return the dealer
	 */
	public boolean isDealer() {
		return dealer;
	}

	/**
	 * @param dealer
	 *            the dealer to set
	 */
	public void setDealer(boolean dealer) {
		this.dealer = dealer;
	}

	/**
	 * @return the architect
	 */
	public boolean isArchitect() {
		return architect;
	}

	/**
	 * @param architect
	 *            the architect to set
	 */
	public void setArchitect(boolean architect) {
		this.architect = architect;
	}

	/**
	 * @return the interiordesigner
	 */
	public boolean isInteriordesigner() {
		return interiordesigner;
	}

	/**
	 * @param interiordesigner
	 *            the interiordesigner to set
	 */
	public void setInteriordesigner(boolean interiordesigner) {
		this.interiordesigner = interiordesigner;
	}

	/**
	 * @return the enduser
	 */
	public boolean isEnduser() {
		return enduser;
	}

	/**
	 * @param enduser
	 *            the enduser to set
	 */
	public void setEnduser(boolean enduser) {
		this.enduser = enduser;
	}


	/**
	 * @return the dashboardTypeId
	 */
	public Integer getDashboardTypeId() {
		return dashboardTypeId;
	}

	/**
	 * @param dashboardTypeId
	 *            the dashboardTypeId to set
	 */
	public void setDashboardTypeId(Integer dashboardTypeId) {
		this.dashboardTypeId = dashboardTypeId;
	}

}
