/**
 * 
 */
package com.sarvah.mbg.rest.catalog.response;

import java.util.List;

import com.sarvah.mbg.rest.model.AbstractCollectionResponse;
import com.sarvah.mbg.userprofile.response.RecentlyViewedDetails;

/**
 * @author Shivu
 *
 */
public class RecentlyViewedResponse extends AbstractCollectionResponse {

	private List<RecentlyViewedDetails> recentlyViewedResponse;

	/**
	 * @return the recentlyViewedResponse
	 */
	public List<RecentlyViewedDetails> getRecentlyViewedResponse() {
		return recentlyViewedResponse;
	}

	/**
	 * @param recentlyViewedResponse
	 *            the recentlyViewedResponse to set
	 */
	public void setRecentlyViewedResponse(
			List<RecentlyViewedDetails> recentlyViewedResponse) {
		this.recentlyViewedResponse = recentlyViewedResponse;
	}

}
