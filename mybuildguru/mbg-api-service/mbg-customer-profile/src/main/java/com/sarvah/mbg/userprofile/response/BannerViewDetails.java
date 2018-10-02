/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

import java.util.Date;

import com.sarvah.mbg.domain.common.asset.FileType;

/**
 * @author Shiva
 *
 */
public class BannerViewDetails {

	private String customerName;

	private String role;

	private String location;

	private Date startDate;

	private Date endDate;

	private String status;

	private FileType fileFormat;

	private long fileSize;

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 *            the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the fileFormat
	 */
	public FileType getFileFormat() {
		return fileFormat;
	}

	/**
	 * @param fileType
	 *            the fileFormat to set
	 */
	public void setFileFormat(FileType fileType) {
		this.fileFormat = fileType;
	}

	/**
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * @param l
	 *            the fileSize to set
	 */
	public void setFileSize(long l) {
		this.fileSize = l;
	}

}
