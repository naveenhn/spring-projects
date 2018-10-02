/**
 * 
 */
package com.sarvah.mbg.domain.dashboard;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author naveen
 *
 */
@Entity
@Table(name = "dashboard", schema = "mbgdb")
public class Dashboard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "dashboard_id")
	private Integer dashboardId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dashboard_type_id", nullable = false)
	private DashBoardType dashBoardType;

	@Column(name = "user_id")
	private String userId;

	@Column(nullable = false)
	private String data;

	@Column(nullable = false)
	private String createdby;
	

	@Column(nullable = false)
	private String modifiedby;

	@Column(name = "createdtime_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "lastmodifiedtime_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;




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
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
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
	 * @return the lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate
	 *            the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}



	/**
	 * @return the dashBoardType
	 */
	public DashBoardType getDashBoardType() {
		return dashBoardType;
	}

	/**
	 * @param dashBoardType
	 *            the dashBoardType to set
	 */
	public void setDashBoardType(DashBoardType dashBoardType) {
		this.dashBoardType = dashBoardType;
	}

	/**
	 * @return the dashboardId
	 */
	public Integer getDashboardId() {
		return dashboardId;
	}

	/**
	 * @param dashboardId
	 *            the dashboardId to set
	 */
	public void setDashboardId(Integer dashboardId) {
		this.dashboardId = dashboardId;
	}

}
