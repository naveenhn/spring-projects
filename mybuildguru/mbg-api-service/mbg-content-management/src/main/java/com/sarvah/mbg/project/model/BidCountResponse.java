package com.sarvah.mbg.project.model;

public class BidCountResponse {
	private long architectBidsCount;
	private long interiorDesignerBidsCount;
	private long superAdminBidsCount;
	private long totalCount;

	/**
	 * @return the architectBidsCount
	 */
	public long getArchitectBidsCount() {
		return architectBidsCount;
	}

	/**
	 * @param architectBidsCount
	 *            the architectBidsCount to set
	 */
	public void setArchitectBidsCount(long architectBidsCount) {
		this.architectBidsCount = architectBidsCount;
	}

	/**
	 * @return the interiorDesignerBidsCount
	 */
	public long getInteriorDesignerBidsCount() {
		return interiorDesignerBidsCount;
	}

	/**
	 * @param interiorDesignerBidsCount
	 *            the interiorDesignerBidsCount to set
	 */
	public void setInteriorDesignerBidsCount(long interiorDesignerBidsCount) {
		this.interiorDesignerBidsCount = interiorDesignerBidsCount;
	}

	/**
	 * @return the superAdminBidsCount
	 */
	public long getSuperAdminBidsCount() {
		return superAdminBidsCount;
	}

	/**
	 * @param superAdminBidsCount
	 *            the superAdminBidsCount to set
	 */
	public void setSuperAdminBidsCount(long superAdminBidsCount) {
		this.superAdminBidsCount = superAdminBidsCount;
	}

	/**
	 * @return the totalCount
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

}
