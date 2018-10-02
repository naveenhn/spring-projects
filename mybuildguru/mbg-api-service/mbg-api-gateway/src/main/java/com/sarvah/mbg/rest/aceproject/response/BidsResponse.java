/**
 * 
 */
package com.sarvah.mbg.rest.aceproject.response;

import java.util.List;

import com.sarvah.mbg.domain.mongo.aceproject.Bid;
import com.sarvah.mbg.rest.model.AbstractCollectionResponse;

/**
 * @author naveen
 *
 */
public class BidsResponse extends AbstractCollectionResponse {

	private List<Bid> bids;

	/**
	 * @return the bids
	 */
	public List<Bid> getBids() {
		return bids;
	}

	/**
	 * @param bids
	 *            the bids to set
	 */
	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}

}
