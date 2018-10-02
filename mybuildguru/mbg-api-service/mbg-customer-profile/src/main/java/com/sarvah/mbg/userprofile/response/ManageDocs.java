package com.sarvah.mbg.userprofile.response;

import java.util.List;
/**
 * 
 * @author Raju
 *
 */

public class ManageDocs {
	private List<AdminManageDocs> adminManageDocs;
	private int totalDocs;
	private int activeCount;
	private int inActiveCount;

	/**
	 * @return the adminManageDocs
	 */
	public List<AdminManageDocs> getAdminManageDocs() {
		return adminManageDocs;
	}

	/**
	 * @param adminManageDocs
	 *            the adminManageDocs to set
	 */
	public void setAdminManageDocs(List<AdminManageDocs> adminManageDocs) {
		this.adminManageDocs = adminManageDocs;
	}

	/**
	 * @return the activeCount
	 */
	public int getActiveCount() {
		return activeCount;
	}

	/**
	 * @param activeCount
	 *            the activeCount to set
	 */
	public void setActiveCount(int activeCount) {
		this.activeCount = activeCount;
	}

	/**
	 * @return the inActiveCount
	 */
	public int getInActiveCount() {
		return inActiveCount;
	}

	/**
	 * @param inActiveCount
	 *            the inActiveCount to set
	 */
	public void setInActiveCount(int inActiveCount) {
		this.inActiveCount = inActiveCount;
	}

	/**
	 * @return the totalDocs
	 */
	public int getTotalDocs() {
		return totalDocs;
	}

	/**
	 * @param totalDocs
	 *            the totalDocs to set
	 */
	public void setTotalDocs(int totalDocs) {
		this.totalDocs = totalDocs;
	}

}
