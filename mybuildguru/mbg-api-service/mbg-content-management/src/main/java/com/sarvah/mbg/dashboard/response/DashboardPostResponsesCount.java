package com.sarvah.mbg.dashboard.response;

import java.util.List;

import com.sarvah.mbg.domain.mongo.aceproject.Bid;

/**
 * 
 * @author Raju
 *
 */

public class DashboardPostResponsesCount {
	private long totalCount;
	private long architects;
	private long interiorDesigners;
	private long buildGuru;
	private long dealers;
	private long biddingDone;
	private List<Bid> architectBids;
	private List<Bid> interiorDesignerBids;
	private List<Bid> buildGuruBids;
	private List<Bid> dealerBids;

	/**
	 * @return the architectBids
	 */
	public List<Bid> getArchitectBids() {
		return architectBids;
	}

	/**
	 * @param architectBids the architectBids to set
	 */
	public void setArchitectBids(List<Bid> architectBids) {
		this.architectBids = architectBids;
	}

	/**
	 * @return the interiorDesignerBids
	 */
	public List<Bid> getInteriorDesignerBids() {
		return interiorDesignerBids;
	}

	/**
	 * @param interiorDesignerBids the interiorDesignerBids to set
	 */
	public void setInteriorDesignerBids(List<Bid> interiorDesignerBids) {
		this.interiorDesignerBids = interiorDesignerBids;
	}

	/**
	 * @return the buildGuruBids
	 */
	public List<Bid> getBuildGuruBids() {
		return buildGuruBids;
	}

	/**
	 * @param buildGuruBids the buildGuruBids to set
	 */
	public void setBuildGuruBids(List<Bid> buildGuruBids) {
		this.buildGuruBids = buildGuruBids;
	}

	/**
	 * @return the dealerBids
	 */
	public List<Bid> getDealerBids() {
		return dealerBids;
	}

	/**
	 * @param dealerBids the dealerBids to set
	 */
	public void setDealerBids(List<Bid> dealerBids) {
		this.dealerBids = dealerBids;
	}

	public long getBiddingDone() {
		return biddingDone;
	}

	public void setBiddingDone(long biddingDone) {
		this.biddingDone = biddingDone;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getArchitects() {
		return architects;
	}

	public void setArchitects(long architects) {
		this.architects = architects;
	}

	public long getInteriorDesigners() {
		return interiorDesigners;
	}

	public void setInteriorDesigners(long interiorDesigners) {
		this.interiorDesigners = interiorDesigners;
	}

	public long getBuildGuru() {
		return buildGuru;
	}

	public void setBuildGuru(long buildGuru) {
		this.buildGuru = buildGuru;
	}

	public long getDealers() {
		return dealers;
	}

	public void setDealers(long dealers) {
		this.dealers = dealers;
	}

}
